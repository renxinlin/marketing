package com.jgw.supercodeplatform.prizewheels.infrastructure.transfer;

import com.jgw.supercodeplatform.prizewheels.domain.model.ScanRecord;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ScanRecordPojo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class ScanRecordPojoTranfer {
    @Autowired
    private ModelMapper modelMapper;

    public ScanRecord tranferPojoToDomain(ScanRecordPojo scanRecordPojo) {
        return modelMapper.map(scanRecordPojo,ScanRecord.class);
    }

    public ScanRecordPojo tranferDomainToPojo(ScanRecord scanRecord) {
        return modelMapper.map(scanRecord,ScanRecordPojo.class);
    }
}
