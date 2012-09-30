package com.baps.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.baps.dao.DataAccessService;
import com.baps.vo.ContactEntryVO;
import com.opensymphony.xwork2.ActionSupport;

public class FindContactService extends ActionSupport implements ServletRequestAware {
	
	private String firstName;
	private String lastName;
	private String mobile;
	private String email;
	private String spouseName;
	private String spouseEmail;
	private String spouseCellPhone;
	private String address;
	private String keyword;
	
	private List<ContactEntryVO> searchResult;
	private HttpServletRequest request;
	private ContactEntryVO searchObj;
	private String findSerial;
	
	Logger log = Logger.getLogger(FindContactService.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String execute()
	{
		DataAccessService service = new DataAccessService();
		List<ContactEntryVO> result = null;
		//List<ContactEntryVO> result =  service.getRecord(firstName, lastName, mobile,email,spouseName,spouseEmail,spouseCellPhone,address);
		if(null != keyword && !"".equals(keyword))
		 result =  service.getRecordForGenericKeyword(keyword);
		
		setSearchResult(result);
		System.out.println(firstName);
		if(result != null) {
			request.getSession().setAttribute("searchResult",result);
			System.out.println("Found Records :: " + result.size());
		}
		return "success";
		
		
		
	}
	
	public String getSearchedRecord()
	{
		//List<ContactEntryVO> tempResult = (List<ContactEntryVO>)request.getSession(false).getAttribute("searchResult");
		if(findSerial != null && !findSerial.equals("")){
			
			DataAccessService service = new DataAccessService();
			ContactEntryVO entry = service.getRecord(findSerial);
			 setSearchObj(entry);
			/*for(ContactEntryVO entry : tempResult){
				if(entry.getSerialNumber().equals(findSerial)){
					setSearchObj(entry);
					return "success";
				}
			}
			*/
		}
		return "success";
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<ContactEntryVO> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List<ContactEntryVO> searchResult) {
		this.searchResult = searchResult;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
		
	}

	public ContactEntryVO getSearchObj() {
		return searchObj;
	}

	public void setSearchObj(ContactEntryVO searchObj) {
		this.searchObj = searchObj;
	}

	public String getFindSerial() {
		return findSerial;
	}

	public void setFindSerial(String findSerial) {
		this.findSerial = findSerial;
	}
	public List getPendingNumber()
	{
		int totalNum = 0;
		List tempList = new ArrayList();
		if(searchObj != null  && searchObj.getChildrens() != null){
			totalNum = searchObj.getChildrens().size();
		}
		for(int i = totalNum+1 ; i <= 4 ; i++){
			tempList.add(i);
		}
		return tempList;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getSpouseEmail() {
		return spouseEmail;
	}

	public void setSpouseEmail(String spouseEmail) {
		this.spouseEmail = spouseEmail;
	}

	public String getSpouseCellPhone() {
		return spouseCellPhone;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setSpouseCellPhone(String spouseCellPhone) {
		this.spouseCellPhone = spouseCellPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	

	
}
