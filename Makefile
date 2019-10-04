all: clean A2C

clean:
	$(RM) *.class

A2A:
	javac A2A.java;
	java A2A N=2 S=2

A2B:
	javac A2B.java;\
	java A2B ../Binput.txt

A2C:
	javac A2C.java;\
	java A2C ../Cinput.txt

report:
	pandoc Report.md -s --highlight-style=pygments -o Report.pdf
