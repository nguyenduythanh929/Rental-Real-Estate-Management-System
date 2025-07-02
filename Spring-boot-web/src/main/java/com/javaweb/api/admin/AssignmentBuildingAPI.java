package com.javaweb.api.admin;

import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.impl.AssignmentBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assign/building")
public class AssignmentBuildingAPI {

    @Autowired
    private AssignmentBuildingService assignmentBuildingService;

    @PutMapping
    public ResponseEntity<?> updateAssignment(@RequestBody AssignmentBuildingDTO assignmentBuildingDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (assignmentBuildingDTO.getBuildingId() == null) {
            responseDTO.setMessage("Validate failed");
            return ResponseEntity.badRequest().body(responseDTO);
        }
        assignmentBuildingService.updateAssignment(assignmentBuildingDTO);
        responseDTO.setMessage("Assignment building update successfully");
        return ResponseEntity.ok().body(responseDTO);
    }
}
