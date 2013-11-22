package utils;

import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Антон
 * Date: 22.11.13
 * Time: 21:08
 * To change this template use File | Settings | File Templates.
 */
public interface VFS {
    boolean isExist(String path);
    boolean isDirectory(String path);
    String getAbsolutePath(String file);
    byte[] getBytes(String file);
    String getUFT8Text(String file);
    Iterator<String> getIterator(String startDir);
    //void showFiles(String path);
}
