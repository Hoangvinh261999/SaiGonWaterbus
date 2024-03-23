<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Đăng Nhập</title>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous">
</script><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous"><link type="text/css" rel="stylesheet" href="cartcss.css">
<script src="https://kit.fontawesome.com/d49588c4a5.js"
crossorigin="anonymous"></script>
</head>
<body>
    <section class="vh-100">
        <div class="container-fluid">
          <div class="row">
            <div class="col-sm-6 text-black ml-5">
      
              <div class="px-5 ms-xl-4 mt-5">
                <span class="h1 fw-bold mb-0">
                    <img src="/asm/img/admin/logo.png" alt="" style="width: 60%; height: 100%; border-radius: 5px;">
                  </span>
                                </div>
      
              <div class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-2 pt-5 pt-xl-0 mt-xl-n5">
      
                <form action="/asm/login" method="post" style="width: 36rem;">
          <% if (request.getParameter("error") != null && request.getParameter("error").equals("fail")) { %>
        <h2 style="color: red;">Sai tên đăng nhập hoặc mật khẩu!</h2>
    <% } %>
				<h3 class="mb-3 pb-3 text-center" style="font-size: xx-large;;letter-spacing: 1px; font-weight: bold; font-family: 'Pacifico', cursive; color: #3498db; text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);">Login</h3>
      
                  <div class="form-outline mb-4">
                                      <label class="form-label" for="form2Example18">Email address</label>     
                    <input type="email" name="username" id="form2Example18" placeholder="Email" class="form-control form-control-lg" />
                  </div>
                  <div class="form-outline mb-4">
                 <label class="form-label" for="form2Example28">Password</label>
                    <input type="password" name="password" placeholder="Password" id="form2Example28" class="form-control form-control-lg" required="required"/>
                  </div>
                  <div class="pt-1 text-center mt-3">
					<button type="submit" class="btn btn-primary" value="Log in">Login</button>
					<a href="/asm/forgotpass"><button type="button" class="btn btn-secondary">Forgot Pass</button></a>
                  </div>
                  <div class="text-center">
                    <p>or sign in with:</p>
                    <button type="button" class="btn btn-link btn-floating mx-1">
                      <i class="fab fa-facebook-f"></i> <a href="/asm/oauth2/authorization/facebook">Đăng nhập với facebook</a>  <br>
    
                    </button>
  
                    <button type="button" class="btn btn-link btn-floating mx-1">
                      <i class="fab fa-google"> </i><a href="/asm/oauth2/authorization/google">Đăng nhập với google</a>
                      
                    </button>

                  </div>
                  <p style="text-align: center;">Don't have an account? <a href="/asm/register" class="link-info">Register here</a></p>
      
                </form>
      
              </div>
      
            </div>
            <div class="col-sm-6 px-0 d-none d-sm-block">
              <img src="/asm/img/admin/IMG_8948.jpg"
                alt="Login image" class="w-100 vh-100" style="object-fit: cover; object-position: left;">
            </div>
          </div>
        </div>
      </section>
      
</body>
</html>