###*
# Created by prima on 10.03.15.
###

angular.module('taskbookApp')
.factory 'AuthorizationService', [
  '$location'
  '$rootScope'
  'CurrentUserService'
  'MessageService'
  ($location, $rootScope, currentUser, message) ->

    {
      taskCreatorsOnly: ->
        if !currentUser.getEnabled()
          $location.path '/'
        else
          return 'authorized'
        return

      taskEditorsOnly: (author) ->
        if !currentUser.hasRole([ 'MODERATOR', 'ADMIN' ]) and !currentUser.equalsTo(author)
          if _.contains($location.path(), 'edit')
            $location.path '/'
            return
          else
            $rootScope.flash.clearQueue()
            $rootScope.flash.setMessage message.get('task_verification')
            $location.path '/tasks'
            return
        'authorized'
    }
]