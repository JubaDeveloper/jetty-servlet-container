package io.github.jubadeveloper;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafView;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.IOException;

@Configuration
public class Application {
    public static AnnotationConfigWebApplicationContext context;
    public static void main(String[] args) throws Exception {
        // Create an instance of Jetty server
        Server server = new Server(8080);

        // Create the ServletContextHandler
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/");

        // Initialize the Spring context and add DispatcherServlet
        context = new AnnotationConfigWebApplicationContext();
        context.register(WebMvcConfig.class);
        context.scan("io.github.jubadeveloper");

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        dispatcherServlet.setDetectAllViewResolvers(true);
        ServletHolder servletHolder = new ServletHolder(dispatcherServlet);
        servletContextHandler.addServlet(servletHolder, "/*");

        // Set the servlet context handler to the server and start the server
        server.setHandler(servletContextHandler);
        server.start();
        server.join();
    }
}

@Configuration
@EnableWebMvc
class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public ITemplateResolver templateResolver(
            ResourceLoader resourceLoader
    ) throws IOException {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setApplicationContext(Application.context);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(ResourceLoader resourceLoader) throws IOException {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolver(templateResolver(resourceLoader));
        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver(ResourceLoader resourceLoader) throws IOException {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine(resourceLoader));
        viewResolver.setOrder(4);
        return viewResolver;
    }
}
