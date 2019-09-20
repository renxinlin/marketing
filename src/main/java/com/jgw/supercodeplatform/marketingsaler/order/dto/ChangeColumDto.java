package com.jgw.supercodeplatform.marketingsaler.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeColumDto {
    private Long id;
    private String oldFormName;
    private String oldColumnName;
    private String newFormName;
    private String newColumnName;
    private String tableName;

    private String value;
}
