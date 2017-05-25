package com.JavaClub.service.credit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.Const;
import com.JavaClub.util.PageData;
import com.JavaClub.util.StringUtil;
import com.JavaClub.util.UuidUtil;
import com.util.SendMailUtil;

/**
 * 
 * @descript 社保信息
 * @author 余思
 * @createTime 2017年5月22日下午4:22:01
 * @version 1.0
 */
@Service("socialInsuranceService")
public class SocialInsuranceService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	private static final String QUERYSOCIALINSURANCE = "SocialInsuranceMapper.querySocialInsurance";	

	/**
	 * 
	 * @descript 统计社保信息
	 * @author 余思
	 * @since 2017年5月22日下午4:34:51
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String querySocialInsurance(PageData pd) throws Exception {
		List<Map<String, Object>> sisum = (List<Map<String, Object>>)  dao.findForList(QUERYSOCIALINSURANCE,pd);
		String[] peoplesum_md = {"城镇职工基本养老保险参保人数","失业保险参保人数","职工基本医疗保险参保人数","工伤保险参保人数","生育保险参保人数"};
		String[] basesum_md = {"单位参加城镇职工基本养老保险缴费基数","单位参加失业保险缴费基数","单位参加职工基本医疗保险缴费基数","单位参加工伤保险缴费基数","单位参加生育保险缴费基数"};
		String[] Paidin_md = {"参加城镇职工基本养老保险本期实际缴费金额","参加失业保险本期实际缴费金额","参加职工基本医疗保险本期实际缴费金额","参加工伤保险本期实际缴费金额","参加生育保险本期实际缴费金额"};
		String[] outstanding_md = {"单位参加城镇职工基本养老保险累计欠缴金额","单位参加失业保险累计欠缴金额","单位参加职工基本医疗保险累计欠缴金额","参加工伤保险累计欠缴金额","单位参加生育保险累计欠缴金额"};
		//人数
		String peoplesum_pj = "['"+peoplesum_md[0]+"("+StringUtil.clearDian(sisum.get(0).get("SO110").toString())+"人)',"+StringUtil.clearDian(sisum.get(0).get("SO110").toString())+"],";
		peoplesum_pj += "['"+peoplesum_md[1]+"("+StringUtil.clearDian(sisum.get(0).get("SO210").toString())+"人)',"+StringUtil.clearDian(sisum.get(0).get("SO210").toString())+"],";
		peoplesum_pj += "['"+peoplesum_md[2]+"("+StringUtil.clearDian(sisum.get(0).get("SO310").toString())+"人)',"+StringUtil.clearDian(sisum.get(0).get("SO310").toString())+"],";
		peoplesum_pj += "['"+peoplesum_md[3]+"("+StringUtil.clearDian(sisum.get(0).get("SO410").toString())+"人)',"+StringUtil.clearDian(sisum.get(0).get("SO410").toString())+"],";
		peoplesum_pj += "['"+peoplesum_md[4]+"("+StringUtil.clearDian(sisum.get(0).get("SO510").toString())+"人)',"+StringUtil.clearDian(sisum.get(0).get("SO510").toString())+"],";
		//基数
		String basesum_pj = "['"+basesum_md[0]+"("+sisum.get(1).get("SO110")+"万元)',"+sisum.get(1).get("SO110")+"],";
		basesum_pj += "['"+basesum_md[1]+"("+sisum.get(1).get("SO210")+"万元)',"+sisum.get(1).get("SO210")+"],";
		basesum_pj += "['"+basesum_md[2]+"("+sisum.get(1).get("SO310")+"万元)',"+sisum.get(1).get("SO310")+"],";
		basesum_pj += "['"+basesum_md[3]+"("+sisum.get(1).get("SO410")+"万元)',"+sisum.get(1).get("SO410")+"],";
		basesum_pj += "['"+basesum_md[4]+"("+sisum.get(1).get("SO510")+"万元)',"+sisum.get(1).get("SO510")+"],";
		//实缴
		String Paidin_pj = "['"+Paidin_md[0]+"("+sisum.get(2).get("SO110")+"万元)',"+sisum.get(2).get("SO110")+"],";
		Paidin_pj += "['"+Paidin_md[1]+"("+sisum.get(2).get("SO210")+"万元)',"+sisum.get(2).get("SO210")+"],";
		Paidin_pj += "['"+Paidin_md[2]+"("+sisum.get(2).get("SO310")+"万元)',"+sisum.get(2).get("SO310")+"],";
		Paidin_pj += "['"+Paidin_md[3]+"("+sisum.get(2).get("SO410")+"万元)',"+sisum.get(2).get("SO410")+"],";
		Paidin_pj += "['"+Paidin_md[4]+"("+sisum.get(2).get("SO510")+"万元)',"+sisum.get(2).get("SO510")+"],";
		//欠缴
		String outstanding_pj = "['"+outstanding_md[0]+"("+sisum.get(3).get("SO110")+"万元)',"+sisum.get(3).get("SO110")+"],";
		outstanding_pj += "['"+outstanding_md[1]+"("+sisum.get(3).get("SO210")+"万元)',"+sisum.get(3).get("SO210")+"],";
		outstanding_pj += "['"+outstanding_md[2]+"("+sisum.get(3).get("SO310")+"万元)',"+sisum.get(3).get("SO310")+"],";
		outstanding_pj += "['"+outstanding_md[3]+"("+sisum.get(3).get("SO410")+"万元)',"+sisum.get(3).get("SO410")+"],";
		outstanding_pj += "['"+outstanding_md[4]+"("+sisum.get(3).get("SO510")+"万元)',"+sisum.get(3).get("SO510")+"],";
		return peoplesum_pj+"@"+basesum_pj+"@"+Paidin_pj+"@"+outstanding_pj;
	}
	/**
	 * 
	 * @descript 表格清算
	 * @author 余思
	 * @since 2017年5月22日下午6:05:19
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryLiquidation(PageData pd) throws Exception {
		List<Map<String, Object>> sisum = (List<Map<String, Object>>)  dao.findForList(QUERYSOCIALINSURANCE,pd);
		sisum.get(0).put("SO110", StringUtil.clearDian(sisum.get(0).get("SO110").toString()));
		sisum.get(0).put("SO210", StringUtil.clearDian(sisum.get(0).get("SO210").toString()));
		sisum.get(0).put("SO310", StringUtil.clearDian(sisum.get(0).get("SO310").toString()));
		sisum.get(0).put("SO410", StringUtil.clearDian(sisum.get(0).get("SO410").toString()));
		sisum.get(0).put("SO510", StringUtil.clearDian(sisum.get(0).get("SO510").toString()));
		return sisum;
	}
}