import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;


public final class FS {
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
	public static LinkedList<File> seperatedFilesToList(String s) throws FileNotFoundException, SecurityException {
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
					throw new SecurityException("Cannot list child files of" + file.toString());
				}
			}
			else 
				throw new FileNotFoundException(path + " does not exist");
		}
		
		return fileList;
	}

}
