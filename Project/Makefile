#
# Zakary Olyarnik
# zwo24@drexel.edu
# CS338:GUI, MyGradebook Project
#

# Makefile taken from:
# http://stackoverflow.com/questions/16287020/java-makefile-run

JFLAGS = -g
JC = javac
JVM = java

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	MyGradebook.java \
	ClassListPanel.java \
	ClassViewPanel.java \
	Calculator.java \
	Class.java \
	Category.java \
	Grade.java

MAIN = MyGradebook

default: run

classes: $(CLASSES:.java=.class)

run: classes
	$(JVM) $(MAIN)

clean:
	$(RM) *.class
