import java.util.List;

public class TestOutput {
	public static final String GREEN = "\u001B[32m";
	public static final String RED = "\u001B[31m";
	public static final String DEFAULT = "\u001B[0m";

	public TestOutput(){
	}
	
	public void printStart(String programName){
		System.out.println(GREEN+"[RUN---------] "+ DEFAULT + programName);
	}	
	
	public void printPass(){
		System.out.println(GREEN+"[--------PASS]"+DEFAULT);
	}
	
	public void printFail(){
		System.out.println(RED+"[   FAILED   ]"+DEFAULT);
	}

	public void printTestCaseStart(int obfuscationLevel){
		obfuscationLevel -= 1; //filenames were obfuscationlevel+1
		System.out.println(GREEN+"***Starting Test Case: Obfuscation Level "+obfuscationLevel+"***"+DEFAULT);
	}

	public void printResults(int obfuscationLevel, int passedTests, int totalTests, List<String> failedTests){
		totalTests-=1;
		obfuscationLevel -= 1; //filenames were obfuscationlevel+1
		System.out.println("-----------RESULTS-----------");
		if(passedTests != totalTests) {
			System.out.println("     "+passedTests + "/" + totalTests + " Tests Passed");
			for(String prog : failedTests) {
				System.out.println(RED+"[   FAILED   ] "+DEFAULT+prog);
			}
			System.out.println(); //space test cases
		} else {
			System.out.println(GREEN+"Obfuscation Level "+obfuscationLevel+" [PASSED]"+DEFAULT+"\n");
		}
	}

	public void printGlobalResults(List<Integer> failedTestCases){
		System.out.println("--------GLOBAL RESULTS--------");
		if(failedTestCases.isEmpty()){
			System.out.println(GREEN+"    All Test Cases Passed"+DEFAULT);
		} else {
			for (int obfuscationLevel : failedTestCases)
			System.out.println(RED+" Obfuscation Level "+(obfuscationLevel-1)+" [FAILED]"+DEFAULT);
		}
	}
}

