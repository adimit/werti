# WERTi

This file should get you going on installing WERTi. Please ask if something
seems unclear, I will try to keep the documentation as clean as possible.

## Additional Documentation

On the Github page of WERTi, there is a wiki that holds troubleshooting
advice and a guide on how to develop WERTi in Eclipse: 
http://github.com/adimit/werti/wikis/home

## Installation instructions

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
		-Dversion=3.7.0 \
		-Dfile=/path/to/lingpipe-3.7.0.jar \
		-Dpackaging=jar

Take care to replace `/path/to/lingpipe.jar` with the location where you
downloaded lingpipe.  Do not change the other parameters.

You can find the model files in `demos/models/*` of the LingPipe package.  If
you did not download the full package, the model files are located
[here](http://alias-i.com/lingpipe/web/models.html). You will currently only
need `pos-en-general-brown.HiddenMarkovModel`. Put them into
`src/main/resources/models/lgptagger` (you may have to create the directory
first).

## Building

After everything is in place, just go ahead and type

	mvn package

This will take a while. The GWT modules are slow to compile, and a *lot* of
dependencies will be downloaded. Make sure you have a good Internet connection
and some coffee (or other favored beverage).

In order to develop and debug GWT parts of WERTi, it is
necessary to run the GWT Shell. This is described below
as it must to be done after deploying.

## Deploying

While WERTi may run on other Servlet containers, it has so far only been tested with Tomcat 6.x. There are several ways of deploying the application.

### Local Development: Using Symlinks

If WERTi is installed to your local system, then placing a symlink from
`$CATALINA_HOME/webapps` to `target/WERTi` is the easiest way to deploy WERTi
on your local development server. You can use the `mvn war:exploded` tasks to
redeploy the application this way. Note that Tomcat will require you to `touch`
WERTi's `web.xml` for it to notice a change and reload.

### Local Development: Using the Maven Tomcat Plug in

Maven's Tomcat Plug in can deploy webapps on a local or remote tomcat server.
See the [documentation for
deploying](http://mojo.codehaus.org/tomcat-maven-plugin/deployment.html) and
the [usage and configuration
documentation](http://mojo.codehaus.org/tomcat-maven-plugin/usage.html) on the
official site.

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

## Building and Running GWT the Shell 

Now that everything is in place, start your Tomcat server and run

	mvn gwt:gwt

After some compiling, this should open the Google Web Toolkit Development Shell
with a running WERTi in it. This shell allows the development and debugging of
GWT Java classes.
