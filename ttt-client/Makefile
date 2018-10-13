all: run

run: ttt-client
				java -cp bin:./libs/* edu.northeastern.cs5007.TTTClient

ttt-client: src/edu/northeastern/cs5007/TTTClient.java
						javac -Xlint:deprecation -nowarn -d bin -sourcepath src -cp ./libs/*:. src/edu/northeastern/cs5007/TTTClient.java

clean: 

				rm -rf bin/*