package interfaces;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Iterator;
import java.util.List;

public interface VFS {

    boolean isExist(String path);

    boolean isDirectory(String path) throws NoSuchFileException;

    String getAbsolutePath(String fileName) throws NoSuchFileException;

    byte[] getBytes(String fileName) throws NoSuchFileException;

    String getUTF8Text(String fileName) throws NoSuchFileException;

    Iterator<String> getIterator(String startDirectory) throws NoSuchFileException;

    void setRootDirectory(String rootDirectory);

    String getRootDirectory();

    List<String> getContentsFromDirectory(String directory) throws IOException;

    int deleteFilesIfExist(String... files) throws IOException;

    boolean createDirectory(String directoryName) throws IOException;

    int createFiles(String directory, String[] names, boolean overrideExists) throws IOException;


}
