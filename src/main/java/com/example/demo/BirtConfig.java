package com.example.demo;

import java.util.logging.Level;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class BirtConfig {

	@Bean
    public IReportEngine reportEngine() {
    	
        EngineConfig config = new EngineConfig();
        // Set engine home if necessary, typically points to BIRT runtime directory
        // config.setEngineHome("path/to/birt/runtime"); 
        //config.setLogConfig( "d:\\logs", Level.ALL);

        try {
            Platform.startup(config);
        } catch (BirtException e) {
            throw new IllegalStateException("Failed to start BIRT platform", e);
        }
        IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        IReportEngine engine = factory.createReportEngine(config);
        // Optionally, set logging level
        // engine.changeLogLevel(Level.ALL); 
        return engine;
    }
}



/*
@Configuration
public class BirtConfig implements FactoryBean<IReportEngine>, ApplicationContextAware, DisposableBean {

    private IReportEngine birtEngine;
    private ApplicationContext appContext;

    @Override
    public IReportEngine getObject() throws Exception {
        EngineConfig config = new EngineConfig();
        // Configure logging, resource paths, etc.
        config.setLogConfig("logs", null); // Example: set log directory
        config.setResourcePath( appContext.getResource("classpath:/reports").getFile().getAbsolutePath());

        Platform.startup(config);
        birtEngine = new ReportEngine(config);
        return birtEngine;
    }

    @Override
    public Class<?> getObjectType() {
        return IReportEngine.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.appContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        if (birtEngine != null) {
            birtEngine.destroy();
            Platform.shutdown();
        }
    }

}
*/
