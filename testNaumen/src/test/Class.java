package test;

import java.util.*;

public class Class implements Comparable {
	
  public long modificationDate;
  public String className;
                           
  public Class(long modificationDate, String className) {
	  this.modificationDate = modificationDate;
	  this.className = className;
  }
                           
  /* Перегрузка метода compareTo */
  public int compareTo(Object obj) {
	  Class tmp = (Class)obj;
	  if (this.modificationDate < tmp.modificationDate) {
      /* текущее меньше полученного */
		  return -1;
	  } else if(this.modificationDate > tmp.modificationDate) {
      /* текущее больше полученного */
		  return 1;
	  }
	  if (this.className.compareToIgnoreCase(tmp.className) < 0) {
	      /* текущее меньше полученного */
		  return -1;
	  } else 
		  if(this.className.compareToIgnoreCase(tmp.className) > 0) {
			  /* текущее больше полученного */
			  return 1;
	  }
	  /* текущее равно полученному */
	  return 0;  
  }

}
