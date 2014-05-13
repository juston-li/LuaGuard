import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.File;
import java.lang.Process;

public class Test{
	int testNum,passedTests;
	String failedTests;
	List<String> list;
	TestOutput testOutput;
	boolean testPass;

	public Test(){
		testNum=1;
		passedTests=0;
		failedTests="";
		testOutput = new TestOutput();
	}

	public void getTests(){
		try {
			list = new ArrayList<String>();
			BufferedReader br = new BufferedReader(new FileReader("test_programs"));
			String line;
				while ((line = br.readLine()) != null) {
   					list.add(line);
				}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void runTests(){
		for(String s : list) {
			testPass = true; 
			testOutput.printStart(testNum);
			System.out.println(s+"\n");
			//TODO:Call obfuscator, output moved to current directory as obfuscated.lua

			/* File redirect seems to increase the measured execution times
			   Isolate tests by running seperate comparisons for outputs 
			   and execution time */
			compareOutput(s);
			compareExecutionTime(s);
			compareFileSize(s);
			if(testPass) {
				testOutput.printPass(testNum);
				passedTests++;
			} else {
				testOutput.printFail(testNum);
				failedTests = failedTests+testNum+" ";
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

			//TODO: Windows doesn't have diff so need a library packaged
			File diff = new File("diff.txt");
			pb = new ProcessBuilder("diff", "output.txt", "obfuscated_output.txt");
			pb.redirectOutput(diff);
			Process p = pb.start();
			p.waitFor();

			if(diff.length() == 0) {
				System.out.println("Output matches\n");
			} else { 
				File obf = new File("obfuscated.lua");
				File obf_dump = new File("obfuscated"+testNum+".lua");
				File diff_dump = new File("diff"+testNum+".txt");
				obf.renameTo(obf_dump); //TODO: Use actual file name, remove file path
				diff.renameTo(diff_dump);

				testPass=false;

				System.out.println("Output does not match");
				System.out.println("Diff located in diff_'testnumber'.txt");
				System.out.println("lua program located in obf_'program'.lua\n");
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

	public static void main(String []args){
		Test test = new Test();

		test.getTests();
		test.runTests();
	}
}
