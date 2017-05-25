package com.JavaClub.controller.credit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.spec.SecurityHelper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.entity.AjaxPage;
import com.JavaClub.entity.Page;
import com.JavaClub.service.credit.QRCodeService;
import com.JavaClub.util.Base64Image;
import com.JavaClub.util.Connect;
import com.JavaClub.util.Const;
import com.JavaClub.util.DelAllFile;
import com.JavaClub.util.ExcelUtil;
import com.JavaClub.util.FileUtil;
import com.JavaClub.util.FileZip;
import com.JavaClub.util.JsonUtils;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.Logger;
import com.JavaClub.util.MapReplaceUtils;
import com.JavaClub.util.PageData;
import com.JavaClub.util.SendMailUtil;
import com.JavaClub.util.StrUtil;
import com.JavaClub.util.UuidUtil;
import com.JavaClub.util.Verification;

/**
 *
 * @descript 牌照控制器
 * @author 龚志强
 * @createTime 2016年9月18日上午11:00:48
 * @version 1.0
 */
@Controller
@RequestMapping(value="/qrCodeController")
@Scope("prototype")
public class QRCodeController extends BaseController {
	 protected Logger logger = Logger.getLogger(SendMailUtil.class);
	//牌照业务层
	@Resource(name = "qrCodeService")
	private QRCodeService qrCodeService;

	private static final String ENTNAME = "entname";  
	private static final String UNAME = "uname";  
	private static final String QRLIST = "qrList";  
	private static final String PRINTINFO = "printInfo";  
	private static final String BATCHNUMBER = "batchNumber";  
	private static final String LICENSENUMBER = "licensenumber";  
	private static final String PRIPID = "pripid";  
	private static final String REGNO = "regno";  
	private static final String REMARK = "remark";  
	
	/**
	 *
	 * @descript 牌照申请查询
	 * @author 龚志强
	 * @since 2016年9月18日下午3:12:44
	 * @return 返回数据和视图
	 * @throws Exception
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryEnteraddition", produces = "text/html;charset=UTF-8")
	public ModelAndView queryEnteraddition() throws Exception{
		ModelAndView mv = new ModelAndView("qrcode/qrcode_apply_list");
		PageData pd = new PageData();
		pd = getPageData();

    	//设置接口的加密
        Verification.EncodesearchKey(pd, ENTNAME);
        //设置分页的页码和每页显示的条数
        Verification.setPageParameter2(pd);
        //调用企业信息查询接口
        Map<String, Object> quertenteraddition = Connect.sendConnectByPdToMap(Const.ENTERPRISEURLFOUR, pd, "POST");
        Verification.DecodesearchKey(pd, ENTNAME);

        //判断接口调用是否成功
        if(Verification.StatusIsSuccess(quertenteraddition)){
            //得到商标信息JSON数据中的data数据
			Map<String,Object> dataMap_enteraddition = (Map<String, Object>) quertenteraddition.get("data");
			List<Map<String, Object>> enteraddition = (List<Map<String, Object>>) dataMap_enteraddition.get("Result");
            mv.addObject("Enteraddition", enteraddition);
            //分页的拼接
            Page page = Verification.getPage(dataMap_enteraddition);
            
            mv.addObject("page", page);
        }

        mv.addObject("pd", pd);

        return mv;
	}

	/**
	 *
	 * @descript 跳转添加牌照申请页面
	 * @author 龚志强
	 * @since 2016年9月18日上午11:03:21
	 * @return 返回数据和视图
	 * @throws Exception
	 */
	@RequestMapping(value = "/toCreateQRCode")
	@SuppressWarnings("unchecked")
	public ModelAndView querySysCodeInfo()throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//获取证件类型编号
		List<Map<String,Object>> sysCodeList = qrCodeService.querySysCodeInfo();

		//设置返回数据和视图
		mv.addObject("pd", pd);
		mv.addObject("sysCodeList", sysCodeList);
		mv.setViewName("qrcode/qrcode_add");

		return mv;
	}

	/**
	 *
	 * @descript 添加牌照申请信息
	 * @author 龚志强
	 * @since 2016年9月18日下午3:22:09
	 * @return success或fail字符串
	 * @throws Exception
	 */
	@RequestMapping(value = "/createQRCode", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String createQRCode(HttpServletRequest request)throws Exception{
		PageData pd = new PageData();
		pd = getPageData();

		//设置申请编号ID
		pd.put("aid", UuidUtil.get32UUID());
		//设置申请状态为审核通过
		pd.put("astatus", 1);

		//获取当前用户ID和用户名
		if(null != getUser()){
			pd.put("uid", getUser().getUSER_ID());
			pd.put(UNAME, getUser().getNAME());
		}

		//设置打印编号ID
		pd.put("pid", UuidUtil.get32UUID());
		//设置打印状态为状态未审核
		pd.put("pstatus", 0);

		//添加申请牌照
		return qrCodeService.insertQRCodeApply(pd, request);
	}
	
	/**
	 * 
	 * @descript 跳转到修改牌照申请页面
	 * @author 龚志强
	 * @since 2016年10月12日上午11:35:29
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toUpdateQRCode")
	@SuppressWarnings("unchecked")
	public ModelAndView toUpdateQRCode(Page page)throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//获取牌照申请信息
		page.setPd(pd);
		Map<String,Object> applyCodeMap =  ListUtils.getListMap(qrCodeService.queryQRCodeInfo(page), 0);
		//获取证件类型编号
		List<Map<String,Object>> sysCodeList = qrCodeService.querySysCodeInfo();

		//设置返回数据和视图
		mv.addObject("pd", pd);
		mv.addObject("sysCodeList", sysCodeList);
		mv.addObject("applyCodeMap", applyCodeMap);
		mv.setViewName("qrcode/qrcode_edit");

		return mv;
	}
	
	/**
	 * 
	 * @descript 修改牌照申请
	 * @author 龚志强
	 * @since 2016年10月12日上午11:21:49
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateQRCodeApply", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateQRCodeApply(HttpServletRequest request)throws Exception{
		PageData pd = new PageData();
		pd = getPageData();

		//添加申请牌照
		return qrCodeService.updateQRCodeApply(pd);
	}

	/**
	 *
	 * @descript 查询某个企业的牌照申请记录
	 * @author 龚志强
	 * @since 2016年9月19日上午11:16:50
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeApplyDetail", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	public ModelAndView queryQRCodeApplyDetail(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有配置项信息
		List<Map<String,Object>> qrList = qrCodeService.queryQRCodeInfo(page);

		//设置返回视图和配置项数据
		mv.addObject("pd", pd);
		mv.addObject(QRLIST, qrList);
		mv.setViewName("qrcode/qrcode_list");

		return mv;
	}

	/**
	 *
	 * @descript 查询牌照申请记录
	 * @author 龚志强
	 * @since 2016年9月19日上午11:16:50
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeApplyInfo", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView queryQRCodeApplyInfo(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有牌照申请记录
		List<Map<String,Object>> qrList = qrCodeService.queryQRCodeInfo(page);

		//设置返回视图和牌照申请记录
		mv.addObject("pd", pd);
		mv.addObject(QRLIST, qrList);
		mv.setViewName("qrcode/qrcode_apply_records");

		return mv;
	}
 
//	/**
//	 *
//	 * @descript 查询牌照打印
//	 * @author 龚志强
//	 * @since 2016年9月19日上午11:16:50
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/queryQRCodeInfoAjax", produces = "text/html;charset=UTF-8")
//	@SuppressWarnings("unchecked")
//	@ResponseBody
//	public String queryQRCodeInfoAjax() throws Exception{
//		Page page = new Page();
//		PageData pd = new PageData();
//		pd = getPageData();
//
//		//设置ajax分页
//		page = AjaxPage.setAjaxPage(pd, page);
//		//获取牌照打印数据
//		List<Map<String,Object>> qrCodeList = qrCodeService.queryQRCodePrintInfo(page);
//		//设置返回数据和视图
//		MapReplaceUtils.handleLsitMapData(qrCodeList);
//		String str = AjaxPage.toJson(qrCodeList, page);
//
//		return str;
//	}

	/**
	 *
	 * @descript 查询某个牌照记录详情
	 * @author 龚志强
	 * @since 2016年9月22日下午11:22:42
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeById")
	@SuppressWarnings("rawtypes")
	@ResponseBody
	public ModelAndView queryQRCodeById() throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//获取牌照各种记录
		List qrCodeList = qrCodeService.queryQRCodeById(pd);
		//牌照申请表
		List<Map<String,Object>> applyList = ListUtils.getListIndex(qrCodeList, 0);
		//牌照打印表
		List<Map<String,Object>> printList = ListUtils.getListIndex(qrCodeList, 1);
		//牌照状态审核表
		List<Map<String,Object>> stateauditList = ListUtils.getListIndex(qrCodeList, 2);
		//牌照异常表
		List<Map<String,Object>> abnormalList = ListUtils.getListIndex(qrCodeList, 3);
		//牌照制作表
		List<Map<String,Object>> makeList = ListUtils.getListIndex(qrCodeList, 4);
		//牌照入库确认表
		List<Map<String,Object>> storageList = ListUtils.getListIndex(qrCodeList, 5);
		//牌照领取通知表
		List<Map<String,Object>> noticeList = ListUtils.getListIndex(qrCodeList, 6);
		//牌照领取记录
		List<Map<String,Object>> recordList = ListUtils.getListIndex(qrCodeList, 7);
		//牌照编码记录
		List<Map<String,Object>> licenseRecordList = ListUtils.getListIndex(qrCodeList, 8);

		//设置返回视图和牌照各种记录
		mv.addObject("pd", pd);
		mv.addObject("applyList", applyList);
		mv.addObject("printList", printList);
		mv.addObject("stateauditList", stateauditList);
		mv.addObject("abnormalList", abnormalList);
		mv.addObject("makeList", makeList);
		mv.addObject("storageList", storageList);
		mv.addObject("noticeList", noticeList);
		mv.addObject("recordList", recordList);
		mv.addObject("licenseRecordList", licenseRecordList);
		
		//设置牌照打开页面，1为牌照领取通知详情。其他为牌照申请详情
		if("1".equals(pd.getString("openType"))){
			mv.setViewName("qrcode/qrcode_notice_det");
		}else{
			mv.setViewName("qrcode/qrcode_apply_det");
		}
		
		return mv;
	}

	/**
	 *
	 * @descript 查询牌照审核
	 * @author 龚志强
	 * @since 2016年9月20日下午8:06:06
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeAuditInfo", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView queryQRCodeAuditInfo(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有牌照审核
		List<Map<String,Object>> qrList = qrCodeService.queryQRCodeAuditInfo(page);

		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject(QRLIST, qrList);
		mv.setViewName("qrcode/qrcode_audit_list");

		return mv;
	}

	/**
	 *
	 * @descript 查询某个牌照审核
	 * @author 龚志强
	 * @since 2016年9月21日上午9:39:15
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeAuditById", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView queryQRCodeAuditById() throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//获取某个牌照审核
		Map<String,Object> qrMap = ListUtils.getListMap(qrCodeService.queryQRCodeAuditByIdqy(pd), 0);
		if(null != qrMap && "".equals(qrMap.get("ENTNAME").toString())){
			qrMap = ListUtils.getListMap(qrCodeService.queryQRCodeAuditByIdgt(pd), 0);
		}
		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject("qrMap", qrMap);
		mv.setViewName("qrcode/qrcode_audit_detail");

		return mv;
	}

	/**
	 *
	 * @descript 修改牌照审核状态
	 * @author 龚志强
	 * @since 2016年9月21日上午9:57:38
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateAuditById", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateAuditById(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = getPageData();

		//获取当前用户ID和用户名
		if(null != getUser()){
			pd.put("uid", getUser().getUSER_ID());
			pd.put(UNAME, getUser().getNAME());
		}

		//修改牌照审核状态
		return qrCodeService.updateAuditById(pd);
	}

	/**
	 *
	 * @descript 查询牌照选择制作
	 * @author 龚志强
	 * @since 2016年9月21日上午11:19:49
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeMakeAjax", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public String queryQRCodeMakeAjax() throws Exception{
		Page page = new Page();
		PageData pd = new PageData();
		pd = getPageData();

		//设置ajax分页
		page = AjaxPage.setAjaxPage(pd, page);
		//获取牌照选择制作
		List<Map<String,Object>> qrCodeList = qrCodeService.queryQRCodeMakelistPage(page);
		//设置返回数据和视图
		MapReplaceUtils.handleLsitMapData(qrCodeList);
		return AjaxPage.toJson(qrCodeList, page);
	}

	/**
	 *
	 * @descript 添加牌照选择制作
	 * @author 龚志强
	 * @since 2016年9月21日下午12:04:49
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/createQRCodeMake", produces = "text/html;charset=UTF-8")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String createQRCodeMake(HttpServletRequest request, @RequestParam(value="printInfo") String printInfo)throws Exception{
		PageData pd = new PageData();
		//制作成功企业
		List<PageData> sucList = new ArrayList<>();
		//制作重复的企业
		List<PageData> errList = new ArrayList<>();
		Map<String, Object> msgMap = new HashMap<>();
		String result = "";
		
		pd = getPageData();
		//添加成功失败的标识,默认失败
		String flag = Const.FAIL;

		//如果没有获取到前台传来的值就返回失败
		if(!pd.containsKey(PRINTINFO) || "".equals(pd.getString(PRINTINFO))){
			return Const.FAIL;
		}
		
		//获取当前的批次号，例如0000001
		String batchNumber = getAreaId();
		
		//创建牌照文件夹路径，
		String fileName = Const.AREA + batchNumber;
		String codePath = request.getSession().getServletContext().getRealPath(Const.FILEPATHTWODIMENSIONCODE + fileName);
		
		//创建牌照文件夹
		File fileUrl = new File(codePath);
		if(!fileUrl.exists()) {
			fileUrl.mkdirs();
		}
		
		//获取牌照打印主键ID，企业名称，牌照编码，pripid，数量
		String []printArr = pd.getString(PRINTINFO).split("::");
		for(int i = 0 ; i < printArr.length; i++){
			String []pidArr = printArr[i].split(",");
			//创建一个临时的PD对象
			PageData tempPD = new PageData();
			
			//获取当前用户ID和用户名
			if(null != getUser()){
				tempPD.put("uid", getUser().getUSER_ID());
				tempPD.put(UNAME, getUser().getNAME());
			}
			
			//获取区域
			tempPD.put(BATCHNUMBER, batchNumber);
			tempPD.put("areaCode", Const.AREA);
			
			//二维码生成的位置
			tempPD.put("codePath", codePath);
			
			//设置牌照选择制作主键和打印牌照主键 
			tempPD.put("mid", UuidUtil.get32UUID());
			tempPD.put("pid", StrUtil.getArrValue(pidArr,0));
			tempPD.put(ENTNAME, StrUtil.getArrValue(pidArr,1));		//获取企业名称
			tempPD.put(LICENSENUMBER, StrUtil.getArrValue(pidArr,2));	//获取牌照名称，如赣XJ-2017-00000676
			tempPD.put(PRIPID, SecurityHelper.getDecrypt(StrUtil.getArrValue(pidArr,3)));		//获取pripid,用于查询企业牌照b64编码
			tempPD.put("qrcodenum", StrUtil.getArrValue(pidArr,4));		//获取牌照数量
			
			//设置修改牌照打印状态为3，3为送去制作 
			tempPD.put("status", "3");
			
			List<Map<String,Object>> entList = qrCodeService.queryEntInfoqyByPripid(tempPD);
			if(entList.isEmpty()){//如果企业表无数据，再查个体表
				entList = qrCodeService.queryEntInfogtByPripid(tempPD);
			}
			if(!entList.isEmpty()){
				tempPD.put("licensecode", entList.get(0).get("LICENSECODE"));
			}
			
			//添加申请牌照
			flag = qrCodeService.insertQRCodeMake(tempPD);
			if(Const.FAIL.equals(flag)){
				errList.add(tempPD);
			}else{
				sucList.add(tempPD);
				//在指定目录生成二维码牌照
				Base64Image.getFileFromBase64String(tempPD.getString("licensecode"), codePath + "/" + tempPD.getString(LICENSENUMBER).replace("/", "") + ".jpg");
			}
		}
		
		//制作的企业全部为重复的企业，已经制作过的企业
		if(printArr.length <= errList.size()){
			msgMap.put("flag", Const.ALLREPEAT);
			msgMap.put("entNum", printArr.length);
			msgMap.put("errList", errList);
			result = JsonUtils.toJson(msgMap);
		} else {
			msgMap.put("flag", Const.SUCCESS);
			msgMap.put("entNum", printArr.length);
			msgMap.put("errList", errList);
			result = JsonUtils.toJson(msgMap);
		}
		
		//生成Excel
		createExcel(codePath, sucList, batchNumber);
		
		//压缩文件
		FileZip.zip(codePath);

		//删除原有文件
		DelAllFile.delFolder(codePath);

		return result + "::" + codePath + ".zip::" + fileName + ".zip";
	}
	
	/**
	 * 生成Excel
	 * @param codePath excel路径
	 * @param qrCodeList excel数据源
	 * @throws Exception
	 */
	@SuppressWarnings("resource")
	public void createExcel(String codePath, List<PageData> sucList, String batchNumber) throws Exception {
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
		for (int i = 1; i < sucList.size() + 1; i++) {
			HSSFRow nextrow = sheet.createRow(i);
			//写入企业名称
			HSSFCell cell2 = nextrow.createCell(0);
			cell2.setCellValue(sucList.get(i-1).getString(ENTNAME));
			//写入牌照编码
			cell2 = nextrow.createCell(1);
			cell2.setCellValue(sucList.get(i-1).getString(LICENSENUMBER));
			//写入牌照数量
			cell2 = nextrow.createCell(2);
			cell2.setCellValue(sucList.get(i-1).getString("qrcodenum"));
			//写入牌照批次
			cell2 = nextrow.createCell(3);
			cell2.setCellValue(Const.AREA + batchNumber);
		}

		//保存到目录下
		File file = new File(codePath + "/" + Const.EXCELRECORD);
		try {
			boolean flag=file.createNewFile();
			if(!flag){}
			FileOutputStream stream = FileUtils.openOutputStream(file);
			workbook.write(stream);
			stream.close();
		} catch (IOException e) {
			logger.error(e.toString(),e);
		}
	}

	/**
	 *
	 * @descript 查询所有批次号
	 * @author 龚志强
	 * @since 2016年9月27日上午10:57:47
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryBarchNumberlistPage", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView queryBarchNumberlistPage(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有牌照异常
		List<Map<String,Object>> bnList = qrCodeService.queryBarchNumberlistPage(page);

		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject("bnList", bnList);
		mv.setViewName("qrcode/qrcode_batchnumber_list");

		return mv;
	}

	/**
	 *
	 * @descript 获取最新的牌照选择制作的批次
	 * @author 龚志强
	 * @since 2016年9月21日下午1:55:06
	 * @return
	 * @throws Exception
	 */
	public String getAreaId() throws Exception{
		//获取数据最大的批次号
		String batchNumber = qrCodeService.queryQRCodeMakeBybarchNumber();

		//如果没有批次号就给初始值
		if(batchNumber == null || "".equals(batchNumber)){
			//给一个默认的最大编号
			batchNumber = "000001";
		}else{
			//创建新的批次号
			String temp = batchNumber;
			//转成数字，+1
			int i = Integer.parseInt(temp);
			i++;
			//再转换成字符串
			temp = String.valueOf(i);
			int len = temp.length();
			//凑成七位
			for(int j=0; j<6-len; j++){
				temp = "0" + temp;
			}
			batchNumber = temp;
		}

		return batchNumber;
	}

	/**
	 *
	 * @descript 查询牌照异常
	 * @author 龚志强
	 * @since 2016年9月21日下午3:23:55
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeAbnormallistPage", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView queryQRCodeAbnormallistPage(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有牌照异常
		List<Map<String,Object>> qrList = qrCodeService.queryQRCodeAbnormallistPage(page);

		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject(QRLIST, qrList);
		mv.setViewName("qrcode/qrcode_abnormal_list");

		return mv;
	}

	/**
	 *
	 * @descript 查询牌照异常详情
	 * @author 龚志强
	 * @since 2016年9月21日下午5:32:48
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeAbnormalByAid", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView queryQRCodeAbnormalByAid(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取牌照异常详情
		Map<String,Object> qrMap = ListUtils.getListMap((List<Map<String, Object>>) qrCodeService.queryQRCodeAbnormallistPage(page), 0);

		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject("qrMap", qrMap);

		//1为详情页面，2为处理页面
		if("1".equals(pd.getString("openType"))) {
			mv.setViewName("qrcode/qrcode_abnormal_detail");
		} else {
			mv.setViewName("qrcode/qrcode_abnormal_deal");
		}


		return mv;
	}

	/**
	 *
	 * @descript 修改牌照异常修复描述等信息
	 * @author 龚志强
	 * @since 2016年9月21日下午5:21:53
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateQRCodeAbnormal", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String updateQRCodeAbnormal(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = getPageData();
		//添加成功失败的标识
		String flag = "";

		//获取当前用户ID和用户名
		if(null != getUser()){
			pd.put("uid", getUser().getUSER_ID());
			pd.put(UNAME, getUser().getNAME());
		}

		//添加申请牌照
		flag = qrCodeService.updateQRCodeAbnormal(pd);

		return flag;
	}

	/**
	 *
	 * @descript 查询牌照入库确认信息
	 * @author 龚志强
	 * @since 2016年9月22日上午9:46:21
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeStoragelistPage", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView queryQRCodeStoragelistPage(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有牌照入库确认
		List<Map<String,Object>> qrList = qrCodeService.queryQRCodeStoragelistPage(page);

		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject(QRLIST, qrList);
		mv.setViewName("qrcode/qrcode_storage_list");

		return mv;
	}

	/**
	 *
	 * @descript 添加牌照入库确认
	 * @author 龚志强
	 * @since 2016年9月22日上午10:54:53
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertQRCodeStorage", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String insertQRCodeStorage(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = getPageData();
		//添加成功失败的标识
		String flag = "";

		//获取牌照入库确认主键ID
		pd.put("sid", UuidUtil.get32UUID());
		//获取当前用户ID和用户名
		if(null != getUser()){
			pd.put("uid", getUser().getUSER_ID());
			pd.put(UNAME, getUser().getNAME());
		}

		//添加申请牌照
		flag = qrCodeService.insertQRCodeStorage(pd);

		return flag;
	}

	/**
	 *
	 * @descript 查询牌照领取通知
	 * @author 龚志强
	 * @since 2016年9月22日上午11:42:36
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeNoticelistPage", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView queryQRCodeNoticelistPage(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有牌照入库确认
		List<Map<String,Object>> qrList = qrCodeService.queryQRCodeNoticelistPage(page);

		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject(QRLIST, qrList);
		mv.setViewName("qrcode/qrcode_notice_list");

		return mv;
	}
	
	/**
	 *
	 * @descript 库存查询
	 * @author 龚志强
	 * @since 2016年9月22日上午11:42:36
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeStocklistPage", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView queryQRCodeStocklistPage(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有牌照入库确认
		List<Map<String,Object>> qrList = qrCodeService.queryQRCodeNoticelistPage(page);

		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject(QRLIST, qrList);
		mv.setViewName("qrcode/qrcode_stock_list");

		return mv;
	}

	/**
	 *
	 * @descript 发送通知
	 * @author 龚志强
	 * @since 2016年9月22日下午1:49:43
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendNotice", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String sendNotice(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = getPageData();
		//通知成功失败标志
		String flag = "";
		//通知失败的企业集合
		List<PageData> errEntList = new ArrayList<>();

		//获取当前用户ID和用户名
		if(null != getUser()){
			pd.put("uid", getUser().getUSER_ID());
			pd.put(UNAME, getUser().getNAME());
		}

		//如果获取不到通知数据，就返回失败结果
		if("".equals(pd.getString("noticeInfo") + "")){
			return Const.FAIL;
		}
		
		//获取未处理的通知打印编号，电话，邮箱，企业名称，注册号，申请人
		String []noticeArr = pd.getString("noticeInfo").split("::");
		for(int i = 0; i < noticeArr.length; i++){
			String[] noticeInfoArr = noticeArr[i].split(",");
			//设置牌照通知主键
			pd.put("nid", UuidUtil.get32UUID());
			//获取牌照打印编号，电话，邮箱，企业名称，注册号
			pd.put("pid", StrUtil.getArrValue(noticeInfoArr, 0));
			pd.put("tel", StrUtil.getArrValue(noticeInfoArr, 1));
			pd.put("email", StrUtil.getArrValue(noticeInfoArr, 2));
			pd.put(ENTNAME, StrUtil.getArrValue(noticeInfoArr, 3));
			pd.put(REGNO, StrUtil.getArrValue(noticeInfoArr, 4));
			pd.put("applyName", StrUtil.getArrValue(noticeInfoArr, 5));

			try{
				//发送通知
				flag = qrCodeService.insertQRCodeNotice(pd);
				//通知失败
				if(Const.FAIL.equals(flag)){
					errEntList.add(pd);
				}
			} catch(Exception ex) {
				errEntList.add(pd);
			}
		}

		return JsonUtils.toJson(errEntList);
	}

	/**
	 *
	 * @descript 查询牌照出库
	 * @author 龚志强
	 * @since 2016年9月22日下午4:11:53
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeReceivelistPage", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView queryQRCodeReceivelistPage(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有牌照入库确认
		List<Map<String,Object>> qrList = qrCodeService.queryQRCodeReceivelistPage(page);

		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject(QRLIST, qrList);
		mv.setViewName("qrcode/qrcode_notice_receive");

		return mv;
	}
	
	/**
	 *
	 * @descript 添加牌照出库记录
	 * @author 龚志强
	 * @since 2016年9月22日下午4:49:46
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/insertQRCodeReceive", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String insertQRCodeReceive(HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = getPageData();
		//添加成功失败的标识
		String flag = "";
		//领取失败的企业集合
		List<PageData> errEntList = new ArrayList<>();

		//获取当前用户ID和用户名
		if(null != getUser()){
			pd.put("uid", getUser().getUSER_ID());
			pd.put(UNAME, getUser().getNAME());
		}

		//获取未处理的通知打印编号，企业名称，注册号
		String[] receiveArr = pd.getString("receiveInfo").split("::");
		for(int i = 0; i < receiveArr.length; i++){
			String[] receiveInfoArr = receiveArr[i].split(",");
			
			//生成牌照领取主键ID
			pd.put("rid", UuidUtil.get32UUID());
			pd.put("pid", StrUtil.getArrValue(receiveInfoArr, 0));
			pd.put(ENTNAME, StrUtil.getArrValue(receiveInfoArr, 1));
			pd.put(REGNO, StrUtil.getArrValue(receiveInfoArr, 2));
			
			try{
				//添加领取记录
				flag = qrCodeService.insertQRCodeReceive(pd);
				//领取失败
				if(Const.FAIL.equals(flag)){
					errEntList.add(pd);
				}
			} catch(Exception ex) {
				errEntList.add(pd);
			}
		}

		return JsonUtils.toJson(errEntList);
	}

	/**
	 *
	 * @descript 查询牌照领取记录
	 * @author 龚志强
	 * @since 2016年9月22日下午4:58:18
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryQRCodeRecordlistPage", produces = "text/html;charset=UTF-8")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView queryQRCodeRecordlistPage(Page page) throws Exception{
		ModelAndView mv = getModelAndView();
		PageData pd = new PageData();
		pd = getPageData();

		//设置分页
		page.setPd(pd);
		//获取所有牌照入库确认
		List<Map<String,Object>> qrList = qrCodeService.queryQRCodeRecordlistPage(page);

		//设置返回视图和数据
		mv.addObject("pd", pd);
		mv.addObject(QRLIST, qrList);
		mv.setViewName("qrcode/qrcode_notice_record");

		return mv;
	}

	/**
	 * 牌照下载
	 * @param codePath 下载路径
	 * @param fileName 下载名称
	 * @param response 返回对象
	 * @return
	 */
	@RequestMapping(value = "/downImg")
	public void downImg(HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		pd = getPageData();

		FileUtil.upload(response, pd.getString("codePath"), pd.getString("fileName"));
	}

	/**
	 *
	 * @descript 下载导入模板
	 * @author 龚志强
	 * @since 2016年9月27日上午10:20:18
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downExcel")
	public void downExcel(HttpServletResponse response, HttpServletRequest request) throws Exception{
		//文件名称
		String fileName = Const.EXCELNAME;
		
		//用于解决各种浏览器名称的乱码空格问题。
        String header = request.getHeader("User-Agent").toUpperCase();
        if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
            fileName = URLEncoder.encode(fileName, "utf-8");
            fileName = fileName.replace("+", "%20");    //IE下载文件名空格变+号问题
        } else {
            //fileName = new String(fileName.getBytes(), "UTF-8");
        }
		
		//文件路径  %E7%89%8C%E7%85%A7%E5%AF%BC%E5%85%A5%E6%A8%A1%E6%9D%BF.xls
		String codePath = request.getSession().getServletContext().getRealPath(Const.UPEXCEL + fileName);

		FileUtil.upload(response, codePath, fileName);
	}

	/**
	 *
	 * @descript 下载批次号
	 * @author 龚志强
	 * @since 2016年9月27日上午11:13:17
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/downBatchNumber")
	public void downBatchNumber(HttpServletResponse response, HttpServletRequest request) throws Exception {
		PageData pd = new PageData();
		pd = getPageData();

		//文件路径
		String codePath = request.getSession().getServletContext().getRealPath(Const.FILEPATHTWODIMENSIONCODE + pd.getString(BATCHNUMBER));

		FileUtil.upload(response, codePath, pd.getString(BATCHNUMBER));
	}

	/**
	 *
	 * @descript 导入Excel
	 * @author 龚志强
	 * @since 2016年9月27日下午2:00:39
	 * @param request
	 * @param upload
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/batchQRCodeAtExcel", produces="text/html;charset=UTF-8")
	public String batchQRCodeAtExcel(HttpServletRequest request, @RequestParam("upload") MultipartFile upload) throws Exception {
		//导入的牌照集合
		List<PageData> qrcodeList = new ArrayList<>();
		//申请失败的企业集合
		List<PageData> errEntList = new ArrayList<>();
		//参数集合
		PageData pd = null;
		//导入成功失败标志
		String flag = "";

		//解析Excel
		readExcel(upload, qrcodeList);
		
		//记录导入的企业个数
		int entCount = qrcodeList.size();

		//查询数据获取企业信息
		for(int i = 0; i < qrcodeList.size(); i++){
			//设置查询企业信息参数
			pd = qrcodeList.get(i);

			//查询企业信息
			List<Map<String, Object>> entList = qrCodeService.queryEntInfo(pd);
			//判断是否查询成功
			if(!entList.isEmpty()){
				//添加社会统一信用代码和社会主体代码
				qrcodeList.get(i).put("uniscid", entList.get(0).get("UNISCID"));
				qrcodeList.get(i).put(PRIPID, entList.get(0).get("PRIPID"));
				qrcodeList.get(i).put(ENTNAME, entList.get(0).get("ENTNAME"));
				qrcodeList.get(i).put(REGNO, entList.get(0).get("REGNORE"));
				qrcodeList.get(i).put("provinceCode", entList.get(0).get("C_PROVINCE"));
			}else{
				qrcodeList.get(i).put(REMARK, "该企业没有查询到，请检查注册号和企业名称是否正确");
				qrcodeList.get(i).put(PRIPID, "error");
				//添加申请失败的企业
				errEntList.add(qrcodeList.get(i));
			}
		}

		//添加牌照申请和牌照打印
		for(int n = 0; n < qrcodeList.size(); n++){
			//获取添加牌照申请和牌照打印信息的参数
			pd = qrcodeList.get(n);

			//判断如果是没有查询到的企业就跳过申请执行下一个企业
			if("error".equals(pd.getString(PRIPID))){
				continue;
			}

			//获取当前用户ID和用户名
			pd.put("uid", getUser().getUSER_ID());
			pd.put(UNAME, getUser().getNAME());
			//设置申请编号ID
			pd.put("aid", UuidUtil.get32UUID());
			pd.put("astatus", 1);

			//设置打印编号ID
			pd.put("pid", UuidUtil.get32UUID());
			pd.put("pstatus", 0);
			
			//设置申请方式，类型
			pd.put("applyWay", "2");
			pd.put("applyWayCN", "批量导入");
			pd.put("applyType", "2");
	        pd.put("applyTypeCN", "批量导入");
	        pd.put("applyId", "0");

			try {
				//导入Excel企业申请
				flag = qrCodeService.batchQRCodeAtExcel(request, pd);
				
				//如果导入Excel企业申请失败
				if(Const.FAIL.equals(flag)){
					pd.put(REMARK, "申请失败");
					//添加申请失败的企业
					errEntList.add(pd);
				}
			} catch (Exception ex) {
				logger.error(ex.toString(),ex);
				pd.put(REMARK, "申请失败");
				//添加申请失败的企业
				errEntList.add(pd);
			}
		}

		return JsonUtils.toJson(errEntList) + "::" + entCount;
	}

	/**
	 * 
	 * @descript 解读Excel
	 * @author 龚志强
	 * @since 2016年9月27日下午2:28:18
	 * @param in 上传对象的流
	 * @param qrcodeList 导入Excel里的数据集合
	 * @return	导入Excel里的数据集合
	 * @throws Exception
	 */
	public List<PageData> readExcel(MultipartFile upload, List<PageData> qrcodeList) throws Exception {
		PageData qrcodeMap = null;
		Workbook workbook = null;  

		//获取上传文件类型
		String originalFileName = upload.getOriginalFilename();
		String contenType = originalFileName.substring(upload.getOriginalFilename().lastIndexOf(".") + 1);
		if("xls".equals(contenType)){
			workbook = new HSSFWorkbook(upload.getInputStream());
		}else if("xlsx".equals(contenType)){
			workbook = new XSSFWorkbook(upload.getInputStream());
		}

		//读取默认第一个工作表sheet
		Sheet sheet =  workbook.getSheetAt(0);
		//获取sheet中最后一个行行号
		int lastRowNum = sheet.getLastRowNum();
		for (int i = 2; i <= lastRowNum; i++) {
			qrcodeMap = new PageData();

			//获取sheet的每行
			Row row = sheet.getRow(i);
			//如果当前这行全部为空就忽视当前这行
			if(null == row){
				continue;
			}
			
			//如果申请的企业名称和注册号都为空就忽视当前一行。
			if("".equals(ExcelUtil.getCellValue(row.getCell(3))) && "".equals(ExcelUtil.getCellValue(row.getCell(4)))){
				continue;
			}

			//添加导入Excel的数据
			qrcodeMap.put("applyName",ExcelUtil.getCellValue(row.getCell(0)));
			qrcodeMap.put("tel",ExcelUtil.getCellValue(row.getCell(1)));
			qrcodeMap.put("email",ExcelUtil.getCellValue(row.getCell(2)));
			qrcodeMap.put(ENTNAME,ExcelUtil.getCellValue(row.getCell(3)));
			qrcodeMap.put(REGNO,ExcelUtil.getCellValue(row.getCell(4)));			
			qrcodeMap.put("applyNumber",ExcelUtil.getCellValue(row.getCell(5)).equals("") ? 1 : ExcelUtil.getCellValue(row.getCell(5)));
			qrcodeMap.put(REMARK,ExcelUtil.getCellValue(row.getCell(6)));

			//添加Excel每行的数据
			qrcodeList.add(qrcodeMap);
		}

		return qrcodeList;
	}
}