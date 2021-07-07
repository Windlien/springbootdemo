package com.example.springbootdemo.utils.my;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class AliSmsUtils {
    // https://mp.weixin.qq.com/s/ue5IHtc2m-LadM7l6kdoeg
    // 首先阿里云控制台开通短信服务
    public static final String accesskey = "XXXXXXXX";
    public static final String accessSecret = "XXXXXX";

    /**
     * SendSms接口是短信发送接口，支持在一次请求中向多个不同的手机号码发送同样内容的短信。
     * @param mobile
     * @param code
     * @return
     * @throws Exception
     */
    public static JSONObject sendSms(String mobile, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accesskey, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", "曼品");
        request.putQueryParameter("TemplateCode", "SMS_183745480");
        JSONObject param = new JSONObject();
        param.put("code", code);
        request.putQueryParameter("TemplateParam", param.toJSONString());
        //返回结果兼容旧接口格式
        JSONObject result = new JSONObject();
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println("ali_sms发送结果:{}"+ response);
            JSONObject data = JSONObject.parseObject(response.getData());
            if (data.get("Code").equals("OK")) {
                result.put("code", "0");
            } else {
                result.put("code", "1");
            }
            result.put("message", data.get("Message"));
        } catch (ServerException e) {
            System.out.println("ali_sms发送结果:{}"+ e.getMessage());
            result.put("code", "1");
            result.put("message", "发送失败");
        } catch (ClientException e) {
            System.out.println("ali_sms发送结果:{}"+ e.getMessage());
            result.put("code", "1");
            result.put("message", "发送失败");
        }
        return result;
    }

    public static void main(String[] args) {
        sendSms("151XXXXXXXX", "8888");
    }
}