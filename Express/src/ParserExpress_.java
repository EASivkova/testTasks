/*
 * ParserExpress_.java. Created on 18.08.2009
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
import java.io.FileReader;
import java.io.IOException;

import ru.svrw.logged.Log;
import ru.svrw.sql.rowset.CachedRowSet;
import ru.svrw.sql.ds.BufferPool;
import ru.svrw.util.Properties;

public class ParserExpress_ {

    static BufferedReader br;
    static boolean error = false;

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
    		int kol, int num_tr, int numRow, int doroga) {
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
    		int direct, int registr, int privilege, int num_doc, int num_dpr, int numRow, int doroga) {
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
    		int direct, int registr, int num_p, int privilege, int num_doc, int kol, int num_dpr, int numRow, int doroga) {
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
    
	private static void readFile(String adress, boolean oneFio, String nameFileRead, String view_tr, String view_pass, int numRow, int doroga) {
    	
        try
        {
            br = new BufferedReader(new FileReader(adress + nameFileRead)); // ����� � ����� ���������� ����� - nameFileRead
            
            if (nameFileRead.indexOf("SP.") > -1)
            	parse(7, 0, 10, 11, 12, 13, 5, oneFio, nameFileRead, 13, 2, 1, 3, 4, 0, 7000, 0, view_tr, view_pass, 8, 9, 6, numRow, doroga);
            else
            	parse(7, 8, 13, 14, 15, 0, 5, oneFio, nameFileRead, 15, 2, 1, 3, 4, 10, 6000, 11, view_tr, view_pass, 9, 12, 6, numRow, doroga);
        }
        catch (Exception e)
        {
            System.out.println("Error open file: " + nameFileRead);
            error = true;
        }

        if (chekError()) return;
        
    }

	private static void readFile2(String adress, String nameFileRead, int numRow, int doroga) {
    	
        try
        {
            br = new BufferedReader(new FileReader(adress + nameFileRead)); // ����� � ����� ���������� ����� - nameFileRead
            
            if (nameFileRead.indexOf("PP") > -1)
            	parse_pp(5, 6, 12, 13, 14, 15, 2, nameFileRead, 15, 1, 8, 4, 7, 3, numRow, doroga);
            else
            	parse_pr(5, 8, 9, 10, 11, 2, nameFileRead, 11, 1, 8, 7000, 4, 6, 7, 3, numRow, doroga);
        }
        catch (Exception e)
        {
            System.out.println("Error open file: " + nameFileRead);
            error = true;
        }

        if (chekError()) return;
        
    }

	public static void main(String[] args) {
		String adress = "C:\\Work\\Express\\";
		String dat = "";
		String month_ = "";
		String year = "";
		int doroga = 80;   // ��� ������, � ������� ��������� ������
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
	   	 	
		month_ = dat.substring(5,7);		// ��������� �����
		year = dat.substring(0,4);		// ��������� ���
		
	// readFile(String adress, boolean oneFio, String nameFileRead, String view_tr, String view_pass, int numRow)
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "BSLP.txt", "�� ������� ����������� OAO P��", "���� ����", 13, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "BSLSP.txt", "�� ������� ����������� OAO P��", "���� ����", 13, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "DSLP.txt", "�� ��������� � OAO P��", "���� ����", 20, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "DSLSP.txt", "�� ��������� � OAO P��", "���� ����", 20, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "FSLP.txt", "", "��������� �����������, ������������� ��� ���", 20, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "FSLSP.txt", "", "��������� �����������, ������������� ��� ���", 20, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", false, "ILNP.txt", "�� ������ �����������", "����, ����������� �� ��������� ���������� � ����������� ��� ���", 21, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", false, "ILNSP.txt", "�� ������ �����������", "����, ����������� �� ��������� ���������� � ����������� ��� ���", 21, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "NLNP.txt", "�� ������ �����������", "����, �������� ������� ������� � ���������� �.�. ������", 13, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "NLNSP.txt", "�� ������ �����������", "����, �������� ������� ������� � ���������� �.�. ������", 13, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "PLNP.txt", "", "����������", 16, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "PLNSP.txt", "", "����������", 16, doroga); 
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "RLNP.txt", "�� ������ �����������", "��������� ��� ���", 15, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "RLNSP.txt", "�� ������ �����������", "��������� ��� ���", 15, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "RRUP.txt", "�� ���� �� ������/�����", "��������� ��� ���", 15, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "RRUSP.txt", "�� ���� �� ������/�����", "��������� ��� ���", 15, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "RSNP.txt", "���������", "��������� ��� ���", 15, doroga);
		readFile(adress + doroga + "_" + year + "." + month_ + "\\", true, "RSNSP.txt", "���������", "��������� ��� ���", 15, doroga);

	// readFile2(String adress, String nameFileRead, int numRow)
/*		readFile2(adress + doroga + "_" + year + "." + month_ + "\\", "PP.txt", 15, doroga);
		readFile2(adress + doroga + "_" + year + "." + month_ + "\\", "PPRZD.txt", 18, doroga);
		readFile2(adress + doroga + "_" + year + "." + month_ + "\\", "PR.txt", 13, doroga);
		readFile2(adress + doroga + "_" + year + "." + month_ + "\\", "PRRZD.txt", 16, doroga);
*/
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