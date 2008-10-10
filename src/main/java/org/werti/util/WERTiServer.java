package org.werti.util;

import java.io.PrintWriter;

import java.rmi.RemoteException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import danbikel.parser.DecoderServer;
import danbikel.parser.DecoderServerRemote;
import danbikel.parser.Settings;

import danbikel.switchboard.Switchboard;

public class WERTiServer extends DecoderServer implements DecoderServerRemote {

	private static final long serialVersionUID = 2;

	private static final int TIMEOUT = 20;

	private final static String derivedDataFilename = "src/main/resources/wsj-02-21.obj.gz";

	public WERTiServer() throws RemoteException {
		super(TIMEOUT);
	}

	public static void main(String[] args) {
		try {
			System.err.printf("Memory: %s/%s\n"
					, Runtime.getRuntime().freeMemory()/1024/1024
					, Runtime.getRuntime().totalMemory()/1024/1024);
			final Switchboard sb = new Switchboard(new PrintWriter(System.out));
			Settings.set(Settings.decoderRelaxConstraintsAfterBeamWidening, "false");
			sb.setSettings(Settings.getSettings());
			sb.export();
			final Registry r = LocateRegistry.createRegistry(1099);
			r.bind("Switchboard", sb);
			System.err.printf("Constructed Switchboard\n");
			final WERTiServer srv = new WERTiServer();
			System.out.println("Registering with Switchboard now.");
			srv.register("Switchboard");
			System.out.println("I have the ID " + srv.id);
			srv.setModelCollection(derivedDataFilename);
			System.err.println("Listening…");
			srv.startAliveThread();
			srv.unexportWhenDead();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
}
