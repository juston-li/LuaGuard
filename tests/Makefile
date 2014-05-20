all: 
	$(MAKE) -C src
	mv src/Test.jar ./ 

cleantest:
	rm -r output

clean:
	rm -f Test.jar
	cd src; $(MAKE) clean
