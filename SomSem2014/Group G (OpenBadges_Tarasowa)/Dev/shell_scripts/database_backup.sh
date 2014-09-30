#!/bin/bash

MyUSER="backup"     # USERNAME
MyPASS="xnMpeQMwza4uGrEb"       # PASSWORD
MyHOST="localhost"          # Hostname

MYSQL="$(which mysql)"
MYSQLDUMP="$(which mysqldump)"
CHOWN="$(which chown)"
CHMOD="$(which chmod)"
GZIP="$(which gzip)"

DEST="/var/backups"

MBD="$DEST/mysql"

HOST="$(hostname)"

NOW="$(date +"%d-%m-%Y")"

# File to store current backup file
BACKUP_FILENAME=""
# Store list of databases
DB_LIST=""

# DO NOT BACKUP these databases
IGGY="test information_schema"

[ ! -d $MBD ] && mkdir -p $MBD || :
 
# Only root can access it!
$CHOWN 0.0 -R $DEST
$CHMOD 0600 $DEST

# Get all database list first
DB_LIST="$($MYSQL -u $MyUSER -h $MyHOST -p$MyPASS -Bse 'show databases')"

#echo $DB_LIST

for db in $DB_LIST
do
    skipdb=-1
    if [ "$IGGY" != "" ];
    then
	for i in $IGGY
	do
	    [ "$db" == "$i" ] && skipdb=1 || :
	done
    fi
 
    if [ "$skipdb" == "-1" ] ; then
	FILE="$MBD/$db.$HOST.$NOW.gz"
	# do all inone job in pipe,
	# connect to mysql using mysqldump for select mysql database
	# and pipe it out to gz file in backup dir :)
        $MYSQLDUMP -u $MyUSER -h $MyHOST -p$MyPASS $db | $GZIP -9 > $FILE
    fi
done
