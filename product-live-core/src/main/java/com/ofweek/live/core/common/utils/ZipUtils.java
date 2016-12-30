package com.ofweek.live.core.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtils {

	private final static Logger logger = LoggerFactory.getLogger(ZipUtils.class);
    public final static int DEFAULT_BUFFER_SIZE = 1024;

    public static void zip(String fileToZip) throws IOException, FileNotFoundException, NullPointerException, IllegalArgumentException {
        String encoding = System.getProperty("file.encoding");
        zip(fileToZip, encoding);
    }

    public static void zip(String fileToZip, String encoding) throws IOException, FileNotFoundException, NullPointerException,
            IllegalArgumentException {
        zip(fileToZip, DEFAULT_BUFFER_SIZE, encoding);
    }

    public static void zip(String fileToZip, String zippedFileName, String encoding) throws IOException, FileNotFoundException, NullPointerException,
            IllegalArgumentException {
        zip(fileToZip, zippedFileName, DEFAULT_BUFFER_SIZE, encoding);
    }

    public static void zip(String fileToZip, int bufferSize, String encoding) throws IOException, FileNotFoundException, NullPointerException,
            IllegalArgumentException {
        String zippedFileName = fileToZip + ".zip";
        zip(fileToZip, zippedFileName, bufferSize, encoding);
    }

    public static void zip(String fileToZip, String zippedFileName, int bufferSize, String encoding) throws IOException, FileNotFoundException,
            NullPointerException, IllegalArgumentException {
        long startTime = System.currentTimeMillis();
        if (StringUtils.isEmpty(zippedFileName)) {
            throw new NullPointerException(String.format("zipped file name must not be null."));
        }
        if (bufferSize < 0) {
            throw new IllegalArgumentException("buffer size must larger than 0");
        }
        File file = new File(fileToZip);
        if (!file.exists()) {
            throw new FileNotFoundException("File[" + fileToZip + "] is not found.");
        }
        String rootDir = FileUtils.mkdirs(zippedFileName);
        ZipOutputStream output = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zippedFileName)));
        if (encoding == null) {
            encoding = System.getProperty("file.encoding");
        }
        output.setEncoding(encoding);
        byte[] buffer = null;
        try {
            buffer = new byte[bufferSize];
            outputEntry(rootDir, file, output, buffer);
        } finally {
            buffer = null;
            output.close();
        }
        if (logger.isDebugEnabled()) {
            long costTime = System.currentTimeMillis() - startTime;
            logger.debug("zip the file[" + fileToZip + "] with name[" + zippedFileName + "] finished. cost time[" + costTime + "]");
        }
    }

    private static void outputEntry(String rootDir, File file, ZipOutputStream zipOut, byte[] buffer) throws IOException {
        if (file.isDirectory()) {
            zipOut.putNextEntry(new ZipEntry(file.getName() + "/"));
            try {
                File[] files = file.listFiles();
                for (File child : files) {
                    outputEntry(rootDir, child, zipOut, buffer);
                }
            } finally {
                zipOut.closeEntry();
            }
        } else {
            InputStream input = null;
            String fileName = file.getPath().substring(rootDir.length());
            logger.debug("add zip entry[" + fileName + "] to directory[" + rootDir + "]");
            zipOut.putNextEntry(new ZipEntry(fileName));
            try {
                input = new BufferedInputStream(new FileInputStream(file));
                int readedCount = 0;
                byte[] readedBytes = null;
                while ((readedCount = IOUtils.read(input, buffer)) > 0) {
                    if(readedCount == buffer.length) {
                        readedBytes = buffer;
                    } else {
                        readedBytes = ArrayUtils.subarray(buffer, 0, readedCount);
                    }
                    zipOut.write(readedBytes, 0, readedCount);
                }
                readedBytes = null;
            } finally {
                IOUtils.closeQuietly(input);
                zipOut.closeEntry();
            }
        }
    }
    
    public static void unzip(String unzipfileName) throws ZipException, IOException, 
            NullPointerException, IllegalArgumentException {
        String rootDir = new File(unzipfileName).getParent();
        unzip(unzipfileName, rootDir);
    }
    
    public static void unzip(String unzipfileName, String rootDir, String encoding) throws ZipException, IOException, 
            NullPointerException, IllegalArgumentException {
        unzip(unzipfileName, rootDir, DEFAULT_BUFFER_SIZE, encoding);
    }
    
    public static void unzip(String unzipfileName, String rootDir) throws ZipException, IOException, 
            NullPointerException, IllegalArgumentException {
        unzip(unzipfileName, rootDir, DEFAULT_BUFFER_SIZE, null);
    }

    public static void unzip(String unzipfileName, String rootDir, int bufferSize, String encoding) throws ZipException, IOException, 
            NullPointerException, IllegalArgumentException {
        long startTime = System.currentTimeMillis();
        if (StringUtils.isEmpty(unzipfileName)) {
            throw new NullPointerException(String.format("unzip file name must not be null."));
        }
        if (StringUtils.isEmpty(rootDir)) {
            throw new NullPointerException(String.format("upzip directory must not be null."));
        }
        if (bufferSize < 0) {
            throw new IllegalArgumentException("buffer size must larger than 0");
        }
        InputStream input = null;
        FileOutputStream output = null;
        File file = null;
        ZipFile zipFile = null;
        try {
            if(encoding == null) {
                encoding = System.getProperty("file.encoding");
            }
            zipFile = new ZipFile(unzipfileName, encoding);
            for (Enumeration<?> entries = zipFile.getEntries(); entries.hasMoreElements();) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                file = new File(rootDir, entry.getName());
                logger.debug("unzip zip entry[" + entry.getName() + "] to directory [" + rootDir + "].");
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    input = zipFile.getInputStream(entry);
                    output = new FileOutputStream(file);
                    int readedCount = 0;
                    byte[] readedBytes = null;
                    byte[] buffer = new byte[bufferSize];
                    while ((readedCount = IOUtils.read(input, buffer)) > 0) {
                        if(readedCount == buffer.length) {
                            readedBytes = buffer;
                        } else {
                            readedBytes = ArrayUtils.subarray(buffer, 0, readedCount);
                        }
                        output.write(readedBytes, 0, readedCount);
                    }
                    readedBytes = null;
                    buffer = null;
                    IOUtils.closeQuietly(input);
                    IOUtils.closeQuietly(output);
                }
            }
            
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
            zipFile.close();
        }
        if (logger.isDebugEnabled()) {
            long costTime = System.currentTimeMillis() - startTime;
            logger.debug("unzip the file[" + unzipfileName + "] with name[" + rootDir + "] finished. cost time[" + costTime + "]");
        }
    }

    private ZipUtils() {
    	
    }

}
