package com.xy.o2o.service;

import java.io.InputStream;

import org.apache.tomcat.util.IntrospectionUtils;

import com.xy.o2o.dto.ShopExecution;
import com.xy.o2o.entity.Shop;
import com.xy.o2o.exceptions.ShopOperationException;

public interface ShopService {
	ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;
	Shop getShopById(long shopId);
	ShopExecution modifyShop(Shop shop,InputStream shopImgInputStream,String fileName);
}
