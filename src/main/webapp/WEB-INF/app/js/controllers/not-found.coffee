'use strict'
angular.module('taskbookApp')
.controller 'NotFoundController', [
  '$scope'
  'ModalService'
  ($scope, ModalService) ->
    $scope.openContactForm = ModalService.openContactForm
]