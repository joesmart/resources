package com.feature.resources.server.util;

import com.google.common.io.Files;

import java.io.*;

/**
 * User: yanjianzou
 * Date: 12/12/12
 * Time: 2:29 PM
 */
public class FilesUtil {

    private static String baseDirPath;
    private static String thumbnailPath;

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

    public static FileOutputStream createTemplateThumbnailFile(String graphicId,int width,int height) throws FileNotFoundException {
        initPath();
        File thumbnailFile = new File(getFileName(graphicId, width, height));
        return new FileOutputStream(thumbnailFile);
    }

    private static String getFileName(String graphicId, int width, int height) {
        return thumbnailPath + File.separator+graphicId+"_"+width+"_"+height+".PNG";
    }

    private static void initPath() {
        if(baseDirPath == null || "".equals(baseDirPath)){
            File baseDir = new File(System.getProperty("java.io.tmpdir"));
            baseDirPath = baseDir.getPath();
        }
        if (thumbnailPath == null|| "".equals(thumbnailPath)){
            thumbnailPath = baseDirPath + File.separator+"thumnail";
            String temp = Files.createTempDir().getPath();
            File file = new File(thumbnailPath);
            file.mkdir();
        }
    }

    public static boolean isThumbnailFileAlreadyExists(String graphicId, int width, int height){
        initPath();
        File thumbnailFile = new File(getFileName(graphicId, width, height));
        return  thumbnailFile.exists();

    }

    public static void writeTempThumbnailFile(String graphicId, int width, int height,OutputStream outputStream) throws IOException {
        byte[] bytes = new byte[2048];
        InputStream input = null;
        input = new FileInputStream(new File(getFileName(graphicId, width, height)));
        int result = 0;
        while ((result = input.read(bytes)) != -1) {
            outputStream.write(bytes, 0, result);
        }
        outputStream.flush();
    }
}
