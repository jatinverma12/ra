package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.ExpensesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Expenses} and its DTO {@link ExpensesDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeesMapper.class})
public interface ExpensesMapper extends EntityMapper<ExpensesDTO, Expenses> {

    @Mapping(source = "incurredBy.id", target = "incurredById")
    @Mapping(source = "incurredBy.employeeId", target = "incurredByEmployeeId")
    ExpensesDTO toDto(Expenses expenses);

    @Mapping(source = "incurredById", target = "incurredBy")
    Expenses toEntity(ExpensesDTO expensesDTO);

    default Expenses fromId(Long id) {
        if (id == null) {
            return null;
        }
        Expenses expenses = new Expenses();
        expenses.setId(id);
        return expenses;
    }
}
