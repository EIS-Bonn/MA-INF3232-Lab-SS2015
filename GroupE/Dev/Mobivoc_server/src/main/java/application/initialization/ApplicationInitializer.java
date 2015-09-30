package application.initialization;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import application.configuration.MVCConfiguration;
import application.controllers.MobivocController;


public class ApplicationInitializer implements WebApplicationInitializer {

    
public void onStartup(ServletContext container) throws ServletException {
		
		//first add Context Loader Listener as a Root
		addContextLoaderListener(container);
		
		//then add MVC dispatcher
		addMVCDispatcher(container);
	}


	/**
	 * Add RootContext to container
	 * @param container
	 */
	private void addContextLoaderListener(final ServletContext container){
		
		//Root Context for the application
	    AnnotationConfigWebApplicationContext rootContext = 
	    						new AnnotationConfigWebApplicationContext();
	
	    //Add as a ContextLoaderListener to container
	    container.addListener(new ContextLoaderListener(rootContext));	
	}
	
	/**
	 * Add MVC Dispatcher to root context
	 * @param container
	 */
	private void addMVCDispatcher(final ServletContext container){
		 // Create the dispatcher servlet's Spring application context
	    AnnotationConfigWebApplicationContext dispatcherServlet = 
	    						new AnnotationConfigWebApplicationContext();
	    
	    
	    dispatcherServlet.register(MVCConfiguration.class);
	         
	    // Register and map the dispatcher servlet
	    ServletRegistration.Dynamic dispatcher = 
	    		container.addServlet("MVCDispatcher", 
	    				new DispatcherServlet(dispatcherServlet));
	    
	    dispatcher.setLoadOnStartup(1);
	    dispatcher.addMapping("/");
	}
		

 }