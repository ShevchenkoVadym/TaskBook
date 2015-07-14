#!/usr/bin/env bash
# for jelastic
mysqldump -uroot -pyi690dNqHD test > /var/lib/jelastic/backup/mysql/$( date +"%Y-%m-%d_%H-%M-%S" ).sql
mongoexport --db TaskBook --collection task --out /var/lib/jelastic/backup/mongo/$( date +"%Y-%m-%d_%H-%M-%S" ).json

# mongo import
# mongoimport --db TaskBook --collection task --file путь к файлу

# Cron tasks:
0 5 * * * /var/lib/jelastic/backup/dump.sh # every day at 5 am
*/5 * * * * /opt/shared/home/scripts/clean_test.sh # once in 5 min

# Clean test: delete all created more than min ago
find /opt/shared/home/test_utility/lib/testTask -mmin +1 -exec rm -f {} \;