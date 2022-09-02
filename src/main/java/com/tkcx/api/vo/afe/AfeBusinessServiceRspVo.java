package com.tkcx.api.vo.afe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Service")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AfeBusinessServiceRspVo {

    private AfeBusinessBodyRspVo  AfeBusinessBodyRspVo;

    @XmlElement(name="Body")
    public AfeBusinessBodyRspVo getAfeBusinessBodyReqVo() {
        return AfeBusinessBodyRspVo;
    }

    public void setAfeBusinessBodyReqVo(AfeBusinessBodyRspVo afeBusinessBodyReqVo) {
        this.AfeBusinessBodyRspVo = afeBusinessBodyReqVo;
    }
}
