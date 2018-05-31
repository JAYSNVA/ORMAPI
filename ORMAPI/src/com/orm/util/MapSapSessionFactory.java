package com.orm.util;

import java.sql.Connection;

public class MapSapSessionFactory {
	public static Connection connection = null;

	public void init(Connection conn) {
		this.connection = conn;
	}
}