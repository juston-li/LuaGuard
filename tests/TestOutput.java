public class TestOutput {
	public static final String GREEN = "\u001B[32m";
	public static final String RED = "\u001B[31m";
	public static final String DEFAULT = "\u001B[0m";

	public TestOutput(){
	}
	
	public void printStart(int testNum){
		System.out.println(GREEN+"*****************************");
		System.out.println("**     Starting Test " + testNum + "     **");
		System.out.println("*****************************"+DEFAULT);
	}	
	
	public void printPass(int testNum){
		System.out.println(GREEN+"*****************************");
		System.out.println("**      Test " + testNum + " PASSED      **");
		System.out.println("*****************************\n"+DEFAULT);
	}
	
	public void printFail(int testNum){
		System.out.println(RED+"*****************************");
		System.out.println("**      Test " + testNum + " FAILED      **");
		System.out.println("*****************************\n"+DEFAULT);
	}
	
	public void printResults(int passedTests, int totalTests, String failedTests){
		totalTests-=1;
		System.out.println("-----------RESULTS-----------");
		if(passedTests != totalTests) {
			System.out.println(passedTests + "/" + totalTests + " Tests Passed");
			System.out.println("Tests " + failedTests + " Failed");
		} else {
			System.out.println("All Tests Passed");
		}
	}
}

