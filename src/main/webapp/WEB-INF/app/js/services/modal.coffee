'use strict'

angular.module('taskbookApp')
.factory 'ModalService', [
  'ngDialog'
  (ngDialog) ->
    @openTaskTestResult = (result)->
      ngDialog.open {
        template: 'partials/modals/task-test-result.html',
        data: result
      }

    @openContactForm = ->
      ngDialog.open template: 'partials/modals/contact-form.html'

    @openUserUpdateDialog = (scope)->
      ngDialog.open {
        template: 'partials/modals/update-user-profile.html'
        controller: 'UpdateUserController'
        scope: scope
      }

    @openImageUpdateDialog = (scope)->
      ngDialog.open {
        template: 'partials/modals/update-user-image.html'
        controller: 'UpdateUserImageController'
        scope: scope
      }

    @
]