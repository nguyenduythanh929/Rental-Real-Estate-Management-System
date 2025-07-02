<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
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
    <title>Danh sách khách hàng</title>
</head>
<body>
<div class="main-content" style="font-family: 'Times New Roman', Times, serif;">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try {
                    ace.settings.check("breadcrumbs", "fixed");
                } catch (e) {
                }
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
                    Danh sách khách hàng
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
                    <a href="/admin/customer-list" data-action="reload">
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
                            <form:form id="listForm" method="GET" action="/admin/customer-list"
                                       modelAttribute="modelSearch">
                                <div class="row">
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <div class="col-xs-6">
                                                <label> Tên khách hàng </label>
                                                <form:input class="form-control" path="fullName"/>
                                            </div>
                                            <div class="col-xs-6">
                                                <label> Người thêm </label>
                                                <form:input class="form-control" path="createdBy"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <div class="col-xs-6">
                                                <label> Di động </label>
                                                <form:input class="form-control" path="phone"/>
                                            </div>
                                            <div class="col-xs-6">
                                                <label> Email </label>
                                                <form:input class="form-control" path="email"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-xs-12">
                                            <div class="col-xs-6">
                                                <label> Trạng thái </label>
                                                <form:select path="status" class="form-control">
                                                    <form:option value="">-- Chọn tất cả trạng thái --</form:option>
                                                    <form:options items="${status}"/>
                                                </form:select>
                                            </div>
                                            <security:authorize access="hasRole('MANAGER')">
                                            <div class="col-xs-2">
                                                <label> Nhân viên </label>
                                                <form:select path="staffId" class="form-control">
                                                    <form:option value="">-- Chọn nhân viên --</form:option>
                                                    <form:options items="${staffs}"/>
                                                </form:select>
                                            </div>
                                            </security:authorize>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-xs-12">
                                        <div class="col-xs-6">
                                            <button type="button" class="btn btn-primary btn-sm" id="btnSearchCustomer">
                                                <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                                Tìm kiếm
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div>
        <div class="pull-right">
            <a href="/admin/customer-edit">
                <button class="btn btn-app btn-success btn-xs" title="Thêm khách hàng">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-person-fill-add" viewBox="0 0 16 16">
                        <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7m.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0m-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0"/>
                        <path d="M2 13c0 1 1 1 1 1h5.256A4.5 4.5 0 0 1 8 12.5a4.5 4.5 0 0 1 1.544-3.393Q8.844 9.002 8 9c-5 0-6 3-6 4"/>
                    </svg>
                </button>
            </a>
            <button class="btn btn-app btn-danger btn-xs" title="Xóa khách hàng" id="btnDeleteCustomer">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                     class="bi bi-person-fill-x" viewBox="0 0 16 16">
                    <path d="M11 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0m-9 8c0 1 1 1 1 1h5.256A4.5 4.5 0 0 1 8 12.5a4.5 4.5 0 0 1 1.544-3.393Q8.844 9.002 8 9c-5 0-6 3-6 4"/>
                    <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7m-.646-4.854.646.647.646-.647a.5.5 0 0 1 .708.708l-.647.646.647.646a.5.5 0 0 1-.708.708l-.646-.647-.646.647a.5.5 0 0 1-.708-.708l.647-.646-.647-.646a.5.5 0 0 1 .708-.708"/>
                </svg>
            </button>
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

            <display:column property="fullName" title="Tên khách hàng"/>
            <display:column property="phone" title="Di động"/>
            <display:column property="email" title="Email"/>
            <display:column property="demand" title="Nhu cầu"/>
            <display:column property="createdBy" title="Người thêm"/>
            <display:column property="createdDate" title="Ngày thêm"/>
            <display:column property="status" title="Tình trạng"/>

            <display:column title="Thao tác">
                <div class="btn-group">
                    <security:authorize access="hasRole('MANAGER')">
                        <button class="btn btn-xs btn-success" onclick="assignmentCustomer(${tableList.id})"
                                title="Giao khách hàng">
                            <i class="ace-icon fa fa-users fa-check bigger-120"></i>
                        </button>
                    </security:authorize>
                    <a href="/admin/customer-edit-${tableList.id}" class="btn btn-xs btn-info" title="Sửa thông tin">
                        <i class="ace-icon fa fa-pencil bigger-120"></i>
                    </a>
                    <button class="btn btn-xs btn-danger" onclick="deleteCustomer(${tableList.id})" title="Xóa khách hàng">
                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                    </button>
                </div>
            </display:column>

        </display:table>

    </div><!-- /.span -->
</div>

    <div class="modal fade" id="assignmentCustomerModal" tabindex="-1" aria-labelledby="exampleModalLabel"
        aria-hidden="true" style="font-family: 'Times New Roman', Times, serif;">
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
                                    <input type="checkbox" class="ace" id="idCustomer" value="1">
                                    <span class="lbl"></span>
                                </label>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <input type="hidden" id="customerId" value="">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" id="btnCancelModal">Hủy thao tác</button>
                    <button type="button" class="btn btn-primary" id="btnAssignCustomer">Giao tòa nhà</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /.main-content -->
<script>
    function assignmentCustomer(customerId) {
        $('#assignmentCustomerModal').modal();
        $('#customerId').val(customerId);
        loadStaff(customerId);
    }

    function loadStaff(customerId) {
        $.ajax({
            type: "GET",
            url: "/api/customers/" + customerId + "/staffs",
            dataType: "json",
            success: function (response) {
                var row = '';
                $.each(response.data, function (index, item) {
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

    $('#btnAssignCustomer').click(function (e) {
        e.preventDefault();
        var json = {};
        json['customerId'] = $('#customerId').val();
        var staffIds = $('#staffList').find('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        json['staffs'] = staffIds;
        console.log('Success....');
        if (customerId == '') {
            alert('Id Not Found');
        } else {
            assignCustomers(json);
        }
    });

    $('#btnDeleteCustomer').click(function (e) {
        e.preventDefault();
        var customerIds = $('#tableList').find('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        if (customerIds == '') {
            alert('No Customer Selected');
        } else {
            deleteCustomers(customerIds);
        }
    });

    function deleteCustomer(id) {
        if (id == '') {
            alert('Id Not Found');
        } else {
            deleteCustomers(id);
        }
    }

    function deleteCustomers(ids) {
        $.ajax({
            type: "DELETE",
            url: "/api/customers/" + ids,
            dataType: "json",
            success: function (response) {
                alert(response.message);
                window.location.href = "/admin/customer-list";
            },
            error: function (response) {
                let json = JSON.parse(response.responseText);
                alert(json.message);
            }
        });
    }

    function assignCustomers(json) {
        $.ajax({
            type: "PUT",
            url: "/api/assign/customer",
            data: JSON.stringify(json),
            dataType: "json",
            contentType: "application/json",
            success: function (response) {
                alert(response.message);
            },
            error: function (response) {
                let json = JSON.parse(response.responseText);
                alert(json.message);
            }
        });
    }

    $('#btnSearchCustomer').click(function (e) {
        e.preventDefault();
        $('#listForm').submit();
    });
    $('#btnCancelModal').click(function (e) {
        e.preventDefault();
        $('#assignmentCustomerModal').modal('hide');
    });
</script>
</body>
</html>
