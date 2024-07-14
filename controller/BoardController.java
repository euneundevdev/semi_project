package com.project.aloneBab.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.aloneBab.board.model.service.BoardService;
import com.project.aloneBab.board.model.vo.Board;
import com.project.aloneBab.board.model.vo.DivideSearch;
import com.project.aloneBab.board.model.vo.Image;
import com.project.aloneBab.board.model.vo.RandomRecipe;
import com.project.aloneBab.board.model.vo.Recipe;
import com.project.aloneBab.board.model.vo.Reply;
import com.project.aloneBab.common.AllException;
import com.project.aloneBab.common.PageInfo;
import com.project.aloneBab.common.Pagination;
import com.project.aloneBab.member.model.vo.Member;

@Controller
public class BoardController {

	@Autowired
	private BoardService bService;
	
//댓글 적기	
//c:forEach items="${rpList}" var="rl"
//댓글 list 추가하기 
// 	
	@RequestMapping("search.tip")
	public String searchTip(
	    @RequestParam(value = "page", defaultValue = "1") int currentPage,
	    @RequestParam(value = "searchType") String searchType,
	    @RequestParam(value = "honeyKeyword") String honeyKeyword,
	    Model model
	) {
	    // 게시판 분류 '꿀팁'에 대한 게시물 수 가져오기
	    int listCount = bService.getTipListCount("꿀팁");

	    // 검색 결과 가져오기
	    ArrayList<Board> result = bService.searchTip(searchType, honeyKeyword);
	    
	    // 페이지 정보 설정
	    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
	    
	    // 검색 결과에 따라 페이지네이션이 적용된 게시물 리스트 가져오기
	    ArrayList<Board> list = bService.tipListView(pi, "꿀팁");

	    // 모델에 데이터 추가
	    if (!result.isEmpty()) {
	        model.addAttribute("list", list);
	        model.addAttribute("result", result);
	        model.addAttribute("searchType", searchType);
	        model.addAttribute("honeyKeyword", honeyKeyword);
	        model.addAttribute("pi", pi); // 페이지 정보 추가

	        return "tipSearch"; // 해당 뷰로 이동    
	    } else {
	        model.addAttribute("msg", "검색 결과가 없습니다.");
	        return "tipSearch"; // 뷰 이름이 동일하더라도 메시지를 전달
	    }
	}

	
	// 꿀팁 목록 페이지 이동
	@RequestMapping("tip.tip")
	public String tipListView(@RequestParam(value="page", defaultValue="1")int currentPage, Model model) {
		
		// 게시글 불러오고 숫자 셈
		int listCount = bService.getListCount("꿀팁");
		
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);
		
		ArrayList<Board> list = bService.tipListView(pi, "꿀팁");
		
		if(list!=null) {
			model.addAttribute("list", list);
			model.addAttribute("pi",pi);
		
			return "tipList";
		} else {
			throw new AllException("게시글 조회 실패.");
		}
	}
	
	// 꿀팁 작성 insert
	@RequestMapping("insert.tip")
	public String insertTip (@ModelAttribute Board b, HttpSession session) {
		String writer = ((Member)session.getAttribute("loginUser")).getId();
		b.setWriter(writer);
		b.setBoardGenre("꿀팁");
		
		int result = bService.insertTip(b);

		if(result >0) {
			return "redirect:tip.tip";
		}else {
			throw new AllException("게시글 작성 실패했어요.");		
		}
	}
	
	//꿀팁 게시판 글 하나 선택
	@RequestMapping("detail.tip")
	public String selectTip(@RequestParam("bNo")int bNo, @RequestParam("page")int page, HttpSession session, Model model) {
		Member loginUser = (Member)session.getAttribute("loginUser");
		String id = null;
		if(loginUser != null) {
			id = loginUser.getId();
		}
		Board board = bService.selectTip(bNo, id);
		
		if(board != null) {
			model.addAttribute("b", board);
			model.addAttribute("page", page);
			return "tipContent";
		}else {
			throw new AllException("게시글 상세보기 실패.");		
		}
	}
	
	// 꿀팁 작성 페이지 이동
	@RequestMapping("write.tip")
	public String tipWriteView() {
		return "tipWrite";
	}
	
	//꿅수정폼
	@RequestMapping("updateForm.tip")
	public String updateForm(@RequestParam("bNo")int bNo, @RequestParam("page") int page, Model model) {
		Board b = bService.selectTip(bNo, null);
		model.addAttribute("b", b);
		model.addAttribute("page", page); 
		return "tipEdit";
	}
	
	
	// 꿀팁 수정
	@RequestMapping("update.tip")
	public String updateTip(@ModelAttribute Board b, @RequestParam("page") int page, Model model) {

		b.setBoardGenre("꿀팁");
	    
		int result = bService.updateTip(b);
		
		if(result>0) {
			model.addAttribute("bNo", b.getBoardNo());
			model.addAttribute("page", page);
			return "redirect:detail.tip"; 
			
		} else {
			throw new AllException("게시글 수정을 실패했어요.");
		}
	}
	
	// 꿀팁 삭제
	@RequestMapping("delete.tip")
	public String deleteTip(@RequestParam("bNo")int bNo) {
		int result = bService.deleteTip(bNo);
		if(result>0) {
			return "redirect:tip.tip";
		}else {
			throw new AllException("게시글 삭제를 실패했습니다.");
		}
		
	}
	
	
	
	// 레시피 목록 페이지 이동
	@RequestMapping("recipe.re")
	public String recipeListView(@RequestParam(value="page", defaultValue="1") int currentPage, Model model) {
		int listCount = bService.getListCount("레시피");
		System.out.println(listCount);
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 12);
		
		ArrayList<Board> bList = bService.selectBoardList(pi, "레시피");
//		System.out.println(bList);
		ArrayList<Recipe> rList = bService.selectRecipeList(null);
		// null 넘기면 : 전체 조회(레시피 다가져옴)
//		System.out.println(rList);
		ArrayList<Image> iList = bService.selectImageList(null);
		// null 넘기면 : 전체 조회(썸네일[image_level = 0] 가져옴)
//		System.out.println(iList);
		
		// 아직 테스트는 안해봄. 이론상 됨
		
		if(bList != null) {
			model.addAttribute("pi", pi);
			model.addAttribute("bList", bList);
			model.addAttribute("rList", rList);
			model.addAttribute("iList", iList);
			return "recipeList";
		} else {
			return "recipeList";
		}
	}
	
	// 레시피 분류한것들 조회
	@RequestMapping("bunryuRecipe.re")
	public String bunryuRecipe(@ModelAttribute DivideSearch ds, Model model, @RequestParam("searchWord") String find) {
		if(find.trim() == null && ds.getNation().equals("all") && ds.getDifficulty().equals("all")) { // 아무것도 안하고 검색 눌렀음
			return "recipeList"; // 돌려보냄
		}else { // 무언가 검색어를 썼거나, 나라를 선택했거나, 난이도를 선택했음 
			int listCount1 = bService.getDivideCount(ds); // 단어, 나라, 난이도로 분류한 갯수
			PageInfo pi = Pagination.getPageInfo(1, listCount1, 12);
			
			ArrayList<Board> bList = bService.selectDivideBoardList(pi,ds); // 페이지인포랑 분류한거로 보드리스트 가져오기
//			ArrayList<Recipe> rList = bService.selectDivideRecipeList(ds);
			ArrayList<Recipe> rList = bService.selectRecipeList(null); // 리스트화면에서 보드넘버랑 비교해서 할거라서 걍 다 가져옴
//			ArrayList<Image> iList = bService.selectDivdeImageList(ds);  
			// bId = recipeNo 랑 recipeNo = recipeNo 끼리 맞출꺼라 다 가져 와도 될듯함			
			ArrayList<Image> iList = bService.selectImageList(null); // 리스트 화면에서 레시피넘버랑 비교해서 할거라 다 가져옴
			
			String nothing = null;
			
			if(bList != null) {
				model.addAttribute("pi", pi);
				model.addAttribute("bList", bList);
				model.addAttribute("rList", rList);
				model.addAttribute("iList", iList);
				return "recipeList";
			}else { // 보드리스트가 비었다 = 분류에 맞는게 하나도 없다 = 띄울게 없다
				model.addAttribute("nothing", nothing);
				return "recipeList";
			}
		}
	}
	
	
	// 레시피 상세 보기
	@RequestMapping("recipeContent.re")
	public String recipeContentView(@RequestParam("bNo") int bNo, @RequestParam("page") int page, @RequestParam("rNo") int rNo,  HttpSession session, Model model) {
		System.out.println("레시피 상세보기");
		Member loginUser = (Member)session.getAttribute("loginUser");
		String id = null;
		if(loginUser != null) {
			id = loginUser.getId();
			System.out.println("로그인 유저 있음");
		}
		Board b = bService.selectBoard(bNo, id);// bId 는 보드넘버, id 는 로그인유저 아이디
		ArrayList<Recipe> r = bService.selectRecipeList((Integer)bNo);// 선택한 레시피 가져오기
		ArrayList<Image> iList = bService.selectImageList((Integer)rNo);// 레시피번호로 이미지들 가져오기
		
		if(b != null) {
			String[] contents = b.getContent().split("§§●");
			System.out.println(contents[0]);
			ArrayList<Board> bList = bService.selectRecommendBoardList(r.get(0).getNation());
			ArrayList<Recipe> rList = bService.selectRecipeList(null);
			ArrayList<Image> iListAll = bService.selectImageList(null);
			
			model.addAttribute("b",b);
			model.addAttribute("r",r);
			model.addAttribute("iList", iList);
			model.addAttribute("contents", contents); // model addattribute 이거 하나 더 했떠니 오류 생김 + 110줄에 String[] 배열 만들고서 애드함
			model.addAttribute("rList", rList);
			model.addAttribute("iListAll", iListAll);
			model.addAttribute("bList", bList);
			
			return "recipeContent";
		} else {
			return "에러페이지";
		}
	}
	
	// 레시피 작성 페이지 이동
	@RequestMapping("write.re")
	public String recipeWriteView() {
		return "recipeWrite";
	}
	
	// 레시피 작성 ( 보드, 레시피 저장 가능 )
	@RequestMapping("insertRecipe.re")
	public String insertRecipe(@RequestParam("title") String title, @RequestParam("content") String content, @ModelAttribute Recipe recipe, @RequestParam("file") ArrayList<MultipartFile> files, HttpServletRequest req) {
		String id = ((Member)req.getSession().getAttribute("loginUser")).getId();
		Board b = new Board();
		b.setWriter(id);
		b.setBoardGenre("레시피");
		b.setTitle(title);
		b.setContent(content);
		
		System.out.println(content);
		
		ArrayList<Image> iList = new ArrayList<Image>();
		for(int i = 0; i< files.size(); i++) {
			MultipartFile upload = files.get(i);
			if(upload.getOriginalFilename() != "") {
				String[] returnArr = saveFile(upload, req);
				
				Image img = new Image();
				img.setImageName(returnArr[1]);
				img.setImageURL(returnArr[0]);
				iList.add(img);
			} else {
				System.out.println("보컨 136"); // 뭔가 에러 아무튼 돌려보내면 됨
			}
		}
		
		for(int i=0; i<iList.size(); i++) {
			Image img = iList.get(i);
			if(i==0) {
				img.setTitleImg(0);
			} else {
				img.setTitleImg(1);
			}
			System.out.println("이미지 수 : "+i);
		}
		
		int result1 = 0;
		int result2 = 0;
		System.out.println(iList);
		if(iList.isEmpty()) {
			// 이미지가 없다는 뜻
			// 이미지 안넣냐고 물어보기
			System.out.println("이미지 리스트 없음");
		} else {
			System.out.println("insertBoard 전");
			result1 = bService.insertBoard(b); // 게시판에 넣는 서비스 성공시 1
			System.out.println("161 : "+result1);
			if(result1>0) {
				int rResult = bService.insertRecipe(recipe);
				System.out.println("164 : "+rResult);
				if(rResult>0) {
					for(Image i : iList) {
						i.setRecipeNo(recipe.getRecipeNo());
					}
					result2 = bService.insertImage(iList); // 성공시 이미지 갯수만큼 n
					System.out.println("170 : "+result2);
				}else {1
					return "에러페이지";
				}
			}else {
				return "에러페이지";
			}
		} // if(iList.isEmpty()) 문 탈출
		if(result1 + result2 == 1 + iList.size()) { // 잘 들어갔다는 뜻
//			if(result2 == 0) { // 이미지가 없다는 뜻, 우린 이미지 없으면 작성 불가능
//				return "redirect:";
//			} else {
//			}
			
			return "redirect:recipe.re";
		} else { // 에러가 나서 레시피 작성에 실패한 경우
			for(Image i : iList) {
				deleteFile(i.getImageName(), req);
			}
			return "redirect:index.jsp";
		}
	}
	
	// 이미지 저장 메소드
	public String[] saveFile(MultipartFile upload, HttpServletRequest req) {
		String savePath = req.getSession().getServletContext().getRealPath("resources") + "\\image";
		System.out.println(savePath);
		
		File folder = new File(savePath);
		if(!folder.exists()) folder.mkdirs(); // image 폴더 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		int randomNo = (int)(Math.random()*100000);
		String originalFileName = upload.getOriginalFilename();
		String renameFileName = sdf.format(new Date()) + randomNo + originalFileName.substring(originalFileName.lastIndexOf("."));
		
		String renamePath = folder + "\\" + renameFileName;
		try {
			upload.transferTo(new File(renamePath));
		} catch (Exception e) {
			System.out.println("파일 전송 실패" + e.getMessage());
		}
		String[] returnArr = new String[2];
		returnArr[0] = savePath;
		returnArr[1] = renameFileName;
		
		return returnArr;
	}
	
	// 글 쓰다가 에러가 났을 경우 입력하던 이미지 삭제
	public void deleteFile(String fileName, HttpServletRequest req) {
		String savePath = req.getSession().getServletContext().getRealPath("resources") + "\\image";
		
		File f = new File(savePath + "\\" + fileName);
		if(f.exists()) {
			f.delete();
		}
	}
	
	@RequestMapping("updateRecipe.re")
	public String updateRecipe(@RequestParam("bNumber") int bNo, @RequestParam("rNumber") int rNo, Model model) {
		Board b = bService.selectBoard(bNo, null);// bId 는 보드넘버, id 는 로그인유저 아이디
		ArrayList<Recipe> r = bService.selectRecipeList((Integer)bNo);// 선택한 레시피 가져오기
		ArrayList<Image> iList = bService.selectImageList((Integer)rNo);// 레시피번호로 이미지들 가져오기
		
		if(b != null) {
			String[] contents = b.getContent().split("§§●");
			
			model.addAttribute("b",b);
			model.addAttribute("r",r);
			model.addAttribute("iList", iList);
			model.addAttribute("contents", contents);
			
			return "editRecipe";
		} else {
			return "에러페이지";
		}
	}
	
	@RequestMapping("editRecipe.re")
	public String editRecipe(@RequestParam("title") String title, @RequestParam("content") String content, @ModelAttribute Recipe recipe, @RequestParam("file") ArrayList<MultipartFile> files, HttpServletRequest req) {
		String id = ((Member)req.getSession().getAttribute("loginUser")).getId();
		Board b = new Board();
		b.setWriter(id);
		b.setBoardGenre("레시피");
		b.setTitle(title);
		b.setContent(content);
		return null;
	}	
	@RequestMapping("deleteRecipe.re")
	public String delete(@RequestParam("bNumber") int bId) {
		int bResult = bService.deleteBoard(bId);
		if(bResult > 0) {
			return "redirect:recipe.re";
		} else {
			return "에러페이지";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("random.re")
	public String randomView() {
		
		return "randomMenu";
	}

	@RequestMapping("randomChoice.re")
	public String randomChoice(@RequestParam("nation") String[] nation, @RequestParam("difficulty") String[] difficulty, Model model) {
				
		HashMap<String, Object> key = new HashMap<String, Object>();
		key.put("nation", nation);
		key.put("difficulty", difficulty);
						
		ArrayList<RandomRecipe> ra = bService.randomChoice(key);
		
		Random random = new Random();			
		int num = random.nextInt(ra.size() + 1);	
			
		RandomRecipe randomRecipe = ra.get(num);	
			
		model.addAttribute(randomRecipe);
						
		return "randomMenu";
	}
	
	@RequestMapping("randomContent.re")
	public String randomContent() {
		
		return "randomMenu";
	}
	
	@RequestMapping("tipcomment.tip")
	public String tipcomment(@RequestParam(value="Page", defaultValue="1") int currentPage, Model model) {		

		
		int listCount = bService.getListCount(null);		

		PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 5);
		ArrayList<Reply> tipcomment = bService.tipcomment(pi);
		
		if(tipcomment != null) {
			model.addAttribute("pi", pi);
			model.addAttribute("tipcomment", tipcomment);
			return "tipcomment";
		}else {
			throw new AllException();
		}
		
		
	}
	@RequestMapping(value="insertReply.bo", produces="application/json; charset-UTF-8")
	@ResponseBody
	public String insertReply(@ModelAttribute Reply r) {
		int result = bService.insertReply(r);
//		ArrayList<Reply> list = bService.selectReply(r.getRefBoardId());
//		
//		JSONArray array = new JSONArray();
//		for(Reply reply : list) {
//			JSONObject json = new JSONObject(); // {key:value}
//			json.put("replyId", reply.getReplyNo());
//			json.put("replyContent", reply.getReplyContent());
//			json.put("refBoardId", reply.getRefBoardId());
//			json.put("replyWriter", reply.getReplyWriter());
//			json.put("nameNick", reply.getNickName());
//			json.put("replyCreateDate", reply.getReplyCreateDate());
//			json.put("replyModifyDate", reply.getReplyModifyDate());
//			json.put("replyStatus", reply.getReplyStatus());
//			
//			array.put(json);
//		}
		
		
		
		
//		return array.toString();
		return null;
	}
	
	// 내 글 화면에서 게시글로 넘어가는 과정
	@RequestMapping("selectMyBoard.bo")
	public String selectMyBoard(@RequestParam("boardNo") int boardNo,
								@RequestParam("page") int page,
								HttpSession session,
								Model model) {
		Board board = bService.selectMyBoard(boardNo);

		String genre = board.getBoardGenre();
		
		model.addAttribute("myPage", "Y");
		
		if(genre.equals("레시피")) {
			Recipe recipe = bService.selectRecipe(board.getBoardNo());
			ArrayList<Image> iList = bService.selectImage(recipe.getRecipeNo());
			
			model.addAttribute("board", board);
			model.addAttribute("recipe", recipe);
			model.addAttribute("iList", iList);
			model.addAttribute("page", page);
			
			return "recipeContent";	// redirect로 상세조회 페이지 url넣기
		} else {
			
			model.addAttribute("page", page);
			
			if(genre.equals("꿀팁")) {
				model.addAttribute("board", board);
				return "tipContent"; // redirect로 상세조회 페이지 url넣기
			} else {
				model.addAttribute("boardNo", board.getBoardNo());
				
				
				return "redirect:noticeSelect.no";
			}
		}
		
	}
	
	
}