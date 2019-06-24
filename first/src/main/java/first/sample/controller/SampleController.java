package first.sample.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import first.common.common.CommandMap;
import first.login.naver.User;
import first.sample.service.MemberService;
import first.sample.service.SampleService;

@Controller
public class SampleController {
	Logger log = Logger.getLogger(this.getClass());

	@Resource(name="sampleService")
	private SampleService sampleService;

	@Resource(name="memberService")
	private MemberService memberService;


	@RequestMapping(value = "/board/mycontents.do")
	public ModelAndView UserOwnContents(CommandMap commandMap,@RequestParam(value="act", required=false) String act) throws Exception {

		ModelAndView mv = new ModelAndView();

		if(commandMap.get("SCH_TYPE") != null && commandMap.get("SCH_KEYWORD") != null ) {
			mv.addObject("SCH_TYPE", commandMap.get("SCH_TYPE"));
			mv.addObject("SCH_KEYWORD", commandMap.get("SCH_KEYWORD"));
		}

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		commandMap.put("USER_ID", memberVO.getId());
		Map<String,Object> contentslist = null;
		if(act == null || act.equals("contents")) {
			contentslist = memberService.getOwnContents(commandMap.getMap());
		}else if(act.equals("replys")) {
			contentslist = memberService.getOwnReplys(commandMap.getMap());
		}else {
			
			mv.setViewName("redirect:/board/board.do");
			return mv;
		}
		
		Map<String,Object> menulist = sampleService.selectMenuList(commandMap.getMap());


		mv.addObject("paginationInfo", (PaginationInfo)contentslist.get("paginationInfo"));
		mv.addObject("contentslist", contentslist.get("result"));
		mv.addObject("menulist", menulist.get("menulist"));

		mv.addObject("NICKNAME", memberVO.getNickname());
		mv.addObject("USER_LEVEL", memberVO.getLevel());
		mv.addObject("act",act);


		mv.setViewName("board/usercontentslist");
		return mv;
	}


	@RequestMapping(value = "/board/memberupdate.do")
	public ModelAndView MemberUpdate(CommandMap commandMap) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		memberVO.setNickname(commandMap.get("nickname").toString());
		memberVO.setEmail(commandMap.get("email").toString());

		ModelAndView mv = new ModelAndView();
		memberService.UpdateUserInfo(memberVO);
		mv.setViewName("redirect:/board/member.do");
		return mv;
	}

	@RequestMapping(value = "/board/memberedit.do")
	public ModelAndView MemberEditForm(@RequestParam(value="id", required=false) String id,CommandMap commandMap) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		if(id == null) {

			id = memberVO.getId();
		}
		commandMap.put("USER_ID", memberVO.getId());

		ModelAndView mv = new ModelAndView();

		User MemberVO = memberService.getUserInfo(id);
		Map<String,Object> menulist = sampleService.selectMenuList(commandMap.getMap());

		mv.addObject("member",MemberVO);
		mv.addObject("menulist",menulist.get("menulist"));
		mv.setViewName("board/useredit");
		return mv;
	}

	@RequestMapping(value = "/board/member.do")
	public ModelAndView MemberInfo(@RequestParam(value="id", required=false) String id,CommandMap commandMap) throws Exception {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;
		System.out.println(memberVO.getNickname());
		if(id == null) {

			id = memberVO.getId();
		}
		commandMap.put("USER_ID", memberVO.getId());

		ModelAndView mv = new ModelAndView();

		User MemberVO = memberService.getUserInfo(id);
		Map<String,Object> menulist = sampleService.selectMenuList(commandMap.getMap());

		mv.addObject("member",MemberVO);
		mv.addObject("menulist",menulist.get("menulist"));
		mv.setViewName("board/user");
		return mv;
	}


	@RequestMapping(value = "/board/delete.do")
	public ModelAndView DoDelete(CommandMap commandMap) throws Exception {
		//글쓰기
		ModelAndView mv = new ModelAndView();
		sampleService.deleteBoard(commandMap.getMap());
		mv.setViewName("redirect:/board/board.do");
		return mv;
	}


	@RequestMapping(value = "/board/updateposts.do")
	public ModelAndView UpdatePost(CommandMap commandMap,HttpServletRequest request) throws Exception {
		//글쓰기
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;
		ModelAndView mv = new ModelAndView();
		commandMap.put("USER_ID", memberVO.getId());
		sampleService.updatePost(commandMap.getMap(), request);
		mv.setViewName("redirect:/board/board.do");
		return mv;
	}


	@RequestMapping(value = "/board/updateform.do")
	public ModelAndView UpdateForm(CommandMap commandMap,HttpServletRequest request,HttpServletResponse response,@RequestParam(value="BOARD_ID", required=false) String BOARD_ID) throws Exception {
		//글쓰기 폼
		ModelAndView mv = new ModelAndView();

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;



		if(BOARD_ID == null || BOARD_ID.length() < 3) {
			BOARD_ID = "main";
		}
		Map<String,Object> board = sampleService.getBoardName(BOARD_ID); 



		if(Integer.parseInt((String) board.get("BO_LEVEL")) < Integer.parseInt(memberVO.getLevel())) {


			mv.addObject("msg", "접근권한없음");
			mv.addObject("url", "/first/board/board.do");
			mv.setViewName("/redirect");
			return mv;
		}

		commandMap.put("USER_ID", memberVO.getId());
		Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap(),request,response);

		Map<String,Object> check =  (Map<String, Object>) map.get("map");

		if(check.get("USER_ID").toString().equals(memberVO.getId()) == false) {

			mv.addObject("msg", "접근권한없음");
			mv.addObject("url", "/first/board/board.do?BOARD_ID="+check.get("BOARD_ID").toString());
			mv.setViewName("/redirect");
			return mv;

		}


		Map<String,Object> PageMap = sampleService.ReplyList(commandMap.getMap());
		Map<String,Object> menulist = sampleService.selectMenuList(commandMap.getMap());

		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		mv.addObject("menulist", menulist.get("menulist"));





		mv.addObject("BOARD_ID", BOARD_ID);
		mv.addObject("BOARD_NAME", board.get("BO_NAME"));
		mv.addObject("USER_ID", memberVO.getId());
		mv.addObject("NICKNAME", memberVO.getNickname());
		mv.addObject("USER_LEVEL", memberVO.getLevel());
		mv.setViewName("board/update");

		return mv;
	}


	@RequestMapping(value = "/board/insert.do")
	public ModelAndView DoInsert(CommandMap commandMap,HttpServletRequest request) throws Exception {
		//글쓰기
		ModelAndView mv = new ModelAndView();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		commandMap.put("USER_NICKNAME", memberVO.getNickname());
		commandMap.put("USER_ID", memberVO.getId());

		sampleService.insertBoard(commandMap.getMap(), request);
		mv.setViewName("redirect:/board/board.do");
		return mv;
	}


	@RequestMapping(value = "/board/write.do")
	public ModelAndView BoardForm(CommandMap commandMap,@RequestParam(value="BOARD_ID", required=false) String BOARD_ID) throws Exception {
		//글쓰기 폼
		ModelAndView mv = new ModelAndView();
		mv.setViewName("board/write");

		if(BOARD_ID == null || BOARD_ID.length() < 3) {
			BOARD_ID = "main";
		}

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		System.out.println(memberVO.getId());
		commandMap.put("USER_ID", memberVO.getId());
		Map<String,Object> board = sampleService.getBoardName(BOARD_ID); 

		if(Integer.parseInt((String) board.get("BO_LEVEL")) < Integer.parseInt(memberVO.getLevel())) {


			mv.addObject("msg", "접근권한없음");
			mv.addObject("url", "/first/board/board.do");
			mv.setViewName("/redirect");
			return mv;
		}


		Map<String,Object> menulist = sampleService.selectMenuList(commandMap.getMap());


		mv.addObject("menulist", menulist.get("menulist"));
		mv.addObject("NICKNAME", memberVO.getNickname());
		mv.addObject("USER_LEVEL", memberVO.getLevel());
		mv.addObject("BOARD_ID", BOARD_ID);
		mv.addObject("BOARD_NAME", board.get("BO_NAME"));
		return mv;
	}



	@RequestMapping(value="/board/DeleteReply.do")
	public ModelAndView DeleteReply(CommandMap commandMap) throws Exception{
		//ModelAndView mv = new ModelAndView("/sample/replyInsert.do");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		commandMap.put("USER_NICKNAME", memberVO.getNickname());
		commandMap.put("USER_ID", memberVO.getId());

		ModelAndView mv = new ModelAndView("redirect:/board/read.do?IDX="+commandMap.get("IDX"));
		sampleService.deleteReply(commandMap.getMap());
		return mv;
	}

	@RequestMapping(value="/board/UpdateReply.do")
	public ModelAndView UpdateReply(CommandMap commandMap) throws Exception{
		//ModelAndView mv = new ModelAndView("/sample/replyInsert.do");

		ModelAndView mv = new ModelAndView("redirect:/board/read.do?IDX="+commandMap.get("BOARDIDX"));
		sampleService.updateReply(commandMap.getMap());
		return mv;
	}


	@RequestMapping(value="/board/InsertReply.do")
	public ModelAndView InsertReply(CommandMap commandMap) throws Exception{
		//ModelAndView mv = new ModelAndView("/sample/replyInsert.do");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		commandMap.put("USER_NICKNAME", memberVO.getNickname());
		commandMap.put("USER_ID", memberVO.getId());

		ModelAndView mv = new ModelAndView("redirect:/board/read.do?IDX="+commandMap.get("BOARDIDX"));
		sampleService.insertReply(commandMap.getMap());
		return mv;
	}


	@RequestMapping(value = "/board/board.do")
	public ModelAndView BoardList(CommandMap commandMap,@RequestParam(value="BOARD_ID", required=false) String BOARD_ID) throws Exception {

		ModelAndView mv = new ModelAndView();

		if(BOARD_ID == null || BOARD_ID.length() < 3) {
			BOARD_ID = "main";
		}

		if(commandMap.get("SCH_TYPE") != null && commandMap.get("SCH_KEYWORD") != null ) {
			mv.addObject("SCH_TYPE", commandMap.get("SCH_TYPE"));
			mv.addObject("SCH_KEYWORD", commandMap.get("SCH_KEYWORD"));
		}

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;


		commandMap.put("USER_ID", memberVO.getId());
		Map<String,Object> board = sampleService.getBoardName(BOARD_ID); 

		if(Integer.parseInt((String) board.get("BO_LEVEL")) < Integer.parseInt(memberVO.getLevel())) {


			mv.addObject("msg", "접근권한없음");
			mv.addObject("url", "/first/board/board.do");
			mv.setViewName("/redirect");
			return mv;
		}

		Map<String,Object> contentslist = sampleService.selectBoardList(commandMap.getMap());
		Map<String,Object> menulist = sampleService.selectMenuList(commandMap.getMap());


		mv.addObject("paginationInfo", (PaginationInfo)contentslist.get("paginationInfo"));
		mv.addObject("contentslist", contentslist.get("result"));
		mv.addObject("menulist", menulist.get("menulist"));

		mv.addObject("NICKNAME", memberVO.getNickname());
		mv.addObject("USER_LEVEL", memberVO.getLevel());
		mv.addObject("BOARD_ID", BOARD_ID);
		mv.addObject("BOARD_NAME", board.get("BO_NAME"));

		mv.setViewName("board/list");
		return mv;
	}

	@RequestMapping(value = "/board/read.do")
	public ModelAndView BoardRead(CommandMap commandMap,HttpServletRequest request,HttpServletResponse response,@RequestParam(value="BOARD_ID", required=false) String BOARD_ID) throws Exception {

		ModelAndView mv = new ModelAndView();

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User memberVO = (User)principal;

		commandMap.put("USER_ID", memberVO.getId());

		if(BOARD_ID == null || BOARD_ID.length() < 3) {
			BOARD_ID = "main";
		}

		Map<String,Object> board = sampleService.getBoardName(BOARD_ID); 

		if(Integer.parseInt((String) board.get("BO_LEVEL")) < Integer.parseInt(memberVO.getLevel())) {


			mv.addObject("msg", "접근권한없음");
			mv.addObject("url", "/first/board/board.do");
			mv.setViewName("/redirect");
			return mv;
		}

		Map<String,Object> map = sampleService.selectBoardDetail(commandMap.getMap(),request,response);
		Map<String,Object> PageMap = sampleService.ReplyList(commandMap.getMap());
		Map<String,Object> menulist = sampleService.selectMenuList(commandMap.getMap());

		mv.addObject("paginationInfo", (PaginationInfo)PageMap.get("paginationInfo"));
		mv.addObject("replylist", PageMap.get("result"));
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		mv.addObject("menulist", menulist.get("menulist"));



		mv.addObject("NICKNAME", memberVO.getNickname());
		mv.addObject("USER_ID", memberVO.getId());
		mv.addObject("USER_LEVEL", memberVO.getLevel());


		mv.addObject("BOARD_ID", BOARD_ID);
		mv.addObject("BOARD_NAME", board.get("BO_NAME"));

		mv.setViewName("board/read");
		return mv;
	}

	@RequestMapping(value="/sample/testMapArgumentResolver.do")
	public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("");

		if(commandMap.isEmpty() == false){
			Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
			Entry<String,Object> entry = null;
			while(iterator.hasNext()){
				entry = iterator.next();
				log.debug("key : "+entry.getKey()+", value : "+entry.getValue());
			}
		}
		return mv;
	}



}
