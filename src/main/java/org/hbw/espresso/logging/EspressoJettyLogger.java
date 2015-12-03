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
import org.eclipse.jetty.util.log.JavaUtilLog;
import org.eclipse.jetty.util.log.Logger;

public class EspressoJettyLogger extends AbstractLogger {

	DateFormat dateFormat;
	List<OutputStream> loggingList = new ArrayList<>();
	Boolean debug;
	String endl;

	private void logMessage(String type, String message) {
		String built = String.format("[%s %s %s] %s", ManagementFactory.getRuntimeMXBean().getName(), this.dateFormat.format(new Date()), type, message);
		for (OutputStream o : loggingList) {
			try {
				o.write(built.getBytes());
			} catch (IOException e) {
				// We should probably do something about this
			}
		}
	}

	protected EspressoJettyLogger(List<OutputStream> files) {
		this.dateFormat = new SimpleDateFormat("HH:mm:ss mm/dd/yyyy");
		this.endl = "\r\n";
		this.debug = false;
		for (OutputStream s : files) {
			loggingList.add(s);
		}
	}

	@Override
	protected Logger newLogger(String string) {
		return new JavaUtilLog(string);
	}

	@Override
	public String getName() {
		return "Jetty Logger for Espresso";
	}

	@Override
	public void warn(String string, Object... os) {
		logMessage("WARNING", string);
	}

	@Override
	public void warn(Throwable e) {
		Arrays.asList(e.getStackTrace()).stream().forEachOrdered((x -> {
			this.warn(x.toString());
		}));
	}

	@Override
	public void warn(String string, Throwable thrwbl) {
		this.warn(string);
		this.warn(thrwbl);
	}

	@Override
	public void info(String string, Object... os) {
		logMessage("INFO", string);
	}

	@Override
	public void info(Throwable e) {
		Arrays.asList(e.getStackTrace()).stream().forEachOrdered((x -> {
			this.info(x.toString());
		}));
	}

	@Override
	public void info(String string, Throwable thrwbl) {
		this.info(string);
		this.info(thrwbl);
	}

	@Override
	public boolean isDebugEnabled() {
		return this.debug;
	}

	@Override
	public void setDebugEnabled(boolean bln) {
		this.debug = (Boolean) bln;
	}

	@Override
	public void debug(String string, Object... os) {
		if (this.debug) {
			logMessage("DEBUG", string);
		}
	}

	@Override
	public void debug(Throwable e) {
		Arrays.asList(e.getStackTrace()).stream().forEachOrdered((x -> {
			this.debug(x.toString());
		}));
	}

	@Override
	public void debug(String string, Throwable thrwbl) {
		this.debug(string);
		this.debug(thrwbl);
	}

	@Override
	public void ignore(Throwable e) {
	}

}
