package doc;

import java.awt.Frame;
import java.io.*;
import java.util.regex.*;

import javax.swing.JOptionPane;
import javax.xml.parsers.*;

import org.w3c.dom.*;

import jxl.*;
import ru.svrw.eivc.date.Calendar;
import ru.svrw.eivc.util.Properties;
import ru.svrw.eivc.util.StringUtils;

public class IdentificationGVC {
	
	private Calendar today = new Calendar();
	private String address_listContractors;
	private String workCatalog;
	private String user;
	private String YYYYmm;
	private String family;
	private int indexMonth; // jcbMonth.getSelectedIndex()
	private boolean copies;
	private String sError = "";
	
	private String inn = " ";
	private String kpp = " ";
	private String id_R3 = " ";
	private String newName = " ";
	private String krName = " ";
	private String adresat = " ";
	
	private StringUtils su = new StringUtils(); 
	private Properties prop = new Properties(su.replaceSystemParameter("${pathProject}/conf/settings.properties"));

	
	IdentificationGVC(String address_listContractors, String workCatalog, String YYYYmm, String family, int indexMonth) {
		this.address_listContractors = address_listContractors;
		this.workCatalog = workCatalog;
		this.YYYYmm = YYYYmm;
		this.family = family;
		user = family.substring(0, 1) + "_";
		this.indexMonth = indexMonth; // jcbMonth.getSelectedIndex()
	}

	public String getSError() {
		return sError;
	}

	// распознание файлов txt из папки TOut
	public void parseTXT() {
        String str = "";
        String s = "";
        String nameFolder = " ";
        String[] title = {"є", "yyyyMM", "id R3", "Ќаименование предпри€ти€", "»м€ файла", "јдресат (полн.наименование)"};
        int row = 1;
    	File fileOut;
    	File fileRename;
    	File folderFirma; 
    // файлы из Out, переименованные файлы, каталог Tout, папка дл€ файлов предпри€ти€
        BufferedReader br;
        today = new Calendar();
        try {
        	WorkXLS reestr = new WorkXLS(workCatalog + "–еестр" + File.separator + user + "реестр обр.файлов от " + today.getDBTimestamp() + ".xls", 6);
        	reestr.createWorkbook(title);
        	File folderTOut = new File(workCatalog + "TOut");
        	String[] nameFile = folderTOut.list();
        	for (int j = 0; j < nameFile.length; j++) {
        		fileOut = new File(workCatalog + "Out" + File.separator + "Out" + nameFile[j].substring(4, 8) + ".pdf");
        		br = new BufferedReader(new FileReader(workCatalog + "TOut" + File.separator + nameFile[j]));
        		if (br != null) {
        			while ((str = br.readLine()) != null)
        				s += ";" + str.trim();
        			s = s.replaceAll(" ", "");
        			s = s.replaceAll("ю", "10");
        			s = s.replaceAll("ё", "10");
        			System.out.println(nameFile[j].substring(4, 8));
        			definitionTypeFile(s);
					reestr.writeLabel(0, row, nameFile[j].substring(4, 8), 0);
    				if (((inn.equals(" ") || inn == null) && (id_R3.equals(" ") || id_R3 == null)) || newName.equals(" ")) {
    					reestr.writeLabel(1, row, "–учна€ обработка файла", 0);
    				} else {
    					getDataFirm(nameFile[j].substring(4, 8));
    					if (id_R3.length() > 10)
    						id_R3 = id_R3.substring(0, 10);
    					nameFolder = getNameFolder(krName);
    					switch (Integer.parseInt(newName)) {
    					case 15: newName = proverka(nameFolder, newName, "([^.,0-9])(\\d{1,6})([^.,0-9])", s); break;
    					case 25: newName = proverka(nameFolder, newName, getRegex("factura"), s); break;
    					case 35: newName = proverka(nameFolder, newName, getRegex("billGVC"), s); break;
    					default: proverka(nameFolder, newName); break;
    					}
    					reestr.writeLabel(1, row, YYYYmm, 0);
    					reestr.writeLabel(2, row, id_R3, 0);
    					reestr.writeLabel(3, row, krName, 0);
    					reestr.writeLabel(4, row, newName, 0);
    					reestr.writeLabel(5, row, adresat, 0);
    					if (copies) {
   	 						if (!fileOut.delete())
   	 							sError += nameFile[j].substring(4, 8) + " (копи€) не удалось удалить\n";
    					} else {
    						folderFirma = new File(workCatalog + family + File.separator + YYYYmm + File.separator + nameFolder);
    						folderFirma.mkdirs();
       	 					fileRename = new File(workCatalog + family + File.separator + YYYYmm + File.separator + nameFolder + File.separator + newName + ".pdf");
       	 					if (fileRename.exists()) {
       	 						if (!fileOut.delete())
       	 							sError += nameFile[j].substring(4, 8) + " (копи€) не удалось удалить\n";
       	 					} else {
       	 						if (!fileOut.renameTo(fileRename))
       	 							sError += nameFile[j].substring(4, 8) + " не переименован в " + nameFolder + File.separator + newName + "\n";
       	 					}
    					}
    				}
					row++;
        		}
    			nameFolder = " ";
        		s = "";
				inn = " ";
				kpp = " ";
				newName = " ";
				adresat = " ";
        		id_R3 = " ";
        		krName = " ";
        		copies = false;
        		br.close();
				if (!fileOut.exists()) {
 					File file3 = new File(workCatalog + "TOut" + File.separator + nameFile[j]);
 					if (!file3.delete())
	 					sError += nameFile[j] + " не удалось удалить\n";
	 			}
        	}
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private void definitionTypeFile(String s) {
		System.out.println(s);
    	Matcher m;
		if (!findRegex_Invoice(s))
			if (!findRegex_BILL(s))
				findRegex_AKT(s);
		if (!findRegex_InnKpp(getRegex("innkpp"), s, 12))   //  запись в формате "inn/kpp"
			if (!findRegex_InnKpp(getRegex("innKPPkpp"), s, 14)) {   //  запись в формате "inn  ѕѕ kpp"
				m = Pattern.compile(getRegex("inn")).matcher(s);
				while (m.find() && (inn.equals(" ") || inn.equals("7708503727"))) {
					System.out.println(m.group());
					String str = ignoreTChK(m.group());
					inn = str.substring(1, 13);
				}
				m.reset();
			}
	}
	
	private boolean findRegex_Invoice(String s) {
		Pattern p = Pattern.compile(getRegex("factura"));
		Matcher m = p.matcher(s);
		while (m.find()) {
			newName = "25";
		}
		m.reset();
		if (newName.equals("25"))
			return true;
		else
			return false;
	}
	
	private boolean findRegex_AKT(String s) {
		Pattern p = Pattern.compile("([^.,0-9])(\\d{1,6})([^.,0-9])");
		Matcher m = p.matcher(s);
		while (m.find()) {
			newName = "15";
		}
		m.reset();
		if (newName.equals("15"))
			return true;
		else
			return false;
	}
	
	private boolean findRegex_BILL(String s) {
		//Pattern p = Pattern.compile("(\\D)(\\D)(\\d{1,4})([^.,0-9]{2,3})([0-3])(\\d)([.,])(0|1)(\\d)([.,])(2)(0)(\\d{2})");
		Pattern p = Pattern.compile(getRegex("billGVC"));
		Matcher m = p.matcher(s);
		while (m.find()) {
			newName = "35";
		}
		m.reset();
		if (newName.equals("35"))
			return true;
		else
			return false;
	}
	
	private boolean findRegex_InnKpp(String regex, String s, int startKPP) {
		Matcher m = Pattern.compile(regex).matcher(s);
		while (m.find() && (inn.equals(" ") || inn != null)) {
			String str = ignoreTChK(m.group());
			if (str.indexOf("7708503727") != 1) {
				inn = str.substring(1, 11);
				kpp = str.substring(startKPP);
			}
		}
		m.reset();
		if (inn.equals(" ") || inn != null || kpp.equals(" ") || kpp != null)
			return false;
		else
			return true;
	}
	
	private void getDataFirm(String nameDoc) {
		int z = 0;
		try {
			Workbook wbXLS = Workbook.getWorkbook(new File(address_listContractors)); 
			Sheet shXLS = wbXLS.getSheet(0); 
			for (int i = 1; i < shXLS.getRows(); i++) {
				if ((shXLS.getCell(12, i).getContents().indexOf(inn) > -1 && shXLS.getCell(13, i).getContents().indexOf(kpp) > -1 && !inn.equals(" ") && !kpp.equals(" ")) || (shXLS.getCell(12, i).getContents().indexOf(inn) > -1 && !inn.equals(" "))) {
					id_R3 = shXLS.getCell(5, i).getContents().trim();
					krName = shXLS.getCell(1, i).getContents().trim();
					adresat = shXLS.getCell(21, i).getContents().trim() + " " + shXLS.getCell(11, i).getContents().trim();
					z++;
				}
			}
			if (z > 1) {
				String[] choices = new String[z];
				int[] k = new int[z];
				int a = 0;
				for (int i = 1; i < shXLS.getRows(); i++)
					if ((shXLS.getCell(12, i).getContents().indexOf(inn) > -1 && shXLS.getCell(13, i).getContents().indexOf(kpp) > -1 && !inn.equals(" ") && !kpp.equals(" ")) || (shXLS.getCell(12, i).getContents().indexOf(inn) > -1 && !inn.equals(" "))) {
						if (a > z)
							System.out.println("2 record one number R3");
						choices[a] = shXLS.getCell(5, i).getContents().trim() + " (" + shXLS.getCell(0, i).getContents().trim() + ") " + shXLS.getCell(21, i).getContents().trim() + " " + shXLS.getCell(11, i).getContents().trim();
						k[a] = i;
						a++;
					}
				String response = (String) JOptionPane.showInputDialog(new Frame(), 
						"ƒл€ документа " + nameDoc + " было найдено более 1 номера договора R3\n (»ЌЌ " + inn + ")...",
						"¬ыбор предпри€ти€", JOptionPane.QUESTION_MESSAGE, null, 
						choices, // Array of choices
						choices[0]); // Initial choice
				int q = 0;
				if (response != null) {
					for (int i = 0; i < z; i++)
						if (choices[i].equals(response))
							q = k[i];
				}
				id_R3 = shXLS.getCell(5, q).getContents().trim();
				krName = shXLS.getCell(1, q).getContents().trim();
				adresat = shXLS.getCell(21, q).getContents().trim() + " " + shXLS.getCell(11, q).getContents().trim();
			}
			wbXLS.close();
        } catch (Exception e) {
            System.out.println(e);
        }
		if (adresat.length() > 60)
			adresat = adresat.substring(0, 60);
	}
	
	private void getDataFirm_XML(String nameDoc) {
		int z = 0;
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
    				if ((elj.getElementsByTagName("inn").item(0).getChildNodes().item(0).getNodeValue().indexOf(inn) > -1 && elj.getElementsByTagName("kpp").item(0).getChildNodes().item(0).getNodeValue().indexOf(kpp) > -1 && !inn.equals(" ") && !kpp.equals(" ")) || (elj.getElementsByTagName("inn").item(0).getChildNodes().item(0).getNodeValue().indexOf(inn) > -1 && !inn.equals(" "))) {
    					id_R3 = elj.getElementsByTagName("r3").item(0).getChildNodes().item(0).getNodeValue().trim();
    					krName = elj.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue().trim();
    					adresat = elj.getElementsByTagName("name_for_cover").item(0).getChildNodes().item(0).getNodeValue().trim();
    					if (elj.getElementsByTagName("addressee").item(0).getChildNodes().item(0) != null)
    						adresat += " " + elj.getElementsByTagName("addressee").item(0).getChildNodes().item(0).getNodeValue().trim();
    					z++;
    				}
                }
			}
			if (z > 1) {
				String[] choices = new String[z];
				int[] k = new int[z];
				int a = 0;
	            for (int je = 0; je < nodeLst.getLength(); je++) {
	                Node fstNode = nodeLst.item(je);
	                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
	                    Element elj = (Element)fstNode;
	    				if ((elj.getElementsByTagName("inn").item(0).getChildNodes().item(0).getNodeValue().indexOf(inn) > -1 && elj.getElementsByTagName("kpp").item(0).getChildNodes().item(0).getNodeValue().indexOf(kpp) > -1 && !inn.equals(" ") && !kpp.equals(" ")) || (elj.getElementsByTagName("inn").item(0).getChildNodes().item(0).getNodeValue().indexOf(inn) > -1 && !inn.equals(" "))) {
							if (a > z)
								System.out.println("2 record one number R3");
							choices[a] = elj.getElementsByTagName("r3").item(0).getChildNodes().item(0).getNodeValue().trim() + " (" + elj.getElementsByTagName("manager").item(0).getChildNodes().item(0).getNodeValue().trim() + ") " + elj.getElementsByTagName("name_for_cover").item(0).getChildNodes().item(0).getNodeValue().trim() + " " + elj.getElementsByTagName("addressee").item(0).getChildNodes().item(0).getNodeValue().trim();
							k[a] = je;
							a++;
	    				}
	                }
				}
				String response = (String) JOptionPane.showInputDialog(new Frame(), 
						"ƒл€ документа " + nameDoc + " было найдено более 1 номера договора R3\n (»ЌЌ " + inn + ")...",
						"¬ыбор предпри€ти€", JOptionPane.QUESTION_MESSAGE, null, 
						choices, // Array of choices
						choices[0]); // Initial choice
				int q = 0;
				if (response != null) {
					for (int i = 0; i < z; i++)
						if (choices[i].equals(response))
							q = k[i];
				}
                Node fstNode = nodeLst.item(q);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elj = (Element)fstNode;
					id_R3 = elj.getElementsByTagName("r3").item(0).getChildNodes().item(0).getNodeValue().trim();
					krName = elj.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue().trim();
					adresat = elj.getElementsByTagName("name_for_cover").item(0).getChildNodes().item(0).getNodeValue().trim();
					if (elj.getElementsByTagName("addressee").item(0).getChildNodes().item(0) != null)
						adresat += " " + elj.getElementsByTagName("addressee").item(0).getChildNodes().item(0).getNodeValue().trim();
                }
			}
        } catch (Exception e) {
            System.out.println(e);
        }
		if (adresat.length() > 60)
			adresat = adresat.substring(0, 60);
	}
	
	private String getNameFolder(String krName) {
		if (!id_R3.equals(" ") && !id_R3.equals("") && id_R3 != null) {
			return (krName.equals(" ")?id_R3:(krName + " " + id_R3));
		}
		if (!krName.equals(" ") && !krName.equals("") && krName != null)
			return krName + " idR3";
		return inn + ((!kpp.equals(" ") && kpp != null)?("_" + kpp):"") + " idR3";
	}
	
	private String proverka(String nameFolder, String nameFile, String regex, String s) {
    	String[] title = {"idR3_ р. наименование", "список файлов (, )", "номера счетов дл€ повтор€ющихс€ документов (, )"};
    	String spisok = "";
    	String id = "";
    	String IDs = "";
    	int kol = 0;
    	int row = 0;
    	String nameXLS = "autoreport_" + YYYYmm.substring(0, 4) + ".xls";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		while (m.find()) {
			switch (Integer.parseInt(nameFile)) {
			case 15: id = m.group().substring(1, 7); break;
			case 25: id = s.substring(s.indexOf(m.group()) + 17, s.indexOf(m.group()) + 27); break;
			case 35: 
				int end;
				if (m.group().indexOf(",") > -1)
					if (m.group().indexOf(".") < m.group().indexOf(",") && m.group().indexOf(".") > 6) 
						end = m.group().indexOf("."); 
					else 
						end = m.group().indexOf(",");
				else
					end = m.group().indexOf("."); 
				id = m.group().substring(2, end-4); 
				break;
			}
		}
		m.reset();
        try {
        	File file = new File(prop.getString("adres.archives") + nameXLS);
    		WorkXLS xls = new WorkXLS(prop.getString("adres.archives") + nameXLS, 3);
        	if (!file.exists()) { // если файл или папка не существует
        		xls.createWorkbookYear(title);
        		row = 1;
        	} else {
   				if (xls.findRow_inAutoreport(indexMonth, nameFolder, 0)) {
   					row = xls.getRow();
   					String[] contRow = xls.getContentRow();
  					spisok = contRow[1];
   					IDs = contRow[2];
   					if (spisok.indexOf(nameFile) >= 0) {
   						if (IDs.indexOf(id) >= 0) {
   							copies = true;
   						} else {
   							String[] temp = spisok.split(", ");
   							for (int i = 0; i < temp.length; i++)
   								if (temp[i].indexOf(nameFile) >= 0)
   									kol++;
   						}
   					}
   				} else {
   					row = xls.getCountRow();
   				}
        	}
    		nameFile += kol;
        	if (!copies) {
        		xls.writeLabel(0, row, nameFolder, indexMonth);
            	if (spisok.equals("") || spisok == null)
            		xls.writeLabel(1, row, nameFile, indexMonth);
            	else
            		xls.writeLabel(1, row, spisok + ", " + nameFile, indexMonth);
            	if (IDs.equals("") || IDs == null)
            		xls.writeLabel(2, row, id, indexMonth);
            	else
            		xls.writeLabel(2, row, IDs + ", " + id, indexMonth);
        	}
        } catch (Exception e) {
            System.out.println(e);
        }
        return nameFile;
	}
	
	private void proverka(String nameFolder, String nameFile) {
    	String[] title = {"idR3_ р.наименование", "список файлов (, )", "номера сечтов дл€ повтор€ющихс€ документов (, )"};
    	String spisok = "";
    	int row = 0;
    	String nameXLS = "autoreport_" + YYYYmm.substring(0, 4) + ".xls";
        try {
        	File file = new File(prop.getString("adres.archives") + nameXLS);
    		WorkXLS xls = new WorkXLS(prop.getString("adres.archives") + nameXLS, 3);
        	if (!file.exists()) { // если файл или папка не существует
        		xls.createWorkbookYear(title);
        		row = 1;
        	} else {
        		if (xls.findCell(indexMonth, nameFolder)) {
   					row = xls.getRow();
   					spisok = xls.getContentCell();
   					if (spisok.indexOf(nameFile) >= 0)
						copies = true;
       			} else {
       				row = xls.getCountRow();
       			}
        	}
        	if (!copies) {
        		xls.writeLabel(0, row, nameFolder, indexMonth);
            	if (spisok.equals("") || spisok == null)
            		xls.writeLabel(1, row, nameFile, indexMonth);
            	else
            		xls.writeLabel(1, row, spisok + ", " + nameFile, indexMonth);
        	}
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private String getRegex(String type) {
		String reg = "";
	    	//  запись в формате "inn/kpp"
		if (type.equals("innkpp")) {
			reg = "([^_.Д,!li0-9])";
			for (int i = 0; i < 19; i++) {
				reg += "([,._Д-]{0,4})([QOќoобli!юёз«0-9])";
				if (i == 9)
					reg += "([,._Д-]{0,4})/";
			}
		}
		   //  запись в формате "inn  ѕѕ kpp"
		if (type.equals("innKPPkpp")) {
			reg = "([^_.Д,!li0-9])";
			for (int i = 0; i < 19; i++) {
				reg += "([,._Д-]{0,4})([QOќoобli!юёз«0-9])";
				if (i == 9)
					reg += "([;,._Д-]{0,4})(\\D{3})";
			}
		}
    		//  запись в формате "inn"
		if (type.equals("inn")) {
			reg = "([^_.Д,!li0-9])";
			for (int i = 0; i < 12; i++) {
				reg += "([,._Д]{0,4})([QOќoобli!юёз«0-9])";
			}
			reg += "([,._Д]{0,4})([^.,0-9])";
		}
			//  запись в формате заголовка счет-фактуры (newName 25)
		if (type.equals("factura")) {
			reg = "([li!1])([,._Д-]{0,4})";
			reg += "(2)([,._Д-]{0,4})";
			reg += "(7)([,._Д-]{0,4})";
			reg += "([QOќoо0]{9})([,._Д-]{0,4})";
			reg += "([li!1])([,._Д-]{0,4})";
			reg += "(2)([,._Д-]{0,4})";
			reg += "(3)([,._Д-]{0,4})";
			reg += "(2)([,._Д-]{0,4})/";
		}
			//  запись в формате счета √¬÷ (newName 35)
		if (type.equals("billGVC")) {
			reg = "([^_.Д,!li0-9])";
			reg += "(\\d{1,6})";
			reg += "([^.,0-9]{2,3})";
			reg += "([0-3])";
			reg += "(\\d)";
			reg += "([.,])";
			reg += "(0|1)";
			reg += "(\\d)";
			reg += "([.,])";
			reg += "(2)";
			reg += "(0)";
			reg += "(\\d{2})";
		}
		return reg;
	}
	
	private String ignoreTChK(String str) {
		str = str.replaceAll("Q", "0"); // анг
		str = str.replaceAll("O", "0"); // анг
		str = str.replaceAll("o", "0"); // анг
		str = str.replaceAll("ќ", "0"); // рус
		str = str.replaceAll("о", "0"); // рус
		str = str.replaceAll("б", "6"); // рус
		str = str.replaceAll("l", "1"); // анг
		str = str.replaceAll("i", "1"); // анг
		str = str.replaceAll("ю", "10"); // рус
		str = str.replaceAll("ё", "10"); // рус
		str = str.replaceAll("з", "3"); // рус
		str = str.replaceAll("«", "3"); // рус
		str = str.replaceAll("!", "1");
		str = str.replaceAll(",", "");
		str = str.replaceAll("_", "");
		str = str.replaceAll("-", "");
		str = str.replaceAll("Д", "");
		str = str.replaceAll(";", "");
		if (str.indexOf(".") >= 0) {
			for (int i = 0; str.indexOf(".") >= 0; i++) {
				str = str.substring(0, str.indexOf(".")) + str.substring(str.indexOf(".")+1);
			}
		}
		return str;
	}
}
