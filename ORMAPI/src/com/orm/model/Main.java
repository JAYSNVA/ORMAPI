package com.orm.model;

import java.util.Date;

import com.orm.service.IBaseEntityService;
import com.orm.serviceImpl.IBaseEntityServiceImpl;

public class Main {
	public static void main(String[] args) {
		IBaseEntityService<MarkedProductTest> service = new IBaseEntityServiceImpl();
		MarkedProductTest obj = new MarkedProductTest();
		obj.setId(Long.valueOf(125L));
		obj.setProductName("MAPSAP");
		obj.setCompanyName("YC");
		obj.setAnnoDate(new Date());
		obj.setIsActive(1);
		obj.setCdate(new Date());
		obj.setLatitude(Double.valueOf(41.545D));
		obj.setMarkedCause("TESTTTT");
		obj.setSerialNumber("15644");
		obj.setBytes(new byte['?']);
		service.save(obj, MarkedProductTest.class);
	}
}
