'use strict'
angular.module('taskbookApp')
.controller 'UsersController', [
  '$scope'
  'UserService'
  'CurrentUserService'
  ($scope, User, currentUser) ->
    defaultValues =
      users: []
      itemsPerPage: 10
      currentPage: 1
      roles: [
        'ADMIN'
        'MODERATOR'
        'USER'
      ]
      updated_user: ''
      predicate: 'rating'
      reverse: true
      nameQuery: ''
    angular.extend $scope, defaultValues

    requestUsers = (pageNum, perPage) ->
      sort = if $scope.reverse then '-' + $scope.predicate else $scope.predicate
      User.query {
        page: pageNum - 1
        per_page: perPage
        sort: sort
        name_query: $scope.nameQuery
      }, (object) ->
        $scope.users = object.content or []
        $scope.totalItems = object.total or 0
        if !object.pageable
          $scope.currentPage = 1
        else
          $scope.currentPage = object.pageable.page + 1

    requestUsers $scope.currentPage, $scope.itemsPerPage

    pageChanged = (currentPage) ->
      $scope.currentPage = currentPage or 1
      requestUsers $scope.currentPage, $scope.itemsPerPage

    change = ->
      $scope.updated_user = ''

    changeRole = (user) ->
      if !currentUser.isAdmin()
        return
      userToUpdate =
        username: user.username
        roles: user.roles
        isEnabled: user.isEnabled
      User.update({ id: userToUpdate.username }, userToUpdate).$promise.then (->
        $scope.updated_user = userToUpdate.username
      ), (error) ->
        console.warn error.data
        $scope.updated_user = ''

    banUser = (user) ->
      if !currentUser.isAdmin()
        return
      userToUpdate =
        username: user.username
        roles: user.roles
        isEnabled: if user.isEnabled then false else true
      User.update({ id: userToUpdate.username }, userToUpdate).$promise.then (->
        user.isEnabled = userToUpdate.isEnabled
      ), (error) ->
        console.warn error.data

    deleteUser = (user) ->
      if !currentUser.isAdmin()
        return
      User.destroy { id: user.username }, (->
        index = $scope.users.indexOf(user)
        $scope.users.splice index, 1
        pageChanged $scope.currentPage
      ), (error) ->
        console.warn error.data

    sortBy = (predicate) ->
      $scope.predicate = predicate
      $scope.reverse = !$scope.reverse
      requestUsers $scope.currentPage, $scope.itemsPerPage
      return

    resetFilterName = ->
      $scope.nameQuery = ''
      requestUsers $scope.currentPage, $scope.itemsPerPage
      return

    $scope.pageChanged = pageChanged
    $scope.change = change
    $scope.changeRole = changeRole
    $scope.banUser = banUser
    $scope.deleteUser = deleteUser
    $scope.sortBy = sortBy

    $scope.isAuthorized = ->
      currentUser.hasRole [ 'ADMIN' ]

    $scope.equalsTo = currentUser.equalsTo
    $scope.resetFilterName = resetFilterName
]