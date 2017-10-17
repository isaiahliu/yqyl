package org.trinity.yqyl.process.controller;

import java.net.URI;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.trinity.common.exception.IException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.message.exception.GeneralErrorMessage;
import org.trinity.yqyl.common.message.exception.ErrorMessage;
import org.trinity.yqyl.process.controller.base.ISmsProcessController;

@Service
public class SmsProcessController implements ISmsProcessController {
    @Value("${args.sms.server}")
    private String server;

    @Value("${args.sms.appid}")
    private String appid;

    @Value("${args.sms.appsec}")
    private String appsec;

    @Value("${args.sms.batch}")
    private String batch;

    private final String nonceStr = "YQYL";

    private int serialNo = 1;

    @Autowired
    private IExceptionFactory exceptionFactory;

    @Override
    public void sendMessage(final String cellphone, final String verifyCode) throws IException {
        final int no = serialNo++;
        final String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        final String content = String.format("【泰安一卡通】尊敬的益券养老用户,您的验证码是:%s", verifyCode);
        final String str = String.format(
                "app_key=%s&batch_num=%s&content=%s&dest_id=%s&mission_num=%s&nonce_str=%s&sms_type=verify_code&time_stamp=%s&app_secret=%s",
                appid, batch, content, cellphone, no, nonceStr, timestamp, appsec);
        System.out.println(str);
        final String sign = md5(str);
        System.out.println(sign);
        final Document document = DocumentFactory.getInstance().createDocument();
        document.setXMLEncoding("UTF-8");

        final Element root = document.addElement("xml");

        final Element head = root.addElement("head");
        head.addElement("app_key").setText(appid);
        head.addElement("time_stamp").setText(timestamp);
        head.addElement("nonce_str").setText(nonceStr);
        head.addElement("sign").setText(sign);

        final Element body = root.addElement("body");
        final Element dest = body.addElement("dests").addElement("dest");
        dest.addElement("mission_num").setText(String.valueOf(no));
        dest.addElement("dest_id").setText(cellphone);

        body.addElement("batch_num").setText(batch);
        body.addElement("sms_type").setText("verify_code");
        body.addElement("content").setText(content);

        final String xml = root.asXML();
        System.out.println(xml);
        final List<HttpMessageConverter<?>> converters = new ArrayList<>();
        final HttpMessageConverter<String> converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(converter);

        final RestTemplate restTemplate = new RestTemplate(converters);

        final RequestEntity<String> entity = new RequestEntity<>(xml, HttpMethod.POST,
                URI.create("http://" + server + "/api/v1/manySend"));

        final String response = restTemplate.exchange(entity, String.class).getBody();

        Matcher matcher = Pattern.compile("<error_code>(\\d+)</error_code>").matcher(response);
        matcher.find();
        final String errorCode = matcher.group(1);

        matcher = Pattern.compile("<error_msg>(.*)</error_msg>").matcher(response);
        matcher.find();
        final String message = matcher.group(1);

        if (Integer.parseInt(errorCode) != 0) {
            throw exceptionFactory.createException(ErrorMessage.SEND_SMS_FAILED, message);
        }
    }

    private String md5(final String code) throws IException {
        try {
            final MessageDigest md5 = MessageDigest.getInstance("MD5");

            final StringBuilder str = new StringBuilder();
            for (final byte b : md5.digest(code.getBytes("utf-8"))) {
                final String s = Integer.toHexString(Byte.toUnsignedInt(b));
                if (s.length() == 1) {
                    str.append("0");
                }
                str.append(s);
            }
            return str.toString();
        } catch (final Exception e) {
            throw exceptionFactory.createException(GeneralErrorMessage.UNKNOWN_EXCEPTION);
        }
    }
}
