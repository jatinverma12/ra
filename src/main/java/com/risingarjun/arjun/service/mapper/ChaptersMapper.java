package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.ChaptersDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Chapters} and its DTO {@link ChaptersDTO}.
 */
@Mapper(componentModel = "spring", uses = {CoursesMapper.class, SubjectsMapper.class})
public interface ChaptersMapper extends EntityMapper<ChaptersDTO, Chapters> {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.course", target = "courseCourse")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "subject.subjectTitle", target = "subjectSubjectTitle")
    ChaptersDTO toDto(Chapters chapters);

    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "subjectId", target = "subject")
    Chapters toEntity(ChaptersDTO chaptersDTO);

    default Chapters fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chapters chapters = new Chapters();
        chapters.setId(id);
        return chapters;
    }
}
