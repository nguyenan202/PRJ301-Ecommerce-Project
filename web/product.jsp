<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html class="relative">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Products</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
              integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="./css/styleProduct.css">
    </head>

    <body>
        <jsp:useBean id="c" class="dal.CategoryDAO" />
        <jsp:useBean id="s" class="dal.SupplierDAO" />
        <jsp:useBean id="p" class="dal.ProductDAO" />
        <div class="root h-full bg-slate-300 relative">
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
                        <a href="cart.jsp" class="h-full">
                            <img src="./image/cart.png" alt="cart" class="h-full" />
                        </a>
                        <span class="text-slate-100 mx-2">CART</span>
                        <span class="text-slate-100 rounded-xl bg-orange-700 px-2 py-1">${sessionScope.cart != null ? sessionScope.cart.getList().size() : 0}</span>
                    </div>
                </div>
            </div>
            <div id="scrool-to"></div>
            <div class="mt-16 mb-4 pt-12 px-20 flex justify-between">
                <h1 class="text-5xl font-medium">Product(Total: ${requestScope.totalProduct != null ? requestScope.totalProduct : p.numberProducts})</h1>
                <c:if test="${requestScope.msgDelete != null}" >
                    <h2 class="text-2xl flex items-end text-green-700">${requestScope.msgDelete}</h2>
                </c:if>
                <c:if test="${sessionScope.user.getRole() == 1}">
                        <a href="create" class="bg-violet-600 text-white rounded hover:bg-violet-500 flex justify-center items-center px-6">Add new product</a>
                </c:if>
            </div>
            <form action="filter" method="get">
                <div class="container pt-10 pb-10 pl-10  pr-10 flex">
                    <div class="left p-2 basis-3/12 border rounded border-gray-400 bg-slate-200 h-fit">
                        <h2 class="flex justify-center items-center text-xl p-2 border rounded border-gray-400">Filter
                        </h2>
                        <div class="category">
                            <h2 class="text-xl font-medium h-10 flex items-center">Search</h2>
                            <div class="feature-divider"></div>
                            <div class="search list ml-6 mr-6 mt-4 mb-4 flex">
                                <input type="text" name="search" value="${requestScope.nameSearch}" placeholder="Enter name to search"/>
                                <div class="bg-slate-300 flex justify-center items-center text-base w-12 cursor-pointer">
                                    <button type="submit">
                                        <i class="fa-solid fa-magnifying-glass"></i>
                                    </button>
                                </div>
                            </div>
                            <h2 class="text-xl font-medium h-10 flex items-center">Category</h2>
                            <div class="feature-divider"></div>
                            <div class="list ml-6 mr-6 mt-4 mb-4">
                                <p>
                                    <select name="category">
                                        <option value="-1" ${-1 == requestScope.catChose?'selected':''}>All</option>
                                        <c:forEach items="${c.all}" var="i">
                                            <option value="${i.getId()}" ${i.getId() == requestScope.catChose?'selected':''}>${i.getName()}</option>
                                        </c:forEach>
                                    </select>
                                </p>
                            </div>
                            <h2 class="text-xl font-medium h-10 flex items-center">Sort</h2>
                            <div class="feature-divider"></div>
                            <div class="list ml-6 mr-6 mt-4 mb-4">
                                <p>
                                    <select name="sort">
                                        <option value="-1">None</option>
                                        <option value="0" ${requestScope.sort == "0" ? 'selected' : ''}>Price Descending</option>
                                        <option value="1" ${requestScope.sort == "1" ? 'selected' : ''}>Price Ascending</option>
                                    </select>
                                </p>
                            </div>
                            <h2 class="text-xl font-medium h-10 flex items-center">Supplier</h2>
                            <div class="feature-divider"></div>
                            <div class="list ml-6 mt-4 mb-4">
                                <c:forEach items="${s.all}" var="i">
                                    <c:if test="${requestScope.supChose != null}">
                                        <p><input id="sup${i.getId()}" type="checkbox" name="supplier" value="${i.getId()}" ${s.checkSupplier(requestScope.supChose,i.getId()) ? 'checked' : ''}/> <label for="sup${i.getId()}">${i.getName()}</label>
                                        </p>
                                    </c:if>
                                    <c:if test="${requestScope.supChose == null}">
                                        <p><input id="sup${i.getId()}" type="checkbox" name="supplier" value="${i.getId()}"/><label for="sup${i.getId()}">${i.getName()}</label>
                                        </p>
                                    </c:if>
                                </c:forEach>
                            </div>
                            <h2 class="text-xl font-medium h-10 flex items-center">Price</h2>
                            <div class="feature-divider"></div>
                            <div class="filter-price ml-6 mr-6 mt-4 mb-4">
                                <div class="to flex justify-between">
                                    <div class="to-left w-2/5">
                                        <input type="number" class="w-full border rounded border-black"
                                               name="priceBegin" value="${requestScope.priceStartChose != null ? requestScope.priceStartChose : 0}" />
                                    </div>
                                    To
                                    <div class="to-right w-2/5">
                                        <input type="number" class="w-full border rounded border-black"
                                               name="priceEnd" value="${requestScope.priceEndChose != null ? requestScope.priceEndChose : 99999}" />
                                    </div>
                                </div>
                                <div
                                    class="filter-price-submit mt-3 bg-orange-700 py-2 rounded hover:bg-orange-400">
                                    <input type="submit" value="Filter"
                                           class="flex justify-center items-center w-full h-full cursor-pointer" />
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="right basis-9/12 relative">
                        <div class="page absolute">
                            <c:forEach begin="1" end="${requestScope.pageSize != null ? requestScope.pageSize : p.numberOfPageFirst}" var="i">
                                <span class="${(i==1 && requestScope.pageActive == null) || (i == requestScope.pageActive) ?'active':''}">
                                    <input type="submit" name="page" value="${i}" class="cursor-pointer"/>
                                </span>
                            </c:forEach>
                        </div>

                        <c:set var="search" value=""/>
                        <c:set var="categoryId" value="-1"/>
                        <c:set var="sortBy" value="-1"/>
                        <c:set var="supliers" value=""/>
                        <c:set var="priceS" value="0"/>
                        <c:set var="priceE" value="99999"/>
                        <c:set var="pageN" value="1"/>

                        <c:if test="${requestScope.nameSearch != null}">
                            <c:set var="search" value="${requestScope.nameSearch}"/>
                        </c:if>
                        <c:if test="${requestScope.catChose != null}">
                            <c:set var="categoryId" value="${requestScope.catChose}"/>
                        </c:if>
                        <c:if test="${requestScope.sort != null}">
                            <c:set var="sortBy" value="${requestScope.sort}"/>
                        </c:if>
                        <c:if test="${requestScope.supChoseString != null}">
                            <c:set var="supliers" value="${requestScope.supChoseString}"/>
                        </c:if>
                        <c:if test="${requestScope.priceStartChose != null}">
                            <c:set var="priceS" value="${requestScope.priceStartChose}"/>
                        </c:if>
                        <c:if test="${requestScope.priceEndChose != null}">
                            <c:set var="priceE" value="${requestScope.priceEndChose}"/>
                        </c:if>
                        <c:if test="${requestScope.pageActive != null}">
                            <c:set var="pageN" value="${requestScope.pageActive}"/>
                        </c:if>

                        <div class="products flex justify-evenly flex-wrap">
                            <jsp:useBean id="i" class="dal.ProductDAO"/>
                            <c:if test="${requestScope.data == null}">
                                <c:forEach items="${i.getProductByPage(i.all, 1)}" var="p">
                                    <div class="item w-56 mb-6">
                                        <div class="image h-56 overflow-hidden relative">
                                            <img src="${p.getImage()}"
                                                 alt="product" class="w-full h-56">
                                            <div class="order w-full h-10 flex justify-center items-center">
                                                <c:if test="${sessionScope.user.getRole() != 1}">
                                                    <p class="text-white inline">Add to cart?</p>
                                                    <c:if test="${p.getUnitInStock() == 0}">
                                                        <button class="cursor-pointer ml-2 py-1 px-6 bg-black rounded text-white" onclick="showNotEnought()">Add</button>
                                                    </c:if>
                                                    <c:if test="${p.getUnitInStock() != 0}">
                                                        <a href="cart?productId=${p.getId()}&quantity=1" class="cursor-pointer ml-2 py-1 px-6 bg-black rounded text-white">Add</a>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${sessionScope.user.getRole() == 1}">
                                                    <p class="text-white inline">Edit Product?</p>
                                                    <a href="updateproduct?id=${p.getId()}" class="cursor-pointer ml-2 py-1 px-6 bg-black rounded text-white edit-btn">Edit</a>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="info p-4 border-t-2 h-44 flex flex-col justify-between">
                                            <h2 class="text-lg font-medium">${p.getName()}</h2>
                                            <p>Unit in stock: ${p.getUnitInStock()}</p>
                                            <p>Price: 
                                                <span class="text-rose-600 font-bold">${p.getUnitPrice()}$</span>
                                            </p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${requestScope.data != null}">
                                <c:forEach items="${requestScope.data}" var="p">
                                    <div class="item w-56 mb-6">
                                        <div class="image h-56 overflow-hidden relative">
                                            <img src="${p.getImage()}"
                                                 alt="product" class="w-full h-56">
                                            <div class="order w-full h-10 flex justify-center items-center">
                                                <c:if test="${sessionScope.user.getRole() != 1}">
                                                    <p class="text-white inline">Add to cart?</p>
                                                    <c:if test="${p.getUnitInStock() == 0}">
                                                        <button class="cursor-pointer ml-2 py-1 px-6 bg-black rounded text-white" onclick="showNotEnought()">Add</button>
                                                    </c:if>
                                                    <c:if test="${p.getUnitInStock() != 0}">
                                                        <a href="cart?productId=${p.getId()}&quantity=1" class="cursor-pointer ml-2 py-1 px-6 bg-black rounded text-white">Add</a>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${sessionScope.user.getRole() == 1}">
                                                    <p class="text-white inline">Edit Product?</p>
                                                    <a href="updateproduct?id=${p.getId()}" class="cursor-pointer ml-2 py-1 px-6 bg-black rounded text-white edit-btn">Edit</a>
                                                </c:if>
                                            </div>
                                        </div>
                                        <div class="info p-4 border-t-2 h-44 flex flex-col justify-between">
                                            <h2 class="text-lg font-medium">${p.getName()}</h2>
                                            <p>Unit in stock: ${p.getUnitInStock()}</p>
                                            <p>Price: 
                                                <span class="text-rose-600 font-bold">${p.getUnitPrice()}$</span>
                                            </p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <c:if test="${requestScope.errorCart != null}">
                                <script>
                                    alert("${requestScope.errorCart}");
                                    window.location = 'product.jsp'
                                </script>
                            </c:if>
                        </div>
                    </div>
                </div>
            </form>
            <div class="scrool-top fixed bottom-10 right-10">
                <a href="#" type="button" class="btn bg-violet-500 text-white py-2 px-6 rounded hidden" onclick="gotoTop()">
                    Move to top
                </a>
            </div>
            <%@include file="footer.jsp" %>
        </div>
    </body>
</html>
<script type="text/javascript" src="/Project1/js/header.js"></script>
<script type="text/javascript" src="/Project1/js/product.js"></script>