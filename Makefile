# Make file for Java

# Java compiler
JAVAC = javac

# Java compiler flags
JAVAFLAGS = -g -classpath .:./src/ -d ./classes

# Creating a .class file
COMPILE = $(JAVAC) $(JAVAFLAGS)

CLASS_FILES = src/interpreter/myInterpreter.class

all: $(CLASS_FILES)

%.class : %.java
	$(COMPILE) $<
