<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url var="buildingAPI" value="/api/buildings"/>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: DUY THANH
  Date: 02/05/2025
  Time: 2:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thông tin khách hàng</title>
</head>
<body>
<div class="main-content" style="font-family: 'Times New Roman', Times, serif;">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">

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
                    Thông tin khách hàng
                    <small>
                        <i class="ace-icon fa fa-angle-double-right"></i>
                        overview &amp; stats
                    </small>
                </h1>
            </div>
            <!-- /.page-header -->
        </div>

        <div class="row">
            <div class="col-xs-12">
                <form:form class="form-horizontal" role="form" id="form-edit" action="/admin/customer-edit" method="GET" modelAttribute="customerEdit">
                    <div class="form-group">
                        <label class="col-xs-3 control-label">Tên khách hàng</label>
                        <div class="col-xs-9">
                            <form:input class="form-control" path="fullName" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label">Số điện thoại</label>
                        <div class="col-xs-9">
                            <form:input class="form-control" path="phone" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label">Email</label>
                        <div class="col-xs-9">
                            <form:input class="form-control" path="email" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label">Tên công ty</label>
                        <div class="col-xs-9">
                            <form:input class="form-control" path="companyName" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label">Nhu cầu</label>
                        <div class="col-xs-9">
                            <form:input class="form-control" path="demand" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label">Trạng thái</label>
                        <div class="col-xs-2">
                            <form:select path="status" class="form-control">
                                <form:options items="${status}"/>
                            </form:select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-3 control-label"></label>
                        <div class="col-xs-9">
                            <c:if test="${not empty customerEdit.id}">
                                <button type="button" class="btn btn-warning" id="btnAddCustomer">Cập nhật thông tin</button>
                            </c:if>
                            <c:if test="${empty customerEdit.id}">
                                <button type="button" class="btn btn-primary" id="btnAddCustomer">Thêm khách hàng</button>
                            </c:if>
                            <a href="/admin/customer-list">
                                <button type="button" class="btn btn-danger">Hủy thao tác</button>
                            </a>
                        </div>
                    </div>
                    <input type="hidden" id="id" value="${customerEdit.id}"></input>
                </form:form>

                <c:if test="${not empty customerEdit.id}">
                    <c:forEach var="item" items="${transaction}">
                        <div class="col-xs-12">
                            <h2 class="smaller lighter blue">
                                ${item.value}
                                <div class="pull-right">
                                    <button type="button" class="btn btn-primary" onclick="addTransaction('${item.key}',${customerEdit.id})">Thêm giao dịch</button>
                                </div>
                            </h2>
                            <div class="hr hr-16 dotted hr-dotted"></div>
                        </div>
                        <c:if test="${item.key == 'CSKH'}">
                            <div class="row">
                                <div class="col-xs-12">
                                    <table id="CSKHList" class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>Ngày tạo</th>
                                            <th>Người tạo</th>
                                            <th>Ngày sửa</th>
                                            <th>Người sửa</th>
                                            <th>Chi tiết giao dịch</th>
                                            <th>Thao tác</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach var="transaction" items="${CSKH}">
                                            <tr>
                                                <td>${transaction.createdDate}</td>
                                                <td>${transaction.createdBy}</td>
                                                <td >${transaction.modifiedDate}</td>
                                                <td>${transaction.modifiedBy}</td>
                                                <td>${transaction.note}</td>
                                                <td>
                                                    <div class="hidden-sm hidden-xs btn-group">

                                                        <button type="button" class="btn btn-xs btn-info" onclick="updateTransaction('${item.key}', ${customerEdit.id},${transaction.id},'${transaction.note}')" title="Sửa thông tin">
                                                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                                                        </button>

                                                        <security:authorize access="hasRole('MANAGER')">
                                                        <button type="button" class="btn btn-xs btn-danger" onclick="deleteTransaction(${transaction.id}, ${customerEdit.id})" title="Xóa giao dịch">
                                                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                        </button>
                                                        </security:authorize>
                                                    </div>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${item.key == 'DDX'}">
                            <div class="row">
                                <div class="col-xs-12">
                                    <table id="DDXList" class="table table-striped table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>Ngày tạo</th>
                                            <th>Người tạo</th>
                                            <th>Ngày sửa</th>
                                            <th>Người sửa</th>
                                            <th>Chi tiết giao dịch</th>
                                            <th>Thao tác</th>
                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach var="transaction" items="${DDX}">
                                            <tr>
                                                <td>${transaction.createdDate}</td>
                                                <td>${transaction.createdBy}</td>
                                                <td >${transaction.modifiedDate}</td>
                                                <td>${transaction.modifiedBy}</td>
                                                <td>${transaction.note}</td>
                                                <td>
                                                    <div class="hidden-sm hidden-xs btn-group">

                                                        <button type="button" class="btn btn-xs btn-info" onclick="updateTransaction('${item.key}', ${customerEdit.id},${transaction.id}, '${transaction.note}')" title="Sửa thông tin">
                                                            <i class="ace-icon fa fa-pencil bigger-120"></i>
                                                        </button>
                                                        <security:authorize access="hasRole('MANAGER')">
                                                        <button type="button" class="btn btn-xs btn-danger" onclick="deleteTransaction(${transaction.id}, ${customerEdit.id})" title="Xóa giao dịch">
                                                            <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                                        </button>
                                                        </security:authorize>
                                                    </div>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div><!-- /.span -->
                            </div>
                        </c:if>
                    </c:forEach>
                </c:if>
            </div>
        </div>
        <!-- /.page-content -->

        <div class="modal fade" id="AddOrUpdateTransactionModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true"
             style="font-family: 'Times New Roman', Times, serif;">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Nhập thông tin giao dịch</h5>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="col-sm-3 control-label">Chi tiết giao dịch</label>
                            <div class="col-sm-9">
                                <input type="text" id="note" class="form-control" placeholder="Nhập chi tiết giao dịch">
                            </div>
                        </div>
                        <input type="hidden" id="code" value="">
                        <input type="hidden" id="customerId" value="">
                        <input type="hidden" id="transactionId" value="">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" id="btnCancelModal">Hủy</button>
                        <button type="button" class="btn btn-primary" id="btnAddOrUpdateTransaction">Xác nhận</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- /.main-content -->

<script>
    function addTransaction(code, customerId){
        $('#AddOrUpdateTransactionModal').modal();
        $('#note').val('');
        $('#code').val(code);
        $('#customerId').val(customerId);
    }

    function updateTransaction(code, customerId, transactionId, note){
        $('#AddOrUpdateTransactionModal').modal();
        $('#code').val(code);
        $('#customerId').val(customerId);
        $('#transactionId').val(transactionId);
        $('#note').val(note);
    }

    $('#btnAddOrUpdateTransaction').click(function (e){
        e.preventDefault();
        var data = {};
        data['id'] = $('#transactionId').val();
        data['code'] = $('#code').val();
        data['note'] = $('#note').val();
        data['customerId'] = $('#customerId').val();
        if(data['note'] != ''){
            AddOrUpdateTransaction(data);
        }
        else{
            alert("Vui lòng điền nội dung");
        }
    });

    function AddOrUpdateTransaction(data){
        $.ajax({
            type : "POST",
            url : "/api/transactions",
            data : JSON.stringify(data),
            dataType : "json",
            contentType : "application/json",
            success : function(response){
                alert(response.message);
                window.location.href = "/admin/customer-edit-" + data['customerId'];
            },
            error : function(response){
                let json = JSON.parse(response.responseText);
                alert(json.message);
            }
        });
    }

    function deleteTransaction(id, customerId){
        $.ajax({
            type : "DELETE",
            url : "/api/transactions/" + id,
            dataType : "json",
            success : function(response){
                alert(response.message);
                window.location.href = "/admin/customer-edit-" + customerId;
            },
            error : function(response){
                let json = JSON.parse(response.responseText);
                alert(json.message);
            }
        });
    }

    var ok = 1;
    function validateDataCustomer(json){
        $('.error-message').html('');
        if(json['fullName'] === ''){
            ok = 0;
            $('#fullName').after('<span class="error-message" style="color: red">Tên không được trống</span>');
        }
        if(json['phone'] === ''){
            ok = 0;
            $('#phone').after('<span class="error-message" style="color: red">Số điện thoại không được trống</span>');
        }
        if(json['demand'] === ''){
            ok = 0;
            $('#demand').after('<span class="error-message" style="color: red">Yêu cầu không được trống</span>');
        }
    }
    $('#btnAddCustomer').click(function(){
        var formData = $('#form-edit').serializeArray();
        var json = {};
        $.each(formData, function(i, it){
            json["" + it.name + ""] = it.value;
        });
        json['id'] = $('#id').val();
        ok = 1;
        validateDataCustomer(json);
        if(ok == 0){
            alert('Failed');
        }
        else{
            if(json['id'] === ''){
                addCustomer(json);
            }
            else{
                updateCustomer(json);
            }
        }
    });

    function addCustomer(json){
        $.ajax({
            type : "POST",
            url : "/api/customers",
            data : JSON.stringify(json),
            dataType : "json",
            contentType: "application/json",
            success : function(response){
                alert(response.message);
                window.location.href = "/admin/customer-list";
            },
            error : function(response){
                let json = JSON.parse(response.responseText);
                alert(json.message);
            }
        });
    }

    function updateCustomer(json){
        $.ajax({
            type : "PUT",
            url : "/api/customers",
            data : JSON.stringify(json),
            dataType : "json",
            contentType: "application/json",
            success : function(response){
                alert(response.message);
                window.location.href = "/admin/customer-list";
            },
            error : function(response){
                let json = JSON.parse(response.responseText);
                alert(json.message);
            }
        });
    }

    $('#btnCancelModal').click(function (e){
        e.preventDefault();
        $('#AddOrUpdateTransactionModal').modal('hide');
    });
</script>
</body>
</html>
