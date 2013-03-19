package cn.fjy.hostmanager.plan;

import java.util.List;
import java.util.Vector;

import com.google.common.collect.Lists;

import cn.fjy.hostmanager.host.HostFileSupport;
import cn.fjy.hostmanager.pojo.Domain;
import cn.fjy.hostmanager.pojo.Plan;

/**
 * Description: MappingPlanManager.java All Rights Reserved.
 * 
 * @version 1.0 2013-3-13 上午11:55:34
 * @author Gray(jy.feng@zuche.com)
 */

public class PlanService {

	private PlanDao dao = new PlanDao();
	
	private HostFileSupport hostFileSupport = new HostFileSupport();

	public void save(Integer planId, String planName,
			Vector<Vector<Object>> rows) {
		Plan plan = new Plan();
		plan.setName(planName);
		plan.setId(planId);
		List<Domain> domainList = rows2DomainList(rows);
		if (domainList != null) {
			plan.setDomainList(domainList);
			dao.update(plan);
		}
	}

	public List<Domain> rows2DomainList(Vector<Vector<Object>> rows) {
		List<Domain> domainList = null;
		if (rows != null && rows.size() > 0) {
			domainList = Lists.newArrayList();
			for (Vector<Object> row : rows) {
				Domain domain = new Domain();
				domain.setIp(row.get(0).toString());
				domain.setDomain(row.get(1).toString());
				domainList.add(domain);
			}
		}
		return domainList;
	}

	public void del(Integer planId) {
		dao.del(planId);
	}

	public List<Plan> fingPlanList() {
		return dao.fingPlanList();
	}

	public List<Domain> findDomainList(Integer planId) {
		return dao.findDomainList(planId);
	}

	public void initDatabase() {
		dao.init();
	}
	
	public boolean switchDNS(Vector<Vector<Object>> rows){
		boolean result = false;
		List<Domain> domainList = rows2DomainList(rows);
		if(domainList != null){
			result = hostFileSupport.addDomainMapping(domainList);
		}
		return result;
	}

}
