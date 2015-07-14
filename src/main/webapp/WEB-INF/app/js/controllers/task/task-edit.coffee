'use strict'
angular.module('taskbookApp')
.controller 'TaskEditController', [
  '$scope'
  '$routeParams'
  '$location'
  'TaskService'
  'ngDialog'
  'CurrentUserService'
  'AuthorizationService'
  'ModalService'
  'LoaderService'
  'BASE_EDITOR'
  ($scope, $routeParams, $location, taskService, ngDialog, currentUser, authorize, ModalService, loaderService, BASE_EDITOR) ->
    loaderService.toggle()

    defaultValues = {
      show_global_loader: false
      task_state: 'new'
      show_publish_loader: false
      show_test_loader: false
      editorOptions: BASE_EDITOR
      libs: [ 'JAVA_JUNIT', 'JAVA_TESTNG' ]
      levels: [ 'BEGINNER', 'JUNIOR', 'MIDDLE', 'SENIOR' ]
      lifeStages: [ 'DRAFT', 'VERIFICATION', 'APPROVED', 'REJECTED', 'HIDDEN' ]
      tags: []
    }

    angular.extend $scope, defaultValues

    fields = [
      'id'
      'sourceCode'
      'tests'
      'templateCode'
      'taskName'
      'tags'
      'testEnvironment'
      'condition'
      'level'
      'author'
      'lifeStage'
    ]

    task_id = $routeParams.id

    taskService.tagsQuery {}, (res) ->
      $scope.tags = res
    , (error) ->
      console.warn error

    if !task_id
      $scope.task = {
        author: currentUser.getUsername()
        testEnvironment: 'JAVA_JUNIT'
        level: 'BEGINNER'
        lifeStage: if currentUser.isAdmin() or currentUser.isModerator() then 'APPROVED' else 'VERIFICATION'
        tags: []
        topLevelCommentsId: []
      }
      loaderService.toggle()

    else
      $scope.show_global_loader = true

      taskService.get({
        id: task_id
        fields: fields
      }, ((task) ->
        authorize.taskEditorsOnly task.author
        $scope.task = task
        $scope.task_state = 'existed'
      ), (error) ->
        console.warn error
        $location.path '/tasks'
      ).$promise.finally ->
        loaderService.toggle()

    publish = (task) ->
      $scope.show_publish_loader = true;
      result = undefined

      if !task.id
        taskService.save(task, ((res) ->
            $scope.task = res.task
            result = res.map
            result.statusCode = 201
          ), (error) ->
            console.warn error
            result =
              compilation: 'NO_ANSWER'
              answer: error.data
              statusCode: error.status
        ).$promise.finally ->
          ModalService.openTaskTestResult result
          $scope.show_publish_loader = false
      else
        result = undefined

        taskService.update(task, ((res) ->
            $scope.task = res
            result =
              compilation: 'OK'
              tests: 'OK'
              statusCode: 200
          ), (error) ->
            console.warn error
            result = error.data.map or
            compilation: 'NO_ANSWER'
            answer: error.data
        ).$promise.finally ->
          ModalService.openTaskTestResult result
          $scope.show_publish_loader = false

    check = (task) ->
      $scope.show_test_loader = true
      $scope.message = undefined
      result = undefined

      taskService.check({ statistic: false }, task, ((res) ->
          result = res.map
          result.random = Math.floor(Math.random() * 3 + 1)
        ), (error) ->
          console.warn error
          result = error.data.map or { compilation: 'NO_ANSWER', answer: error.data }
      ).$promise.finally ->
        ModalService.openTaskTestResult result
        $scope.show_test_loader = false

    $scope.check = check
    $scope.publish = publish
    $scope.isAdmin = currentUser.isAdmin
    $scope.isRegular = currentUser.isRegular
]