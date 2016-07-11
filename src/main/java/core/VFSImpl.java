package core;

import interfaces.VFS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VFSImpl implements VFS {

    private String rootDirectory;

    public VFSImpl(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public boolean isExist(String path) {
        File file = new File(rootDirectory + path);
        boolean isExist = file.exists();
        return isExist;
    }

    public boolean isDirectory(String path) throws FileNotFoundException {
        File file = new File(rootDirectory + path);

        if (file.exists()) {
            return file.isDirectory();
        } else {
            String exceptionMessage = "File for path " + rootDirectory + path + " doesn't exist";
            throw new FileNotFoundException(exceptionMessage);
        }
    }

    public String getAbsolutePath(String fileName) throws FileNotFoundException {
        File file = new File(rootDirectory + fileName);
        if (file.exists()) {
            return file.getAbsolutePath();
        } else {
            String exceptionMessage = "File for path " + rootDirectory + fileName + " doesn't exist";
            throw new FileNotFoundException(exceptionMessage);
        }
    }

    public byte[] getBytes(String fileName) throws FileNotFoundException {

        if (isExist(fileName)) {
            try (RandomAccessFile accessFile = new RandomAccessFile(rootDirectory + fileName, "r");
                 FileChannel inChannel = accessFile.getChannel()) {

                List<Byte> list = new ArrayList<>();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int byteReads = inChannel.read(buffer);

                while (byteReads != -1) {
                    buffer.flip();

                    while (buffer.hasRemaining()) {
                        list.add(buffer.get());
                    }
                    buffer.clear();
                    byteReads = inChannel.read(buffer);
                }

                byte[] result = new byte[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    result[i] = list.get(i);
                }

                return result;

            } catch (IOException ex) {
                //TODO: Handle exception
                return null;
            }


        } else {
            String exceptionMessage = "File for path " + rootDirectory + fileName + " doesn't exist";
            throw new FileNotFoundException(exceptionMessage);
        }
    }

    public String getUTF8Text(String fileName) throws FileNotFoundException {

        if (isExist(fileName)) {
            try (RandomAccessFile file = new RandomAccessFile(rootDirectory + fileName, "r");
                 FileChannel inChannel = file.getChannel()) {

                StringBuilder stringBuilder = new StringBuilder();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int bytesRead = inChannel.read(buffer);

                while (bytesRead != -1) {
                    buffer.flip();

                    while (buffer.hasRemaining()) {
                        stringBuilder.append((char) (buffer.get()));
                    }

                    buffer.clear();
                    bytesRead = inChannel.read(buffer);
                }

                return stringBuilder.toString();

            } catch (IOException ex) {
                //TODO: Handle exception
                return null;
            }
        } else {
            String exceptionMessage = "File for path " + rootDirectory + fileName + " doesn't exist";
            throw new FileNotFoundException(exceptionMessage);
        }
    }

    public Iterator<String> getIterator(String startDirectory) throws FileNotFoundException {
        return new FileIterator(startDirectory);
    }
}
