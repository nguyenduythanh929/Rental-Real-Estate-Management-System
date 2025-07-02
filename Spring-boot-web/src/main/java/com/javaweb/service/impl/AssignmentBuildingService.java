package com.javaweb.service.impl;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.IAssignmentBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AssignmentBuildingService implements IAssignmentBuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void updateAssignment(AssignmentBuildingDTO assignmentBuildingDTO) {
        List<UserEntity> users = userRepository.findByIdIn(assignmentBuildingDTO.getStaffs());
        BuildingEntity building = buildingRepository.findById(assignmentBuildingDTO.getBuildingId()).get();
        building.setUsers(users);
        buildingRepository.save(building);
    }
}

