package others;

import com.mchange.lang.LongUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author fengjirong 2020/05/19 14:59
 */
public class oneTest {
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("zhangsan");
		list.add("lisi");
		list.add("wangwu");
		list.add("zhangsan");
		List list1 = new ArrayList<>(new LinkedHashSet<>(list));
		System.out.println(list.toString());
		System.out.println(list1.toString());
		System.out.println("2020-01-01".substring(0,7));
	}
}
