package com.mycompany.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Login {

    private String loginurl = "http://passport.51.com/login.5p?callback=jsonp1390035314287&_=1390035333705&passport_51_jsonp=true&passport_51_user=liplip2006&passport_51_password=198812255yu&from=www_index_v_c&passport_auto_login=1&version=2";
    private String html;

    public static void main(String[] args) throws IOException {
        Login lo = new Login();
        CloseableHttpClient client = HttpClients.createDefault();
        lo.login(client);
        lo.gomain(client);
        client.close();

    }

    public void login(CloseableHttpClient client) {
        try {
            CloseableHttpResponse response1 = null;
            HttpGet httpGet = new HttpGet(loginurl);
            response1 = client.execute(httpGet);
            System.out.println(response1.getStatusLine());
            //cookies
            Header[] h = response1.getHeaders("Set-Cookie");
            for (Header header : h) {
                System.out.println(header.getValue());
            }
            HttpEntity entity1 = response1.getEntity();
            InputStream is = entity1.getContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i = -1;
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
            html = baos.toString();
            System.out.println(html);
            EntityUtils.consume(entity1);
            response1.close();
            try {
                httpGet.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gomain(CloseableHttpClient client) {
        try {
            String url = "http://my.51.com/?from=passport";
            CloseableHttpResponse response1 = null;
            HttpGet httpGet = new HttpGet(url);
            response1 = client.execute(httpGet);
            System.out.println(response1.getStatusLine());
            //cookies
            Header[] h = response1.getHeaders("Set-Cookie");
            for (Header header : h) {
                System.out.println(header.getValue());
            }
            HttpEntity entity1 = response1.getEntity();
            InputStream is = entity1.getContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i = -1;
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
            html = baos.toString();
            System.out.println(html);
            EntityUtils.consume(entity1);
            response1.close();
            try {
                httpGet.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void getcore(CloseableHttpClient client){
            try {             
                String url="http://my.51.com/task/reward/?gid=20&tid=435&callback=jsonp1390036841287&_=1390036842322";
                CloseableHttpResponse response1 = null;
                HttpGet httpGet = new HttpGet(url);
                response1 = client.execute(httpGet);
                System.out.println(response1.getStatusLine());
                //cookies
                Header[] h = response1.getHeaders("Set-Cookie");
                for (Header header : h) {
                    System.out.println(header.getValue());
                }
                HttpEntity entity1 = response1.getEntity();
                InputStream is = entity1.getContent();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int i = -1;
                while ((i = is.read()) != -1) {
                    baos.write(i);
                }
                html = baos.toString();
                System.out.println(html);
                EntityUtils.consume(entity1);
                response1.close();
                try {
                    httpGet.clone();
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }               
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    
    
    
    }
    
    
    
}
