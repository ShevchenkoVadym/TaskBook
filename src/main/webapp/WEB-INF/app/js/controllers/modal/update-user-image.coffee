# Created by prima on 19.03.15.

'use strict'
angular.module('taskbookApp')
.controller 'UpdateUserImageController', [
  '$scope'
  '$http'
  'UserService'
  'MessageService'
  'UploadService'
  ($scope, $http, User, message, uploadService) ->

    uploadUserImage = (file) ->
      $scope.show_loader = true

      reg = /^.*\.(jpg|jpeg|gif|png)$/

      if file == undefined or !reg.test(file.name.toLowerCase())
        $scope.formError = message.get('incorrect_img_format') or 'error'
        $scope.show_loader = false
        $scope.userImg = undefined
      else
        uploadService.send(file).success((res) ->
          $scope.user.imageUrl = res.url

          User.update({ id: $scope.user.username }, $scope.user, (->
            $scope.formError = undefined
          ), (error) ->
            console.warn error
          ).$promise.finally ->
            $scope.show_loader = false
        ).error (err) ->
          console.warn err
          $scope.formError = message.get('incorrect_img_format') or 'error'
          $scope.show_loader = false

    deleteImage = ->
      temp = $scope.user.imageUrl
      $scope.formError = undefined
      $scope.show_loader = true
      $scope.user.imageUrl = ''

      User.update({ id: $scope.user.username }, $scope.user, (->
        $scope.user.imageUrl = ''
        $scope.closeThisDialog()
      ), (error) ->
        $scope.user.imageUrl = temp
        $scope.formError = message.get('unknown_error')
        console.warn error
      ).$promise.finally ->
        $scope.show_loader = false

    $scope.uploadUserImage = uploadUserImage
    $scope.deleteImage = deleteImage
]