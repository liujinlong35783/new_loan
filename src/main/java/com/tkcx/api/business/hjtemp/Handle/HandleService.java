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

    @Value("${acct.isRemove}")
    private Boolean isRemove;


//    @Async
    public void startHandle(String filePath,String fileType){

        //异步进行保存工作
        try {

            switch (fileType){
                case "t_act_one_detail":
                    List<AcctDetailTempModel> detailList = makeListData(filePath, AcctDetailTempModel.class);
                    if(isRemove) {
                        acctDetailTempService.remove(null);
                        log.info("AcctDetailTempModel数据清空成功");
                    }
                    acctDetailTempService.saveBatch(detailList);
                    log.info("AcctDetailTempModel保存成功");
                    break;
                case "t_act_brch_day_tot":
                    List<AcctBrchTempModel> brchList = makeListData(filePath, AcctBrchTempModel.class);
                    if(isRemove){
                        acctBrchTempService.remove(null);
                        log.info("AcctBrchTempModel数据清空成功");
                    }
                    acctBrchTempService.saveBatch(brchList);
                    log.info("AcctBrchTempModel保存成功");
                    break;
                case "t_act_busi_code_map":
                    List<AcctBusiCodeModel> busiList = makeListData(filePath, AcctBusiCodeModel.class);
                    acctBusiCodeService.remove(null);
                    log.info("AcctBusiCodeModel数据清空成功");
                    acctBusiCodeService.saveBatch(busiList);
                    log.info("AcctBusiCodeModel保存成功");
                    break;
                case "t_act_pub_org":
                    List<AcctOrgTempModel> orgList = makeListData(filePath, AcctOrgTempModel.class);
                    acctOrgTempService.remove(null);
                    log.info("AcctOrgTempModel数据清空成功");
                    acctOrgTempService.saveBatch(orgList);
                    log.info("AcctOrgTempModel保存成功");
                    break;
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


}
