package doc;

import java.util.Vector;

public class JTableDefault {

	// заголовки таблицы
	public Vector getHeader(String table) {
		Vector column = new Vector();
		if (table.equals("OneLetter")) { // на панели обработки одиночных конвертов
			column.add("Предприятие");
			column.add("Кол-во");
			column.add(" ");
		}
		if (table.equals("LastID")) { // список предприятий с ID последнего конверта на панели Поиск ID конвертов
			column.add("Предприятие");
			column.add("ID последнего конверта");
			column.add(" ");
		}
		if (table.equals("AllID")) { // все ID конвертов по предприятию на панели Поиск ID конвертов
			column.add("Дата отправки");
			column.add("ID конверта");
			column.add(" ");
		}
		return column;
	}
	
	// содержимое таблицы (пусто)
	public Vector getDataNull() {
		Vector dat = new Vector();
		return dat;
	}
	
}
