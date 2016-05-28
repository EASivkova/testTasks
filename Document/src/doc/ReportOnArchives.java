package doc;

import java.io.File;
import java.util.Vector;
import java.util.regex.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import jxl.*;

public class ReportOnArchives {
	
	private String result = "";
	private String sError = "";
	private String[] itemUser;
	private String YYYYmm;
	
	ReportOnArchives(String[] itemUser, String YYYYmm) {
		this.itemUser = itemUser;
		this.YYYYmm = YYYYmm;
	}

	public String getResult() {
		return result;
	}

	public String getSError() {
		return sError;
	}

	/* проверка содержимого архива за указаный месяц */
	public void parseCatalog(String adresArchives, String adresReport, String year, int month, String address_listContractors) {
    	String[] title = {" ", "договор R3", "список конрагентов (кр.наименование)", "Наименование", "АКТ", "СЧЕТ-ФАКТУРА", "СЧЕТ", "НЕ РАСПОЗНАННЫЕ", "Каталог", "Состояние договора"};
   		File workbookYear = new File(adresReport + year + ".xls");
		WorkXLS report = new WorkXLS(adresReport + year + ".xls", 6);
       	if (!workbookYear.exists())
       		report.createWorkbookYear(title);
   		try {
   			// заполнение книги отчета списком контрагентов
			report.open(month);
			Workbook wbBD = Workbook.getWorkbook(new File(address_listContractors)); 
			Sheet shBD = wbBD.getSheet(0);
			for (int q = 2; q < shBD.getRows() && shBD.getCell(1, q).getContents() != ""; q++) {
   				report.writeLabel(0, q-1, shBD.getCell(0, q).getContents());
   				report.writeLabel(1, q-1, shBD.getCell(5, q).getContents());
   				report.writeLabel(2, q-1, shBD.getCell(1, q).getContents());
   				report.writeLabel(3, q-1, shBD.getCell(4, q).getContents());
   				report.writeLabel(9, q-1, shBD.getCell(24, q).getContents());
   			}
			wbBD.close();
			for (int i = 0; i < itemUser.length; i++) {
				File folder = new File(adresArchives + itemUser[i] + File.separator + YYYYmm + File.separator);
		        String[] nameFirm = "".split("");
		    	if (folder.exists() && folder.isDirectory()) {
		    		nameFirm = folder.list();
		    		for (int j = 0; j < nameFirm.length; j++) {
	   					if (!controlFormatName(nameFirm[j])) {
		    				sError += "По адресу " + itemUser[i] + "/" + YYYYmm + " каталог предприятия '" + nameFirm[j] + "' имеет не верный формат\n";
	   					} else {
	   						wbBD = Workbook.getWorkbook(new File(address_listContractors)); 
	   						shBD = wbBD.getSheet(0);
	   						Cell c = shBD.findCell(nameFirm[j].substring(nameFirm[j].lastIndexOf(" ") + 1));
			    			int row = 0;
			    			if (c != null) {
			    				row = c.getRow() - 1;
			    				if (!shBD.getCell(1, row+1).getContents().equals(nameFirm[j].substring(0, nameFirm[j].lastIndexOf(" ")))) {
			    					Cell[] cells = shBD.getColumn(5);
			    					for (int k = row+2; k < cells.length; k++) {
			    						if (cells[k].getContents().equals(nameFirm[j].substring(nameFirm[j].lastIndexOf(" ") + 1))) {
			    							row = 0;
			    							break;
			    						}
			    					}
			    				}
			    			}
			    			if (row == 0) {
			    				c = shBD.findCell(nameFirm[j].substring(0, nameFirm[j].lastIndexOf(" ")));
			    				if (c != null)
			    					row = c.getRow() - 1;
			    				else {
			    					Cell[] cells = shBD.getColumn(1);
			    					for (int k = 0; k < cells.length; k++) {
			    						if (cells[k].getContents().trim().toUpperCase().equals(nameFirm[j].substring(0, nameFirm[j].lastIndexOf(" ")).toUpperCase())) {
			    							row = k + 1;
			    							break;
			    						}
			    					}
			    				}
			    			}
			    			wbBD.close();
			    			if (row == 0) {
			    				sError += "Предприятие " + nameFirm[j] + " (" + itemUser[i] + "/" + YYYYmm + ") не найдено в Списке контрагентов\n";
			    			} else {
								int[] kolDoc = countTypeDoc(folder + File.separator + nameFirm[j]);
			    				for (int k = 0; k < kolDoc.length; k++) {
			    					if (kolDoc[k] != 0) {
			    						report.writeNumber(k + 4, row, kolDoc[k]);
			    					}
			    				}
			    				report.writeLabel(8, row, itemUser[i]);
			    			}
	   	   				}
			    	}
		    	}
			}
			report.close();
			result = "Отчет по архиву за указаный месяц сформирован.";
   			if (!sError.equals("") && sError != null)
   	   	   		result += " Отчет не полон. Подробнее в лог-файле.";
		} catch (Exception e) {
			System.out.println(e);
	   		result = "Ошибка формирования отчета по архиву за указаный месяц. ";
		}
	}
	
	/* проверка содержимого архива за указаный месяц */
	public void parseCatalog_XML(String adresArchives, String adresReport, String year, int month, String address_listContractors) {
    	String[] title = {" ", "договор R3", "список конрагентов (кр.наименование)", "Наименование", "АКТ", "СЧЕТ-ФАКТУРА", "СЧЕТ", "НЕ РАСПОЗНАННЫЕ", "Каталог", "Состояние договора"};
   		File workbookYear = new File(adresReport + year + ".xls");
		WorkXLS report = new WorkXLS(adresReport + year + ".xls", 6);
       	if (!workbookYear.exists())
       		report.createWorkbookYear(title);
   		try {
   			// заполнение книги отчета списком контрагентов
			report.open(month);
			for (int i = 0; i < itemUser.length; i++) {
				File folder = new File(adresArchives + itemUser[i] + File.separator + YYYYmm + File.separator);
		        String[] nameFirm = "".split("");
		    	if (folder.exists() && folder.isDirectory()) {
		    		nameFirm = folder.list();
		    		Vector<String> clients = new Vector<String>();
		    		for (int j = 0; j < nameFirm.length; j++) {
	   					if (!controlFormatName(nameFirm[j]))
		    				sError += "По адресу " + itemUser[i] + "/" + YYYYmm + " каталог предприятия '" + nameFirm[j] + "' имеет не верный формат\n";
	   					else
	   						clients.add(nameFirm[j]);
		    		}
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document doc = db.parse(new File(address_listContractors));
					doc.getDocumentElement().normalize();
					NodeList nodeLst = doc.getElementsByTagName("contractor");
					for (int je = 0; je < nodeLst.getLength(); je++) {
   						Node fstNode = nodeLst.item(je);
   						if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
   							Element elj = (Element)fstNode;
  							report.writeLabel(0, je+1, elj.getElementsByTagName("manager").item(0).getChildNodes().item(0).getNodeValue());
   							report.writeLabel(1, je+1, elj.getElementsByTagName("r3").item(0).getChildNodes().item(0).getNodeValue());
   							report.writeLabel(2, je+1, elj.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue());
   							report.writeLabel(3, je+1, elj.getElementsByTagName("full_name").item(0).getChildNodes().item(0).getNodeValue());
   							if (elj.getElementsByTagName("status_dogovor").item(0).getChildNodes().item(0) != null)
   								report.writeLabel(9, je+1, elj.getElementsByTagName("status_dogovor").item(0).getChildNodes().item(0).getNodeValue());
   				    		for (int j = 0; j < clients.size(); j++) {
		   						if (elj.getElementsByTagName("r3").item(0).getChildNodes().item(0).getNodeValue().indexOf(clients.elementAt(j).substring(clients.elementAt(j).lastIndexOf(" ") + 1)) > -1) {
   			   						if (elj.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue().toUpperCase().indexOf(clients.elementAt(j).substring(0, nameFirm[j].lastIndexOf(" ")).toUpperCase()) > -1) {
   										int[] kolDoc = countTypeDoc(folder + File.separator + clients.elementAt(j));
   					    				for (int k = 0; k < kolDoc.length; k++) {
   					    					if (kolDoc[k] != 0) {
   					    						report.writeNumber(k + 4, je+1, kolDoc[k]);
   					    					}
   					    				}
   					    				report.writeLabel(8, je+1, itemUser[i]);
   					    				clients.removeElementAt(j);
  					    				break;
   			   						}
		   						} else {
   			   						if (elj.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue().toUpperCase().indexOf(clients.elementAt(j).substring(0, nameFirm[j].lastIndexOf(" ")).toUpperCase()) > -1) {
   										int[] kolDoc = countTypeDoc(folder + File.separator + clients.elementAt(j));
   					    				for (int k = 0; k < kolDoc.length; k++) {
  					    					if (kolDoc[k] != 0) {
  					    						report.writeNumber(k + 4, je+1, kolDoc[k]);
   					    					}
  					    				}
   					    				report.writeLabel(8, je+1, itemUser[i]);
   					    				clients.removeElementAt(j);
   					    				break;
   			   						}
		   						}
	   						}
	   	   				}
			    	}
			    	for (int j = 0; j < clients.size(); j++)
	    				sError += "Предприятие " + clients.elementAt(j) + " (" + itemUser[i] + "/" + YYYYmm + ") не найдено в Списке контрагентов\n";
		    	}
			}
			report.close();
			result = "Отчет по архиву за указаный месяц сформирован.";
   			if (!sError.equals("") && sError != null)
   	   	   		result += " Отчет не полон. Подробнее в лог-файле.";
		} catch (Exception e) {
			System.out.println(e);
	   		result = "Ошибка формирования отчета по архиву за указаный месяц. ";
		}
	}
	
	/* проверка содержимого каталога пользователя за указаный месяц */
	public void parseCatalogUser(String startAdres, String year, int month) {
    	String[] title = {"договор R3", "Кр.наименование", "АКТ", "СЧЕТ-ФАКТУРА", "СЧЕТ", "НЕ РАСПОЗНАННЫЕ"};
        String[] nameFirm = "".split("");
		File folder = new File(startAdres + YYYYmm);
    	if (folder.exists()) {
        	if (folder.isDirectory())
        		nameFirm = folder.list();
    		File workbookYear = new File(startAdres + year + ".xls");
    		WorkXLS xls = new WorkXLS(startAdres + year + ".xls", 6);
        	if (!workbookYear.exists())
        		xls.createWorkbookYear(title);
    		try {
    			xls.open(month);
    			for (int i = 0; i < nameFirm.length; i++) {
					if (!controlFormatName(nameFirm[i])) {
	    				xls.writeLabel(1, i + 1, nameFirm[i]);
					} else {
						xls.writeLabel(1, i + 1, nameFirm[i].substring(0, nameFirm[i].lastIndexOf(" ")));
						xls.writeLabel(0, i + 1, nameFirm[i].substring(nameFirm[i].lastIndexOf(" ") + 1));
					}
					int[] kolDoc = countTypeDoc(startAdres + YYYYmm + File.separator + nameFirm[i]);
    				for (int j = 0; j < 3; j++) {
    					if (kolDoc[j] != 0) {
    						xls.writeNumber(j + 2, i + 1, kolDoc[j]);
    					}
    				}
    			}
    			xls.close();
        		result = "Отчет по архиву за указаный месяц у данного пользоваеля сформирован. ";
			} catch (Exception e) {
				System.out.println(e);
	    		result = "Ошибка формирования отчета по архиву за указаный месяц у данного пользоваеля. ";
			}
    	} else {
    		result = "В архиве за указаный месяц у данного пользоваеля нет файлов. ";
    	}
	}
	
	private boolean controlFormatName(String name) {
		String regex1 = "(.+)\\s(\\d{9,10})";
		String regex2 = "(.+)\\s(i)(d)(R)(3)";
		boolean formatNames = false;
		Pattern p = Pattern.compile(regex1);
		Matcher m = p.matcher(name);
		while (m.find()) {
			formatNames = true;
		}
		m.reset();
		if (!formatNames) {
			p = Pattern.compile(regex2);
			m = p.matcher(name);
			while (m.find()) {
				formatNames = true;
			}
			m.reset();
		}
		return formatNames;
	}

	/* определение кол-ва типов документов в папке */
	private int[] countTypeDoc(String adrFolder) {
        String[] nameFile = "".split("");
		File folderFirm = new File(adrFolder);
		if (folderFirm.isDirectory())
			nameFile = folderFirm.list();
		int[] kolDoc = {0, 0, 0, 0};
		for (int j = 0; j < nameFile.length; j++) {
			for (int k = 0; k < 3; k++) {
				if (nameFile[j].indexOf("" + (k+1)) == 0)
					kolDoc[k]++;
			}
			if (nameFile[j].indexOf("1") != 0 && nameFile[j].indexOf("2") != 0 && nameFile[j].indexOf("3") != 0) {
				kolDoc[3]++;
			}
		}
		return kolDoc;
	}
		
}
