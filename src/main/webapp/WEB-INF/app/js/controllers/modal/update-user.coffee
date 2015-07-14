###*
# Created by prima on 02.04.15.
###

angular.module('taskbookApp')
.controller 'UpdateUserController', [
  '$q'
  '$scope'
  'UserService'
  'MessageService'
  'CurrentUserService'
  ($q, $scope, User, message, currentUser) ->
    bcrypt = new bCrypt

    updateUser = (userDetails, current_password, new_password) ->
      if !currentUser.equalsTo($scope.user.username)
        return
      if current_password != undefined and current_password.length > 0
        if new_password != undefined and new_password.length > 0
          # Если новый и старый пароль заданы
          # Хешируем введенный текущий пароль
          salt = null
          crypt(current_password, currentUser.getPasswordHash()).then (pass) ->
            # проверяем на совпадение с паролем залогиненого пользователя
            if pass == currentUser.getPasswordHash()
              # хешируем новый пароль и записываем его в объект юзер
              salt = bcrypt.gensalt(10)
              crypt(new_password, salt).then (pass) ->
                userDetails.password = pass
                performUpdate userDetails
                return
            else
              $scope.formError = message.get('incorrect_current_password')
              console.log $scope.formError
        else
          $scope.formError = message.get('empty_new_password')
          console.log $scope.formError
      else
        performUpdate userDetails
      return

    crypt = (password, salt) ->
      deferred = $q.defer()

      encrypt = (call) ->
        bcrypt.hashpw password, salt, (data) ->
          call data
          return

      encrypt (pass) ->
        deferred.resolve pass

      deferred.promise

    performUpdate = (userDetails) ->
      User.update { id: userDetails.username }, userDetails, (->
        $scope.formError = undefined
        $scope.closeThisDialog()
      ), (error) ->
        console.warn error
        if error.status == 503
          $scope.formError = message.get('server_unavailable')
        else
          $scope.formError = error.data

    $scope.updateUser = updateUser
    return
]