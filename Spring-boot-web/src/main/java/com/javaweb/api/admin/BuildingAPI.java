package com.javaweb.api.admin;

import com.javaweb.constant.SystemConstant;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.security.utils.SecurityUtils;
import com.javaweb.service.IBuildingService;
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
@RequestMapping("/api/buildings")
public class BuildingAPI {

    @Autowired
    private IBuildingService buildingService;

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<?> createBuilding(@Valid @RequestBody BuildingDTO buildingDTO, BindingResult bindingResult) throws Exception{
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
        BuildingEntity buildingEntity = buildingService.createBuilding(buildingDTO);
        responseDTO.setData(buildingEntity);
        responseDTO.setMessage("Completed");
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping
    public ResponseEntity<?> updateBuilding(@Valid @RequestBody BuildingDTO buildingDTO, BindingResult bindingResult) throws Exception{
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
            if(!buildingService.checkAssignedStaff(buildingDTO.getId(), staffId)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền sửa tòa nhà này");
            }
        }
        BuildingEntity buildingEntity = buildingService.updateBuilding(buildingDTO);
        responseDTO.setData(buildingEntity.getName());
        responseDTO.setMessage("Completed");
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/{id}/staffs")
    public ResponseEntity<?> loadStaffs(@PathVariable Long id) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<UserEntity> staffList = userService.getStaffList();
        List<UserEntity> assignedStaffs = userService.getAssignedBuildingStaffs(id);
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
    public ResponseEntity<?> deleteBuildings(@PathVariable List<Long> ids) {
        ResponseDTO responseDTO = new ResponseDTO();
        if (ids == null) {
            responseDTO.setMessage("No Building To Delete");
            return ResponseEntity.badRequest().body(responseDTO);
        }
        if(SecurityUtils.getAuthorities().contains(SystemConstant.ADMIN_ROLE)){
            Long staffId = SecurityUtils.getPrincipal().getId();
            for(Long id : ids){
                if(!buildingService.checkAssignedStaff(id, staffId)){
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền xóa tòa nhà này");
                }
            }
        }
        String result = buildingService.deleteBuildings(ids);
        responseDTO.setMessage(result);
        return ResponseEntity.ok().body(responseDTO);
    }
}
