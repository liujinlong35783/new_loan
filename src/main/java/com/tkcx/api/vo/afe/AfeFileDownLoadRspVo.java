package com.tkcx.api.vo.afe;

import com.tkcx.api.vo.base.afe.AFEApiResponseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class AfeFileDownLoadRspVo  extends AFEApiResponseVo {

    private String localFilePath;
    private String localFilePathInfo;
    private String errCode;
    private String errMsg;
    private String seqNo;

    @Override
    public void withMap(Map<String, Object> map) {

    }
}
