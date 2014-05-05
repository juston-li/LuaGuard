/*
 * Author: Tommy Dodson
 * Team: Indean Runners - Lua Guard
 * Date: 4/24/2014
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class CLI {

	//Forward controls xxx.jar [type] [level] [Lua source input] [AST output] [obuscated output] [blacklist] [custom name list]
	//Reverse controls xxx.jar [type] [Lua source input] [AST input] [deobfuscated output]
	
	private static String TYPE_FORWARD = "-f"; //forward
	private static String TYPE_REVERSE = "-r"; //reverse
	
	private static int OBFUSCATION_LEVELS = 3; //number of obfuscation levels
	
	
	private static File getDir(String path) throws SecurityException, FileNotFoundException {
		File dir = new File(path);
		if(dir.isDirectory())
			return dir;
		else if(!dir.exists()){
			if(dir.mkdirs())
				return dir;
			throw new SecurityException();
		}
		else 
			throw new FileNotFoundException();
		
	}
	private static File getFile(String path) throws FileNotFoundException {
		File f = new File(path);
		if(f.isFile())
			return f;
		else{
			if(!f.exists())
				throw new FileNotFoundException();
		throw new FileNotFoundException();
		}
	}
	private static LinkedList<File> seperatedFilesToList(String s) throws FileNotFoundException, SecurityException {
		String[] paths = s.split(File.pathSeparator);
		LinkedList<File> fileList = new LinkedList<File>();
		
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
					throw new SecurityException();
				}
			}
			else 
				throw new FileNotFoundException();
		}
		
		return fileList;
	}
	private static boolean[] obfuscationLevels(String s) throws NumberFormatException {
		//parse args1 for integer then convert to binary and reverse for easier processing
		
		boolean[] levels = new boolean[OBFUSCATION_LEVELS];
		Arrays.fill(levels, false);
		try{
			char[] binLevels = new StringBuffer(Integer.toBinaryString(Integer.parseInt(s,10))).reverse().toString().toCharArray();
			int min = Math.min(OBFUSCATION_LEVELS, binLevels.length);
			
			for(int i = 0; i < min; ++i)
				if(binLevels[i] == '1')
					levels[i] = true;
	
		}
		catch (NumberFormatException e){
			throw new NumberFormatException();
		}

		return levels;
	}
	public static void demoForward(boolean[] level, List<File> luaSource, File astOutputDir, File outputSource, List<File> blacklist, File cstmNamingList){
		System.out.println("Forward Demo");
		String s;
		for(int i = 0; i < level.length; ++i){
			s = "\t";
			s += level[i]? "Will apply " : "Will NOT apply ";
			s += "obfuscation level " + i;
			System.out.println(s);
		}
		System.out.println("\n\n");
		System.out.println("Input Lua Source");
		
		for(File f : luaSource){
			s = "\t";
			s += f.getAbsolutePath().toString();
			System.out.println(s);
		}
		System.out.println("\n\n");
		System.out.println("AST output DIR");
		System.out.println("\t" + astOutputDir.getAbsolutePath().toString());
		
		System.out.println("\n\n");
		System.out.println("Output Lua Source");
		System.out.println("\t" + outputSource.getAbsolutePath().toString());
		
		System.out.println("\n\n");
		System.out.println("Blacklist file");
		for(File f : blacklist){
			s = "\t";
			s += f.getAbsolutePath().toString();
			System.out.println(s);
		}
		System.out.println("\n\n");
		System.out.println("Custom Naming List Path");
		System.out.println("\t" + cstmNamingList.getAbsolutePath().toString());
		
	}
	public static void main(String[] args) {
		
		if(args.length > 0){
			
		
			String type = args[0];
			if(type.equals(TYPE_FORWARD)){
				if(args.length == 7){
					
					try {
						boolean[] levels = obfuscationLevels(args[1]);
						LinkedList<File> luaSource = seperatedFilesToList(args[2]);
						File astOutputDir = getDir(args[3]);
						File outputSource = getDir(args[4]);
						LinkedList<File> blacklist = seperatedFilesToList(args[5]);
						File customNameList = getFile(args[6]);
						
						demoForward(levels, luaSource, astOutputDir, outputSource, blacklist, customNameList);
						
						
					}
					catch(NumberFormatException e){
						System.err.println("The level parameter was not formated as a valid base 10 integer.");
						System.err.println("Run this program without any parameters to see the help page.");
					}
					catch(FileNotFoundException e){
						System.err.println("A file was not found in one of the path specifiers");
						System.err.println("Run this program without any parameters to see the help page.");
					}
					catch(SecurityException e){
						System.err.println("A security issue on a directory or file is preventing the application from running normally");
						System.err.println("Run this program without any parameters to see the help page.");
					}
				}
				else {
					System.err.println("There is a discrepency between the options provided and the template for Forward controls we found " + args.length + " parameters");
					System.err.println("Run this program without any parameters to see the help page.");
				}
			}
			else if(type.equals(TYPE_REVERSE)){
				if(args.length == 4){
					try {
						List<File> luaSource = seperatedFilesToList(args[1]);
						File astInputDir = getDir(args[2]);
						List<File> outputSource = seperatedFilesToList(args[3]);
						
					}
					catch(NumberFormatException e){
						System.err.println("The level parameter was not formated as a valid base 10 integer.");
						System.err.println("Run this program without any parameters to see the help page.");
					}
					catch(FileNotFoundException e){
						System.err.println("A file was not found in one of the path specifiers");
						System.err.println("Run this program without any parameters to see the help page.");
					}
					catch(SecurityException e){
						System.err.println("There is a discrepency between the options provided and the template for Forward controls");
						System.err.println("Run this program without any parameters to see the help page.");
					}
				}
				else {
					System.err.println("There is a discrepency between the options provided and the template for Reverse controls we found " + args.length + " parameters");
					System.err.println("Run this program without any parameters to see the help page.");
				}
			}
			else {
				System.err.println("Cannot recognize first option use either -f or -r");
				System.err.println("Run this program without any parameters to see the help page.");
			}
			
			
		} else {
			System.out.println("Lua Guard all purpose obfuscation utility for LUA source code.");
			System.out.println("To Use Lua Guard follow one of the following use commands\n\n");
			
			System.out.println("Forward\n");
			System.out.println("\txxx.jar [type] [level] [Lua source input] [AST output] [obuscated output] [blacklist] [custom name list]\n\n");
			
			System.out.println("Reverse\n");
			System.out.println("\txxx.jar [type] [Lua source input] [AST input] [deobfuscated output]\n\n");
			
			System.out.println("[type]   -   Obfuscation direction type options are forward and reverse\n");
			System.out.println("\t      -f   or   -r\n\n");
			
			System.out.println("[level]   -   Integer representation of obfuscation level.");
			System.out.println("\t      The integer is converted to the binary representation and read from least significant bit to most significant bit.");
			System.out.println("\t      example the integer 25 is 11001 in binary. The first, second, and fifth obfuscation methods would be applied to the input\n\n");
			
			System.out.println("[Lua source input]   -   the input lua source to be obfuscated.");
			System.out.println("\t      The input can be either a file a folder or a seperated combination of the two using a ; for windows and a : for unix as path seperators.");
			
		}

	}

}
