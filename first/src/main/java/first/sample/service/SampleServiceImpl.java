package first.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import first.common.util.FileUtils;
import first.sample.dao.SampleDAO;

@Service("sampleService")
public class SampleServiceImpl implements SampleService{
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="fileUtils")
	private FileUtils fileUtils;

	@Resource(name="sampleDAO")
	private SampleDAO sampleDAO;


	@Override
	public Map<String, Object> ReplyList(Map<String, Object> map) throws Exception {
		return sampleDAO.ReplyList(map);
	}

	@Override
	public Map<String, Object> selectBoardDetail2(Map<String, Object> map) throws Exception {

		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = sampleDAO.selectBoardDetail(map);
		resultMap.put("map", tempMap);

		return resultMap;
	}

	@Override
	public void insertReplyBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
		sampleDAO.updateReplyBoard(map);
		sampleDAO.insertReplyBoard(map);
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		for(int i=0, size=list.size(); i<size; i++){
			sampleDAO.insertFile(list.get(i));
		}
	}

	@Override
	public void insertReply(Map<String, Object> map) throws Exception {
		sampleDAO.insertReply(map);
	}


	@Override
	public void ReReplyInsert(Map<String, Object> map) throws Exception {
		sampleDAO.updateReplyReply(map);
		sampleDAO.insertReplyReply(map);
	}

	@Override
	public void ReReplyUpdate(Map<String, Object> map) throws Exception {
		sampleDAO.updateReply(map);
	}

	@Override
	public Map<String, Object> selectBoardList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardList(map);
	}

	@Override
	public Map<String, Object> selectMenuList(Map<String, Object> map) throws Exception {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		resultMap.put("menulist", sampleDAO.selectMenuList(map));
		return resultMap;
	}


	@Override
	public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {

		sampleDAO.insertBoard(map);
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		System.out.println(list.size());
		for(int i=0, size=list.size(); i<size; i++){
			System.out.println(list.get(i));
			sampleDAO.insertFile(list.get(i));
		}
	}



	@Override
	public Map<String, Object> selectBoardDetail(Map<String, Object> map,HttpServletRequest request,HttpServletResponse response) throws Exception {

		Cookie[] cookies = request.getCookies();

		// 비교하기 위해 새로운 쿠키
		Cookie viewCookie = null;

		// 쿠키가 있을 경우 
		if (cookies != null && cookies.length > 0) 
		{
			for (int i = 0; i < cookies.length; i++)
			{
				// Cookie의 name이 cookie + reviewNo와 일치하는 쿠키를 viewCookie에 넣어줌 
				if (cookies[i].getName().equals("cookie"+map.get("IDX")))
				{ 
					viewCookie = cookies[i];
				}
			}
		}

		if (viewCookie == null) {
			Cookie newCookie = new Cookie("cookie"+map.get("IDX"), "|" + map.get("IDX") + "|");
            
            // 쿠키 추가
            response.addCookie(newCookie);
            
			sampleDAO.updateHitCnt(map);
		}
		
		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = sampleDAO.selectBoardDetail(map);
		resultMap.put("map", tempMap);

		List<Map<String,Object>> list = sampleDAO.selectFileList(map);
		resultMap.put("list", list);

		return resultMap;
	}


	@Override
	public void updatePost(Map<String, Object> map, HttpServletRequest request) throws Exception{
		sampleDAO.updatePost(map);

		sampleDAO.deleteFileList(map);
		List<Map<String,Object>> list = fileUtils.parseUpdateFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size(); i<size; i++){
			System.out.println(list.get(i));
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				sampleDAO.insertFile(tempMap);
			}
			else{
				sampleDAO.updateFile(tempMap);
			}
		}
	}


	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
		sampleDAO.deleteBoard(map);
	}

	@Override
	public Map<String, Object> getBoardRole(Map<String, Object> map) throws Exception {
		return sampleDAO.getBoardRole(map);
	}

	@Override
	public void updateReply(Map<String, Object> map) throws Exception {
		sampleDAO.updateReply(map);

	}

	@Override
	public void deleteReply(Map<String, Object> map) throws Exception {
		if(sampleDAO.CheckReplyParent(map) > 0) {

			sampleDAO.deleteReply(map);
		}else {

			sampleDAO.RealdeleteReply(map);
		}


	}
	@Override
	public Map<String,Object> getBoardName(String id) throws Exception {
		// TODO Auto-generated method stub
		
		
		return sampleDAO.GetBoardName(id);
	}

}
