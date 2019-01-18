package com.socgen.kata.bank_account.util;

import com.socgen.kata.bank_account.dto.output.OperationsHistoryDto;
import com.socgen.kata.bank_account.model.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OperationHistoryMapper {

    List<OperationsHistoryDto> entityListToDtos(List<Operation> operation);
}
