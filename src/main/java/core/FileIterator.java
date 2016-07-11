package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class FileIterator implements Iterator<String> {

    private Queue<File> files = new LinkedList<>();

    FileIterator(String path) throws FileNotFoundException {
        File file = new File(path);
        if (file.exists()) {
            files.add(file);
        }else {
            String exceptionMessage = "Directory fo path " + path + " doesn't exist";
            throw new FileNotFoundException(exceptionMessage);
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
            for (File subFile: file.listFiles()){
                files.add(subFile);
            }
        }
        return files.poll().getAbsolutePath();
    }

    @Override
    public void remove() {

    }
}
