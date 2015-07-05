'use strict';

angular.module('chifcheerApp')
    .factory('Resturant', function ($resource, DateUtils) {
        return $resource('api/resturants/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
