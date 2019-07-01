package first.sample.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import first.common.common.CommandMap;
import first.login.naver.User;

public interface AdminService {

	Map<String, Object> GetCountInfo() throws Exception;
	Map<String, Object> getUserLog10() throws Exception;
	Map<String, Object> getUser(Map<String, Object> map) throws Exception;
	Map<String, Object> getLog(Map<String, Object> map) throws Exception;
	Map<String, Object> getBoard(Map<String, Object> map) throws Exception;
	Map<String, Object> getContents(Map<String, Object> map) throws Exception;
	Map<String, Object> getReply(Map<String, Object> map) throws Exception;
	void InsertBoard(Map<String, Object> map) throws Exception;
	void InsertUser(Map<String, Object> map) throws Exception;
	boolean CheckDuplicateUserID(Map<String, Object> map) throws Exception;
	boolean CheckDuplicateBoardID(Map<String, Object> map) throws Exception;
	Map<String, Object> getUserInfo(Map<String, Object> map) throws Exception;
	void UpdateUser(Map<String, Object> map) throws Exception;
	Map<String, Object> UpdateBoardForm(Map<String, Object> map) throws Exception;
	void UpdateBoard(Map<String, Object> map) throws Exception;
	void deleteBoard(Map<String, Object> map) throws Exception;
	void deleteUserId(Map<String, Object> map) throws Exception;
	void deleteContents(Map<String, Object> map) throws Exception;
	void deleteReply(Map<String, Object> map) throws Exception;
	void UpdatePost(Map<String, Object> map,HttpServletRequest request) throws Exception;
	void UpdateReply(Map<String, Object> map) throws Exception;
	Map<String, Object> getMenu(Map<String, Object> map) throws Exception;


}
