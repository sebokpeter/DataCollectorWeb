<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OPC configuration</title>
    </head>
    <body>
        <h1>OPC configuration</h1>
        
        <form name="opc_form" method="post" action="opc_conf_servlet">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Name">
            </div>
            <div class="form-group">
                <label for="url">OPC URL</label>
                <input type="text" class="form-control" id="url" name="url" placeholder="OPC URL">
            </div>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="anonymous" name="anonymous">
                <label class="form-check-label" for="anonymous">Anonymous access</label>
            </div>
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Username">
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
            </div>
            
            <button type="submit" class="btn btn-primary">Save</button>
        </form>
    </body>
</html>
