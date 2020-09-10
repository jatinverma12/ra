package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.RoleAccessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoleAccess} and its DTO {@link RoleAccessDTO}.
 */
@Mapper(componentModel = "spring", uses = {JhiauthorityMapper.class, FeaturesMapper.class})
public interface RoleAccessMapper extends EntityMapper<RoleAccessDTO, RoleAccess> {

    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.name", target = "roleName")
    @Mapping(source = "feature.id", target = "featureId")
    @Mapping(source = "feature.featureDetail", target = "featureFeatureDetail")
    RoleAccessDTO toDto(RoleAccess roleAccess);

    @Mapping(source = "roleId", target = "role")
    @Mapping(source = "featureId", target = "feature")
    RoleAccess toEntity(RoleAccessDTO roleAccessDTO);

    default RoleAccess fromId(Long id) {
        if (id == null) {
            return null;
        }
        RoleAccess roleAccess = new RoleAccess();
        roleAccess.setId(id);
        return roleAccess;
    }
}
