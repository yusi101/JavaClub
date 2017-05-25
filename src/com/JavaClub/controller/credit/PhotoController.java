package com.JavaClub.controller.credit;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.entity.system.User;
import com.JavaClub.service.Interface.PhotoService;
import com.JavaClub.util.Connect;
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;


/**
 * 
 * @descript (轮播图)
 * @author 汤彬
 * @createTime 2016年9月19日下午4:12:58
 * @version 1.0
 */
@Controller
@RequestMapping(value="/photoController")
public class PhotoController  extends BaseController{
	
	final static String attachMent_Type = "轮播图片"; //设置图片类型
	
	@Autowired
	public PhotoService photoService; //轮播图的Service
	/**
	 * 
	 * @descript (轮播图列表)
	 * @author 汤彬
	 * @since 2016年9月19日下午4:13:49
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/queryPhotoInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView queryPhotoInfo() throws Exception {
        ModelAndView mv=new ModelAndView("carousel/carousel_list");
        PageData pd = new PageData();
        pd = this.getPageData();

        //设置接口的加密
        Verification.EncodeKeyNo(pd, "attachMent_Name");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询轮播接口
        Map<String, Object> queryPhotoInfo = Connect.sendConnectByPdToMap("Interface/photoInterface/queryPhotoInfo", pd, "POST");
        
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(queryPhotoInfo)){
	        //得到轮播图信息JSON数据中的data数据
	        Map<String,Object> dataMap_Photo = (Map<String, Object>)  queryPhotoInfo.get("data");
	        List<Map<String, Object>> photoInfo = (List<Map<String, Object>>) dataMap_Photo.get("photoInfo");
	        mv.addObject("photoInfo",photoInfo);
	       
	        //分页的拼接
	        Page page=Verification.getPage(dataMap_Photo);
	        mv.addObject("page", page);
        }
        mv.addObject("pd", pd);
        return mv;
    }
	
	/**
	 * 
	 * @descript (跳到编辑页面的方法)
	 * @author 汤彬
	 * @since 2016年9月20日下午1:50:53
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/toQueryPhotoInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView toQueryPhotoInfo() throws Exception {
        ModelAndView mv=new ModelAndView("carousel/carousel_edt");
        PageData pd = new PageData();
        pd = this.getPageData();

        //设置接口的加密
        Verification.EncodeKeyNo(pd, "attachMent_Name");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询轮播接口
        Map<String, Object> queryPhotoInfo = Connect.sendConnectByPdToMap("Interface/photoInterface/queryPhotoInfo", pd, "POST");
        
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(queryPhotoInfo)){
	        //得到轮播图信息JSON数据中的data数据
	        Map<String,Object> dataMap_Photo = (Map<String, Object>)  queryPhotoInfo.get("data");
	        List<Map<String, Object>> photoInfo = (List<Map<String, Object>>) dataMap_Photo.get("photoInfo");
	        mv.addObject("photoInfo",photoInfo);
	       
	        //分页的拼接
	        Page page=Verification.getPage(dataMap_Photo);
	        mv.addObject("page", page);
        }
        mv.addObject("pd", pd);
        return mv;
    }
	
	
	/**
	 * 
	 * @descript (添加轮播图)
	 * @author 汤彬
	 * @since 2016年9月19日下午5:40:30
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/createPhotoInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String createPhotoInfo() throws Exception {
		PageData  pd=this.getPageData();
		
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		
		//添加UUID
		pd.put("ID", this.get32UUID());
		pd.put("attachMent_Type", attachMent_Type);
		pd.put("attachMent_CreateUser", user.getNAME());
		
		
		return Verification.getResultString(photoService.createPhoto(pd));
	}
	
    
    /**
     * 
     * @descript (编辑轮播图)
     * @author 汤彬
     * @since 2016年9月20日下午2:36:59
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updatePhotoInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updatePhotoInfo() throws Exception {
		PageData  pd=this.getPageData();
		return Verification.getResultString(photoService.updatePhoto(pd));
	}
	
    
    /**
     * 
     * @descript (删除轮播图)
     * @author 汤彬
     * @since 2016年9月20日下午3:15:32
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deletePhotoInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deletePhotoInfo() throws Exception {
		PageData  pd=this.getPageData();
		return Verification.getResultString(photoService.deletePhoto(pd));
	}
}
