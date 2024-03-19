package io.github.jubadeveloper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.io.IOException;
import java.util.Locale;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { Application.class, WebMvcConfig.class})
public class ContextTest {
    @Autowired
    public ApplicationContext applicationContext;
    @Autowired
    public ThymeleafViewResolver viewResolver;
    @Autowired
    public ResourceLoader resourceLoader;

    @Test
    public void shouldResolveHelloView () throws Exception {
        View view = viewResolver.resolveViewName("hello", Locale.US);
        Assertions.assertThat(view).isNotNull();
    }

}
