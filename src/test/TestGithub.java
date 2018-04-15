package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TestGithub{
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    String info=new String();
    String address=new String();
      
    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver","E:/ѧϰ����/�������/lab2/chromedriver.exe");
        driver = new ChromeDriver();
        baseUrl = "https://psych.liebes.top/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
      
    @Test
    public void test1() throws Exception {   
        //��ȡexcel�ļ�
        File file = new File("E:/ѧϰ����/�������/lab2/input.xls");  
        
        int rows;
        int agree = 0;
        int disagree = 0;
        ArrayList disInfos = new ArrayList();
        
        try { 
            InputStream is = new FileInputStream(file.getAbsolutePath()); 
            Workbook wb = Workbook.getWorkbook(is); 
            Sheet sheet = wb.getSheet(0);
            
            rows = sheet.getRows();
            
            for (int i = 0; i < sheet.getRows(); i++) {  
                   String number = sheet.getCell(0, i).getContents();  //��ȡѧ��
                   String pwd = number.substring(number.length()-6,number.length()); //��ȡ����
                   address = sheet.getCell(1, i).getContents().trim();  //��ȡgithub��ַ,�ǵ���trimȥ����β�ո�
                   
                   //���ʸ�����ַ
                   driver.get(baseUrl + "/st");
                   //�����û���
                   driver.findElement(By.id("username")).clear();
                   driver.findElement(By.id("username")).sendKeys(number);
                   //��������
                   driver.findElement(By.id("password")).clear();
                   driver.findElement(By.id("password")).sendKeys(pwd);
                   //�����¼��ť
                   driver.findElement(By.id("submitButton")).click();
                   //��¼�ɹ�֮�󣬻�õ�ǰҳ����û���Ϣ
                   info = driver.findElement(By.tagName("p")).getText().trim();
                   
                   if(info.endsWith("/")) {
                       info = info.substring(0, info.length()-1);
                   }
                   if(address.endsWith("/")) {
                       address = address.substring(0, address.length()-1);
                   }
                   
                   //�Ƚϲ�ѯ��Ϣ            
                   if(info.equals(address)) {
                       assertEquals(info,address);
                       agree ++;
                   }
                   else {
                       disagree ++;
                       disInfos.add(number);
                       disInfos.add(info);
                   }
            }
            driver.close();
            System.out.println("���� " + rows + " ����Ϣ");
            System.out.println("��Ϣƥ����� " + agree + " ��");
            System.out.println("��Ϣ��ƥ����� " + disagree + " ��");
            System.out.println("��Ϣ��ƥ���Ϊ��" + disInfos.toString());
            
        } catch (FileNotFoundException e) { 
            e.printStackTrace();  
        } catch (BiffException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  

    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

}