angular.module('taskbookApp')
.filter 'timestamp', [ ->
  (unixdate) ->
    if (!unixdate)
      return ''

    date = new Date(unixdate)
    hours = if date.getHours() < 10 then '0' + date.getHours() else date.getHours()
    minutes = if date.getMinutes() < 10 then '0' + date.getMinutes() else date.getMinutes()
    seconds = if date.getSeconds() < 10 then '0' + date.getSeconds() else date.getSeconds()

    hours + ':' + minutes + ':' + seconds
]