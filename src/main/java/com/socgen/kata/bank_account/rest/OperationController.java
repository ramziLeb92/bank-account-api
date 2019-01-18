package com.socgen.kata.bank_account.rest;

import com.socgen.kata.bank_account.dto.input.OperationRequestDto;
import com.socgen.kata.bank_account.dto.output.AccountAmountDto;
import com.socgen.kata.bank_account.dto.output.OperationDto;
import com.socgen.kata.bank_account.dto.output.OperationsHistoryDto;
import com.socgen.kata.bank_account.exception.AccountNotFoundException;
import com.socgen.kata.bank_account.exception.InsufficientFundException;
import com.socgen.kata.bank_account.model.Operation;
import com.socgen.kata.bank_account.service.AccountService;
import com.socgen.kata.bank_account.service.OperationService;
import com.socgen.kata.bank_account.util.OperationHistoryMapper;
import com.socgen.kata.bank_account.util.OperationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    private final AccountService accountService;

    private final OperationHistoryMapper operationHistoryMapper;

    private final OperationMapper operationMapper;

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperationDto> executeOperation(@Validated @RequestBody OperationRequestDto operationRequestDto) throws AccountNotFoundException, InsufficientFundException {

        log.info("execute operation for the account {} for the amount {} ", operationRequestDto.getAccountNumber(), operationRequestDto.getAccountNumber());
        Operation operation = operationService.registerOperation(operationRequestDto.getAccountNumber(), operationRequestDto.getOperationAmount());
        log.info("New Operation registered for account {}", operationRequestDto.getAccountNumber());
        OperationDto operationDto = operationMapper.entityToDto(operation);
        return new ResponseEntity<>(operationDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OperationsHistoryDto>> getAllOperationForAClient(
            @RequestParam(value = "accountNumber") int accountNumber,
            @RequestParam(value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate start,
            @RequestParam(value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) throws AccountNotFoundException {

        log.info("getAllOperationForAClient() accountNumber: {}", accountNumber);

        List<Operation> operations = operationService.findOperations(accountNumber, start, end);
        List<OperationsHistoryDto> operationsHistoryDtos = operationHistoryMapper.entityListToDtos(operations);
        return ResponseEntity.status(HttpStatus.OK).body(operationsHistoryDtos);
    }

    @GetMapping(path = "/{accountNumber}/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAccountStatus(@PathVariable("accountNumber") final Integer accountNumber) throws AccountNotFoundException {

        log.info("getAccountStatus() accountNumber: {}", accountNumber);

        Double amount = accountService.getAccountAmount(accountNumber);

        AccountAmountDto accountAmountDto = AccountAmountDto.builder().amount(amount).build();

        return ResponseEntity.status(HttpStatus.OK).body(accountAmountDto);
    }

}
