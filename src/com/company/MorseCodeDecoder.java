package com.company;//Daniel Huan Nguyen

//  The purpose of this program is to output all possible solution to a Morse code segment
//  if the user is not given any spaces.
//  This first version is a brute force algorithm. I will later make use of a look-up table of some sort.

import java.lang.String;
import java.io.*;
import java.util.Scanner;

public class MorseCodeDecoder {

	public static int solutionCounter = 0;
	public static int counter = 0;
	
	public static void main(String[] args) throws IOException {
		Scanner cin = new Scanner(System.in);
        MorseTree decoder = new MorseTree();
		
		//Here is where the user inputs the Morse code. Use only "." and "-"
		System.out.println("Please enter the morse code. Use only \".\" and \"-\". No spaces");
		String code = cin.next();
		
		//The filename will be where all the possible solutions are stored.
        String filename = CreateFilename(code) + ".txt";

        //If the the input was not correct, then the program will stop and not create the file.
        if(!filename.equals(".txt")) {
            filename = CreateFilename(code) + ".txt";
            String empty = "";

            //time will keep track of how long the program takes to decode all possible solutions.
            long time;

            FileOutputStream fout = null;
            try {
                fout = new FileOutputStream(filename);
            } finally {
                if (fout != null) {
                    fout.close();
                }
            }

            //Start the timer here.
            long start = System.nanoTime();
            MorseCode(code, empty, filename, decoder);

            //End the timer here
            time = System.nanoTime() - start;

            //Output the time taken
            //System.out.println(time+" nanoseconds");
            FileWriter writer = new FileWriter(filename, true);
            BufferedWriter bfw = new BufferedWriter(writer);
            //bfw.write(time+" nanoseconds\n");
            bfw.write("Solutions : " + solutionCounter);
            bfw.newLine();
            bfw.write("Counter = " + counter);
            bfw.newLine();
            bfw.close();
            System.out.println("Solutions : " + solutionCounter);
            System.out.println("Counter : " + counter);
            System.out.println("Program Finished");
        }
        else{
            System.out.println("Please check that you inputted the Morse Code Correctly");
        }
	}
	
	//This function is where all of the Morse code segmentation occurs.
	public static void MorseCode(String code, String answer, String filename, MorseTree decoder) throws IOException{
		if(code.length()>0){
			String currentCode = "";  //This keeps track of the current code for this recursive function. 
			String tempAnswer = "";  //This keeps track of the decoded version of currentCode
			
			for(int i=0; i<5; i++){  //This for loop is 5 because the max morse code size is 5
				if(code.length()>i){ //The purpose of this if statement is to make sure that the code segment is long enough to append another character
					currentCode = currentCode + code.substring(i,i+1);  //currentCode is set to what the previous recursive call sent
					if(!(decoder.decode(currentCode).equals(""))){ //if currentCode is not empty, then decode it
						tempAnswer = answer + decoder.decode(currentCode);  counter++;//Decodes currentCode and sets it to tempAnswer
						MorseCode(code.substring(i+1, code.length()),tempAnswer, filename, decoder);  //Sends the rest of the undecoded morse code into the next recursive call along with the current answer.
					}
				}
				else break;
			}
		}
		else{
			//Print out result here
			FileWriter writer = new FileWriter(filename, true); 
			BufferedWriter bfw = new BufferedWriter(writer);
			bfw.write(answer);
			bfw.newLine();
			bfw.close();
			solutionCounter++;
		}
	}

    //The purpose of this function is to convert the "." and "-" to "O" and "A" to create a textfile name.
    public static String CreateFilename(String code){
        int length = code.length();
        String temp = "";
        for(int i=0;i<length;i++){
            if(code.substring(i,i+1).equals(".")){
                temp += "O";
            }
            else if(code.substring(i,i+1).equals("-")){
                temp += "A";
            }
            else
                return "";
        }
        return temp;
    }
}
