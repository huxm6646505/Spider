package org.web.org.web;

import us.codecraft.webmagic.Spider;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
 	   String answerUrl="//https://www.zhihu.com/question/30116337";
	   Spider.create(new AlbeeSpider()).addUrl(answerUrl).thread(1).run();
    }
}
