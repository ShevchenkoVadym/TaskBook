'use strict'

### Ангулярная ng-model не работает с input данными типа "файл"
 поэтому необходимо создать кастомную директиву для бинда
 файлов к переменным
###

angular.module('taskbookApp')
.directive 'fileModel', [
  '$parse'
  ($parse) ->
    {
    restrict: 'A'
    link: (scope, element, attrs) ->
      model = $parse(attrs.fileModel)
      modelSetter = model.assign
      element.bind 'change', ->
        scope.$apply ->
          modelSetter scope, element[0].files[0]
    }
]