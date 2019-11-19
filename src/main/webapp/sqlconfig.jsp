<%@page import="BE.DescriptorConn"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SQL configuration</title>
        <script type="text/javascript">
            function PickNumber() {
                var number = prompt("Please enter the number of entries");
                if (number !== null && !isNaN(number)) {
                    document.desc_open_form.set_number.value = number;
                    document.desc_open_form.submit();
                }
                else if (number === null) {
                    return;
                }
                else {
                    PickNumber();
                }
            }
        </script>
    </head>
    <body class="px-5 pt-3">
        <h1>SQL server parameter configuration</h1>

        <div class="row">
            <div class="col-xl-5 col-lg-7 col-9">
                <form name="sql_form" method="post" action="sqlconfig">
                    <div class="form-group">
                        <label for="name">Login name</label>
                        <input type="text" class="form-control" id="name" name="login" placeholder="Login name" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="pwd" placeholder="Password" required>
                    </div>
                    <div class="form-group">
                        <label for="db_name">Database name</label>
                        <input type="text" class="form-control" id="db_name" name="db_name" placeholder="Database name" required>
                    </div>
                    <div class="form-group">
                        <label for="address">Address</label>
                        <input type="text" class="form-control" id="address" name ="address" placeholder="Address" required>
                    </div>
                    <div class="form-group">
                        <label for="port">Port</label>
                        <input type="number" class="form-control" id="port" name="port" min="1" max="65535" placeholder="Port" required>
                    </div>
                    <div class="row align-items-end">
                        <div class="form-group col-11">
                            <label for="descriptorSelect">Descriptor</label>
                            <select class="form-control" id="descriptorSelect" name="conn" required>
                                <%
                                    List<DescriptorConn> descriptors = (List<DescriptorConn>) request.getAttribute("descriptors");
                                    Iterator descIter = descriptors.iterator();

                                    while (descIter.hasNext()) {
                                        DescriptorConn next = (DescriptorConn) descIter.next();
                                        out.println("<option value=" + next.getId() + ">" + next.getName() + "</option>");
                                    }
                                %>
                            </select>
                        </div>

                        <div class="col-1 pl-0">
                            <a href="javascript:PickNumber();" class="btn btn-primary font-weight-bold px-0 py-0 mt-1" style="border-radius: 15px; width: 30px; height: 30px; margin-bottom: 20px">+</a>
                        </div>
                    </div>        

                    <button type="submit" class="btn btn-primary">Save</button>
                    <a class="btn btn-secondary" href="main">Back</a>
                </form>

                <form id="desc_open_form" name="desc_open_form" method="get" action="descriptor">
                    <input type="hidden" id="set_number" name="set_number">
                </form>
            </div>
        </div>
    </body>
</html>
