package com.tkcx.api.business.acctPrint.model.Interface;

public interface IAcctPrintCommon {
    /**
     * @return 转换后的html代码
     */
    String toHtmlString();

    /**
     * 定制html的列表title
     * @param htmlBuffer
     */
    void customizedHtmlTitle(StringBuffer htmlBuffer);
}
