'use strict'
angular.module('taskbookApp')
.controller 'LoginController', [
  '$scope'
  '$http'
  '$location'
  '$route'
  'ngDialog'
  'UserService'
  'CurrentUserService'
  'MessageService'
  ($scope, $http, $location, $route, ngDialog, userService, currentUser, message) ->
    $scope.show_loader = false

    signIn = (userDetails) ->
      $scope.show_loader = true
      currentUser.login(userDetails, ((res) ->
        $scope.formError = undefined
        ngDialog.closeAll()
        currentUser.setUser res

        if $location.path() == '/login'
          $location.url '/users/' + userDetails.username
        else if $location.path() == '/users/' + userDetails.username
          setTimeout (->
            $route.reload()
          ), 1000
        ), (error) ->
          console.warn error
          if error.status == 503
            $scope.formError = message.get('server_unavailable')
          else
            $scope.formError = error.data
        ).$promise.finally ->
          $scope.show_loader = false

    recoverPassword = (userEmail) ->
      $scope.show_loader = true
      userService.recover({ email: userEmail }, (->
        $scope.formError = undefined
        $scope.formInfo = message.get('started_password_recovery')
      ), (error) ->
        console.warn error
        $scope.formInfo = undefined

        if error.status == 503
          $scope.formError = message.get('server_unavailable')
        else
          $scope.formError = error.data
        return
      ).$promise.finally ->
        $scope.show_loader = false

    $scope.signIn = signIn
    $scope.recoverPassword = recoverPassword
]