package com.JavaClub.controller.credit;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.entity.system.User;
import com.JavaClub.service.Interface.SelfUpdataService;
import com.JavaClub.service.credit.RespondedService;
import com.JavaClub.util.Base64Image;
import com.JavaClub.util.Connect;
import com.JavaClub.util.Const;
import com.JavaClub.util.FileUtil;
import com.JavaClub.util.Logger;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;


/**
 * 
 * @descript (企业自主更新)
 * @author 汤彬
 * @createTime 2016年9月14日上午10:56:45
 * @version 1.0
 */
@Controller
@RequestMapping(value="/selfUpdataController")
public class SelfUpdataController extends BaseController{
	
	 protected Logger logger = Logger.getLogger(this.getClass());
	 
	final static int statusOne = 1 ; //状态1 审核通过
	final static int statusTwo = 2 ; //状态2 审核失败
	
	
	@Autowired
	public SelfUpdataService selfUpdataService;
	
	@Autowired
	RespondedService respondedService;
	
	/**
	 * 
	 * @descript (企业自主更新列表)
	 * @author 汤彬
	 * @since 2016年9月14日上午10:56:16
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySelfUpdata", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView querySelfUpdata(RedirectAttributes attr) throws Exception{
		ModelAndView mv = new ModelAndView("selfupdata/enterpriseself_list");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		Subject currentUser = SecurityUtils.getSubject();  
	    Session session = currentUser.getSession();
	    User user = (User)session.getAttribute(Const.SESSION_USER);
	    
	    if(user.getPRIPID() != null && !"".equals(user.getPRIPID())){
	    	attr.addAttribute("pripid",user.getPRIPID());
	    	return new ModelAndView("redirect:/selfUpdataController/querySelfUpdatainfo");
	    }
	    
    	//设置接口的加密
        Verification.EncodesearchKey(pd, "entname");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询商标接口接口
        Map<String, Object> quertenteraddition = Connect.sendConnectByPdToMap2(Const.ENTERPRISEURL, pd, "POST");
        Verification.DecodesearchKey(pd, "entname");
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(quertenteraddition)){
            //得到商标信息JSON数据中的data数据
            @SuppressWarnings("unchecked")
			Map<String,Object> dataMap_enteraddition = (Map<String, Object>) quertenteraddition.get("data");
            @SuppressWarnings("unchecked")
			List<Map<String, Object>> enteraddition = (List<Map<String, Object>>) dataMap_enteraddition.get("Result");
            mv.addObject("Enteraddition",enteraddition);
            //分页的拼接
            Page page=Verification.getPage(dataMap_enteraddition);
            mv.addObject("page", page);
        }
        mv.addObject("pd", pd);
        return mv;
	}
	
	
	/**
	 * 
	 * @descript (某个企业自主更新列表)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:02:24
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/querySelfUpdatainfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView querySelfUpdatainfo() throws Exception {
        ModelAndView mv=new ModelAndView("selfupdata/selfupdata_list");
        PageData pd = new PageData();
        pd = this.getPageData();

        //设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询企业自主更新接口
        Map<String, Object> querySelfUpdataInfo = Connect.sendConnectByPdToMap("Interface/selfUpdataInterface/querySelfUpdata", pd, "POST");
        
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(querySelfUpdataInfo)){
	        //得到企业自主更新JSON数据中的data数据
	        Map<String,Object> dataMap_SelfUpdata = (Map<String, Object>) querySelfUpdataInfo.get("data");
	        List<Map<String, Object>> selfUpdataInfo = (List<Map<String, Object>>) dataMap_SelfUpdata.get("selfUpdataInfo");
	        mv.addObject("selfUpdataInfo",selfUpdataInfo);
	        //分页的拼接
	        Page page=Verification.getPage(dataMap_SelfUpdata);
	        mv.addObject("page", page);
        }    

        mv.addObject("pd", pd);
        return mv;
    }
	
	
	/**
	 * 
	 * @descript (企业自主更新审核列表)
	 * @author 汤彬
	 * @since 2016年9月19日上午10:28:24
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
    @RequestMapping(value = "/querySelfUpdataAuditingInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView querySelfUpdataAuditingInfo() throws Exception {
        ModelAndView mv=new ModelAndView("selfupdata/selfupdataauditing_list");
        PageData pd = new PageData();
        pd = this.getPageData();

        //设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询企业自主更新接口
        Map<String, Object> querySelfUpdataInfo = Connect.sendConnectByPdToMap("Interface/selfUpdataInterface/querySelfUpdata", pd, "POST");
        
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(querySelfUpdataInfo)){
	        //得到企业自主更新JSON数据中的data数据
	        Map<String,Object> dataMap_SelfUpdata = (Map<String, Object>) querySelfUpdataInfo.get("data");
	        List<Map<String, Object>> selfUpdataInfo = (List<Map<String, Object>>) dataMap_SelfUpdata.get("selfUpdataInfo");
	        mv.addObject("selfUpdataInfo",selfUpdataInfo);
	        //分页的拼接
	        Page page=Verification.getPage(dataMap_SelfUpdata);
	        mv.addObject("page", page);
        }        

        mv.addObject("pd", pd);
        return mv;
    }
	
	
	/**
	 * 
	 * @descript (企业自主更新审核详情)
	 * @author 汤彬
	 * @since 2016年9月19日下午2:35:17
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/querySelfUpdataAuditingDetailInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public ModelAndView querySelfUpdataAuditingDetailInfo() throws Exception {
        ModelAndView mv=new ModelAndView("selfupdata/selfupdataauditing_det");
        PageData pd = new PageData();
        pd = this.getPageData();

        //查询企业自主更新审核详情
        List<Map<String, Object>> selfUpdataInfo = selfUpdataService.querySelfUpdataAuditingDetailInfo(pd);
        //查询企业自主更新审核记录
        pd.put("relationId", pd.getString("ID"));
        List<Map<String, Object>> respondedInfo = respondedService.queryRespondedInfoByRelationId(pd);
        
        mv.addObject("selfUpdataInfo",selfUpdataInfo);
        mv.addObject("respondedInfo",respondedInfo);
        mv.addObject("pd", pd);
        return mv;
    }
	
	/**
	 * 
	 * @descript (跳转到添加企业列表页面)
	 * @author 汤彬
	 * @since 2016年9月14日下午3:02:41
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/toSelfUpdata")
	public ModelAndView toSelfUpdata()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		Map<String, Object> entMap = null;
		if(!"".equals(user.getPRIPID())){
			pd.put("pripid", user.getPRIPID());
			entMap = (Map<String, Object>) selfUpdataService.queryEntnameByPripid(pd);
		}
		
		mv.addObject("entMap", entMap);
		mv.addObject("pd", pd);
		mv.setViewName("selfupdata/selfupdata_add");
		
		return mv;
	}
	
	/**
	 * 
	 * @descript (跳转到企业更新审核页面)
	 * @author 汤彬
	 * @since 2016年9月18日下午2:57:12
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
	@RequestMapping(value="/toSelfUpdataAuditing")
	public ModelAndView toSelfUpdataAuditing()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData  pd=this.getPageData();
		
		//设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询专利接口
        Map<String, Object> querySelfUpdataDetailInfo = Connect.sendConnectByPdToMap("Interface/selfUpdataInterface/querySelfUpdata", pd, "POST");
		
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(querySelfUpdataDetailInfo)){
	        //得到企业自主更新JSON数据中的data数据
	        Map<String,Object> dataMap_SelfUpdataDetail = (Map<String, Object>) querySelfUpdataDetailInfo.get("data");
	        List<Map<String, Object>> selfUpdataInfo = (List<Map<String, Object>>) dataMap_SelfUpdataDetail.get("selfUpdataInfo");
	        mv.addObject("selfUpdataInfo",selfUpdataInfo);
        }
        
		mv.addObject("pd", pd);
		mv.setViewName("selfupdata/selfupdata_aud");
		return mv;
	}
	
	/**
	 * 
	 * @descript (跳转到企业更新拒审页面)
	 * @author 汤彬
	 * @since 2016年9月18日下午2:57:12
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
	@RequestMapping(value="/toSelfUpdataDenied")
	public ModelAndView toSelfUpdataDenied()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData  pd=this.getPageData();
		
		//设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询专利接口
        Map<String, Object> querySelfUpdataDetailInfo = Connect.sendConnectByPdToMap("Interface/selfUpdataInterface/querySelfUpdata", pd, "POST");
		
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(querySelfUpdataDetailInfo)){
	        //得到企业自主更新JSON数据中的data数据
	        Map<String,Object> dataMap_SelfUpdataDetail = (Map<String, Object>) querySelfUpdataDetailInfo.get("data");
	        List<Map<String, Object>> selfUpdataInfo = (List<Map<String, Object>>) dataMap_SelfUpdataDetail.get("selfUpdataInfo");
	        mv.addObject("selfUpdataInfo",selfUpdataInfo);
        }
        
		mv.addObject("pd", pd);
		mv.setViewName("selfupdata/selfupdata_den");
		return mv;
	}
	
	
	
	/**
	 * 
	 * @descript (添加企业自主更新)
	 * @author 汤彬
	 * @since 2016年9月14日下午4:22:04
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createSelfUpdata")
	@ResponseBody
	public String createSelfUpdata() throws Exception {
		PageData  pd=this.getPageData();
	
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		
		//添加UUID
		pd.put("id", this.get32UUID());
		pd.put("createUserId", user.getUSER_ID());
		
		return Verification.getResultString(selfUpdataService.createSelfUpdata(pd));
	}
	
	/**
	 * 
	 * @descript (删除企业自主更新)
	 * @author 汤彬
	 * @since 2016年9月18日下午3:37:48
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSelfUpdata")
	@ResponseBody
	public String deleteSelfUpdata() throws Exception {
		PageData  pd=this.getPageData();
		return Verification.getResultString(selfUpdataService.deleteSelfUpdata(pd));
	}
	
	/**
	 * 
	 * @descript (跳转到企业自主更新页面)
	 * @author 汤彬
	 * @since 2016年9月18日下午3:58:26
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked" })
	@RequestMapping(value="/toSelfUpdataUpdate")
	public ModelAndView toSelfUpdataUpdate()throws Exception{
		ModelAndView mv=this.getModelAndView();
		PageData  pd=this.getPageData();
		
		//设置接口的加密
        Verification.EncodeKeyNo(pd, "pripid");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用查询专利接口
        Map<String, Object> querySelfUpdataDetailInfo = Connect.sendConnectByPdToMap("Interface/selfUpdataInterface/querySelfUpdata", pd, "POST");
		
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(querySelfUpdataDetailInfo)){
	        //得到企业自主更新JSON数据中的data数据
	        Map<String,Object> dataMap_SelfUpdataDetail = (Map<String, Object>) querySelfUpdataDetailInfo.get("data");
	        List<Map<String, Object>> selfUpdataInfo = (List<Map<String, Object>>) dataMap_SelfUpdataDetail.get("selfUpdataInfo");
	        mv.addObject("selfUpdataInfo",selfUpdataInfo);
        }
        
		mv.addObject("pd", pd);
		mv.setViewName("selfupdata/selfupdata_upd");
		return mv;
	}
	
	/**
	 * 
	 * @descript (企业自主更新)
	 * @author 汤彬
	 * @since 2016年9月18日下午4:26:38
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSelfUpdata")
	@ResponseBody
	public String updateSelfUpdata() throws Exception {
		PageData  pd=this.getPageData();
		return Verification.getResultString(selfUpdataService.updateSelfUpdata(pd));
	}
	
	
	/**
	 * 
	 * @descript (企业自主更新审核)
	 * @author 汤彬
	 * @since 2016年9月19日上午11:46:38
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSelfUpdataAuditing", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateSelfUpdataAuditing() throws Exception {
		PageData  pd=this.getPageData();
		
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("status", statusOne);
		if(null != user){
			pd.put("createUserId", user.getUSER_ID());
			pd.put("uid", user.getUSER_ID());
		}
		return selfUpdataService.updateSelfUpdataAuditing(pd);
	}
	
	
	/**
	 * 
	 * @descript (企业自主更新拒审)
	 * @author 汤彬
	 * @since 2016年9月19日下午1:44:33
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSelfUpdataDenied", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateSelfUpdataDenied() throws Exception {
		PageData  pd=this.getPageData();
		
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		
		//添加回复类型
		pd.put("status", statusTwo);
		if(null != user){
			pd.put("createUserId", user.getUSER_ID());
			pd.put("uid", user.getUSER_ID());
		}
		return selfUpdataService.updateSelfUpdataAuditing(pd);
	}
	
	
	/**
	 * 
	 * @descript (文件图片处理)
	 * @author 汤彬
	 * @since 2016年9月18日上午9:45:32
	 * @param multipartRequest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/imgUpload", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String imgUpload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)throws Exception {
		String path="";
		response.setContentType("text/html;charset=UTF-8");
		// 获取多个file
		for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
			String key = (String) it.next();
			MultipartFile imgFile = multipartRequest.getFile(key);
			if (imgFile.getOriginalFilename().length() > 0) {
				String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
				String fileName = uuid + imgFile.getOriginalFilename();
				try {
					String uploadFileUrl = multipartRequest.getSession().getServletContext().getRealPath("/uploadFiles/imgPath");
					//String uploadFileUrl = com.zhirong.qrcode.util.Const.APP_PATH+ "/";
					File file = new File(uploadFileUrl);
					if (!file.exists() && !file.isDirectory()) {
						file.mkdirs();
					}
					FileUtil.saveFileFromInputStream(imgFile.getInputStream(), uploadFileUrl, fileName);
					path=Base64Image.getBASE64StringFromFile(uploadFileUrl+"/"+fileName);//把图片转成base64
				} catch (Exception e) {
					logger.error(e.toString(),e);
					return "false";
				}
			}
		}
		//Base64Image.getFileFromBase64String(path,multipartRequest.getSession().getServletContext().getRealPath("/uploadFiles/imgPath/bbb.png"));
		return path;
	}
	
	
	
}
