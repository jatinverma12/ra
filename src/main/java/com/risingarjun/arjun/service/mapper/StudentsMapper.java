package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.StudentsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Students} and its DTO {@link StudentsDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CoursesMapper.class})
public interface StudentsMapper extends EntityMapper<StudentsDTO, Students> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    StudentsDTO toDto(Students students);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "removeCourse", ignore = true)
    Students toEntity(StudentsDTO studentsDTO);

    default Students fromId(Long id) {
        if (id == null) {
            return null;
        }
        Students students = new Students();
        students.setId(id);
        return students;
    }
}
