package com.tubager.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.tubager.exception.FileNotFound;
import com.tubager.service.BookService;
import com.tubager.service.FileService;

@RestController
public class FileController {
	
	@Autowired
	private FileService fileService;
	@Autowired
	private BookService bookService;
	
	@RequestMapping(value="/resource/file/{id}", method=RequestMethod.GET )
	public void getById(@PathVariable String id, HttpServletResponse response) throws IOException{
		OutputStream stream = response.getOutputStream();
		GridFSDBFile file = fileService.getById(id);
		DBObject meta = file.getMetaData();
		response.setHeader("Content-Type", meta.get("Content-Type").toString());
		file.writeTo(stream);
	}
	
	@RequestMapping(value="/resource/filebymeta", method=RequestMethod.GET )
	public @ResponseBody void getByMeta(@RequestParam("username") String username,
										@RequestParam("paragraph") String paragraph, 
										@RequestParam("filename") String filename, 
										HttpServletResponse response) throws IOException{
		OutputStream stream = response.getOutputStream();
		GridFSDBFile file =  fileService.getByUserAndParagraphAndFilename(username, paragraph, filename);
		DBObject meta = file.getMetaData();
		response.setHeader("Content-Type", meta.get("Content-Type").toString());
		file.writeTo(stream);
	}
	
	@RequestMapping(value="/resource/filebyuser", method=RequestMethod.GET )
	public @ResponseBody List<DBObject> listByUser(@RequestParam("username") String username){
		return fileService.getByUser(username);
	}
	
	@RequestMapping(value="/resource/filebyname", method=RequestMethod.GET )
	public @ResponseBody void getByName(@RequestParam("name") String filename,HttpServletResponse response) throws Exception{
		OutputStream stream = response.getOutputStream();
		System.out.println(filename);
		GridFSDBFile file =  fileService.getByFilename(filename);
		if(file == null){
			throw new FileNotFound(filename + " not found");
		}
		DBObject meta = file.getMetaData();
		response.setHeader("Content-Type", meta.get("Content-Type").toString());
		file.writeTo(stream);
	}
	
}
