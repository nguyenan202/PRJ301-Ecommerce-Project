<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <style>
            .input input {
                background-color: #FFF6F4;
            }

            .container {
                background: radial-gradient(circle, rgba(238,174,202,1) 0%, rgba(148,187,233,1) 100%);
            }
        </style>
    </head>

    <body>
        <div class="flex items-center justify-center h-screen w-screen container">
            <form action="login" method="post">
                <div class="bg-white w-96 p-6 rounded shadow-sm">
                    <div class="flex items-center justify-center mb-4">
                        <img src="./image/logo.png" alt="logo" class="h-32">
                    </div>
                    <label class="text-sm text-slate-700">Welcome back!!!</label>
                    <h1 class="text-5xl font-semibold mb-6 text-orange-600">Sign in</h1>
                    <div class="input my-3">
                        <span class="block text-base font-medium text-slate-700">Username</span>
                        <input type="text" name="username" placeholder="Enter user name" class="w-full p-2" value="${requestScope.usernameExistMessage == null?param.username:''}"/>
                    </div>
                    <c:if test="${requestScope.usernameExistMessage != null}">
                        <span class="block text-red-500">${requestScope.usernameExistMessage}</span>
                    </c:if>
                    <div class="input my-3">
                        <span class="block text-base font-medium text-slate-700">Password</span>
                        <input type="password" name="password" placeholder="Enter password" class="w-full p-2" />
                    </div>
                    <c:if test="${requestScope.passwordMessage != null}">
                        <span class="block text-red-500">${requestScope.passwordMessage}</span>
                    </c:if>
                    <div class="login-btn flex justify-center my-6">
                        <input
                            class="bg-orange-500 hover:bg-orange-600 active:bg-orange-700 w-28 p-2 rounded-xl cursor-pointer"
                            type="submit" value="Login" />
                    </div>
                    <c:if test="${requestScope.creatUserMessage != null}" >
                        <span class="block text-green-600">${requestScope.creatUserMessage}</span>
                    </c:if>
                    <div class="sign-up flex justify-center items-center">
                        <span class="text-sm text-slate-700">I don't have an account ? </span>
                        <a href="signup.jsp" class="text-orange-500 ml-px">Sign up</a>
                    </div>
                    <div class="sign-up flex justify-center items-center">
                        <span class="text-sm text-slate-700">Forgot password ? </span>
                        <a href="forgotPassword.jsp" class="text-orange-500 ml-px">Reset</a>
                    </div>
                </div>
            </form>
        </div>
    </body>

</html>