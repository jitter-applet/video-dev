/**
 * 
 */
package cn.zzuisa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.zzuisa.controller.interceptor.MiniInterceptor;

/**
 * @ClassName: WebMvcConfig
 * @Description: TODO
 * @author Ao
 * @date Nov 21, 2018
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	final String MACPATH = "file:/Users/zzu/Desktop/videos/";
	final String WINDOWSPATH = "file:D:/wx-videosResource/";
	final String WINDOWSFFMPEG_EXE = "E:\\ffmpeg\\bin\\ffmpeg.exe";
	public static final String UBUNTUPATH = "file:/usr/wx-videos/";
	public static final String UBUNTUPATHOFVIDEO = "file:/usr/wx-videos/";
	public static final String UBUNTUFFMPEG = "ffmpeg";

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 配置虚拟路径
		registry.addResourceHandler("/**").addResourceLocations(UBUNTUPATH)
				.addResourceLocations("classpath:/META-INF/resources/");
	}
//
//	@Bean
//	public MiniInterceptor miniInterceptor() {
//		return new MiniInterceptor();
//	}
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**").addPathPatterns("/video/upload","/video/uploadCover")
//		.addPathPatterns("/bgm/**");
//		super.addInterceptors(registry);
//	}

}
