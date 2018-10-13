


all: run

run: ttt-server
	LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/lib ./ttt-server

ttt-server: ttt-server.c
	gcc -o ttt-server ttt-server.c -lulfius -ljansson

copy_needed_libs:
	mkdir -p libs
	sudo cp /usr/local/lib/libjansson.so.4 ./libs/
	sudo cp /usr/local/lib/libmicrohttpd.so.12 ./libs/
	sudo cp /usr/local/lib/libulfius.so.2.3 ./libs/
	sudo cp /usr/lib/libyder.so.1.2.0 ./libs/
	sudo cp /usr/lib/liborcania.so.1.2.0 ./libs/

make_docker: ttt-server copy_needed_libs
	# Copy systems libs into a local dir so that we can include them
	# in the Docker container
	sudo docker build -t ttt-server .

run_docker:
	sudo docker run -i -t -p 8080:8080 week14-server:latest ./ttt-server

clean:
	sudo docker images -a | grep "ttt-server" |awk '{print $$3}' | xargs sudo docker rmi -f ttt-server 
	rm -rf ./ttt-server ./libs