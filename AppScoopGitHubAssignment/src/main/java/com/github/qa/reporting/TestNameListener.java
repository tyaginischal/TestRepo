package com.github.qa.reporting;

import java.lang.reflect.Field;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.internal.BaseTestMethod;
import org.testng.internal.TestResult;

/**
 * The listener interface for receiving testName events. The class that is
 * interested in processing a testName event implements this interface, and the
 * object created with that class is registered with a component using the
 * component's <code>addTestNameListener<code> method. When the testName event
 * occurs, that object's appropriate method is invoked.
 *
 * @author Chandan Kumar
 */
public class TestNameListener extends TestListenerAdapter {

	/**
	 * Sets the test name in xml.
	 *
	 * @param tr the new test name in xml
	 */
	private void setTestNameInXml(ITestResult tr) {
		try {
			Field mMethod = TestResult.class.getDeclaredField("m_method");
			mMethod.setAccessible(true);
			mMethod.set(tr, tr.getMethod().clone());
			Field mMethodName = BaseTestMethod.class.getDeclaredField("m_methodName");
			mMethodName.setAccessible(true);
			mMethodName.set(tr.getMethod(), tr.getTestName());
		} catch (IllegalAccessException ex) {
			Reporter.log(ex.getLocalizedMessage(), true);
		} catch (NoSuchFieldException ex) {
			ex.getMessage();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.testng.TestListenerAdapter#onTestSuccess(org.testng.ITestResult)
	 */
	@Override
	public void onTestSuccess(ITestResult tr) {
		setTestNameInXml(tr);
		super.onTestSuccess(tr);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.testng.TestListenerAdapter#onTestFailure(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure(ITestResult tr) {
		setTestNameInXml(tr);
		super.onTestFailure(tr);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.testng.TestListenerAdapter#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onTestSkipped(ITestResult tr) {
		setTestNameInXml(tr);
		super.onTestSkipped(tr);
	}

}