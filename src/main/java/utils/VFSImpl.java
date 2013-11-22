package utils;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 22.11.13
 * Time: 21:10
 * To change this template use File | Settings | File Templates.
 */
public class VFSImpl implements VFS {

    private String root;
    public VFSImpl(String root) {
        this.root = root;
    }

    @Override
    public boolean isExist(String path) {
        return new File(new StringBuffer(root).append(path).toString()).exists();
    }

    @Override
    public boolean isDirectory(String path) {
        return new File(new StringBuffer(root).append(path).toString()).isDirectory();
    }

    @Override
    public String getAbsolutePath(String file) {
        return new File(root + file).getAbsolutePath();
    }

    @Override
    public byte[] getBytes(String file) {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getUFT8Text(String file) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterator<String> getIterator(String startDir) {
        return new FileIterator(startDir);
    }

 /*   private void generateIndent(int count) {
        StringBuffer indent = new StringBuffer(" ");
        for (int i = 0; i < count; indent.append(" "), i++) {};
    }

    @Override
    public void showFiles(String path) {
       File file = new File(path);
        if (!file.isDirectory()) {
            return;
        }

        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            showFiles(files[i].getAbsolutePath());
        }
    }*/

    private class FileIterator implements Iterator<String> {

        Queue<File> files = new LinkedList<File>();

        public FileIterator(String path) {
            files.add(new File(root + path));
        }

        @Override
        public boolean hasNext() {
            return !files.isEmpty();
        }

        @Override
        public String next() {
            File file = files.peek();
            if(file.isDirectory()){
                for(File subFile : file.listFiles()){
                    files.add(subFile);
                }
            }

            return files.poll().getAbsolutePath();
        }

        @Override
        public void remove() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}
