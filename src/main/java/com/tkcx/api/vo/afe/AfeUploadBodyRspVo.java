package com.tkcx.api.vo.afe;

import com.tkcx.api.vo.callback.ServiceRequestVo;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement(name = "Body")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AfeUploadBodyRspVo extends ServiceRequestVo {

    //文件传输代码默认100033
    private String fileTransCode;
    //文件传输平台全路径(含文件名称)
    private String fileAllPath;


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


    @Override
    public void withMap(Map<String, Object> map) {
        this.fileAllPath = StringUtils.trim((String) map.get("FileAllPath"));
        this.fileTransCode = StringUtils.trim((String) map.get("FileTransCode"));
    }
}
