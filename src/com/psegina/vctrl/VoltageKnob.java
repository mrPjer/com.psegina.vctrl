package com.psegina.vctrl;

/**
 * @author Petar Šegina <psegina@ymail.com>
 *  * This class acts as an interface to the undervolt module
 */
public class VoltageKnob {
	private static ShellCommand cmd = new ShellCommand();

	/**
	 * Resets the system voltage to it's default values
	 */
	public static void reset(){
		cmd.su.run("echo '0' > /proc/undervolt");
		applyChanges();
	}
	
	/**
	 * Changes voltage "what" to voltage "with"
	 * @param what The default voltage value
	 * @param with Value which should be applied
	 */
	public static void replace (int what, int with){
		cmd.su.run("echo '"+what+" "+with+"' > /proc/undervolt");
		applyChanges();
	}
	
	/**
	 * Increases the system voltage by amount
	 * @param amount absolute value of the integer by which to increase voltage 
	 */
	public static void increase(int amount){
		cmd.su.run("echo '+"+amount+"' > /proc/undervolt");
		applyChanges();
	}
	/**
	 * Decreases the system voltage by amount
	 * @param amount absolute value of the integer by which to decrease voltage
	 */
	public static void decrease(int amount){
		cmd.su.run("echo '-"+amount+"' > /proc/undervolt");
		applyChanges();
	}
	/**
	 * Makes sure that changes are applied.
	 */
	private static void applyChanges(){
			cmd.su.run("echo '1' > /proc/undervolt");
	}
}
