package org.web.org.web;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class AlbeeSpider implements PageProcessor {




	    // 设置编码 ，超时时间，重试次数，
	    private Site site = Site.me().setRetryTimes(10).setSleepTime(5000).setTimeOut(5000)
	            .addCookie("www.zhihu.com", "unlock_ticket", "Mi4wQUJDTXNXVllVZ2dBa01CR2UtTFFDaVlBQUFCZ0FsVk5URS1FV1FBendCSlBMOTZxMU1qMFd2TnFFMVpuV2MyRDhB|2b5fc9f9cc82\n" +
	            		"|68309d8a59e9206c27e3a03e96d8ce7f2adb18c29bb12eef68c4")
	            .addCookie("login", "Mi4wQUJDTXNXVllVZ2dBa01CR2UtTFFDaVlBQUFCZ0FsVk5URS1FV1FBendCSlBMOTZxMU1qMFd2TnFFMVpuV2MyRDhB|2b5fc9f9cc8268309d8a59e9206c27e3a03e96d8ce7f2adb18c29bb12eef68c4")
	            .addCookie("Domain", "zhihu.com")
            .addCookie("z_c0", "你的cookie值")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");

	        /*  .addCookie("www.zhihu.com", "unlock_ticket", "Mi4wQUFEQWRDMDFBQUFBa0VLVlg3YjlDeGNBQUFCaEFsVk5vakY5V1FBMlBoVFNIaXh1akJYVGhvbnhHSVpnX0k1YVpB|1498784930\n" +
                    "|22f9fa8b6055194936e48cdc230f064ca0b239f4")
//            .addCookie("login", "MWRiZWUxNmMzOTA5NDdmNTkwNGRmNWQyZWZhNDRmY2U=|1475371295|b9e9c165fc1d3c314afa2b66e3ff27c514bb4946")
            .addCookie("Domain", "zhihu.com")
            .addCookie("z_c0", "你的cookie值")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.89 Safari/537.36");
*/
	    //话题精华页
	    //https://www.zhihu.com/topic/19551388/top-answers
	    private static final String URL_topAnswer = "https://www\\.zhihu\\.com/topic/\\d+/top-answers";
	    private static final String URL_topAnswerPage = "https://www\\.zhihu\\.com/topic/\\d+/top-answers\\?page=\\d";
	    //话题索引页
	    //https://www.zhihu.com/topic/19551388
	    private static final String URL_topic = "^https://www\\.zhihu\\.com/topic/\\d+$";
	    //问题的索引
	    //https://www.zhihu.com/question/20902967
	    private static final String URL_question = "^https://www\\.zhihu\\.com/question/\\d+$";
	    //https://www.baidu.com
	    private static final String test = "https://www\\.baidu\\.com";
	    //https://www.zhihu.com/question/19647535/answer/110944270
	    private  static  final String URL_answer = "https://www\\.zhihu\\.com/question/\\d+/answer/\\d+";
	    //https://www.zhihu.com/people/dan-wen-hui-10/answers
	    private  static  final String URL_user = "https://www\\.zhihu\\.com/people/[\\s\\S]+/answers";
	    private String offset = "0";
	    
	    
	public Site getSite() {
		// TODO Auto-generated method stub
		 return site;
	}

	public void process(Page page) {
		        if(page.getUrl().regex(URL_answer).match()){
		            List<String> urlList  = page.getHtml().xpath("//div[@class=RichContent-inner]//img/@data-original").all();
		            String questionTitle = page.getHtml().xpath("//h1[@class=QuestionHeader-title]/text()").toString();
		            System.out.println("题目："+questionTitle);
		            System.out.println(urlList);
		            System.out.println(urlList.size());
		            List<String> url = new ArrayList<String>();
		            for (int i=0;i<urlList.size();i=i+2){
		                url.add(urlList.get(i));
		            }
		            String filePath = "D:\\知乎图片\\";
		            try {
		                downLoadPics(url,questionTitle,filePath);
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		
	}
	
	
	public static void main(String[] args) {
		  String answerUrl="https://www.zhihu.com/question/36055254/answer/101613748";
		 
			  Spider.create(new AlbeeSpider()).addUrl(answerUrl).thread(1).run();
	}
	


	 public static boolean downLoadPics( List<String> imgUrls,String title, String filePath) throws Exception {
	        boolean isSuccess = true;

	        // 文件路径+标题
	        String dir = filePath +title;
	        // 创建
	       dir= dir.substring(0,dir.length()-1);
//	        dir=dir.replaceAll("?", "1");
	        File fileDir = new File(dir);
	        fileDir.mkdirs();
	        
	        int i = 1;
	        // 循环下载图片
	        for (String imgUrl : imgUrls) {
	            URL url = new URL(imgUrl);
	            // 打开网络输入流
	            DataInputStream dis = new DataInputStream(url.openStream());
	            int x=(int)(Math.random()*1000000);
	            String newImageName = dir+ "/" + x+"pic" + i + ".jpg";
	            // 建立一个新的文件
	            FileOutputStream fos = new FileOutputStream(new File(newImageName));
	            byte[] buffer = new byte[1024];
	            int length;
	            System.out.println("正在下载......第 " + i + "张图片......请稍后");
	            // 开始填充数据
	            while ((length = dis.read(buffer)) > 0) {
	                fos.write(buffer, 0, length);
	            }
	            dis.close();
	            fos.close();
	            System.out.println("第 " + i + "张图片下载完毕......");
	            i++;
	        }
	        return isSuccess;
	    }

    
}
