/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年9月20日下午3:11:49
 * @version 1.0
 */
package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.entity.system.User;
import com.JavaClub.service.Interface.CarouselService;
import com.JavaClub.util.Connect;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;

/**
 * @descript (轮播图管理)
 * @author 李海涛
 * @createTime 2016年9月20日下午3:11:49
 * @version 1.0
 */
@Controller
@RequestMapping(value="/carouselController")
public class CarouselController extends BaseController {

    @Autowired
    CarouselService carouselService;
    
    @SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/queryCarouselInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryPhotoInfo() throws Exception {
        ModelAndView mv=new ModelAndView("carousel/carousel_list");
        PageData pd = new PageData();
        pd = this.getPageData();

        //设置接口的加密
        Verification.EncodeKeyNo(pd, "name");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询轮播接口
        Map<String, Object> queryPhotoInfo = Connect.sendConnectByPdToMap("Interface/carouselInterface/queryCarouselInfo", pd, "POST");
        
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(queryPhotoInfo)){
            //得到轮播图信息JSON数据中的data数据
            Map<String,Object> dataMap_carouselInfo = (Map<String, Object>)  queryPhotoInfo.get("data");
            List<Map<String, Object>> carouselInfo = (List<Map<String, Object>>) dataMap_carouselInfo.get("carouselInfo");
            mv.addObject("carouselInfo",carouselInfo);
           
            //分页的拼接
            Page page=Verification.getPage(dataMap_carouselInfo);
            mv.addObject("page", page);
        }
        mv.addObject("pd", pd);
        return mv;
    }
    
    /**
     * 
     * @descript (增加轮播图)
     * @author 李海涛
     * @since 2016年9月18日下午3:03:35
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/createCarousel",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String createCarousel()throws Exception{
       PageData pd=new PageData();
       pd=this.getPageData();
       pd.put("id", this.get32UUID());
       User user=this.getUser();
       pd.put("userId", user.getUSER_ID());
       pd.put("userName", user.getNAME());
       pd.put("areaCode", user.getAREA_CODE());
       return Verification.getResultString(carouselService.insertCarouse(pd));
    }
   
   
    /**
     * 
     * @descript (跳转修改播图)
     * @author 李海涛
     * @since 2016年9月21日下午6:53:49
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/toUpdateCarousel", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView toUpdateCarousel() throws Exception {
        ModelAndView mv=new ModelAndView("carousel/carousel_edit");
        PageData pd = new PageData();
        pd = this.getPageData();
        //设置接口的加密
        Verification.EncodeKeyNo(pd, "name");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询轮播接口
        Map<String, Object> queryPhotoInfo = Connect.sendConnectByPdToMap("Interface/carouselInterface/queryCarouselInfo", pd, "POST");
        
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(queryPhotoInfo)){
            //得到轮播图信息JSON数据中的data数据
            Map<String,Object> dataMap_carouselInfo = (Map<String, Object>)  queryPhotoInfo.get("data");
            Map<String, Object> carouselInfo = ListUtils.getListMap((List<Map<String, Object>>) dataMap_carouselInfo.get("carouselInfo"), 0);
            mv.addObject("carouselInfo",carouselInfo);
        }
        mv.addObject("pd", pd);
        return mv;
    }
    /**
     * 
     * @descript (修改播图)
     * @author 李海涛
     * @since 2016年9月18日下午3:03:35
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/updateCarousel",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String updateCarousel()throws Exception{
       PageData pd=new PageData();
       pd=this.getPageData();
       User user=this.getUser();
       pd.put("userId", user.getUSER_ID());
       pd.put("userName", user.getNAME());
       pd.put("areaCode", user.getAREA_CODE());
       return Verification.getResultString(carouselService.updateCarouse(pd));
    }
    
    /**
     * 
     * @descript (删除轮播图)
     * @author 李海涛
     * @since 2016年9月18日下午3:03:35
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/deleteCarousel",produces="text/html;charset=UTF-8")
    @ResponseBody
    public String deleteCarousel()throws Exception{
       PageData pd=new PageData();
       pd=this.getPageData();
       return Verification.getResultString(carouselService.deleteCarouse(pd));
    }
}
