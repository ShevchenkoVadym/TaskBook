'use strict'

angular.module('taskbookApp')
.service 'LoaderService', ->
		@toggle = ->
				angular.element('#global-loader').toggleClass 'loader-hidden'

		@isVisible = ->
				angular.element('#global-loader').hasClass 'loader-hidden'

		@