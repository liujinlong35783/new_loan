package com.tkcx.api.vo;

import com.tkcx.api.vo.callback.ServiceResponseVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.HashMap;
import java.util.Map;

/**
 * 互金通知响应
 *
 * @author tianyi
 *
 */
@Getter
@Setter
@ToString
public class HjFileDataRspVo extends ServiceResponseVo {

    @Override
    public Map<String, Object> toMap() {
        return new HashMap<>();
    }
}

