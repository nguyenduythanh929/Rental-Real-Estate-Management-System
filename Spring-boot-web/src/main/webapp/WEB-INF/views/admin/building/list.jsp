<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: DUY THANH
  Date: 02/05/2025
  Time: 1:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sách tòa nhà</title>
</head>
<body>
<div class="main-content" style="font-family: 'Times New Roman', Times, serif;">
    <div class="main-content-inner">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check("breadcrumbs", "fixed");
                    } catch (e) {}
                </script>

                <ul class="breadcrumb">
                    <li>
                        <i class="ace-icon fa fa-home home-icon"></i>
                        <a href="#">Home</a>
                    </li>
                    <li class="active">Dashboard</li>
                </ul>
                <!-- /.breadcrumb -->
            </div>

            <div class="page-content">
                <div class="page-header">
                    <h1>
                        Danh sách tòa nhà
                        <small>
                            <i class="ace-icon fa fa-angle-double-right"></i>
                            overview &amp; stats
                        </small>
                    </h1>
                </div>
                <!-- /.page-header -->
            </div>
            <!-- /.page-content -->

            <div class="row">
                <div class="col-xs-12">
                    <div class="widget-box">
                        <div class="widget-header">
                            <h4 class="widget-title">Tìm kiếm</h4>

                            <span class="widget-toolbar">
                    <a href="/admin/building-list" data-action="reload">
                      <i class="ace-icon fa fa-refresh"></i>
                    </a>

                    <a href="#" data-action="collapse">
                      <i class="ace-icon fa fa-chevron-up"></i>
                    </a>

                    <a href="#" data-action="close">
                      <i class="ace-icon fa fa-times"></i>
                    </a>
                  </span>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main">
                                <form:form id="listForm" method="GET" action="/admin/building-list" modelAttribute="modelSearch">
                                    <div class="row">
                                        <div class="form-group">
                                            <div class="col-xs-12">
                                                <div class="col-xs-6">
                                                    <label> Tên tòa nhà </label>
                                                    <form:input class="form-control" path="name" />
                                                </div>
                                                <div class="col-xs-6">
                                                    <label> Diện tích sàn </label>
                                                    <form:input class="form-control" path="floorArea" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-xs-12">
                                                <div class="col-xs-2">
                                                    <label> Quận </label>
                                                    <form:select path="district" class="form-control">
                                                        <form:option value="">-- Chọn quận --</form:option>
                                                        <form:options items="${district}"/>
                                                    </form:select>
                                                </div>
                                                <div class="col-xs-5">
                                                    <label> Đường </label>
                                                    <form:input class="form-control" path="street" />
                                                </div>
                                                <div class="col-xs-5">
                                                    <label> Phường </label>
                                                    <form:input class="form-control" path="ward" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-xs-12">
                                                <div class="col-xs-4">
                                                    <label> Sô tầng hầm </label>
                                                    <form:input class="form-control" path="numberOfBasement" />
                                                </div>
                                                <div class="col-xs-4">
                                                    <label> Hướng </label>
                                                    <form:input class="form-control" path="direction" />
                                                </div>
                                                <div class="col-xs-4">
                                                    <label> Hạng </label>
                                                    <form:input class="form-control" path="level" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-xs-12">
                                                <div class="col-xs-3">
                                                    <label> Diện tích từ </label>
                                                    <form:input class="form-control" path="areaFrom" />
                                                </div>
                                                <div class="col-xs-3">
                                                    <label> Diện tích đến </label>
                                                    <form:input class="form-control" path="areaTo" />
                                                </div>
                                                <div class="col-xs-3">
                                                    <label> Giá thuê từ </label>
                                                    <form:input class="form-control" path="rentPriceFrom" />
                                                </div>
                                                <div class="col-xs-3">
                                                    <label> Giá thuê đến </label>
                                                    <form:input class="form-control" path="rentPriceTo" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-xs-12">
                                                <div class="col-xs-5">
                                                    <label> Tên quản lý </label>
                                                    <form:input class="form-control" path="managerName" />
                                                </div>
                                                <div class="col-xs-5">
                                                    <label> SĐT quản lý </label>
                                                    <form:input class="form-control" path="managerPhone" />
                                                </div>
                                                <security:authorize access="hasRole('MANAGER')">
                                                    <div class="col-xs-2">
                                                        <label> Chọn nhân viên </label>
                                                        <form:select path="staffId" class="form-control">
                                                            <form:option value="">--- Chọn nhân viên ---</form:option>
                                                            <form:options items="${staffs}"/>
                                                        </form:select>
                                                    </div>
                                                </security:authorize>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-xs-12">
                                                <div class="col-xs-5">
                                                    <form:checkboxes path="typeCode" items="${type}"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-xs-12">
                                                <div class="col-xs-6">
                                                    <button type="button" class="btn btn-primary btn-sm" id="btnSearchBuilding">
                                                        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                                        Tìm kiếm
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                        <!-- /.span -->
                    </div>
                    <div class="pull-right">
                        <a href="/admin/building-edit">
                            <button class="btn btn-app btn-success btn-xs" title="Thêm tòa nhà">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-building-add" viewBox="0 0 16 16">
                                    <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7m.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0"/>
                                    <path d="M2 1a1 1 0 0 1 1-1h10a1 1 0 0 1 1 1v6.5a.5.5 0 0 1-1 0V1H3v14h3v-2.5a.5.5 0 0 1 .5-.5H8v4H3a1 1 0 0 1-1-1z"/>
                                    <path d="M4.5 2a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm3 0a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm3 0a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm-6 3a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm3 0a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm3 0a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm-6 3a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm3 0a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5z"/>
                                </svg>
                            </button>
                        </a>
                        <button class="btn btn-app btn-danger btn-xs" title="Xóa tòa nhà" id="btnDeleteBuilding">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-building-dash" viewBox="0 0 16 16">
                                <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7M11 12h3a.5.5 0 0 1 0 1h-3a.5.5 0 0 1 0-1"/>
                                <path d="M2 1a1 1 0 0 1 1-1h10a1 1 0 0 1 1 1v6.5a.5.5 0 0 1-1 0V1H3v14h3v-2.5a.5.5 0 0 1 .5-.5H8v4H3a1 1 0 0 1-1-1z"/>
                                <path d="M4.5 2a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm3 0a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm3 0a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm-6 3a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm3 0a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm3 0a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm-6 3a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm3 0a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5z"/>
                            </svg>
                        </button>
                    </div>
                </div>
            </div>
            <div class="hr hr-20 hr-double"></div>
            <!-- Table list building -->
            <div class="row">
                <div class="col-xs-12">
                    <display:table name="model.listResult"
                                   requestURI="${formUrl}"
                                   partialList="true"
                                   sort="external"
                                   size="${model.totalItem}"
                                   pagesize="${model.maxPageItems}"
                                   id="tableList"
                                   class="table table-striped table-bordered table-hover">

                        <display:column title="<input type='checkbox' onclick='checkAll()'/>" class="center">
                            <input type="checkbox" value="${tableList.id}"/>
                        </display:column>

                        <display:column property="name" title="Tên tòa nhà"/>
                        <display:column property="address" title="Địa chỉ"/>
                        <display:column property="numberOfBasement" title="Số tầng hầm"/>
                        <display:column property="managerName" title="Tên quản lý"/>
                        <display:column property="managerPhone" title="SĐT quản lý"/>
                        <display:column property="floorArea" title="Diện tích sàn"/>
                        <display:column property="rentArea" title="Diện tích thuê"/>
                        <display:column property="rentPrice" title="Giá thuê"/>
                        <display:column property="serviceFee" title="Phí dịch vụ"/>
                        <display:column property="brokerageFee" title="Phí môi giới"/>

                        <display:column title="Thao tác">
                            <div class="btn-group">
                                <security:authorize access="hasRole('MANAGER')">
                                    <button class="btn btn-xs btn-success" onclick="assignmentBuilding(${tableList.id})" title="Giao tòa nhà">
                                        <i class="ace-icon fa fa-users fa-check bigger-120"></i>
                                    </button>
                                </security:authorize>
                                <security:authorize access="hasAnyRole('MANAGER','STAFF')">
                                <a href="/admin/building-edit-${tableList.id}" class="btn btn-xs btn-info" title="Sửa thông tin">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </a>
                                </security:authorize>
                                <button class="btn btn-xs btn-danger" onclick="deleteBuilding(${tableList.id})" title="Xóa tòa nhà">
                                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                </button>
                            </div>
                        </display:column>

                    </display:table>

                </div><!-- /.span -->
            </div>
        </div>
    <div class="modal fade" id="assignmentBuildingModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
         style="font-family: 'Times New Roman', Times, serif;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Danh sách nhân viên</h5>
                </div>
                <div class="modal-body">
                    <table id="staffList" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th class="center">
                                <label class="pos-rel">Chọn</label>
                            </th>
                            <th class="center">Tên nhân viên</th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td class="center">
                                <label class="pos-rel">
                                    <input type="checkbox" class="ace"  id="idBuilding" value="1">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <input type="hidden" id="buildingId" value="">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" id="btnCancelModal">Hủy thao tác</button>
                    <button type="button" class="btn btn-primary" id="btnAssignBuilding">Giao tòa nhà</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /.main-content -->
<script>
    function assignmentBuilding(buildingId){
        $('#assignmentBuildingModal').modal();
        $('#buildingId').val(buildingId);
        loadStaff(buildingId);
    }

    function loadStaff(buildingId){
        $.ajax({
            type : "GET",
            url : "/api/buildings/" + buildingId + "/staffs",
            dataType : "json",
            success : function(response){
                var row = '';
                $.each(response.data, function (index, item){
                    row += '<tr>';
                    row +=
                        '<td className="center"> <input type="checkbox" value=' + item.staffId + ' id="checkbox_' + item.staffId + '" class="center"' + item.checked + '/> </td>';
                    row += '<td class="center">' + item.fullName + '</td>';
                    row += '</tr>';
                });
                console.log(row);
                $('#staffList tbody').html(row);
            },
            error: function (response) {
                alert(response.responseJSON.data.join('\n'));
            }
        });
    }

    $('#btnAssignBuilding').click(function (e) {
        e.preventDefault();
        var json = {};
        json['buildingId'] = $('#buildingId').val();
        var staffIds = $('#staffList').find('tbody input[type=checkbox]:checked').map(function(){
            return $(this).val();
        }).get();
        json['staffs'] = staffIds;
        console.log('Success....');
        if(buildingId == ''){
            alert('Id Not Found');
        }
        else{
            assignBuildings(json);
        }
    });

    $('#btnDeleteBuilding').click(function(e){
        e.preventDefault();
        var buildingIds = $('#tableList').find('tbody input[type=checkbox]:checked').map(function(){
            return $(this).val();
        }).get();
        if(buildingIds == ''){
            alert('No Building Selected');
        }
        else{
            deleteBuildings(buildingIds);
        }
    });

    function deleteBuilding(id){
        if(id == ''){
            alert('Id Not Found');
        }
        else{
            deleteBuildings(id);
        }
    }

    function deleteBuildings(ids){
        $.ajax({
            type : "DELETE",
            url : "/api/buildings/" + ids,
            dataType : "json",
            success : function(response){
                alert(response.message);
                window.location.href = "/admin/building-list";
            },
            error : function(response){
                let json = JSON.parse(response.responseText);
                alert(json.message);
            }
        });
    }

    function assignBuildings(json){
        $.ajax({
            type : "PUT",
            url : "/api/assign/building",
            data : JSON.stringify(json),
            dataType : "json",
            contentType: "application/json",
            success : function(response){
                alert(response.message);
            },
            error : function(response){
                let json = JSON.parse(response.responseText);
                alert(json.message);
            }
        });
    }
    $('#btnSearchBuilding').click(function (e){
        e.preventDefault();
        $('#listForm').submit();
    });
    $('#btnCancelModal').click(function (e){
       e.preventDefault();
       $('#assignmentBuildingModal').modal('hide');
    });
</script>
</body>
</html>
