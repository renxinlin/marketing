package com.jgw.supercodeplatform.marketing.common.model;

import java.io.Serializable;

/**
 * 全局序列编码表实体类
 * @author liujianqiang
 * @date 2018年1月17日
 */
public class CodeGlobalseq implements Serializable{
	private static final long serialVersionUID = 6911117287103607100L;
	private String keysType;
	private long currentMax;
	private long expectedMax;
	private long intervaData;
	private long neardataDiffer;
	public String getKeysType() {
		return keysType;
	}
	public void setKeysType(String keysType) {
		this.keysType = keysType;
	}
	public long getCurrentMax() {
		return currentMax;
	}
	public void setCurrentMax(long currentMax) {
		this.currentMax = currentMax;
	}
	public long getExpectedMax() {
		return expectedMax;
	}
	public void setExpectedMax(long expectedMax) {
		this.expectedMax = expectedMax;
	}
	public long getIntervaData() {
		return intervaData;
	}
	public void setIntervaData(long intervaData) {
		this.intervaData = intervaData;
	}
	public long getNeardataDiffer() {
		return neardataDiffer;
	}
	public void setNeardataDiffer(long neardataDiffer) {
		this.neardataDiffer = neardataDiffer;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (currentMax ^ (currentMax >>> 32));
		result = prime * result + (int) (expectedMax ^ (expectedMax >>> 32));
		result = prime * result + (int) (intervaData ^ (intervaData >>> 32));
		result = prime * result + ((keysType == null) ? 0 : keysType.hashCode());
		result = prime * result + (int) (neardataDiffer ^ (neardataDiffer >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (getClass() != obj.getClass()) {
            return false;
        }
		CodeGlobalseq other = (CodeGlobalseq) obj;
		if (currentMax != other.currentMax) {
            return false;
        }
		if (expectedMax != other.expectedMax) {
            return false;
        }
		if (intervaData != other.intervaData) {
            return false;
        }
		if (keysType == null) {
			if (other.keysType != null) {
                return false;
            }
		} else if (!keysType.equals(other.keysType)) {
            return false;
        }
		if (neardataDiffer != other.neardataDiffer) {
            return false;
        }
		return true;
	}
	
	
	
	
	
}
