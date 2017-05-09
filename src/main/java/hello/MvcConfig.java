package hello;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


public class MvcConfig extends WebMvcConfigurerAdapter {

	 @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/index").setViewName("index");
	        registry.addViewController("/").setViewName("index");
	        registry.addViewController("/hello").setViewName("hello");
	        registry.addViewController("/loginadmin").setViewName("loginadmin");
	    }
	
}
