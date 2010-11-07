package com.psegina.vctrl;

import java.io.IOException;
import java.io.InputStream;

/**
 * A helper class used mainly to keep code clean.
 * @author Petar Šegina <psegina@ymail.com>
 *
 */
public class Helper {
	private static ShellCommand cmd = new ShellCommand();
	private Helper(){};
	
	/**
	 * Gives the output of a process. For example, if we were to
	 * run a shell command and wanted to read it's output, we'd
	 * pass the associated process and get the output returned
	 * by this procedure.
	 * @param p The process associated to the shell command
	 * @return String representation of the result
	 */
	public static String processToString(Process p){
		String result="";
		InputStream is;
		int tmp=0;
		
		is = p.getInputStream();
		
		try {
			tmp = is.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(tmp!=-1){
			result += (char) tmp;
			try {
				tmp = is.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Checks if the proper undervolt module is loaded
	 * @return Boolean true if module is found else false
	 */
	public static boolean checkForModule(){
		Process p = null;
		p=cmd.su.run("lsmod");
		if(processToString(p).contains("undervolt"))
			return true;
		else return false;
	}
	
}
