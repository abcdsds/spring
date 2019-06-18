package first.sample.dao;
 
import java.util.List;
import java.util.Map;
 
import org.springframework.stereotype.Repository;
 
import first.common.dao.AbstractDAO;
 
@Repository("sampleDAO")
public class SampleDAO extends AbstractDAO{
 

	public Map<String,Object> GetBoardName(String map) throws Exception{
        return (Map<String,Object>) selectOne("sample.GetBoardName", map);
    }

	public void insertReplyBoard(Map<String, Object> map) throws Exception{
        insert("sample.insertReplyBoard", map);
    }
    
    public void insertReplyReply(Map<String, Object> map) throws Exception{
        insert("reply.insertReplyReply", map);
    }
    
    public void insertReply(Map<String, Object> map) throws Exception{
        insert("reply.insertReply", map);
    }
    
    public void updateReplyBoard(Map<String, Object> map) throws Exception{
        update("sample.updateReplyBoard", map);
    }
    
    public void updateReplyReply(Map<String, Object> map) throws Exception{
        update("reply.updateReplyReply", map);
    }
    
    public void updateReply(Map<String, Object> map) throws Exception{
        update("reply.updateReply", map);
    }
    
    public int CheckReplyParent(Map<String, Object> map) throws Exception{
        return (Integer) selectOne("reply.checkreplycount", map);
    }
    
    public void deleteReply(Map<String, Object> map) throws Exception{
        update("reply.DeleteReply", map);
    }
    
    public void RealdeleteReply(Map<String, Object> map) throws Exception{
        delete("reply.RealDeleteReply", map);
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> ReplyList(Map<String, Object> map) throws Exception{
        return (Map<String, Object>)selectPagingList("reply.selectBoardList", map);
    }
    
	

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> selectBoardList2(Map<String, Object> map) throws Exception{
		return (List<Map<String, Object>>)selectPagingList2("sample.selectBoardList", map);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardListSearch(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectPagingList("sample.boardsearch", map);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectBoardList(Map<String, Object> map) throws Exception{
		return (Map<String, Object>)selectPagingList("sample.selectBoardList", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> selectMenuList(Map<String, Object> map) throws Exception{
		return (List<Map<String,Object>>)selectList("sample.selectMenuList", map);
	}



    public void insertBoard(Map<String, Object> map) throws Exception{ 
    	insert("sample.insertBoard", map); 
    	
    }
    public void insertFile(Map<String, Object> map) throws Exception{ 
    	insert("sample.insertFile", map);
    	
    }


    public void updateHitCnt(Map<String, Object> map) throws Exception{
    	update("sample.updateHitCnt", map);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> selectBoardDetail(Map<String, Object> map) throws Exception{
    	return (Map<String, Object>) selectOne("sample.selectBoardDetail", map);
    }
    

    public void updatePost(Map<String, Object> map) throws Exception{
    	update("sample.updateContents", map);
    }
    
    public void deleteBoard(Map<String, Object> map) throws Exception{
    	update("sample.deleteBoard", map);
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> selectFileList(Map<String, Object> map) throws Exception{
    	return (List<Map<String, Object>>)selectList("sample.selectFileList", map);
    }

  

    public void deleteFileList(Map<String, Object> map) throws Exception{
    	update("sample.deleteFileList", map);
    }

    public void updateFile(Map<String, Object> map) throws Exception{
    	update("sample.updateFile", map);
    }

    @SuppressWarnings("unchecked")
	public Map<String, Object> getBoardRole(Map<String, Object> map) throws Exception{
		return (Map<String, Object>) selectOne("sample.getBoardRole",map);
    }


 
}
