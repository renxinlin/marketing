package com.jgw.supercodeplatform.marketing.common.model.es;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Created by jgw136 on 2018/05/11.
 */
public class Page {
    @Value("${codedata.page.size}")
    private Integer size;
    private Integer from;

    public Integer getSize() {
        return size == null ? 10 : size;
    }

    public Page setSize(Integer size) {
        this.size = size;
        return this;
    }

    public Integer getFrom() {
        return from == null ? 0 : from;
    }

    public Page setFrom(Integer from) {
        this.from = from;
        return this;
    }

    @Override
    public String toString() {
        return "Page{" +
                "size=" + size +
                ", from=" + from +
                '}';
    }
}
