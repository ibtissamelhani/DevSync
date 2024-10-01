<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome to DevSync</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            width: 50%;
            margin: 0 auto;
            text-align: center;
            padding: 50px;
            border: 1px solid #ddd;
            box-shadow: 2px 2px 12px rgba(0, 0, 0, 0.1);
        }
        input[type="text"], input[type="submit"] {
            padding: 10px;
            margin: 10px;
            width: 80%;
        }
        input[type="submit"] {
            background-color: #007BFF;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Welcome to DevSync Application</h1>
    <p>Enter your name to start:</p>

    <form action="welcome.jsp" method="get">
        <input type="text" name="username" placeholder="Enter your name" required>
        <input type="submit" value="Submit">
        <input type="text" name="username" placeholder="Enter your name" required>
        <input type="submit" value="Submit">
        <input type="text" name="username" placeholder="Enter your name" required>
        <input type="submit" value="Submit">
    </form>
</div>
</body>
</html>
