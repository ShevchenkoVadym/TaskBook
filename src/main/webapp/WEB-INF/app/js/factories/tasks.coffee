'use strict'

angular.module('taskbookApp')
.factory 'TaskService', [
  '$resource'
  'HOST'
  ($resource, HOST) ->
    BASE_PATH = HOST + 'rest/api/v1/tasks'

    $resource(BASE_PATH + '/:id', {},
      query: method: 'GET'
      save: method: 'POST'
      update: { method: 'PUT', url: BASE_PATH + '/:id', params: { id: '@id' }}
      destroy: method: 'DELETE'
      check: { method: 'POST', params: { id: 'test' }}
      tagsQuery: { method: 'GET', params: { id: 'tags' }, isArray: true }
      levelsQuery: { method: 'GET', params: { id: 'levels'}, isArray: true }
      envsQuery: { method: 'GET', params: { id: 'environment'}, isArray: true }
      vote: { method: 'POST', url: BASE_PATH + '/:id/vote', params:{ id: '@id', username: '@username', delta: '@delta' }}
      addedByUser: { method: 'GET', url: BASE_PATH + '/user/:username/added', params: { username: '@username' }}
      solvedByUserIds: { method: 'GET', url: BASE_PATH + '/user/:name/solved_ids', params: { name: '@name' }, isArray: true })
]