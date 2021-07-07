package com.example.springbootdemo.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

@Controller
public class FileController {
    private static final Pattern PATTERN_P = Pattern.compile("\\.");
    private static final Logger logger = Logger.getLogger(FileController.class);
    private static final String FILE_JES_SCRIPT_FOR_WHAT = "JES_RESOURCE";
	@Autowired
	JdbcTemplate jdbcTemplate;

    /**
     * @param response
     * @param storageType   存储类型  cp--classpath;db---FileEx;sc---jes_script
     * @param key		关键字,标识号
     * @param formatType	图片格式  gif/jpeg/png/base64/
     */
    @ResponseBody
    @RequestMapping(value = "/{storageType:cp|db|cs}/{ssId}/{key}.{formatType:gif|jpeg|png|jpg|ico}",produces ={"image/gif","image/jpeg","image/png","image/x-icon"})
    public void getImageResource(HttpServletResponse response, @PathVariable("storageType") String storageType,
    		@PathVariable("ssId") String ssId,@PathVariable("formatType") String formatType,
    		@PathVariable("key") String key) throws FileNotFoundException {
    	//图片格式转为小写用于判断
    	formatType = formatType.toLowerCase();
    	key = checkClassPathFileKey(storageType, key, formatType);
		if ("gif".equals(formatType)||".gif".equals(formatType)) {
			response.setContentType("image/gif");
			
		} else if ("jpeg".equals(formatType) || ".jpeg".equals(formatType) 
				|| "jpg".equals(formatType) || ".jpg".equals(formatType)) {
			response.setContentType("image/jpeg");
			
		} else if ("png".equals(formatType)||".png".equals(formatType)) {
			response.setContentType("image/png");
			
		}  else if ("ico".equals(formatType)||".ico".equals(formatType)) {
			response.setContentType("image/x-icon");
		}else {
            logger.error("function getImageFile() formatType is (" + formatType + ") not support");
            throw new FileNotFoundException("该请求不支持格式为("+formatType+")格式的图片");
		}
        byte[] resouceContent = getFileContent(storageType , key, ssId, formatType);
        try {
            response.getOutputStream().write(resouceContent);
            response.flushBuffer();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private byte[] getFileContent(final String storageType, final String key ,final String ssId,
    		String formatType) throws FileNotFoundException{
    	if(null == formatType){
    		formatType = "";
    	}
        byte[] resourceBuilder = null;
		resourceBuilder = jdbcTemplate.queryForObject("select file_blob from jes_file_ex where ss_id=? and file_key=?",byte[].class,ssId,key);
        if(null == resourceBuilder){
            System.out.println(" - 未找到有效的资源");
        	 throw new FileNotFoundException("未找到有效的资源(ssId="
        	 + ssId +";storageType="+storageType+";fileKey="+key+")");
        }
        return resourceBuilder;
    }

    private String checkClassPathFileKey(String storageType, String key, String formatType){
    	if("cp".equals(storageType)){
    		if(!key.endsWith("."+formatType)){
    			key = key + "." + formatType;
    		}
    	}
    	return key;
    }
}
