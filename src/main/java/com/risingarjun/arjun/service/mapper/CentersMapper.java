package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.CentersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Centers} and its DTO {@link CentersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CentersMapper extends EntityMapper<CentersDTO, Centers> {


    @Mapping(target = "centerheads", ignore = true)
    @Mapping(target = "removeCenterhead", ignore = true)
    Centers toEntity(CentersDTO centersDTO);

    default Centers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Centers centers = new Centers();
        centers.setId(id);
        return centers;
    }
}
