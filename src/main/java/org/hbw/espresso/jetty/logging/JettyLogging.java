/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hbw.espresso.jetty.logging;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
//import org.eclipse.jetty.util.log.AbstractLogger;
import org.eclipse.jetty.util.log.JavaUtilLog;
import org.eclipse.jetty.util.log.Logger;
/**
 *
 * @author niles
 */
public class JettyLogging /* extends AbstractLogger */ {

	DateFormat dateFormat;
	List<OutputStream> loggingList;
	Boolean debug;
	String endl;
	private static JettyLogging instance = null;
	public static void Initialize(Object... files) {
		if (instance == null) {
			JettyLogging.getInstance().warn("Tried to initalize an already-initialized logger");
		} else {
			instance = new JettyLogging(Arrays.asList(files));
		}
	}
	public static JettyLogging getInstance() {
		if (instance == null) {
			JettyLogging.Initialize(System.out);
			JettyLogging.getInstance().warn("Tried to get instance without an initialized instance. Automatically creating one that logs to system.out");
		}
		return instance;
	}
	private JettyLogging(List<Object> filesList) {
		this.dateFormat = new SimpleDateFormat("HH:mm:ss mm/dd/yyyy");
		endl = "\r\n";
		for (Object object : filesList) {
			if ("String".equals(object.getClass().getName())) {
				FileOutputStream f;
				try {
					f = new FileOutputStream((String) object);
				} catch (FileNotFoundException e1) {
					try {
						File file = new File((String) object);
						file.createNewFile();
						f = new FileOutputStream((String) object);
					} catch (Exception e2) {
						// We should probably do something
						continue;
					}
				}
				loggingList.add((OutputStream)f);
			} else {
				loggingList.add((OutputStream) object);
			}
		}
	}
	private void logMessage(String type, String message) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(ManagementFactory.getRuntimeMXBean().getName());
		builder.append(" ");
		builder.append(type);
		builder.append(" ");
		builder.append(this.dateFormat.format(new Date()));
		builder.append("] ");
		builder.append(message);
		builder.append(endl);
		String built = builder.toString();
		for (OutputStream o : loggingList) {
			try {
				o.write(built.getBytes());
			} catch (IOException e) {
				// We should probably do something about this
			}
		}
	}
	
	protected Logger newLogger(String string) {
		return new JavaUtilLog(string);
	}

	
	public String getName() {
		return "Jetty Logger for Espresso";
	}

	
	public void warn(String string, Object... os) {
		logMessage("WARNING", string);
	}

	
	public void warn(Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public void warn(String string, Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public void info(String string, Object... os) {
		logMessage("INFO", string);
	}

	
	public void info(Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public void info(String string, Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public Boolean isDebugEnabled() {
		return this.debug;
	}

	
	public void setDebugEnabled(boolean bln) {
		this.debug = (Boolean) bln;
	}

	
	public void debug(String string, Object... os) {
		if (this.debug) {
			logMessage("DEBUG", string);
		}
	}

	
	public void debug(Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public void debug(String string, Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	
	public void ignore(Throwable thrwbl) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
