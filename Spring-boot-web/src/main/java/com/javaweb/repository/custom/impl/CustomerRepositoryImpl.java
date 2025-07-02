package com.javaweb.repository.custom.impl;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.enums.Status;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.repository.custom.CustomerRepositoryCustom;
import com.javaweb.utils.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<CustomerEntity> fillAll(CustomerSearchRequest customerSearchRequest, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        StringBuilder join = new StringBuilder();
        StringBuilder where = new StringBuilder(" WHERE 1=1");
        StringBuilder page = new StringBuilder();
        buildJoins(join, customerSearchRequest);
        buildWhere(where, customerSearchRequest);
        buildPage(page, pageable);
        sql.append("SELECT DISTINCT c.* FROM customer c")
                .append(join).append(where)
                .append(" ORDER BY c.createddate DESC")
                .append(page);
        Query query = entityManager.createNativeQuery(sql.toString(), CustomerEntity.class);
        List<CustomerEntity> customers = query.getResultList();
        long totalElements = countTotalItem(customerSearchRequest);
        return new PageImpl<>(customers, pageable, totalElements);
    }

    public void buildJoins(StringBuilder join, CustomerSearchRequest customerSearchRequest){
        if (customerSearchRequest.getStaffId() != null) {
            join.append(" JOIN assignmentcustomer ass ON c.id = ass.customerid");
        }
    }

    public void buildWhere(StringBuilder where, CustomerSearchRequest customerSearchRequest){
        try{
            Field[] fields = CustomerSearchRequest.class.getDeclaredFields();
            for(Field field : fields){
                field.setAccessible(true);
                String key = field.getName();
                if(!key.equals("staffId") && !key.equals("status")){
                    Object value = field.get(customerSearchRequest);
                    if(StringUtils.check(value.toString())){
                        where.append(" AND c." + key + " LIKE '%" + value + "%'");
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Long staffId = customerSearchRequest.getStaffId();
        if(staffId != null){
            where.append(" AND ass.staffid = " + staffId);
        }
        String status = customerSearchRequest.getStatus();
        if(StringUtils.check(status)){
            String status1 = Status.valueOf(status).getStatusName();
            where.append(" AND c.status LIKE '%" + status1 + "%'");
        }
        where.append(" AND c.is_active = 1");
    }

    public void buildPage(StringBuilder page, Pageable pageable){
        page.append(" LIMIT ").append(pageable.getPageSize())
                .append(" OFFSET ").append(pageable.getOffset());
    }

    @Override
    public int countTotalItem(CustomerSearchRequest customerSearchRequest) {
        String sql = buildQueryFilter(customerSearchRequest);
        Query query = entityManager.createNativeQuery(sql);
        Object result = query.getSingleResult();
        if (result instanceof Number) {
            return ((Number) result).intValue();
        }
        return 0;
    }

    private String buildQueryFilter(CustomerSearchRequest customerSearchRequest) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT c.id) FROM customer c");
        StringBuilder join = new StringBuilder();
        StringBuilder where = new StringBuilder(" WHERE 1=1");
        buildJoins(join, customerSearchRequest);
        buildWhere(where, customerSearchRequest);
        sql.append(join).append(where);
        return sql.toString();
    }
}
