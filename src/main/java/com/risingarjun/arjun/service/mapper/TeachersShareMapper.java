package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.TeachersShareDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeachersShare} and its DTO {@link TeachersShareDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeesMapper.class, SubjectsMapper.class, CoursesMapper.class, AcademicSessionsMapper.class})
public interface TeachersShareMapper extends EntityMapper<TeachersShareDTO, TeachersShare> {

    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "teacher.employeeId", target = "teacherEmployeeId")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "subject.subjectTitle", target = "subjectSubjectTitle")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.course", target = "courseCourse")
    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "session.acadSessionId", target = "sessionAcadSessionId")
    TeachersShareDTO toDto(TeachersShare teachersShare);

    @Mapping(source = "teacherId", target = "teacher")
    @Mapping(source = "subjectId", target = "subject")
    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "sessionId", target = "session")
    TeachersShare toEntity(TeachersShareDTO teachersShareDTO);

    default TeachersShare fromId(Long id) {
        if (id == null) {
            return null;
        }
        TeachersShare teachersShare = new TeachersShare();
        teachersShare.setId(id);
        return teachersShare;
    }
}
