package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.StudentsSubjectsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudentsSubjects} and its DTO {@link StudentsSubjectsDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentsMapper.class, AcademicSessionsMapper.class, SubjectsMapper.class, CoursesMapper.class})
public interface StudentsSubjectsMapper extends EntityMapper<StudentsSubjectsDTO, StudentsSubjects> {

    @Mapping(source = "registrationno.id", target = "registrationnoId")
    @Mapping(source = "registrationno.studentRegId", target = "registrationnoStudentRegId")
    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "session.acadSession", target = "sessionAcadSession")
    StudentsSubjectsDTO toDto(StudentsSubjects studentsSubjects);

    @Mapping(source = "registrationnoId", target = "registrationno")
    @Mapping(source = "sessionId", target = "session")
    @Mapping(target = "removeSubjects", ignore = true)
    @Mapping(target = "removeCourse", ignore = true)
    StudentsSubjects toEntity(StudentsSubjectsDTO studentsSubjectsDTO);

    default StudentsSubjects fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentsSubjects studentsSubjects = new StudentsSubjects();
        studentsSubjects.setId(id);
        return studentsSubjects;
    }
}
