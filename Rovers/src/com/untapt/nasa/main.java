package com.untapt.nasa;

public class main {

	public static void main(String[] args) throws Exception {
		// Initiate the controller with the given input filename
		RoversController roversController = new RoversController(args[0]);
		
		System.out.println("Loaded Input: "+args[0]);
		
		// Run the instructions and output to file
		roversController.run();
		
		System.out.println("Process Completed.");
	}

}
