package com.tkcx.api.vo.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 服务对象
 * 
 * @author linghujie
 *
 */
@Getter
@Setter
@ToString
@XStreamAlias("Service")
public class ServiceVo {
    @XStreamAlias("SysHead")
    private SysHeadVo sysHead;
    @XStreamAlias("AppHead")
    private AppHeadVo appHead;
    @XStreamAlias("Body")
    private BodyVo body;

//    /**
//     * 获取请求签名段
//     */
//    @JSONField(serialize = false)
//    public String getRequestMacBlock() {
//        StringBuilder sb = new StringBuilder();
////        sb.append(serviceHeader.getServiceSn());
////        sb.append(serviceHeader.getServiceTime());
////        sb.append(serviceBody.getRequest().getMac());
//        String block = sb.toString();
//        log.info(block);
//        return block;
//    }
//
//    /**
//     * 获取返回签名段
//     */
//    @JSONField(serialize = false)
//    public String getResponseMacBlock() {
//        StringBuilder sb = new StringBuilder();
////        sb.append(serviceHeader.getServiceSn());
////        sb.append(serviceHeader.getServiceTime());
////        sb.append(serviceHeader.getServiceResponse().getStatus());
////        sb.append(valueString(serviceBody.getResponse()));
//        String block = sb.toString();
//        log.info(block);
//        return block;
//    }
//
//    /**
//     * 拼装Map的Value内容
//     */
//    private String valueString(Map<?, ?> map) {
//        if (map == null) {
//            return "";
//        }
//        StringBuilder sb = new StringBuilder();
//        for (Object val : map.values()) {
//            if (null != val) {
//                if (val instanceof AbstractMap) {
//                    sb.append(valueString((Map<?, ?>) val));
//                } else {
//                    String str = val.toString();
//                    for (int i = 0; i < str.length(); ++i) {
//                        if (CharUtils.isAsciiPrintable(str.charAt(i))) {
//                            sb.append(str.charAt(i));
//                        }
//                    }
//                }
//            }
//        }
//        return sb.toString();
//    }
}
