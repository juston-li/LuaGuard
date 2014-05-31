import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class MLAobfuscate {
	
	private walker TexasRanger = new walker();
	private int rand = Integer.MAX_VALUE;
	
	public void obfuscate(File ast, ArrayList<String> bl){		
		TexasRanger.addToBlacklist(bl);
		JsonReader jreader = TexasRanger.read_ast(ast.getAbsolutePath());
		StringWriter swriter = new StringWriter();
		JsonWriter jwriter = new JsonWriter(swriter);
		obf0(jreader, jwriter);
		TexasRanger.write_ast(swriter, ast.getAbsolutePath());

	}
	
	// walks the ast, obfuscates names, and writes it out
		public void obf0(JsonReader reader, JsonWriter writer){
			try {
				int i = 2;
				while (true){
					i++;
					JsonToken token = reader.peek();
					switch (token){
						case BEGIN_ARRAY:
							reader.beginArray();
							writer.beginArray();
							break;
						case END_ARRAY:
							reader.endArray();
							writer.endArray();
							break;
						case BEGIN_OBJECT:
							reader.beginObject();
							writer.beginObject();
							break;
						case END_OBJECT:
							reader.endObject();
							writer.endObject();
							break;
						case NAME:
							String name = reader.nextName();
							if (i == 1 && name.equals("name")){
								i = -1;
							}
							writer.name(name);
							break;
						case STRING:
							String str = reader.nextString();
							if (str.equals("Identifier")){
								i = 0;
							} else if (i == 0 && !TexasRanger.check_blacklist(str)){
								// check if in blacklist and previously obfuscated
								if (!TexasRanger.check_name(str)){
									String ob_name = MLAobfuscateProduce((int)(Math.random()*rand)+1);
									TexasRanger.obfuscated_names.put(str, ob_name);
									writer.value(ob_name);
								} else {
									writer.value(TexasRanger.obfuscated_names.get(str));
								}
								i = 2;
								break;
							}
							writer.value(str);
							break;
						case NUMBER:
							String num = reader.nextString();
							writer.value(new BigDecimal(num));
							break;
						case BOOLEAN:
							boolean bool = reader.nextBoolean();
							writer.value(bool);
							break;
						case NULL:
							reader.nextNull();
							writer.nullValue();
							break;
						case END_DOCUMENT:
							return;
					}
				}
			} catch (IOException e){
				System.err.println(e.getMessage());
			}
		}
    	
	public String MLAobfuscateProduce(int number){
		try{
			number = +number;
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
			if(TexasRanger.blacklist.contains(answer))
				return MLAobfuscateProduce((int)(Math.random()*rand)+1);
			return answer;
	
		}catch(Exception e){
			System.out.println("MLAobfuscate Exception!");
			return "";
		}
	}
}
