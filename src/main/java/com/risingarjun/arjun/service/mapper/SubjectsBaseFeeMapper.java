package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.SubjectsBaseFeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubjectsBaseFee} and its DTO {@link SubjectsBaseFeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {CoursesMapper.class, AcademicSessionsMapper.class})
public interface SubjectsBaseFeeMapper extends EntityMapper<SubjectsBaseFeeDTO, SubjectsBaseFee> {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.course", target = "courseCourse")
    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "session.acadSession", target = "sessionAcadSession")
    SubjectsBaseFeeDTO toDto(SubjectsBaseFee subjectsBaseFee);

    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "sessionId", target = "session")
    SubjectsBaseFee toEntity(SubjectsBaseFeeDTO subjectsBaseFeeDTO);

    default SubjectsBaseFee fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubjectsBaseFee subjectsBaseFee = new SubjectsBaseFee();
        subjectsBaseFee.setId(id);
        return subjectsBaseFee;
    }
}
