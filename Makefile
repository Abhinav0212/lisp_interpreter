# Make file for Java

# Java compiler
JAVAC = javac

# Java compiler flags
JAVAFLAGS = -g

# Creating a .class file
COMPILE = $(JAVAC) $(JAVAFLAGS)

CLASS_FILES = myInterpreter.class

all: $(CLASS_FILES)

%.class : %.java
	$(COMPILE) $<
