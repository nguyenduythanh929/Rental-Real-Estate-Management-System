package com.javaweb.converter;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.enums.District;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.response.BuildingSearchResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuildingConverter {

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public BuildingEntity toBuildingEntity(BuildingDTO buildingDTO) {
        BuildingEntity buildingEntityOld = null;
        if (buildingDTO.getId() != null) {
            buildingEntityOld = entityManager.find(BuildingEntity.class, buildingDTO.getId());
        }
        BuildingEntity buildingEntityNew = modelMapper.map(buildingDTO, BuildingEntity.class);
        String types = String.join(",", buildingDTO.getTypeCode());
        buildingEntityNew.setType(types);
        if (buildingEntityOld != null) {
            buildingEntityNew.setRentAreaEntities(buildingEntityOld.getRentAreaEntities());
            buildingEntityNew.setUsers(buildingEntityOld.getUsers());
            buildingEntityNew.setCreatedDate(buildingEntityOld.getCreatedDate());
            buildingEntityNew.setCreatedBy(buildingEntityOld.getCreatedBy());
        }
        return buildingEntityNew;
    }

    public BuildingSearchResponse toBuildingSearchResponse(BuildingEntity buildingEntity) {
        BuildingSearchResponse buildingSearchResponse = modelMapper.map(buildingEntity, BuildingSearchResponse.class);
        buildingSearchResponse.setAddress(buildingEntity.getStreet() + "," + buildingEntity.getWard() + ","
                + District.valueOf(buildingEntity.getDistrict()).getDistrictName());
        List<RentAreaEntity> rentAreaEntities = buildingEntity.getRentAreaEntities();
        buildingSearchResponse.setRentArea(rentAreaEntities.stream().map(i -> i.getValue().toString()).collect(Collectors.joining(",")));
        return buildingSearchResponse;
    }

    public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
        BuildingDTO buildingDTO = modelMapper.map(buildingEntity, BuildingDTO.class);
        String rentAreas = buildingEntity.getRentAreaEntities().stream().map(i -> i.getValue().toString()).collect(Collectors.joining(","));
        buildingDTO.setRentArea(rentAreas);
        List<String> typeCodes = Arrays.asList(buildingEntity.getType().split(","));
        buildingDTO.setTypeCode(typeCodes);
        return buildingDTO;
    }
}
