'use strict'

angular.module('taskbookApp')
.controller 'ContactController', [
  '$scope'
  'UserService'
  'MessageService'
  'CurrentUserService'
  ($scope, userService, message, currentUser) ->
    defaultValues =
      sendInProgress: false
      result:
        message: ''
        success: false
        done: false
      formData: {}

    submit = ->
      $scope.sendInProgress = true
      feedback =
        requester: $scope.formData.inputEmail
        subject: $scope.formData.inputSubject
        message: $scope.formData.inputMessage
      userService.sendFeedback(feedback).$promise.then((->
        $scope.result =
          message: message.get('feedback_success')
          success: true
          done: true
      ), ->
        $scope.result =
          message: message.get('feedback_error')
          success: false
          done: true
      ).finally ->
        $scope.sendInProgress = false

    angular.extend $scope, defaultValues

    if currentUser.isLoggedIn()
      $scope.formData.inputEmail = currentUser.getEmail()

    $scope.submit = submit
]