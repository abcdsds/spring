package first.sample.service;

import java.util.Map;

import first.common.common.CommandMap;
import first.login.naver.User;

public interface MemberService {

	void UpdateUserInfo(User memberVO) throws Exception;
	User getUserInfo(String userId) throws Exception;
	void InsertLog(User memberVO) throws Exception;
	void InsertCommonUser(Map<String, Object> map) throws Exception;
	Map<String, Object> getOwnContents(Map<String, Object> map) throws Exception;
	Map<String, Object> getOwnReplys(Map<String, Object> map) throws Exception;

}
