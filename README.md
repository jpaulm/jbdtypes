For a description of the rationale behind these 
classes, see https://jpaulm.github.io/busdtyps.html ,
and for an even more high-level introduction, which also talks about the problems with scientific data: 
https://jpaulm.github.io/datatyps.html . 

[![Maven Central](https://img.shields.io/maven-central/v/com.jpaulmorrison/jbdtypes.svg?label=JBDTypes)](https://search.maven.org/search?q=g:%22com.jpaulmorrison%22%20AND%20a:%22jbdtypes%22)

The `jar` file may not be all that useful, as this code should be regarded more as a framework, and the user may very well have to add methods as s/he develops his/her project.  Look at https://github.com/jpaulm/jbdtypes/tree/master/src/main/java/com/jpaulmorrison/sample to get a flavour for how these methods are to be used.

This artifact has been added to Maven mainly to allow data types in https://github.com/jpaulm/fbp-etl to be resolved.

Note that `Monetary` is different from `MPrice`, as in the real world - they both have currency and amount, but they have slightly different attributes and methods.  Also, `PCPrice` (PerCent Price) is different from `MPrice` (Monetary Price).
