package com.untapt.nasa;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Scanner;

public class RoversController {
	
	// Position Coordinates
	private int x;
	private int y;
	private int direction;
	
	// Plateau bounds
	private int xMax;
	private int yMax;
	
	// Input filename
	private String input;
	private static final String filePath = "data/";
	
	// Constructors set all rovers to 0,0 N
	public RoversController(String input) {
		this.x = 0;
		this.y = 0;
		this.direction = 0;
		this.input = input;
	}
	
	// Current rover's position toString
	public String getPosition() {
		return this.x+" "+this.y+" "+directionToChar(this.direction);
	}
	
	// Plateau boundary toString
	public String getBoundary() {
		return this.xMax+" "+this.yMax;
	}
	
	// Run the input instructions for the specified rovers 
	public void run() {
		Scanner scanner;
		BufferedWriter bw;
		String file = this.filePath+this.input.replace(".in", "");
		
		try {
			scanner = new Scanner(new FileReader(file+".in"));
			bw = new BufferedWriter(new FileWriter(file+".out"));
			
			// Set the plateau boundary
			if (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] plateau = line.split(" ");
				this.xMax = Integer.parseInt(plateau[0]);
				this.yMax = Integer.parseInt(plateau[1]);
				
				// Process the instruction set for each rover
				while(scanner.hasNextLine()) {
					
					// Set the initial location of the rover
					initRoverPosition(scanner.nextLine());
					
					// Execute instruction set for this rover
					roverExplore(scanner.nextLine());
					
					// Update final position to output
					bw.write(getPosition()+"\n");
				}
			}
			scanner.close();
			bw.close();
			
		} catch(IOException e) {
			// file i/o error occurred
			e.printStackTrace();
		}
	}
	
	// Set the given rover's initial coordinates and direction
	private void initRoverPosition(String location) {
		String[] point = location.split(" ");
		int x = Integer.parseInt(point[0]);
		int y = Integer.parseInt(point[1]);
		int direction = directionToInt(point[2].charAt(0));
		try {
			// Ensure the starting position is valid
			if (((x >= 0) && (x <= this.xMax)) &&
				((y >= 0) && (y <= this.yMax)) &&
				((direction >= 0) && (direction <= 3))) {
				this.x = x;
				this.y = y;
				this.direction = direction;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			System.err.println("Invalid starting position. Default to 0 0 N");
		}
	}
	
	// Converts an char direction to an int
	private int directionToInt(char direction) {
		int result = 0;
		switch (direction) {
			case 'N':
				result = 0;
				break;
			case 'E':
				result = 1;
				break;
			case 'S':
				result = 2;
				break;
			case 'W':
				result = 3;
				break;
		}
		return result;
	}
	
	// Converts an int direction to a char
	private char directionToChar(int direction) {
		char result = 'N';
		switch (direction) {
			case 0:
				result = 'N';
				break;
			case 1:
				result = 'E';
				break;
			case 2:
				result = 'S';
				break;
			case 3:
				result = 'W';
				break;
		}
		return result;
	}
	
	// Carry the set of instructions for the rover's movement
	private void roverExplore(String instructions) {
		for (int i = 0; i < instructions.length(); i++) {
			char instruction = instructions.charAt(i);
			switch (instruction) {
				case 'L':
					turnLeft();
					break;
				case 'R':
					turnRight();
					break;
				case 'M':
				try {
					move();
				} catch(Exception e) {
					System.err.println("Failed to perform move. Rover would be out of plateau.");
				}
					break;
			}
		}
	}
	
	// Decrements direction to turn left  
	private void turnLeft() {
		this.direction = (this.direction-1 < 0)? 3 : this.direction-1;
	}
	
	// Increments direction to turn right
	private void turnRight() {
		this.direction = (this.direction+1 > 3)? 0 : this.direction+1;
	}
	
	// Moves one unit based on direction
	// Throws an exception if out of bounds
	private void move() throws Exception {
		switch (this.direction) {
			case 0: // Move up
				if (this.y+1 <= this.yMax) {
					this.y++;
				} else {
					throw new Exception();
				}
				break;
			case 1: // Move right
				if (this.x+1 <= this.xMax) {
					this.x++;
				} else {
					throw new Exception();
				}
				break;
			case 2: // Move down
				if (this.y-1 >= 0) {
					this.y--;
				} else {
					throw new Exception();
				}
				break;
			case 3: // Move left
				if (this.x-1 >= 0) {
					this.x--;
				} else {
					throw new Exception();
				}
				break;
		}
	}
}
