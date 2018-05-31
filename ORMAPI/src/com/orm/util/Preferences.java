package com.orm.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class Preferences {
	public static boolean inPortal = true;

	public static void close(Connection con, PreparedStatement stmt, ResultSet rset) {
		close(con);
		close(stmt);
		close(rset);
	}

	public static void close(Connection con, Statement stmt, ResultSet rset) {
		close(con);
		close(stmt);
		close(rset);
	}

	public static void close(Connection con, PreparedStatement stmt) {
		close(con);
		close(stmt);
	}

	public static void close(Connection con, Statement stmt) {
		close(con);
		close(stmt);
	}

	public static void close(Connection resource) {
		try {
			if (resource != null) {
				resource.close();
				resource = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement resource) {
		try {
			if (resource != null) {
				resource.close();
				resource = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(Statement resource) {
		try {
			if (resource != null) {
				resource.close();
				resource = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet resource) {
		try {
			if (resource != null) {
				resource.close();
				resource = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static long nextLongId(String table, String col, Connection connection) {
		Statement stmt = null;
		ResultSet rset = null;
		long maxid = 0;
		try {
//			connection = MapSapSessionFactory.connection;
			stmt = connection.createStatement();
			rset = stmt.executeQuery("select max(" + col + ") as maxid from " + table + "");
			if (rset.next()) {
				maxid = rset.getLong("maxid");
				maxid++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxid;
	}

	public static void setString(PreparedStatement stmt, int index, String value) throws SQLException {
		if ((value == null) || (value.length() == 0)) {
			stmt.setNull(index, 12);
		} else {
			stmt.setString(index, value.trim());
		}
	}

	public static void setInteger(PreparedStatement stmt, int index, Integer value) throws SQLException {
		if (value == null) {
			stmt.setNull(index, 4);
		} else {
			stmt.setInt(index, value.intValue());
		}
	}

	public static void setDouble(PreparedStatement stmt, int index, Double value) throws SQLException {
		if (value == null) {
			stmt.setNull(index, 8);
		} else {
			stmt.setDouble(index, value.doubleValue());
		}
	}

	public static void setTimestamp(PreparedStatement stmt, int index, Date value) throws SQLException {
		if (value == null) {
			stmt.setNull(index, 4);
		} else {
			stmt.setTimestamp(index, new Timestamp(value.getTime()));
		}
	}

	public static void setLong(PreparedStatement stmt, int index, Long value) throws SQLException {
		if (value == null) {
			stmt.setNull(index, -5);
		} else {
			stmt.setLong(index, value.longValue());
		}
	}

	public static void setFloat(PreparedStatement stmt, int index, Float value) throws SQLException {
		if (value == null) {
			stmt.setNull(index, 6);
		} else {
			stmt.setFloat(index, value.floatValue());
		}
	}

	public static void setBytes(PreparedStatement stmt, int index, byte[] value) throws SQLException {
		if (value == null) {
			stmt.setNull(index, 6);
		} else {
			stmt.setBytes(index, value);
		}
	}

	public static void setBoolean(PreparedStatement stmt, int index, Boolean value) throws SQLException {
		if (value == null) {
			stmt.setNull(index, 12);
		} else if (value.booleanValue()) {
			stmt.setString(index, "X");
		} else {
			stmt.setNull(index, 12);
		}
	}
}