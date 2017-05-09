package hello;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import hello.mailing.adminMail;
import hello.service.Locker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Configuration
@ComponentScan(basePackageClasses={
		hello.repo.EventRepository.class, hello.model.Event.class, EventController.class,
		hello.model.Partner.class, hello.repo.PartnerRepository.class, hello.PartnerController.class,
		hello.model.Offer.class, hello.repo.OfferRepository.class, hello.service.EventService.class, 
		hello.service.Locker.class
})
@EnableAutoConfiguration
@Order(HIGHEST_PRECEDENCE)

@EnableJpaRepositories("hello") //should point to the package where the repositories are :)
public class Application {
	
	// moustii
	
	 /* Used for running in "embedded" mode */
    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
               
            }
        };
    }
    
	/* https://github.com/spring-projects/spring-boot/issues/2825#issuecomment-93479758 */
    @Bean
    public EmbeddedServletContainerCustomizer servletContainerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof TomcatEmbeddedServletContainerFactory) {
                    customizeTomcat((TomcatEmbeddedServletContainerFactory)container); 
                }
            }

            private void customizeTomcat(TomcatEmbeddedServletContainerFactory tomcatFactory) {
                tomcatFactory.addContextCustomizers(new TomcatContextCustomizer() {

                    @Override
                    public void customize(Context context) {
                        Container jsp = context.findChild("jsp");
                        if (jsp instanceof Wrapper) {
                            ((Wrapper)jsp).addInitParameter("development", "false");
                        }

                    }

                });
            }

        };
    }
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	public static String img_user = "src/main/resources/static/img/user";
	public static String img_event = "src/main/resources/static/img/event";
	public static String img_partner = "src/main/resources/static/img/partner";
	
	@Bean
    CommandLineRunner init() {
        return (String[] args) -> {
            new File(Application.img_user).mkdirs();
            new File(Application.img_event).mkdirs();
            new File(Application.img_partner).mkdirs();
        };
    }

    public static void main(String[] args) {
    
    	
    	
    	SpringApplication.run(Application.class, args);

    	
    	System.err.println("'t werkt.");
    	
    
        
    }
    
    
    
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(Application.class);
    }
    
}