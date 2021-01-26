package com.tkcx.api.vo;

import com.tkcx.api.vo.callback.ServiceResponseVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 综合前端响应
 *
 * @author tianyi
 *
 */
@Getter
@Setter
@ToString
public class ZhqdQueryRspVo extends ServiceResponseVo {

    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件传输代码
     */
    private String fileTrnsmCd;
    /**
     * 查询结果计数
     */
    private Integer queryCount;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("OvrlRcrd", queryCount);
        map.put("FileTrnsmCd", fileTrnsmCd);
        map.put("FilPath", filePath);
        map.put("FileNm", fileName);
        return map;
    }
}
