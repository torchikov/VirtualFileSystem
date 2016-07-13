package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class FileIterator implements Iterator<String> {

    private Queue<File> files = new LinkedList<>();

    FileIterator(String path) throws NoSuchFileException {
        File file = new File(path);
        if (file.exists()) {
            files.add(file);
        }else {
            String exceptionMessage = "Directory fo path " + path + " doesn't exist";
            throw new NoSuchFileException(exceptionMessage);
        }

    }



    @Override
    public boolean hasNext() {
        return !files.isEmpty();
    }

    @Override
    public String next() {
        File file = files.peek();

        if (file.isDirectory()){
            Collections.addAll(files, file.listFiles());
        }
        return files.poll().getAbsolutePath();
    }

    @Override
    public void remove() {

    }
}
