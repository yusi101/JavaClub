/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年9月26日上午11:35:42
 * @version 1.0
 */
package com.JavaClub.controller.Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.Interface.ActiveService;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.Verification;

/**
 * @descript (自主业务)
 * @author 李海涛
 * @createTime 2016年9月26日上午11:35:42
 * @version 1.0
 */
@Controller
@RequestMapping(value="/Interface/activeInterface")
public class ActiveInterface extends BaseController{

    @Autowired
    private ActiveService activeService;
    
    /**
     * 
     * @descript (查询评论列表)
     * @author 李海涛
     * @since 2016年9月26日下午2:37:09
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryCommentInfo", produces = "text/html;charset=UTF-8")
    public String queryCommentInfo() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
            List<Map<String, Object>> commentInfo=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                commentInfo= activeService.queryCommentInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                commentInfo= activeService.queryCommentInfo(pd);
            }
            for (int i = 0; i < commentInfo.size(); i++) {
                Map<String, Object> commentInfo_map=commentInfo.get(i);
                pd.put("pid", commentInfo_map.get("COMMENTID").toString());
                List<Map<String, Object>> commentSonInfo = activeService.queryCommentInfo(pd);
                MapReplaceUtils.handleLsitMapData(commentSonInfo);
                commentInfo_map.put("commentSonInfo", commentSonInfo);
                commentInfo_map.put("commentSonInfoCount", commentSonInfo.size());
                commentInfo.set(i, commentInfo_map);
            }
            MapReplaceUtils.handleLsitMapData(commentInfo);
            resultMap.put("commentInfo",commentInfo);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    /**
     * 
     * @descript (评论企业)
     * @author 李海涛
     * @since 2016年9月26日下午2:37:09
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/createCommentInfo", produces = "text/html;charset=UTF-8")
    public String createCommentInfo() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@memberId@remark");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
            pd.put("id", this.get32UUID());
            pd.put("pid",pd.get("pid")==null ? "0" :pd.get("pid"));
            int insert_Result=activeService.insertCommentInfo(pd);
            //返回受影响的行数
            resultMap.put("affectedRow", insert_Result+"");
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    /**
     * 对点评进行点赞
     * 李海涛
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createPraiseSuccessqty", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String createPraiseSuccessqty() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@memberId@opeType");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            pd.put("id", get32UUID());
            Verification.DecodeKeyNo(pd, "commentId");
            String affectedRow="";
            if(pd.getString("opeType").equals("0")){
                affectedRow = activeService.savePraiseSuccessqty(pd)+"";
            }else if(pd.getString("opeType").equals("1")){
                affectedRow = activeService.cancelPraiseSuccessqty(pd)+"";
            }else{
                return  MyGson.toJson(Verification.LackParameter(""));
            }
            resultMap.put("affectedRow", affectedRow+"");
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        
        return MyGson.toJson(map);
    }
    
     /**
     * 对点评进行吐槽
     * 李海涛
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createPraiseFailedqty", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String createPraiseFailedqty() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@memberId@opeType");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            pd.put("id", get32UUID());
            Verification.DecodeKeyNo(pd, "commentId");
            String affectedRow = "";
            if(pd.getString("opeType").equals("0")){
                affectedRow = activeService.savePraiseFailedqty(pd)+"";
            }else if(pd.getString("opeType").equals("1")){
                affectedRow = activeService.cancelPraiseFailedqty(pd)+"";
            }else{
                return  MyGson.toJson(Verification.LackParameter(""));
            }
            resultMap.put("affectedRow", affectedRow+"");
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        
        
        return MyGson.toJson(map);
    }
    
    
    
    
    /**
     * 
     * @descript (关注企业)
     * @author 李海涛
     * @since 2016年9月26日下午2:37:09
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/createFollowInfo", produces = "text/html;charset=UTF-8")
    public String createFollowInfo() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@memberId");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
            pd.put("id", this.get32UUID());
            int insert_Result=activeService.insertFollowInfo(pd);
            //返回受影响的行数
            resultMap.put("affectedRow", insert_Result+"");
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    /**
     * 
     * @descript (取消关注企业)
     * @author 李海涛
     * @since 2016年9月26日下午2:37:09
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/cancelFollowInfo", produces = "text/html;charset=UTF-8")
    public String cancelFollowInfo() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@memberId");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
            pd.put("id", this.get32UUID());
            int insert_Result=activeService.cancelFollowInfo(pd);
            //返回受影响的行数
            resultMap.put("affectedRow", insert_Result+"");
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    /**
     * 
     * @descript (查询关注列表)
     * @author 李海涛
     * @since 2016年9月26日下午2:37:09
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryFollowInfo", produces = "text/html;charset=UTF-8")
    public String queryFollowInfo() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
            List<Map<String, Object>> followInfo=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                followInfo= activeService.queryFollowInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                followInfo= activeService.queryFollowInfo(pd);
            }
            MapReplaceUtils.handleLsitMapData(followInfo);
            resultMap.put("followInfo",followInfo);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    /**
     * 
     * @descript (查询投诉列表)
     * @author 李海涛
     * @since 2016年9月26日下午2:37:09
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryComplaintInfo", produces = "text/html;charset=UTF-8")
    public String queryComplaintInfo() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
            List<Map<String, Object>> complaintInfo=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                complaintInfo= activeService.queryComplaintInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                complaintInfo= activeService.queryComplaintInfo(pd);
            }
            if(StringUtil.isNotEmpty(pd.getString("id"))){
                for (int i = 0; i < complaintInfo.size(); i++) {
                    Map<String, Object> complaintInfo_map=complaintInfo.get(i);
                    pd.put("relationId", complaintInfo_map.get("ID").toString());
                    List<Map<String, Object>> attachmentInfo = activeService.queryAttachmentInfo(pd);
                    MapReplaceUtils.handleLsitMapData(attachmentInfo);
                    complaintInfo_map.put("attachmentInfo", attachmentInfo);
                    complaintInfo_map.put("attachmentInfoCount", attachmentInfo.size());
                    complaintInfo.set(i, complaintInfo_map);
                } 
            }
            
            MapReplaceUtils.handleLsitMapData(complaintInfo);
            resultMap.put("complaintInfo",complaintInfo);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    
    /**
     * 
     * @descript (用户对企业进行投诉)
     * @author 李海涛
     * @since 2016年9月26日下午2:37:09
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/createComplainInfo", produces = "text/html;charset=UTF-8")
    public String createComplainInfo() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@memberId@typeId@title@remark");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
            pd.put("id", this.get32UUID());
            int insert_Result=activeService.insertComplainInfo(pd);
            //返回受影响的行数
            resultMap.put("affectedRow", insert_Result+"");
            resultMap.put("relationId", pd.getString("id"));
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    /**
     * 
     * @descript (用户对企业进行取消投诉)
     * @author 李海涛
     * @since 2016年9月26日下午2:37:09
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/cancelComplainInfo", produces = "text/html;charset=UTF-8")
    public String cancelComplainInfo() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "id");
            int insert_Result=activeService.cancelComplainInfo(pd);
            activeService.deleteAttachment(pd);
            //返回受影响的行数
            resultMap.put("affectedRow", insert_Result+"");
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    
   
    /**
     * 
     * @descript (上传附件)
     * @author 李海涛
     * @since 2016年9月26日下午5:12:47
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createAttachment", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String createAttachment() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@memberId@type@attchmentDesc@attchmentSteam");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "relationId");
            int insert_Result=0;
            String attachmentBase[]=pd.getString("attchmentSteam").split("@");
            for (int i = 0; i < attachmentBase.length; i++) {
                pd.put("attachmentId", get32UUID());
                String filename=get32UUID()+String.valueOf(System.currentTimeMillis())+".jpg";
                pd.put("attachmentName",filename);
                pd.put("attachmentPath",attachmentBase[i]);
                pd.put("description", pd.get("attchmentDesc").toString().split("@")[i]);
                insert_Result+= activeService.insertAttachment(pd);
            }
            resultMap.put("affectedRow", insert_Result+"");
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    /**
     * 
     * @descript (删除附件)
     * @author 李海涛
     * @since 2016年9月26日下午2:37:09
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAttachment", produces = "text/html;charset=UTF-8")
    public String deleteAttachment() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "relationId");
            int insert_Result=activeService.deleteAttachment(pd);
            //返回受影响的行数
            resultMap.put("affectedRow", insert_Result+"");
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    
    /**
     * 
     * @descript (查询企业认领列表)
     * @author 李海涛
     * @since 2016年9月26日下午2:37:09
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/queryClaimInfo", produces = "text/html;charset=UTF-8")
    public String queryClaimInfo() throws Exception{
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "pripid");
            List<Map<String, Object>> claimInfo=new ArrayList<>();
            
            //如果pageSize存在，并且大于1，则该接口分页，否则不分页
            if(StringUtil.isInt(pd.getString("pageSize"))){
                Page page = new Page();
                Verification.SetPageList(pd, page);//设置page的分页
                claimInfo= activeService.queryClaimInfo(page);
                Verification.getPageMessage(resultMap, page);
            }else{
                claimInfo= activeService.queryClaimInfo(pd);
            }
            if(StringUtil.isNotEmpty(pd.getString("claimId"))){
                for (int i = 0; i < claimInfo.size(); i++) {
                    Map<String, Object> claimInfo_map=claimInfo.get(i);
                    pd.put("relationId", claimInfo_map.get("CLAIMID").toString());
                    List<Map<String, Object>> attachmentInfo = activeService.queryAttachmentInfo(pd);
                    MapReplaceUtils.handleLsitMapData(attachmentInfo);
                    claimInfo_map.put("attachmentInfo", attachmentInfo);
                    claimInfo_map.put("attachmentInfoCount", attachmentInfo.size());
                    claimInfo.set(i, claimInfo_map);
                } 
            }
            
            MapReplaceUtils.handleLsitMapData(claimInfo);
            resultMap.put("claimInfo",claimInfo);
        }catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
    /**
     * 认领企业
     * 胡华锋
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createClaimInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String createClaimInfo() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@KeyNo@deviceId@openType");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            //openType为0为认领 1为修改认领
            if(pd.getString("openType").equals("0")){
                 VerificationParameter=Verification.VerificationParameter(pd,"token@KeyNo@deviceId@memberId@openType");
                 if(!"".equals(VerificationParameter)){
                     return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
                 }
                pd.put("id", get32UUID());    //认领ID
                Verification.DecodeKeyNo(pd, "pripid");   //企业主键
                int insert_Result = activeService.insertClaimInfo(pd);
                //返回受影响的行数
                resultMap.put("affectedRow", insert_Result+"");
                //返回关联ID  用于上传附件
                resultMap.put("relationId",pd.getString("id"));
            }else if(pd.getString("openType").equals("1")){
                 VerificationParameter=Verification.VerificationParameter(pd,"token@KeyNo@deviceId@memberId@pripid@openType");
                 Verification.DecodeKeyNo(pd, "id");    //认领ID
                 int update_Result = activeService.updateClaimInfo(pd);
                 resultMap.put("affectedRow", update_Result+"");
            }else{
                return  MyGson.toJson(Verification.ResultMessage(0));   
            }
            
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        
        return MyGson.toJson(map);
    }
    
    /**
     * 取消认领企业
     * 胡华锋
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cancelClaimInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String cancelClaimInfo() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo@pripid@memberId");
            if(!"".equals(VerificationParameter)){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            Verification.DecodeKeyNo(pd, "claimId");
            int delete_Result = activeService.cancelClaimInfo(pd);
            resultMap.put("affectedRow", delete_Result+"");
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        
        return MyGson.toJson(map);
    }
   
    /**
     * 
     * @descript (保存用户发聩)
     * @author 李海涛
     * @since 2016年11月2日下午4:28:54
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveOpinion", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveOpinion() throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        try {
            String VerificationParameter=Verification.VerificationParameter(pd,"token@deviceId@KeyNo");
            if(!VerificationParameter.equals("")){
                return  MyGson.toJson(Verification.LackParameter(VerificationParameter));
            }
            if(!Verification.VerificationMd5(pd)){
                return  MyGson.toJson(Verification.Md5Match());
            }
            pd.put("id", get32UUID());
            pd.put("memberId",  pd.getString("KeyNo"));
            int save_Result = activeService.saveOpinion(pd);
            resultMap.put("affectedRow", save_Result+"");
        } catch (Exception e) {
            return  MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson(map);
    }
}
