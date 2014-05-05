/*
 * Author: Tommy Dodson
 * Team: Indean Runners - Lua Guard
 * Date: 4/24/2014
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class CLI {

	//Forward controls xxx.jar [level][Lua file path input][Data dictionary path output][output after obfuscation][blacklist][custom name list] 
	//example xxx.jar 5 "C:\Users\tommy\Desktop\tmp;C:\Users\tommy\Desktop\tmp 2" C:\Users\tommy\Desktop\AST C:\Users\tommy\Desktop\Output -blacklist="C:\Users\tommy\Desktop\tmp 2\blacklisted.txt" -customlist=C:\Users\tommy\Desktop\custom.txt
	
	private static int OBFUSCATION_LEVELS = 3; //number of obfuscation levels
	private static String BLACKLIST_SWITCH = "-blacklist";
	private static String CUSTOMNAME_SWITCH = "-customlist";

	private static HashSet<String> populateBlacklist(File f){
		//read file of newline seperated strings to be put on the blacklist
		HashSet<String> set = new HashSet<String>();
		if(f == null)
			return set;
		try{
			
			BufferedReader rdr = new BufferedReader(new FileReader(f));
			
			String line;
			while((line = rdr.readLine()) != null)
				set.add(line.trim());
			
			rdr.close();
		}
		catch(IOException e){
			System.out.println("Unable to read from file " + f.getAbsolutePath());
		}
		return set;
	}
	private static HashMap<String, String> populateCustomlist(File f) {
		// populate map with pairs <a,b>
		// a is the original path to a file
		// b is the new name of the file
		HashMap<String, String> map = new HashMap<String, String>();
		if(f == null)
			return map;
		try{
			BufferedReader rdr = new BufferedReader(new FileReader(f));
			
			String[] pairs;
			String line;
			while((line = rdr.readLine()) != null){
				pairs = line.split("\t");
				if(pairs.length == 2)
					map.put(pairs[0].trim(), pairs[1].trim());
			}
			
			rdr.close();
		}
		catch(IOException e){
			System.out.println("Unable to read from file " + f.getAbsolutePath());
		}
		return map;
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
			throw new NumberFormatException(s + " is not represented as a base 10 integer");
		}

		return levels;
	}
	
	private static String processOptionalArgs(String[] str, String option) {
		
		for(String s : str){
			String[] parts = s.split("=");
			
			if(parts.length == 2 && parts[0].equals(option))
				return parts[1];
		}
		return null;		
	}
	public static void main(String[] args) {
		
		if(args.length > 0){
			
			if(4 <= args.length && args.length <= 6){
				
				//if(NODE && Luaparse are in the path)
				
				try {
					
					boolean[] levels = obfuscationLevels(args[0]);
					LinkedList<File> luaSource = FS.seperatedFilesToList(args[1]);
					File astOutputDir = FS.getDir(args[2]);
					File outputSource = FS.getDir(args[3]);
					File blacklist = null;
					File customNameList = null;
					
					HashSet<String> blacklistSet = new HashSet<String>();
					HashMap<String, String> customNameMap = new HashMap<String, String>();
					if(args.length > 4){
						String[] optionalArgs = Arrays.copyOfRange(args, 4, args.length);
						String path = processOptionalArgs(optionalArgs, BLACKLIST_SWITCH);
						blacklist = (path != null)? FS.getFile(path) : null;
						blacklistSet = populateBlacklist(blacklist);
						
						path = processOptionalArgs(optionalArgs, CUSTOMNAME_SWITCH);
						customNameList = (path != null)? FS.getFile(path) : null;
						customNameMap = populateCustomlist(customNameList);
					}
					
					demo(levels, luaSource, astOutputDir, outputSource, blacklist, customNameList);

					if(System.getProperty("os.name").contains("Windows")){
						String tmppath = "C:\\node_modules\\luaparse\\bin\\";
						ProcessBuilder p;
						for(File f : luaSource){
							p = new ProcessBuilder("C:\\Program Files\\nodejs\\node_modules\\.bin\\luaparse.cmd","-f",f.getAbsolutePath());
							p.redirectOutput(new File(astOutputDir + File.separator + FS.getFileNameWithoutExtension(f) + ".txt"));
							p.start();
						}
						
					}
					else { //linux / mac
						ProcessBuilder p = new ProcessBuilder("luaparse","-f",luaSource.getFirst().getAbsolutePath());
						p.redirectOutput(new File(astOutputDir + "\\log.txt"));
						p.start();
					}
				
			
				}
				
				catch(NumberFormatException e){
					System.err.println(e.getMessage());
					System.err.println("Run this program without any parameters to see the help page.");
				}
				catch(FileNotFoundException e){
					System.err.println(e.getMessage());
					System.err.println("Run this program without any parameters to see the help page.");
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch(SecurityException e){
					System.err.println(e.getMessage());
					System.err.println("Run this program without any parameters to see the help page.");
				} 
			}
			else {
				System.err.println("There is a discrepency between the options provided and the template we found " + args.length + " parameters when 4, 5, or 6 are allowed");
				System.err.println("Run this program without any parameters to see the help page.");
			}
			

			
			
		} else {
			// psudo "man" page
			
			String TAB = "\t";
			String NL = "\n";
			
			System.out.println("NAME");
			System.out.println(TAB + "lg - Lua Guard" + NL);
			
			System.out.println("SYNOPSIS");
			System.out.println(TAB + "lg [level][Lua file path input][Data dictionary path output][output after obfuscation][blacklist][custom name list]" + NL);
			
			System.out.println("DESCRIPTION");
			System.out.println(TAB + "Lua Guard all purpose obfuscation utility for LUA source code.");
			System.out.println(TAB + "To Use Lua Guard follow one of the following use commands" + NL);
			
			System.out.println("[level]");
			System.out.println(TAB + "Base 10 integer representation of obfuscation level");
			System.out.println(TAB + "options here..." + NL);
			
			System.out.println("[Lua file path input]");
			System.out.println(TAB + "The input lua source to be obfuscated.");
			System.out.println(TAB + "can be either a file a folder or a seperated combination of the two using the OS dependant file seperator." + NL);
			
			System.out.println("[Data dictionary path output]");
			System.out.println(TAB + "The output directory where Dictionary files will be stored.");
			System.out.println(TAB + "The command will attempt to create the directory if it does not exist." + NL);
			
			System.out.println("[output after obfuscation]");
			System.out.println(TAB + "The directory where the obfuscated code will be placed.");
			System.out.println(TAB + "The command will attempt to create the directory if it does not exist." + NL);
			
			System.out.println("[-blacklist=file]");
			System.out.println(TAB + "A file containing a new line seperated list of variables to not rename.");
			System.out.println(TAB + "white space will be trimmed from the list of variables" + NL);
			
			System.out.println("[-customlist=file]");
			System.out.println(TAB + "A file containing a new line seperated list of tab delimeted pairs where the first value is a file path that will get renamed to the second value within the output directory.");
			System.out.println(TAB + "/tmp/file.lua" + TAB + "newfile will rename file.lua to newfile.lua after obfuscation" + NL);
			
			System.out.println("AUTHORS");
			System.out.println(TAB + "Indian Runners - The combined efforts of students from PKU and the University of Oregon" + NL);
			
			System.out.println("REPORTING BUGS");
			System.out.println(TAB + "Reporting Bug contact info here" + NL);
			
			System.out.println("SEE ALSO");
			System.out.println(TAB + "node.js - http://nodejs.org/");
			System.out.println(TAB + "LuaParse - http://oxyc.github.io/luaparse/");
		}

	}
	public static void demo(boolean[] level, List<File> luaSource, File astOutputDir, File outputSource, File blacklist, File cstmNamingList){
		System.out.println("CLI Demo");
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
		
		if(blacklist != null){			
			System.out.println("\n\n");
			System.out.println("Blacklist file");
			System.out.println("\t" + blacklist.getAbsolutePath().toString());
			HashSet<String> set = populateBlacklist(blacklist);
			for(String str : set)
				System.out.println("\t\t" + str);
		}
		if(cstmNamingList != null){
			System.out.println("\n\n");
			System.out.println("Custom Naming List File");
			System.out.println("\t" + cstmNamingList.getAbsolutePath().toString());
			HashMap<String, String> map = populateCustomlist(cstmNamingList);
			for(Map.Entry<String, String> m : map.entrySet())
				System.out.println("\t\t" + m.getKey() + " " + m.getValue());
		}
		
	}

}
