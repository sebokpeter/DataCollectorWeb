<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create descriptor</title>
    </head>
    <body class="px-5 pt-3">
        <h1>Create descriptor</h1>
        <div class="row">
            <div class="col-xl-5 col-lg-7 col-9">
                <form name="d_form" method="post" action="descriptor">
                    <div class="form-group">
                        <label for="name">Name</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Name" required>
                    </div>
                    <div class="form-group">
                        <label for="name">Table name</label>
                        <input type="text" class="form-control" id="table_name" name="table_name" placeholder="Table name" required>
                    </div>
                    <%
                        int num = Integer.parseInt((String) request.getAttribute("set_number"));

                        for (int i = 0; i < num; i++) {
                    %>
                    <h3>Entry <%= i + 1%></h3>

                    <div class="px-3">
                        <div class="form-group">
                            <label for="name">Data Type</label>
                            <select class="form-control" id="type" name="type" required>
                                <option value="STRING">String</option>
                                <option value="INTEGER">Integer</option>
                                <option value="REAL">Real</option>
                                <option value="BOOLEAN">Boolean</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="name">OPC Node Namespace</label>
                            <input type="number" min="0" class="form-control" id="ns" name="ns" placeholder="OPC Node Namespace" required>
                        </div>
                        <div class="form-group">
                            <label for="name">OPC Node ID</label>
                            <input type="text" class="form-control" id="nid" name="nid" placeholder="OPC Node ID" required>
                        </div>
                        <div class="form-group">
                            <label for="name">OPC Node ID Type</label>
                            <select class="form-control" id="id_type" name="id_type" required>
                                <option value="string">String</option>
                                <option value="int">Integer</option>
                            </select>
                        </div>
                    </div>
                    <%
                        }
                    %>
                    <div class="pb-3">
                        <button type="submit" class="btn btn-primary">Save</button>
                        <a class="btn btn-secondary" href="sqlconfig">Back</a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
