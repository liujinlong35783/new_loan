package com.tkcx.api.business.hjtemp.hjThread;

import java.util.Date;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/3 17:00
 */
public class HjBaseThread extends Thread {

    private String filePath;

    private Boolean isRemove;

    private Date fileDate;

    /**
     * 标志线程阻塞情况
     */
    private boolean pause = true;

    public HjBaseThread(String filePath, Boolean isRemove, Date fileDate) {
        this.filePath = filePath;
        this.isRemove = isRemove;
        this.fileDate = fileDate;
    }

}
