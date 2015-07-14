'use strict'
angular.module('taskbookApp')
.controller 'TasksController', [
  '$q'
  '$scope'
  '$location'
  'TaskService'
  'CurrentUserService'
  ($q, $scope, $location, taskService, currentUser) ->
    defaultValues =
      tasks: []
      itemsPerPage: 10
      currentPage: 1
      tags: []
      levels: []
      predicate: 'rating'
      reverse: true
      filter: {}
      multipleTags: tags: []
      multipleLevels: levels: []
      solvedIds: []
      solvedCriteria: 'all'

    init = ->
      tags = undefined
      levels = undefined
      tag_params = $location.search().tags
      level_params = $location.search().levels
      if tag_params != 'undefined'
        if typeof tag_params == 'string' then (tags = [ tag_params ]) else (tags = tag_params)
        $scope.multipleTags.tags = tags
      if level_params != 'undefined'
        if typeof level_params == 'string' then (levels = [ level_params ]) else (levels = level_params)
        $scope.multipleLevels.levels = levels
      queryPromises = [
        taskService.levelsQuery({}, ((res) ->
          $scope.levels = res
        ), (error) ->
          console.warn error
        ).$promise
        taskService.tagsQuery({}, ((res) ->
            $scope.tags = res
        ), (error) ->
          console.warn error
        ).$promise
      ]
      $q.all(queryPromises).then ->
        requestTasks $scope.currentPage, $scope.itemsPerPage
        if currentUser.isLoggedIn()
          taskService.solvedByUserIds { name: currentUser.getUsername() }, ((res) ->
            $scope.solvedIds = res
          ), (error) ->
            console.warn error

    angular.extend $scope, defaultValues

    requestTasks = (pageNum, perPage) ->
      sort = if $scope.reverse then '-' + $scope.predicate else $scope.predicate
      inverse = null
      username = currentUser.getUsername()
      switch $scope.solvedCriteria
        when 'solved'
          inverse = false
        when 'not_solved'
          inverse = true
        else
          username = null
      taskService.query {
        page: pageNum - 1
        per_page: perPage
        sort: sort
        levels: $scope.multipleLevels.levels
        tags: $scope.multipleTags.tags
        username: username
        inverse: inverse
      }, ((object) ->
        $scope.tasks = object.content or []
        $scope.totalItems = object.total or 0
        if !object.pageable
          $scope.currentPage = 1
        else
          $scope.currentPage = object.pageable.page + 1
      ), (error) ->
        console.warn error

    init()

    pageChanged = (currentPage) ->
      $scope.currentPage = currentPage or 1
      requestTasks $scope.currentPage, $scope.itemsPerPage

    deleteTask = (task) ->
      if !currentUser.isAdmin()
        return
      taskService.destroy { id: task.id }, (->
        index = $scope.tasks.indexOf(task)
        $scope.tasks.splice index, 1
        pageChanged $scope.currentPage
      ), null

    sortBy = (predicate) ->
      $scope.predicate = predicate
      $scope.reverse = !$scope.reverse
      requestTasks $scope.currentPage, $scope.itemsPerPage
      return

    isSolved = (id) ->
      if !currentUser.isLoggedIn()
        return false
      _.contains $scope.solvedIds, id

    $scope.deleteTask = deleteTask
    $scope.pageChanged = pageChanged
    $scope.sortBy = sortBy

    $scope.isAuthorized = ->
      currentUser.hasRole [
        'MODERATOR'
        'ADMIN'
      ]

    $scope.isAdmin = currentUser.isAdmin
    $scope.isSolved = isSolved
]