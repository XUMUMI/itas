<%@ page import="Inquiry.*" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="IO.Out" %>
<%@ page import="IO.In" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    Gson gson = new Gson();

    Out output = new Out(out);
    In input = new In(request);

    String reqs = request.getParameter("reqs");
    if(reqs != null)
        switch (reqs){
            case "ident": {
                String mac = request.getParameter("mac");
                if(mac != null) output.print(gson.toJson(new SignStatus(mac)));
            }break;

            case "viaName": {
                String userName = request.getParameter("userName");
                if(userName != null) output.print(gson.toJson(new ViaUserName(userName)));
            }break;

            case "signIn": {
                String mac = request.getParameter("mac");
                String password = input.get("password");
                if(mac != null && password != null) {
                    output.print(gson.toJson(new SignIn(mac, password)));
                }
            }break;

            case "signUp": {
                String mac = request.getParameter("mac");
                String userName = request.getParameter("userName");
                String password = input.get("password");
                if(mac != null && userName != null && password != null) {
                    output.print(gson.toJson(new SignUp(mac, userName, password)));
                }
            }break;

            case "getActInfo": {
                String userName = request.getParameter("userName");
                String password = input.get("password");
                if(userName != null && password != null) {
                    output.print(gson.toJson(new CheckInAct(userName, password)));
                }
            }break;

            case "checkInLog": {
                String userName = request.getParameter("userName");
                String password = input.get("password");
                if(userName != null && password != null) {
                    output.print(gson.toJson(new CheckInLog(userName, password)));
                }
            }break;
        }
%>
