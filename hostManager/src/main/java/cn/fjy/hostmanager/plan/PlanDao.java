package cn.fjy.hostmanager.plan;

import cn.fjy.hostmanager.pojo.Domain;
import cn.fjy.hostmanager.pojo.Plan;
import cn.fjy.hostmanager.util.JDBCUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description: ResourceFileManager.java All Rights Reserved.
 *
 * @version 1.0 2013-3-13 上午11:58:00
 * @author Gray(jy.feng@zuche.com)
 */
public class PlanDao {

    public void add(Plan plan) {
        if (plan != null) {
            String planSql = "INSERT INTO T_PLAN (NAME) VALUES('" + plan.getName() + "')";
            List<Domain> domainList = plan.getDomainList();
            if (domainList != null && domainList.size() > 0) {
                JDBCUtil.modify(planSql);
                List<Map<String, Object>> planValues = JDBCUtil.query("SELECT ID FROM T_PLAN WHERE NAME='" + plan.getName() + "'");
                if (planValues != null && planValues.size() > 0) {
                    List<String> domainSqls = new ArrayList<String>();
                    for (Domain domain : domainList) {
                        String sql = "INSERT INTO T_DOMAIN (ip,domain,paln_id) VALUES('" + domain.getIp() + "','" + domain.getDomain() + "'," + planValues.get(0).get("ID") + ")";
                        domainSqls.add(sql);
                    }
                    JDBCUtil.modify((String[]) domainSqls.toArray());
                }
            }
        }
    }

    public void del(Integer planId) {
    }

    public void update(Plan plan) {
    }

    public void init() {
        JDBCUtil.modify("CREATE TABLE T_PLAN (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,NAME VARCHAR(100) NOT NULL)");
        JDBCUtil.modify("CREATE TABLE T_DOMAIN (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,IP VARCHAR(100) NOT NULL,DOMAIN VARCHAR(100) NOT NULL,PLAN_ID INTEGER NOT NULL)");
    }
}
