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
package org.pentaho.ctools.cdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.pentaho.ctools.suite.CToolsTestSuite;
import org.pentaho.ctools.utils.DirectoryWatcher;
import org.pentaho.ctools.utils.ElementHelper;
import org.pentaho.ctools.utils.PageUrl;
import org.pentaho.ctools.utils.ScreenshotTestRule;

/**
 * Testing the functionalities related with Prpt Component.
 *
 * Naming convention for test:
 *  'tcN_StateUnderTest_ExpectedBehavior'
 *
 */
@FixMethodOrder( MethodSorters.NAME_ASCENDING )
public class PrptComponent {

  //Instance of the driver (browser emulator)
  private static WebDriver DRIVER;
  // Instance to be used on wait commands
  private static Wait<WebDriver> WAIT;
  //Log instance
  private static Logger LOG = LogManager.getLogger( PrptComponent.class );

  @Rule
  public ScreenshotTestRule screenshotTestRule = new ScreenshotTestRule( DRIVER );

  /**
   * Shall initialized the test before run each test case.
   */
  @BeforeClass
  public static void setUp() {
    LOG.info( "setUp##" + PrptComponent.class.getSimpleName() );
    DRIVER = CToolsTestSuite.getDriver();
    WAIT = CToolsTestSuite.getWait();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Display Content
   * Description:
   *    Check if the contents present in page is the expected.
   * Steps:
   *    1. Check title web page and sample title.
   */
  @Test( timeout = 60000 )
  public void tc0_OpenSamplePage() {
    // The URL for the PrptComponent under CDF samples
    // This sample is in: 
    // ::Public/plugin-samples/CDF/Documentation/Component Reference/Core Components/PrptComponent
    DRIVER.get( PageUrl.PRPT_COMPONENT );

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
    ElementHelper.WaitForElementPresence( DRIVER, By.id( "glasspane" ), 10 );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );

    //Check if the some elements are already displayed
    //Check if element with "Line" is visible
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );
    ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.cssSelector( "div.parameter-label" ) );
    //Check if element with "LINE: Classic Cars" is visible
    DRIVER.switchTo().frame( "reportContent" );
    ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//tr/td" ) );

    //Back to root
    DRIVER.switchTo().defaultContent();
  }

  /**
   * ############################### Test Case 1 ###############################
   *
   * Test Case Name:
   *    Display Content
   * Description:
   *    Check if the contents present in page is the expected.
   * Steps:
   *    1. Check title web page and sample title.
   */
  @Test( timeout = 60000 )
  public void tc1_PageContent_DisplayTitle() {
    // Wait for title become visible and with value 'Community Dashboard Framework'
    WAIT.until( ExpectedConditions.titleContains( "Community Dashboard Framework" ) );
    // Wait for visibility of 'VisualizationAPIComponent'
    WAIT.until( ExpectedConditions.visibilityOfElementLocated( By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );

    // Validate the sample that we are testing is the one
    assertEquals( "Community Dashboard Framework", DRIVER.getTitle() );
    assertEquals( "PrptComponent", ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='dashboardContent']/div/div/div/h2/span[2]" ) ) );
  }

  /**
   * ############################### Test Case 2 ###############################
   *
   * Test Case Name:
   *    Reload Sample
   * Description:
   *    Reload the sample (not refresh page).
   * Steps:
   *    1. Click in Code and then click in button 'Try me'.
   */
  @Test( timeout = 60000 )
  public void tc2_ReloadSample_SampleReadyToUse() {
    LOG.info( "tc2_ReloadSample_SampleReadyToUse" );

    // ## Step 1
    // Render again the sample
    ElementHelper.ClickJS( DRIVER, By.xpath( "//div[@id='example']/ul/li[2]/a" ) );
    ElementHelper.ClickJS( DRIVER, By.xpath( "//div[@id='code']/button" ) );

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.cssSelector( "div.blockUI.blockOverlay" ) );
    ElementHelper.WaitForElementPresence( DRIVER, By.id( "glasspane" ), 10 );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );

    //Check if the some elements are already displayed
    //Check if element with "Line" is visible
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );
    ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.cssSelector( "div.parameter-label" ) );
    //Check if element with "LINE: Classic Cars" is visible
    DRIVER.switchTo().frame( "reportContent" );
    ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//tr/td" ) );

    //Back to root
    DRIVER.switchTo().defaultContent();

    // Now sample element must be displayed
    assertTrue( ElementHelper.FindElement( DRIVER, By.id( "sample" ) ).isDisplayed() );

    //Check the number of divs with id 'SampleObject'
    //Hence, we guarantee when click Try Me the previous div is replaced
    int nSampleObject = DRIVER.findElements( By.id( "sampleObject" ) ).size();
    assertEquals( 1, nSampleObject );
  }

  /**
   * ############################### Test Case 3 ###############################
   *
   * Test Case Name:
   *    Select HTML and PDF (content type)
   * Description:
   *    The test case pretends validate the result when change for option HTML
   *    and PDF for content-type.
   * Steps:
   *    1. Select PDF
   *    2. Select HTML
   */
  @Test( timeout = 60000 )
  public void tc3_SelectHTMLAndPDF_PageDisplayedAccording() {
    LOG.info( "tc3_SelectHTMLAndPDF_PageDisplayedAccording" );

    // ## Step 1
    Select select = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    select.selectByValue( "pageable/pdf" );
    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    WAIT.until( ExpectedConditions.presenceOfElementLocated( By.id( "sampleObject" ) ) );
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );
    //Check presence of tool bar elements
    assertNotNull( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='toolbar']/div" ) ) );
    assertNotNull( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='toolbar']/div[2]" ) ) );
    assertNotNull( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='toolbar']/span" ) ) );
    //Check the Product Name and Output Type
    String prodName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//td/div/div" ) );
    assertEquals( "Line", prodName );
    assertNotNull( ElementHelper.FindElement( DRIVER, By.xpath( "//td/div/div[2]/select" ) ) );
    String outputTypeName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select/../../div" ) );
    assertEquals( "Output Type", outputTypeName );
    assertNotNull( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    //Check for View Report button
    String buttonName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//button/span" ) );
    assertEquals( "View Report", buttonName );
    //Check the generated image
    DRIVER.switchTo().frame( "reportContent" );
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']" ) );
    assertNotNull( element );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div" ), "L I N E :" );
    String text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div" ) );
    assertEquals( "L I N E :", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div[2]" ), "C l a s s i c" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div[2]" ) );
    assertEquals( "C l a s s i c", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div[3]" ), "C a r s" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div[3]" ) );
    assertEquals( "C a r s", text );

    // ## Step 2
    DRIVER.switchTo().defaultContent();
    select = new Select( ElementHelper.FindElement( DRIVER, By.cssSelector( "select" ) ) );
    select.selectByValue( "table/html;page-mode=stream" );

    // NOTE - we have to wait for loading disappear
    ElementHelper.WaitForElementInvisibility( DRIVER, By.xpath( "//div[@class='blockUI blockOverlay']" ) );

    WAIT.until( ExpectedConditions.presenceOfElementLocated( By.id( "sampleObject" ) ) );
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );
    //Check presence of tool bar elements
    assertNotNull( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='toolbar']/div" ) ) );
    assertNotNull( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='toolbar']/div[2]" ) ) );
    assertNotNull( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@id='toolbar']/span" ) ) );
    //Check the Product Name and Output Type
    prodName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//td/div/div" ) );
    assertEquals( "Line", prodName );
    assertNotNull( ElementHelper.FindElement( DRIVER, By.xpath( "//td/div/div[2]/select" ) ) );
    outputTypeName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select/../../div" ) );
    assertEquals( "Output Type", outputTypeName );
    assertNotNull( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    //Check for View Report button
    buttonName = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//button/span" ) );
    assertEquals( "View Report", buttonName );
    //Check the generated image
    DRIVER.switchTo().frame( "reportContent" );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//tbody/tr" ) );
    assertNotNull( element );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr" ), "LINE: Classic Cars" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr" ) );
    assertEquals( "LINE: Classic Cars", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr[3]/td" ), "Autoart Studio Design" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr[3]/td" ) );
    assertEquals( "Autoart Studio Design", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ), "1958 Chevy Corvette Limited Edition" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ) );
    assertEquals( "1958 Chevy Corvette Limited Edition", text );
    text = ElementHelper.GetAttribute( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ), "href" );
    assertEquals( "http://images.google.com/images?q=1958%20Chevy%20Corvette%20Limited%20Edition", text );
  }

  /**
   * ############################### Test Case 4 ###############################
   *
   * Test Case Name:
   *    Toggle Prompt Panel
   * Description:
   *    The test case pretends disable and enable the prompt panel.
   * Steps:
   *    1. Enable prompt panel
   *    2. Disable prompt panel
   */
  @Test( timeout = 60000 )
  public void tc4_TogglePromptPanel_PromptPanelEnableDisable() {
    LOG.info( "tc4_TogglePromptPanel_PromptPanelEnableDisable" );
    DRIVER.switchTo().defaultContent();

    // ## Step 1
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );
    assertNotNull( ElementHelper.FindElement( DRIVER, By.id( "reportControlPanel" ) ) );
    ElementHelper.FindElement( DRIVER, By.xpath( "//span[@id='toolbar-parameterToggle']/span" ) ).click();
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "reportControlPanel" ) );
    assertFalse( ElementHelper.WaitForElementNotPresent( DRIVER, By.id( "reportControlPanel" ), 2 ) );

    // ## Step 2
    ElementHelper.FindElement( DRIVER, By.xpath( "//span[@id='toolbar-parameterToggle']/span" ) ).click();
    assertNotNull( ElementHelper.FindElement( DRIVER, By.id( "reportControlPanel" ) ) );
  }

  /**
   * ############################### Test Case 5 ###############################
   *
   * Test Case Name:
   *    Select Several Products
   * Description:
   *    The test case pretends to validate that when we select a product a new
   *    report is generated.
   * Steps:
   *    1. Disable Classic Cars and assert results
   *    2. Enable Motorcycles and assert results
   *
   */
  @Test( timeout = 60000 )
  public void tc5_SelectSeveralProducts_ReportIsRefreshed() {
    LOG.info( "tc5_SelectSeveralProducts_ReportIsRefreshed" );
    DRIVER.switchTo().defaultContent();
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );

    // ## Step 1
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='pentaho-toggle-button-container']/div/div/button" ) );
    assertNotNull( element );
    String text = element.getText();
    assertEquals( "Classic Cars", text );
    element.click();
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );
    DRIVER.switchTo().frame( "reportContent" );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//tbody/tr" ) );
    assertNotNull( element );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr" ), "LINE: Planes" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr" ) );
    assertEquals( "LINE: Planes", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr[3]/td" ), "Autoart Studio Design" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr[3]/td" ) );
    assertEquals( "Autoart Studio Design", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ), "P-51-D Mustang" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ) );
    assertEquals( "P-51-D Mustang", text );
    text = ElementHelper.GetAttribute( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ), "href" );
    assertEquals( "http://images.google.com/images?q=P-51-D%20Mustang", text );

    // ## Step 2
    DRIVER.switchTo().defaultContent();
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@class='pentaho-toggle-button-container']/div/div[2]/button" ) );
    assertNotNull( element );
    text = element.getText();
    assertEquals( "Motorcycles", text );
    element.click();
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );
    DRIVER.switchTo().frame( "reportContent" );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//tbody/tr" ) );
    assertNotNull( element );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr" ), "LINE: Motorcycles" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr" ) );
    assertEquals( "LINE: Motorcycles", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr[3]/td" ), "Autoart Studio Design" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr[3]/td" ) );
    assertEquals( "Autoart Studio Design", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ), "1997 BMW F650 ST" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ) );
    assertEquals( "1997 BMW F650 ST", text );
    text = ElementHelper.GetAttribute( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ), "href" );
    assertEquals( "http://images.google.com/images?q=1997%20BMW%20F650%20ST", text );

  }

  /**
   * ############################### Test Case 6 ###############################
   *
   * Test Case Name:
   *    Output Type
   * Description:
   *    We pretend to test all Output Type options.
   * Steps:
   *    1. Select: HTML (Paginated)
   *    2. Select: HTML (Single Page)
   *    3. Select: PDF
   *    4. Select: Excel
   *    5. Select: Excel 2007
   *    6. Select: Comma Separated Value
   *    7. Select: Rich-Text-Format
   *    8. Select: Text
   * @throws InterruptedException
   */
  @Test( timeout = 60000 )
  public void tc6_SelectAllOutputTypeOptions_DialogBoxIsRaised() {
    LOG.info( "tc6_SelectAllOutputTypeOptions_DialogBoxIsRaised" );
    DRIVER.switchTo().defaultContent();

    String downloadDir = CToolsTestSuite.getDownloadDir();

    // ## Step 1
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );
    Select select = new Select( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "table/html;page-mode=page" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );
    //Check the generated image
    DRIVER.switchTo().frame( "reportContent" );
    WebElement element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//tbody/tr" ) );
    assertNotNull( element );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr" ), "LINE: Motorcycles" );
    String text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr" ) );
    assertEquals( "LINE: Motorcycles", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr[3]/td" ), "Autoart Studio Design" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr[3]/td" ) );
    assertEquals( "Autoart Studio Design", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ), "1997 BMW F650 ST" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ) );
    assertEquals( "1997 BMW F650 ST", text );
    text = ElementHelper.GetAttribute( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ), "href" );
    assertEquals( "http://images.google.com/images?q=1997%20BMW%20F650%20ST", text );

    // ## Step 2
    DRIVER.switchTo().defaultContent();
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );
    select = new Select( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "table/html;page-mode=stream" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );
    //Check the generated image
    DRIVER.switchTo().frame( "reportContent" );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//tbody/tr" ) );
    assertNotNull( element );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr" ), "LINE: Motorcycles" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr" ) );
    assertEquals( "LINE: Motorcycles", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr[3]/td" ), "Autoart Studio Design" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr[3]/td" ) );
    assertEquals( "Autoart Studio Design", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ), "1997 BMW F650 ST" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ) );
    assertEquals( "1997 BMW F650 ST", text );
    text = ElementHelper.GetAttribute( DRIVER, By.xpath( "//tbody/tr[5]/td[3]/a" ), "href" );
    assertEquals( "http://images.google.com/images?q=1997%20BMW%20F650%20ST", text );

    // ## Step 3
    DRIVER.switchTo().defaultContent();
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );
    select = new Select( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "pageable/pdf" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );
    //Check the generated image
    DRIVER.switchTo().frame( "reportContent" );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']" ) );
    assertNotNull( element );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div" ), "L I N E :" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div" ) );
    assertEquals( "L I N E :", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div[2]" ), "M o t o r c y c l e s" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div[2]" ) );
    assertEquals( "M o t o r c y c l e s", text );
    ElementHelper.WaitForTextPresence( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div[3]" ), "MSRP" );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//div[@id='pageContainer1']/div[@class='textLayer']/div[3]" ) );
    assertEquals( "MSRP", text );

    // ## Step 4
    DRIVER.switchTo().defaultContent();
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );
    new File( downloadDir + "\\InventorybyLine.xls" ).delete();
    select = new Select( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "table/excel;page-mode=flow" );
    //Wait for file to be created in the destination dir
    DirectoryWatcher.WatchForCreate( downloadDir );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );
    assertTrue( new File( downloadDir + "\\InventorybyLine.xls" ).exists() );
    new File( downloadDir + "\\InventorybyLine.xls" ).delete();

    // ## Step 5
    new File( downloadDir + "\\InventorybyLine.xlsx" ).delete();
    select = new Select( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;page-mode=flow" );
    //Wait for file to be created in the destination dir
    DirectoryWatcher.WatchForCreate( downloadDir );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );
    assertTrue( new File( downloadDir + "\\InventorybyLine.xlsx" ).exists() );
    new File( downloadDir + "\\InventorybyLine.xlsx" ).delete();

    // ## Step 6
    new File( downloadDir + "\\InventorybyLine.csv" ).delete();
    select = new Select( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "table/csv;page-mode=stream" );
    //Wait for file to be created in the destination dir
    DirectoryWatcher.WatchForCreate( downloadDir );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );
    assertTrue( new File( downloadDir + "\\InventorybyLine.csv" ).exists() );
    new File( downloadDir + "\\InventorybyLine.csv" ).delete();

    // ## Step 7
    new File( downloadDir + "\\InventorybyLine.rtf" ).delete();
    select = new Select( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "table/rtf;page-mode=flow" );
    //Wait for file to be created in the destination dir
    DirectoryWatcher.WatchForCreate( downloadDir );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );
    assertTrue( new File( downloadDir + "\\InventorybyLine.rtf" ).exists() );
    new File( downloadDir + "\\InventorybyLine.rtf" ).delete();

    // ## Step 8
    //TODO - pageable/text
    DRIVER.switchTo().defaultContent();
    DRIVER.switchTo().frame( "sampleObject_prptFrame" );
    select = new Select( ElementHelper.FindElement( DRIVER, By.xpath( "//div[@class='parameter']/div[2]/select" ) ) );
    select.selectByValue( "pageable/text" );
    ElementHelper.WaitForElementInvisibility( DRIVER, By.id( "glasspane" ) );
    //Check the generated image
    DRIVER.switchTo().frame( "reportContent" );
    element = ElementHelper.WaitForElementPresenceAndVisible( DRIVER, By.xpath( "//pre" ) );
    assertNotNull( element );
    text = ElementHelper.WaitForElementPresentGetText( DRIVER, By.xpath( "//pre" ) );
    assertTrue( text.contains( "LINE: Motorcycles" ) );

  }

  @AfterClass
  public static void tearDown() {
    LOG.info( "tearDown##" + PrptComponent.class.getSimpleName() );
  }
}
