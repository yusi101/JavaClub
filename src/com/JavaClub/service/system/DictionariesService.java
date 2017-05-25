package com.JavaClub.service.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("dictionariesService")
public class DictionariesService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;


	//新增
	public void insertDictionaries(PageData pd)throws Exception{
		dao.save("DictionariesMapper.save", pd);
	}

	//修改
	public int updateDictionariesById(PageData pd)throws Exception{
		int num = (int) dao.update("DictionariesMapper.edit", pd);
		return num;
	}

	//通过id获取数据
	public PageData queryDictionariesById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DictionariesMapper.findById", pd);
	}

	//查询总数
	public PageData findCount(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DictionariesMapper.findCount", pd);
	}

	//查询某编码
	public PageData findBmCount(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DictionariesMapper.findBmCount", pd);
	}

	//列出同一父类id下的数据
	@SuppressWarnings("unchecked")
	public List<PageData> dictlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("DictionariesMapper.dictlistPage", page);

	}

	//删除
	public void delete(PageData pd) throws Exception {
		dao.delete("DictionariesMapper.delete", pd);

	}

	//	 查询字典表中的数据
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryDictionaries(PageData pd) throws Exception {
		return (List<Map<String,Object>>) dao.findForList("DictionariesMapper.findDictionaries", pd);
	}
	
	/**
	 * 
	 * @descript (判断)
	 * @author 魏旋
	 * @since 2017年2月14日下午3:27:24
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	 @SuppressWarnings("unchecked")
		public List<Map<String, Object>> queryDict(PageData pd) throws Exception{
			return (List<Map<String, Object>>) dao.findForList("DictionariesMapper.querydict", pd);
		}

}
