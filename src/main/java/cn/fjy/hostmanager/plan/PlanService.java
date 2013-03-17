package cn.fjy.hostmanager.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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

	public void save(Integer planId, String planName,
			Vector<Vector<Object>> rows) {
		Plan plan = new Plan();
		plan.setName(planName);
		plan.setId(planId);
		if (rows != null) {
			List<Domain> domainList = new ArrayList<Domain>();
			for (Vector<Object> row : rows) {
				Domain domain = new Domain();
				domain.setIp(row.get(0).toString());
				domain.setDomain(row.get(1).toString());
				domainList.add(domain);
			}
			plan.setDomainList(domainList);
			dao.update(plan);
		}
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
	
	public void initDatabase(){
		dao.init();
	}

}
