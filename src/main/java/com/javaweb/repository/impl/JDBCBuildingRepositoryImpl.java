package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;

@Repository
@PropertySource("classpath:application-uat.properties")
public class JDBCBuildingRepositoryImpl implements BuildingRepository {
	
	@Value("${spring.datasource.url}")
	private String DB_URL;
	
	@Value("${spring.datasource.username}")
	private String USER;
	
	@Value("${spring.datasource.password}")
	private String PASS;
	
	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if (staffId != null) {
			sql.append("INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if (typeCode != null && typeCode.size() != 0) {
			sql.append("INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append("INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
		Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		Long rentAreaTo = buildingSearchBuilder.getAreaTo();
		if (rentAreaFrom != null || rentAreaTo != null) {
			sql.append("INNER JOIN rentarea ON b.id = rentarea.buildingid ");
		}
	}
	
	public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for (Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if (!fieldName.equals("staffId") && !fieldName.equals("typeCode") && !fieldName.startsWith("area")
						&& !fieldName.startsWith("rentPrice")) {
					Object value = item.get(buildingSearchBuilder);
					if (value != null) {
						if (item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer")) {
							where.append("AND b." + fieldName + " = " + value + " ");
						} else if (item.getType().getName().equals("java.lang.String")) {
							where.append("AND b." + fieldName + " LIKE '%" + value + "%' ");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if(staffId != null) {
			where.append("AND assignmentbuilding.staffid = " + staffId + " ");
		}
		Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		Long rentAreaTo = buildingSearchBuilder.getAreaTo();
		if (rentAreaFrom != null) {
			where.append("AND rentarea.value >= " + rentAreaFrom + " ");
		}
		if (rentAreaTo != null) {
			where.append("AND rentarea.value <= " + rentAreaTo + " ");
		}
		Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
		if (rentPriceFrom != null) {
			where.append("AND b.rentprice >= " + rentPriceFrom + " ");
		}
		if (rentPriceTo != null) {
			where.append("AND b.rentprice <= " + rentPriceTo + " ");
		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if (typeCode != null && typeCode.size() != 0) {
			List<String> code = new ArrayList<String>();
			for(String it : typeCode) {
				code.add("'" + it + "'");
			}
			where.append("AND renttype.code IN(" + String.join(",", code) + ") ");
		}
	}
	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, b.rentprice, "
				+ "b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder("WHERE 1 = 1 ");
		queryNormal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		where.append("GROUP BY b.id");
		sql.append(where);
		System.out.println(sql);
		List<BuildingEntity> res = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql.toString());
			while(rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setId(rs.getLong("id"));	
				buildingEntity.setName(rs.getString("name"));
				buildingEntity.setWard(rs.getString("ward")); 	
//				buildingEntity.setDistrictid(rs.getLong("districtid")); 	
				buildingEntity.setStreet(rs.getString("street")); 	
//				buildingEntity.setFloorArea(rs.getLong("floorarea")); 	
				buildingEntity.setRentPrice(rs.getLong("rentprice")); 	
//				buildingEntity.setServiceFee(rs.getString("servicefee")); 	
//				buildingEntity.setBrokerageFee(rs.getLong("brokeragefee")); 	
				buildingEntity.setManagerName(rs.getString("managername")); 	
				buildingEntity.setManagerPhoneNumber(rs.getString("managerphonenumber")); 	
				res.add(buildingEntity);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return res;
	}
	
}
