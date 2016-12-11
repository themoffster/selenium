package com.themoffster.selenium.aop.aspect;

import com.themoffster.selenium.model.DriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/**
 * The class which intercepts messages for Spring AOP.
 */
@Aspect
public class ErrorInterceptor {

    /**
     * The logger
     */
    private static final Logger LOGGER = Logger.getLogger(ErrorInterceptor.class);
    @Autowired
    private DriverManager driverManager;

    /**
     * Allows for a method call which throws an exception to be intercepted after the exception is
     * thrown. For this to happen, the method which the error propagated from needs to be a public method and reside in
     * the {@link com.themoffster.selenium.pages} package. There are no other limitations on the join point.<br>
     * This method will be triggered only when the above conditions are met.
     *
     * @param joinPoint the intercepted method call
     * @param ex        the exception thrown by the join point method
     */
    @AfterThrowing(pointcut = "execution(public * com.themoffster.selenium.pages.*.*(..))", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        LOGGER.info("Exception thrown in " + joinPoint.getSignature().getName() + "()");
        LOGGER.error(ex);
        takeScreenshot(driverManager.getWebDriver());
    }

    private void takeScreenshot(WebDriver driver) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("error_screenshot.jpg"));
        } catch (IOException ex) {
            LOGGER.error("Unable to take screenshot when error occurred. " + ex);
        }
    }
}
