package doc;

import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import jxl.*;

public class ListCover {

	private String address_listContractors;
	private String workCatalog;
	private String user;
	private String sError = "";
	private String sResult = "";
	
	ListCover(String address_listContractors, String workCatalog, String user) {
		this.address_listContractors = address_listContractors;
		this.workCatalog = workCatalog;
		this.user = user;
	}
	
	public String getSResult() {
		return sResult;
	}

	public String getSError() {
		return sError;
	}

	/* печать конвертов */
	public void createFileForPP() {
        String temp = "";
        int num = 1;
        String[] title = {"NUM", "INDEXTO", "REGION", "AREA", "CITY", "ADRES", "ADRESAT", "MASS", "VALUE", "PAYMENT", "COMMENT"};
        String[] listReestr = "".split("");
        try {
        	WorkXLS xls = new WorkXLS(workCatalog + user + "конверты.xls", 11);
        	xls.createWorkbook(title);
        	Workbook BD = Workbook.getWorkbook(new File(address_listContractors)); 
        	Sheet sheetBD = BD.getSheet(0); 
            File folderReestr = new File(workCatalog + "Реестр" + File.separator);
           	if (folderReestr.isDirectory())
           		listReestr = folderReestr.list(new FilenameFilter() {
    				public boolean accept(File dir, String name) {
    					if (name.indexOf(user + "реестр обр.файлов") > -1)
    						return true;
    					else
    						return false;
    				}
           		});
           	for (int k = 0; k < listReestr.length; k++) {
       			Workbook workbookR = Workbook.getWorkbook(new File(workCatalog + "Реестр" + File.separator + listReestr[k]));
       			Sheet sheetR = workbookR.getSheet(0); 
       			for (int j = 1; j < sheetR.getRows(); j++) {
      				if (sheetR.getCell(1, j).getContents().indexOf("Ручная обработка") < 0 && temp.indexOf(sheetR.getCell(2, j).getContents().trim()) < 0) {
      					for (int q = 2; q < sheetBD.getRows(); q++) {
       						if (sheetBD.getCell(5, q).getContents().trim().equals(sheetR.getCell(2, j).getContents()) && !sheetR.getCell(2, j).getContents().trim().equals("") && sheetBD.getCell(17, q).getContents().indexOf("почта") > -1) {
       							String index = sheetBD.getCell(9, q).getContents().trim();
       							String adres = sheetBD.getCell(10, q).getContents().trim();
       							String adresat = sheetBD.getCell(21, q).getContents().trim() + " " + sheetBD.getCell(11, q).getContents().trim();
       							if (index.equals("") || index == null || adres.equals("") || adres == null || adresat.equals(" "))
       								sError += sheetBD.getCell(1, q).getContents() + " - в БД отсутствует индекс и/или адрес, и/или адресат (в список конвертов не занесено)\n";
       							else {
       								writeToFileForPP(num, index, adres, adresat);
       								num++;
       							}
   								temp += sheetR.getCell(2, j).getContents().trim();
       						}
       					}
       				}
       			}
       			workbookR.close();
       		}
           	BD.close();
           	sResult = "Файл для импорта в Партионную почту создан. ";
        } catch (Exception e) {
            System.out.println(e);
            sResult = "Ошибка формирования файла. ";
        }
	}
	
	/* печать конвертов */
	public void createFileForPP_XML() {
        String temp = "";
        int num = 1;
        String[] title = {"NUM", "INDEXTO", "REGION", "AREA", "CITY", "ADRES", "ADRESAT", "MASS", "VALUE", "PAYMENT", "COMMENT"};
        String[] listReestr = "".split("");
        try {
        	WorkXLS xls = new WorkXLS(workCatalog + user + "конверты.xls", 11);
        	xls.createWorkbook(title);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(address_listContractors));
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("contractor");
            File folderReestr = new File(workCatalog + "Реестр" + File.separator);
           	if (folderReestr.isDirectory())
           		listReestr = folderReestr.list(new FilenameFilter() {
    				public boolean accept(File dir, String name) {
    					if (name.indexOf(user + "реестр обр.файлов") > -1)
    						return true;
    					else
    						return false;
    				}
           		});
           	for (int k = 0; k < listReestr.length; k++)  {
       			Workbook workbookR = Workbook.getWorkbook(new File(workCatalog + "Реестр" + File.separator + listReestr[k]));
      			Sheet sheetR = workbookR.getSheet(0); 
      			for (int j = 1; j < sheetR.getRows(); j++) {
      				if (sheetR.getCell(1, j).getContents().indexOf("Ручная обработка") < 0 && temp.indexOf(sheetR.getCell(2, j).getContents().trim()) < 0) {
      		            for (int je = 0; je < nodeLst.getLength(); je++) {
      		                Node fstNode = nodeLst.item(je);
       		                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
       		                    Element elj = (Element)fstNode;
           						if (elj.getElementsByTagName("r3").item(0).getChildNodes().item(0).getNodeValue().indexOf(sheetR.getCell(2, j).getContents()) > -1 && !sheetR.getCell(2, j).getContents().trim().equals("") && elj.getElementsByTagName("view_sending").item(0).getChildNodes().item(0).getNodeValue().indexOf("почта") > -1) {
           							if (elj.getElementsByTagName("index").item(0).getChildNodes().item(0) != null && elj.getElementsByTagName("address").item(0).getChildNodes().item(0) != null && elj.getElementsByTagName("name_for_cover").item(0).getChildNodes().item(0) != null) {
               							String index = elj.getElementsByTagName("index").item(0).getChildNodes().item(0).getNodeValue();
               							String address = elj.getElementsByTagName("address").item(0).getChildNodes().item(0).getNodeValue();
               							String adresat = elj.getElementsByTagName("name_for_cover").item(0).getChildNodes().item(0).getNodeValue();
               							if (elj.getElementsByTagName("addressee").item(0).getChildNodes().item(0) != null)
               								adresat += " " + elj.getElementsByTagName("addressee").item(0).getChildNodes().item(0).getNodeValue();
              							writeToFileForPP(num, index, address, adresat);
              							num++;
          							} else {
           								sError += elj.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue() + " - в БД отсутствует индекс и/или адрес, и/или адресат (в список конвертов не занесено)\n";
           							}
       								temp += sheetR.getCell(2, j).getContents().trim();
           						}
       		                }
       					}
       				}
       			}
       			workbookR.close();
       		}
           	sResult = "Файл для импорта в Партионную почту создан. ";
        } catch (Exception e) {
            System.out.println(e);
            sResult = "Ошибка формирования файла. ";
        }
	}
	
	public void writeToFileForPP(int num, String index, String adres, String adresat) {
		try {
        	WorkXLS xls = new WorkXLS(workCatalog + user + "конверты.xls", 11);
   			xls.open(0);
   			xls.writeNumber(0, num, num);
   			xls.writeLabel(1, num, index);
   			if (adres.length() > 60) {
   				adres = adres.substring(0, 60);
   				sError += "В записи №" + num + " слишком длинный адрес (при записи в файл лишнее было отброшено)\n";
   			}
   			xls.writeLabel(5, num, adres);
   			if (adresat.length() > 60) {
   				adresat = adresat.substring(0, 60);
   				sError += "В записи №" + num + " слишком длинный адресат (при записи в файл лишнее было отброшено)\n";
   			}
   			xls.writeLabel(6, num, adresat);
   			xls.writeNumber(7, num, 0.04); // MASS
   			xls.writeNumber(8, num, 0); // VALUE
   			xls.writeNumber(9, num, 0); // PAYMENT
   			xls.writeLabel(10, num, " "); // COMMENT
   			num++;
       		xls.close();
        } catch (Exception e) {
            System.out.println(e);
            sResult = "Ошибка формирования файла со списком конвертов. ";
        }
	}
	
}
