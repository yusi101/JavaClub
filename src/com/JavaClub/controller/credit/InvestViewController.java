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
 * @descript 投资链图
 * @author 龚志强
 * @createTime 2016年9月23日下午4:44:18
 * @version 1.0
 */
@Controller
@RequestMapping(value="/investViewController")
public class InvestViewController extends BaseController{
	
	final String GREEN = "#4aceb1";		//绿色节点
	final String PURPLE = "#7985f3";	//紫色节点
	final String GRAY = "#CCC";			//灰色节点
	final String BLUE = "rgb(36, 166, 218)";	//蓝色节点
	private static final String ENTNAME = "ENTNAME";
	private static final String UNISCID = "UNISCID";
	private static final String REGNO = "REGNO";
	private static final String REGCAP = "REGCAP";
	private static final String ESTDATE = "ESTDATE";
	/**
	 * 投资链图
	 * @descript 
	 * @author 龚志强
	 * @since 2016年10月12日上午10:21:37
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryEnteraddition", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public ModelAndView queryEnteraddition() throws Exception{
		ModelAndView mv = new ModelAndView("investview/enterprise_list");;
		PageData pd = new PageData();
		pd = this.getPageData();
		
    	//设置接口的加密
        Verification.EncodesearchKey(pd, ENTNAME);
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter(pd);
        //调用企业信息查询接口
        Map<String, Object> quertenteraddition = Connect.sendConnectByPdToMap(Const.ENTERPRISEURLFOUR, pd, "POST");
        Verification.DecodesearchKey(pd, ENTNAME);
        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(quertenteraddition)){
            //得到商标信息JSON数据中的data数据
			Map<String,Object> dataMap_enteraddition = (Map<String, Object>) quertenteraddition.get("data");
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
	 * 投资链图
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/investView", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String investView(HttpServletRequest request, HttpServletResponse response) throws ServiceException, IOException {
		PageData pd = new PageData();		
		pd = this.getPageData();
		
		//设置接口加密
		Verification.EncodeKeyNo(pd, "pripid");
		//设置根节点
		Map<String,Object> nodeMap = new HashMap<String, Object>();
		List<Map<String, Object>> nodeList = new ArrayList<Map<String, Object>>();

		//获取数据
		Map<String,Object> resultMap = Connect.sendConnectByPdToMap("/Interface/investViewInterface/queryInvestView.do", pd,"post");

		//判断查询的结果集是否为空
		if(!resultMap.isEmpty()){
			//处理数据
			Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
			//设置自然人节点和连接
			List<Map<String, Object>> npList = (List<Map<String, Object>>) dataMap.get("npList");
			//自然人对外投资节点和连接
			Map<String, Object> npMap2 = (Map<String, Object>) dataMap.get("npMap2");
			//设置非自然人节点和连接
			List<Map<String, Object>> nnpList = (List<Map<String, Object>>) dataMap.get("nnpList");
			//设置对外投资节点和连接
			List<Map<String, Object>> fiList = (List<Map<String, Object>>) dataMap.get("fiList");	
			//对外投资下限节点和连接
			Map<String, Object> fiMap2 = (Map<String, Object>) dataMap.get("fiMap2");
			Map<String, Object> fiMap3 = (Map<String, Object>) dataMap.get("fiMap3");
			//非自然人对外投资节点和连接
			Map<String, Object> nnpMap2 = (Map<String, Object>) dataMap.get("nnpMap2");
			//设置分支机构节点和连接
			List<Map<String, Object>> ebList = (List<Map<String, Object>>) dataMap.get("ebList");
			
			//设置基础节点
			nodeList.add(setNode("股东", queryInvPerson(npList, nnpList, npMap2, nnpMap2), "", npList.isEmpty() && nnpList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("分支机构", queryBrchinfo(ebList), "", ebList.isEmpty() ? GRAY : GREEN));
			nodeList.add(setNode("对外投资", queryInvInvestments(fiList,fiMap2,fiMap3), "", fiList.isEmpty() ? GRAY : GREEN));
		}

		nodeMap.put("name", pd.getString(ENTNAME));
		nodeMap.put("children", nodeList);
		nodeMap.put("content", "");
		nodeMap.put("fill", BLUE);
		
		return JsonUtils.toJson(nodeMap);
	}
	
	/**
	 * 
	 * @descript 设置自然人节点和连接
	 * @author 龚志强
	 * @since 2016年9月14日下午2:51:22
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
				String uuid = UUID.randomUUID().toString();
				content = new StringBuilder();
				content.append("<table id='" + uuid.replace("-", "") + "'>")
					.append("<tr><td>股东："+ StrUtil.delNull(npList.get(i).get("INV")) +"</td></tr>")
					.append("<tr><td>股东类型：自然人</td></tr>")
					.append("<tr><td>证照/证件类型："+ StrUtil.delNull(npList.get(i).get("CERTYPE_CN")) +"</td></tr>")	
					.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");			
				
				
				//获取自然人对外投资节点
				twoNodeList = queryInvPersonTwo(npMap2, npList.get(i).get("CERNO") + "");
				//添加节点
				mapdata = setNode(StrUtil.delNull(npList.get(i).get("INV")), twoNodeList, content + "", PURPLE);
				nodeList.add(mapdata);						
			}
		}
		
		//判断非自然人集合是否为空
		if(null != nnpList){
			for(int i = 0; i < nnpList.size(); i++){
				String uuid = UUID.randomUUID().toString();
				content = new StringBuilder();
				content.append("<table id='" + uuid.replace("-", "") + "'>")
					.append("<tr><td>股东："+ StrUtil.delNull(nnpList.get(i).get("INV")) +"</td></tr>")
					.append("<tr><td>股东类型：" + StrUtil.delNull(nnpList.get(i).get("INVTYPE_CN")) + "</td></tr>")
					.append("<tr><td>证照/证件类型："+ StrUtil.delNull(nnpList.get(i).get("BLICTYPE_CN")) +"</td></tr>")
					.append("<tr><td>证照/证件号码：***</td></tr>")
					.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");			
				
				//获取非自然人对外投资节点
				twoNodeList = queryInvInvestmentTwo(nnpMap2, nnpList.get(i).get("BLICNO") + "");
				//添加节点
				mapdata = setNode(StrUtil.delNull(nnpList.get(i).get("INV")), twoNodeList ,content + "", PURPLE);
				nodeList.add(mapdata);
			}
		}

		return nodeList;
	}

	/**
	 * 
	 * @descript 设置对外投资节点和连接
	 * @author 龚志强
	 * @since 2016年9月14日下午2:52:19
	 * @param fiList 对外投资集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 */
	public List<Map<String,Object>> queryInvInvestments(List<Map<String,Object>> fiList,Map<String,Object> fiMap2, Map<String,Object> fiMap3){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		List<Map<String,Object>> twoNodeList = null; //返回对外投资的节点集合
		Map<String,Object> mapdata = null;	//临时节点连接数据集合
		StringBuilder content = null;		//节点详情

		//判断对外投资集合是否为空
		if(null == fiList){
			return null;
		}

		for(int i = 0; i < fiList.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>企业名称："+ StrUtil.delNull(fiList.get(i).get(ENTNAME)) +"</td></tr>")
				.append("<tr><td>法定代表人："+ StrUtil.delNull(fiList.get(i).get("NAME")) +"</td></tr>");				
				if(null == fiList.get(i).get(UNISCID) || "".equals(fiList.get(i).get(UNISCID))){
					content.append("<tr><td>注册号："+ StrUtil.delNull(fiList.get(i).get(REGNO)) +"</td></tr>");
				}else{
					content.append("<tr><td>统一社会信用代码："+ StrUtil.delNull(fiList.get(i).get(UNISCID)) +"</td></tr>");
				}
				content.append("<tr><td>注册资本："+ StrUtil.delNull(fiList.get(i).get(REGCAP)) + "万元</td></tr>")
				.append("<tr><td>成立时间："+ StrUtil.delNull(fiList.get(i).get(ESTDATE)) +"</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
		
			twoNodeList = queryInvInvestmentThree(fiMap2, fiMap3, fiList.get(i).get(REGNO) + "");
			//添加节点
			mapdata = setNode(StrUtil.delNull(fiList.get(i).get(ENTNAME)), twoNodeList, content + "", PURPLE);
			nodeList.add(mapdata);		
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 设置分支机构节点和连接
	 * @author 龚志强
	 * @since 2016年9月14日下午2:52:37
	 * @param ebList 分支机构集合
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
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>分支机构名称："+ ebList.get(i).get("BRNAME") + "</td></tr>");		
				if(null == ebList.get(i).get(UNISCID) || "".equals(ebList.get(i).get(UNISCID))){
					content.append("<tr><td>注册号：" + ebList.get(i).get(REGNO) + "</td></tr>");
				}else{
					content.append("<tr><td>统一社会信用代码：" + ebList.get(i).get(UNISCID) + "</td></tr>");
				}	
				content.append("<tr><td>登记机关：" + ebList.get(i).get("REGORG_CN") + "</td></tr>")
				.append("<tr><td>登记日期：" + ebList.get(i).get("REGIDATE") + "</td></tr>") 
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			
			//添加节点
			mapdata = setNode(StrUtil.delNull(ebList.get(i).get("BRNAME")), null , content + "", PURPLE);
			nodeList.add(mapdata);	
		}
		
		return nodeList;
	}

	/**
	 * 
	 * @descript 企业对外投资第三层
	 * @author 龚志强
	 * @since 2016年9月14日下午2:53:04
	 * @param nnpMap2 对外投资集合
	 * @param nodeList 节点集合
	 * @param linkList 连点集合
	 * @param num 节点分组
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryInvInvestmentThree(Map<String, Object> nnpMap2, Map<String, Object> nnpMap3, String mapKey){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		List<Map<String,Object>> twoNodeList = null; //返回对外投资的节点集合
		Map<String,Object> mapdata = null;		//临时节点连接数据集合
		List<Map<String,Object>> filist = null;	//临时对外投资数据集合
		StringBuilder content = null;			//节点详情		

		//判断对外投资集合是否为空
		if(null == nnpMap2){
			return null;
		}
	
		filist = (List<Map<String, Object>>) nnpMap2.get(mapKey);
		
		//如果获取到的集合为空就返回
		if(null == filist){
			return null;
		}
		
		for(int i = 0; i < filist.size(); i++){	
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>企业名称："+ StrUtil.delNull(filist.get(i).get(ENTNAME)) +"</td></tr>")
				.append("<tr><td>法定代表人："+ StrUtil.delNull(filist.get(i).get("NAME")) +"</td></tr>");				
				if(null == filist.get(i).get(UNISCID) || "".equals(filist.get(i).get(UNISCID))){
					content.append("<tr><td>注册号："+ StrUtil.delNull(filist.get(i).get(REGNO)) +"</td></tr>");
				}else{
					content.append("<tr><td>统一社会信用代码："+ StrUtil.delNull(filist.get(i).get(UNISCID)) +"</td></tr>");
				}
				content.append("<tr><td>注册资本："+ StrUtil.delNull(filist.get(i).get(REGCAP)) + "万元</td></tr>")
				.append("<tr><td>成立时间："+ StrUtil.delNull(filist.get(i).get(ESTDATE)) +"</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			
			//判断是否有第三层企业对外投资
			if(null != nnpMap3){
				twoNodeList = queryInvInvestmentTwo(nnpMap3, filist.get(i).get(REGNO) + "");
			}
			//添加节点
			mapdata = setNode(StrUtil.delNull(filist.get(i).get(ENTNAME)), twoNodeList, content + "", PURPLE);
			nodeList.add(mapdata);		
		}
	
		return nodeList;
	}
	
	/**
	 * 
	 * @descript 企业对外投资第二层
	 * @author 龚志强
	 * @since 2016年9月14日下午2:53:04
	 * @param nnpMap2 对外投资集合
	 * @param nodeList 节点集合
	 * @param linkList 连点集合
	 * @param num 节点分组
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryInvInvestmentTwo(Map<String, Object> nnpMap3, String mapKey){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;		//临时节点连接数据集合
		List<Map<String,Object>> filist = null;	//临时对外投资数据集合
		StringBuilder content = null;			//节点详情		

		//判断对外投资集合是否为空
		if(null == nnpMap3){
			return null;	
		}
	
		filist = (List<Map<String, Object>>) nnpMap3.get(mapKey);
		
		//如果获取到的集合为空就返回
		if(null == filist){
			return null;
		}
		
		for(int i = 0; i < filist.size(); i++){	
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>企业名称："+ StrUtil.delNull(filist.get(i).get(ENTNAME)) +"</td></tr>")
				.append("<tr><td>法定代表人："+ StrUtil.delNull(filist.get(i).get("NAME")) +"</td></tr>");				
				if(null == filist.get(i).get(UNISCID) || "".equals(filist.get(i).get(UNISCID))){
					content.append("<tr><td>注册号："+ StrUtil.delNull(filist.get(i).get(REGNO)) +"</td></tr>");
				}else{
					content.append("<tr><td>统一社会信用代码："+ StrUtil.delNull(filist.get(i).get(UNISCID)) +"</td></tr>");
				}
				content.append("<tr><td>注册资本："+ StrUtil.delNull(filist.get(i).get(REGCAP)) + "万元</td></tr>")
				.append("<tr><td>成立时间："+ StrUtil.delNull(filist.get(i).get(ESTDATE)) +"</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");		
			
			//添加节点
			mapdata = setNode(StrUtil.delNull(filist.get(i).get(ENTNAME)), null, content + "", PURPLE);
			nodeList.add(mapdata);		
		}
	
		return nodeList;
	}

	/**
	 * 
	 * @descript 自然人对外投资
	 * @author 龚志强
	 * @since 2016年9月14日下午2:53:40
	 * @param npMap2 自然人集合
	 * @param nodeList 节点集合
	 * @param linkList 连接集合
	 * @param entname 企业名称
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryInvPersonTwo(Map<String, Object> npMap2, String mapKey){
		List<Map<String,Object>> nodeList = new ArrayList<Map<String,Object>>(); //返回的节点集合
		Map<String,Object> mapdata = null;		//临时节点连接数据集合
		List<Map<String,Object>> filist = null;	//临时自然人集合
		StringBuilder content = null;			//节点详情

		//判断自然人集合是否为空
		if(null == npMap2){
			return null;
		}
	
		filist = (List<Map<String, Object>>) npMap2.get(mapKey);
		
		//如果获取到的集合为空就返回
		if(null == filist){
			return null;
		}

		for(int i = 0; i < filist.size(); i++){
			String uuid = UUID.randomUUID().toString();
			content = new StringBuilder();
			content.append("<table id='" + uuid.replace("-", "") + "'>")
				.append("<tr><td>企业名称："+ StrUtil.delNull(filist.get(i).get(ENTNAME)) +"</td></tr>")
				.append("<tr><td>法定代表人："+ StrUtil.delNull(filist.get(i).get("NAME")) +"</td></tr>");			
				if(null == filist.get(i).get(UNISCID) || "".equals(filist.get(i).get(UNISCID))){
					content.append("<tr><td>注册号："+StrUtil.delNull(filist.get(i).get(REGNO)) +"</td></tr>");
				}else{
					content.append("<tr><td>统一社会信用代码："+ StrUtil.delNull(filist.get(i).get(UNISCID)) +"</td></tr>");
				}
				content.append("<tr><td>注册资本："+ StrUtil.delNull(filist.get(i).get(REGCAP)) + "万元</td></tr>")
				.append("<tr><td>成立时间："+ StrUtil.delNull(filist.get(i).get(ESTDATE)) +"</td></tr>")
				.append("<tr align='center'><td><input type='button' class='ok' id='ok' value='确定' onclick='ok(\"" + uuid.replace("-", "") + "\")'/></td></tr></table>");
			
			//添加节点
			mapdata = setNode(StrUtil.delNull(filist.get(i).get(ENTNAME)), null, content + "", PURPLE);
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
}