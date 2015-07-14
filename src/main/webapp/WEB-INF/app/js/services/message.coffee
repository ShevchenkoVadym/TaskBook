'use strict'

angular.module('taskbookApp')
.factory 'MessageService', [
  'Messages'
  (Messages) ->
    messages = Messages.query()

    messageMethods = get: (name) ->
      _.result _.find(messages, (el) ->
        el.name == name
      ), 'text'

    angular.extend messages, messageMethods

    messages
]