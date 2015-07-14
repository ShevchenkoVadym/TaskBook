'use strict'
angular.module('taskbookApp')
.controller 'HomePageController', [
  '$scope'
  'UserService'
  'TaskService'
  ($scope, userService, taskService) ->
    defaultValues =
      users: []
      tasks: []

    angular.extend $scope, defaultValues

    userService.query {
      page: 0
      per_page: 10
    }, (object) ->
      $scope.users = object.content

    taskService.query {
      page: 0
      per_page: 10
    }, ((object) ->
      $scope.tasks = object.content
    ), null
]