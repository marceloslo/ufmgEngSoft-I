full:
	make build
	make run

build: 
	cd "$(shell pwd)/src"  && javac UrnaElection.java

run: 
	cd "$(shell pwd)/src" && java UrnaElection

clean:
	rm **/*.class