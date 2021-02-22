package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.tkcx.api.business.acctPrint.service.*;
import com.tkcx.api.business.hjtemp.service.AcctBrchTempService;
import com.tkcx.api.business.hjtemp.service.AcctBusiCodeService;
import com.tkcx.api.business.hjtemp.service.AcctDetailTempService;
import com.tkcx.api.business.wdData.service.*;
import com.tkcx.api.common.BeanContext;
import com.tkcx.api.common.BusiCommonService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 会计凭证基类
 *
 * @author
 * @since 2019-08-06
 */
@Setter
@Getter
@Service
public class AcctBaseThread extends Thread {

    /**
     * 当前执行日期
     */
    private Date curDate;
    /**
     * 开始日期
     */
    private Date startDate;


    public AcctBaseThread(Date curDate) {
        this.curDate = curDate;
    }

    public AcctDataService acctDataService = BeanContext.getBean(AcctDataService.class);

    public BusiOrgSeqService busiOrgSeqService = BeanContext.getBean(BusiOrgSeqService.class);

    public BusiCommonService busiCommonService = BeanContext.getBean(BusiCommonService.class);

    public RepayPeriodRecordService repayPeriodRecordService = BeanContext.getBean(RepayPeriodRecordService.class);

    public RefundReceiptService refundReceiptService = BeanContext.getBean(RefundReceiptService.class);

    public AssetService assetService = BeanContext.getBean(AssetService.class);

    public LoanDetailBillService loanDetailBillService = BeanContext.getBean(LoanDetailBillService.class);

    public InterestBillService interestBillService = BeanContext.getBean(InterestBillService.class);

    public AssetGrantRecordService assetGrantRecordService = BeanContext.getBean(AssetGrantRecordService.class);

    public CardiiService cardiiService = BeanContext.getBean(CardiiService.class);

    public RepayAssemblyRecordService repayAssemblyRecordService = BeanContext.getBean(RepayAssemblyRecordService.class);

    public LoanAccBillService loanAccBillService = BeanContext.getBean(LoanAccBillService.class);

    public AccountVoucherService accountVoucherService = BeanContext.getBean(AccountVoucherService.class);

    public AcctBrchTempService acctBrchTempService = BeanContext.getBean(AcctBrchTempService.class);

    public BusiOrgBillService busiOrgBillService = BeanContext.getBean(BusiOrgBillService.class);

    public AcctDetailTempService acctDetailTempService = BeanContext.getBean(AcctDetailTempService.class);

    public LoanReceiptService loanReceiptService = BeanContext.getBean(LoanReceiptService.class);

    public AssetIndividualService assetIndividualService = BeanContext.getBean(AssetIndividualService.class);

    public LoanAdjustService loanAdjustService = BeanContext.getBean(LoanAdjustService.class);

    public AcctBusiCodeService acctBusiCodeService = BeanContext.getBean(AcctBusiCodeService.class);

    public InterestAccrualService interestAccrualService = BeanContext.getBean(InterestAccrualService.class);

    public RepayPlanService repayPlanService = BeanContext.getBean(RepayPlanService.class);

    /**
     * 公共筛选构造器
     * @param clazz
     * @param field
     * @param <T>
     * @return
     */
    public <T> QueryWrapper getQueryWrapper(Class<T> clazz, String field){
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        TableInfo tableInfo = SqlHelper.table(clazz);
        List<TableFieldInfo> fieidList =  tableInfo.getFieldList();
        String columnName = "";
        for (TableFieldInfo fieid: fieidList) {
            if(field.equals(fieid.getProperty())){
                columnName = fieid.getColumn();
            }
        }
        if(startDate==null){//开始日期为空，取前一天数据
            startDate = DateUtil.parse(DateUtil.formatDate(DateUtil.offsetDay(curDate, -1)));
        }
        queryWrapper.ge(columnName, startDate).lt(columnName, curDate);
        // queryWrapper.between(columnName,startDate, curDate);
        return queryWrapper;
    }
}
