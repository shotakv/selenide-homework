package ge.tbcitacademy.listeners;

import ge.tbcitacademy.data.Constants;
import ge.tbcitacademy.util.Helper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.screenshot;


public class CustomTestListener implements ITestListener {
    private LocalDateTime testStartTime;
    private LocalDateTime methodStartTime;

    @Override
    public void onStart(ITestContext context) {
        testStartTime = LocalDateTime.now();
        System.out.println(Constants.TEST + context.getName() + Constants.STARTED_AT + testStartTime);
    }

    @Override
    public void onFinish(ITestContext context) {
        LocalDateTime testEndTime = LocalDateTime.now();
        Duration duration = Duration.between(testStartTime, testEndTime);
        System.out.println(Constants.TEST + context.getName() + Constants.ENDED_AT + testEndTime);
        System.out.println(Constants.TEST_TOTAL_TIME + context.getName() + Constants.SEMICOLON + duration.toMinutesPart() + Constants.MINUTES + duration.toSecondsPart() + Constants.SECONDS);
    }

    @Override
    public void onTestStart(ITestResult result) {
        methodStartTime = LocalDateTime.now();
        System.out.println(Constants.METHOD + result.getMethod().getMethodName() + Constants.STARTED_AT + methodStartTime);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Helper.logMethodEnd(result, ""); //empty status so it doesn't log the status
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Helper.logMethodEnd(result, Constants.FAILURE_TEXT); //providing so it only logs when the test fails
        System.out.println(Constants.FAILURE_REASON + result.getThrowable());

        String testName = result.getMethod().getMethodName();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String screenshotFilePath = Constants.SCREENSHOT_PATH + testName + "/" + timestamp;
        screenshot(screenshotFilePath);

        System.out.println(Constants.SAVED_SCREENSHOT + testName + " at " + screenshotFilePath + Constants.PNG);
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        Helper.logMethodEnd(result, ""); //empty status so it doesn't log the status
    }


}
