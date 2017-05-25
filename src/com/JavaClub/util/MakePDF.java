package com.JavaClub.util;


import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 *  
 * @author 李海涛
 *
 */
public class MakePDF{  
	
	protected static Logger logger = Logger.getLogger(MakePDF.class);
	
	private static final String KEY = "key0,key1,key2,key3,key4,key5,key6,key7";
	private static final String BLACK = "#000000";
	
    public static void main(String[] args){
        Test_MakePDF();
    }
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
    public static  void Test_MakePDF(){
        try {
            Document document = new Document(PageSize.A4, 36, 36,30, 20);
            PdfWriter.getInstance(document,new FileOutputStream("D:\\testHeaderAndFooter.pdf") );
            
            BaseFont baseFont = BaseFont.createFont("/usr/share/fonts/zh_CN/msyh.ttc,1",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);  
            
            Font chinese = getFont();
            Paragraph paragraph; 
            PdfPTable table;
            PdfPCell cell ;//创建单元格
            /** 
             * HeaderFooter的第2个参数为非false时代表打印页码 
             * 页眉页脚中也可以加入图片，并非只能是文字 
             */  
            HeaderFooter footer=new HeaderFooter(new Phrase("第 ",chinese),new Phrase(" 页",chinese));  
            footer.setAlignment(1); /*0是靠左  1是居中 2是居右  */
            footer.setBorderColor(Color.black);  
            footer.setBorder(Rectangle.ALIGN_CENTER);  
            document.setFooter(footer);  
            /* 页眉页脚的设置一定要在open前设置好*/  


            document.open(); 
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
            document.add(setFiestPDF(desc8,baseFont,13, Color.black,0,0,""));
            document.newPage();
            List<Map<String, Object>> catalogue_list = new ArrayList<Map<String, Object>>();//存放目录的模块名称和页码
            for (int i = 0; i < 10; i++) {
                Map<String, Object> map=new HashMap<>(2);
                map.put("modularName", "李海涛"+i);
                map.put("pageNumber", i+3);
                catalogue_list.add(map);
            }
            String str=".........................";
            for (int j = 0; j < 2; j++) {
                str+=str;
            }
            document.add(setFiestPDF("目录",baseFont,18, Color.black,0,23,"center"));
            for (int i = 0; i < catalogue_list.size(); i++) {
                paragraph = new Paragraph(StringUtil.toHanzi((i+1)+"")+"、"+catalogue_list.get(i).get("modularName")+str.substring(0,str.length()-(catalogue_list.get(i).get("modularName").toString().length()*2))+catalogue_list.get(i).get("pageNumber"), new Font(baseFont, 14, Font.NORMAL, Color.decode("#208CCC")));
                paragraph.setIndentationLeft(30);
                paragraph.setSpacingBefore(10);
                paragraph.setAlignment(Element.ALIGN_CENTER);// 设置标题居中
                document.add(paragraph);
            }
            
            
            
           
            
            
            
            
            
            
            
            
            
            
            
            
            float[] width = {0.8f, 0.2f};//可以设置表格的宽度
            table = new PdfPTable(width);
            table.setSpacingBefore(40);
            table.setWidthPercentage(100);//宽度为100%
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);//居中

            cell = new PdfPCell();
            cell.setBorder(0);
            paragraph = new Paragraph("江西智容科技", new Font(baseFont,30, Font.NORMAL, Color.BLACK));//设置文字大小
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.newPage();
            document.add(setFiestPDF("报告说明",baseFont,18, Color.black,0,23,"center"));

            
            document.newPage();
            /**
             * 生成最上面的头部
             */
            paragraph.setSpacingAfter(12);//设置内容段落间距
            cell.addElement(paragraph);
            paragraph = new Paragraph("(企业征信报告)", chinese);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setSpacingAfter(12);//设置内容段落间距
            cell.addElement(paragraph);
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒");//设置日期格式

            paragraph = new Paragraph("改征信报告生成于"+df.format(new Date())+",改文档呈现的内容为截止时间点的数据快照。", chinese);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setSpacingAfter(12);//设置内容段落间距
            cell.addElement(paragraph);
            table.addCell(cell);


            Image head = Image.getInstance("D:\\腾讯QQ\\QQ消息\\1083573600\\FileRecv\\PNG资料\\logoandname.png");
            head.setAlignment(Image.MIDDLE);//设置图片居中显示
            head.setWidthPercentage(100);
            cell = new PdfPCell();
            cell.setBorder(0);
            cell.addElement(head);
            paragraph = new Paragraph("企业征信报告", new Font(baseFont,12, Font.NORMAL, Color.BLACK));
            paragraph.setAlignment(Element.ALIGN_CENTER);//设置标题居中
            paragraph.setSpacingAfter(12);//设置内容段落间距
            cell.addElement(paragraph);
            table.addCell(cell);
            document.add(table);

            String sort="";
            String name="";
            paragraph = new Paragraph("注册信息1", new Font(baseFont,18, Font.NORMAL, Color.BLACK));
            paragraph.setAlignment(Element.ALIGN_LEFT);//设置标题居中
            document.add(paragraph);
            document.add(MyLine("",Color.blue));
            sort="登记号,分类 号,软件全称,软件简称,版本号,著作权人,登记日期";
            name="登记号,分类 号,软件全称,软件简称,版本号,著作权人,登记日期";
            float[] w = { 0.1f, 0.1f, 0.2f, 0.18f, 0.1f, 0.2f, 0.12f };
            List<Map<String, Object>> list=new ArrayList<>();
            Map<String, Object> map=new HashMap<>();
            for (int i = 0; i < 10; i++) {
                map.put("登记号", "2012SR 060677");
                map.put("分类 号", "201006000");
                map.put("软件全称", "智容移动互联网统 一接入网格化管理 平台");
                map.put("软件简称", "智容移 动互联 网统一 接入平 台");
                map.put("版本号", "V1.0");
                map.put("著作权人", "江西智容科技有限 公司:中国");
                map.put("登记日期", "2016-07-02");
                list.add(map);  
            }

            document.add(MyTable(list,sort,name,w));

            /**
             * 注册信息
             */
            paragraph = new Paragraph("注册信息", new Font(baseFont,18, Font.NORMAL, Color.BLACK));
            paragraph.setAlignment(Element.ALIGN_LEFT);//设置标题居中
            document.add(paragraph);
            document.add(MyLine("",Color.blue));
            sort=KEY;
            name="key01,key11,key21,key13,key41,key51,key61,key71";
            float[] ww = {0.8f, 0.2f};
            document.add(MyTable(getMap(),sort,name,ww));


            table = new PdfPTable(2);
            table.setWidthPercentage(100);//宽度为100%
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);//居中


            PdfPTable tableleft=MyTable(getMap(),KEY,KEY,w);
            tableleft.setWidthPercentage(100);//宽度为100%
            tableleft.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);//居中
            cell = new PdfPCell();
            cell.setBorder(0);
            cell.addElement(tableleft);
            table.addCell(cell);

            PdfPTable tableright=MyTable(getMap(),KEY,KEY,w);
            tableright.setWidthPercentage(100);//宽度为100%
            tableright.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);//居中
            cell = new PdfPCell();
            cell.setBorder(0);
            cell.addElement(tableright);
            table.addCell(cell);
            document.add(table);


            table=MyTable(getMap(),KEY,KEY,w);
            table.setWidthPercentage(50);//宽度为100%
            table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);//居中
            document.add(table);

            Chunk underline = new Chunk("hello,AJava.org ");
            underline.setUnderline(0.1f, -1f);
            document.add(underline);
            // 创建Chunk对象，设置下划线的厚度为1
            Chunk strike = new Chunk("欢迎到outofmemory.cn交流学习");
            strike.setUnderline(1f, 3f);
            document.add(strike);


            document.add(MyLine("",Color.blue));

            document.add(MyLine("江西智容科技有限公司",Color.red));
            document.close(); 
        } catch (Exception e) {
        	logger.error(e.toString(),e);
        }
    }


    /**
     * 显示表格，
     * @author 李海涛
     * @since 2016年8月4日下午4:06:57
     * @param list 显示在表格中的数据
     * @param sort 显示顺序（表头）,用,分割
     * @return PdfPTable 可以直接add到Document中
     * 
     */
    public static PdfPTable MyTable(List<Map<String,Object>> list, String sort,String name,float[] width){
        PdfPTable table = new PdfPTable(width);
        PdfPCell cell = new PdfPCell();//创建单元格
        try {
            BaseFont baseFont = BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);  
            Font chinese1 = new Font(baseFont,8, Font.NORMAL, Color.BLACK);//表头的字体
            Font chinese2 = new Font(baseFont,5, Font.NORMAL, Color.BLACK);//表格内容的字体
            Font chinese3 = new Font(baseFont,20, Font.NORMAL, Color.BLACK);//没有数据的字体
            table.setSpacingAfter(20);
            table.setWidthPercentage(100);//宽度为100%
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);//居中
            String sequence[]=sort.split(",");
            String sequencename[]=name.split(",");
            for (int i = 0; i < sequence.length; i++) {
                cell.setBackgroundColor(Color.decode("#00BFFF"));//表头背景颜色
                cell.setPhrase(new Phrase(sequencename[i], chinese1));//Phrase：短语；排名
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setPadding(8);
                cell.setBorder(Rectangle.BOX);
                cell.setBorderColor(Color.decode(BLACK));
                table.addCell(cell);//cell单元格
            }
            if(list.isEmpty()){
                cell.setPhrase(new Phrase("没有数据", chinese3));//Phrase：短语；排名
                cell.setBackgroundColor(Color.decode("#FFFFFF"));//表头背景颜色
                cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                cell.setPadding(8);
                cell.setColspan(sequencename.length);
                cell.setBorder(Rectangle.BOX);
                cell.setBorderColor(Color.decode(BLACK));
                table.addCell(cell);//cell单元格
            }else{
                for (int i = 0; i < list.size(); i++) {
                    Map<String,Object> map =list.get(i);
                    for (int j = 0; j < sequence.length; j++) {
                        try {
                            cell.setPhrase(new Phrase(map.get(sequence[j]).toString(), chinese2));//Phrase：短语；排名
                        } catch (Exception e) {
                            cell.setPhrase(new Phrase("", chinese2));//Phrase：短语；排名
                        }
                        cell.setBackgroundColor(Color.decode("#FFFFFF"));//表头背景颜色
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                        cell.setPadding(8);
                        cell.setBorder(Rectangle.BOX);
                        cell.setBorderColor(Color.decode(BLACK));
                        table.addCell(cell);//cell单元格
                    }
                }
            }

        } catch (Exception e) {}

        return table;
    }
    /**
     * 纵向显示表格，
     * @author 李海涛
     * @since 2016年8月4日下午4:06:57
     * @param tablemap 显示在表格中的数据
     * @param sort 显示顺序（表头）,用,分割
     * @return PdfPTable 可以直接add到Document中
     * 
     */
    public static PdfPTable MyTable(Map<String,Object> tablemap, String sort,String name,float[] width){
        PdfPTable table= new PdfPTable(width);
        PdfPCell cell = new PdfPCell();//创建单元格
        try {
            Font chinese = getFont();;

            table.setSpacingAfter(20);
            table.setWidthPercentage(100);//宽度为100%
            table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);//居中
            String sequence[]=sort.split(",");
            String sequencename[]=name.split(",");
            for (int i = 0; i < sequence.length; i++) {
                //标题
                //cell.setBackgroundColor(Color.decode("#00BFFF"));//表头背景颜色
                cell.setPhrase(new Phrase(sequencename[i], chinese));//Phrase：短语；排名
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                cell.setPadding(10);
                cell.setBorder(Rectangle.BOX);
                cell.setBorderColor(Color.decode(BLACK));
                table.addCell(cell);//cell单元格
                //内容
                try {
                    cell.setPhrase(new Phrase(tablemap.get(sequence[i]).toString(), chinese));//Phrase：短语；排名
                } catch (Exception e) {
                    cell.setPhrase(new Phrase("", chinese));//Phrase：短语；排名
                }
                //cell.setBackgroundColor(Color.decode("#00BFFF"));//表头背景颜色
                cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
                cell.setPadding(10);
                cell.setBorder(Rectangle.BOX);
                cell.setBorderColor(Color.decode(BLACK));
                table.addCell(cell);//cell单元格
            }

        } catch (Exception e) {

        }
        return table;
    }

    /**
     * 显示文字，
     * @author 李海涛
     * @since 2016年8月4日下午4:06:57
     * @param info 将要显示的文字
     * @return Paragraph 可以直接add到Document中
     * 
     */
    public static Paragraph MyParagraph(String info){
        try {

            Font chinese =getFont();
            Paragraph	paragraph = new Paragraph(info, chinese);//报告内容标题
            paragraph.setAlignment(Element.ALIGN_CENTER);//设置标题居中
            paragraph.setSpacingAfter(12);//设置内容段落间距
            return paragraph;
        } catch (Exception e) {}
        return null;
    }
    /**
     * 显示图片，
     * @author 李海涛
     * @since 2016年8月4日下午4:06:57
     * @param img 图片的路径
     * @return Image 可以直接add到Document中
     * 
     */
    public static Image MyImage(String img){
        Image image=null;
        try {
            image = Image.getInstance(img);
        } catch(Exception e) {}
        image.setWidthPercentage(100);
        image.setAlignment(Image.MIDDLE);//设置图片居中显示
        image.scalePercent(70);
        return image;
    }
    /**
     * 显示横线，
     * @author 李海涛
     * @since 2016年8月4日下午4:06:57
     * @param color 横线颜色
     * @return PdfPTable 可以直接add到Document中
     * @throws IOException 
     * @throws DocumentException 
     * 
     */
    public static PdfPTable MyLine(String info,Color color) throws DocumentException, IOException{
        PdfPTable table= new PdfPTable(1);
        PdfPCell cell=new PdfPCell();
        table.setWidthPercentage(100);//宽度为100%
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);//居中
        table.setSpacingAfter(5);
        table.setSpacingBefore(5);
        cell.setBorder(0);
        cell.setBackgroundColor(color);
        Font chinese = getFont();
        Paragraph	paragraph = new Paragraph(info, chinese);//报告内容标题
        paragraph.setAlignment(Element.ALIGN_CENTER);//设置标题居中
        paragraph.setSpacingAfter(12);//设置内容段落间距
        if(!info.isEmpty()){
            cell.addElement(paragraph);
        }
        table.addCell(cell);
        return table;
    }




    public static Map<String,Object> getMap(){
        Map<String,Object> map =new HashMap<String,Object>();
        for (int i = 0; i <= 7; i++) {
            map.put("key"+i, i);
        }
        return map;
    }
    public static Font getFont() throws DocumentException, IOException{
        BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font chinese = new Font(baseFont, 8, Font.NORMAL, Color.BLACK);
        return chinese;
    }
}  