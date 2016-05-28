/*
 * DisplayLocation.java. Created on 10.12.2008 9:41:03
 *
 * Copyright 2006 SAV Soft, Inc. All rights reserved.
 */
package ru.svrw.sql.utils;

import ru.svrw.data.Records;
import ru.svrw.sql.ds.BufferPool;
import java.sql.*;
import ru.svrw.sql.rowset.CachedRowSet;

public class DisplayLocation
{
    private Records records;
    private BufferPool bp;
    private int commandsEX;
    private int ifcaRet;
    private int ifcaRes;
    private int xsBytes;
    private String errorMsg; 
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        BufferPool bp = new BufferPool("${pathProject}/conf/asoup2.properties");
        DisplayLocation dl = new DisplayLocation(bp);
        System.out.println(dl.getRecords().toString("\n"));
    }
    
    public DisplayLocation(BufferPool bp)
    {
        this.bp = bp;
        commandsEX = 0;
        ifcaRet = 0;
        ifcaRes = 0;
        xsBytes = 0;
        errorMsg = null;
        
        execute();
    }
    
    public void execute()
    {
        records = new Records();
        Connection con = bp.getConnection(new CachedRowSet());        
        CallableStatement cs = null;
        ResultSet rs = null;
        try
        {
            String command = "CALL SYSPROC.DSNACCMD(?, ?, ?, ?, ?, ?, ?, ?)";
            String c = "-DISPLAY LOCATION";
            cs = con.prepareCall(command);

            cs.setString(1, c);
            cs.setInt(2, c.length());
            cs.setString(3, "NO");
            cs.registerOutParameter(4, Types.INTEGER);
            cs.registerOutParameter(5, Types.INTEGER);
            cs.registerOutParameter(6, Types.INTEGER);
            cs.registerOutParameter(7, Types.INTEGER);
            cs.registerOutParameter(8, Types.VARCHAR);
            
            System.out.println("1");
            
            if (cs.execute())
            {
                System.out.println("2");
                rs = cs.getResultSet();
                System.out.println("3");
                String tmp;
                while (rs.next())
                {
//                    System.out.print(rs.getString(1) + ", ");
//                    System.out.println(rs.getString(2));
                    tmp = rs.getString(2);
                    if (tmp.trim().length() == 67)
                    {
//                        records.addRecord();
//                        records.addField(tmp);
                        records.addRecord();
                        records.addField(tmp.substring(0, 17).trim());
                        records.addField(tmp.substring(17, 26).trim());
                        records.addField(tmp.substring(26, 42).trim());
                        records.addField(tmp.substring(42, 53).trim());
                        records.addField(tmp.substring(53, 61).trim());
                        records.addField(tmp.substring(61).trim());
//                    System.out.print(rs.getString(3) + ", ");
//                    System.out.print(rs.getString(4) + ", ");
//                    System.out.println(rs.getString(5));
                    }
                }
                System.out.println("4");
            }
            else System.out.println("Not Execute!");
        }
        catch (Exception e)
        { 
            System.out.println(e.toString());
        }
        finally
        {
            try
            {
                if (rs!= null) rs.close();
                if (cs!= null) cs.close();
                if (con!= null) con.close();
                if (bp!= null) bp.close();
            } catch (Exception e) {};
        }
        System.out.println("5");
    }
    
    public Records getRecords()
    {
       return records; 
    }

    /**
     * @return the commandsEX
     */
    public int getCommandsEX()
    {
        return commandsEX;
    }

    /**
     * @return the ifcaRet
     */
    public int getIfcaRet()
    {
        return ifcaRet;
    }

    /**
     * @return the ifcaRes
     */
    public int getIfcaRes()
    {
        return ifcaRes;
    }

    /**
     * @return the xsBytes
     */
    public int getXsBytes()
    {
        return xsBytes;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg()
    {
        return errorMsg;
    }
    
    
}
