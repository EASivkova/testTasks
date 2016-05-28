package doc;

import java.io.*;
import java.util.regex.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import jxl.*;

public class Sorter {
	
	private String result = "";
	private String workCatalog;
	private String address_listContractors;
	private String user;
	private String[] listReestr = "".split("");
	private int[] kolSlKonv;
	private String spisokObr = "";
	private int numKonv = 0;
	
	Sorter(String workCatalog, String address_listContractors, String user) {
		this.workCatalog = workCatalog;
		this.address_listContractors = address_listContractors;
		this.user = user;
	}

	public String getResult() {
		return result;
	}

	public void sortDocumentNonLetter() {
        BufferedWriter bw;
        String newSTR = "";
        int newNumkonv = 0;
        String otdStopka = ";;";
        int numOtd = 0;
        String[] title = {"id R3", "Наименование предприятия"};
        File folderReestr = new File(workCatalog + "Реестр" + File.separator);
       	if (folderReestr.isDirectory())
       		listReestr = folderReestr.list();
       	String spisokKonv = "";
		if (address_listContractors.indexOf(".xml") > -1)
			spisokKonv = getSpisokLetter_XML();
		else
			spisokKonv = getSpisokLetter();
        String[] konv = spisokKonv.split(";;");
        try {
   			int[] kolKonv = new int[konv.length]; // кол-во документов, которое должно быть положено в каждый из конвертов
   			countDocInLetter(konv, kolKonv);
			kolSlKonv = new int[konv.length]; // кол-во документов, которое было положено в каждый из конвертов
   			bw = new BufferedWriter(new FileWriter(workCatalog + user + "список для сортировки документов.txt",true));
           	WorkXLS xls = new WorkXLS(workCatalog + user + "стопки документов.xls", 2);
           	xls.createWBlistDocuments(title);
			int rowZ = 1, rowB = 1, rowL = 1, rowE = 1, rowK = 1;
            for (int k = 0; k < listReestr.length; k++) 
            	if (listReestr[k].indexOf(user + "реестр обр.файлов") > -1) {
                	Workbook wbReestr = Workbook.getWorkbook(new File(workCatalog + "Реестр" + File.separator + listReestr[k]));
                	Sheet shReestr = wbReestr.getSheet(0);
           			for (int q = 1; q < shReestr.getRows(); q++) {
           				Pattern p = Pattern.compile(shReestr.getCell(3, q).getContents() + " - " + shReestr.getCell(4, q).getContents());
           				Matcher m = p.matcher(spisokObr);
           				int kol = 0;
           				while (m.find())
           					kol++;
           		        String strSpiska = getStrSpiska(shReestr.getCell(1, q).getContents(), shReestr.getCell(2, q).getContents(), shReestr.getCell(3, q).getContents(), shReestr.getCell(4, q).getContents(), kol, kolKonv, newNumkonv, konv);
           				// разбор строки с номерами конвертов
           				if (strSpiska.indexOf("конверт") > -1) {
           					if (otdStopka.indexOf(";;" + numKonv + ";;") < 0) {
           						if (strSpiska.indexOf("закрыть конверт") > -1) {
               						newSTR = "К      -> З";
               						newNumkonv++;
           						} else {
               						newSTR = "К      -> О";
               						otdStopka += numKonv + ";;";
           						}
           						newSTR += " (" + strSpiska.substring(strSpiska.indexOf("(")+1, strSpiska.indexOf(")")) + ")";
           					} else {
           						newSTR = "О ";
           						String[] t = otdStopka.split(";;");
           						for (int x = 1; x < t.length; x++)
           							if (t[x].equals("" + numKonv)) {
           								numOtd = t.length - x;
           							}
           						if (numOtd > 1) {
           							if (numOtd < 10)
           								newSTR += "(0";
           							else
           								newSTR += "(";
           							newSTR += numOtd + ")";
           						} else
           							newSTR += "    ";
           						if (strSpiska.indexOf("закрыть конверт") > -1) {
               						newSTR += " -> З";
               						otdStopka = otdStopka.replaceAll(";;" + numKonv + ";;", ";;");
           						} else {
               						newSTR += " -> О";
               						otdStopka = otdStopka.replaceAll(";;" + numKonv + ";;", ";;");
               						otdStopka += numKonv + ";;";
           						}
           						newSTR += " (" + strSpiska.substring(strSpiska.indexOf("(")+1, strSpiska.indexOf(")")) + ")";
           					}
           				} else {
           					newSTR = strSpiska;
           				}
           				bw.write(newSTR);
           				bw.newLine();
           				if (newSTR.indexOf(" -> З") >= 0) {
           					xls.writeLabel(0, rowZ, shReestr.getCell(2, q).getContents(), 0);
           					xls.writeLabel(1, rowZ, shReestr.getCell(3, q).getContents(), 0);
           					rowZ++;
           				}
               			if (newSTR.indexOf(" -> Б") >= 0) {
               				xls.writeLabel(0, rowB, shReestr.getCell(2, q).getContents(), 1);
               				xls.writeLabel(1, rowB, shReestr.getCell(3, q).getContents(), 1);
               				rowB++;
               			}
               			if (newSTR.indexOf(" -> Л") >= 0) {
               				xls.writeLabel(0, rowL, shReestr.getCell(2, q).getContents(), 2);
               				xls.writeLabel(1, rowL, shReestr.getCell(3, q).getContents(), 2);
               				rowL++;
               			}
               			if (newSTR.indexOf(" -> Э") >= 0) {
               				xls.writeLabel(0, rowE, shReestr.getCell(2, q).getContents(), 3);
               				xls.writeLabel(1, rowE, shReestr.getCell(3, q).getContents(), 3);
               				rowE++;
               			}
               			if (newSTR.indexOf(" -> Ф") >= 0) {
               				xls.writeLabel(0, rowK, shReestr.getCell(2, q).getContents(), 4);
               				xls.writeLabel(1, rowK, shReestr.getCell(3, q).getContents(), 4);
               				rowK++;
               			}
           			}
           			wbReestr.close();
           		}
            bw.close();
           	result = "Файл для сортировки документов создан. ";
        } catch (Exception e) {
            System.out.println(e);
           	result = "Ошибка создания Файла для сортировки документов. ";
        }
	}
	
	private String getSpisokLetter() {
		String spisokKonv = "0;;";
        try { // составление списка предприятий, содержащихся в списке конвертов
        	Workbook BD = Workbook.getWorkbook(new File(address_listContractors)); 
        	Sheet sheetBD = BD.getSheet(0); 
           	for (int k = 0; k < listReestr.length; k++) 
           		if (listReestr[k].indexOf(user + "реестр обр.файлов") > -1) {
           			Workbook workbookR = Workbook.getWorkbook(new File(workCatalog + "Реестр" + File.separator + listReestr[k]));
           			Sheet sheetR = workbookR.getSheet(0); 
           			for (int j = 1; j < sheetR.getRows(); j++)
           				if (sheetR.getCell(1, j).getContents().indexOf("Ручная обработка") < 0 && spisokKonv.indexOf(sheetR.getCell(2, j).getContents().trim()) < 0) {
           					for (int q = 2; q < sheetBD.getRows(); q++)
           						if (sheetBD.getCell(5, q).getContents().trim().equals(sheetR.getCell(2, j).getContents()) && !sheetR.getCell(2, j).getContents().trim().equals("") && sheetBD.getCell(17, q).getContents().indexOf("почта") > -1 && !sheetBD.getCell(9, q).getContents().trim().equals("") && !sheetBD.getCell(10, q).getContents().trim().equals("") && !(sheetBD.getCell(21, q).getContents().trim() + " " + sheetBD.getCell(11, q).getContents().trim()).equals(" "))
           							spisokKonv += sheetR.getCell(2, j).getContents().trim() + ";;";
           				}
           			workbookR.close();
           		}
           	BD.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return spisokKonv;
	}
	
	private String getSpisokLetter_XML() {
		String spisokKonv = "0;;";
        try { // составление списка предприятий, содержащихся в списке конвертов
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(address_listContractors));
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("contractor");
           	for (int k = 0; k < listReestr.length; k++) 
           		if (listReestr[k].indexOf(user + "реестр обр.файлов") > -1) {
           			Workbook workbookR = Workbook.getWorkbook(new File(workCatalog + "Реестр" + File.separator + listReestr[k]));
           			Sheet sheetR = workbookR.getSheet(0); 
           			for (int j = 1; j < sheetR.getRows(); j++)
           				if (sheetR.getCell(1, j).getContents().indexOf("Ручная обработка") < 0 && spisokKonv.indexOf(sheetR.getCell(2, j).getContents().trim()) < 0) {
           		            for (int je = 0; je < nodeLst.getLength(); je++) {
           		                Node fstNode = nodeLst.item(je);
           		                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
           		                    Element elj = (Element)fstNode;
               						if (elj.getElementsByTagName("r3").item(0).getChildNodes().item(0).getNodeValue().trim().equals(sheetR.getCell(2, j).getContents()) && !sheetR.getCell(2, j).getContents().trim().equals("") && elj.getElementsByTagName("view_sending").item(0).getChildNodes().item(0).getNodeValue().indexOf("почта") > -1 && elj.getElementsByTagName("index").item(0).getChildNodes().item(0) != null && elj.getElementsByTagName("address").item(0).getChildNodes().item(0) != null && elj.getElementsByTagName("name_for_cover").item(0).getChildNodes().item(0) != null)
               							spisokKonv += sheetR.getCell(2, j).getContents().trim() + ";;";
           		                }
           		            }
           				}
           			workbookR.close();
           		}
        } catch (Exception e) {
            System.out.println(e);
        }
        return spisokKonv;
	}
	
	/* определение кол-ва документов каждом конверте */
	private void countDocInLetter(String[] konv, int[] kolKonv) {
       	for (int j = 1; j < konv.length; j++) {
            String strNameF = "";
            for (int k = 0; k < listReestr.length; k++) {
            	if (listReestr[k].indexOf(user + "реестр обр.файлов") > -1) {
            		try {
            			Workbook workbook = Workbook.getWorkbook(new File(workCatalog + "Реестр" + File.separator + listReestr[k]));
            			Sheet sheet = workbook.getSheet(0);
            			for (int q = 1; q < sheet.getRows(); q++) {
            				if (konv[j].indexOf(sheet.getCell(2, q).getContents().trim()) > -1 && !sheet.getCell(2, q).getContents().trim().equals("")) {
            					int w = strNameF.indexOf(sheet.getCell(4, q).getContents());
            					int d = sheet.getCell(4, q).getContents().length() + 1;
            					if (w > -1) {
            						int kol = Integer.parseInt(strNameF.substring(w + d, w + d + 1)) + 1;
            						strNameF = strNameF.substring(0, w + d) + kol + strNameF.substring(w + d + 1);
            					} else
            						if (strNameF.equals("") || strNameF == null)
            							strNameF = sheet.getCell(4, q).getContents() + ":" + 1;
            						else
            							strNameF += ";" + sheet.getCell(4, q).getContents() + ":" + 1;
            				}
            			}
            			workbook.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
            	}
            }
           	String[] temp = strNameF.split(";");
           	kolKonv[j] = 0;
           	for (int q = 0; q < temp.length; q++)
           		switch (Integer.parseInt(temp[q].substring(0, 1))) {
           		case 1:
           			if (Integer.parseInt(temp[q].substring(temp[q].indexOf(":")+1)) >= 3)
           				kolKonv[j] += 2;
           			else
           				if (Integer.parseInt(temp[q].substring(temp[q].indexOf(":")+1)) == 2)
           					kolKonv[j]++;
           			break;
           		case 2:
           			if (Integer.parseInt(temp[q].substring(temp[q].indexOf(":")+1)) >= 3)
           				kolKonv[j]++;
           			break;
           		case 3:
           			if (Integer.parseInt(temp[q].substring(temp[q].indexOf(":")+1)) >= 1)
           				kolKonv[j]++;
           			break;
           		}
       	}
	}
	
	private String getStrSpiska(String cell_1q, String cell_2q, String cell_3q, String cell_4q, int kol, int[] kolKonv, int newNumkonv, String[] konv) {
		numKonv = 0;
        String str = "";
		String strSpiska = "";
		if (cell_1q.indexOf("Ручная обработка") > -1) {
			strSpiska = "       -> ? (неизвестный тип документа)";
		} else {
			if (cell_2q.trim().equals("")) {
				strSpiska = "       -> ";
				if (cell_3q.trim().equals("")) {
					strSpiska += "? (";
					switch (Integer.parseInt(cell_4q.substring(0, 1))) {
					case 1: strSpiska += "А"; break;
					case 2: strSpiska += "СФ"; break;
					case 3: strSpiska += "СП"; break;
					}
					strSpiska += " - предприятие не определено)";
				} else {
       				if (cell_4q.substring(0, 1).equals("1")) {
       					switch (kol) {
       					case 0: strSpiska += "Б (А - "; break;
       					case 1: case 2: strSpiska += "Ф (А - "; break;
       					case 3: strSpiska += "Э (А - "; break;
       					default: strSpiska = "Л (А - "; break;
       					}
       					spisokObr += cell_3q + " - " + cell_4q;
       				}
       				if (cell_4q.substring(0, 1).equals("2")) {
       					switch (kol) {
       					case 0: case 1: strSpiska += "Б (СФ - "; break;
       					case 2: strSpiska += "Ф (СФ - "; break;
       					case 3: strSpiska += "Э (СФ - "; break;
       					default: strSpiska += "Л (СФ - "; break;
       					}
       					spisokObr += cell_3q + " - " + cell_4q;
       				}
       				if (cell_4q.substring(0, 1).equals("3")) {
       					switch (kol) {
       					case 0: strSpiska += "Ф (СП - "; break;
       					case 1: strSpiska += "Э (СП - "; break;
       					default: strSpiska += "Л (СП - "; break;
       					}
       					spisokObr += cell_3q + " - " + cell_4q;
       				}
   					strSpiska += cell_3q + ")";
				}
			} else {
				for (int i = 1; i < konv.length; i++) {
					if (konv[i].indexOf(cell_2q.trim()) > -1 && !cell_2q.trim().equals(""))
						numKonv = i;
				}
				if (numKonv != 0)
					str = "конверт " + (numKonv - newNumkonv);
				else
					str = "       -> Ф";
				if (cell_4q.substring(0, 1).equals("1")) {
					switch (kol) {
					case 0: strSpiska = "       -> Б (А - " + cell_3q + ")"; break;
					case 1: case 2: 
						strSpiska = str + " (А - " + cell_3q + ")";
						if (numKonv != 0)
							kolSlKonv[numKonv]++;
						break;
					case 3: strSpiska = "       -> Э (А - " + cell_3q + ")"; break;
					default: strSpiska = "       -> Л (А - " + cell_3q + ")"; break;
					}
					spisokObr += cell_3q + " - " + cell_4q;
				}
				if (cell_4q.substring(0, 1).equals("2")) {
					switch (kol) {
					case 0: case 1: strSpiska = "       -> Б (СФ - " + cell_3q + ")"; break;
					case 2:
						strSpiska = str + " (СФ - " + cell_3q + ")";
						if (numKonv != 0)
							kolSlKonv[numKonv]++;
						break;
					case 3: strSpiska = "       -> Э (СФ - " + cell_3q + ")"; break;
					default: strSpiska = "       -> Л (СФ - " + cell_3q + ")"; break;
					}
					spisokObr += cell_3q + " - " + cell_4q;
				}
				if (cell_4q.substring(0, 1).equals("3")) {
					switch (kol) {
					case 0:
						strSpiska = str + " (СП - " + cell_3q + ")";
						if (numKonv != 0)
							kolSlKonv[numKonv]++;
						break;
					case 1: strSpiska = "       -> Э (СП - " + cell_3q + ")"; break;
					default: strSpiska = "       -> Л (СП - " + cell_3q + ")"; break;
					}
					spisokObr += cell_3q + " - " + cell_4q;
				}
				if (kolSlKonv[numKonv] == kolKonv[numKonv] && numKonv != 0) {
					strSpiska += " закрыть конверт " + numKonv;
					kolSlKonv[numKonv] = 50;
				}
			}
		}
		return strSpiska;
	}
}
