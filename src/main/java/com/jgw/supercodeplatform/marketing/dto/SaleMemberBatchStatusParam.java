package com.jgw.supercodeplatform.marketing.dto;

import lombok.Data;

import java.util.List;
@Data
public class SaleMemberBatchStatusParam {
    private List<Long> ids;
    private int state;
}
