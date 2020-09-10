package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.AcademicSessionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AcademicSessions} and its DTO {@link AcademicSessionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AcademicSessionsMapper extends EntityMapper<AcademicSessionsDTO, AcademicSessions> {



    default AcademicSessions fromId(Long id) {
        if (id == null) {
            return null;
        }
        AcademicSessions academicSessions = new AcademicSessions();
        academicSessions.setId(id);
        return academicSessions;
    }
}
