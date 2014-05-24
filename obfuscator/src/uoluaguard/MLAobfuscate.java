package uoluaguard;

public class MLAobfuscate {
	
/**
 * Generate a new variable name according to the current number of variable. 
 * For an example, if the number is 4, then the new name is "_d";
 * @param number: The number of names that have been obfuscated.
 * @return: The new name.
 */
	public String MLAobfuscateProduce(int number){
		try{
			number = number - 1;
			if(number < 0)
				return "";
			String answer="";
			char mid;
			
			answer += (char)(number % 26 + 'a');
			number = number / 26;
			while(number > 0)
			{
				number = number - 1;
				mid=(char)((number%26)+'a');
				answer= mid + answer;
				number/=26;
			}
			
			//after Minimum Lexical Algorithm, add "_" after the variable
			if(answer.length() > 0)
				answer = "_" + answer;
			return answer;
	
		}catch(Exception e){
			System.out.println("MLAobfuscate Exception!");
			return "";
		}
	}
}
