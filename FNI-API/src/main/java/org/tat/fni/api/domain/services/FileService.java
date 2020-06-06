package org.tat.fni.api.domain.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

public class FileService {

	public static <T> void writesCsvFromBean(Path path, List<T> objectList) throws Exception {

		try (Writer writer = new FileWriter(path.toString())) {
			StatefulBeanToCsv<Object> sbc = new StatefulBeanToCsvBuilder<>(writer).withSeparator(CSVWriter.DEFAULT_ESCAPE_CHARACTER).build();

			sbc.write(objectList);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeToZipFile(File file, ZipOutputStream zipStream) throws FileNotFoundException, IOException {

		File aFile = new File(file.getPath());
		try (FileInputStream fis = new FileInputStream(aFile)) {
			ZipEntry zipEntry = new ZipEntry(file.getPath());
			zipStream.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipStream.write(bytes, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Date resetStartDate(Date startDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date resetEndDate(Date endDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static Date minusDays(Date date, int day) {
		DateTime dateTime = new DateTime(date, DateTimeZone.getDefault());
		return dateTime.minusDays(day).toDate();
	}

	public static String getFileChecksum(MessageDigest digest, File file) throws IOException {
		StringBuilder sb = new StringBuilder();
		// Get file input stream for reading the file content
		try (FileInputStream fis = new FileInputStream(file)) {
			// Create byte array to read data in chunks
			byte[] byteArray = new byte[1024];
			int bytesCount = 0;
			// Read file data and update in message digest
			while ((bytesCount = fis.read(byteArray)) != -1) {
				digest.update(byteArray, 0, bytesCount);
			}

			// Get the hash's bytes
			byte[] bytes = digest.digest();

			// This bytes[] has bytes in decimal format;
			// Convert it to hexadecimal format

			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			// return complete hash
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String getDateToString(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY-HH-mm");
		return simpleDateFormat.format(date);
	}

}
