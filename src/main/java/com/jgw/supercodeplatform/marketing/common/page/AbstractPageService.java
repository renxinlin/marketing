package com.jgw.supercodeplatform.marketing.common.page;

import java.util.Map;

import com.jgw.supercodeplatform.marketing.common.util.BeanPropertyUtil;
import com.jgw.supercodeplatform.marketing.common.util.CommonUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 功能描述：分页查询的抽象类
 *
 * @Author corbett
 * @Description //TODO
 * @Date 11:17 2018/11/02
 **/
@ApiModel(description = "分页结果父model")
public class AbstractPageService<E extends DaoSearch> extends CommonUtil {

    public <T> PageResults<T> listSearchViewLike(E searchParams) throws Exception {
        //总页数
        int total = count(searchParams);
        Map param = BeanPropertyUtil.toMap(searchParams);
        
        param.put("current", searchParams.getCurrent());
        //分页参数
        Page page = getPage(param, total);
        searchParams.setStartNumber(page.getStartNumber());
        searchParams.setPageSize(page.getPageSize());
        //返回分页结果
        Object t=searchResult(searchParams);
        return new PageResults(t, page);
    }

    protected <T> T searchResult(E searchParams) throws Exception {
        return null;
    }

    protected int count(E searchParams) throws Exception {
        return 1;
    }

    @ApiModel(description = "分页结果model")
    public static class PageResults<T> {
        @ApiModelProperty("分页结果数据")
        private T list; 
        @ApiModelProperty("分页信息")
        private Page pagination;
        @ApiModelProperty("额外信息,没有特别声明不需要关注")
        private Object other;
        
		public PageResults(T list, Page pagination) {
			this.list = list;
			this.pagination = pagination;
		}
		public T getList() {
			return list;
		}
		public void setList(T list) {
			this.list = list;
		}
		public Page getPagination() {
			return pagination;
		}
		public void setPagination(Page pagination) {
			this.pagination = pagination;
		}
		public Object getOther() {
			return other;
		}
		public void setOther(Object other) {
			this.other = other;
		}
    }
}
