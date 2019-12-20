package com.jgw.supercodeplatform.mutIntegral.domain.entity.manager.sendIntegral;

import com.jgw.supercodeplatform.mutIntegral.infrastructure.constants.MutiIntegralCommonConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.Asserts;

import java.util.Date;

/**
 * 积分被派送人
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class SendIntegralPersonDomain {

    private  String organizationId;
    private  String organizationName;
    private  String memberName;
    private  Integer integralNum;
    private  String mobile;
    private  Long id;
    private String customerId;
    private String customerName;
    public void cpoyInfo(SendIntegralPersonDomain existsPerson) {
        Asserts.check(existsPerson != null, MutiIntegralCommonConstants.nullError);
        this.id = existsPerson.id;
        this.memberName = existsPerson.getMemberName();
        this.customerId = existsPerson.getCustomerId();
        this.customerName = existsPerson.getCustomerName();
    }

    public ProductSendIntegralDomain newRecord(String operatorId,String operatorName,String remark) {
        ProductSendIntegralDomain productSendIntegralDomain = new ProductSendIntegralDomain();
        productSendIntegralDomain.setCustomerId(customerId);
        productSendIntegralDomain.setCustomerName(customerName);
        productSendIntegralDomain.setIntegralNum(integralNum);
        productSendIntegralDomain.setMemberId(id);
        productSendIntegralDomain.setMemberName(memberName);
        productSendIntegralDomain.setOperationTime(new Date());
        productSendIntegralDomain.setOrganizationId(organizationId);
        productSendIntegralDomain.setOrganizationName(organizationName);
        productSendIntegralDomain.setRemark(remark);
        productSendIntegralDomain.setOperaterId(operatorId);
        productSendIntegralDomain.setOperaterName(operatorName);
        return productSendIntegralDomain;
    }
}
