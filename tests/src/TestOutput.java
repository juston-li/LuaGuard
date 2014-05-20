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
	
	public void printResults(int passedTests, int totalTests, List<String> failedTests){
		totalTests-=1;
		System.out.println("-----------RESULTS-----------");
		if(passedTests != totalTests) {
			System.out.println(passedTests + "/" + totalTests + " Tests Passed");
			for(String prog : failedTests) {
				System.out.println(RED+"[   FAILED   ] "+DEFAULT+prog);
			}
		} else {
			System.out.println(GREEN+"      All Tests Passed"+DEFAULT);
		}
	}
}

