<%@ page import="Inquiry.*" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    Gson gson = new Gson();

    String reqs = request.getParameter("reqs");
    if(reqs != null){
        switch (reqs){
            case "ident": {
                String mac = request.getParameter("mac");
                if(mac != null) IO.IO.print(out, gson.toJson(new SignStatus(mac)));
            }break;

            case "viaName": {
                String userName = request.getParameter("userName");
                if(userName != null) IO.IO.print(out, gson.toJson(new ViaUserName(userName)));
            }break;

            case "signIn": {
                String mac = request.getParameter("mac");
                String password = request.getParameter("password");
                if(mac != null && password != null) {
                    IO.IO.print(out, gson.toJson(new SignIn(mac, password)));
                }
            }break;

            case "signUp": {
                String mac = request.getParameter("mac");
                String userName = request.getParameter("userName");
                String password = request.getParameter("password");
                if(mac != null && userName != null && password != null) {
                    IO.IO.print(out, gson.toJson(new SignUp(mac, userName, password)));
                }
            }break;

        }
    }
%>
