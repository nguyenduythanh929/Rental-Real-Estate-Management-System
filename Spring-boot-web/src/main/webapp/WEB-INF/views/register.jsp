<%--
  Created by IntelliJ IDEA.
  User: DUY THANH
  Date: 17/06/2025
  Time: 5:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Đăng ký</title>
</head>
<body>
<div class="container">
    <form id="register-form" action="/register" method="post">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-12 col-md-8 col-lg-6 col-xl-5">
            <div
                    class="card text-white"
                    style="border-radius: 1rem; background-color: #35bf76"
            >
                <div class="card-body p-2 px-5 text-center">
                    <div class="md-5 md-4 mt-4 pb-2">
                        <h2 class="fw-bold mb-2 text-uppercase">Create an account</h2>
                        <p class="text-white-50 mb-2">Please enter your Information</p>

                        <div class="form-outline form-white mb-2">
                            <label class="form-label" for="fullName">Fullname</label>
                            <input
                                    type="text"
                                    id="fullName"
                                    class="form-control form-control-lg"
                            />
                        </div>

                        <div class="form-outline form-white mb-2">
                            <label class="form-label" for="userName">Username</label>
                            <input
                                    type="text"
                                    id="userName"
                                    class="form-control form-control-lg"
                            />
                        </div>

                        <div class="form-outline form-white mb-2">
                            <label class="form-label" for="password">Password</label>
                            <input
                                    type="password"
                                    id="password"
                                    class="form-control form-control-lg"
                            />
                        </div>

                        <div class="form-outline form-white mb-2">
                            <label class="form-label" for="repeatPassword"
                            >Repeat your password</label
                            >
                            <input
                                    type="password"
                                    id="repeatPassword"
                                    class="form-control form-control-lg"
                            />
                        </div>

                        <div class="form-check mb-2">
                            <input
                                    class="form-check-input"
                                    type="checkbox"
                                    value=""
                                    id="form2Example3cg"
                                    style="margin-top: 0.3em;"
                            />
                            <label class="form-check-label" for="form2Example3cg">
                                I agree all statements in
                                <a
                                        href="#!"
                                        class="text-decoration-underline text-white fw-bold"
                                >
                                    Terms of service
                                </a>
                            </label>
                        </div>

                        <button class="btn btn-outline-light btn-lg px-5" type="submit" id="btnRegister">
                            Register
                        </button>

                        <div
                                class="d-flex justify-content-center text-center mt-2 pt-1"
                        >
                            <a href="#!" class="login-extension text-white"
                            ><i class="fab fa-facebook-f fa-lg"></i
                            ></a>
                            <a href="#!" class="login-extension text-white"
                            ><i class="fab fa-twitter fa-lg mx-4 px-2"></i
                            ></a>
                            <a href="#!" class="login-extension text-white"
                            ><i class="fab fa-google fa-lg"></i
                            ></a>
                        </div>

                        <p class="text-center text-muted mt-2 mb-0">
                            Have already an account?
                            <a href="/login" class="fw-bold text-body"
                            ><u style="color: white">Login here</u></a
                            >
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </form>
</div>
<script>
    var ok = 1;
    function validateDataBuilding(json){
        $('.error-message').html('');
        if(json['fullName'] === ''){
            ok = 0;
            $('#fullName').after('<span class="error-message" style="color: red">Họ và tên không được trống</span>');
        }
        if(json['userName'] === ''){
            ok = 0;
            $('#userName').after('<span class="error-message" style="color: red">User Name không được trống</span>');
        }
        if(json['password'] === ''){
            ok = 0;
            $('#password').after('<span class="error-message" style="color: red">Password không được trống</span>');
        }
        if(json['repeatPassword'] === ''){
            ok = 0;
            $('#repeatPassword').after('<span class="error-message" style="color: red">Vui lòng nhập lại password</span>');
        }
    }


    $('#btnRegister').click(function (e){
        e.preventDefault();
        var json = {};
        json['fullName'] = $('#fullName').val();
        json['userName'] = $('#userName').val();
        json['password'] = $('#password').val();
        json['repeatPassword'] = $('#repeatPassword').val();
        ok = 1;
        if(json['password'] !== json['repeatPassword']){
            ok = 0;
            alert('Password nhập không khớp. Vui lòng nhập lại');
        }
        validateDataBuilding(json);
        if(ok === 0){
            alert('Validated Failed');
        }
        else{
            registerUser(json);
        }
    });

    function registerUser(json){
        $.ajax({
            type : "POST",
            url : "/api/user/register",
            data : JSON.stringify(json),
            dataType : "json",
            contentType : "application/json",
            success : function(response){
                alert(response.message);
            },
            error : function(response){
                let json = JSON.parse(response.responseText);
                alert(json.message);
            }
        });
    }
</script>
</body>
</html>
