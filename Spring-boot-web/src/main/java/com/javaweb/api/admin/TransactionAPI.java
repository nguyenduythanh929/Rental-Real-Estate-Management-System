package com.javaweb.api.admin;

import com.javaweb.constant.SystemConstant;
import com.javaweb.entity.TransactionEntity;
import com.javaweb.model.dto.TransactionDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.security.utils.SecurityUtils;
import com.javaweb.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionAPI {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> addOrUpdateTransaction(@Valid  @RequestBody TransactionDTO transactionDTO, BindingResult bindingResult){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList());
                responseDTO.setMessage("Validate failed");
                responseDTO.setData(errors);
                return ResponseEntity.badRequest().body(responseDTO);
            }
        } catch (Exception ex) {
            responseDTO.setMessage("Internal Server Error");
            responseDTO.setDetail(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);
        }
        if(SecurityUtils.getAuthorities().contains(SystemConstant.ADMIN_ROLE)){
            Long staffId = SecurityUtils.getPrincipal().getId();
            if(transactionDTO.getId()!= null && !transactionService.checkAssignedStaff(transactionDTO.getId(), staffId)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền cập nhật giao dịch này");
            }
        }
        TransactionEntity transaction = transactionService.addOrUpdateTransaction(transactionDTO);
        responseDTO.setData(transaction.getCode());
        responseDTO.setMessage("Completed");
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (id == null) {
            responseDTO.setMessage("No Transaction To Delete");
            return ResponseEntity.badRequest().body(responseDTO);
        }
        if(SecurityUtils.getAuthorities().contains(SystemConstant.ADMIN_ROLE)){
            Long staffId = SecurityUtils.getPrincipal().getId();
                if(!transactionService.checkAssignedStaff(id, staffId)){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền xóa giao dịch này");
                }
        }
        String result = transactionService.deleteTransaction(id);
        responseDTO.setMessage(result);
        return ResponseEntity.ok().body(responseDTO);
    }
}
