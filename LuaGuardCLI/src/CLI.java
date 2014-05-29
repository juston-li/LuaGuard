/*
 * Author: Tommy Dodson
 * Team: Indean Runners - Lua Guard
 * Date: 4/24/2014
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.AbstractMap.SimpleEntry;


public class CLI {

	// lg.jar -jar LEVEL INPUT AST_PATH OUTPUT_PATH [-blacklist=BLACKLIST_PATH] [-debug]
	// lg.jar -jar LEVEL INPUT AST_PATH OUTPUT_PATH [-blacklist=BLACKLIST_PATH]
	// lg.jar -jar LEVEL INPUT AST_PATH OUTPUT_PATH [-debug]
	// lg.jar -jar LEVEL INPUT AST_PATH OUTPUT_PATH
	// lg.jar -jar [-help]
	// lg.jar -jar
	
	private final static String BLACKLIST_PARAM = "-blacklist";

	private final static String DEBUG_SWITCH = "-debug";
	private final static String HELP_SWITCH = "-help";
	
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
						String path = processOptionalArgs(BLACKLIST_PARAM, optionalArgs);
						blacklistFile = (path != null)? UTIL.getFile(path) : null;
						
						//debug handler
						String d = processOptionalArgs(DEBUG_SWITCH, optionalArgs);
						if(d != null && d.equals(TRUE))
							debug = true;
					}
					
					//get global and optional user defined blacklist and run the driver
					ArrayList<String> blacklist = UTIL.getBlacklist(blacklistFile);
					if(debug)
						debugBefore(levels, ioMap, output, blacklist);
					
					
					//########################################################################################## The main call to do work
					UTIL.driver(levels, ioMap, astMap, blacklist);
					
					
					if(debug)
						debugAfter(ioMap, astMap);
					

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
				if(args.length == 1){
					if(processOptionalArgs(HELP_SWITCH,  args[0]).equals(TRUE))
						man();
				}
				System.err.println("There is a discrepency between the options provided and the template we found " + args.length + " parameters when" + MIN_ARGS + " through " + MAX_ARGS + " are allowed");
				System.err.println("Run this program without any parameters to see the help page.");
			}

			
		} else {
			//no args passed to jar file
			man();
		}

	}
	//*******************************************************************************************************************************************************************************************
	private static String processOptionalArgs(String option, String... strings) {
		for(String s : strings){
			//parameters
			if(s.contains("=")){
				String[] parts = s.split("=");
				if(parts.length == 2 && parts[0].equals(option))
					return parts[1];
			//switches
			} else {
				if(s.equals(option))
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
		
		System.out.println(TAB + "Valid ways to run the program are as follows");
		System.out.println(TAB + TAB + "lg.jar -jar LEVEL INPUT AST_PATH OUTPUT_PATH [-blacklist=BLACKLIST_PATH] [-debug]");
		System.out.println(TAB + TAB + "lg.jar -jar LEVEL INPUT AST_PATH OUTPUT_PATH [-blacklist=BLACKLIST_PATH]");
		System.out.println(TAB + TAB + "lg.jar -jar LEVEL INPUT AST_PATH OUTPUT_PATH [-debug]");
		System.out.println(TAB + TAB + "lg.jar -jar LEVEL INPUT AST_PATH OUTPUT_PATH");
		System.out.println(TAB + TAB + "lg.jar -jar [-help]");
		System.out.println(TAB + TAB + "lg.jar -jar" + NL);
		
		System.out.println("DESCRIPTION");
		System.out.println(TAB + "Lua Guard all purpose obfuscation utility for LUA source code.");
		System.out.println(TAB + "The following are descriptions for filling in the command templates" + NL);
		
		System.out.println("LEVEL");
		System.out.println(TAB + "REQUIRED to obfuscate code");
		System.out.println(TAB + "Base 10 integer representation of obfuscation level");
		System.out.println(TAB + TAB + "0");
		System.out.println(TAB + TAB + TAB + "Code is minified but not obfuscated. Unnecessary white space is removed." + NL);
		System.out.println(TAB + TAB + "1");
		System.out.println(TAB + TAB + TAB + "Name Mangler is performed. In addition to minified code the identifiers are changed to random names" + NL);
		
		System.out.println("INPUT");
		System.out.println(TAB + "REQUIRED to obfuscate code");
		System.out.println(TAB + "The input lua source to be obfuscated.");
		System.out.println(TAB + "can be either a file a folder or a seperated combination of the two using the OS dependant file seperator." + NL);
		
		System.out.println("AST_PATH");
		System.out.println(TAB + "REQUIRED to obfuscate code");
		System.out.println(TAB + "The directory where the obfuscated code will be placed.");
		System.out.println(TAB + "The command will attempt to create the directory if it does not exist." + NL);
		
		System.out.println("OUTPUT_PATH");
		System.out.println(TAB + "REQUIRED to obfuscate code");
		System.out.println(TAB + "The directory where the obfuscated code will be placed.");
		System.out.println(TAB + "The command will attempt to create the directory if it does not exist." + NL);
		
		System.out.println("[-blacklist=BLACKLIST_PATH]");
		System.out.println(TAB + "OPTIONAL");
		System.out.println(TAB + "-blacklist= should be entered verbatim at the commandline");
		System.out.println(TAB + "BLACKLIST_PATH is a path to a file");
		System.out.println(TAB + TAB + "A file containing a new line seperated list of variables to not rename.");
		System.out.println(TAB + TAB + "white space will be trimmed from the list of variables" + NL);
		
		System.out.println("[-debug]");
		System.out.println(TAB + "OPTIONAL");
		System.out.println(TAB + "-debug entered verbatim is a switch");
		System.out.println(TAB + TAB + "print out debugging info");
		System.out.println(TAB + TAB + "Debug info will print in two steps. Once prior to obfuscation and once after. Valid program arguments must be given for debugging" + NL);
		
		System.out.println("[-help]");
		System.out.println(TAB + "-help entered verbatim at the command line is a switch");
		System.out.println(TAB + TAB + "Prints this help page");
		System.out.println(TAB + TAB + "-help is only valid when it is the only program argument" + NL);
		
		System.out.println("AUTHORS");
		System.out.println(TAB + "Indian Runners - The combined efforts of students from PKU and the University of Oregon" + NL);
		
		System.out.println("SEE ALSO");
		System.out.println(TAB + "node.js - http://nodejs.org/");
		System.out.println(TAB + "LuaParse - http://oxyc.github.io/luaparse/");
		System.out.println(TAB + "luamin - https://github.com/mathiasbynens/luamin");
	}
	//*******************************************************************************************************************************************************************************************
	public static void debugBefore(int level, SimpleEntry<ArrayList<File>, ArrayList<String>> luaSource, File outputSource, ArrayList<String> blacklist){
		System.out.println("Debug");
		String s;
		
		System.out.println("Will apply obfuscation level " + level);
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
		
		System.out.println();
		
		
	}
	public static void debugAfter(SimpleEntry<ArrayList<File>, ArrayList<String>> ioMap, SimpleEntry<ArrayList<File>, ArrayList<String>> astMap){
		ArrayList<File> iokey = ioMap.getKey();
		ArrayList<String> astval = astMap.getValue();
		ArrayList<String>ioval = ioMap.getValue();
		
		int n = iokey.size();
		System.out.println("\n\n*****************************");
		System.out.println("Data Dump\nThe formula is as follows");
		System.out.println();
		System.out.println("Input LUA File Path");
		System.out.println("Input LUA Content");
		System.out.println();
		System.out.println("AST File Path");
		System.out.println("AST LUA Content");
		System.out.println();
		System.out.println("Output LUA File Path");
		System.out.println("Output LUA Content");
		System.out.println("\n");
		
		
		String path;
		try {
			for(int i = 0; i < n; ++i){
				
				System.out.println("*****************************\n");
				path = iokey.get(i).getAbsolutePath();
				System.out.println("Input");
				System.out.println(path);
				System.out.println(new String(Files.readAllBytes(Paths.get(path))));
				System.out.println();
				
				path = astval.get(i);
				System.out.println("AST");
				System.out.println(path);
				System.out.println(new String(Files.readAllBytes(Paths.get(UTIL.lookup(path).getAbsolutePath()))));
				System.out.println();
				
				path = ioval.get(i);
				System.out.println("Output");
				System.out.println(path);
				System.out.println(new String(Files.readAllBytes(Paths.get(UTIL.lookup(path).getAbsolutePath()))));
				
			}
		} catch (IOException e) {
			System.out.println("Was unable to read a files content");
		}
	}
	//*******************************************************************************************************************************************************************************************

}
