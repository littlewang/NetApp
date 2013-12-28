package com.mycompany.mavenproject1;

import com.mycompany.db.WwdEmbedded;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        App app = new App();
        app.first(httpclient);
        app.secode(httpclient);
        app.name(httpclient);
        app.img(httpclient);
        app.cheack(httpclient);
        app.third(httpclient);
        app.fourth(httpclient);
        app.fifth(httpclient);
        app.sixth(httpclient);
        app.seventh();
    }
    private String html;
    private String imgurl;
    private String baseimgurl;
    private String src;
    private String code;
    private String username;
    private String gobject;

    public void first(CloseableHttpClient httpclient) {
        CloseableHttpResponse response1 = null;
        try {
            HttpGet httpGet = new HttpGet("http://passport.51.com/reg2.5p");
            response1 = httpclient.execute(httpGet);
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
            EntityUtils.consume(entity1);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                response1.close();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void secode(CloseableHttpClient httpclient) {
        Document doc = Jsoup.parse(html);
        Element newsHeadlines = doc.select("#pp_reg_authcode_img").first();
        src = newsHeadlines.attr("src");
        baseimgurl = "http://passport.51.com/authcode?key=" + src;
        imgurl = baseimgurl + "&v=" + new Date().getTime();
    }

    public void name(CloseableHttpClient httpclient) {
        username = "";
        for (int i = 0; i < 9; i++) {//你想生成几个字符的，就把3改成几，如果改成１,那就生成一个随机字母．
            username = username + (char) (Math.random() * 26 + 'A');
        }
        System.out.println(username);
    }

    public void img(CloseableHttpClient httpclient) {
        try {
            HttpGet httpGet = new HttpGet(imgurl);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            System.out.println(response1.getStatusLine());
            File storeFile = new File("code.jpg");
            InputStream is = response1.getEntity().getContent();
            OutputStream os = new FileOutputStream(storeFile);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            is.close();
            code = (String) JOptionPane.showInputDialog(null, null, "验证�?", JOptionPane.INFORMATION_MESSAGE, new ImageIcon("code.jpg"), null, null);
            imgurl = baseimgurl + "&v=" + new Date().getTime();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cheack(CloseableHttpClient httpclient) {
        String url = "http://passport.51.com/authcode/checkapi?key=" + src + "&code=" + code;
        CloseableHttpResponse response1 = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            response1 = httpclient.execute(httpGet);
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
            System.out.println(baos.toString());
            EntityUtils.consume(entity1);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                response1.close();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void third(CloseableHttpClient httpclient) {
        try {
            HttpGet httpGet = new HttpGet("http://passport.51.com/stat/reg?t=username&c=passport&q=authcode");
            httpclient.execute(httpGet);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fourth(CloseableHttpClient httpclient) {
        try {
            HttpPost httpPost = new HttpPost("http://passport.51.com/reg/ajaxapi?chn=passport");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("pp_reg_type", "username"));
            nvps.add(new BasicNameValuePair("pp_reg_user", username));
            nvps.add(new BasicNameValuePair("pp_reg_pass", "123456789"));
            nvps.add(new BasicNameValuePair("pp_reg_repass", "123456789"));
            nvps.add(new BasicNameValuePair("pp_reg_sex", "1"));
            nvps.add(new BasicNameValuePair("pp_reg_authcode_key", src));
            nvps.add(new BasicNameValuePair("pp_reg_authcode", code));
            nvps.add(new BasicNameValuePair("pp_reg_from", "www_index_v_c"));
            nvps.add(new BasicNameValuePair("pp_reg_url", "http://passport.51.com/reg2.5p?from=www_index_v_c&c=reg1"));
            nvps.add(new BasicNameValuePair("pp_reg_referer", ""));
            nvps.add(new BasicNameValuePair("pp_reg_redirect", ""));
            nvps.add(new BasicNameValuePair("pp_reg_invider", ""));
            nvps.add(new BasicNameValuePair("pp_reg_source", ""));
            nvps.add(new BasicNameValuePair("pp_reg_token", ""));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response2 = null;
            try {
                response2 = httpclient.execute(httpPost);
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                InputStream is = entity2.getContent();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int i = -1;
                while ((i = is.read()) != -1) {
                    baos.write(i);
                }
                gobject = baos.toString();
                System.out.println(baos.toString());
                EntityUtils.consume(entity2);
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    response2.close();
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void fifth(CloseableHttpClient httpclient) {
        JSONObject jo = new JSONObject(gobject);
        HttpGet httpGet = new HttpGet(jo.get("url").toString());
        CloseableHttpResponse response1 = null;

        try {
            response1 = httpclient.execute(httpGet);
            HttpEntity entity1 = response1.getEntity();
            InputStream is = entity1.getContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i = -1;
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
            String result = baos.toString();
            System.out.println(result);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                response1.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void sixth(CloseableHttpClient httpclient) {
        CloseableHttpResponse response2 = null;
        InputStream is2 = null;
        HttpGet httpGet2 = new HttpGet("http://my.51.com/guide/step3?from=step2&from2=passport");
        try {
            response2 = httpclient.execute(httpGet2);
            HttpEntity entity2 = response2.getEntity();
            is2 = entity2.getContent();
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            int i = -1;
            while ((i = is2.read()) != -1) {
                baos2.write(i);
            }
            String result2 = baos2.toString();
            //System.out.println(result2);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response2.close();
                is2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void seventh() {
        WwdEmbedded em = new WwdEmbedded();
        em.getcnn();
        em.save("tom", username, "123456789");
        em.list();
        em.close();
    }

}
