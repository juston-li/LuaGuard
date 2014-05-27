import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.File;
import java.lang.Process;
import java.util.*;

public class Test{
	int testNum, passedTests, obfuscationLevels;
	List<String> failedTests;
	List<Integer> failedTestCases;
	TestOutput testOutput;
	double execTimeThreshold;
	double filesizeThreshold;
	boolean testPass, testCasePass;

	public Test(double execTime, double filesize){
		execTimeThreshold = execTime;
		filesizeThreshold = filesize;
		failedTestCases = new ArrayList<Integer>();
		testOutput = new TestOutput();
	}

	public void getObfuscatedPrograms(){
		//Changes this to add obfuscation levels 
		obfuscationLevels = 2; //Levels 0 and 1
		try {
			for (int level = 0; level < obfuscationLevels; level++){
				ProcessBuilder pb = new ProcessBuilder("java", "-jar", "lg.jar", Integer.toString(level), "tests/test_programs",
					"tests/output/ast", "tests/output/obfuscated");
				pb.directory(new File("../"));
				Process p = pb.start();
				p.waitFor();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void runTests(){
		File dir = new File("test_programs");
		File[] testPrograms = dir.listFiles();
		if (testPrograms != null) {
			//Loop through obfuscation levels
			for (int test=1; test <= obfuscationLevels; test++){
				//Reset results for each test case
				testCasePass=true;
				testNum = 1;
				passedTests=0;
				failedTests = new ArrayList<String>();
				testOutput.printTestCaseStart(test);
				//Loop through each program for test case
				//Obfuscated code is stored numerically as xx_obfuscated#.lua
				//Does NOT overwrite, will continue to increase # for each test run
				for (File prog : testPrograms) {
					testOutput.printStart(prog.getName());
					testPass = true;

					//Ugly string manipulations for file paths to Original and obfuscated
					String progFile = "test_programs/"+prog.getName();
					String[] array = prog.getName().split("\\.");
					String filename = array[0];
					String fileExt = array[1];
					String obfProgFile = "output/obfuscated/"+filename+"_obfuscated"+test+"."+fileExt;

					/* File redirect seems to increase the measured execution times
					   Isolate tests by running seperate comparisons for outputs
					   and execution time */
					compareOutput(progFile,obfProgFile);
					//No need to run if no time or filesize thresholds given
					if(execTimeThreshold != 0){
						compareExecutionTime(progFile, obfProgFile);
					}
					if(filesizeThreshold != 0) {
						compareFileSize(progFile, obfProgFile);
					}
					if(testPass) {
						testOutput.printPass();
						passedTests++;
					} else {
						testOutput.printFail();
						String obfFile = new File( obfProgFile).getName();
						failedTests.add(obfFile);
						testCasePass=false;
					}
					testNum++;
				}
				testOutput.printResults(test,passedTests,testNum,failedTests);
				if(!testCasePass) {
					failedTestCases.add(test);
					
				}
			}
		} else {
			System.out.println("No programs to test in test_programs");
		}
		testOutput.printGlobalResults(failedTestCases);
	}

	public void compareOutput(String originalProgram, String obfuscatedProgram){
		try {
			String origProgName = new File(originalProgram).getName();
			String obfProgName = new File(obfuscatedProgram).getName();

			//Original
			ProcessBuilder pb = new ProcessBuilder("lua", origProgName);	
			pb.redirectOutput(new File("output.txt"));
 			pb.redirectErrorStream(true);
			pb.directory(new File("test_programs"));
			Process p = pb.start();	
			p.waitFor();
			pb.start();	

			//Obfuscated - need to rename file to original name for same lua errors
			File obfFile = new File(obfuscatedProgram);
			File obf2Orig = new File("output/obfuscated/"+origProgName);
			obfFile.renameTo(obf2Orig);
			pb = new ProcessBuilder("lua", origProgName);
			pb.redirectOutput(new File("obfuscated_output.txt"));
			pb.redirectErrorStream(true);
			pb.directory(new File("output/obfuscated"));
			p = pb.start();	
			p.waitFor();
			//Move original program back
			File renamedObfFile = new File("output/obfuscated/"+origProgName);
			File orig2Obf = new File("output/obfuscated/"+obfProgName);
			renamedObfFile.renameTo(orig2Obf);

			File diff = new File("diff.txt");
			pb = new ProcessBuilder("diff", "output.txt", "obfuscated_output.txt");
			pb.redirectOutput(diff);
			p = pb.start();
			p.waitFor();
			
			//If file is not empty, something is different with outputs, test failed
			if(diff.length() != 0) { 
				testPass=false;

				//Get filename with and without .lua
				String fileName = new File(obfuscatedProgram).getName();
				int dot = fileName.lastIndexOf(".");
    				String progName  = fileName.substring(0, dot);

				//Create output directory to store outputs
				File outputDir=new File("output");
				if(!outputDir.exists()){
					outputDir.mkdir();
				}

				//Create diff directory to store diffs
				File diffDir=new File("output/diff");
				if(!diffDir.exists()){
					diffDir.mkdir();
				}

				//Dump diff in files rather than fill terminal
				File diffDump = new File("output/diff/diff_"+progName+".txt");
				diff.renameTo(diffDump);
 
				System.out.println("ERROR: Output does not match");
				System.out.println("Diff, obfuscated program and AST located in output folder");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void compareExecutionTime(String originalProgram, String obfuscatedProgram){
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

			pb = new ProcessBuilder("lua", obfuscatedProgram);
			
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
		//execTimeThreshold given as decimal
		//1.2 would mean 120% longer time is unacceptable
		double ratio = obfElapsedTime / origElapsedTime;
		if(ratio > execTimeThreshold) {
			testPass=false;

			System.out.println("ERROR: Execution time difference exceeds threshold");
			System.out.println("Obfuscated Execution Time: "+obfElapsedTime+"ns");
			System.out.println("Original Execution Time: "+origElapsedTime+"ns");
		}
	}

	public void compareFileSize(String originalProgram, String obfuscatedProgram){
		File origFile = new File(originalProgram);
		File obfFile = new File(obfuscatedProgram);
		
		double origLength = origFile.length();
		double obfLength = obfFile.length();
		//same as execTimeThreshold, filesize thres given as decimal
		//1.2 would mean 120% larger is unacceptable
		double ratio = obfLength / origLength;
		if(ratio > filesizeThreshold) {
			testPass=false;

			System.out.println("ERROR: Filesize difference exceeds threshold");
			System.out.println("Original File Size: "+origLength+" Bytes");
			System.out.println("Obfuscated File Size: "+obfLength+" Bytes");
		}
	}
	
	public void cleanUp(){
		//TODO:Using files as an intermediary is really inefficient; slow read/write to disk
		//Change to ByteArrayOutputStream would be better but make using system diff harder
		File diff = new File("diff.txt");
		File output = new File("output.txt");
		File obfOutput = new File("obfuscated_output.txt");

		diff.delete();
		output.delete();
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

		test.getObfuscatedPrograms();
		test.runTests();
		test.cleanUp();
	}
}
