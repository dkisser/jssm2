package org.lf.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

public class PYMTest {
	private Logger logger = Logger.getLogger(PYMTest.class);
	
	@Test
	public void testPYM() {
		String pym = StringUtils.toShortPinYin("宣告失踪", 5);
		System.out.println(pym);
	}

	private void updatePYM(String tablename, String srcColumn, String tarColumn, int top) {
		Connection cnn = null; // 数据库连接对象
		PreparedStatement ps = null; // 。。
		ResultSet rs = null; // 结果集
		String pym = null; // 拼音码
		String sql = null; // sql语句
		Savepoint sp = null; // 事务回滚点
		
		String chinese = null;

		// 获取与数据库的连接
		cnn = DBUtils.getConnection();

		// 查出本表中所有的mc字段的值，并存入list中
		sql = "select distinct " + srcColumn + " from " + tablename;
		try {
			ps = cnn.prepareStatement(sql);

			rs = ps.executeQuery();
			List<String> list = new ArrayList<String>();

			while (rs.next()) {
				String s = rs.getString(srcColumn);
				list.add(s);
			}

			// 设置事务回滚保存点
			sp = cnn.setSavepoint();

			for (String str : list) {
				chinese = str;
				// 转换中文字符串，生成修改pym字段的sql语句
				pym = StringUtils.toShortPinYin(chinese, 5).toLowerCase();
				pym = pym.length() < top ? pym : pym.substring(0, top);
				sql = "update " + tablename + " set " + tarColumn + "=?" 
						+ " where " + srcColumn + "=?";

				// 执行sql语句
				ps = cnn.prepareStatement(sql);
				ps.setString(1, pym);
				ps.setString(2, str);
				ps.executeUpdate();
			}

			// 提交事务
			cnn.commit();

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (cnn != null) {
				cnn.close();
			}

		} catch (Exception e) {
//			e.printStackTrace();
			logger.error("update " + tablename + " " + chinese + " translate to pinyin error");
			try {
				if (sp != null) {
					cnn.rollback(sp);
				} else {
					cnn.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@Test
	public void testUpdatePYM() {
		// exists error
//		updatePYM("c_ay", "mc", "pym", 5);
		
		// finished.
//		updatePYM("c_fy", "fymc", "pym", 5);
//		updatePYM("c_ahz", "ahz", "pym", 5);
//		updatePYM("c_ajian_lx", "mc", "pym", 5);
//		updatePYM("c_ajuan_fl", "mc", "pym", 5);
//		updatePYM("c_ajuan_lx", "mc", "pym", 5);
//		updatePYM("c_ajuan_ml", "mc", "pym", 5);
//		updatePYM("c_ajuan_yjfl", "mc", "pym", 5);
//		updatePYM("c_aqjb", "mc", "pym", 5);
//		updatePYM("c_jgdm", "mc", "pym", 5);
//		updatePYM("c_mlpc", "pcmc", "pym", 5);
//		updatePYM("c_spcx", "mc", "pym", 5);
		updatePYM("c_spjg", "mc", "pym", 5);
//		updatePYM("c_spzh", "zh", "pym", 5);
//		updatePYM("c_yhzw", "mc", "pym", 5);
	}
}
