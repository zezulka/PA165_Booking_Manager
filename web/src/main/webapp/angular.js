'use strict';
/**
 * Define the application and declare dependencies (ngRoute).
 */
var bookingManager = angular.module('bookingManager', ['ngRoute', 'ngCookies', 'controllers']);
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
                when('/about', {templateUrl: 'partials/about.html'}).
                when('/hotel/:hotelId', {templateUrl: 'partials/hotel_detail.html', controller: 'HotelDetailCtrl'}).
                when('/room/:roomId', {templateUrl: 'partials/room_detail.html', controller: 'RoomDetailCtrl'}).
                // this route should handle the use case "System admin should be also able to find customers who have some room reserved in a certain time range"
                when('/admin/browse_users', {templateUrl: 'partials/admin/browse_customers.html', controller: 'AdminBrowseCustomersCtrl'}).
                when('/admin/newroom', {templateUrl: 'partials/admin/new_room.html', controller: 'AdminNewRoomCtrl'}).
                when('/admin/hotels', {templateUrl: 'partials/admin/hotels.html', controller: 'AllHotelsCtrl'}).
                when('/login', {templateUrl: 'login.html', controller: 'LoginController'}).
                when('/logout', {templateUrl: 'login.html', controller: 'LogoutController'}).
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
        hotel.roomCount = response.data['_embedded']['rooms'].length;
        hotel.rooms = []; //By default, we do not want to show any rooms to the user as no date range is selected at the beginning
        console.log('AJAX loaded ' + hotel.rooms.length + 'rooms to the hotel' +  hotel.name);
    });
}

controllers.controller('AvailableRoomsController', function ($scope, $rootScope, $http) {
    $scope.vacancies = function (hotel) {
        console.log("foobar");
        var hotelId = hotel.id;
        var from = $scope.from.toISOString().substring(0, 10);
        var to = $scope.to.toISOString().substring(0, 10);
        // Remeber these globally as we'll need to use them later
        $rootScope.fromDate = from;
        $rootScope.toDate = to;
        $http.get('/pa165/rest/hotels/' + hotelId + '/vacancy?from=' + from + '&to=' + to).then(function (response) {
            hotel.rooms = response.data['_embedded']['rooms'];
        });
    }
});

controllers.controller('RoomBookingController', function ($scope, $rootScope, $http, $location, PageService) {
    $scope.book = function (room) {
        var body = {room: room, fromDate: $rootScope.fromDate, toDate: $rootScope.toDate, user: PageService.getUser(), total: room.recommendedPrice};
        console.log(body);
        $http.post('/pa165/rest/bookings/create', body)
                .then(function (response) {
                    console.log("Booking successfully created.");
                    $location.path('/hotel/' + room.hotel.id);
                    $rootScope.successAlert = 'Booking successfully created';
                }, function (reason) {
                    console.log("Could not create booking.");
                });
    }
});

controllers.controller('BrowseHotelsCtrl', function ($scope, $http, PageService) {
    console.log('/pa165/rest/hotels/');
    $scope.loggedIn = PageService.isLoggedIn;
    $http.get('/pa165/rest/hotels/').then(function (response) {
        var hotels = response.data['_embedded']['hotels'];
        console.log('AJAX loaded all hotels.');
        $scope.hotels = hotels;
        for (var i = 0; i < hotels.length; i++) {
            var hotel = hotels[i];
            var roomsLink = hotel['_links'].rooms.href;
            loadHotelRooms($http, hotel, roomsLink);
        }
    });
});
controllers.controller('HotelDetailCtrl',
        function ($scope, $rootScope, $routeParams, $http) {
            var hotelId = $routeParams.hotelId;
            $http.get('/pa165/rest/hotels/' + hotelId).then(
                    function (response) {
                        $scope.hotel = response.data;
                        console.log('[AJAX] hotel ' + $scope.hotel.name + 'detail load');

                        var hotelRoomsLink = $scope.hotel['_links'].rooms.href;
                        loadHotelRooms($http, $scope.hotel, hotelRoomsLink);
                    },
                    function error(response) {
                        console.log('failed to load product');
                        console.log(response);
                        $rootScope.warningAlert = 'Cannot load product: ' + response.data.message;
                    }
            );
        });
controllers.controller('RoomDetailCtrl',
        function ($scope, $rootScope, $routeParams, $http) {
            var roomId = $routeParams.roomId;
            $http.get('/pa165/rest/rooms/' + roomId).then(
                    function (response) {
                        $scope.room = response.data;
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
*
* Admin stuff
*
*/

function loadUsersBookings($http, user, from, to) {
    $http.get('/pa165/rest/bookings/byUser?from=' + from + '&to=' + to + '&user=' + user.id).then(function (response) {
        user.bookingCount = response.data['_embedded']['bookings'].length;
        console.log('bookings loaded ' + user.bookingCount);
    });
}

controllers.controller('AdminBrowseCustomersCtrl', function ($scope, $rootScope, $http) {
   $scope.browseCostumers = function () {
       var from = $scope.from.toISOString().substring(0, 10);
       var to = $scope.to.toISOString().substring(0, 10);
       $http.get('/pa165/rest/users/reserved?from=' + from + '&to=' + to).then(function (response) {
           console.log("users reserved");
           var users = response.data['_embedded']['users'];
           $scope.users = users;
           for (var i = 0; i < users.length; i++) {
               var user = users[i];
               loadUsersBookings($http, user, from, to);
           }
       });
   }
});


/**
 * Authentication stuff.
 */
controllers.factory('PageService', function ($rootScope, $http, $location, $cookies) {
    var _title = '';
    var _pageName = '';
    var _useSchedulerLayout = false;
    var _restPrefix = '/pa165/rest/';

    $rootScope.user = undefined;
    $rootScope.admin = false;

    return {
        getTitle: function () {
            var title = 'Booking Manager';
            if (_title !== '') {
                title = _title + ' - ' + title;
            }

            return title;
        },

        setTitle: function (title) {
            _title = title;
        },

        getPageName: function () {
            return _pageName;
        },

        setPageName: function (pageName) {
            _pageName = pageName;
        },

        isSchedulerLayoutUsed: function () {
            return _useSchedulerLayout;
        },

        useSchedulerLayout: function () {
            _useSchedulerLayout = true;
        },

        isEditing: function (route) {
            return route.current.$$route.edit === true;
        },

        getDataAsync: function (url) {
            return $http.get(_restPrefix + url).then(function (response) {
                return response.data;
            }, function () {
                return null;
            });
        },

        sendDataAsync: function (url, method, data, successMessage, successUrl, errorMessages, qsa) {
            var _this = this;
            return $http({
                url: _restPrefix + url,
                method: method,
                data: data
            }).then(function (response) {
                _this.pushSuccessMessage(successMessage);
                if (qsa != null) {
                    $location.path(successUrl).search(qsa);
                } else {
                    $location.path(successUrl);
                }
            }, function (reason) {
                var type = reason.data.type;
                console.log("Failure code: " + type);

                var message = 'Unknown error.';
                if (type in errorMessages) {
                    message = errorMessages[type];
                } else if ('otherwise' in errorMessages) {
                    message = errorMessages['otherwise'];
                }

                message = message.replace('{msg}', reason.data.message);
                _this.pushErrorMessage(message);
                _this.consumeMessages();
            });
        },

        pushSuccessMessage: function (msg) {
            $rootScope.successQueue = msg;
        },

        pushWarningMessage: function (msg) {
            $rootScope.warningQueue = msg;
        },

        pushErrorMessage: function (msg) {
            $rootScope.errorQueue = msg;
        },

        consumeMessages: function () {
            $rootScope.success = $rootScope.successQueue;
            $rootScope.successQueue = null;

            $rootScope.warning = $rootScope.warningQueue;
            $rootScope.warningQueue = null;

            $rootScope.error = $rootScope.errorQueue;
            $rootScope.errorQueue = null;
        },

        getUser: function () {
            var cookieUser = $cookies.getObject('user');
            if (cookieUser != null) {
                $rootScope.user = cookieUser;
            }
            return $rootScope.user;
        },

        setUser: function (user) {
            $rootScope.user = user;
            $cookies.putObject('user', user);
        },

        isLoggedIn: function () {
            return typeof this.getUser() !== typeof undefined && this.getUser() !== null;
        },

        isAdministrator: function () {
            return this.isLoggedIn() && this.getUser().isAdmin;
        },

        login: function (userAuthenticate) {
            var _this = this;
            console.log("login()");
            return $http({
                url: '/pa165/rest/users/authenticate',
                method: 'POST',
                data: userAuthenticate
            })
                    .then(function (response) {
                        if (response.data === "true") {
                            console.log("login OK");
                            _this.getDataAsync('/users/email/' + userAuthenticate.email + '/').then(function (user) {
                                _this.setUser(user);
                                $rootScope.admin = $rootScope.user.administrator;
                                $location.path('/');
                            });
                        } else {
                            $rootScope.errorAlert = 'User authentication failed. Wrong email or password';
                            _this.consumeMessages();
                        }
                    }, function (reason) {
                        $rootScope.errorAlert = 'User authentication failed. Wrong email or password';
                        _this.consumeMessages();
                    }
                    )
        },

        logout: function () {
            this.setUser(undefined);
            $cookies.remove('user');
            this.pushSuccessMessage('Successfully logged out.');
            $rootScope.user = undefined;
            $rootScope.admin = false;
            $location.path('/');
        },

        requireLogin: function () {
            if (!this.isLoggedIn()) {
                $location.path('/');
            }
        },

        requireAdmin: function () {
            if (!this.isAdministrator()) {
                $location.path('/');
            }
        }
    };
});


controllers.controller('LoginController', [
    '$scope',
    '$rootScope',
    'PageService',

    function ($scope, $rootScope, PageService) {
        PageService.consumeMessages();
        PageService.setPageName('Log In');

        $scope.userAuthenticate = {
            email: '',
            password: ''
        };
        $scope.login = function (userAuthenticate) {
            PageService.login(userAuthenticate);
        };
    }

]);

controllers.controller('LogoutController', [
    '$location',
    'PageService',

    function ($location, PageService) {
        PageService.consumeMessages();
        PageService.logout();
        $location.path('/');
    }

]);