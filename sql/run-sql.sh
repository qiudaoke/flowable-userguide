#!/bin/bash
export MAVEN_OPTS="-Xms521M -Xmx1024M"
if [[ "$1" == "" ]] ; then

   	echo "Missing argument: correct usage <./run-sql.sh database>"
   	
else
	DATABASE=$1
    echo "Database type: $DATABASE"
    
    cd flowable-sql-6.0.0
    if [[ "$DATABASE" == "oracle" ]] ; then
    	mvn -PgenerateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=5.99.0 -DnewVersion=6.0.0 clean install
    	mvn -PcombineUpgradeSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=5.99.0 -DnewVersion=6.0.0 clean install
    else
    	mvn -PgenerateSql -Ddatabase=$DATABASE -DoldVersion=5.99.0 -DnewVersion=6.0.0 clean install
    	mvn -PcombineUpgradeSql -Ddatabase=$DATABASE -DoldVersion=5.99.0 -DnewVersion=6.0.0 clean install
    fi
    
    cd ..
    
    cd flowable-sql-6.0.1
    if [[ "$DATABASE" == "oracle" ]] ; then
    	mvn -PgenerateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.0.0 -DnewVersion=6.0.1 clean install
    	mvn -PcombineUpgradeSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.0.0 -DnewVersion=6.0.1 clean install
    else
    	mvn -PgenerateSql -Ddatabase=$DATABASE -DoldVersion=6.0.0 -DnewVersion=6.0.1 clean install
    	mvn -PcombineUpgradeSql -Ddatabase=$DATABASE -DoldVersion=6.0.0 -DnewVersion=6.0.1 clean install
    fi
    
    cd ..
    
    cd flowable-sql-6.1.0
    if [[ "$DATABASE" == "oracle" ]] ; then
    	mvn -PgenerateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.0.1 -DnewVersion=6.1.0 clean install
    	mvn -PcombineUpgradeSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.0.1 -DnewVersion=6.1.0 clean install
    else
    	mvn -PgenerateSql -Ddatabase=$DATABASE -DoldVersion=6.0.1 -DnewVersion=6.1.0 clean install
    	mvn -PcombineUpgradeSql -Ddatabase=$DATABASE -DoldVersion=6.0.1 -DnewVersion=6.1.0 clean install
    fi
    
    cd ..
    
    cd flowable-sql-6.1.1
    if [[ "$DATABASE" == "oracle" ]] ; then
    	mvn -PgenerateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.1.0 -DnewVersion=6.1.1 clean install
    	mvn -PcombineUpgradeSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.1.0 -DnewVersion=6.1.1 clean install
    else
    	mvn -PgenerateSql -Ddatabase=$DATABASE -DoldVersion=6.1.0 -DnewVersion=6.1.1 clean install
    	mvn -PcombineUpgradeSql -Ddatabase=$DATABASE -DoldVersion=6.1.0 -DnewVersion=6.1.1 clean install
    fi
    
    cd ..
    
    cd flowable-sql-6.1.2
    if [[ "$DATABASE" == "oracle" ]] ; then
    	mvn -PgenerateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.1.1 -DnewVersion=6.1.2 clean install
    	mvn -PcombineUpgradeSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.1.1 -DnewVersion=6.1.2 clean install
    else
    	mvn -PgenerateSql -Ddatabase=$DATABASE -DoldVersion=6.1.1 -DnewVersion=6.1.2 clean install
    	mvn -PcombineUpgradeSql -Ddatabase=$DATABASE -DoldVersion=6.1.1 -DnewVersion=6.1.2 clean install
    fi
    
    cd ..
    
    cd flowable-sql-6.2.0
    if [[ "$DATABASE" == "oracle" ]] ; then
    	mvn -PgenerateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.1.2 -DnewVersion=6.2.0 clean install
    	mvn -PcombineUpgradeSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.1.2 -DnewVersion=6.2.0 clean install
    else
    	mvn -PgenerateSql -Ddatabase=$DATABASE -DoldVersion=6.1.2 -DnewVersion=6.2.0 clean install
    	mvn -PcombineUpgradeSql -Ddatabase=$DATABASE -DoldVersion=6.1.2  -DnewVersion=6.2.0 clean install
    fi
    
    cd ..
    
    cd flowable-sql-6.2.1
    if [[ "$DATABASE" == "oracle" ]] ; then
    	mvn -PgenerateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.2.0 -DnewVersion=6.2.1 clean install
    	mvn -PcombineUpgradeSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.2.0 -DnewVersion=6.2.1 clean install
    else
    	mvn -PgenerateSql -Ddatabase=$DATABASE -DoldVersion=6.2.0 -DnewVersion=6.2.1 clean install
    	mvn -PcombineUpgradeSql -Ddatabase=$DATABASE -DoldVersion=6.2.0 -DnewVersion=6.2.1 clean install
    fi
    
    cd ..
    
    cd flowable-sql-6.3.0
    if [[ "$DATABASE" == "oracle" ]] ; then
    	mvn -PgenerateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.2.1 -DnewVersion=6.3.0 clean install
    	mvn -PcombineUpgradeSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.2.1 -DnewVersion=6.3.0 clean install
    else
    	mvn -PgenerateSql -Ddatabase=$DATABASE -DoldVersion=6.2.1 -DnewVersion=6.3.0 clean install
    	mvn -PcombineUpgradeSql -Ddatabase=$DATABASE -DoldVersion=6.2.1 -DnewVersion=6.3.0 clean install
    fi
    
    cd ..
        
	cd flowable-sql-6.3.1
    if [[ "$DATABASE" == "oracle" ]] ; then
    	mvn -PgenerateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.3.0 -DnewVersion=6.3.1 clean install
    	mvn -PcombineUpgradeSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.3.0 -DnewVersion=6.3.1 clean install
    else
    	mvn -PgenerateSql -Ddatabase=$DATABASE -DoldVersion=6.3.0 -DnewVersion=6.3.1 clean install
    	mvn -PcombineUpgradeSql -Ddatabase=$DATABASE -DoldVersion=6.3.0 -DnewVersion=6.3.1 clean install
    fi
    
    cd .. 
	 
	cd flowable-sql-6.4.0
    if [[ "$DATABASE" == "oracle" ]] ; then
    	mvn -PgenerateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.3.1 -DnewVersion=6.4.0 clean install
    	mvn -PcombineUpgradeSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.3.1 -DnewVersion=6.4.0 clean install
    else
    	mvn -PgenerateSql -Ddatabase=$DATABASE -DoldVersion=6.3.1 -DnewVersion=6.4.0 clean install
    	mvn -PcombineUpgradeSql -Ddatabase=$DATABASE -DoldVersion=6.3.1 -DnewVersion=6.4.0 clean install
    fi
    
    cd ..
    
    cd flowable-sql-6.4.1
    if [[ "$DATABASE" == "oracle" ]] ; then
    	mvn -PgenerateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.4.0 -DnewVersion=6.4.1 clean install
    	mvn -PgenerateCreateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT clean install
    	mvn -PcombineUpgradeSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT -DoldVersion=6.4.0 -DnewVersion=6.4.1 clean install
    	mvn -PcombineCreateSql -Ddatabasewithschema=$DATABASE -Duser.timezone=GMT clean install
    else
    	mvn -PgenerateSql -Ddatabase=$DATABASE -DoldVersion=6.4.0 -DnewVersion=6.4.1 clean install
    	mvn -PgenerateCreateSql -Ddatabase=$DATABASE clean install
    	mvn -PcombineUpgradeSql -Ddatabase=$DATABASE -DoldVersion=6.4.0 -DnewVersion=6.4.1 clean install
    	mvn -PcombineCreateSql -Ddatabase=$DATABASE clean install
    fi
    
    cd ..
fi
