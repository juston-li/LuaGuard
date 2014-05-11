import java.io.File;
import java.util.ArrayList;

interface Obfuscator {
	/*
	Obfuscator Interface:
	This interface specifies the requirements obfuscate an AST file
	*/
	
	File obfuscate(File ast, ArrayList<String> blacklist);
	/*
	
	ast: The ast file to be obfuscated using either walker.js or GSON
	
	blacklist: A list of keywords and identifiers not to be obfuscated
		The blacklist should be checked before modifying any identifier.
	
	The returned File should be the overwritten file that is the result
	of the obfuscation method.
	*/
}