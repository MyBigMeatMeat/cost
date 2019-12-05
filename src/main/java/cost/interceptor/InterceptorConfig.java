package cost.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Administrator on 2018/12/18.
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor getLoginInterceptor() {
        return new LoginInterceptor();
    }

    //配置拦截器
    public void addInterceptors(InterceptorRegistry registry){
        //registry.addInterceptor此方法添加拦截器
        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**");
    }
}
