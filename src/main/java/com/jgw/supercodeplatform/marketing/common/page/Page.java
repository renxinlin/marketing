package com.jgw.supercodeplatform.marketing.common.page;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分页实体类
 * @author liujianqiang
 * @date 2018年9月25日
 */
@ApiModel(description = "分页结果实体")
public class Page implements Serializable{
	private static final long serialVersionUID = 1414975513183194980L;
	@ApiModelProperty("当前页开始数字")
	private int startNumber;//当前页开始数字
	@ApiModelProperty("当前页")
	private int current;//当前页
	@ApiModelProperty("总页数")
	private int totalPage;//总页数
	@ApiModelProperty("每页记录数")
	private int pageSize;//每页记录数
	@ApiModelProperty("总记录数")
	private int total;//总记录数
	
	public int getStartNumber() {
		return startNumber;
	}
	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}


	/**
	 * 根据当前页和每页记录数和总记录数,初始化页码实体类
	 * @author liujianqiang
	 * @data 2018年10月10日
	 * @param pageSize
	 * @param current
	 * @param total
	 * @return
	 * @throws Exception
	 */
    public Page(int pageSize,int current,int total) throws SuperCodeException{
    	if(pageSize < 1 || current < 1){
    		throw new SuperCodeException("每页记录数或者当前页的值小于1");
    	}
    	this.pageSize = pageSize;
    	this.current = current;
    	this.total = total;
    	this.startNumber = (current-1)*pageSize;
    	
    	if(total == 0){//假如总记录数为0
    		this.totalPage = 1;
    	}else{
    		int temCount = total%pageSize;
    		int totalPage = total/pageSize;
    		if(temCount != 0){//假如余数不为0,则总页数加1
    			totalPage = totalPage + 1;
    		}
    		this.totalPage = totalPage;
    	}
    }
	public Page() {
	}
    
}
