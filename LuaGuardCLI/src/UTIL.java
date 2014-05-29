import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;



public final class UTIL {
	//*******************************************************************************************************************************************************************************************

	private final static String OUTPUT_SUFFIX = "_obfuscated";

	//*******************************************************************************************************************************************************************************************
	//main driver for luaguard
	public static void driver(int obfuscationLevels, SimpleEntry<ArrayList<File>, ArrayList<String>> ioMap, SimpleEntry<ArrayList<File>, ArrayList<String>> astMap, ArrayList<String> blacklist){
		ArrayList<File> ioInput = ioMap.getKey();
		ArrayList<String> ioOutput = ioMap.getValue();
		ArrayList<String> astOutput = astMap.getValue();
		
		walker obfuscate0 = new walker();
		ExchangeOfLetters obfuscate1 = new ExchangeOfLetters();
		MLAobfuscate obfuscate2 = new MLAobfuscate();
		
		File ast;
		String luaOutputPath;
		for(int i = 0; i < ioInput.size(); ++i){
			ast = parse(ioInput.get(i), astOutput.get(i));
			luaOutputPath = ioOutput.get(i);

			if(ast != null){
				if(obfuscationLevels == 1)
					obfuscate0.obfuscate(ast,  blacklist);
				
				else if(obfuscationLevels == 2)
					obfuscate1.obfuscate(ast,  blacklist);
				
				else if(obfuscationLevels == 3)
					obfuscate2.obfuscate(ast,  blacklist);
					
				unparse(ast, luaOutputPath);
			}
		}
	}
	//*******************************************************************************************************************************************************************************************
	public static void runProcess(File inputRedirect, File outputRedirect, String... commandWithParameters){ //String... is var args, use as many string params or an array of strings
		try {
			ProcessBuilder p = new ProcessBuilder(commandWithParameters).redirectInput(inputRedirect).redirectOutput(outputRedirect).redirectError(ProcessBuilder.Redirect.INHERIT);
			//ProcessBuilder p = new ProcessBuilder(commandWithParameters).redirectInput(ProcessBuilder.Redirect.INHERIT).redirectOutput(ProcessBuilder.Redirect.INHERIT).redirectError(ProcessBuilder.Redirect.INHERIT);
			//System.out.println(p.command());		
			p.start().waitFor();
		} catch (InterruptedException e) {
			System.err.println("Process was interupted. " + commandWithParameters);
		} catch (IOException e) {
			System.err.println("Was unable to read from file " + inputRedirect.getAbsolutePath() + " or possibly write to " + commandWithParameters[commandWithParameters.length-1]);
		}
	}


	//node luamin/node_modules/luaparse/bin/luaparse.js --scope -f lua > ast
	@SuppressWarnings("resource")
	private static File parse(File lua, String astPath){
		String node = "node";
		String path = "luamin" + File.separator + "node_modules" + File.separator + "luaparse" + File.separator + "bin" + File.separator;

		if(System.getProperty("os.name").contains("Linux")){
			node = "nodejs";
		}
		
		File ast = renamer(astPath);
		
		// node luamin/node_modules/luaparse/bin/luaparse.js --scope -f INPUTFILE > OUTPUTFILE
		UTIL.runProcess(lua, ast, node, path+"luaparse.js", "--scope", "-f", lua.getAbsolutePath(), ">", ast.getAbsolutePath());
		
		
		//Replace the empty parse preceding the actual parse.
		try {
			String s = new String(Files.readAllBytes(Paths.get(ast.getAbsolutePath())));
			s = s.replaceFirst("^(\\{\"type\":\"Chunk\",\"body\":\\[],\"comments\":\\[],\"globals\":\\[]\\}\\s)?", ""); //remove front garbage
			s = s.replaceAll("(?m)(>:\\s\\[1:0]\\sUnexpected symbol '>' near\\s'<eof>'\\n\\{\"type\":\"Chunk\",\"body\":\\[],\"comments\":\\[],\"globals\":\\[]}\\n)?$", ""); //remove back garbage
			FileOutputStream f = new FileOutputStream(ast, false);
			f.write(s.getBytes());
			f.flush();
			f.close();
			
			//if the empty parse was the only parse revert to the empty parse because it was an empty input file
			String l = new String(Files.readAllBytes(Paths.get(ast.getAbsolutePath()))).trim();
			if(l == ""){
				f = new FileOutputStream(ast, false);
				f.write(l.getBytes());
				f.flush();
				f.close();
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ast;		
	}
	//node luamin/bin/luamin.js -f ast > lua
	private static void unparse(File ast, String outputPath){
		String node = "node";
		String path = "luamin" + File.separator + "bin" + File.separator;

		if(System.getProperty("os.name").contains("Linux")){
			node = "nodejs";
		}
		
		File lua = renamer(outputPath);
		try {
			lua.createNewFile();
		
			//node luamin/bin/luamin.js -f INPUTFILE > OUTPUTFILE
			UTIL.runProcess(ast, lua,  node, path+"luamin.js", "-c", ast.getAbsolutePath(), ">", lua.getAbsolutePath());

		} catch (IOException e) {
			System.out.println("Was unable to create file " + ast.getAbsolutePath());
		} 
	}
	//This is only intended for parse and unparse to call
	private static File renamer(String suggestedPath){
		File f = new File(suggestedPath);
		
		//create necessary directories
		if(!f.getParentFile().exists())
			f.getParentFile().mkdirs();
		
		//loop terminates once f doesn't have a name collision.
		String name;
		while(f.exists()){
			int n = Integer.valueOf(f.getName().replaceAll("^.*?(_obfuscated)+|(\\..+)?$", "")) + 1; //anycharacters upto the last occurance of _obfuscated. on the right side remove optional dot followed by anything
			name = getFileNameWithoutExtension(f);
			name = name.replaceAll("\\d+(\\..+)?$","") + String.valueOf(n); //Looking at the end remove the optional dot followed by anything (.lua) and any digits preceeding

			f = new File(f.getParentFile().getAbsolutePath() + File.separator + name + "." + getDottlessFileExtension(suggestedPath));
		}
		return f;
	}
	//only used for a debugging method in CLI. Looks up the with the highest suffix from the suggestedPath
	public static File lookup(String suggestedPath){
		File f;
		boolean flag = false;
		f = new File(suggestedPath);
		String name;
		while(f.exists()){
			if(!flag)
				flag = true;
			int n = Integer.valueOf(f.getName().replaceAll("^.*?(_obfuscated)+|(\\..+)?$", "")) + 1; //anycharacters upto the last occurance of _obfuscated. on the right side remove optional dot followed by anything
			name = getFileNameWithoutExtension(f);
			name = name.replaceAll("\\d+(\\..+)?$","") + String.valueOf(n); //Looking at the end remove the optional dot followed by anything (.lua) and any digits preceeding

			f = new File(f.getParentFile().getAbsolutePath() + File.separator + name + "." + getDottlessFileExtension(suggestedPath));
		}
		if(flag){
			int n = Integer.valueOf(f.getName().replaceAll("^.*?(_obfuscated)+|(\\..+)?$", "")); //anycharacters upto the last occurance of _obfuscated. on the right side remove optional dot followed by anything
			name = getFileNameWithoutExtension(f);
			name = name.replaceAll("\\d+(\\..+)?$","") + String.valueOf(n-1); //Looking at the end remove the optional dot followed by anything (.lua) and any digits preceeding

			f = new File(f.getParentFile().getAbsolutePath() + File.separator + name + "." + getDottlessFileExtension(suggestedPath));
		}
			
		return f;
	}
	//*******************************************************************************************************************************************************************************************
	public static ArrayList<String> getBlacklist(File f) throws FileNotFoundException{
		//Returns the blacklist. If the parameter is null then it returns the default blacklist else it returns the union of the default and the file provided.
		String path = "." + File.separator + "LuaGuardCLI" + File.separator + "keyword_blacklist.txt";
		ArrayList<String> list = readBlacklist(UTIL.getFile(path));	
		if(f != null)
			list.addAll(readBlacklist(f));
		
		return list;
	}
	private static ArrayList<String> readBlacklist(File f){
		//read file of newline seperated strings to be put on the blacklist
		ArrayList<String> list = new ArrayList<String>();
		if(f == null)
			return list;
		try{
			
			BufferedReader rdr = new BufferedReader(new FileReader(f));
			
			String line;
			while((line = rdr.readLine()) != null)
				list.add(line.trim());
			
			rdr.close();
		}
		catch(IOException e){
			System.out.println("Unable to read from file " + f.getAbsolutePath());
		}
		return list;
	}
	//*******************************************************************************************************************************************************************************************
	public static File getDir(String path) throws SecurityException, FileNotFoundException {
		File dir = new File(path);
		if(dir.isDirectory())
			return dir;
		else if(!dir.exists()){
			if(dir.mkdirs())
				return dir;
			throw new SecurityException(dir.toString() + " cannot be created due to os security settings");
		}
		else 
			throw new FileNotFoundException(dir.toString() + " is not a directory");
		
	}
	public static File getFile(String path) throws FileNotFoundException {
		File f = new File(path);
		if(f.isFile())
			return f;
		else{
			if(!f.exists())
				throw new FileNotFoundException(f.toString() + " Does not exist");			
			throw new FileNotFoundException(f.toString() + " is not a file");
		}
	}
	//*******************************************************************************************************************************************************************************************
	public static String getFileNameWithoutExtension(String s){
		int pos = s.lastIndexOf(".");
		if (pos > 0) {
		    s = s.substring(0, pos);
		}
		return s;
	}
	public static String getFileNameWithoutExtension(File f){
		return getFileNameWithoutExtension(f.getName());
	}
	public static String getDottlessFileExtension(String f){
		String extension = "";

		int i = f.lastIndexOf('.');
		if (i >= 0) {
		    extension = f.substring(i+1);
		}
		return extension;
	}
	//*******************************************************************************************************************************************************************************************
	public static SimpleEntry<ArrayList<File>, ArrayList<String>> seperatedFilesToList(String seperatedList, File targetDirectory) throws FileNotFoundException, SecurityException {
		return seperatedFilesToList(seperatedList, targetDirectory, false);
	}
	
	public static SimpleEntry<ArrayList<File>, ArrayList<String>> seperatedFilesToList(String seperatedList, File targetDirectory, boolean astDir) throws FileNotFoundException, SecurityException {
		String[] paths = seperatedList.split(File.pathSeparator);
		ArrayList<File> fileList = new ArrayList<File>();
		
		ArrayList<String> output = new ArrayList<String>();
		SimpleEntry<ArrayList<File>, ArrayList<String>> pair = new SimpleEntry<ArrayList<File>, ArrayList<String>>(fileList, output);
		
		String fileExtension = (astDir)? "ast" : null;
		
		File file;
		for(String path : paths){
			file = new File(path);
			//##################### path is a file
			if(file.isFile()){
				fileList.add(file);
				output.add(targetDirectory.getAbsolutePath() + File.separator + getFileNameWithoutExtension(file.getName()) + OUTPUT_SUFFIX + "1." + ((fileExtension != null)? fileExtension : getDottlessFileExtension(file.getName())));
			}
			//##################### path is a Directory
			else if(file.isDirectory()){
				String parent = path;
				try{
					String[] children = file.list();
					parent += File.separator;
					
					String child;
					for(int i = 0; i < children.length; ++i){
						child = children[i];
						if(new File(parent + child).isDirectory())
							targetDirectory = new File(targetDirectory.getAbsolutePath() + File.separator + child.toString() + File.separator);
						
						SimpleEntry<ArrayList<File>, ArrayList<String>> pack =  seperatedFilesToList(parent + child, targetDirectory, astDir);
						fileList.addAll(pack.getKey());
						output.addAll(pack.getValue());
					}
				}
				catch (SecurityException e){
					throw new SecurityException("Cannot list child files of" + file.toString());
				}
			}
			else 
				throw new FileNotFoundException(path + " does not exist");
		}
		
		return pair;
	}
	//*******************************************************************************************************************************************************************************************
}