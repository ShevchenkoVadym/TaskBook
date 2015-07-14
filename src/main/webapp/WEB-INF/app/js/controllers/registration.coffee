'use strict'
angular.module('taskbookApp')
.controller 'RegistrationController', [
  '$scope'
  '$location'
  'ngDialog'
  'UserService'
  'MessageService'
  ($scope, $location, ngDialog, User, message) ->

    signUp = (newUserDetails) ->
      newUser = angular.copy(newUserDetails)
      bcrypt = new bCrypt
      salt = bcrypt.gensalt(10)

      next = ->
        User.save(newUser).$promise.then (->
          $scope.formError = undefined
          ngDialog.closeAll()
          msg = message.get('successful_reg')

          if $location.path() == '/login'
            $scope.formNewUser.$setPristine()
            $scope.new_user = ''
            $scope.password_verify = ''
            $scope.formInfo = msg
          else
            $scope.flash.clearQueue()
            #убрать старое сообщение (если есть) из флеша
            $scope.flash.setMessage msg
            $location.url '/login'
        ), (error) ->
          console.warn error
          $scope.formInfo = undefined

          if error.status == 503
            $scope.formError = message.get('server_unavailable')
          else
            $scope.formError = error.data

      if google.loader.ClientLocation
        newUser.country = google.loader.ClientLocation.address.country
      else
        newUser.country = ''
      bcrypt.hashpw newUser.password, salt, (data) ->
        newUser.password = data
        next()

    $scope.signUp = signUp
]