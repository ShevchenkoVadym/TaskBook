'use strict'

angular.module('taskbookApp')
.constant 'HOST', ''
.constant 'BASE_EDITOR', {
		lineWrapping: true
		lineNumbers: true
		tabSize: 2
		smartIndent: true
		mode: 'text/x-java'
		height: 'dynamic'
		extraKeys: { "Ctrl-Space": "autocomplete" }
}