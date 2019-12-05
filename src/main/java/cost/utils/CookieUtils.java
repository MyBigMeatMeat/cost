package cost.utils;

import cost.constant.Constant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hongnan.zhao on 2015/12/14.
 */
public class CookieUtils {
    private static Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    public static String getCookie(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0)
            return null;
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(key))
                return cookies[i].getValue();
        }
        return null;
    }

    public static void saveCookie(HttpServletResponse response, String key, String value) {
        CookieUtils.saveCookie(response, key, value, -1);
    }

    public static void saveCookie(HttpServletResponse response, String key, String value, int second) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(second);
//        cookie.setDomain(Constant.CookieConstant.domain);
        response.addCookie(cookie);
    }

    public static void saveCookie(HttpServletResponse response, String key, String value, int maxAge,
                                  String path, String domain) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }

    public static void clearCookie(HttpServletResponse response, String key) {
        Cookie cookie = new Cookie(key, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setDomain(Constant.domain);
        response.addCookie(cookie);
    }

    public static void clearCookie(HttpServletResponse response, String key, String domain) {
        Cookie cookie = new Cookie(key, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }

    public static String getUserId(HttpServletRequest request, HttpServletResponse response){
        try {
            String userIdString = getCookie(request, Constant.USER_ID);
            //logger.info("cookie中的userIdString：{}", userIdString);
            if(StringUtils.isEmpty(userIdString)){
                response.sendRedirect(Constant.INDEX_URL_WEB);
                return null;
            }
            return RSAUtils.decrypt(userIdString, DESCoder.privateKey);
        } catch (Exception e) {
            logger.error("出现异常 异常原因：{}", e.getMessage());
        }
        return null;
    }
}