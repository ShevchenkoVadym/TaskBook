'use strict'

angular.module('taskbookApp')
.factory 'Country', [
  '$resource'
  ($resource) ->
    $resource 'data/countries.json', {}, query:
      method: 'GET'
      isArray: true
]

angular.module('taskbookApp')
.factory 'Messages', [
  '$resource'
  ($resource) ->
    $resource 'data/messages.json', {}, query:
      method: 'GET'
      isArray: true
]

angular.module('taskbookApp')
.factory 'Developers', [
  '$resource'
  ($resource) ->
    $resource 'data/developers.json', {}, query:
      method: 'GET'
      isArray: true
]