package cn.fjy.hostmanager.pojo;

/**
 * Description: Domain.java
 * All Rights Reserved.
 * @version 1.0  2013-3-13 下午3:55:12  
 * @author Gray(jy.feng@zuche.com) 
 */

public class Domain {
    
    private Integer id;
    
    private String ip;
    
    private String domain;
    
    private Integer planId;

    public Integer getPlanId() {
		return planId;
	}

	public void setPlanId(Integer planId) {
		this.planId = planId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

}
