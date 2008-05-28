package org.werti.html;

import java.io.PrintWriter;

public class ErrorPage {
	public static void show(PrintWriter out, org.werti.uima.UnrecoverableUIMAException uuimae) {
		out.print(HTML.preamble("Internal Server Error!"));
		out.print(HTML.element("h1", "WERTi entered an exceptional state and had to abort operation."));
		out.print(HTML.element("p", "WERTi encountered an error during processing that shouldn't have"
					+ " occured. This error has been logged. If you want to, you can send"
					+ " an error report to the <a href=\"mailto:admtrov@sfs.uni-tuebingen.de>\""
					+ "administrator</a>."));
		out.print(HTML.element("p", "Please include in your report the following output (which is a Java stack trace)"));
		out.print(HTML.element("code", uuimae.getMessage()));
		out.print(HTML.BR);
		uuimae.printStackTrace(out);
		out.print(HTML.footer());
	}
}
