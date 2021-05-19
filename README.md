# Computer Database Project

## To generate a new project from this archetype

mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-webapp -DarchetypeVersion=1.4

## Run Maven

mvn exec:java -Dexec.mainClass="App"

## Insert h2 database 
RUNSCRIPT FROM 'computer-database-test.sql'
