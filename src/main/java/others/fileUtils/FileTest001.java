package others.fileUtils;

import org.apache.commons.lang3.StringUtils;
import java.io.File;

/**
 * Description:
 *
 * @author makeDoBetter
 * @version 1.0
 * @date 2021/6/17 17:07
 * @since JDK 1.8
 */
public class FileTest001 {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\fengjirong\\Desktop\\a_19999_TAX_01006_202106_02_000.dat.gz");
        String gzFileName = file.getName();
        long gzLength = file.length();
        String ver = String.format("%-50s", gzFileName) + String.format("%-20s", gzLength);
        System.out.println(ver);
        System.out.println(StringUtils.equals(null, "hello"));
    }
}
