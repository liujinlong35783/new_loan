package com.acct.job.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/4 15:40
 */
@Slf4j
public class PageUtils {

    public static int startPageNum = 1;

    public static int pageSize = 5000;

    public static int pageSizeSeq = 10000;

    /**
     * 计算总页数
     *
     * @param totalRecord
     * @return
     */
    public static int calTotalPage(int totalRecord) {

        return (totalRecord + pageSize - 1) / pageSize;
    }

    /**
     * 计算总页数
     *
     * @param totalRecord
     * @return
     */
    public static int calTotalPageSeq(int totalRecord) {

        return (totalRecord + pageSizeSeq - 1) / pageSizeSeq;
    }


}
