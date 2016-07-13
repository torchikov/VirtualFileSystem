package core;

import com.sun.istack.internal.NotNull;
import interfaces.VFS;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VFSImpl implements VFS {

    private String rootDirectory;

    public VFSImpl(@NotNull String rootDirectory) throws NoSuchFileException {
        if (!Files.exists(Paths.get(rootDirectory))) {
            throw new NoSuchFileException("File or directory fo path " + rootDirectory + " doesn't exist");
        }

        if (!Files.isDirectory(Paths.get(rootDirectory))){
            throw new UnsupportedOperationException("You can't set files as a root directory");
        }
        this.rootDirectory = rootDirectory;
    }

    @Override
    public boolean isExist(String path) {
        Path resultPath = Paths.get(rootDirectory + path);

        return Files.exists(resultPath);
    }

    @Override
    public boolean isDirectory(String path) throws NoSuchFileException {
        Path resultPath = Paths.get(rootDirectory + path);

        if (Files.exists(resultPath)) {
            return Files.isDirectory(resultPath);
        } else {
            String exceptionMessage = "File for path " + rootDirectory + path + " doesn't exist";
            throw new NoSuchFileException(exceptionMessage);
        }
    }

    @Override
    public String getAbsolutePath(String path) throws NoSuchFileException {
        Path resultPath = Paths.get(rootDirectory + path);
        if (Files.exists(resultPath)) {
            return resultPath.toAbsolutePath().toString();
        } else {
            String exceptionMessage = "File for path " + rootDirectory + path + " doesn't exist";
            throw new NoSuchFileException(exceptionMessage);
        }
    }

    @Override
    public byte[] getBytes(String fileName) throws NoSuchFileException {

        Path resultPath = Paths.get(rootDirectory + fileName);

        if (Files.isDirectory(resultPath)) {
            throw new UnsupportedOperationException("This operation can't be apply for directories");
        }

        if (Files.exists(resultPath)) {
            try (RandomAccessFile accessFile = new RandomAccessFile(rootDirectory + fileName, "r");
                 FileChannel inChannel = accessFile.getChannel()) {

                List<Byte> list = new ArrayList<>();

                ByteBuffer buffer = ByteBuffer.allocate(128);
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
            throw new NoSuchFileException(exceptionMessage);
        }
    }

    @Override
    public String getUTF8Text(String fileName) throws NoSuchFileException {

        Path resultPath = Paths.get(rootDirectory + fileName);

        if (Files.isDirectory(resultPath)) {
            throw new UnsupportedOperationException("This operation can't be apply for directories ");
        }

        if (Files.exists(resultPath)) {
            try (RandomAccessFile file = new RandomAccessFile(rootDirectory + fileName, "r");
                 FileChannel inChannel = file.getChannel()) {

                StringBuilder stringBuilder = new StringBuilder();
                ByteBuffer buffer = ByteBuffer.allocate(128);
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
            throw new NoSuchFileException(exceptionMessage);
        }
    }

    @Override
    public Iterator<String> getIterator(String startDirectory) throws NoSuchFileException {
        return new FileIterator(startDirectory);
    }

    @Override
    public String getRootDirectory() {
        return rootDirectory;
    }

    @Override
    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }
}
