package com.jgw.supercodeplatform.marketing.common.page;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 功能描述：
 *
 * @Author corbett
 * @Description //TODO
 * @Date 14:30 2018/11/02
 * @Param
 * @return
 **/
@ApiModel(value = "搜索model")
public class DaoSearch {
    @ApiModelProperty(name = "flag", value = "搜索标记;0-高级搜索-使用and连接；1-普通搜索-使用or连接,为空时表示无条件查询", example = "1", required = false,hidden=false)
    private Integer flag;
    @ApiModelProperty(name = "search", value = "普通搜索值", example = "dsad")
    private String search;
    @ApiModelProperty(name = "params", value = "高级查询，或删除/更新条件参数，即sql语句中where子句参数，多参数目前只支持and连接", example = "\"{\"Id\":\"1\"}\"",hidden=false)
    private Map<String, String> params;
    @ApiModelProperty(name = "startNumber", value = "分页开始数值", example = "1",hidden=true)
    private Integer startNumber;
    @ApiModelProperty(name = "pageSize", value = "每页多少数据", example = "10")
    private Integer pageSize;
    @ApiModelProperty(name = "current", value = "第几页", example = "1")
    private Integer current;
    
    
    public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Integer getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(Integer startNumber) {
		this.startNumber = startNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getFlag() {
		return flag;
	}

    public Map getParams() {
        return params;
    }

	public Integer getCurrent() {
		return current;
	}

    public void setCurrent(Integer current) {
        this.current = current == null || current == 0 ? 1 : current;
    }

	@SuppressWarnings("unchecked")
	public void setParams(String params) {
        try {
            if (params != null && !"".equals(params) && !"null".equals(params)) {
                this.params = new ObjectMapper().readValue(params, HashMap.class);
            } else {
                this.params = new HashMap<String,String>(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.params = new HashMap<String,String>(0);
        }
    }


    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
