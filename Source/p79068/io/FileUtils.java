package p79068.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class FileUtils {
	
	/**
	 * Returns a list of all items in the specified directory.
	 * @return a list of all items in {@code dir}
	 * @throws IllegalArgumentException if {@code dir} is not a directory
	 */
	public static List<File> listItems(File dir) {
		if (!dir.isDirectory())
				throw new IllegalArgumentException("Not a directory");
		return Arrays.asList(dir.listFiles());
	}
	
	
	/**
	 * Returns a list of all files in the specified directory.
	 * @return a list of all files in {@code dir}
	 * @throws IllegalArgumentException if {@code dir} is not a directory
	 */
	public static List<File> listFiles(File dir) {
		if (!dir.isDirectory())
			throw new IllegalArgumentException("Not a directory");
		
		List<File> result = new ArrayList<File>();
		for (File item : dir.listFiles()) {
			if (item.isFile())
				result.add(item);
		}
		return result;
	}
	
	
	/**
	 * Returns a list of all directories in the specified directory.
	 * @return a list of all directories in {@code dir}
	 * @throws IllegalArgumentException if {@code dir} is not a directory
	 */
	public static List<File> listDirs(File dir) {
		if (!dir.isDirectory())
			throw new IllegalArgumentException("Not a directory");
		
		List<File> result = new ArrayList<File>();
		for (File item : dir.listFiles()) {
			if (item.isDirectory())
				result.add(item);
		}
		return result;
	}
	
	
	
	/**
	 * Returns the name of the specified file, without its extension.
	 * For example, "abc.jpg" yields "abc", "foo.tar.gz" yields "foo.tar", and "README" yields "README".
	 * @return the name of the file
	 */
	public static String getNameOnly(File file) {
		return getNameOnly(file.getName());
	}
	
	
	/**
	 * Returns the name of the specified file name string, without its extension.
	 * For example, "abc.jpg" yields "abc", "foo.tar.gz" yields "foo.tar", and "README" yields "README".
	 * For all strings, this expression is true: {@code s.equals(getNameOnly(s) + getExtension(s))}.
	 * @return the name of the file name string
	 */
	public static String getNameOnly(String name){
		int index = name.lastIndexOf('.');
		if (index > -1)
			return name.substring(0, index);
		else
			return name;
	}
	
	
	/**
	 * Returns the extension of the specified file, including the dot. An empty string is also possible.
	 * For example, "abc.jpg" yields ".jpg", "foo.tar.gz" yields ".gz", and "README" yields "".
	 * @return the extension of the file
	 */
	public static String getExtension(File file) {
		return getExtension(file.getName());
	}
	
	
	/**
	 * Returns the extension of the specified file name string, including the dot. An empty string is also possible.
	 * For example, "abc.jpg" yields ".jpg", "foo.tar.gz" yields ".gz", and "README" yields "".
	 * For all strings, this expression is true: {@code s.equals(getNameOnly(s) + getExtension(s))}.
	 * @return the extension of the file name string
	 */
	public static String getExtension(String name){
		int index = name.lastIndexOf('.');
		if (index != -1)
			return name.substring(index, name.length());
		else
			return "";
	}
	
	
	
	/**
	 * Renames the specified file to the new name. The new name contains the name portion only, and no paths.
	 * @throws IOException if the rename failed
	 */
	public static void rename(File file, String newname) throws IOException {
		File newfile = new File(file.getParentFile(), newname);
		if (newfile.exists() && !file.getCanonicalFile().equals(newfile.getCanonicalFile()))
			throw new IOException(String.format("New file exists: %s", newfile));
		if (!file.renameTo(newfile))
			throw new IOException(String.format("Rename failed: %s --> %s", file, newname));
	}
	
	
	
	/** 
	 * Not instantiable.
	 */
	private FileUtils() {}
	
}