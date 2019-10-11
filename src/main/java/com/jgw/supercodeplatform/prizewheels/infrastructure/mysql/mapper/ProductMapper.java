package com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.mapper;
import com.jgw.supercodeplatform.prizewheels.infrastructure.mysql.pojo.ProductPojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author renxinlin
 * @since 2019-10-09
 */
@Repository
public interface ProductMapper extends BaseMapper<ProductPojo> {
    @Autowired
    private ModelMapper modelMapper;


}
