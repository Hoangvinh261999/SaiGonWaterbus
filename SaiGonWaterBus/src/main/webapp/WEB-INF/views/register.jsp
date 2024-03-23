<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous">
    </script><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous"><link type="text/css" rel="stylesheet" href="cartcss.css">
    <script src="https://kit.fontawesome.com/d49588c4a5.js"
    crossorigin="anonymous"></script>
</head>
<body>

    <section class="vh-100 bg-image"
        style="background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp');">
        <div class="mask d-flex align-items-center h-100 gradient-custom-3">
            <div class="container h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                        <div class="card" style="border-radius: 15px;">
                            <div class="card-body p-5">
                                <h2 class="text-uppercase text-center mb-5">Đăng ký tài khoản</h2>
								<h5>${message}</h5>
                                <form:form action="/asm/register" method="post" modelAttribute="user" id="registrationForm">
    <div class="form-outline mb-4">
        <label class="form-label" for="hoTen">Họ tên</label>
        <form:input path="hoTen" id="hoTen" class="form-control form-control-lg" required="required"/>
    </div>

    <div class="form-outline mb-4">
        <label class="form-label" for="password">Mật khẩu</label>
        <form:password path="password" id="password" class="form-control form-control-lg" required="required"/>
    </div>

    <div class="form-outline mb-4">
        <label class="form-label" for="confirmPassword">Xác nhận mật khẩu</label>
        <input type="password" id="confirmPassword" class="form-control form-control-lg" required="required">
    </div>

    <div class="form-outline mb-4">
        <label class="form-label">Email</label>
        <form:input path="email" id="email" class="form-control form-control-lg" required="required"/>
    </div>

    <div class="form-outline mb-4">
        <button formaction="/asm/getcode"id="getConfirmationCode" class="btn btn-light btn-block btn-lg gradient-custom-4 text-body w-100" style="display: grid;">
            <label class="form-label" for="form3Example4cdg">Lấy mã xác nhận gmail</label>
        </button>
        <div id="countdownDisplay"></div>
        <input placeholder="Nhập mã xác thực" type="text" id="codegmail" name="codegmail" class="form-control form-control-lg mt-3" required="required">
    </div>

    <div class="form-check d-flex justify-content-center mb-5">
    
		<input type="checkbox" id="form2Example3cg" class="form-check-input me-2" required="required">
        <label class="form-check-label" for="form2Example3g"> 
            I agree all statements in <a href="#!" class="text-body"><u>Terms of service</u></a>
        </label>
    </div>

    <div class="d-flex justify-content-center">
        <button type="submit" class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">Register</button>
    </div>

    <p class="text-center text-muted mt-5 mb-0">Have already an account? <a href="/asm/login" class="fw-bold text-body"><u>Login here</u></a></p>
</form:form>


                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
<script>
var firstClick = true; // Biến để kiểm tra lần ấn đầu tiên
var countdownExpired = false; // Biến để kiểm tra xem đã hết thời gian chưa

document.getElementById("getConfirmationCode").addEventListener("click", function(event) {
    if (!countdownExpired) { // Kiểm tra nếu chưa hết thời gian
        if (firstClick) {
            var email = document.getElementById("email").value; // Lấy giá trị email được nhập
            document.getElementById("getConfirmationCode").disabled = true;

            // Kiểm tra giá trị user.authSDt
            var authSDt = "${user.authSdt}";
            if (authSDt == null || authSDt === "") {
                // Thực hiện lấy mã xác thực
                var xhr = new XMLHttpRequest();
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4) {
                        if (xhr.status === 200) {
                            startCountdown();
                            alert("Email gửi thành công!");
                        } else {
                            var errorResponse = JSON.parse(xhr.responseText);
                            alert("Lỗi: " + errorResponse.error);
                            countdownExpired = false;
                            firstClick = true;
                            document.getElementById("getConfirmationCode").disabled = false; // Cho phép ấn lại nút "Lấy mã xác thực"
                        }
                    }
                };
                xhr.open("POST", "/asm/getcode", true);
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.send("to=" + email);
            } else {
               
                document.getElementById("codegmail").value = ''; 
            }

            firstClick = false;
        }
    }
});


// Hàm đếm ngược
function startCountdown() {
    var countdown = 5; // Số giây cho đếm ngược, tức là 3 phút
    var timer = setInterval(function() {
        countdown--; // Giảm số giây còn lại
        var minutes = Math.floor(countdown / 60);
        var seconds = countdown % 60;
        document.getElementById("countdownDisplay").innerHTML = "Nhận mã xác thực sau: " + minutes + " phút " + seconds + " giây";
        if (countdown <= 0) {
            clearInterval(timer); // Dừng đếm ngược khi hết thời gian
            document.getElementById("countdownDisplay").innerHTML = " ";
            document.getElementById("getConfirmationCode").disabled = false; // Cho phép ấn lại nút "Lấy mã xác thực"
            countdownExpired = false; // Đặt lại biến countdownExpired
            firstClick = true;
        }
    }, 1000); 
}

</script>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        document.getElementById("registrationForm").addEventListener("submit", function(event) {
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;

            if (password !== confirmPassword) {
                event.preventDefault(); // Ngăn form submit đi
                alert("Xác nhận mật khẩu không khớp với mật khẩu đã nhập. Vui lòng thử lại.");
            }
        });
    });
</script>



</body>
</html>