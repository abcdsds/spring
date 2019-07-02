package first.sample.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import first.common.common.CommandMap;
import first.common.dao.AbstractDAO;

@Repository("adminDAO")
public class AdminDAO extends AbstractDAO{
	

	@SuppressWarnings("unchecked")
	public Map<String, Object> CountAll() throws Exception{
		return (Map<String, Object>) selectOne("admin.getCountAll");
    }
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> UserLogList10() throws Exception{
		return (List<Map<String,Object>>)selectList("admin.getUserLog10");
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> UserList(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectPagingList("admin.getAllUser", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> MenuList(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectPagingList("admin.getAllMenu", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> LogList(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectPagingList("admin.getAllLog", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> BoardList(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectPagingList("admin.getAllBoard", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> ContentsList(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectPagingList("admin.getAllContents", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> ReplyList(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectPagingList("admin.getAllReply", map);
	}
	

	public void InsertBoard(Map<String, Object> map) throws Exception{
		insert("admin.InsertBoard", map);
	}
	
	public void InsertMenu(Map<String, Object> map) throws Exception{
		insert("admin.InsertMenu", map);
	}
	
	
	public void InsertUser(Map<String, Object> map) throws Exception{
		insert("admin.InsertUser", map);
	}
	
	public int CheckDuplicateUserID(Map<String, Object> map) throws Exception{
		return (Integer) insert("admin.DuplicateUserId", map);
	}
	
	public int CheckDuplicateBoardID(Map<String, Object> map) throws Exception{
		return (Integer) insert("admin.DuplicateBoardId", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getUserInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("admin.getUserInfo",map);
    }
	
	public void UpdateUser(Map<String, Object> map) throws Exception{
		update("admin.updateUserInfo", map);
	}
	
	public void UpdateReply(Map<String, Object> map) throws Exception{
		update("admin.updateReply", map);
	}
	
	public void UpdatePost(Map<String, Object> map) throws Exception{
		update("admin.updatePost", map);
	}
	
	public void UpdateMenu(Map<String, Object> map) throws Exception{
		update("admin.updateMenu", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getBoardInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("admin.getBoardInfo",map);
    }
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMenuInfo(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("admin.getMenuInfo",map);
    }

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMainMenuList(Map<String, Object> map) throws Exception{
		return (List<Map<String,Object>>)selectList("admin.getMainMenuList");
    }

	public void UpdateBoard(Map<String, Object> map) throws Exception{
		update("admin.updateBoardInfo", map);
	}
	
	
	public void DeleteBoard(Map<String, Object> map) throws Exception{
		delete("admin.DeleteBoard", map);
	}
	
	public void DeleteMenu(Map<String, Object> map) throws Exception{
		delete("admin.DeleteMenu", map);
	}
	
	public void DeleteUserId(Map<String, Object> map) throws Exception{
		delete("admin.DeleteUserId", map);
	}
	
	public void DeleteContents(Map<String, Object> map) throws Exception{
		delete("admin.DeleteContent", map);
	}
	
	public void DeleteReply(Map<String, Object> map) throws Exception{
		delete("admin.DeleteReply", map);
	}
	
	


}
