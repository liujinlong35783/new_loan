package com.tkcx.api.business.hjtemp.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tkcx.api.business.acctPrint.model.BusiOrgBillModel;
import com.tkcx.api.business.acctPrint.model.InterestBillModel;
import com.tkcx.api.utils.BusinessUtils;
import com.tkcx.api.utils.ToolUtil;
import com.tkcx.api.vo.ZhqdQueryReqVo;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author cuijh
 * @since 2019/10/29 18:03
 */
public class GenerateTxtFileUtil {

    public static List<String> makeTxtFile(List queryResults, ZhqdQueryReqVo queryReq){
        String operator = queryReq.getAppHeadVo().getTxnTlrId();

        List<String> bufferList = new ArrayList<>();
        // 行数，机构扎账单每页展示22条数据，其他均为24条
        int row = queryReq.getInterfaceIden() == 3 ? 22 : 24;

        // 文件页数
        int countPage = (queryResults.size() % row == 0)
                ? queryResults.size() / row : queryResults.size() / row + 1;

        for (int i = 0; i < countPage; i++) {
            String debtNo = "";
            String contractNo = "";
            if (queryReq.getInterfaceIden() == 7 && queryResults.size() > 0) {
                debtNo = ((InterestBillModel)queryResults.get(0)).getDebtNo();
                contractNo = ((InterestBillModel)queryResults.get(0)).getContractNo();
            }

            // 拼接title
            bufferList.add(appendTitle(queryReq.getInterfaceIden()));
            bufferList.add("");

            // 拼接表格外表头
            bufferList.add(appendTHead(queryReq, countPage, i + 1, debtNo, contractNo));

            // 拼接表格固定行宽
            bufferList.add(appendTRow(queryReq.getInterfaceIden(), "┏", "┳", "┓"));

            // 拼接行名称
            bufferList.add(appendTRName(queryReq.getInterfaceIden()));

            if (queryResults.size() > 0) {
                int fromIndex = i * row;
                int toIndex = (i + 1) * row;

                if (i == countPage - 1) {
                    toIndex = queryResults.size();
                }

                int debtNum = 0;
                int creditNum = 0;
                BigDecimal debtAmount = new BigDecimal(0);
                BigDecimal creditAmount = new BigDecimal(0);

                int offBalanceDebtNum = 0;
                int offBalanceCreditNum = 0;
                BigDecimal offBalanceDebtAmount = new BigDecimal(0);
                BigDecimal offBalanceCreditAmount = new BigDecimal(0);
                for (Object obj : queryResults.subList(fromIndex, toIndex)) {

                    // 机构轧帐单统计表内外合计
                    if (queryReq.getInterfaceIden() == 3 && obj instanceof BusiOrgBillModel) {
                        BusiOrgBillModel orgBillModel = (BusiOrgBillModel)obj;

                        if ("0".equals(orgBillModel.getFlag())) {
                            debtNum += orgBillModel.getDebtNum();
                            debtAmount = debtAmount.add(new BigDecimal(orgBillModel.getDebtAmount()));
                            creditNum += orgBillModel.getCreditNum();
                            creditAmount = creditAmount.add(new BigDecimal(orgBillModel.getCreditAmount()));
                        } else {
                            offBalanceDebtNum += orgBillModel.getDebtNum();
                            offBalanceDebtAmount = offBalanceDebtAmount.add(new BigDecimal(orgBillModel.getDebtAmount()));
                            offBalanceCreditNum += orgBillModel.getCreditNum();
                            offBalanceCreditAmount = offBalanceCreditAmount.add(new BigDecimal(orgBillModel.getCreditAmount()));
                        }
                    }

                    // 拼接行分割线
                    bufferList.add(appendTRow(queryReq.getInterfaceIden(), "┣", "╋", "┫"));

                    // 拼接行元素
                    bufferList.add(obj.toString());
                }

                // 机构轧帐单拼接表内外合计
                if (queryReq.getInterfaceIden() == 3) {
                    bufferList.addAll(appendBusiOrgBill(debtNum, debtAmount, creditNum, creditAmount,
                            offBalanceDebtNum, offBalanceDebtAmount, offBalanceCreditNum, offBalanceCreditAmount));
                }
            }

            // 拼接行尾分割线
            bufferList.add(appendTRow(queryReq.getInterfaceIden(), "┗", "┻", "┛"));
            if (queryReq.getInterfaceIden() == 3) {
                String line = bufferList.get(bufferList.size() - 1).replaceFirst("┻", "━");
                bufferList.set(bufferList.size() - 1, line);
            }

            // 拼接表格尾
            bufferList.add(appendTFoot(queryReq.getInterfaceIden(), operator));
            bufferList.add("{hy}");
        }

        return bufferList;
    }

    private static List<String> appendBusiOrgBill(int debtNum, BigDecimal debtAmount, int creditNum,
                                            BigDecimal creditAmount, int offBalanceDebtNum, BigDecimal offBalanceDebtAmount,
                                            int offBalanceCreditNum, BigDecimal offBalanceCreditAmount) {

        String debtAmountStr = ToolUtil.fenToYuan(debtAmount.toPlainString());
        String creditAmountStr = ToolUtil.fenToYuan(creditAmount.toPlainString());
        String offDebtAmount = ToolUtil.fenToYuan(offBalanceDebtAmount.toPlainString());
        String offBCreditAmount = ToolUtil.fenToYuan(offBalanceCreditAmount.toPlainString());


        List<String> bufferList = new ArrayList<>();
        bufferList.add(appendTRow(3, "┣", "╋", "┫"));
        String line = bufferList.get(bufferList.size() - 1).replaceFirst("╋", "┻");
        bufferList.set(bufferList.size() - 1, line);

        StringBuffer buffer = new StringBuffer();
        buffer.append("┃").append(addDefaultVal(17)).append("表内合计").append(addDefaultVal(17))
                .append("┃").append(BusinessUtils.addDefaultVal(24 - String.valueOf(debtNum).length()) + debtNum)
                .append("┃").append(BusinessUtils.addDefaultVal(28 - debtAmountStr.length()) + debtAmountStr)
                .append("┃").append(BusinessUtils.addDefaultVal(24 - String.valueOf(creditNum).length()) + creditNum)
                .append("┃").append(BusinessUtils.addDefaultVal(28 - creditAmountStr.length()) + creditAmountStr).append("┃");
        bufferList.add(buffer.toString());

        bufferList.add(appendTRow(3, "┣", "╋", "┫"));
        line = bufferList.get(bufferList.size() - 1).replaceFirst("╋", "━");
        bufferList.set(bufferList.size() - 1, line);

        buffer = new StringBuffer();
        buffer.append("┃").append(addDefaultVal(17)).append("表外合计").append(addDefaultVal(17))
                .append("┃").append(BusinessUtils.addDefaultVal(24 - String.valueOf(offBalanceDebtNum).length()) + offBalanceDebtNum)
                .append("┃").append(BusinessUtils.addDefaultVal(28 - offDebtAmount.length()) + offDebtAmount)
                .append("┃").append(BusinessUtils.addDefaultVal(24 - String.valueOf(offBalanceCreditNum).length()) + offBalanceCreditNum)
                .append("┃").append(BusinessUtils.addDefaultVal(28 - offBCreditAmount.length()) + offBCreditAmount).append("┃");
        bufferList.add(buffer.toString());

        return bufferList;

    }

    /**
     * 拼接表格名称
     *
     * @param intfId
     * @return
     */
    public static String appendTitle(int intfId) {
        if (intfId == 3) {
            return addDefaultVal(70) + "网  贷  机  构  轧  帐  单";
        } else if (intfId == 4) {
            return addDefaultVal(85) + "网  贷  机  构  流  水";
        } else if (intfId == 5) {
            return addDefaultVal(105) + "网  贷  贷  款  科  目  分  户  账";
        } else if (intfId == 6) {
            return addDefaultVal(120) + "网  贷  贷  款  明  细  账";
        } else if (intfId == 7) {
            return addDefaultVal(125) + "网  贷  贷  款  利  息  登  记  簿";
        } else if (intfId == 9) {
            return addDefaultVal(125) + "网贷贷款形态调整登记簿";
        }
        return null;
    }

    /**
     * 拼接表格外表头
     *
     * @param queryReq
     * @param countPage
     * @param currentPage
     * @return
     */
    public static String appendTHead(ZhqdQueryReqVo queryReq, Integer countPage,
                                     Integer currentPage, String debtNo, String contractNo) {
        String orgCode = queryReq.getAppHeadVo().getOrgId();
        String acgDt = queryReq.getSysHeadVo().getAcgDt();
        String orgName = queryReq.getOrgName();

        Integer[] theadWidth = getRowWidth(queryReq.getInterfaceIden(), 0);

        StringBuffer buffer = new StringBuffer();
        buffer.append(" 机构名称:").append(orgName).append(addDefaultVal(theadWidth[0] - orgName.length() * 2));
        buffer.append("机构号:").append(orgCode).append(addDefaultVal(theadWidth[1]));
        if (queryReq.getInterfaceIden() == 7) {
            buffer.append("合同号:").append(contractNo).append(addDefaultVal(theadWidth[2]));
            buffer.append("借据号:").append(debtNo).append(addDefaultVal(theadWidth[2]));
        } else if (queryReq.getInterfaceIden() == 3 || queryReq.getInterfaceIden() == 4){
            buffer.append("会计日期:").append(acgDt).append(addDefaultVal(theadWidth[2]));
        } else {
            buffer.append(addDefaultVal(theadWidth[2]));
        }
        buffer.append("币种:").append("人民币").append(addDefaultVal(theadWidth[3]));
        buffer.append("单位:").append("元").append(addDefaultVal(theadWidth[4]));
        buffer.append("页数:").append("共").append(countPage).append("页/第").append(currentPage).append("页");

        return buffer.toString();
    }


    /**
     * 拼接表格尾部
     * @param intfId
     * @param operator
     * @return
     */
    public static String appendTFoot(int intfId, String operator) {
        Integer[] tfootWidth = getRowWidth(intfId, 2);
        StringBuffer buffer = new StringBuffer();
        buffer.append(" 打印柜员:").append(operator).append(addDefaultVal(tfootWidth[0] - operator.length()));
        buffer.append(addDefaultVal(tfootWidth[1])).append("打印日期:").append(DateUtil.format(new Date(), "yyyy年MM月dd日 HH时mm分ss秒"));

        return buffer.toString();
    }

    /**
     * 获取表格宽度
     *
     * @param intfId
     * @param widthType
     * @return
     */
    public static Integer[] getRowWidth(int intfId, int widthType) {
        if (intfId == 3) {
            if (widthType == 0) {
                // 表头宽度
                return new Integer[]{50, 7, 5, 7, 5};
            } else if (widthType == 1) {
                // 列宽
                return new Integer[]{10, 10, 12, 14, 12, 14};
            } else {
                // 表尾宽度
                return new Integer[]{38, 74};
            }
        } else if (intfId == 4) {
            if (widthType == 0) {
                return new Integer[]{56, 12, 12, 12, 12};
            } else if (widthType == 1) {
                return new Integer[]{14, 14, 14, 14, 5, 5, 5, 4, 5, 3};
            } else {
                return new Integer[]{38, 100};
            }
        } else if (intfId == 5) {
            if (widthType == 0) {
                return new Integer[]{77, 38, 36, 34, 32};
            } else if (widthType == 1) {
                return new Integer[]{4, 23, 6, 13, 5, 4, 4, 5, 5, 10, 4, 5, 5, 10, 5, 4, 5, 10};
            } else {
                return new Integer[]{38, 204};
            }
        } else if (intfId == 6) {
            if (widthType == 0) {
                return new Integer[]{72, 37, 37, 33, 33};
            } else if (widthType == 1) {
                return new Integer[]{4, 23, 6, 14, 14, 11, 5, 5, 5, 5, 5, 5, 5, 5, 5, 10};
            } else {
                return new Integer[]{38, 200};
            }
        } else if (intfId == 7) {
            if (widthType == 0) {
                return new Integer[]{64, 20, 20, 20, 20};
            } else if (widthType == 1) {
                return new Integer[]{4, 23, 14, 6, 5, 5, 6, 6, 5, 5, 5, 6, 6, 5, 5, 4, 10, 5};
            } else {
                return new Integer[]{38, 196};
            }
        } else if (intfId == 9) {
            if (widthType == 0) {
                return new Integer[]{78, 38, 38, 34, 34};
            } else if (widthType == 1) {
                return new Integer[]{4, 23, 8, 6, 14, 14, 11, 5, 5, 12, 6, 12, 8, 5};
            } else {
                return new Integer[]{38, 210};
            }
        }
        return null;
    }

    /**
     * 拼接表格行内容
     * @param intfId
     * @return
     */
    public static String appendTRow(int intfId, String startStr, String splitStr, String endStr) {
        Integer[] sizes = getRowWidth(intfId, 1);

        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < sizes.length; i++) {
            if (i == 0) {
                buffer.append(startStr);
            } else {
                buffer.append(splitStr);
            }
            buffer.append(addAcross(sizes[i]));
        }
        buffer.append(endStr);

        return buffer.toString();
    }

    /**
     * 拼接行名称
     * @param intfId
     * @return
     */
    private static String appendTRName(Integer intfId) {
        Map<String, Integer> map = new LinkedHashMap<>();
        if (intfId == 3) {
            map.put("科目控制字", 5);
            map.put("表内外标识", 5);
            map.put("本日借方笔数", 6);
            map.put("本日借方发生额", 7);
            map.put("本日贷方笔数", 6);
            map.put("本日贷方发生额", 7);
        } else if (intfId == 4) {
            map.put("流水号", 11);
            map.put("借据号", 11);
            map.put("贷款账号", 10);
            map.put("摘要", 12);
            map.put("借贷标识", 1);
            map.put("科目控制字", 0);
            map.put("发生额", 2);
            map.put("交易类型", 0);
            map.put("机构号", 2);
            map.put("柜员号", 0);
        } else if (intfId == 5) {
            map.put("机构号", 1);
            map.put("机构名称", 19);
            map.put("户名", 4);
            map.put("贷款账号", 9);
            map.put("科目控制字", 0);
            map.put("本金状态", 0);
            map.put("账户状态", 0);
            map.put("放款日", 2);
            map.put("放款金额", 1);
            map.put("放款账号", 6);
            map.put("执行利率", 0);
            map.put("到期日", 2);
            map.put("最后还款日", 0);
            map.put("还款账号", 6);
            map.put("还款金额", 1);
            map.put("借贷标识", 0);
            map.put("余额", 3);
            map.put("身份证号", 6);
        } else if (intfId == 6) {
            map.put("机构号", 1);
            map.put("机构名称", 19);
            map.put("户名", 4);
            map.put("贷款账号", 10);
            map.put("借据号", 11);
            map.put("合同号", 8);
            map.put("发放日期", 1);
            map.put("发放金额", 1);
            map.put("还款日期", 1);
            map.put("生效日期", 1);
            map.put("借贷标识", 1);
            map.put("还本金额", 1);
            map.put("还息金额", 1);
            map.put("全部金额", 1);
            map.put("本金余额", 1);
            map.put("身份证号", 6);
        } else if (intfId == 7) {
            map.put("机构号", 1);
            map.put("机构名称", 19);
            map.put("贷款账号", 10);
            map.put("户名", 4);
            map.put("还款日期", 1);
            map.put("应还本金", 1);
            map.put("应还正常利息", 0);
            map.put("应还逾期利息", 0);
            map.put("应还费用", 1);
            map.put("应还汇总", 1);
            map.put("已还本金", 1);
            map.put("已还正常利息", 0);
            map.put("已还逾期利息", 0);
            map.put("已还费用", 1);
            map.put("已还汇总", 1);
            map.put("结清标志", 0);
            map.put("身份证号", 6);
            map.put("结息日期", 1);
        } else if (intfId == 9) {
            map.put("机构号", 1);
            map.put("机构名称", 19);
            map.put("贷款形态调整日期", 0);
            map.put("户名", 4);
            map.put("贷款账号", 10);
            map.put("借据号", 11);
            map.put("合同号", 8);
            map.put("发放日期", 1);
            map.put("到期日期", 1);
            map.put("原科目名称", 7);
            map.put("原科目控制字", 0);
            map.put("转换后科目名称", 5);
            map.put("转换后科目控制字", 0);
            map.put("贷款余额", 1);
        }

        StringBuffer buffer = new StringBuffer();

        for (String colName : map.keySet()) {
            int size = map.get(colName);
            buffer.append("┃").append(addDefaultVal(size))
                    .append(colName).append(addDefaultVal(size));
        }
        buffer.append("┃");

        return buffer.toString();
    }




    public static String addDefaultVal(int size) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            buffer.append(" ");
        }
        return buffer.toString();
    }

    public static String addAcross(int size) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            buffer.append("━");
        }
        return buffer.toString();
    }


    /**
     * 写入txt文件
     *
     * @param queryResults
     * @param queryReq
     * @return
     */
    public static boolean writeDataTxt(List queryResults, ZhqdQueryReqVo queryReq, String txtPathName) {
        // 拼接文件内容
        List<String> result = makeTxtFile(queryResults, queryReq);

        long start = System.currentTimeMillis();

        boolean flag = false;

        if (result != null && !result.isEmpty() && StringUtils.isNotEmpty(txtPathName)) {
            FileUtil.appendLines(result, txtPathName, "GBK");
            flag = true;
            System.out.println("写入文件耗时：*********************************" + (System.currentTimeMillis() - start) + "毫秒");
        }
        return flag;

    }


}
