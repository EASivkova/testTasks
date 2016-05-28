package doc;

import java.io.*;
import java.net.*;

import ru.svrw.eivc.date.Calendar;
import ru.svrw.eivc.util.Properties;
import ru.svrw.eivc.util.StringUtils;

public class Internet {

	private StringUtils su = new StringUtils(); 
	private Properties prop = new Properties(su.replaceSystemParameter("${pathProject}/conf/settings.properties"));

    private String proxyHost = prop.getString("internet.proxyHost");
    private Integer proxyPort = Integer.parseInt(prop.getString("internet.proxyPort"));
    private String barcode;
    private String textHTML = "";
    private String[] response = new String[4];
    
    public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

    // выход на сайт Почты России для сбора информации по ID конверта
    private void connect() {
        try {
        	URL dataURL = new URL("http://www.russianpost.ru/resp_engine.aspx?Path=rp/servise/ru/home/postuslug/trackingpo");
            URLConnection conn=dataURL.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(100);
            PrintWriter out = new PrintWriter(byteStream,true);
            Calendar today = new Calendar();
            String month = "" + today.getMonth();
            if (today.getMonth() < 10)
            	month = "0" + today.getMonth();
            String day = "" + today.getDay();
            if (today.getDay() < 10)
            	day = "0" + today.getDay();
            out.print("BarCode=" + barcode + "&searchsign=1&CDAY=" + day + "&CMONTH=" + month + "&CYEAR=" + today.getYear());
            out.flush();
            conn.setRequestProperty("Content-Length", String.valueOf(byteStream.size()));
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byteStream.writeTo(conn.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            textHTML = "";
            while((line=in.readLine())!=null)
            	textHTML += line+"\n";
        } catch(Exception ee){
        	System.out.println(ee);
        	textHTML = ee.toString();
        }
	}
    
    // тест соединения с инернет
    public boolean testing() {
        try {
        	URL dataURL = new URL("http://www.russianpost.ru/resp_engine.aspx?Path=rp/servise/ru/home/postuslug/trackingpo");
            URLConnection conn=dataURL.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)));
            conn.connect();
            return true;
        } catch(Exception ee){
        	return false;
        }
    }
	
    // парсинг ответа
    private String parseHTML(boolean data) {
    	System.out.println("1");
       	connect();
       	if (textHTML.indexOf("Network is unreachable: connect") >= 0)
       		return response[0];
       	else {
       		System.out.println(textHTML);
            if (textHTML.indexOf("<table class=") < 0) {
            	return response[1];
            } else {
            	textHTML = textHTML.substring(textHTML.indexOf("<table class="), textHTML.indexOf("</table>", textHTML.indexOf("<table class=")) + 8);
            	textHTML = textHTML.substring(0, textHTML.indexOf("</tr>") + 5) + textHTML.substring(textHTML.lastIndexOf("<tr"));
            	if (textHTML.indexOf("javascript:Wind(") >= 0)
            		return (response[2] + (data?textHTML.substring(textHTML.indexOf("javascript:Wind(") - 33, textHTML.indexOf("javascript:Wind(") - 23):""));
            	else
            		return response[3];
            }
       	}
	}
	
    // варианты ответа для XLS файла за период
    public String forXLS() {
    	response[0] = "Нет соединения с Internet"; 
    	response[1] = "Информация об объекте " + barcode + " на сайте отсутствует"; 
    	response[2] = "Вручение ";
    	response[3] = "Не вручено";
    	return parseHTML(true);
	}
	
    // варианты ответа для таблиц
    public String forJTable() {
    	response[0] = "Нет соединения с Internet. Удостовертесь, что VIPNet установлен в режим Внешняя конфигурация. "; 
    	response[1] = "?"; 
    	response[2] = "+";
    	response[3] = "-";
    	return parseHTML(false);
	}
	
}
