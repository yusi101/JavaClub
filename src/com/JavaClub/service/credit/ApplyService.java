package com.JavaClub.service.credit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Const;
import com.JavaClub.util.CreateCode;
import com.JavaClub.util.PageData;

/**
 * @descript (用一句话描述改方法的作用)
 * @author 李坡
 * @createTime 2016年9月28日上午9:00:59
 * @version 1.0
 */
@Service("ApplyService")
public class ApplyService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	final String queryLicenseRecordInfo = "QRCodeMapper.queryLicenseRecordInfo";	//查询牌照编码信息
	final String insertLicenseRecord = "QRCodeMapper.insertLicenseRecord";			//添加牌照编码信息
	final String insertQRCodeApply = "QRCodeMapper.insertQRCodeApply";				//添加牌照申请
	final String insertQRCodePrint = "QRCodeMapper.insertQRCodePrint";				//添加牌照打印
	final String queryApplyinfolistPage = "ApplyMapper.queryApplyinfolistPage";		//查询在线申请牌照
	final String insertResponded = "ApplyMapper.insertResponded";					//添加管理员回复信息
	final String updateApplyById = "ApplyMapper.updateApplyById";					//修改牌照申请状态 
	final String queryApplyInfo = "ApplyMapper.queryApplyInfo";						//查询在线企业信息
	final String queryApplylistPage = "ApplyMapper.queryApplylistPage";				//在线审核牌照
	final String queryApplyById = "ApplyMapper.queryApplyById";						//查询牌照在线审核详情
	
	/**
	 *
	 * @descript 查询在线申请牌照
	 * @author 李坡
	 * @since 2016年9月28日上午9:21:45
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryApplyInfo(Page page) throws Exception {
		List<Map<String, Object>> qrCodeList = (List<Map<String, Object>>) dao.findForList(queryApplyinfolistPage, page);
		
		return qrCodeList;
	}
	
	/**
	 *
	 * @descript  在线添加牌照
	 * @author 李坡
	 * @since 2016年9月27日下午1:44:11
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String insertApply(PageData pd, HttpServletRequest request) throws Exception {
		String flag = Const.FAIL;
		int num = 0;

		num = (int) dao.save(insertQRCodeApply, pd);

		if(0 < num){
			flag =  Const.SUCCESS;
		}

		return flag;
	}

	/**
	 *
	 * @descript 查询在线企业信息
	 * @author 李坡
	 * @since 2016年9月27日下午1:53:19
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryApplyInfo(PageData pd) throws Exception{
		return dao.findForListMap(queryApplyInfo, pd);
	}

	/**
	 *
	 * @descript 在线审核牌照
	 * @author 李坡
	 * @since 2016年9月28日上午9:22:17
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryApply(Page page) throws Exception {
		List<Map<String, Object>> qrCodeList = (List<Map<String, Object>>) dao.findForList(queryApplylistPage, page);
		
		return qrCodeList;
	}
	
	/**
	 *
	 * @descript 查询牌照在线审核详情
	 * @author 李坡
	 * @since 2016年9月28日上午10:29:21
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryApplyById(PageData pd) throws Exception{
		return (List<Map<String, Object>>) dao.findForList(queryApplyById, pd);
	}

	/**
	 * 
	 * @descript 在线审核牌照通过
	 * @author 龚志强
	 * @since 2016年10月8日下午3:40:13
	 * @param pd
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String okPass(PageData pd, HttpServletRequest request) throws Exception {
		String flag = Const.FAIL;
		int num = 0;

		//获取牌照和牌照编码数据
		List<Map<String,Object>> qrList = (List<Map<String, Object>>) dao.findForList(queryLicenseRecordInfo, pd);
		if(qrList.isEmpty()){
			//获取牌照编码
			DateFormat fmt = new SimpleDateFormat("-yyyy-");
			pd.put("code", "赣XJ" + fmt.format(new Date()));
			//获取二维码图片
			pd.put("codeImg", CreateCode.CreateQRCodeCircle(request, pd));
			
			//添加牌照编码信息
			num += (int) dao.save(insertLicenseRecord, pd);
		} else {
			//获取牌照编码
			pd.put("code", qrList.get(0).get("LICENSENUMBER"));
			//获取二维码图片
			pd.put("codeImg", qrList.get(0).get("LICENSECODE"));
		}
		
		//添加牌照打印信息
		num += (int) dao.save(insertQRCodePrint, pd);
		//添加管理员回复信息
		num += (int) dao.save(insertResponded, pd);
		//修改牌照申请状态
		num += (int) dao.update(updateApplyById, pd);

		if(0 < num){
			flag =  Const.SUCCESS;
		}

		return flag;
	}
	
	/**
	 * 
	 * @descript 在线审核牌照不通过
	 * @author 龚志强
	 * @since 2016年10月8日下午3:31:12
	 * @param pd
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String noPass(PageData pd) throws Exception {
		String flag = Const.FAIL;
		int num = 0;
		
		//修改牌照申请状态
		num += (int) dao.update(updateApplyById, pd);
		//添加管理员回复
		num += (int) dao.save(insertResponded, pd);

		if(0 < num){
			flag =  Const.SUCCESS;
		}

		return flag;
	}
}