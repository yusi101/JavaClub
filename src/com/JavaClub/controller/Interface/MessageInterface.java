package com.JavaClub.controller.Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.Interface.MessageService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript (用户消息)
 * @author 李海涛
 * @createTime 2016年9月27日下午3:20:58
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/Interface/messageInterface")
public class MessageInterface extends BaseController{

    @Autowired
    private MessageService messageService;

    /**
     * 
     * @descript (根据用户ID查询个人未读消息记录)
     * @author 李海涛
     * @since 2016年9月27日下午3:10:35
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/querymymessageCount", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String querymymessageCount() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        //初始化数据
        try {			
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "memberId");
            int messageCount = messageService.queryMessageCount(pd);			
            resultMap.put("messageCount", messageCount+"");					
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }


    /**
     * 
     * @descript (根据用户ID查询个人消息)
     * @author 李海涛
     * @since 2016年9月27日下午3:11:08
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryMessageInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String queryMessageInfo() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);

        //初始化数据
        try {			
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "memberId");

            List<Map<String, Object>> messageInfo=new ArrayList<>();

            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                messageInfo= messageService.queryMessageInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                messageInfo= messageService.queryMessageInfo(pd);
            }

            MapReplaceUtils.handleLsitMapData(messageInfo);
            resultMap.put("messageInfo",messageInfo);

        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }



    /**
     * 
     * @descript (根据ID查询个人消息和修改状态)
     * @author tb
     * @since 2016年8月12日上午9:57:37
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryMessageById", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String queryMessageById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);

        //初始化数据
        try {			
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");

            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            pd.put("ID",  pd.getString("claimId"));

            List<Map<String, Object>> messageIdInfo = messageService.queryMessageById(pd);

            int messageId = messageService.updateMessageById(pd);
            MapReplaceUtils.handleLsitMapData(messageIdInfo);
            resultMap.put("messageId", messageId);
            resultMap.put("messageIdInfo", messageIdInfo);					

        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }



    /**
     * 
     * @descript (根据ID删除个人消息)
     * @author tb
     * @since 2016年8月12日上午9:57:37
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deteleMessageById", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String deteleMessageById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);

        //初始化数据
        try {			
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }

            pd.put("ID",  pd.getString("claimId"));

            int delete_Result = messageService.deleteMessageById(pd);
            resultMap.put("affectedRow", delete_Result+"");				

        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }

        return MyGson.toJson(map);
    }

}
