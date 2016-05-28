/*
 * ParserExpress.java. Created on 27.05.2009 13:39:00
 *
 * Copyright 2006 SAV Soft, Inc. All rights reserved.
 */

/**
 * @author Aleksey Shubin (AShubin@r66.ru)
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;




public class ParserExpress3
{
    static BufferedReader br;
    static BufferedWriter bw;
    static Vector base;
    static PrintWriter out;
    static boolean error = false;
    static Hashtable writeFiles;
    static Data data = new Data();

    public static class Data
    {
        final int NAME = 0;
        final int SUBDIR = 1;
        final int BW = 2;
        final int MONEY = 3;
        final int DOP = 4;
        final int COUNT_ROW = 5;
        final int FULL_NAME_FILE = 6;
        Hashtable data = new Hashtable();
        Hashtable subdir = new Hashtable();
        
//        Hashtable record;  // 0 - name, 1 - bw, 2 - money, 3 - dop        
        
        public void addData(String nameFile, String dir)
        {
            
        }
        
//        private getBwInt()

        public void writeTitle(String title, String dir, String subDir, String nameFile, String pofix)
        {
            BufferedWriter bw;        
            String fullNameFile = dir + "\\" + subDir + "\\" + nameFile + pofix + ".xls";
            try
            {            
                bw = new BufferedWriter(new FileWriter(fullNameFile));
                bw.write(title);
                bw.newLine();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }

        public void write(String str, String dir, String subDir, String nameFile, String pofix)
        {
            BufferedWriter bw;        
            String fullNameFile = dir + "\\" + subDir + "\\" + nameFile + pofix + ".xls";
            try
            {            
                bw = new BufferedWriter(new FileWriter(fullNameFile, true));
                bw.write(str);
                bw.newLine();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        
        public BufferedWriter getBw(String title, String dir, String subDir, String nameFile, String pofix)
        {
            BufferedWriter bw;        
            String fullNameFile = dir + "\\" + subDir + "\\" + nameFile + pofix + ".xls";
            
            try
            {            
                nameFile = nameFile + pofix;
                Hashtable record = (Hashtable)data.get(nameFile);
                if (record == null)
                {                    
                    bw = new BufferedWriter(new FileWriter(fullNameFile));
//                    bw = null;
                    
                    bw.append(title);
                    bw.newLine();
                    
                    Vector r = new Vector();
                    r.add(NAME, nameFile);
                    r.add(SUBDIR, subDir);                    
//                    	Hashtable rBW = new Hashtable();
//                    	rBW.put(pofix, bw);
//                    	r.add(BW, rBW);
                    
                    r.add(BW, bw);
                    r.add(BW, bw);
                    
                    r.add(MONEY, new Double(0));
                    r.add(DOP, new Double(0));
                    r.add(COUNT_ROW, new Integer(0));
                    r.add(FULL_NAME_FILE, fullNameFile);
                    
                    record = new Hashtable();
                    record.put(subDir, r);
                    
                    data.put(nameFile, record);
                    Object o = subdir.get(subdir);
                    if (o == null)
                        subdir.put(subdir, subdir);
                }
                else
                {
                    Vector r = (Vector)record.get(subDir);
//                    Hashtable rBW = (Hashtable)r.get(BW);
//                    if (rBW.get(pofix) == null)
//                    {
//                        bw = new BufferedWriter(new FileWriter(fullNameFile));
//                        bw.append(title);
//                        bw.newLine();
//                        
//                        rBW.put(pofix, bw);
//                    }
//                    else bw = (BufferedWriter)rBW.get(pofix);
                    
                    
//                    bw = (BufferedWriter)r.get(BW);
//                    if (bw == null)
                        bw = new BufferedWriter(new FileWriter(fullNameFile, true));
                }
            }
            catch (Exception e)
            {
                System.out.println("Error open getBw: " + fullNameFile);
                return null;
            }
            return bw;          
        }        
        
        public void addMoney(String nameFile, String subDir, String mon)
        {
            try
            {            
                Hashtable record = (Hashtable)data.get(nameFile);
                if (record != null)
                {                    
                    Vector r = (Vector)record.get(subDir);                    
                    Double monNew = (Double)r.get(MONEY);
                    r.set(MONEY, new Double(monNew.doubleValue() + new Double(mon).doubleValue()));
                }
            }
            catch (Exception e)
            {
                System.out.println("Error addMoney");
            }
        }

        public void addDop(String nameFile, String subDir, String dop)
        {
            try
            {            
                Hashtable record = (Hashtable)data.get(nameFile);
                if (record != null)
                {                    
                    Vector r = (Vector)record.get(subDir);                    
                    Double dopNew = (Double)r.get(DOP);
                    r.set(DOP, new Double(dopNew.doubleValue() + new Double(dop).doubleValue()));
                }
            }
            catch (Exception e)
            {
                System.out.println("Error addMoney");
            }
        }

        public void addCountRow(String nameFile, String subDir)
        {
            try
            {            
                Hashtable record = (Hashtable)data.get(nameFile);
                if (record != null)
                {                    
                    Vector r = (Vector)record.get(subDir);                    
                    Integer countRow = (Integer)r.get(COUNT_ROW);
                    r.set(COUNT_ROW, new Integer(countRow.intValue() + new Integer(1).intValue()));
                }
            }
            catch (Exception e)
            {
                System.out.println("Error addCountRow\n" + e);
            }
        }
        
        public Enumeration elements()
        {
            return data.elements();
        }
    }
    
    private static boolean chekError()
    {
        if (error)
        {
            try
            {
                if (br != null) br.close();
                br = null;
            }
            catch (IOException ioe) {}
            try
            {
                if (bw != null) bw.close();
                bw = null;
            }
            catch (IOException ioe) {}
            try
            {
                if (out != null) out.close();
                out = null;
            }
            catch (Exception e) {}
        }
        return error;
    }
    
    private static BufferedReader openFile(String nameFile)
    {
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(nameFile));
        }
        catch (Exception e)
        {
            System.out.println("Error open file: " + nameFile);
            error = true;
            return null;
        }        
        return br;
    }
    
    private static BufferedWriter getBw(String title, String dir, String nameFile, String pofix)
    {
        BufferedWriter bw = null;        
//        String fullNameFile = dir + "\\" + nameFile + pofix + ".xls";
//        
//        try
//        {            
//            bw = (BufferedWriter)data.getBw(title, dir);
//            if (bw == null)
//            {
//                bw = new BufferedWriter(new FileWriter(fullNameFile));
//                writeFiles.put(fullNameFile, bw);
//                
//                bw.append(title);
//                bw.newLine();
//            }
//        }
//        catch (Exception e)
//        {
//            System.out.println("Error open getBw: " + fullNameFile);
//            return null;
//        }
        return bw;
    }
    
    private static int sum(Object o, int i)
    {
        Integer ii = (Integer)o;
        return ii.intValue() + i;
    }
    
    private static void closeAll()
    {
        BufferedWriter bw; 
        try
        {  
            for (Enumeration e = data.elements(); e.hasMoreElements();)
            {
                Hashtable record = (Hashtable)e.nextElement();
                for (Enumeration ee = record.elements(); ee.hasMoreElements();)
                {
                    Vector r = (Vector)ee.nextElement();
//                    Hashtable rBW = (Hashtable)r.get(data.BW);
//                    for (Enumeration eee = rBW.elements(); eee.hasMoreElements();)
//                    {
//                        
//                        bw = (BufferedWriter)eee.nextElement();
//                        if (bw != null) 
//                        {
//                            System.out.print("Close: ");
//                            bw.close();
//                            System.out.println(r.get(data.NAME) + " " + r.get(data.SUBDIR));
//                        }
//                    }
//                  bw = (BufferedWriter)r.get(data.BW);  
                  bw = new BufferedWriter(new FileWriter((String)r.get(data.FULL_NAME_FILE), true));
                  if (bw != null) 
                  {                      
                      String tmp = (String)r.get(data.NAME);
                      if (tmp.indexOf("6000") > -1)
                      {
                          bw.write("<tr><td colspan=\"16\"><b>Итого (по формуле Excel):</b></td><td><b>=СУММ(Q3:Q" + sum(r.get(data.COUNT_ROW), 2) + ")</b></td></tr>");
                          bw.write("<tr><td colspan=\"16\"><b>Итого (программно):</b></td><td><b>" + r.get(data.MONEY) + "</b></td></tr>");
                      }
                      else
                      {
                          bw.write("<tr><td colspan=\"13\"><b>Итого (по формуле Excel):</b></td><td><b>=СУММ(N3:N" + sum(r.get(data.COUNT_ROW), 2) + ")</b></td>");
                          bw.write("<td><b>=СУММ(O3:O" + sum(r.get(data.COUNT_ROW), 2) + ")</b></td></tr>");
                          bw.write("<tr><td colspan=\"13\"><b>Итого (программно):</b></td><td><b>" + r.get(data.MONEY) + "</b></td><td><b>" + r.get(data.DOP) + "</b></td></tr>");
                      }
                      
                      System.out.print("Close: ");
                      bw.close();
                      System.out.println(r.get(data.NAME) + " " + r.get(data.SUBDIR));
                  }                  
               }                
            }
        }
        catch (Exception e)
        {            
            System.out.println("Error closeAll()\n" + e);            
        }        
    }
    
    
    private static void parse(String title, BufferedReader br, String dir, String subDir, String pofix, int numColFileName, 
            				  int countColumns, int fio, boolean oneFio, String date,
            				  String money, String station)
    {
        String[] strArr = "".split("");
        String strTmp = "";
        BufferedWriter bw = null;
        
        double mon, dop;
        mon = 0; 
        dop = 0;
        
        try
        {
            int j = 0;            
            while ((strTmp = br.readLine()) != null)
            {
                if (strTmp.indexOf(" --") > -1)
                    j++;
                if (j > 1) break;
            }            
            
            boolean first = true;
            String[] dateArr = date.split(",");
            String[] moneyArr = money.split(",");
            String[] stationArr = station.split(",");
            StringBuffer sb = new StringBuffer(); 
            int numStrCell = 0;
            if (br != null)                
            while ((strTmp = br.readLine()) != null)
            {
                numStrCell++;
                if (strTmp.indexOf("--") > -1)
                {
                    numStrCell = 0;
                    if (strArr.length == countColumns + 1)
                    {
                        bw = data.getBw(title, dir, subDir, strArr[numColFileName], pofix);
                        bw.write("<tr>");
//                        sb.append("<tr>");
                        for (int i = 1; i < strArr.length; i++)
                        {
                            if (strArr[i].length() == -1 || strArr[i].trim().equals(""))
                                strArr[i] = "&nbsp;";
                            if (!oneFio && i == fio)
                            {
//                                System.out.println(strArr[i]);
                                strArr[i] = strArr[i].substring(0, strArr[i].lastIndexOf(" ")) +
                                	        strArr[i].substring(strArr[i].lastIndexOf(" "), strArr[i].lastIndexOf(" ") + 3) +
                                	        "<br>" + strArr[i].substring(strArr[i].lastIndexOf(" ") + 3, strArr[i].length() - 2) +
                                	        " " + strArr[i].substring(strArr[i].length() - 2);
//                                System.out.println(strArr[i]);
                            }
                            if (i == 6) bw.write("<td class=\"number\">" + strArr[i] + "</td>"); 
                            else bw.write("<td>" + strArr[i] + "</td>");
//                            sb.append("<td>" + strArr[i] + "</td>");
                        }                        
                        bw.write("</tr>");
                        bw.newLine();
//                        sb.append("</tr>\n");
                        bw.close();
                        data.addCountRow(strArr[numColFileName] + pofix, subDir);
                        if (!oneFio) data.addCountRow(strArr[numColFileName] + pofix, subDir);
                    }
                    first = true;
                    continue;
                }
                if (first)
                {
                    first = false;
                    strArr = strTmp.split("!");
                    if (strArr.length == countColumns + 1)
                    {
                        bw = data.getBw(title, dir, subDir, strArr[numColFileName], pofix);
                        bw.close();
                    }

                }
                else
                {
                    String[] tmp = strTmp.split("!");
                    for (int i = 0; i < tmp.length; i++)
                    {
                        if (stationArr != null && numStrCell == 2)
                            for (int k = 0; k < stationArr.length; k++)
                                if (stationArr[k].trim().equals(new Integer(i).toString()))
                                    strArr[i] = strArr[i] + "</td><td>";
//                        if ((i == 13) || (i == 14)) strArr[i] = strArr[i] + "</td><td>";
                        strArr[i] = strArr[i] + tmp[i].trim();
//                      Для правильного представления даты: 08-01-09  (ДД-ММ-ГГ)                         
                        if (dateArr != null && numStrCell == 2)
                            for (int k = 0; k < dateArr.length; k++)
                                if (dateArr[k].trim().equals(new Integer(i).toString()))
                                    if (strArr[i].length() > 5)
                                        strArr[i] = strArr[i].substring(0,2) + "-" +
                                        			strArr[i].substring(2,4) + "-" +
                                        			strArr[i].substring(4);
                        if (moneyArr != null && numStrCell == 2)
                            for (int k = 0; k < moneyArr.length; k++)
                                if (moneyArr[k].trim().equals(new Integer(i).toString()))
                                {
                                    if (i == 15 || i == 12)
                                    {
//                                        mon = mon + new Double(strArr[i]).doubleValue();
                                        data.addMoney(strArr[numColFileName] + pofix, subDir, strArr[i]);                                        
                                    }
                                    else
                                    {
                                        data.addDop(strArr[numColFileName] + pofix, subDir, strArr[i]);
                                    }
                                    
                                    if (strArr[i].indexOf(".") > -1)
                                        strArr[i] = strArr[i].substring(0, strArr[i].indexOf(".")) + 
                                        		    "," +
                                        		    strArr[i].substring(strArr[i].indexOf(".") + 1);
                                }
                    }                                    
                }
            }
//            if (bw != null)
//                bw.write("<tr><td>" + mon + "</td><td>" + dop + "</td></tr>");
        }
        catch (IOException ioe) 
        {
            error = true;
            System.out.println("Error read or write file\n" + ioe);
        }        
        return;        
    }    
    
    private static void finanse(String title, String dir, String subDir, String nameFile, String pofix, int numColFileName, 
			  				    int countColumns, int fio, boolean oneFio, String date,
			  				    String money, String station)
    {
        BufferedReader br = openFile(dir + "\\" + nameFile);
        if (br == null) return;
        
        parse(title, br, dir, subDir, pofix, numColFileName, countColumns, fio, oneFio, date, money, station);
    }    

    public static void main(String[] args)
    {
        String strTmp = "";
        String[] strArr;        
        String nameFileWrite = "";
        String nameFileRead = "";
        
        writeFiles = new Hashtable();
        
        String title6000 = "<html>\n" + 
        			   "   <style>.number {mso-number-format:\"0\";}</style><body>\n" + 
        			   "       <table border=\"1\">\n" + 
        			   "<tr><th rowspan=\"2\">Наименование перевозчика</th>" +
        			   "<th rowspan=\"2\">Форма трансп тр.</th>" + 
        			   "<th rowspan=\"2\">Код орг-ции, в штате которой состоит работник</th>" +
        			   "<th rowspan=\"2\">Код подразделения</th>" +
        			   "<th rowspan=\"2\">Фамилия, инициалы пассажира</th>" + 
        			   "<th rowspan=\"2\">Номер трансп. стребования</th>" + 
        			   "<th rowspan=\"2\">Дата оформления ППД</th>" + 
        			   "<th rowspan=\"2\">Дата начала действия стребования</th>" + 
        			   "<th rowspan=\"2\">Номер оформленного бланка</th>" + 
        			   "<th rowspan=\"2\">Способ оформления</th>" + 
        			   "<th rowspan=\"2\">Вид документа</th>" + 
        			   "<th rowspan=\"2\">Количество документов</th>" + 
        			   "<th colspan=\"4\">Станция (зона)</th>" + 
        			   "<th rowspan=\"2\">Стоимость</th>" +
        			   "</tr><th colspan=\"2\">Отправления</th><th colspan=\"2\">Назначения</th></tr>";
        
        String title7000 = "<html>\n" + 
		   "   <style>.number {mso-number-format:\"0\";}</style><body>\n" + 
		   "       <table border=\"1\">\n" + 
		   "<tr><th rowspan=\"2\">Наименование перевозчика</th>" +
		   "<th rowspan=\"2\">Форма трансп тр.</th>" + 
		   "<th rowspan=\"2\">Код орг-ции, в штате которой состоит работник</th>" +
		   "<th rowspan=\"2\">Код подразделения</th>" +
		   "<th rowspan=\"2\">Фамилия, инициалы пассажира, Фамилия инициалы работника, на чьем иждивении</th>" + 
		   "<th rowspan=\"2\">Номер трансп. стребования</th>" + 
		   "<th rowspan=\"2\">Дата оформления ППД</th>" + 
		   "<th rowspan=\"2\">Номер оформленного бланка</th>" + 
		   "<th rowspan=\"2\">Количество билетов АСУ ЭКСПРЕСС</th>" +		   
		   "<th colspan=\"4\">Станция (зона)</th>" + 
		   "<th rowspan=\"2\">Стоимость</th>" +
		   "<th rowspan=\"2\">Доплата (сумма пдоплачивается пассажиром)</th>" +
		   "</tr><th colspan=\"2\">Отправления</th><th colspan=\"2\">Назначения</th></tr>";        

//      dir, subDir, nameFile, pofix, numColFileName, countColums, fio, one fio,
//      date,              money,         , station        
        
        String dir = "C:\\Work\\Express";
        
//        finanse(title6000, dir, "finans", "FSLP.txt", "_6000", 3,  15,          5,   true,
//                new String("7,8"), new String("15"), new String("13,14"));
//        closeAll();
//        finanse(title7000, dir, "finans", "FSLSP.txt", "_7000", 3,  13,          5,   true,
//                new String("7"), new String("12,13"), new String("10,11"));
//        closeAll();
//        finanse(title6000, dir, "igdiven", "ILNP.txt", "_6000", 3,  15,          5,   false,
//             new String("7,8"), new String("15"), new String("13,14"));
//        closeAll();
//        finanse(title7000, dir, "igdiven", "ILNSP.txt", "_7000", 3,  13,          5,   true,
//                new String("7"), new String("12,13"), new String("10,11"));
//        closeAll();
//        finanse(title6000, dir, "pension", "PLNP.txt", "_6000", 3,  15,          5,   true,
//                new String("7,8"), new String("15"), new String("13,14"));
//        closeAll();
//        finanse(title7000, dir, "pension", "PLNSP.txt", "_7000", 3,  13,          5,   true,
//                   new String("7"), new String("12,13"), new String("10,11"));
//        closeAll();
//        finanse(title6000, dir, "rasoviy", "RLNP.txt", "_6000", 3,  15,          5,   true,
//                new String("7,8"), new String("15"), new String("13,14"));
//        closeAll();
//        finanse(title7000, dir, "rasoviy", "RLNSP.txt", "_7000", 3,  13,          5,   true,
//                   new String("7"), new String("12,13"), new String("10,11"));
//        closeAll();
//        finanse(title6000, dir, "rabota", "RRUP.txt", "_6000", 3,  15,          5,   true,
//                new String("7,8"), new String("15"), new String("13,14"));
//        closeAll();
//        finanse(title7000, dir, "rabota", "RRUSP.txt", "_7000", 3,  13,          5,   true,
//                   new String("7"), new String("12,13"), new String("10,11"));
//        closeAll();
//        finanse(title6000, dir, "slygba", "RSNP.txt", "_6000", 3,  15,          5,   true,
//                new String("7,8"), new String("15"), new String("13,14"));
//        closeAll();
//        finanse(title7000, dir, "slygba", "RSNSP.txt", "_7000", 3,  13,          5,   true,
//                   new String("7"), new String("12,13"), new String("10,11"));
//        closeAll();	

        finanse(title7000, dir, "razoviyZRD", "PPRZD.txt", "_6000", 3,  15,          5,   true,
                new String("7,8"), new String("15"), new String("13,14"));
        closeAll();

        try
        {
            bw = new BufferedWriter(new FileWriter(dir + "//Itogo.xls"));
            bw.write("<table border = \"0\"><tr>");
            bw.write("<table border = \"0\"><th>код организации и № поезда");
            bw.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        
        
        
        
        
        /*        
        
        
        try
        {
            br = new BufferedReader(new FileReader(nameFileRead));
        }
        catch (Exception e)
        {
            System.out.println("Error open file: " + nameFileRead);
            error = true;
        }

        if (chekError()) return;
        try
        {
            nameFileWrite = nameFileRead.substring(0, nameFileRead.lastIndexOf("\\") + 1) + 
                			"new_" + 
            				nameFileRead.substring(nameFileRead.lastIndexOf("\\") + 1);
            System.out.println(nameFileWrite);
            bw = new BufferedWriter(new FileWriter(nameFileWrite));
        }
        catch (Exception e)
        {
            System.out.println("Error create file: " + nameFileWrite);
            error = true;
            return;
        }
        if (chekError()) return;
        
        strArr = "".split("");
        try
        {
            Vector v = new Vector();
            boolean first = true;
            if (br != null)
            while ((strTmp = br.readLine()) != null)
            {
                if (strTmp.indexOf("--") > -1)
                {
                    for (int i = 1; i < strArr.length; i++)
                        bw.write(strArr[i] + "!");
                    bw.newLine();                    
                    first = true;
                    continue;
                }
                if (first)
                {
                    first = false;
                    strArr = strTmp.split("!");
                }
                else
                {
                    String[] tmp = strTmp.split("!");
                    for (int i = 0; i < tmp.length; i++)
                    {
                        if ((i == 12) || (i == 13)) strArr[i] = strArr[i] + "!";
                        strArr[i] = strArr[i] + tmp[i].trim();
                    }                                    
                }
            }
        }
        catch (IOException ioe) 
        {
            error = true;
            System.out.println("Error read file: " + nameFileRead);
        }
        
        error = true;
        chekError();
        */
    }
}
