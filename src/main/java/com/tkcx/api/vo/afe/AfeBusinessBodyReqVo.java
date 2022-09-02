package com.tkcx.api.vo.afe;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="Body")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "", propOrder = {
        "filePath",
        "fileTranCode",
        "date",
        "localFilePath",
        "fileName",
        "fileServerType",
        "disNode"
})
public class AfeBusinessBodyReqVo {

    private String  filePath;
    private String  fileTranCode;
    private String  date;

    private String  localFilePath;

    private String  fileName;

    private String  fileServerType;

    private String  disNode;
    @XmlElement(name="FileServerType")
    public String getFileServerType() {
        return fileServerType;
    }

    public void setFileServerType(String fileServerType) {
        this.fileServerType = fileServerType;
    }
    @XmlElement(name="DisNode")
    public String getDisNode() {
        return disNode;
    }

    public void setDisNode(String disNode) {
        this.disNode = disNode;
    }

    @XmlElement(name="LocalFilePath")
    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    @XmlElement(name="FileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }






    @XmlElement(name="FilePath")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    @XmlElement(name="FileTranCode")
    public String getFileTranCode() {
        return fileTranCode;
    }

    public void setFileTranCode(String fileTranCode) {
        this.fileTranCode = fileTranCode;
    }

    @XmlElement(name="Date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
