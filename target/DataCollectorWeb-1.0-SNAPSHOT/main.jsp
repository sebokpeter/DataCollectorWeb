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
    <body>
        <h1>Seacon DataCollector</h1>
        <form name="selection_form" method="post" action="main_page">
            <table border="1">
                <tr>
                    <td>ID</td>
                    <td>NAME</td>
                    <td>PASSWORD</td>
                    <td>DATABASE NAME</td>
                    <td>DATABASE ADDRESS</td>
                    <td>DATABASE PORT</td>
                    <td>DESCRIPTOR</td>
                    <td>SELECTED</td>
                    <td>DELETE</td>
                </tr>
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
            </table>

            <input type="submit" value="Start">

            <h2>
                <a href="sqlconf_servlet">Add New Configuration</a>
            </h2>

            <table border="1">
                <tr>
                    <td>ID</td>
                    <td>NAME</td>
                    <td>URL</td>
                    <td>USERNAME</td>
                    <td>PASSWORD</td>
                    <td>ANONYMOUS</td>
                    <td>SELECTED</td>
                    <td>DELETE</td>
                </tr>
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
                        out.println("<td><button type=\"submit\" name=\"deleteOPCButton\" value=" + next.getId() + ">Delete</td>");
                        out.println("</tr>");
                    }
                %>
            </table>
            <h2>
                <a href="opc_conf_servlet">OPC configuration</a>
            </h2>
        </form>
    </body>
</html>
