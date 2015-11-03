package com.untapt.nasa;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author timonsafaie
 *
 */
public class RoversControllerTest {

	private RoversController rc;
	
	@Before
	public void testInit() {
		rc = new RoversController("set1.in");
		System.out.println("testInit\n========"); 
		assertEquals("0 0 N", rc.getPosition());
		System.out.println("Initial Position: "+rc.getPosition()+"\n"); 
	}

	@Test
	public void testOutput() {
		int count = 0;
		String line;
		
		rc.run();
		
		// First test if plateau boundaries set correctly
		System.out.println("testOutput\n=========="); 
		assertEquals("5 5", rc.getBoundary());
		System.out.println("Plateau: "+rc.getBoundary());
		
		// Check if each rover's final position is as expected
		try {
			Scanner sc = new Scanner(new FileReader("data/set1.out"));
			// Test entire file, line by line
			line = sc.nextLine();
			assertEquals("1 3 N", line);
			System.out.println("Rover"+(++count)+": "+line);
			line = sc.nextLine();
			assertEquals("5 1 E", line);
			sc.close();
			System.out.println("Rover"+(++count)+": "+line);
		} catch (FileNotFoundException e) {
			// File failed to be read
			e.printStackTrace();
		}
	}
}
