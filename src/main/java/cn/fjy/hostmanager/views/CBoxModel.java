package cn.fjy.hostmanager.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import cn.fjy.hostmanager.pojo.Plan;

/**
 * hostManager
 * 
 * @date 2013-3-17 下午5:34:31
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class CBoxModel implements ComboBoxModel {

	private List<Plan> planList;

	private Integer selectIndex = 0;

	public CBoxModel() {
		initItems(null);
	}

	public void initItems(List<Plan> planList) {
		selectIndex = 0;
		if (planList == null) {
			planList = new ArrayList<Plan>();
		}
		if (planList.size() == 0) {
			Plan plan = new Plan();
			plan.setName("默认方案");
			planList.add(plan);
		}
		this.planList = planList;
	}

	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	public Object getElementAt(int arg0) {
		Plan plan = planList.get(arg0);
		return plan.getName();
	}

	public Integer getSelectIndex() {
		return planList.get(selectIndex).getId();
	}

	public int getSize() {
		return planList.size();
	}

	public void removeListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub

	}

	public Object getSelectedItem() {
		if (planList.size() == 0) {
			return null;
		}
		Plan plan = planList.get(selectIndex);
		return plan.getName();
	}

	public void setSelectedItem(Object anItem) {
		for (int i = 0; i < planList.size(); i++) {
			Plan plan = planList.get(i);
			String name = plan.getName();
			if (name.equals(anItem)) {
				selectIndex = i;
				break;
			}
		}
	}

}
