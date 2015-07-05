'use strict';

angular.module('chifcheerApp')
    .controller('ResturantController', function ($scope, Resturant, ParseLinks) {
        $scope.resturants = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Resturant.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.resturants = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Resturant.get({id: id}, function(result) {
                $scope.resturant = result;
                $('#saveResturantModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.resturant.id != null) {
                Resturant.update($scope.resturant,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Resturant.save($scope.resturant,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Resturant.get({id: id}, function(result) {
                $scope.resturant = result;
                $('#deleteResturantConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Resturant.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteResturantConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveResturantModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.resturant = {resName: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
