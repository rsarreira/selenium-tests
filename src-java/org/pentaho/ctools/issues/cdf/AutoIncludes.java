/*!*****************************************************************************
 *
 * Selenium Tests For CTools
 *
 * Copyright (C) 2002-2015 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/
package org.pentaho.ctools.issues.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * The script is testing the issue:
 * - http://jira.pentaho.com/browse/CDF-548
 *
 * and the automation test is described:
 * - http://jira.pentaho.com/browse/QUALITY-1149
 *
 * NOTE
 * To test this script it is required to have CDF plugin installed.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class AutoIncludes {
  // Instance of the driver (browser emulator)
  private final WebDriver driver = CToolsTestSuite.getDriver();
  // The base url to be append the relative url in test
  private final String baseUrl = CToolsTestSuite.getBaseUrl();
  //Access to wrapper for webdriver
  private final ElementHelper elemHelper = new ElementHelper();
  // Log instance
  private final Logger log = LogManager.getLogger( AutoIncludes.class );
  // Getting screenshot when test fails
  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( this.driver );

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Assert Auto-Includes is working as expected
   *
   * Description:
   *    Open sample and assert query data was automatically included to the dashboard
   *
   * Steps:
   *    1. Open created sample and click button
   *    2. Assert alert shows expected text
   *
   */
  @Test( timeout = 120000 )
  public void tc1_CdfAutoIncludes_ReturnsValues() {
    this.log.info( "tc1_CdfAutoIncludes_ReturnsValues" );

    /*
     * ## Step 1
     */
    //Open Created sample and click button
    this.driver.get( this.baseUrl + "api/repos/%3Apublic%3AIssues%3ACDF%3ACDF-595%3ACDF-595.wcdf/generatedContent" );

    //Click Query button
    WebElement queryButton = this.elemHelper.FindElement( this.driver, By.xpath( "//div[@id='table']/button" ) );
    assertNotNull( queryButton );
    queryButton.click();

    /*
     * ## Step 2
     */
    String alertMessage = this.elemHelper.WaitForAlertReturnConfirmationMsg( this.driver );
    assertEquals( "Shipped,2004,4114929.960000002,4.114929960000002,Shipped,2005,1513074.4600000002,1.5130744600000001", alertMessage );

  }
}
