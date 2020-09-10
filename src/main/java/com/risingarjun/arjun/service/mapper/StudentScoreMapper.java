package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.StudentScoreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudentScore} and its DTO {@link StudentScoreDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentsMapper.class, QuestionsMapper.class})
public interface StudentScoreMapper extends EntityMapper<StudentScoreDTO, StudentScore> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.studentRegId", target = "studentStudentRegId")
    @Mapping(source = "questionId.id", target = "questionIdId")
    StudentScoreDTO toDto(StudentScore studentScore);

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "questionIdId", target = "questionId")
    StudentScore toEntity(StudentScoreDTO studentScoreDTO);

    default StudentScore fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentScore studentScore = new StudentScore();
        studentScore.setId(id);
        return studentScore;
    }
}
