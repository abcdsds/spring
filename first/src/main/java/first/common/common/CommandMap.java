package first.common.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.nhncorp.lucy.security.xss.XssFilter;
import com.nhncorp.lucy.security.xss.XssPreventer;

public class CommandMap {
	Map<String,Object> map = new HashMap<String,Object>();
	XssFilter filter = XssFilter.getInstance("lucy-xss-superset.xml");

	public Object get(String key){
		return map.get(key);
	}
	
	public void put(String key, Object value){

		System.out.println(XssPreventer.escape(filter.doFilter(key)));
		System.out.println(XssPreventer.escape(filter.doFilter((String) value)));
		map.put(XssPreventer.escape(filter.doFilter(key)), XssPreventer.escape(filter.doFilter((String) value)));
	}
	
	public Object remove(String key){
		return map.remove(key);
	}
	
	public boolean containsKey(String key){
		return map.containsKey(key);
	}
	
	public boolean containsValue(Object value){
		return map.containsValue(value);
	}
	
	public void clear(){
		map.clear();
	}
	
	public Set<Entry<String, Object>> entrySet(){
		return map.entrySet();
	}
	
	public Set<String> keySet(){
		return map.keySet();
	}
	
	public boolean isEmpty(){
		return map.isEmpty();
	}
	
	public void putAll(Map<? extends String, ?extends Object> m){
		map.putAll(m);
	}
	
	public Map<String,Object> getMap(){
		return map;
	}
}
