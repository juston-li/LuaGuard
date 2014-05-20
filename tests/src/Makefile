all: classes
	jar cfe Test.jar Test Test.class TestOutput.class

classes: Test.java TestOutput.java
	javac Test.java TestOutput.java

clean:
	rm -f *.class Test.jar
