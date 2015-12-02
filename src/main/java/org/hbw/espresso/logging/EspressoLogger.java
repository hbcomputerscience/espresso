/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hbw.espresso.logging;

import java.util.Arrays;
import java.util.List;
import org.hbw.espresso.functor.Maybe;

/**
 *
 * @author niles
 */
public class EspressoLogger {

	private static Maybe<EspressoJettyLogger> instance = new Maybe<>(null);

	public static void Initialize(Object... files) {
		if (instance.isNothing()) {
			EspressoLogger.warn("Tried to initalize an already-initialized logger");
		} else {
			System.out.println(files.length);
			instance = new Maybe<>(new EspressoJettyLogger(Arrays.asList(files)));
		}
	}

	public static Maybe<EspressoJettyLogger> getInstance() {
		if (instance.isNothing()) {
			EspressoLogger.Initialize(System.out);
			EspressoLogger.warn("Tried to get instance without an initialized instance. Automatically creating one that logs to system.out");
		}
		return instance;
	}

	private EspressoLogger(List<Object> filesList) {
		//instance = new Maybe<>(new EspressoJettyLogger(filesList));
		// Do not call this method
	}

	public static void warn(String string, Object... os) {
		instance.fmap(((x) -> {
			x.warn(string, os);
		}));
	}

	public static void warn(Throwable thrwbl) {
		
	}

	public static void warn(String string, Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public static void info(String string, Object... os) {
		instance.fmap(((x) -> {
			x.info(string, os);
		}));
	}

	public static void info(Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public static void info(String string, Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public static Maybe<Boolean> isDebugEnabled() {
		return instance.fmap(x -> {
			return x.debug;
		});
	}

	public static void setDebugEnabled(Boolean bln) {
		instance.fmap(x -> {
			x.setDebugEnabled((boolean) bln);
		});
	}

	public static void debug(String string, Object... os) {
		instance.fmap(((x) -> {
			x.debug(string, os);
		}));

	}

	public static void debug(Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public static void debug(String string, Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public static void ignore(Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
