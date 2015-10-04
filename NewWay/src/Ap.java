import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Ap {

	private Map<String, Set<String>> dataSourceMap; //用户及其使用的app
	private Map<Set<String>, Integer> singleTime = null; //每个app及其出现次数

	public Ap(Map<String, Set<String>> dataSourceMap) {
		this.dataSourceMap=dataSourceMap;
		singleTime = new HashMap<>();
	}


	/***循环每个app，找到某个app的所有使用者的app集合，然后组合找到包含该app的二项集以及出现次数
	 *  求出关联率后，在以上的用户和使用app中删除该app
	 * @return void  
	 * @author Merlin
	 * @date 2015年10月3日
	 */
	public void getGroup(){
		Map<String, Set<String>> copyDataSMap = dataSourceMap;

		Set<Entry<String, Set<String>>> copyDataSMapSet = copyDataSMap.entrySet();
		Iterator<Set<String>> singTIterator = singleTime.keySet().iterator();
		//循环每个app
		while (singTIterator.hasNext()) {

			//使用该款app的用户，保存imei
			Set<String> keyStrings = new HashSet<>();
			//筛选后包含该app的所有用户app列表
			Map<String, Set<String>> containsAppsMap = new HashMap<String, Set<String>>();

			//该次app包名
			Set<String> appNameSet = singTIterator.next(); 
			String  appName = new String();
			for (String string : appNameSet) {
				appName =string;
			}

			//找到该次app的所有使用者的集合
			for (Entry<String, Set<String>> entry : copyDataSMapSet) {
				Set<String> usersApp = entry.getValue(); //循环每个用户，获取每个用户的app
				if (usersApp.containsAll(appNameSet)) {

					//保存某一个app的所有使用者，准备对这些提取二项集
					containsAppsMap.put(entry.getKey(), entry.getValue());
					//找到了哪些用户
					keyStrings.add(entry.getKey());
				}
			}


			//存放二项集
			Set<Set<String>> tempSet =new HashSet<>();
			//存放二项集以及其次数
			Map<Set<String>, Integer> map = new HashMap<>();

			//提取二项集
			Set<Entry<String, Set<String>>> set = containsAppsMap.entrySet();
			for (Entry<String, Set<String>> entry : set) {
				Set<String> appsSet = entry.getValue();
				//这些二项集是以此次的app为开头
				for (String string : appsSet) {
					Set<String> newSets =new HashSet<>();
					if (string.equals(appName)){
						continue;
					}else{
						newSets.add(string);
						newSets.add(appName);
						tempSet.add(newSets);
					}
				}
				//放到map中，如果存在就值加一，否则就新添加
				for (Set<String> secondSet : tempSet) {
					if (map.containsKey(secondSet)) {
						int value = map.get(secondSet)+1;
						map.put(secondSet, value);
					}else {
						map.put(secondSet, 1);
					}
				}
			}

			//结果
			Iterator<Entry<Set<String>, Integer>> mapIterator = map.entrySet().iterator();
			while (mapIterator.hasNext()) {
				Set<String> mapSet = mapIterator.next().getKey();//获取二项集
				Integer count = map.get(mapSet);//获取该二项集次数
				for (String string : mapSet) {
					Set<String> s1=new HashSet<>();
					s1.add(string);
					Double singleCount = singleTime.get(s1).doubleValue();
					//结果筛选
					/*if (singleCount<0.5) {
						continue;
					}*/
					
					//System.out.println(string+":"+singleCount);
					System.out.println("结果是："+mapSet+"--->"+string+":"+count/singleCount);
				}
			}

			System.out.println("---");
			//删除用户app中的该app
			for (String string : keyStrings) {
				copyDataSMap.get(string).removeAll(appNameSet);
				//移除仅包含一个元素的集合和空集合
				if (copyDataSMap.get(string).size()<=1) {
					copyDataSMap.remove(string);
				}
			}
		}
	}



	//获取每一个app的次数 
	public Map<Set<String>, Integer> getSingleTimes(){
		Set<Set<String>> singSet  = singleTime.keySet();

		//循环每一个用户
		Iterator<Entry<String, Set<String>>> dataSIterator = dataSourceMap.entrySet().iterator();
		while (dataSIterator.hasNext()) {
			Entry<String, Set<String>> entry =dataSIterator.next();
			Set<String> appSet = entry.getValue();
			for (String string : appSet) {
				Set<String> newSet = new HashSet<>();
				newSet.add(string);
				if (singSet.contains(newSet)) {
					int value = singleTime.get(newSet) + 1;
					singleTime.put(newSet, value);
				}else {
					singleTime.put(newSet, 1);
				}

			}
		}
		System.out.println(singleTime);
		return singleTime;
	}




}
