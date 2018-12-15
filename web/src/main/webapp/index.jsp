<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap-theme.min.css" crossorigin="anonymous">
        <title>Booking manager</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
            <a class="navbar-brand" href="#">BookingManager v0.999</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" 
                    aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbar">
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#!/admin/browse_users">Users</a></li>
                            <li><a class="dropdown-item" href="#!/admin/hotels">Hotels</a></li>
                        </ul>
                    </li>
                </ul>
                <a class="btn btn-outline-primary" href="#">Log in</a>
            </div>
        </nav>

        <main role="main" class="container">

            <div ng-app="bookingManager">
                <div ng-show="warningAlert" class="alert alert-warning alert-dismissible" role="alert">
                    <button type="button" class="close" aria-label="Close" ng-click="hideWarningAlert()"> <span aria-hidden="true">&times;</span></button>
                    <strong>Warning!</strong> <span>{{warningAlert}}</span>
                </div>
                <div ng-show="errorAlert" class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" aria-label="Close" ng-click="hideErrorAlert()"> <span aria-hidden="true">&times;</span></button>
                    <strong>Error!</strong> <span>{{errorAlert}}</span>
                </div>
                <div ng-show="successAlert" class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" aria-label="Close" ng-click="hideSuccessAlert()"> <span aria-hidden="true">&times;</span></button>
                    <strong>Success !</strong> <span>{{successAlert}}</span>
                </div>
                <div ng-view></div>
            </div>

        </main><!-- /.container -->
        <!-- Popper and Bootstrap-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" crossorigin="anonymous"></script>

        <!-- JQuery and Angular -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.5/angular.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.5/angular-resource.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.5/angular-route.min.js"></script>
        <script src="${pageContext.request.contextPath}/angular.js"></script>

    </body>
</html>