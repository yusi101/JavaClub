package com.JavaClub.controller.Interface;

import java.io.UnsupportedEncodingException;
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
import com.JavaClub.service.Interface.BaseinfoService;
import com.JavaClub.util.Const;
import com.JavaClub.util.EnumUtil;
import com.JavaClub.util.Logger;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.MyGson;
import com.JavaClub.util.PageData;
import com.JavaClub.util.Verification;
import com.util.ConnectUrl;

@Controller
@RequestMapping(value = "/Interface/baseinfoInterface")
public class BaseinfoInterface extends BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
    private static final String RESULT = "Result";
    private static final String C_AREA_LEVEL = "c_area_level";
    private static final String C_CODE = "c_code";
    private static final String C_NAME = "c_name";
    @Autowired
    BaseinfoService baseinfoService;

    /**
     * 
     * @descript (企业搜索接口)
     * @author 李海涛
     * @since 2016年9月20日下午1:43:50
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/queryEnterpriseInfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String querymodelbaseinfo() throws Exception {
        Page page = new Page();
        PageData pd = this.getPageData();
        Map<String,Object> map=Verification.Success();
        Map<String,Object> resultMap=new HashMap<>();
        map.put("data",resultMap);
        
        String searchType = pd.getString("searchType");// 搜索的类型，0、企业、1、法人、2、商标    3、失信
        try {
            Verification.SetPageList(pd, page);// 设置page的分页
            if (searchType == null || EnumUtil.SEARCH_BY_ENTNAME.getName().equals(searchType)) {
                pd.put("LogID", get32UUID());
                pd.put("remark", EnumUtil.SEARCH_BY_ENTNAME.getValue());

                //联动查询不记录日志
                if(!"true".equals(pd.getString("linkage"))){
                    pd.put("logType", EnumUtil.SEARCH_BY_ENTNAME.getName());
                    baseinfoService.saveKeywordLog(pd);
                }
                Map<String,Object> result_Map=ConnectUrl.sendConnectByPdToMap(Const.ENTERPRISEURL, pd, "POST");
                if(null != result_Map && !result_Map.isEmpty()){
                	 Map<String,Object> data_map=(Map<String, Object>)result_Map.get("data");
                     List<Map<String, Object>> result_list=(List<Map<String, Object>>)data_map.get(RESULT);
                     MapReplaceUtils.handleLsitMapData(result_list);
                     data_map.put(RESULT, result_list);
                     result_Map.put("data",data_map);
                     return  MyGson.toJson2(result_Map);
                }else{
                	List<Map<String, Object>> listresult = baseinfoService.queryEnterpriseEntNamelistPage(page);
                    MapReplaceUtils.handleLsitMapData(listresult);
                    resultMap.put(RESULT, listresult);
                    Verification.getPageMessage(resultMap, page);
                }
            } else if (EnumUtil.SEARCH_BY_LEGAL.getName().equals(searchType)) {
                pd.put("LogID", get32UUID());
                pd.put("remark", EnumUtil.SEARCH_BY_LEGAL.getValue());
                //联动查询不记录日志
                if(!"true".equals(pd.getString("linkage"))){
                    pd.put("logType", EnumUtil.SEARCH_BY_LEGAL.getName());
                    baseinfoService.saveKeywordLog(pd);
                }
                Map<String,Object> result_Map=ConnectUrl.sendConnectByPdToMap(Const.ENTERPRISEURL, pd, "POST");
                Map<String,Object> data_map=(Map<String, Object>)result_Map.get("data");
                List<Map<String, Object>> result_list=(List<Map<String, Object>>)data_map.get(RESULT);
                MapReplaceUtils.handleLsitMapData(result_list);
                data_map.put(RESULT, result_list);
                result_Map.put("data",data_map);
                return  MyGson.toJson2(result_Map);
            } else if (EnumUtil.SEARCH_BY_OPSCOPE.getName().equals(searchType)) {
                pd.put("LogID", get32UUID());
                pd.put("remark", EnumUtil.SEARCH_BY_OPSCOPE.getValue());

                //联动查询不记录日志
                if(!"true".equals(pd.getString("linkage"))){
                    pd.put("logType", EnumUtil.SEARCH_BY_OPSCOPE.getName());
                    baseinfoService.saveKeywordLog(pd);
                }
                Map<String,Object> result_Map=ConnectUrl.sendConnectByPdToMap(Const.ENTERPRISEURL, pd, "POST");
                Map<String,Object> data_map=(Map<String, Object>)result_Map.get("data");
                List<Map<String, Object>> result_list=(List<Map<String, Object>>)data_map.get(RESULT);
                MapReplaceUtils.handleLsitMapData(result_list);
                data_map.put(RESULT, result_list);
                result_Map.put("data",data_map);
                return  MyGson.toJson2(result_Map);
            } else if (EnumUtil.SEARCH_BY_COURTCASE.getName().equals(searchType)) {
                pd.put("LogID", get32UUID());
                pd.put("remark", EnumUtil.SEARCH_BY_COURTCASE.getValue());

                //联动查询不记录日志
                if(!"true".equals(pd.getString("linkage"))){
                    pd.put("logType", EnumUtil.SEARCH_BY_COURTCASE.getName());
                    baseinfoService.saveKeywordLog(pd);
                }
                List<Map<String, Object>> listresult = baseinfoService.getEntByCourtcaseinfolistPage(page);
                MapReplaceUtils.handleLsitMapData(listresult);
                resultMap.put(RESULT, listresult);
                Verification.getPageMessage(resultMap, page);
            }else if (EnumUtil.SEARCH_BY_NEW.getName().equals(searchType)) {
//                pd.put("LogID", get32UUID());
//                pd.put("remark", EnumUtil.SEARCH_BY_COURTCASE.getValue());

//                //联动查询不记录日志
//                if(!"true".equals(pd.getString("linkage"))){
//                    pd.put("logType", EnumUtil.SEARCH_BY_COURTCASE.getName());
//                    baseinfoService.saveKeywordLog(pd);
//                }
                List<Map<String, Object>> listresult = baseinfoService.queryEnterpriseByNewlistPage(page);
                MapReplaceUtils.handleLsitMapData(listresult);
                resultMap.put(RESULT, listresult);
                Verification.getPageMessage(resultMap, page);
            }
        } catch (Exception e) {
        	logger.error(e.toString(),e);
            return MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson2(map);
    }


    
    @ResponseBody
    @RequestMapping(value = "/queryArea", produces = "text/html;charset=UTF-8")
    public String queryArea(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        try {
            Map<String, Object> map = Verification.Success();
            List<Map<String, Object>> list = new ArrayList<>();
            List<Map<String, Object>> areaInfo = baseinfoService.getArea();
            Map<String, Object> mapdata = new HashMap<>();
            for (int i = 0; i < areaInfo.size(); i++) {
                Map<String, Object> mapAll = areaInfo.get(i);
                if (null != mapAll.get(C_AREA_LEVEL) && "1".equals(mapAll.get(C_AREA_LEVEL))) {
                    Map<String, Object> province = new HashMap<>();
                    Map<String, Object> mapProvince = areaInfo.get(i);
                    Map<String, Object> proCity = new HashMap<>();
                    proCity.put(C_CODE, "");
                    proCity.put(C_NAME, "全省");
                    List<Map<String, Object>> citylist = new ArrayList<>();
                    citylist.add(proCity);
                    for (int j = 0; j < areaInfo.size(); j++) {
                        Map<String, Object> mapCity = areaInfo.get(j);
                        if (null != mapCity.get(C_CODE) && "2".equals(mapCity.get(C_AREA_LEVEL)) && (mapCity
                                .get(C_CODE).toString().indexOf(mapProvince.get(C_CODE).toString()) == 0)) {
                            citylist.add(mapCity);
                        }

                    }
                    province.put(C_CODE, mapProvince.get(C_CODE));
                    province.put(C_NAME, mapProvince.get(C_NAME));
                    province.put(C_AREA_LEVEL, mapProvince.get(C_AREA_LEVEL));
                    province.put("citycode", citylist);
                    list.add(province);
                }

            }

            mapdata.put("city", list);
            map.put("data", mapdata);
            return MyGson.toJson(map);
        } catch (Exception e) {
        	logger.error(e.toString(),e);
            return MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
    }

    /* 选择地方 */
    @ResponseBody
    @RequestMapping(value = "/querycitys", produces = "text/html;charset=UTF-8")
    public String querycity(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        try {
            Map<String, Object> map = Verification.Success();
            Map<String, Object> cityjx = baseinfoService.getcityjx();
            Map<String, Object> cityhn = baseinfoService.getcityhn();
            List<Map<String, Object>> citylistjx = baseinfoService.getcitycodejx();
            List<Map<String, Object>> citylisthn = baseinfoService.getcitycodehn();
            Map<String, Object> qsjx = new HashMap<>();
            qsjx.put(C_CODE, "");
            qsjx.put(C_NAME, "全省");
            Map<String, Object> qshn = new HashMap<>();
            qshn.put(C_CODE, "");
            qshn.put(C_NAME, "全省");
            citylistjx.add(0, qsjx);
            citylisthn.add(0, qshn);
            Map<String, Object> mapdata = new HashMap<>();
            List<Map<String, Object>> citylist = new ArrayList<>();
            cityjx.put("citycode", citylistjx);
            cityhn.put("citycode", citylisthn);

            citylist.add(cityjx);
            citylist.add(cityhn);
            mapdata.put("city", citylist);
            map.put("data", mapdata);
            return MyGson.toJson(map);
        } catch (Exception e) {
        	logger.error(e.toString(),e);
            return MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }

    }

    /* 选择行业 */
    @ResponseBody
    @RequestMapping(value = "/queryindustry", produces = "text/html;charset=UTF-8")
    public String queryindustry(HttpServletRequest request, HttpServletResponse response)
            throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        try {
            Map<String, Object> map = Verification.Success();
            List<Map<String, Object>> list = baseinfoService.getindustry();
            Map<String, Object> m = new HashMap<>();
            m.put("EC_VALUE", "");
            m.put("EC_NAME", "所有行业");
            list.add(0, m);
            Map<String, Object> mapdata = new HashMap<>();
            mapdata.put("industry", list);
            map.put("data", mapdata);
            return MyGson.toJson(map);
        } catch (Exception e) {
        	logger.error(e.toString(),e);
            return MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }

    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/queryAllCountForEnterprise", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String queryAllCountForEnterprise(Page page) throws Exception {
        PageData pd = this.getPageData();
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
            List enterpriseInfoList = baseinfoService.queryAllCountForEnterprise(pd);
            List<Map<String, Object>> baseinfo = (List<Map<String, Object>>) enterpriseInfoList.get(0);
            if(baseinfo.isEmpty()){
            	page.setPd(pd);
            	baseinfo = baseinfoService.getEnterprise(page);
            }
            List<Map<String, Object>> allCount = (List<Map<String, Object>>) enterpriseInfoList.get(1);
            MapReplaceUtils.handleLsitMapData(baseinfo);
            resultMap.put("baseInfo", baseinfo);
            MapReplaceUtils.handleLsitMapData(allCount);
            resultMap.put("allCount", allCount);
        } catch (Exception e) {
        	logger.error(e.toString(),e);
            return MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson2(map);
    }

    @RequestMapping(value = "/saveOperationLog", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveOperationLog() throws Exception {
        PageData pd = this.getPageData();
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
            /* 添加纪录到数据库中 */
            Verification.DecodeKeyNo(pd, "pripid");
            pd.put("logid", get32UUID());
            if (null == pd.getString("modulename") || "".equals(pd.getString("modulename"))) {
                pd.put("modulename", "九宫格首页");
            }
            int insert_Result = baseinfoService.saveOperationLog(pd);
            resultMap.put("affectedRow", Integer.toString(insert_Result));
        } catch (Exception e) {
        	logger.error(e.toString(),e);
            return MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson2(map);
    }

    /**
     * 
     * @descript (记录搜索关键词)
     * @author 李海涛
     * @since 2016年11月2日下午6:21:45
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveKeywordLog", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String saveKeywordLog() throws Exception {
        PageData pd = this.getPageData();
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
            /* 添加纪录到数据库中 */
            Verification.DecodeKeyNo(pd, "memberId");
            pd.put("LogID", get32UUID());
            int insert_Result=baseinfoService.saveKeywordLog(pd);
            resultMap.put("affectedRow", Integer.toString(insert_Result));
        } catch (Exception e) {
        	logger.error(e.toString(),e);
            return MyGson.toJson(Verification.ExceptionError(e.getMessage()));
        }
        return MyGson.toJson2(map);
    }
}
