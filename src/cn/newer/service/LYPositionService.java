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
 * �������Խӿڵ����ݣ���װ��List�������ظ�����������
 * @author Tiger
 *
 */
public class LYPositionService {
	//�˷������ڽ������Խӿڵ�����
	public List<PbsLongYouDate> position() throws Exception{
		//�ӿ�
		String str = "http://61.153.58.90:8093/qzbikeapp/netpoints?lon=119.16&lat=29.02&len=0&type=3&tenant=0003";
		String jsonString = null;
		//Logger logger = null;
		//����URL��
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
	
		//��ȡ����
		String strString = jsonString.substring(jsonString.indexOf("data")+6, jsonString.lastIndexOf(","));
		//��������
		List<PbsLongYouDate> list = new ArrayList<>();
		
		//��������
		JSONArray jsStr = JSONArray.fromObject(strString); 
        if(jsStr.size()>0){
          //ѭ������
       	  for(int i=0;i<jsStr.size();i++){
       		  PbsLongYouDate pbsLongYouDate = new PbsLongYouDate();
	       	  JSONObject job = jsStr.getJSONObject(i);  // ���� jsonarray ���飬��ÿһ������ת�� json ����
	       	  String name = job.get("name").toString();
	       	  String number = job.get("number").toString();
	       	  String lat = job.get("lat").toString();
	          String lon = job.get("lon").toString();
	      	  String rentcount = job.get("rentcount").toString();
	      	  String restorecount = job.get("restorecount").toString();
	       	  
	       	  //���ݴ��������
	      	  pbsLongYouDate.setName(name);
	      	  pbsLongYouDate.setNumber(number);
	      	  pbsLongYouDate.setLon(lon);
	      	  pbsLongYouDate.setLat(lat);
	      	  pbsLongYouDate.setRentcount(rentcount);
	      	  pbsLongYouDate.setRestorecount(restorecount);
	      	  list.add(pbsLongYouDate);
       	  	}
        }
        //�ر���
    	is.close();
        return list;
	}
}
