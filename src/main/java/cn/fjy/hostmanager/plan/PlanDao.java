package cn.fjy.hostmanager.plan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.fjy.hostmanager.pojo.Domain;
import cn.fjy.hostmanager.pojo.Plan;
import cn.fjy.hostmanager.util.JDBCUtil;

import com.google.common.collect.Lists;

/**
 * Description: ResourceFileManager.java All Rights Reserved.
 * 
 * @version 1.0 2013-3-13 上午11:58:00
 * @author Gray(jy.feng@zuche.com)
 */
public class PlanDao {

	public void add(Plan plan) {
		if (plan != null) {
			String planSql = "INSERT INTO T_PLAN (NAME) VALUES('"
					+ plan.getName() + "')";
			List<Domain> domainList = plan.getDomainList();
			if (domainList != null && domainList.size() > 0) {
				Integer id = JDBCUtil.insert(planSql);
				if (id != null && id > 0) {
					List<String> domainSqls = new ArrayList<String>();
					for (Domain domain : domainList) {
						String sql = "INSERT INTO T_DOMAIN (IP,DOMAIN,PLAN_ID) VALUES('"
								+ domain.getIp()
								+ "','"
								+ domain.getDomain()
								+ "'," + id + ")";
						domainSqls.add(sql);
					}
					String[] sqls = new String[domainSqls.size()];
					JDBCUtil.modify(domainSqls.toArray(sqls));
				}
			}
		}
	}

	public void del(Integer planId) {
		if (planId != null) {
			String planSql = "DELETE FROM T_PLAN WHERE ID =" + planId;
			String domainSql = "DELETE FROM T_DOMAIN WHERE PLAN_ID =" + planId;
			JDBCUtil.modify(domainSql);
			JDBCUtil.modify(planSql);
		}
	}

	public void update(Plan plan) {
		if (plan != null) {
			if (plan.getId() != null) {
				del(plan.getId());
			}
			add(plan);
		}
	}

	public List<Plan> fingPlanList() {
		List<Plan> planList = null;
		String sql = "SELECT * FROM T_PLAN";
		List<Map<String, Object>> resultList = JDBCUtil.query(sql);
		if (resultList != null) {
			planList = Lists.newArrayList();
			for (Map<String, Object> map : resultList) {
				Plan plan = map2Plan(map);
				planList.add(plan);
			}
		}
		return planList;
	}

	public Plan findPlan(Integer id) {
		Plan plan = null;
		String sql = "SELECT * FROM T_PLAN WHERE ID=" + id;
		List<Map<String, Object>> resultList = JDBCUtil.query(sql);
		if (resultList != null && resultList.size() > 0) {
			Map<String, Object> map = resultList.get(0);
			plan = map2Plan(map);
			plan.setDomainList(findDomainList(id));
		}
		return plan;
	}

	public List<Domain> findDomainList(Integer id) {
		List<Domain> list = null;
		String sql = "SELECT * FROM T_DOMAIN WHERE PLAN_ID=" + id;
		List<Map<String, Object>> resultList = JDBCUtil.query(sql);
		if (resultList != null && resultList.size() > 0) {
			list = Lists.newArrayList();
			for (Map<String, Object> map : resultList) {
				Domain domain = map2Domain(map);
				list.add(domain);
			}
		}
		return list;
	}

	public void init() {
		String basePath = System.getProperty("user.dir");
		File dataFile = new File(basePath + File.separator + "data");
		if(!dataFile.exists()){
			JDBCUtil.modify("CREATE TABLE T_PLAN (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,NAME VARCHAR(100) NOT NULL)");
			JDBCUtil.modify("CREATE TABLE T_DOMAIN (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,IP VARCHAR(100) NOT NULL,DOMAIN VARCHAR(100) NOT NULL,PLAN_ID INTEGER NOT NULL)");
		}
	}

	private Plan map2Plan(Map<String, Object> map) {
		Plan plan = new Plan();
		plan.setId(Integer.valueOf(map.get("ID").toString()));
		plan.setName(map.get("NAME").toString());
		return plan;
	}

	private Domain map2Domain(Map<String, Object> map) {
		Domain domain = new Domain();
		domain.setId(Integer.valueOf(map.get("ID").toString()));
		domain.setIp(map.get("IP").toString());
		domain.setDomain(map.get("DOMAIN").toString());
		domain.setPlanId(Integer.valueOf(map.get("PLAN_ID").toString()));
		return domain;
	}

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
	}

}
