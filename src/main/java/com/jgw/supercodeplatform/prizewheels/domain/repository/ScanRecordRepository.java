package com.jgw.supercodeplatform.prizewheels.domain.repository;

import com.jgw.supercodeplatform.prizewheels.domain.model.ScanRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-15
 */
@Repository
public interface ScanRecordRepository  {

    ScanRecord getCodeRecord(String outCodeId,String codeType);

    void saveScanRecord(ScanRecord scanRecord);
}
