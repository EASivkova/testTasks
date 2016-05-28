import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TestDate
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
//        String dt = new SimpleDateFormat("dd-MMM-yy hh:mm").format(Calendar.getInstance().getTime());
//        System.out.println(dt);
/*        
        NumberFormat nf = new DecimalFormat("0.0");
        double d = 0.04166666667;
        int h = (int)Math.floor(d * 24);
//      int m = (int)Math.floor(time.doubleValue() * 24 * 60 % 60);
        int m = (int)Math.floor(d * 24 % 10);
        System.out.println(d + " " + (d * 24) + " " + h + "," + m);
        System.out.println(nf.format(d * 24));
*/
        ru.svrw.date.Calendar cal = new ru.svrw.date.Calendar();
        long l = new Long("0834611333172").longValue();
        cal.setTimeInMillis(l);
        System.out.println(cal.getDBTimestamp());
        
        System.out.println("----------------");
        String str = new char[10].toString();
        System.out.println("'" + str + "'");            
    }
}
