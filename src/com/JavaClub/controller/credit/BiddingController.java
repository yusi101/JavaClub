package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Connect;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript  获取招标信息
 * @author 李坡
 * @createTime 2016年9月14日上午10:54:34
 * @version 1.0
 */
@Controller
@RequestMapping(value="/biddingController")
public class BiddingController extends BaseController{

	/**
	 * 
	 * @descript  获取招标信息
	 * @author 李坡
	 * @since 2016年9月14日上午10:54:40
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/queryBiddingInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryBiddingInfo() throws Exception {
        ModelAndView mv=new ModelAndView("bidding/bidding_list");
        PageData pd = new PageData();
        pd = this.getPageData();

		//设置接口的加密
		Verification.EncodeKeyNo(pd, "entname");
		//设置分页的页码和每页显示的条数
		Verification.setPageParameter(pd);
		//调用查询招标信息接口
		Map<String, Object> querybidding = Connect.sendConnectByPdToMap("Interface/biddingInterface/queryBinddingInfo", pd, "POST");
		Verification.DecodeKeyNo(pd, "entname");
		//判断接口调用是否成功
		if(Verification.StatusIsSuccess(querybidding)){
			//得到商标信息JSON数据中的data数据
			Map<String,Object> dataMap_bidding = (Map<String, Object>) querybidding.get("data");
			List<Map<String, Object>> bidding = (List<Map<String, Object>>) dataMap_bidding.get("biddingInfo");
			mv.addObject("bidding",bidding);
			//分页的拼接
			Page page=Verification.getPage(dataMap_bidding);
			mv.addObject("page", page);
		}

		mv.addObject("pd", pd);
		return mv;
    }
	
	
	
	/**
	 * 
	 * @descript 获取招标信息详情
	 * @author 李坡
	 * @since 2016年9月14日上午10:54:10
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryBiddingDetail", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryBiddingDetail() throws Exception{
		ModelAndView mv =  new ModelAndView("bidding/bidding_detail");
		PageData pd = new PageData();
		pd=this.getPageData();

		//设置接口的加密
		Verification.EncodeKeyNo(pd, "keyNo");
		//调用招标信息详情
		Map<String, Object> querybindding = Connect.sendConnectByPdToMap("Interface/biddingInterface/queryBinddingInfo", pd, "POST");
		Verification.DecodeKeyNo(pd, "keyNo");
		//判断接口调用是否成功
		if(Verification.StatusIsSuccess(querybindding)){
			//得到商标信息JSON数据中的data数据
			@SuppressWarnings("unchecked")
			Map<String,Object> dataMap_biddingt = (Map<String, Object>) querybindding.get("data");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> biddingt = (List<Map<String, Object>>) dataMap_biddingt.get("biddingInfo");
			Map<String, Object> map_biddingt= biddingt.get(0);
			mv.addObject("bidding",map_biddingt);
		}

		mv.addObject("pd", pd);
		return mv;
	}

}
 