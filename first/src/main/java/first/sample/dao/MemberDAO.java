package first.sample.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import first.common.dao.AbstractDAO;
import first.login.naver.User;

@Repository("memberDAO")
public class MemberDAO extends AbstractDAO{


	public User memberlogin(Map<String, Object> map) throws Exception{
		return (User)selectOne("member.login", map);
	}

	public void updateReplyBoard(Map<String, Object> map) throws Exception{
		update("member.updateReplyBoard", map);
	}

	public int selectsocialId(User memberVO) throws Exception{
		return (Integer) selectOne("member.checksocialid", memberVO);
	}

	public void insertsocialId(User memberVO) throws Exception{
		insert("member.insertUser", memberVO);
	}

	public void insertCommonUser(Map<String, Object> map) throws Exception{
		insert("member.InsertCommonUser", map);
	}

	public String getRole(User memberVO) throws Exception{
		return selectOne("member.getRole", memberVO).toString();
	}

	public User getUserInfo(User memberVO) throws Exception{
		return (User)selectOne("member.getUserInfo", memberVO);
	}

	public String UpdateUserInfo(User memberVO) throws Exception{
		return update("member.updateUserInfo", memberVO).toString();
	}

	public void insertLog(User memberVO) throws Exception{
		insert("member.insertUserLog", memberVO);
	}

    @SuppressWarnings("unchecked")
	public Map<String, Object> getOwnContents(Map<String, Object> map) throws Exception {
		return (Map<String, Object>) selectPagingList("member.getOwnContents", map);
	}
    
    @SuppressWarnings("unchecked")
   	public Map<String, Object> getOwnReplys(Map<String, Object> map) throws Exception {
   		return (Map<String, Object>) selectPagingList("member.getOwnReplys", map);
   	}







}
