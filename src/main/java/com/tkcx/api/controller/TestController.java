package com.tkcx.api.controller;

import cn.hutool.core.date.DateUtil;
import com.acct.job.jobhandler.ScheduleRunnable;
import com.alibaba.fastjson.JSONObject;
import com.tkcx.api.business.hjtemp.fileService.BusiCodeFileService;
import com.tkcx.api.business.hjtemp.handle.HandleService;
import com.tkcx.api.business.hjtemp.service.AcctDetailTempService;
import com.tkcx.api.business.wdData.service.AcctDataService;
import com.tkcx.api.common.BeanContext;
import com.tkcx.api.service.BankApiService;
import com.tkcx.api.service.imp.AfeCommonService;
import com.tkcx.api.service.imp.QnBankApiServiceImpl;
import com.tkcx.api.utils.ToolUtil;
import com.tkcx.api.vo.ftpFile.FileDownloadReqVo;
import common.core.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private BankApiService bankApiService;

    @Autowired
    private HandleService handleService;


    @Autowired
    private AcctDataService acctDataService;

    @Autowired
    private AcctDetailTempService acctDetailTempService;

//    @Autowired
//    private AfeServiceWD afeService;



    /**
     * 测试AFE前置方法
     *
     * @param fileVo
     * @return
     */
    @RequestMapping(value = "/buildLink", method = RequestMethod.POST)
    public String buildLink(@RequestBody FileDownloadReqVo fileVo) {
        String busiPath = "";
        try {
            busiPath = bankApiService.downloadFile(fileVo);
            log.info("文件保存路径：" + busiPath);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        return busiPath;
    }

    /**
     * 测试文件下载方法
     *
     * @param fileVo
     * @return
     */
    @RequestMapping(value = "/testDownload", method = RequestMethod.POST)
    public String testDownloadFile(@RequestBody FileDownloadReqVo fileVo) {
        String busiPath = "";
        try {
            busiPath = bankApiService.downloadFile(fileVo);
            log.info("文件保存路径：" + busiPath);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        return busiPath;
    }

    /**
     * 测试文件解析数据方法
     *
     * @param fileVo
     * @return
     */
    @RequestMapping(value = "/testMakeData", method = RequestMethod.POST)
    public void testMakeData(@RequestBody FileDownloadReqVo fileVo) {

        String fileType = fileVo.getFileType();
        handleService.startHandle(fileType, DateUtil.parseDate(fileVo.getFileDate()));
    }

    /**
     * 手动数据生成
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/testAcctData", method = RequestMethod.POST)
    public String testAcctData(@RequestBody JSONObject json) {
        String result = "ok";
        try {
            bankApiService.acctDataHandler(json);
            log.info("请求参数：" + json.toJSONString());
        } catch (ApplicationException e) {
            log.error("数据异常{}", e);
        }
        return result;
    }

    @RequestMapping(value = "/testThread", method = RequestMethod.POST)
    public String testThread() {
        String result = "ok";
        new ScheduleRunnable().run();
        return result;
    }
    @Autowired
    private AfeCommonService afeCommonService;

    /**
     * 加密上传请求报文 ok
     * @param message
     * @return
     */
    @RequestMapping(value = "/encryptXml", method = RequestMethod.POST)
    public String encryptXml(@RequestBody String message) {
        log.info("测试加密上传请求报文 encryptXml message :"+message);
        try {
            String rspVo = bankApiService.encryptXml(message);
            return rspVo;
        } catch (ApplicationException e) {
            return null;
        }
    }

    /**
     * 定时抓取文件处理
     * @param message
     * @return
     */
    @RequestMapping(value = "/makeDownloadFile", method = RequestMethod.POST)
    public boolean makeDownloadFile(@RequestBody String message) throws ApplicationException {
        log.info("测试加密上传请求报文 encryptXml message :");
        boolean result = bankApiService.makeDownloadFile();
        return result;
    }
    private BusiCodeFileService busiCodeFileService = BeanContext.getBean(BusiCodeFileService.class);
    /**
     * 测试业务编码表删除
     * @return
     */
    @RequestMapping(value = "/delBusiCodeData", method = RequestMethod.POST)
    public String delBusiCodeData() throws ApplicationException {

        Date fileDate = new Date();
        busiCodeFileService.delBusiCodeData(fileDate);
        return "ok";
    }

//    /**
//     * 测试业务编码表删除
//     * @return
//     */
//    @RequestMapping(value = "/cu1", method = RequestMethod.POST)
//    public String cu1() throws ApplicationException {
//
//        String message = new ToolUtil().diao("123");
//        return "123";
//    }

}
