package com.tkcx.api.controller;

import com.tkcx.api.service.BankApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import common.core.exception.ApplicationException;


@RestController
public class ServerController {

    @Autowired
    private BankApiService bankApiService;

    /**
     * 互金通知下载报文
     * @param message xml加密报文
     * @return
     */
    @RequestMapping(value = "/acctNotice", method = RequestMethod.POST)
    public String singleSend(@RequestBody String message) {
        try {
            return bankApiService.hjNotice(message);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 综合前端查询
     * @param message ml加密报文
     * @return
     */
    @RequestMapping(value = "/queryAcctData", method = RequestMethod.POST)
    public String queryAcctData(@RequestBody String message) {
        try {
            String rspVo = bankApiService.zhqdQuery(message);
            return rspVo;
        } catch (ApplicationException e) {
            return null;
        }
    }
}
