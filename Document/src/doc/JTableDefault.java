package doc;

import java.util.Vector;

public class JTableDefault {

	// ��������� �������
	public Vector getHeader(String table) {
		Vector column = new Vector();
		if (table.equals("OneLetter")) { // �� ������ ��������� ��������� ���������
			column.add("�����������");
			column.add("���-��");
			column.add(" ");
		}
		if (table.equals("LastID")) { // ������ ����������� � ID ���������� �������� �� ������ ����� ID ���������
			column.add("�����������");
			column.add("ID ���������� ��������");
			column.add(" ");
		}
		if (table.equals("AllID")) { // ��� ID ��������� �� ����������� �� ������ ����� ID ���������
			column.add("���� ��������");
			column.add("ID ��������");
			column.add(" ");
		}
		return column;
	}
	
	// ���������� ������� (�����)
	public Vector getDataNull() {
		Vector dat = new Vector();
		return dat;
	}
	
}
