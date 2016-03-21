.PHONY: default clean doc test stats

JFLAGS = -g -d build/ -sourcepath source/
JC = javac

default: clean Twotter.jar

clean:
	$(RM) -rf build/edu/umw/twotter/*.class
	$(RM) -rf Twotter.jar

Twotter.jar:
	$(JC) $(JFLAGS) source/*.java
	cd build; jar cvfm ../Twotter.jar ../manifest.ms ./edu/umw/twotter/*.class; cd ../

doc:
	cd source; javadoc -d ../doc/ *.java; cd ../
	open -a Safari doc/index.html

test:
	java -ea -jar Twotter.jar

stats:
	find source/ -name '*.java' | xargs wc -l

