// 꿀팁 게시글 목록 가져오기+검색
	@RequestMapping("tip.tip")
	public String tipListView(@RequestParam(value = "page", defaultValue = "1") int currentPage,
	                          @RequestParam(value = "searchType", required = false) String searchType,
	                          @RequestParam(value = "honeyKeyword", required = false) String honeyKeyword, Model model) {
		
	    // 검색 조건 HashMap에 추가
	    HashMap<String, String> map = new HashMap<String, String>();
	    map.put("searchType", searchType);
	    map.put("honeyKeyword", honeyKeyword);
	    // 서치타입과 키워드에 해당하는 게시글 수 카운트
	    
	    int listCount = bService.getListCount("꿀팁");
	    
	    PageInfo pi = Pagination.getPageInfo(currentPage, listCount, 10);

	    // 검색 결과 리스트 가져오기
	    ArrayList<Board> list = bService.tipListView(pi, map);

	    if (list != null) {
	        model.addAttribute("list", list);
	        model.addAttribute("pi", pi);
	        model.addAttribute("searchType", searchType);
	        model.addAttribute("honeyKeyword", honeyKeyword);

	        return "tipList";
	    } else {
	        throw new AllException("게시글 목록 가져오기에 실패했습니다.");
	    }
	}


	// 꿀팁 작성 insert
	@RequestMapping("insert.tip")
	public String insertTip(@ModelAttribute Board b, HttpSession session) {
		String writer = ((Member) session.getAttribute("loginUser")).getId();
		b.setWriter(writer);
		b.setBoardGenre("꿀팁");

		int result = bService.insertTip(b);

		if (result > 0) {
			return "redirect:tip.tip";
		} else {
			throw new AllException("게시글 작성 실패했어요.");
		}
	}

	// 꿀팁 게시판 글 하나 선택, 댓글, 검색
	@RequestMapping("detail.tip")
	public String selectTip(@RequestParam("bNo") int bNo, @RequestParam("page") int page, HttpSession session,
			Model model) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		String id = null;
		if (loginUser != null) {
			id = loginUser.getId();
		}
		Board board = bService.selectTip(bNo, id);
		ArrayList<Reply> rpList = bService.rpList(bNo);

		if (board != null) {
			model.addAttribute("b", board);
			model.addAttribute("page", page);
			model.addAttribute("rpList", rpList);
			return "tipContent";
		} else {
			throw new AllException("게시글 상세보기 실패.");
		}
	}

	// 꿀팁 작성 페이지 이동
	@RequestMapping("write.tip")
	public String tipWriteView() {
		return "tipWrite";
	}

	// 꿅수정폼
	@RequestMapping("updateForm.tip")
	public String updateForm(@RequestParam("bNo") int bNo, @RequestParam("page") int page, Model model) {
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

		if (result > 0) {
			model.addAttribute("bNo", b.getBoardNo());
			model.addAttribute("page", page);
			return "redirect:detail.tip";

		} else {
			throw new AllException("게시글 수정을 실패했어요.");
		}
	}

	// 꿀팁 삭제
	@RequestMapping("delete.tip")
	public String deleteTip(@RequestParam("bNo") int bNo) {
		int result = bService.deleteTip(bNo);
		if (result > 0) {
			return "redirect:tip.tip";
		} else {
			throw new AllException("게시글 삭제를 실패했습니다.");
		}

	}
