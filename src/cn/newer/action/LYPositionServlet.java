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
 * ����ҳ������
 */
public class LYPositionServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	//����jspҳ���λ������
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("======�յ�����jsp������=======");
		//����LYPositionService����
		LYPositionService service = new LYPositionService();
		PrintWriter out = null;
		try {
			//����service�����еķ���ֵ
			List<PbsLongYouDate> list = service.position();
			//���ñ����ʽ
			response.setCharacterEncoding("UTF-8");
	        response.setContentType("application/json; charset=utf-8");
	        //��װ����
	        JSONArray jsonObject = JSONArray.fromObject(list);
	        //�������
	        //System.out.println("LYPositionServlet_dopost����������ԣ�"+jsonObject);
	        String jsonData = jsonObject.toString();
	        //�������
			out = response.getWriter();
			out.println(jsonData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//�ر���
			if(out != null){
				out.close();
			}
		}
	}
}
