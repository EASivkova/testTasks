/*
 * Express.java. Created on 18.08.2009
 *
 * Copyright 2006 SAV Soft, Inc. All rights reserved.
 */

/**
 * @author SEA
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

	// ����� �������� ������� ������� ��� ������, ��� ������� �������������� ������ (���������� doroga)
	// ����� ����� ������� ��� ������������ �� ����� �� ������: C:/Work/Express/doroga_year.month/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ru.svrw.logged.Log;
import ru.svrw.sql.rowset.CachedRowSet;
import ru.svrw.sql.ds.BufferPool;
import ru.svrw.util.Properties;

public class Express {

    static BufferedReader br;
    static boolean error = false;
    static BufferedWriter bw;
    private static String s = ""; // ������ ������ ������������ � ��������
    static String n = ""; // ��������� ������ ����������� ��� ������ � ����� � ��������
    
	static String dir = "C:\\Work\\Express\\"; // ����� � ������� txt
	static int doroga = 76;   // ��� ������, � ������� ��������� ������

    private static boolean chekError()
    {
        if (error)
        {
            try
            {
                if (br != null) br.close();
                br = null;
                if (bw != null) bw.close();
                bw = null;
            }
            catch (IOException ioe) {}
        }
        return error;
    }
    /* ������ ������� � ������, � ����. ������� (������., ����������), �� ����������, c ��������, � ��������, ������� ����� �������, �������� �����, 
     * ���������� ������� � ������ ����������� � ��, ����� ������� � ������ ������. ����������, � ������������� �����������, 
     * � ����� ����������� � �-� ��������, � ����� ������������� � �-� ��������, � ��������� ������� ����������, ����� ������ (6000-� ��� 7000-�), 
     * ����� ������� � ��������� ���� ���������, ��� ��������� (��������� � �.�.), ��� ���������, ����� ������� � ��������� ������ ���������, 
     * � ���-��� �������, � ������� ����������, ����� ������ ������� � ������� ���� ������ �� �����
     */
    private static void parse(int dat, int dat_st, int st_ot, int st_n, int fare, int summ, int fio, boolean oneFio, String nameFileRead, int countColumns, 
    		int form_tr, int transp, int code_org, int code_podr, int registr, int num_p, int view_doc, String view_tr, String view_pass, int num_doc, 
    		int kol, int num_tr, int numRow) {
        String[] strArr = "".split("");
        String strTmp = "";
        
        try
        {
	    // �������� ��� ��������� � ������������, ��������� ������� �� ����� ������� ����� � ��� � ����� ����� ����� ��������� ��� ���� � ����
	       Log log = new Log("${pathProject}/conf/BufferPool.properties");
	       BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
	    // �������� ��� ���� ������ ����������� ��� ����� �������� ����� �� ��������� ���� ������ ����� ������ � ����� ���������
	    // ����� � ��� � ����� ����� ����� � ��������� ����������� � SQL
	       Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
	    // ������ ��� ������ � ������ �������, ����� ������ ��������� SQL ����� �� ������� SQL � ����. ��� ���������� ������ ������� ��. ����� �������.
	       Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
	       CachedRowSet crs = new CachedRowSet(log2);
	       String sql = prop.getString("sql.select");       

	       boolean first = true;
            int numStrCell = 0;
            int for_ind = 0;
            int tra_ind = 0;
            int org_ind = 0;
            int pod_ind = 0;
            int reg_ind = 0;
            int vd_ind = 0;
            int vt_ind = 0;
            String[] st = "".split("");
            String sta_otp = "";
            String sta_n = "";
            int vp_ind = 0;
            String famOne = "";
            String famTwo = "";
            int kolRow = 0;
            
            if (br != null)
            while ((strTmp = br.readLine()) != null) // ��������� ������ �� ����� (���� �� ������ �� ����)
            {
                numStrCell++;
                kolRow++;
                if (kolRow > numRow) {
                if (strTmp.indexOf("--") > -1) // ����� � ������ ������ ��������� �������� ��������� (�� ���� �������� ������� ������� ����� ��������� � ������)
                {
                    numStrCell = 0;
                    if (strArr.length == countColumns + 1)
                    {
                    	strArr[0] = null;
                    	for (int i = 1; i < strArr.length; i++) { // ���������� �������� ��������� ������� � ���� ������
                            if (strArr[i].length() == -1 || strArr[i].trim().equals("") || strArr[i].trim().equals("''"))
                                strArr[i] = null;
                            if (i == form_tr) {
                       	 		sql = prop.getString("sql.sel_FormTr");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	// ���� ������ ���������� ������� � �� ���� ������, �� ... 
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		// ���� ���������� ������� ������ 0, �� ...
                           	 		if (crs.size() > 0) {
                           	 			while (crs.next()) for_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                           	 			sql = prop.getString("sql.sel_maxFormTr");
                           	 			if (sql == null) {
                           	 				log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                           	 				break;
                           	 			}
                           	 			crs.setSQL(sql);
                           	 			// ���� ������ ���������� ������� � �� ���� ������, �� ... 
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) for_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_FormTr");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   			// ��������� SQL � ���������            
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(for_ind);
                                   			// �������� SQL, ���� ���� ������, �� ��������� ������
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   	         log2.error(crs.getException());
                                   	         break;
                                   			}
                                   	 	}
                                   	 	// ���� ���� ������, �� ����������� 
                                   	 	else {
                                  	         log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	// ���� ���� ������, �� ����������� 
                           	 	else {
                          	         log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }
                            
                            if (i == transp) {
                       	 		sql = prop.getString("sql.sel_transp");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                           	 	if (strArr[i].indexOf("6000") > -1)
                           	 		crs.addParameter((strArr[i].substring(0, (strArr[i].trim()).length()-4)).trim());
                           	 	else
                           	 		crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 	    while (crs.next()) tra_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxTransp");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) tra_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_transp");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                       	 	if (strArr[i].indexOf("6000") > -1)
                                       	 		crs.addParameter((strArr[i].substring(0, (strArr[i].trim()).length()-4)).trim());
                                       	 	else
                                       	 		crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(tra_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else {
                                   	 		log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == code_org) {
                       	 		sql = prop.getString("sql.sel_codeOrg");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 		while (crs.next()) org_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxCodeOrg");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) org_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_codeOrg");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(org_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else {
                                   	 		log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == code_podr) {
                       	 		sql = prop.getString("sql.sel_codePodr");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 		while (crs.next()) pod_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxCodePodr");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) pod_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_codePodr");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(pod_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else {
                                   	 		log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == registr) {
                       	 		sql = prop.getString("sql.sel_registr");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 		while (crs.next()) reg_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxRegistr");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) reg_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_registr");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(reg_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else {
                                   	 		log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == view_doc) {
                       	 		sql = prop.getString("sql.sel_viewDoc");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 		while (crs.next()) vd_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxViewDoc");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) vd_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_viewDoc");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(vd_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else {
                                   	 		log2.error(crs.getException());
                                   	 		break;
                                   	 	}
                           	 		}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == st_ot) {
                             	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                               			while (crs.next()) sta_otp = crs.getString(1);
                               		}
                               		else {
                            			sta_otp = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                      	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                    		log2.error(crs.getException());
                                    		break;
                                     	}
                               	 	}
                               	 }
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }
                            if (i == st_n) {
                               	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                             			while (crs.next()) sta_n = crs.getString(1);
                               		}
                               		else {
                               			sta_n = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                       	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                       		log2.error(crs.getException());
                                       		break;
                                       	}
                                 	}
                                }
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                            }

                            if (i == num_tr) {
                            	strArr[i] = "'" + strArr[i] + "'";
                            }
                            
                            if (i == fio) {
                            	if (!oneFio) {
                            		if (strArr[i].indexOf("=") > -1) {
                            			famOne = strArr[i].substring(0, strArr[i].indexOf("=") + 3).trim();
                            			famTwo = "'" + strArr[i].substring(strArr[i].indexOf("=") + 4, strArr[i].length()).trim() + "'";
                            		}
                            		else {
                            			famOne = strArr[i].substring(0, strArr[i].indexOf(" ")).trim() + " " + strArr[i].substring(strArr[i].lastIndexOf(" ") + 1, strArr[i].lastIndexOf(" ") + 3);
                            			famTwo = "'" + strArr[i].substring(strArr[i].lastIndexOf(" ") + 3, strArr[i].length() - 2) + " " + strArr[i].substring(strArr[i].length() - 2) + "'";
                            		}
                            	}
                            	else {
                            		if (strArr[i].indexOf("=") > -1) 
                            			famOne = strArr[i].trim();
                            		else 
                            			famOne = strArr[i].substring(0,strArr[i].length() - 2).trim() + " " + strArr[i].substring(strArr[i].length() - 2);
                            		famTwo = null;
                            	}
                            }
                    	}
                    	
               	 		sql = prop.getString("sql.sel_viewTr");
               	 		if (sql == null) {
               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
               	 			break;
               	 		}
                   	 	crs.setSQL(sql);
               			crs.addParameter(view_tr);
                       	if (bp.executeQuery(crs) && !crs.isError())
                       	{
                       		if (crs.size() > 0) {
                       			while (crs.next()) vt_ind = crs.getInteger(1);
                       		}
                       		else {
                       	 		sql = prop.getString("sql.sel_maxViewTr");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		while (crs.next()) vt_ind = crs.getInteger(1,0) + 1;
                           	 		sql = prop.getString("sql.ins_viewTr");
                           	 		if (sql == null) {
                           	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                           	 			break;
                           	 		}
                           	 		crs.setSQL(sql);
                           			crs.addParameter(view_tr);
                           			crs.addParameter(vt_ind);
                           			if (!bp.updateQuery(crs) || crs.isError())
                           			{
                           				log2.error(crs.getException());
                           				break;
                           			}
                           	 	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                       		}
                       	}
                   	 	else {
                   	 		log2.error(crs.getException());
                   	 		break;
                   	 	}
                        
               	 		sql = prop.getString("sql.sel_viewPass");
               	 		if (sql == null) {
               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
               	 			break;
               	 		}
                   	 	crs.setSQL(sql);
               			crs.addParameter(view_pass);
                       	if (bp.executeQuery(crs) && !crs.isError())
                       	{
                       		if (crs.size() > 0) {
                       			while (crs.next()) vp_ind = crs.getInteger(1);
                       		}
                       		else {
                       	 		sql = prop.getString("sql.sel_maxViewPass");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                              	if (bp.executeQuery(crs) && !crs.isError())
                              	{
                              		while (crs.next()) vp_ind = crs.getInteger(1,0) + 1;
                           	 		sql = prop.getString("sql.ins_viewPass");
                           	 		if (sql == null) {
                           	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                           	 			break;
                           	 		}
                           	 		crs.setSQL(sql);
                              		crs.addParameter(view_pass);
                              		crs.addParameter(vp_ind);
                              		if (!bp.updateQuery(crs) || crs.isError())
                              		{
                              			log2.error(crs.getException());
                              			break;
                              		}
                              	}
                           	 	else {
                           	 		log2.error(crs.getException());
                           	 		break;
                           	 	}
                       		}
                       	}
                   	 	else {
                   	 		log2.error(crs.getException());
                   	 		break;
                   	 	}
                        
                       	if (registr == 0) {
                       		if (num_p == 7000) 
                       			reg_ind = 2; 
                       	}
                       	n = strArr[num_doc];
                       	if (vd_ind == 0)
                       		if (reg_ind == 0) 
                             	crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
                             		sta_n + ", " + vt_ind + ",  null, " + num_p + ", null, " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
                             		vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
                             		famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + doroga + ")");
                       		else 
                       			crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
                    				sta_n + ", " + vt_ind + ", null, " + num_p + ", " + reg_ind + ", " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
                     				vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
                     				famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + doroga + ")");
                       	else
                       		if (reg_ind == 0) 
                             	crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
                             		sta_n + ", " + vt_ind + ", " + vd_ind + ", " + num_p + ", null, " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
                             		vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
                             		famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + doroga + ")");
                       		else
                       			crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
                       				sta_n + ", " + vt_ind + ", " + vd_ind + ", " + num_p + ", " + reg_ind + ", " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
                       				vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
                       				famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + doroga + ")");

                        if (!bp.updateQuery(crs) || crs.isError())
                        {
                        	log2.error(crs.getException());
                        	log2.debug("SQL:\n\t" + crs.getSQLAndParameters());
                        	break;
                        }
                    }
                    strArr = "".split("");
                	first = true;
                	continue;
                }
                if (first) // ������ ������ ����� "--"
                {
                    first = false;
                    strArr = strTmp.split("!"); // ������ �������� ����� "!"
                }
                else // ����������� ���� � ������ ��� "--"
                {
                    String[] tmp = strTmp.split("!"); // ������ �������� ����� "!"
                    for (int i = 0; i < tmp.length; i++)
                    {
                        if (numStrCell == 2) {
                            if (st_ot == i || st_n == i)
                                strArr[i] = strArr[i] + ";"; // ���������� ����������� ����� ��������� � ������� ������� (��� ��������� � ����� �������� �������)
                            if (view_doc == i)
                            	strArr[i] = strArr[i].trim() + " ";
                        }
                        strArr[i] = strArr[i] + tmp[i].trim();
                        // ��� ����������� ������������� ����: 08.01.2009  (��.��.����)                         
                        if (numStrCell == 2)
                            if (dat == i || dat_st == i)
                                if (strArr[i].length() > 5)
                                	strArr[i] = "'" + strArr[i].substring(0,2) + "." + strArr[i].substring(2,4) + ".20" + strArr[i].substring(4) + "'";
                    }                                    
                }
                }
            }
            bp.close();
        }
        catch (IOException ioe) 
        {
            error = true;
            System.out.println("Error read file: " + nameFileRead);
        }
        
        error = true;
        chekError();

    }
    
    /* ������ ������� � ������, � ����. ������� (������., ����������), �� ����������, c ��������, � ��������, �������� �����, 
     * ���������� ������� � ������ ����������� � ��, ����� ������� � ������������� �������� (��������), � ��������� ������� ����������, 
     * ����� ������� � ��������� ���� ������, � ������� ���������, � ������� ��������� ��� ���������� ������, 
     * ����� ������ ������� � ������� ���� ������ �� �����
     */
    private static void parse_pp(int dat, int dat_st, int st_ot, int st_n, int fare, int summ, int fio, String nameFileRead, int countColumns, 
    		int direct, int registr, int privilege, int num_doc, int num_dpr, int numRow) {
        String[] strArr = "".split("");
        String strTmp = "";
        
        try
        {
	       Log log = new Log("${pathProject}/conf/BufferPool.properties");
	       BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
	       Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
	       Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
	       CachedRowSet crs = new CachedRowSet(log2);
	       String sql = prop.getString("sql.select");       

            boolean first = true;
            int numStrCell = 0;
            int dir_ind = 0;
            int reg_ind = 0;
            int vd_ind = 0;
            int kol = 0;
       	 	int view_doc = 0;
            String[] st = "".split("");
            String sta_otp = "";
            String sta_n = "";
            String famOne = "";
            int kolRow = 0;
            
            if (br != null)
            while ((strTmp = br.readLine()) != null)
            {
                numStrCell++;
                kolRow++;
                if (kolRow > numRow) {
                if (strTmp.indexOf("--") > -1)
                {
                    numStrCell = 0;
                    if (strArr.length == countColumns + 1)
                    {
                    	strArr[0] = null;
                    	for (int i = 1; i < strArr.length; i++) {
                            if (strArr[i].length() == -1 || strArr[i].trim().equals("") || strArr[i].trim().equals("''"))
                                strArr[i] = null;
                            if (i == direct) {
                       	 		sql = prop.getString("sql.sel_direct");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 	    while (crs.next()) dir_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxDirect");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) dir_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_direct");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                       	 	crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(dir_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else log2.error(crs.getException());
                           	 		}
                           	 	}
                           	 	else log2.error(crs.getException());
                            }
                            if ((i == registr) && (strArr[i] != null)) {
                           	 	reg_ind = 1;
                           	 	kol = i + 1;
                           	 	view_doc = i;
                            }

                            if ((i == (registr + 2)) && (strArr[i] != null)) {
                           	 	reg_ind = 2;
                           	 	kol = i + 1;
                           	 	view_doc = i;
                            }

                           	if ((i == view_doc) && (strArr[i] != null)) {
                           		if (strArr[i].indexOf(" ") > -1) {
                           			strArr[i] = strArr[i].substring(0,strArr[i].indexOf(" ")) + strArr[i].substring(strArr[i].lastIndexOf(" "));
                           		}
                       	 		sql = prop.getString("sql.sel_viewDoc");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 		while (crs.next()) vd_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxViewDoc");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) vd_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_viewDoc");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                   	 		crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(vd_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else log2.error(crs.getException());
                           	 		}
                           	 	}
                           	 	else log2.error(crs.getException());
                            }

                            if (i == st_ot) {
                             	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                               			while (crs.next()) sta_otp = crs.getString(1);
                               		}
                               		else {
                            			sta_otp = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                      	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                    		log2.error(crs.getException());
                                    		break;
                                     	}
                               	 	}
                               	 }
                               	 else log2.error(crs.getException());
                            }
                            if (i == st_n) {
                               	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                             			while (crs.next()) sta_n = crs.getString(1);
                               		}
                               		else {
                               			sta_n = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                       	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                       		log2.error(crs.getException());
                                       		break;
                                       	}
                                 	}
                                }
                               	else log2.error(crs.getException());
                            }

                            if (i == privilege) {
                       	 		sql = prop.getString("sql.sel_privilege");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                       	 		crs.setSQL(sql);
                               	crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 			while (crs.next()) strArr[i] = crs.getString(1);
                           	 		}
                           	 		else {
                           	 			strArr[i] = strArr[i].trim();
                               	 		sql = prop.getString("sql.ins_privilege");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                   		crs.addParameter(strArr[i].trim());
                                   		if (!bp.updateQuery(crs) || crs.isError())
                                   		{
                                   			log2.error(crs.getException());
                                   			break;
                                   		}
                           	 		}
                           	 	}
                           	 	else log2.error(crs.getException());
                            }
                            
                            if ((i == fio) && (strArr[i] != null)) {
                           		if (strArr[i].indexOf("=") > -1) 
                           			famOne = strArr[i].trim();
                           		else 
                           			famOne = strArr[i].substring(0,strArr[i].length() - 2).trim() + " " + strArr[i].substring(strArr[i].length() - 2);
                           	}
                            
                            if ((i == num_dpr) && (strArr[i] != null)) 
                            	strArr[i] = "'" + strArr[i].trim() + "'";
                    	}
                    	
                    	crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", " + strArr[privilege] + ", " + 
                    		sta_n + ", null, " + vd_ind + ", null, " + reg_ind + ", null, null, null, null, null, " + dir_ind + ", " + strArr[kol].trim() + ", " + 
                    		strArr[fare] + ", " + strArr[summ] + ", '" + famOne + "', null, " + strArr[dat_st] + ", " + strArr[num_dpr] + ", null," + doroga + ")");
                    	
                        if (!bp.updateQuery(crs) || crs.isError())
                        {
                        	log2.error(crs.getException());
                        	log2.debug("SQL:\n\t" + crs.getSQLAndParameters());
                        	break;
                        }
                    }
                    strArr = "".split("");
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
                        if (numStrCell == 2) {
                            if (st_ot == i || st_n == i)
                                strArr[i] = strArr[i] + ";";
                            if (view_doc == i)
                            	strArr[i] = strArr[i].trim() + " ";
                        }
                        strArr[i] = strArr[i] + tmp[i].trim();
                        if (numStrCell == 2)
                            if (dat == i || dat_st == i)
                                if (strArr[i].length() > 5)
                                	strArr[i] = "'" + strArr[i].substring(0,2) + "." + strArr[i].substring(2,4) + ".20" + strArr[i].substring(4) + "'";
                    }                                    
                }
                }
            }
            bp.close();
        }
        catch (IOException ioe) 
        {
            error = true;
            System.out.println("Error read file: " + nameFileRead);
        }
        
        error = true;
        chekError();
    }
    
    /* ������ ������� � ������, � ����. ������� (������., ����������), �� ����������, c ��������, � ��������, �������� �����, 
     * ���������� ������� � ������ ����������� � ��, ����� ������� � ������������� �������� (��������), � ��������� ������� ����������, 
     * ����� ������ (6000-� ��� 7000-�), ��� ������, ����� ������� � ������� ���������, � ���-��� �������, � ������� ��������� ��� ���������� ������, 
     * ����� ������ ������� � ������� ���� ������ �� �����
     */
    private static void parse_pr(int dat, int st_ot, int st_n, int fare, int summ, int fio, String nameFileRead, int countColumns, 
    		int direct, int registr, int num_p, int privilege, int num_doc, int kol, int num_dpr, int numRow) {
        String[] strArr = "".split("");
        String strTmp = "";
        
        try
        {
	       Log log = new Log("${pathProject}/conf/BufferPool.properties");
	       BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
	       Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
	       Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
	       CachedRowSet crs = new CachedRowSet(log2);
	       String sql = prop.getString("sql.select");       

            boolean first = true;
            int numStrCell = 0;
            int dir_ind = 0;
            int reg_ind = 0;
            String[] st = "".split("");
            String sta_otp = "";
            String sta_n = "";
            String famOne = "";
            int kolRow = 0;
            
            if (br != null)
            while ((strTmp = br.readLine()) != null)
            {
                numStrCell++;
                kolRow++;
                if (kolRow > numRow) {
                if (strTmp.indexOf("--") > -1)
                {
                    numStrCell = 0;
                    if (strArr.length == countColumns + 1)
                    {
                    	strArr[0] = null;
                    	for (int i = 1; i < strArr.length; i++) {
                            if (strArr[i].length() == -1 || strArr[i].trim().equals("") || strArr[i].trim().equals("''"))
                                strArr[i] = null;
                            if (i == direct) {
                       	 		sql = prop.getString("sql.sel_direct");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 	    while (crs.next()) dir_ind = crs.getInteger(1);
                           	 		}
                           	 		else {
                               	 		sql = prop.getString("sql.sel_maxDirect");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                                   	 	crs.setSQL(sql);
                                   	 	if (bp.executeQuery(crs) && !crs.isError())
                                   	 	{
                                   	 		while (crs.next()) dir_ind = crs.getInteger(1,0) + 1;
                                   	 		sql = prop.getString("sql.ins_direct");
                                   	 		if (sql == null) {
                                   	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                                   	 			break;
                                   	 		}
                                       	 	crs.setSQL(sql);
                                   			crs.addParameter(strArr[i].trim());
                                   			crs.addParameter(dir_ind);
                                   			if (!bp.updateQuery(crs) || crs.isError())
                                   			{
                                   				log2.error(crs.getException());
                                   				break;
                                   			}
                                   	 	}
                                   	 	else log2.error(crs.getException());
                           	 		}
                           	 	}
                           	 	else log2.error(crs.getException());
                            }

                            if ((i == registr) && (strArr[i] != null))
                           	 	reg_ind = 3;

                            if ((i == (registr + 2)) && (strArr[i] != null))
                           	 	reg_ind = 2;

                            if (i == st_ot) {
                             	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                               			while (crs.next()) sta_otp = crs.getString(1);
                               		}
                               		else {
                            			sta_otp = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                      	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                    		log2.error(crs.getException());
                                    		break;
                                     	}
                               	 	}
                               	 }
                               	 else log2.error(crs.getException());
                            }
                            if (i == st_n) {
                               	st = strArr[i].split(";");
                             	if (st[0].indexOf("'") > -1)
                             		st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
                       	 		sql = prop.getString("sql.sel_station");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                           	 	crs.setSQL(sql);
                       			crs.addParameter(st[0].trim());
                               	if (bp.executeQuery(crs) && !crs.isError())
                               	{
                               		if (crs.size() > 0) {
                             			while (crs.next()) sta_n = crs.getString(1);
                               		}
                               		else {
                               			sta_n = st[1];
                               	 		sql = prop.getString("sql.ins_station");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                       	crs.addParameter(st[0].trim());
                                       	crs.addParameter(st[1]);
                                       	if (!bp.updateQuery(crs) || crs.isError())
                                       	{
                                       		log2.error(crs.getException());
                                       		break;
                                       	}
                                 	}
                                }
                               	else log2.error(crs.getException());
                            }

                            if (i == privilege) {
                       	 		sql = prop.getString("sql.sel_privilege");
                       	 		if (sql == null) {
                       	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                       	 			break;
                       	 		}
                       	 		crs.setSQL(sql);
                               	crs.addParameter(strArr[i].trim());
                           	 	if (bp.executeQuery(crs) && !crs.isError())
                           	 	{
                           	 		if (crs.size() > 0) {
                           	 			while (crs.next()) strArr[i] = crs.getString(1);
                           	 		}
                           	 		else {
                           	 			strArr[i] = strArr[i].trim();
                               	 		sql = prop.getString("sql.ins_privilege");
                               	 		if (sql == null) {
                               	 			log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
                               	 			break;
                               	 		}
                               	 		crs.setSQL(sql);
                                   		crs.addParameter(strArr[i].trim());
                                   		if (!bp.updateQuery(crs) || crs.isError())
                                   		{
                                   			log2.error(crs.getException());
                                   			break;
                                   		}
                           	 		}
                           	 	}
                           	 	else log2.error(crs.getException());
                            }
                            
                            if (i == fio)
                           		famOne = "'" + strArr[i].trim() + "'";
                            
                            if (i == num_dpr)
                            	strArr[i] = "'" + strArr[i].trim() + "'";
                    	}
                    	
                   		if (reg_ind == 0)
                           	crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", " + strArr[privilege] + ", " + 
                           		sta_n + ", null, null, " + num_p + ", null, null, null, null, null, null, " + dir_ind + ", " + strArr[kol].trim() + ", " + 
                           		strArr[fare] + ", " + strArr[summ] + ", " + famOne + ", null, null, " + strArr[num_dpr] + ", null," + doroga + ")");
                   		else
                   			crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", " + strArr[privilege] + ", " + 
                   				sta_n + ", null, null, " + num_p + ", " + reg_ind + ", null, null, null, null, null, " + dir_ind + ", " + strArr[kol].trim() + ", " + 
                 				strArr[fare] + ", " + strArr[summ] + ", " + famOne + ", null, null, " + strArr[num_dpr] + ", null," + doroga + ")");

                        if (!bp.updateQuery(crs) || crs.isError())
                        {
                        	log2.error(crs.getException());
                        	log2.debug("SQL:\n\t" + crs.getSQLAndParameters());
                        	break;
                        }
                    }
                    strArr = "".split("");
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
                        if (numStrCell == 2) {
                            if (st_ot == i || st_n == i)
                                strArr[i] = strArr[i] + ";";
                        }
                        strArr[i] = strArr[i] + tmp[i].trim();
                        if ((numStrCell == 2) && (dat == i) && (strArr[i].length() > 5))
                           	strArr[i] = "'" + strArr[i].substring(0,2) + "." + strArr[i].substring(2,4) + ".20" + strArr[i].substring(4) + "'";
                    }                                    
                }
                }
            }
            bp.close();
        }
        catch (IOException ioe) 
        {
            error = true;
            System.out.println("Error read file: " + nameFileRead);
        }
        
        error = true;
        chekError();

    }
    
	private static void readFile(boolean oneFio, String nameFileRead, String view_tr, String view_pass, int numRow) {
		String dat = "";
		String month = "";
		String year = "";
    	
        try
        {
        	n = "";
    		Log log = new Log("${pathProject}/conf/BufferPool.properties");
    		BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
    		Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
    		Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
    		CachedRowSet crs = new CachedRowSet(log2); 
    		String sql;       

       	 	sql = prop.getString("sql.sel_monthBundle"); 
    		crs.setSQL(sql);		//	����.���� => 
           	crs.addParameter(doroga);
    		if (bp.executeQuery(crs) && !crs.isError()) {
    			// ���� ���������� ������� ������ 0, �� ...
    			if (crs.size() > 0)
    				if (crs.next()) dat = crs.getString(1);
    		}
    		// ���� ���� ������, �� ����������� 
    		else
    			log2.error(crs.getException());
    		bp.close();
    	   	 	
    		month = dat.substring(5,7);		// ��������� �����
    		year = dat.substring(0,4);		// ��������� ���
    		
            br = new BufferedReader(new FileReader(dir + doroga + "_" + year + "." + month + "\\" + nameFileRead)); // ����� � ����� ���������� ����� - nameFileRead
            
            if (nameFileRead.indexOf("SP.") > -1)
            	parse(7, 0, 10, 11, 12, 13, 5, oneFio, nameFileRead, 13, 2, 1, 3, 4, 0, 7000, 0, view_tr, view_pass, 8, 9, 6, numRow);
            else
            	parse(7, 8, 13, 14, 15, 0, 5, oneFio, nameFileRead, 15, 2, 1, 3, 4, 10, 6000, 11, view_tr, view_pass, 9, 12, 6, numRow);
        }
        catch (Exception e)
        {
            System.out.println("Error open file: " + nameFileRead);
            s += nameFileRead + "(" + n + "), ";
            error = true;
        }

        if (chekError()) return;
        
    }

	private static void readFile2(String nameFileRead, int numRow) {
		String dat = "";
		String month = "";
		String year = "";
    	
        try
        {
    		Log log = new Log("${pathProject}/conf/BufferPool.properties");
    		BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
    		Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
    		Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
    		CachedRowSet crs = new CachedRowSet(log2); 
    		String sql;       

       	 	sql = prop.getString("sql.sel_monthBundle"); 
    		crs.setSQL(sql);		//	����.���� => 
           	crs.addParameter(doroga);
    		if (bp.executeQuery(crs) && !crs.isError()) {
    			// ���� ���������� ������� ������ 0, �� ...
    			if (crs.size() > 0)
    				if (crs.next()) dat = crs.getString(1);
    		}
    		// ���� ���� ������, �� ����������� 
    		else
    			log2.error(crs.getException());
    		bp.close();
    	   	 	
    		month = dat.substring(5,7);		// ��������� �����
    		year = dat.substring(0,4);		// ��������� ���
    		
            br = new BufferedReader(new FileReader(dir + doroga + "_" + year + "." + month + "\\" + nameFileRead)); // ����� � ����� ���������� ����� - nameFileRead
            
            if (nameFileRead.indexOf("PP") > -1)
            	parse_pp(5, 6, 12, 13, 14, 15, 2, nameFileRead, 15, 1, 8, 4, 7, 3, numRow);
            else
            	parse_pr(5, 8, 9, 10, 11, 2, nameFileRead, 11, 1, 8, 7000, 4, 6, 7, 3, numRow);
        }
        catch (Exception e)
        {
            System.out.println("Error open file: " + nameFileRead);
            s += nameFileRead + ", ";
            error = true;
        }

        if (chekError()) return;
        
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
   	 								System.out.println("��� �������");
   	 						else
                       			log2.error(crs.getException());
   	 						sql = prop.getString("sql.sel_reg"); 
   	 						crs.setSQL(sql);
   	 						crs.addParameter(crs1.getInteger(6));
   	 						if (bp.executeQuery(crs) && !crs.isError())
   	 							if (crs.size() > 0)
   	   	 							while (crs.next()) registr = crs.getString(1);
   	 							else
   	 								System.out.println("��� �������");
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
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_codPodr"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(7));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) code_podr = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namTransp"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(8));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) transp = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namFormTr"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(9));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) form_tr = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getString(3));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) sta_o = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getString(4));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) sta_n = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
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
   	 				System.out.println("��� �������");
   	 		else
   	 			log2.error(crs1.getException());
 			if ((st[12] == null) || (st[12] == ""))
	   	 		nstr = crs1.size() + 2;
 			else
	   	 		nstr = crs1.size()*2 + 2;
	 		if (num_p == 6000)
	 			bw.write("<tr><td colspan=\"16\" align=\"right\" ><b>�����</b></td><td colspan=\"1\" >=����(Q3:Q" + nstr + ")</td></tr>");
	 		else
	 			bw.write("<tr><td colspan=\"13\" align=\"right\" ><b>�����</b></td><td colspan=\"2\" >=����(N3:O" + nstr + ")</td></tr>");
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
    
    private static void writeViewTr(String fullNameFile, String year, String month, int vt, int codOr, BufferPool bp, Log log, Log log2, Properties prop, int doroga) {
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
            sql = prop.getString("sql.sel_bundleVTr"); 
            crs1.setSQL(sql);
            crs1.addParameter(year + "-" + month + "-31");
            crs1.addParameter(year + "-" + month + "-01");
            crs1.addParameter(vt);
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
 						sql = prop.getString("sql.sel_vDoc"); 
 						crs.setSQL(sql);
 						crs.addParameter(crs1.getInteger(5));
 						if (bp.executeQuery(crs) && !crs.isError())
 							if (crs.size() > 0) 
 								while (crs.next()) view_doc = crs.getString(1);
 							else
 								System.out.println("��� �������");
 						else
                   			log2.error(crs.getException());
 						sql = prop.getString("sql.sel_reg"); 
 						crs.setSQL(sql);
 						crs.addParameter(crs1.getInteger(6));
 						if (bp.executeQuery(crs) && !crs.isError())
 							if (crs.size() > 0)
   	 							while (crs.next()) registr = crs.getString(1);
 							else
 								System.out.println("��� �������");
 						else
 							log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_codOrg"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(codOr);
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) code_org = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_codPodr"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(7));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) code_podr = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namTransp"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(8));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) transp = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namFormTr"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getInteger(9));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) form_tr = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getString(3));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) sta_o = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	   	 				crs.addParameter(crs1.getString(4));
   	   	 				if (bp.executeQuery(crs) && !crs.isError())
   	   	 					if (crs.size() > 0)
   	   	 						while (crs.next()) sta_n = crs.getString(1);
   	   	 					else
   	   	 						System.out.println("��� �������");
   	   	 				else
   	   	 					log2.error(crs.getException());
	   	 				str = "<tr valign=\"top\" align=\"center\" ><td align=\"left\" >" + transp + "</td><td>" + form_tr + "</td><td>" + registr + "</td><td>" + 
	   	 				st[2] + "</td><td class=\"number\" >" + st[14] + "</td><td align=\"left\" >" + view_doc + "</td><td>" + st[1] + "</td><td>76</td><td>2010/03</td><td>" + 
	   	 				code_org + "</td><td>" + code_org + "</td><td>" + code_podr + "</td><td>" + st[4] + "</td><td align=\"left\" >" + sta_n + "</td><td>" + 
	   	 				st[3] + "</td><td align=\"left\" >" + sta_o + "</td><td align=\"left\" >" + st[13] + "<br>" + st[12] + "</td><td>" + 
	   	 				st[10] + "</td><td class=\"number\">" + st[16] + "</td><td class=\"number\" >" + st[11] + "</td></tr>";
   	   	 				bw.write(str);
   	 					bw.newLine();
   	 				}
   	 			else
   	 				System.out.println("��� �������");
   	 		else
   	 			log2.error(crs1.getException());
 			if ((st[12] == null) || (st[12] == ""))
	   	 		nstr = crs1.size() + 2;
 			else
	   	 		nstr = crs1.size()*2 + 2;
	 		bw.write("<tr><td colspan=\"18\" align=\"right\" ><b>�����</b></td><td colspan=\"2\" >=����(S3:T" + nstr + ")</td></tr>");
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
    
    private static void itogo(BufferPool bp, Log log, Log log2, Properties prop, int kolOrg, int kolVPass, String year, String month) {
    	String fullNameFile = dir + "\\" + doroga + "_" + year + "." + month + "\\Itogo.xls";
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
					"<tr><th width=90 >��� �����������</th>" +
					"<th width=125 >��������� �����������, ������������� ��� ���</th>" + 
					"<th width=177 >����, ����������� �� ��������� ���������� � ����������� ��� ���</th>" + 
					"<th width=90 >����������</th>" + 
					"<th width=76 >��������� ��� ���</th>" + 
					"<th width=60 >���� ����</th>" + 
					"<th width=143 >����, �������� ������� ������� � ���������� �.�. ������</th>" + 
					"<th width=65 >�����</th></tr>");
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
                    				System.out.println("��� ����� ��� ���������� ���� ���������");
                    		else
                    			log2.error(crs1.getException());
                    		str = str + "<td class=\"number\" align=\"center\" >" + summ + "</td>";
                    		summ = 0.0;
                    	}
                    	nstr++;
                    	str = str + "<td>=����(B" + nstr + ":G" + nstr + ")</td></tr>";
                    	bw.write(str);
       				}
       				else
        				System.out.println("��� �����������");
        		else
        			log2.error(crs.getException());
            }
			nstr = kolOrg + 1;
			bw.write("<tr><td align=\"right\" ><b>�����</b></td><td>=����(B2:B" + nstr + ")</td><td>=����(C2:C" + nstr + ")</td><td>=����(D2:D" + nstr + 
					")</td><td>=����(E2:E" + nstr + ")</td><td>=����(F2:F" + nstr + ")</td><td>=����(G2:G" + nstr + ")</td><td>=����(H2:H" + nstr + 
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

    public static void createFile() {
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
		   "<tr><th rowspan=\"2\" width=120 >������������ �����������</th>" +
		   "<th rowspan=\"2\" width=60 >����� ������ ��.</th>" + 
		   "<th rowspan=\"2\" width=108 >��� �����������</th>" +
		   "<th rowspan=\"2\" width=112 >��� �������������</th>" +
		   "<th rowspan=\"2\" width=160 >�������, �������� ���������</th>" + 
		   "<th rowspan=\"2\" width=108 >����� ������. ����������</th>" + 
		   "<th rowspan=\"2\" width=94 >���� ���������� ���</th>" + 
		   "<th rowspan=\"2\" width=94 >���� ������ �������� ����������</th>" + 
		   "<th rowspan=\"2\" width=71 >����� ������. ������</th>" + 
		   "<th rowspan=\"2\" width=92 >������ ����������</th>" + 
		   "<th rowspan=\"2\" width=80 >��� ���������</th>" + 
		   "<th rowspan=\"2\" width=85 >���������� ����������</th>" + 
		   "<th colspan=\"4\" >������� (����)</th>" + 
		   "<th rowspan=\"2\" >���������</th>" +
		   "</tr><th colspan=\"2\">�����������</th><th colspan=\"2\">����������</th></tr>";
        String title7 = "<html>\n" + 
		   "   <style>.number {mso-number-format:\"0\";}</style><body>\n" + 
		   "       <table border=\"1\" >\n" + 
		   "<tr><th rowspan=\"2\" width=120 >������������ �����������</th>" +
		   "<th rowspan=\"2\" width=60 >����� ������ ��.</th>" + 
		   "<th rowspan=\"2\" width=108 >��� �����������</th>" +
		   "<th rowspan=\"2\" width=112 >��� �������������</th>" +
		   "<th rowspan=\"2\" width=160 >�������, �������� ���������, �������, �������� ���������, �� ���� ���������</th>" + 
		   "<th rowspan=\"2\" width=115 >����� ������. ����������</th>" + 
		   "<th rowspan=\"2\" width=94 >���� ���������� ���</th>" + 
		   "<th rowspan=\"2\" width=71 >����� ������. ������</th>" + 
		   "<th rowspan=\"2\" width=85 >���������� ������� ��� ��������</th>" +		   
		   "<th colspan=\"4\">������� (����)</th>" + 
		   "<th rowspan=\"2\">���������</th>" +
		   "<th rowspan=\"2\" width=111 >������� (����� ������������� ����������)</th>" +
		   "</tr><th colspan=\"2\">�����������</th><th colspan=\"2\">����������</th></tr>";
		
        Log log = new Log("${pathProject}/conf/BufferPool.properties");
        BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
        Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
        Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
        CachedRowSet crs = new CachedRowSet(log2);
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;       

   	 	File f;
	//	select * from bundle where data<'2009-07-31' and data>'2009-07-01'
   	 	sql = prop.getString("sql.sel_datBundle"); 
   	 	crs.setSQL(sql);		//	����.���� => 
      	crs.addParameter(doroga);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 	{
   	 		// ���� ���������� ������� ������ 0, �� ...
   	 		if (crs.size() > 0)
   	 			if (crs.next()) dat = crs.getString(1);
   	 	}
   	 	// ���� ���� ������, �� ����������� 
   	 	else
   	 		log2.error(crs.getException());
        month = dat.substring(5,7);		// ��������� �����
        year = dat.substring(0,4);		// ��������� ���

   // ���-�� ����������� (��� �������� � �����)
        sql = prop.getString("sql.sel_countOrg"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 	{
   	 		if (crs.size() > 0)
   	 			if (crs.next()) kolOrg = crs.getInteger(1);
   	 	}
   	 	else
   	 		log2.error(crs.getException());
        
   // ���-�� ����� ���������� (��� �������� � �����)
        sql = prop.getString("sql.sel_countVPass"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 		if (crs.size() > 0)
   	 			if (crs.next()) kolVPass = crs.getInteger(1);
   	 		else
   	 			System.out.println("��� �������");
   	 	else
   	 		log2.error(crs.getException());
        
	 	for (int i = 1; i <= kolVPass; i++) {
        	// ������� ����� � �������� ����������: viewPass
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
            	        		// �������� ���� � �����: codeOrg_numP.xls
        	    			sql = prop.getString("sql.sel_codOrg"); 
        	    			crs.setSQL(sql);
        	    			crs.addParameter(j);
        	        	    if (bp.executeQuery(crs) && !crs.isError())
        	        	    	if (crs.size() > 0) {
        	        	    		if (crs.next()) 
        	        	    			fullNameFile = dir + doroga + "_" + year + "." + month + "\\" + subDir + "\\" + crs.getString(1) + "_" + k + ".xls";
        	        	    	}
        	        	    	else
        	        	    		System.out.println("��� �������");
        	        	    else
        	        	    	System.out.println(crs.getException());
        	    			if (k == 6000)
        	    				writeTitle(title6, fullNameFile);
        	    			else
        	    				writeTitle(title7, fullNameFile);
        	    			write(fullNameFile, k, year, month, i, j, bp, log, log2, prop, doroga);
        	    		}
        	    	else
        	    		System.out.println("��� �������");
        	    else
        	    	log2.error(crs1.getException());
       	 	}
        }
     
        // �������� �������
        itogo(bp, log, log2, prop, kolOrg, kolVPass, year, month);
	}

    public static void createFileViewTr() {
		String subDir = "";
		String dat = "";
		String month = "";
		String year = "";
		String fullNameFile = "";
		int kolVTransp = 0;
		int j = 0;
        String title = "<html>\n" + 
		   "   <style>.number {mso-number-format:\"0\";}</style><body>\n" + 
		   "       <table border=\"1\" >\n" + 
		   "<tr><th rowspan=\"2\" width=120 >������������ �������� (��������)</th>" +
		   "<th rowspan=\"2\" width=60 >����� ������������� ����������</th>" + 
		   "<th rowspan=\"2\" width=92 >������ ���������� ���������� ���������</th>" + 
		   "<th rowspan=\"2\" width=71 >����� ������������ ������ ��� ��������</th>" + 
		   "<th rowspan=\"2\" width=108 >N ������������� ����������</th>" + 
		   "<th rowspan=\"2\" width=80 >��� ���������</th>" + 
		   "<th rowspan=\"2\" width=94 >���� ���������� ���������� ���������</th>" + 
		   "<th rowspan=\"2\" width=108 >������ ����������</th>" +
		   "<th rowspan=\"2\" width=80 >�����</th>" + 
		   "<th rowspan=\"2\" width=108 >����� �����</th>" +
		   "<th rowspan=\"2\" width=108 >��� �����������, � ����� �������� ������� ��������</th>" +
		   "<th rowspan=\"2\" width=112 >��� �������������, ��������� ������������ ����������</th>" +
		   "<th colspan=\"4\" >������� (����)</th>" + 
		   "<th rowspan=\"2\" width=160 >�������, ��������</th>" + 
		   "<th rowspan=\"2\" width=85 >���-�� ����������</th>" + 
		   "<th rowspan=\"2\" width=111 >�������, ���.</th>" +
		   "<th rowspan=\"2\">��������� �������, ���������� ����������,���.</th>" +
		   "</tr><th colspan=\"2\">�����������</th><th colspan=\"2\">����������</th></tr>";
		
        Log log = new Log("${pathProject}/conf/BufferPool.properties");
        BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
        Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
        Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
        CachedRowSet crs = new CachedRowSet(log2);
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;       

   	 	File f;
	//	select * from bundle where data<'2009-07-31' and data>'2009-07-01'
   	 	sql = prop.getString("sql.sel_datBundle"); 
   	 	crs.setSQL(sql);		//	����.���� => 
      	crs.addParameter(doroga);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 	{
   	 		// ���� ���������� ������� ������ 0, �� ...
   	 		if (crs.size() > 0)
   	 			if (crs.next()) dat = crs.getString(1);
   	 	}
   	 	// ���� ���� ������, �� ����������� 
   	 	else
   	 		log2.error(crs.getException());
        month = dat.substring(5,7);		// ��������� �����
        year = dat.substring(0,4);		// ��������� ���

   // ���-�� ����� ��������� (��� �������� � �����)
        sql = prop.getString("sql.sel_countVTransp"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 		if (crs.size() > 0)
   	 			if (crs.next()) kolVTransp = crs.getInteger(1);
   	 		else
   	 			System.out.println("��� �������");
   	 	else
   	 		log2.error(crs.getException());
        
	 	for (int i = 2; i <= kolVTransp; i++) {
        	// ������� ����� � �������� ����������: viewTr
            sql = prop.getString("sql.sel_vTr"); 
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

   	 		sql = prop.getString("sql.spisokOrgVTr"); 
   	 		crs1.setSQL(sql);
   	 		crs1.addParameter(year + "-" + month + "-31");
   	 		crs1.addParameter(year + "-" + month + "-01");
   	 		crs1.addParameter(i);
   	 		crs1.addParameter(doroga);
      	    if (bp.executeQuery(crs1) && !crs1.isError())
       	    	if (crs1.size() > 0)
       	    		while (crs1.next()) {
           	        	j = crs1.getInteger(1);
           	        		// �������� ���� � �����: codeOrg_numP.xls
       	    			sql = prop.getString("sql.sel_codOrg"); 
       	    			crs.setSQL(sql);
       	    			crs.addParameter(j);
       	        	    if (bp.executeQuery(crs) && !crs.isError())
       	        	    	if (crs.size() > 0) {
       	        	    		if (crs.next()) 
       	        	    			fullNameFile = dir + doroga + "_" + year + "." + month + "\\" + subDir + "\\" + crs.getString(1) + ".xls";
       	        	    	}
       	        	    	else
       	        	    		System.out.println("��� �������");
       	        	    else
       	        	    	System.out.println(crs.getException());
   	    				writeTitle(title, fullNameFile);
       	    			writeViewTr(fullNameFile, year, month, i, j, bp, log, log2, prop, doroga);
       	    		}
       	    	else
      	    		System.out.println("��� �������");
       	    else
       	    	log2.error(crs1.getException());
        }
	}

    public static void main(String[] args) {
        int timeB = 0;
        int timeEnd = 0;
        
		Log log = new Log("${pathProject}/conf/BufferPool.properties");
		BufferPool bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
		Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
		Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
		CachedRowSet crs = new CachedRowSet(log2); 
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

		bp.close();
	   	 	
	// readFile(boolean oneFio, String nameFileRead, String view_tr, String view_pass, int numRow)
		readFile(true, "BSLP.txt", "�� ������� ����������� OAO P��", "���� ����", 13);
		readFile(true, "BSLSP.txt", "�� ������� ����������� OAO P��", "���� ����", 13);
		readFile(true, "DSLP.txt", "�� ��������� � OAO P��", "���� ����", 20);
		readFile(true, "DSLSP.txt", "�� ��������� � OAO P��", "���� ����", 20);
		readFile(true, "FSLP.txt", "", "��������� �����������, ������������� ��� ���", 20);
		readFile(true, "FSLSP.txt", "", "��������� �����������, ������������� ��� ���", 20);
		readFile(false, "ILNP.txt", "�� ������ �����������", "����, ����������� �� ��������� ���������� � ����������� ��� ���", 21);
		readFile(false, "ILNSP.txt", "�� ������ �����������", "����, ����������� �� ��������� ���������� � ����������� ��� ���", 21);
		readFile(true, "NLNP.txt", "�� ������ �����������", "����, �������� ������� ������� � ���������� �.�. ������", 13);
		readFile(true, "NLNSP.txt", "�� ������ �����������", "����, �������� ������� ������� � ���������� �.�. ������", 13);
		readFile(true, "PLNP.txt", "", "����������", 16);
		readFile(true, "PLNSP.txt", "", "����������", 16); 
		readFile(true, "RLNP.txt", "�� ������ �����������", "��������� ��� ���", 15);
		readFile(true, "RLNSP.txt", "�� ������ �����������", "��������� ��� ���", 15);
		readFile(true, "RRUP.txt", "�� ���� �� ������/�����", "��������� ��� ���", 15);
		readFile(true, "RRUSP.txt", "�� ���� �� ������/�����", "��������� ��� ���", 15);
		readFile(true, "RSNP.txt", "���������", "��������� ��� ���", 15);
		readFile(true, "RSNSP.txt", "���������", "��������� ��� ���", 15);

	// readFile2(String nameFileRead, int numRow)
		readFile2("PP.txt", 15);
		readFile2("PPRZD.txt", 18);
		readFile2("PR.txt", 13);
		readFile2("PRRZD.txt", 16);

   	 	System.out.println(s);
   	 	if (s == "")
   	 		createFile();
   	 	if (s == "")
   	 		createFileViewTr();
   	 	
		log = new Log("${pathProject}/conf/BufferPool.properties");
		bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
		log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
		prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
		crs = new CachedRowSet(log2); 

		sql = prop.getString("sql.time"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 	{
   	 		if (crs.size() > 0)
   	 			if (crs.next()) timeEnd = crs.getInteger(1);
   	 	}
   	 	else
   	 		log2.error(crs.getException());
   	 	bp.close();

   	 	System.out.println("����� ������ ���������: " + (timeEnd-timeB)/60 + " ����� " + ((timeEnd-timeB)-((timeEnd-timeB)/60)*60) + " �����");
	}
}