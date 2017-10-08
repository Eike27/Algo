package de.wussen.algo2.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Log {

	private static boolean dev = false;
	private static String fileName;
	private static ArrayList<String> info = new ArrayList<>();
	private static SimpleDateFormat formatterLog = new SimpleDateFormat("yyyy.MM.dd - HH:mm:ss");
	private static SimpleDateFormat formatterTxt= new SimpleDateFormat("yyyy_MM_dd");
	
	public static void setDev(boolean dev) {
		Log.info("Developer mode <" + dev + ">");
		Log.dev = dev;
	}
	
	public static void setFileName(String fileName) {
		Log.fileName = fileName;
	}
	
	public static void warn(String msg) {
		warn(msg, false);
	}
	
	public static void warn(String msg, boolean extraLine) {
		msg("Warn", msg, extraLine);
	}
	
	public static void error(String msg) {
		error(msg, false);
	}
	
	public static void error(String msg, boolean extraLine) {
		msg("Error", msg, extraLine);
	}
	
	public static void info(String msg) {		
		info(msg, false);
	}
	
	public static void info(String msg, boolean extraLine) {		
		msg("Info", msg, extraLine);
	}
	
	private static void msg(String type, String msg, boolean extraLine) {
		Date currentTime = new Date(); 
		String infoMsg = type + " <" + formatterLog.format(currentTime) + ">: " + msg;
		info.add(infoMsg);
		if(dev) {
			System.out.println(infoMsg);
		}
		printToLogFile(currentTime, infoMsg, extraLine);
	}
	
	public static void error(Exception e, String className) {
		Date currentTime = new Date(); 
		String infoMsg = "Error <" + formatterLog.format(currentTime) + ">: Class: <" + className + "> " + e.getMessage();
		info.add(infoMsg);
		if(dev) {
			System.out.println(infoMsg);
		}
		e.printStackTrace();
		printToLogFile(currentTime, infoMsg, false);
		boolean first = true;
		for(StackTraceElement elem : e.getStackTrace()) {
			if(first) {
				first = false;
				printToLogFile(currentTime, elem.toString(), false);				
			} else {
				printToLogFile(currentTime, "\t" + elem.toString(), false);				
			}
		}
	}
	
	private static void printToLogFile(Date currentTime, String infoMsg, boolean extraLine) {
		String file = System.getProperty("user.home") + File.separatorChar + "documents" + File.separatorChar + "LogFiles" + File.separatorChar + formatterTxt.format(currentTime) + "_INFO_" + Log.fileName + ".txt"; 
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			out.write(infoMsg);
			out.newLine();
			if(extraLine) {
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param fileName w/o spaces !!!
	 */
	public static void createLogFiles(String fileName) {
		info.add("");
		Date currentTime = new Date(); 
		String file =System.getProperty("user.home") + File.separatorChar + "documents" + File.separatorChar + "LogFiles" + File.separatorChar + formatterTxt.format(currentTime) + "_INFO_" + fileName + ".txt"; 

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
			for(String s : info) {
				out.write(s);
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void checkDirectory() {
		File fl = new File(System.getProperty("user.home") + File.separatorChar + "documents" + File.separatorChar + "LogFiles");
	    if(fl.exists()==false) {
		    Log.info("Creating LogFiles folder <" + fl.getAbsolutePath() + ">");
		    if(!fl.mkdir()) {
		    	Log.error("Could not create LogFiles folder <" + fl.getAbsolutePath() + ">");
		    }
	    }
	}

}
