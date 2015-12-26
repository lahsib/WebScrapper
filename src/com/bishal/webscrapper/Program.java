/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bishal.webscrapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Bishal Lama
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    
    public static String getURLContent(String uri)
    {
        try{
        URL url=new URL(uri);
        URLConnection conn= url.openConnection();
        BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        String line="";
        StringBuilder content=new StringBuilder();
        
        
        while((line=reader.readLine())!=null){
            content.append(line);
        }
        return content.toString();
        
        }catch(MalformedURLException me){
             System.out.println(me.getMessage());
             return null;
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
             return null;
        }
        
        
        
    }
    
    public static void writeFile(int num,String content) throws IOException{
        
        FileWriter writer=new FileWriter(new File("url_" + num + ".html"));
        writer.write(content);
        writer.close();
        
        
    }
    public static void main(String[] args) {
        
        String data=getURLContent("http://jobsnepal.com");
        System.out.println("Scrapping Started please be seated");
        if(data!=null){
        String contentPattern="<a class=\"job-item\" (.*?) href=\"(.*?)\" >(.*?)<\\/a>";
        
            Pattern pattern=Pattern.compile(contentPattern);
            Matcher matcher=pattern.matcher(data);
            int i=1;
            while(matcher.find()){
                String jobTitle=matcher.group(3).trim();
                String link=matcher.group(2);
                
                data=getURLContent(link);
                if(data!=null){
                    try{
                    writeFile(i, data);
                    }catch(IOException ioe){}
                }
                i++;
            }
        }
            
        System.out.println("Scrapping Complete");
        
        
        
    }
    
}
