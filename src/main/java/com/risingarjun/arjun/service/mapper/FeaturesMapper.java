package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.FeaturesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Features} and its DTO {@link FeaturesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FeaturesMapper extends EntityMapper<FeaturesDTO, Features> {



    default Features fromId(Long id) {
        if (id == null) {
            return null;
        }
        Features features = new Features();
        features.setId(id);
        return features;
    }
}
