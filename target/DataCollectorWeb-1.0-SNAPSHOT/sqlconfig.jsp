<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="entity.DescriptorConn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SQL configuration</title>
    </head>
    <body>
        <h1>SQL server parameter configuration</h1>
        <form name="sql_form" method="post" action="sqlconf_servlet">
            <div class="form-group">
                <label for="name">Login name</label>
                <input type="text" class="form-control" id="name" placeholder="Login name">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" placeholder="Password">
            </div>
            <div class="form-group">
                <label for="db_name">Database name</label>
                <input type="text" class="form-control" id="db_name" placeholder="Database name">
            </div>
            <div class="form-group">
                <label for="address">Address</label>
                <input type="text" class="form-control" id="address" placeholder="Address">
            </div>
            <div class="form-group">
                <label for="address">Port</label>
                <input type="number" class="form-control" id="port" min="1" max="65535" placeholder="Port">
            </div>
            <div class="form-group">
                <label for="descriptorSelect">Example multiple select</label>
                <select class="form-control" id="descriptorSelect">
                    <%
                        List<DescriptorConn> descriptors = (List<DescriptorConn>) request.getAttribute("descriptros");
                        Iterator descIter = descriptors.iterator();

                        while (descIter.hasNext()) {
                            DescriptorConn next = (DescriptorConn) descIter.next();
                            out.println("<option value=" + next.getId() + ">" + next.getName() + "</option>");
                        }
                    %>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Send</button>
        </form>
        <a href="main_page">Back</a>
        <a href="descriptor_servlet">Create new descriptor</a>
    </body>
</html>
