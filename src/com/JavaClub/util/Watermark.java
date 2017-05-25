package com.JavaClub.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.swetake.util.Qrcode;

public class Watermark {

	protected static Logger logger = Logger.getLogger(Watermark.class);

	public static float THIRD_ARC=0.14f ;//第三圈的位置


	/**
	 * 把图片印刷到图片上
	 * 
	 * @param pressImg --
	 *            水印文件
	 * @param targetImg --
	 *            目标文件
	 * @param x
	 *            --x坐标
	 * @param y
	 *            --y坐标
	 */
	public final static void pressImage(String pressImg, String targetImg,
			int x, int y) {
		try {
			//目标文件
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);

			//水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			//g.drawImage(src_biao, (wideth - wideth_biao) / 2,(height - height_biao) / 2, wideth_biao, height_biao, null);
			g.drawImage(src_biao, x, y, wideth_biao, height_biao, null);
			//水印文件结束
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
	}

	/**
	 * 打印文字水印图片
	 * 
	 * @param pressText
	 *            --文字
	 * @param targetImg --
	 *            目标图片
	 * @param fontName --
	 *            字体名
	 * @param fontStyle --
	 *            字体样式
	 * @param color --
	 *            字体颜色
	 * @param fontSize --
	 *            字体大小
	 * @param x --
	 *            偏移量
	 * @param y
	 */

	public static void pressText(String pressText, String targetImg,
			String fontName, int fontStyle, Color color, int fontSize, int x,int y) {
		try {
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.drawString(pressText, x, y);
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
	}  

	public static void getSealBottom(String content, String filename) {
		try {
			File _file = new File(filename);
			Image src = ImageIO.read(_file);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			int circleRadius = Math.min(width,height)/2;
			g.setPaint(Color.decode("#1F479F"));//
			g.setStroke(new BasicStroke(5));//设置画笔的粗度

			int fontSize = 30;
			Font f = new Font("黑体",Font.BOLD,fontSize);
			FontRenderContext context = g.getFontRenderContext();
			Rectangle2D bounds = f.getStringBounds(content,context);

			double msgWidth = bounds.getWidth();
			int countOfMsg = content.length();
			double interval = msgWidth/(countOfMsg-1);//计算间距


			double newRadius = circleRadius + bounds.getY()+10;//bounds.getY()是负数，这样可以将弧形文字固定在圆内了。-5目的是离圆环稍远一点
			double radianPerInterval = 2 * Math.asin(interval / (1.8 * newRadius));//每个间距对应的角度   越小距离越大

			//第一个元素的角度
			double firstAngle;
			if(countOfMsg % 2 == 1){//奇数
				firstAngle = (countOfMsg-1)*radianPerInterval/2.0 + Math.PI/2+0.08;
			}else{//偶数
				firstAngle = (countOfMsg/2.0-1)*radianPerInterval + radianPerInterval/2.0 +Math.PI/2+0.08;
			}
			for(int i = 0;i<countOfMsg;i++){
				double aa = firstAngle - i*radianPerInterval;
				double ax = newRadius * Math.sin(Math.PI/2 - aa);//小小的trick，将【0，pi】区间变换到[pi/2,-pi/2]区间
				double ay = newRadius * Math.cos(aa-Math.PI/2);//同上类似，这样处理就不必再考虑正负的问题了
				AffineTransform transform = AffineTransform .getRotateInstance(aa-Math.PI/2);// ,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g.setFont(f2);
				g.drawString(content.substring(i,i+1), (float) (circleRadius+ax),  (float) (circleRadius + ay));
			}
			g.dispose();//销毁资源
			FileOutputStream out = new FileOutputStream(filename);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
		}
	}
	public static void getSealTop(String content, String filename) {
		try {
			File _file = new File(filename);
			Image src = ImageIO.read(_file);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = image.createGraphics();
			int circleRadius = Math.min(width,height)/2;
			/***********draw circle*************/
			g2d.drawImage(src, 0, 0, width, height, null);
			g2d.setPaint(Color.decode("#1F479F"));//
			g2d.setStroke(new BasicStroke(5));//设置画笔的粗度
			GradientPaint gradient_1=new GradientPaint(10,0,Color.decode("#1282CB"),0,0,Color.decode("#002061"),true);
			g2d.setPaint(gradient_1);

			int fontSize = 30;
			Font f = new Font("新宋体",Font.BOLD,fontSize);
			FontRenderContext context = g2d.getFontRenderContext();
			Rectangle2D bounds = f.getStringBounds(content,context);

			double msgWidth = bounds.getWidth();
			int countOfMsg = content.length();
			double interval = msgWidth/(countOfMsg-1);//计算间距


			double newRadius = circleRadius + bounds.getY()-15;//bounds.getY()是负数，这样可以将弧形文字固定在圆内了。-5目的是离圆环稍远一点
			double radianPerInterval = 2 * Math.asin(interval / (2.2 * newRadius));//每个间距对应的角度   越大距离越大

			//第一个元素的角度
			double firstAngle;
			if(countOfMsg % 2 == 1){//奇数
				firstAngle = (countOfMsg-1)*radianPerInterval/2.0 + Math.PI/2+0.08;
			}else{//偶数
				firstAngle = (countOfMsg/2.0-1)*radianPerInterval + radianPerInterval/2.0 +Math.PI/2+0.08;
			}
			for(int i = 0;i<countOfMsg;i++){
				double aa = firstAngle - i*radianPerInterval;
				double ax = newRadius * Math.sin(Math.PI/2 - aa);//小小的trick，将【0，pi】区间变换到[pi/2,-pi/2]区间
				double ay = newRadius * Math.cos(aa-Math.PI/2);//同上类似，这样处理就不必再考虑正负的问题了
				AffineTransform transform = AffineTransform .getRotateInstance(Math.PI/2 - aa);// ,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g2d.setFont(f2);
				g2d.drawString(content.substring(i,i+1), (float) (circleRadius+ax),  (float) (circleRadius - ay));
			}
			g2d.dispose();//销毁资源
			FileOutputStream out = new FileOutputStream(filename);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
		}
	}
	/**
	 * 给图片上面加上圆环文字
	 * @author 李海涛
	 * @param image 底图
	 * @param content 文字内容
	 * @return
	 */
	public static BufferedImage getSealTop(BufferedImage image,String content) {
		try {

			int width = image.getWidth();
			int height = image.getHeight();
			Graphics2D g2d = image.createGraphics();
			int circleRadius = Math.min(width,height)/2;
			/***********draw circle*************/
			g2d.setPaint(Color.decode("#1F479F"));//
			g2d.setStroke(new BasicStroke(5));//设置画笔的粗度
			GradientPaint gradient_1=new GradientPaint(10,0,Color.decode("#1282CB"),0,0,Color.decode("#002061"),true);
			g2d.setPaint(gradient_1);

			int fontSize = 30;
			Font f = new Font("新宋体",Font.BOLD,fontSize);
			FontRenderContext context = g2d.getFontRenderContext();
			Rectangle2D bounds = f.getStringBounds(content,context);

			double msgWidth = bounds.getWidth();
			int countOfMsg = content.length();
			double interval = msgWidth/(countOfMsg-1);//计算间距


			double newRadius = circleRadius + bounds.getY()-(int)(width*0.031f);//bounds.getY()是负数，这样可以将弧形文字固定在圆内了。-5目的是离圆环稍远一点
			double radianPerInterval = 2 * Math.asin(interval / (2.2 * newRadius));//每个间距对应的角度   越大距离越大

			//第一个元素的角度
			double firstAngle;
			if(countOfMsg % 2 == 1){//奇数
				firstAngle = (countOfMsg-1)*radianPerInterval/2.0 + Math.PI/2+0.08;
			}else{//偶数
				firstAngle = (countOfMsg/2.0-1)*radianPerInterval + radianPerInterval/2.0 +Math.PI/2+0.08;
			}
			for(int i = 0;i<countOfMsg;i++){
				double aa = firstAngle - i*radianPerInterval;
				double ax = newRadius * Math.sin(Math.PI/2 - aa);//小小的trick，将【0，pi】区间变换到[pi/2,-pi/2]区间
				double ay = newRadius * Math.cos(aa-Math.PI/2);//同上类似，这样处理就不必再考虑正负的问题了
				AffineTransform transform = AffineTransform .getRotateInstance(Math.PI/2 - aa);// ,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g2d.setFont(f2);
				g2d.drawString(content.substring(i,i+1), (float) (circleRadius+ax),  (float) (circleRadius - ay));
			}
			g2d.dispose();//销毁资源
			return image;
		} catch (Exception e) {
		}
		return image;
	}
    /**
     * 给图片下面加圆环文字
     * @author 李海涛
     * @param image 底图
     * @param content 文字内容
     * @return
     */
	public static BufferedImage getSealBottom(BufferedImage image,String content) {
		try {
			int width = image.getWidth();
			int height = image.getHeight();
			Graphics2D g = image.createGraphics();
			int circleRadius = Math.min(width,height)/2;
			g.setPaint(Color.decode("#1F479F"));//
			g.setStroke(new BasicStroke(5));//设置画笔的粗度
			int fontSize = 30;
			Font f = new Font("黑体",Font.BOLD,fontSize);
			FontRenderContext context = g.getFontRenderContext();
			Rectangle2D bounds = f.getStringBounds(content,context);

			double msgWidth = bounds.getWidth();
			int countOfMsg = content.length();
			double interval = msgWidth/(countOfMsg-1);//计算间距


			double newRadius = circleRadius + bounds.getY()+10;//bounds.getY()是负数，这样可以将弧形文字固定在圆内了。-5目的是离圆环稍远一点
			double radianPerInterval = 2 * Math.asin(interval / (1.8 * newRadius));//每个间距对应的角度   越小距离越大

			//第一个元素的角度
			double firstAngle;
			if(countOfMsg % 2 == 1){//奇数
				firstAngle = (countOfMsg-1)*radianPerInterval/2.0 + Math.PI/2+0.08;
			}else{//偶数
				firstAngle = (countOfMsg/2.0-1)*radianPerInterval + radianPerInterval/2.0 +Math.PI/2+0.08;
			}
			for(int i = 0;i<countOfMsg;i++){
				double aa = firstAngle - i*radianPerInterval;
				double ax = newRadius * Math.sin(Math.PI/2 - aa);//小小的trick，将【0，pi】区间变换到[pi/2,-pi/2]区间
				double ay = newRadius * Math.cos(aa-Math.PI/2);//同上类似，这样处理就不必再考虑正负的问题了
				AffineTransform transform = AffineTransform .getRotateInstance(aa-Math.PI/2);// ,x0 + ax, y0 + ay);
				Font f2 = f.deriveFont(transform);
				g.setFont(f2);
				g.drawString(content.substring(i,i+1), (float) (circleRadius+ax),  (float) (circleRadius + ay));
			}
			g.dispose();//销毁资源

		} catch (Exception e) {
		}
		return image;
	}
	
	/**
     * 给图片下面加圆环文字
     * @author 李海涛
     * @param image 底图
     * @param content 文字内容
     * @return
     */
    public static BufferedImage getSealCenter(BufferedImage image,String content) {
        try {
            int width = image.getWidth();
            int height = image.getHeight();
            Graphics2D g = image.createGraphics();
            g.setPaint(Color.decode("#1F479F"));//
            g.setStroke(new BasicStroke(5));//设置画笔的粗度
            int fontSize = 24;
            Font f = new Font("微软雅黑",Font.PLAIN,fontSize);
            FontRenderContext context = g.getFontRenderContext();
            Rectangle2D bounds = f.getStringBounds(content,context);
            g.setFont(f);
            double msgWidth = bounds.getWidth();
            g.drawString(content, (int)(width-msgWidth)/2,  (int)((height/2)+85+bounds.getHeight()));
            g.dispose();//销毁资源

        } catch (Exception e) {
        }
        return image;
    }
	/**
	 * 生成带三个圈的图片
	 * @author 李海涛 
	 * @param width 宽度 
	 * @param height 高度
	 * @return
	 */
	public static BufferedImage getTransparent(int width,int height){
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setStroke(new BasicStroke(1));//设置画笔的粗度
		g2d.setPaint(Color.BLUE);//设置画笔的颜色
		g2d.drawArc(0, 0, width, height, 0, 360);

		g2d.setStroke(new BasicStroke(5));//设置画笔的粗度
		g2d.setPaint(Color.BLUE);//设置画笔的颜色
		g2d.drawArc(8, 8, width-16, height-16, 0, 360);

		g2d.setStroke(new BasicStroke(7));//设置画笔的粗度
		g2d.setPaint(Color.BLUE);//设置画笔的颜色
		g2d.drawArc((int)(width*THIRD_ARC), (int)(height*THIRD_ARC), width-(int)(width*THIRD_ARC*2), height-(int)(height*THIRD_ARC*2), 0, 360);
		//释放对象
		g2d.dispose();
		return image;
	}
	public static void main1(String[] args) throws Exception {
		/*int width = 450;
		int height = 450;
		BufferedImage image = getTransparent(width,height);
		image=getSealTop(image, "江西智容科技有限公司");
		image=getSealBottom(image, "信用 - 新建");
		BufferedImage CreditCode=CreditCode("www.iconfont.com", (int)((width-((width*(THIRD_ARC+0.05f)))*2)*0.8f));
		image=setCodeOnCenter(image, CreditCode);
		File file = new File("g:/IMG_20141203_092851.jpg");
		Image im = ImageIO.read(file);
		image=createPhotoAtCenter((BufferedImage)im,image);
		//image=createPhotoAtCenter("g:/IMG_20141203_092851.jpg", image);
		ImageIO.write(image, "png", new File("C:/Users/Administrator/Desktop/test.png"));*/
		File _file = new File("C:/Users/Administrator/Desktop/Qrcircle.png");
		Image src = ImageIO.read(_file);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.drawImage(src, 0, 0, width, height, null);
		g2d.dispose();
		image=getSealTop(image,"江西智容科技有限公司");
		image=getSealBottom(image,"信用 - 新建");
		BufferedImage creditcode=CreditCode("www.iconfont.com", (int)((width-((width*(THIRD_ARC+0.05f)))*2)*0.8f));
		image=setCodeOnCenter(image, creditcode);
		File file = new File("C:/Users/Administrator/Desktop/Logo.png");
		Image im = ImageIO.read(file);
		image=createPhotoAtCenter((BufferedImage)im,image);
		image=getSealCenter(image, "IDcode");
		
		file = new File("C:/Users/Administrator/Desktop/Basemap.png");
		 im = ImageIO.read(file);
		image=setCodeOnCenter((BufferedImage)im, image, (im.getWidth(null)-image.getWidth())/2, 0);
		
		BufferedImage i = new BufferedImage(im.getWidth(null)+10, im.getHeight(null)+10,
				BufferedImage.TYPE_INT_ARGB);
		 g2d = i.createGraphics();
		g2d.drawImage(image, 5, 5, im.getWidth(null), im.getHeight(null), null);
		g2d.dispose();
		
		ImageIO.write(i, "png", new File("C:/Users/Administrator/Desktop/test.png"));
	}
	/**
	 * 将logo放入图片中心
	 * @author 李海涛
	 * @param center  中间的图片，
	 * @param bufImg 底图
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage createPhotoAtCenter(BufferedImage center, BufferedImage bufImg) throws Exception {
		Graphics2D g = bufImg.createGraphics();
		//获取bufImg的中间位置
		int centerX = bufImg.getMinX() + bufImg.getWidth()/2 - center.getWidth()/2;
		int centerY = bufImg.getMinY() + bufImg.getHeight()/2 -center.getHeight()/2;
		g.drawImage(center,centerX,centerY,center.getWidth(),center.getHeight(),null);
		g.dispose();
		return bufImg;
	}
	
	/**
	 * 将二维码图片放入到底图中
	 * @author 李海涛
	 * @param image  底图
	 * @param creditcode   二维码图片
	 * @return
	 */
	public static BufferedImage setCodeOnCenter(BufferedImage image,BufferedImage creditcode) {
		try {
			int w1 = image.getWidth();
			int h1 = image.getHeight();
			int w2 = creditcode.getWidth();
			int h2 = creditcode.getHeight();
			Graphics2D g = image.createGraphics();
			g.drawImage(creditcode, null, (w1-w2)/2, (h1-h2)/2);
			g.dispose();//销毁资源

		} catch (Exception e) {
		}
		return image;
	}
	
	/**
	 * 将二维码图片放入到底图中
	 * @author 李海涛
	 * @param image  底图
	 * @param creditcode   二维码图片
	 * @param x x起始坐标
	 * @param y y起始坐标
	 * @return
	 */
	public static BufferedImage setCodeOnCenter(BufferedImage image,BufferedImage creditcode,int x,int y) {
		try {
			
			Graphics2D g = image.createGraphics();
			g.drawImage(creditcode, null, x, y);
			g.dispose();//销毁资源

		} catch (Exception e) {
		}
		return image;
	}
	
	/**
	 * 创建二维码图片
	 * @author 李海涛
	 * @param content  二维码url
	 * @param width 二维码的宽度 
	 * @return
	 */
	public static BufferedImage CreditCode(String content, int width) {
		BufferedImage bufImg = null;  
		int size=((width-67)/12)+1;  
		try {  
			Qrcode qrcodeHandler = new Qrcode();  
			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小 
			if(size<=10){
				qrcodeHandler.setQrcodeErrorCorrect('L');   
			}else if(size>10 && size <=25){
				qrcodeHandler.setQrcodeErrorCorrect('M');   
			}else if(size>25 && size <=35){
				qrcodeHandler.setQrcodeErrorCorrect('Q');   
			}else{
				qrcodeHandler.setQrcodeErrorCorrect('H');   
			}

			qrcodeHandler.setQrcodeEncodeMode('B');  
			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大  
			qrcodeHandler.setQrcodeVersion(size);  
			// 获得内容的字节数组，设置编码格式  
			byte[] contentBytes = content.getBytes("utf-8");  
			// 图片尺寸  
			int imgSize = 67 + 12 * (size - 1);  
			bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);  
			Graphics2D gs = bufImg.createGraphics();  
			// 设置背景颜色  
			gs.setBackground(Color.WHITE);  
			gs.clearRect(0, 0, imgSize, imgSize);  

			// 设定图像颜色> BLACK  
			gs.setColor(Color.BLACK);  
			// 设置偏移量，不设置可能导致解析出错  
			int pixoff = 2;  
			// 输出内容> 二维码  
			if (contentBytes.length > 0 && contentBytes.length < 800) {  
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
				for (int i = 0; i < codeOut.length; i++) {  
					for (int j = 0; j < codeOut.length; j++) {  
						if (codeOut[j][i]) {  
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
						}  
					}  
				}  
			} else {  
				throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");  
			}  
			gs.dispose();  
			bufImg.flush();  
		} catch (Exception e) {  
			logger.error(e.toString(),e);
		}  
		return bufImg; 
	}

	/** 截取图片 */
	/**
	 * 
	 * @descript (截取图片)
	 * @author 李海涛
	 * @since 2016年9月30日下午5:58:39
	 * @param file 原始文件
	 * @param newFile  新文件
	 * @param x //开始截取的X轴
	 * @param y //开始截取的Y轴
	 * @param width //截取的宽度
	 * @param height //截取的高度
	 */
    public static void cutting(File file, File newFile, int x, int y,int width, int height) {  
        try {  
            String endName = file.getName();  
            endName = endName.substring(endName.lastIndexOf(".") + 1);  
            Iterator<ImageReader> readers = ImageIO  
                    .getImageReadersByFormatName(endName);  
            ImageReader reader = readers.next();  
            InputStream is = new FileInputStream(file);  
            ImageInputStream iis = ImageIO.createImageInputStream(is);  
            reader.setInput(iis, true);  
            ImageReadParam param = reader.getDefaultReadParam();  
            Rectangle rect = new Rectangle(x, y, width, height);  
            param.setSourceRegion(rect);  
            BufferedImage bi = reader.read(0, param);  
	            ImageOutputStream out = ImageIO.createImageOutputStream(new FileOutputStream(newFile));  
	            ImageIO.write(bi, endName, out);  
  
        } catch (Exception e) {  
        	logger.error(e.toString(),e);
        }  
    }  
    
    public static void main(String[] args) {
       String str="C:/Users/Administrator/桌面/aaaaa.jpg";
       cutting(new File(str), new File("C:/Users/Administrator/桌面/bbbbb.jpg"), 115, 115, 168, 168);
    }
}
