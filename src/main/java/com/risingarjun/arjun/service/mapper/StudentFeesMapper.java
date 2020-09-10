package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.StudentFeesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudentFees} and its DTO {@link StudentFeesDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentsMapper.class, SubjectsMapper.class, AcademicSessionsMapper.class, EmployeesMapper.class})
public interface StudentFeesMapper extends EntityMapper<StudentFeesDTO, StudentFees> {

    @Mapping(source = "registrationno.id", target = "registrationnoId")
    @Mapping(source = "registrationno.studentRegId", target = "registrationnoStudentRegId")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "subject.subjectTitle", target = "subjectSubjectTitle")
    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "session.acadSession", target = "sessionAcadSession")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "teacher.employeeId", target = "teacherEmployeeId")
    StudentFeesDTO toDto(StudentFees studentFees);

    @Mapping(source = "registrationnoId", target = "registrationno")
    @Mapping(source = "subjectId", target = "subject")
    @Mapping(source = "sessionId", target = "session")
    @Mapping(source = "teacherId", target = "teacher")
    StudentFees toEntity(StudentFeesDTO studentFeesDTO);

    default StudentFees fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentFees studentFees = new StudentFees();
        studentFees.setId(id);
        return studentFees;
    }
}
