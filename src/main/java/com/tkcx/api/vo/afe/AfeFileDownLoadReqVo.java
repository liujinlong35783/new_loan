package com.tkcx.api.vo.afe;

import com.tkcx.api.vo.callback.ServiceRequestVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class AfeFileDownLoadReqVo extends ServiceRequestVo {

    private String  filePath;

    private String fileTranCode;

    private String date;
    //请求流水号
    private String cnsmrSeqNo;


    @Override
    public void withMap(Map<String, Object> map) {

    }
}
