import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class extentrep {
   static ExtentReports extent;
    public static ExtentReports config() {
        String path = System.getProperty("user.dir") + "/reports/index.html";
        ExtentSparkReporter rep = new ExtentSparkReporter(path);
        rep.config().setReportName("Web Automation Results");

        rep.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(rep);
        extent.setSystemInfo("Tester", "Kavin");

        return extent;

    }
}
