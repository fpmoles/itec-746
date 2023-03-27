# ITEC-746
Example Data Access code for ITEC-746 Database Systems at the University of Kansas

## Directories explained
* __dat__ - contains sql files and scripts
  * _start_postgres.sh_ - starts a docker image running postgres, please note you should change the postgres password
  * _schema.sql_ - contains ddl to add tables to the database. 
  * _data.sql_ - contains dml to add data to the tables
  * see [instructions](dat/execution.md) for information on use
* __java-raw-jdbc__ - a projedt that connects to the database and serves data out of a REST api to show how to use raw JDBC.
  * Requires: JDK 17
  * Requires: Apache Maven
