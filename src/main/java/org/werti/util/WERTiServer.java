package org.werti.util;

import java.rmi.RemoteException;

import danbikel.parser.DecoderServer;
import danbikel.parser.DecoderServerRemote;
import danbikel.parser.Settings;

import danbikel.switchboard.Switchboard;

import org.apache.log4j.Logger;

public class WERTiServer extends DecoderServer implements DecoderServerRemote {
	private static final Logger log =
		Logger.getLogger(WERTiServer.class);

	private static final long serialVersionUID = 2;

	private static final int PORT = 1099;
	private static final int TIMEOUT = 5000;

	private final static String derivedDataFilename = "src/main/resources/wsj-02-21.obj.gz";


	public WERTiServer() throws RemoteException {
		super(TIMEOUT, PORT);
	}

	public static void main(String[] args) {
		try {
			final WERTiServer srv = new WERTiServer();
			srv.setModelCollection(derivedDataFilename);
			srv.register(Switchboard.defaultBindingName);
			Settings.setSettings(srv.switchboard.getSettings());
			srv.startAliveThread();
			srv.unexportWhenDead();
		} catch (Exception e) {
			log.fatal(e);
		}
	}
}
