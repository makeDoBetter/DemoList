package others;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/5/19 17:07
 * @since JDK 1.8
 */
public class NewTest0519 {
    public static void main(String[] args) {
        //SimpleDateFormat dateFormat02 = new SimpleDateFormat("yyyyMMddHHmmdd");
        SimpleDateFormat dateFormat02 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        String period = dateFormat.format(new Date());
        System.out.println(dateFormat02.format(new Date()));
        System.out.println(period);
        String[] arr = "a_19999_TAX_01005_202103_10_000.dat.gz".split("[._]");
        String code;
        int i = Integer.parseInt(arr[5])+1;
        if (i>0 && i<10){
            code = "0"+i;
        }else {
            code = i + "";
        }
        System.out.println(arr[0]+"_"+arr[1]+"_"+arr[2]+"_"+arr[3]+"_"+arr[4]+"_"+code +"_"+arr[6]);
        System.out.println("a_19999_TAX_01005_202103_10_000.dat.gz".substring(0,27));
        System.out.println(dateFormat02.format(new Date()));
    }
}
