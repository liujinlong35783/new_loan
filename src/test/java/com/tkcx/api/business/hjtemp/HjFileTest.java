package com.tkcx.api.business.hjtemp;

import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/1 14:29
 */

public class HjFileTest {


    /**
     * 计算读取文件结束行数
     *
     * @param readEndNum
     * @param txtTotalNum
     * @return
     */
    private static int calReadEndLine(int readEndNum, int txtTotalNum) {

        System.out.println("已读取文件行数："+readEndNum+"，文件总行数："+ txtTotalNum);
        if(readEndNum < txtTotalNum &&
                (txtTotalNum-readEndNum) > HjFileFlagConstant.ONE_TIME_READ_LINE_NUM) {
            System.out.println("文件总行数查询>已读取行数，文件读取继续");
            // 结束行数与文件总行数之差大于500，则结束行数更新为当前结束行数+500(步长)
            return readEndNum + HjFileFlagConstant.ONE_TIME_READ_LINE_NUM;
        }else if(readEndNum < txtTotalNum &&
                (txtTotalNum-readEndNum) < HjFileFlagConstant.ONE_TIME_READ_LINE_NUM){
            // 结束行数与文件总行数之差小于500，则结束行数更新为当前结束行数+剩下未读取的行数
            return readEndNum + (txtTotalNum-readEndNum);
        }else if(readEndNum == txtTotalNum) {
            return txtTotalNum;
        }else {
            System.out.println("文件读取结束行数判断异常！！！！");
            return 0;
        }
    }


}
