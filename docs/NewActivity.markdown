# Creating a New Activity in WERTi

... is not too straightforward at the moment.

_ATTENTION! ACHTUNG! ATTENZIONE!_ The entire procedure is under heavy
development. This document might become deprecated the moment you read it.

## 1. Create a Class implementing org.werti.client.tasks.Task

Your class will serve as the part of the graphical interfaces written in
GWT that displays the specialized query form for one or more activities. 
Make sure your class returns an adequate RunConfiguration for each
activity that is provided in your  task (see next section).

## 2. Create a Class implementing org.werti.client.run.RunConfiguration

This class will specify what your activity needs. Make sure to specify the
property keys for pre- and postprocessing components (see next section).

## 3. Edit /src/main/webapp/WERTi.properties

Map from the property keys introduced in the previous step to the UIMA
descriptor files for the analysis engines required as pre- and postprocessors
of your activity. Postprocessing means input enhancement shown to the reader,
preprocessing refers to the linguistic processing.

### 3.1 Create Aggregate Analysis Engines if needed

As you can only have single pre- and postprocessing AEs in UIMA, you might
want to combine several of them into aggregate AEs.

## 4. Edit org.werti.WERTi

Make sure to add a new tab with your task to the tabs that are desplayed
in the query form.

## 5. Create a Activity GWT Module in org.werti.client.enhancement

The module must have the name as specified in your RunConfiguration implementation,
suffixed with .java. 

### 5.1 Create a GWT Module Descriptor in org.werti.client.enhancement.public

The XML file must have the very same name as the module from the previous step,
suffixed with .gwt.xml.

### 5.2 Create a CSS in org.werti.client.enhancement.public

The CSS file must have the very same name as the module from the previous step,
suffixed with .css.

## 6. Introduce the New Module in /pom.xml and Compile GWT.

Add something like

	<param>org.werti.enhancements.GerundsColorize</param>

to pom.xml so that Maven is willing to compile your new GWT stuff. Then run

	mvn gwt:compile
