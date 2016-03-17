CP = ./:

JFLAGS = -g -d build/ -sourcepath src/ -cp "$(CP)"
JC = javac

default: clean Twotter.jar

clean:
	$(RM) build/*.class
	$(RM) Twotter.jar

Twotter.jar:
	$(JC) $(JFLAGS) source/*.java
	cd build; jar cvfm ../Twotter.jar ../manifest.ms ./*.class; cd ../

test:
	java -jar Twotter.jar

stats:
	find source/ -name '*.java' | xargs wc -l

