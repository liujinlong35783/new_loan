package com.tkcx.api.controller;

import com.tkcx.api.service.BankApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import common.core.exception.ApplicationException;

import java.text.ParseException;

@Slf4j
@RestController
public class ServerController {

    @Autowired
    private BankApiService bankApiService;

    /**
     * 互金通知下载报文（23：00（日切之后）~2：00通知下载，日期文件都为23：00当日）
     * @param message xml加密报文
     * @return
     */
    @RequestMapping(value = "/acctNotice", method = RequestMethod.POST)
    public String singleSend(@RequestBody String message) {
        try {
            String rspVo = bankApiService.hjNotice(message);
            log.info("/acctNotice end :"+rspVo);
            return rspVo;
        } catch (ApplicationException e) {
            e.printStackTrace();
            log.info(e.getMessage());
            return null;
        }
    }
}
