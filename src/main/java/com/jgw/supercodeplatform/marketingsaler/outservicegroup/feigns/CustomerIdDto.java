package com.jgw.supercodeplatform.marketingsaler.outservicegroup.feigns;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerIdDto implements Serializable {
    private String customerId;
}
