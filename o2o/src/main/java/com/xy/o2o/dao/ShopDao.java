package com.xy.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xy.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * 分页查询店铺,可输入的条件有：店铺名（模糊），店铺状态，店铺Id,店铺类别,区域ID
	 * 
	 * @param shopCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
			@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	
	/**
	 * 返回queryShopList总数
	 * 
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	/**
	 * 新增店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	/**
	 * 更新店铺
	 */
	int updateShop(Shop shop);
	/**
	 * 查询店铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
}
