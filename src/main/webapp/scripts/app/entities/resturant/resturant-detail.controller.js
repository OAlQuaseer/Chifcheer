'use strict';

angular.module('chifcheerApp')
    .controller('ResturantDetailController', function ($scope, $stateParams, Resturant) {
        $scope.resturant = {};
        $scope.load = function (id) {
            Resturant.get({id: id}, function(result) {
              $scope.resturant = result;
            });
        };
        $scope.load($stateParams.id);
    });
