package com.tkcx.api.vo;

import com.tkcx.api.vo.callback.ServiceRequestVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class HjFileDataReqVo extends ServiceRequestVo {

    private String msgDate;//时间戳唯一标识

    private String remark;//备注
    /**
     * 文件存放信息
     */
    private List<FileInfo> fileInfo;


    @Override
    public void withMap(Map<String, Object> map) {
        this.msgDate = StringUtils.trim((String) map.get("TRD_DT1"));
        this.remark = StringUtils.trim((String) map.get("REMRK"));
        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) map.get("FILE_INFO_ARR");
        this.fileInfo = new ArrayList<>();
        for (HashMap<String, Object> filemap: list ) {
            FileInfo info = new FileInfo();
            info.withMap(filemap);
            fileInfo.add(info);
        }
    }

    @Getter
    @Setter
    @ToString
    public static class FileInfo extends ServiceRequestVo {

        /**
         * 文件标识
         */
        private String fileFlag;

        /**
         * 文件路径
         */
        private String filPath;

        /**
         * 文件名称
         */
        private String fileNm;

        /**
         * 文件传输代码
         */
        private String fileTrnsmCd;

        @Override
        public void withMap(Map<String, Object> map) {
            this.fileFlag = StringUtils.trim((String) map.get("FILE_GNRT_FLG"));
            this.filPath = StringUtils.trim((String) map.get("FILE_PTH_ADDR"));
            this.fileNm = StringUtils.trim((String) map.get("FILE_APLTN"));
            this.fileTrnsmCd = StringUtils.trim((String) map.get("FILE_TRNSM_CD"));
        }
    }

}