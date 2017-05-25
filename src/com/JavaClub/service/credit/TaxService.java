package com.JavaClub.service.credit;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.util.PageData;


/**
 * 
 * @descript (纳税总额)
 * @author 余思
 * @createTime 2017年2月14日下午2:53:57
 * @version 1.0
 */
@Service("taxService")
public class TaxService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	private static final String QUERYTAX = "TaxMapper.queryTax";	
	private static final String QUERYTAXSUM = "TaxMapper.queryTaxSum";				



	/**
	 * 
	 * @descript (纳税总额)
	 * @author 余思
	 * @since 2017年2月14日下午2:54:11
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public List<Map<String, Object>> queryTax(PageData pd) throws Exception {
		return (List<Map<String, Object>>)  dao.findForList(QUERYTAX,pd);
	}
	/**
	 * 
	 * @descript (纳税总额 饼图)
	 * @author 余思
	 * @since 2017年5月23日上午11:44:24
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String queryTaxSum(PageData pd) throws Exception {
		List<Map<String, Object>> listpages =  (List<Map<String, Object>>)dao.findForList(QUERYTAXSUM,pd);
		return "['企业',"+listpages.get(0).get("RATGRO")+"],['农专',"+listpages.get(1).get("RATGRO")+"],['个体',"+listpages.get(2).get("RATGRO")+"]";
	}
}
