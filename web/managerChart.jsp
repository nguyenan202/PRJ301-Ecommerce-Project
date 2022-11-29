<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="bg-slate-300">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chart</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
              integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <script src="https://cdn.jsdelivr.net/npm/chart.js@3.9.1/dist/chart.min.js"></script>
        <style>
            .chart {
                max-height: 500px;
            }
        </style>
    </head>

    <body class="h-min-screen pb-16">
        <div class="h-16 bg-zinc-900 w-screen py-3 px-8 fixed z-50 top-0" id="header">
            <div class="nav flex h-full justify-between">
                <div class="logo basis-2/6 flex justify-center items-center"><span
                        class="block text-slate-100 text-3xl">GamingGear</span></div>
                <div class="menu basis-3/6">
                    <ul class="flex justify-between items-center h-full text-slate-300">
                        <li><a href="home.jsp"
                               class="hover:text-slate-100 relative after:absolute after:bottom-0 after:left-0 after:bg-slate-50 after:h-0.5 after:w-0 hover:after:w-full after:transition-all after:ease-in-out after:duration-300 uppercase">Home</a>
                        </li>
                        <c:if test="${sessionScope.user == null}">
                            <li><a href="login.jsp"
                                   class="hover:text-slate-100 relative after:absolute after:bottom-0 after:left-0 after:bg-slate-50 after:h-0.5 after:w-0 hover:after:w-full after:transition-all after:ease-in-out after:duration-300">Login</a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.user != null}">
                            <li class="user">
                                <a class="cursor-pointer">Hello ${sessionScope.user.getFirstName()} <img
                                        src="./image/arrowDown.png" class="inline h-4" /></a>
                                <ul class="user-menu hidden absolute top-16 w-36 text-black shadow-2xl bg-white">
                                    <li class="px-5 py-3 cursor-pointer hover:bg-slate-700 hover:text-white">
                                        Profile
                                    </li>
                                    <c:if test="${sessionScope.user.getRole() == 1}">
                                        <li class="px-5 py-3 cursor-pointer hover:bg-slate-700 hover:text-white"
                                            onclick="goManagement()">
                                            Management
                                        </li>
                                    </c:if>
                                    <c:if test="${sessionScope.user.getRole() == 1}">
                                        <li class="px-5 py-3 cursor-pointer hover:bg-slate-700 hover:text-white"
                                            onclick="goChart()">
                                            Chart
                                        </li>
                                    </c:if>
                                    <c:if test="${sessionScope.user.getRole() == 2}">
                                        <li class="px-5 py-3 cursor-pointer hover:bg-slate-700 hover:text-white"
                                            onclick="goOrder()">
                                            View Orders
                                        </li>
                                    </c:if>
                                    <li class="px-5 py-3 cursor-pointer hover:bg-slate-700 hover:text-white">
                                        Logout
                                    </li>
                                </ul>
                            </li>
                        </c:if>
                    </ul>
                </div>
                <div class="cart flex basis-1/6 ml-16 flex items-center">
                    <a href="cart.jsp" class="h-full">
                        <img src="./image/cart.png" alt="cart" class="h-full" />
                    </a>
                    <span class="text-slate-100 mx-2">CART</span>
                    <span class="text-slate-100 rounded-xl bg-orange-700 px-2 py-1">${sessionScope.cart != null ?
                                                                                      sessionScope.cart.getList().size() : 0}</span>
                </div>
            </div>
        </div>
        <div class="component">
            <div class="mt-24 mb-8 text-center text-7xl">
                <h1>Income per month($)</h1>
            </div>
            <div class="w-9/12 h-3/4 mx-auto bg-gray-200 flex justify-center items-center">
                <canvas id="my-chart-order" class="chart"></canvas>
            </div>
        </div>
        <div class="component">
            <div class="mt-24 mb-8 text-center text-7xl">
                <h1>Top Customers</h1>
            </div>
            <div class="w-9/12 h-3/4 mx-auto bg-gray-200 flex justify-center items-center">
                <canvas id="my-chart-customer" class="chart"></canvas>
            </div>
        </div>
        <div class="component">
            <div class="mt-24 mb-8 text-center text-7xl">
                <h1>Best Seller</h1>
            </div>
            <div class="w-9/12 h-3/4 mx-auto bg-gray-200 flex justify-center items-center">
                <canvas id="my-chart-product" class="chart"></canvas>
            </div>
        </div>
    </body>

</html>
<script type="text/javascript" src="/Project1/js/header.js"></script>
<script type="text/javascript" src="/Project1/js/chart.js"></script>