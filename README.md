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
3. Run it with <code>hadoop jar birdwatch-1.0-SNAPSHOT.jar input/ output/</code>

####Webserver
1. <code>npm install</code>
2. <code>node index.js</code>
3. Go to <code>http://localhost:3000</code>

####Contributors
- João Sampaio - [@joaoSampaio](https://github.com/joaosampaio)
- Nuno Nogueira - [@nunofmn](https://github.com/nunofmn)
- Pedro Braz - [@PeBraz](https://github.com/pebraz)

