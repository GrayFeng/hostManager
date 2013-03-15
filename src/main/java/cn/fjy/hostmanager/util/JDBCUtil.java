package cn.fjy.hostmanager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;

/**
 * Description: JDBCUtil.java All Rights Reserved.
 *
 * @version 1.0 2013-3-13 下午1:15:41
 * @author Gray(jy.feng@zuche.com)
 */
public class JDBCUtil {

    private static String default_db_name = "data";

    public static Connection getConnection(String dbFilePath) {
        Connection conn = null;
        try {
            if (dbFilePath == null || "".equals(dbFilePath.trim())) {
                dbFilePath = default_db_name;
            }
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void modify(String sql) {
        Connection conn = getConnection(null);
        Statement st = null;
        try {
            st = conn.createStatement();
            st.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, st, null);
        }
    }

    public static void modify(String[] sqls) {
        Connection conn = getConnection(null);
        Statement st = null;
        try {
            if (sqls != null) {
                conn.setAutoCommit(false);
                st = conn.createStatement();
                for (String sql : sqls) {
                    st.addBatch(sql);
                }
                st.executeBatch();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn, st, null);
        }
    }

    public static List<Map<String, Object>> query(String sql) {
        List<Map<String, Object>> list = null;
        Connection conn = getConnection(null);
        Statement st = null;
        ResultSet rs = null;
        if (conn != null) {
            try {
                st = conn.createStatement();
                rs = st.executeQuery(sql);
                if (rs != null) {
                    list = resultSet2List(rs);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(conn, st, rs);
            }
        }
        return list;
    }

    private static List<Map<String, Object>> resultSet2List(ResultSet rs) {
        List<Map<String, Object>> list = null;
        if (rs != null) {
            list = new ArrayList<Map<String, Object>>();
            try {
                while (rs.next()) {
                    Map<String, Object> map = resultSet2Map(rs);
                    if (map != null && map.size() > 0) {
                        list.add(map);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    private static Map<String, Object> resultSet2Map(ResultSet rs) {
        Map<String, Object> map = null;
        if (rs != null) {
            map = new HashMap<String, Object>();
            try {
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int columnCount = rsMetaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String cName = rsMetaData.getColumnLabel(i);
                    if (!Strings.isNullOrEmpty(cName)) {
                        map.put(cName, rs.getString(cName));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private static void close(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        JDBCUtil.modify("drop table test");
    }
}
