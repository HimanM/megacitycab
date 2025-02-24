<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ include file="WEB-INF/views/shared/default_navbar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mega City Cab - Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* Fullscreen background image */
        body {
            background: url('<%= request.getContextPath() %>/assets/images/taxi_bg.jpg') no-repeat center center/cover;
            height: 100vh;
            display: flex;
            flex-direction: column;
        }


        /* Centered hero section */
        .hero {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            color: white;
            padding: 20px;
        }

        .hero h1 {
            font-size: 3rem;
            font-weight: bold;
            text-shadow: 2px 2px 5px rgba(0,0,0,0.5);
        }

        .hero p {
            font-size: 1.3rem;
            margin-top: 10px;
            text-shadow: 1px 1px 3px rgba(0,0,0,0.5);
        }

        /* Get Started button */
        .btn-get-started {
            font-size: 1.2rem;
            padding: 12px 30px;
            background: #ffcc00;
            border: none;
            color: black;
            font-weight: bold;
            border-radius: 30px;
            transition: 0.3s;
        }

        .btn-get-started:hover {
            background: #ffaa00;
            transform: scale(1.1);
        }
    </style>
</head>
<body>



<!-- Hero Section -->
<div class="hero">
    <div>
        <h1>Reliable & Fast Cab Service in Colombo</h1>
        <p>Thousands of customers trust Mega City Cab every month for their daily rides.</p>
        <a href="${pageContext.request.contextPath}/auth/login" class="btn btn-get-started mt-3">Get Started</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
