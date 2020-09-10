package com.risingarjun.arjun.service.mapper;

import com.risingarjun.arjun.domain.*;
import com.risingarjun.arjun.service.dto.SalariesPaymentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SalariesPayment} and its DTO {@link SalariesPaymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeesMapper.class})
public interface SalariesPaymentMapper extends EntityMapper<SalariesPaymentDTO, SalariesPayment> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.employeeId", target = "employeeEmployeeId")
    SalariesPaymentDTO toDto(SalariesPayment salariesPayment);

    @Mapping(source = "employeeId", target = "employee")
    SalariesPayment toEntity(SalariesPaymentDTO salariesPaymentDTO);

    default SalariesPayment fromId(Long id) {
        if (id == null) {
            return null;
        }
        SalariesPayment salariesPayment = new SalariesPayment();
        salariesPayment.setId(id);
        return salariesPayment;
    }
}
