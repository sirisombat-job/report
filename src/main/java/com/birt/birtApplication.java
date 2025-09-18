package com.birt;

import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.*;
import java.util.logging.Level; 

public class birtApplication {

	public static void main(String[] args) {
        IReportEngine engine = null;
        try {

        	// 1. Configure and start the BIRT Platform
            EngineConfig config = new EngineConfig();
            // Optional: Set a log directory for BIRT engine logs
            config.setLogConfig( "d:\\logs", Level.ALL);
            Platform.startup(config);
        	
            // 2. Create a Report Engine
            IReportEngineFactory factory = (IReportEngineFactory) Platform
                    .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
            engine = factory.createReportEngine(config);

/*        	
        	BirtConfig config = new BirtConfig();        	
        	engine = config.birtReportEngine();
*/
            // 3. Open the report design
            String reportDesignPath = "D:\\Work\\SSO\\workspace\\JavaReport\\R5101.rptdesign"; // Replace with your report design path
            IReportRunnable design = engine.openReportDesign(reportDesignPath);

            // 4. Create a task to run and render the report
            IRunAndRenderTask task = engine.createRunAndRenderTask(design);

            // 5. Set rendering options (e.g., PDF)            
            PDFRenderOption options = new PDFRenderOption();
            options.setOutputFormat("pdf");
            OutputStream os = new FileOutputStream("D:\\R5101.pdf");
            options.setOutputStream(os);
            task.setRenderOption(options);

            // Optional: Set report parameters if your report has them
            Map<String, Object> params = new HashMap<>();
            
            params.put("SSO_NO", "1001");
            params.put("SECTION_TYPE", "33");
            params.put("RECEIPT_DATE", "2023-03-09");
            params.put("ReportCode", "R5101");
            params.put("UserName", "QUKO");
            task.setParameterValues(params);

            // 6. Run the report
            task.setRenderOption(options);
            task.run();


            // 7. Close the task
            task.close();
            os.close();

            System.out.println("Report generated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8. Shut down the BIRT engine and platform
            if (engine != null) {
                engine.destroy();
            }
            Platform.shutdown();
        }
    }
}
