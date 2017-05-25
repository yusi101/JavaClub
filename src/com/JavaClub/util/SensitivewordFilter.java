package com.JavaClub.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.JavaClub.service.credit.KeywordService;

/**
 * 
 * @descript (敏感词过滤)
 * @author 李海涛
 * @createTime 2016年12月12日下午3:22:43
 * @version 1.0
 */
public class SensitivewordFilter {
	@SuppressWarnings("rawtypes")
	private Map sensitiveWordMap = null;
	public static final int minMatchTYpe = 1;      //最小匹配规则
	public static final int maxMatchType = 2;      //最大匹配规则
	
	/**
	 * 构造函数，初始化敏感词库
	 */
	public SensitivewordFilter(){
		AbstractApplicationContext applicationContext =  new ClassPathXmlApplicationContext("classpath:spring/ApplicationContext.xml");
		KeywordService keywordService = applicationContext.getBean("keywordService", KeywordService.class);
		try {
			PageData pd=new PageData();
			pd.put("STATUS", "1");
			sensitiveWordMap = addSensitiveWordToHashMap(keywordService.queryKeyword(pd));
			
		} catch (Exception e) {
		}finally {
			applicationContext.close();
		}
	}
	
	/**
	 * 
	 * @descript (判断文字是否包含敏感字符)
	 * @author 李海涛
	 * @since 2016年12月12日下午3:23:22
	 * @param txt 文字
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return 若包含返回true，否则返回false
	 */
	public boolean isContaintSensitiveWord(String txt,int matchType){
		boolean flag = false;
		for(int i = 0 ; i < txt.length() ; i++){
			int matchFlag = this.CheckSensitiveWord(txt, i, matchType); //判断是否包含敏感字符
			if(matchFlag > 0){    //大于0存在，返回true
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 
	 * @descript (获取文字中的敏感词)
	 * @author 李海涛
	 * @since 2016年12月12日下午3:23:55
	 * @param txt 文字
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return
	 */
	public Set<String> getSensitiveWord(String txt , int matchType){
		Set<String> sensitiveWordList = new HashSet<String>();
		
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i, matchType);    //判断是否包含敏感字符
			if(length > 0){    //存在,加入list中
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;    //减1的原因，是因为for会自增
			}
		}
		
		return sensitiveWordList;
	}
	
	/**
	 * 
	 * @descript (替换敏感字字符)
	 * @author 李海涛
	 * @since 2016年12月12日下午3:24:13
	 * @param txt
	 * @param matchType
	 * @param replaceChar 替换字符，默认*
	 * @return
	 */
	public  String replaceSensitiveWord(String txt,int matchType,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType);     //获取所有的敏感词
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		
		return resultTxt;
	}
	
	/**
	 * 
	 * @descript (获取替换字符串)
	 * @author 李海涛
	 * @since 2016年12月12日下午3:24:33
	 * @param replaceChar
	 * @param length
	 * @return
	 */
	private String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}
		
		return resultReplace;
	}
	
	/**
	 * 
	 * @descript (检查文字中是否包含敏感字符，检查规则如下：)
	 * @author 李海涛
	 * @since 2016年12月12日下午3:24:47
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return 如果存在，则返回敏感词字符的长度，不存在返回0
	 */
	@SuppressWarnings({ "rawtypes"})
	public int CheckSensitiveWord(String txt,int beginIndex,int matchType){
		boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0;     //匹配标识数默认为0
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);     //获取指定key
			if(nowMap != null){     //存在，则判断是否为最后一个
				matchFlag++;     //找到相应key，匹配标识+1 
				if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
					flag = true;       //结束标志位为true   
					if(SensitivewordFilter.minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
						break;
					}
				}
			}
			else{     //不存在，直接返回
				break;
			}
		}
		if(matchFlag < 2 || !flag){        //长度必须大于等于1，为词 
			matchFlag = 0;
		}
		return matchFlag;
	}
	/**
	 * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
	 * 中 = {
	 *      isEnd = 0
	 *      国 = {<br>
	 *      	 isEnd = 1
	 *           人 = {isEnd = 0
	 *                民 = {isEnd = 1}
	 *                }
	 *           男  = {
	 *           	   isEnd = 0
	 *           		人 = {
	 *           			 isEnd = 1
	 *           			}
	 *           	}
	 *           }
	 *      }
	 *  五 = {
	 *      isEnd = 0
	 *      星 = {
	 *      	isEnd = 0
	 *      	红 = {
	 *              isEnd = 0
	 *              旗 = {
	 *                   isEnd = 1
	 *                  }
	 *              }
	 *      	}
	 *      }
	 * @author 李海涛
	 * @since 2016年12月12日下午3:24:47
	 * @param keyWordSet  敏感词库
	 * @version 1.0
	 * @return 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map addSensitiveWordToHashMap(List<Map<String,Object>> keyWordSet) {
		Map sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
		String key = null;  
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		//迭代keyWordSet
		Iterator<Map<String,Object>> iterator = keyWordSet.iterator();
		while(iterator.hasNext()){
			key = iterator.next().get("KEYWORD").toString();    //关键字
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);       //转换成char型
				Object wordMap = nowMap.get(keyChar);       //获取
				
				if(wordMap != null){        //如果存在该key，直接赋值
					nowMap = (Map) wordMap;
				}
				else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
					newWorMap = new HashMap<String,String>();
					newWorMap.put("isEnd", "0");     //不是最后一个
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}
				
				if(i == key.length() - 1){
					nowMap.put("isEnd", "1");    //最后一个
				}
			}
		}
		return sensitiveWordMap;
	}
	public static void main(String[] args) {
		/*SensitivewordFilter filter = new SensitivewordFilter();
		String string = "太多的伤感情怀也许只局限于饲养基地 荧幕中的情节毛爷爷复活，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
						+ "然后法轮功 我们的扮演的角色就是跟随着主人公的毛 泽东复活，喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
						+ "难过就躺在某一个人带考的怀里尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三级片 深人静的晚上，关上电话静静的发呆着。毛泽东";
		System.out.println("待检测语句字数：" + string.length());
		long beginTime = System.currentTimeMillis();
		Set<String> set = filter.getSensitiveWord(string, 2);
		long endTime = System.currentTimeMillis();
		System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
		System.out.println("总共消耗时间为：" + (endTime - beginTime));
		System.out.println(filter.replaceSensitiveWord(string, 1, "*"));*/
		 
		AbstractApplicationContext applicationContext =  new ClassPathXmlApplicationContext("classpath:spring/ApplicationContext.xml");
		System.out.println("5555");
		applicationContext.close();
	}
}
