package com.example.emb.service.common;

import ch.qos.logback.core.net.server.Client;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.example.emb.service.exception.SmsErrorException;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AliService {
    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.signName}")
    private String signName;

    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    public void sendSms(String phoneNumber, String templateParam) {
        try {
            // 初始化SDK
            DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
            IAcsClient client = new DefaultAcsClient(profile);

            // 构造请求
            SendSmsRequest request = new SendSmsRequest();
            request.setPhoneNumbers(phoneNumber);
            request.setSignName(signName);
            request.setTemplateCode(templateCode);
            request.setTemplateParam(templateParam);

            // 发送短信
            SendSmsResponse response = client.getAcsResponse(request);

            // 处理响应结果
            if ("OK".equals(response.getCode())) {
                System.out.println("短信发送成功！");
            } else {
                System.err.println("短信发送失败：" + response.getMessage());
                throw new SmsErrorException("短信发送失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

