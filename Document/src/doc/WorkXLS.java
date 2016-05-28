package doc;

import java.io.File;
import java.util.Locale;

import jxl.*;
import jxl.write.*;
import jxl.write.Number;

public class WorkXLS {
	
	private String contentCell = "";
	private int countRow = 0;
	private String[] contentRow;
	private int row = 0;
	private String adres;
	
	WorkXLS(String adres, int countColumn) {
		this.adres = adres;
		contentRow = new String[countColumn];
	}

	public String getContentCell() {
		return contentCell;
	}

	public int getRow() {
		return row;
	}

	public int getCountRow() {
		return countRow;
	}

	public String[] getContentRow() {
		return contentRow;
	}

	/* создание книги дл€ архива (лист = мес€ц)*/
	public void createWorkbookYear(String[] title) {
    	WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbookWr; 
    	WritableSheet sheetWr; 
        Label lab;
		try {
			workbookWr = Workbook.createWorkbook(new File(adres), wbSettings);
			for (int i = 0; i < 12; i++) {
				sheetWr = workbookWr.createSheet(DocumentStart.MONTHS[i], i); 
				for (int j = 0; j < title.length; j++) {
					lab = new Label(j, 0, title[j]);
					sheetWr.addCell(lab);
				}
			}
			workbookWr.write();
			workbookWr.close();
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	
	public void createWorkbook(String[] title) {
    	WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbookWr; 
    	WritableSheet sheetWr; 
        Label lab;
		try {
			workbookWr = Workbook.createWorkbook(new File(adres), wbSettings);
			sheetWr = workbookWr.createSheet("Ћист 1", 0); 
			for (int j = 0; j < title.length; j++) {
				lab = new Label(j, 0, title[j]);
				sheetWr.addCell(lab);
			}
			workbookWr.write();
			workbookWr.close();
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	
	public void createWBlistDocuments(String[] title) {
		String[] nameSheet = {"«апечатанные", "Ёкономисты", "Ѕухгалтери€", " лиенты (факс, в руки)", "Ћишние копии"};
    	WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
        Label lab;
		try {
			WritableWorkbook wbWr = Workbook.createWorkbook(new File(adres), wbSettings); 
			WritableSheet sheetWr;
			for (int j = 0; j < nameSheet.length; j++) {
				sheetWr = wbWr.createSheet(nameSheet[j], j); 
				for (int i = 0; i < title.length; i++) {
					lab = new Label(i, 0, title[i]);
					sheetWr.addCell(lab);
				}
			}
			wbWr.write();
			wbWr.close();
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	
	public void writeLabel(int col, int row, String text, int indexSheet) {
        Label lab;
    	WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbookWr; 
    	WritableSheet sheetWr; 
        try {
       		workbookWr = Workbook.createWorkbook(new File(adres), Workbook.getWorkbook(new File(adres)), wbSettings); 
           	sheetWr = workbookWr.getSheet(indexSheet); 
           	lab = new Label(col, row, text);
           	sheetWr.addCell(lab);
         	workbookWr.write();
    		workbookWr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	WorkbookSettings wbSettings = new WorkbookSettings();
	WritableWorkbook workbookWr; 
	WritableSheet sheetWr; 
	public void open(int indexSheet) {
		wbSettings.setLocale(new Locale("en", "EN"));
        try {
       		workbookWr = Workbook.createWorkbook(new File(adres), Workbook.getWorkbook(new File(adres)), wbSettings); 
           	sheetWr = workbookWr.getSheet(indexSheet); 
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public void writeLabel(int col, int row, String text) {
        Label lab;
        try {
           	lab = new Label(col, row, text);
           	sheetWr.addCell(lab);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public void close() {
        try {
         	workbookWr.write();
    		workbookWr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public void writeNumber(int col, int row, int number, int indexSheet) {
        Number num;
    	WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));
		WritableWorkbook workbookWr; 
    	WritableSheet sheetWr; 
        try {
       		workbookWr = Workbook.createWorkbook(new File(adres), Workbook.getWorkbook(new File(adres)), wbSettings); 
           	sheetWr = workbookWr.getSheet(indexSheet); 
           	num = new Number(col, row, number);
           	sheetWr.addCell(num);
         	workbookWr.write();
    		workbookWr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public void writeNumber(int col, int row, int number) {
        Number num;
        try {
           	num = new Number(col, row, number);
           	sheetWr.addCell(num);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public void writeNumber(int col, int row, double number) {
        Number num;
        try {
           	num = new Number(col, row, number);
           	sheetWr.addCell(num);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	public boolean findCell(int indexSheet, String phraze) {
		row = 0;
		countRow = 0;
		contentCell = "";
        try {
           	Workbook workbook = Workbook.getWorkbook(new File(adres)); 
           	Sheet sheet = workbook.getSheet(indexSheet); 
           	countRow = sheet.getRows();
   			for (int j = 1; j < countRow; j++) {
   				if (sheet.getCell(0, j).getContents().equals(phraze)) {
   					row = j;
   					contentCell = sheet.getCell(1, j).getContents();
   				}
   			}
          	workbook.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        if (!contentCell.equals("") && contentCell != null)
        	return true;
        else
        	return false;
	}
	
	public boolean findRow_inAutoreport(int indexSheet, String phraze, int column) {
		row = 0;
		countRow = 0;
		boolean res = false;
        try {
           	Workbook workbook = Workbook.getWorkbook(new File(adres)); 
           	Sheet sheet = workbook.getSheet(indexSheet); 
           	countRow = sheet.getRows();
   			for (int j = 1; j < countRow; j++) {
   				if (sheet.getCell(column, j).getContents().equals(phraze)) {
   					row = j;
   					for (int i = 0; i < sheet.getColumns(); i++)
   						contentRow[i] = sheet.getCell(i, j).getContents();
   					res = true;
   				}
   			}
          	workbook.close();
        } catch (Exception e) {
            System.out.println(e);
        }
       	return res;
	}
	
	public void parametrsFile(int indexSheet) {
		countRow = 0;
        try {
           	Workbook workbook = Workbook.getWorkbook(new File(adres)); 
           	Sheet sheet = workbook.getSheet(indexSheet); 
           	countRow = sheet.getRows();
          	workbook.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
}
