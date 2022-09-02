package com.tkcx.api.vo.afe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Body")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AfeBusinessBodyRspVo {

    private String localFilePath;
    private String localFilePathInfo;
    private String errCode;
    private String errMsg;
    //文件传输代码默认100033
    private String fileTransCode;
    //文件传输平台全路径(含文件名称)
    private String fileAllPath;

    @XmlElement(name="LocalFilePath")
    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    @XmlElement(name="localfilepath_info")
    public String getLocalFilePathInfo() {
        return localFilePathInfo;
    }

    public void setLocalFilePathInfo(String localFilePathInfo) {
        this.localFilePathInfo = localFilePathInfo;
    }

    @XmlElement(name="errcode")
    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    @XmlElement(name="errmsg")
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @XmlElement(name="fileTransCode")
    public String getFileTransCode() {
        return fileTransCode;
    }

    public void setFileTransCode(String fileTransCode) {
        this.fileTransCode = fileTransCode;
    }

    @XmlElement(name="fileAllPath")
    public String getFileAllPath() {
        return fileAllPath;
    }

    public void setFileAllPath(String fileAllPath) {
        this.fileAllPath = fileAllPath;
    }
}
