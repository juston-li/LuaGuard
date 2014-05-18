// javac -Xlint -cp [path to]gson-2.2.4.jar:. walker.java
// java -cp [path to]gson-2.2.4.jar:. walker [input file]

import com.google.gson.stream.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class walker implements Obfuscator{
	private ArrayList<String> blacklist;
	private Map<String, String> obfuscated_names;

	// check if name is in blacklist
	// true if name is in blacklist
	private boolean check_blacklist(String name){
		if (blacklist.contains(name)){
			return true;
		} else {
			return false;
		}
	}

	// check if name has been obfuscated
	// true if name has been obfuscated
	private boolean check_name(String name){
		if (obfuscated_names.containsKey(name)){
			return true;
		} else {
			return false;
		}
	}

	// generates a random obfuscated name
	private String getObfuscatedName(){
		// generate random name and check if valid
		String name = Long.toHexString(Double.doubleToLongBits(Math.random()));

		// possible collisions start at 2,147,483,648 obfuscated names
		int i = 0;
		while (obfuscated_names.containsKey(name) && i < 2147483647){
			name = Long.toHexString(Double.doubleToLongBits(Math.random()));
			i++;
		}
		return name;
	}

	// constructor
	public walker(){
		obfuscated_names = new HashMap<String, String>();
		blacklist = new ArrayList<String>();
	}

	// walks the ast and writes it out
	public void walker(JsonReader reader, JsonWriter writer){
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
						writer.name(name);
						break;
					case STRING:
						String str = reader.nextString();
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
						} else if (i == 0 && !check_blacklist(str)){
							// check if in blacklist and previously obfuscated
							if (!check_name(str)){
								String ob_name = getObfuscatedName();
								obfuscated_names.put(str, ob_name);
								writer.value(ob_name);
							} else {
								writer.value(obfuscated_names.get(str));
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

	// read from file
	// returns JsonReader if valid, else null
	public JsonReader read_ast(String filename){
		try {
			FileReader fr = new FileReader(filename);
			JsonReader jreader = new JsonReader(fr);
			return jreader;
		} catch (FileNotFoundException e){
			System.err.println(e.getMessage());
		}
		return null;
	}

	// write obfuscated ast to file
	public void write_ast(StringWriter swriter, String filename){
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filename));
			writer.write(swriter.getBuffer().toString());
			// write end of file  ----- might need to change for windows compadibility
			writer.write("\n");
			writer.close();
		} catch (IOException e){
			System.err.println(e.getMessage());
		}
	}

	// replaces Black List with give ArrayList<String,String>
	public void setBlacklist(ArrayList<String> bl){
		blacklist = bl;
	}

	// add ArrayList to black list
	public void addToBlacklist(ArrayList<String> bl){
		blacklist.addAll(bl);
	}

	// add str to black list
	public void addToBlacklist(String str){
		blacklist.add(str);
	}

	public void obfuscate(File ast, ArrayList<String> bl){
		walker TexasRanger = new walker();
		TexasRanger.addToBlacklist(bl);
		JsonReader jreader = TexasRanger.read_ast(ast.getName());
		StringWriter swriter = new StringWriter();
		JsonWriter jwriter = new JsonWriter(swriter);
		TexasRanger.obf0(jreader, jwriter);
		TexasRanger.write_ast(swriter, ast.getName());
	}
	public static void main(String[] args){
		// create new walker
		walker TexasRanger = new walker();
		
		// test blacklist stuff
		ArrayList<String> bl = new ArrayList<String>();
		bl.add("a");
		TexasRanger.addToBlacklist("b");
		TexasRanger.addToBlacklist(bl);
		//TexasRanger.setBlacklist(bl);

		// set up reader, writer, and input file
		JsonReader jreader = TexasRanger.read_ast(args[0]);
		StringWriter swriter = new StringWriter();
		JsonWriter jwriter = new JsonWriter(swriter);

		// walk and obfuscate the ast
		TexasRanger.obf0(jreader, jwriter);

		// write ast to file
		TexasRanger.write_ast(swriter, "ob_"+args[0]);

		// // print created ast to stdout
		// System.out.println(swriter.getBuffer().toString());
	}
}