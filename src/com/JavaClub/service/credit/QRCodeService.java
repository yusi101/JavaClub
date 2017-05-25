package com.JavaClub.service.credit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Const;
import com.JavaClub.util.CreateCode;
import com.JavaClub.util.Logger;
import com.JavaClub.util.PageData;
import com.JavaClub.util.SmsUtil;
import com.JavaClub.util.StrUtil;
import com.JavaClub.util.UuidUtil;
import com.util.SendMailUtil;

/**
 *
 * @descript 牌照业务层
 * @author 龚志强
 * @createTime 2016年9月18日下午3:23:42
 * @version 1.0
 */
@Service("qrCodeService")
public class QRCodeService {

	protected Logger logger = Logger.getLogger(this.getClass());
	 
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	static final String queryEntInfo = "QRCodeMapper.queryEntInfo";								//查询企业信息
	static final String queryEntInfoqyByPripid = "QRCodeMapper.queryEntInfoqyByPripid";
	static final String queryEntInfogtByPripid = "QRCodeMapper.queryEntInfogtByPripid";
	static final String queryLicenseRecordInfo = "QRCodeMapper.queryLicenseRecordInfo";			//查询牌照编码信息
	static final String querySysCodeInfo = "QRCodeMapper.querySysCodeInfo";						//查询证件类型编号
	static final String queryApplyRecordslistPage = "QRCodeMapper.queryApplyRecordslistPage";		//查询牌照申请记录
	static final String queryQRCodePrintlistPage = "QRCodeMapper.queryQRCodePrintlistPage";		//查询牌照打印
	static final String queryQRCodeById = "QRCodeMapper.queryQRCodeById";							//查询某个牌照记录详情
	static final String queryQRCodeAuditlistPage = "QRCodeMapper.queryQRCodeAuditlistPage";		//查询牌照正常审核
	static final String queryQRCodeAuditByIdqy = "QRCodeMapper.queryQRCodeAuditByIdqy";				//查询某个牌照正常审核 企业
	static final String queryQRCodeAuditByIdgt = "QRCodeMapper.queryQRCodeAuditByIdgt";				//查询某个牌照正常审核 个体
	static final String queryQRCodeMakelistPage = "QRCodeMapper.queryQRCodeMakelistPage";			//查询牌照选择制作
	static final String queryQRCodeMakeBybarchNumber = "QRCodeMapper.queryQRCodeMakeBybarchNumber";//查询牌照选择制作的批次
	static final String queryBarchNumberlistPage = "QRCodeMapper.queryBarchNumberlistPage";		//查询牌照批次号
	static final String queryQRCodeAbnormallistPage = "QRCodeMapper.queryQRCodeAbnormallistPage";	//查询牌照异常记录
	static final String queryQRCodeStoragelistPage = "QRCodeMapper.queryQRCodeStoragelistPage";	//查询牌照入库确认
	static final String queryQRCodeNoticelistPage = "QRCodeMapper.queryQRCodeNoticelistPage";		//查询牌照领取通知
	static final String queryQRCodeReceivelistPage = "QRCodeMapper.queryQRCodeReceivelistPage";	//查询牌照领取
	static final String queryQRCodeRecordlistPage = "QRCodeMapper.queryQRCodeRecordlistPage";		//查询牌照领取记录
	static final String updatePrintById = "QRCodeMapper.updatePrintById";							//修改牌照打印状态
	static final String updateQRCodeRecord = "QRCodeMapper.updateQRCodeRecord";					//修改牌照编码信息
	static final String updateQRCodeAbnormal = "QRCodeMapper.updateQRCodeAbnormal";				//修改牌照异常修复描述等信息
	static final String updateQRCodeApply = "QRCodeMapper.updateQRCodeApply";						//修改牌照申请
	static final String insertLicenseRecord = "QRCodeMapper.insertLicenseRecord";					//添加牌照编码信息
	static final String insertQRCodeApply = "QRCodeMapper.insertQRCodeApply";						//添加牌照申请
	static final String insertQRCodeReceipts = "QRCodeMapper.insertQRCodeReceipts";				//添加牌照回执单
	static final String inseertBusinessLicense = "QRCodeMapper.inseertBusinessLicense";			//添加牌照营业执照
	static final String insertQRCodePrint = "QRCodeMapper.insertQRCodePrint";						//添加牌照打印
	static final String insertQRCodeMake = "QRCodeMapper.insertQRCodeMake";						//添加牌照选择制作
	static final String insertQRCodeAudit = "QRCodeMapper.insertQRCodeAudit";						//添加状态审核
	static final String insertQRCodeAbnormal = "QRCodeMapper.insertQRCodeAbnormal";				//添加牌照异常
	static final String insertQRCodeStorage = "QRCodeMapper.insertQRCodeStorage";					//添加牌照入库确认
	static final String insertQRCodeNotice = "QRCodeMapper.insertQRCodeNotice";					//添加牌照领取通知
	static final String insertQRCodeReceive = "QRCodeMapper.insertQRCodeReceive";					//添加牌照领取记录
	
	private static final String CODEIMG = "codeImg";  
	private static final String CODE = "code";  
	private static final String LICENSENUMBER = "LICENSENUMBER";  
	private static final String LICENSECODE = "LICENSECODE";  
	
	/**
	 * 
	 * @descript 查询企业信息
	 * @author 龚志强
	 * @since 2016年9月28日上午10:00:28
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryEntInfo(PageData pd) throws Exception {

		return (List) dao.findForList(queryEntInfo, pd);
	}
	
	/**
	 * 
	 * @descript 根据pripid查询企业信息
	 * @author 龚志强
	 * @since 2017年1月23日下午2:25:11
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryEntInfoqyByPripid(PageData pd) throws Exception {

		return (List) dao.findForList(queryEntInfoqyByPripid, pd);
	}
	/**
	 * 
	 * @descript 根据pripid查询企业信息
	 * @author 龚志强
	 * @since 2017年1月23日下午2:25:11
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryEntInfogtByPripid(PageData pd) throws Exception {

		return (List) dao.findForList(queryEntInfogtByPripid, pd);
	}
	/**
	 *
	 * @descript 查询证件类型编号
	 * @author 龚志强
	 * @since 2016年9月18日上午10:59:50
	 * @return 证件类型编号List集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List querySysCodeInfo() throws Exception {
		return (List) dao.findForList(querySysCodeInfo, "");
	}

	/**
	 *
	 * @descript 添加申请牌照信息
	 * @author 龚志强
	 * @since 2016年9月18日下午3:23:19
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String insertQRCodeApply(PageData pd, HttpServletRequest request) throws Exception {
		String flag = Const.FAIL;
		int num = 0;

		//获取牌照和牌照编码数据
		List<Map<String,Object>> qrList = (List<Map<String, Object>>) dao.findForList(queryLicenseRecordInfo, pd);
		if(qrList.isEmpty()){
			//获取牌照编码
			DateFormat fmt = new SimpleDateFormat("-yyyy-");
			pd.put(CODE, Const.AREA_ALIAS + Const.AREA + fmt.format(new Date()));
			//获取二维码图片
			pd.put(CODEIMG, CreateCode.CreateQRCodeCircle(request, pd));
			
			//添加牌照编码记录
			num += (int) dao.save(insertLicenseRecord, pd);
		} else {
			//获取牌照编码
			pd.put(CODE, qrList.get(0).get(LICENSENUMBER));
			//获取二维码图片
			pd.put(CODEIMG, qrList.get(0).get(LICENSECODE));
		}
		
		//添加牌照申请记录和牌照打印记录
		num += (int) dao.save(insertQRCodeApply, pd);
		num += (int) dao.save(insertQRCodePrint, pd);

		if(0 < num){
			flag =  Const.SUCCESS;
		}

		return flag;
	}
	
	/**
	 * 
	 * @descript 修改申请牌照
	 * @author 龚志强
	 * @since 2016年10月12日上午11:19:27
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String updateQRCodeApply(PageData pd) throws Exception {
		String flag = Const.FAIL;
		//修改申请牌照
		int num = (int) dao.update(updateQRCodeApply, pd);

		if(0 < num){
			flag =  Const.SUCCESS;
		}

		return flag;
	}
	
	/**
	 * 
	 * @descript 添加牌照回执单打印记录
	 * @author 龚志强
	 * @since 2016年10月17日下午4:45:24
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String insertQRCodeReceipts(PageData pd) throws Exception {
		String flag = Const.FAIL;
		//修改申请牌照
		int num = (int) dao.save(insertQRCodeReceipts, pd);

		if(0 < num){
			flag =  Const.SUCCESS;
		}

		return flag;
	}
	
	/**
	 * 
	 * @descript 添加牌照营业执照打印记录
	 * @author 龚志强
	 * @since 2016年10月17日下午5:27:26
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String inseertBusinessLicense(PageData pd) throws Exception {
		String flag = Const.FAIL;
		int num = (int) dao.save(inseertBusinessLicense, pd);

		if(0 < num){
			flag =  Const.SUCCESS;
		}

		return flag;
	}
	
	/**
	 *
	 * @descript 导入Excel
	 * @author 龚志强
	 * @since 2016年9月27日下午2:13:23
	 * @param in
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public synchronized String batchQRCodeAtExcel(HttpServletRequest request, PageData pd) throws Exception {
		//默认失败标志
		String flag = Const.FAIL;
		int num = 0;

		//获取牌照和牌照编码数据
		List<Map<String,Object>> qrList = (List<Map<String, Object>>) dao.findForList(queryLicenseRecordInfo, pd);
		if(qrList.isEmpty()){
			//获取牌照编码
			DateFormat fmt = new SimpleDateFormat("-yyyy-");
			pd.put(CODE, Const.AREA_ALIAS + Const.AREA + fmt.format(new Date()));
			//获取二维码图片
			pd.put(CODEIMG, CreateCode.CreateQRCodeCircle(request, pd));
			
			num += (int) dao.save(insertLicenseRecord, pd);
		} else {
			//获取牌照编码
			pd.put(CODE, qrList.get(0).get(LICENSENUMBER));
			//获取二维码图片
			pd.put(CODEIMG, qrList.get(0).get(LICENSECODE));
		}

		//添加牌照申请和牌照打印
		num += (int) dao.save(insertQRCodeApply, pd);
		num += (int) dao.save(insertQRCodePrint, pd);

		if(0 < num){
			flag =  Const.SUCCESS;
		}

		return flag;
	}

	/**
	 *
	 * @descript 查询某个牌照记录详情
	 * @author 龚志强
	 * @since 2016年9月22日下午11:06:32
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodeById(PageData pd) throws Exception{

		return (List) dao.findForList(queryQRCodeById, pd);
	}

	/**
	 *
	 * @descript 查询牌照申请记录
	 * @author 龚志强
	 * @since 2016年9月18日上午10:59:50
	 * @return 牌照申请记录List集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodeInfo(Page page) throws Exception {

		return (List) dao.findForList(queryApplyRecordslistPage, page);
	}

	/**
	 *
	 * @descript 查询牌照打印
	 * @author 龚志强
	 * @since 2016年9月18日上午10:59:50
	 * @return 牌照打印List集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodePrintInfo(Page page) throws Exception {
		
		return (List) dao.findForList(queryQRCodePrintlistPage, page);
	}

	/**
	 *
	 * @descript 查询牌照正常审核
	 * @author 龚志强
	 * @since 2016年9月18日上午10:59:50
	 * @return 牌照打印List集合
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodeAuditInfo(Page page) throws Exception {

		return (List) dao.findForList(queryQRCodeAuditlistPage, page);
	}

	/**
	 *
	 * @descript 查询某个牌照正常审核 企业
	 * @author 龚志强
	 * @since 2016年9月21日上午9:29:14
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodeAuditByIdqy(PageData pd) throws Exception {

		return (List) dao.findForList(queryQRCodeAuditByIdqy, pd);
	}
	/**
	 *
	 * @descript 查询某个牌照正常审核 个体
	 * @author 龚志强
	 * @since 2016年9月21日上午9:29:14
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodeAuditByIdgt(PageData pd) throws Exception {

		return (List) dao.findForList(queryQRCodeAuditByIdgt, pd);
	}
	/**
	 *
	 * @descript 修改牌照审核状态
	 * @author 龚志强
	 * @since 2016年9月21日上午9:56:18
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String updateAuditById(PageData pd) throws Exception {
		String flag = Const.FAIL;
		int num = 0;

		//如果修改牌照打印状态为2，向牌照异常表里插入异常信息。
		if("2".equals(pd.getString("status"))) {
			num += (int) dao.update(updatePrintById, pd);
			if(0 < num){
				//异常主键
				pd.put("aid", UuidUtil.get32UUID());
				num += (int) dao.save(insertQRCodeAbnormal, pd);
			}
			///如果修改牌照打印状态为1，向状态审核表中插入审核成功信息
		} else if("1".equals(pd.getString("status"))) {
			num += (int) dao.update(updatePrintById, pd);
			if(0 < num){
				//审核成功主键
				pd.put("sid", UuidUtil.get32UUID());
				num += (int) dao.save(insertQRCodeAudit, pd);
			}
		}

		if(0 < num){
			flag =  Const.SUCCESS;
		}

		return flag;
	}

	/**
	 *
	 * @descript 查询牌照选择制作
	 * @author 龚志强
	 * @since 2016年9月22日下午6:03:26
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodeMakelistPage(Page page) throws Exception {

		return (List) dao.findForList(queryQRCodeMakelistPage, page);
	}
	
	/**
	 *
	 * @descript 添加牌照选择制作
	 * @author 龚志强
	 * @since 2016年9月21日下午12:04:25
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public synchronized String insertQRCodeMake(PageData pd) throws Exception {
		String flag = Const.FAIL;

		//修改牌照打印状态
		int num = (int) dao.update(updatePrintById, pd);
		if(0 < num){
			//添加牌照选择制作
			dao.save(insertQRCodeMake, pd);
			flag = Const.SUCCESS;
		}else{
			return flag;
		}
		
		return flag;
	}

	/**
	 *
	 * @descript 查询牌照选择制作的批次
	 * @author 龚志强
	 * @since 2016年9月21日下午1:48:12
	 * @return
	 * @throws Exception
	 */
	public String queryQRCodeMakeBybarchNumber() throws Exception{

		return (String) dao.findForObject(queryQRCodeMakeBybarchNumber, null);
	}

	/**
	 *
	 * @descript 查询牌照批次号
	 * @author 龚志强
	 * @since 2016年9月27日上午10:54:33
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryBarchNumberlistPage(Page page) throws Exception{

		return (List) dao.findForList(queryBarchNumberlistPage, page);
	}

	/**
	 *
	 * @descript 查询牌照异常
	 * @author 龚志强
	 * @since 2016年9月21日下午3:20:04
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodeAbnormallistPage(Page page) throws Exception {

		return (List) dao.findForList(queryQRCodeAbnormallistPage, page);
	}

	/**
	 *
	 * @descript 修改牌照异常修复描述等信息
	 * @author 龚志强
	 * @since 2016年9月21日下午5:11:09
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String updateQRCodeAbnormal(PageData pd) throws Exception {
		String flag = Const.FAIL;
		int num = 0;

		//修改牌照异常修复描述等信息
		num += (int) dao.update(updateQRCodeAbnormal, pd);
		//修改牌照编码信息
		num += (int) dao.update(updateQRCodeRecord, pd);
		//修改牌照打印状态
		num += (int) dao.update(updatePrintById, pd);

		if(0 < num){
			flag =  Const.SUCCESS;
		}

		return flag;
	}

	/**
	 *
	 * @descript 查询牌照入库确认信息
	 * @author 龚志强
	 * @since 2016年9月22日上午9:47:22
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodeStoragelistPage(Page page) throws Exception {

		return (List) dao.findForList(queryQRCodeStoragelistPage, page);
	}

	/**
	 *
	 * @descript 添加牌照入库确认
	 * @author 龚志强
	 * @since 2016年9月22日上午10:52:08
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String insertQRCodeStorage(PageData pd) throws Exception {
		String flag = Const.FAIL;
		int num = (int) dao.update(updatePrintById, pd);

		if(0 < num){
			//添加牌照入库确认
			 dao.save(insertQRCodeStorage, pd);
			flag =  Const.SUCCESS;
		}else{
			flag = Const.REPEAT;
		}

		return flag;
	}

	/**
	 *
	 * @descript 查询牌照领取通知
	 * @author 龚志强
	 * @since 2016年9月22日上午11:42:00
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodeNoticelistPage(Page page) throws Exception {

		return (List) dao.findForList(queryQRCodeNoticelistPage, page);
	}

	/**
	 *
	 * @descript 添加牌照领取通知
	 * @author 龚志强
	 * @since 2016年9月22日下午2:27:36
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String insertQRCodeNotice(PageData pd) throws Exception {
		String flag = Const.FAIL;
		int num = 0;

		//添加牌照领取通知记录
		num += (int) dao.save(insertQRCodeNotice, pd);
		//修改牌照打印状态
		num += (int) dao.update(updatePrintById, pd);

		if(0 < num){
			flag =  Const.SUCCESS;
			//发送邮件
			List<String> sendTo = new ArrayList<>();
            sendTo.add(pd.getString("email"));
            String content="您的企业牌照已经制作完成，请您尽快来领取！如有疑问请拨打服务热线电话：" + Const.TELEPHONE;
            SendMailUtil.sendHtmlEmail(sendTo,"【" + Const.SYSNAME + "】牌照领取",content);
            //发送短信
            SmsUtil.sendSmsNotice(pd.getString("tel"), pd.getString("applyName"), pd.getString("entname"));
		}

		return flag;
	}

	/**
	 *
	 * @descript 查询牌照领取
	 * @author 龚志强
	 * @since 2016年9月22日下午4:11:10
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodeReceivelistPage(Page page) throws Exception {

		return (List) dao.findForList(queryQRCodeNoticelistPage, page);
	}

	/**
	 *
	 * @descript 添加牌照领取记录
	 * @author 龚志强
	 * @since 2016年9月22日下午4:47:59
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String insertQRCodeReceive(PageData pd) throws Exception {
		String flag = Const.FAIL;
		int num = 0;

		//修改牌照打印状态
		num += (int) dao.update(updatePrintById, pd);

		if(0 < num){
			//添加牌照领取记录
			dao.save(insertQRCodeReceive, pd);
			flag =  Const.SUCCESS;
		} else {
			flag = Const.REPEAT;
		}

		return flag;
	}

	/**
	 *
	 * @descript 查询牌照领取记录
	 * @author 龚志强
	 * @since 2016年9月22日下午4:57:34
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List queryQRCodeRecordlistPage(Page page) throws Exception {

		return (List) dao.findForList(queryQRCodeRecordlistPage, page);
	}

	/**
	 * 生成Excel
	 * @param codePath excel路径
	 * @param qrCodeList excel数据源
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public void createExcel(String codePath, String[] entArr, String batchNumber) throws Exception {
		//创建Excel工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		sheet.setDefaultColumnWidth(20);
		sheet.setDefaultRowHeightInPoints(20);

		//设置标题
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("企业名称");
		row.createCell(1).setCellValue("牌照编码");
		row.createCell(2).setCellValue("牌照数量");
		row.createCell(3).setCellValue("牌照批次号");

		//追加数据
		for (int i = 1; i < entArr.length + 1; i++) {
			//如果该企业重复制作了就被赋值为空字符，防止添加重复制作的企业名称
			if("".equals(entArr[i-1])){
				continue;
			}
			//获取打印主键，企业名称，牌照编码，牌照，数量
			String[] pidArr = entArr[i-1].split(",");
			HSSFRow nextrow = sheet.createRow(i);
			//写入企业名称
			HSSFCell cell2 = nextrow.createCell(0);
			cell2.setCellValue(StrUtil.getArrValue(pidArr,1));
			//写入牌照编码
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(StrUtil.getArrValue(pidArr,2));
			//写入牌照数量
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(StrUtil.getArrValue(pidArr,4));
			//写入牌照批次
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(Const.AREA + batchNumber);
		}

		//保存到目录下
		File file = new File(codePath + "/" + Const.EXCELRECORD);
		try {
			boolean galg=file.createNewFile();
			if(galg){}
			FileOutputStream stream = FileUtils.openOutputStream(file);
			workbook.write(stream);
			stream.close();
		} catch (IOException e) {
			logger.error(e.toString(),e);
		}
	}
	
	/**
    *
    * @descript 获取牌照编码
    * @author 龚志强
    * @since 2016年9月22日下午4:57:34
    * @param page
    * @return
    * @throws Exception
    */
	@SuppressWarnings("unchecked")
	public String queryQRBase64CodeInfo(PageData pd) throws Exception {
		List<Map<String,Object>> qrList = (List<Map<String, Object>>) dao.findForList(queryLicenseRecordInfo, pd);
    	if(qrList.isEmpty()){
    		//获取二维码图片
    		return CreateCode.CreateQRCodeCircle(null, pd);
    	} else {
//    		//获取二维码图片
    		return qrList.get(0).get(LICENSECODE).toString();
    	}
	}
}