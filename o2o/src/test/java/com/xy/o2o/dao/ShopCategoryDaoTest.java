package com.xy.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xy.o2o.BaseTest;
import com.xy.o2o.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{
	@Autowired
	private ShopCategoryDao shopCategoryDao;
	@Test
	public void testQueryShopCategory() {
		ShopCategory test1 = new ShopCategory();
		ShopCategory test2 = new ShopCategory();
		test1.setShopCategoryId(1L);
		test2.setParent(test1);
		List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(test2);
		assertEquals(1, shopCategoryList.size());
		
		
	}
}
