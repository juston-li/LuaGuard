import     org.fusesource.jansi.AnsiConsole;
import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class TestOutput {

	public TestOutput(){
	}
	
	public void printStart(int testNum){
		AnsiConsole.out.println(ansi().fg(GREEN).a("*****************************").reset());
		AnsiConsole.out.println(ansi().fg(GREEN).a("**     Starting Test " + testNum + "     **").reset());
		AnsiConsole.out.println(ansi().fg(GREEN).a("*****************************").reset());
	}	
	
	public void printPass(int testNum){
		AnsiConsole.out.println(ansi().fg(GREEN).a("*****************************").reset());
		AnsiConsole.out.println(ansi().fg(GREEN).a("**      Test " + testNum + " PASSED      **").reset());
		AnsiConsole.out.println(ansi().fg(GREEN).a("*****************************\n").reset());
	}
	
	public void printFail(int testNum){
		AnsiConsole.out.println(ansi().fg(RED).a("*****************************").reset());
		AnsiConsole.out.println(ansi().fg(RED).a("**      Test " + testNum + " FAILED      **").reset());
		AnsiConsole.out.println(ansi().fg(RED).a("*****************************\n").reset());
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

