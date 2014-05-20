all: 
	$(MAKE) -C src
	mv src/Test.jar ./ 

cleantest:
	rm -r diff obfuscated

clean:
	rm -f Test.jar
	cd src; $(MAKE) clean
