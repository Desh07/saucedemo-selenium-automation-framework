package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {

        if (extent == null) {

            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");

            spark.config().setReportName("Saucedemo Automation Report");
            spark.config().setDocumentTitle("Test Execution Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Project", "Saucedemo UI Automation");
            extent.setSystemInfo("Tester", "Deshanth");
            extent.setSystemInfo("Tool", "Selenium + TestNG");
        }

        return extent;
    }
}