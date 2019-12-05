package cost;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@EnableAutoConfiguration
@Controller
@MapperScan("cost.dao")
public class App   extends SpringBootServletInitializer {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class,args);
		logger.info("----------------------张旭亮大帅逼----------------------");
	}

	@RequestMapping("/")
	public String home(HttpServletResponse response) throws Exception {
		//response.sendRedirect(Constant.INDEX_URL_WEB);//跳转首页为jsp方式
		return "index";//跳转首页为html方式
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		logger.info("----------------------张旭亮大帅逼----------------------");
		return application.sources(App.class);
	}
}
