package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.EmployeesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employees} and its DTO {@link EmployeesDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface EmployeesMapper extends EntityMapper<EmployeesDTO, Employees> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    EmployeesDTO toDto(Employees employees);

    @Mapping(source = "userId", target = "user")
    Employees toEntity(EmployeesDTO employeesDTO);

    default Employees fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employees employees = new Employees();
        employees.setId(id);
        return employees;
    }
}
