package com.JavaClub.controller.credit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Connect;
import com.JavaClub.util.Const;
import com.JavaClub.util.JsonUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StrUtil;
import com.JavaClub.util.Verification;

/**
 * 
 * @descript 全景视图控制器
 * @author 龚志强
 * @createTime 2016年9月23日下午4:43:44
 * @version 1.0
 */
@Controller
@RequestMapping("/overallView")
public class OverallViewController extends BaseController{
	
	final String GREEN = "#4aceb1";		//绿色节点
	final String PURPLE = "#7985f3";	//紫色节点
	final String GRAY = "#CCC";			//灰色节点
	final String BLUE = "rgb(36, 166, 218)";	//蓝色节点
	
	/**
	 * 全景视图
	 * @descript 
	 * @author 龚志强
	 * @since 2016年10月12日上午10:21:37
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEnteraddition", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryEnteraddition() throws Exception{
		ModelAndView mv = new ModelAndView("allview/enterprise_list");;
		PageData pd = new PageData();
		pd = this.getPageData();
		
    	//设置接口的加密
        Verification.EncodesearchKey(pd, "entname");
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用企业信息查询接口
        Map<String, Object> quertenteraddition = Connect.sendConnectByPdToMap(Const.ENTERPRISEURLFOUR, pd, "POST");
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
	 * @descript 全景视图
	 * @author 龚志强
	 * @since 2016年9月14日下午2:33:09
	 * @param request 请求
	 * @param response 响应
	 * @return 返回视图数据
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/overallView", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String queryAllView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageData pd = new PageData();		
		pd = this.getPageData();
		
		//设置接口加密
		Verification.EncodeKeyNo(pd, "pripid");
		//设置根节点
		Map<String,Object> nodeMap = new HashMap<String, Object>();
		List<Map<String, Object>> nodeList = new ArrayList<Map<String, Object>>();
		
		//获取全景视图数据
		Map<String,Object> resultMap = Connect.sendConnectByPdToMap("Interface/allViewInterface/queryAllView.do", pd,"post");

		//判断查询的结果集是否为空
		if(!resultMap.isEmpty()){
			//处理数据
			Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
			//获取自然人节点
			List<Map<String,Object>> invPersonList = (List<Map<String, Object>>) dataMap.get("invPersonList");
			//获取非自然人节点
			List<Map<String,Object>> invEstmentList = (List<Map<String, Object>>) dataMap.get("invEstmentList");
			//获取法定代表人节点
			List<Map<String,Object>> priPersonList = (List<Map<String, Object>>) dataMap.get("priPersonList");	
			//获取主要人员节点
			List<Map<String,Object>> priPersonList2 = (List<Map<String, Object>>) dataMap.get("priPersonList2");		
			//获取对外投资节点
			List<Map<String,Object>> invEstmentList2 = (List<Map<String, Object>>) dataMap.get("invEstmentList2");
			//获取判决文书节点
			List<Map<String,Object>> judgmentList = (List<Map<String, Object>>) dataMap.get("judgmentList");
			//获取失信被执行人节点
			List<Map<String,Object>> courtcaseList = (List<Map<String, Object>>) dataMap.get("courtcaseList");
			//获取行政许可节点
			List<Map<String,Object>> certificateList = (List<Map<String, Object>>) dataMap.get("certificateList");
			//获取其他行政许可节点
			List<Map<String,Object>> permitList = (List<Map<String, Object>>) dataMap.get("permitList");
			//获取行政处罚节点
			List<Map<String,Object>> caseList = (List<Map<String, Object>>) dataMap.get("caseList");
			//获取分支机构节点
			List<Map<String,Object>> brchinfoList = (List<Map<String, Object>>) dataMap.get("brchinfoList");
			//获取疑似关系节点
			List<Map<String,Object>> priPersonList3 = (List<Map<String, Object>>) dataMap.get("priPersonList3");
			
			//设置基础节点
			nodeList.add(setNode("主要人员", queryPriPerson(priPersonList2), "", priPersonList2.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("股东", queryInvPerson(invPersonList,invEstmentList), "", invPersonList.isEmpty() && invEstmentList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("判决文书", queryJudgmentinfo(judgmentList), "", judgmentList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("失信被执行人", queryCourtcaseinfo(courtcaseList), "", courtcaseList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("对外投资", queryInvInvestments(invEstmentList2), "", invEstmentList2.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("法定代表人", queryPriPersons(priPersonList), "", priPersonList.isEmpty() ? GRAY : "#4aceb1"));
			nodeList.add(setNode("行政许可", queryLicCertificate(certificateList,permitList), "", certificateList.isEmpty() && permitList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("行政处罚", queryOtCase(caseList), "", caseList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("分支机构", queryBrchinfo(brchinfoList), "", brchinfoList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("疑似关系", queryPriPersonRelation(priPersonList3), "", priPersonList3.isEmpty() ? GRAY : GREEN));
		}

		nodeMap.put("name", pd.getString("entname"));
		nodeMap.put("children", nodeList);
		nodeMap.put("content", "");
		nodeMap.put("fill", BLUE);
		return JsonUtils.toJson(nodeMap);
	}

	/**
	 * 
	 * @descript 设置自然人节点和非自然节点
	 * @author 龚志强
	 * @since 2016年9月14日下午2:33:40
	 * @param invPersonList 自然人集合
	 */
	public List<Map<String,Object>> queryInvPerson(List<Map<String,Object>> invPersonList, List<Map<String,Object>> invEstmentList){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;		//临时节点集合
		StringBuilder content = null;			//节点详情

		//判断自然人集合是否为空
		if(null == invPersonList){
			return null;
		}

		//循环自然人集合，添加节点和连接
		for(int i = 0; i < invPersonList.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>股东："+ invPersonList.get(i).get("INV") +"</td></tr>")
				.append("<tr><td>股东类型：自然人</td></tr>")
				.append("<tr><td>证照/证件类型："+ invPersonList.get(i).get("CERTYPE_CN") +"</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			//添加节点
			mapdata = setNode(StrUtil.delNull(invPersonList.get(i).get("INV")), null, content + "", PURPLE);
			nodeList.add(mapdata);
		}
		
		//判断非自然人集合是否为空
		if(null == invEstmentList){
			return null;
		}

		//循环非自然人集合，添加节点和连接
		for(int i = 0; i < invEstmentList.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>股东："+ StrUtil.delNull(invEstmentList.get(i).get("INV")) +"</td></tr>")
				.append("<tr><td>股东类型：" + StrUtil.delNull(invEstmentList.get(i).get("INVTYPE_CN")) + "</td></tr>")
				.append("<tr><td>证照/证件类型："+ StrUtil.delNull(invEstmentList.get(i).get("BLICTYPE_CN")) +"</td></tr>")
				.append("<tr><td>证照/证件号码：***</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			//添加节点
			mapdata = setNode(StrUtil.delNull(invEstmentList.get(i).get("INV")), null, content + "", PURPLE);
			nodeList.add(mapdata);
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 设置法定代表人节点和连接
	 * @author 龚志强
	 * @since 2016年9月14日下午2:34:31
	 * @param priPersonList 法定代表人集合
	 */
	public List<Map<String,Object>> queryPriPersons(List<Map<String,Object>> priPersonList){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断法定代表人集合是否为空
		if(null == priPersonList){
			return null;
		}

		//循环法定代表人集合，添加节点和连接
		for(int i = 0; i < priPersonList.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>法定代表人："+ priPersonList.get(i).get("NAME") +"</td></tr>")
				.append("<tr><td>职务："+ priPersonList.get(i).get("POSITION_CN") +"</td></tr>")
				.append("<tr align='center'><td colspan='2'><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			//添加节点
			mapdata = setNode(StrUtil.delNull(priPersonList.get(i).get("NAME")), null, content + "", PURPLE);
			nodeList.add(mapdata);
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 设置主要人员节点和连接
	 * @author 龚志强
	 * @since 2016年9月14日下午2:34:55
	 * @param priPersonList2 主要人员集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryPriPerson(List<Map<String,Object>> priPersonList2){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断主要人员集合是否为空
		if(null == priPersonList2){
			return null;
		}

		//循环主要人员集合，添加节点和连接
		for(int i = 0; i < priPersonList2.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>主要人员："+ priPersonList2.get(i).get("NAME") +"</td></tr>")
				.append("<tr><td>职务："+ priPersonList2.get(i).get("POSITION_CN") +"</td></tr>")
				.append("<tr align='center'><td colspan='2'><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			//添加节点
			mapdata = setNode(StrUtil.delNull(priPersonList2.get(i).get("NAME")), null, content + "", PURPLE);
			nodeList.add(mapdata);		
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 设置对外投资节点和连接
	 * @author 龚志强
	 * @since 2016年9月14日下午2:35:14
	 * @param invEstmentList2 对外投资集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryInvInvestments(List<Map<String,Object>> invEstmentList2){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断对外投资集合是否为空
		if(null == invEstmentList2){
			return null;
		}

		//循环对外投资集合，添加节点和连接
		for(int i = 0; i < invEstmentList2.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>企业名称："+ StrUtil.delNull(invEstmentList2.get(i).get("ENTNAME")) +"</td></tr>")
				.append("<tr><td>法定代表人："+ StrUtil.delNull(invEstmentList2.get(i).get("NAME")) +"</td></tr>");			
				if(null == invEstmentList2.get(i).get("UNISCID") || "".equals(invEstmentList2.get(i).get("UNISCID"))){
					content.append("<tr><td>注册号："+StrUtil.delNull(invEstmentList2.get(i).get("REGNO")) +"</td></tr>");
				}else{
					content.append("<tr><td>统一社会信用代码："+ StrUtil.delNull(invEstmentList2.get(i).get("UNISCID")) +"</td></tr>");
				}
				content.append("<tr><td>注册资本："+ StrUtil.delNull(invEstmentList2.get(i).get("REGCAP")) + "万元</td></tr>")
				.append("<tr><td>成立时间："+ StrUtil.delNull(invEstmentList2.get(i).get("ESTDATE")) +"</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			
			//添加节点
			mapdata = setNodeT(StrUtil.delNull(invEstmentList2.get(i).get("ENTNAME")), null, content + "", PURPLE, StrUtil.delNull(invEstmentList2.get(i).get("PRIPID")),
					StrUtil.delNull(invEstmentList2.get(i).get("REGNO")),StrUtil.delNull(invEstmentList2.get(i).get("BLICNO")));
			nodeList.add(mapdata);		
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 设置判决文书节点和连接
	 * @author 龚志强
	 * @since 2016年9月14日下午2:35:34
	 * @param judgmentList 判决文书集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryJudgmentinfo(List<Map<String,Object>> judgmentList){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断判决文书集合是否为空
		if(null == judgmentList){
			return null;
		}

		//循环判决文书集合，添加节点和连接
		for(int i = 0; i < judgmentList.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>判决书文号：" + judgmentList.get(i).get("CASENUM") +"</td></tr>")
				.append("<tr><td>作出判决机关：" + judgmentList.get(i).get("SUPDEPARTMENT") +"</td></tr>")
				.append("<tr><td>作出判决书日期："+ judgmentList.get(i).get("SENTENCEDATE")+"</td></tr>")
				.append("<tr><td>判决内容：" + judgmentList.get(i).get("SENTENCECONMENT") +"</td></tr>")
				.append("<tr align='center'><td colspan='2'><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			//添加节点
			mapdata = setNode(StrUtil.delNull(judgmentList.get(i).get("CASENUM")), null, content + "", PURPLE);
			nodeList.add(mapdata);
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 设置失信被执行人节点和连接
	 * @author 龚志强
	 * @since 2016年9月14日下午2:35:53
	 * @param courtcaseList 失信被执行人集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryCourtcaseinfo(List<Map<String,Object>> courtcaseList){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断失信被执行人集合是否为空
		if(null == courtcaseList){
			return null;
		}

		//循环失信被执行人集合，添加节点和连接
		for(int i = 0; i < courtcaseList.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>被执行人姓名："+ courtcaseList.get(i).get("INAME") +"</td></tr>")
				.append("<tr><td>被执行人的履行情况："+ courtcaseList.get(i).get("PERFORMANCE") +"</td></tr>")
				.append("<tr><td>执行法院："+ courtcaseList.get(i).get("COURT_NAME") +"</td></tr>")
				.append("<tr><td>发布时间：" + courtcaseList.get(i).get("PUBLISH_DATE") +"</td></tr>")
//				.append("<tr><td>失信被执行人行为具体情形："+ courtcaseList.get(i).get("DISREPUT_TYPE_NAME") +"</td></tr>")
//				.append("<tr><td>生效法律文书确定的义务："+ courtcaseList.get(i).get("DUTY") +"</td></tr>")
				.append("<tr align='center'><td colspan='2'><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");						
			//添加节点
			mapdata = setNode(StrUtil.delNull(courtcaseList.get(i).get("CASE_CODE")), null, content + "", PURPLE);
			nodeList.add(mapdata);		
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 设置行政许可节点和其他行政许可节点
	 * @author 龚志强
	 * @since 2016年9月14日下午2:36:13
	 * @param certificateList 行政许可集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryLicCertificate(List<Map<String,Object>> certificateList, List<Map<String,Object>> permitList){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断行政许可集合是否为空
		if(null == certificateList){
			return null;
		}

		//循环行政许可集合，添加节点和连接
		for(int i = 0; i < certificateList.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>许可文件名称："+ certificateList.get(i).get("LICNAME") + "</td></tr>")
				.append("<tr><td>许可证号：" + certificateList.get(i).get("LICNO") + "</td></tr>" )
				.append("<tr><td>登记机关：" + certificateList.get(i).get("LICANTH") + "</td></tr>")
				.append("<tr><td>有效期：" + certificateList.get(i).get("VALFROM") + "至" + certificateList.get(i).get("VALTO") + "</td></tr>")
				.append("<tr><td>详情：" + certificateList.get(i).get("LICITEM") + "</td></tr>")
				.append("<tr align='center'><td colspan='2'><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");	
			//添加节点
			mapdata = setNode(StrUtil.delNull(certificateList.get(i).get("LICNO")), null, content + "", PURPLE);
			nodeList.add(mapdata);		
		}
		
		//判断其他行政许可集合是否为空
		if(null == permitList){
			return null;
		}
		
		//循环其他行政许可集合，添加节点和连接
		for(int i = 0; i < permitList.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>许可文件名称："+ permitList.get(i).get("LICNAME_CN") + "</td></tr>")
				.append("<tr><td>许可证号：" + permitList.get(i).get("LICNO") + "</td></tr>")
				.append("<tr><td>登记机关：" + permitList.get(i).get("LICANTH") + "</td></tr>")
				.append("<tr><td>有效期：" + permitList.get(i).get("VALFROM") + "至" + permitList.get(i).get("VALTO") + "</td></tr>")
				.append("<tr><td>详情："+ permitList.get(i).get("LICITEM") + "</td></tr>")
				.append("<tr align='center'><td colspan='2'><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");		
			//添加节点
			mapdata = setNode(StrUtil.delNull(permitList.get(i).get("LICNO")), null, content + "", PURPLE);
			nodeList.add(mapdata);		
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 设置行政处罚节点和连接
	 * @author 龚志强
	 * @since 2016年9月14日下午2:36:52
	 * @param caseList 行政处罚集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryOtCase(List<Map<String,Object>> caseList){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断行政处罚集合是否为空
		if(null == caseList){
			return null;
		}

		//循环行政处罚集合，添加节点和连接
		for(int i = 0; i < caseList.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td colspan='2'>处罚决定书文号：" + caseList.get(i).get("PENDECNO") + "</td></tr>")
				.append("<tr><td colspan='2'>违法行为类型：" + caseList.get(i).get("ILLEGACTTYPE") + "</td></tr>")
				.append("<tr><td colspan='2'>处罚种类：" + caseList.get(i).get("PENTYPE_CN") + "</td></tr>")
//				.append("<tr><td>罚款金额：" + StrUtil.delNull(caseList.get(i).get("PENAM")) + "万元</td><td>没收金额：" + StrUtil.delNull(caseList.get(i).get("FORFAM")) + "万元</td></tr>")				
				.append("<tr><td colspan='2'>作出行政处罚决定机关名称：" + caseList.get(i).get("JUDAUTH") + "</td></tr>")
				.append("<tr><td colspan='2'>作出处罚决定书日期：" + caseList.get(i).get("PENDECISSDATE") + "</td></tr>")
				.append("<tr><td colspan='2'>公示日期：" + caseList.get(i).get("PUBLICDATE") + "</td></tr>")
				.append("<tr align='center'><td colspan='2'><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");					
			//添加节点
			mapdata = setNode(StrUtil.delNull(caseList.get(i).get("PENDECNO")), null, content + "", PURPLE);
			nodeList.add(mapdata);			
		}
		
		return nodeList;
	}
	
	/**
	 * 
	 * @descript 设置分支机构节点
	 * @author 龚志强
	 * @since 2016年9月14日下午2:58:30
	 * @param ebList 分子机构集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryBrchinfo(List<Map<String,Object>> ebList){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断分支机构集合是否为空
		if(null == ebList){
			return null;
		}

		for(int i = 0; i < ebList.size(); i++){
			content = new StringBuilder();
			String uuid = UUID.randomUUID().toString();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td colspan='2'>分支机构名称："+ StrUtil.delNull(ebList.get(i).get("BRNAME")) + "</td></tr>");
				if(null == ebList.get(i).get("UNISCID") || "".equals(ebList.get(i).get("UNISCID"))){
					content.append("<tr><td colspan='2'>注册号："+ StrUtil.delNull(ebList.get(i).get("REGNO")) +"</td></tr>");
				}else{
					content.append("<tr><td colspan='2'>统一社会信用代码："+ StrUtil.delNull(ebList.get(i).get("UNISCID")) +"</td></tr>");
				}	
				content.append("<tr><td colspan='2'>登记机关："+ StrUtil.delNull(ebList.get(i).get("REGORG_CN")) +"</td></tr>")
				.append("<tr><td colspan='2'>登记日期："+ StrUtil.delNull(ebList.get(i).get("REGIDATE")) +"</td></tr>")
				.append("<tr align='center'><td colspan='2'><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			//添加节点
			mapdata = setNode(StrUtil.delNull(ebList.get(i).get("BRNAME")), null, content + "", PURPLE);
			nodeList.add(mapdata);
		}
		
		return nodeList;
	}
	
	/**
	 * 
	 * @descript 设置疑似关系节点
	 * @author 龚志强
	 * @since 2016年9月14日下午2:34:55
	 * @param priPersonList2 主要人员集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryPriPersonRelation(List<Map<String,Object>> priPersonList3){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断疑似关系集合是否为空
		if(null == priPersonList3){
			return null;
		}

		//循环疑似关系集合，添加节点和连接
		for(int i = 0; i < priPersonList3.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>名称："+ priPersonList3.get(i).get("NAME") +"</td>")
				.append("<td>职务："+ priPersonList3.get(i).get("POSITION_CN") +"</td></tr>")
				.append("<tr align='center'><td colspan='2'><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			//添加节点
			mapdata = setNode(StrUtil.delNull(priPersonList3.get(i).get("NAME")), null, content + "", PURPLE);
			nodeList.add(mapdata);		
		}
		
		return nodeList;
	}

	/**
	 * 设置节点
	 * @descript 这种d3的节点和连接线
	 * @author 龚志强
	 * @since 2016年11月1日下午3:01:33
	 * @param name 名称
	 * @param list 子节点
	 * @param fill 节点颜色
	 * @param content 详情内容
	 * @return
	 */
	public Map<String,Object> setNode(String name, List<Map<String, Object>> list, String content, String fill){
		Map<String,Object> nodeMap = new HashMap<String,Object>();

		nodeMap.put("name", name);
		nodeMap.put("children", list);
		nodeMap.put("content", content);
		nodeMap.put("fill", fill);

		return nodeMap;
	}
	
	/**
	 * 设置节点
	 * @descript 这种d3的节点和连接线
	 * @author 龚志强
	 * @since 2016年11月1日下午3:01:33
	 * @param name 名称
	 * @param list 子节点
	 * @param fill 节点颜色
	 * @param content 详情内容
	 * @return
	 */
	public Map<String,Object> setNodeT(String name, List<Map<String, Object>> list, String content, String fill,
			String pripid, String regno, String provinceCode){
		Map<String,Object> nodeMap = new HashMap<String,Object>();

		nodeMap.put("name", name);
		nodeMap.put("children", list);
		nodeMap.put("content", content);
		nodeMap.put("fill", fill);
		nodeMap.put("pripid", pripid);
		nodeMap.put("regno", regno);
		nodeMap.put("provinceCode", provinceCode);

		return nodeMap;
	}
}