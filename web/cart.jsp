<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-slate-300">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
              integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="./css/styleCart.css">
    </head>
    <body>
        <jsp:useBean id="cd" class="dal.CategoryDAO" />
        <div class="root bg-slate-300 relative pb-16">
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
                                    <ul
                                        class="user-menu hidden absolute top-16 w-36 text-black shadow-2xl bg-white">
                                        <li class="px-5 py-3 cursor-pointer hover:bg-slate-700 hover:text-white">
                                            Profile
                                        </li>
                                        <c:if test="${sessionScope.user.getRole() == 1}">
                                            <li class="px-5 py-3 cursor-pointer hover:bg-slate-700 hover:text-white" onclick="goManagement()">
                                                Management
                                            </li>                                            
                                        </c:if>
                                        <c:if test="${sessionScope.user.getRole() == 1}">
                                            <li class="px-5 py-3 cursor-pointer hover:bg-slate-700 hover:text-white" onclick="goChart()">
                                                Chart
                                            </li>                                            
                                        </c:if>
                                        <c:if test="${sessionScope.user.getRole() == 2}">
                                            <li class="px-5 py-3 cursor-pointer hover:bg-slate-700 hover:text-white" onclick="goOrder()">
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
                        <ul class="flex justify-between items-center h-full text-slate-300">
                            <li><a href="product.jsp"
                                   class="hover:text-slate-100 relative after:absolute after:bottom-0 after:left-0 after:bg-slate-50 after:h-0.5 after:w-0 hover:after:w-full after:transition-all after:ease-in-out after:duration-300 uppercase">Products</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <form action="orderprocess" method="get">
                <div class="mt-16 mb-12 pt-12 pl-20 flex">
                    <h1 class="text-5xl font-medium">Total Price: $ 
                        <c:if test="${sessionScope.cart != null && sessionScope.cart.getTotalPrice() != 0}">
                            <fmt:formatNumber type="number" pattern=".##" value="${sessionScope.cart.getTotalPrice()}" />
                        </c:if>
                        <c:if test="${sessionScope.cart == null || sessionScope.cart.getTotalPrice() == 0}">0,0</c:if>
                        </h1>
                    <c:if test="${sessionScope.user != null && sessionScope.cart.getList().size() > 0}">
                        <input type="submit" class="bg-blue-600 hover:bg-blue-500 hover:text-black ml-7 flex justify-center cursor-pointer items-center text-white px-5 rounded-lg" value="Check out"/>                    
                    </c:if>
                    <c:if test="${requestScope.orderSuccess != null}">
                        <h1 class="text-4xl font-medium leading-relaxed ml-12">${requestScope.orderSuccess}<a href="order.jsp" class="underline text-blue-600">View Order</a></h1>
                    </c:if>
                    <c:if test="${requestScope.orderFailt != null}">
                        <h1 class="text-4xl font-medium leading-relaxed ml-12">${requestScope.orderFailt}</h1>
                    </c:if>
                </div>

                <jsp:useBean id="sd" class="dal.ShipperDAO"/>
                <jsp:useBean id="ud" class="dal.UserDAO"/>
                <div class="chose-info ml-48">
                    <div class="chose-info-shipper w-10/12">
                        <select name="shipperId" class="w-72 my-3 h-7">
                            <option value="-1">Chose one Shipper</option>
                            <c:forEach items="${sd.all}" var="s">
                                <option value="${s.getId()}" ${requestScope.shId == s.getId() ? "selected" : ""} >${s.getName()} (${s.getShipTime()}) --- ${s.getShipPrice()}$ </option>
                            </c:forEach>
                        </select>
                        <c:if test="${requestScope.emptyShipper != null}">
                            <span class="text-red-500 ml-4 font-medium">${requestScope.emptyShipper}</span>
                        </c:if>
                    </div>
                    <div class="chose-info-address w-10/12">
                        <select name="addressId" class="w-72 my-3 h-7">
                            <option value="-1">Chose one Address</option>
                            <c:forEach items="${ud.getAddressByUserId(sessionScope.user.getUserId())}" var="ad">
                                <option value="${ad.getId()}" ${requestScope.addId == ad.getId() ? "selected" : ""} >${ad.getAddressLine()}</option>
                            </c:forEach>
                        </select>
                        <c:if test="${requestScope.emptyAddress != null}">
                            <span class="text-red-500 ml-4 font-medium">${requestScope.emptyAddress}</span>
                        </c:if>
                    </div>
                </div>
            </form>

            <c:if test="${requestScope.quantityMessage != null}">
                <div class="message my-3 text-center text-lg font-medium">
                    <p class="text-red-500">${requestScope.quantityMessage}</p>
                </div>
            </c:if>

            <div class="order-table flex justify-center mb-16">
                <table class="table-fixed w-10/12 rounded-t-lg overflow-hidden">
                    <thead class="bg-amber-100 border-b-2 border-gray-200">
                        <tr>
                            <th class="p-3 text-lg font-semibold">Name</th>
                            <th class="p-3 text-lg font-semibold">Category</th>
                            <th class="p-3 text-lg font-semibold">Price</th>
                            <th class="p-3 text-lg font-semibold">Quantity</th>
                            <th class="p-3 text-lg font-semibold">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    <form name="f" action="" method="post">
                        <c:set var="step" value="0"/>
                        <c:forEach items="${sessionScope.cart.getList()}" var="i">
                            <c:set var="step" value="${step+1}"/>
                            <fmt:parseNumber var="num" type="number" value="${step}"/>
                            <tr class="${num % 2 == 0 ? 'bg-gray-200' : 'bg-gray-100'}">
                                <td class="text-center p-3">${i.getProduct().getName()}</td>
                                <td class="text-center p-3">${cd.getNameById(i.getProduct().getCatId())}</td>
                                <td class="text-center p-3">
                                    <fmt:formatNumber type="number" pattern=".##" value="${i.getPrice()}" />
                                </td>
                                <td class="text-center p-3 flex">
                                    <button class="text-white bg-blue-600 hover:bg-blue-500 px-3 rounded" onclick="updateQuantity(${i.getProduct().getId()}, -1)">-</button>
                                    <input type="text" readonly value="${i.getQuantity()}" class="border-2 rounded border-gray-400 mx-2 pl-3 w-7/12"/>
                                    <button class="text-white bg-blue-600 hover:bg-blue-500 px-3 rounded" onclick="updateQuantity(${i.getProduct().getId()}, 1)">+</button>
                                </td>
                                <td class="text-center p-3">
                                    <button class="text-white bg-red-600 hover:bg-red-500 px-3 py-1 rounded" onclick="doDelete(${i.getProduct().getId()})"><a href="#">Delete</a></button>
                                </td>
                            </tr>
                        </c:forEach>
                    </form>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
<script type="text/javascript" src="/Project1/js/header.js"></script>
<script type="text/javascript" src="/Project1/js/cart.js"></script>
