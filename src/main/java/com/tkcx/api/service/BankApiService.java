package com.tkcx.api.service;

import com.alibaba.fastjson.JSONObject;
import com.tkcx.api.vo.ftpFile.FileDownloadReqVo;
import common.core.exception.ApplicationException;

import java.text.ParseException;

/**
 * 服务接口
 *
 * @author linghujie
 *
 */
public interface BankApiService {

    /**
     * 互金通知
     */
    String hjNotice(String msg) throws ApplicationException;

    /**
     * 综合前端查询接口
     */
    String zhqdQuery(String msg) throws ApplicationException;

    /**
     * 文件下载
     * @param req
     * @return
     * @throws ApplicationException
     */
    String downloadFile(FileDownloadReqVo req) throws ApplicationException;

    /**
     * 手动任务处理
     * @param json 时间范围
     * @return
     * @throws ApplicationException
     */
    String acctDataHandler(JSONObject json) throws ApplicationException;
}

