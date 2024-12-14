package kr.co.gudi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.co.gudi.dto.BoardDTO;
import kr.co.gudi.service.BoardService;


@RestController
public class BoardController {
	Logger logger = LoggerFactory.getLogger(getClass());
//	@RequestParam(value="/")
//	public 
	
	@Autowired BoardService board_ser;
	
	@PostMapping(value="/list.ajax")
	public Map<String , Object> listDo() {
		List<BoardDTO> list = board_ser.listDo();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return map;
	}
	
	@RequestMapping(value="/write.go")
	public ModelAndView writeGo() {
		return new ModelAndView("write");	
	}
	
	
	@RequestMapping(value="/write.do")
	public ModelAndView writeDo(@RequestParam Map<String, Object> params) {
		int row = board_ser.writeDo(params);
        Object idx = params.get("idx");
		ModelAndView mav = new ModelAndView();
		if (row>0) {
			mav.setViewName("redirect:/detail.do?idx="+idx );
		}else {
			mav.setViewName("write");
		}
		return mav;
	}
	
	@RequestMapping(value="/detail.do")
	public ModelAndView detailDo(int idx) {
		
		
		BoardDTO info = board_ser.detailDo(idx,true);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("detail");
		mav.addObject("info",info);
		return mav;
	}
	
	@RequestMapping(value="/list.go")
	public ModelAndView listGo() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("list");
		return mav;
	}
	
	
	@RequestMapping(value="/detailDel.do")
	public ModelAndView detailDel(int idx) {
		int row = board_ser.boardDel(idx);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:list.go");
		return mav;
	}
	
	@RequestMapping(value="/logout.do")
	public ModelAndView logout(HttpSession session) {
		session.removeAttribute("loginId");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/");
		return mav;
	}
	
	@RequestMapping(value="/update.go")
	public ModelAndView updateGo(int idx) {
		BoardDTO info = board_ser.detailDo(idx,true);
		ModelAndView mav = new ModelAndView();
		mav.addObject("info",info);
		mav.setViewName("update");
		return mav;
	}
	@RequestMapping(value="/update.do")
	public ModelAndView updateDo(@RequestParam Map<String, Object> params) {
		int row = board_ser.updateDo(params);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/detail.do?idx="+params.get("idx") );
		return mav;
	}
	
}



































