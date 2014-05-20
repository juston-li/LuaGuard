/*
 * Author: Tommy Dodson
 * Team: Indean Runners - Lua Guard
 * Date: 4/24/2014
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;


public class CLI {

	// lg.jar -jar LEVEL INPUT_PATH AST_PATH OUTPUT_PATH [-blacklist=BLACKLIST_PATH] [-debug]
	
	private final static String BLACKLIST_PARAM = "-blacklist";

	private final static String DEBUG_SWITCH = "-debug";
	
	private final static String TRUE = "TRUE";
	
	private final static int MIN_ARGS = 4;
	private final static int MAX_ARGS = 6;

	//*******************************************************************************************************************************************************************************************
	public static void main(String[] args) {

		if(args.length > 0){
			
			if(MIN_ARGS <= args.length && args.length <= MAX_ARGS){
				
				//if(NODE && Luaparse are in the path)
				
				try {
					//minimum required params. Error if cannot parse.
					int levels = Integer.parseInt(args[0],10);
					File output = UTIL.getDir(args[3]);
					File ast = UTIL.getDir(args[2]);
					SimpleEntry<ArrayList<File>, ArrayList<String>> ioMap = UTIL.seperatedFilesToList(args[1], output);
					SimpleEntry<ArrayList<File>, ArrayList<String>> astMap = UTIL.seperatedFilesToList(args[1], ast, true); //true switch for astDir
					
					File blacklistFile = null;
					boolean debug = false;
					
					
					if(args.length > MIN_ARGS){
						//blacklist handler
						String[] optionalArgs = Arrays.copyOfRange(args, MIN_ARGS, args.length);
						String path = processOptionalArgs(optionalArgs, BLACKLIST_PARAM);
						blacklistFile = (path != null)? UTIL.getFile(path) : null;
						
						//debug handler
						String d = processOptionalArgs(optionalArgs, DEBUG_SWITCH);
						if(d != null && d.equals(TRUE))
							debug = true;
						System.out.println("Debug is " + debug);
					}
					
					//get global and optional user defined blacklist and run the driver
					ArrayList<String> blacklist = UTIL.getBlacklist(blacklistFile);
					//if(debug)
						debug(levels, ioMap, output, blacklist);
					UTIL.driver(levels, ioMap, astMap, blacklist);
					

				}
				
				catch(NumberFormatException e){
					System.err.println(e.getMessage());
					System.err.println("Run this program without any parameters to see the help page.");
				}
				catch(FileNotFoundException e){
					System.err.println(e.getMessage());
					System.err.println("Run this program without any parameters to see the help page.");
				}
				catch(SecurityException e){
					System.err.println(e.getMessage());
					System.err.println("Run this program without any parameters to see the help page.");
				} 
			}
			else {
				System.err.println("There is a discrepency between the options provided and the template we found " + args.length + " parameters when" + MIN_ARGS + " through " + MAX_ARGS + " are allowed");
				System.err.println("Run this program without any parameters to see the help page.");
			}

			
		} else {
			//no args passed to jar file
			man();
		}

	}
	//*******************************************************************************************************************************************************************************************
	private static String processOptionalArgs(String[] str, String option) {
		for(String s : str){
			//parameters
			if(s.contains("=")){
				String[] parts = s.split("=");
				if(parts.length == 2 && parts[0].equals(option))
					return parts[1];
			//switches
			} else {
				if(str.equals(option))
					return TRUE;
			}
		}
		return null;		
	}
	//*******************************************************************************************************************************************************************************************
	private static void man(){
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
		
		System.out.println("[output after obfuscation]");
		System.out.println(TAB + "The directory where the obfuscated code will be placed.");
		System.out.println(TAB + "The command will attempt to create the directory if it does not exist." + NL);
		
		System.out.println("[-blacklist=file]");
		System.out.println(TAB + "A file containing a new line seperated list of variables to not rename.");
		System.out.println(TAB + "white space will be trimmed from the list of variables" + NL);
		
		System.out.println("AUTHORS");
		System.out.println(TAB + "Indian Runners - The combined efforts of students from PKU and the University of Oregon" + NL);
		
		System.out.println("SEE ALSO");
		System.out.println(TAB + "node.js - http://nodejs.org/");
		System.out.println(TAB + "LuaParse - http://oxyc.github.io/luaparse/");
	}
	//*******************************************************************************************************************************************************************************************
	public static void debug(int level, SimpleEntry<ArrayList<File>, ArrayList<String>> luaSource, File outputSource, ArrayList<String> blacklist){
		System.out.println("Debug");
		String s;
		
		boolean[] levels = UTIL.obfuscationLevels(level);
		for(int i = 0; i < levels.length; ++i){
			s = "\t";
			s += levels[i]? "Will apply " : "Will NOT apply ";
			s += "obfuscation level " + i;
			System.out.println(s);
		}
		System.out.println("\n\n");
		System.out.println("Input Lua Source");
		
		System.out.println("Inputs");
		for(File f : luaSource.getKey()){
			s = "\t";
			s += f.getAbsolutePath().toString();
			System.out.println(s);
		}
		
		System.out.println("Outputs");
		for(String f : luaSource.getValue()){
			s = "\t";
			s += f;
			System.out.println(s);
		}
		
		System.out.println("\n\n");
		System.out.println("Output Lua Source");
		System.out.println("\t" + outputSource.getAbsolutePath().toString());
				
		System.out.println("\n\n");
		System.out.println("BlackList");
		for(String str : blacklist)
			System.out.println("\t" + str);

		
	}
	//*******************************************************************************************************************************************************************************************

}
