#
# Скрипт для генерации json массива стран на основе папки с изображениями флагов
#
output = File.new('../html/partials/countries.json', "w")
output.puts('[')

temp = Array.new

Dir.foreach('../html/img/flags/16/') do |flag|
  temp << flag.gsub('.png', '').to_s
end

# отсортировать в алфавитном порядке
temp.sort_by!{ |item| item.downcase }

# убрать лишнее
not_needed = ["OPEC", "CIS", "NATO", "CARICOM", "African Union(OAS)", "Olimpic Movement", "Red Cross", "ASEAN", "Antarctica", "Ukraine", "Russian Federation", "Belarus", "Kazakhstan", ".", ".."]
temp = temp - not_needed

# переместить страны наверх списка
temp.unshift("Ukraine")
temp.unshift("Kazakhstan")
temp.unshift("Belarus")
temp.unshift("Russian Federation")

# записать json
temp.each do |item|
  output.puts('  "' + item + '",')
end

output.puts(']')
output.close
