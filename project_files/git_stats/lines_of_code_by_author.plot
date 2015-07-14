set terminal png transparent size 640,240
set size 1.0,1.0

set terminal png transparent size 640,480
set output 'lines_of_code_by_author.png'
set key left top
set xdata time
set timefmt "%s"
set format x "%Y-%m-%d"
set grid y
set ylabel "Lines"
set xtics rotate
set bmargin 6
plot 'lines_of_code_by_author.dat' using 1:2 title "Slava Kisel" w lines, 'lines_of_code_by_author.dat' using 1:3 title "Lars" w lines, 'lines_of_code_by_author.dat' using 1:4 title "blacky0x0" w lines, 'lines_of_code_by_author.dat' using 1:5 title "Alex-e" w lines, 'lines_of_code_by_author.dat' using 1:6 title "Ilya Khromov" w lines, 'lines_of_code_by_author.dat' using 1:7 title "layo" w lines, 'lines_of_code_by_author.dat' using 1:8 title "Sergio Shapoval" w lines, 'lines_of_code_by_author.dat' using 1:9 title "silh" w lines, 'lines_of_code_by_author.dat' using 1:10 title "StanislavPonomarev" w lines, 'lines_of_code_by_author.dat' using 1:11 title "Hubertj" w lines, 'lines_of_code_by_author.dat' using 1:12 title "Slayfi" w lines, 'lines_of_code_by_author.dat' using 1:13 title "Anatoly Yashkin" w lines
