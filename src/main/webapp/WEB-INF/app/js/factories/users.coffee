'use strict'

angular.module('taskbookApp')
.factory 'UserService', [
  '$resource'
  'HOST'
  ($resource, HOST) ->
    BASE_PATH = HOST + 'rest/api/v1/users'

    $resource BASE_PATH + '/:id', { id: '@id' },
      query: method: 'GET'
      save: method: 'POST'
      update: method: 'PUT'
      destroy: method: 'DELETE'
      login: { method: 'POST', params: id: 'login' }
      recover: { method: 'POST', params: { id: 'recover', email: '@email' }}
      sendFeedback: { method: 'POST', url: BASE_PATH + '/feedback' }
      getUserTags: { method: 'GET', url: BASE_PATH + '/:id/tags', params: { id: '@id' }}
]