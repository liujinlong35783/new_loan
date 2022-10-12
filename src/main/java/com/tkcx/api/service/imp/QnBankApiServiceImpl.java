package com.tkcx.api.service.imp;

import cn.hutool.core.date.DateTime;
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
import com.tkcx.api.utils.DateUtils;
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
import java.text.ParseException;
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
	private QnFtpClientServiceImpl qnFtpClientServiceImpl;

	@Autowired
	private HjFileInfoService hjFileInfoService;

	@Autowired
	public HjFileHandleService hjFileHandleService;
	@Autowired
	private AfeUtilsServiceImpl afeUtilsService;
	@Autowired
	private BankCommonService bankCommonService;
	@Autowired
	private AfeCommonService afeCommonService;

	@Autowired
	private ZhqdBusinesService zhqdBusinesService;

	@Value("${storage.tempDownload.path}")
	private String tempDownloadPath;
	private static final int FILE_NAME_LENGTH = 40;

	@Override
	public String hjNotice(String EncryptMsg) throws ApplicationException {
		log.info("QnBankApiServiceImpl hjNotice EncryptMsg:"+EncryptMsg);
		//解密后的报文
		String msg = afeUtilsService.afeDecryptMsg(EncryptMsg);
		log.info("QnBankApiServiceImpl hjNotice DecryptMsg:"+msg);
		HjFileDataRspVo rsp = new HjFileDataRspVo();
		rsp.setTxnSt("S");
		rsp.setRetCd("000000");
		rsp.setRetMsg("通知成功");
		HjFileDataReqVo req = null;
		try {
			//报文转实体对象
			req = bankCommonService.receive(msg, HjFileDataReqVo.class);
			//获取文件信息列表
			List<HjFileDataReqVo.FileInfo> fileList = req.getFileInfo();
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
			String responseData = afeCommonService.encrypt(req, message);

			return responseData;
		}
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
			req = bankCommonService.receive(msg, ZhqdQueryReqVo.class);
			rsp = zhqdBusinesService.queryEntry(req);//获取返回信息
			resultXml = bankCommonService.response(req, rsp);
		}catch (ApplicationException e) {
			log.error("查询失败,错误原因：" + e);
			rsp.setRetCd("999999");
			rsp.setRetMsg("查询失败");
		}
		String responseData = afeCommonService.encrypt(req, resultXml);
		return responseData;
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
}
