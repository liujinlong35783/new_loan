package com.tkcx.api.business.acctPrint.html2pdf;

import cn.hutool.core.date.DateUtil;
import com.itextpdf.kernel.geom.PageSize;
import com.tkcx.api.business.acctPrint.model.AccountVoucherModel;
import com.tkcx.api.business.acctPrint.model.BusiOrgBillModel;
import com.tkcx.api.business.acctPrint.model.InterestBillModel;
import com.tkcx.api.business.acctPrint.model.Interface.IAcctPrintCommon;
import com.tkcx.api.utils.HtmlToPdf;
import com.tkcx.api.utils.ToolUtil;
import com.tkcx.api.vo.ZhqdQueryReqVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 根据业务类型适应不同的转换器
 */
public class BusiHtmlToPdf {

    private static StringBuffer htmlTemplate;
    private static HtmlToPdf pdf;

    /**
     * 转pdf
     * @param listData
     * @param queryReq
     * @param pdfPath
     * @return
     */
    public static boolean toPdf(List<IAcctPrintCommon> listData, ZhqdQueryReqVo queryReq, String pdfPath) {
        Integer interfaceIden = queryReq.getInterfaceIden();

        htmlTemplate = new StringBuffer(HtmlToPdf.getHtmlTemplateByBusiType(interfaceIden));
        pdf = HtmlToPdf.getHtmlToPdf(pdfPath, PageSize.A4.rotate());
        setBasicInfo(queryReq, htmlTemplate);
        if (interfaceIden == 1 || interfaceIden == 2) {
            return singleDataToPdf(listData);
        } else if(interfaceIden == 3){
            return busiOrgBillDataToPdf(listData);
        } else if(interfaceIden == 8){
            if (interfaceIden == 8) {
                setValue("busiDate", DateUtil.format(((AccountVoucherModel)listData.get(0)).getBusiDate(), "yyyy年MM月dd日 HH时mm分ss秒"), htmlTemplate);

                setValue("voucherNo",((AccountVoucherModel)listData.get(0)).getVoucherNo(), htmlTemplate);
                setValue("busiType",((AccountVoucherModel)listData.get(0)).getBusiType(), htmlTemplate);
                setValue("transferFlag",((AccountVoucherModel)listData.get(0)).getTransferFlag()+"", htmlTemplate);
                setValue("serialNo",((AccountVoucherModel)listData.get(0)).getSerialNo(), htmlTemplate);
                setValue("operator",((AccountVoucherModel)listData.get(0)).getOperator(), htmlTemplate);
            }
            return accountVoucherDataToPdf(listData);
        } else {
            if (interfaceIden == 7) {
                setValue("contractNo", ((InterestBillModel)listData.get(0)).getContractNo(), htmlTemplate);
                setValue("debtNo",((InterestBillModel)listData.get(0)).getDebtNo(), htmlTemplate);
            }
            return listDataToPdf(listData);
        }
    }

    /**
     * 单独关于客户信息的
     * @param listData
     * @return
     */
    private static boolean singleDataToPdf(List<IAcctPrintCommon> listData) {
        StringBuffer htmlBuffer = new StringBuffer(htmlTemplate.toString());
        IAcctPrintCommon model;
        for (int i = 0; listData != null && i < listData.size(); i++) {
            model = listData.get(i);
            setValue("htmlContent", model.toHtmlString(), htmlBuffer);
            model.customizedHtmlTitle(htmlBuffer);
            pdf.addNewPage(htmlBuffer);
            htmlBuffer = new StringBuffer(htmlTemplate.toString());
        }
        pdf.close();
        return true;
    }
    /**
     * 贷款凭证的
     * @param listData
     * @return
     */
    private static boolean busiOrgBillDataToPdf(List<IAcctPrintCommon> listData){
        StringBuffer htmlBuffer = new StringBuffer(htmlTemplate.toString()), htmlContent = new StringBuffer();
        BusiOrgBillModel model;
        Integer lineNum = getLineNumValue("lineNum", htmlBuffer);
        lineNum = lineNum == null ? 20 : lineNum;
        int pageNumber = 0,
                pageCount = new BigDecimal(listData.size()).divide(new BigDecimal(lineNum)
                        , 0, BigDecimal.ROUND_UP).intValue();
        Integer debtNum = 0, creditNum = 0, debtNumOut = 0, creditNumOut = 0;
        String debtAmount = "0",creditAmount = "0", debtAmountOut = "0",creditAmountOut = "0";
        for (int i = 0; listData != null && i < listData.size(); i++) {
            model = (BusiOrgBillModel) listData.get(i);
            if ("0".equals(model.getFlag())) {//表内
                debtNum += model.getDebtNum();
                creditNum += model.getCreditNum();
                debtAmount = new BigDecimal(debtAmount).add(new BigDecimal(model.getDebtAmount())).toPlainString();
                creditAmount = new BigDecimal(creditAmount).add(new BigDecimal(model.getCreditAmount())).toPlainString();
            } else {//表外
                debtNumOut += model.getDebtNum();
                creditNumOut += model.getCreditNum();
                debtAmountOut = new BigDecimal(debtAmountOut).add(new BigDecimal(model.getDebtAmount())).toPlainString();
                creditAmountOut = new BigDecimal(creditAmountOut).add(new BigDecimal(model.getCreditAmount())).toPlainString();
            }
            htmlContent.append(model.toHtmlString());
            if ((i != 0 && i % lineNum == 0) || i == listData.size() - 1) {//分页。10条数据为一页
                pageNumber++;
                setValue("htmlContent", htmlContent.toString(), htmlBuffer);
                setValue("pageInfo", "共" + pageCount + "页/第" + pageNumber + "页", htmlBuffer);
                setValue("tableTotal", "<td>" + debtNum + "</td><td>" + ToolUtil.fenToYuan(debtAmount) + "</td><td>"
                        + creditNum + "</td><td>" + ToolUtil.fenToYuan(creditAmount) + "</td>", htmlBuffer);//表内合计
                setValue("tableTotalOut", "<td>" + debtNumOut + "</td><td>" + ToolUtil.fenToYuan(debtAmountOut) + "</td><td>"
                        + creditNumOut + "</td><td>" + ToolUtil.fenToYuan(creditAmountOut) + "</td>", htmlBuffer);//表外合计
                pdf.addNewPage(htmlBuffer);
                htmlBuffer = new StringBuffer(htmlTemplate.toString());
                htmlContent = new StringBuffer();
            }
        }
        pdf.close();
        return true;
    }
    /**
     * 贷款凭证的
     * @param listData
     * @return
     */
    private static boolean accountVoucherDataToPdf(List<IAcctPrintCommon> listData){
        StringBuffer htmlBuffer = new StringBuffer(htmlTemplate.toString());
        StringBuffer htmlContent = new StringBuffer();
        AccountVoucherModel model;
        String serialNo = null;
        for (int i = 0; listData != null && i < listData.size(); i++) {
            model = (AccountVoucherModel) listData.get(i);
            serialNo = model.getSerialNo();
            if(null == serialNo || serialNo.equals(model.getSerialNo())){
                htmlContent.append(model.toHtmlString());
            }
            if(null != serialNo && (!serialNo.equals(model.getSerialNo()) || i+1 == listData.size())){//流水号改变
                setValue("htmlContent", htmlContent.toString(), htmlBuffer);
                pdf.addNewPage(htmlBuffer);
                htmlBuffer = new StringBuffer(htmlTemplate.toString());
                htmlContent = new StringBuffer(model.toHtmlString());
            }
            if(null == serialNo || !serialNo.equals(model.getSerialNo())){
                model.customizedHtmlTitle(htmlBuffer);
            }

        }
        pdf.close();
        return true;
    }
    /**
     * 管理集合信息的
     *
     * @param listData
     * @return
     */
    private static boolean listDataToPdf(List<IAcctPrintCommon> listData) {
        StringBuffer htmlBuffer = new StringBuffer(htmlTemplate.toString()), htmlContent = new StringBuffer();
        IAcctPrintCommon model;
        Integer lineNum = getLineNumValue("lineNum", htmlBuffer);
        lineNum = lineNum == null ? 20 : lineNum;
        int pageNumber = 0,
                pageCount = new BigDecimal(listData.size()).divide(new BigDecimal(lineNum)
                        , 0, BigDecimal.ROUND_UP).intValue();

        for (int i = 0; listData != null && i < listData.size(); i++) {
            model = listData.get(i);

            htmlContent.append(model.toHtmlString());
            if ((i != 0 && i % lineNum == 0) || i == listData.size() - 1) {//分页。10条数据为一页
                pageNumber++;
                setValue("htmlContent", htmlContent.toString(), htmlBuffer);
                setValue("pageInfo", "共" + pageCount + "页/第" + pageNumber + "页", htmlBuffer);
                pdf.addNewPage(htmlBuffer);
                htmlBuffer = new StringBuffer(htmlTemplate.toString());
                htmlContent = new StringBuffer();
            }
            if(0 == i || i % lineNum == 0){
                model.customizedHtmlTitle(htmlBuffer);
            }
        }
        pdf.close();
        return true;
    }

    private static void setBasicInfo(ZhqdQueryReqVo queryReq, StringBuffer htmlStr) {
        setValue("acctDate", queryReq.getSysHeadVo().getAcgDt(), htmlStr);
        setValue("orgCode", queryReq.getAppHeadVo().getOrgId(), htmlStr);
        setValue("orgName", queryReq.getOrgName(), htmlStr);
        setValue("printPerson", queryReq.getAppHeadVo().getTxnTlrId(), htmlStr);
        setValue("printDate", DateUtil.format(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"), htmlStr);
    }

    /**
     * 赋值方法
     *
     * @param key
     * @param value
     * @param targetBuffer 目标模板
     * @return 赋值后结果
     */
    public static void setValue(String key, String value, StringBuffer targetBuffer) {
        Integer keyIndex = targetBuffer.indexOf("#{" + key + "}");
        if (value != null && keyIndex != null && keyIndex > -1) {
            int startIndex = keyIndex, endIndex = keyIndex + key.length() + 3;
            targetBuffer.replace(startIndex, endIndex, value);
            setValue(key, value, targetBuffer);
        }
    }
    private static Integer getLineNumValue(String key, StringBuffer targetBuffer){
        Integer value = null;
        Integer keyIndex = targetBuffer.indexOf("#{" + key + "");
        if (key != null && keyIndex != null && keyIndex > -1) {
            Integer keyEndIndex = targetBuffer.indexOf("}", keyIndex);
            value = Integer.valueOf(targetBuffer.substring(keyIndex + 3 + key.length(), keyEndIndex).trim());
            targetBuffer.replace(keyIndex, keyEndIndex+1,"");
            Integer values = getLineNumValue(key, targetBuffer);
            if(values != null)value = values;
        }
        return value;
    }
}
