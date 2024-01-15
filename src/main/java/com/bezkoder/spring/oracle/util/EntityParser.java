package com.bezkoder.spring.oracle.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Query;

import org.springframework.util.StringUtils;

public class EntityParser {
	public static <T> List<T> resultSetToList(ResultSet rst, Class<T> type) throws SQLException {

		List<T> list = new ArrayList<T>();
		try {
			ResultSetMetaData rsmd = rst.getMetaData();
			int columnCount = rsmd.getColumnCount();

			List<String> columnsName = new ArrayList<>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				String name = rsmd.getColumnName(i);
				columnsName.add(name);
			}

			while (rst.next()) {
				for (int i = 0; i < columnCount; i++) {
					System.out.println(columnsName.get(i) + ":" + rst.getObject(i + 1));
				}
				T t = type.newInstance();
				loadResultSetIntoObject(rst, t, columnsName);// Point 4
				list.add(t);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Unable to get the records: " + e.getMessage(), e);
		}
		return list;
	}

	public static void loadResultSetIntoObject(ResultSet rst, Object object, List<String> availableColumns)
			throws IllegalArgumentException, IllegalAccessException, SQLException {
		Class<?> zclass = object.getClass();

		for (Field field : zclass.getDeclaredFields()) {
			field.setAccessible(true);
			DBTable column = field.getAnnotation(DBTable.class);

			if (column == null || StringUtils.isEmpty(column.columnName()))
				continue;
			if (!availableColumns.contains(column.columnName()))
				continue;

			Object value = rst.getObject(column.columnName());

			Class<?> type = field.getType();
			if (isPrimitive(type)) {
				if (!(value instanceof BigInteger)) {
					if(value instanceof BigDecimal) {
						
						value = ((BigDecimal) value).longValue();
					}
					else {
					Class<?> boxed = boxPrimitiveClass(type);
					value = boxed.cast(value);
					}
				} else {
					value = ((BigInteger) value).longValue();
				}

			}
			field.set(object, value);
		}
	}
	
	public static Class<?> boxPrimitiveClass(Class<?> type) {
		if (type == int.class) {
			return Integer.class;
		} else if (type == long.class) {
			return Long.class;
		} else if (type == double.class) {
			return Double.class;
		} else if (type == float.class) {
			return Float.class;
		} else if (type == boolean.class) {
			return Boolean.class;
		} else if (type == byte.class) {
			return Byte.class;
		} else if (type == char.class) {
			return Character.class;
		} else if (type == short.class) {
			return Short.class;
			
		}
		
		 else if (type == BigDecimal.class) {
				return Long.class;
				
			}
		else {
			String string = "class '" + type.getName() + "' is not a primitive";
			throw new IllegalArgumentException(string);
		}
	}
	public static boolean isPrimitive(Class<?> type) {
		return (type == int.class || type == long.class || type == double.class || type == float.class
				|| type == boolean.class || type == byte.class || type == char.class || type == short.class||type == BigDecimal.class);
	}
	
	/*
	 * public static <T> List<T> selectQuery(Class<T> type, String query,
	 * List<SQLParamObject> inputParams) throws SQLException { List<T> list = new
	 * ArrayList<T>(); try (Connection conn = dataSource.getConnection()) { try
	 * (PreparedStatement prepareStatement = conn.prepareStatement(query)) {
	 * 
	 * for(int i = 0 ; i < inputParams.size(); i++) { SQLParamObject obj =
	 * inputParams.get(i); prepareStatement.setObject(i + 1, obj.getValue(),
	 * obj.getTargetSqlType()); }
	 * 
	 * try (ResultSet rst = prepareStatement.executeQuery()) {
	 * 
	 * ResultSetMetaData rsmd = rst.getMetaData(); int columnCount =
	 * rsmd.getColumnCount();
	 * 
	 * List<String> columnsName = new ArrayList<>(columnCount); for (int i = 1; i <=
	 * columnCount; i++ ) { String name = rsmd.getColumnName(i);
	 * columnsName.add(name); }
	 * 
	 * while (rst.next()) { for(int i = 0; i < columnCount;i++) {
	 * System.out.println(columnsName.get(i) + ":" + rst.getObject(i+1)); } T t =
	 * type.newInstance(); loadResultSetIntoObject(rst, t, columnsName);// Point 4
	 * list.add(t); } } } catch (InstantiationException | IllegalAccessException e)
	 * { throw new RuntimeException("Unable to get the records: " + e.getMessage(),
	 * e); } } return list; }
	 */

}
