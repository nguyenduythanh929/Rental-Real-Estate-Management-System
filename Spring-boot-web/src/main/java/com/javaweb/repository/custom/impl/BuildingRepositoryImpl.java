package com.javaweb.repository.custom.impl;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.repository.custom.BuildingRepositoryCustom;
import com.javaweb.utils.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<BuildingEntity> findAllBuildings(BuildingSearchRequest buildingSearchRequest, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        StringBuilder join = new StringBuilder();
        StringBuilder where = new StringBuilder(" WHERE 1=1");
        StringBuilder page = new StringBuilder();
        buildJoins(join, buildingSearchRequest);
        buildWhere(where, buildingSearchRequest);
        buildPage(page, pageable);
        sql.append("SELECT DISTINCT b.* FROM building b")
                .append(join).append(where)
                .append(" ORDER BY b.createddate DESC")
                .append(page);
        Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
        List<BuildingEntity> buildings = query.getResultList();
        long totalElements = countTotalItem(buildingSearchRequest);
        return new PageImpl<>(buildings, pageable, totalElements);
    }

    @Override
    public int countTotalItem(BuildingSearchRequest buildingSearchRequest) {
        String sql = buildQueryFilter(buildingSearchRequest);
        Query query = entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        if (result instanceof Number) {
            return ((Number) result).intValue();
        }
        return 0;
    }

    private String buildQueryFilter(BuildingSearchRequest buildingSearchRequest) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT b.id) FROM building b");
        StringBuilder join = new StringBuilder();
        StringBuilder where = new StringBuilder(" WHERE 1=1");
        buildJoins(join, buildingSearchRequest);
        buildWhere(where, buildingSearchRequest);
        sql.append(join).append(where);
        return sql.toString();
    }

    private void buildJoins(StringBuilder join, BuildingSearchRequest buildingSearchRequest) {
        if (buildingSearchRequest.getStaffId() != null) {
            join.append(" JOIN assignmentbuilding ass ON b.id = ass.buildingid");
        }
        if (buildingSearchRequest.getAreaFrom() != null || buildingSearchRequest.getAreaTo() != null) {
            join.append(" JOIN rentarea r ON b.id = r.buildingid");
        }
    }

    private void buildWhere(StringBuilder where, BuildingSearchRequest buildingSearchRequest) {
        try {
            Field[] fields = BuildingSearchRequest.class.getDeclaredFields();
            for (Field item : fields) {
                item.setAccessible(true);
                String key = item.getName();
                if (!key.equals("staffId") && !key.equals("typeCode") && !key.startsWith("area")
                        && !key.startsWith("rentPrice")) {
                    Object value = item.get(buildingSearchRequest);
                    if (value != null && !value.toString().isEmpty()) {
                        if (NumberUtils.isLong(value.toString())) {
                            where.append(" AND b." + key + " = " + value);
                        } else {
                            where.append(" AND b." + key + " LIKE '%" + value + "%'");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Long staffId = buildingSearchRequest.getStaffId();
        if (staffId != null) {
            where.append(" AND ass.staffid = " + staffId);
        }
        Long areaFrom = buildingSearchRequest.getAreaFrom();
        Long areaTo = buildingSearchRequest.getAreaTo();
        if (areaFrom != null) {
            where.append(" AND r.value >= " + areaFrom);
        }
        if (areaTo != null) {
            where.append(" AND r.value <= " + areaTo);
        }
        List<String> typeCode = buildingSearchRequest.getTypeCode();
        if (typeCode != null && !typeCode.isEmpty()) {
            where.append(" AND "
                    + typeCode.stream().map(i -> "b.type LIKE '%" + i + "%'").collect(Collectors.joining(" OR ")));
        }
        Long rentPriceFrom = buildingSearchRequest.getRentPriceFrom();
        Long rentPriceTo = buildingSearchRequest.getRentPriceTo();
        if (rentPriceFrom != null) {
            where.append(" AND b.rentprice >= " + rentPriceFrom);
        }
        if (rentPriceTo != null) {
            where.append(" AND b.rentprice <= " + rentPriceTo);
        }
    }

    private void buildPage(StringBuilder page, Pageable pageable) {
        page.append(" LIMIT ").append(pageable.getPageSize())
                .append(" OFFSET ").append(pageable.getOffset());
    }
}
