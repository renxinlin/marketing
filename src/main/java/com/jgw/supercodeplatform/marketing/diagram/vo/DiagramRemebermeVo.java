package com.jgw.supercodeplatform.marketing.diagram.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 图表记住选择
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagramRemebermeVo {
    private String organizationId;
    private String userId;
    private String choose;
}
