package com.lowagie.text.pdfs;

import com.JavaClub.util.Logger;
import com.JavaClub.util.StringUtil;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PdfUtil
{
  protected static Logger logger = Logger.getLogger(PdfUtil.class);
  private static final String CURRENTINDEX_ONE = "CURRENTINDEX_ONE";
  public static final String[] hanziArr = { "个", "十", "百", "千", "万", "十", "百", "千", "亿" };
  public static final String[] numberArr = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "点" };

  public static boolean mergePdfFiles(String[] files, String saveFile, int del)
  {
    try
    {
      Document document = new Document(new PdfReader(files[0]).getPageSize(1));
      PdfCopy copy = new PdfCopy(document, new FileOutputStream(saveFile));
      document.open();
      for (int i = 0; i < files.length; i++) {
        PdfReader reader = new PdfReader(files[i]);
        int n = reader.getNumberOfPages();
        for (int j = 1; j <= n; j++) {
          document.newPage();
          PdfImportedPage page = copy.getImportedPage(reader, j);
          copy.addPage(page);
        }
      }
      document.close();
      if (del == 1) {
        delFile(files);
      }
      return true;
    }
    catch (Exception e) {
    	logger.error(e.toString(),e);
    }return false;
  }

  public static void delFile(String[] files)
  {
    try
    {
      for (int i = 0; i < files.length; i++) {
        String filePath = files[i];
        File myDelFile = new File(filePath);
        myDelFile.delete();
      }
    } catch (Exception e) {
      logger.error("删除文件操作出错",e);
    }
  }

  public static void main1(String[] args)
  {
    String[] files = { "D:/泰豪软件股份有限公司1.pdf", "D:/泰豪软件股份有限公司2.pdf" };
    mergePdfFiles(files, "D:/泰豪软件股份有限公司100.pdf", 1);
  }

  public static String toHanzi(String s)
  {
    StringBuilder result = new StringBuilder();
    int pointIndex = s.indexOf('.');

    String decimal = "";
    String integer;
    if (-1 == pointIndex) {
      integer = s;
    } else {
      integer = s.substring(0, pointIndex);
      decimal = s.substring(pointIndex + 1);
    }
    for (int i = 0; i < integer.length(); i++) {
      char c = integer.charAt(i);
      if (c != '0')
      {
        result.append(numberArr[Integer.parseInt(String.valueOf(c))]);
        if (i != integer.length() - 1)
          result.append(hanziArr[integer.length() - 1 - i]);
      }
    }
    if ((decimal != null) && (!"".equals(decimal))) {
      result.append(numberArr[11]);
    }
    if(!StringUtil.isEmpty(decimal)){
	    for (int i = 0; i < decimal.length(); i++) {
	      char c = decimal.charAt(i);
	      result.append(numberArr[Integer.parseInt(String.valueOf(c))]);
	    }
    }
    return result.toString().replace("一十", "十");
  }

  public static String toHanzi(int number) {
    String hanzi = toHanzi(number);
    //number++;
    return hanzi;
  }

  public static void ssd(Map<String, Object> CURRENTINDEX) {
    CURRENTINDEX.put(CURRENTINDEX_ONE, Integer.valueOf(Integer.parseInt(CURRENTINDEX.get(CURRENTINDEX_ONE).toString()) + 1));
  }

  public static void main(String[] args) {
    Map<String, Object> CURRENTINDEX = new HashMap<>();
    CURRENTINDEX.put(CURRENTINDEX_ONE, Integer.valueOf(1));
    CURRENTINDEX.put("CURRENTINDEX_TWO", Integer.valueOf(1));
    ssd(CURRENTINDEX);
    System.out.println(CURRENTINDEX.toString());
  }
}