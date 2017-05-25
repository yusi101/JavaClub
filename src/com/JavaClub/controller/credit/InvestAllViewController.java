package com.JavaClub.controller.credit;

import java.io.IOException;
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
import com.google.protobuf.ServiceException;

/**
 * 
 * @descript 投资全景
 * @author 龚志强
 * @createTime 2016年9月23日下午4:44:08
 * @version 1.0
 */
@Controller
@RequestMapping(value="/investAllViewController")
public class InvestAllViewController extends BaseController{
	
	final String GREEN = "#4aceb1";				//绿色节点
	final String PURPLE = "#7985f3";			//紫色节点
	final String GRAY = "#CCC";					//灰色节点
	final String BLUE = "rgb(36, 166, 218)";	//蓝色节点
	
	/**
	 * 投资全景
	 * @descript 
	 * @author 龚志强
	 * @since 2016年10月12日上午10:21:37
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryEnteraddition", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryEnteraddition() throws Exception{
		ModelAndView mv = new ModelAndView("investallview/enterprise_list");;
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
	 * 投资全景
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/investAllView", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String investAllView(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
		PageData pd = new PageData();		
		pd = this.getPageData();
		//设置接口加密
		Verification.EncodeKeyNo(pd, "pripid");
		//设置根节点
		Map<String,Object> nodeMap = new HashMap<String, Object>();
		List<Map<String, Object>> nodeList = new ArrayList<Map<String, Object>>();

		//获取数据
		Map<String,Object> resultMap = Connect.sendConnectByPdToMap("/Interface/investAllViewInterface/queryInvestAllView.do", pd,"post");

		//判断查询的结果集是否为空
		if(!resultMap.isEmpty()){
			//处理数据
			Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
			//设置自然人节点
			List<Map<String, Object>> npList = (List<Map<String, Object>>) dataMap.get("npList");
			//设置非自然人节点
			List<Map<String, Object>> nnpList = (List<Map<String, Object>>) dataMap.get("nnpList");
			//设置主要人员节点
			List<Map<String, Object>> kyList = (List<Map<String, Object>>) dataMap.get("kyList");
			//设置法定代表人节点
			List<Map<String, Object>> lrList = (List<Map<String, Object>>) dataMap.get("lrList");	
			//设置分支机构节点
			List<Map<String, Object>> ebList = (List<Map<String, Object>>) dataMap.get("ebList");
			//设置对外投资
			List<Map<String, Object>> invList = (List<Map<String, Object>>) dataMap.get("invList");
			//自然人对外投资
			Map<String, Object> npMap2 = (Map<String, Object>) dataMap.get("npMap2");
			//主要人员对外投资
			Map<String, Object> kyMap2 = (Map<String, Object>) dataMap.get("kyMap2");
			//法定代表人对外投资
			Map<String, Object> lrMap2 = (Map<String, Object>) dataMap.get("lrMap2");
			//非自然人对外投资
			Map<String, Object> nnpMap2 = (Map<String, Object>) dataMap.get("nnpMap2");
			//分支机构对外投资
			Map<String, Object> ebMap2 = (Map<String, Object>) dataMap.get("ebMap2");
			
			//设置基础节点
			nodeList.add(setNode("主要人员", queryPriPerson(kyList, kyMap2), "", kyList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("股东", queryInvPerson(npList, nnpList, npMap2, nnpMap2), "", npList.isEmpty() && nnpList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("法定代表人", queryPriPersons(lrList, lrMap2), "", lrList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("分支机构", queryBrchinfo(ebList, ebMap2), "", ebList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("对外投资", queryInvInvestments(invList), "", invList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("历史股东", null, "", GRAY));
			nodeList.add(setNode("历史法人", null, "", GRAY));
		}

		nodeMap.put("name", pd.getString("entname"));
		nodeMap.put("children", nodeList);
		nodeMap.put("content", "");
		nodeMap.put("fill", BLUE);
		
		return JsonUtils.toJson(nodeMap);
	}
	
	/**
	 * 
	 * @descript 设置自然人和非自然人节点
	 * @author 龚志强
	 * @since 2016年9月14日下午2:57:17
	 * @param pdList 自然人集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryInvPerson(List<Map<String,Object>> npList, List<Map<String,Object>> nnpList,
			Map<String,Object> npMap2, Map<String,Object> nnpMap2){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		List<Map<String,Object>> twoNodeList = null; //返回对外投资的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断自然人集合是否为空
		if(null != npList){
			for(int i = 0; i < npList.size(); i++){
				twoNodeList = new ArrayList<Map<String,Object>>();
				content = new StringBuilder();
				String uuid = UUID.randomUUID().toString();
				content.append("<table id='" + uuid.replace("-", "") + "'>")
					.append("<tr><td>股东："+ StrUtil.delNull(npList.get(i).get("INV")) +"</td></tr>")
					.append("<tr><td>股东类型：自然人</td></tr>")
					.append("<tr><td>证照/证件类型："+ StrUtil.delNull(npList.get(i).get("CERTYPE_CN")) +"</td></tr>" )
					.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
				
				//获取自然人对外投资节点
				twoNodeList = queryNameEnt(npMap2, npList.get(i).get("CERNO") + "");
				//添加节点
				mapdata = setNode(StrUtil.delNull(npList.get(i).get("INV")), twoNodeList, content + "", PURPLE);
				nodeList.add(mapdata);					
			}
		}

		//判断非自然人集合是否为空
		if(null != nnpList){
			for(int i = 0; i < nnpList.size(); i++){
				twoNodeList = new ArrayList<Map<String,Object>>();
				content = new StringBuilder();
				String uuid = UUID.randomUUID().toString();
				content.append("<table id='" + uuid.replace("-", "") + "'>")
					.append("<tr><td>股东："+ StrUtil.delNull(nnpList.get(i).get("INV")) +"</td></tr>")
					.append("<tr><td>股东类型：" + StrUtil.delNull(nnpList.get(i).get("INVTYPE_CN")) + "</td></tr>")
					.append("<tr><td>证照/证件类型："+ StrUtil.delNull(nnpList.get(i).get("BLICTYPE_CN")) +"</td></tr>")
					.append("<tr><td>证照/证件号码：***</td></tr>")
					.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>"); 
				
				//获取非自然人对外投资节点
				twoNodeList = queryEntEnt(nnpMap2, nnpList.get(i).get("BLICNO") + "");
				//添加节点
				mapdata = setNode(StrUtil.delNull(nnpList.get(i).get("INV")), twoNodeList, content + "", PURPLE);
				nodeList.add(mapdata);
			}
		}

		return nodeList;
	}

	/**
	 * 
	 * @descript 设置主要人员节点
	 * @author 龚志强
	 * @since 2016年9月14日下午2:57:50
	 * @param kyList 主要人员集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryPriPerson(List<Map<String,Object>> kyList, Map<String,Object> kyMap2){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		List<Map<String,Object>> twoNodeList = null; //返回对外投资的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断主要人员集合是否为空
		if(null == kyList){
			return null;
		}

		for(int i = 0; i < kyList.size(); i++){
			twoNodeList = new ArrayList<Map<String,Object>>();
			content = new StringBuilder();
			String uuid = UUID.randomUUID().toString();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>主要人员："+ StrUtil.delNull(kyList.get(i).get("NAME")) +"</td></tr>")
				.append("<tr><td>职务："+ StrUtil.delNull(kyList.get(i).get("POSITION_CN")) +"</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			
			//获取主要人员对外投资节点
			twoNodeList = queryNameEnt(kyMap2, kyList.get(i).get("CERNO") + "");
			//添加节点
			mapdata = setNode(StrUtil.delNull(kyList.get(i).get("NAME")), twoNodeList, content + "", PURPLE);
			nodeList.add(mapdata);		
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 设置法定代表人节点
	 * @author 龚志强
	 * @since 2016年9月14日下午2:58:12
	 * @param lrList 法定代表人集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryPriPersons(List<Map<String,Object>> lrList, Map<String,Object> lrMap2){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		List<Map<String,Object>> twoNodeList = null; //返回对外投资的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断法定代表人集合是否为空
		if(null == lrList){
			return null;
		}

		for(int i = 0; i < lrList.size(); i++){
			content = new StringBuilder();
			String uuid = UUID.randomUUID().toString();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>法定代表人："+ StrUtil.delNull(lrList.get(i).get("NAME")) +"</td></tr>")
				.append("<tr><td>职务："+ StrUtil.delNull(lrList.get(i).get("POSITION_CN")) +"</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			
			//获取法定代表人对外投资节点
			twoNodeList = queryNameEnt(lrMap2, lrList.get(i).get("CERNO") + "");
			//添加节点
			mapdata = setNode(StrUtil.delNull(lrList.get(i).get("NAME")), twoNodeList, content + "", PURPLE);
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
	public List<Map<String,Object>> queryBrchinfo(List<Map<String,Object>> ebList, Map<String,Object> ebMap2){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		List<Map<String,Object>> twoNodeList = null; //返回对外投资的节点集合
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
				.append("<tr><td>分支机构名称："+ StrUtil.delNull(ebList.get(i).get("BRNAME")) + "</td></tr>");
				if(null == ebList.get(i).get("UNISCID") || "".equals(ebList.get(i).get("UNISCID"))){
					content.append("<tr><td>注册号："+ StrUtil.delNull(ebList.get(i).get("REGNO")) +"</td></tr>");
				}else{
					content.append("<tr><td>统一社会信用代码："+ StrUtil.delNull(ebList.get(i).get("UNISCID")) +"</td></tr>");
				}	
				content.append("<tr><td>登记机关："+ StrUtil.delNull(ebList.get(i).get("REGORG_CN")) +"</td></tr>")
				.append("<tr><td>登记日期："+ StrUtil.delNull(ebList.get(i).get("REGIDATE")) +"</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			
			//获取非自然人对外投资节点
			twoNodeList = queryEntEnt(ebMap2, ebList.get(i).get("REGNO") + "");
			//添加节点
			mapdata = setNode(StrUtil.delNull(ebList.get(i).get("BRNAME")), twoNodeList, content + "", PURPLE);
			nodeList.add(mapdata);
		}
		
		return nodeList;
	}
	
	/**
	 * 
	 * @descript 设置对外投资节点
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

		//循环对外投资集合，添加节点
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
			if("1".equals(StrUtil.delNull(invEstmentList2.get(i).get("REGSTATE"))) || "99".equals(StrUtil.delNull(invEstmentList2.get(i).get("REGSTATE")))){
				mapdata = setNode(StrUtil.delNull(invEstmentList2.get(i).get("ENTNAME")), null, content + "", PURPLE);
			} else {
				mapdata = setNode(StrUtil.delNull(invEstmentList2.get(i).get("ENTNAME")) 
					+ " (" + StrUtil.delNull(invEstmentList2.get(i).get("REGSTATE_CN")) + ")", null, content + "", GRAY);				
			}
			nodeList.add(mapdata);		
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 人名对外投资
	 * @author 龚志强
	 * @since 2016年9月14日下午2:58:49
	 * @param map 自然人集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 * @param entname 企业名称
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryNameEnt(Map<String, Object> map, String mapKey){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;
		List<Map<String,Object>> filist = null;
		StringBuilder content = null;

		//如果结果集为空就返回
		if(null == map){
			return null;
		}
		
		filist = (List<Map<String, Object>>) map.get(mapKey);
		
		//如果获取到的集合为空就返回
		if(null == filist){
			return null;
		}
		
		for(int i = 0; i < filist.size(); i++){
			if(null == filist.get(i).get("ENTNAME")){
				continue;
			}
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>企业名称："+ StrUtil.delNull(filist.get(i).get("ENTNAME")) +"</td></tr>")
				.append("<tr><td>法定代表人："+ StrUtil.delNull(filist.get(i).get("NAME")) +"</td></tr>");			
				if(null == filist.get(i).get("UNISCID") || "".equals(filist.get(i).get("UNISCID"))){
					content.append("<tr><td>注册号："+StrUtil.delNull(filist.get(i).get("REGNO")) +"</td></tr>");
				}else{
					content.append("<tr><td>统一社会信用代码："+ StrUtil.delNull(filist.get(i).get("UNISCID")) +"</td></tr>");
				}
				content.append("<tr><td>注册资本："+ StrUtil.delNull(filist.get(i).get("REGCAP")) + "万元</td></tr>")
				.append("<tr><td>成立时间："+ StrUtil.delNull(filist.get(i).get("ESTDATE")) +"</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			//添加节点
			mapdata = setNode(StrUtil.delNull(filist.get(i).get("ENTNAME")), null, content + "", PURPLE);
			nodeList.add(mapdata);
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 企业对外投资
	 * @author 龚志强
	 * @since 2016年9月14日下午2:59:57
	 * @param map 非自然人集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 * @param entname 企业名称
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryEntEnt(Map<String, Object> map, String mapKey){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;
		List<Map<String,Object>> filist = null;
		StringBuilder content = null;

		//如果结果集为空就返回
		if(null == map){
			return null;
		}

		filist = (List<Map<String, Object>>) map.get(mapKey);
		
		//如果获取到的集合为空就返回
		if(null == filist){
			return null;
		}
		
		for(int i = 0; i < filist.size(); i++){
			if(null == filist.get(i).get("ENTNAME")){
				continue;
			}
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>企业名称："+ StrUtil.delNull(filist.get(i).get("ENTNAME")) +"</td></tr>")
				.append("<tr><td>法定代表人："+ StrUtil.delNull(filist.get(i).get("NAME")) +"</td></tr>");				
				if(null == filist.get(i).get("UNISCID") || "".equals(filist.get(i).get("UNISCID"))){
					content.append("<tr><td>注册号："+ StrUtil.delNull(filist.get(i).get("REGNO")) +"</td></tr>");
				}else{
					content.append("<tr><td>统一社会信用代码："+ StrUtil.delNull(filist.get(i).get("UNISCID")) +"</td></tr>");
				}
				content.append("<tr><td>注册资本："+ StrUtil.delNull(filist.get(i).get("REGCAP")) + "万元</td></tr>")
				.append("<tr><td>成立时间："+ StrUtil.delNull(filist.get(i).get("ESTDATE")) +"</td></tr>")
				.append("<tr><td>登记状态："+ StrUtil.delNull(filist.get(i).get("REGSTATE_CN")) +"</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			
			//添加节点
			mapdata = setNode(StrUtil.delNull(filist.get(i).get("ENTNAME")), null, content + "", PURPLE);
			nodeList.add(mapdata);
		}
		
		return nodeList;
	}

	/**
	 * 设置节点
	 * @descript d3的节点和连接线
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
}