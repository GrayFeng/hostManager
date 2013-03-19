package cn.fjy.hostmanager.views;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import cn.fjy.hostmanager.pojo.Domain;

/**
 * hostManager
 * 
 * @date 2013-3-17 下午3:00:39
 * @author Gray(tyfjy823@gmail.com)
 * @version 1.0
 */
public class TableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private String[] titles = {"IP", "域名"};

	private Vector<Vector<Object>> rows;

	public TableModel() {
		initRows(null);
	}

	public void initRows(List<Domain> domainList) {
		if (rows == null) {
			rows = new Vector<Vector<Object>>();
		} else {
			rows.clear();
		}
		if (domainList != null) {
			for (Domain domain : domainList) {
				Vector<Object> vector = new Vector<Object>();
				vector.add(0, domain.getIp());
				vector.add(1, domain.getDomain());
				rows.add(vector);
			}
		}
	}

	public int getColumnCount() {
		return titles.length;
	}

	public int getRowCount() {
		return rows.size();
	}

	public Object getValueAt(int arg0, int arg1) {
		return rows.get(arg0).get(arg1);
	}

	@Override
	public String getColumnName(int column) {
		return titles[column];
	}

	@Override
	public int findColumn(String arg0) {
		// TODO Auto-generated method stub
		return super.findColumn(arg0);
	}

	public void del(int index) {
		if (index >= 0 && index < rows.size()) {
			rows.remove(index);
		}
	}

	@Override
	public Class<?> getColumnClass(int index) {
		return super.getColumnClass(index);
	}

	public void add(Integer index, Vector<Object> row) {
		if (index != null) {
			rows.add(index, row);
		} else {
			rows.add(row);
		}
	}

	public Vector<Vector<Object>> getRows() {
		return rows;
	}

}
