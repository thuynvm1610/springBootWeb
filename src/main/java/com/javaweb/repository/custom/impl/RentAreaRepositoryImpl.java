package com.javaweb.repository.custom.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.RentAreaEntity;
import com.javaweb.utils.ConnectionJDBCUtil;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {	
	
	@Override
	public List<RentAreaEntity> getValueByBuildingId(Long id) {
		String sql = "SELECT * FROM rentarea r WHERE r.buildingid = " + id + " ";
		List<RentAreaEntity> rentAreas = new ArrayList<RentAreaEntity>();
		try (Connection conn = ConnectionJDBCUtil.getConnection()) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				RentAreaEntity rentAreaEntity = new RentAreaEntity();
				rentAreaEntity.setValue(rs.getString("value"));
				rentAreas.add(rentAreaEntity);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rentAreas;
	}

}
