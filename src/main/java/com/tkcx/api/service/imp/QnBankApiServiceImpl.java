package com.tkcx.api.service.imp;

import cn.hutool.core.date.DateUtil;
import com.acct.job.thread.*;
import com.alibaba.fastjson.JSONObject;
import com.dcfs.esb.ftp.utils.ThreadSleepUtil;
import com.google.inject.internal.util.Lists;
import com.tkcx.api.business.acctPrint.model.AcctLogModel;
import com.tkcx.api.business.hjtemp.handle.HandleService;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.exception.FileErrorCode;
import com.tkcx.api.service.BankApiService;
import com.tkcx.api.vo.*;
import com.tkcx.api.vo.ftpFile.FileDownloadReqVo;
import common.core.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 综合服务类
 *
 * @author tianyi
 *
 */
@Slf4j
@Service("bankApiService")
public class QnBankApiServiceImpl implements BankApiService {

	@Autowired
	private BankCommonService bankCommonService;

	@Autowired
	private QnFtpClientServiceImpl qnFtpClientServiceImpl;
	@Autowired
	private ZhqdBusinesService zhqdBusinesService;

	@Autowired
	private HjFileInfoService hjFileInfoService;

	@Autowired
	public HjFileHandleService hjFileHandleService;

	@Autowired
	private AfeUtilsServiceImpl afeUtilsService;

	@Autowired
	private AfeCommonService afeCommonService;

	@Value("${storage.tempUpload.path}")
	private String tempUploadPath;
	@Value("${storage.tempDownload.path}")
	private String tempDownloadPath;
	@Value("${storage.remote.path}")
	private String remotePath;
	private static final int FILE_NAME_LENGTH = 40;

	@Override
	public String hjNotice(String EncryptMsg) throws ApplicationException {
//		log.info("QnBankApiServiceImpl hjNotice EncryptMsg:"+EncryptMsg);
		log.info("QnBankApiServiceImpl hjNotice DecryptMsg:"+EncryptMsg);
		//解密后的报文
		//互金直推不过AFE
//		String msg = afeUtilsService.afeDecryptMsg(EncryptMsg);
//		log.info("QnBankApiServiceImpl hjNotice DecryptMsg:"+msg);
		HjFileDataRspVo rsp = new HjFileDataRspVo();
		rsp.setTxnSt("S");
		rsp.setRetCd("000000");
		rsp.setRetMsg("通知成功");
		HjFileDataReqVo req = null;
		try {
			//报文转实体对象
			req = bankCommonService.receive(EncryptMsg, HjFileDataReqVo.class);
			//获取文件信息列表
			List fileList = req.getFileInfo();
			log.info("fileList :"+fileList);
			if(fileList == null || fileList.size() == 0) {
				log.info("fileList size null or size==0");
				log.error("互金文件信息为空");
				rsp.setRetCd("999999");
				rsp.setRetMsg("下载文件失败");
				String response = bankCommonService.response(req, rsp);
				return response;
			}
			log.info("互金推送的文件信息:{}", fileList.size());
			Date fileDate = DateUtil.parse(req.getSysHeadVo().getTxnDt(),"yyyy-MM-dd");
			// 查询是否已经推送了互金文件
			if(!queryHjFileInfo(fileDate)){
				log.error("互金推送的文件信息已在数据库中存在，但已存在信息更新异常");
				rsp.setRetCd("999999");
				rsp.setRetMsg("下载文件失败");
				String response = bankCommonService.response(req, rsp);
				return response;
			}

			// 组装保存推送数据信息
			List<HjFileInfoModel> hjFileList = assembleHjFileInfo(fileList, fileDate);
			if(hjFileList == null) {
				log.error("{}日，互金数据保存失败", fileDate);
				log.info("{}日，互金数据保存失败", fileDate);
			}
			// 互金推送数据日志表记录
			if (null != req.getSysHeadVo()) {
				AcctLogModel acctLog = new AcctLogModel();
				acctLog.setAcctDate(DateUtil.parse(req.getSysHeadVo().getAcgDt()));
				acctLog.setContent(req.getRemark());
				acctLog.setLogType(0);
				acctLog.setSerialNo(req.getSysHeadVo().getCnsmrSeqNo());
				acctLog.insert();
			}
			// 异步下载文件,解析后保存至数据库中
			hjFileHandleService.downloadHjFile(hjFileList, fileDate,req);
		} catch (Exception e) {
			log.error("文件下载请求失败,错误原因：{}", e);
			log.info("文件下载请求失败,错误原因：{}", e);
			rsp.setRetCd("999999");
			rsp.setRetMsg("下载文件失败");
		}finally {
			log.info("hjNotice finally req:"+req+",rsp:"+rsp);
			String message = bankCommonService.response(req, rsp);
//			String responseData = afeCommonService.encrypt(req, message);

			return message;
		}
	}



	/**
	 * 查询是否已经推送了互金文件
	 *
	 * @param fileDate
	 */
	private boolean queryHjFileInfo(Date fileDate){

		HjFileInfoModel queryInfo = new HjFileInfoModel();
		queryInfo.setFileDate(fileDate);
		queryInfo.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
		// 查看当日是否已经推送了数据
		List<HjFileInfoModel> hjFileInfoModels = hjFileInfoService.selectModelList(queryInfo);
		log.info("日期{}，查询到的互金推送记录{}", fileDate, hjFileInfoModels.size());
		// 如果推送了，更新已经推送的记录为删除状态
		if(hjFileInfoModels != null && hjFileInfoModels.size() > 1 &&
				!hjFileInfoService.updateBatchById(hjFileInfoModels)){
			return false;
		}
		return true;
	}


	/**
	 * 组装保存入库互金文件信息
	 *
	 * @param fileList
	 * @param fileDate
	 * @return
	 */
	private List<HjFileInfoModel> assembleHjFileInfo(List<HjFileDataReqVo.FileInfo> fileList, Date fileDate) {

		List<HjFileInfoModel> hjFileList = Lists.newArrayList();
		for (HjFileDataReqVo.FileInfo file : fileList) {
			HjFileInfoModel hjFileInfoModel = new HjFileInfoModel();
			hjFileInfoModel.setFileName(file.getFileNm());
			hjFileInfoModel.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
			hjFileInfoModel.setReadFlag(HjFileFlagConstant.NOT_FINISH);
			hjFileInfoModel.setNextReadStartNum(HjFileFlagConstant.READ_START_NUM_INITIAL);
			hjFileInfoModel.setNextReadEndNum(HjFileFlagConstant.READ_END_NUM_INITIAL);
			hjFileInfoModel.setFileTransCode(file.getFileTrnsmCd());
			hjFileInfoModel.setFileType(file.getFileFlag());
			hjFileInfoModel.setCreateDate(DateUtil.date());
			// 文件日期
			hjFileInfoModel.setFileDate(fileDate);
			// 文件路径
			hjFileInfoModel.setFilePath(file.getFilPath() + file.getFileNm());
			hjFileList.add(hjFileInfoModel);
		}
		if(hjFileInfoService.saveBatch(hjFileList)) {
			return hjFileList;
		}
		return null;
	}

	/**
	 * 获取文件类型
	 * @param fileName
	 * @return
	 */
	public static String fileExcuteGetType(String fileName){
		int start = fileName.lastIndexOf(File.separator);
		int end = fileName.lastIndexOf("_");
		String fileType = fileName.substring(start + 1,end);
		return fileType;
	}
	/**
	 * 获取文件时间
	 * @param fileName
	 * @return
	 */
	public static Date fileExcuteGetData(String fileName,String fileType){
		int start = fileName.lastIndexOf("_");
		int end = fileName.lastIndexOf(".");
		String fileData = fileName.substring(start + 1,end);
		Date date = DateUtil.parse(fileData, "yyyyMMdd");
		return date;
	}
	@Override
	public String zhqdQuery(String EncryptMsg) throws ApplicationException {
		log.info("QnBankApiServiceImpl zhqdQuery EncryptMsg:"+EncryptMsg);
		//解密后的报文
		String msg = afeUtilsService.afeDecryptMsg(EncryptMsg);
		log.info("QnBankApiServiceImpl zhqdQuery DecryptMsg:"+msg);
		ZhqdQueryRspVo rsp = new ZhqdQueryRspVo();
		ZhqdQueryReqVo req = null;
		String resultXml = "";
		try {
			req = bankCommonService.receiveZH(msg, ZhqdQueryReqVo.class);
			rsp = zhqdBusinesService.queryEntry(req);//获取返回信息
			resultXml = bankCommonService.responseZH(req, rsp);
		}catch (ApplicationException e) {
			log.error("查询失败,错误原因：" + e);
			rsp.setRetCd("999999");
			rsp.setRetMsg("查询失败");
		}
		String responseData = afeCommonService.encrypt(req, resultXml);
		return responseData;
	}


	/**
	 * 加密请求报文
	 */
	@Override
	public String encryptXml(String msg)  {
		log.info("QnBankApiServiceImpl encryptXml 请求明文:"+msg);
		//AFE请求ACCT的柜面请求报文密文
		String responseData = afeCommonService.encryptXML(msg);
		return responseData;
	}
	/**
	 * 文件下载方法
	 * @param req 下载信息
	 * @return 返回文件保存路径
	 * @throws ApplicationException
	 */
	@Override
	public String downloadFile(FileDownloadReqVo req) throws ApplicationException {
		if (req == null) {
			throw new ApplicationException(FileErrorCode.FILES_CANNOT_BE_EMPTY);
		}

		if (req.getFilePath() == null || req.getFilePath().isEmpty()) {
			throw new ApplicationException(FileErrorCode.FILES_CANNOT_BE_EMPTY);
		}

		String downloadTranCode = req.getDownloadTranCode();
		if (downloadTranCode == null || downloadTranCode.isEmpty()) {
			throw new ApplicationException(FileErrorCode.FILE_DOWNLOAD_CODE_CANNOT_BE_EMPTY);
		}

		String remoteFilePath = req.getFilePath();
		if (!remoteFilePath.startsWith("/")) {
			remoteFilePath = "/" + remoteFilePath;
		}
		String downloadPath = tempDownloadPath + DateUtil.format(new Date(),"yyyyMMddHHmmss") + "/";

		if (!StringUtils.isEmpty(downloadPath)) {
			File tempDownloadDir = new File(downloadPath);
			if (!tempDownloadDir.exists()) {
				tempDownloadDir.mkdir();
			}
		}

		File remoteFile = new File(remoteFilePath);
		String fileName = remoteFile.getName();
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

		String localFullPathFileName = downloadPath
				+ RandomStringUtils.randomAlphanumeric(FILE_NAME_LENGTH).toLowerCase() + "." + fileExt;
		qnFtpClientServiceImpl.getFileStreamByTranCode(localFullPathFileName, remoteFilePath, downloadTranCode);

		return localFullPathFileName;
	}

	/**
	 * 异步执行
	 * @param json 时间范围 {"startAcctDate":"", "endAcctDate":""}
	 * @return
	 * @throws ApplicationException
	 */
	@Async
	@Override
	public String acctDataHandler(JSONObject json) throws ApplicationException {

		//获取会计时间
		Date startAcctDate = json.getDate("startAcctDate");
		Date endAcctDate = json.getDate("endAcctDate");
		Integer acctNo = json.getInteger("acctNo");

		Date startDate = new Date();
		log.info("AcctDataHandler start ..." + startDate);

		long days = DateUtil.betweenDay(startAcctDate, endAcctDate, true);

		// 创建线程池
		ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
				5,10,60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(5),
				Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
//		ExecutorService exe = Executors.newFixedThreadPool(Integer.valueOf(String.valueOf(days)));

		for(int i = 0; i < days; i++) {


			if (acctNo ==null || acctNo == 1){
				// 1. 借款借据回单
//				exe.execute(new LoanReceiptThread(endAcctDate));
				threadPool.execute(new LoanReceiptThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 2) {
				// 2. 还款回单
//				exe.execute(new RefundReceiptThread(endAcctDate));
				threadPool.execute(new RefundReceiptThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 3) {
				// 3. 网贷业务机构轧账单
//				exe.execute(new BusiOrgBillThread(endAcctDate));
				threadPool.execute(new BusiOrgBillThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 4) {
				// 4. 网贷业务机构业务流水
//				exe.execute(new BusiOrgSeqThread(endAcctDate));
				threadPool.execute(new BusiOrgSeqThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 5) {
				// 5. 贷款分户账
//				exe.execute(new LoanAccBillThread(endAcctDate));
				threadPool.execute(new LoanAccBillThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 6) {
				// 6. 贷款明细账
//				exe.execute(new LoanDetailBillThread(endAcctDate));
				threadPool.execute(new LoanDetailBillThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 7) {
				// 7. 贷款利息登记簿
//				exe.execute(new InterestBillThread(endAcctDate));
				threadPool.execute(new InterestBillThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 8) {
				// 8. 会计凭证(记账凭证/交易凭证)
//				exe.execute(new AcctVoucherThread(endAcctDate));
				threadPool.execute(new AcctVoucherThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 9) {
				// 9. 贷款形态调整明细清单、贷款调整登记簿
//				exe.execute(new LoanAdjustThread(endAcctDate));
				threadPool.execute(new LoanAdjustThread(endAcctDate));
			}

			endAcctDate = DateUtil.offsetDay(endAcctDate, -1);
		}


		// 关闭线程池
		threadPool.shutdown();
		// 判断线程是否执行完成
		while (true) {
			if (threadPool.isTerminated()) {
				Date endDate = new Date();
				log.info("AcctDataHandler end ......" + endDate);
				log.info("定时任务耗时：" + DateUtil.formatBetween(startDate,endDate));
				break;
			}
			ThreadSleepUtil.sleepSecondIngoreEx(60);
		}
		return "0000";
	}


	@Autowired
	private HandleService handleService;
	@Override
	public boolean makeDownloadFile(){
		try {
			boolean result = false;
			log.info("makeDownloadFile" );
			ArrayList<String> fileNameList = new ArrayList<>();
			fileNameList.add("t_act_one_detail");
			fileNameList.add("t_act_brch_day_tot");
			fileNameList.add("t_act_busi_code_map");
			fileNameList.add("t_act_pub_org");
			fileNameList.add("t_act_one_detail_99000000");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			//String format = sdf.format(new Date());
			//String format1 = sdf1.format(new Date());
			String format =sdf.format(20230623);
			String format1 =sdf.format(20230623);
			Date fileDate = DateUtil.parse(format,"yyyyMMdd");
			ArrayList<HjFileInfoModel> hjFileInfoModels = new ArrayList<>();
			for (String fileName : fileNameList) {
				HjFileInfoModel hjFileInfoModel = new HjFileInfoModel();
				hjFileInfoModel.setFileName(fileName+"_"+format1+".txt");
				hjFileInfoModel.setFileType(fileName);
				hjFileInfoModel.setFilePath("/share_data/act/20230623");
				//systemNO
				hjFileInfoModel.setFileTransCode("113500");
				hjFileInfoModels.add(hjFileInfoModel);
			}
			HjFileDataReqVo req = new HjFileDataReqVo();
			for (HjFileInfoModel hjFileInfoModel : hjFileInfoModels) {
				FileDownloadReqVo fileVo = new FileDownloadReqVo();
				// 文件传输码
				fileVo.setDownloadTranCode(hjFileInfoModel.getFileTransCode());
				// 文件下载路径
				fileVo.setFilePath(hjFileInfoModel.getFilePath());
				// 下载文件
				String downFilePath = hjFileHandleService.downloadFile(fileVo, req);
				String fileType = hjFileInfoModel.getFileType();
				if(StringUtils.isNotEmpty(downFilePath)){
					log.info("日期{}，文件 {} ====== 下载成功", hjFileInfoModel.getFileDate(), fileType);

					/** TODO 要改成定时器方式触发解析互金文件逻辑
					 * 需要保存下载路径到数据库，然后定时器触发时，需要根据文件类型、文件日期，读取未删除、未读取的互金文件信息
					 */
					hjFileHandleService.saveHjFileDownloadPath(downFilePath, hjFileInfoModel);
					// 下载成功后，解析文件，入库
					handleService.startHandle(fileType, fileDate);

				}
				return result;
			}
			return result;
		} catch (ApplicationException e) {
			log.info(e.getMessage());
			throw new RuntimeException(e);
		}
	}


	public String  makeDownloadFile1(){
		try {
			String message ="000016630030jwbSaTTkGJ6Qudgbe0rC/uNO106100<?xml version=\"1.0\" encoding=\"UTF-8\"?><Service><SysHead><SvcCd>3002040002</SvcCd><SvcScn>03</SvcScn><SvcSplrTxnCd></SvcSplrTxnCd><CnsmrSysId>108400</CnsmrSysId><TxnDt>2022-09-03</TxnDt><TxnTm>231706</TxnTm><AcgDt>2022-09-03</AcgDt><CnsmrSeqNo>1084002022090211170609180189</CnsmrSeqNo><TxnChnlTp>01000000</TxnChnlTp><ChnlDtl>01000000</ChnlDtl><TxnTmlId></TxnTmlId><CnsmrSvrId>8.1.8.244</CnsmrSvrId><OrigCnsmrSeqNo>1084002022090211170609180189</OrigCnsmrSeqNo><OrigCnsmrId>108400</OrigCnsmrId><OrigTmlId></OrigTmlId><OrigCnsmrSvrId></OrigCnsmrSvrId><UsrLng>CHINESE</UsrLng><FileFlg></FileFlg></SysHead><AppHead><TxnTlrId>ACCT</TxnTlrId><OrgId>27013000</OrgId><TlrPwsd></TlrPwsd><TlrLvl></TlrLvl><TlrTp></TlrTp><AprvFlg></AprvFlg><AhrTlrInf type=\"array\"></AhrTlrInf><AprvTlrInf type=\"array\"></AprvTlrInf><AhrFlg></AhrFlg></AppHead><Body><TranOcrDt>23:17:06</TranOcrDt><Rmrk>数据抽取</Rmrk><DtlInfoAry type=\"array\"><Struct><MakeFileFlg>t_act_one_detail</MakeFileFlg><FilPath>/100024/acc/</FilPath><FileNm>t_act_one_detail_20220902.txt</FileNm><FileTrnsmCd>100024</FileTrnsmCd></Struct><Struct><MakeFileFlg>t_act_brch_day_tot</MakeFileFlg><FilPath>/100024/acc/</FilPath><FileNm>t_act_brch_day_tot_20220902.txt</FileNm><FileTrnsmCd>100024</FileTrnsmCd></Struct><Struct><MakeFileFlg>t_act_busi_code_map</MakeFileFlg><FilPath>/100024/acc/</FilPath><FileNm>t_act_busi_code_map_20220902.txt</FileNm><FileTrnsmCd>100024</FileTrnsmCd></Struct><Struct><MakeFileFlg>t_act_pub_org</MakeFileFlg><FilPath>/100024/acc/</FilPath><FileNm>t_act_pub_org_20220902.txt</FileNm><FileTrnsmCd>100024</FileTrnsmCd></Struct></DtlInfoAry></Body></Service>";
			return hjNotice(message);
		} catch (ApplicationException e) {
			log.info(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws ApplicationException {
		new QnBankApiServiceImpl().makeDownloadFile();
	}
}
