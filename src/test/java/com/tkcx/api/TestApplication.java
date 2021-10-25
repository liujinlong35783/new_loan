package com.tkcx.api;

import com.tkcx.api.business.hjtemp.Handle.HandleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/10/23 10:39
 */
@Slf4j
//@EnableAsync
//@PropertySource({"classpath:bank.properties"})
//@MapperScan("com.tkcx.api.business.*.dao")
//@SpringBootApplication
public class TestApplication {



    public static void main(String[] args) {

        SpringApplication.run(AcctPrintApplication.class, args);
        log.info(AcctPrintApplication.class.getSimpleName() + " is success!");

        HandleService handleService = new HandleService();
        log.info("************************************************t_act_one_detail共51840条数据****************************************");
        String detailPath = "C:\\Users\\ccjh\\Desktop\\test\\t_act_one_detail_20500101.txt";
        String detailType = "t_act_one_detail";
        //handleService.makeListData(detailPath, AcctDetailTempModel.class);
        handleService.startHandle(detailPath, detailType);
        log.info("***********************************t_act_brch_day_tot共248832条测试数据*****************************************************");
        String branchPath = "C:\\Users\\ccjh\\Desktop\\test\\t_act_brch_day_tot_20500101.txt";
        String branchType = "t_act_brch_day_tot";
        handleService.startHandle(branchPath, branchType);
        log.info("*******************************************t_act_busi_code_map共133120条数据*********************************************");
        String acctCodePath = "C:\\Users\\ccjh\\Desktop\\test\\t_act_busi_code_map_20500101.txt";
        String acctCodeType = "t_act_busi_code_map";
        handleService.startHandle(acctCodePath, acctCodeType);
        log.info("**********************************************t_act_pub_org共30900条数据******************************************");
        String pubOrgPath = "C:\\Users\\ccjh\\Desktop\\test\\t_act_pub_org_20500101.txt";
        String pubOrgType = "t_act_pub_org";
        handleService.startHandle(pubOrgPath, pubOrgType);


//        List<AcctDetailTempModel> detailList = AcctDetailTempConvert.makeAcctDetailList("C:\\Users\\ccjh\\Desktop\\test\\t_act_one_detail_20500101.txt");
//        log.info("t_act_one_detail待更新的数据总数：{},插入的第一条数据信息", detailList.size());
//        List<AcctBrchTempModel> brchList = AcctBrchTempConvert.makeAcctDetailList("C:\\Users\\ccjh\\Desktop\\test\\t_act_brch_day_tot_20500101.txt");
//        log.info("t_act_brch_day_tot待更新的数据总数：{},插入的第一条数据信息", brchList.size());
//        List<AcctBusiCodeModel> busiList = AcctBusiCodeConvert.makeAcctDetailList("C:\\Users\\ccjh\\Desktop\\test\\t_act_brch_day_tot_20500101.txt");
//        log.info("t_act_busi_code_map待更新的数据总数：{},插入的第一条数据信息", busiList.size());
//        List<AcctOrgTempModel> orgList = AcctOrgTempConvert.makeAcctDetailList("C:\\Users\\ccjh\\Desktop\\test\\t_act_pub_org_20500101.txt");
//        log.info("t_act_pub_org待更新的数据总数：{},插入的第一条数据信息", orgList.size());
    }


}
