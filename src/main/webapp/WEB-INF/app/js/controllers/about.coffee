'use strict'
angular.module('taskbookApp')
.controller 'AboutController', [
  '$scope'
  '$location'
  'ModalService'
  'TaskService'
  'Developers'
  ($scope, $location, ModalService, TaskService, Developers) ->
    $scope.tasks = []
    $scope.developers = []

    Developers.query {}, (res) ->
      $scope.developers = _.shuffle res

    TaskService.query {page: 0, per_page: 30}, (res) ->
      $scope.totalTasks = res.total || 0
      $scope.tasks = res.content || []

    getLastTaskCount = ->
      last = String($scope.totalTasks).slice(-1)
      if last == 1
        return 1
      else if _.contains([2, 3, 4], last)
        return 2
      else
        return 3

    randomTask = ->
      if !$scope.tasks.length
        $location.path 'tasks'
      else
        task = $scope.tasks[Math.floor(Math.random()*$scope.tasks.length)]
        $location.path 'tasks/' + task.id

    $scope.getLastTaskCount = getLastTaskCount
    $scope.randomTask = randomTask
    $scope.openContactForm = ModalService.openContactForm
]