package com.tkcx.api.business.hjtemp.Handle;

import com.tkcx.api.business.hjtemp.hjThread.AcctBrchReadThread;
import com.tkcx.api.business.hjtemp.hjThread.AcctBusiReadThread;
import com.tkcx.api.business.hjtemp.hjThread.AcctDetailReadThread;
import com.tkcx.api.business.hjtemp.hjThread.AcctOrgReadThread;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 互金数据文件解析保存类
 */
@Slf4j
@Service
public class HandleService {

    @Value("${acct.isRemove}")
    private Boolean isRemove;

//    @Async
    public void startHandle(String filePath,String fileType){

        //异步进行保存工作
        try {
            log.info("开始解析互金：{}文件", fileType);

            // 自定义线程池用来进行互金文件的解析
            ThreadPoolExecutor readThreadPool = new ThreadPoolExecutor(
                    4,5,60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(3),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
            switch (fileType){
                case "t_act_one_detail":
                    // 2021年10月24解决CPU使用率高问题
                    readThreadPool.execute(new AcctDetailReadThread(filePath, isRemove));
                    break;
                case "t_act_brch_day_tot":
                    readThreadPool.execute(new AcctBrchReadThread(filePath, isRemove));
                    break;
                case "t_act_busi_code_map":
                    readThreadPool.execute(new AcctBusiReadThread(filePath, isRemove));
                    break;
                case "t_act_pub_org":
                    readThreadPool.execute(new AcctOrgReadThread(filePath, isRemove));
                    break;
                default:
                    log.error("文件类型：【{}】错误", fileType);
            }
            // 关闭线程池
            readThreadPool.shutdown();
            boolean flag;
            do {
                flag = ! readThreadPool.awaitTermination(500, TimeUnit.MILLISECONDS);
            } while (flag);
        }catch (Exception e){
            log.error("解析互金文件异常{}" ,e);
        }finally {
            log.info("互金：{}文件解析成功", fileType);
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
