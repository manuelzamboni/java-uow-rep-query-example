package mz.examples.uowrepquery.api;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class AppInitializer implements WebApplicationInitializer {
  @Override
  public void onStartup(ServletContext container) throws ServletException {
    var context = new AnnotationConfigWebApplicationContext();
    context.scan("mz.examples.uowrepquery.api");
    container.addListener(new ContextLoaderListener(context));

    var dispatcher = container.addServlet("mvc", new DispatcherServlet(context));
    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/");
  }
}
