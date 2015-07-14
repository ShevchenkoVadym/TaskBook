'use strict'

###*
# Current user service
###

angular.module('taskbookApp').factory 'CurrentUserService', [
  '$resource'
  '$location'
  '$http'
  '$rootScope'
  '$cookieStore'
  'HOST'
  ($resource, $location, $http, $rootScope, $cookieStore, HOST) ->
    User = $resource(HOST + 'rest/api/v1/users/:id', {},
      load: { method: 'GET', params: { id: 'current_user' }}
      login: { method: 'POST', params: { id: 'login' }}
      logout: { method: 'GET', url: HOST + '/j_spring_security_logout' })

    user = {}

    User.load {}, (data) ->
      angular.extend user, data
      
    userMethods =
      isLoggedIn: ->
        !!$cookieStore.get 'token' or !!user.username
      setUser: (data) ->
        user.roles = []
        if data
          $cookieStore.put 'token', 'token-stub'
          $cookieStore.put 'username', data.username
          angular.extend user, data
        else
          $cookieStore.remove 'token'
          $cookieStore.remove 'username'
          user.username = null

        return
      getUsername: ->
        user.username || $cookieStore.get 'username'
      getFullname: ->
        user.fullName
      getEmail: ->
        user.email
      getPasswordHash: ->
        user.password
      getEnabled: ->
        user.isEnabled
      getNonReadOnly: ->
        user.isNonReadOnly
      getRoles: ->
        user.roles
      isRegular: ->
        !_.intersection(user.roles, [
          'ADMIN'
          'MODERATOR'
        ]).length
      isAdmin: ->
        _.contains user.roles, 'ADMIN'
      isModerator: ->
        _.contains user.roles, 'MODERATOR'
      addRole: (role) ->
        user.roles.push role
        return
      setRoles: (roles) ->
        user.roles = roles
        return
      hasRole: (rolesToCheck) ->
        if user.roles == undefined
          false
        else
          _.intersection(user.roles, rolesToCheck).length > 0
      equalsTo: (name) ->
        username = user.username || $cookieStore.get 'username'
        if !name or !username
          false
        else
          username.toLowerCase() == name.toLowerCase()
      login: (params, cb, cbErr) ->
        User.login params, cb, cbErr
      logout: (message) ->
        message = message or 'До свидания!'
        User.logout()
        $cookieStore.remove 'token'
        $cookieStore.remove 'username'
        user = angular.extend({}, userMethods)
        $rootScope.flash.setMessage message
        $location.url '/'
      load: (cb, cbErr)->
        User.load {}, cb, cbErr

    angular.extend user, userMethods
    user
]