/* COMPSCI 424 Program 1
 * Name:
 * 
 * This is a template. Program1.java *must* contain the main class
 * for this program. Otherwise, feel free to edit these files, even
 * these pre-written comments or my provided code. You can add any
 * classes, methods, and data structures that you need to solve the
 * problem and display your solution in the correct format.
 */
package compsci424.p1.java;
import java.util.LinkedList;
import java.util.Scanner;
/**
 * Main class for this program. The required steps have been copied
 * into the main method as comments. Feel free to add more comments to
 * help you understand your code, or for any other reason. Also feel
 * free to edit this comment to be more helpful for you.
 */
public class Program1 {
    // Declare any class/instance variables that you need here.

    /**
     * @param args command-line arguments, which can be ignored
     */
    public static void main(String[] args) {

        // 1. Ask the user to enter commands of the form "create N",
        //    "destroy N", or "end", where N is an integer between 0 
        //    and 15.
    	System.out.println("Create or destroy N, where N is an integer between 0 and 15 (ex: create 1), or end by typing 'end':"); 
    	Scanner inp = new Scanner(System.in);
    	String userInp = inp.nextLine();	
    	LinkedList<String> commandList = new LinkedList<String>();
    	//creating version objects
    	Version1 v1 = new Version1();
    	Version2 v2 = new Version2();
    	
    	// will run until user types end. will add all commands to a list and 
    	// run them later
    	while (!userInp.equals("end")) {
    		commandList.add(userInp);
    		userInp = inp.nextLine();
    	}
    	//running all commands for v1
    	System.out.println("Version 1: ");
    	for (int i = 0; i < commandList.size(); i++) {
    		parseCommand1(commandList.get(i), v1);
    	}
    	//running all commands for v2
    	System.out.println("Version 2: ");
    	for (int i = 0; i < commandList.size(); i++) {
    		parseCommand2(commandList.get(i), v2);
    	}
    	
    	
    	// time check for v1
	
    	long start = System.currentTimeMillis();
    	for (int i = 0; i < 200; i++) {
    		for (int j = 0; j < commandList.size(); j++) {
        		parseCommand1NoShow(commandList.get(j), v1);
        	}
    	}
    	long end = System.currentTimeMillis() - start;
    	System.out.println("Total run time for v1 is " + end);
    	//time check for v2
    	start = System.currentTimeMillis();
    	for (int i = 0; i < 200; i++) {
    		for (int j = 0; j < commandList.size(); j++) {
        		parseCommand2NoShow(commandList.get(j), v2);
        	}
    	}
    	end = System.currentTimeMillis() - start;
    	System.out.println("Total run time for v2 is " + end);
    	
       
        System.out.println("Builds without errors and runs to completion.");
    }
    /**
     * You may be looking at this and its clones and be thinking "what are the point of having
     * 4 of these?". uh well I made one method to execute a given command to both versions
     * but then I read a bit more and realized that they have to run separately to check for 
     * time. then i also realized that the output doesnt want showProcessInfo to run either.
     * reading has once again proven to be my achilles heel. 
     * @param command: a String, something like "destroy n"
     * @param v1: a Version1PCB object to apply commands to 
     */
	private static void parseCommand1(String command, Version1 v1) {
		String arr[] = command.split(" ");
		// felt wrong to not add any catch in case something went wrong with input so
		// i just slapped a quick and easy try/catch block on this. and who said
		// i dont prepare for bad input?
		try {
			int num = Integer.parseInt(arr[1]);
			if(arr[0].equals("destroy")) {
				v1.destroy(num);
				v1.showProcessInfo();
			}
			else if(arr[0].equals("create")){
				v1.create(num);
				v1.showProcessInfo();
			}
			else {
				System.out.println("Invalid command entered.");
			}	
		}
		catch (Exception e) {
			System.out.println("Error! Invalid command likely entered!");
		}
		
		
	}
	/**
	 * same as last method, but doesnt call showProcessInfo so you can actually read the output
	 * @param command
	 * @param v1
	 */
	private static void parseCommand1NoShow(String command, Version1 v1) {
		String arr[] = command.split(" ");
		try {
			int num = Integer.parseInt(arr[1]);
			if(arr[0].equals("destroy")) {
				v1.destroy(num);
				//v1.showProcessInfo();
			}
			else if(arr[0].equals("create")){
				v1.create(num);
				//v1.showProcessInfo();
			}
			else {
				System.out.println("Invalid command entered.");
			}
		}
		catch (Exception e) {
			System.out.println("Error! Invalid command likely entered!");
		}
		
		
	}
	/**
	 * pretty much the same as parseCommand1. read that methods description if you wanna know 
	 * the stupid reason i made this method
	 * @param command
	 * @param v2
	 */
	private static void parseCommand2(String command, Version2 v2) {
		String arr[] = command.split(" ");
		try {
			int num = Integer.parseInt(arr[1]);
			if(arr[0].equals("destroy")) {			
				v2.destroy(num);
				v2.showProcessInfo();
			}
			else if(arr[0].equals("create")){
				v2.create(num);
				v2.showProcessInfo();
			}
			else {
				System.out.println("Invalid command entered.");
			}
		}
		catch (Exception e) {
			System.out.println("Error! Invalid command likely entered!");
		}
		
		
	}
	/**
	 * check description for last one
	 * @param command
	 * @param v2
	 */
	private static void parseCommand2NoShow(String command, Version2 v2) {
		String arr[] = command.split(" ");
		try {
			int num = Integer.parseInt(arr[1]);
			if(arr[0].equals("destroy")) {			
				v2.destroy(num);
				//v2.showProcessInfo();
			}
			else if(arr[0].equals("create")){
				v2.create(num);
				//v2.showProcessInfo();
			}
			else {
				System.out.println("Invalid command entered.");
			}
		}
		catch (Exception e) {
			System.out.println("Error! Invalid command likely entered!");
		}
		
		
	}
}
