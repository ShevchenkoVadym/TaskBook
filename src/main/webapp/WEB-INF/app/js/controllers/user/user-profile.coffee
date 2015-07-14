'use strict'
angular.module('taskbookApp')
.controller 'UserProfileController', [
  '$q'
  '$scope'
  '$routeParams'
  '$http'
  '$location'
  'UserService'
  'TaskService'
  'ModalService'
  'CurrentUserService'
  'Country'
  'LoaderService'
  ($q, $scope, $routeParams, $http, $location, User, taskService, modalService, currentUser, Country, loaderService) ->
    loaderService.toggle()

    defaultValues =
      tasksAdded: []
      tasksSolved: []
      itemsPerPage: 10
      currentPageAdded: 1
      currentPageSolved: 1
      predicate: 'rating'
      reverse: true
      tags: []
      show_loader: false

    angular.extend $scope, defaultValues
    user_name = $routeParams.username
    fields = ['id', 'taskName', 'rating', 'author', 'level', 'creationDate']

    User.get({
      id: user_name
    }, ((user) ->
      $scope.user = user
      return
    ), (error) ->
      console.warn error
      $scope.flash.clearQueue()
      $scope.flash.setMessage error.data
      $location.path '/users'
      return
    ).$promise.finally ->
      loaderService.toggle()

    if currentUser.equalsTo(user_name)
      $scope.profileTemplateUrl = 'partials/users/_as_user.html'
    else
      $scope.profileTemplateUrl = 'partials/users/_as_guest.html'

    User.getUserTags { id: user_name }, ((res) ->
      for key of res
        if key == '$promise'
          break
        $scope.tags.push
          name: key
          count: res[key]
      return
    ), (error) ->
      console.warn error

    requestTasks = (pageNum, type) ->
      sort = if $scope.reverse then '-' + $scope.predicate else $scope.predicate
      params =
        username: user_name
        page: pageNum - 1
        per_page: $scope.itemsPerPage
        sort: sort
        fields: fields
      switch type
        when 'added'
          taskService.addedByUser params, ((res) ->
            if res.content != undefined and res.content.length > 0
              $scope.tasksAdded = res.content
              $scope.totalItemsAdded = res.total
              $scope.currentPageAdded = res.pageable.page + 1
            return
          ), (error) ->
            console.warn error
        when 'solved'
          taskService.query params, ((res) ->
            if res.content != undefined and res.content.length > 0
              $scope.tasksSolved = res.content
              $scope.totalItemsSolved = res.total
              $scope.currentPageSolved = res.pageable.page + 1
            return
          ), (error) ->
            console.warn error

    requestTasks $scope.currentPageAdded, 'added'
    requestTasks $scope.currentPageSolved, 'solved'

    pageChanged = (type, pageNum) ->
      requestTasks pageNum, type
      return

    sortBy = (predicate, type) ->
      $scope.predicate = predicate
      $scope.reverse = !$scope.reverse
      if type == 'solved'
        requestTasks $scope.currentPageSolved, type
      else
        requestTasks $scope.currentPageAdded, type
      return

    updateDialog = ->
      modalService.openUserUpdateDialog $scope
      return

    updateImageDialog = ->
      modalService.openImageUpdateDialog $scope
      return

    $scope.pageChanged = pageChanged
    $scope.openUpdateDialog = updateDialog
    $scope.openImageUpdateDialog = updateImageDialog
    $scope.equalsTo = currentUser.equalsTo
    $scope.sortBy = sortBy
    $scope.countries = Country.query()
    return
]