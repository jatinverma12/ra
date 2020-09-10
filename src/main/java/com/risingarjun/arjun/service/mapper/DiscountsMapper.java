package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.DiscountsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Discounts} and its DTO {@link DiscountsDTO}.
 */
@Mapper(componentModel = "spring", uses = {AcademicSessionsMapper.class})
public interface DiscountsMapper extends EntityMapper<DiscountsDTO, Discounts> {

    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "session.acadSession", target = "sessionAcadSession")
    DiscountsDTO toDto(Discounts discounts);

    @Mapping(source = "sessionId", target = "session")
    Discounts toEntity(DiscountsDTO discountsDTO);

    default Discounts fromId(Long id) {
        if (id == null) {
            return null;
        }
        Discounts discounts = new Discounts();
        discounts.setId(id);
        return discounts;
    }
}
