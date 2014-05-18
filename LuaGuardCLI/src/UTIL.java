import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.rmi.CORBA.Util;


public final class UTIL {
	
	//####
	//###
	//##
	//#
	//obfuscators will be registered with LuaGuard by adding an instance to this array List
	private static ArrayList<Obfuscator> obfuscators = new ArrayList<Obfuscator>();
	//#
	//##
	//###
	//####
	
	private final static String OUTPUT_SUFFIX = "_obfuscated";
/*
	public static void driver(int obfuscationLevels, SimpleEntry<ArrayList<File>, ArrayList<String>> inputList, File outputDir, ArrayList<String> blacklist){
		boolean[] levels = obfuscationLevels(obfuscationLevels);
		String luaPath;
		File f;
		for(int i = 0; i < inputList.size(); ++i){
			
			luaPath = inputList.get(i).getAbsolutePath();
			f = parse(inputList.get(i));

			if(f != null){
				for(int j = 0; j < obfuscators.size(); ++j){
					if(levels[j])
						obfuscators.get(j).obfuscate(f, blacklist);
				}
				unparse(f, luaPath);
			}
		}
	}
*/
	public static void runProcess(File outputRedirect, String... commandWithParameters){ //String... is var args, use as many string params or an array of strings
		try {
			new ProcessBuilder(commandWithParameters).redirectOutput(outputRedirect).start().waitFor();
		} catch (InterruptedException e) {
			System.err.println("Process was interupted. " + commandWithParameters);
		} catch (IOException e) {
			System.err.println("Was unable to write to file. " + outputRedirect.getAbsolutePath());
		}
	}
	
	private static File parse(File lua){
		String node = "node";

		if(System.getProperty("os.name").contains("Linux")){
			node = "nodejs";
		}
		
		File f;
		try {
			f = File.createTempFile(UTIL.getFileNameWithoutExtension(lua) + OUTPUT_SUFFIX, new Date().toString());
			f.deleteOnExit();
			String path = "luamin" + File.separator + "node_modules" + File.separator + "luaparse" + File.separator + "bin" + File.separator;
			
			UTIL.runProcess(f, node, path+"luaparse", "-f", lua.getAbsolutePath());

			return f;
			
		} catch (IOException e1) {
			System.out.println("Was not able to create a temp ast file for " + lua.getAbsolutePath());
		}
		return null;
		
	}
	private static File unparse(File ast, String orginalFilePath){
		/* fill in luamin use here
		String path = "luamin" + File.separator + "node_modules" + File.separator + "luaparse" + File.separator + "bin" + File.separator;
		ProcessBuilder p = new ProcessBuilder(node, path+"luaparse", "-f", lua.getAbsolutePath());
		p.redirectOutput(new File(astDir + File.separator + FS.getFileNameWithoutExtension(lua) + OUTPUT_SUFFIX + ".ast"));
		p.start();
		*/
		return ast;//TODO
	}
	
	public static boolean[] obfuscationLevels(int n)  {
		
		boolean[] levels = new boolean[obfuscators.size()];
		Arrays.fill(levels, false);

		char[] binLevels = new StringBuffer(Integer.toBinaryString(n)).reverse().toString().toCharArray();
		int min = Math.min(obfuscators.size(), binLevels.length);
		
		for(int i = 0; i < min; ++i)
			if(binLevels[i] == '1')
				levels[i] = true;

		return levels;
	}
	
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
	public static String getFileNameWithoutExtension(File f){
		String name = f.getName();
		int pos = name.lastIndexOf(".");
		if (pos > 0) {
		    name = name.substring(0, pos);
		}
		return name;
	}
	
	/* Orginal
	 
	public static ArrayList<File> seperatedFilesToList(String s) throws FileNotFoundException, SecurityException {
		String[] paths = s.split(File.pathSeparator);
		ArrayList<File> fileList = new ArrayList<File>();
		
		for(String path : paths){
			File file = new File(path);
			if(file.isFile())
				fileList.add(file);
			
			else if(file.isDirectory()){
				String parent = path;
				try{
					String[] children = file.list();
					parent += File.separator;
					
					for(String child : children)
						fileList.addAll(seperatedFilesToList(parent + child));	
				}
				catch (SecurityException e){
					throw new SecurityException("Cannot list child files of" + file.toString());
				}
			}
			else 
				throw new FileNotFoundException(path + " does not exist");
		}
		
		return fileList;
	}
	 
	 
	 */
	
	
	
	public static SimpleEntry<ArrayList<File>, ArrayList<String>> seperatedFilesToList(String s, File outputSubDirectory) throws FileNotFoundException, SecurityException {
		//hashmap<inputFiles, outputFilePathPromise> There may be a name collision with outputFiles and conflicts will be delt with by the unparser.		
		
		String[] paths = s.split(File.pathSeparator);
		ArrayList<File> fileList = new ArrayList<File>();
		
		ArrayList<String> output = new ArrayList<String>();
		SimpleEntry<ArrayList<File>, ArrayList<String>> pair = new SimpleEntry<ArrayList<File>, ArrayList<String>>(fileList, output);
		
		File file;
		for(String path : paths){
			file = new File(path);
			if(file.isFile()){
				fileList.add(file);
				output.add(outputSubDirectory.getAbsolutePath() + File.separator + file.getName());
			}
			
			else if(file.isDirectory()){
				String parent = path;
				try{
					String[] children = file.list();
					parent += File.separator;
					
					String child;
					for(int i = 0; i < children.length; ++i){
						child = children[i];
						if(new File(parent + child).isDirectory())
							outputSubDirectory = new File(outputSubDirectory.getAbsolutePath() + File.separator + child.toString() + File.separator);
						
						SimpleEntry<ArrayList<File>, ArrayList<String>> other =  seperatedFilesToList(parent + child, outputSubDirectory);
						fileList.addAll(other.getKey());
						output.addAll(other.getValue());
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

}
