// read javax.json documentation at docs.oracle.com/javaee/7/api/javax/json/package-summary.html
//
// to compile add javax.json-api-1.0.jar to the classpath
// example: javac -Xlint -cp ~/Documents/javax.json-api-1.0.jar:. obfuscator.java
//
// to run add javax.json-1.0.4.jar to the classpath
// example: java -cp ~/Documents/javax.json-1.0.4.jar:. obfuscator

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import java.lang.NullPointerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class obfuscator{
	// blacklist
	private ArrayList<String> blackList;
	// ast
	private JsonObject ast;
	private JsonObject obfuscated_ast;
	private char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    private Map<String, String> ht;

//	// prints formated ast
//	private void obf0(JsonValue tree, String key, JsonObjectBuilder obj, JsonArrayBuilder arr, JsonBuilderFactory fac){
//		JsonObjectBuilder subObj;
//		JsonArrayBuilder subArr;
//		switch (tree.getValueType()){
//			case OBJECT:
//				JsonObject object = (JsonObject)tree;
//				for (String name:object.keySet())
//					obf0(object.get(name), name, obj, arr, fac);
//				break;
//			case ARRAY:
//				JsonArray array = (JsonArray)tree;
//				System.out.println(key+" : ");
//				subArr = fac.createArrayBuilder();
//				subObj = fac.createObjectBuilder();
//				for (JsonValue val:array){
//					obf0(val, null, subObj, subArr, fac);
//				}
//				obj.add(key,subArr);
//				break;
//			case STRING:
//				JsonString jstr = (JsonString)tree;
//				String str = jstr.toString();
//
//				if(key.equals("name") && !check_blacklist(str)){
//					// check if mapped already
//					if (check_mangled(str)){
//						//System.out.println(str+" mapped to "+ht.get(str));
//						// use mapped name
//						System.out.println(key+" : "+ht.get(str));
//						obj.add(key,ht.get(str));
//                        if(arr != null)
//                            arr.add(obj);
//						break;
//					} else {
//						// map to new name
//						String temp = getObfuscatedName();
//						// System.out.println("key: "+key+"\t"+str+" map to "+temp);
//						ht.put(str,temp);
//						// convert String to JsonString
//						System.out.println(key+" : "+temp);
//						obj.add(key,temp);
//                        if(arr != null)
//                            arr.add(obj);
//						break;
//					}
//				}
//				System.out.println(key+" : "+str);
//				obj.add(key,jstr);
//                if(arr != null)
//                    arr.add(obj);
//				break;
//			case NUMBER:
//				JsonNumber num = (JsonNumber)tree;
//				System.out.println(key+" : "+num.toString());
//				obj.add(key,num);
//                if(arr != null)
//                    arr.add(obj);
//				break;
//			case TRUE:
//			case FALSE:
//			case NULL:
//				System.out.println(key+" : "+tree.getValueType().toString());
//				obj.add(key,tree);
//                if(arr != null)
//                    arr.add(obj);
//				break;
//		}
//	}

	private JsonObjectBuilder obf0(JsonValue tree, String key, JsonObjectBuilder obj, JsonArrayBuilder arr, JsonBuilderFactory fac){
		JsonObjectBuilder subObj;
		JsonArrayBuilder subArr;
		switch (tree.getValueType()){
			case OBJECT:
				JsonObject object = (JsonObject)tree;
				for (String name:object.keySet())
					obf0(object.get(name), name, obj, arr, fac);
                return obj;
			case ARRAY:
				JsonArray array = (JsonArray)tree;
				System.out.println(key+" : ");
				subArr = fac.createArrayBuilder();
				subObj = fac.createObjectBuilder();
				for (JsonValue val:array){
					subArr.add(obf0(val, null, subObj, subArr, fac));
				}
				return obj.add(key,subArr);
			case STRING:
				JsonString jstr = (JsonString)tree;
				String str = jstr.toString();
                // check if str to mangle and is not in blacklist
				if(key.equals("name") && !check_blacklist(str)){
					// check if mapped already
					if (check_mangled(str)){
						//System.out.println(str+" mapped to "+ht.get(str));
						// use mapped name
						System.out.println(key+" : "+ht.get(str));
						return obj.add(key,ht.get(str));
                    
                    // else it is not mapped
					} else {
						// map to new name
						String temp = getObfuscatedName();
						// System.out.println("key: "+key+"\t"+str+" map to "+temp);
						ht.put(str,temp);
						// convert String to JsonString
						System.out.println(key+" : "+temp);
						return obj.add(key,temp);
						
					}
				}
				System.out.println(key+" : "+str);
				return obj.add(key,jstr);
			case NUMBER:
				JsonNumber num = (JsonNumber)tree;
				System.out.println(key+" : "+num.toString());
				return obj.add(key,num);
			case TRUE:
			case FALSE:
			case NULL:
				System.out.println(key+" : "+tree.getValueType().toString());
				return obj.add(key,tree);
		}
        return obj;
	}

	// check blacklist
	// true if name is in blacklist
	private Boolean check_blacklist(String name){
		if (blackList != null && blackList.contains(name)){
			return true;
		} else {
			return false;
		}
	}

	// check if name has already been mangled
	// true if name has already been mangled
	private Boolean check_mangled(String name){
		if (ht != null && ht.containsKey(name)){
			return true;
		} else {
			return false;
		}
	}

    // ----- ADD STRING OBFUSCATION CODE TO THIS METHOD -----
	// generate a random string
	private String getObfuscatedName(){
		Random rand = new Random();
		String str = "";
		str += letters[rand.nextInt(26)];
		str += rand.nextInt(100);
		return str;
	}

	// prints formated ast
	private void print(JsonValue tree, String key, String tab){
		if (key != null){
			System.out.print(tab+"Key: "+key+": ");
		}
		switch (tree.getValueType()){
			case OBJECT:
				System.out.println(tab+"OBJECT");
				JsonObject object = (JsonObject)tree;
				for (String name:object.keySet())
					print(object.get(name), name, tab+"  ");
				break;
			case ARRAY:
				System.out.println("ARRAY");
				JsonArray array = (JsonArray)tree;
				for (JsonValue val:array)
					print(val, null, tab+"  ");
				break;
			case STRING:
				JsonString str = (JsonString)tree;
				System.out.println("String "+str.getString());
				break;
			case NUMBER:
				JsonNumber num = (JsonNumber)tree;
				System.out.println("NUMBER "+num.toString());
				break;
			case TRUE:
			case FALSE:
			case NULL:
				System.out.println(tree.getValueType().toString());
				break;
		}
	}

	public obfuscator(){
		blackList = new ArrayList<String>();
		ht = new HashMap<String,String>();
	}

	// read in an ast
	public void read(String filename){
		try {
			// open file and read ast
			InputStream fis = new FileInputStream(filename);
			JsonReader reader = Json.createReader(fis);
			ast = reader.readObject();
			// close file and reader
			reader.close();
			fis.close();
		} catch (IOException e){
			System.err.println(e.getMessage());
		}
	}

	// write out an ast
	public void write(String filename){
		try {
			// open file and write ast
			OutputStream fos = new FileOutputStream(filename);
			JsonWriter writer = Json.createWriter(fos);
			writer.writeObject(obfuscated_ast);
			// close writer
			writer.close();
		} catch (FileNotFoundException e){
			System.err.println(e.getMessage());
		}
	}

	// read in blacklist
	public void read_blacklist(String filename){
		try {
			// open file and read into blacklist
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			blackList = new ArrayList<String>();
			while((line = br.readLine()) != null){
				// TODO: check that line has no white space
				blackList.add(line);
			}
			// close file
			br.close();
		} catch (IOException e){
			System.err.println(e.getMessage());
		}
	}

	// obfuscation name mangler
	public void name_mangler(){
		Map<String,Integer> config = new HashMap<String,Integer>(); 
		JsonBuilderFactory fac = Json.createBuilderFactory(config);
		JsonObjectBuilder obfuscated_ast_builder = fac.createObjectBuilder();
		obf0(ast,null,obfuscated_ast_builder, null, fac);
		obfuscated_ast = obfuscated_ast_builder.build();
	}

    // print the source ast to stdout
	public void print_ast(){
		print(ast,null,"");
	}

    // print the obfuscated ast to stdout
	public void print_obf(){
		print(obfuscated_ast,null,"");
	}

	public static void main(String[] args){
		// open test and write to ob_test
		obfuscator test = new obfuscator();
		test.read("test.asc");	// test is a ast to obfuscate
		//test.print_ast();
		//System.out.println("\n\n-------------------------\n");
		test.name_mangler();
		//test.print_obf();
		test.write("ob_test.asc");
	}
}