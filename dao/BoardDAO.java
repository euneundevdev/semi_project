package com.project.aloneBab.board.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.project.aloneBab.board.model.vo.Board;
import com.project.aloneBab.board.model.vo.DivideSearch;
import com.project.aloneBab.board.model.vo.Image;
import com.project.aloneBab.board.model.vo.RandomRecipe;
import com.project.aloneBab.board.model.vo.Recipe;
import com.project.aloneBab.board.model.vo.Reply;
import com.project.aloneBab.common.PageInfo;

@Repository("bDAO")
public class BoardDAO {

	public int getListCount(SqlSessionTemplate sqlSession, String i) {
		return sqlSession.selectOne("recipe-mapper.getListCount", i);
	}

	public ArrayList<Board> selectBoardList(SqlSessionTemplate sqlSession, PageInfo pi, String i) {
		RowBounds rb = new RowBounds((pi.getCurrentPage()-1)*pi.getBoardLimit(), pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("recipe-mapper.selectBoardList", i, rb);
	}

	public ArrayList<Recipe> selectRecipeList(SqlSessionTemplate sqlSession, Integer bId) {
		return (ArrayList)sqlSession.selectList("recipe-mapper.selectRecipeList", bId);
	}

	public ArrayList<Image> selectImageList(SqlSessionTemplate sqlSession, Integer rId) {
		return (ArrayList)sqlSession.selectList("recipe-mapper.selectImageList", rId);
	}

	public int updateCount(SqlSessionTemplate sqlSession, int bId) {
		return sqlSession.update("recipe-mapper.updateCount", bId);
	}
	
	public int updateCountTip(SqlSessionTemplate sqlSession, int bNo) {
		return sqlSession.update("boardMapper.updateCount", bNo);
	}
	
	
	public Board selectBoard(SqlSessionTemplate sqlSession, int bId) {
		return sqlSession.selectOne("recipe-mapper.selectBoard", bId);
	}

	public int insertImage(SqlSessionTemplate sqlSession, ArrayList<Image> iList) {
		return sqlSession.insert("recipe-mapper.insertImage", iList);
	}

	public int deleteBoard(SqlSessionTemplate sqlSession, int bId) {
		return sqlSession.update("recipe-mapper.deleteRecipe", bId);
	}

	public int getDivideCount(SqlSessionTemplate sqlSession, DivideSearch ds) {
		return sqlSession.selectOne("recipe-mapper.getDivideCount", ds);
	}

	public ArrayList<Board> selectDivideBoardList(SqlSessionTemplate sqlSession, PageInfo pi, DivideSearch ds) {
		RowBounds rb = new RowBounds((pi.getCurrentPage()-1)*pi.getBoardLimit(), pi.getBoardLimit());
		return (ArrayList)sqlSession.selectList("recipe-mapper.selectDivideBoardList", ds, rb);
	}

	public int insertBoard(SqlSessionTemplate sqlSession, Board b) {
		return sqlSession.insert("recipe-mapper.insertBoard", b);
	}

	public int insertRecipe(SqlSessionTemplate sqlSession, Recipe recipe) {
		return sqlSession.insert("recipe-mapper.insertRecipe", recipe);
	}
	
	public ArrayList<Board> selectRecommendBoardList(SqlSessionTemplate sqlSession, String nation) {
		return (ArrayList)sqlSession.selectList("recipe-mapper.selectRecommendBoardList", nation);
	}

	public Board selectMyBoard(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.selectOne("boardMapper.selectMyBoard", boardNo);
	}

	public Recipe selectRecipe(SqlSessionTemplate sqlSession, int boardNo) {
		return sqlSession.selectOne("boardMapper.selectRecipe", boardNo);
	}

	public ArrayList<Image> selectImage(SqlSessionTemplate sqlSession, int recipeNo) {
		return (ArrayList)sqlSession.selectList("boardMapper.selectImage", recipeNo);
	}
	
	public ArrayList<RandomRecipe> randomChoice(SqlSessionTemplate sqlSession, HashMap<String, Object> key) {
		
		return (ArrayList)sqlSession.selectList("boardMapper.randomChoice", key);
	}
	
	public Board tipcomment(SqlSessionTemplate sqlSession, int bId) {
		return sqlSession.selectOne("board-Mapper.selectBoard", bId);
	}

	public ArrayList<Reply> selectReply(SqlSessionTemplate sqlSession, int bId) {
		return (ArrayList)sqlSession.selectList("board-Mapper.selectReply", bId);
	}

	public int insertReply(SqlSessionTemplate sqlSession, Reply r) {
		return sqlSession.insert("board-Mapper.insertReply", r);
	}

	public ArrayList<Reply> tipcomment(SqlSessionTemplate sqlSession, PageInfo pi) {
		int offset = (pi.getCurrentPage()-1)*pi.getBoardLimit();
		RowBounds RowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("boardMapper.tipcomment", RowBounds);
	}
	
	public int getTipListCount(SqlSessionTemplate sqlSession, String i) {
		return sqlSession.selectOne("boardMapper.getListCount", i);
	}

	public ArrayList<Board> tipListView(SqlSessionTemplate sqlSession, PageInfo pi, String i) {
		int offset = (pi.getCurrentPage() - 1)*pi.getBoardLimit();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		return (ArrayList)sqlSession.selectList("boardMapper.tipListView", i, rowBounds);
	}

	public int insertTip(SqlSessionTemplate sqlSession, Board b) {
		return sqlSession.insert("boardMapper.insertTip",b);
	}

	public Board selectTip(SqlSessionTemplate sqlSession, int bNo) {
		return sqlSession.selectOne("boardMapper.selectTip", bNo);
	}

	public int updateTip(SqlSessionTemplate sqlSession, Board b) {
		return sqlSession.update("boardMapper.updateTip",b);
	}

	public int deleteTip(SqlSessionTemplate sqlSession, int bNo) {
		return sqlSession.update("boardMapper.deleteTip", bNo);
	}

	public ArrayList<Board> searchTip(SqlSessionTemplate sqlSession, String searchType, String honeyKeyword) {
	    HashMap<String, Object> params = new HashMap<>();
	    params.put("searchType", searchType);
	    params.put("honeyKeyword", honeyKeyword);
	    
	    List<Board> result = sqlSession.selectList("boardMapper.searchTip", params);
	    return new ArrayList<>(result); // List를 ArrayList로 변환
	}



	


}