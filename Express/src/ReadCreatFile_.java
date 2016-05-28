/*
 * ReadCreatFile.java. Created on 05.10.2009
 *
 * Copyright 2006 SAV Soft, Inc. All rights reserved.
 */

/**
 * @author SEA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

	// Перед запуском следует указать код дороги, для которой обрабатываются данные (переменная doroga)
	// Файлы будут формироваться в папки по адресу: C:/Work/Express/doroga_year.month/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import ru.svrw.logged.Log;
import ru.svrw.sql.rowset.CachedRowSet;
import ru.svrw.sql.ds.BufferPool;
import ru.svrw.util.Properties;

public class ReadCreatFile_ {

    static BufferedWriter bw;
    static boolean error = false;

    private static boolean chekError()
    {
        if (error)
        {
            try
            {
                if (bw != null) bw.close();
                bw = null;
            }
            catch (IOException ioe) {}
        }
        return error;
    }
    
    private static void writeTitle(String title, String fullNameFile) {
    	BufferedWriter bw;
    	
        try
        {            
            bw = new BufferedWriter(new FileWriter(fullNameFile));
            bw.write(title);
            bw.newLine();
            bw.close();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    private static void write(String fullNameFile, int num_p, String year, String month, int vp, int codOr, BufferPool bp, Log log, Log log2, Properties prop, int doroga) {
		String str = "";
        String[] st = "                ".split("");
		
		String view_doc = "";
		String registr = "";
		String code_org = "";
		String code_podr = "";
		String transp = "";
		String form_tr = "";
		String sta_o = "";
		String sta_n = "";
		
		int nstr = 0;

		BufferedWriter bw;
        CachedRowSet crs = new CachedRowSet(log2);
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;       

        try
        {            
            bw = new BufferedWriter(new FileWriter(fullNameFile,true));
            sql = prop.getString("sql.sel_bundle"); 
            crs1.setSQL(sql);
            crs1.addParameter(year + "-" + month + "-31");
            crs1.addParameter(year + "-" + month + "-01");
            crs1.addParameter(num_p);
            crs1.addParameter(vp);
            crs1.addParameter(codOr);
            crs1.addParameter(doroga);
   	 		if (bp.executeQuery(crs1) && !crs1.isError())
   	 			if (crs1.size() > 0)
   	 				while (crs1.next()) {
   	 					for (int n = 1; n <= crs1.getColumnCount(); n++)
   	 						if (crs1.getString(n) == null) 
   	 							st[n] = "";
   	 						else
   	 							st[n] = crs1.getString(n);
   	 					if (num_p == 6000) {
   	 						sql = prop.getString("sql.sel_vDoc"); 
   	 						crs.setSQL(sql);
   	 						crs.addParameter(crs1.getInteger(5));
   	 						if (bp.executeQuery(crs) && !crs.isError())
   	 							if (crs.size() > 0) 
   	 								while (crs.next()) view_doc = crs.getString(1);
   	 							else
   	 								System.out.println("Нет записей");
   	 						else
                       			log2.error(crs.getException());
   	 						sql = prop.getString("sql.sel_reg"); 
   	 						crs.setSQL(sql);
   	 						crs.addParameter(crs1.getInteger(6));
   	 						if (bp.executeQuery(crs) && !crs.isError())
   	 							if (crs.size() > 0)
   	   	 							while (crs.next()) registr = crs.getString(1);
   	 							else
   	 								System.out.println("Нет записей");
   	 						else
   	 							log2.error(crs.getException());
   	 					}
	 					sql = prop.getString("sql.sel_codOrg"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(codOr);
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) code_org = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("Нет записей");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_codPodr"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(7));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) code_podr = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("Нет записей");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namTransp"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(8));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) transp = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("Нет записей");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namFormTr"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(9));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) form_tr = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("Нет записей");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getString(3));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) sta_o = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("Нет записей");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getString(4));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) sta_n = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("Нет записей");
   	   	 				else
   	   	 					log2.error(crs.getException());
   	   	 				if (num_p == 6000)
   	   	 					str = "<tr valign=\"top\" align=\"center\" ><td align=\"left\" >" + transp + "</td><td>" + form_tr + "</td><td>" + 
   	   	 					code_org + "</td><td>" + code_podr + "</td><td align=\"left\" >" + st[13] + "<br>" + st[12] + "</td><td class=\"number\" >" + st[14] + "</td><td>" + 
   	   	 					st[1] + "</td><td>" + st[15] + "</td><td>" + st[2] + "</td><td>" + registr + "</td><td align=\"left\" >" + view_doc + 
   	   	 					"</td><td>" + st[10] + "</td><td align=\"left\" >" + sta_o + "</td><td>" + st[3] + "</td><td align=\"left\" >" + sta_n + 
   	   	 					"</td><td>" + st[4] + "</td><td class=\"number\">" + st[11] + "</td></tr>";
   	   	 				else
   	   	 					str = "<tr valign=\"top\" align=\"center\" ><td align=\"left\" >" + transp + "</td><td>" + form_tr + "</td><td>" + 
   	   	 					code_org + "</td><td>" + code_podr + "</td><td align=\"left\" >" + st[13] + "<br>" + st[12] + "</td><td class=\"number\">" + st[14] + "</td><td>" + 
   	   	 					st[1] + "</td><td>" + st[2] + "</td><td>" + st[10] + "</td><td align=\"left\" >" + sta_o + "</td><td>" + st[3] + 
   	   	 					"</td><td align=\"left\" >" + sta_n + "</td><td>" + st[4] + "</td><td class=\"number\" >" + st[11] + 
   	   	 					"</td><td class=\"number\" >" + st[15] + "</td></tr>";
   	   	 				bw.write(str);
   	 					bw.newLine();
   	 				}
   	 			else
   	 				System.out.println("Нет записей");
   	 		else
   	 			log2.error(crs1.getException());
 			if ((st[12] == null) || (st[12] == ""))
	   	 		nstr = crs1.size() + 2;
 			else
	   	 		nstr = crs1.size()*2 + 2;
	 		if (num_p == 6000)
	 			bw.write("<tr><td colspan=\"16\" align=\"right\" ><b>Итого</b></td><td colspan=\"1\" >=сумм(Q3:Q" + nstr + ")</td></tr>");
	 		else
	 			bw.write("<tr><td colspan=\"13\" align=\"right\" ><b>Итого</b></td><td colspan=\"2\" >=сумм(N3:O" + nstr + ")</td></tr>");
			bw.write("</table></body></html>");
            bw.close();
        }
        catch (Exception e)
        {
            error = true;
            System.out.println(e);
        }
        chekError();
    }
    
    private static void itogo(BufferPool bp, Log log, Log log2, Properties prop, int kolOrg, int kolVPass, String year, String month, String dir, int doroga) {
    	String fullNameFile = dir + "\\Itogo.xls";
    	String str = "";
    	int nstr = 1;
    	double summ = 0.0;
    	int i = 0;
		
    	BufferedWriter bw;
        CachedRowSet crs = new CachedRowSet(log2);
        CachedRowSet crs1 = new CachedRowSet(log2);
        CachedRowSet crs3 = new CachedRowSet(log2);
        String sql;       

        try
        {            
            bw = new BufferedWriter(new FileWriter(fullNameFile));
			bw.write("<html>\n" + 
					"<style>.number {mso-number-format:\"0\";}</style><body>\n" + 
					"<table border=\"1\">\n" + 
					"<tr><th width=90 >Код организации</th>" +
					"<th width=125 >работники организаций, финансируемых ОАО РЖД</th>" + 
					"<th width=177 >дети, находящиеся на иждевении работников и пенсионеров ОАО РЖД</th>" + 
					"<th width=90 >пенсионеры</th>" + 
					"<th width=76 >работники ОАО РЖД</th>" + 
					"<th width=60 >иные лица</th>" + 
					"<th width=143 >дети, родители которых погибли в результате ж.д. аварий</th>" + 
					"<th width=65 >ИТОГО</th></tr>");
   	 		sql = prop.getString("sql.orgMonth"); 
   	 		crs3.setSQL(sql);
   	 		crs3.addParameter(year + "-" + month + "-31");
   	 		crs3.addParameter(year + "-" + month + "-01");
   	 		crs3.addParameter(doroga);
    	    if (bp.executeQuery(crs3) && !crs3.isError())
   	    		while (crs3.next()) {
   	    			kolOrg = crs3.size();
   	    			i = crs3.getInteger(1);
				sql = prop.getString("sql.sel_codOrg"); 
                crs.setSQL(sql);
                crs.addParameter(i);
        		if (bp.executeQuery(crs) && !crs.isError())
       				if (crs.next()) {
                    	str = "<tr><td>" + crs.getString(1) + "</td>";
                    	for (int j = 1; j <= kolVPass; j++) {
                    		sql = prop.getString("sql.sel_sumVPass"); 
                    		crs1.setSQL(sql);
                    		crs1.addParameter(year + "-" + month + "-31");
                    		crs1.addParameter(year + "-" + month + "-01");
                    		crs1.addParameter(j);
                    		crs1.addParameter(i);
                    		crs1.addParameter(doroga);
                    		if (bp.executeQuery(crs1) && !crs1.isError())
                    			if (crs1.size() > 0) {
                    				if (crs1.next())
                    					summ = crs1.getDouble(1,0);
                    			}
                    			else
                    				System.out.println("Нет суммы для указанного вида пассажира");
                    		else
                    			log2.error(crs1.getException());
                    		str = str + "<td class=\"number\" align=\"center\" >" + summ + "</td>";
                    		summ = 0.0;
                    	}
                    	nstr++;
                    	str = str + "<td>=сумм(B" + nstr + ":G" + nstr + ")</td></tr>";
                    	bw.write(str);
       				}
       				else
        				System.out.println("Нет организации");
        		else
        			log2.error(crs.getException());
            }
			nstr = kolOrg + 1;
			bw.write("<tr><td align=\"right\" ><b>Итого</b></td><td>=сумм(B2:B" + nstr + ")</td><td>=сумм(C2:C" + nstr + ")</td><td>=сумм(D2:D" + nstr + 
					")</td><td>=сумм(E2:E" + nstr + ")</td><td>=сумм(F2:F" + nstr + ")</td><td>=сумм(G2:G" + nstr + ")</td><td>=сумм(H2:H" + nstr + 
					")</td></tr>");
			bw.write("</table></body></html>");
            bw.close();
        }
        catch (Exception e)
        {
            error = true;
            System.out.println(e);
        }
        chekError();
    }
    public static void main(String[] args) {
		String dir = "C:\\Work\\Express";
		String subDir = "";
		String dat = "";
		String month = "";
		String year = "";
		String fullNameFile = "";
		int kolOrg = 0;
		int kolVPass = 0;
		int j = 0;
		String title6 = "<html>\n" + 
		   "   <style>.number {mso-number-format:\"0\";}</style><body>\n" + 
		   "       <table border=\"1\" >\n" + 
		   "<tr><th rowspan=\"2\" width=120 >Наименование перевозчика</th>" +
		   "<th rowspan=\"2\" width=60 >Форма трансп тр.</th>" + 
		   "<th rowspan=\"2\" width=108 >Код организации</th>" +
		   "<th rowspan=\"2\" width=112 >Код подразделения</th>" +
		   "<th rowspan=\"2\" width=160 >Фамилия, инициалы пассажира</th>" + 
		   "<th rowspan=\"2\" width=108 >Номер трансп. требования</th>" + 
		   "<th rowspan=\"2\" width=94 >Дата оформления ППД</th>" + 
		   "<th rowspan=\"2\" width=94 >Дата начала действия требования</th>" + 
		   "<th rowspan=\"2\" width=71 >Номер оформл. бланка</th>" + 
		   "<th rowspan=\"2\" width=92 >Способ оформления</th>" + 
		   "<th rowspan=\"2\" width=80 >Вид документа</th>" + 
		   "<th rowspan=\"2\" width=85 >Количество документов</th>" + 
		   "<th colspan=\"4\" >Станция (зона)</th>" + 
		   "<th rowspan=\"2\" >Стоимость</th>" +
		   "</tr><th colspan=\"2\">Отправления</th><th colspan=\"2\">Назначения</th></tr>";
        String title7 = "<html>\n" + 
		   "   <style>.number {mso-number-format:\"0\";}</style><body>\n" + 
		   "       <table border=\"1\" >\n" + 
		   "<tr><th rowspan=\"2\" width=120 >Наименование перевозчика</th>" +
		   "<th rowspan=\"2\" width=60 >Форма трансп тр.</th>" + 
		   "<th rowspan=\"2\" width=108 >Код организации</th>" +
		   "<th rowspan=\"2\" width=112 >Код подразделения</th>" +
		   "<th rowspan=\"2\" width=160 >Фамилия, инициалы пассажира, Фамилия, инициалы работника, на чьем иждивении</th>" + 
		   "<th rowspan=\"2\" width=115 >Номер трансп. требования</th>" + 
		   "<th rowspan=\"2\" width=94 >Дата оформления ППД</th>" + 
		   "<th rowspan=\"2\" width=71 >Номер оформл. бланка</th>" + 
		   "<th rowspan=\"2\" width=85 >Количество билетов АСУ ЭКСПРЕСС</th>" +		   
		   "<th colspan=\"4\">Станция (зона)</th>" + 
		   "<th rowspan=\"2\">Стоимость</th>" +
		   "<th rowspan=\"2\" width=111 >Доплата (сумма доплачивается пассажиром)</th>" +
		   "</tr><th colspan=\"2\">Отправления</th><th colspan=\"2\">Назначения</th></tr>";
        int doroga = 80;   // код дороги, к которой относятся данные
        int timeB = 0;
        int timeEnd = 0;
		
        Log log = new Log("${pathProject}/conf/BufferPool.properties");
        BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
        Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
        Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
        CachedRowSet crs = new CachedRowSet(log2);
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;       

        sql = prop.getString("sql.time"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 	{
   	 		if (crs.size() > 0)
   	 			if (crs.next()) timeB = crs.getInteger(1);
   	 	}
   	 	else
   	 		log2.error(crs.getException());

   	 	File f;
	//	select * from bundle where data<'2009-07-31' and data>'2009-07-01'
   	 	sql = prop.getString("sql.sel_datBundle"); 
   	 	crs.setSQL(sql);		//	макс.дата => 
      	crs.addParameter(doroga);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 	{
   	 		// если количество записей больше 0, то ...
   	 		if (crs.size() > 0)
   	 			if (crs.next()) dat = crs.getString(1);
   	 	}
   	 	// если была ошибка, то распечатаем 
   	 	else
   	 		log2.error(crs.getException());
        month = dat.substring(5,7);		// последний месяц
        year = dat.substring(0,4);		// последний год

   // кол-во организаций (для перебора в цикле)
        sql = prop.getString("sql.sel_countOrg"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 	{
   	 		if (crs.size() > 0)
   	 			if (crs.next()) kolOrg = crs.getInteger(1);
   	 	}
   	 	else
   	 		log2.error(crs.getException());
        
   // кол-во видов пассажиров (для перебора в цикле)
        sql = prop.getString("sql.sel_countVPass"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 		if (crs.size() > 0)
   	 			if (crs.next()) kolVPass = crs.getInteger(1);
   	 		else
   	 			System.out.println("Нет записей");
   	 	else
   	 		log2.error(crs.getException());
        
	 	for (int i = 1; i <= kolVPass; i++) {
        	// создаем папку в заданной директории: viewPass
            sql = prop.getString("sql.sel_vPass"); 
            crs.setSQL(sql);
            crs.addParameter(i);
       	 	if (bp.executeQuery(crs) && !crs.isError())
 			if (crs.next()) {
       	 				subDir = crs.getString(1);
       	 				f = new File("../../Express/" + doroga + "_" + year+ "." + month + "/" + subDir);
       	 				f.mkdirs();
 			}
       	 	else
       	 		log2.error(crs.getException());

       	 	for (int k = 6000; k <= 7000; k += 1000) {
       	 		sql = prop.getString("sql.spisokOrg"); 
       	 		crs1.setSQL(sql);
       	 		crs1.addParameter(year + "-" + month + "-31");
       	 		crs1.addParameter(year + "-" + month + "-01");
       	 		crs1.addParameter(k);
       	 		crs1.addParameter(i);
       	 		crs1.addParameter(doroga);
        	    if (bp.executeQuery(crs1) && !crs1.isError())
        	    	if (crs1.size() > 0)
        	    		while (crs1.next()) {
            	        	j = crs1.getInteger(1);
            	        		// создадим файл в папке: codeOrg_numP.xls
        	    			sql = prop.getString("sql.sel_codOrg"); 
        	    			crs.setSQL(sql);
        	    			crs.addParameter(j);
        	        	    if (bp.executeQuery(crs) && !crs.isError())
        	        	    	if (crs.size() > 0) {
        	        	    		if (crs.next()) 
        	        	    			fullNameFile = dir + "\\" + doroga + "_" + year + "." + month + "\\" + subDir + "\\" + crs.getString(1) + "_" + k + ".xls";
        	        	    	}
        	        	    	else
        	        	    		System.out.println("Нет записей");
        	        	    else
        	        	    	System.out.println(crs.getException());
        	    			if (k == 6000)
        	    				writeTitle(title6, fullNameFile);
        	    			else
        	    				writeTitle(title7, fullNameFile);
        	    			write(fullNameFile, k, year, month, i, j, bp, log, log2, prop, doroga);
        	    		}
        	    	else
        	    		System.out.println("Нет записей");
        	    else
        	    	log2.error(crs1.getException());
       	 	}
        }
     
        // Итоговая таблица
        itogo(bp, log, log2, prop, kolOrg, kolVPass, year, month, dir + "\\" + doroga + "_" + year + "." + month, doroga);

        sql = prop.getString("sql.time"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 	{
   	 		if (crs.size() > 0)
   	 			if (crs.next()) timeEnd = crs.getInteger(1);
   	 	}
   	 	else
   	 		log2.error(crs.getException());

   	 	System.out.println("Время работы программы: " + (timeEnd-timeB)/60 + " часов " + ((timeEnd-timeB)-((timeEnd-timeB)/60)*60) + " минут");
   	 	bp.close();
	}
}