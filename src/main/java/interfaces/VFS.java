package interfaces;

import java.nio.file.NoSuchFileException;
import java.util.Iterator;

public interface VFS {

    boolean isExist(String path);

    boolean isDirectory(String path) throws NoSuchFileException;

    String getAbsolutePath(String fileName) throws NoSuchFileException;

    byte[] getBytes(String fileName) throws NoSuchFileException;

    String getUTF8Text(String fileName) throws NoSuchFileException;

    Iterator<String> getIterator(String startDirectory) throws NoSuchFileException;

    void setRootDirectory(String rootDirectory);

    String getRootDirectory();


}
