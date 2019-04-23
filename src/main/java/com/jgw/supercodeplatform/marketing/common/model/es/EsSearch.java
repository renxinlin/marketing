package com.jgw.supercodeplatform.marketing.common.model.es;

import com.jgw.supercodeplatform.marketing.enums.EsIndex;
import com.jgw.supercodeplatform.marketing.enums.EsType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Created by jgw136 on 2018/04/27.
 */
public class EsSearch {
    private EsIndex index;
    private EsType type;
    private Map<String, Object> param;
    private SortBuilder sortBuilder;
    private List<QueryBuilder> queryBuilders;
    private Page page;

    public List<QueryBuilder> getQueryBuilders() {
        return queryBuilders;
    }

    public void setQueryBuilders(List<QueryBuilder> queryBuilders) {
        this.queryBuilders = queryBuilders;
    }

    public Page getPage() {
        return page == null ? new Page() : page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public SortBuilder getSortBuilder() {
        return sortBuilder;
    }

    public void setSortBuilder(SortBuilder sortBuilder) {
        this.sortBuilder = sortBuilder;
    }

    public EsIndex getIndex() {
        return index;
    }

    public void setIndex(EsIndex index) {
        this.index = index;
    }

    public EsType getType() {
        return type;
    }

    public void setType(EsType type) {
        this.type = type;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "EsSearch{" +
                "index=" + index +
                ", type=" + type +
                ", param=" + param +
                ", sortBuilder=" + sortBuilder +
                ", page=" + page +
                '}';
    }
}
