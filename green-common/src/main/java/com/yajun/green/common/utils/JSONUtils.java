package com.yajun.green.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @author Eric Fang
 * 
 *         Apr 6, 2016
 */
public class JSONUtils {
	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator keysItr = object.keySet().iterator();
		while (keysItr.hasNext()) {
			String key = (String) keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.size(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

	public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
		Map<String, Object> retMap = new HashMap<String, Object>();

		if (json != null) {
			retMap = toMap(json);
		}
		return retMap;
	}
	
	public static JSONArray toJsonArray (List<Map<String, Object>> mapList){
		JSONArray jsonarray = new JSONArray();
		if(!mapList.isEmpty()){
			for(int i=0; i<mapList.size(); i++){
				JSONObject json = new JSONObject();
				Iterator it = mapList.get(i).keySet().iterator();
				while(it.hasNext()){
					String field = (String) it.next();
					json.put(field, mapList.get(i).get(field));
				}
				jsonarray.add(json);
			}
		}
		return jsonarray;
	}
	
	public static void main(String[] arges) {

        String property = System.getProperty("user.dir");
        System.out.println(property);
		
		JSONObject body = new JSONObject();
    	body.put("title", "cleaning");
    	body.put("description", "cleaning");
    	body.put("endTime", new Date());
    	body.put("taskType", 2);
    	body.put("taskType", "daily_work");
    	body.put("categoryNames", "produce");
    	body.put("categoryScore", 3);
    	body.put("importantName", "normal");
    	body.put("importantScore", 2);
    	body.put("reminded", true);
    	body.put("taskStatus", "NOT_STATED");
    	body.put("creator", 1);
       	JSONArray userArray = new JSONArray();
       	JSONObject guanzhuren = new JSONObject();
       	guanzhuren.put("userId", 4);
       	guanzhuren.put("userTypeForTask", "guanzhuren");
       	JSONObject zerenren = new JSONObject();
       	zerenren.put("userId", 5);
       	zerenren.put("userTypeForTask", "zerenren");
       	userArray.add(guanzhuren);
       	userArray.add(zerenren);
       	body.put("userlist", userArray);
       	
       	Map<String, Object> result = jsonToMap(body); 
       	System.out.println(result);

	}

}
