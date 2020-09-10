package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.ScholarshipsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Scholarships} and its DTO {@link ScholarshipsDTO}.
 */
@Mapper(componentModel = "spring", uses = {AcademicSessionsMapper.class})
public interface ScholarshipsMapper extends EntityMapper<ScholarshipsDTO, Scholarships> {

    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "session.acadSession", target = "sessionAcadSession")
    ScholarshipsDTO toDto(Scholarships scholarships);

    @Mapping(source = "sessionId", target = "session")
    Scholarships toEntity(ScholarshipsDTO scholarshipsDTO);

    default Scholarships fromId(Long id) {
        if (id == null) {
            return null;
        }
        Scholarships scholarships = new Scholarships();
        scholarships.setId(id);
        return scholarships;
    }
}
