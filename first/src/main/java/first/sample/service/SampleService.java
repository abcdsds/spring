package first.sample.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SampleService {


	
	void updatePost(Map<String, Object> map, HttpServletRequest request) throws Exception;

	
	Map<String, Object> selectBoardListSearch(Map<String, Object> map) throws Exception;
	List<Map<String, Object>> selectBoardList2(Map<String, Object> map) throws Exception;

	Map<String, Object> ReplyList(Map<String, Object> map) throws Exception;

	void ReReplyInsert(Map<String, Object> map) throws Exception;

	void ReReplyUpdate(Map<String, Object> map) throws Exception;
	void insertReplyBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;
	Map<String, Object> selectBoardDetail2(Map<String, Object> map) throws Exception;
	
	//----------------------------�Խñ�-------------------------------------------------
	
	Map<String, Object> selectBoardList(Map<String, Object> map) throws Exception;
	Map<String, Object> selectBoardDetail(Map<String, Object> map,HttpServletRequest request,HttpServletResponse response) throws Exception;
	void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception;
	void deleteBoard(Map<String, Object> map) throws Exception;
	
	//----------------------------��� --------------------------------------------------
	void insertReply(Map<String, Object> map) throws Exception;
    void updateReply(Map<String, Object> map) throws Exception;
	void deleteReply(Map<String, Object> map) throws Exception;
	
	//-----------------------------����Ȯ�� , �޴����, �Խ����̸� --------------------------------------
	Map<String, Object> getBoardRole(Map<String, Object> map) throws Exception;
	Map<String, Object> selectMenuList(Map<String, Object> map) throws Exception;
	Map<String,Object> getBoardName(String id) throws Exception;

}