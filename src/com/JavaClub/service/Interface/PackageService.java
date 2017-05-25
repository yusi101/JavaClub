package com.JavaClub.service.Interface;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.JavaClub.dao.DaoSupport;
import com.JavaClub.entity.Page;
import com.JavaClub.util.PageData;

@Service("packageService")
public class PackageService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public List<Map<String, Object>> queryPkglistPage(Page page) throws Exception {
		return (List<Map<String, Object>>) dao.findForListMap("PkgInfoMapper.queryPkgInfolistPage",page);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> packageInfo(PageData pd) throws Exception {
		return  (List<Map<String, Object>>) dao.findForList("PkgInfoMapper.queryPkgInfo",pd);
	}

	public int savePackage(PageData pd) throws Exception {
		return  (int) dao.save("PkgInfoMapper.savePackage",pd);
	}

	public int savePkgWay(PageData pd) throws Exception {
		return  (int) dao.save("PkgInfoMapper.savePkgWay",pd);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryPkgInfo(PageData pd) throws Exception {
		
		return (List<Map<String, Object>>) dao.findForList("PkgInfoMapper.queryPkgInfoById",pd);
	}

	public int updatePackage(PageData pd) throws Exception {
		 return (int) dao.update("PkgInfoMapper.updatePackage",pd);
	}
}
