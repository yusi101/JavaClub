package com.JavaClub.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.JavaClub.entity.system.User;
import com.JavaClub.util.Const;


/**
 * 
 * @ClassName: LoginHandlerInterceptor 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author XWang
 * @date 2016年3月21日 下午5:43:50 
 *
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO Auto-generated method stub
        String path = request.getServletPath();
       /* Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        String str=map.toString();
        String ip = "";
        if (request.getHeader("x-forwarded-for") == null) {  
            ip = request.getRemoteAddr();  
        }else{
            ip = request.getHeader("x-forwarded-for");  
        }
        File fileName=new File(request.getSession().getServletContext().getRealPath(Const.IMGPATH)+"/test.txt");
        FileWriter fw = null;
        try {
            if(!fileName.exists()){  
                fileName.createNewFile(); 
            }
            fw = new FileWriter(fileName,false);
            fw.write(str+request.getRemoteAddr()+"==="+request.getHeader("x-forwarded-for")+"======="+request.getRemoteHost());
            fw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }*/
               
        if(path.matches(Const.NO_INTERCEPTOR_PATH)){
            return true;
        }else{
           //shiro管理的session
            Subject currentUser = SecurityUtils.getSubject();  
            Session session = currentUser.getSession();
            User user = (User)session.getAttribute(Const.SESSION_USER);
            if(user!=null){
                return true;
            }else{
                //登陆过滤
                //response.sendRedirect(request.getSession().getServletContext() + Const.LOGIN);
                return true;	
            }
        }
    }

}
