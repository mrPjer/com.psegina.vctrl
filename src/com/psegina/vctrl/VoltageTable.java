package com.psegina.vctrl;


public class VoltageTable {
	private static String rawVoltageTable;
	private VoltagePair[] VoltageTable;
	private ShellCommand cmd = new ShellCommand();
	
	public String test = "";
	
	/**
	 * Constructs a new VoltageTable
	 * @param r Pass a runtime to use when executing shell commands
	 */
	public VoltageTable(){
		// Makes sure we have the table loaded into memory
		read();
	}
	
	
	public VoltagePair[] getPairs(){
		return VoltageTable;
	}
	
	/**
	 * Reads the raw voltage table from the system
	 */
	public void read(){
		String result = "";
		Process p = null;
		p = cmd.su.run("cat /proc/undervolt");
		result = Helper.processToString(p);
		rawVoltageTable = result;
		VoltageTable = parse();
	}
	
	/**
	 * Parses the raw table info
	 * @return an array of VoltagePair objects
	 */
	private VoltagePair[] parse(){
		// Parses the table and returns VoltagePair objects
		
		String[] tmp;
		String row = "";
		
		// Splits raw info into individual rows
		String[] rows = rawVoltageTable.split("\n");
		test = ""+rows.length;
		
		// We need to create an array of an adequate size
		VoltagePair[] result = new VoltagePair[rows.length];

		
		for(int j=0;j<rows.length;j++){
			VoltagePair temp = new VoltagePair();
			row = rows[j];
			while(row.contains("  "))
			row = row.replace("  "," ");
			tmp=row.split(" ");
			test = "#"+tmp[1]+"#";
				temp.originalVoltage = Integer.parseInt(tmp[0]);
				temp.currentVoltage = Integer.parseInt(tmp[1]);
				temp.targetSpeed = Integer.parseInt(tmp[2]);
			result[j]=temp;
			temp = null;
		}
		return result;
	}
	
	/**
	 * Saves the voltage table for future use
	 */
	public static void save(){
		// TODO
	}
	
	/**
	 * Applies the saved voltage table
	 */
	public static void apply(){
		// TODO
	}
}
