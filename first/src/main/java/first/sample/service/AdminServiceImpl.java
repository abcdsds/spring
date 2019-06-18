package first.sample.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import first.common.common.CommandMap;
import first.common.util.FileUtils;
import first.sample.dao.AdminDAO;
import first.sample.dao.SampleDAO;

@Service("adminService")
public class AdminServiceImpl implements AdminService{

	@Resource(name="adminDAO")
	private AdminDAO adminDAO;
	
	@Resource(name="sampleDAO")
	private SampleDAO sampleDAO;
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;

	@Override
	public Map<String, Object> GetCountInfo() throws Exception {
		// TODO Auto-generated method stub

		Map<String, Object> resultMap = new HashMap<String,Object>();
		Map<String, Object> tempMap = adminDAO.CountAll();
		resultMap.put("map", tempMap);
		return resultMap;
	}

	@Override
	public Map<String, Object> getUserLog10() throws Exception {

		Map<String, Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> tempMap = adminDAO.UserLogList10();
		resultMap.put("list", tempMap);

		return resultMap;
	}

	@Override
	public Map<String, Object> getUser(Map<String, Object> map) throws Exception {


		return adminDAO.UserList(map);
	}

	@Override
	public Map<String, Object> getLog(Map<String, Object> map) throws Exception {

		return adminDAO.LogList(map);
	}

	@Override
	public Map<String, Object> getBoard(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return adminDAO.BoardList(map);
	}

	@Override
	public Map<String, Object> getContents(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return adminDAO.ContentsList(map);
	}

	@Override
	public Map<String, Object> getReply(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return adminDAO.ReplyList(map);
	}

	@Override
	public void InsertBoard(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		adminDAO.InsertBoard(map);
	}

	@Override
	public void InsertUser(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		adminDAO.InsertUser(map);
	}

	@Override
	public boolean CheckDuplicateUserID(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		int check = adminDAO.CheckDuplicateUserID(map);
		System.out.println(check);
		if(check < 0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean CheckDuplicateBoardID(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		int check = adminDAO.CheckDuplicateBoardID(map);
		System.out.println(check);
		if(check < 0) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public Map<String, Object> getUserInfo(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return adminDAO.getUserInfo(map);
	}
	
	@Override
	public void UpdateUser(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		adminDAO.UpdateUser(map);
	}
	
	@Override
	public void UpdateReply(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		adminDAO.UpdateReply(map);
	}
	
	@Override
	public void UpdatePost(Map<String, Object> map,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		adminDAO.UpdatePost(map);
		
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
	public Map<String, Object> UpdateBoardForm(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return adminDAO.getBoardInfo(map);
	}

	@Override
	public void UpdateBoard(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		adminDAO.UpdateBoard(map);
	}

	@Override
	public void deleteContents(Map<String, Object> map) throws Exception {
		adminDAO.DeleteContents(map);
	}
	
	
	@Override
	public void deleteUserId(Map<String, Object> map) throws Exception {
		adminDAO.DeleteUserId(map);
	}
	
	
	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
		adminDAO.DeleteBoard(map);
	}

	@Override
	public void deleteReply(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		if(sampleDAO.CheckReplyParent(map) > 0) {

			sampleDAO.deleteReply(map);
		}else {

			sampleDAO.RealdeleteReply(map);
		}

	}

	

}
