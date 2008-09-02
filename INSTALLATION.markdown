# WERTi

This file should get you going on installing WERTi. Please ask if something seems unclear, I will try to keep the documentation as clean as possible.

Sorry this turned out to be so big. I did not realise what a pain it is to
install WERTi until I had to actually write it down. I will try to see if it's
possible to come up with something less verbose and extensive and probably make
a 'WERTi-lite' release at some point in the future. Currently the complexity is
mostly due to the fact that we support three different taggers, which isn't
neccessary for a running system, but might be if we choose to have
multi-language support (which should come some time in the future).

##Installation instructions

WERTi runs on Apache Tomcat version 5 or higher. Version 6 or higher is
recommended. Note that your Tomcat installation will have to make use of log4j.

### Building requirements
* A recent [Tomcat](http://tomcat.apache.org/download-60.cgi) (5 or higher, 6 recommended)
* A recent version of [Apache/IBM UIMA](http://incubator.apache.org/uima/downloads.cgi)
* [GWT](http://code.google.com/webtoolkit/download.html), version **1.5** or higher
* [The Lingpipe toolkit](http://alias-i.com/lingpipe/web/download.html), 
  Version 3.5 or higher, including model files
* A recent build of the `uima-an-tagger`.
* Currently, we also depend on the 
  [Stanford tagger](http://nlp.stanford.edu/software/tagger.shtml#Download) 
  (a recent build from around April 2008 would do)
* The `lib` package, located at [github](http://github.com/adimit/lib)
* Peripherals, like `git`, `mvn` (maven), `ant` and `svn` (subversion).

#### Notes:
- In order to get logging to work, you just need to have log4j's .jar somewhere
  Tomcat can see it (e.g. werti's `lib` folder or Tomcat's `lib` folder) and
  put a log4j.properties in werti's WEB-INF/lib folder.  If you want to have
  log4j-logging within Catalina itself, use the [following
  instructions](http://tomcat.apache.org/tomcat-6.0-doc/logging.html#log4j).

- To build the `uima-an-tagger`, just download the uima sandbox and compile the
  tagger. The steps involved look something like this:

		svn co http://svn.apache.org/repos/asf/incubator/uima/sandbox/trunk/ uima-sandbox
		cd uima-sandbox/Tagger
		mvn resources:resources compiler:compile resources:testResources\
			compiler:testCompile surefire:test jar:jar
		ln -s target/uima-an-tagger.jar /somewhere/else
	
	This should leave you with a working version of the uima-an-tagger.

- to build my utility library package:

		git clone git://github.com/adimit/lib.git
		cd lib
		ant dist
		ln -s dist/lib-0.1.jar /somewhere/else

	That's it. You just have to copy or link `dist/lib-0.1.jar` where `build.xml` can find it. 

- In order for everything to work, you'll also need the model files for the
taggers. Take a look at `WERTi.properties` to find out what they are. Their
locations in the taggers are the following:

	* For the uima-an-tagger it's
		- `resources/german/TuebaModel.dat`
		- `resources/english/BrownModel.dat`
	* For the lingpipe tagger it's
		- `demos/models/*`

		If you downloaded the full distribution. Otherwise the models are
		located [here](http://alias-i.com/lingpipe/web/models.html). You will
		only need `pos-en-general-brown.HiddenMarkovModel` currently
	* For the Stanford tagger it's
		- `models/*`

		You will only need `bidirectional-wsj-0-18.tagger` currently

### Adjusting build paths

You will have to adjust some paths on your system for everything to make work
out as expected. It is best to just put all `.jar` files in one directory (or
symlink them if you want). The reason they're not in `WEB-INF/lib` is currently
twofold: reduction of the distribution size and potential redistribution issues
with commercial packages (like lingpipe). This might change in the near future.

In the end, you should end up with the following `.jar`s:

* `lib-0.1.jar` (My utility library)
* `uima-an-tagger.jar`
* `log4j*jar`
* `uima-core.jar`
* `stanford-postagger*jar`
* `lingpipe*jar`
* `gwt-servlet.jar`

You should also have the `gwt-user.jar` and `gwt-dev-linux.jar` (If you're
running Linux, that is. You'd be crazy if you don't, anyway)

#### Note:
The `.classpath` file that is currently also under version control will not
work for your system. We should probably take it out of version control. Feel
free to modify it in your own branch. I will take care to merge it manually in
my tree if you ask me to pull.

### System-specific locations (Compile time)

The file `build.properties` should be adjusted to hold paths that make sense on
your given system. The paths currently needed are the following:

* `hmmtagger.models`: a folder containing all models for the uima-an-tagger.
* `ptbtagger.models`: a folder containing all models for the Stanford PoS tagger.
* `lgptagger.models`: a folder containing all models for the Stanford PoS
   tagger.
* `catalina.home`: the base directory of your tomcat installation
* `libraries.locations`: a folder containing the jar files needed to compile
   WERTi.  See below for a full list.
* `gwt-user.location`:  explicit path to the location of the `gwt-user.jar` library 
* `gwt-dev.location`: explicit path to the location of the `gwt-dev-${OS}.jar` library

#### Notes:

* Please note that the use of the Stanford tagger is currently discouraged. In
fact, it doesn't work with the current source and will need adjustments.
* The explicit locations for the gwt jars are needed to compile the gwt models
from within ant instead of using the helper scripts. Since we'll have a module
for every enhancement type, this would clutter up the root directory.

### System-specific locations (Runtime)

The file `www/WERTi.properties` contains runtime paths and adjustments that
control the default behaviour of the WERTi server-side code. Note that there is
no way you can reference this file from client-side GWT code, since it is not
accessible to the client.

* `ptbtagger.base`: the location the ant build file copies the models to under
the build directory. No need to change this.  
* `ptbtagger.${LANG}`: The location of the language specific tagger models for
the Stanford tagger under
the base path.
* `hmmtagger.base`: the location the ant build file copies the models to under
the build directory. No need to change this.  
* `hmmtagger.${LANG}`: The
location of the language specific tagger models for the uima-an-tagger under
the base path.
* `lgptagger.base`: the location the ant build file copies the models to under
the build directory. No need to change this.  
* `lgptagger.en`: the location of
the English model file for the Lingpipe tagger.
* `descriptorPath`: the base path of the descriptors under the build directory.
No need to change this.
* `enhancer.${types}`: The post-processing descriptor to use. This can be
aggregate or primitive. default is used by most tests.
* `aggregate.${types}`: the pre-processing descriptor to use. This one does the
annotation work. Typically, this is an aggregate descriptor, hence the name.
* `this-server`: You will probably have to change this. It denotes the server the
GWT-scripts reside on. This is a bit hackish for now, I don't know if it'll
work when deploying to a *real* server. Will have to look into this.

### Building

Currently, we do not support deploying as .war, for various reasons. Most of
all, it would be a giant 100M+ .war file. Big war. You can build this monster
by using the `dist` target for ant.  Just copying the `/bin` directory to
tomcat or even symlinking from the webapps base folder to `/bin` should work
fine for now. This will be addressed in the future.[2]

The ant task `all` should build everything you need.

	ant all

This will supposedly take a while. The GWT modules are slow to compile. Expect
about 2-3 minutes on recent systems

The ant task `reload` does nothing but touch the file `/bin/WEB-INF/web.xml`,
which Tomcat monitors by default to find out when to reload a servlet context.
Use this if you symlink the webapp folder to the output directory `/bin`.

### Documentation

You could read the report located under `docs/paper`. The API docs will be
generated by the ant target `javadoc`. I will try to generate some more
documentation over time. Please send requests for documentation my way.

Notes
-----

[2] (Actually, the real reasons are twofold: security permissions with Tomcat and
the fact that we can't write to a .war from within the webapp, but we need to
create temporary files accessible to the application from within the server.
The way to solve this would likely be to use Pushlets, but that seems to be
rather complicated.)
