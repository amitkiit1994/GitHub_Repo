package org.ridecell.assignment.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.ridecell.assignment.base.TestBase;

public class TestUtil extends TestBase{
	
	public static boolean sendMail(String subject) throws IOException {
		Properties props=new Properties();
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.ssl.trust", "smtp.gmail.com");


	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(prop.getProperty("From_Mail_Recipient"), prop.getProperty("From_Mail_Recipient_Password"));
	                }
	            });
		try {
			
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(prop.getProperty("From_Mail_Recipient")));
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(prop.getProperty("To_Mail_Recipient")));

	         // Set Subject: header field
	         message.setSubject(subject);

	         BodyPart messageBodyPart = new MimeBodyPart();
	         BodyPart messageBodyPart1 = new MimeBodyPart();
	         BodyPart messageBodyPart2 = new MimeBodyPart();
	         BodyPart messageBodyPart3 = new MimeBodyPart();

	         // Fill the message
	         messageBodyPart.setText("TEST RESULTS by Amit");

	         // Create a multipart message
	         Multipart multipart = new MimeMultipart();

	         // Set text message part
	         multipart.addBodyPart(messageBodyPart);

	         // Part two is attachment
	         messageBodyPart = new MimeBodyPart();
	         String filename = System.getProperty("user.dir")
						+ "\\ExtentReportResults.html";
	         DataSource source = new FileDataSource(filename);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName("ExtentReportResults.html");
	         multipart.addBodyPart(messageBodyPart);
	         
	         messageBodyPart2 = new MimeBodyPart();
	         String excelfilenameUI = System.getProperty("user.dir")
						+ "\\execdir\\"+prop.getProperty("FileNameAPIData")+".xlsx";
	         DataSource sourceExcelUI = new FileDataSource(excelfilenameUI);
	         messageBodyPart2.setDataHandler(new DataHandler(sourceExcelUI));
	         messageBodyPart2.setFileName(prop.getProperty("FileNameAPIData")+".xlsx");
	         multipart.addBodyPart(messageBodyPart2);
	         
	         messageBodyPart3 = new MimeBodyPart();
	         String excelfilenameApi = System.getProperty("user.dir")
	        		 + "\\execdir\\"+prop.getProperty("FileNameUIData")+".xlsx";
	         DataSource sourceExcelApi = new FileDataSource(excelfilenameApi);
	         messageBodyPart3.setDataHandler(new DataHandler(sourceExcelApi));
	         messageBodyPart3.setFileName(prop.getProperty("FileNameUIData")+".xlsx");
	         multipart.addBodyPart(messageBodyPart3);
	         
	         messageBodyPart1 = new MimeBodyPart();
	         String filename1 = System.getProperty("user.dir")
						+ "\\target\\surefire-reports\\emailable-report.html";
	         String content = Jsoup.parse(new File(filename1), "UTF-8").toString();
	         messageBodyPart1.setContent(content,"text/html");
	         
	         multipart.addBodyPart(messageBodyPart1);
	         // Send the complete message parts
	         message.setContent(multipart);

	         // Send message
	         Transport.send(message);
	         return true;
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	         return false;
	      }
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static void writeRepoDetailsToExcel(String filename, LinkedHashMap<String, String> repoDetails) throws IOException {
		String file_location = System.getProperty("user.dir") +  "\\execdir\\"+ filename;
		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet(filename);

		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		Map<String, Object[]> repoInfo = new TreeMap<String, Object[]>();
		repoInfo.put("1", new Object[] { "REPOSITORY NAMES", "REPOSITORY DESC" });
		int c = 2;
		for(Map.Entry<String, String> map: repoDetails.entrySet()) {
			repoInfo.put(String.valueOf(c),
					new Object[] { map.getKey(),map.getValue() });
			c = c + 1;
		}

		// Iterate over data and write to sheet
		Set<String> keyid = repoInfo.keySet();
		int rowid = 0;

		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = repoInfo.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}
		// Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File(file_location+".xlsx"));
		workbook.write(out);
		out.close();
		workbook.close();
	}
	
}
