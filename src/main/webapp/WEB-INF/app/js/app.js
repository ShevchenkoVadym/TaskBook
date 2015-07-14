'use strict';

/* App Module */

var taskbookApp = angular.module('taskbookApp', [
  'ngRoute',
  'ngDialog',
  'ngCookies',
  'ngAnimate',
  'ngResource',
  'ui.bootstrap',
  'ui.bootstrap.tpls',
  'ui.codemirror',
  'ui.select',
  'ImageCropper',
  'angularUtils.directives.dirDisqus'
  ,'templates-dist'
]);

taskbookApp.config([
  '$routeProvider',
  '$httpProvider',
  function($routeProvider, $httpProvider) {
    $httpProvider.defaults.cache = false;
    if (!$httpProvider.defaults.headers.get) {
      $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';

    $routeProvider.
      when('/', {
        templateUrl: 'partials/home.html',
        controller: 'HomePageController',
        publicAccess: true
      }).
      when('/tasks', {
        templateUrl: 'partials/tasks/tasks.html',
        controller: 'TasksController',
        publicAccess: true
      }).
      when('/tasks/new', {
        templateUrl: 'partials/tasks/task-new.html',
        controller: 'TaskEditController',
        publicAccess: false
      }).
      when('/tasks/:id/edit', {
        templateUrl: 'partials/tasks/task-new.html',
        controller: 'TaskEditController',
        publicAccess: false
      }).
      when('/tasks/:id', {
        templateUrl: 'partials/tasks/task-detail.html',
        controller: 'TaskDetailController',
        publicAccess: true
      }).
      when('/users', {
        templateUrl: 'partials/users/users.html',
        controller: 'UsersController',
        publicAccess: true
      }).
      when('/users/:username', {
        templateUrl: 'partials/users/user-profile.html',
        controller: 'UserProfileController',
        publicAccess: true
      }).
      when('/login', {
        templateUrl: 'partials/login.html',
        publicAccess: true
      }).
      when('/play', {
        templateUrl: 'partials/playground.html',
        controller: 'PlaygroundController',
        publicAccess: false
      }).
      when('/about', {
        templateUrl: 'partials/about.html',
        controller: 'AboutController',
        publicAccess: true
      }).
      when('/_=_', {
        redirectTo: '/',
        publicAccess: true
      }).
      otherwise({
        templateUrl: 'partials/404.html',
        controller: 'NotFoundController',
        publicAccess: true
      });

    }])
    .run([
      '$rootScope',
      '$location',
      '$cookieStore',
      'CurrentUserService',
      'flash',
      'MessageService',
      function($rootScope, $location, $cookieStore, currentUser, flash, message) {

        $rootScope.$on('$routeChangeStart', function(event, next) {

          if ( currentUser.isLoggedIn()){
            if (next.originalPath === '/login') $location.path('/');
          } else if (!next.publicAccess) {
            if(next.controller) {
              $rootScope.flash.clearQueue();
              $rootScope.flash.setMessage(message.get('logged_in_only'));
            }
            $location.path('/');
          }

        });

        $rootScope.$on('$routeChangeSuccess', function() {
          currentUser.load(function(res){
            currentUser.setUser(res);
          }, function(err){
            currentUser.setUser(null);
          });
        });

        var isMenuItemActive = function(item){
          return $location.path() === item;
        };

        function getCurrentYear() {
          var date = new Date();
          return date.getFullYear();
        }

        $rootScope.global_loader = false;
        $rootScope.flash = flash;
        $rootScope.currentUser = currentUser;
        $rootScope.isLoggedIn = currentUser.isLoggedIn;
        $rootScope.logout = currentUser.logout;
        $rootScope.getUsername = currentUser.getUsername;
        $rootScope.getFullname = currentUser.getFullname;
        $rootScope.isMenuItemActive = isMenuItemActive;
        $rootScope.currentYear = getCurrentYear();
      }
    ]);
