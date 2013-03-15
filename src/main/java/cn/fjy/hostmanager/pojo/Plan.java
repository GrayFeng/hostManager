package cn.fjy.hostmanager.pojo;

import java.util.List;

/**
 * Description: Plan.java
 * All Rights Reserved.
 * @version 1.0  2013-3-13 下午3:53:54  
 * @author Gray(jy.feng@zuche.com) 
 */

public class Plan {
	
	private Integer id;
	
	private String name;
	
	private List<Domain> domainList;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Domain> getDomainList() {
		return domainList;
	}

	public void setDomainList(List<Domain> domainList) {
		this.domainList = domainList;
	}
}
