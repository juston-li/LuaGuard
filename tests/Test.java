import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.File;
import java.lang.Process;

public class Test{
	int testNum,passedTests;
	List<String> testPrograms;
	List<String> failedTests;
	TestOutput testOutput;
	boolean testPass;

	public Test(){
		testNum=1;
		passedTests=0;
		failedTests = new ArrayList<String>();;
		testPrograms = new ArrayList<String>();
		testOutput = new TestOutput();
	}

	public void getTests(){
		try {
			BufferedReader br = new BufferedReader(new FileReader("test_programs"));
			String line;
				while ((line = br.readLine()) != null) {
   					testPrograms.add(line);
				}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void runTests(){
		for(String prog : testPrograms) {
			testPass = true; 
			testOutput.printStart(prog);
			//TODO:Call obfuscator, output moved to current directory as obfuscated.lua

			/* File redirect seems to increase the measured execution times
			   Isolate tests by running seperate comparisons for outputs 
			   and execution time */
			compareOutput(prog);
			compareExecutionTime(prog);
			compareFileSize(prog);
			if(testPass) {
				testOutput.printPass();
				passedTests++;
			} else {
				testOutput.printFail();
				failedTests.add(prog);
			}

			testNum++;
		}
		testOutput.printResults(passedTests,testNum,failedTests);
	}

	public void compareOutput(String originalProgram){
		try {
			/*Original*/
			ProcessBuilder pb = new ProcessBuilder("lua", originalProgram);	
			pb.redirectOutput(new File("output.txt"));
			pb.start();	

			/*Obfuscated*/
			pb = new ProcessBuilder("lua", "obfuscated.lua");
			pb.redirectOutput(new File("obfuscated_output.txt"));
			pb.start();

			File diff = new File("diff.txt");
			pb = new ProcessBuilder("diff", "output.txt", "obfuscated_output.txt");
			pb.redirectOutput(diff);
			Process p = pb.start();
			p.waitFor();
			
			//If file is not empty, something is different with outputs, test failed
			if(diff.length() != 0) { 
				testPass=false;

				//Get filename with and without .lua
				String fileName = new File(originalProgram).getName();
				int dot = fileName.lastIndexOf(".");
    				String progName  = fileName.substring(0, dot);

				//Dump diff and obfuscated lua program in files rather than fill terminal
				File obf = new File("obfuscated.lua");
				File diffDump = new File("diff_"+progName+".txt");
				File obfDump = new File("obfuscated_"+fileName);
				obf.renameTo(obfDump);
				diff.renameTo(diffDump);
 
				System.out.println("Output does not match");
				System.out.println("Diff located in "+diffDump.getName());
				System.out.println("lua program located in "+obfDump.getName()+"\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void compareExecutionTime(String originalProgram){
		try {
			ProcessBuilder pb = new ProcessBuilder("lua", originalProgram);
			/*First run seems to take more time even for the same program
			  Allocating thread? Load program to mem? 
			  Run it once before we start measuring execution time */
			pb.start();	

			/*Original*/
			long origStartTime = System.currentTimeMillis();
			Process p = pb.start();	
			int i = p.waitFor();
			long origStopTime = System.currentTimeMillis();
			long origElapsedTime = origStopTime - origStartTime;
			System.out.println("Original Execution Time: "+origElapsedTime+" ms");

			pb = new ProcessBuilder("lua", "obfuscated.lua");
			
			/*Obfuscated*/
			long obfStartTime = System.currentTimeMillis();
			p = pb.start();	
			i = p.waitFor();
			long obfStopTime = System.currentTimeMillis();
			long obfElapsedTime = obfStopTime - obfStartTime;
			System.out.println("Obfuscated Execution Time: "+obfElapsedTime+" ms\n");
	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//TODO: Add option to fail on unaccceptable execution time difference
	}

	public void compareFileSize(String originalProgram){
		File origFile = new File(originalProgram);
		File obfFile = new File("obfuscated.lua");
		System.out.println("Original File Size: "+origFile.length()+" Bytes");
		System.out.println("Obfuscated File Size: "+obfFile.length()+" Bytes");

		//TODO: Add option to fail on unaccceptable file size difference
	}
	
	public void cleanUp(){
		//TODO:Using files as an intermediary is really inefficient; slow read/write to disk
		//Change to ByteArrayOutputStream
		File output = new File("output.txt");
		File obfLua = new File("obfuscated.lua");
		File obfOutput = new File("obfuscated_output.txt");

		output.delete();
		obfLua.delete();
		obfOutput.delete();
	}

	public static void main(String []args){
		Test test = new Test();

		test.getTests();
		test.runTests();
		test.cleanUp();
	}
}
