package com.jgw.supercodeplatform.marketing.dto.test;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CodeUserDto {

    private String openid;

    private String outerCode;

    private String codeType;

    private String organizationId;

    private String organizationFullName;

    private String productId;

    private String productName;

    private String productBatchId;

    private String productBatchName;

    private String sbatchId;

    private Integer businessType;

    private Integer clientRole;

}
