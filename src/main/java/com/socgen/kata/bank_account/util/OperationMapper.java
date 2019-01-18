package com.socgen.kata.bank_account.util;

import com.socgen.kata.bank_account.dto.output.OperationDto;
import com.socgen.kata.bank_account.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OperationMapper {
    @Mappings({
            @Mapping(target = "accountNumber", source="operation.account.accountNumber")
            })
    OperationDto entityToDto(Operation operation);
}
