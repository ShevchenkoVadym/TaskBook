'use strict'

### Выделяем метод загрузки файлов в отдельный сервис - на случай,
  если понадобиться загружать картинки к задачам, например.
  ВАЖНО: метод использует FormData, которая не поддерживается в
  InternetExplorer 9 и его более ранних версиях
###

angular.module('taskbookApp')
.service 'UploadService', [
  '$http'
  ($http) ->

    @send = (file) ->
      uploadUrl = 'http://deviantsart.com'
      fd = new FormData
      fd.append 'file', file
      $http.post uploadUrl, fd,
        transformRequest: angular.identity
        headers: 'Content-Type': undefined

    return
]