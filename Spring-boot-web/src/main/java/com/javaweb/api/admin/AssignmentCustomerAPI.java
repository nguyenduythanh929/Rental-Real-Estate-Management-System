package com.javaweb.api.admin;

import com.javaweb.model.dto.AssignmentCustomerDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.IAssignmentCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assign/customer")
public class AssignmentCustomerAPI {

    @Autowired
    private IAssignmentCustomerService assignmentCustomerService;

    @PutMapping
    public ResponseEntity<?> updateAssignment(@RequestBody AssignmentCustomerDTO assignmentCustomerDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (assignmentCustomerDTO.getCustomerId() == null) {
            responseDTO.setMessage("Validate failed");
            return ResponseEntity.badRequest().body(responseDTO);
        }
        assignmentCustomerService.updateAssignment(assignmentCustomerDTO);
        responseDTO.setMessage("Assignment building update successfully");
        return ResponseEntity.ok().body(responseDTO);
    }
}
