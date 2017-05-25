package com.JavaClub.controller.credit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.service.credit.AnnualReportAnalysisService;
import com.JavaClub.service.credit.IndustryTypeService;
import com.JavaClub.service.credit.PrivatepartyService;
import com.JavaClub.util.ListUtils;
import com.JavaClub.util.PageData;

/**
 * 
 *  descript (非公党建人员分析)
 *  author 余思
 *  createTime 2017年1月12日上午9:50:01
 *  version 1.0
 */
 @Controller
 @RequestMapping(value = "/privatepartyController")
public class PrivatepartyController extends BaseController{

	 @Autowired
	PrivatepartyService privatepartyService;
	
	private static final String qiye1 = "qiye1";
	private static final String qiye2 = "qiye2";
	private static final String NUMBER = "NUMBER";
	private static final String ENTTYPE = "ENTTYPE";
	/**
	 * 
	 *  descript (查询从业人员)
	 *  author 余思
	 *  since 2017年1月10日上午11:03:54
	 *  return
	 *  throws Exception
	 */
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 @RequestMapping(value = "/queryEmployee", produces = "text/html;charset=UTF-8")
	public ModelAndView queryEmployee() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd=this.getPageData();
		
		List<Map<String,Object>> listpage =privatepartyService.queryPrivateparty(pd);
		
		int sum1=0,sum2=0,sum3=0,sum4=0,sum5=0,sum6=0;
		int sum1s=0,sum2s=0,sum3s=0,sum4s=0,sum5s=0,sum6s=0;
		int sum1n=0,sum2n=0,sum3n=0,sum4n=0,sum5n=0,sum6n=0;
		List jingy =new ArrayList<>();
		List guz =new ArrayList<>();
		List sum =new ArrayList<>();
		List<Map<String,String>> listpage1 =new ArrayList<>();
		if(ListUtils.isNotEmpty(listpage)){
		
			int sumqy1=0,sumnz1=0,sumgt1=0;
			int sumqy2=0,sumnz2=0,sumgt2=0;
			int sumqy3=0,sumnz3=0,sumgt3=0;
			int sumqy4=0,sumnz4=0,sumgt4=0;
			int sumqy5=0,sumnz5=0,sumgt5=0;
			int sumqy6=0,sumnz6=0,sumgt6=0;
			int sumqy7=0,sumnz7=0,sumgt7=0;
			int sumqy8=0,sumnz8=0,sumgt8=0;
//			市场主体类型 1为企业，2为农专，3为个体
			for (Map<String, Object> map : listpage) {
				if(map.get("TYPE").toString().equals("1") || map.get("TYPE").toString().equals("2")){
					sum1+=Integer.parseInt(map.get(NUMBER).toString());
					if(map.get("TYPE").toString().equals("1")){
						sum1s+=Integer.parseInt(map.get(NUMBER).toString());
						switch (map.get(ENTTYPE).toString()) {
						case "1":
							sumqy1+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						case "2":
							sumnz1+=Integer.parseInt(map.get(NUMBER).toString());				
							break;
						case "3":
							sumgt1+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						}
					}else{
						sum1n+=Integer.parseInt(map.get(NUMBER).toString());
						switch (map.get(ENTTYPE).toString()) {
						case "1":
							sumqy2+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						case "2":
							sumnz2+=Integer.parseInt(map.get(NUMBER).toString());				
							break;
						case "3":
							sumgt3+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						}
					}
				}
			}
			Map<String, String> map1=new HashMap<>();
			map1.put("name", "高校毕业生");
			map1.put("sum1", (sumqy1+sumnz1+sumgt1)+" ");
			map1.put(qiye1, sumqy1+" ");
			map1.put("nz1", sumnz1+" ");
			map1.put("gts1", sumgt1+" ");
			map1.put("sum2", " "+(sumqy2+sumnz2+sumgt2));
			map1.put(qiye2, " "+sumqy2);
			map1.put("nz2", " "+sumnz2);
			map1.put("gts2", " "+sumgt2);
			listpage1.add(map1);
			for (Map<String, Object> map : listpage) {
				 if(map.get("TYPE").toString().equals("3") || map.get("TYPE").toString().equals("4")){
					sum2+=Integer.parseInt(map.get(NUMBER).toString());
					if(map.get("TYPE").toString().equals("3")){
						sum2s+=Integer.parseInt(map.get(NUMBER).toString());
						switch (map.get(ENTTYPE).toString()) {
						case "1":
							sumqy3+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						case "2":
							sumnz3+=Integer.parseInt(map.get(NUMBER).toString());				
							break;
						case "3":
							sumgt3+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						}
					}else{
						sum2n+=Integer.parseInt(map.get(NUMBER).toString());
						switch (map.get(ENTTYPE).toString()) {
						case "1":
							sumqy4+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						case "2":
							sumnz4+=Integer.parseInt(map.get(NUMBER).toString());				
							break;
						case "3":
							sumgt4+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						}
					}
				}
			}
			Map<String, String> map2=new HashMap<>();
			map2.put("name", "退役士兵");
			map2.put("sum1", (sumqy3+sumnz3+sumgt3)+" ");
			map2.put(qiye1, sumqy3+" ");
			map2.put("nz1", sumnz3+" ");
			map2.put("gts1", sumgt3+" ");
			map2.put("sum2", " "+(sumqy4+sumnz4+sumgt4));
			map2.put(qiye2, " "+sumqy4);
			map2.put("nz2", " "+sumnz4);
			map2.put("gts2", " "+sumgt4);
			listpage1.add(map2);
			for (Map<String, Object> map : listpage) {
				if(map.get("TYPE").toString().equals("5") || map.get("TYPE").toString().equals("6")){
					sum3+=Integer.parseInt(map.get(NUMBER).toString());
					if(map.get("TYPE").toString().equals("5")){
						sum3s+=Integer.parseInt(map.get(NUMBER).toString());
						switch (map.get(ENTTYPE).toString()) {
						case "1":
							sumqy5+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						case "2":
							sumnz5+=Integer.parseInt(map.get(NUMBER).toString());				
							break;
						case "3":
							sumgt5+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						}
					}else{
						sum3n+=Integer.parseInt(map.get(NUMBER).toString());
						switch (map.get(ENTTYPE).toString()) {
						case "1":
							sumqy6+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						case "2":
							sumnz6+=Integer.parseInt(map.get(NUMBER).toString());				
							break;
						case "3":
							sumgt6+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						}
					}
				}
			}
			Map<String, String> map3=new HashMap<>();
			map3.put("name", "残疾人");
			map3.put("sum1", (sumqy5+sumnz5+sumgt5)+"");
			map3.put(qiye1, sumqy5+"");
			map3.put("nz1", sumnz5+"");
			map3.put("gts1", sumgt5+"");
			map3.put("sum2", " "+(sumqy6+sumnz6+sumgt6));
			map3.put(qiye2, " "+sumqy6);
			map3.put("nz2", " "+sumnz6);
			map3.put("gts2", " "+sumgt6);
			listpage1.add(map3);
			for (Map<String, Object> map : listpage) {
				if(map.get("TYPE").toString().equals("7") || map.get("TYPE").toString().equals("8")){
					sum4+=Integer.parseInt(map.get(NUMBER).toString());
					if(map.get("TYPE").toString().equals("7")){
						sum4s+=Integer.parseInt(map.get(NUMBER).toString());
						switch (map.get(ENTTYPE).toString()) {
						case "1":
							sumqy7+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						case "2":
							sumnz7+=Integer.parseInt(map.get(NUMBER).toString());				
							break;
						case "3":
							sumgt7+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						}
					}else{
						sum4n+=Integer.parseInt(map.get(NUMBER).toString());
						switch (map.get(ENTTYPE).toString()) {
						case "1":
							sumqy8+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						case "2":
							sumnz8+=Integer.parseInt(map.get(NUMBER).toString());				
							break;
						case "3":
							sumgt8+=Integer.parseInt(map.get(NUMBER).toString());
							break;
						}
					}
				}
			}
			Map<String, String> map4=new HashMap<>();
			map4.put("name", "再就业");
			map4.put("sum1", (sumqy7+sumnz7+sumgt7)+" ");
			map4.put(qiye1, sumqy7+" ");
			map4.put("nz1", sumnz7+" ");
			map4.put("gts1", sumgt7+" ");
			map4.put("sum2", (sumqy8+sumnz8+sumgt8)+"");
			map4.put(qiye2, sumqy8+"");
			map4.put("nz2", sumnz8+""); 
			map4.put("gts2", sumgt8+"");
			listpage1.add(map4);
			Map<String, String> map5=new HashMap<>();
			map5.put("name", "党员/预备党员");
			map5.put("sum1", "0");
			map5.put(qiye1, "0");
			map5.put("nz1", "0"); 
			map5.put("gts1", "0");
			map5.put("sum2", "0");
			map5.put(qiye2, "0");
			map5.put("nz2", "0"); 
			map5.put("gts2", "0");
			listpage1.add(map5);
			Map<String, String> map6=new HashMap<>();
			map6.put("name", "农民人数");
			map6.put("sum1", "0");
			map6.put(qiye1, "0");
			map6.put("nz1", "0"); 
			map6.put("gts1", "0");
			map6.put("sum2", "0");
			map6.put(qiye2, "0");
			map6.put("nz2", "0"); 
			map6.put("gts2", "0");
			listpage1.add(map6);
			
		}
		
		jingy.add(sum1s);
		jingy.add(sum2s);
		jingy.add(sum3s);
		jingy.add(sum4s);
		jingy.add(sum5s);
		jingy.add(sum6s);
		
		guz.add(sum1n);
		guz.add(sum2n);
		guz.add(sum3n);
		guz.add(sum4n);
		guz.add(sum5n);
		guz.add(sum6n);
		
		sum.add(sum1);
		sum.add(sum2);
		sum.add(sum3);
		sum.add(sum4);
		sum.add(sum5);
		sum.add(sum6);
		
		//饼图所需要的格式
		//String yuan1 ="['企业',"+sumqy+"],['农专',"+sumnz+"],['个体',"+sumgt+"]";
//		int sum=0;
//		for(int i=0;i<listpage.size();i++){
//			sum=sum+(int)listpage.get(i).get("COUNT");
//		}
//		pd.put("sum", sum);
		
		mv.addObject("sum", sum);
		mv.addObject("guz", guz);
		mv.addObject("jingy", jingy);
		mv.addObject("listpages", listpage);
		mv.addObject("listpages1", listpage1);
		mv.addObject("pd", pd);
		//mv.addObject("yuan", yuan1);
		
		mv.setViewName("privateparty/employee");
		return mv;
	}
	 
		@Autowired
		IndustryTypeService industryTypeService;
		
		@Autowired
		AnnualReportAnalysisService annualReportAnalysisService;
		/**
		 * 
		 * @descript (查询非公党建人员)3
		 * @author 余思
		 * @since 2017年1月17日上午9:49:53
		 * @return
		 * @throws Exception
		 */
		 @SuppressWarnings({ "rawtypes", "unchecked" })
		 @RequestMapping(value = "/queryPrivateparty", produces = "text/html;charset=UTF-8")
		public ModelAndView queryPrivateparty() throws Exception {
			ModelAndView mv = this.getModelAndView();
			PageData pd=this.getPageData();
			pd=this.getPageData();
			List<Map<String,Object>> listpage =privatepartyService.queryPrivateparty(pd);
			List  countyList = new ArrayList<>();
			countyList.add("党委");
			countyList.add("党总支");
			countyList.add("党支部");
			countyList.add("未成立");
			countyList.add("未公布");
			List  countyList1 = new ArrayList<>();
			countyList1.add("'法定代表人/经营者为党组织书记'");
			countyList1.add("'法定代表人/经营者为党员'");
			countyList1.add("'法定代表人/经营者为群众'");
			countyList1.add("'其他'");
			
			int dw_qy=0,dw_gt=0,dzz_qy=0,dzz_gt=0,dzb_qy=0,dzb_gt=0,wcl_qy=0,wcl_gt=0,wgb_qy=0,wgb_gt=0;
			int zzsj_qy=0,zzsj_gt=0,dy_qy=0,dy_gt=0,qz_qy=0,qz_gt=0,qt_qy=0,qt_gt=0;
			for (Map<String, Object> map : listpage) {
				if(map.get("TYPE").toString().equals("21")){//党委
					if(map.get(ENTTYPE).toString().equals("1")){//企业
						dw_qy+=Integer.parseInt(map.get(NUMBER).toString());
					}else if(map.get(ENTTYPE).toString().equals("3")){//个体
						dw_gt+=Integer.parseInt(map.get(NUMBER).toString());
					}
				}
				if(map.get("TYPE").toString().equals("22")){//党总支
					if(map.get(ENTTYPE).toString().equals("1")){//企业
						dzz_qy+=Integer.parseInt(map.get(NUMBER).toString());
					}else if(map.get(ENTTYPE).toString().equals("3")){//个体
						dzz_gt+=Integer.parseInt(map.get(NUMBER).toString());
					}
				}
				if(map.get("TYPE").toString().equals("23")){//党支部
					if(map.get(ENTTYPE).toString().equals("1")){//企业
						dzb_qy+=Integer.parseInt(map.get(NUMBER).toString());
					}else if(map.get(ENTTYPE).toString().equals("3")){//个体
						dzb_gt+=Integer.parseInt(map.get(NUMBER).toString());
					}
				}
				if(map.get("TYPE").toString().equals("24")){//未成立
					if(map.get(ENTTYPE).toString().equals("1")){//企业
						wcl_qy+=Integer.parseInt(map.get(NUMBER).toString());
					}else if(map.get(ENTTYPE).toString().equals("3")){//个体
						wcl_gt+=Integer.parseInt(map.get(NUMBER).toString());
					}
				}
				if(map.get("TYPE").toString().equals("25")){//未公布
					if(map.get(ENTTYPE).toString().equals("1")){//企业
						wgb_qy+=Integer.parseInt(map.get(NUMBER).toString());
					}else if(map.get(ENTTYPE).toString().equals("3")){//个体
						wgb_gt+=Integer.parseInt(map.get(NUMBER).toString());
					}
				}
				if(map.get("TYPE").toString().equals("41")){//法定代表人/经营者为党组织书记
					if(map.get(ENTTYPE).toString().equals("1")){//企业
						zzsj_qy+=Integer.parseInt(map.get(NUMBER).toString());
					}else if(map.get(ENTTYPE).toString().equals("3")){//个体
						zzsj_gt+=Integer.parseInt(map.get(NUMBER).toString());
					}
				}
				if(map.get("TYPE").toString().equals("31")){//法定代表人/经营者为党员
					if(map.get(ENTTYPE).toString().equals("1")){//企业
						dy_qy+=Integer.parseInt(map.get(NUMBER).toString());
					}else if(map.get(ENTTYPE).toString().equals("3")){//个体
						dy_gt+=Integer.parseInt(map.get(NUMBER).toString());
					}
				}
				if(map.get("TYPE").toString().equals("32")){//法定代表人/经营者为群众
					if(map.get(ENTTYPE).toString().equals("1")){//企业
						qz_qy+=Integer.parseInt(map.get(NUMBER).toString());
					}else if(map.get(ENTTYPE).toString().equals("3")){//个体
						qz_gt+=Integer.parseInt(map.get(NUMBER).toString());
					}
				}
				if(map.get("TYPE").toString().equals("30")){//其他
					if(map.get(ENTTYPE).toString().equals("1")){//企业
						qt_qy+=Integer.parseInt(map.get(NUMBER).toString());
					}else if(map.get(ENTTYPE).toString().equals("3")){//个体
						qt_gt+=Integer.parseInt(map.get(NUMBER).toString());
					}
				}
			}
			List<Integer> listqy =new ArrayList<>();
			List<Integer> listgt =new ArrayList<>();
			listqy.add(dw_qy);
			listqy.add(dzz_qy);
			listqy.add(dzb_qy);
			listqy.add(wcl_qy);
			listqy.add(wgb_qy);
			listgt.add(dw_gt);
			listgt.add(dzz_gt);
			listgt.add(dzb_gt);
			listgt.add(wcl_gt);
			listgt.add(wgb_gt);
			List<Integer> jylistqy =new ArrayList<>();
			List<Integer> jylistgt =new ArrayList<>();
			jylistqy.add(zzsj_qy);
			jylistqy.add(dy_qy);
			jylistqy.add(qz_qy);
			jylistqy.add(qt_qy);
			jylistgt.add(zzsj_gt);
			jylistgt.add(dy_gt);
			jylistgt.add(qz_gt);
			jylistgt.add(qt_gt);
			List<Integer> jylistsum =new ArrayList<>();
			for(int i=0;i<jylistqy.size();i++){
				jylistsum.add(jylistqy.get(i)+jylistgt.get(i));
			}
			//饼图所需要的格式
//			String yuan1 ="['企业',"+sumqy+"],['农专',"+sumnz+"],['个体',"+sumgt+"]";
			String yuan="";
			List<Map<String,String>> listpageTa =new ArrayList<>();
			for(int i=0;i<countyList.size();i++){
				yuan +="['"+countyList.get(i)+"',"+(listqy.get(i)+listgt.get(i))+"],";
				Map<String, String> map=new HashMap<>();
				map.put("name", countyList.get(i).toString());
				map.put("qy", listqy.get(i).toString());
				map.put("gts", listgt.get(i).toString());
				listpageTa.add(map);
			}
			mv.addObject("pd", pd);
			mv.addObject("listpageTa", listpageTa);
			mv.addObject("dataAxis", countyList.toArray());
			mv.addObject("dataAxisjy", countyList1.toArray());
			mv.addObject("jylistsum", jylistsum);
			
			mv.addObject("jylistqy", jylistqy);
			mv.addObject("jylistgt", jylistgt);
			mv.addObject("listqy", listqy);
			mv.addObject("listgt", listgt);
			mv.addObject("yuan", yuan);
			mv.setViewName("privateparty/privateparty");
			return mv;
		}
}
