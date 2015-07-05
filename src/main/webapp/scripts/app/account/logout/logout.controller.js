'use strict';

angular.module('chifcheerApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
