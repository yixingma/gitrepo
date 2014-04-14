package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@ComponentScan
@EnableAutoConfiguration
@Configuration
@ImportResource("classpath:config/application-config.xml")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// @Bean
	// public AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter()
	// {
	// final AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter = new
	// AnnotationMethodHandlerAdapter();
	// final MappingJacksonHttpMessageConverter
	// mappingJacksonHttpMessageConverter = new
	// MappingJacksonHttpMessageConverter();
	//
	// HttpMessageConverter<?>[] httpMessageConverter = {
	// mappingJacksonHttpMessageConverter };
	//
	// String[] supportedHttpMethods = { "POST", "GET", "HEAD" };
	//
	// annotationMethodHandlerAdapter.setMessageConverters(httpMessageConverter);
	// annotationMethodHandlerAdapter.setSupportedMethods(supportedHttpMethods);
	//
	// return annotationMethodHandlerAdapter;
	// }
}