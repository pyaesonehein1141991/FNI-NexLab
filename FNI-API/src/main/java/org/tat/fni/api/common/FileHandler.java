package org.tat.fni.api.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
//import org.primefaces.model.UploadedFile;

/**
 * Utility for manipulating the file operations.
 * 
 * @author A.C.N
 * @since 1.0.0
 * @date 2013/07/31
 * 
 */
public class FileHandler {
	public static String getFileName(String filePath) {
		int lastIndex = filePath.lastIndexOf("/") + 1;
		return filePath.substring(lastIndex, filePath.length());
	}

	public static void forceMakeDirectory(String fullFilePath) throws IOException {
		forceMakeDirectory(new File(fullFilePath));
	}

	public static void forceMakeDirectory(File file) throws IOException {
		FileUtils.forceMkdir(file);
	}

//	public static void createFile(String systemPath, String filePath, UploadedFile uploadedFile) throws IOException {
//		createFile(systemPath + filePath, uploadedFile);
//	}
//
//	public static void createFile(String fullFilePath, UploadedFile uploadedFile) throws IOException {
//		createFile(new File(fullFilePath), uploadedFile.getContents());
//	}
//
//	public static void createFile(File file, UploadedFile uploadedFile) throws IOException {
//		createFile(file, uploadedFile.getContents());
//	}

	public static void createFile(File file, byte[] content) throws IOException {
		String filePath = file.getPath();
		int lastIndex = filePath.lastIndexOf("\\") + 1;
		FileUtils.forceMkdir(new File(filePath.substring(0, lastIndex)));
		FileOutputStream outputStream = new FileOutputStream(file);
		IOUtils.write(content, outputStream);
		outputStream.close();
	}

	public static void forceDelete(String systemPath, String filePath) throws IOException {
		forceDelete(systemPath + filePath);
	}

	public static void forceDelete(String fullFilePath) throws IOException {
		forceDelete(new File(fullFilePath));
	}

	public static void forceDelete(File file) throws IOException {
		if (file.exists()) {
			FileUtils.forceDelete(file);
		}
	}

	public static void moveFiles(String systemPath, String sourceDirectoryPath, String destinationDirectoryPath) throws IOException {
		// clear target folder before moving (copying)
		File targetDeleteDir = new File(systemPath + destinationDirectoryPath);
		FileUtils.deleteDirectory(targetDeleteDir);

		// copy the source folder to target folder
		File sourceDir = new File(systemPath + sourceDirectoryPath);
		File targetDir = new File(systemPath + destinationDirectoryPath);
		FileUtils.copyDirectory(sourceDir, targetDir);

		// clear the source folder
		int lastIndex = sourceDirectoryPath.lastIndexOf("/") + 1;
		String deletePath = sourceDirectoryPath.substring(0, lastIndex);
		File deleteDir = new File(systemPath + deletePath);
		FileUtils.deleteDirectory(deleteDir);

		// soooo , the contents of source folder is moved to target folder
		// P.S. moveDirectory could be used instead of copyDirectory to skip
		// delete source file part but that's prone to file exist exception etc.
	}

	public static void renameFile(String srcPath, String renamePath) {
		File srcFile = new File(srcPath);
		File renameFile = new File(renamePath);
		srcFile.renameTo(renameFile);
	}

	public static boolean exitFile(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	public static void copyDirectory(String srcPath, String destPath) throws IOException {
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);
		if (srcFile.exists()) {
			FileUtils.copyDirectory(srcFile, destFile);
		}
	}
}
