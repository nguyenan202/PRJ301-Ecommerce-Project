<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-slate-300">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Detail</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
              integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body class="h-min-screen">
        <jsp:useBean id="cd" class="dal.CategoryDAO" />

        <div class="root h-full bg-slate-300 relative pb-16">
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
                        <a href="cart.jsp" class="h-full">
                            <img src="./image/cart.png" alt="cart" class="h-full" />
                        </a>
                        <span class="text-slate-100 mx-2">CART</span>
                        <span class="text-slate-100 rounded-xl bg-orange-700 px-2 py-1">${sessionScope.cart != null ? sessionScope.cart.getList().size() : 0}</span>
                    </div>
                </div>
            </div>

            <div class="mt-16 mb-12 pt-12 pl-20 flex">
                <h1 class="text-5xl font-medium">Order Detail ID: ${requestScope.orderId}</h1>
                <c:if test="${requestScope.msg != null}">
                    <h3 class="text-2xl font-medium text-green-600 flex flex-col-reverse ml-12">${requestScope.msg}</h3>
                </c:if>
            </div>

            <div class="order-table flex justify-center mb-16">
                <table class="table-fixed w-10/12 rounded-t-lg overflow-hidden">
                    <thead class="bg-amber-100 border-b-2 border-gray-200">
                        <tr>
                            <th class="p-3 text-lg font-semibold">Name</th>
                            <th class="p-3 text-lg font-semibold">Category</th>
                            <th class="p-3 text-lg font-semibold">Quantity</th>
                            <th class="p-3 text-lg font-semibold">Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="step" value="0"/>
                        <c:forEach items="${requestScope.data}" var="i">
                            <c:set var="step" value="${step+1}"/>
                            <fmt:parseNumber var="num" type="number" value="${step}"/>
                            <tr class="${num % 2 == 0 ? 'bg-gray-200' : 'bg-gray-100'}">
                                <td class="text-center p-3">${i.getProduct().getName()}</td>
                                <td class="text-center p-3">${cd.getNameById(i.getProduct().getCatId())}</td>
                                <td class="text-center p-3">
                                    ${i.getQuantity()}
                                </td>
                                <td class="text-center p-3">
                                    <fmt:formatNumber type="number" pattern=".##" value="${i.getPrice()}" />
                                </td>
                            </tr>
                        </c:forEach>
                        <tr class="bg-gray-100 border-t-2 border-black">
                            <td class="text-center p-3"></td>
                            <td class="text-center p-3 text-2xl font-medium">Shipper</td>
                            <td class="text-center p-3 text-2xl font-medium">
                                ${requestScope.shipper}
                            </td>
                            <td class="text-center p-3 text-2xl font-medium">
                                ${requestScope.shipPrice}$
                            </td>
                        </tr>

                        <tr class="bg-gray-100 border-t-2 border-black">
                            <td class="text-center p-3"></td>
                            <td class="text-center p-3"></td>
                            <td class="text-center p-3 text-4xl font-medium">
                                Total
                            </td>
                            <td class="text-center p-3 text-4xl">
                                ${requestScope.totalMoney}$
                            </td>
                        </tr>
                        <c:if test="${(requestScope.status == 0 || requestScope.status == 2) && sessionScope.user.getRole() != 1}">
                            <tr class="bg-gray-100 border-t-2 border-black">
                                <td class="text-center p-3"></td>
                                <td class="text-center p-3"></td>
                                <td class="text-center p-3"></td>
                                <td class="text-center p-3 text-3xl">
                                    <button class="cancel-btn bg-red-600 px-6 py-3 rounded-lg hover:bg-red-500 ">Cancel Order</button>
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>

            <div class="form-reason flex justify-center items-center hidden">
                <form action="cancelorder" method="post">
                    <input type="hidden" name="oId" value="${requestScope.orderId}"/>
                    <h3 class="text-3xl font-medium mb-2">Reason cancel order?</h3>
                    <textarea class="input-reason w-96 h-44 outline-none p-2" name="reason" placeholder="type your reason here"></textarea>
                    <input type="submit" value="Save" class="cursor-pointer ml-4 bg-blue-600 px-4 py-1 hover:bg-blue-500"/>
                </form>
            </div>
            <c:if test="${requestScope.reason != null}">
                <div class="flex justify-center items-center">
                    <div>
                        <h3 class="text-3xl font-medium mb-2">Reason cancel order</h3>
                        <textarea readonly class="input-reason w-96 h-44 outline-none p-2" name="reason" placeholder="type your reason here">${requestScope.reason}</textarea>
                    </div>
                </div>
            </c:if>
        </div>
    </body>
</html>
<script type="text/javascript" src="/Project1/js/header.js"></script>
<script>
                                                const btn = document.querySelector('.cancel-btn')
                                                const formR = document.querySelector('.form-reason')
                                                const input = document.querySelector('.input-reason')

                                                btn.addEventListener('click', () => {
                                                    if (formR.style.display == '' || formR.style.display == 'none') {
                                                        formR.style.display = 'flex'
                                                        input.focus()
                                                        btn.innerText = 'Hide'
                                                    } else {
                                                        formR.style.display = 'none'
                                                        btn.innerText = 'Cancel Order'
                                                    }
                                                })


</script>