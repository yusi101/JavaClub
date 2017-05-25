/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年9月20日下午3:11:49
 * @version 1.0
 */
package com.JavaClub.controller.credit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.credit.HomePageService;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;

/**
 * @descript (轮播图管理)
 * @author 李海涛
 * @createTime 2016年9月20日下午3:11:49
 * @version 1.0
 */
@Controller
@RequestMapping(value="/homePageController")
public class HomePageController extends BaseController {

    @Autowired
    HomePageService homePageService;
    
   
    /**
     * 
     * @descript (跳转修改播图)
     * @author 李海涛
     * @since 2016年9月21日下午6:53:49
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toHomePage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView toHomePage() throws Exception {
        ModelAndView mv=new ModelAndView("system/homePage");
        PageData pd = new PageData();
        pd = this.getPageData();
       
        mv.addObject("pd", pd);
        return mv;
    }
    
    
    /**
     * 
     * @descript (查询各行业的牌照申请、领取、未领取情况)
     * @author 李海涛
     * @since 2016年9月21日下午6:53:49
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"rawtypes", "unchecked" })
    @RequestMapping(value = "/queryIndustryphyCount", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String queryIndustryphyCount() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        List industryphyCounts= homePageService.queryIndustryphyCount(pd);
        // 行业门类列表
        List industryphyList=new ArrayList<>();
        //各行业牌照申请列表
        List qrcodeApplyList=new ArrayList<>();
        //各行业牌照领取列表
        List qrcodeReceiveList=new ArrayList<>();
        //各行业牌照未领取列表
        List qrcodeNoReceiveList=new ArrayList<>();
        
        for (int i = 0; i < ((List<Map<String, Object>>) industryphyCounts.get(0)).size(); i++) {
            industryphyList.add(i,((List<Map<String, Object>>) industryphyCounts.get(0)).get(i).get("COUNT"));
            qrcodeApplyList.add(i,((List<Map<String, Object>>) industryphyCounts.get(1)).get(i).get("COUNT"));
            qrcodeReceiveList.add(i,((List<Map<String, Object>>) industryphyCounts.get(2)).get(i).get("COUNT"));
            qrcodeNoReceiveList.add(i,((List<Map<String, Object>>) industryphyCounts.get(3)).get(i).get("COUNT"));
            
        }
        industryphyCounts.set(0, industryphyList);
        industryphyCounts.set(1, qrcodeApplyList);
        industryphyCounts.set(2, qrcodeReceiveList);
        industryphyCounts.set(3, qrcodeNoReceiveList);
        
        return MyGson.toJson(industryphyCounts);
    }
    
    /**
     * 
     * @descript (查询牌照各类型和状态下的数量)
     * @author 李海涛
     * @since 2016年9月21日下午6:53:49
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/queryApplyTypeAndStatusCount", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String queryApplyTypeAndStatusCount() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        List qrcodeVariousStatusCount= homePageService.queryQrcodeVariousStatusCount(pd);
        return MyGson.toJson(qrcodeVariousStatusCount);
    }
}
