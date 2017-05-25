package com.lowagie.text.pdfs;

import com.JavaClub.util.Logger;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;

public class MakePDF
{
  protected static Logger logger = Logger.getLogger(MakePDF.class);
  private static final String BLACK = "#000000";
  private static final String WHITE = "#FFFFFF";
  
  public static void main(String[] args)
  {
    Test_MakePDF();
  }
  public static void Test_MakePDF() {
    try {
      Document document = new Document(PageSize.A4, 36.0F, 36.0F, 30.0F, 20.0F);
      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D:\\testHeaderAndFooter.pdf"));
      TableHeader header = new TableHeader();
      header.setOnStartPage(1);
      header.setImagePath("D:\\腾讯QQ\\QQ消息\\1083573600\\FileRecv\\PNG资料\\");
      writer.setPageEvent(header);
      Rectangle rect = new Rectangle(36.0F, 54.0F, 559.0F, 788.0F);
      rect.setBorderColor(Color.black);
      writer.setBoxSize("art", rect);
      writer.setPageEvent(header);

      BaseFont baseFont = getBaseFontSt();
      Font chinese = getFont();

      document.open();

      float[] width = { 0.8F, 0.2F };
      PdfPTable table = new PdfPTable(width);
      table.setSpacingBefore(40.0F);
      table.setWidthPercentage(100.0F);
      table.setHorizontalAlignment(1);

      PdfPCell cell = new PdfPCell();
      cell.setBorder(0);
      Paragraph paragraph = new Paragraph("江西智容科技", new Font(baseFont, 30.0F, 0, Color.BLACK));
      paragraph.setAlignment(0);

      paragraph.setSpacingAfter(12.0F);
      cell.addElement(paragraph);
      paragraph = new Paragraph("(企业征信报告)", chinese);
      paragraph.setAlignment(0);
      paragraph.setSpacingAfter(12.0F);
      cell.addElement(paragraph);
      SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒");

      paragraph = new Paragraph("改征信报告生成于" + df.format(new Date()) + ",改文档呈现的内容为截止时间点的数据快照。", chinese);
      paragraph.setAlignment(0);
      paragraph.setSpacingAfter(12.0F);
      cell.addElement(paragraph);
      table.addCell(cell);

      Image head = Image.getInstance("G:/IMG_20141203_092851.jpg");
      head.setAlignment(1);
      head.setWidthPercentage(100.0F);
      cell = new PdfPCell();
      cell.setBorder(0);
      cell.addElement(head);
      paragraph = new Paragraph("企业征信报告", new Font(baseFont, 12.0F, 0, Color.BLACK));
      paragraph.setAlignment(1);
      paragraph.setSpacingAfter(12.0F);
      cell.addElement(paragraph);
      table.addCell(cell);
      document.add(table);

      table = new PdfPTable(width);
      table.setSpacingBefore(40.0F);
      table.setWidthPercentage(100.0F);
      table.setHorizontalAlignment(1);

      head = Image.getInstance("http://tm-image.qichacha.com/4d47b506a565fb95a0b75f2efa86dc4e.jpg@100h_160w_1l_50q");
      head.setAlignment(1);
      head.setWidthPercentage(100.0F);
      cell = new PdfPCell(head);
      cell.setBorder(0);
      table.addCell(cell);

      cell = new PdfPCell(head);
      paragraph = new Paragraph("企业征信报告", new Font(baseFont, 12.0F, 0, Color.BLACK));
      paragraph.setAlignment(1);
      paragraph.setSpacingAfter(12.0F);
      cell.addElement(paragraph);
      table.addCell(cell);
      document.add(table);

      paragraph = new Paragraph("注册信息1", new Font(baseFont, 18.0F, 0, Color.BLACK));
      paragraph.setAlignment(0);
      document.add(paragraph);
      document.add(MyLine("", Color.decode("#ADABAB")));
      String sort = "登记号,分类 号,软件全称,软件简称,版本号,著作权人,登记日期";
      String name = "登记号,分类 号,软件全称,软件简称,版本号,著作权人,登记日期";
      float[] w = { 0.1F, 0.1F, 0.2F, 0.18F, 0.1F, 0.2F, 0.12F };
      List<Map<String, Object>> list = new ArrayList<>();
      for (int i = 0; i < 10; i++) {
        Map<String, Object> map = new HashMap<>();
        map.put("登记号", "http://tm-image.qichacha.com/4d47b506a565fb95a0b75f2efa86dc4e.jpg@100h_160w_1l_50q");
        map.put("分类 号", "201006000");
        map.put("软件全称", "智容移动互联网统 一接入网格化管理 平台");
        map.put("软件简称", "智容移 动互联 网统一 接入平 台");
        map.put("版本号", "V1.0" + i);
        map.put("著作权人", "江西智容科技有限 公司:中国");
        map.put("登记日期", "2016-07-02");
        list.add(map);
      }
      MyTable(list, sort, name, w, new int[] { 2 }, 1);
      document.add(MyTable(list, sort, name, w, new int[] { 2 }, 1));
      document.add(MyTable(list, sort, name, w, 1));

      paragraph = new Paragraph("注册信息", new Font(baseFont, 18.0F, 0, Color.BLACK));
      paragraph.setAlignment(0);
      document.add(paragraph);
      document.add(MyLine("", Color.blue));
      sort = "key0,key1,key2,key3,key4,key5,key6,key7";
      name = "key01,key11,key21,key13,key41,key51,key61,key71";
      float[] ww = { 0.8F, 0.2F };
      document.add(MyTable(getMap(), sort, name, ww));

      table = new PdfPTable(2);
      table.setWidthPercentage(100.0F);
      table.setHorizontalAlignment(1);

      PdfPTable tableleft = MyTable(getMap(), "key0,key1,key2,key3,key4,key5,key6,key7", "key0,key1,key2,key3,key4,key5,key6,key7", w);
      tableleft.setWidthPercentage(100.0F);
      tableleft.setHorizontalAlignment(0);
      cell = new PdfPCell();
      cell.setBorder(0);
      cell.addElement(tableleft);
      table.addCell(cell);

      PdfPTable tableright = MyTable(getMap(), "key0,key1,key2,key3,key4,key5,key6,key7", "key0,key1,key2,key3,key4,key5,key6,key7", w);
      tableright.setWidthPercentage(100.0F);
      tableright.setHorizontalAlignment(0);
      cell = new PdfPCell();
      cell.setBorder(0);
      cell.addElement(tableright);
      table.addCell(cell);
      document.add(table);

      table = MyTable(getMap(), "key0,key1,key2,key3,key4,key5,key6,key7", "key0,key1,key2,key3,key4,key5,key6,key7", w);
      table.setWidthPercentage(50.0F);
      table.setHorizontalAlignment(0);
      document.add(table);

      Chunk underline = new Chunk("hello,AJava.org ");
      underline.setUnderline(0.1F, -1.0F);
      document.add(underline);

      Chunk strike = new Chunk("欢迎到outofmemory.cn交流学习");
      strike.setUnderline(1.0F, 3.0F);
      document.add(strike);

      document.add(MyLine("", Color.blue));

      document.add(MyLine("江西智容科技有限公司", Color.red));
      document.close();
    }
    catch (Exception localException)
    {
    	logger.error(localException.toString(),localException);
    }
  }

  public static PdfPTable MyTable(List<Map<String, Object>> list, String sort, String name, float[] width, int serial)
  {
    if (serial > 0) {
      for (int i = 0; i < list.size(); i++) {
        Map<String, Object> map = list.get(i);
        map.put("SERIAL", Integer.valueOf(i + 1));
        list.set(i, map);
      }
      return MyTable(list, ("SERIAL," + sort), ("序号," + name), ArrayUtils.add(width, 0, 0.1F));
    }else{
    	return MyTable(list, sort, name, width);
    }
    
  }

  public static PdfPTable MyTable(List<Map<String, Object>> list, String sort, String name, float[] width, int[] imagePosition, int serial)
  {
    if (serial > 0) {
      for (int i = 0; i < list.size(); i++) {
    	  Map<String, Object> map = list.get(i);
        map.put("SERIAL", Integer.valueOf(i + 1));
        list.set(i, map);
      }
      return MyTable(list, ("SERIAL," + sort), ("序号," + name), (ArrayUtils.add(width, 0, 0.1F)), imagePosition);
    }else{
      return MyTable(list, sort, name, width, imagePosition);
    }
  
  }

  public static PdfPTable MyTable(List<Map<String, Object>> list, String sort, String name, float[] width)
  {
    ArrayUtils.add(width, 4.0F);
    PdfPTable table = new PdfPTable(width);
    PdfPCell cell = new PdfPCell();
    try {
      BaseFont baseFont = getBaseFontYh();
      Font chinese1 = new Font(baseFont, 10.0F, 1, Color.BLACK);
      Font chinese2 = new Font(baseFont, 8.0F, 0, Color.BLACK);
      Font chinese3 = new Font(baseFont, 20.0F, 0, Color.BLACK);
      table.setSpacingAfter(20.0F);
      table.setWidthPercentage(100.0F);
      table.setHorizontalAlignment(1);
      String[] sequence = sort.split(",");
      String[] sequencename = name.split(",");
      for (int i = 0; i < sequence.length; i++) {
        cell.setBackgroundColor(Color.decode("#F7F3F3"));
        cell.setPhrase(new Phrase(sequencename[i], chinese1));
        cell.setUseAscender(true);
        cell.setPadding(5.0F);
        cell.setHorizontalAlignment(1);
        cell.setVerticalAlignment(5);
        cell.setLeading(0.0F, 1.5F);
        cell.setBorder(15);
        cell.setBorderColor(Color.decode(BLACK));
        table.addCell(cell);
      }
      if (list.isEmpty()) {
        cell.setPhrase(new Phrase("没有记录", chinese3));
        cell.setBackgroundColor(Color.decode(WHITE));
        cell.setHorizontalAlignment(1);
        cell.setPadding(8.0F);
        cell.setColspan(sequencename.length);
        cell.setBorder(15);
        cell.setBorderColor(Color.decode(BLACK));
        table.addCell(cell);
      } else {
        for (int i = 0; i < list.size(); i++) {
        	 Map<String, Object> map = list.get(i);
          for (int j = 0; j < sequence.length; j++) {
            try {
              cell.setPhrase(new Paragraph(map.get(sequence[j]).toString(), chinese2));
            } catch (Exception e) {
            	logger.error(e.toString(),e);
              cell.setPhrase(new Phrase("", chinese2));
            }
            cell.setBackgroundColor(Color.decode(WHITE));
            cell.setHorizontalAlignment(1);
            cell.setUseAscender(true);
            cell.setPadding(5.0F);
            cell.setVerticalAlignment(5);
            cell.setLeading(0.0F, 1.5F);
            cell.setBorder(15);
            cell.setBorderColor(Color.decode(BLACK));
            table.addCell(cell);
          }
        }
      }
    } catch (Exception localException1) { 
    	logger.error(localException1.toString(),localException1);
    }

    return table;
  }

  public static PdfPTable MyTable(List<Map<String, Object>> list, String sort, String name, float[] width, int[] imagePosition)
  {
    PdfPTable table = new PdfPTable(width);
    PdfPCell cell = new PdfPCell();
    try {
      BaseFont baseFont = getBaseFontYh();
      Font chinese1 = new Font(baseFont, 10.0F, 1, Color.BLACK);
      Font chinese2 = new Font(baseFont, 8.0F, 0, Color.BLACK);
      Font chinese3 = new Font(baseFont, 20.0F, 0, Color.BLACK);
      table.setSpacingAfter(20.0F);
      table.setWidthPercentage(100.0F);
      table.setHorizontalAlignment(1);
      String[] sequence = sort.split(",");
      String[] sequencename = name.split(",");
      for (int i = 0; i < sequence.length; i++) {
        cell.setBackgroundColor(Color.decode("#F7F3F3"));
        cell.setPhrase(new Phrase(sequencename[i], chinese1));
        cell.setUseAscender(true);
        cell.setPadding(5.0F);
        cell.setHorizontalAlignment(1);
        cell.setVerticalAlignment(5);
        cell.setLeading(0.0F, 1.5F);
        cell.setBorder(15);
        cell.setBorderColor(Color.decode(BLACK));
        table.addCell(cell);
      }
      if (list.isEmpty()) {
        cell.setPhrase(new Phrase("没有记录", chinese3));
        cell.setBackgroundColor(Color.decode(WHITE));
        cell.setHorizontalAlignment(1);
        cell.setPadding(8.0F);
        cell.setColspan(sequencename.length);
        cell.setBorder(15);
        cell.setBorderColor(Color.decode(BLACK));
        table.addCell(cell);
      } else {
        for (int i = 0; i < list.size(); i++) {
        	 Map<String, Object> map = list.get(i);
          for (int j = 0; j < sequence.length; j++) {
            try {
              if (ArrayUtils.contains(imagePosition, j + 1)) {
                Image image = Image.getInstance(map.get(sequence[j]).toString());
                image.setAlignment(1);
                image.setWidthPercentage(90.0F);
                cell.addElement(image);
              } else {
                cell.setPhrase(new Paragraph(map.get(sequence[j]).toString(), chinese2));
              }
            } catch (Exception e) {
            	logger.error(e.toString(),e);
              cell.setPhrase(new Phrase("", chinese2));
            }
            cell.setBackgroundColor(Color.decode(WHITE));
            cell.setHorizontalAlignment(1);
            cell.setUseAscender(true);
            cell.setPadding(5.0F);
            cell.setVerticalAlignment(5);
            cell.setLeading(0.0F, 1.5F);
            cell.setBorder(15);
            cell.setBorderColor(Color.decode(BLACK));
            table.addCell(cell);
          }
        }
      }
    } catch (Exception localException1) { 
    	logger.error(localException1.toString(),localException1);
    }

    return table;
  }

  public static PdfPTable MyTable(Map<String, Object> tablemap, String sort, String name, float[] width)
  {
    PdfPTable table = new PdfPTable(width);
    PdfPCell cell = new PdfPCell();
    try {
      Font chinese = new Font(getBaseFontYh(), 10.0F, 0, Color.BLACK);
      table.setSpacingAfter(20.0F);
      table.setWidthPercentage(100.0F);
      String[] sequence = sort.split(",");
      String[] sequencename = name.split(",");
      for (int i = 0; i < sequence.length; i++)
      {
        cell.setPhrase(new Phrase(sequencename[i], new Font(getBaseFontYh(), 12.0F, 1, Color.BLACK)));
        cell.setUseAscender(true);
        cell.setPadding(10.0F);
        cell.setVerticalAlignment(5);
        cell.setHorizontalAlignment(0);
        cell.setBorder(15);
        cell.setBorderColor(Color.decode(BLACK));
        table.addCell(cell);
        try
        {
          cell.setPhrase(new Phrase(tablemap.get(sequence[i]).toString(), chinese));
        } catch (Exception e) {
        	logger.error(e.toString(),e);
          cell.setPhrase(new Phrase("", chinese));
        }

        cell.setUseAscender(true);
        cell.setPadding(10.0F);
        cell.setVerticalAlignment(5);
        cell.setHorizontalAlignment(0);
        cell.setLeading(0.0F, 1.5F);
        cell.setBorder(15);
        cell.setBorderColor(Color.decode(BLACK));
        table.addCell(cell);
      }
    }
    catch (Exception localException1)
    {
    	logger.error(localException1.toString(),localException1);
    }
    return table;
  }

  public static Paragraph MyParagraph(String info)
  {
    try
    {
      Font chinese = getFont();
      Paragraph paragraph = new Paragraph(info, chinese);
      paragraph.setAlignment(1);
      paragraph.setSpacingAfter(12.0F);
      return paragraph; } catch (Exception localException) {
    }
    return null;
  }

  public static Image MyImage(String img)
  {
    Image image = null;
    try {
      image = Image.getInstance(img); } catch (Exception localException) {
    }
    image.setWidthPercentage(100.0F);
    image.setAlignment(1);
    image.scalePercent(70.0F);
    return image;
  }

  public static PdfPTable MyLine(String info, Color color)
    throws DocumentException, IOException
  {
    PdfPTable table = new PdfPTable(1);
    PdfPCell cell = new PdfPCell();
    table.setWidthPercentage(100.0F);
    table.setHorizontalAlignment(1);
    table.setSpacingAfter(5.0F);
    table.setSpacingBefore(15.0F);
    cell.setBorder(0);
    cell.setBackgroundColor(color);
    Font chinese = getFont();
    Paragraph paragraph = new Paragraph(info, chinese);
    paragraph.setAlignment(1);
    paragraph.setSpacingAfter(12.0F);
    if (!info.isEmpty()) {
      cell.addElement(paragraph);
    }
    table.addCell(cell);
    return table;
  }

  public static Map<String, Object> getMap()
  {
    Map<String, Object> map = new HashMap<>();
    for (int i = 0; i <= 7; i++) {
      map.put("key" + i, Integer.valueOf(i));
    }
    return map;
  }
  public static BaseFont getBaseFontYh() throws DocumentException, IOException {
    /*BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/msyh.ttc,1", "Identity-H", true);*/
	  return BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
  }
  public static BaseFont getBaseFontSt() throws DocumentException, IOException {
	  return BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
  }
  public static Font getFont() throws DocumentException, IOException {
	  return new Font(getBaseFontYh(), 8.0F, 0, Color.BLACK);
  }
}