package cost.interceptor;

import cost.constant.Constant;
import cost.utils.*;
import cost.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/12/18.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil redisUtil;

    private static Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            LoginRequired loginRequired = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
            // 如果方法上的注解为空 则获取类的注解
            if (loginRequired == null) {
                loginRequired = handlerMethod.getMethod().getDeclaringClass().getAnnotation(LoginRequired.class);
            }
            // 如果标记了注解，则判断权限
            if(loginRequired != null){
                return checkWebTicket(request, response);
            }
        }
        return true;
    }


    /**
     * 检验Web的票据
     *
     * 一个接口方法如果被标记为LoginRequired注解，并且scope是Web，则对该接口的使用必须要能满足以下条件:
     *      1. Cookie 中存在 USER_ID 代表的值，并且该用户是有效用户
     *      2. Cookie 中存在 USER_TICKET_OF_T 代表的值，并且该值是Redis中 USER_ID 对应的未过期的值
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private boolean checkWebTicket(
            final HttpServletRequest request,
            final HttpServletResponse response
    ) throws Exception {
        String userIdString = CookieUtils.getCookie(request, Constant.USER_ID);
        String ticketOfT = CookieUtils.getCookie(request, Constant.USER_TICKET_OF_T);
        //String matrixID = (String)request.getParameter("matrixID");
        logger.info("userIdString : {} ", userIdString);
        logger.info("ticketOfT : {} ", ticketOfT);
        //logger.info("matrixID : {} ", matrixID);
        //Cookie中没有票据 直接返回失败
        if(StringUtils.isEmpty(userIdString) || StringUtils.isEmpty(ticketOfT)){
            logger.error("拦截器验证失败，没有找到票据或T票");
            // clear userId
            CookieUtils.saveCookie(response, Constant.USER_ID, "");
            CookieUtils.clearCookie(response, Constant.USER_ID);

            // clear T ticket
            CookieUtils.saveCookie(response, Constant.USER_TICKET_OF_T, "");
            CookieUtils.clearCookie(response, Constant.USER_TICKET_OF_T);
            //response.sendRedirect(Constant.LOGIN_URL_CC);
            response.sendError(401, "fail");
            return false;
        }else{
            //Cookie中的userId解密
            String userId  = RSAUtils.decrypt(userIdString, DESCoder.privateKey);
            //根据userId获取redis中保存的T票
            String ticketOfTRedis = redisUtil.get(userId);
            logger.info("redis获取的T票为：{}", ticketOfTRedis);
            //redis中的T票为空或者与Cookie中的T票不一致 则返回失败
            if(StringUtils.isEmpty(ticketOfTRedis) || !ticketOfT.equals(ticketOfTRedis)){
                // clear userId
                CookieUtils.saveCookie(response, Constant.USER_ID, "");
                CookieUtils.clearCookie(response, Constant.USER_ID);
                // clear T ticket
                CookieUtils.saveCookie(response, Constant.USER_TICKET_OF_T, "");
                CookieUtils.clearCookie(response, Constant.USER_TICKET_OF_T);
                //response.sendRedirect(Constant.LOGIN_URL_CC);
                response.sendError(401, "fail");
                return false;
            }
        }

        /*if(StringUtils.isNotEmpty(matrixID)){

            String userId  = RSAUtils.decrypt(userIdString, DESCoder.privateKey);
            logger.info("拦截器解密之后的userId: " + userId);

            if(userId.equals(matrixID)){
                logger.info("拦截器验证成功");
                return true;
            }else{
                logger.error("拦截器验证失败");
                // clear userId
                CookieUtils.saveCookie(response, Constant.USER_ID, "");
                CookieUtils.clearCookie(response, Constant.USER_ID);

                // clear T ticket
                CookieUtils.saveCookie(response, Constant.USER_TICKET_OF_T, "");
                CookieUtils.clearCookie(response, Constant.USER_TICKET_OF_T);
                response.sendRedirect(Constant.LOGIN_URL_WEB);
                return false;
            }
        }*/
        return true;
    }
}