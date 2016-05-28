package doc;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

import ru.svrw.eivc.util.*;
import ru.svrw.eivc.util.Properties;

public class DocumentStart extends Frame implements ActionListener {
	
	public static final String[] MONTHS = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

	private Button bStart;
	private String[] itemUser;
	private JComboBox users;
	private String login_old;
	private StringUtils su = new StringUtils(); 
	private Properties prop = new Properties(su.replaceSystemParameter("${pathProject}/conf/settings.properties"));

	DocumentStart() {
		setLayout(new GridBagLayout());
		itemUser = getItemUser();
		users = new JComboBox(itemUser);
		users.setSelectedIndex(0);
		add(new Label("Фамилия пользователя:"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		add(users, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		add(new Label(""), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
			bStart = new Button("Запустить");
			bStart.addActionListener(this);
		add(bStart, new GridBagConstraints(0, 2, 2, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		// Создание окна
		setTitle("Обработка финансовых документов");
		setSize(350, 190);
		setBackground(Color.getHSBColor(100, 200, 50));
		setForeground(Color.black);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		DocumentStart d = new DocumentStart();
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == bStart) {
			String login = (String) users.getSelectedItem();
			setDefaultUser(login);
			String[] itemYear = getItemYear();
			Interface inter = new Interface(itemUser, itemYear, login);
			setVisible(false);
		}
	}

	private String[] getItemYear() {
		try {
			String[] item = prop.getString("input.years").split(";");
			return item;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	} 

	private String[] getItemUser() {
		TreeSet<String> tSet = new TreeSet<String>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(su.replaceSystemParameter("${pathProject}/conf/users.xml")));
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("user");
            for (int je = 0; je < nodeLst.getLength(); je++) {
                Node fstNode = nodeLst.item(je);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elj = (Element)fstNode;
                    if (elj.getElementsByTagName("ivc").item(0).getChildNodes().item(0).getNodeValue().equals(prop.getString("input.ivc")))
                       	tSet.add(elj.getElementsByTagName("login").item(0).getChildNodes().item(0).getNodeValue());
                }
            }
        } catch (Exception e) {
            System.out.println("Error read file users.xml");
        }
        String[] item = new String[tSet.size()];
        int i = 0;
        for (String s : tSet) {
        	if (i == 0) {
        		item[i] = s.substring(1);
        		login_old = s.substring(1);
        	} else
        		item[i] = s;
        	i++;
        }
		return item;
	}

	private void setDefaultUser(String login) {
        try {
        	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(su.replaceSystemParameter("${pathProject}/conf/users.xml")));
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("user");
            for (int je = 0; je < nodeLst.getLength(); je++) {
                Node fstNode = nodeLst.item(je);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elj = (Element)fstNode;
                    if (elj.getElementsByTagName("ivc").item(0).getChildNodes().item(0).getNodeValue().equals(prop.getString("input.ivc"))) {
                    	if (elj.getElementsByTagName("login").item(0).getChildNodes().item(0).getNodeValue().equals("_" + login_old))
                    		elj.getElementsByTagName("login").item(0).getChildNodes().item(0).setNodeValue(login_old);
                    	if (elj.getElementsByTagName("login").item(0).getChildNodes().item(0).getNodeValue().equals(login))
                    		elj.getElementsByTagName("login").item(0).getChildNodes().item(0).setNodeValue("_" + login);
                    }
                }
            }
            
/*        	//set up a transformer
        	TransformerFactory transfac = TransformerFactory.newInstance();
        	Transformer trans = transfac.newTransformer();
         
            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();
         
        	byte buf[] = xmlString.getBytes();
        	OutputStream f0 = new FileOutputStream(su.replaceSystemParameter("${pathProject}/conf/users.xml"));
        	for(int i=0;i<buf .length;i++) {
        	   f0.write(buf[i]);
        	}
        	f0.close();
        	buf = null; */
            
            Transformer t = TransformerFactory.newInstance().newTransformer();
            StreamResult sr = new StreamResult(new OutputStreamWriter(new FileOutputStream(new File(su.replaceSystemParameter("${pathProject}/conf/users.xml"))), "utf-8"));
            t.transform(new DOMSource(doc), sr);
        } catch (Exception e) {
            System.out.println(e);
        }
	}
}
