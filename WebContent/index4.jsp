<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page
	import="java.io.ByteArrayOutputStream,
	java.io.DataOutput,java.io.DataOutputStream,
	java.awt.Color,
java.io.FileOutputStream,
com.lowagie.text.Document,
com.lowagie.text.Element,
com.lowagie.text.Font,
com.lowagie.text.PageSize,
com.lowagie.text.Paragraph,
com.lowagie.text.pdf.BaseFont,
com.lowagie.text.pdf.PdfPCell,
com.lowagie.text.pdf.PdfPTable,
com.lowagie.text.pdf.PdfWriter,
com.lowagie.text.pdfs.MakePDF
"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>联络员信息</title>
<script src="${pageContext.request.contextPath}/static/scripts/util/jquery-1.9.1.min.js"></script>
<%
	response.setContentType("application/pdf;charset=UTF-8");
	response.setHeader("Content-Disposition", "inline;filename=informBook.pdf");
	 Document document = new Document(PageSize.A4, 36, 36,30, 20);
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	PdfWriter writer = PdfWriter.getInstance(document, bos);
	BaseFont baseFont = MakePDF.getBaseFontSt();
	Paragraph paragraph; 
    PdfPTable table;
    PdfPCell cell ;//创建单元格
    
	document.open();
	float[] width = {0.8f, 0.2f};//可以设置表格的宽度
    table = new PdfPTable(width);
    table.setSpacingBefore(40);
    table.setWidthPercentage(100);//宽度为100%
    table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);//居中

    cell = new PdfPCell();
    cell.setBorder(0);
    paragraph = new Paragraph("江西智容科技", new Font(baseFont,30, Font.NORMAL, Color.BLACK));//设置文字大小
    paragraph.setAlignment(Element.ALIGN_LEFT);

   
    /**
     * 注册信息
     */
    paragraph = new Paragraph("注册信息", new Font(baseFont,18, Font.NORMAL, Color.BLACK));
    paragraph.setAlignment(Element.ALIGN_LEFT);//设置标题居中
    document.add(paragraph);
    document.add(MakePDF.MyLine("",Color.blue));
    String sort="key0,key1,key2,key3,key4,key5,key6,key7";
    String name="key01,key11,key21,key13,key41,key51,key61,key71";
    float[] ww = {0.8f, 0.2f};
    document.add(MakePDF.MyTable(MakePDF.getMap(),sort,name,ww));

    table = new PdfPTable(2);
    table.setWidthPercentage(100);//宽度为100%
    table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);//居中


    PdfPTable tableleft=MakePDF.MyTable(MakePDF.getMap(),"key0,key1,key2,key3,key4,key5,key6,key7","key0,key1,key2,key3,key4,key5,key6,key7",ww);
    tableleft.setWidthPercentage(100);//宽度为100%
    tableleft.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);//居中
    cell = new PdfPCell();
    cell.setBorder(0);
    cell.addElement(tableleft);
    table.addCell(cell);

    document.add(MakePDF.MyLine("江西智容科技有限公司",Color.red));
    document.close(); 

	//在页面上写出内容
	DataOutputStream output = new DataOutputStream(response.getOutputStream());
	byte[] bytes = bos.toByteArray();
	response.setContentLength(bytes.length);
	for (int i = 0; i < bytes.length; i++) {
		output.writeByte(bytes[i]);
	}
	output.flush();//刷新
	//out --jsp   
	out.clear();
	out = pageContext.pushBody();
%>
</head>
<script type="text/javascript">
$(function(){ 
	$("#toolbar").hide();
});
</script>
<body>
</body>
</html>