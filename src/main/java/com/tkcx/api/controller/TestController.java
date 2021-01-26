package com.tkcx.api.controller;

import com.acct.job.jobhandler.ScheduleRunnable;
import com.alibaba.fastjson.JSONObject;
import com.tkcx.api.business.hjtemp.Handle.HandleService;
import com.tkcx.api.service.BankApiService;
import com.tkcx.api.vo.ftpFile.FileDownloadReqVo;
import common.core.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private BankApiService bankApiService;

    @Autowired
    private HandleService handleService;
    /**
     * 测试文件下载方法
     * @param fileVo
     * @return
     */
    @RequestMapping(value = "/testDownload", method = RequestMethod.POST)
    public String testDownloadFile(@RequestBody FileDownloadReqVo fileVo){
        String busiPath = "";
        try {
            busiPath = bankApiService.downloadFile(fileVo);
            log.info("文件保存路径："+busiPath);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        return busiPath;
    }

    /**
     * 测试文件解析数据方法
     * @param fileVo
     * @return
     */
    @RequestMapping(value = "/testMakeData", method = RequestMethod.POST)
    public String testMakeData(@RequestBody FileDownloadReqVo fileVo){
        return handleService.startHandle(fileVo);
    }

    /**
     * 手动数据生成
     * @param json
     * @return
     */
    @RequestMapping(value = "/testAcctData", method = RequestMethod.POST)
    public String testAcctData(@RequestBody JSONObject json){
        String result = "ok";
        try {
            bankApiService.acctDataHandler(json);
            log.info("请求参数："+json.toJSONString());
        } catch (ApplicationException e) {
            log.error("数据异常{}",e);
        }
        return result;
    }

    @RequestMapping(value = "/testThread", method = RequestMethod.POST)
    public String testThread(){
        String result = "ok";
        new ScheduleRunnable().run();
        return result;
    }
}
