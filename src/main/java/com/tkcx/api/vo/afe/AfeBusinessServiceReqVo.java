package com.tkcx.api.vo.afe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Service")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AfeBusinessServiceReqVo {

    private AfeBusinessBodyReqVo  afeBusinessBodyReqVo;

    @XmlElement(name="Body")
    public AfeBusinessBodyReqVo getAfeBusinessBodyReqVo() {
        return afeBusinessBodyReqVo;
    }

    public void setAfeBusinessBodyReqVo(AfeBusinessBodyReqVo afeBusinessBodyReqVo) {
        this.afeBusinessBodyReqVo = afeBusinessBodyReqVo;
    }
}
