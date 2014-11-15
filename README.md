Birdwatch
=========
Cloud Computing application

###Requirements
- JDK 1.7
- Maven 3.2.3
- Node.js 0.10.26 or higher

###How to run
####Log Generator
1. <code>npm install</code>
2. <code>node birdgenerator 10000</code> (generates a log file with 10000 entries)

####MapReduce app
1. <code>mvn package</code>
2. Move the resulting jar in the <code>target/</code> directory
3. Download the following jar: [AWS Java SDK](http://sdk-for-java.amazonwebservices.com/latest/aws-java-sdk.zip), [jackson-core](http://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.4.3/jackson-core-2.4.3.jar), [jackson-databind](http://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.4.3/jackson-databind-2.4.3.jar), [jackson-annotations](http://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.4.3/jackson-annotations-2.4.3.jar) 
and add them to the <code>lib/</code> folder in Hadoop installation. 
4. Add the jars in lib to the <code>HADOOP_CLASSPATH</code> environment variable.
5. Set the <code>AWS_ACCESS_KEY_ID</code> and <code>AWS_ACCESS_SECRET_ACCESS_KEY</code> environment variables.
6. Run it with <code>hadoop jar birdwatch-1.0-SNAPSHOT.jar input/</code>

####Webserver
1. <code>npm install</code>
2. <code>node index.js</code>
3. Go to <code>http://localhost:3000</code>

####Contributors
- João Sampaio - [@joaoSampaio](https://github.com/joaosampaio)
- Nuno Nogueira - [@nunofmn](https://github.com/nunofmn)
- Pedro Braz - [@PeBraz](https://github.com/pebraz)

