package com.JavaClub.util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdfs.MakePDF;
import com.lowagie.text.pdfs.PdfUtil;

public class PDFUtils {
	
	protected static Logger logger = Logger.getLogger(PDFUtils.class);
	 
	public static void makePdf(String entname,String select,String pripid) throws Exception {
		String path = "C:\\Users\\不愿别人见识你的妩媚\\Desktop";
		String fileName = entname + ".pdf";
		try {
			Document document = new Document(PageSize.A4, 36, 36, 30, 20);
			PdfWriter.getInstance(document, new FileOutputStream(path + "/" + fileName));// 报告的位置
			BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

			document.open();
			Color font_color = new Color(126,147,186);//字体颜色 //边框颜色
			Color table_bg = new Color(220,230,240); //table背景色

			//年报信息   纵向的样式 
			document.add(getTitleTable("年报信息", baseFont, 13, font_color, 0, 12, table_bg,font_color));
			float[] dateFloat = {0.2f, 0.4f,0.4f};// 可以设置表格的列
			String[] dataTitile = {"序号","年报年度","发布日期"};
			String[] dataContent1 = {"1","2015年度","2015年08月20日"};
			String[] dataContent2 = {"2","2014年度","2014年08月20日"};
			document.add(getContentTable(dateFloat,dataTitile, baseFont, Font.BOLD,Element.ALIGN_CENTER,table_bg, font_color,false));
			document.add(getContentTable(dateFloat,dataContent1, baseFont, Font.NORMAL,Element.ALIGN_CENTER,null, font_color,false));
			document.add(getContentTable(dateFloat,dataContent2, baseFont, Font.NORMAL,Element.ALIGN_CENTER,null, font_color,false));

			//企业资产状况   横向的样式 
			document.add(getTitleTable("企业资产状况信息", baseFont, 13, font_color, 0, 12, table_bg,font_color));
			float[] qyFloat = {0.25f, 0.25f, 0.25f, 0.25f };// 可以设置表格的宽度
			String[] qyContent1 = {"资产总额","该企业选择不公示","营业总收入中主营业务收入","该企业选择不公示"};
			String[] qyContent2 = {"所有者权益合计","该企业选择不公示","净利润","该企业选择不公示"};
			String[] qyContent3 = {"营业总收入","该企业选择不公示","纳税总额","该企业选择不公示"};
			String[] qyContent4 = {"利润总额","该企业选择不公示","负债总额","该企业选择不公示"};
			//2015年度
			document.add(getTitleTable("2015年度", baseFont, 10, Color.BLACK, 0, 4, table_bg,font_color));
			document.add(getContentTable(qyFloat,qyContent1, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));
			document.add(getContentTable(qyFloat,qyContent2, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));
			document.add(getContentTable(qyFloat,qyContent3, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));
			document.add(getContentTable(qyFloat,qyContent4, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));
			//2014年度
			document.add(getTitleTable("2014年度", baseFont, 10, Color.BLACK, 0, 4, table_bg,font_color));	
			document.add(getContentTable(qyFloat,qyContent1, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));
			document.add(getContentTable(qyFloat,qyContent2, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));
			document.add(getContentTable(qyFloat,qyContent3, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));
			document.add(getContentTable(qyFloat,qyContent4, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));

			//联络信息
			document.add(getTitleTable("联络信息", baseFont, 13, font_color, 0, 12, table_bg,font_color));
			float[] llFloat = {0.2f, 0.3f, 0.2f, 0.3f };// 可以设置表格的宽度
			String[] llContent1 = {"电话","110","地址","南昌市公安局"};
			String[] llContent2 = {"邮箱","110@qq.com","邮编","110"};
			document.add(getTitleTable("2015年度", baseFont, 10, Color.BLACK, 0, 4, table_bg,font_color));
			document.add(getContentTable(llFloat,llContent1, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));
			document.add(getContentTable(llFloat,llContent2, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));
			//2014年度
			document.add(getTitleTable("2014年度", baseFont, 10, Color.BLACK, 0, 4, table_bg,font_color));	
			document.add(getContentTable(llFloat,llContent1, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));
			document.add(getContentTable(llFloat,llContent2, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true));

			//股东(发起人)及出资信息(单位:万人民币)
			document.add(getTitleTable("股东(发起人)及出资信息(单位:万人民币)", baseFont, 13, font_color, 0, 12, table_bg,font_color));
			float[] gdFloat = {0.16f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f, 0.14f };// 可以设置表格的宽度
			String[] gdTitle={"发起人","认缴出资额","认缴出资时间","认缴出资方式","实缴出资额","实缴出资时间","实缴出资方式"};
			String[] gdContent={"南昌市财政局","2200.20","2015-02-28","货币","3200.20","2015-03-28","货币"};

			//2015年度
			document.add(getTitleTable("2015年度", baseFont, 10, Color.BLACK, 0, 4, table_bg,font_color));
			document.add(getContentTable(gdFloat,gdTitle, baseFont, Font.BOLD,Element.ALIGN_CENTER,null, font_color,false));
			document.add(getContentTable(gdFloat,gdContent, baseFont, Font.NORMAL,Element.ALIGN_CENTER,null, font_color,false));
			//2014年度
			document.add(getTitleTable("2014年度", baseFont, 10, Color.BLACK, 0, 4, table_bg,font_color));
			document.add(getContentTable(gdFloat,gdTitle, baseFont, Font.BOLD,Element.ALIGN_CENTER, null,font_color,false));
			document.add(getContentTable(gdFloat,gdContent, baseFont, Font.NORMAL,Element.ALIGN_CENTER,null, font_color,false));

			//网站信息///////////////////////
			document.add(getTitleTable("网站信息", baseFont, 13, font_color, 0, 12, table_bg,font_color));
			float[] wzFloat = {0.1f, 0.2f, 0.35f, 0.35f };// 可以设置表格的宽度
			String[] wzTtitle={"序号","网站类型","网站名称","网址"};
			String[] wzContent={"1","金融","江西智容","www.baidu.com"};
			//2015年度
			document.add(getTitleTable("2015年度", baseFont, 10, Color.BLACK, 0, 4, table_bg,font_color));
			document.add(getContentTable(wzFloat,wzTtitle, baseFont, Font.BOLD,Element.ALIGN_LEFT,null, font_color,false));
			document.add(getContentTable(wzFloat,wzContent, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,false));
			//2014年度
			document.add(getTitleTable("2014年度", baseFont, 10, Color.BLACK, 0, 4, table_bg,font_color));
			document.add(getContentTable(wzFloat,wzTtitle, baseFont, Font.BOLD,Element.ALIGN_LEFT, null,font_color,false));
			document.add(getContentTable(wzFloat,wzContent, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,false));

			document.close();
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
	}
	

    /**
     * 
     * @descript (设置标题信息)
     * @author 李海涛
     * @since 2016年10月26日下午6:37:36
     * @param document
     * @param writer
     * @param titleType 标题类型 1级标题为1，2级为2
     * @param CatalogueName  标题名称
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public static void setCatalogueTitle(Document document,PdfWriter writer,Map<String, Object> CURRENTINDEX,int titleType,String CatalogueName) throws Exception{
        BaseFont baseFont = MakePDF.getBaseFontYh();
        Font font_catalogue_one=new Font(baseFont, 18, Font.BOLD, Color.decode("#000000"));//一级标题的字体
        Font font_catalogue_two=new Font(baseFont, 15, Font.BOLD, Color.decode("#000000"));//二级标题的字体
        Font  font_catalogue_three=new Font(baseFont, 12, Font.BOLD, Color.decode("#000000"));;//三级标题的字体
        int CURRENTINDEX_ONE=Integer.parseInt(CURRENTINDEX.get("CURRENTINDEX_ONE").toString()); //记录第几个一级目录
        int CURRENTINDEX_TWO=Integer.parseInt(CURRENTINDEX.get("CURRENTINDEX_TWO").toString()); //记录第几个二级目录
        int CURRENTINDEX_THREE=Integer.parseInt(CURRENTINDEX.get("CURRENTINDEX_THREE").toString()); //记录第几个三级目录
        List catalogue_list=(List) CURRENTINDEX.get("CATALOGUE_LIST");//存放目录的模块名称和页码
        if(titleType==1){//一级标题
            document.newPage();
            String catalogue_title=PdfUtil.toHanzi(CURRENTINDEX_ONE+"")+"、"+CatalogueName;
            setCatalogue(catalogue_list, catalogue_title, writer.getCurrentPageNumber(),titleType);
            Paragraph paragraph = new Paragraph(catalogue_title, font_catalogue_one);
            CURRENTINDEX_ONE++;
            CURRENTINDEX_TWO=1;
            paragraph.setAlignment(Element.ALIGN_LEFT);// 设置标题居中
            document.add(paragraph);
            document.add(MakePDF.MyLine("", Color.decode("#ADABAB")));
        }else if(titleType==2){//二级标题
            String catalogue_title=(CURRENTINDEX_ONE-1)+"."+CURRENTINDEX_TWO+"、"+CatalogueName;
            setCatalogue(catalogue_list, catalogue_title, writer.getCurrentPageNumber(),titleType);
            Paragraph paragraph = new Paragraph(catalogue_title, font_catalogue_two);
            CURRENTINDEX_TWO++;
            CURRENTINDEX_THREE=1;
            paragraph.setAlignment(Element.ALIGN_LEFT);// 设置标题居中
            paragraph.setFirstLineIndent(20);
            paragraph.setSpacingAfter(15);
            document.add(paragraph);
        }else if(titleType==3){//三级标题
            String catalogue_title=(CURRENTINDEX_ONE-1)+"."+(CURRENTINDEX_TWO-1)+"."+CURRENTINDEX_THREE+"、"+CatalogueName;
            setCatalogue(catalogue_list, catalogue_title, writer.getCurrentPageNumber(),titleType);
            Paragraph paragraph = new Paragraph(catalogue_title, font_catalogue_three);
            CURRENTINDEX_THREE++;
            paragraph.setAlignment(Element.ALIGN_LEFT);// 设置标题居中
            paragraph.setFirstLineIndent(40);
            paragraph.setSpacingAfter(15);
            document.add(paragraph);
        }
        CURRENTINDEX.put("CURRENTINDEX_ONE", CURRENTINDEX_ONE);
        CURRENTINDEX.put("CURRENTINDEX_TWO", CURRENTINDEX_TWO);
        CURRENTINDEX.put("CURRENTINDEX_THREE", CURRENTINDEX_THREE);
        CURRENTINDEX.put("CATALOGUE_LIST", catalogue_list);
    }
    /**
     * 这个方法是判断字符串是否含有中文
     */
    @SuppressWarnings({"rawtypes", "unchecked" })
    public static void setCatalogue(List list,String modularName,int pageNumber,int titleType){
        Map<String,Object> map=new HashMap<>(1);
        map.put("modularName", modularName);
        map.put("pageNumber", pageNumber);
        map.put("titleType", titleType);
        list.add(map);
    }
	/**
	 * 
	 * @descript (设置table标题)
	 * @author 李文海
	 * @since 2016年10月26日下午4:31:18
	 * @param title  标题名称
	 * @param baseFont  字体样式
	 * @param fontSize	字体大小
	 * @param font_color	字体颜色
	 * @param margin_left	左边距
	 * @param padding_top	上边距
	 * @param table_bg		背景颜色
	 * @param border_color  边框颜色
	 * @return
	 */
	public static PdfPTable getTitleTable(String title,BaseFont baseFont,int fontSize,Color font_color,int margin_left,int padding_top,Color table_bg,Color border_color){
		float[] gudong = {1f };// 可以设置表格的宽度

		PdfPTable table = new PdfPTable(gudong);
			table.setWidthPercentage(100);// 宽度为100%
			table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);// 居中

		PdfPCell cell = new PdfPCell();
			Paragraph	paragraph = new Paragraph(title, new Font(baseFont, fontSize, Font.BOLD, font_color));
			paragraph.setAlignment(Element.ALIGN_LEFT);// 设置左对齐
			paragraph.setIndentationLeft(margin_left);//设置左边距
			paragraph.setSpacingAfter(padding_top);// 设置内容段落间距
			cell.addElement(paragraph);
			cell.setBackgroundColor(table_bg);
			cell.setBorderColor(border_color);
			table.addCell(cell);

		return table;
	}
	
	/**
	 * 
	 * @descript (设置表格内容   注意gdFloat 和content 的长度要一样)
	 * @author 李文海
	 * @since 2016年10月26日下午4:50:07
	 * @param gdFloat  设置表格的列
	 * @param content   内容
	 * @param baseFont	字体样式
	 * @param font_weight	字体粗细
	 * @param align   字体对齐方式
	 * @param table_bg  背景颜色   null为不设置
	 * @param border_color	边框颜色
	 * @param firstCellisBold 第一列和第三列是否需要加粗 true为加粗
	 * @return
	 */
	public static PdfPTable getContentTable(float[] gdFloat,String[] content,BaseFont baseFont,int font_weight,int align,Color table_bg,Color border_color,boolean firstCellisBold){
		
		PdfPTable table = new PdfPTable(gdFloat);
			table.setWidthPercentage(100);// 宽度为100%
			table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);// 居中
		
		for(int i=0;i<content.length;i++){
			PdfPCell cell = new PdfPCell();
			Paragraph paragraph;
			//第一列和第三列是否需要加粗  true为加粗
			if(firstCellisBold){
				if((i+1)==1 || (i+1)==3){
					paragraph = new Paragraph(content[i], new Font(baseFont, 10, Font.BOLD, Color.BLACK));
				}else{
					paragraph = new Paragraph(content[i], new Font(baseFont, 10, font_weight, Color.BLACK));
				}
			}else{
				paragraph = new Paragraph(content[i], new Font(baseFont, 10, font_weight, Color.BLACK));
			}
			paragraph.setAlignment(align);// 设置居中
			paragraph.setSpacingAfter(4);// 设置内容段落间距
			cell.addElement(paragraph);
			//设置背景颜色  为null不设置
			if(table_bg != null){
				cell.setBackgroundColor(table_bg);
			}
			cell.setBorderColor(border_color);
			table.addCell(cell);
		}
		return table;
	}
	/////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @descript (设置标题 )
	 * @author 李文海
	 * @since 2016年10月27日上午9:11:12
	 * @param document      文档
	 * @param title   		标题
	 * @param baseFont		字体样式
	 * @param fontSize		字体大小
	 * @param font_color	字体颜色
	 * @param padding_top	上边距
	 * @param table_bg		背景色
	 * @param border_color	边框颜色
	 * @param align 		对齐方式
	 */
	public static void setTableTitle(Document document,String title,BaseFont baseFont,int fontSize,Color font_color,int padding_top,Color table_bg,Color border_color,int align){
		float[] gudong = {1f };// 可以设置表格的宽度

		PdfPTable table = new PdfPTable(gudong);
			table.setWidthPercentage(100);// 宽度为100%
			table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);// 居中

		PdfPCell cell = new PdfPCell();
			Paragraph	paragraph = new Paragraph(title, new Font(baseFont, fontSize, Font.BOLD, font_color));
			paragraph.setAlignment(align);// 设置左对齐
			paragraph.setSpacingAfter(padding_top);// 设置内容段落间距
			cell.addElement(paragraph);
			cell.setBackgroundColor(table_bg);
			cell.setBorderColor(border_color);
			table.addCell(cell);
			try {
				document.add(table);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	 * @descript (设置表格内容   注意gdFloat 和content 的长度要一样)
	 * @author 李文海
	 * @since 2016年10月26日下午4:50:07
	 * @param document 文档
	 * @param gdFloat  设置表格的列
	 * @param content   内容
	 * @param baseFont	字体样式
	 * @param font_weight	字体粗细
	 * @param align   字体对齐方式
	 * @param table_bg  背景颜色   null为不设置
	 * @param border_color	边框颜色
	 * @param firstCellisBold 第一列和第三列是否需要加粗 true为加粗
	 * @return
	 */
	public static void setTableContent(Document document,float[] gdFloat,String[] content,BaseFont baseFont,int font_weight,int align,Color table_bg,Color border_color,boolean firstCellisBold){
		
		PdfPTable table = new PdfPTable(gdFloat);
			table.setWidthPercentage(100);// 宽度为100%
			table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);// 居中
		
		for(int i=0;i<content.length;i++){
			PdfPCell cell = new PdfPCell();
			Paragraph paragraph;
			//第一列和第三列是否需要加粗  true为加粗
			if(firstCellisBold){
				if((i+1)==1 || (i+1)==3){
					paragraph = new Paragraph(content[i], new Font(baseFont, 10, Font.BOLD, Color.BLACK));
				}else{
					paragraph = new Paragraph(content[i], new Font(baseFont, 10, font_weight, Color.BLACK));
				}
			}else{
				paragraph = new Paragraph(content[i], new Font(baseFont, 10, font_weight, Color.BLACK));
			}
			paragraph.setAlignment(align);// 设置居中
			paragraph.setSpacingAfter(4);// 设置内容段落间距
			cell.addElement(paragraph);
			//设置背景颜色  为null不设置
			if(table_bg != null){
				cell.setBackgroundColor(table_bg);
			}
			cell.setBorderColor(border_color);
			table.addCell(cell);
			try {
				document.add(table);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @descript (接收所有年报信息)
	 * @author 李文海
	 * @since 2016年10月27日上午11:27:03
	 * @param document  			文档
	 * @param baseFont 				字体样式
	 * @param priptype 				类型
	 * @param yearList				年度
	 * @param baseinfoList			年报基本信息
	 * @param webList				网站或者网店信息
	 * @param licenceList			行政许可信息
	 * @param moneyList				资产状况信息
	 * @param updateList			修改信息
	 * @param branchList			分支机构信息
	 * @param subCapitalList		股东及出资
	 * @param forinvestMentList 	对外投资信息
	 * @param forguaranteeList		对外提供保证担保信息
	 * @param alterstockInfoList	股权变更信息
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
    public static void setYearDate(Document document,PdfWriter writer,Map<String,Object> CURRENTINDEX,BaseFont baseFont,String priptype,
			List<Map<String,Object>> yearList,List<Map<String,Object>> baseinfoList,
			List<Map<String,Object>> webList,List<Map<String,Object>> licenceList,
			List<Map<String,Object>> moneyList,List<Map<String,Object>> updateList,
			List<Map<String,Object>> branchList,List<Map<String,Object>> subCapitalList,
			List<Map<String,Object>> forinvestMentList,List<Map<String,Object>> forguaranteeList,
			List<Map<String,Object>> alterstockInfoList) throws Exception{
		
		Color font_color = new Color(126,147,186);//字体颜色 //边框颜色
		Color table_bg = Color.decode("#F7F3F3"); //table背景色
		
		//添加年度信息
		setCatalogueTitle(document, writer,CURRENTINDEX,2,"年度信息");
		//setTableTitle(document, "年度信息", baseFont, 13, font_color,12, table_bg,font_color,Element.ALIGN_LEFT);
		float[] widthFloat = {0.2f, 0.4f,0.4f};// 可以设置表格的列
		String[] title = {"序号","年报年度","发布日期"};
		setTableContent(document, widthFloat, title, baseFont,  Font.BOLD, Element.ALIGN_CENTER, table_bg, font_color, false);
		
		if(yearList.size() <=0 ){
			setTableTitle(document,"没有记录", baseFont, 10,Color.BLACK , 4, Color.WHITE,font_color,Element.ALIGN_CENTER);
		}
		
		for(Map<String,Object> map:yearList){
			String[] content = {map.get("index").toString(),map.get("year").toString(),map.get("date").toString()};
			setTableContent(document, widthFloat, content, baseFont,  Font.NORMAL, Element.ALIGN_CENTER, null, font_color, false);
		}
		
		if(("9500").equals(priptype)){
			//个体年报信息
			
			//添加年报基本信息
			String[] key = {"TRANAME","REGNO","NAME","TEL","FUNDAM","EMPNUM","ISWEB"};
			String[] name = {"企业名称","注册号","经营者","联系电话","资金数额","从业人数","是否有网站或网店"};
			setBaseinfoPDF(document,writer,CURRENTINDEX,baseFont, table_bg, font_color, baseinfoList, yearList, key, name,"年报基本信息");
			
			//添加网站或者网店信息
			float[] webFloat = {0.1f, 0.2f, 0.35f, 0.35f };// 网站的float
			String[] webName={"序号","网站类型","网站名称","网址"};// 网站的标题
			 String[] webKey = {"WEBTYPE","WEBSITNAME","WEBSITE"};
			setWebPDF(document,writer,CURRENTINDEX, baseFont, table_bg, font_color, webList, yearList,webFloat,webName,"网站或者网店信息",webKey,false);
			
			//添加行政许可信息  
			float[] licenceFloat = {0.2f, 0.4f, 0.4f}; 
			String[] licenceName = {"序号","许可文件名称","有效期至"};
			String[] licenceKey = {"LICNAME_CN","VALTO"};
			setWebPDF(document,writer,CURRENTINDEX, baseFont, table_bg, font_color, licenceList, yearList,licenceFloat,licenceName,"行政许可信息",licenceKey,false);
			
			//添加资产状况 
			String[] moneyKey = {"VENDINC","RATGRO"};
			String[] moneyName = {"销售额或营业收入","纳税总额"};
			setBaseinfoPDF(document,writer,CURRENTINDEX,baseFont, table_bg, font_color, moneyList, yearList, moneyKey, moneyName,"企业资产状况信息");
			
			//修改信息  
			float[] updateFloat = {0.25f, 0.25f, 0.25f,0.25f}; 
			String[] updateName = {"修改事项","修改前","修改后","修改日期"};
			String[] updateKey = {"ALITEM","ALTBE","ALTAF","ALTDATE"};
			setWebPDF(document,writer,CURRENTINDEX, baseFont, table_bg, font_color,updateList , yearList,updateFloat,updateName,"修改信息",updateKey,true);
		
		}else if("9100".equals(priptype)){
			 //农专年报信息
			
			//添加年报基本信息
			String[] key = {"ENTNAME","REGNO","TEL","EMAIL","MEMNUM"};
			String[] name = {"合作社名称","注册号","联系电话","邮箱","成员人数"};
			setBaseinfoPDF(document,writer,CURRENTINDEX,baseFont, table_bg, font_color, baseinfoList, yearList, key, name,"年报基本信息");
			
			//添加网站或者网店信息
			float[] webFloat = {0.1f, 0.2f, 0.35f, 0.35f };// 网站的float
			String[] webName={"序号","网站类型","网站名称","网址"};// 网站的标题
			 String[] webKey = {"WEBTYPE","WEBSITNAME","WEBSITE"};
			setWebPDF(document,writer,CURRENTINDEX, baseFont, table_bg, font_color, webList, yearList,webFloat,webName,"网站或者网店信息",webKey,false);
			
			//添加行政许可信息  
			float[] licenceFloat = {0.2f, 0.4f, 0.4f}; 
			String[] licenceName = {"序号","许可文件名称","有效期至"};
			String[] licenceKey = {"LICNAME_CN","VALTO"};
			setWebPDF(document,writer,CURRENTINDEX, baseFont, table_bg, font_color, licenceList, yearList,licenceFloat,licenceName,"行政许可信息",licenceKey,false);
			
			//分支机构信息  
			float[] branchFloat = {0.2f, 0.4f, 0.4f}; 
			String[] branchName = {"序号","分支机构名称","统一社会信用代码"};
			String[] branchKey = {"BRNAME","UNISCID"};
			setWebPDF(document,writer,CURRENTINDEX, baseFont, table_bg, font_color, branchList, yearList,branchFloat,branchName,"分支机构信息 ",branchKey,false);
			
			//添加资产状况  
			String[] moneyKey = {"PRIYEASALES","RATGRO","PRIYEALOAN","PRIYEAPROFIT","PRIYEASUB"};
			String[] moneyName = {" 销售额营业总收入","纳税总额","金融贷款","盈余总额","获得政府扶持资金、补助"};
			setBaseinfoPDF(document,writer,CURRENTINDEX,baseFont, table_bg, font_color, moneyList, yearList, moneyKey, moneyName,"企业资产状况信息");
			
			//修改信息
			float[] updateFloat = {0.25f, 0.25f, 0.25f,0.25f}; 
			String[] updateName = {"修改事项","修改前","修改后","修改日期"};
			String[] updateKey = {"ALITEM","ALTBE","ALTAF","ALTDATE"};
			setWebPDF(document,writer,CURRENTINDEX, baseFont, table_bg, font_color,updateList , yearList,updateFloat,updateName,"修改信息",updateKey,true);
		}else{
			 //企业年报信息
			
			//添加年报基本信息
			String[] key = {"ENTNAME","REGNO","TEL","POSTALCODE","ADDR","EMAIL","ISCHANGE","BUSST_CN","ISWEB","ISLETTER","EMPNUM"};
			String[] name = {"企业名称","注册号","企业联系电话","邮政编码","企业通讯地址","电子邮箱","本年度是否发生股东股权转让","企业经营状态","是否有网站或网店","企业是否有投资信息或购买其他公司股权","从业人数"};
			setBaseinfoPDF(document,writer,CURRENTINDEX,baseFont, table_bg, font_color, baseinfoList, yearList, key, name,"年报基本信息");
			
			//添加网站或者网店信息
			float[] webFloat = {0.1f, 0.2f, 0.35f, 0.35f };// 网站的float
			String[] webName={"序号","网站类型","网站名称","网址"};// 网站的标题
			 String[] webKey = {"WEBTYPE","WEBSITNAME","WEBSITE"};
			setWebPDF(document,writer,CURRENTINDEX, baseFont, table_bg, font_color, webList, yearList,webFloat,webName,"网站或者网店信息",webKey,false);
			
			 //股东及出资  
			String[] subCapitalKey = {"INVNAME","LISUBCONAM","SUBCONDATE","SUBCONFORM_CN","LIACCONAM","ACCONDATE","ACCONFORM_CN"};
			String[] subCapitalName = {"股东","认缴出资额(万元)","认缴出资时间","认缴出资方式","实缴出资额(万元)","实缴出资时间","实缴出资方式"};
			
			//setTableTitle(document, "股东及出资 ", baseFont, 13, font_color,12, table_bg,font_color,Element.ALIGN_LEFT);
			setCatalogueTitle(document, writer,CURRENTINDEX,2,"股东及出资");
			if(subCapitalList.size() <=0 ){
				setTableTitle(document,"没有记录", baseFont, 10,Color.BLACK , 4, Color.WHITE,font_color,Element.ALIGN_CENTER);
			}
			
			for(int i=0;i<subCapitalList.size();i++){
				//添加年度小标题
				setTableTitle(document,yearList.get(i).get("year").toString(), baseFont, 10, Color.BLACK, 4, table_bg,font_color,Element.ALIGN_LEFT);
				//列表
				List<Map<String, Object>> tempList = (List<Map<String, Object>>) subCapitalList.get(i).get("list");
				setBaseinfoPDF(document,writer,CURRENTINDEX,baseFont, table_bg, font_color, tempList,yearList , subCapitalKey, subCapitalName, "");
			}
			
			//对外投资信息  
			float[] forinvestMentFloat = {0.2f, 0.4f, 0.4f };// 网站的float
			String[] forinvestMentName={"序号","投资设立企业或购买股权企业名称","对外投资企业注册号"};// 网站的标题
			 String[] forinvestMentKey = {"ENTNAME","UNISCID"};
			setWebPDF(document,writer,CURRENTINDEX, baseFont, table_bg, font_color, forinvestMentList, yearList,forinvestMentFloat,forinvestMentName,"对外投资信息",forinvestMentKey,false);
			
			 //企业资产状况信息
			String[] moneyKey = {"ASSGRO","VENDINC","MAIBUSINC","RATGRO","TOTEQU","PROGRO","NETINC","LIAGRO"};
			//String[] moneyName = {"所有者权益合计","利润总额","净利润","负债总额","所有者权益合计","利润总额","净利润","负债总额"};
			String[] moneyName = {"资产总额","营业总收入","营业总收入中主营业务收入","纳税总额","所有者权益合计","利润总额","净利润","负债总额"};
			setBaseinfoPDF(document,writer,CURRENTINDEX,baseFont, table_bg, font_color, moneyList, yearList, moneyKey, moneyName,"企业资产状况信息");
			
			//对外提供保证担保信息   
			String[] forguaranteeKey = {"MORE","MORTGAGOR","PRICLASECKIND","PRICLASECAM","PEFPER","GUARANPERIOD","GATYPE"};
			String[] forguaranteeName = {"债权人","债务人","主债权种类","主债权数额","履行债务的期限","保证的期间","保证的方式"};
			//setTableTitle(document, "对外提供保证担保信息  ", baseFont, 13, font_color,12, table_bg,font_color,Element.ALIGN_LEFT);
			setCatalogueTitle(document, writer,CURRENTINDEX,2,"对外提供保证担保信息");
			if(forguaranteeList.size() <=0 ){
				setTableTitle(document,"没有记录", baseFont, 10,Color.BLACK , 4, Color.WHITE,font_color,Element.ALIGN_CENTER);
			}
			
			for(int i=0;i<forguaranteeList.size();i++){
				//添加年度小标题
				setTableTitle(document,yearList.get(i).get("year").toString(), baseFont, 10, Color.BLACK, 4, table_bg,font_color,Element.ALIGN_LEFT);
				//列表
				List<Map<String, Object>> tempList = (List<Map<String, Object>>) forguaranteeList.get(i).get("list");
				setBaseinfoPDF(document,writer,CURRENTINDEX,baseFont, table_bg, font_color, tempList,yearList , forguaranteeKey, forguaranteeName, "");
			}
			
			//股权变更信息
			String[]  alterstockInfoKey = {"INV","TRANSAMPR","TRANSAMAFT","ALTDATE"};
			String[]  alterstockInfoName = {"股东","变更前股权比例","变更后股权比例","股权变更日期"};
			float[] alterstockInfoFloat = {0.25f, 0.25f, 0.25f ,0.25f};
			setWebPDF(document,writer,CURRENTINDEX, baseFont, table_bg, font_color, alterstockInfoList, yearList,alterstockInfoFloat,alterstockInfoName,"股权变更信息",alterstockInfoKey,true);
			
			//修改信息
			float[] updateFloat = {0.25f, 0.25f, 0.25f,0.25f}; 
			String[] updateName = {"修改事项","修改前","修改后","修改日期"};
			String[] updateKey = {"ALITEM","ALTBE","ALTAF","ALTDATE"};
			setWebPDF(document,writer,CURRENTINDEX, baseFont, table_bg, font_color,updateList , yearList,updateFloat,updateName,"修改信息",updateKey,true);
		}
		
	}
	
	/**
	 * 
	 * @descript (横向  生成网站或者网店信息/行政处罚信息/修改信息/分支机构信息/对外投资信息     (List数据类型) )
	 * @author 李文海
	 * @since 2016年10月27日下午3:32:05
	 * @param document
	 * @param baseFont
	 * @param table_bg  	背景色
	 * @param font_color	字体颜色
	 * @param webList		网站或者网店信息List
	 * @param yearList		年度List
	 * @param widthFloat	列数
	 * @param name			显示的值
	 * @param title			标题
	 * @param key			值的key
	 * @param noIndex		是否需要序号列 true为不需要
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
    public static void setWebPDF(Document document,PdfWriter writer,Map<String,Object> CURRENTINDEX,BaseFont baseFont,Color table_bg,Color font_color,
			List<Map<String,Object>> webList,List<Map<String,Object>> yearList,
			float[] widthFloat,String[] name,String title,String[] key,boolean noIndex) throws Exception{
		
		//setTableTitle(document, title, baseFont, 13, font_color,12, table_bg,font_color,Element.ALIGN_LEFT);
		setCatalogueTitle(document, writer,CURRENTINDEX,2,title);
		if(webList.size() <=0 ){
			setTableTitle(document,"没有记录", baseFont, 10,Color.BLACK , 4, Color.WHITE,font_color,Element.ALIGN_CENTER);
		}
		
		for(int i=0;i<webList.size();i++){
			//添加年度小标题
			setTableTitle(document,yearList.get(i).get("year").toString(), baseFont, 10, Color.BLACK, 4, table_bg,font_color,Element.ALIGN_LEFT);
			//标题
			setTableContent(document, widthFloat, name, baseFont, Font.BOLD, Element.ALIGN_LEFT, null, font_color, false);
			//列表
			List<Map<String, Object>> tempList = (List<Map<String, Object>>) webList.get(i).get("list");
			
			if(tempList.size() <=0 ){
				setTableTitle(document,"没有记录", baseFont, 10,Color.BLACK , 4, Color.WHITE,font_color,Element.ALIGN_CENTER);
			}
			
			for(int j=0;j<tempList.size();j++){
				 Map<String, Object> map = tempList.get(j);
				 
				 String[] cwebContent = new String[key.length+1];
				 //true时不需要 序号
				 if(noIndex){
					 for(int g=0;g<key.length;g++){
						 if(map.get(key[g]) == null){
							 cwebContent[g] = "";
						 }else{
							 cwebContent[g] = map.get(key[g]).toString();
						 }
						 
					 }
				 }else{
					 cwebContent[0] = (j+1)+"";
					 for(int g=0;g<key.length;g++){
						 cwebContent[g+1] = map.get(key[g]).toString();
					 }
				 }
				setTableContent(document, widthFloat, cwebContent, baseFont, Font.NORMAL, Element.ALIGN_LEFT, null, font_color, false);
				 
			 }
		}
	}
	/**
	 * 
	 * @descript (纵向（固定4列表格） 生成年报基本信息/企业资产  （Map类型的数据）)
	 * @author 李文海
	 * @since 2016年10月27日下午2:47:36
	 * @param document		文档
	 * @param baseFont		字体样式
	 * @param table_bg		背景色
	 * @param font_color	字体颜色
	 * @param list			需要显示的数据
	 * @param yearList		年度List
	 * @param key			需要现实的值的键
	 * @param name			需要显示的值
	 * @param widthFolat	生成几列多宽的table
	 * @param title			标题
	 * @throws Exception 
	 */
	public static void setBaseinfoPDF(Document document,PdfWriter writer,Map<String,Object> CURRENTINDEX,BaseFont baseFont,Color table_bg,Color font_color,List<Map<String,Object>> list,List<Map<String,Object>> yearList,String[] key,String[] name,String title) throws Exception{
		//当title为空时  不需要再生产标题和下面的年度小标题
		if(StringUtil.isNotEmpty(title)){
			/*setTableTitle(document, title, baseFont, 13, font_color,12, table_bg,font_color,Element.ALIGN_LEFT);*/
		   setCatalogueTitle(document, writer,CURRENTINDEX,2,title);
		}
		
		float[] widthFolat = {0.25f, 0.25f, 0.25f, 0.25f };//年报的float
		
		if(list.size() <=0 ){
			setTableTitle(document,"没有记录", baseFont, 10,Color.BLACK , 4, Color.WHITE,font_color,Element.ALIGN_CENTER);
		}
		
		for(int j=0;j<list.size();j++){
			
			if(StringUtil.isNotEmpty(title)){
				//添加年度小标题
				setTableTitle(document,yearList.get(j).get("year").toString(), baseFont, 10, Color.BLACK, 4, table_bg,font_color,Element.ALIGN_LEFT);
			}
			
			for(int i=0;i<key.length;i++){
				Map<String,Object> map =  list.get(j);
				int other = i+1;
				if(other < key.length){
			        String[] content = {name[i],map.get(key[i])==null? "" : map.get(key[i]).toString(),name[other],map.get(key[other])==null ? "": map.get(key[other]).toString()};
			        setTableContent(document,widthFolat,content, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true);
				}else{
					String[] content = {name[i],map.get(key[i])==null? "" : map.get(key[i]).toString(),"",""};
					setTableContent(document,widthFolat,content, baseFont, Font.NORMAL,Element.ALIGN_LEFT,null, font_color,true);
				}
				i = other;
			}
		}
	}
	
	 /**
     * 
     * @descript (获取年度)
     * @author 李文海
     * @since 2016年10月27日上午10:47:50
     * @param index  序号
     * @param year	年度
     * @param date	日期
     * @return
     */
    public static Map<String,Object> getYear(int index,Object year,Object date){
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("index", index);    //序号  例如： 1
    	map.put("year", year==null ? "": year+"年度"); //例如：2015年度
    	map.put("date", date==null ? "" :date); //例如：2015年度08月01日
    	return map;
    }
    
    /**
     * 
     * @descript (把数据添加进Map)
     * @author 李文海
     * @since 2016年10月27日上午10:56:25
     * @param object  PageData /  List
     * @param type  0 为pd/1为list
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String,Object> getTableContent(Object object,int type){
    	Map<String,Object> map = new HashMap<String, Object>();
    	if(type == 0){
    		map = (Map<String, Object>) object;
    	}else if(type == 1){
    		map.put("list", object);
    	}
    	return map;
    }
    
	public static void main(String[] args) {
		/*try {
			makePdf("江西智容","企业登记","123");
			System.out.println("ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Map<String, Object> map = new HashMap<>();
		map.put("key", "1");
		if(map.get("list")!=null){
			System.out.println("list");
		}else{
			System.out.println(map.get("key"));
		}
	}
	
	
}
