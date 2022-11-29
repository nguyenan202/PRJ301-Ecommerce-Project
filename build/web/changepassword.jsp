<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
              integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
    </head>
    <body>
        <div class="root relative">
            <div class="back-profile mt-6 ml-6">
                <a href="profile.jsp" class="text-3xl">
                    <i class="fa-solid fa-arrow-left text-black"></i> <span class="text-black">Profile</span>
                </a>
            </div>
            <center>
                <form action="changepassword" method="post">
                    <table>
                        <tr class="h-10">
                            <th colspan="4">
                                <h1 class="text-5xl m-5">Change Password</h1>
                            </th>
                        </tr>
                        <tr class="h-10 text-lg">
                            <td colspan="2">Old Password: </td>
                            <td colspan="2">
                                <input type="password" class="border-2 rounded w-full" name="oldpass"/>
                            </td>
                        </tr>
                        <c:if test="${requestScope.oldPassMessage != null}">
                            <tr class="h-4 text-base">
                                <td colspan="2"></td>
                                <td colspan="2" class="text-red-500">
                                    ${requestScope.oldPassMessage}
                                </td>
                            </tr>
                        </c:if>
                        <tr class="h-10 text-lg">
                            <td colspan="2">New Password: </td>
                            <td colspan="2">
                                <input type="password" class="border-2 rounded w-full" name="newpass"/>
                            </td>
                        </tr>
                        <c:if test="${requestScope.emptyPassMessage != null}">
                            <tr class="h-4 text-base">
                                <td colspan="2"></td>
                                <td colspan="2" class="text-red-500">
                                    ${requestScope.emptyPassMessage}
                                </td>
                            </tr>
                        </c:if>
                        <tr class="h-10 text-lg">
                            <td colspan="2">Re-New Password: </td>
                            <td colspan="2">
                                <input type="password" class="border-2 rounded w-full" name="re-newpass"/>
                            </td>
                        </tr>
                        <c:if test="${requestScope.samePassMessage != null}">
                            <tr class="h-4 text-base">
                                <td colspan="2"></td>
                                <td colspan="2" class="text-red-500">
                                    ${requestScope.samePassMessage}
                                </td>
                            </tr>
                        </c:if>
                        <tr>
                            <td colspan="4">
                                <input type="submit" value="Change" class="w-full mt-2 py-2 bg-orange-700 rounded cursor-pointer text-white hover:bg-orange-400 hover:text-black"/>         
                            </td>
                        </tr>
                        <c:if test="${requestScope.changePassMessage != null}">
                            <tr class="h-10 text-base">
                                <td colspan="4" class="text-green-600">
                                    ${requestScope.changePassMessage}
                                </td>
                            </tr>
                        </c:if>
                    </table>
                </form>
            </center>
        </div>
    </body>
</html>
