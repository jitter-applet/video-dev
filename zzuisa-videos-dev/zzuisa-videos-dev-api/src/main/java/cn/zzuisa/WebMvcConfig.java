/**
 * 
 */
package cn.zzuisa;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName: WebMvcConfig
 * @Description: TODO
 * @author Ao
 * @date Nov 21, 2018
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	final String  MACPATH = "file:/Users/zzu/Desktop/videos/";
	final String WINDOWSPATH = "file:D:/wx-videosResource/";
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 配置虚拟路径
		registry.addResourceHandler("/**").addResourceLocations(WINDOWSPATH)
				.addResourceLocations("classpath:/META-INF/resources/");
	}

}
