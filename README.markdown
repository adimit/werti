# WERTi ― a Platform for ICALL Systems

WERTi is a web platform for second language learners and programmers interested
in providing functionality for second language learning assistance. It is
capable of retrieving documents from certain web sites and then annotating
their linguistic structure in a way that could to help the learner. The web
content can then be marked up in a modular way and the enhanced content exposed
to the user through an interactive interface. This should ultimately allow the
learner to understand the underlying principles of the target language, and
thus acquire a firmer grasp of the language itself.

### Architecture
The system currently only supports English as a target language, but is planned
to be highly modular and thus extensible towards other languages.

WERTi is confirmed to run on a Tomcat web server and relies on a recent JVM. It
was only tested using the Sun JVM. The basic architecture relies on tomcat's
servlets API, the (incubating) Apache UIMA API and the Google Web Toolkit. Thus
it makes use of Java, AJAX, HTML and XML peripheral (e.g. Maven, Eclipse
EMF ...) technologies.

The system is currently in early development. For more information, please
refer to the documentation under /docs or contact the author
`admtrov@sfs.uni-tuebingen.de`

### Requirements
* A recent Tomcat (5 or higher, 6 recommended)
* GWT, version 1.5 or higher
* The Lingpipe toolkit, Version 3.5 or higher, including model files
* Maven 2

See `INSTALLATION.markdown` for instructions on how to install. See
`docs/HACKING.markdown` on some guidance if you want to work on the system.
