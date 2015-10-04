import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Main {
	private static Map<String, Set<String>> dataSourceMap = new HashMap<>();
	private static Ap ap;

	public static void main(String[] args) {
		//-----模拟数据
		Set<String> set1 =new HashSet<String>();
		set1.add("A");
		set1.add("B");
		set1.add("C");
		set1.add("D");
		set1.add("E");
		dataSourceMap.put("set1", set1);
		
		Set<String> set2 =new HashSet<String>();
		set2.add("B");
		set2.add("C");
		set2.add("F");
		dataSourceMap.put("set2", set2);
		
		Set<String> set3 =new HashSet<String>();
		set3.add("F");
		set3.add("G");
		set3.add("H");
		dataSourceMap.put("set3", set3);
		
		Set<String> set4 =new HashSet<String>();
		set3.add("A");
		set3.add("C");
		set3.add("H");
		dataSourceMap.put("set4", set4);
		
		
		System.out.println(dataSourceMap);

		//-----数据传递
		ap = new Ap(dataSourceMap);
		ap.getSingleTimes();
		ap.getGroup();
	}
}
