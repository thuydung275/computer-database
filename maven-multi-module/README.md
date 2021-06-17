### generate parent project
```
mvn archetype:generate -DgroupId=com.excilys.cdb -DartifactId=maven-multi-module
```

### change packaging to pom in the pom.xml file located in the parent's directory
```
<packaging>pom</packaging>
```

### change packaging to war in the pom.xml file located in the submodules directory
```
<packaging>war</packaging>
```

### generate submodules
```
cd maven-multi-module
mvn archetype:generate -DgroupId=com.excilys.cdb -DartifactId=core
mvn archetype:generate -DgroupId=com.excilys.cdb -DartifactId=persistence
mvn archetype:generate -DgroupId=com.excilys.cdb -DartifactId=service
mvn archetype:generate -DgroupId=com.excilys.cdb -DartifactId=binding
mvn archetype:generate -DgroupId=com.excilys.cdb -DartifactId=webapp
mvn archetype:generate -DgroupId=com.excilys.cdb -DartifactId=console
```

### to share all the configuration with our submodules, in their pom.xml files, we'll have to declare the parent
```<parent>
    <groupId>com.excilys.cdb</groupId>
    <artifactId>maven-multi-module</artifactId>
    <version>1.0-SNAPSHOT</version>
</parent>
```

### build the project in the parent's directory
```
mvn package
```