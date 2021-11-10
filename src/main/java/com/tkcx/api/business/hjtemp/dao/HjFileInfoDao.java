package com.tkcx.api.business.hjtemp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/2/7 16:08
 */
@Repository
public interface HjFileInfoDao extends BaseMapper<HjFileInfoModel> {

    /**
     * 查询互金文件列表
     * @param queryInfo
     * @return
     */
    List<HjFileInfoModel> selectModelList(@Param("e") HjFileInfoModel queryInfo);

    /**
     * 查询互金文件信息
     *
     * @param queryInfo
     * @return
     */
    HjFileInfoModel selectOneModel(@Param("e") HjFileInfoModel queryInfo);

    /**
     * 统计数量
     * @param model
     * @return
     */
    int selectModelCount(@Param("e") HjFileInfoModel model);

    /**
     * 互金文件下载完成后更新文件下载路径及文件总行数信息
     * @param model
     * @return
     */
    @Update("UPDATE QN_DB_ACCT.HJ_FILE_INFO SET FILE_LINE_TOTAL_NUM = #{e.fileLineTotalNum}, FILE_DOWNLOAD_PATH = #{e.fileDownloadPath} WHERE FILE_TYPE=#{e.fileType} AND FILE_DATE=#{e.fileDate} AND DELETE_FLAG=#{e.deleteFlag} AND READ_FLAG=#{e.readFlag}")
    int updateDownloadFile(@Param("e")HjFileInfoModel model);
}
