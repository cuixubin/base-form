package com.dlshouwen.core.base.utils;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Logger;

public class SysConfigLoader
{
  private Properties p = new Properties();
  private static Logger logger = Logger.getLogger(SysConfigLoader.class.getName());
  private static final String BASEDIR = SysConfigLoader.class.getResource("").getPath();
  private static final String DEFAULTDIR = BASEDIR.substring(0, BASEDIR.indexOf("com/")) + "conf/sysConfig.properties";
  
  public SysConfigLoader()
  {
    try
    {
      read(DEFAULTDIR, "utf-8");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      logger.error(e.getMessage());
    }
  }
  
  private static SysConfigLoader sysConfig = new SysConfigLoader();
  
  public static SysConfigLoader getSystemConfig()
  {
    return sysConfig;
  }
  
  public void read(String url, String encoding)
    throws IOException
  {
    File file = new File(url);
    InputStream is = new BufferedInputStream(new FileInputStream(file));
    this.p = new Properties();
    this.p.load(new InputStreamReader(is, encoding));
  }
  
  public String getProperty(String name)
  {
    return this.p.getProperty(name);
  }
  
  public String getProperty(String name, String defaultValue)
  {
    return this.p.getProperty(name, defaultValue);
  }
  
  public Enumeration getNames()
  {
    return this.p.propertyNames();
  }
  
  public void Output(PrintStream ps)
  {
    this.p.list(ps);
  }
  
  public void write(String key, String value)
    throws Exception
  {
    File file = new File(DEFAULTDIR);
    

    InputStream fis = new FileInputStream(file);
    this.p.load(fis);
    fis.close();
    OutputStream fos = new FileOutputStream(file);
    if (!(this.p.getProperty(key) == null ? "" : this.p.getProperty(key)).equals(value.trim()))
    {
      this.p.setProperty(key, value);
      this.p.store(fos, key + " = " + value);
      fos.close();
    }
  }
  
  public Properties getP()
  {
    return this.p;
  }
  
  public static void main(String[] args)
  {
    String test = getSystemConfig().getProperty("test");
    String isNull = getSystemConfig().getProperty("test12", "1");
    System.out.println(test);
    System.out.println(isNull);
  }
}
