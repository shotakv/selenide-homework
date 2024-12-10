package ge.tbcitacademy.listeners;

import ge.tbcitacademy.data.Constants;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.time.Duration;
import java.time.LocalDateTime;

public class CustomSuiteListener implements ISuiteListener {
    private LocalDateTime startTime;

    @Override
    public void onStart(ISuite suite) {
        startTime = LocalDateTime.now();
        System.out.println(Constants.SUITE + suite.getName() + Constants.STARTED_AT + startTime);
    }

    @Override
    public void onFinish(ISuite suite) {
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);
        System.out.println(Constants.SUITE + suite.getName() + Constants.ENDED_AT + endTime);
        System.out.println(Constants.SUITE_TOTAL_TIME + suite.getName() + Constants.SEMICOLON + duration.toMinutesPart() + Constants.MINUTES + duration.toSecondsPart() + Constants.SECONDS);
    }

}
