package first.sample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import first.common.common.CommandMap;
import first.login.naver.Role;
import first.login.naver.User;
import first.sample.dao.MemberDAO;

@Service("memberService")
public class MemberServiceImpl implements MemberService{

	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="memberDAO")
	private MemberDAO memberDAO;

	@Override
	public Map<String,Object> getOwnReplys(Map<String,Object> map) throws Exception {	
		return memberDAO.getOwnReplys(map);
	}
	
	
	@Override
	public Map<String,Object> getOwnContents(Map<String,Object> map) throws Exception {
		return memberDAO.getOwnContents(map);
	}
	
	
	@Override
	public User getUserInfo(String UserId) throws Exception {

		User memberVO = new User();
		memberVO.setId(UserId);
		memberVO = memberDAO.getUserInfo(memberVO);
		return memberVO;
	}

	@Override
	public void InsertLog(User memberVO) throws Exception {
		memberDAO.insertLog(memberVO);
	}

	@Override
	public void InsertCommonUser(Map<String, Object> map) throws Exception {
		
		memberDAO.insertCommonUser(map);
	}
	
	@Override
	public void UpdateUserInfo(User memberVO) throws Exception {
		memberDAO.UpdateUserInfo(memberVO);
	}

}
