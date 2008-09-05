# WERTi

This file should get you going on installing WERTi. Please ask if something
seems unclear, I will try to keep the documentation as clean as possible.

##Installation instructions

WERTi runs on Apache Tomcat version 5 or higher. Version 6 or higher is
recommended.

### Building requirements
* A recent [Tomcat](http://tomcat.apache.org/download-60.cgi) (5 or higher, 6 recommended)
* [The Lingpipe toolkit](http://alias-i.com/lingpipe/web/download.html), 
  Version 3.5 or higher, including model files
* Peripherals, like `git`, `mvn` (maven).

#### Notes:
- The logging configuration is in `WEB-INF/classes/log4j.properties`


## Preparation

You will need to download the model files for the LingPipe tagger and also add
it to your local Maven repository. You will also need to initialize Maven's the
GWT plugin.

### Model Files for the LingPipe Tagger

You should download the LingPipe package, version 3.5.1.  It is located
[here](http://alias-i.com/lingpipe/web/download.html).  You should download the
*full* *distribution*.

Maven needs to be able to find the LingPipe package. Currently there is no repository
for LingPipe, so you need to add it yourself:

	mvn install:install-file \
		-DgroupId=com.aliasi \
		-DartifactId=lingpipe \
		-Dversion=3.5.1 \
		-Dfile=/path/to/lingpipe-3.5.1.jar \
		-Dpackaging=jar

Take care to replace `/path/to/lingpipe.jar` with the location where you
downloaded lingpipe.  Do not change the other parameters.

You can find the model files in `demos/models/*` of the LingPipe package.  If
you did not download the full package, the model files are located
[here](http://alias-i.com/lingpipe/web/models.html). You will currently only
need `pos-en-general-brown.HiddenMarkovModel`. Put them into
`src/main/resources/models/lgptagger` (you may have to create the directory
first).

### GWT setup

In order to set up the GWT plugins for Maven, you need to first download GWT,
version 1.5.2 [here](http://code.google.com/webtoolkit/download.html). When you
extract the archive, you should end up with a folder named `gwt-OSNAME-1.5.2`.
You should put the directory it sits in into an environment variable named `GWT_HOME`.

	wget http://google-web-toolkit.googlecode.com/files/gwt-linux-1.5.2.tar.bz2
	tar xjf gwt-linux-*.tar.bz2
	export GWT_HOME=`pwd`


## Building

After everything is in place, just go ahead and type

	mvn package

This will take a while. The GWT modules are slow to compile, and a *lot* of
dependencies will be downloaded. Make sure you have a good Internet connection
and some coffee (or other favored beverage).

### System-specific locations (Runtime)

The file `src/main/webapp/WERTi.properties` contains runtime paths and adjustments that
control the default behaviour of the WERTi server-side code. Note that there is
no way you can reference this file from client-side GWT code, since it is not
accessible to the client.

* `this-server`: You will probably have to change this. It denotes the server the
GWT-scripts reside on. This is a bit hackish for now, I don't know if it'll
work when deploying to a *real* server. Will have to look into this.

The rest of the variables are explained in the documentation. Typically, you
shouldn't have to touch them.
