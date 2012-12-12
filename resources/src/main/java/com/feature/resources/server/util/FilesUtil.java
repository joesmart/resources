package com.feature.resources.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * User: yanjianzou
 * Date: 12/12/12
 * Time: 2:29 PM
 */
public class FilesUtil {
    public static byte[] readAllBytes(File file) {
        byte[] tempBytes = new byte[4096];
        byte[] copyBytes = new byte[0];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            int length = 0;
            while((length = inputStream.read(tempBytes))>0){
                byte[] temp = copyBytes;
                copyBytes = new byte[length+temp.length];
                System.arraycopy(temp, 0, copyBytes, 0, temp.length);
                System.arraycopy(tempBytes,0,copyBytes,temp.length,length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return copyBytes;
    }
}
