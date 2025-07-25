package com.javaweb.service.impl;

import com.javaweb.constant.SystemConstant;
import com.javaweb.converter.BuildingConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.service.IBuildingService;
import com.javaweb.utils.NumberUtils;
import com.javaweb.utils.UploadFileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BuildingService implements IBuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingConverter buildingConverter;

    private final UploadFileUtils uploadFileUtils;

    public BuildingService(UploadFileUtils uploadFileUtils) {
        this.uploadFileUtils = uploadFileUtils;
    }

    @Override
    public List<BuildingSearchResponse> findAllBuildings(BuildingSearchRequest buildingSearchRequest, Pageable pageable) {
        Page<BuildingEntity> buildings = buildingRepository.findAllBuildings(buildingSearchRequest, pageable);
        List<BuildingEntity> newBuildings = buildings.getContent();
        List<BuildingSearchResponse> buildingSearchResponses = new ArrayList<>();
        for (BuildingEntity buildingEntity : newBuildings) {
            BuildingSearchResponse buildingSearchResponse = buildingConverter.toBuildingSearchResponse(buildingEntity);
            buildingSearchResponses.add(buildingSearchResponse);
        }
        return buildingSearchResponses;
    }

    @Override
    public BuildingEntity createBuilding(BuildingDTO buildingDTO) throws Exception {
        BuildingEntity buildingExist = buildingRepository.findByName(buildingDTO.getName());
        if (buildingExist != null) {
            throw new Exception("Building Name Existed");
        }
        BuildingEntity buildingEntity = buildingConverter.toBuildingEntity(buildingDTO);
        String[] rentAreas = buildingDTO.getRentArea().split(",");
        for (String rentArea : rentAreas) {
            if (NumberUtils.isLong(rentArea)) {
                Long area = Long.parseLong(rentArea);
                RentAreaEntity rentAreaEntity = new RentAreaEntity();
                rentAreaEntity.setValue(area);
                rentAreaEntity.setBuildingEntity(buildingEntity);

                buildingEntity.getRentAreaEntities().add(rentAreaEntity);
            }
        }
        saveThumbnail(buildingDTO, buildingEntity);
        buildingRepository.save(buildingEntity);
        return buildingEntity;
    }

    @Override
    public BuildingDTO findBuildingById(Long id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found"));
        return buildingConverter.toBuildingDTO(buildingEntity);
    }

    @Override
    public BuildingEntity updateBuilding(BuildingDTO buildingDTO) throws Exception {
        BuildingEntity buildingExist = buildingRepository.findByName(buildingDTO.getName());
        if (buildingExist != null && !buildingExist.getId().equals(buildingDTO.getId())) {
            throw new Exception("Building Name Existed");
        }
        BuildingEntity buildingEntity = buildingConverter.toBuildingEntity(buildingDTO);
        buildingEntity.getRentAreaEntities().clear();

        String[] rentAreas = buildingDTO.getRentArea().split(",");
        for (String rentArea : rentAreas) {
            if (NumberUtils.isLong(rentArea)) {
                Long area = Long.parseLong(rentArea);
                RentAreaEntity rentAreaEntity = new RentAreaEntity();
                rentAreaEntity.setValue(area);
                rentAreaEntity.setBuildingEntity(buildingEntity);

                buildingEntity.getRentAreaEntities().add(rentAreaEntity);
            }
        }
        BuildingEntity foundBuilding = buildingRepository.findById(buildingDTO.getId())
                .orElseThrow(() -> new NotFoundException("Building not found!"));
        buildingEntity.setImage(foundBuilding.getImage());
        saveThumbnail(buildingDTO, buildingEntity);
        buildingRepository.save(buildingEntity);
        return buildingEntity;
    }

    @Override
    public String deleteBuildings(List<Long> ids) {
        buildingRepository.deleteByIdIn(ids);
        return SystemConstant.DELETE_SUCCESS;
    }

    @Override
    public int countTotalItem(BuildingSearchRequest buildingSearchRequest) {
        return buildingRepository.countTotalItem(buildingSearchRequest);
    }

    @Override
    public boolean checkAssignedStaff(Long id, Long staffId) {
        BuildingEntity building = buildingRepository.findById(id).orElseThrow(() -> new RuntimeException("Building not found"));
        return building.getUsers().stream().anyMatch(it -> Objects.equals(it.getId(), staffId));
    }

    private void saveThumbnail(BuildingDTO buildingDTO, BuildingEntity buildingEntity) {
        String path = "/building/" + buildingDTO.getImageName();
        if (null != buildingDTO.getImageBase64()) {
            if (null != buildingEntity.getImage()) {
                if (!path.equals(buildingEntity.getImage())) {
                    File file = new File("C://home/office" + buildingEntity.getImage());
                    file.delete();
                }
            }
            byte[] bytes = Base64.decodeBase64(buildingDTO.getImageBase64().getBytes());
            uploadFileUtils.writeOrUpdate(path, bytes);
            buildingEntity.setImage(path);
        }
    }
}
