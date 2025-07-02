package com.javaweb.api.admin;

import com.javaweb.constant.SystemConstant;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.security.utils.SecurityUtils;
import com.javaweb.service.ICustomerService;
import com.javaweb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO customerDTO, BindingResult bindingResult) throws Exception{
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
        CustomerEntity customerEntity = customerService.createCustomer(customerDTO);
        responseDTO.setMessage("Completed");
        responseDTO.setData(customerEntity.getFullName());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO, BindingResult bindingResult) throws Exception{
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
            if(customerService.checkAssignedStaff(customerDTO.getId(), staffId)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền sửa khách hàng này");
            }
        }
        CustomerEntity customerEntity = customerService.updateCustomer(customerDTO);
        responseDTO.setData(customerEntity.getFullName());
        responseDTO.setMessage("Completed");
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/{id}/staffs")
    public ResponseEntity<?> loadStaffs(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<UserEntity> staffList = userService.getStaffList();
        List<UserEntity> assignedStaffs = userService.getAssignedCustomerStaffs(id);
        List<StaffResponseDTO> staffResponseDTOS = new ArrayList<>();
        for (UserEntity staff : staffList) {
            StaffResponseDTO staffResponseDTO = new StaffResponseDTO();
            if (assignedStaffs.contains(staff)) {
                staffResponseDTO.setChecked("checked");
            }
            staffResponseDTO.setFullName(staff.getFullName());
            staffResponseDTO.setStaffId(staff.getId());
            staffResponseDTOS.add(staffResponseDTO);
        }
        responseDTO.setMessage("Completed");
        responseDTO.setData(staffResponseDTOS);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("{ids}")
    public ResponseEntity<?> deleteCustomers(@PathVariable List<Long> ids) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (ids == null) {
            responseDTO.setMessage("No Customer To Delete");
            return ResponseEntity.badRequest().body(responseDTO);
        }
        if(SecurityUtils.getAuthorities().contains(SystemConstant.ADMIN_ROLE)){
            Long staffId = SecurityUtils.getPrincipal().getId();
            for(Long id : ids){
                if(customerService.checkAssignedStaff(id, staffId)){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền xóa khách hàng này");
                }
            }
        }
        String result = customerService.deleteCustomer(ids);
        responseDTO.setMessage(result);
        return ResponseEntity.ok().body(responseDTO);
    }
}
