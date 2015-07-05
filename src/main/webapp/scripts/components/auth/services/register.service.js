'use strict';

angular.module('chifcheerApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


