package org.hbw.espresso.logging;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import org.hbw.espresso.functor.Maybe;

public class EspressoLogger {

	/**
	 * Initially sets the private static variable instance to a maybe with the
	 * value nothing.
	 */
	private static Maybe<EspressoJettyLogger> instance = new Maybe<>(null);

	/**
	 * Takes a number of OutputStreams that it passes to the constructor of
	 * EspressoJettyLogger to create an instance of the jetty logger, unless
	 * it's not the first time that it's been called, in which case it logs a
	 * warning.
	 *
	 * @param files
	 */
	public static void initialize(OutputStream... files) {
		if (!instance.isNothing()) {
			EspressoLogger.warn("Tried to initalize an already-initialized logger");
		} else {
			instance = new Maybe<>(new EspressoJettyLogger(Arrays.asList(files)));
		}
	}

	/**
	 * Used to get instance if we've been initialized, otherwise creates a
	 * default logger and logs a warning, then returns that.
	 *
	 * @return EspressoJettyLogger instance
	 */
	public static Maybe<EspressoJettyLogger> getInstance() {
		if (instance.isNothing()) {
			EspressoLogger.initialize(System.out);
			EspressoLogger.warn("Tried to get instance without an initialized instance. Automatically creating one that logs to system.out");
		}
		return instance;
	}

	/**
	 * Do not call this method.
	 */
	private EspressoLogger(List<Object> filesList) {
		// Do not call this method
	}

	/**
	 * If instance is set it logs a warning with the message string, otherwise
	 * getInstance creates a default logger to log the message.
	 *
	 * @param string
	 * @param os
	 */
	public static void warn(String string, Object... os) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.warn(string, os);
		}));
	}

	/**
	 * If instance is set it logs a warning with a message derived from thrwbl,
	 * otherwise getInstance creates a default logger to log the message.
	 *
	 * @param thrwbl
	 */
	public static void warn(Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.warn(thrwbl);
		}));
	}

	/**
	 * If instance is set it logs a warning with a message derived from string
	 * and thrwbl, otherwise getInstance creates a default logger to log the
	 * message.
	 *
	 * @param string
	 * @param thrwbl
	 */
	public static void warn(String string, Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.warn(string, thrwbl);
		}));
	}

	/**
	 * If instance is set it logs an informational message with the message
	 * string, otherwise getInstance creates a default logger to log the
	 * message.
	 *
	 * @param string
	 * @param os
	 */
	public static void info(String string, Object... os) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.info(string, os);
		}));
	}

	/**
	 * If instance is set it logs an informational message with a message
	 * derived from thrwbl, otherwise getInstance creates a default logger to
	 * log the message.
	 *
	 * @param thrwbl
	 */
	public static void info(Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.info(thrwbl);
		}));
	}

	/**
	 * If instance is set it logs an informational message with a message
	 * derived from string and , otherwise getInstance creates a default logger
	 * to log the message.
	 *
	 * @param string
	 * @param thrwbl
	 */
	public static void info(String string, Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.info(string, thrwbl);
		}));
	}

	/**
	 * If instance is set it returns a maybe with the value of debug, otherwise
	 * getInstance creates a default logger
	 *
	 * @return Maybe with the value debug
	 */
	public static Maybe<Boolean> isDebugEnabled() {
		return EspressoLogger.getInstance().fmap(x -> {
			return x.debug;
		});
	}

	/**
	 * If instance is set it sets debug, otherwise getInstance creates a default
	 * logger then sets the debug value of that.
	 *
	 * @param bln
	 */
	public static void setDebugEnabled(Boolean bln) {
		EspressoLogger.getInstance().fmap(x -> {
			x.setDebugEnabled((boolean) bln);
		});
	}

	/**
	 * If instance is set it logs a debug message with the message string,
	 * otherwise getInstance creates a default logger to log the message.
	 *
	 * @param string
	 * @param os
	 */
	public static void debug(String string, Object... os) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.debug(string, os);
		}));

	}

	/**
	 * If instance is set it logs a debug message with a message derived from
	 * the exception thrwbl, otherwise getInstance creates a default logger to
	 * log the message.
	 *
	 * @param thrwbl
	 */
	public static void debug(Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.debug(thrwbl);
		}));
	}

	/**
	 * If instance is set it logs a debug message with a message derived from
	 * the exception thrwbl and the string string, otherwise getInstance creates
	 * a default logger to log the message.
	 *
	 * @param string
	 * @param thrwbl
	 */
	public static void debug(String string, Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.debug(string, thrwbl);
		}));
	}

	/**
	 * Does nothing because the Jetty Logging API is pants on head retarded
	 *
	 * @param thrwbl
	 */
	public static void ignore(Throwable thrwbl) {
		EspressoLogger.getInstance().fmap(((x) -> {
			x.ignore(thrwbl);
		}));
	}
}
