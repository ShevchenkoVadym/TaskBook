'use strict'
angular.module('taskbookApp')
.controller 'TaskDetailController', [
  '$scope'
  '$routeParams'
  '$location'
  'TaskService'
  'ngDialog'
  'CurrentUserService'
  'MessageService'
  'AuthorizationService'
  'ModalService'
  'BASE_EDITOR'
  ($scope, $routeParams, $location, taskService, ngDialog, currentUser, message, authorize, ModalService, BASE_EDITOR) ->
    defaultValues =
      task: {}
      editorOptions: angular.copy BASE_EDITOR
      disqus_ready: false
      show_loader: false
      condStyle: 'min-height': '30px'
    angular.extend $scope, defaultValues
    $scope.editorOptions.autofocus = true

    taskService.get {
      id: $routeParams.id
      fields: [ 'id', 'tests', 'templateCode', 'taskName', 'tags', 'testEnvironment', 'condition', 'author',
                'level', 'rating', 'likedBy', 'dislikedBy', 'votersAmount', 'lifeStage'
      ]
    }, ((task) ->
      if task.lifeStage != 'APPROVED'
        authorize.taskEditorsOnly task.author

      $scope.task = task
      $scope.disqus_url = 'http://javapractice.disqus.com/' + task.id
      $scope.disqus_ready = true
      length = $scope.task.condition.length / 50
      length = length + ($scope.task.condition.match(/\n/g) or []).length
      length = Math.ceil(length)
      $scope.condStyle = 'min-height': length * 11 + 'px'
    ), (error) ->
      console.warn error
      $scope.flash.clearQueue()
      $scope.flash.setMessage message.get('task_not_found')
      $location.path '/tasks'

    check = (template) ->
      $scope.show_loader = true
      $scope.task.sourceCode = template
      result = undefined
      taskService.check({ statistic: true }, $scope.task, ((res) ->
          result = res.map
          result.random = Math.floor((Math.random() * 3) + 1)
        ), (error) ->
          console.warn error
          result = error.data.map or {
            compilation: 'NO_ANSWER'
            answer: error.data
          }
      ).$promise.finally ->
        ModalService.openTaskTestResult result
        $scope.show_loader = false

    hasLiked = ->
      _.contains $scope.task.likedBy, currentUser.getUsername()

    hasDisliked = ->
      _.contains $scope.task.dislikedBy, currentUser.getUsername()

    vote = (delta) ->
      taskService.vote {
        id: $scope.task.id
        username: currentUser.getUsername()
        delta: delta
      }, {}, (->
        $scope.task.rating = $scope.task.rating + delta
        if delta > 0
          $scope.task.likedBy.push currentUser.getUsername()
        else
          $scope.task.dislikedBy.push currentUser.getUsername()
        return
      ), (error) ->
        console.warn error

    deleteTask = (task) ->
      if !currentUser.isAdmin()
        return
      taskService.destroy { id: task.id }, (->
        $scope.flash.clearQueue()
        $scope.flash.setMessage message.get('successful_task_delete')
        $location.path '/tasks'
      ), null

    editorsOnly = ->
      currentUser.isAdmin() or currentUser.isModerator() or $scope.task.author == currentUser.getUsername()

    $scope.isEnabled = currentUser.getEnabled
    $scope.isNonReadOnly = currentUser.getNonReadOnly
    $scope.taskEditorsOnly = editorsOnly
    $scope.check = check
    $scope.hasLiked = hasLiked
    $scope.hasDisliked = hasDisliked
    $scope.vote = vote
    $scope.deleteTask = deleteTask
    return
]