package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.TeachersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Teachers} and its DTO {@link TeachersDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeesMapper.class, SubjectsMapper.class, CoursesMapper.class})
public interface TeachersMapper extends EntityMapper<TeachersDTO, Teachers> {

    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "teacher.employeeId", target = "teacherEmployeeId")
    TeachersDTO toDto(Teachers teachers);

    @Mapping(source = "teacherId", target = "teacher")
    @Mapping(target = "removeSubjects", ignore = true)
    @Mapping(target = "removeCourses", ignore = true)
    Teachers toEntity(TeachersDTO teachersDTO);

    default Teachers fromId(Long id) {
        if (id == null) {
            return null;
        }
        Teachers teachers = new Teachers();
        teachers.setId(id);
        return teachers;
    }
}
