package com.tkcx.api.constant;

public class SystemLogConstant {
	//分隔符
	public static final String SEPARATE = "|";
	public static final String BAK = "_";
	public static final String ENTER = "\r\n";
	//日切准备
	public static final String DAY_SWITCH_READY_NOED_START = "日切准备_start";
	public static final String DAY_SWITCH_READY_NOED_CHECK = "日切准备_check";
	
	//日切通知
	public static final String DAY_SWITCH_NOTICE_NOED_START = "日切通知_start";
	public static final String DAY_SWITCH_NOTICE_NOED_CHECK = "日切通知_check";
	//财务核算核对
	public static final String ACCOUNTING_CHECKING_NOED_START = "财务核算核对_start";
	public static final String ACCOUNTING_CHECKING_NOED_CHECK = "财务核算核对_check";
	//日切结束通知
	public static final String DAY_SWITCH_END_NOED_START = "日切结束通知_start";
	public static final String DAY_SWITCH_END_NOED_CHECK = "日切结束通知_check";
	//向互金核算供数
	public static final String PROVIDE_DATA_NOED_START = "向互金核算供数_start";
	public static final String PROVIDE_DATA_NOED_CHECK = "向互金核算供数_check";
	//秦农网贷生成征信文件
	public static final String CREDIT_REPORTING_NOED_START = "秦农网贷生成征信文件_start";
	public static final String CREDIT_REPORTING_NOED_CHECK = "秦农网贷生成征信文件_check";
	//秦农网贷生成监管报送及报表文件
	public static final String SUPERVISE_NOED_START = "秦农网贷生成监管报送及报表文件_start";
	public static final String SUPERVISE_NOED_CHECK = "秦农网贷生成监管报送及报表文件_check";
	//秦农网贷生成营改增文件
	public static final String QN_TAXATION_NOED_START = "秦农网贷生成营改增文件_start";
	public static final String QN_TAXATION_NOED_CHECK = "秦农网贷生成营改增文件_check";
	
	public static final String NOED_STATUS_START = "执行状态[start]";
	public static final String NOED_STATUS_DOING = "执行状态[doing]";
	public static final String NOED_STATUS_SUCCESS = "执行状态[success]";
	public static final String NOED_STATUS_ERROR = "执行状态[error]";
	
	public static final String NOED_RESULT_DOING = "正在运行";
	public static final String NOED_RESULT_END = "成功";
}
