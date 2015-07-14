'use strict'

angular.module('taskbookApp')
.directive 'ngConfirmClick', [ -> {
  link: (scope, element, attr) ->
    msg = attr.ngConfirmClick or 'Are you sure?'
    clickAction = attr.confirmedClick
    element.bind 'click', ->
      if window.confirm(msg)
        scope.$eval clickAction
  }
]