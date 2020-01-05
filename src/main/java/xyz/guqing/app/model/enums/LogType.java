package xyz.guqing.app.model.enums;

import xyz.guqing.app.model.support.LogTypeConstant;

public enum LogType {
	/**
	 * 日志类型为新增
	 */
	INSERT(LogTypeConstant.INSERT, "新增"),
	/**
	 * 日志类型为更新
	 */
	UPDATE(LogTypeConstant.UPDATE, "更新"),
	/**
	 * 日志类型为删除
	 */
	DELETE(LogTypeConstant.DELETE, "删除"),
	/**
	 * 日志类型为用户登录
	 */
	LOGIN(LogTypeConstant.LOGIN, "登录");

	private int type;
	private String name;
	private LogType(int type, String name){
		this.type = type;
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public String getName() {
		return name;
	}
}

