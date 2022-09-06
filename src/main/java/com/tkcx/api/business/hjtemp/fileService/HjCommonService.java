package com.tkcx.api.business.hjtemp.fileService;

import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @project：
 * @author： zhaodan
 * @description： 互金文件处理公共逻辑
 * @create： 2021/11/3 17:23
 */
@Slf4j
@Service
public class HjCommonService {

    @Autowired
    private HjFileInfoService hjFileInfoService;

    /**
     * 更新已读取的文件信息
     *
     * @param queryResult
     */
    public boolean updateReadFileInfo(HjFileInfoModel queryResult) {

        int readEndNum = queryResult.getNextReadEndNum();
        int txtTotalNum = queryResult.getFileLineTotalNum();
        /** 更新已读取的文件信息 */
        HjFileInfoModel updateInfo = new HjFileInfoModel();
        updateInfo.setFileId(queryResult.getFileId());
        updateInfo.setNextReadStartNum(readEndNum);
        updateInfo.setNextReadEndNum(calReadEndLine(readEndNum, txtTotalNum));
        updateInfo.setReadFlag(judgeReadFlag(readEndNum, txtTotalNum));
        return hjFileInfoService.updateById(updateInfo);
    }

    /**
     * 获取fileDate日未删除未读取的互金会计科目文件信息
     *
     * @param fileDate
     * @return
     */
    public HjFileInfoModel queryHjFileInfo(Date fileDate, String fileType) {

        HjFileInfoModel queryCon = new HjFileInfoModel();
        queryCon.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
        queryCon.setFileDate(fileDate);
        queryCon.setFileType(fileType);
        return hjFileInfoService.selectOneModel(queryCon);
    }

    /**
     * 根据文件名称获取前一天未删除未读取的新网贷文件信息
     *
     * @param fileName
     * @return
     */
    public HjFileInfoModel queryXWDFileInfo(String fileName, String fileType) {

        HjFileInfoModel queryCon = new HjFileInfoModel();
        queryCon.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
        queryCon.setFileName(fileName);
        queryCon.setFileType(fileType);
        return hjFileInfoService.selectOneModel(queryCon);
    }


    /**
     * 计算读取文件结束行数
     *
     * @param readEndNum
     * @param txtTotalNum
     * @return
     */
    private int calReadEndLine(int readEndNum, int txtTotalNum) {

        if(readEndNum < txtTotalNum &&
                (txtTotalNum-readEndNum) > HjFileFlagConstant.ONE_TIME_READ_LINE_NUM) {
            log.info("文件总行数查询 - 已读取行数【大于】100000行，文件读取继续");
            // 结束行数与文件总行数之差大于500，则结束行数更新为当前结束行数+500(步长)
            return readEndNum + HjFileFlagConstant.ONE_TIME_READ_LINE_NUM;
        }else if(readEndNum < txtTotalNum &&
                (txtTotalNum-readEndNum) <= HjFileFlagConstant.ONE_TIME_READ_LINE_NUM){
            log.info("文件总行数查询 - 已读取行数【小于等于】500行，文件读取继续");
            /**
             * 结束行数与文件总行数之差小于500，结束行数更新为当前结束行数+剩下未读取的行数
             * 结束行数是文件总行数的倍数时，结束行数更新为当前结束行数+剩下未读取的行数+1，这里这样设置，是因为读取文件的条件是当前行数小于文件总行数
             */
            return readEndNum + (txtTotalNum-readEndNum) + HjFileFlagConstant.READ_START_NUM_INITIAL;
        }else if(readEndNum >= txtTotalNum) {
            log.info("文件总行数查询 = 已读取行数，文件读取结束");
            return txtTotalNum;
        }else {
            log.error("文件读取结束行数判断异常！！！！");
            return 0;
        }
    }

    /**
     * 判断文件是否读取完成
     * @param readEndNum
     * @param txtTotalNum
     * @return
     */
    private String judgeReadFlag(int readEndNum, int txtTotalNum) {

        if(readEndNum == txtTotalNum){
            return HjFileFlagConstant.FINISHED;
        }
        return HjFileFlagConstant.NOT_FINISH;
    }




}
