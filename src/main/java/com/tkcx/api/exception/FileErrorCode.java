package com.tkcx.api.exception;

/**
 * 错误码
 * 
 * @author wangjunhai
 *
 */
public final class FileErrorCode {
	private FileErrorCode() {
	}

	/**
	 * 上传文件失败
	 */
	public static final int UPLOAD_FILE_ERROR = 75000;
	/**
	 * 下载文件失败
	 */
	public static final int DOWNLOAD_FILE_ERROR = 75001;
	/**
	 * 未知的ContentType
	 */
	public static final int UNKNOWN_CONTENT_TYPE = 75002;
	/**
	 * 文件{}不能为空
	 */
	public static final int FILE_CANNOT_BE_EMPTY = 75003;
	/**
	 * 无法读取文件{}
	 */
	public static final int FILE_CANNOT_BE_READ = 75004;
	/**
	 * 请勿上传空内容
	 */
	public static final int FILES_CANNOT_BE_EMPTY = 75005;
	/**
	 * 文件类型不能为空
	 */
	public static final int FILE_TYPE_CANNOT_BE_EMPTY = 75006;
	/**
	 * 文件内容不能为空
	 */
	public static final int FILE_CONTNET_CANNOT_BE_EMPTY = 75007;
	/**
	 * 获取文件失败
	 */
	public static final int GET_FILE_FAIL = 75008;

	/**
	 * 请求出错,请检查参数
	 */
	public static final int FAILED = 75999;

	/**
	 * 下载文件传输码不能为空
	 */
	public static final int FILE_DOWNLOAD_CODE_CANNOT_BE_EMPTY = 75009;

}
