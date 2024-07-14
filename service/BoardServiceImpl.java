package com.project.aloneBab.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.aloneBab.board.model.dao.BoardDAO;
import com.project.aloneBab.board.model.vo.Board;
import com.project.aloneBab.board.model.vo.DivideSearch;
import com.project.aloneBab.board.model.vo.Image;
import com.project.aloneBab.board.model.vo.RandomRecipe;
import com.project.aloneBab.board.model.vo.Recipe;
import com.project.aloneBab.board.model.vo.Reply;
import com.project.aloneBab.common.PageInfo;


@Service("bService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private BoardDAO bDAO;

	@Override
	public int getListCount(String i) {
		return bDAO.getListCount(sqlSession, i);
	}

	@Override
	public ArrayList<Board> selectBoardList(PageInfo pi, String i) {
		return bDAO.selectBoardList(sqlSession, pi, i);
	}

	@Override
	public ArrayList<Recipe> selectRecipeList(Integer bId) {
		return bDAO.selectRecipeList(sqlSession, bId);
	}

	@Override
	public ArrayList<Image> selectImageList(Integer rId) {
		return bDAO.selectImageList(sqlSession, rId);
	}

	@Override
	public Board selectBoard(int bId, String id) {
		Board b = bDAO.selectBoard(sqlSession, bId);
		if(b != null) {
			if(id != null && !b.getWriter().equals(id)) {// 로그인 유저가 있으며, 작성자와 로그인유저가 같다면
				int result = bDAO.updateCount(sqlSession, bId);
				if(result>0) {
					b.setBoardCount(b.getBoardCount()+1);
				}
			}
		}
		return b;
	}

	@Override
	public int insertImage(ArrayList<Image> iList) {
		return bDAO.insertImage(sqlSession, iList);
	}

	@Override
	public int deleteBoard(int bId) {
		return bDAO.deleteBoard(sqlSession, bId);
	}

	@Override
	public int getDivideCount(DivideSearch ds) {
		return bDAO.getDivideCount(sqlSession, ds);
	}

	@Override
	public ArrayList<Board> selectDivideBoardList(PageInfo pi, DivideSearch ds) {
		return bDAO.selectDivideBoardList(sqlSession,pi,ds);
	}

	@Override
	public int insertBoard(Board b) {
		return bDAO.insertBoard(sqlSession, b);
	}

	@Override
	public int insertRecipe(Recipe recipe) {
		return bDAO.insertRecipe(sqlSession, recipe);
	}

	@Override
	public ArrayList<Board> selectRecommendBoardList(String nation) {
		return bDAO.selectRecommendBoardList(sqlSession, nation);
	}

//	@Override
//	public ArrayList<Recipe> selectDivideRecipeList(DivideSearch ds) {
//		return bDAO.selectDivideRecipeList(sqlSession,ds);
//	}
//
//	@Override
//	public ArrayList<Image> selectDivdeImageList(DivideSearch ds) {
//		return bDAO.selectDivdeImageList(sqlSession,ds);
//	}

	@Override
	public Board selectMyBoard(int boardNo) {
		return bDAO.selectMyBoard(sqlSession, boardNo);
	}
	
	@Override
	public Recipe selectRecipe(int boardNo) {
		return bDAO.selectRecipe(sqlSession, boardNo);
	}
	
	@Override
	public ArrayList<Image> selectImage(int recipeNo) {
		return bDAO.selectImage(sqlSession, recipeNo);
	}
	
	@Override
	public ArrayList<RandomRecipe> randomChoice(HashMap<String, Object> key) {
		// TODO Auto-generated method stub
		return bDAO.randomChoice(sqlSession, key);
	}
	
	@Override
	public Board tipcomment(int bId, String id) {
		Board b = bDAO.tipcomment(sqlSession, bId);
		if(b != null) {
			if(id != null && !b.getWriter().equals(id)) {
				int result = bDAO.updateCount(sqlSession, bId);
				if(result > 0) {
					b.setBoardCount(b.getBoardCount() + 1);
				}
			}
		}
		return null;
	}

	@Override
	public ArrayList<Reply> selectReply(int bId) {
		return bDAO.selectReply(sqlSession, bId);
	}

	@Override
	public int insertReply(Reply r) {
		return bDAO.insertReply(sqlSession, r);
	}

	@Override
	public ArrayList<Reply> tipcomment(PageInfo pi) {
		return bDAO.tipcomment(sqlSession, pi);
	}

	//	@Override
//	public ArrayList<Recipe> selectDivideRecipeList(DivideSearch ds) {
//		return bDAO.selectDivideRecipeList(sqlSession,ds);
//	}
//
//	@Override
//	public ArrayList<Image> selectDivdeImageList(DivideSearch ds) {
//		return bDAO.selectDivdeImageList(sqlSession,ds);
//	}
	
	@Override
	public int getTipListCount(String i) {
		return bDAO.getTipListCount(sqlSession, i);
	}
	
	@Override
	public ArrayList<Board> tipListView(PageInfo pi, String string) {
		return bDAO.tipListView(sqlSession, pi, string);
	}

	@Override
	public int insertTip(Board b) {
		return bDAO.insertTip(sqlSession, b);
	}

	@Override
	public Board selectTip(int bNo, String id) {
		Board b = bDAO.selectTip(sqlSession, bNo);
		if(b != null) {
			if(id != null && !b.getWriter().equals(id)) {
				int result = bDAO.updateCountTip(sqlSession, bNo);
				if(result> 0){
					b.setBoardCount(b.getBoardCount()+1);
				}
			}
		}
		return b;
	}
	
	@Override
	public int updateTip(Board b) {
		return bDAO.updateTip(sqlSession, b);
	}

	@Override
	public int deleteTip(int bNo) {
		return bDAO.deleteTip(sqlSession, bNo);
	}

	@Override
	public ArrayList<Board> searchTip(String searchType, String honeyKeyword) {
		return bDAO.searchTip(sqlSession, searchType, honeyKeyword);
	}


	    }
	
