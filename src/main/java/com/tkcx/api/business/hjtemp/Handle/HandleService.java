package com.tkcx.api.business.hjtemp.Handle;

import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.hjtemp.model.AcctOrgTempModel;
import com.tkcx.api.business.hjtemp.service.AcctBrchTempService;
import com.tkcx.api.business.hjtemp.service.AcctBusiCodeService;
import com.tkcx.api.business.hjtemp.service.AcctDetailTempService;
import com.tkcx.api.business.hjtemp.service.AcctOrgTempService;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${acct.isRemove}")
    private Boolean isRemove;


//    @Async
    public void startHandle(String filePath,String fileType){

        //异步进行保存工作
        try {
            log.info("开始解析互金：{}文件", fileType);
            switch (fileType){
                case "t_act_one_detail":
                    List<AcctDetailTempModel> detailList = makeListData(filePath, AcctDetailTempModel.class);
                    log.info("t_act_one_detail待更新的数据总数：{},插入的第一条数据信息", detailList.size(),
                            detailList.get(0).getAccountCode(), detailList.get(0).getCreateDate());
                    if(isRemove) {
                        acctDetailTempService.remove(null);
                        log.info("AcctDetailTempModel数据清空成功");
                    }
                    acctDetailTempService.saveBatch(detailList);
                    log.info("AcctDetailTempModel保存成功");
                    break;
                case "t_act_brch_day_tot":
                    List<AcctBrchTempModel> brchList = makeListData(filePath, AcctBrchTempModel.class);
                    log.info("t_act_brch_day_tot待更新的数据总数：{},插入的第一条数据信息", brchList.size(),
                            brchList.get(0).getAcctOrg(), brchList.get(0).getCreateDate());
                    if(isRemove){
                        acctBrchTempService.remove(null);
                        log.info("AcctBrchTempModel数据清空成功");
                    }
                    acctBrchTempService.saveBatch(brchList);
                    log.info("AcctBrchTempModel保存成功");
                    break;
                case "t_act_busi_code_map":
                    List<AcctBusiCodeModel> busiList = makeListData(filePath, AcctBusiCodeModel.class);
                    log.info("t_act_busi_code_map待更新的数据总数：{},插入的第一条数据信息", busiList.size(),
                            busiList.get(0).getBusiCode(), busiList.get(0).getCreateDate());
                    acctBusiCodeService.remove(null);
                    log.info("AcctBusiCodeModel数据清空成功");
                    acctBusiCodeService.saveBatch(busiList);
                    log.info("AcctBusiCodeModel保存成功");
                    break;
                case "t_act_pub_org":
                    List<AcctOrgTempModel> orgList = makeListData(filePath, AcctOrgTempModel.class);
                    log.info("t_act_pub_org待更新的数据总数：{},插入的第一条数据信息", orgList.size(),
                            orgList.get(0).getOrgCode(), orgList.get(0).getCreateDate());
                    acctOrgTempService.remove(null);
                    log.info("AcctOrgTempModel数据清空成功");
                    acctOrgTempService.saveBatch(orgList);
                    log.info("AcctOrgTempModel保存成功");
                    break;
            }
            log.info("互金：{}文件解析成功", fileType);
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


}
