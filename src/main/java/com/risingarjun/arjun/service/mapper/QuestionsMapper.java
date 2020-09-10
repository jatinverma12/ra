package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.QuestionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Questions} and its DTO {@link QuestionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {CoursesMapper.class, SubjectsMapper.class, ChaptersMapper.class})
public interface QuestionsMapper extends EntityMapper<QuestionsDTO, Questions> {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.course", target = "courseCourse")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "subject.subjectTitle", target = "subjectSubjectTitle")
    @Mapping(source = "chapter.id", target = "chapterId")
    @Mapping(source = "chapter.chapterTitle", target = "chapterChapterTitle")
    QuestionsDTO toDto(Questions questions);

    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "subjectId", target = "subject")
    @Mapping(source = "chapterId", target = "chapter")
    Questions toEntity(QuestionsDTO questionsDTO);

    default Questions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Questions questions = new Questions();
        questions.setId(id);
        return questions;
    }
}
