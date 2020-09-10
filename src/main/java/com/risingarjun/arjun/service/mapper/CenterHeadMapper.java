package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.CenterHeadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CenterHead} and its DTO {@link CenterHeadDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeesMapper.class, CentersMapper.class})
public interface CenterHeadMapper extends EntityMapper<CenterHeadDTO, CenterHead> {

    @Mapping(source = "centerhead.id", target = "centerheadId")
    @Mapping(source = "centerhead.employeeId", target = "centerheadEmployeeId")
    CenterHeadDTO toDto(CenterHead centerHead);

    @Mapping(source = "centerheadId", target = "centerhead")
    @Mapping(target = "removeCenter", ignore = true)
    CenterHead toEntity(CenterHeadDTO centerHeadDTO);

    default CenterHead fromId(Long id) {
        if (id == null) {
            return null;
        }
        CenterHead centerHead = new CenterHead();
        centerHead.setId(id);
        return centerHead;
    }
}
