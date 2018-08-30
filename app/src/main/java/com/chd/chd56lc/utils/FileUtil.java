package com.chd.chd56lc.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

/**
 * description:
 *
 * @version V1.0
 * @create 2014-3-19 下午12:03:01
 * @copyright Copyright (c) 2014 www.yydreamer.com,Inc. All Rights Reserved.
 */
@SuppressLint("DefaultLocale")
public class FileUtil {


    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;
    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;
    /**
     * The number of bytes in a gigabyte.
     */
    public static final long ONE_GB = ONE_KB * ONE_MB;
    /**
     * The UTF-8 character set, used to decode octets in URLs.
     */
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private FileUtil() {
    }

    public static boolean deleteDir(File dir) {
        // if (dir != null && dir.isDirectory()) {

        if (dir != null && dir.exists() && dir.isDirectory()) {
            String[] children = dir.list();

            if (children == null) {
                return dir.delete();
            }
            for (String str : children) {
                boolean success = deleteDir(new File(dir, str));
                if (!success) {
                    return false;
                }
            }
        }
        if (dir != null && dir.exists()) {
            return dir.delete();
        }
        return false;
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
             e.printStackTrace();
        }
    }

    /***
     * 删除指定文件夹下所有文件
     *
     * @param path
     *            文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {

        boolean flag = false;

        try {

            File file = new File(path);
            if (!file.exists()) {
                return false;
            }
            if (!file.isDirectory()) {
                return false;
            }
            String[] tempList = file.list();
            File temp = null;
            for (int i = 0; i < tempList.length; i++) {
                if (path.endsWith(File.separator)) {
                    Logger.d("FileUtil", "FileUtil" + i + ":" + path + "/" + tempList[i]);
                    temp = new File(path + tempList[i]);
                } else {
                    temp = new File(path + File.separator + tempList[i]);
                }
                if (temp.isFile()) {
                    Logger.d("FileUtil", "FileUtil" + i + ":" + path + "/" + tempList[i]);
                    temp.delete();
                }
                if (temp.isDirectory()) {
                    delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                    Logger.d("FileUtil", "FileUtil" + i + ":" + path + "/" + tempList[i]);
                    delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据文件名获取扩展名
     *
     * @param filename 文件名
     * @return 文件扩展名
     */
    public static String getFileExtensionFromName(String filename) {
        if (filename == null) {
            return null;
        }
        int index = indexOfExtension(filename);
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    private static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        }
        int extensionPos = filename.lastIndexOf(".");
        int lastSeparator = filename.lastIndexOf("/");
        return (lastSeparator > extensionPos ? -1 : extensionPos);
    }

    /**
     * Returns the file extension or an empty string if there is no extension.
     * This method is a convenience method for obtaining the extension of a url
     * and has undefined results for other Strings.
     *
     * @param url
     * @return The file extension of the given url.
     */
    public static String getFileExtensionFromUrl(String url) {
        if (url != null && url.length() > 0) {
            int query = url.lastIndexOf('?');
            if (query > 0) {
                url = url.substring(0, query);
            }
            int filenamePos = url.lastIndexOf('/');
            String filename = 0 <= filenamePos ? url.substring(filenamePos + 1) : url;

            // if the filename contains special characters, we don't
            // consider it valid for our matching purposes:
            if (filename.length() > 0 && Pattern.matches("[a-zA-Z_0-9.\\-()]+", filename)) {
                int dotPos = filename.lastIndexOf('.');
                if (0 <= dotPos) {
                    return filename.substring(dotPos + 1);
                }
            }
        }

        return "";
    }

    public static String getFileExtensionFromSource(byte[] picHeader) {
        String picExtendName = null;

        if (picHeader.length >= 2 && (picHeader[0] == 66) && (picHeader[1] == 77)) {
            // header bytes contains BM?
            picExtendName = "BMP";
        } else if (picHeader.length >= 4 && (picHeader[1] == 80) && (picHeader[2] == 78) &&
                (picHeader[3] == 71)) {
            // header bytes contains PNG?
            picExtendName = "PNG";
        } else if (picHeader.length >= 6 && (picHeader[0] == 71) && (picHeader[1] == 73) &&
                (picHeader[2] == 70) && (picHeader[3] == 56) && ((picHeader[4] == 55) ||
                (picHeader[4] == 57)) && (picHeader[5] == 97)) {
            // header bytes contains GIF87a or GIF89a?
            picExtendName = "GIF";
        } else if (picHeader.length >= 10 && (picHeader[6] == 74) && (picHeader[7] == 70) &&
                (picHeader[8] == 73) && (picHeader[9] == 70)) {
            // header bytes contains JFIF?
            picExtendName = "JPG";
        }

        return picExtendName;
    }

    public static boolean isGif(String filePath) {
        boolean isGif = false;
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                StringBuilder head = new StringBuilder("");
                for (int i = 0; i < 6; i++) {
                    head.append((char) fis.read());
                }
                if (head.indexOf("GIF") == 0) {
                    isGif = true;
                }
            } catch (FileNotFoundException e) {
                //LogUtil.e("isGif", e.toString());
            } catch (IOException e) {
                //LogUtil.e("isGif", e.toString());
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        return isGif;
    }

    public static byte[] readFileToByteArray(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);

        return readFileToByteArray(file);
    }

    public static byte[] readFileToByteArray(File file) {
        byte[] fileBytes = new byte[(int) file.length()];

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int offset = 0;
            int count = (int) file.length();
            int temp = 0;

            while ((temp = fis.read(fileBytes, offset, count)) > 0) {
                offset += temp;
                count -= temp;
            }
        } catch (FileNotFoundException e) {
            //LogUtil.e("readFile", e.toString());
        } catch (IOException e) {
            //LogUtil.e("readFile", e.toString());
        } catch (Exception e) {
            //LogUtil.e("readFile", e.toString());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    //LogUtil.e("readFile", e.toString());
                }
            }
        }

        return fileBytes;
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (file.canRead() == false) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
    }

    public static String byteCountToDisplaySize(long size) {
        String displaySize;

        if (size / ONE_GB > 0) {
            displaySize = String.format("%1$.1f GB", (float) size / ONE_GB);
        } else if (size / ONE_MB > 0) {
            displaySize = String.format("%1$.1f MB", (float) size / ONE_MB);
        } else if (size / ONE_KB > 0) {
            displaySize = String.format("%1$.1f KB", (float) size / ONE_KB);
        } else {
            displaySize = String.valueOf(size) + " Bytes";
        }
        return displaySize;
    }

    public static long sizeOf(File file) {

        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }

        if (file.isDirectory()) {
            return sizeOfDirectory(file);
        } else {
            return file.length();
        }

    }

    public static long sizeOfDirectory(File directory) {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        long size = 0;

        File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            return 0L;
        }
        for (File file : files) {
            size += sizeOf(file);
        }

        return size;
    }

    public static File toFile(URL url) {
        if (url == null || !"file".equalsIgnoreCase(url.getProtocol())) {
            return null;
        } else {
            String filename = url.getFile().replace('/', File.separatorChar);
            filename = decodeUrl(filename);
            return new File(filename);
        }
    }

    /**
     * Decodes the specified URL as per RFC 3986, i.e. transforms
     * percent-encoded octets to characters by decoding with the UTF-8 character
     * set. This function is primarily intended for usage with
     * {@link java.net.URL} which unfortunately does not enforce proper URLs. As
     * such, this method will leniently accept invalid characters or malformed
     * percent-encoded octets and simply pass them literally through to the
     * result string. Except for rare edge cases, this will make unencoded URLs
     * pass through unaltered.
     *
     * @param url The URL to decode, may be <code>null</code>.
     * @return The decoded URL or <code>null</code> if the input was
     * <code>null</code>.
     */
    static String decodeUrl(String url) {
        String decoded = url;
        if (url != null && url.indexOf('%') >= 0) {
            int n = url.length();
            StringBuffer buffer = new StringBuffer();
            ByteBuffer bytes = ByteBuffer.allocate(n);
            for (int i = 0; i < n; ) {
                if (url.charAt(i) == '%') {
                    try {
                        do {
                            byte octet = (byte) Integer.parseInt(url.substring(i + 1, i + 3), 16);
                            bytes.put(octet);
                            i += 3;
                        } while (i < n && url.charAt(i) == '%');
                        continue;
                    } catch (RuntimeException e) {
                        // malformed percent-encoded octet, fall through and
                        // append characters literally
                    } finally {
                        if (bytes.position() > 0) {
                            bytes.flip();
                            buffer.append(UTF8.decode(bytes).toString());
                            bytes.clear();
                        }
                    }
                }
                buffer.append(url.charAt(i++));
            }
            decoded = buffer.toString();
        }
        return decoded;
    }

    public static File getFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile(File fl) {
        String ret = "";
        try {
            FileInputStream fin = new FileInputStream(fl);
            ret = convertStreamToString(fin);
            //Make sure you close all streams.
            fin.close();
        } catch (Exception e) {

        }
        return ret;
    }
}
