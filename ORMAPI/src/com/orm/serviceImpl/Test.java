package com.orm.serviceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.orm.model.Student;
import com.orm.service.IBaseEntityService;
import com.orm.util.MapSapSessionFactory;
import com.orm.util.Preferences;

public class Test {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection connection = null;
		if (MapSapSessionFactory.connection == null) {
			Class.forName("com.mysql.jdbc.Driver");
			connection =DriverManager.getConnection("jdbc:mysql://159.203.162.166:3306/indirimli_urun","root", "IsmekDers1?");
		}
		
		
		Student student = new Student(Preferences.nextLongId("TBL_STUDENT", "ID",connection), "43416224234", "Bu?ra", new Date());
		IBaseEntityService<Student> service = new IBaseEntityServiceImpl<Student>(connection);
//		System.out.println("Sonuc : " + service.save(student, Student.class));
		try {
//			System.out.println("Sonuc : " + service.update(student, Student.class));
//			System.out.println("Sonuc : " + service.deleteByColumn("ID",1,Student.class));
			List<Student> students = service.findAll(Student.class);
			System.out.println(students.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
