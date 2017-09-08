package cn.newer.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.newer.po.PbsLongYouDate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 接收来自接口的数据，封装成List，并返回给方法调用者
 * @author Tiger
 *
 */
public class LYPositionService {
	//此方法用于接收来自接口的数据
	public List<PbsLongYouDate> position() throws Exception{
		//接口
		String str = "http://61.153.58.90:8093/qzbikeapp/netpoints?lon=119.16&lat=29.02&len=0&type=3&tenant=0003";
		String jsonString = null;
		//Logger logger = null;
		//调用URL类
		URL u = new URL(str);
		HttpURLConnection http = (HttpURLConnection) u.openConnection();
		http.setDoOutput(true);
		http.setDoInput(true);
		http.setUseCaches(false);
		http.setRequestProperty("Accept-Charset", "UTF-8");
		http.setRequestProperty("contentType", "UTF-8");
		http.setRequestProperty("", "");
		http.connect();
		String ct = http.getContentType();
		//logger.debug(ct);
		InputStream is = http.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;
		while( (b = is.read()) != -1){
			baos.write(b);
		}
		
		jsonString = new String(baos.toByteArray(), "UTF-8");
	
		//截取数据
		String strString = jsonString.substring(jsonString.indexOf("data")+6, jsonString.lastIndexOf(","));
		//创建对象
		List<PbsLongYouDate> list = new ArrayList<>();
		
		//解析参数
		JSONArray jsStr = JSONArray.fromObject(strString); 
        if(jsStr.size()>0){
          //循环遍历
       	  for(int i=0;i<jsStr.size();i++){
       		  PbsLongYouDate pbsLongYouDate = new PbsLongYouDate();
	       	  JSONObject job = jsStr.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
	       	  String name = job.get("name").toString();
	       	  String number = job.get("number").toString();
	       	  String lat = job.get("lat").toString();
	          String lon = job.get("lon").toString();
	      	  String rentcount = job.get("rentcount").toString();
	      	  String restorecount = job.get("restorecount").toString();
	       	  
	       	  //数据存入对象中
	      	  pbsLongYouDate.setName(name);
	      	  pbsLongYouDate.setNumber(number);
	      	  pbsLongYouDate.setLon(lon);
	      	  pbsLongYouDate.setLat(lat);
	      	  pbsLongYouDate.setRentcount(rentcount);
	      	  pbsLongYouDate.setRestorecount(restorecount);
	      	  list.add(pbsLongYouDate);
       	  	}
        }
        //关闭流
    	is.close();
        return list;
	}
}
