package com.xy.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xy.o2o.dto.ShopExecution;
import com.xy.o2o.entity.Area;
import com.xy.o2o.entity.PersonInfo;
import com.xy.o2o.entity.Shop;
import com.xy.o2o.entity.ShopCategory;
import com.xy.o2o.enums.ShopStateEnum;
import com.xy.o2o.exceptions.ShopOperationException;
import com.xy.o2o.service.AreaService;
import com.xy.o2o.service.ShopCategoryService;
import com.xy.o2o.service.ShopService;
import com.xy.o2o.util.CodeUtil;
import com.xy.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/shopoperation")
	public String shopOperation() {
		// spring-web.xml已经配置好前后缀
		return "shop/shopoperation";
	}
	@RequestMapping(value="/getshopbyid",method=RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getShopById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		if(shopId>-1L) {
			try {
			Shop shop = shopService.getShopById(shopId);
			List<Area> areaList = areaService.getAreaList();
			modelMap.put("shop", shop);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
			}catch(Exception e){
				modelMap.put("success", false);
				modelMap.put("errMes", "Empty shopId");
			}
		}
		return modelMap;
	}
	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入错误的验证码");
			return modelMap;
		}
		// 1.接受并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		// 2.注册店铺
		if (shop != null && shopImg != null) {
			// session to do
			PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
			shop.setOwner(owner);
			ShopExecution se;
			try {
				se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				@SuppressWarnings("unchecked")
				List<Shop> shopList = (List<Shop>)request.getAttribute("shopList");
				if(shopList == null || shopList.size() == 0) {
					shopList = new ArrayList<Shop>();
				}
				shopList.add(se.getShop());
				request.getSession().setAttribute("shopList",shopList);
				
				if (se.getState() == ShopStateEnum.CHECK.getState())
					modelMap.put("success", true);
				else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
		// 3.返回结果
	}
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入错误的验证码");
			return modelMap;
		}
		// 1.接受并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");

		} 
		// 2.修改店铺
		if (shop != null && shop.getShopId() != null) {
		
			ShopExecution se;
			try {
				if(shopImg == null)
					se = shopService.modifyShop(shop, null, null);
				else
					se = shopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if (se.getState() == ShopStateEnum.SUCCESS.getState())
					modelMap.put("success", true);
				else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺Id");
			return modelMap;
		}
		// 3.返回结果
	}
	/*
	 * private static void inputStreamToFile(InputStream ins,File file) {
	 * FileOutputStream os = null; try { os = new FileOutputStream(file); int
	 * bytesRead =0; byte[] buffer = new byte[1024]; while((bytesRead =
	 * ins.read(buffer)) != -1) { os.write(buffer, 0, bytesRead); } } catch
	 * (Exception e) { throw new
	 * RuntimeException("调用inputstream产生异常："+e.getMessage()); }finally { try { if(os
	 * != null) os.close(); if(ins != null) ins.close(); } catch (IOException e) {
	 * throw new RuntimeException("inputstream关闭io产生异常："+e.getMessage()); } } }
	 */
}
