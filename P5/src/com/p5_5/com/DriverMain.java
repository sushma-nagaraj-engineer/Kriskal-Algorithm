package com.p5_5.com;

public class DriverMain {
	public static void main(String[] args) {
		if (args.length == 1) {
			System.out.println("The file name you entered is " + args[0]);
			String file = args[0];
			// the file as passed as input to the algorithm using command line
			KriskalAlgorithm.start(file);
			System.out.println("Successfully generated the graph ");
		} else {
			System.err.println("Enter only the file name");
		}

	}
}
