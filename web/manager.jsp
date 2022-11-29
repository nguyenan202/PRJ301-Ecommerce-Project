<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="bg-slate-300">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Managment</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
              integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.22/css/dataTables.bootstrap4.min.css">
        <script src="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.22/js/dataTables.bootstrap4.min.js"></script>
        <style>
            .element:hover .icon {
                display: inline;
            }

            .pagination {
                display:flex;
            }

            .previous {
                border-top-left-radius: 10%;
                border-bottom-left-radius: 10%;
            }
            .next {
                border-top-right-radius: 10%;
                border-bottom-right-radius: 10%;
            }

            .paginate_button {
                border: 1px solid gray;
                padding: 4px 12px;
                font-size: 18px;
                cursor: pointer;
            }

            .active {
                background-color: #007bff;
                color: #fff;
            }

            #sortTable_filter{
                width: 50vw;
                font-size: 20px;
            }

            #sortTable_filter input {
                padding: 4px 8px;
                outline: none;
                border-radius: 5px;
            }
        </style>
    </head>
    <body class="h-min-screen">
        <jsp:useBean id="od" class="dal.OrderDAO"/>

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
                            <li><a href="product.jsp"
                                   class="hover:text-slate-100 relative after:absolute after:bottom-0 after:left-0 after:bg-slate-50 after:h-0.5 after:w-0 hover:after:w-full after:transition-all after:ease-in-out after:duration-300 uppercase">Products</a>
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
                        <a href="cart.jsp" class="h-full">
                            <img src="./image/cart.png" alt="cart" class="h-full" />
                        </a>
                        <span class="text-slate-100 mx-2">CART</span>
                        <span class="text-slate-100 rounded-xl bg-orange-700 px-2 py-1">${sessionScope.cart != null ? sessionScope.cart.getList().size() : 0}</span>
                    </div>
                </div>
            </div>

            <div class="order-table mb-16 mt-40">
                <div class="flex justify-center">
                    <table id="sortTable" class="table table-striped table-bordered table-fixed w-10/12 rounded-t-lg overflow-hidden" style="width: 80vw;">
                        <thead class="bg-amber-100 border-b-2 border-gray-200">
                            <tr>
                                <th class="p-3 text-lg font-semibold th-sm">
                                    ID
                                </th>
                                <th class="p-3 text-lg font-semibold th-sm">
                                    User
                                </th>
                                <th class="p-3 text-lg font-semibold th-sm">
                                    Order Date
                                </th>
                                <th class="p-3 text-lg font-semibold th-sm">
                                    Total Money
                                </th>
                                <th class="p-3 text-lg font-semibold th-sm">Status</th>
                                <th class="p-3 text-lg font-semibold th-sm">Action</th>
                            </tr>
                        </thead>
                        <tbody>

                            <c:forEach items="${requestScope.data == null ? od.allOrderForAdmin : requestScope.data}" var="order">
                                <tr class="bg-gray-200">
                                    <td class="text-center p-3">${order.getOrderId()}</td>
                                    <td class="text-center p-3">${order.getUserName()}</td>
                                    <td class="text-center p-3">
                                        ${order.getDate()}
                                    </td>
                                    <td class="text-center p-3">
                                        ${order.getTotalPrice()}$
                                    </td>
                                    <td class="text-center p-3">
                                        <c:if test="${order.getStatus() == -2}">
                                            <button type="button" class="text-white bg-red-600 px-3 py-1 rounded cursor-default">Canceled</button>
                                        </c:if>
                                        <c:if test="${order.getStatus() == -1}">
                                            <button type="button" class="text-white bg-red-600 px-3 py-1 rounded cursor-default">Rejected</button>
                                        </c:if>
                                        <c:if test="${order.getStatus() == 0}">
                                            <button type="button" class="text-gray-100 bg-gray-400 px-3 py-1 rounded cursor-default">Pending</button>
                                        </c:if>
                                        <c:if test="${order.getStatus() == 1}">
                                            <button type="button" class="text-white bg-green-500 px-3 py-1 rounded cursor-default">Accepted</button>
                                        </c:if>
                                            <c:if test="${order.getStatus() == 2}">
                                            <button type="button" class="text-white bg-green-300 px-3 py-1 rounded cursor-default">Delivering</button>
                                        </c:if>
                                    </td>
                                    <td class="text-center p-3 flex justify-evenly">
                                        <c:if test="${order.getStatus() != -2}">
                                            <a href="orderaction?orderId=${order.getOrderId()}&action=1" class="bg-green-500 text-gray-100 py-1 px-2 text-center rounded">
                                                <i class="fa-solid fa-check"></i>
                                            </a>
                                            <a href="orderaction?orderId=${order.getOrderId()}&action=2" class="bg-green-300 text-gray-100 py-1 px-2 text-center rounded">
                                                <i class="fa-solid fa-check"></i>
                                            </a>
                                            <a href="orderaction?orderId=${order.getOrderId()}&action=-1" class="bg-red-600 text-gray-100 py-1 px-2 text-center rounded">
                                                <i class="fa-solid fa-x"></i>
                                            </a>
                                        </c:if>
                                        <button type="button" class="text-white bg-blue-700 px-3 py-1 rounded"><a href="orderdetail?orderid=${order.getOrderId()}">View</a></button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
<script type="text/javascript" src="/Project1/js/header.js"></script>
<script>
                                                $('#sortTable').DataTable();
</script>