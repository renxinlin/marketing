package com.jgw.supercodeplatform.marketing.dao;

/**
 * 描述：公共sql模块
 *
 * @author corbett
 *         Created by corbett on 2018/10/15.
 */
public interface CommonSql {
    String select = " SELECT ";
    String startScript = "<script>";
    String endScript = "</script>";
    String count = "count(*)";
    String page = "<if test='startNumber != null and startNumber != 0 and pageCount != null and pageCount != 0'> LIMIT #{startNumber},#{pageCount}</if>";
    String page_limit = "LIMIT #{startNum},#{pageCount}";
}
