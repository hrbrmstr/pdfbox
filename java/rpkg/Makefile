.PHONY: clean pkg deps run

pkg:
		mvn --quiet package
			cp target/rpkg-1.0-SNAPSHOT.jar ../../inst/java

clean:
		mvn clean

deps:
		mvn dependency:copy-dependencies -DoutputDirectory=deps

run:
		java -cp "deps/*:target/wrc-1.0-SNAPSHOT.jar" is.rud.rpkg.App

