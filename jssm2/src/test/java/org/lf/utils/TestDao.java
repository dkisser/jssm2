package org.lf.utils;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lf.jssm.db.dao.JEnvMapper;
import org.lf.jssm.db.dao.JWlryMapper;
import org.lf.jssm.db.pojo.JWlry;
import org.lf.jssm.service.model.AJJYOuterStaticModel;
import org.lf.jssm.service.statistics.AJJYOuterStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestDao {
	@Autowired
	private JWlryMapper jwlryDao;
	@Autowired
	private AJJYOuterStaticService ajjyOuterService;
	@Test
	public void test1(){
		AJJYOuterStaticModel outer = ajjyOuterService.getAllDataByYear(2016);
		System.out.println(outer.getYear());
		System.out.println(outer.getPermit());
		System.out.println(outer.getUnpermit());
	}
}
