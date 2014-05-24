package uoluaguard;
public class ExchangeOfLetters {
	/**
	 * This function changes the letters order of the variable name. For an example, 
	 * the name "abcd" after obfuscation would become "_dbca".
	 * @param name: the name you want to obfuscate
	 * @return: the new name
	 */
	public String EOFProduce(String name){
		//System.out.println("old name is " + name);
		
		char[] result = name.toCharArray();
		if(name.length() <= 0)
			return "";
		for(int i = 0; i < name.length() / 2; ){
			char temp = result[i];
			result[i] = result[name.length() - 1 - i];
			result[name.length() - 1 - i] = temp;
			i = i + 2;
		}
		
		//System.out.println("new name is _" + String.valueOf(result));
		return ("_" + String.valueOf(result));
	}
}
