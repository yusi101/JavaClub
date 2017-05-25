/**
 * @descript (用一句话描述改方法的作用)
 * @author 李海涛
 * @createTime 2016年9月16日下午4:28:00
 * @version 1.0
 */
package com.JavaClub.controller.system;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.JavaClub.controller.base.BaseController;
import com.JavaClub.util.Base64Image;
import com.JavaClub.util.FileUtil;
import com.JavaClub.util.MyGson;

/**
 * @descript (文件上传)
 * @author 李海涛
 * @createTime 2016年9月16日下午4:28:00
 * @version 1.0
 */
@Controller
@RequestMapping(value="filesController")
public class FilesController extends BaseController{

    /**
     * 
     * @descript (文件上传)
     * @author 李海涛
     * @since 2016年9月21日下午3:37:20
     * @param multipartRequest
     * @param response
     * @return fileName 文件名
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/FileUpload", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String FileUpload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        String uploadFileUrl=multipartRequest.getParameter("uploadFileUrl");
        uploadFileUrl= uploadFileUrl == null ?  multipartRequest.getSession().getServletContext().getRealPath("/uploadfiles") : uploadFileUrl;
        String fileName = this.get32UUID();
        // 获取多个file
        for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
            String key = (String) it.next();
            MultipartFile imgFile = multipartRequest.getFile(key);
            if (imgFile.getOriginalFilename().length() > 0) {
                fileName += imgFile.getOriginalFilename();
                try {
                    File file = new File(uploadFileUrl);
                    if (!file.exists() && !file.isDirectory()) {
                        file.mkdirs();
                    }
                    FileUtil.saveFileFromInputStream(imgFile.getInputStream(), uploadFileUrl, fileName);
                } catch (Exception e) {
                    return fileName;
                }
            }
        }
        Map<String , String> map=new HashMap<>(2);
        map.put("filePath", uploadFileUrl);
        map.put("fileName", fileName);
        
        return MyGson.toJson(map);
    }
    
    /**
     * 
     * @descript (图片上传 返回base64)
     * @author 李海涛
     * @since 2016年9月21日下午3:37:57
     * @param multipartRequest
     * @param response
     * @return base64编码
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/convertBase64", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String convertBase64(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        String base64 ="";
        // 获取多个file
        for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
            String key = (String) it.next();
            MultipartFile imgFile = multipartRequest.getFile(key);
            if (imgFile.getOriginalFilename().length() > 0) {
                base64 = Base64Image.getBase64StringFromInputStream(imgFile.getInputStream());
            }
        }
        return base64;
    }
    /**
     * 
     * @descript (邮件附件文件上传)
     * @author 余思
     * @since 2017年2月10日上午9:41:02
     * @param multipartRequest
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/emailFileUpload", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String emailFileUpload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        String uploadFileUrl=multipartRequest.getParameter("uploadFileUrl");
        uploadFileUrl= uploadFileUrl == null ?  multipartRequest.getSession().getServletContext().getRealPath("/uploadfiles/EmailMods") : uploadFileUrl;
        String fileName = this.get32UUID();
        // 获取多个file
        for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
            String key = (String) it.next();
            MultipartFile imgFile = multipartRequest.getFile(key);
            if (imgFile.getOriginalFilename().length() > 0) {
                fileName += imgFile.getOriginalFilename();
                try {
                    File file = new File(uploadFileUrl);
                    if (!file.exists() && !file.isDirectory()) {
                        file.mkdirs();
                    }
                    FileUtil.saveFileFromInputStream(imgFile.getInputStream(), uploadFileUrl, fileName);
                } catch (Exception e) {
                    return fileName;
                }
            }
        }
        Map<String , String> map=new HashMap<>(2);
        map.put("filePath", uploadFileUrl);
        map.put("fileName", fileName);
        
        return MyGson.toJson(map);
    }
}
