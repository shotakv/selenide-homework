package ge.tbcitacademy.listeners;

import ge.tbcitacademy.data.Constants;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.util.List;

public class CustomReportListener implements IReporter {
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        System.out.println(Constants.GENERATING_REPORT);
        for (ISuite suite : suites) {
            suite.getResults().forEach((key, result) -> {
                result.getTestContext().getFailedTests().getAllResults().forEach(failedTest -> {
                    System.out.println(Constants.SUITE + suite.getName() + Constants.COMMA + Constants.TEST + failedTest.getTestContext().getName() + Constants.COMMA + Constants.METHOD + failedTest.getMethod().getMethodName() + " " +Constants.FAILURE_TEXT);
                });
            });
        }
    }
    //this comment means nothing
}
