package com.jgw.supercodeplatform.prizewheels.infrastructure.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jgw.supercodeplatform.prizewheels.domain.model.ScanRecord;
import com.jgw.supercodeplatform.prizewheels.domain.repository.ScanRecordRepository;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper.ScanRecordMapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ScanRecordPojo;
import com.jgw.supercodeplatform.prizewheels.infrastructure.transfer.ScanRecordPojoTranfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScanRecordRepositoryImpl implements ScanRecordRepository {
    @Autowired
    private ScanRecordMapper mapper;

    @Autowired
    private ScanRecordPojoTranfer scanRecordPojoTranfer;
    @Override
    public ScanRecord getCodeRecord(String outerCodeId, String codeTypeId) {
        QueryWrapper<ScanRecordPojo> query = new QueryWrapper<>();
        query.eq("OuterCodeId",outerCodeId);
        query.eq("CodeTypeId",codeTypeId);

        ScanRecordPojo scanRecordPojo = mapper.selectOne(query);
        if(scanRecordPojo == null){
            return null;
        }
        return scanRecordPojoTranfer.tranferPojoToDomain(scanRecordPojo);

    }

    @Override
    public void saveScanRecord(ScanRecord scanRecord) {
        mapper.insert( scanRecordPojoTranfer.tranferDomainToPojo(scanRecord));

    }
}
