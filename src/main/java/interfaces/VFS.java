package interfaces;

import java.io.FileNotFoundException;
import java.util.Iterator;

public interface VFS {

    boolean isExist(String path);

    boolean isDirectory(String path) throws FileNotFoundException;

    String getAbsolutePath(String fileName) throws FileNotFoundException;

    byte[] getBytes(String fileName) throws FileNotFoundException;

    String getUTF8Text(String fileName) throws FileNotFoundException;

    Iterator<String> getIterator (String startDirectory) throws FileNotFoundException;



}
