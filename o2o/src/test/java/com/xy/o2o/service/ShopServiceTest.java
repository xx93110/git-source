package com.xy.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xy.o2o.BaseTest;
import com.xy.o2o.dto.ShopExecution;
import com.xy.o2o.entity.Area;
import com.xy.o2o.entity.PersonInfo;
import com.xy.o2o.entity.Shop;
import com.xy.o2o.entity.ShopCategory;
import com.xy.o2o.enums.ShopStateEnum;
import com.xy.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;
	@Test
	public void testModifyShop() throws ShopOperationException,FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺名称");
		File shopImg = new File("D:/developer kits/Images/o2o/bizhi.png");
		InputStream is = new FileInputStream(shopImg);
		shopService.modifyShop(shop, is, "bizhi.png");
	}
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试2");
		shop.setShopDesc("test2");
		shop.setShopAddr("test2");
		shop.setPhone("tes2");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File shopImg = new File("D:/developer kits/Images/o2o/xiaohuangren.jpg");
		InputStream is = new FileInputStream(shopImg);
		ShopExecution se = shopService.addShop(shop,is,shopImg.getName());
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
	}
}
