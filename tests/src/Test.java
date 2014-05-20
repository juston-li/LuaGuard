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
	double execTimeThreshold;
	double filesizeThreshold;
	boolean testPass;

	public Test(double execTime, double filesize){
		testNum=1;
		passedTests=0;
		execTimeThreshold = execTime;
		filesizeThreshold = filesize;
		failedTests = new ArrayList<String>();;
		testPrograms = new ArrayList<String>();
		testOutput = new TestOutput();
	}

	public void getTests(){
		try {
			BufferedReader br = new BufferedReader(new FileReader("test_programs.txt"));
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
		//Main loop through each program and run tests
		for(String prog : testPrograms) {
			//Commenting in test_programs
			if(prog.startsWith("#")){
				continue;
			}

			testPass = true; 
			testOutput.printStart(prog);
			//TODO:Call obfuscator, output moved to current directory as obfuscated.lua

			/* File redirect seems to increase the measured execution times
			   Isolate tests by running seperate comparisons for outputs 
			   and execution time */
			compareOutput(prog);
			//no need to run if no time or filesize thresholds given
			if(execTimeThreshold != 0){ 
				compareExecutionTime(prog);
			}
			if(filesizeThreshold != 0) {
				compareFileSize(prog);
			}

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
			Process p = pb.start();	
			p.waitFor();
			pb.start();	

			/*Obfuscated*/
			pb = new ProcessBuilder("lua", "obfuscated.lua");
			pb.redirectOutput(new File("obfuscated_output.txt"));
			p = pb.start();	
			p.waitFor();

			File diff = new File("diff.txt");
			pb = new ProcessBuilder("diff", "output.txt", "obfuscated_output.txt");
			pb.redirectOutput(diff);
			p = pb.start();
			p.waitFor();
			
			//If file is not empty, something is different with outputs, test failed
			if(diff.length() != 0) { 
				testPass=false;

				//Get filename with and without .lua
				String fileName = new File(originalProgram).getName();
				int dot = fileName.lastIndexOf(".");
    				String progName  = fileName.substring(0, dot);

				//Create diff directory to store diffs
				File diffDir=new File("diff");
				if(!diffDir.exists()){
					diffDir.mkdir();
				}

				//Dump diff in files rather than fill terminal
				File diffDump = new File("diff/diff_"+progName+".txt");
				diff.renameTo(diffDump);
 
				System.out.println("[Output does not match]");
				System.out.println("Diff located in diff folder");
				System.out.println("Obfuscated lua program located in obfuscated folder");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void compareExecutionTime(String originalProgram){
		long origElapsedTime = 1;
		long obfElapsedTime = 1;
		try {
			ProcessBuilder pb = new ProcessBuilder("lua", originalProgram);
			/*First run seems to take more time even for the same program
			  Allocating thread? Load program to mem? 
			  Run it once before we start measuring execution time */
			pb.start();	

			/*Original*/
			long origStartTime = System.nanoTime();
			Process p = pb.start();	
			int i = p.waitFor();
			long origStopTime = System.nanoTime();
			origElapsedTime = origStopTime - origStartTime;

			pb = new ProcessBuilder("lua", "obfuscated.lua");
			
			/*Obfuscated*/
			long obfStartTime = System.nanoTime();
			p = pb.start();	
			i = p.waitFor();
			long obfStopTime = System.nanoTime();
			obfElapsedTime = obfStopTime - obfStartTime;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//TODO: Programs can take as little as several milliseconds with timer fluctuating
		//      Making a 1ms to 2ms difference a 200% increase. Use nanoseconds for precision? 
		double ratio = obfElapsedTime / origElapsedTime;
		
		if(ratio > execTimeThreshold) {
			testPass=false;

			System.out.println("[Execution time difference exceeds threshold]");
			System.out.println("Obfuscated Execution Time: "+obfElapsedTime+"ns");
			System.out.println("Original Execution Time: "+origElapsedTime+"ns");
		}
	}

	public void compareFileSize(String originalProgram){
		File origFile = new File(originalProgram);
		File obfFile = new File("obfuscated.lua");
		
		double origLength = origFile.length();
		double obfLength = obfFile.length();

		double ratio = obfLength / origLength;
		
		if(ratio > filesizeThreshold) {
			testPass=false;

			System.out.println("[Filesize difference exceeds threshold]");
			System.out.println("Original File Size: "+origLength+" Bytes");
			System.out.println("Obfuscated File Size: "+obfLength+" Bytes");
		}
	}
	
	public void cleanUp(){
		//TODO:Using files as an intermediary is really inefficient; slow read/write to disk
		//Change to ByteArrayOutputStream
		File diff = new File("diff.txt");
		File output = new File("output.txt");
		File obfLua = new File("obfuscated.lua");
		File obfOutput = new File("obfuscated_output.txt");

		diff.delete();
		output.delete();
		obfLua.delete();
		obfOutput.delete();

		//If all tests passed no diffs, get rid of diff directory
		File diffDir=new File("diff");
		if(failedTests.size() != 0){
			diffDir.delete();
		}
	}

	public static void main(String []args){
		double execTime = 0;
		double filesize = 0;
		if(args.length == 2 || args.length == 4){
			if(args[0].equals("-time")) {
				execTime = Double.parseDouble(args[1]);
			} else if (args[0].equals("-filesize")) {
				filesize = Double.parseDouble(args[1]);
			}
		}
		if (args.length == 4) {
			if(args[2].equals("-time")) {
				execTime = Double.parseDouble(args[3]);
			} else if (args[2].equals("-filesize")) {
				filesize = Double.parseDouble(args[3]);
			}
		}
		Test test = new Test(execTime,filesize);

		test.getTests();
		test.runTests();
		test.cleanUp();
	}
}
