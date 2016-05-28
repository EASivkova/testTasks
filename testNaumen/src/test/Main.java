package test;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String line;
		File startFolder = null;
		String stFolder;
		System.out.println("Dlya zaversheniya raboty programmy vvedite: exit");
		for (;;) {
			System.out.println("Vvedite adres kataloga s klassami:");
			line = in.readLine();
			if (line == null || line.equals("exit"))
				break;
			stFolder = line;
			startFolder = new File(stFolder);
			if (!startFolder.exists())
				System.out.println("Takogo adresa ne suschestvuet");
			else
				break;
		}
		if (line != null && !line.equals("exit")) {
			Vector<File> files = new Vector<File>();
			Vector<File> catalogs = new Vector<File>();
			catalogs.add(startFolder);
			for (int i = 0; i < catalogs.size(); i++) {
				String[] folder = catalogs.elementAt(i).list();
	    		for (int j = 0; j < folder.length; j++) {
	    			File f = new File(catalogs.elementAt(i).getAbsolutePath() + File.separator + folder[j]);
	    			if (f.isDirectory()) {
	    				catalogs.add(f);
	    			} else {
						if (Pattern.compile(".java$").matcher(folder[j]).find())
	    					files.add(f);
	    			}
	    		}
			}
			int countFiles = files.size();
			String[] namesClass = new String[countFiles];
			long[] ms = new long[countFiles];
			for (int i = 0; i < countFiles; i++) {
				namesClass[i] = files.elementAt(i).getName();
				ms[i] = files.elementAt(i).lastModified();
			}
			System.out.println("Spisok klassov sostavlen.");
			ISearch is = new ISearch();
			is.refresh(namesClass, ms);
			System.out.println("Spisok klassov otsortirovan.");
			for (;;) {
				System.out.println("Vvedite naimenovanie iskomogo klassa:");
				String start = in.readLine();
				if (start == null || start.equals("exit"))
					break;
				try {
					String[] classes = is.guess(start);
					if (classes.length > 0) {
						System.out.println("Spisok naidenyh klassov:");
						for (int i = 0; i < classes.length; i++)
							System.out.println(classes[i]);
					} else
						System.out.println("Klassy ne naideny.");
					System.out.println();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
		System.out.println("Programma zavershila svoyu rabotu.");
	}
	
}

class ISearch implements ISearcher {
	
/*	Class[] classes;
	
	public void refresh(String[] classNames, long[] modificationDates){
		classes = new Class[classNames.length];
		for (int i = 0; i < classNames.length; i++) {
			classes[i] = new Class(modificationDates[i], classNames[i]);
		}
		Arrays.sort(classes);
	}

	public String[] guess(String start) {
		Vector<String> el = new Vector<String>();
		int j = 0;
		for (int i = 0; (i < classes.length && j < 12); i++) {
			if (classes[i].className.startsWith(start)) {
				el.add(classes[i].className);
//				System.out.println(classes[i].className + " " + classes[i].modificationDate);
				j++;
			}
		}
		String[] elements = new String[el.size()];
		for (int i = 0; i < el.size(); i++)
			elements[i] = (String) el.elementAt(i);
		return elements;
	}
 */
	Map<Long, String> tmap = new TreeMap<Long, String>();
	
	public void refresh(String[] classNames, long[] modificationDates){
		for (int i = 0; i < classNames.length; i++) {
			tmap.put(modificationDates[i], classNames[i]);
		}
	}

	public String[] guess(String start) {
		Vector<String> el = new Vector<String>();
		int j = 0;
		for (Long dat : tmap.keySet()) {
			if (tmap.get(dat).startsWith(start)) {
				el.add(tmap.get(dat));
		//		System.out.println(tmap.get(dat) + " " + dat);
				j++;
			}
		}
		String[] elements = new String[el.size()];
		for (int i = 0; i < el.size() && i < 12; i++)
			elements[i] = (String) el.elementAt(i);
		return elements;
	}
}