<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up</title>
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
        <div class="flex items-center justify-center h-full w-full container py-12">
            <form action="signup" method="post">
                <div class="bg-white w-96 p-6 rounded shadow-sm">
                    <div class="flex items-center justify-center mb-4">
                        <img src="./image/logo.png" alt="logo" class="h-32">
                    </div>
                    <h1 class="text-5xl font-semibold mb-6 text-orange-600">Sign Up</h1>
                    <div class="flex">
                        <div class="input">
                            <span class="block text-base font-medium text-slate-700">First Name</span>
                            <input type="text" name="firstname" placeholder="Enter first name" class="w-11/12 p-2" value="${param.firstname}"/>
                            <c:if test="${requestScope.emptyFName != null}">
                                <span class="block text-red-500">${requestScope.emptyFName}(*)</span>
                            </c:if>
                        </div>
                        <div class="input">
                            <span class="block text-base font-medium text-slate-700">Last Name</span>
                            <input type="text" name="lastname" placeholder="Enter last name" class="w-full p-2" value="${param.lastname}"/>
                            <c:if test="${requestScope.emptyLName != null}">
                                <span class="block text-red-500">${requestScope.emptyLName}(*)</span>
                            </c:if>
                        </div>
                    </div>
                    <div class="input my-3">
                        <span class="block text-base font-medium text-slate-700">Username</span>
                        <input type="text" name="username" placeholder="Enter user name" class="w-full p-2" value="${param.username}"/>
                        <c:if test="${requestScope.emptyUsername != null}">
                            <span class="block text-red-500">${requestScope.emptyUsername}(*)</span>
                        </c:if>
                        <c:if test="${requestScope.userExistMessage != null}">
                            <span class="block text-red-500">${requestScope.userExistMessage}(*)</span>
                        </c:if>
                    </div>
                    <div class="input my-3">
                        <span class="block text-base font-medium text-slate-700">Password</span>
                        <input type="password" name="password" placeholder="Enter password" class="w-full p-2" />
                        <c:if test="${requestScope.emptyPass != null}">
                            <span class="block text-red-500">${requestScope.emptyPass}(*)</span>
                        </c:if>
                    </div>
                    <div class="input my-3">
                        <span class="block text-base font-medium text-slate-700">Confirm Password</span>
                        <input type="password" name="passwordconfirm" placeholder="Re-Enter password" class="w-full p-2" />
                        <c:if test="${requestScope.emptyRePass != null}">
                            <span class="block text-red-500">${requestScope.emptyRePass}(*)</span>
                        </c:if>
                        <c:if test="${requestScope.samePasswordMessage != null}">
                            <span class="block text-red-500"> ${requestScope.samePasswordMessage}(*)</span>
                        </c:if>
                    </div>
                    <div class="input my-3">
                        <span class="block text-base font-medium text-slate-700">Email</span>
                        <input type="email" name="email" placeholder="Enter email" class="w-full p-2" value="${param.email}"/>
                        <c:if test="${requestScope.emptyEmail != null}">
                            <span class="block text-red-500">${requestScope.emptyEmail}(*)</span>
                        </c:if>
                    </div>
                    <div class="input my-3">
                        <span class="block text-base font-medium text-slate-700">Phone</span>
                        <input type="text" name="phone" placeholder="Enter phone number" class="w-full p-2" value="${param.phone}"/>
                        <c:if test="${requestScope.emptyPhone != null}">
                            <span class="block text-red-500">${requestScope.emptyPhone}(*)</span>
                        </c:if>
                        <c:if test="${requestScope.phoneMessage != null}">
                            <span class="block text-red-500">${requestScope.phoneMessage}(*)</span>
                        </c:if>
                    </div>
                    <div class="login-btn flex justify-center my-6">
                        <input
                            class="bg-orange-500 hover:bg-orange-600 active:bg-orange-700 w-28 p-2 rounded-xl cursor-pointer"
                            type="submit" value="Create" />
                    </div>
                        <c:if test="${requestScope.creatUserMessage != null}">
                            <span class="block text-red-500">${requestScope.creatUserMessage}(!)</span>
                        </c:if>
                    <div class="sign-up flex justify-center items-center">
                        <span class="text-sm text-slate-700">Already have an account ? </span>
                        <a href="login.jsp" class="text-orange-500 ml-px">Sign in</a>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>
