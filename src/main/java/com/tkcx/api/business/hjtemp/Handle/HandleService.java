package com.tkcx.api.business.hjtemp.Handle;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.acctPrint.model.AcctLogModel;
import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.hjtemp.model.AcctOrgTempModel;
import com.tkcx.api.business.hjtemp.service.AcctBrchTempService;
import com.tkcx.api.business.hjtemp.service.AcctBusiCodeService;
import com.tkcx.api.business.hjtemp.service.AcctDetailTempService;
import com.tkcx.api.business.hjtemp.service.AcctOrgTempService;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.vo.HjFileDataReqVo;
import com.tkcx.api.vo.base.SysHeadVo;
import com.tkcx.api.vo.ftpFile.FileDownloadReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 互金数据文件解析保存类
 */
@Slf4j
@Service
public class HandleService {

    @Autowired
    private AcctBrchTempService acctBrchTempService;
    @Autowired
    private AcctBusiCodeService acctBusiCodeService;
    @Autowired
    private AcctDetailTempService acctDetailTempService;
    @Autowired
    private AcctOrgTempService acctOrgTempService;

    private HjFileDataReqVo hjFileDataReq = new HjFileDataReqVo();

    @Value("${acct.isRemove}")
    private Boolean isRemove;

    @Async
    public void startHandle(String brchPath, String busiPath, String detailPath, String orgPath){

        //异步进行保存工作
        try {
            if(StringUtils.isNotEmpty(brchPath)) {
                List<AcctBrchTempModel> brchList = makeListData(brchPath, AcctBrchTempModel.class);
                if(isRemove){
                    acctBrchTempService.remove(null);
                    log.info("AcctBrchTempModel数据清空成功");
                }
                acctBrchTempService.saveBatch(brchList);
                log.info("AcctBrchTempModel保存成功");
            }
            if(StringUtils.isNotEmpty(busiPath)) {
                List<AcctBusiCodeModel> busiList = makeListData(busiPath, AcctBusiCodeModel.class);
                acctBusiCodeService.remove(null);
                log.info("AcctBusiCodeModel数据清空成功");
                acctBusiCodeService.saveBatch(busiList);
                log.info("AcctBusiCodeModel保存成功");
            }
            if(StringUtils.isNotEmpty(detailPath)) {
                List<AcctDetailTempModel> detailList = makeListData(detailPath, AcctDetailTempModel.class);
                if(isRemove) {
                    acctDetailTempService.remove(null);
                    log.info("AcctDetailTempModel数据清空成功");
                }
                acctDetailTempService.saveBatch(detailList);
                log.info("AcctDetailTempModel保存成功");
            }
            if(StringUtils.isNotEmpty(orgPath)) {
                List<AcctOrgTempModel> orgList = makeListData(orgPath, AcctOrgTempModel.class);
                acctOrgTempService.remove(null);
                log.info("AcctOrgTempModel数据清空成功");
                acctOrgTempService.saveBatch(orgList);
                log.info("AcctOrgTempModel保存成功");
            }
            //互金推送数据日志表记录
            SysHeadVo sysVo = hjFileDataReq.getSysHeadVo();
            if(null!=sysVo){
                AcctLogModel acctLog = new AcctLogModel();
                acctLog.setAcctDate(DateUtil.parse(sysVo.getAcgDt()));
                acctLog.setContent(hjFileDataReq.getRemark());
                acctLog.setLogType(0);
                acctLog.setSerialNo(sysVo.getCnsmrSeqNo());
                acctLog.insert();
            }
        }catch (Exception e){
            new RuntimeException(e);
            log.error("数据异常{}" ,e);
        }
    }
    /**
     * 通用处理
     * @param path
     * @return
     */
    public  <T> List<T> makeListData(String path, Class<T> clazz){

        List<T> modelList = new ArrayList<>();
        try {
            List<String> list = FileUtil.readLine(path);
            /*以下调用带参的、私有构造函数*/
            Constructor constructor = clazz.getDeclaredConstructor(new Class[]{String.class});
            constructor.setAccessible(true);

            //遍历行数据
            T model = null;
            for (String lineStr : list) {
                if(StringUtils.isEmpty(lineStr)){continue;}
                //行数据生成对象
                model = (T) constructor.newInstance(new Object[]{lineStr});
                modelList.add(model);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(clazz.getName()+"数据保存异常");
        }
        return modelList;
    }


    /**
     * 单独加鸡腿
     */
    public String startHandle(FileDownloadReqVo fileVo){
        String basePath = fileVo.getFilePath();
        String key = fileVo.getDownloadTranCode();
        Integer isRemove = fileVo.getIsRemove();
        //异步进行保存工作
        try {
            if ("brch".equals(key) && StringUtils.isNotEmpty(basePath)) {
                List<AcctBrchTempModel> brchList = makeListData(basePath, AcctBrchTempModel.class);
                if(isRemove == 1) {
                    acctBrchTempService.remove(null);
                    log.info("AcctBrchTempModel数据清空成功");
                }
                acctBrchTempService.saveBatch(brchList);
                log.info("AcctBrchTempModel保存成功");
            } else if ("busi".equals(key) && StringUtils.isNotEmpty(basePath)) {
                List<AcctBusiCodeModel> busiList = makeListData(basePath, AcctBusiCodeModel.class);
                if(isRemove == 1) {
                    acctBusiCodeService.remove(null);
                    log.info("AcctBusiCodeModel数据清空成功");
                }
                acctBusiCodeService.saveBatch(busiList);
                log.info("AcctBusiCodeModel保存成功");
            } else if ("detail".equals(key) && StringUtils.isNotEmpty(basePath)) {
                List<AcctDetailTempModel> detailList = makeListData(basePath, AcctDetailTempModel.class);
                if(isRemove == 1) {
                    acctDetailTempService.remove(null);
                    log.info("AcctDetailTempModel数据清空成功");
                }
                acctDetailTempService.saveBatch(detailList);
                log.info("AcctDetailTempModel保存成功");
            } else if ("org".equals(key) && StringUtils.isNotEmpty(basePath)) {
                List<AcctOrgTempModel> orgList = makeListData(basePath, AcctOrgTempModel.class);
                if(isRemove == 1) {
                    acctOrgTempService.remove(null);
                    log.info("AcctOrgTempModel数据清空成功");
                }
                acctOrgTempService.saveBatch(orgList);
                log.info("AcctOrgTempModel保存成功");
            }
        }catch (Exception e){
            new RuntimeException(e);
            log.error("数据异常{}" ,e);
            return "9999";
        }
        return "0000";
    }

    public HjFileDataReqVo getHjFileDataReq() {
        return hjFileDataReq;
    }

    public void setHjFileDataReq(HjFileDataReqVo hjFileDataReq) {
        this.hjFileDataReq = hjFileDataReq;
    }
}
