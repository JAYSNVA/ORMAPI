package com.orm.serviceImpl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.orm.annotation.Column;
import com.orm.annotation.PrimaryKey;
import com.orm.annotation.Table;
import com.orm.service.IBaseEntityService;
import com.orm.util.Preferences;

public class IBaseEntityServiceImpl<T> implements IBaseEntityService<T> {
	
	private Connection connection;
	
	public IBaseEntityServiceImpl() {
		
	}
	
	public IBaseEntityServiceImpl(Connection conn) {
		this.connection = conn;
	}
	
	public int save(T entity, Class<T> clazz) {
		PreparedStatement stmt = null;
		String tableName = ((Table) clazz.getAnnotation(Table.class)).name();
		String keys = "(";
		String values = "(";
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Column column = (Column) fields[i].getAnnotation(Column.class);
			if (column != null) {
				if (i == fields.length - 1) {
					keys = keys + column.name() + ")";
					values = values + "?)";
				} else {
					keys = keys + column.name() + ",";
					values = values + "?,";
				}
			}
		}
		String query = "INSERT INTO " + tableName + " " + keys + " VALUES" + values;
		try {
			stmt = connection.prepareStatement(query);
			stmt = setStatementValue(entity, stmt, fields);
			return stmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return -1;
	}
	
	public int update(T entity, Class<T> clazz) throws Exception {
		PreparedStatement stmt = null;
		String tableName = ((Table) clazz.getAnnotation(Table.class)).name();
		String keys = "";
		List<String> primaryKeys = new ArrayList<String>();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Column column = (Column) fields[i].getAnnotation(Column.class);
			PrimaryKey columnPrimaryKey = (PrimaryKey) fields[i].getAnnotation(PrimaryKey.class);
			if (columnPrimaryKey != null) {
				primaryKeys.add(column.name());
			}else {
				if (column != null) {
					if (i == fields.length - 1) {
						keys = keys + column.name() + "=?";
					} else {
						keys = keys + column.name() + "=?, ";
					}
				}
			}
		}
		
		String query = "UPDATE " + tableName + " SET " + keys + " WHERE ";
		if (primaryKeys != null && primaryKeys.size() < 0) {
			throw new Exception("Tabloda primary key alan? yok!");
		}
		for (int i = 0; i < primaryKeys.size(); i++) {
			if (i == primaryKeys.size()-1) {
				query += primaryKeys.get(i) + "=? ";
			}else {
				query += primaryKeys.get(i) + "=? AND ";
			}
			
		}
		try {
			stmt = connection.prepareStatement(query);
			stmt = setUpdateStatementValue(entity, stmt, fields);
			return stmt.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return -1;
	}
	
	public int deleteByColumn(String columnName, Object object, Class<T> clazz) {
		PreparedStatement stmt = null;
		String tableName = ((Table) clazz.getAnnotation(Table.class)).name();
		String sql = "DELETE FROM " + tableName + " WHERE " + columnName + "=?";
		try {
			stmt = connection.prepareStatement(sql);
			stmt = setParameter(stmt, object);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findAll(Class<T> clazz) {
		List list = null;
		String tableName = ((Table) clazz.getAnnotation(Table.class)).name();
		String sql = "SELECT * FROM " + tableName;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			Field[] fields = clazz.getDeclaredFields();
			list = new ArrayList();
			while(rs.next()){
				try {
					Class  c = Class.forName(clazz.getName());
					T entity = (T) c.newInstance();
					for (int i = 0; i < fields.length; i++) {
						Column column = (Column) fields[i].getAnnotation(Column.class);
						Field field = fields[i];
					    field.setAccessible(true);
					    field.set(entity, rs.getObject(column.name()));
					}	
					list.add(entity);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Preferences.close(connection, stmt, rs);
		}
		return list;
	}
	
	public static <T> T instanceOf (Class<T> clazz) throws Exception {
	    return clazz.newInstance();
	}

	private PreparedStatement setStatementValue(T entity, PreparedStatement stmt, Field[] fields) throws SQLException {
		for (int i = 0; i < fields.length; i++) {
			Column column = (Column) fields[i].getAnnotation(Column.class);
			fields[i].setAccessible(true);
			if (column != null) {
				setParameter(entity, stmt, column, fields[i], i);
			}
		}
		return stmt;
	}
	
	private PreparedStatement setUpdateStatementValue(T entity, PreparedStatement stmt, Field[] fields) throws SQLException {
		List<Field> primaryFields = new ArrayList<Field>();
		List<Column> primaryColumns = new ArrayList<Column>();
		
		List<Field> normalFields = new ArrayList<Field>();
		List<Column> normalColumns = new ArrayList<Column>();
		
		for (int i = 0; i < fields.length; i++) {
			Column column = (Column) fields[i].getAnnotation(Column.class);
			PrimaryKey columnPrimaryKey = (PrimaryKey) fields[i].getAnnotation(PrimaryKey.class);
			if (columnPrimaryKey != null) {
				primaryColumns.add(column);
				primaryFields.add(fields[i]);
			}else {
				normalColumns.add(column);
				normalFields.add(fields[i]);
			}
		}
		for (int i = 0; i < normalFields.size(); i++) {
			Column column = (Column) normalFields.get(i).getAnnotation(Column.class);
			normalFields.get(i).setAccessible(true);
			if (column != null) {
				setParameter(entity, stmt, column, normalFields.get(i), i);
			}
		}
		for (int i = 0; i < primaryFields.size(); i++) {
			Column column = (Column) primaryFields.get(i).getAnnotation(Column.class);
			primaryFields.get(i).setAccessible(true);
			if (column != null) {
				setParameter(entity, stmt, column, primaryFields.get(i), (i + normalFields.size()));
			}
		}
		return stmt;
	}
	
	@SuppressWarnings("unchecked")
	public PreparedStatement setParameter(T entity, PreparedStatement stmt, Column column, Field field, int i) {
		try {
			if ((column.clazz().isAssignableFrom(Integer.class))
					|| (column.clazz().isAssignableFrom(Integer.TYPE))) {
				Preferences.setInteger(stmt, i + 1, Integer.valueOf(field.getInt(entity)));
			} else if ((column.clazz().isAssignableFrom(Double.class))
					|| (column.clazz().isAssignableFrom(Double.TYPE))) {
				Preferences.setDouble(stmt, i + 1, (Double) field.get(entity));
			} else if ((column.clazz().isAssignableFrom(Long.class))
					|| (column.clazz().isAssignableFrom(Long.TYPE))) {
				Preferences.setLong(stmt, i + 1, (Long) field.get(entity));
			} else if ((column.clazz().isAssignableFrom(Boolean.class))
					|| (column.clazz().isAssignableFrom(Boolean.TYPE))) {
				Preferences.setBoolean(stmt, i + 1, Boolean.valueOf(field.getBoolean(entity)));
			} else if ((column.clazz().isAssignableFrom(Float.class))
					|| (column.clazz().isAssignableFrom(Float.TYPE))) {
				Preferences.setFloat(stmt, i + 1, (Float) field.get(entity));
			} else if (column.clazz().isAssignableFrom(String.class)) {
				Preferences.setString(stmt, i + 1, (String) field.get(entity));
			} else if (column.clazz().isAssignableFrom(Date.class)) {
				Preferences.setTimestamp(stmt, i + 1, (Date) field.get(entity));
			} else if (column.clazz().isAssignableFrom(byte[].class)) {
				Preferences.setBytes(stmt, i + 1, (byte[]) field.get(entity));
			} else if (column.clazz().isAssignableFrom(Byte.TYPE)) {
				stmt.setByte(i + 1, field.getByte(entity));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}
	
	public PreparedStatement setParameter(PreparedStatement stmt, Object object) {
		int i = 0;
		try {
			if ((object.getClass().isAssignableFrom(Integer.class))
					|| (object.getClass().isAssignableFrom(Integer.TYPE))) {
				Preferences.setInteger(stmt, i + 1, (Integer)object);
			} else if ((object.getClass().isAssignableFrom(Double.class))
					|| (object.getClass().isAssignableFrom(Double.TYPE))) {
				Preferences.setDouble(stmt, i + 1, (Double) object);
			} else if ((object.getClass().isAssignableFrom(Long.class))
					|| (object.getClass().isAssignableFrom(Long.TYPE))) {
				Preferences.setLong(stmt, i + 1, (Long) object);
			} else if ((object.getClass().isAssignableFrom(Boolean.class))
					|| (object.getClass().isAssignableFrom(Boolean.TYPE))) {
				Preferences.setBoolean(stmt, i + 1, (Boolean)object);
			} else if ((object.getClass().isAssignableFrom(Float.class))
					|| (object.getClass().isAssignableFrom(Float.TYPE))) {
				Preferences.setFloat(stmt, i + 1, (Float) object);
			} else if (object.getClass().isAssignableFrom(String.class)) {
				Preferences.setString(stmt, i + 1, (String) object);
			} else if (object.getClass().isAssignableFrom(Date.class)) {
				Preferences.setTimestamp(stmt, i + 1, (Date) object);
			} else if (object.getClass().isAssignableFrom(byte[].class)) {
				Preferences.setBytes(stmt, i + 1, (byte[]) object);
			} else if (object.getClass().isAssignableFrom(Byte.TYPE)) {
				stmt.setByte(i + 1, (Byte)object);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}