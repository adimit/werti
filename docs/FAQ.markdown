# Questions That Might Come Up Frequently about WERTi

## mvn gwt:gwt Reports Missing libstdc++ Library

If you encounter an error looking like this:

	[INFO] google.webtoolkit.home (gwtHome) set, using it for GWT dependencies - /home/my.myself.i/werti-dl/gwt-linux-1.5.2
	** Unable to load Mozilla for hosted mode **
	java.lang.UnsatisfiedLinkError: /home/me.myself.i/werti-dl/gwt-linux-1.5.2/mozilla-1.7.12/libxpcom.so: libstdc++.so.5: cannot open shared object file: No such file or directory
	        at java.lang.ClassLoader$NativeLibrary.load(Native Method)
	       	at java.lang.ClassLoader.loadLibrary0(ClassLoader.java:1751)
	        at java.lang.ClassLoader.loadLibrary(ClassLoader.java:1647)
	        at java.lang.Runtime.load0(Runtime.java:769)
	        at java.lang.System.load(System.java:968)
	        at com.google.gwt.dev.shell.moz.MozillaInstall.load(MozillaInstall.java:190)
	        at com.google.gwt.dev.BootStrapPlatform.init(BootStrapPlatform.java:49)
	        at com.google.gwt.dev.GWTShell.main(GWTShell.java:354)

This means that on your system you are missing the old version of libstdc, which is needed by the
Google Web Toolkit. Use the package manager of your system and search for libstdc++ and 
install version 5 of this library. The package name in Ubuntu/Debian is libstdc++5.

