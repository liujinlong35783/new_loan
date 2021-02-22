package com.tkcx.api.service.imp;

import cn.hutool.core.date.DateUtil;
import com.acct.job.thread.*;
import com.alibaba.fastjson.JSONObject;
import com.dcfs.esb.ftp.utils.ThreadSleepUtil;
import com.tkcx.api.business.hjtemp.Handle.HandleService;
import com.tkcx.api.exception.FileErrorCode;
import com.tkcx.api.service.BankApiService;
import com.tkcx.api.vo.HjFileDataReqVo;
import com.tkcx.api.vo.HjFileDataRspVo;
import com.tkcx.api.vo.ZhqdQueryReqVo;
import com.tkcx.api.vo.ZhqdQueryRspVo;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	private HandleService handleService;
	@Autowired
	private QnFtpClientServiceImpl qnFtpClientServiceImpl;
    @Autowired
	private ZhqdBusinesService zhqdBusinesService;

	@Value("${storage.tempUpload.path}")
	private String tempUploadPath;
	@Value("${storage.tempDownload.path}")
	private String tempDownloadPath;
	@Value("${storage.remote.path}")
	private String remotePath;
	private static final int FILE_NAME_LENGTH = 40;

	@Override
	public String hjHotice(String msg) throws ApplicationException {
		HjFileDataRspVo rsp = new HjFileDataRspVo();
		rsp.setTxnSt("S");
		rsp.setRetCd("000000");
		rsp.setRetMsg("通知成功");
		HjFileDataReqVo req = null;
		//开始业务
		String brchPath = "", busiPath = "", detailPath = "", orgPath = "";
		//下载文件
		FileDownloadReqVo fileVo = new FileDownloadReqVo();
		try {
			//报文转实体对象
			req = bankCommonService.receive(msg, HjFileDataReqVo.class);
			//获取文件信息列表
			List<HjFileDataReqVo.FileInfo> filelist = req.getFileInfo();
			if(filelist!=null && filelist.size()>0)
			for (HjFileDataReqVo.FileInfo file: filelist) {
				fileVo.setDownloadTranCode(file.getFileTrnsmCd());//文件传输码
				fileVo.setFilePath(file.getFilPath() + file.getFileNm());//文件下载路径
				//判断文件类型
				switch (file.getFileFlag()){
					case "t_act_one_detail":
						detailPath = downloadFile(fileVo);//返回文件保存路径
						break;
					case "t_act_brch_day_tot":
						brchPath = downloadFile(fileVo);//返回文件保存路径
						break;
					case "t_act_busi_code_map":
						busiPath = downloadFile(fileVo);//返回文件保存路径
						break;
					case "t_act_pub_org":
						orgPath = downloadFile(fileVo);//返回文件保存路径
						break;
				}
			}
			handleService.setHjFileDataReq(req);
			//异步执行保存工作
			handleService.startHandle(brchPath, busiPath, detailPath, orgPath);
			if(StringUtils.isNotEmpty(brchPath)){
				log.info("t_act_brch_day_tot ====== 文件下载成功");
			}
			if(StringUtils.isNotEmpty(busiPath)){
				log.info("t_act_busi_code_map ====== 文件下载成功");
			}
			if(StringUtils.isNotEmpty(detailPath)){
				log.info("t_act_one_detail ====== 文件下载成功");
			}
			if(StringUtils.isNotEmpty(orgPath)){
				log.info("org机构信息 ====== 文件下载成功");
			}
			//结束业务
		} catch (ApplicationException e) {
			log.error("文件下载请求失败,错误原因：" + e);
			rsp.setRetCd("999999");
			rsp.setRetMsg("下载文件失败");
		}finally {
			String response = bankCommonService.response(req, rsp);
			return response;
		}
	}

	@Override
	public String zhqdQuery(String msg) throws ApplicationException {
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
		return resultXml;
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
		ExecutorService exe = Executors.newFixedThreadPool(Integer.valueOf(String.valueOf(days)));

		for(int i = 0; i < days; i++) {


			if (acctNo ==null || acctNo == 1){
				// 1. 借款借据回单
				exe.execute(new LoanReceiptThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 2) {
				// 2. 还款回单
				exe.execute(new RefundReceiptThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 3) {
				// 3. 网贷业务机构轧账单
				exe.execute(new BusiOrgBillThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 4) {
				// 4. 网贷业务机构业务流水
				exe.execute(new BusiOrgSeqThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 5) {
				// 5. 贷款分户账
				exe.execute(new LoanAccBillThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 6) {
				// 6. 贷款明细账
				exe.execute(new LoanDetailBillThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 7) {
				// 7. 贷款利息登记簿
				exe.execute(new InterestBillThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 8) {
				// 8. 会计凭证(记账凭证/交易凭证)
				exe.execute(new AcctVoucherThread(endAcctDate));
			}
			if (acctNo ==null || acctNo == 9) {
				// 9. 贷款形态调整明细清单、贷款调整登记簿
				exe.execute(new LoanAdjustThread(endAcctDate));
			}

			endAcctDate = DateUtil.offsetDay(endAcctDate, -1);
		}


		// 关闭线程池
		exe.shutdown();
		// 判断线程是否执行完成
		while (true) {
			if (exe.isTerminated()) {
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
