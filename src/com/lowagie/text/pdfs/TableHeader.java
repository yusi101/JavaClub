package com.lowagie.text.pdfs;

import com.JavaClub.util.Logger;
import com.lowagie.text.Document;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;

public class TableHeader extends PdfPageEventHelper{
	
  protected Logger logger = Logger.getLogger(this.getClass());
  private static final String BRAND = "#19A5DA";
  String header;
  PdfTemplate total;
  int OnStartPage = 0;
  BaseFont baseFont;
  String imagePath;
  String entname;

  public TableHeader()
  {
    try
    {
      this.baseFont = MakePDF.getBaseFontYh();
    } catch (Exception e) {
    	logger.error(e.toString(),e);
    }
  }

  public void setHeader(String header) { this.header = header; }

  public void setImagePath(String imagePath)
  {
    this.imagePath = imagePath;
  }

  public void setEntname(String entname) {
    this.entname = entname;
  }

  public void setOnStartPage(int OnStartPage)
  {
    this.OnStartPage = OnStartPage;
  }

  public void onOpenDocument(PdfWriter writer, Document document) {
    this.total = writer.getDirectContent().createTemplate(30.0F, 16.0F);
  }

  public void onEndPage(PdfWriter writer, Document document) {
    if (writer.getPageNumber() > this.OnStartPage)
      try {
        setPdfFooter(writer);
        setPdfheader(writer);
      }
      catch (Exception de)
      {
        throw new ExceptionConverter(de);
      }
  }

  public void setPdfheader(PdfWriter writer)
    throws Exception
  {
    PdfPTable table = new PdfPTable(3);
    try
    {
      table.setWidths(new float[] { 0.2F, 0.3F, 0.5F });
      table.setTotalWidth(520.0F);
      table.setLockedWidth(true);
      table.setHorizontalAlignment(1);
      table.getDefaultCell().setFixedHeight(60.0F);
      table.getDefaultCell().setBorder(2);
      table.getDefaultCell().setBorderColor(Color.decode(BRAND));
      Image logoname = Image.getInstance(this.imagePath + "/logoandname.png");
      logoname.scaleAbsolute(45.0F, 50.0F);
      PdfPCell cell = new PdfPCell(logoname);
      cell.setPadding(5.0F);
      cell.setBorder(2);
      cell.setBorderColor(Color.decode(BRAND));
      cell.setBorderWidth(2.0F);
      cell.setHorizontalAlignment(0);
      table.addCell(cell);

      Image sincerity = Image.getInstance(this.imagePath + "/sincerity.png");
      sincerity.scaleAbsolute(126.0F, 40.0F);
      cell = new PdfPCell(sincerity);
      cell.setPadding(10.0F);
      cell.setBorder(2);
      cell.setBorderColor(Color.decode(BRAND));
      cell.setBorderWidth(2.0F);
      cell.setHorizontalAlignment(1);
      table.addCell(cell);

      Phrase phrase = new Phrase(this.entname + "信用报告", new Font(this.baseFont, 12.0F, 0, Color.decode("#000000")));
      cell = new PdfPCell(phrase);
      cell.setHorizontalAlignment(2);
      cell.setVerticalAlignment(6);
      cell.setPaddingBottom(10.0F);
      cell.setPaddingRight(10.0F);
      cell.setBorderColor(Color.decode(BRAND));
      cell.setBorderWidth(2.0F);
      cell.setBorder(2);

      table.addCell(cell);

      table.writeSelectedRows(0, -1, 38.0F, 840.0F, writer.getDirectContent());
    } catch (Exception de) {
      throw new ExceptionConverter(de);
    }
  }

  public void setPdfFooter(PdfWriter writer)
    throws Exception
  {
    PdfPTable table = new PdfPTable(3);
    try
    {
      table.setWidths(new int[] { 24, 24, 2 });
      table.setTotalWidth(520.0F);
      table.setLockedWidth(true);
      table.getDefaultCell().setFixedHeight(20.0F);
      table.getDefaultCell().setBorder(1);
      table.addCell(this.header);

      Phrase phrase = new Phrase("第  " + (writer.getPageNumber() - this.OnStartPage) + " 页 共", new Font(this.baseFont, 14.0F, 0, Color.BLACK));
      PdfPCell cell = new PdfPCell(phrase);
      cell.setBorder(1);
      cell.setHorizontalAlignment(2);
      table.addCell(cell);
      cell = new PdfPCell(Image.getInstance(this.total));
      cell.setBorder(1);
      table.addCell(cell);

      table.writeSelectedRows(0, -1, 38.0F, 30.0F, writer.getDirectContent());
    } catch (Exception de) {
      throw new ExceptionConverter(de);
    }
  }
  public void onCloseDocument(PdfWriter writer, Document document) {
	try {
	  Phrase phr = new Phrase(String.valueOf(writer.getPageNumber() - 1 - this.OnStartPage) + "页", new Font(this.baseFont, 14.0F, 0, Color.BLACK));
      ColumnText.showTextAligned(this.total, 0, phr, 0.0F, 0.0F, 0.0F);
    }
    catch (Exception localException)
    {
    	logger.error(localException.toString(),localException);
    }
  }
}