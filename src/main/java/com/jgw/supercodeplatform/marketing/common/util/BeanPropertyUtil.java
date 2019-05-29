package com.jgw.supercodeplatform.marketing.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class BeanPropertyUtil {
	
	public static void toMapFromObject(Map<String,Object> map,Object object){
		Field[] fields = object.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		for (Field field : fields) {
			try {
				if(field.get(object)!=null)
					map.put(field.getName(), field.get(object));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}			
		}
	}
	public static void toObjectFromMap(Map<String,Object> map,Object object){
		Field[] fields = object.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		for (Field field : fields) {
			if(map.containsKey(field.getName())){
				try {
					field.set(object, map.get(field));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String[] getPropertyNameAndValue(Object object){
		Map<String,Object> map = new HashMap<String,Object>();
		toMapFromObject(map,object);		
		return getPropertyNameAndValue(map);
	}
	
	public static List<HashMap<String, Object>> toHashMap(ResultSet resultSet) {
		ResultSetMetaData rsmd;
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		try {
			rsmd = resultSet.getMetaData();
			int size = rsmd.getColumnCount();
			String[] columnName = new String[size];
			for (int i = 0; i < size; i++) {
				columnName[i] = rsmd.getColumnLabel(i + 1);
			}
			HashMap<String, Object> map = null;
			while (resultSet.next()) {
				map = new HashMap<String, Object>();
				for (int i = 0; i < size; i++) {
					if(resultSet.getObject(columnName[i])!=null)
						map.put(columnName[i].toLowerCase(), resultSet.getObject(columnName[i]));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static int charCount(String string, char c) {
		int count = 0;
		char[] chars = string.toCharArray();
		for (char ch : chars) {
			if (ch == c)
				count++;
		}
		return count;
	}
	public static <T> List<T>  toObjectsFromMaps(List<Map<String,Object>> from,Class<T> cla){
		T object = null;
		Field[] fields = cla.getDeclaredFields();
		List<T> list = new ArrayList<T>();
		Field.setAccessible(fields, true);
		for (Map<String,Object> map : from) {
			try {
				object = cla.newInstance();
				for (Field field : fields) {
					String name = field.getName();
					if(map.containsKey(name)){
						Object value = map.get(name);
						if(value.getClass()==Date.class)
							value = DateUtil.DateFormat(value, "yyyy/MM/dd HH:mm");
						field.set(object, String.valueOf(value));
					}
				}
				list.add(object);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return list;
		
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * @param bean 要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException 如果分析类属性失败
	 * @throws IllegalAccessException 如果实例化 JavaBean 失败
	 * @throws InvocationTargetException 如果调用属性的 setter 方法失败
	 */
	public static Map<String, Object> toMap(Object bean) {
		Class<? extends Object> clazz = bean.getClass();
		Map<String, Object> returnMap = new HashMap<>();
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = null;
					result = readMethod.invoke(bean, new Object[0]);
					if (null != propertyName) {
						propertyName = propertyName.toString();
					}
					if (null != result) {
						result = result.toString();
					}
					returnMap.put(propertyName, result);
				}
			}
		} catch (IntrospectionException e) {
			System.out.println("分析类属性失败");
		} catch (IllegalAccessException e) {
			System.out.println("实例化 JavaBean 失败");
		} catch (IllegalArgumentException e) {
			System.out.println("映射错误");
		} catch (InvocationTargetException e) {
			System.out.println("调用属性的 setter 方法失败");
		}
		return returnMap;
	}

}
