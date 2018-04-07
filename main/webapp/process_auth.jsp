<%-- 
    Document   : test
    Created on : Mar 22, 2017, 11:38:12 AM
    Author     : krist
--%>

<%@page import="java.util.List"%>
<%@page import="com.mycompany.mypersonality.SocialNetwork"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
        <%
            String code = request.getParameter("code");
            if (code == null || code.length() == 0) {
        %>
            Произошла ошибка при авторизации!
        <%           
            } else {
                
                SocialNetwork.SocialNetworkAdapter vk = new SocialNetwork.SNvkAdapter(code);
                List likes = vk.GetUserLikes();
                for (Object like: likes){
        %>
                Лайк: <% like.toString(); %>
        <%
                }

            }
        %>
    
</html>
