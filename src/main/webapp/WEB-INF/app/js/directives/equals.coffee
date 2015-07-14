'use strict'
angular.module('taskbookApp')
.directive 'equals', -> {
restrict: 'A'
require: '?ngModel'
link: (scope, elem, attrs, ngModel) ->
  if !ngModel
    return

  # do nothing if no ng-model
  # watch own value and re-validate on change
  scope.$watch attrs.ngModel, ->
    validate()
    return
  # observe the other value and re-validate on change
  attrs.$observe 'equals', (val) ->
    validate()
    return

  validate = ->
    # values
    val1 = ngModel.$viewValue
    val2 = attrs.equals
    # set validity
    ngModel.$setValidity 'equals', !val1 or !val2 or val1 == val2
    return

}