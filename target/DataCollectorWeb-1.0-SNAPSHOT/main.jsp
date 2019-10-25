<%@page import="entity.SQLData"%>
<%@page import="entity.OPCConf"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index</title>
    </head>
    <body class="px-5 pt-3">
        <h1>Seacon DataCollector</h1>
        
        <h3 class="pt-4 pb-2">SQL Configurations</h3>
        
        <form name="selection_form" method="post" action="main">
            <table class="table table-bordered text-center">
                <thead class="thead-light">
                    <th>ID</th>
                    <th>Name</th>
                    <th>Password</th>
                    <th>Database Name</th>
                    <th>Database Address</th>
                    <th>Database Port</th>
                    <th>Descriptor</th>
                    <th>Selected</th>
                    <th></th>
                </thead>
                <tbody>
                <%
                    List<SQLData> sql = (List<SQLData>) request.getAttribute("sqlData");
                    Iterator sqlIter = sql.iterator();

                    while (sqlIter.hasNext()) {
                        SQLData next = (SQLData) sqlIter.next();
                        out.println("<tr>");
                        out.println("<td>" + next.getId() + "</td>");
                        out.println("<td>" + next.getName() + "</td>");
                        out.println("<td>" + next.getPassword() + "</td>");
                        out.println("<td>" + next.getDbName() + "</td>");
                        out.println("<td>" + next.getDbAddress() + "</td>");
                        out.println("<td>" + next.getDbPort() + "</td>");
                        out.println("<td>" + request.getAttribute("descriptor") + "</td>");
                        out.println("<td><input type=\"radio\" name=\"selection\" value=" + next.getId() + " ></td>");
                        out.println("<td><button type=\"submit\" class=\"btn btn-danger\" name=\"deleteButton\" value=" + next.getId() + ">Delete</td>");
                        out.println("</tr>");
                    }
                %>
                <tr>
                    <td colspan="9" class="py-2">
                        <a href="sqlconfig" class="btn btn-primary font-weight-bold pt-1" style="border-radius: 19px; width: 38px; height: 38px">+</a>
                    </td>
                </tr>
                </tbody>
            </table>

            <h3 class="pt-4 pb-2">OPC Configurations</h3>
            
            <table class="table table-bordered text-center">
                <thead class="thead-light">
                    <th>ID</th>
                    <th>Name</th>
                    <th>URL</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Anonymous</th>
                    <th>Selected</th>
                    <th></th>
                </thead>
                <%
                    List<OPCConf> opc = (List<OPCConf>) request.getAttribute("opcData");
                    Iterator opcIter = opc.iterator();

                    while (opcIter.hasNext()) {
                        OPCConf next = (OPCConf) opcIter.next();
                        out.println("<tr>");
                        out.println("<td>" + next.getId() + "</td>");
                        out.println("<td>" + next.getName() + "</td>");
                        out.println("<td>" + next.getUrl() + "</td>");
                        out.println("<td>" + next.getUserName() + "</td>");
                        out.println("<td>" + next.getPassword() + "</td>");
                        out.println("<td>" + next.isAnonymous() + "</td>");
                        out.println("<td><input type=\"radio\" name=\"opc_selection\" value=" + next.getId() + " ></td>");
                        out.println("<td><button type=\"submit\" class=\"btn btn-danger\" name=\"deleteOPCButton\" value=" + next.getId() + ">Delete</td>");
                        out.println("</tr>");
                    }
                %>
                <tr>
                    <td colspan="9" class="py-2">
                        <a href="opcconfig" class="btn btn-primary font-weight-bold pt-1" style="border-radius: 19px; width: 38px; height: 38px">+</a>
                    </td>
                </tr>
            </table>

            <input type="submit" class="btn btn-primary" value="Start">
        </form>
    </body>
</html>
