package com.baps.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.baps.util.PropertyUtil;
import com.baps.vo.ChildVO;
import com.baps.vo.ContactEntryVO;
import com.baps.vo.PhotoVO;

public class DataAccessService {

Logger log = Logger.getLogger(DataAccessService.class);

	private Connection getConnection()
	{

		Connection conn = null;
		PropertyUtil propUtil =  new PropertyUtil();
		try
		{
			String userName = propUtil.propertyValue("userName");
			String password = propUtil.propertyValue("password");
			String url =propUtil.propertyValue("url"); 
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
			conn = DriverManager.getConnection (url, userName, password);
			log.info("Database connection established");
			
		}
		catch (Exception e)
		{
			log.error ("Cannot connect to database server");
		}

		return conn;
	}

	public ContactEntryVO getRecord(String searchID) 
	{
		java.sql.PreparedStatement pst = null;
		Connection con = null ;

		try {
			StringBuffer query = new StringBuffer(" SELECT * FROM CONTACT_MASTER WHERE SERIAL_NUMBER = ? " ) ;
			con = getConnection();
			pst = con.prepareStatement(query.toString());
			pst.setString(1, searchID);
			ResultSet rs = pst.executeQuery();
			List<ContactEntryVO> result =	mapResult(rs,con);
			return result.get(0);

		}catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public ContactEntryVO getRecordByEmail(String email, String phone) 
	{
		java.sql.PreparedStatement pst = null;
		Connection con = null ;

		try {
			StringBuffer query = new StringBuffer(" SELECT * FROM CONTACT_MASTER WHERE ") ;
			
			boolean flag = false;
			if(null != email && !"".equals(email)){
				query.append(" EMAIL LIKE ? ");
				flag = true;
			}
			
			if(null != phone && !"".equals(phone)){
				if(flag)query.append(" OR ");
						
				query.append(" P_CELL_PHONE LIKE ? ");
			}
			
			con = getConnection();
			pst = con.prepareStatement(query.toString());
			pst.setString(1, searchID);
			ResultSet rs = pst.executeQuery();
			List<ContactEntryVO> result =	mapResult(rs,con);
			return result.get(0);

		}catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	
	public List<ContactEntryVO> getRecordForGenericKeyword(String keyword) 
	{
		java.sql.PreparedStatement pst = null;
		Connection con = null ;
		
		String[] keywords = keyword.split(" ");
		
		
		try {
			StringBuffer query = new StringBuffer(" SELECT * FROM CONTACT_MASTER WHERE " ) ;
				query.append(" ( P_FIRST_NAME LIKE ? ");
				query.append(" OR P_LAST_NAME LIKE ? ");
				query.append(" OR P_CELL_PHONE LIKE ? ");
				query.append(" OR EMAIL LIKE ? ");
				query.append(" OR SPOUSE_NAME LIKE ? ");
				query.append(" OR SPOUSE_EMAIL LIKE ? ");
				query.append(" OR SPOUSE_CELL_PHONE LIKE ? ");
				query.append(" OR ADDRESS LIKE ? ) ");
				
				if(keywords.length > 1){
					
					for(int i= 1 ; i < keywords.length ; i++ ){
						query.append(" AND ( P_FIRST_NAME LIKE ? ");
						query.append(" OR P_LAST_NAME LIKE ? ");
						query.append(" OR P_CELL_PHONE LIKE ? ");
						query.append(" OR EMAIL LIKE ? ");
						query.append(" OR SPOUSE_NAME LIKE ? ");
						query.append(" OR SPOUSE_EMAIL LIKE ? ");
						query.append(" OR SPOUSE_CELL_PHONE LIKE ? ");
						query.append(" OR ADDRESS LIKE ? )");
					   
					}
					
				}
				
			    query.append(" LIMIT 50");
			   
			    con = getConnection();
			    pst = con.prepareStatement(query.toString());
			    keyword = "%" + keyword + "%";
			
				/*pst.setString(1, keyword);
				pst.setString(2, keyword);
				pst.setString(3, keyword);
				pst.setString(4, keyword);
				pst.setString(5, keyword);
				pst.setString(6, keyword);
				pst.setString(7, keyword);
				pst.setString(8, keyword);
				*/
					int j = 1;
					for(int i= 0 ; i < keywords.length ; i++ ){
						keyword = "%" + keywords[i] + "%";
						pst.setString((j++), keyword);
						pst.setString((j++), keyword);
						pst.setString((j++), keyword);
						pst.setString((j++), keyword);
						pst.setString((j++), keyword);
						pst.setString((j++), keyword);
						pst.setString((j++), keyword);
						pst.setString((j++), keyword);
						
					}	
				
						
			ResultSet rs = pst.executeQuery();

			List<ContactEntryVO> result =	mapResult(rs,con);
			return result;

		}catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
		
	public List<ContactEntryVO> getRecord(String firstName,String lastName,String mobile,String email, String spouseFirstName,String spouseEmail,String spouseMobile,String address) 
	{
		java.sql.PreparedStatement pst = null;
		Connection con = null ;

		try {
			StringBuffer query = new StringBuffer(" SELECT * FROM CONTACT_MASTER WHERE 1=1 " ) ;
			if(firstName != null && !firstName.equals("")) {
				query.append(" AND P_FIRST_NAME LIKE ? ");
			}	
			if(lastName != null && !lastName.equals("")) {
				query.append(" AND P_LAST_NAME LIKE ? ");
			}		
			if(mobile != null && !mobile.equals("")) {
				query.append(" AND P_CELL_PHONE LIKE ? ");
			}
			if(email != null && !email.equals("")) {
				query.append(" AND EMAIL LIKE ? ");
			}
			if(spouseFirstName != null && !spouseFirstName.equals("")) {
				query.append(" AND SPOUSE_NAME LIKE ? ");
			}
			if(spouseEmail != null && !spouseEmail.equals("")) {
				query.append(" AND SPOUSE_EMAIL LIKE ? ");
			}
			if(spouseMobile != null && !spouseMobile.equals("")) {
				query.append(" AND SPOUSE_CELL_PHONE LIKE ? ");
			}
			if(address != null && !address.equals("")) {
				query.append(" AND ADDRESS LIKE ? ");
			}
			query.append(" LIMIT 50");
			con = getConnection();
			pst = con.prepareStatement(query.toString());
			int index = 1;
			if(firstName != null && !firstName.equals("")) {
				pst.setString(index, firstName.concat("%"));
				index++;
			} 
			if(lastName != null && !lastName.equals("")) {
				pst.setString(index, lastName.concat("%"));
				index++;
			}
			if(mobile != null && !mobile.equals("")) {
				pst.setString(index, mobile.concat("%"));
				index++;
			}
			if(email != null && !email.equals("")) {
				pst.setString(index, email.concat("%"));
				index++;
			}
			if(spouseFirstName != null && !spouseFirstName.equals("")) {
				pst.setString(index, spouseFirstName.concat("%"));
				index++;
			}
			if(spouseEmail != null && !spouseEmail.equals("")) {
				pst.setString(index, spouseEmail.concat("%"));
				index++;
			}
			if(spouseMobile != null && !spouseMobile.equals("")) {
				pst.setString(index, spouseMobile.concat("%"));
				index++;
			}
			
			if(address != null && !address.equals("")) {
				pst.setString(index, "%".concat(address.concat("%")));
				index++;
			}
			
			ResultSet rs = pst.executeQuery();

			List<ContactEntryVO> result =	mapResult(rs,con);
			return result;

		}catch(Exception e){
			e.printStackTrace();
		}
		finally {

			try {
				pst.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	private List<ContactEntryVO> mapResult(ResultSet rs,Connection con) throws SQLException{
		List<ContactEntryVO> result = new ArrayList<ContactEntryVO>();

		while(rs.next()){
			ContactEntryVO entry = new ContactEntryVO();
			entry.setSerialNumber(rs.getString(1));
			entry.setFirstName(isNull(rs.getString(2)));
			entry.setLastName(isNull(rs.getString(3)));
			entry.setCellPhone(isNull(rs.getString(4)));
			entry.setAddress(isNull(rs.getString(5)));
			entry.setCity(isNull(rs.getString(6)));
			entry.setZip((rs.getString(7)));
			entry.setDob((rs.getDate(8)));
			entry.setEmail(isNull(rs.getString(9)));
			entry.setHomePhone(isNull(rs.getString(10)));
			entry.setWorkPhone(isNull(rs.getString(11)));
			entry.setGaam(isNull(rs.getString(12)));
			entry.setSpouseName(isNull(rs.getString(13)));
			entry.setSpouseDob(rs.getDate(14));
			entry.setSpouseEmail(isNull(rs.getString(15)));
			entry.setSpouseWorkPhone(isNull(rs.getString(16)));
			entry.setSpouseCellPhone(isNull(rs.getString(17)));
			entry.setState(isNull(rs.getString(20)));
			entry.setPrasad(isNull(rs.getString(23)));
			entry.setBalPrakash(isNull(rs.getString(24)));
			entry.setGuj(isNull(rs.getString(25)));
			entry.setInd(isNull(rs.getString(26)));
			
			int serialNo = Integer.parseInt(entry.getSerialNumber());
			List<ChildVO> tempList = getChildRecord(serialNo, con);
			entry.setChildrens(tempList);
			getPhotoRecord(serialNo,con,entry);
			result.add(entry);
		}
		return result ;
	}

	public static String isNull(String args){

		if(args != null && !args.equals("")){
			return args;
		}else
			return "";

	}
	
	public void getPhotoRecord(int serialNo,Connection con,ContactEntryVO entryvo){
		List<ChildVO> lst = new ArrayList<ChildVO>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			stmt = con.prepareStatement(" SELECT * FROM PHOTO_DETAIL WHERE CONTACT_ID = ? ");
			stmt.setString(1, String.valueOf(serialNo));
			rs = stmt.executeQuery();
			if(rs.next()) {
				entryvo.setPhotoID1(rs.getString(3));
				entryvo.setGoodPhoto1(rs.getString(5));
			}
			if(rs.next()) {
				entryvo.setPhotoID2(rs.getString(3));
				entryvo.setGoodPhoto2(rs.getString(5));
			}
			if(rs.next()) {
				entryvo.setPhotoID3(rs.getString(3));
				entryvo.setGoodPhoto3(rs.getString(5));
			}
			if(rs.next()) {
				entryvo.setPhotoID4(rs.getString(3));
				entryvo.setGoodPhoto4(rs.getString(5));
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	public List<ChildVO> getChildRecord(int serialNo,Connection con){
		List<ChildVO> lst = new ArrayList<ChildVO>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			stmt = con.prepareStatement(" SELECT * FROM CHILDREN_MASTER WHERE PARENT_ID = ? ");
			stmt.setInt(1, serialNo);
			rs = stmt.executeQuery();
			while(rs.next()) {
				ChildVO temp = new ChildVO();
				temp.setChildName(rs.getString(3));
				temp.setChildGender(rs.getString(5));
				temp.setChildDob(rs.getDate(4));
				temp.setEnrolledGujClass(rs.getString(6));
				lst.add(temp);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return lst;
	}
	public static void main(String a[]) throws Exception
	{
		ContactEntryVO entryVo = new ContactEntryVO();
		entryVo.setFirstName("jignesh");
		entryVo.setLastName("sakhiya1");
		entryVo.setCity("bellevue");
		entryVo.setZip("98005");
		new DataAccessService().addRecord(entryVo);

	}
	public void deleteRecord(int serialNo,Connection con)
	{
		PreparedStatement stmt = null;
		try{
			
			stmt = con.prepareStatement("DELETE FROM CHILDREN_MASTER WHERE PARENT_ID = ? ");
			stmt.setInt(1, serialNo);
			stmt.executeUpdate();
			
		} catch(Exception e){
			
		} finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void deletePhotoRecord(int serialNo,Connection con)
	{
		PreparedStatement stmt = null;
		try{
			
			stmt = con.prepareStatement("DELETE FROM PHOTO_DETAIL WHERE CONTACT_ID = ? ");
			stmt.setString(1, String.valueOf(serialNo));
			stmt.executeUpdate();
			
		} catch(Exception e){
			
		} finally{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	public boolean addRecord(ContactEntryVO entryVo) throws Exception{
		PreparedStatement stmt = null;
		Connection connection = null;
		ResultSet rs = null;
		String SQL =  " INSERT INTO CONTACT_MASTER " +
		" (P_FIRST_NAME, P_LAST_NAME, P_CELL_PHONE, ADDRESS, CITY, ZIP, DOB, EMAIL, HOME_PHONE, WORK_PHONE, GAAM," +
		" SPOUSE_NAME, SPOUSE_DOB, SPOUSE_EMAIL, SPOUSE_WORK_PHONE, SPOUSE_CELL_PHONE, STATE, PRASAD_GIVEN, BALPRAKASH, GUJ, INDIAN) " + 
		" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ? , ?) ";
		try {
			connection = getConnection();
			stmt = connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1,entryVo.getFirstName() );
			stmt.setString(2,entryVo.getLastName() );
			stmt.setString(3,entryVo.getCellPhone() );
			stmt.setString(4,entryVo.getAddress() );
			stmt.setString(5,entryVo.getCity() );
			stmt.setString(6,entryVo.getZip() );
			stmt.setDate(7,getSQLDate(entryVo.getDob()) );
			stmt.setString(8,entryVo.getEmail() );
			stmt.setString(9,entryVo.getHomePhone());
			stmt.setString(10,entryVo.getWorkPhone());
			stmt.setString(11,entryVo.getGaam());
			stmt.setString(12, entryVo.getSpouseName() );
			stmt.setDate(13,getSQLDate(entryVo.getSpouseDob()) );
			stmt.setString(14,entryVo.getSpouseEmail());
			stmt.setString(15,entryVo.getSpouseWorkPhone());
			stmt.setString(16,entryVo.getSpouseCellPhone());
			stmt.setString(17,entryVo.getState());
			stmt.setString(18,entryVo.getPrasad());
			stmt.setString(19,entryVo.getBalPrakash());
			stmt.setString(20,entryVo.getGuj());
			stmt.setString(21,entryVo.getInd());
			
			int numRowUpdated = stmt.executeUpdate();
			int seqNo = 0;
			rs = stmt.getGeneratedKeys();

			if (rs.next()) {
				seqNo = rs.getInt(1);
				System.out.println("Generate SequneNo ::" + seqNo);
				
				for(ChildVO child : entryVo.getChildrens()){
					
					child.setParentId(seqNo);
					boolean status = addChildRecord(child,connection);
					if(!status)
						throw new Exception("Failded Adding children Record");
						
				}
				
				if(entryVo.getPhotoID1() != null && entryVo.getPhotoID1() != ""){
					PhotoVO photoVo = new PhotoVO();
					photoVo.setPhotoID(entryVo.getPhotoID1());
					String goodPhoto = entryVo.getGoodPhoto1();
					photoVo.setGoodPhoto(goodPhoto);
					photoVo.setContactId(String.valueOf(seqNo));
					boolean result =	addPhotoRecord(photoVo,connection);
				}
				if(entryVo.getPhotoID2() != null && entryVo.getPhotoID2() != ""){
					PhotoVO photoVo = new PhotoVO();
					photoVo.setPhotoID(entryVo.getPhotoID2());
					String goodPhoto = entryVo.getGoodPhoto2();
					photoVo.setGoodPhoto(goodPhoto);
					photoVo.setContactId(String.valueOf(seqNo));
					boolean result =	addPhotoRecord(photoVo,connection);
				}
				if(entryVo.getPhotoID3() != null && entryVo.getPhotoID3() != ""){
					PhotoVO photoVo = new PhotoVO();
					photoVo.setPhotoID(entryVo.getPhotoID3());
					String goodPhoto = entryVo.getGoodPhoto3();
					photoVo.setGoodPhoto(goodPhoto);
					photoVo.setContactId(String.valueOf(seqNo));
					boolean result =	addPhotoRecord(photoVo,connection);
				}
				if(entryVo.getPhotoID4() != null && entryVo.getPhotoID4() != ""){
					PhotoVO photoVo = new PhotoVO();
					photoVo.setPhotoID(entryVo.getPhotoID4());
					String goodPhoto = entryVo.getGoodPhoto4();
					photoVo.setGoodPhoto(goodPhoto);
					photoVo.setContactId(String.valueOf(seqNo));
					boolean result =	addPhotoRecord(photoVo,connection);
				}
				
				
			}	   	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

	/**
	 * Update Record
	 * @param entryVo
	 * @return
	 * @throws Exception
	 */
	public boolean updateRecord(ContactEntryVO entryVo) throws Exception{
		PreparedStatement stmt = null;
		Connection connection = null;
		
		String SQL =  " UPDATE CONTACT_MASTER " +
		" SET P_FIRST_NAME  = ?, P_LAST_NAME = ? , P_CELL_PHONE = ? , ADDRESS = ?, CITY = ?, ZIP = ? , DOB = ? , EMAIL = ?, HOME_PHONE = ?, WORK_PHONE = ?, GAAM = ?," +
		" SPOUSE_NAME = ? , SPOUSE_DOB = ? , SPOUSE_EMAIL = ? , SPOUSE_WORK_PHONE = ? , SPOUSE_CELL_PHONE = ? , STATE = ? ," +
		" PRASAD_GIVEN = ? , BALPRAKASH = ? , GUJ = ? , INDIAN = ? , UPDATED = ? " +
		" WHERE SERIAL_NUMBER = ? ";
		try {
			int serial = Integer.parseInt(entryVo.getSerialNumber());
			connection = getConnection();
			stmt = connection.prepareStatement(SQL);

			stmt.setString(1,entryVo.getFirstName() );
			stmt.setString(2,entryVo.getLastName() );
			stmt.setString(3,entryVo.getCellPhone() );
			stmt.setString(4,entryVo.getAddress() );
			stmt.setString(5,entryVo.getCity() );
			stmt.setString(6,entryVo.getZip() );
			stmt.setDate(7,getSQLDate(entryVo.getDob()) );
			stmt.setString(8,entryVo.getEmail() );
			stmt.setString(9,entryVo.getHomePhone());
			stmt.setString(10,entryVo.getWorkPhone());
			stmt.setString(11,entryVo.getGaam());
			stmt.setString(12, entryVo.getSpouseName() );
			stmt.setDate(13,getSQLDate(entryVo.getSpouseDob()) );
			stmt.setString(14,entryVo.getSpouseEmail());
			stmt.setString(15,entryVo.getSpouseWorkPhone());
			stmt.setString(16,entryVo.getSpouseCellPhone());
			stmt.setString(17,entryVo.getState());
			stmt.setString(18,entryVo.getPrasad());
			stmt.setString(19,entryVo.getBalPrakash());
			stmt.setString(20,entryVo.getGuj());
			stmt.setString(21,entryVo.getInd());
			long time = System.currentTimeMillis();
			java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
			
			stmt.setTimestamp(22, timestamp);
			stmt.setInt(23, serial);
			
			int numRowUpdated = stmt.executeUpdate();
			
			if(entryVo.getChildrens() != null && !entryVo.getChildrens().isEmpty()) { 
			  deleteRecord(serial,connection);
			}
				for(ChildVO child : entryVo.getChildrens()){
					child.setParentId(serial);
					
					boolean status = addChildRecord(child,connection);
					if(!status)
						throw new Exception("Failded Adding children Record");
				}
				
				deletePhotoRecord(serial, connection);
				if(entryVo.getPhotoID1() != null && entryVo.getPhotoID1() != ""){
					PhotoVO photoVo = new PhotoVO();
					photoVo.setPhotoID(entryVo.getPhotoID1());
					String goodPhoto = entryVo.getGoodPhoto1();
					photoVo.setGoodPhoto(goodPhoto);
					photoVo.setContactId(String.valueOf(serial));
					boolean result =	addPhotoRecord(photoVo,connection);
				}
				if(entryVo.getPhotoID2() != null && entryVo.getPhotoID2() != ""){
					PhotoVO photoVo = new PhotoVO();
					photoVo.setPhotoID(entryVo.getPhotoID2());
					String goodPhoto = entryVo.getGoodPhoto2();
					photoVo.setGoodPhoto(goodPhoto);
					photoVo.setContactId(String.valueOf(serial));
					boolean result =	addPhotoRecord(photoVo,connection);
				}
				if(entryVo.getPhotoID3() != null && entryVo.getPhotoID3() != ""){
					PhotoVO photoVo = new PhotoVO();
					photoVo.setPhotoID(entryVo.getPhotoID3());
					String goodPhoto = entryVo.getGoodPhoto3();
					photoVo.setGoodPhoto(goodPhoto);
					photoVo.setContactId(String.valueOf(serial));
					boolean result =	addPhotoRecord(photoVo,connection);
				}
				if(entryVo.getPhotoID4() != null && entryVo.getPhotoID4() != ""){
					PhotoVO photoVo = new PhotoVO();
					photoVo.setPhotoID(entryVo.getPhotoID4());
					String goodPhoto = entryVo.getGoodPhoto4();
					photoVo.setGoodPhoto(goodPhoto);
					photoVo.setContactId(String.valueOf(serial));
					boolean result =	addPhotoRecord(photoVo,connection);
				}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				
				stmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error(e);
			}

		}

		return false;
	}

	private int getNumber(String strArgs)
	{
		if(strArgs != null && !strArgs.equals("") ){
			return Integer.parseInt(strArgs);
		}else
			return 0;
	}
	public boolean addChildRecord(ChildVO childvo,Connection con){

		PreparedStatement stmt = null;
		//Connection connection = null;
		String SQL =  " INSERT INTO CHILDREN_MASTER (PARENT_ID, NAME ,CHILD_DOB ,GENDER , ENROLLED_GUJ_CLASSES) " +
		" VALUES (?, ?, ?, ?, ?) ";
		try {
			//connection = getConnection();
			stmt = con.prepareStatement(SQL);

			stmt.setInt(1,childvo.getParentId());
			stmt.setString(2,childvo.getChildName());
			stmt.setDate(3, getSQLDate(childvo.getChildDob()));
			stmt.setString(4, childvo.getChildGender());
			stmt.setString(5, childvo.getEnrolledGujClass());
			int result = stmt.executeUpdate();
			log.info("result :: " + result);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error(e);
			}

		}

		return true;


	}

	public boolean addPhotoRecord(PhotoVO photoVo,Connection con){

		PreparedStatement stmt = null;
		//Connection connection = null;
		String SQL =  " INSERT INTO PHOTO_DETAIL (CONTACT_ID, PHOTO_ID ,GOOD_PHOTO)  VALUES (?, ?, ?) ";
		try {
			//connection = getConnection();
			stmt = con.prepareStatement(SQL);

			stmt.setString(1,photoVo.getContactId());
			stmt.setString(2,photoVo.getPhotoID());
			stmt.setString(3, photoVo.getGoodPhoto());
			
			int result = stmt.executeUpdate();
			log.info("result :: " + result);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error(e);
			}

		}

		return true;


	}

	private java.sql.Date getSQLDate(java.util.Date date)
	{
		if(date == null )
			return null;

		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}
	private java.util.Date getDate(java.sql.Date date)
	{
		if(date == null)
			return null;
		java.util.Date sqlDate = new java.util.Date(date.getTime());
		return sqlDate;
	}

}
