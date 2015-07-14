angular.module('taskbookApp')
.filter 'datestamp', [ ->
  (unixdate) ->
    if (!unixdate)
      return ''

    months = [
      'Янв',
      'Фев',
      'Мар',
      'Апр',
      'Май',
      'Июн',
      'Июл',
      'Авг',
      'Сен',
      'Окт',
      'Ноя',
      'Дек'
    ]
    date = new Date(unixdate)

    months[date.getMonth()] + ' ' + date.getDate() + ', ' + date.getFullYear()
]