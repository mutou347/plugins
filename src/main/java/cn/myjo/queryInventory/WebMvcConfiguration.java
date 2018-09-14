package cn.myjo.queryInventory;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("*")
        .allowCredentials(true)
        .allowedMethods(HttpMethod.GET.toString(),
		        		HttpMethod.POST.toString(),
		        		HttpMethod.PUT.toString(),
		        		HttpMethod.DELETE.toString());
    }
  
}