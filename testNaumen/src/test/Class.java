package test;

import java.util.*;

public class Class implements Comparable {
	
  public long modificationDate;
  public String className;
                           
  public Class(long modificationDate, String className) {
	  this.modificationDate = modificationDate;
	  this.className = className;
  }
                           
  /* ���������� ������ compareTo */
  public int compareTo(Object obj) {
	  Class tmp = (Class)obj;
	  if (this.modificationDate < tmp.modificationDate) {
      /* ������� ������ ����������� */
		  return -1;
	  } else if(this.modificationDate > tmp.modificationDate) {
      /* ������� ������ ����������� */
		  return 1;
	  }
	  if (this.className.compareToIgnoreCase(tmp.className) < 0) {
	      /* ������� ������ ����������� */
		  return -1;
	  } else 
		  if(this.className.compareToIgnoreCase(tmp.className) > 0) {
			  /* ������� ������ ����������� */
			  return 1;
	  }
	  /* ������� ����� ����������� */
	  return 0;  
  }

}
