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
public class AfeDownFileBodyRspVo extends ServiceRequestVo {

    //本地文件路径    包含文件名称的全路径
    private String localFilePath;
    //错误代码  0-成功，其余失败
    private String errcode;


    @XmlElement(name="localFilePath")
    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    @XmlElement(name="errcode")
    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    @Override
    public void withMap(Map<String, Object> map) {
        this.errcode = StringUtils.trim((String) map.get("errcode"));
        this.localFilePath = StringUtils.trim((String) map.get("LocalFilePath"));
    }
}
