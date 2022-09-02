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
public class ZhqdQueryAFEReqVo extends ServiceResponseVo {

    /**
     * FTP服务器文件路径 ./YYYYMMDD/send/
     */
    private String localFilePath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 查询结果计数
     */
    private Integer queryCount;

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("KEPRCD_OVRAL_QTY", queryCount);
        map.put("FILE_PTH_ADDR", localFilePath);
        map.put("FILE_APLTN", fileName);
        return map;
    }
}
