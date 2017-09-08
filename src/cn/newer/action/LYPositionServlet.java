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

/**
 * 处理页面请求
 */
public class LYPositionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	//处理jsp页面的位置请求
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("======收到来自jsp的请求=======");
		//创建LYPositionService对象
		LYPositionService service = new LYPositionService();
		PrintWriter out = null;
		try {
			//接收service方法中的返回值
			List<PbsLongYouDate> list = service.position();
			//设置编码格式
			response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/json; charset=utf-8");
	        //封装数据
	        JSONArray jsonObject = JSONArray.fromObject(list);
	        //输出测试
	        //System.out.println("LYPositionServlet_dopost方法输出测试："+jsonObject);
	        String jsonData = jsonObject.toString();
	        //数据输出
			out = response.getWriter();
			out.println(jsonData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭流
			if(out != null){
				out.close();
			}
		}
	}
}
