package com.jgw.supercodeplatform.prizewheels.interfaces.assembler;

import com.jgw.supercodeplatform.prizewheels.domain.model.Wheels;
import com.jgw.supercodeplatform.prizewheels.interfaces.dto.WheelsDto;

public class WheelsAssembler {
    public static Wheels fromWeb(WheelsDto wheelsDto) {
        Wheels wheels = new Wheels();
        return wheels;
    }
}
