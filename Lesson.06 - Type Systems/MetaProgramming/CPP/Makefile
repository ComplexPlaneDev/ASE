all: quantity.cpp
	c++ -std=c++17 -fcolor-diagnostics -fansi-escape-codes -O0 -g -o quantity.bin quantity.cpp

fail: quantity.cpp
	c++ -std=c++17 -DINVALID_OPERATION -fcolor-diagnostics -fansi-escape-codes -O0 -g -o quantity.bin quantity.cpp

clean:
	rm -f *.bin *.out
	rm -rf *.dSYM
