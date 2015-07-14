'use strict'
angular.module('taskbookApp')
.factory 'flash', ($rootScope, $document) ->
  queue = []
  currentMessage = ''

  $rootScope.$on '$routeChangeSuccess', ->
    currentMessage = queue.shift() or ''

  angular.element($document[0].body).on 'click', ->
    currentMessage = queue.shift() or ''

  {
    setMessage: (message) ->
      queue.push message
    getMessage: ->
      currentMessage
    clearQueue: ->
      queue = []
  }