'use strict';

angular.module('chifcheerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('resturant', {
                parent: 'entity',
                url: '/resturant',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'chifcheerApp.resturant.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/resturant/resturants.html',
                        controller: 'ResturantController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('resturant');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('resturantDetail', {
                parent: 'entity',
                url: '/resturant/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'chifcheerApp.resturant.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/resturant/resturant-detail.html',
                        controller: 'ResturantDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('resturant');
                        return $translate.refresh();
                    }]
                }
            });
    });
