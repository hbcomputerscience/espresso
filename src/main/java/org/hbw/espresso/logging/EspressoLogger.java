package org.hbw.espresso.logging;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import org.hbw.espresso.functor.Maybe;

public class EspressoLogger {

	private static Maybe<EspressoJettyLogger> instance = new Maybe<>(null);

	public static void initialize(OutputStream... files) {
		if (!instance.isNothing()) {
			EspressoLogger.warn("Tried to initalize an already-initialized logger");
		} else {
			instance = new Maybe<>(new EspressoJettyLogger(Arrays.asList(files)));
		}
	}

	public static Maybe<EspressoJettyLogger> getInstance() {
		if (instance.isNothing()) {
			EspressoLogger.initialize(System.out);
			EspressoLogger.warn("Tried to get instance without an initialized instance. Automatically creating one that logs to system.out");
		}
		return instance;
	}

	private EspressoLogger(List<Object> filesList) {
		//instance = new Maybe<>(new EspressoJettyLogger(filesList));
		// Do not call this method
	}

	public static void warn(String string, Object... os) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.warn(string, os);
		}));
	}

	public static void warn(Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.warn(thrwbl);
		}));
	}

	public static void warn(String string, Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.warn(string, thrwbl);
		}));
	}

	public static void info(String string, Object... os) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.info(string, os);
		}));
	}

	public static void info(Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.info(thrwbl);
		}));
	}

	public static void info(String string, Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.info(string, thrwbl);
		}));
	}

	public static Maybe<Boolean> isDebugEnabled() {
		return EspressoLogger.getInstance().fmap(x -> {
			return x.debug;
		});
	}

	public static void setDebugEnabled(Boolean bln) {
		EspressoLogger.getInstance().fmap(x -> {
			x.setDebugEnabled((boolean) bln);
		});
	}

	public static void debug(String string, Object... os) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.debug(string, os);
		}));

	}

	public static void debug(Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.debug(thrwbl);
		}));
	}

	public static void debug(String string, Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.debug(string, thrwbl);
		}));
	}

	public static void ignore(Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.ignore(thrwbl);
		}));
	}
}
