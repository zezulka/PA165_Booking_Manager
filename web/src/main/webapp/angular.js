'use strict';

/**
 * Define the application and declare dependencies (ngRoute).
 */
var bookingManager = angular.module('bookingManager', ['ngRoute', 'controllers']);
var controllers = angular.module('controllers', []);


/** 
 * Defines new directive (HTML attribute "convert-to-int") for conversion between string and int
 */
controllers.directive('convertToInt', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (val) {
                return parseInt(val, 10);
            });
            ngModel.$formatters.push(function (val) {
                return '' + val;
            });
        }
    };
});

/*
 * Set up routing on the page.
 * 
 * Differences between /browse and /admin/hotels:
 * 
 * /browse route will only show those hotels which have at least one room available
 * for booking.
 * 
 * /admin/hotels must show the administration all the hotels possible.
 * 
 */
bookingManager.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
                when('/browse', {templateUrl: 'partials/browse.html', controller: 'BrowseHotelsCtrl'}).
                when('/hotel/:hotelId', {templateUrl: 'partials/hotel_detail.html', controller: 'HotelDetailCtrl'}).
                when('/room/:roomId', {templateUrl: 'partials/room_detail.html', controller: 'RoomDetailCtrl'}).
                // this route should handle the use case "System admin should be also able to find customers who have some room reserved in a certain time range"
                when('/admin/browse_users', {templateUrl: 'partials/admin_browse_customers.html', controller: 'AdminBrowseCustomersCtrl'}).
                when('/admin/newroom', {templateUrl: 'partials/admin_new_room.html', controller: 'AdminNewRoomCtrl'}).
                when('/admin/hotels', {templateUrl: 'partials/admin_hotels.html', controller: 'AllHotelsCtrl'}).
                when('/login', {templateUrl: 'partials/login.html', controller: 'LoginCtrl'}).
                otherwise({redirectTo: '/browse'});
    }]);

bookingManager.run(function ($rootScope, $http) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
    $http.defaults.headers.common.Accept = 'application/hal+json, */*';
});

function loadHotelRooms($http, hotel, roomLink) {
    $http.get(roomLink).then(function (response) {
        hotel.rooms = response.data['_embedded']['rooms'];
        console.log('AJAX loaded ${hotel.rooms.length} rooms to the hotel ${hotel.name}');
    });
}

controllers.controller('LoginCtrl', function ($scope, $http) {
    $http.get('/pa165/rest/users/authenticate').then(
            function (response) {
                console.log(response.data);
            },
            function error(response) {
                console.log(response.data);
            }
    );
});

controllers.controller('BrowseHotelsCtrl', function ($scope, $http) {
    console.log('/pa165/rest/hotels/');
    $http.get('/pa165/rest/hotels/').then(function (response) {
        var hotels = response.data['_embedded']['hotels'];
        console.log('AJAX loaded all hotels.');
        $scope.hotels = hotels;
        for (var i = 0; i < hotels.length; i++) {
            var hotel = hotels[i];
            var categoryProductsLink = hotel['_links'].products.href;
            loadHotelRooms($http, hotel, categoryProductsLink);
        }
    });
});

controllers.controller('HotelDetailCtrl',
        function ($scope, $rootScope, $routeParams, $http) {
            var hotelId = $routeParams.hotelId;
            $http.get('/pa165/rest/hotels/' + hotelId).then(
                    function (response) {
                        $scope.hotel = response.data;
                        console.log('[AJAX] hotel ${scope.hotel.name} detail load');
                    },
                    function error(response) {
                        console.log("failed to load product ${productId}");
                        console.log(response);
                        $rootScope.warningAlert = 'Cannot load product: ${response.data.message}';
                    }
            );
        });

controllers.controller('RoomDetailCtrl',
        function ($scope, $rootScope, $routeParams, $http) {
            var roomId = $routeParams.roomId;
            $http.get('/pa165/rest/rooms/' + roomId).then(
                    function (response) {
                        $scope.product = response.data;
                        console.log('[AJAX] room ${scope.room.name} detail load');
                    },
                    function error(response) {
                        console.log("failed to load room ${productId}");
                        console.log(response);
                        $rootScope.warningAlert = 'Cannot load product: ${response.data.message}';
                    }
            );
        });

/**
 * TODO: admin routes
 */