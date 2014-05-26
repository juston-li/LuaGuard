package Obfuscate;

public class MLAobfuscate {
	
	private static final String[] KeyWords = new String[] { "and", "break", "do", "else", "elseif",
												"end", "false", "for", "function", "if", "in", "local", 
												"nil", "not", "or", "repeat", "return", "then", "true",
												"until", "while"};
    	
	public String MLAobfuscateProduce(int number){
		try{
			//System.out.println(number);
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
			
			for(int count = 0; count < KeyWords.length; count++){
				if(KeyWords[count].equals(answer))
					return "";
			}
			return answer;
	
		}catch(Exception e){
			System.out.println("MLAobfuscate Exception!");
			return "";
		}
	}
}
