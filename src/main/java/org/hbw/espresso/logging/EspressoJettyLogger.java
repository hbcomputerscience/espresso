package org.hbw.espresso.logging;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.eclipse.jetty.util.log.AbstractLogger;
//import org.eclipse.jetty.util.log.JavaUtilLog;
import org.eclipse.jetty.util.log.Logger;

public class EspressoJettyLogger extends AbstractLogger {

	/**
	 * Instance variable dateFormat, which is written to each log line.
	 */
	protected DateFormat dateFormat;
	/**
	 * Instance variable that contains all OutputStreams that should be logged
	 * to.
	 */
	private final List<OutputStream> loggingList = new ArrayList<>();
	/**
	 * Instance variable that notes whether debug messages should be logged or
	 * ignored.
	 */
	protected Boolean debug;
	/**
	 * Instance variable that contains the line ending. Defaults to \r\n.
	 */
	String endl;

	/**
	 * Takes a type and a message and generates a string containing the current
	 * pid, time and date, type, message, then writes that string to every
	 * OutputStream in loggingList.
	 *
	 * @param type
	 * @param message
	 */
	private void logMessage(String type, String message) {
		String built = String.format("[%s %s %s] %s%s", ManagementFactory.getRuntimeMXBean().getName(), this.dateFormat.format(new Date()), type, message, endl);
		loggingList.stream().forEach((OutputStream o) -> {
			try {
				o.write(built.getBytes());
			} catch (IOException e) {
				// We should probably do something about this
			}
		});
	}

	/**
	 * Constructs a new EspressoJettyLogger with a default date format, line
	 * ending and debug value. Also sets loggingList to the parameter files.
	 *
	 * @param files
	 */
	protected EspressoJettyLogger(List<OutputStream> files) {
		this.dateFormat = new SimpleDateFormat("HH:mm:ss mm/dd/yyyy");
		this.endl = "\r\n";
		this.debug = false;
		loggingList.addAll(files);
	}

	/**
	 * No idea what this does.
	 *
	 * @param string
	 * @return
	 */
	@Override
	protected Logger newLogger(String string) {
		return this;
		//return new JavaUtilLog(string);
	}

	/**
	 * Needed for Jetty API compatability.
	 *
	 * @return String "Jetty Logger for Espresso"
	 */
	@Override
	public String getName() {
		return "Jetty Logger for Espresso";
	}

	/**
	 * Logs a warning with the message string
	 *
	 * @param string
	 * @param os
	 */
	@Override
	public void warn(String string, Object... os) {
		logMessage("WARNING", string);
	}

	/**
	 * Logs a warning from the exception e
	 *
	 * @param e
	 */
	@Override
	public void warn(Throwable e) {
		Arrays.asList(e.getStackTrace()).stream().forEachOrdered((x -> {
			this.warn(x.toString());
		}));
	}

	/**
	 * Logs a warning using the string string then the exception thrwbl.
	 *
	 * @param string
	 * @param thrwbl
	 */
	@Override
	public void warn(String string, Throwable thrwbl) {
		this.warn(string);
		this.warn(thrwbl);
	}

	/**
	 * Logs an info message with the string string
	 *
	 * @param string
	 * @param os
	 */
	@Override
	public void info(String string, Object... os) {
		logMessage("INFO", string);
	}

	/**
	 * Logs an info message from an exception e.
	 *
	 * @param e
	 */
	@Override
	public void info(Throwable e) {
		Arrays.asList(e.getStackTrace()).stream().forEachOrdered((x -> {
			this.info(x.toString());
		}));
	}

	/**
	 * Logs an info message using the string string than the exception thrwbl.
	 *
	 * @param string
	 * @param thrwbl
	 */
	@Override
	public void info(String string, Throwable thrwbl) {
		this.info(string);
		this.info(thrwbl);
	}

	/**
	 * returns whether debug is enabled or not.
	 *
	 * @return boolean currentDebugValue
	 */
	@Override
	public boolean isDebugEnabled() {
		return this.debug;
	}

	/**
	 * Sets debug to bln
	 *
	 * @param bln
	 */
	@Override
	public void setDebugEnabled(boolean bln) {
		this.debug = (Boolean) bln;
	}

	/**
	 * Logs a debug message from the string string if debug is enabled
	 *
	 * @param string
	 * @param os
	 */
	@Override
	public void debug(String string, Object... os) {
		if (this.debug) {
			logMessage("DEBUG", string);
		}
	}

	/**
	 * Logs a debug message from the exception e if debug is enabled.
	 *
	 * @param e
	 */
	@Override
	public void debug(Throwable e) {
		Arrays.asList(e.getStackTrace()).stream().forEachOrdered((x -> {
			this.debug(x.toString());
		}));
	}

	/**
	 * Logs a debug message with the message string and the exception thrwbl.
	 *
	 * @param string
	 * @param thrwbl
	 */
	@Override
	public void debug(String string, Throwable thrwbl) {
		this.debug(string);
		this.debug(thrwbl);
	}

	/**
	 * Does nothing because the Jetty Logging API is pants on head retarded
	 *
	 * @param e
	 */
	@Override
	public void ignore(Throwable e) {
	}

}
