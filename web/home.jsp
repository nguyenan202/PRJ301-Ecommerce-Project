<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Home</title>
            <script src="https://cdn.tailwindcss.com"></script>
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
                integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
                crossorigin="anonymous" referrerpolicy="no-referrer" />
            <link rel="stylesheet" href="./css/styleHome.css">
        </head>

        <body>
            <div class="root">
                <div class="h-16 bg-zinc-900 w-screen py-3 px-8 fixed z-50 top-0" id="header">
                    <div class="nav flex h-full justify-between">
                        <div class="logo basis-2/6 flex justify-center items-center"><span
                                class="block text-slate-100 text-3xl">GamingGear</span></div>
                        <div class="menu basis-3/6">
                            <ul class="flex justify-between items-center h-full text-slate-300">
                                <li><a href="#"
                                        class="hover:text-slate-100 relative after:absolute after:bottom-0 after:left-0 after:bg-slate-50 after:h-0.5 after:w-0 hover:after:w-full after:transition-all after:ease-in-out after:duration-300 uppercase">Home</a>
                                </li>
                                <li><a href="product.jsp"
                                        class="hover:text-slate-100 relative after:absolute after:bottom-0 after:left-0 after:bg-slate-50 after:h-0.5 after:w-0 hover:after:w-full after:transition-all after:ease-in-out after:duration-300 uppercase">Products</a>
                                </li>
                                <li><a href="#about"
                                        class="hover:text-slate-100 relative after:absolute after:bottom-0 after:left-0 after:bg-slate-50 after:h-0.5 after:w-0 hover:after:w-full after:transition-all after:ease-in-out after:duration-300 uppercase">About</a>
                                </li>
                                <li><a href="#contact"
                                        class="hover:text-slate-100 relative after:absolute after:bottom-0 after:left-0 after:bg-slate-50 after:h-0.5 after:w-0 hover:after:w-full after:transition-all after:ease-in-out after:duration-300 uppercase">Contact</a>
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
                            <span class="text-slate-100 rounded-xl bg-orange-700 px-2 py-1">${sessionScope.cart != null
                                ? sessionScope.cart.getList().size() : 0}</span>
                        </div>
                    </div>
                </div>
                <div class="slider">
                    <jsp:useBean id="b" class="dal.BannerDAO" />
                    <c:forEach items="${b.allImage}" var="item">
                        <div class="slide active">
                            <img src="${item.getImage()}" alt="">
                        </div>
                    </c:forEach>

                    <div class="navigation">
                        <i class="fas fa-chevron-left prev-btn"></i>
                        <i class="fas fa-chevron-right next-btn"></i>
                    </div>
                    <div class="navigation-visibility">
                        <c:set var="count" value="0"/>
                        <c:forEach items="${b.allImage}" var="item">
                            <div class="slide-icon ${count==0?'active':''}"></div>
                            <c:set var="count" value="${count+1}"/>
                        </c:forEach>
                    </div>
                </div>
                <div id="hot-products" class="py-20 px-32">
                    <h1 class="text-4xl font-medium text-slate-700 text-center">Best Seller</h1>
                    <div class="feature-divider"></div>
                    <div class="products flex justify-evenly">
                        <jsp:useBean id="i" class="dal.ProductDAO" />
                        <c:forEach items="${i.top4}" var="p">
                            <div class="item w-56">
                                <div class="image h-56 overflow-hidden relative">
                                    <img src="${p.getImage()}" alt="product" class="w-full h-56">
                                    <div class="order w-full h-10 flex justify-center items-center">
                                        <p class="text-white inline">Add to cart?</p>
                                        <c:if test="${p.getUnitInStock() == 0}">
                                            <button class="cursor-pointer ml-2 py-1 px-6 bg-black rounded text-white"
                                                onclick="showNotEnought()">Add</button>
                                        </c:if>
                                        <c:if test="${p.getUnitInStock() != 0}">
                                            <a href="cart?productId=${p.getId()}&quantity=1&home=1"
                                                class="cursor-pointer ml-2 py-1 px-6 bg-black rounded text-white">Add</a>
                                        </c:if>
                                        <c:if test="${requestScope.errorCart != null}">
                                            <script>
                                                alert("${requestScope.errorCart}");
                                                window.location = 'home.jsp'
                                            </script>
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
                    </div>
                </div>
                <div id="about" class="py-20 px-64">
                    <center>
                        <h1 class="text-4xl font-medium text-slate-700">About Us</h1>
                        <div class="feature-divider"></div>
                        <jsp:useBean id="sd" class="dal.SupplierDAO" />
                        <c:forEach items="${sd.top5}" var="s">
                            <div class="content mb-6">
                                <h1 class="text-2xl font-medium text-slate-600">${s.getName()}</h1>
                                <p class="font-serif">${s.getTitle()}</p>
                            </div>
                        </c:forEach>
                    </center>
                </div>
                <%@include file="footer.jsp" %>
            </div>
        </body>

        </html>
        <script type="text/javascript" src="/Project1/js/header.js"></script>
        <script type="text/javascript" src="/Project1/js/home.js"></script>