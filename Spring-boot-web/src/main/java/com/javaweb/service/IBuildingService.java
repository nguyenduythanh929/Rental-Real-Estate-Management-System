package com.javaweb.service;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBuildingService {
    List<BuildingSearchResponse> findAllBuildings(BuildingSearchRequest buildingSearchRequest, Pageable pageable);

    BuildingEntity createBuilding(BuildingDTO buildingDTO) throws Exception;

    BuildingDTO findBuildingById(Long id);

    BuildingEntity updateBuilding(BuildingDTO buildingDTO) throws Exception;

    String deleteBuildings(List<Long> ids);

    int countTotalItem(BuildingSearchRequest buildingSearchRequest);

    boolean checkAssignedStaff(Long id, Long staffId);
}
