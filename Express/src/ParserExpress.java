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
import java.util.Vector;

public class ParserExpress
{
    static BufferedReader br;
    static BufferedWriter bw;
    static PrintWriter out;
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

    public static void main(String[] args)
    {
        String strTmp = "";
        String[] strArr;
        String nameFileRead = args[0];
        String nameFileWrite = "";
        
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
        
    }
}
