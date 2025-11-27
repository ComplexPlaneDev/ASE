all: main.cpp shared_ptr.cpp allocator.cpp
	c++ -std=c++17 -fcolor-diagnostics -fansi-escape-codes -O0 -g -o main.bin main.cpp
	c++ -std=c++17 -fcolor-diagnostics -fansi-escape-codes -O0 -g -o shared_ptr.bin shared_ptr.cpp
	c++ -std=c++17 -fcolor-diagnostics -fansi-escape-codes -O0 -g -o allocator.bin allocator.cpp

clean:
	rm -f *.bin *.out
	rm -rf *.dSYM
