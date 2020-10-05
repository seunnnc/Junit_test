package kr.hkit.exam09.test;

import java.util.List;

import org.junit.Assert;
//import static org.junit.Assert.*; 해주면 Assert빼줘도 실행됨
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.hkit.exam09.BoardVO;
import kr.hkit.exam09.Query;

class QueryTest {

	@BeforeAll
	//모든 테스트 실행되기 전 딱 한번 실행됨
	static void start() {
		Query.createTable();
	}
	
	@AfterAll
	//모든 테스트 실행되고난 후 딱 한번 실행됨
	static void end() {
		Query.dropTable();
	}
	
	@BeforeEach
	void before() {
		Query.boardDelete(0);
		
		BoardVO bv1 = new BoardVO();
		bv1.setBtitle("타이틀1");
		bv1.setBcontent("내용1");
		Query.boardInsert(bv1);
		
		BoardVO bv2 = new BoardVO();
		bv2.setBtitle("타이틀2");
		bv2.setBcontent("내용2");
		Query.boardInsert(bv2);
	}
	
	@Test
	void testA() {
		List<BoardVO> list = Query.getAllBoardList();
		Assert.assertEquals(2, list.size());
		
		BoardVO vo1 = list.get(0);
		Assert.assertEquals("타이틀1", vo1.getBtitle());
		Assert.assertEquals("내용1", vo1.getBcontent());
		
		BoardVO vo2 = list.get(1);
		Assert.assertEquals("타이틀2", vo2.getBtitle());
		Assert.assertEquals("내용2", vo2.getBcontent());
	}
	
	@Test
	void testB() {
		List<BoardVO> list = Query.getAllBoardList();
		
		BoardVO vo1 = list.get(0);
		Query.boardDelete(vo1.getBid());
		BoardVO vo1Db = Query.getBoardDetail(vo1.getBid());
		Assert.assertEquals(0, vo1Db.getBid());
		Assert.assertNull(vo1Db.getBtitle());
		Assert.assertNull(vo1Db.getBcontent());
		
		Assert.assertEquals(1, Query.getAllBoardList().size());
		
		BoardVO vo2 = list.get(1);
		Query.boardDelete(vo2.getBid());
		BoardVO vo2Db = Query.getBoardDetail(vo2.getBid());
		Assert.assertEquals(0, vo2Db.getBid());
		Assert.assertNull(vo2Db.getBtitle());
		Assert.assertNull(vo2Db.getBcontent());
		
		Assert.assertEquals(0, Query.getAllBoardList().size());
	}
	
	@Test
	void testC() {
		List<BoardVO> list = Query.getAllBoardList();
		
		int bid = list.get(0).getBid(); //1
		BoardVO c = new BoardVO();
		c.setBtitle("타이틀3");
		c.setBcontent("내용3");
		c.setBid(bid);
		Query.boardUpdate(c);
		
		bid =  list.get(1).getBid();	//2
		BoardVO d = new BoardVO();
		d.setBtitle("타이틀4");
		d.setBcontent("내용4");
		d.setBid(bid);
		Query.boardUpdate(d);
		
		BoardVO cResult = Query.getBoardDetail(c.getBid());
		Assert.assertEquals(c.getBtitle(), cResult.getBtitle());
		Assert.assertEquals(c.getBcontent(), cResult.getBcontent());
		
		BoardVO dResult = Query.getBoardDetail(d.getBid());
		Assert.assertEquals(d.getBtitle(), dResult.getBtitle());
		Assert.assertEquals(d.getBcontent(), dResult.getBcontent());
	}

}
