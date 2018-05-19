package com.xy.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xy.o2o.dao.ShopCategoryDao;
import com.xy.o2o.entity.ShopCategory;
import com.xy.o2o.service.ShopCategoryService;
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
	@Autowired
	private ShopCategoryDao shopCategoryDao;

	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondotion) {
		// TODO Auto-generated method stub
		return shopCategoryDao.queryShopCategory(shopCategoryCondotion);
	}


}
