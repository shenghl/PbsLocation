package cn.newer.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.newer.po.PbsLongYouDate;
import cn.newer.service.LYPositionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用于处理jsp传来的请求
 */
//@WebServlet("/LYPosition")
public class LYPosition extends HttpServlet {
	//处理页面的的requestLocation的位置请求
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("======收到来自jsp的请求=======");
		//调用service
		LYPositionService service = new LYPositionService();
		PrintWriter out = null;
		try {
			List<PbsLongYouDate> list = service.position();
//			DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
//			
//			dataGridResultInfo.setTotal(list.size());
//			dataGridResultInfo.setRows(list);
//			
			
			
			response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/json; charset=utf-8");

	        JSONArray jsonObject = JSONArray.fromObject( list );
	        System.out.println(jsonObject);
	        String jsonData = jsonObject.toString();

			out = response.getWriter();
			out.println(jsonData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
		}
	}

}
