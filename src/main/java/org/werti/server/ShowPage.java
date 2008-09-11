package org.werti.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * This displays a result page of the processing process.
 *
 */
public class ShowPage extends HttpServlet {
	private static final Logger log =
		Logger.getLogger(ShowPage.class);

	public static final long serialVersionUID = 10;

	/**
	 * We read the parameter <em>'tempfile'</em>, read it and write it out.
	 *
	 * @see HttpServlet
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		res.setContentType("text/html");
		res.setCharacterEncoding("UTF-8");
		final PrintWriter out;
		try{
			out = res.getWriter();
		} catch (IOException ioe) {
			throw new RuntimeException("Failed to open PrintWriter, have to abort.", ioe);
		}
		final String tempfile = req.getParameter("tempfile");
		final BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(tempfile));
		} catch (NullPointerException npe) {
			showError(out);
			throw new RuntimeException("WTF, no tempfile given? Someone's cheating!", npe);
		} catch (FileNotFoundException fnfe) {
			showError(out);
			throw new RuntimeException("Couldn't find file: " + tempfile, fnfe);
		}
		try {
			while(in.ready()) {
				out.write(in.read());
			}
		} catch (IOException ioe) {
			showError(out);
			throw new RuntimeException("Error reading from input stream to: "+tempfile, ioe);
		}
	}

	/**
	 * A small helper to show us an error in the case something goes wrong.
	 */
	private void showError(PrintWriter out) {
		out.print("<html><head><title>Error!</title></head><body><h1>503 Error!</h1><p>We're sorry, but WERTi couldn't find or read from the file you requested. This is probably some server problem. Please <a href=\"mailto:admtrov@sfs.uni-tuebingen.de\">report it</a>, if you like to. The error has been logged on server side, we're looking into it.</p></body></html>");
	}

	/**
	 * We don't want to be POSTed.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		log.warn("Being POSTed. That's not what we expect. Redirecting...");
		doGet(req, res);
	}
}
