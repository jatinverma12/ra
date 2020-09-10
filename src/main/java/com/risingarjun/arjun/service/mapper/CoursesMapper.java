package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.CoursesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Courses} and its DTO {@link CoursesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoursesMapper extends EntityMapper<CoursesDTO, Courses> {


    @Mapping(target = "students", ignore = true)
    @Mapping(target = "removeStudents", ignore = true)
    @Mapping(target = "studentsubjects", ignore = true)
    @Mapping(target = "removeStudentsubjects", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "removeTeachers", ignore = true)
    Courses toEntity(CoursesDTO coursesDTO);

    default Courses fromId(Long id) {
        if (id == null) {
            return null;
        }
        Courses courses = new Courses();
        courses.setId(id);
        return courses;
    }
}
