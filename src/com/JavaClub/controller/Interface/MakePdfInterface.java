/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年9月22日下午7:06:10
 * @version 1.0
 */
package com.JavaClub.controller.Interface;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.Page;
import com.JavaClub.service.Interface.AnnualReportService;
import com.JavaClub.service.Interface.AnomaliesService;
import com.JavaClub.service.Interface.BiddingService;
import com.JavaClub.service.Interface.BrandService;
import com.JavaClub.service.Interface.CopyrightService;
import com.JavaClub.service.Interface.CourtcaseService;
import com.JavaClub.service.Interface.InfomessageService;
import com.JavaClub.service.Interface.JobInfoService;
import com.JavaClub.service.Interface.MortregService;
import com.JavaClub.service.Interface.Myselferservice;
import com.JavaClub.service.Interface.PatentService;
import com.JavaClub.service.Interface.PledgeService;
import com.JavaClub.service.credit.InvestmentService;
import com.JavaClub.service.credit.ReportService;
import com.JavaClub.util.Connect;
import com.JavaClub.util.Const;
import com.JavaClub.util.CreateCode;
import com.JavaClub.util.FileUtil;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.Logger;
import com.JavaClub.util.PDFUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.SendMailUtil;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.UuidUtil;
import com.JavaClub.util.Verification;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdfs.MakePDF;
import com.lowagie.text.pdfs.PdfUtil;
import com.lowagie.text.pdfs.TableHeader;

/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年9月22日下午7:06:10
 * @version 1.0
 */

@Controller
@RequestMapping(value = "Interface/MakePdfInterface")
public class MakePdfInterface extends BaseController{
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	public Myselferservice myselferservice;
	@Autowired
	private InfomessageService infomessageService;

	@Autowired
	private CourtcaseService courtcaseService;

	@Autowired
	private AnomaliesService anomaliesService;

	@Autowired
	private MortregService mortregService;

	@Autowired
	private PledgeService pledgeService;

	@Autowired
	private CopyrightService copyrightService;

	@Autowired
	private PatentService patentService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private BiddingService biddingService;

	@Autowired
	private JobInfoService jobInfoService;

	@Autowired
	private AnnualReportService annualreportService;

	@Autowired
	private InvestmentService investmentService;

	@Autowired
	private ReportService reportService;


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/makePdf", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String makePdf(HttpServletRequest request) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, Object> CURRENTINDEX=new HashMap<>(5);
		CURRENTINDEX.put("CURRENTINDEX_ONE", 1);
		CURRENTINDEX.put("CURRENTINDEX_TWO", 1);
		CURRENTINDEX.put("CURRENTINDEX_THREE", 1);
		CURRENTINDEX.put("CATALOGUE_LIST", new ArrayList());
		//设置接口的加密
		Verification.EncodeKeyNoPDF(pd, "pripid");

		//判断pdf名字是否包含中文 ，因为ios传过来的是 编码的 即 乱码  所以没有中文
		if(isContainsChinese(pd.getString("entname"))==false){
			//当fileName不包含中文时就是ios传过来的值  就需要编码
			pd.put("entname", URLDecoder.decode(pd.getString("entname") ,"UTF-8"));
		}
		String select = pd.getString("select") == null
				? "股东信息, 主要人员,分支机构,变更记录,荣誉信息,失信被执行人,法院判决文书,经营异常,年报信息,股权出质,动产抵押,行政处罚,行政许可,司法信息,预警信息,广告资质,守合同重信用,著作权,软件著作权,专利,商标,招投标,招聘" : pd.getString("select");
		if(isContainsChinese(select)==false){
			//当fileName不包含中文时就是ios传过来的值  就需要编码
			select= URLDecoder.decode(select ,"UTF-8");
		}
		String path = this.getServicePath("/uploadfiles/qrcode");

		//判断路径是否存在  不存在则创建
		FileUtil.createDir(path);

		String fileName = pd.getString("entname") + "2.pdf";
		pd.put("path", path);
		try {
			Document document = new Document(PageSize.A4, 36, 36, 65, 30);
			PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(path + File.separator + fileName));// 报告的位置
			Rectangle rect = new Rectangle(36, 54, 559, 788);
			rect.setBorderColor(Color.black);
			writer.setBoxSize("art", rect);
			TableHeader header=new TableHeader(); 
			header.setOnStartPage(0);
			header.setImagePath(request.getSession().getServletContext().getRealPath("static/images/makepdf"));
			header.setEntname(pd.getString("entname"));
			writer.setPageEvent(header);
			
			BaseFont baseFont = MakePDF.getBaseFontYh(); 
			Image image;
			Paragraph paragraph;

			document.open();
			String sort = "";
			String name = "";
			List allBaseInfo = infomessageService.queryAllBaseInfo(pd);
			String REGCAPCUR_CN = "万";// 币种



			/**
			 * 工商信息
			 */
			document.newPage();
			PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,1,"工商信息");

			PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"登记信息");
			sort = "UNISCID,ENTNAME,REGNO,NAME,ENTTYPE_CN,INDUSTRYPHY,REGCAP,REGORG_CN,ESTDATE,APPRDATE,OPSCOPE,OPFROM,OPTO,REGSTATE_CN,DOM";
			name = "统一社会信用代码,企业名称,注册号,法定代表人,企业类型,行业门类,注册资本,登记机关,成立日期,发证日期,经营范围,经营期限自,经营期限至,登记状态,公司地址";
			float[] w2 = { 0.3f, 0.7f };
			Map<String, Object> baseInfo = ListUtils.getListMap((List<Map<String, Object>>) allBaseInfo.get(0), 0);
			if (baseInfo != null) {
				pd.put("priptype", baseInfo.get("ENTTYPE"));
				// 对资金处理
				REGCAPCUR_CN += (baseInfo.get("REGCAPCUR_CN")==null? "0" : baseInfo.get("REGCAPCUR_CN").toString());
				String str = StringUtil.subZeroAndDot(baseInfo.get("REGCAP") == null ? "0" : baseInfo.get("REGCAP").toString());
				baseInfo.put("REGCAP", str + REGCAPCUR_CN);
			}
			document.add(MakePDF.MyTable(baseInfo, sort, name, w2));
			document.newPage();
			if(select.lastIndexOf("股东信息")>=0){
				// 股东信息		
				List<Map<String, Object>> partnerList = (List<Map<String, Object>>) allBaseInfo.get(1);
				if("453".equals(pd.getString("priptype").substring(0, 3)) || "455".equals(pd.getString("priptype").substring(0, 3))){ //内资合伙企业
					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"合伙人信息");
					sort = "INV,INVTYPE_CN,CERTYPE_CN,CERNO";// 对Map的值排序
					name = "合伙人,合伙人类型,证照/证件类型,证照/证件号码";
					float[] w5 = { 0.2f, 0.2f,0.3f, 0.3f }; 
					for (int i = 0; i < partnerList.size(); i++) {
						Map<String, Object> partnerMap=partnerList.get(i);
						if(partnerMap.get("CERTYPE_CN")!=null){
							partnerMap.put("CERNO", StringUtil.replace(partnerMap.get("CERNO").toString()));
						}
						partnerList.set(i, partnerMap);
					}
					document.add(MakePDF.MyTable(partnerList, sort, name, w5,1)); 
				}else if("5840".equals(pd.getString("priptype").substring(0, 4)) || "6840".equals(pd.getString("priptype").substring(0, 4))){ //外资合伙企业
					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"合伙人信息");
					sort = "INV,INVTYPE_CN,CERTYPE_CN,CERNO,COUNTRY_CN";// 对Map的值排序
					name = "合伙人,合伙人类型,证照/证件类型,证照/证件号码,国别(地区)";
					float[] w5 = { 0.15f, 0.15f,0.25f, 0.25f, 0.2f }; 
					for (int i = 0; i < partnerList.size(); i++) {
						Map<String, Object> partnerMap=partnerList.get(i);
						if(partnerMap.get("CERTYPE_CN")!=null){
							partnerMap.put("CERNO", StringUtil.replace(partnerMap.get("CERNO").toString()));
						}
						partnerList.set(i, partnerMap);
					}
					document.add(MakePDF.MyTable(partnerList, sort, name, w5,1)); 
				}else if("4540".equals(pd.getString("priptype").substring(0, 4)) || "4560".equals(pd.getString("priptype").substring(0, 4))){ //个人独资
					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"出资人信息");
					sort = "INV,SCONFORM";// 对Map的值排序
					name = "姓名,出资方式";
					float[] w5 = { 0.4f, 0.6f}; 
					document.add(MakePDF.MyTable(partnerList, sort, name, w5,1)); 
				}else if("53".equals(pd.getString("priptype").substring(0, 2)) || "63".equals(pd.getString("priptype").substring(0, 2))){ //中外合作非法人企业
					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"合作人信息");
					sort = "INV,SCONFORM";// 对Map的值排序
					name = "合作各方的名称,责任形式";
					float[] w5 = { 0.5f, 0.5f}; 
					document.add(MakePDF.MyTable(partnerList, sort, name, w5,1)); 
				}else{
					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"股东信息");
					sort = "INV,INVTYPE_CN,CERTYPE_CN,CERNO";// 对Map的值排序
					name = "姓名,股东类型,证照/证件类型,证照/证件号码";
					float[] w5 = { 0.2f, 0.2f,0.3f, 0.3f }; 
					for (int i = 0; i < partnerList.size(); i++) {
						Map<String, Object> partnerMap=partnerList.get(i);
						if(partnerMap.get("CERTYPE_CN")!=null){
							partnerMap.put("CERNO", StringUtil.replace(partnerMap.get("CERNO").toString()));
						}
						partnerList.set(i, partnerMap);
					}
					document.add(MakePDF.MyTable(partnerList, sort, name, w5,1)); 
				}
			}
			if (select.lastIndexOf("主要人员信息")>=0) {
				// 主要人员
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"主要人员信息");
				List<Map<String, Object>> personList = (List<Map<String, Object>>) allBaseInfo.get(2);
				sort = "NAME,POSITION_CN,SEX";// 对Map的值排序
				name = "姓名,职位,性别";
				float[] w4 = { 0.3f, 0.3f, 0.4f };
				document.add(MakePDF.MyTable(personList, sort, name, w4,1));
			}
			if (select.lastIndexOf("变更记录信息")>=0) {
				// 变更记录
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"变更记录信息");
				List<Map<String, Object>> changeList = (List<Map<String, Object>>) allBaseInfo.get(3);
				sort = "ALTITEM_CN,ALTBE,ALTAF,ALTDATE";// 对Map的值排序
				name = "变更事项,变更前内容,变更后内容,变更日期";
				float[] w = { 0.25f, 0.3f, 0.3f, 0.15f };
				for (int i = 0; i < changeList.size(); i++) {
					Map<String, Object> listChangemap=changeList.get(i);
					if("投资人(股权)变更".equals(listChangemap.get("ALTITEM_CN"))){
						String altaf = listChangemap.get("ALTAF").toString();
						String altbe = listChangemap.get("ALTBE").toString();
						String newaltaf="";
						String newaaltbe="";
						String[] altafString = altaf.split(";");
						String[] altbeString = altbe.split(";"); 
						for (int j = 0; j < altafString.length; j++) {
							newaltaf+="投资人："+altafString[j].split(",")[0]+"  ";
						}
						for (int j = 0; j < altbeString.length; j++) {
							newaaltbe+="投资人："+altbeString[j].split(",")[0]+"  ";
						}
						listChangemap.put("ALTAF", newaltaf);
						listChangemap.put("ALTBE", newaaltbe);
					}else if("注册资本(金)变更".equals(listChangemap.get("ALTITEM_CN"))){
						listChangemap.put("ALTAF", StringUtil.subZeroAndDot(listChangemap.get("ALTAF").toString()));
						listChangemap.put("ALTBE", StringUtil.subZeroAndDot(listChangemap.get("ALTBE").toString()));
					}else if("更换统一社会信用代码".equals(listChangemap.get("ALTITEM_CN"))){
						listChangemap.put("ALTAF",listChangemap.get("ALTAF"));
						listChangemap.put("ALTBE",listChangemap.get("ALTBE"));
					}else{
						listChangemap.put("ALTAF", StringUtil.replace(listChangemap.get("ALTAF").toString()));
						listChangemap.put("ALTBE", StringUtil.replace(listChangemap.get("ALTBE").toString()));
					}
					changeList.set(i, listChangemap);
				}
				document.add(MakePDF.MyTable(changeList, sort, name, w,1));
			}
			if (select.lastIndexOf("分支机构信息")>=0) {
				// 分支机构
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"分支机构信息");
				List<Map<String, Object>> branchList = (List<Map<String, Object>>) allBaseInfo.get(4);
				sort = "BRNAME,REGNO,REGORG_CN,REGSTATE_CN";// 对Map的值排序
				name = "企业名称,统一社会信用代码/注册号,登记机关,登记状态";
				float[] w3 = { 0.3f, 0.3f, 0.3f, 0.1f };
				for (int i = 0; i < branchList.size(); i++) {
					Map<String, Object> branchMap=branchList.get(i);
					if(branchMap.get("UNISCID")!=null && !"".equals(branchMap.get("UNISCID"))){
						branchMap.put("REGNO", branchMap.get("UNISCID"));
					}
					branchList.set(i, branchMap);
				}
				document.add(MakePDF.MyTable(branchList, sort, name, w3,1));
			} 
			// 对外投资信息
			PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,1,"对外投资信息");
			List perhapsRelation= investmentService.queryPerhapsRelation(pd);
			float[] w3 = { 0.1f,0.20f, 0.25f, 0.15f, 0.15f,0.07f,0.08f};
			float[] w4 = { 0.1f,0.15f, 0.20f, 0.15f, 0.15f,0.07f,0.08f,0.1f};
			if(select.lastIndexOf("企业对外投资信息")>=0){
				float[] ent_perhapsRelation_w2 = { 0.25f, 0.20f, 0.15f, 0.15f,0.07f,0.08f,0.1f};
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"企业对外投资信息");

				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"企业对外投资的企业信息");
				List<Map<String, Object>> ent_perhapsRelation3 = (List<Map<String, Object>>) perhapsRelation.get(2);
				sort = "ENTNAME,REGNO,ESTDATE,ENTTYPE_CN,REGSTATE_CN,REGCAP,POSITION_CN";// 对Map的值排序
				name = "企业名称,统一社会信用代码/注册号,成立日期,企业类型,登记状态,注册资本,投资人类型";
				for (int i = 0; i < ent_perhapsRelation3.size(); i++) {
					Map<String, Object>  perhapsRelation_Map=ent_perhapsRelation3.get(i);
					if(perhapsRelation_Map.get("ENTNAME")!=null && !"".equals(perhapsRelation_Map.get("ENTNAME"))){
						String REGCAP=perhapsRelation_Map.get("REGCAP") == null ? "0" : StringUtil.subZeroAndDot(perhapsRelation_Map.get("REGCAP").toString());
						REGCAP+="万"+ (perhapsRelation_Map.get("REGCAPCUR_CN")==null ? "": perhapsRelation_Map.get("REGCAPCUR_CN").toString());
						perhapsRelation_Map.put("REGCAP", REGCAP);
						if(perhapsRelation_Map.get("UNISCID")!=null && !"".equals(perhapsRelation_Map.get("UNISCID"))){
							perhapsRelation_Map.put("REGNO", perhapsRelation_Map.get("UNISCID"));
						}
						ent_perhapsRelation3.set(i, perhapsRelation_Map);
					}else{
						ent_perhapsRelation3.remove(i);  
					}
				}
				document.add(MakePDF.MyTable(ent_perhapsRelation3, sort, name,ent_perhapsRelation_w2,1));  
			}

			if(select.lastIndexOf("法定代表人对外投资信息")>=0){
				//法定代表人
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"法定代表人对外投资信息");

				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"法定代表人担任其他企业法定代表人的企业信息");
				List<Map<String, Object>> legal_perhapsRelation1 = (List<Map<String, Object>>) perhapsRelation.get(3);
				sort = "NAME,ENTNAME,REGNO,ESTDATE,ENTTYPE_CN,REGSTATE_CN,REGCAP";// 对Map的值排序
				name = "姓名,企业名称,统一社会信用代码/注册号,成立日期,企业类型,登记状态,注册资本";
				for (int i = 0; i < legal_perhapsRelation1.size(); i++) {
					Map<String, Object>  perhapsRelation_Map=legal_perhapsRelation1.get(i);
					if(perhapsRelation_Map.get("ENTNAME")!=null && !"".equals(perhapsRelation_Map.get("ENTNAME"))){
						String REGCAP=perhapsRelation_Map.get("REGCAP") == null ? "0" : StringUtil.subZeroAndDot(perhapsRelation_Map.get("REGCAP").toString());
						REGCAP+="万"+ (perhapsRelation_Map.get("REGCAPCUR_CN")==null ? "": perhapsRelation_Map.get("REGCAPCUR_CN").toString());
						perhapsRelation_Map.put("REGCAP", REGCAP);
						if(perhapsRelation_Map.get("UNISCID")!=null && !"".equals(perhapsRelation_Map.get("UNISCID"))){
							perhapsRelation_Map.put("REGNO", perhapsRelation_Map.get("UNISCID"));
						}
						legal_perhapsRelation1.set(i, perhapsRelation_Map);
					}else{
						legal_perhapsRelation1.remove(i);  
					}
				}
				document.add(MakePDF.MyTable(legal_perhapsRelation1, sort, name, w3,1));


				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"法定代表人任职的企业信息");
				List<Map<String, Object>> legal_perhapsRelation2 = (List<Map<String, Object>>) perhapsRelation.get(4);
				sort = "NAME,ENTNAME,REGNO,ESTDATE,ENTTYPE_CN,REGSTATE_CN,REGCAP,POSITION_CN";// 对Map的值排序
				name = "姓名,企业名称,统一社会信用代码/注册号,成立日期,企业类型,登记状态,注册资本,职务";
				for (int i = 0; i < legal_perhapsRelation2.size(); i++) {
					Map<String, Object>  perhapsRelation_Map=legal_perhapsRelation2.get(i);
					if(perhapsRelation_Map.get("ENTNAME")!=null && !"".equals(perhapsRelation_Map.get("ENTNAME"))){
						String REGCAP=perhapsRelation_Map.get("REGCAP") == null ? "0" : StringUtil.subZeroAndDot(perhapsRelation_Map.get("REGCAP").toString());
						REGCAP+="万"+ (perhapsRelation_Map.get("REGCAPCUR_CN")==null ? "": perhapsRelation_Map.get("REGCAPCUR_CN").toString());
						perhapsRelation_Map.put("REGCAP", REGCAP);
						if(perhapsRelation_Map.get("UNISCID")!=null && !"".equals(perhapsRelation_Map.get("UNISCID"))){
							perhapsRelation_Map.put("REGNO", perhapsRelation_Map.get("UNISCID"));
						}
						legal_perhapsRelation2.set(i, perhapsRelation_Map);
					}else{
						legal_perhapsRelation2.remove(i);  
					}
				}
				document.add(MakePDF.MyTable(legal_perhapsRelation2, sort, name, w4,1));



				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"法定代表人对外投资的企业信息");
				List<Map<String, Object>> legal_perhapsRelation3 = (List<Map<String, Object>>) perhapsRelation.get(5);
				sort = "NAME,ENTNAME,REGNO,ESTDATE,ENTTYPE_CN,REGSTATE_CN,REGCAP,POSITION_CN";// 对Map的值排序
				name = "姓名,企业名称,统一社会信用代码/注册号,成立日期,企业类型,登记状态,注册资本,投资人类型";
				for (int i = 0; i < legal_perhapsRelation3.size(); i++) {
					Map<String, Object>  perhapsRelation_Map=legal_perhapsRelation3.get(i);
					if(perhapsRelation_Map.get("ENTNAME")!=null && !"".equals(perhapsRelation_Map.get("ENTNAME"))){
						String REGCAP=perhapsRelation_Map.get("REGCAP") == null ? "0" : StringUtil.subZeroAndDot(perhapsRelation_Map.get("REGCAP").toString());
						REGCAP+="万"+ (perhapsRelation_Map.get("REGCAPCUR_CN")==null ? "": perhapsRelation_Map.get("REGCAPCUR_CN").toString());
						perhapsRelation_Map.put("REGCAP", REGCAP);
						if(perhapsRelation_Map.get("UNISCID")!=null && !"".equals(perhapsRelation_Map.get("UNISCID"))){
							perhapsRelation_Map.put("REGNO", perhapsRelation_Map.get("UNISCID"));
						}
						legal_perhapsRelation3.set(i, perhapsRelation_Map);
					}else{
						legal_perhapsRelation3.remove(i);  
					}
				}
				document.add(MakePDF.MyTable(legal_perhapsRelation3, sort, name,w4,1)); 
			}

			if(select.lastIndexOf("主要人员对外投资信息")>=0){
				//主要人员对外投资信息
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"主要人员对外投资信息");

				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"主要人员担任法定代表人的企业信息");
				List<Map<String, Object>> personnel_perhapsRelation1 = (List<Map<String, Object>>) perhapsRelation.get(6);
				sort = "NAME,ENTNAME,REGNO,ESTDATE,ENTTYPE_CN,REGSTATE_CN,REGCAP";// 对Map的值排序
				name = "姓名,企业名称,统一社会信用代码/注册号,成立日期,企业类型,登记状态,注册资本";
				for (int i = 0; i < personnel_perhapsRelation1.size(); i++) {
					Map<String, Object>  perhapsRelation_Map=personnel_perhapsRelation1.get(i);
					if(perhapsRelation_Map.get("ENTNAME")!=null && !"".equals(perhapsRelation_Map.get("ENTNAME"))){
						String REGCAP=perhapsRelation_Map.get("REGCAP") == null ? "0" : StringUtil.subZeroAndDot(perhapsRelation_Map.get("REGCAP").toString());
						REGCAP+="万"+ (perhapsRelation_Map.get("REGCAPCUR_CN")==null ? "": perhapsRelation_Map.get("REGCAPCUR_CN").toString());
						perhapsRelation_Map.put("REGCAP", REGCAP);
						if(perhapsRelation_Map.get("UNISCID")!=null && !"".equals(perhapsRelation_Map.get("UNISCID"))){
							perhapsRelation_Map.put("REGNO", perhapsRelation_Map.get("UNISCID"));
						}
						personnel_perhapsRelation1.set(i, perhapsRelation_Map);
					}else{
						personnel_perhapsRelation1.remove(i);  
					}
				}
				document.add(MakePDF.MyTable(personnel_perhapsRelation1, sort, name, w3,1));


				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"主要人员任职的企业信息");
				List<Map<String, Object>> personnel_perhapsRelation2 = (List<Map<String, Object>>) perhapsRelation.get(7);
				sort = "NAME,ENTNAME,REGNO,ESTDATE,ENTTYPE_CN,REGSTATE_CN,REGCAP,POSITION_CN";// 对Map的值排序
				name = "姓名,企业名称,统一社会信用代码/注册号,成立日期,企业类型,登记状态,注册资本,职务";
				for (int i = 0; i < personnel_perhapsRelation2.size(); i++) {
					Map<String, Object>  perhapsRelation_Map=personnel_perhapsRelation2.get(i);
					if(perhapsRelation_Map.get("ENTNAME")!=null && !"".equals(perhapsRelation_Map.get("ENTNAME"))){
						String REGCAP=perhapsRelation_Map.get("REGCAP") == null ? "0" : StringUtil.subZeroAndDot(perhapsRelation_Map.get("REGCAP").toString());
						REGCAP+="万"+ (perhapsRelation_Map.get("REGCAPCUR_CN")==null ? "": perhapsRelation_Map.get("REGCAPCUR_CN").toString());
						perhapsRelation_Map.put("REGCAP", REGCAP);
						if(perhapsRelation_Map.get("UNISCID")!=null && !"".equals(perhapsRelation_Map.get("UNISCID"))){
							perhapsRelation_Map.put("REGNO", perhapsRelation_Map.get("UNISCID"));
						}
						personnel_perhapsRelation2.set(i, perhapsRelation_Map);
					}else{
						personnel_perhapsRelation2.remove(i);  
					}
				}
				document.add(MakePDF.MyTable(personnel_perhapsRelation2, sort, name, w4,1));



				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"主要人员对外投资的企业信息");
				List<Map<String, Object>> personnel_perhapsRelation3 = (List<Map<String, Object>>) perhapsRelation.get(8);
				sort = "NAME,ENTNAME,REGNO,ESTDATE,ENTTYPE_CN,REGSTATE_CN,REGCAP,POSITION_CN";// 对Map的值排序
				name = "姓名,企业名称,统一社会信用代码/注册号,成立日期,企业类型,登记状态,注册资本,投资人类型";
				for (int i = 0; i < personnel_perhapsRelation3.size(); i++) {
					Map<String, Object>  perhapsRelation_Map=personnel_perhapsRelation3.get(i);
					if(perhapsRelation_Map.get("ENTNAME")!=null && !"".equals(perhapsRelation_Map.get("ENTNAME"))){
						String REGCAP=perhapsRelation_Map.get("REGCAP") == null ? "0" : StringUtil.subZeroAndDot(perhapsRelation_Map.get("REGCAP").toString());
						REGCAP+="万"+ (perhapsRelation_Map.get("REGCAPCUR_CN")==null ? "": perhapsRelation_Map.get("REGCAPCUR_CN").toString());
						perhapsRelation_Map.put("REGCAP", REGCAP);
						if(perhapsRelation_Map.get("UNISCID")!=null && !"".equals(perhapsRelation_Map.get("UNISCID"))){
							perhapsRelation_Map.put("REGNO", perhapsRelation_Map.get("UNISCID"));
						}
						personnel_perhapsRelation3.set(i, perhapsRelation_Map);
					}else{
						personnel_perhapsRelation3.remove(i);  
					}
				}
				document.add(MakePDF.MyTable(personnel_perhapsRelation3, sort, name,w4,1));
			}
			if(select.lastIndexOf("股东对外投资信息")>=0){
				// 股东对外投资信息
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"股东对外投资信息");

				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"股东担任法定代表人的企业信息");
				List<Map<String, Object>> shareholder_perhapsRelation1 = (List<Map<String, Object>>) perhapsRelation.get(9);
				sort = "NAME,ENTNAME,REGNO,ESTDATE,ENTTYPE_CN,REGSTATE_CN,REGCAP";// 对Map的值排序
				name = "姓名,企业名称,统一社会信用代码/注册号,成立日期,企业类型,登记状态,注册资本";
				for (int i = 0; i < shareholder_perhapsRelation1.size(); i++) {
					Map<String, Object>  perhapsRelation_Map=shareholder_perhapsRelation1.get(i);
					if(perhapsRelation_Map.get("ENTNAME")!=null && !"".equals(perhapsRelation_Map.get("ENTNAME"))){
						String REGCAP=perhapsRelation_Map.get("REGCAP") == null ? "0" : StringUtil.subZeroAndDot(perhapsRelation_Map.get("REGCAP").toString());
						REGCAP+="万"+ (perhapsRelation_Map.get("REGCAPCUR_CN")==null ? "": perhapsRelation_Map.get("REGCAPCUR_CN").toString());
						perhapsRelation_Map.put("REGCAP", REGCAP);
						if(perhapsRelation_Map.get("UNISCID")!=null && !"".equals(perhapsRelation_Map.get("UNISCID"))){
							perhapsRelation_Map.put("REGNO", perhapsRelation_Map.get("UNISCID"));
						}
						shareholder_perhapsRelation1.set(i, perhapsRelation_Map);
					}else{
						shareholder_perhapsRelation1.remove(i);  
					}
				}
				document.add(MakePDF.MyTable(shareholder_perhapsRelation1, sort, name, w3,1));


				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"股东任职的企业信息");
				List<Map<String, Object>> shareholder_perhapsRelation2 = (List<Map<String, Object>>) perhapsRelation.get(10);
				sort = "NAME,ENTNAME,REGNO,ESTDATE,ENTTYPE_CN,REGSTATE_CN,REGCAP,POSITION_CN";// 对Map的值排序
				name = "姓名,企业名称,统一社会信用代码/注册号,成立日期,企业类型,登记状态,注册资本,职务";
				for (int i = 0; i < shareholder_perhapsRelation2.size(); i++) {
					Map<String, Object>  perhapsRelation_Map=shareholder_perhapsRelation2.get(i);
					if(perhapsRelation_Map.get("ENTNAME")!=null && !"".equals(perhapsRelation_Map.get("ENTNAME"))){
						String REGCAP=perhapsRelation_Map.get("REGCAP") == null ? "0" : StringUtil.subZeroAndDot(perhapsRelation_Map.get("REGCAP").toString());
						REGCAP+="万"+ (perhapsRelation_Map.get("REGCAPCUR_CN")==null ? "": perhapsRelation_Map.get("REGCAPCUR_CN").toString());
						perhapsRelation_Map.put("REGCAP", REGCAP);
						if(perhapsRelation_Map.get("UNISCID")!=null && !"".equals(perhapsRelation_Map.get("UNISCID"))){
							perhapsRelation_Map.put("REGNO", perhapsRelation_Map.get("UNISCID"));
						}
						shareholder_perhapsRelation2.set(i, perhapsRelation_Map);
					}else{
						shareholder_perhapsRelation2.remove(i);  
					}
				}
				document.add(MakePDF.MyTable(shareholder_perhapsRelation2, sort, name, w4,1));



				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"股东对外投资的企业信息");
				List<Map<String, Object>> shareholder_perhapsRelation3 = (List<Map<String, Object>>) perhapsRelation.get(11);
				sort = "NAME,ENTNAME,REGNO,ESTDATE,ENTTYPE_CN,REGSTATE_CN,REGCAP,POSITION_CN";// 对Map的值排序
				name = "姓名,企业名称,统一社会信用代码/注册号,成立日期,企业类型,登记状态,注册资本,投资人类型";
				for (int i = 0; i < shareholder_perhapsRelation3.size(); i++) {
					Map<String, Object>  perhapsRelation_Map=shareholder_perhapsRelation3.get(i);
					if(perhapsRelation_Map.get("ENTNAME")!=null && !"".equals(perhapsRelation_Map.get("ENTNAME"))){
						String REGCAP=perhapsRelation_Map.get("REGCAP") == null ? "0" : StringUtil.subZeroAndDot(perhapsRelation_Map.get("REGCAP").toString());
						REGCAP+="万"+ (perhapsRelation_Map.get("REGCAPCUR_CN")==null ? "": perhapsRelation_Map.get("REGCAPCUR_CN").toString());
						perhapsRelation_Map.put("REGCAP", REGCAP);
						if(perhapsRelation_Map.get("UNISCID")!=null && !"".equals(perhapsRelation_Map.get("UNISCID"))){
							perhapsRelation_Map.put("REGNO", perhapsRelation_Map.get("UNISCID"));
						}
						shareholder_perhapsRelation3.set(i, perhapsRelation_Map);
					}else{
						shareholder_perhapsRelation3.remove(i);  
					}
				}
				document.add(MakePDF.MyTable(shareholder_perhapsRelation3, sort, name,w4,1));
			}
			PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,1,"司法信息");
			Map<String,Object> justiceInfo_Map = Connect.sendConnectByPdToMap("Interface/courtcaseInterface/queryJusticeInfo.do", pd,"post");
			//判断查询的结果集是否为空
			if(Verification.StatusIsSuccess(justiceInfo_Map)){    
				Map<String,Object> dataMap = (Map<String, Object>) justiceInfo_Map.get("data");
				//司法文书信息
				List<Map<String,Object>> judgmentInfoList = (List<Map<String,Object>>) dataMap.get("judgmentInfo");
				//失信被执行人信息
				List<Map<String,Object>> courtcaseInfoList = (List<Map<String,Object>>) dataMap.get("courtcaseInfo");
				//股权冻结信息
				List<Map<String,Object>> sfInfoList = (List<Map<String,Object>>) dataMap.get("sfInfo");
				//股权变更信息
				List<Map<String,Object>> sfAlterList = (List<Map<String,Object>>) dataMap.get("sfAlter");
				if(select.lastIndexOf("司法文书信息")>=0){
					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"司法文书信息");
					sort = "CASENUM,LITIGATIONAMOUNT,STATE,BEFOREDEPARTNAME";// 对Map的值排序
					name = "案件数量/被告,诉讼数量/原告,状态,更新时间";
					float[] judgmentInfoFloat = { 0.3f, 0.3f, 0.2f, 0.2f};
					for(Map<String,Object> m:judgmentInfoList){
						if(StringUtil.isNotEmpty(m.get("STATE").toString()) ){
							if(m.get("STATE").toString().equals("0") ){
								m.put("STATE", "正常");
							}else if(m.get("STATE").toString().equals("1") ){
								m.put("STATE", "失效");
							}
						}
					}
					document.add(MakePDF.MyTable(judgmentInfoList, sort, name, judgmentInfoFloat,1));
				}
				if(select.lastIndexOf("失信被执行人信息")>=0){
					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"失信被执行人信息");
					sort = "COURT_NAME,REG_DATE,GIST_CID,PERFORMANCE,DISREPUT_TYPE_NAME";// 对Map的值排序
					name = "执行法院,立案时间,执行依据文号,被执行人的履行情况,类型名称";
					float[] courtcaseInfoFloat = { 0.2f, 0.2f, 0.2f,0.2f, 0.2f};
					document.add(MakePDF.MyTable(courtcaseInfoList, sort, name, courtcaseInfoFloat,1)); 
				}

				if(select.lastIndexOf("股权冻结信息")>=0){
					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"股权冻结信息");
					sort = "FROAUTH,INVTYPE_CN,FROAM,REGCAPCUR_CN,EXECUTEITEM_CN,FROFROM,FROTO";// 对Map的值排序
					name = "执行法院,被执行人,股权数额,币种,执行事项,冻结期限自,冻结期限至";
					float[] sfInfoFloat = { 0.15f, 0.15f, 0.1f,0.1f, 0.2f,0.15f, 0.15f};
					document.add(MakePDF.MyTable(sfInfoList, sort, name, sfInfoFloat,1));
				}
				if(select.lastIndexOf("股权变更信息")>=0){
					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"股权变更信息");
					sort = "FROAUTH,INV,ALIEN,FROAM";// 对Map的值排序
					name = "执行法院,被执行人,受让人,股权数额";
					float[] sfAlterFloat = { 0.3f, 0.25f, 0.25f,0.2f};
					document.add(MakePDF.MyTable(sfAlterList, sort, name, sfAlterFloat,1));
				}
			}
			if (select.lastIndexOf("失信被执行人信息")>=0) { // 失信被执行人信息
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"失信被执行人信息");
				List getForensic = courtcaseService.queryJusticeInfo(pd);
				List<Map<String, Object>> dishonestList = (List<Map<String, Object>>) getForensic.get(1);
				sort = "COURT_NAME,AREA_NAME,GIST_CID,REG_DATE,CASE_CODE,GIST_UNIT,DUTY,PERFORMANCE,PUBLISH_DATE";// 对Map的值排序
				name = "执行法院,省份,执行依据文号,立案时间,案号,作出执行依据单位,生效法律文书确定的义务,被执行人的履行情况,发布时间";
				float[] v = { 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.2f, 0.1f, 0.1f };
				document.add(MakePDF.MyTable(dishonestList, sort, name, v,1));
			}
			if (select.lastIndexOf("法院判决文书信息")>=0) {// 法院判决文书信息
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"法院判决文书信息");
				List getForensic = courtcaseService.queryJusticeInfo(pd);
				List<Map<String, Object>> judgeList = (List<Map<String, Object>>) getForensic.get(0);
				sort = "ENTNAME,CASENUM,LITIGATIONAMOUNT,SENTENCECONMENT,SENTENCEDATE";// 对Map的值排序
				name = "企业名称,案号 ,判决金额,判决结果,日期";
				float[] v2 = { 0.2f, 0.2f, 0.2f, 0.3f, 0.1f };
				document.add(MakePDF.MyTable(judgeList, sort, name, v2,1));
			}
			PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,1,"风险信息");
			if(select.lastIndexOf("荣誉信息")>=0){
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"荣誉信息");
				Map<String,Object> resultMap = Connect.sendConnectByPdToMap("Interface/honorInterface/queryHonorInfo.do", pd,"post");
				//判断查询的结果集是否为空
				if(Verification.StatusIsSuccess(resultMap)){    
					Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
					List<Map<String,Object>> entList = (List<Map<String,Object>>) dataMap.get("honorInfo");
					sort = "HONORNAME,HONORCONTENT,ORGAN,ANNUAL";// 对Map的值排序
					name = "荣誉名称,荣誉内容（类型）,机关,年度";
					float[] honorFloat = { 0.25f, 0.25f, 0.25f, 0.25f};
					document.add(MakePDF.MyTable(entList, sort, name, honorFloat,1));
				}
			}
			if (select.lastIndexOf("经营异常信息")>=0) {// 获取经营异常信息
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"经营异常信息");
				List<Map<String, Object>> anomaliesInfoList = anomaliesService.queryAnomaliesInfo(pd);
				sort = "SPECAUSE_CN,REMEXCPRES_CN,ABNTIME,REMDATE,ORGAN_NAME";// 对Map的值排序
				name = "列入经营异常原因,移出经营异常原因,列入日期,移出日期,作出决定机关";
				float[] v5 = { 0.3f, 0.3f, 0.1f, 0.1f, 0.2f };
				document.add(MakePDF.MyTable(anomaliesInfoList, sort, name, v5,1));
			} 
			if (select.lastIndexOf("股权出质信息")>=0) {// 股权出质信息
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"股权出质信息");
				List pledgeInfoList = pledgeService.queryPledgeInfo(pd);
				sort = "EQUITYNO,PLEDGOR,IMPAM,IMPORG,EQUPLEDATE";// 对Map的值排序
				name = "股权登记编号,出质人,出质股权数,质权人,登记日期";
				float[] v3 = { 0.3f, 0.15f, 0.2f, 0.15f, 0.2f };
				document.add(MakePDF.MyTable(pledgeInfoList, sort, name, v3,1));
			} 
			if (select.lastIndexOf("动产抵押信息")>=0) {// 动产抵押
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"动产抵押信息");
				List mortregList = mortregService.queryMortregInfo(pd);
				sort = "MORREGCNO,REGORG_CN,PRICLASECAM,REGIDATE";// 对Map的值排序
				name = "登记编号,登记机关,债权数额,登记日期";
				float[] v4 = { 0.3f, 0.3f, 0.2f, 0.2f };
				document.add(MakePDF.MyTable(mortregList, sort, name, v4,1));
			}
			if(select.lastIndexOf("行政处罚信息")>=0){
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"行政处罚信息");
				Map<String,Object> resultMap = Connect.sendConnectByPdToMap("Interface/otCaseInfoInterface/queryOtCaseInfo.do", pd,"post");
				//判断查询的结果集是否为空
				if(Verification.StatusIsSuccess(resultMap)){    
					Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
					List<Map<String,Object>> caseinfoList = (List<Map<String,Object>>) dataMap.get("otCaseInfo");
					sort = "PENDECNO,PENDECCONTENT,PENAM,FORFAM,JUDAUTH,PENDECISSDATE";// 对Map的值排序
					name = "处罚决定书文号,处罚决定书内容,罚款金额,没收金额,作出行政处罚决定机关名称,作出处罚决定书日期";
					float[] caseinfoFloat = {0.15f,0.2f,0.15f,0.15f,0.2f,0.15f };
					for(Map<String,Object> m:caseinfoList){
						if(StringUtil.isNotEmpty(m.get("PENAM").toString()) ){
							m.put("PENAM", getString(m.get("PENAM").toString(), REGCAPCUR_CN));
						}
						if(StringUtil.isNotEmpty(m.get("FORFAM").toString()) ){
							m.put("FORFAM", getString(m.get("FORFAM").toString(), REGCAPCUR_CN));
						}
					}
					document.add(MakePDF.MyTable(caseinfoList, sort, name,caseinfoFloat,1));
				}
			}
			if(select.lastIndexOf("行政许可信息")>=0){//查询行政审批  就是行政许可
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"行政许可信息");
				Map<String,Object> resultMap = Connect.sendConnectByPdToMap("Interface/certificateInterface/queryCertificateInfo.do", pd,"post");
				//判断查询的结果集是否为空
				if(Verification.StatusIsSuccess(resultMap)){
					Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
					//行政许可信息
					List<Map<String,Object>> lic_CertificateList = (List<Map<String,Object>>) dataMap.get("lic_Certificate");
					//其他信息
					List<Map<String,Object>> ot_PermitList = (List<Map<String,Object>>) dataMap.get("ot_Permit");

					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"行政许可信息");
					sort = "LICNO,LICNAME,VALFROM,VALTO,LICANTH";// 对Map的值排序
					name = "许可文件编号,许可文件名称,有效期自,有效期至,许可机关";
					float[] lic_CertificateFloat = { 0.2f, 0.2f, 0.2f,0.2f, 0.2f};
					document.add(MakePDF.MyTable(lic_CertificateList, sort, name, lic_CertificateFloat,1));

					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"其他行政许可信息");
					sort = "CERFITICATION_DATE,LICITEM,TYPE";// 对Map的值排序
					name = "发证日期,许可内容,状态";
					float[] ot_PermitFloat = { 0.3f, 0.4f, 0.3f};
					document.add(MakePDF.MyTable(ot_PermitList, sort, name, ot_PermitFloat,1));

				}
			}
			if(select.lastIndexOf("预警信息")>=0){
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"预警信息");
				Map<String,Object> resultMap = Connect.sendConnectByPdToMap("Interface/warningInterface/queryWarninginfo.do", pd,"post");
				//判断查询的结果集是否为空
				if(Verification.StatusIsSuccess(resultMap)){    
					Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
					//责令改正预警
					List<Map<String,Object>> orderCorrectionList = (List<Map<String,Object>>) dataMap.get("orderCorrection");
					//欠贷信息预警
					List<Map<String,Object>> oweLoanList = (List<Map<String,Object>>) dataMap.get("oweLoan");
					//证照过期预警
					List<Map<String,Object>> licenseExpiredList = (List<Map<String,Object>>) dataMap.get("licenseExpired");
					//欠薪信息预警
					List<Map<String,Object>> oweSalaryList = (List<Map<String,Object>>) dataMap.get("oweSalary");
					//欠税信息预警
					List<Map<String,Object>> oweTaxList = (List<Map<String,Object>>) dataMap.get("oweTax");
					//证照到期预警
					List<Map<String,Object>> licenseExpiresList = (List<Map<String,Object>>) dataMap.get("licenseExpires");

					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"责令改正预警");
					sort = "WARNDATE,WARNAMOUNT,WARNSTATUS,ORGAN,WARNCONTENT";// 对Map的值排序
					name = "预警日期,预警金额,预警状态,预警机关,预警内容";
					float[] orderCorrectionFloat = { 0.2f, 0.2f, 0.2f, 0.2f,0.2f};
					document.add(MakePDF.MyTable(orderCorrectionList, sort, name, orderCorrectionFloat,1));

					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"欠贷信息预警");
					document.add(MakePDF.MyTable(oweLoanList, sort, name, orderCorrectionFloat,1));

					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"证照过期预警");
					document.add(MakePDF.MyTable(licenseExpiredList, sort, name, orderCorrectionFloat,1));

					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"欠薪信息预警");
					document.add(MakePDF.MyTable(oweSalaryList, sort, name, orderCorrectionFloat,1));

					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"欠税信息预警");
					document.add(MakePDF.MyTable(oweTaxList, sort, name, orderCorrectionFloat,1));

					PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,3,"证照到期预警");
					document.add(MakePDF.MyTable(licenseExpiresList, sort, name, orderCorrectionFloat,1));
				}
			}
			if(select.lastIndexOf("广告资质")>=0){
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"广告资质信息");
				Map<String,Object> resultMap = Connect.sendConnectByPdToMap("Interface/advertisingInterface/queryAdvertisingInfo.do", pd,"post");
				//判断查询的结果集是否为空
				if(Verification.StatusIsSuccess(resultMap)){    
					Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
					List<Map<String,Object>> advertisingInfoList = (List<Map<String,Object>>) dataMap.get("advertisingInfo");
					sort = "C_LEVEL,CATEGORY,IDENTIFYDATE,VALFORM,VALFTO,IDENTIFYORGANS,CONTENT";// 对Map的值排序
					name = "广告资质级别,类别,认定时间,有效期自 起,有效期至 止,认定机关,内容";
					float[] advertisingInfoFloat = { 0.1f, 0.1f, 0.1f, 0.1f,0.1f,0.25f,0.25f};
					document.add(MakePDF.MyTable(advertisingInfoList, sort, name, advertisingInfoFloat,1));
				}
			}
			if(select.lastIndexOf("守合同重信用信息")>=0){
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"守合同重信用信息");
				Map<String,Object> resultMap = Connect.sendConnectByPdToMap("Interface/kchcInfoInterface/queryKchcInfo.do", pd,"post");
				//判断查询的结果集是否为空
				if(Verification.StatusIsSuccess(resultMap)){
					Map<String,Object> dataMap = (Map<String, Object>) resultMap.get("data");
					List<Map<String,Object>> kchcInfoList = (List<Map<String,Object>>) dataMap.get("kchcInfo");
					sort = "CONTENT,IDENTIFYDATE,IDENTIFYORGANS,STATE";// 对Map的值排序
					name = "内容,认定日期,认定机关,状态";
					float[] kchcInfoFloat = {0.4f,0.2f,0.2f,0.2f};
					for(Map<String,Object> m:kchcInfoList){
						if(StringUtil.isNotEmpty(m.get("STATE").toString()) ){
							if(m.get("STATE").toString().equals("1") ){
								m.put("STATE", "有效");
							}else if(m.get("STATE").toString().equals("2") ){
								m.put("STATE", "无效");
							}
						}
					}
					document.add(MakePDF.MyTable(kchcInfoList, sort, name, kchcInfoFloat,1));
				}
			}
			if (select.lastIndexOf("年报信息")>=0) {// 著作权
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,1,"年报信息");
				setYearData(document,writer,CURRENTINDEX,baseFont,pd);
			}
			PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,1,"知识产权信息");
			if (select.lastIndexOf("著作权信息")>=0) {// 著作权
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"著作权信息");
				List workPatentInfoList = copyrightService.queryWorkCopyrightInfo(pd);
				sort = "REGISTERID,WORKCLASS,WORKNAME,REGISTERDATA";// 对Map的值排序
				name = "登记号,作品类别,作品名称,登记日期";
				float[] u = { 0.3f, 0.2f, 0.3f, 0.2f };
				document.add(MakePDF.MyTable(workPatentInfoList, sort, name, u,1));
			} 
			if (select.lastIndexOf("软件著作权信息")>=0) { // 软件著作权
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"软件著作权信息");
				List copyrightInfoList = copyrightService.queryCopyrightInfo(pd);
				sort = "REGISTERID,CATEGORYID,SOFTWARENAME,SOFTWARESHORT,VERSIONNUMBER,OWNERNAME,REGISTERDATA";// 对Map的值排序
				name = "登记号,分类号,软件全称,软件简称,版本号,著作权人,登记日期";
				float[] f1 = { 0.1f, 0.1f, 0.2f, 0.18f, 0.1f, 0.2f, 0.12f };
				document.add(MakePDF.MyTable(copyrightInfoList, sort, name, f1,1));
			} 
			if (select.lastIndexOf("专利信息")>=0) { // 专利信息
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"专利信息");
				List PatentInfoList = patentService.queryPatentinfo(pd);
				sort = "ABSTRACTGRAPH,PATENTNAME,PATENTTYPE,INVENTOR,ADATE,DETAILINFO";// 对Map的值排序
				name = "摘要附图,专利名称,专利类型,发明人,授权公布日期,详细信息";
				float[] u2 = { 0.2f,0.2f, 0.15f, 0.15f, 0.15f, 0.15f };
				document.add(MakePDF.MyTable(PatentInfoList, sort, name, u2,new int[]{2},1));
			}
			if (select.lastIndexOf("商标信息")>=0) {// 商标信息
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"商标信息");
				List brandInfoList = brandService.queryBrandInfo(pd);
				sort = "BRANDIMG,BRANDNAME,BRANDSTAUTS,REGCORE,APPLICATIONDATE,LIFESPAN";// 对Map的值排序
				name = "商标图,商标名称,商标状态,商标注册号,申请日期,使用期限";
				float[] u3 = { 0.2f,0.2f, 0.15f, 0.15f, 0.15f, 0.15f };
				document.add(MakePDF.MyTable(brandInfoList, sort, name, u3,new int[]{2},1));
			} 
			PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,1,"经营信息");
			if (select.lastIndexOf("招投标信息")>=0) { // 招投标
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"招投标信息");
				List biddingInfList = biddingService.queryBiddingInfo(pd);
				sort = "TITLE,LOCAL_AREA,INDUSTRY,RELEASE_DATE";// 对Map的值排序
				name = "标题,所属区域,项目分类,发布时间";
				float[] f2 = { 0.4f, 0.25f, 0.15f, 0.2f };
				document.add(MakePDF.MyTable(biddingInfList, sort, name, f2,1));
			} 
			if (select.lastIndexOf("招聘信息")>=0) {// 招聘信息
				PDFUtils.setCatalogueTitle(document, writer,CURRENTINDEX,2,"招聘信息");
				List jobInfoList = jobInfoService.queryJobInfo(pd);
				sort = "POSITION,SITE,SALARY,RELEASE_DATE";// 对Map的值排序
				name = "职位名称,工作地点,薪资,发布时间";
				float[] f3 = { 0.4f, 0.2f, 0.25f, 0.15f };
				document.add(MakePDF.MyTable(jobInfoList, sort, name, f3,1));
			}
			//设置最后两页内容
			document.newPage();
			paragraph = new Paragraph(" ");
			document.add(paragraph);

			paragraph = new Paragraph("—————————本报告完—————————",new Font(baseFont, 18, Font.BOLD, Color.decode("#000000")));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setSpacingBefore((document.bottom()+ document.top()-200)/2);
			document.add(paragraph);
			pd.put("toUrl", Const.CONNECT_PATH_JX+"/Interface/MakePdfInterface/downLoadPDF?fileName="+pd.getString("entname")+".pdf");
			java.awt.Image im=CreateCode.CreateQRCode(request, pd);
			image = Image.getInstance(im, Color.white);
			image.setAlignment(Image.MIDDLE); 
			image.scaleAbsoluteHeight(100);
			image.scaleAbsoluteWidth(100);
			//image.setAbsolutePosition((document.left()+ document.right()-image.getPlainWidth())/2, (document.bottom()+ document.top())/2); 
			document.add(image);
			paragraph = new Paragraph("扫一扫，下载本报告", new Font(baseFont, 12, Font.NORMAL, Color.decode("#000000")));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);


			document.newPage();
			paragraph = new Paragraph(" ");
			document.add(paragraph);

			image = Image.getInstance(request.getSession().getServletContext().getRealPath("static/images/makepdf/logo.png"));;
			image.setAlignment(Image.ALIGN_CENTER); 
			image.scaleAbsoluteHeight(60);
			image.scaleAbsoluteWidth(60);
			image.setAbsolutePosition((document.left()+ document.right()-image.getPlainWidth())/2, (document.bottom()+ document.top()+60)/2); 
			document.add(image);

			paragraph = new Paragraph("以诚立信  以信致远", new Font(baseFont, 16, Font.NORMAL, Color.decode("#000000")));
			paragraph.setSpacingBefore((document.bottom()+ document.top()-150)/2);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);

			paragraph = new Paragraph("江西瑞臻企业征信服务有限公司", new Font(baseFont, 16, Font.NORMAL, Color.decode("#000000")));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);

			paragraph = new Paragraph("全国客户服务热线400-151-1315", new Font(baseFont, 16, Font.NORMAL, Color.decode("#000000")));
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);

			document.close();
			pd.put("catalogue_list",CURRENTINDEX.get("CATALOGUE_LIST"));
			makePdfCatalogue(request, pd);

			//记录报告发送历史
			pd.put("uuid", UuidUtil.get32UUID());
			pd.put("ip", this.getRemortIP());
			if (pd.get("memberId") == null || pd.get("memberId") == "" || pd.get("memberId").equals("")) {
				pd.put("memberId", "游客");
			}
			reportService.insertReporthis(pd);
		} catch (Exception e) {
			logger.error(e.toString(),e);
			return "error";
		}
		return pd.getString("entname")+".pdf";
	}


	@SuppressWarnings("unchecked")
	public void makePdfCatalogue(HttpServletRequest request,PageData pd){
		try { 
			Document document = new Document(PageSize.A4, 36, 36, 65, 30);

			String fileName = pd.getString("entname") + "1.pdf";
			List<Map<String, Object>> catalogue_list = (List<Map<String, Object>>) pd.get("catalogue_list");//存放目录的模块名称和页码
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pd.getString("path") + File.separator +fileName));

			Rectangle rect = new Rectangle(36, 54, 559, 788);
			rect.setBorderColor(Color.black);
			writer.setBoxSize("art", rect);
			TableHeader header=new TableHeader(); 
			header.setOnStartPage(3);
			header.setImagePath(request.getSession().getServletContext().getRealPath("static/images/makepdf"));
			header.setEntname(pd.getString("entname"));
			writer.setPageEvent(header);


			BaseFont baseFont = MakePDF.getBaseFontYh();  
			Phrase phrase;
			Image image;
			Paragraph paragraph; 

			/* 页眉页脚的设置一定要在open前设置好 */
			document.open();

			//设置第一页
			phrase=new Phrase("报告编号：瑞臻征信【2016】RZPJ-0018",new Font(baseFont,12, Font.NORMAL, Color.decode("#000")));
			ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_RIGHT, phrase,rect.getRight()+14, rect.getTop()+35, 0);

			image = Image.getInstance(request.getSession().getServletContext().getRealPath("static/images/makepdf/logoandname.png"));
			image.setAlignment(Image.MIDDLE); 
			image.scaleAbsolute(100,110);
			image.setAbsolutePosition(rect.getWidth()/2, rect.getTop()-200); 
			writer.getDirectContent().addImage(image); 

			image = Image.getInstance(request.getSession().getServletContext().getRealPath("static/images/makepdf/sincerity.png"));
			image.setAlignment(Image.MIDDLE); 
			image.scaleAbsolute(126,40);
			image.setAbsolutePosition(rect.getWidth()/2+rect.getLeft(), rect.getTop()-250); 
			writer.getDirectContent().addImage(image);

			image = Image.getInstance(request.getSession().getServletContext().getRealPath("static/images/makepdf/title.png"));
			image.setAlignment(Image.MIDDLE); 
			image.scaleAbsolute(400,55);
			image.setAbsolutePosition(rect.getLeft()+30, rect.getTop()-320); 
			writer.getDirectContent().addImage(image);

			phrase=new Phrase("报告企业："+pd.getString("entname"),new Font(baseFont,12, Font.NORMAL, Color.decode("#000")));
			ColumnText.showTextAligned(writer.getDirectContent(),
					Element.ALIGN_LEFT, phrase,rect.getWidth()/2-rect.getLeft(), rect.getTop()-370, 0);

			SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
			phrase=new Phrase("报告时间："+sdf.format(new Date()),new Font(baseFont,12, Font.NORMAL, Color.decode("#000")));
			ColumnText.showTextAligned(writer.getDirectContent(),
					Element.ALIGN_LEFT, phrase,rect.getWidth()/2-rect.getLeft(), rect.getTop()-400, 0);


			image = Image.getInstance(request.getSession().getServletContext().getRealPath("static/images/makepdf/address.png"));
			image.setAlignment(Image.MIDDLE); 
			image.scaleAbsolute(255,199);
			image.setAbsolutePosition(rect.getLeft(), rect.getBottom()); 
			writer.getDirectContent().addImage(image);


			//设置第二页
			document.newPage();
			document.add(setFiestPDF("报告说明",baseFont,18, Color.black,0,23,"center"));

			String desc1="1．本报告依据截止报告时间国家企业信用信息公示系统记录的信息生成。除企业自主申报信息标注外，报告中的信息均由相关报数机构和信息主体提供，瑞臻企业征信不保证其真实性和准确性，但承诺在信息整合、汇总、展示的全过程中保持客观、中立的地位。";
			String desc2="2．本报告中的身份信息、主要出资人信息、高管人员信息来源于信息主体在企业法人登记注册时及相关业务变更时所提供的相关资料。";
			String desc3="3．如无特别说明，本报告中的金额类数据项单位均为万元。";
			String desc4="4．如无特别说明，本报告中的金额类汇总数据项均为人民币计价。";
			String desc5="5．企业自主申报信息标注是企业在瑞臻企业征信平台自主申报的信用信息，平台对相关资料进行审核。";
			String desc6="6.本报告仅向信息主体提供，不得作为金融机构的授信依据，任何情况下，对由使用本报告所造成的损失，瑞臻企业征信不承担任何责任。请妥善保管，因保管不当造成信息泄露的，瑞臻征信不承担相关责任。";
			String desc7="7.信息主体有权对本报告中的内容提出异议。如有异议，可联系瑞臻企业征信，提出异议申请。";
			String desc8="8. 全国客户服务热线400-151-1315（国家法定工作日9：00-17：00）。";
			document.add(setFiestPDF(desc1,baseFont,13, Color.black,5,5,""));
			document.add(setFiestPDF(desc2,baseFont,13, Color.black,5,5,""));
			document.add(setFiestPDF(desc3,baseFont,13, Color.black,5,5,""));
			document.add(setFiestPDF(desc4,baseFont,13, Color.black,5,5,""));
			document.add(setFiestPDF(desc5,baseFont,13, Color.black,5,5,""));
			document.add(setFiestPDF(desc6,baseFont,13, Color.black,5,5,""));
			document.add(setFiestPDF(desc7,baseFont,13, Color.black,5,5,""));
			document.add(setFiestPDF(desc8,baseFont,13, Color.black,5,5,""));
			document.newPage();
			String str=".................";
			for (int j = 0; j < 3; j++) {
				str+=str;
			}
			document.add(setFiestPDF("目录",baseFont,18, Color.black,-30,23,"center"));
			for (int i = 0; i < catalogue_list.size(); i++) {
				String cataloguename=catalogue_list.get(i).get("modularName").toString();
				cataloguename+=str.substring(0,(str.length()-cataloguename.length()*4)+(Integer.parseInt(catalogue_list.get(i).get("titleType").toString())-1)*13)+catalogue_list.get(i).get("pageNumber");
				paragraph = new Paragraph(cataloguename, new Font(baseFont, 15-Integer.parseInt(catalogue_list.get(i).get("titleType").toString()), Font.NORMAL, Color.decode("#000")));
				paragraph.setIndentationLeft(10*Integer.parseInt(catalogue_list.get(i).get("titleType").toString()));
				paragraph.setSpacingBefore(5);
				paragraph.setAlignment(Element.ALIGN_LEFT);// 设置标题居中
				document.add(paragraph);
			}
			document.close();
			String files[] = new String[] {pd.getString("path")+ File.separator +pd.getString("entname")+"1.pdf",pd.getString("path")+ File.separator +pd.getString("entname")+"2.pdf"};   
			PdfUtil.mergePdfFiles(files,pd.getString("path") + File.separator +pd.getString("entname")+".pdf", 1);
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
	}
	// 替换字符串中的数字 金额 身份证号码 出生年月
	public String getString(String context, String REGCAPCUR_CN) {
		if(StringUtil.isEmpty(context)){
			return "";
		}else{
			boolean result = context.matches("[0-9]+");
			context=StringUtil.replace(context);
			// 判断是否为纯数字
			if (result == true) {
				context +=  REGCAPCUR_CN;
			} 
			return context;  
		}

	}

	/**
	 * 
	 * @descript (设置第一页封面)
	 * @author 李文海
	 * @since 2016年10月24日下午5:47:19
	 * @param str  字符串
	 * @param baseFont	字体样式，类型
	 * @param size		字体大小	
	 * @param color		字体颜色
	 * @param margin_top	上边距
	 * @param margin_buttom	下边距
	 * @param align   居中 或者 设置左边距
	 * @return
	 */
	public static Paragraph setFiestPDF(String str,BaseFont baseFont,int size,Color color,int margin_top,int margin_buttom,String align){
		Paragraph paragraph = new Paragraph(30,str, new Font(baseFont, size, align=="center" ? Font.BOLD:Font.NORMAL, color));
		if("center".equals(align)){
			paragraph.setAlignment(Element.ALIGN_CENTER);//居中
		}else if("margin_left".equals(align)){
			paragraph.setIndentationLeft(163);//左边距
		}else{

		}
		paragraph.setSpacingBefore(margin_top); //上边距
		paragraph.setSpacingAfter(margin_buttom);// 下边距
		return paragraph;
	}

	/**
	 * 
	 * @descript (转换object中的值)
	 * @author 李海涛
	 * @since 2016年10月10日下午1:23:25
	 * @param obj 
	 * @return
	 */
	public String changeObject(Object obj) {
		try {
			if(obj == null){
				return "无";  
			} else if("1".equals(obj.toString())){
				return "有";
			}else{
				return "无";
			}
		} catch (Exception e) {
			return "无";
		}
	}
	/**
	 * 
	 * @descript (判断是否公示)
	 * @author 李海涛
	 * @since 2016年10月9日下午5:18:11
	 * @param obj1 公示的数据
	 * @param obj2 是否公示的标识  1或2 或空
	 * @return
	 */
	public String getDis(Object obj1, Object obj2) {
		String str1=obj1 == null ? "" : obj1.toString();
		String str2=obj2 == null ? "" : obj2.toString();
		// 当str2==2时企业不公示 ,为空或者为1时 公示
		if (StringUtil.isEmpty(str2) || "1".equals(str2)) {
			str1 = StringUtil.replace(str1);
		}else{
			str1 = "企业选择不公示"; 
		}
		return str1;
	}




	// 判断是否公示
	public String getDis(String str, String strDis, String REGCAPCUR_CN) {
		if (StringUtil.isEmpty(str)) {
			str = "0";
		}
		str = StringUtil.replace(str);
		str = str + REGCAPCUR_CN;
		// 当strDis==2时企业不公示 ,为空或者为1时 公示
		if (StringUtil.isNotEmpty(strDis)) {
			if (strDis.equals("2")) {
				str = "企业选择不公示";
			}
		}
		return str;
	}
	/**
	 * 
	 * @descript (根据企业名称下载pdf)
	 * @author 李海涛
	 * @since 2016年9月20日下午1:21:49
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/downLoadPDF")
	public void downLoadBusinessCard(HttpServletRequest request, HttpServletResponse response) {
		String filename = request.getParameter("fileName");
		// 根据文件名称获取文件，写出输入流
		String path = request.getSession().getServletContext().getRealPath("/uploadfiles/qrcode") + "\\";
		path = path + filename;
		File file = new File(path);

		if (file.exists()) {
			try {
				response.reset();
				response.setHeader("Content-Disposition","attachment; filename=" + new String(filename.getBytes("UTF-8"), "iso-8859-1"));
				response.addHeader("Content-Length", "" + file.length());
				response.setContentType("application/octet-stream;charset=UTF-8");
				FileInputStream is = new FileInputStream(file);
				ServletOutputStream out = response.getOutputStream();
				byte[] b = new byte[1024];
				int len = -1;
				while ((len = is.read(b)) != -1) {
					out.write(b, 0, len);
				}
				out.close();
				is.close();
			} catch (FileNotFoundException ex) {
				logger.error(ex.toString(),ex);
			} catch (IOException ex) {
				logger.error(ex.toString(),ex);
			}
		} else {
			// 输出默认图片
		}
	}

	@RequestMapping(value = "/sendPdfByEmail", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendPdfByEmail(HttpServletRequest request) throws Exception {
		PageData pd = this.getPageData();
		String fileName = pd.getString("fileName");
		//判断pdf名字是否包含中文 ，因为ios传过来的是 编码的 即 乱码  所以没有中文
		if(isContainsChinese(fileName)==false){
			//当fileName不包含中文时就是ios传过来的值  就需要编码
			fileName=URLDecoder.decode(fileName,"UTF-8");
		}
		List<String> sendTo =new ArrayList<String>();
		sendTo.add(pd.getString("sendTo"));
		//设置附件
		String attachPath = this.getRequest().getSession().getServletContext().getRealPath("/uploadfiles/qrcode") + File.separator;
		attachPath = attachPath + fileName;

		String flag=SendMailUtil.sendHtmlEmail(sendTo, fileName.replaceAll(".pdf", "征信报告"), fileName.replaceAll(".pdf", "征信报告"), attachPath);
		return flag;
	}


	public  boolean isContainsChinese(String str){
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())    {
			flg = true;
		}
		return flg;
	}

	/**
	 * 
	 * @descript (生成年报)
	 * @author 李文海
	 * @since 2016年10月27日上午10:40:51
	 * @param document
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	public  void setYearData(Document document,PdfWriter writer,Map<String,Object> CURRENTINDEX,BaseFont baseFont,PageData pd)throws Exception{
		List<Map<String,Object>> yearList 	 			= new ArrayList<Map<String,Object>>(); //年度
		List<Map<String,Object>> baseinfoList 			= new ArrayList<Map<String,Object>>();//年报基本信息
		List<Map<String,Object>> webList 				= new ArrayList<Map<String,Object>>();//网站或者网店信息
		List<Map<String,Object>> licenceList 			= new ArrayList<Map<String,Object>>();//行政许可信息
		List<Map<String,Object>> moneyList 				= new ArrayList<Map<String,Object>>();// 资产状况信息 
		List<Map<String,Object>> updateList 			= new ArrayList<Map<String,Object>>();// 修改信息
		List<Map<String,Object>> branchList 			= new ArrayList<Map<String,Object>>();//分支机构信息
		List<Map<String,Object>> subCapitalList 		= new ArrayList<Map<String,Object>>();//股东及出资
		List<Map<String,Object>> forinvestMentList 		= new ArrayList<Map<String,Object>>();//对外投资信息
		List<Map<String,Object>> forguaranteeList 		= new ArrayList<Map<String,Object>>();//对外提供保证担保信息
		List<Map<String,Object>> alterstockInfoList 	= new ArrayList<Map<String,Object>>(); //股权变更信息
		String priptype = "";

		List GetAllMyselferinfo = myselferservice.queryAllMyselferinfo(pd);
		List<Map<String,Object>> anbaseInfo=(List<Map<String, Object>>)GetAllMyselferinfo.get(0);
		for (int i = 0; i < anbaseInfo.size(); i++) {
			priptype = pd.getString("priptype");
			//个体年报
			pd.put("ancheId", anbaseInfo.get(i).get("ANCHEID").toString());
			if(("9500").equals(pd.getString("priptype"))){
				//获取个体年报信息
				List GetAnnualReport = annualreportService.GetPBAnnualReport(pd);

				//添加年度 
				yearList.add(PDFUtils.getYear((i+1), anbaseInfo.get(i).get("ANCHEYEAR").toString(), anbaseInfo.get(i).get("ANCHEDATE").toString()));

				//添加年报基本信息  key :TRANAME,REGNO,NAME,TEL,FUNDAM,EMPNUM,ISWEB  name:企业名称,注册号,经营者,联系电话,资金数额,从业人数,是否有网站或网店
				Map<String,Object> annualReportPbBaseInfo = ListUtils.getListMap((List<Map<String, Object>>)GetAnnualReport.get(0), 0);
				annualReportPbBaseInfo.put("EMPNUM",getDis(annualReportPbBaseInfo.get("EMPNUM"), annualReportPbBaseInfo.get("EMPNUMDIS")));
				annualReportPbBaseInfo.put("VENDINC",getDis(annualReportPbBaseInfo.get("VENDINC"), annualReportPbBaseInfo.get("VENDINCDIS")));
				annualReportPbBaseInfo.put("RATGRO",getDis(annualReportPbBaseInfo.get("RATGRO"), annualReportPbBaseInfo.get("RATGRODIS")));
				annualReportPbBaseInfo.put("ISWEB",changeObject(annualReportPbBaseInfo.get("ISWEB")));
				baseinfoList.add(PDFUtils.getTableContent(annualReportPbBaseInfo,0));

				//网站或者网店信息  key:WEBTYPE,WEBSITNAME,WEBSITE  name:类型,名称,网址
				List<Map<String, Object>> websiteInfo = (List<Map<String, Object>>)GetAnnualReport.get(1);
				webList.add(PDFUtils.getTableContent(websiteInfo,1));

				//行政许可信息  key:LICNAME_CN,VALTO  name:许可文件名称,有效期至
				List<Map<String, Object>> licenceInfo = (List<Map<String, Object>>)GetAnnualReport.get(2);
				licenceList.add(PDFUtils.getTableContent(licenceInfo,1));

				//资产状况信息   key: VENDINC,RATGRO  name: 销售额或营业收入,纳税总额
				moneyList.add(PDFUtils.getTableContent(annualReportPbBaseInfo,0));

				//修改信息  key:ALITEM,ALTBE,ALTAF,ALTDATE  name:修改事项,修改前,修改后,修改日期
				List<Map<String, Object>> updateInfo = (List<Map<String, Object>>)GetAnnualReport.get(3);
				updateList.add(PDFUtils.getTableContent(updateInfo,1));

			} else  if(("9100").equals(pd.getString("priptype"))){
				//获取农专年报信息
				List GetAnnualReport = annualreportService.GetSFCAnnualReport(pd);

				//添加年度    key : index,year,date   name: 序号,年报年度,发布日期
				yearList.add(PDFUtils.getYear((i+1), anbaseInfo.get(i).get("ANCHEYEAR").toString(), anbaseInfo.get(i).get("ANCHEDATE").toString()));

				//添加年报基本信息  key :ENTNAME,REGNO,TEL,EMAIL,MEMNUM  name:合作社名称,注册号,联系电话,邮箱,成员人数
				Map<String,Object> annualReportSfcBaseInfo = ListUtils.getListMap((List<Map<String, Object>>)GetAnnualReport.get(0), 0);
				annualReportSfcBaseInfo.put("PRIYEASALES",getDis(annualReportSfcBaseInfo.get("PRIYEASALES"), annualReportSfcBaseInfo.get("PRIYEASALESDIS")));
				annualReportSfcBaseInfo.put("RATGRO",getDis(annualReportSfcBaseInfo.get("RATGRO"), annualReportSfcBaseInfo.get("RATGRODIS")));
				annualReportSfcBaseInfo.put("PRIYEALOAN",getDis(annualReportSfcBaseInfo.get("PRIYEALOAN"), annualReportSfcBaseInfo.get("PRIYEALOANDIS")));
				annualReportSfcBaseInfo.put("PRIYEAPROFIT",getDis(annualReportSfcBaseInfo.get("PRIYEAPROFIT"), annualReportSfcBaseInfo.get("PRIYEAPROFITDIS")));
				annualReportSfcBaseInfo.put("PRIYEASUB",getDis(annualReportSfcBaseInfo.get("PRIYEASUB"), annualReportSfcBaseInfo.get("PRIYEASUBDIS")));
				annualReportSfcBaseInfo.put("MEMNUM", "共"+(annualReportSfcBaseInfo.get("MEMNUM") != null && annualReportSfcBaseInfo.get("MEMNUM") != "" ? annualReportSfcBaseInfo.get("MEMNUM"):"0")+
						"人，其中农民人数："+(annualReportSfcBaseInfo.get("FARNUM") != null && annualReportSfcBaseInfo.get("FARNUM") != "" ? annualReportSfcBaseInfo.get("FARNUM"):"0")+
						"。 本年度新增"+(annualReportSfcBaseInfo.get("ANNNEWMEMNUM") != null && annualReportSfcBaseInfo.get("ANNNEWMEMNUM") != "" ? annualReportSfcBaseInfo.get("ANNNEWMEMNUM"):"0")+
						"人，退出"+(annualReportSfcBaseInfo.get("ANNREDMEMNUM") != null && annualReportSfcBaseInfo.get("ANNREDMEMNUM") != "" ? annualReportSfcBaseInfo.get("ANNREDMEMNUM"):"0")+"人");
				baseinfoList.add(PDFUtils.getTableContent(annualReportSfcBaseInfo,0));

				//网站或者网店信息  key:WEBTYPE,WEBSITNAME,WEBSITE  name:类型,名称,网址
				List<Map<String, Object>> websiteInfo = (List<Map<String, Object>>)GetAnnualReport.get(1);
				webList.add(PDFUtils.getTableContent(websiteInfo,1));

				//行政许可信息  key:LICNAME_CN,VALTO  name:许可文件名称,有效期至
				List<Map<String, Object>> licenceInfo = (List<Map<String, Object>>)GetAnnualReport.get(2);
				licenceList.add(PDFUtils.getTableContent(licenceInfo,1));

				//分支机构信息  key:BRNAME,UNISCID  name:分支机构名称,统一社会信用代码
				List<Map<String, Object>> branchinfo = (List<Map<String, Object>>)GetAnnualReport.get(3);
				branchList.add(PDFUtils.getTableContent(branchinfo,1));

				//资产状况信息   key: PRIYEASALES,RATGRO,PRIYEALOAN  PRIYEAPROFIT,PRIYEASUB    name: 销售额营业总收入,纳税总额,金融贷款    盈余总额,获得政府扶持资金、补助
				moneyList.add(PDFUtils.getTableContent(annualReportSfcBaseInfo,0));

				//修改信息  key:ALITEM,ALTBE,ALTAF,ALTDATE  name:修改事项,修改前,修改后,修改日期
				List<Map<String, Object>> updateInfo = (List<Map<String, Object>>)GetAnnualReport.get(4);
				updateList.add(PDFUtils.getTableContent(updateInfo,1));
			}else{
				//获取企业年报信息
				List GetAnnualReport = annualreportService.GetENTAnnualReport(pd);

				//添加年度    key : index,year,date   name: 序号,年报年度,发布日期
				yearList.add(PDFUtils.getYear((i+1), anbaseInfo.get(i).get("ANCHEYEAR"), anbaseInfo.get(i).get("ANCHEDATE")));

				//添加年报基本信息  key :ENTNAME,REGNO,TEL,POSTALCODE,ADDR,EMAIL,ISCHANGE,BUSST_CN,ISWEB,ISLETTER,EMPNUM  name:企业名称,注册号,企业联系电话,邮政编码,企业通讯地址,电子邮箱,本年度是否发生股东股权转让,企业经营状态,是否有网站或网店,企业是否有投资信息或购买其他公司股权,从业人数
				Map<String,Object> annualReportENTBaseInfo = ListUtils.getListMap((List<Map<String, Object>>)GetAnnualReport.get(0), 0);
				annualReportENTBaseInfo.put("ISCHANGE", changeObject(annualReportENTBaseInfo.get("ISCHANGE")));
				annualReportENTBaseInfo.put("ISWEB", changeObject(annualReportENTBaseInfo.get("ISWEB")));
				annualReportENTBaseInfo.put("ISLETTER", changeObject(annualReportENTBaseInfo.get("ISLETTER")));
				annualReportENTBaseInfo.put("EMPNUM",getDis(annualReportENTBaseInfo.get("EMPNUM"), annualReportENTBaseInfo.get("EMPNUMDIS")));
				annualReportENTBaseInfo.put("ASSGRO",getDis(annualReportENTBaseInfo.get("ASSGRO"), annualReportENTBaseInfo.get("ASSGRODIS")));
				annualReportENTBaseInfo.put("VENDINC",getDis(annualReportENTBaseInfo.get("VENDINC"), annualReportENTBaseInfo.get("VENDINCDIS")));
				annualReportENTBaseInfo.put("MAIBUSINC",getDis(annualReportENTBaseInfo.get("MAIBUSINC"), annualReportENTBaseInfo.get("MAIBUSINCDIS")));
				annualReportENTBaseInfo.put("RATGRO",getDis(annualReportENTBaseInfo.get("RATGRO"), annualReportENTBaseInfo.get("RATGRODIS")));
				annualReportENTBaseInfo.put("TOTEQU",getDis(annualReportENTBaseInfo.get("TOTEQU"), annualReportENTBaseInfo.get("TOTEQUDIS")));
				annualReportENTBaseInfo.put("PROGRO",getDis(annualReportENTBaseInfo.get("PROGRO"), annualReportENTBaseInfo.get("PROGRODIS")));
				annualReportENTBaseInfo.put("NETINC",getDis(annualReportENTBaseInfo.get("NETINC"), annualReportENTBaseInfo.get("NETINCDIS")));
				annualReportENTBaseInfo.put("LIAGRO",getDis(annualReportENTBaseInfo.get("LIAGRO"), annualReportENTBaseInfo.get("LIAGRODIS")));
				baseinfoList.add(PDFUtils.getTableContent(annualReportENTBaseInfo,0));

				//网站或者网店信息  key:WEBTYPE,WEBSITNAME,WEBSITE  name:类型,名称,网址
				List<Map<String, Object>> websiteInfo = (List<Map<String, Object>>)GetAnnualReport.get(1);
				webList.add(PDFUtils.getTableContent(websiteInfo,1));

				//股东及出资  key:INVNAME,LISUBCONAM,SUBCONDATE,SUBCONFORM_CN,LIACCONAM,ACCONDATE,ACCONFORM_CN   name:股东,认缴出资额(万元),认缴出资时间,认缴出资方式,实缴出资额(万元),实缴出资时间,实缴出资方式
				List<Map<String, Object>> subCapital = (List<Map<String, Object>>)GetAnnualReport.get(2);
				subCapitalList.add(PDFUtils.getTableContent(subCapital,1));

				//对外投资信息 key:ENTNAME,UNISCID  name:投资设立企业或购买股权企业名称,对外投资企业注册号
				List<Map<String, Object>> forinvestMent = (List<Map<String, Object>>)GetAnnualReport.get(3);
				forinvestMentList.add(PDFUtils.getTableContent(forinvestMent,1));

				//企业资产状况信息   key: ASSGRO,VENDINC,MAIBUSINC,RATGRO  TOTEQU,PROGRO,NETINC,LIAGRO   name:所有者权益合计,利润总额,净利润,负债总额   所有者权益合计,利润总额,净利润,负债总额
				moneyList.add(PDFUtils.getTableContent(annualReportENTBaseInfo,0));

				//对外提供保证担保信息 key:MORE,MORTGAGOR,PRICLASECKIND,PRICLASECAM,PEFPER,GUARANPERIOD,GATYPE  name:债权人,债务人,主债权种类,主债权数额,履行债务的期限,保证的期间,保证的方式
				List<Map<String, Object>> forguaranteeInfo = (List<Map<String, Object>>)GetAnnualReport.get(4);
				for (int j = 0; j < forguaranteeInfo.size(); j++) {
					Map<String, Object> forguaranteeInfoMap=forguaranteeInfo.get(j);
					forguaranteeInfoMap.put("PEFPER", (forguaranteeInfoMap.get("PEFPERFORM") == null ? "" :forguaranteeInfoMap.get("PEFPERFORM").toString())+(forguaranteeInfoMap.get("PEFPERTO") == null ? "" : "-"+forguaranteeInfoMap.get("PEFPERTO").toString()));
					forguaranteeInfo.set(j, forguaranteeInfoMap);
				}
				forguaranteeList.add(PDFUtils.getTableContent(forguaranteeInfo,1));

				//股权变更信息alterstockInfo  key:INV,TRANSAMPR,TRANSAMAFT,ALTDATE  name:股东,变更前股权比例,变更后股权比例,股权变更日期
				List<Map<String, Object>> alterstockInfo = (List<Map<String, Object>>)GetAnnualReport.get(5);
				alterstockInfoList.add(PDFUtils.getTableContent(alterstockInfo,1));

				//修改信息  key:ALITEM,ALTBE,ALTAF,ALTDATE  name:修改事项,修改前,修改后,修改日期
				List<Map<String, Object>> updateInfo = (List<Map<String, Object>>)GetAnnualReport.get(6);
				updateList.add(PDFUtils.getTableContent(updateInfo,1));
			}
		}
		//写入到pdf中
		PDFUtils.setYearDate(document,writer,CURRENTINDEX, baseFont,priptype, yearList, baseinfoList, webList, licenceList, moneyList, updateList, branchList, subCapitalList, forinvestMentList, forguaranteeList, alterstockInfoList);
	}
	/**
	 *
	 * @descript (获取登录用户的IP)
	 * @author 李海涛
	 * @since 2016年9月14日下午2:29:23
	 * @return
	 * @throws Exception
	 */
	public String getRemortIP() throws Exception {
		HttpServletRequest request = this.getRequest();
		String ip = "";

		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}

		return ip;
	}

	/**
	 * 
	 * @descript (查询历史信息)
	 * @author 余思
	 * @since 2016年11月30日上午9:08:37
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryReportCount", produces = "text/html;charset=UTF-8")
	public ModelAndView queryReportCount(Page page) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		//获取当前用户ID
		if(null != getUser()){
			pd.put("UID", getUser().getUSER_ID());
		}
		//设置分页
		page.setPd(pd);
		List<Map<String, Object>> report_list = reportService.queryReportCount(page);
		for(int i=0;i<report_list.size();i++){
			pd.put("CREATEUSER_ID", report_list.get(i).get("CREATEUSER_ID"));
			List<Map<String, Object>> usernme = reportService.queryReportCountUser(pd);
			if(usernme.size()>0){
				report_list.get(i).put("CREATEUSER_ID", usernme.get(0).get("USERNAME"));
			}
		}
		//设置返回数据和视图
		mv.addObject("pd", pd);
		mv.addObject("report_list", report_list);
		mv.setViewName("system/report_list");

		return mv;
	}
	/**
	 * 
	 * @descript (删除报告发送记录)
	 * @author 余思
	 * @since 2016年11月30日上午9:35:39
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteReport", produces = "text/html;charset=UTF-8")
	public String updateUserByid() throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		int num = reportService.deleteReport(pd);
		return Verification.getResultString(num);
	}
	/**
	 * 
	 * @descript (查询单条记录信息)
	 * @author 余思
	 * @since 2016年11月30日上午9:53:27
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	@ResponseBody
	@RequestMapping(value = "/queryReportById", produces = "text/html;charset=UTF-8")
	public ModelAndView queryUserById() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//查询用户数据
		Map<String, Object> report_list = reportService.queryReportById(pd);
		pd.put("CREATEUSER_ID", report_list.get("CREATEUSER_ID"));
		List<Map<String, Object>> usernme = reportService.queryReportCountUser(pd);
		if(usernme.size()>0){
			report_list.put("CREATEUSER_ID", usernme.get(0).get("USERNAME"));
		}
		mv.addObject("report_list", report_list);
		mv.setViewName("system/report_details");

		return mv;
	}
	/**
	 * 
	 * @descript (查询记录)
	 * @author 余思
	 * @since 2016年12月1日下午6:17:39
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryReportName", produces = "text/html;charset=UTF-8")
	public ModelAndView queryUserInfo(Page page) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		//获取当前用户ID
		if(null != getUser()){
			pd.put("UID", getUser().getUSER_ID());
		}

		//设置分页
		page.setPd(pd);
		List<Map<String, Object>> report_list = reportService.queryReportName(page);

		//设置返回数据和视图
		mv.addObject("pd", pd);
		mv.addObject("report_list", report_list);
		mv.setViewName("system/report_list");

		return mv;
	}
}
