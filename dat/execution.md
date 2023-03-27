# Execution of Local Database 

* ensure you have docker running on your machine
* If you are running on Windows, you will need to modify the directories in `start_postgres.sh` to match Windows formatting
* Execute `$ start_postgres.sh`
* Exec into docker container to apply `schema.sql` & `data.sql`
  * `docker exec -it local-pg sh`
  * `psql -U postgres -h localhost`
  * simply copy and paste contents of schema.sql then data.sql into the terminal
  * Alternately, you can copy the files to the image and run them there
