package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.SubjectsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subjects} and its DTO {@link SubjectsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SubjectsMapper extends EntityMapper<SubjectsDTO, Subjects> {


    @Mapping(target = "studentsubjects", ignore = true)
    @Mapping(target = "removeStudentsubject", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "removeTeachers", ignore = true)
    Subjects toEntity(SubjectsDTO subjectsDTO);

    default Subjects fromId(Long id) {
        if (id == null) {
            return null;
        }
        Subjects subjects = new Subjects();
        subjects.setId(id);
        return subjects;
    }
}
