package com.tubager.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tubager.dao.BookDao;
import com.tubager.domain.TBook;
import com.tubager.domain.TLocation;
import com.tubager.domain.TParagraph;
import com.tubager.utility.Utility;

@Service
public class BookService {
	@Autowired
	private BookDao bookDao;
	
	private final static Logger logger = LoggerFactory.getLogger(BookService.class);
	
	public String saveBook(TBook book){
		if(book.getUuid() == null){
			logger.info("creating new book because uuid is null");
			return this.createBook(book);
		}
		TBook oldBook = this.read(book.getUuid());
		if(oldBook == null){
			logger.info("creating new book because old book not found");
			return this.createBook(book);
		}
		this.remove(book.getUuid());
		return this.createBook(book);
	}
	
	public String createBook(TBook book){
		if(book.getUuid() == null){
			String uuid = Utility.getUuid();
			book.setUuid(uuid);
		}
		
		Date date = new Date();
		if(book.getDateCreated() == null){
			book.setDateCreated(date);
		}
		if(book.getStartDate() == null){
			book.setStartDate(date);
		}
		if(book.getFinishDate() == null){
			book.setFinishDate(date);
		}
		
		
		List<TParagraph> paraList = book.getParagraphs();
		int idx = 1;
		for(TParagraph para : paraList){
			para.setIndex(idx);
			if(para.getUuid() == null){
				para.setUuid(Utility.getUuid());
			}
			if(para.getDateCreated() == null){
				para.setDateCreated(date);
			}
			para.setBookUuid(book.getUuid());
			TLocation location = para.getLocation();
			if(location.getUuid() == null){
				location.setUuid(Utility.getUuid());
			}
			para.setLocationUuid(location.getUuid());
			idx++;
		}
		String bookUuid = bookDao.create(book);
		return bookUuid;
	}
	
	public String updateBook(TBook newBook, TBook oldBook){
		this.remove(oldBook.getUuid());
		return this.createBook(newBook);
	}
	
	public void remove(String uuid){
		bookDao.remove(uuid);
	}
	
	public TBook read(String uuid){
		return bookDao.read(uuid);
	}
	
	public List<TBook> listTop(){
		return bookDao.listTop();
	}
	
	public List<TBook> list(String user){
		return bookDao.list(user);
	}
	
	public List<TBook> search(String text){
		return bookDao.search(text);
	}
	
	public void updateResource(String userName, String resourceType, String paragraphId, String ids){
		if("image".equalsIgnoreCase(resourceType)){
			bookDao.updateImage(userName, paragraphId, ids);
		}
		else if("video".equalsIgnoreCase(resourceType)){
			bookDao.updateVideo(userName, paragraphId, ids);
		}
		else if("audio".equalsIgnoreCase(resourceType)){
			bookDao.updateAudio(userName, paragraphId, ids);
		}
	}
}
