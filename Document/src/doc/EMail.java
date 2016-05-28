package doc;

import java.io.*;
import java.util.regex.Pattern;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import jxl.*;
import ru.svrw.eivc.mail.SMTPClient;
import ru.svrw.eivc.util.*;

public class EMail {

	private StringUtils su = new StringUtils(); 
	private Properties propMail = new Properties(su.replaceSystemParameter("${pathProject}/conf/mail.properties"));
	private String body;
	private String subject;
	private boolean err;
	private String workCatalog;
	private String sError;
	
	public String getSError() {
		return sError;
	}

	public void setWorkCatalog(String workCatalog) {
		this.workCatalog = workCatalog;
	}

	private void send(String email, String adrFile) {
		String[] nameFile = "".split("");
		try { 
			System.out.println("mail Send..."); 
			SMTPClient c = new SMTPClient(); 
			c.setHost(propMail.getString("mail.SMTP"));
			c.setCharset(propMail.getString("mail.encoding"));
			c.setFrom(propMail.getString("mail.from"));
			c.setUser(propMail.getString("mail.user"));
			c.setPassword(propMail.getString("mail.password"));
			String[] m = email.split(";");
		//	String[] m = {"esivkova@svrw.rzd"};
			c.addTo(m); 
			c.setSubject(subject); 
			c.setText(body); 
			if (subject.indexOf("List letter") >= 0) {
           		c.addFileName(adrFile);
			} else {
				File f = new File(adrFile);
				if (f.isDirectory())
					nameFile = f.list();
				for (int j = 0; j < nameFile.length; j++)
					c.addFileName(adrFile + File.separator + nameFile[j]);
			}
        	c.setNotificationTo(propMail.getString("mail.notif"));
			if (!c.sendMail())
				err = true;
			else
				err = false;
		} catch (Exception e) { 
			System.out.println("Error send Notification:\n" + e); 
		}
	}
	
	/* отправка письма по заданному адресу */
	public void sendToEMail(String email, String folder, String mmYYYY) {
		String adrFile = workCatalog + folder;
		subject = "Document EIVC " + mmYYYY;
		body = "";
        try {
        	StringUtils su = new StringUtils(); 
        	BufferedReader br = new BufferedReader(new FileReader(su.replaceSystemParameter("${pathProject}/conf/bodyMail.txt")));
            String strTmp = "";
            try {
                if (br != null)
                	while ((strTmp = br.readLine()) != null)   // прочитали строку из файла (если не пустая то цикл)
                		body += strTmp + "\n";
            } catch (IOException ioe) {
                System.out.println("Error read file bodyMail.txt");
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error open file bodyMail.txt");
        }
		send(email, adrFile);
		if (err) { 
			System.out.println("Email NOT Send!"); 
			sError += folder + "(" + email + ") Email NOT Send!\n";
		} else {
			String sNewFolder = workCatalog + "archives" + File.separator + folder;
			File newFolder = new File(sNewFolder);
			newFolder.mkdirs();
    		String[] nameFile = "".split("");
    		File f = new File(adrFile);
       		nameFile = f.list();
        	for (int j = 0; j < nameFile.length; j++) {
        		File file = new File(adrFile + File.separator + nameFile[j]);
        		File newFile = new File(sNewFolder + File.separator + nameFile[j]);
        		if (!file.renameTo(newFile))
        			sError += folder + File.separator + nameFile[j] + " отправлен, но не перемещен в архив\n";
        	}
       		if (!f.delete())
       			sError += folder + " не удалось удалить\n";
		}
	}
	
	/* отправка почты всем пользователям из списка */
	public void sendByAllMail(String address_listContractors, String body) {
        String email = "";
        try {
        	Workbook workbook = Workbook.getWorkbook(new File(address_listContractors)); 
        	Sheet sheet = workbook.getSheet(0); 
       		for (int q = 1; q < sheet.getRows(); q++) {
               	if (!sheet.getCell(7, q).getContents().trim().equals(""))
               		email += ";" + sheet.getCell(7, q).getContents().trim();
               	if (!sheet.getCell(8, q).getContents().trim().equals(""))
               		email += ";" + sheet.getCell(8, q).getContents().trim();
               	if (!email.equals("") && email != null) {
            		subject = "Document EIVC";
            		this.body = body;
            		send(email, workCatalog + "Уведомления");
        			if (err) {
        				System.out.println("Email NOT Send!"); 
        				sError += q + " Email NOT Send!\n";
        			}
               	}
           	    email = "";
       		}
        	workbook.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	/* отправка почты всем пользователям из списка */
	public void sendByAllMail_XML(String address_listContractors, String body) {
        String email = "";
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(address_listContractors));
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("contractor");
            for (int je = 0; je < nodeLst.getLength(); je++) {
                Node fstNode = nodeLst.item(je);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elj = (Element)fstNode;
                   	if (elj.getElementsByTagName("email1").item(0).getChildNodes().item(0) != null)
                   		email += ";" + elj.getElementsByTagName("email1").item(0).getChildNodes().item(0).getNodeValue();
                   	if (elj.getElementsByTagName("email2").item(0).getChildNodes().item(0) != null)
                   		email += ";" + elj.getElementsByTagName("email2").item(0).getChildNodes().item(0).getNodeValue();
                   	if (!email.equals("") && email != null) {
                		subject = "Document EIVC";
                		this.body = body;
                		send(email, workCatalog + "Уведомления");
            			if (err) {
            				System.out.println("Email NOT Send!"); 
            				sError += elj.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue() + " Email NOT Send!\n";
            			}
                   	}
               	    email = "";
                }
       		}
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	/* оправка Списока конвертов за startDay-endDay на today.xls на указаный адрес */
	public void sendMail(String nameFile, String email, String subject, String body) {
		this.subject = subject;
		this.body = body;
		send(email, workCatalog + nameFile + ".xls");
   		if (err) { 
   			System.out.println("Email NOT Send!"); 
   			sError += "Email NOT Send!\n";
   		}
	}
	
	/* отправка файлов по адресатам */
	public void sendFiles(String address_listContractors, String family) {
        String[] yyyyMM = "".split("");
        String[] folder = "".split("");
        String email = "";
        try {
        	Workbook workbook = Workbook.getWorkbook(new File(address_listContractors)); 
        	Sheet sheet = workbook.getSheet(0); 
            File folderUser = new File(workCatalog + family + File.separator);
           	if (folderUser.isDirectory())
           		yyyyMM = folderUser.list(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return Pattern.compile("(\\d{4}).(0|1)(\\d)").matcher(name).matches();
					}
           			
           		});
           	for (int j = 0; j < yyyyMM.length; j++) {
      	        File f4 = new File(workCatalog + family + File.separator + yyyyMM[j] + File.separator);
       	    	if (f4.isDirectory())
       	    		folder = f4.list(new FilenameFilter() {
             			private Pattern p1 = Pattern.compile("([\\D\\s-\\d\\(\\).]+)(\\s)(\\d{10})");
               			private Pattern p2 = Pattern.compile("([\\D\\s-\\d\\(\\).]+)(\\s)(i)(d)(R)(3)");
               			public boolean accept(File dir, String name) {
               				if (p1.matcher(name).matches())
               					return true;
               				else
               					return p2.matcher(name).matches();
               			}
               		});
       	    	for (int i = 0; i < folder.length; i++) {
   					for (int q = 0; q < sheet.getRows(); q++) {
   						if (folder[i].indexOf("idR3") < 0) {
           					if (sheet.getCell(5, q).getContents().trim().indexOf(folder[i].substring(folder[i].length() - 10)) == 0) {
           						if (!sheet.getCell(7, q).getContents().trim().equals(""))
           							email += ";" + sheet.getCell(7, q).getContents().trim();
           						if (!sheet.getCell(8, q).getContents().trim().equals(""))
           							email += ";" + sheet.getCell(8, q).getContents().trim();
           					}
   						} else {
           					if (sheet.getCell(1, q).getContents().trim().equals(folder[i].substring(0, folder[i].length() - 5))) {
           						if (!sheet.getCell(7, q).getContents().trim().equals(""))
           							email += ";" + sheet.getCell(7, q).getContents().trim();
           						if (!sheet.getCell(8, q).getContents().trim().equals(""))
           							email += ";" + sheet.getCell(8, q).getContents().trim();
           					}
   						}
   					}
					if (!email.equals("") && email != null) {
						sendToEMail(email, family + File.separator + yyyyMM[j] + File.separator + folder[i], yyyyMM[j].substring(5) + "." + yyyyMM[j].substring(0,4));
					}
       	    		email = "";
       	    	}
       		}
        	workbook.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	/* отправка файлов по адресатам */
	public void sendFiles_XML(String address_listContractors, String family) {
        String[] yyyyMM = "".split("");
        String[] folder = "".split("");
        String email = "";
        try {
            File folderUser = new File(workCatalog + family + File.separator);
           	if (folderUser.isDirectory())
           		yyyyMM = folderUser.list(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return Pattern.compile("(\\d{4}).(0|1)(\\d)").matcher(name).matches();
					}
           			
           		});
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(address_listContractors));
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("contractor");
           	for (int j = 0; j < yyyyMM.length; j++) {
      	        File f4 = new File(workCatalog + family + File.separator + yyyyMM[j] + File.separator);
       	    	if (f4.isDirectory())
       	    		folder = f4.list(new FilenameFilter() {
             			private Pattern p1 = Pattern.compile("([\\D\\s-\\d\\(\\).]+)(\\s)(\\d{10})");
               			private Pattern p2 = Pattern.compile("([\\D\\s-\\d\\(\\).]+)(\\s)(i)(d)(R)(3)");
               			public boolean accept(File dir, String name) {
               				if (p1.matcher(name).matches())
               					return true;
               				else
               					return p2.matcher(name).matches();
               			}
               		});
       	    	for (int i = 0; i < folder.length; i++) {
                    for (int je = 0; je < nodeLst.getLength(); je++) {
                        Node fstNode = nodeLst.item(je);
                        if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element elj = (Element)fstNode;
       						if (folder[i].indexOf("idR3") < 0) {
               					if (elj.getElementsByTagName("r3").item(0).getChildNodes().item(0).getNodeValue().trim().indexOf(folder[i].substring(folder[i].length() - 10)) == 0) {
               						if (elj.getElementsByTagName("email1").item(0).getChildNodes().item(0) != null)
               							email += ";" + elj.getElementsByTagName("email1").item(0).getChildNodes().item(0).getNodeValue().trim();
               						if (elj.getElementsByTagName("email2").item(0).getChildNodes().item(0) != null)
               							email += ";" + elj.getElementsByTagName("email2").item(0).getChildNodes().item(0).getNodeValue().trim();
               					}
       						} else {
               					if (elj.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue().trim().equals(folder[i].substring(0, folder[i].length() - 5))) {
               						if (elj.getElementsByTagName("email1").item(0).getChildNodes().item(0) != null)
               							email += ";" + elj.getElementsByTagName("email1").item(0).getChildNodes().item(0).getNodeValue().trim();
               						if (elj.getElementsByTagName("email2").item(0).getChildNodes().item(0) != null)
               							email += ";" + elj.getElementsByTagName("email2").item(0).getChildNodes().item(0).getNodeValue().trim();
               					}
       						}
                        }
   					}
					if (!email.equals("") && email != null) {
						sendToEMail(email, family + File.separator + yyyyMM[j] + File.separator + folder[i], yyyyMM[j].substring(5) + "." + yyyyMM[j].substring(0,4));
					}
       	    		email = "";
       	    	}
       		}
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
}
