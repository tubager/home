package com.tubager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tubager.domain.TBook;
import com.tubager.service.BookService;

@RestController
public class BookController {
	@Autowired
	private BookService bookService;

	private final static Logger logger = LoggerFactory.getLogger(BookController.class);
	
	@RequestMapping(value="/book/{uuid}", method=RequestMethod.GET)
	public @ResponseBody TBook getBook(@PathVariable String uuid){
		return bookService.read(uuid);
	}
	
	@RequestMapping(value="/resource/book/{uuid}", method=RequestMethod.GET)
	public @ResponseBody TBook getPublicBook(@PathVariable String uuid){
		return bookService.read(uuid);
	}
	
	@RequestMapping(value="/resource/books", method=RequestMethod.GET)
	public @ResponseBody List<TBook> listTop(){
		return bookService.listTop();
	}
	
	@RequestMapping(value="/resource/searchbooks", method=RequestMethod.GET)
	public @ResponseBody List<TBook> search(@RequestParam("query") String query){
		return bookService.search(query);
	}
}

class UuidData{
	private String uuid;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public UuidData(String uuid){
		this.uuid = uuid;
	}
}
