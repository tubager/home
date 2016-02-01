package com.tubager.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Service
public class FileService {
	
	@Autowired
	GridFsTemplate gridFsTemplate;
	
	public String storeProfileImg(String fileName, MultipartFile file, DBObject metaData) throws IOException{
		Query query = new Query();
        query.addCriteria(Criteria.where("metadata.user").is(metaData.get("user").toString()));
        query.addCriteria(Criteria.where("metadata.for").is("profile"));
        GridFSDBFile old = gridFsTemplate.findOne(query);
        if(old != null){
        	this.deleteById(old.getId().toString());
        }
        InputStream inputStream = file.getInputStream();
		String contentType = metaData.get("Content-Type").toString();//"image/jpeg";
		
		GridFSFile gridFsFile = this.gridFsTemplate.store(inputStream, fileName, contentType, metaData);
		return gridFsFile.getId().toString();
	}
	
	public GridFSDBFile getProfileImg(String userName) {
		Query query = new Query();
        query.addCriteria(Criteria.where("metadata.user").is(userName));
        query.addCriteria(Criteria.where("metadata.for").is("profile"));
		GridFSDBFile file = this.gridFsTemplate.findOne(query);
		return file;
	}
	
	public String storeFile(String fileName, MultipartFile file, DBObject metaData) throws IOException{
		GridFSFile _file = this.getByUserAndParagraphAndFilename(metaData.get("user").toString(), metaData.get("paragraphid").toString(), fileName);
		if(_file != null){
			System.out.println(_file.getFilename());
			return _file.getId().toString();
		}
		InputStream inputStream = file.getInputStream();
		String contentType = metaData.get("Content-Type").toString();//"image/jpeg";
		
		GridFSFile gridFsFile = this.gridFsTemplate.store(inputStream, fileName, contentType, metaData);
		return gridFsFile.getId().toString();
	}
	
	public String storeTemplate(String fileName, MultipartFile file, DBObject metaData) throws IOException{
		GridFSFile _file = this.getByFilename(fileName);
		if(_file != null){
			this.gridFsTemplate.delete(new Query(Criteria.where("filename").is(fileName)));
		}
		InputStream inputStream = file.getInputStream();
		String contentType = metaData.get("Content-Type").toString();//"image/jpeg";
		
		GridFSFile gridFsFile = this.gridFsTemplate.store(inputStream, fileName, contentType, metaData);
		return gridFsFile.getId().toString();
	}
	
	public GridFSDBFile getById(String id) {
		GridFSDBFile file = this.gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
		return file;
	}
	
	public GridFSDBFile getByFilename(String fileName) {
		GridFSDBFile file = gridFsTemplate.findOne(new Query(Criteria.where("filename").is(fileName)));
		return file;
	}
	
	public GridFSDBFile getByUserAndParagraphAndFilename(String user, String paragraphid, String filename){
		//BasicDBObject query = new BasicDBObject("metadata.target_field", "abcdefg");
		//List<GridFSDBFile> files = gridFsTemplate.findOne(query);
		Query query = new Query();
        query.addCriteria(Criteria.where("filename").is(filename));
        query.addCriteria(Criteria.where("metadata.user").is(user));
        query.addCriteria(Criteria.where("metadata.paragraphid").is(paragraphid));
        //query.limit(1);
        query.with(new Sort(Sort.Direction.DESC, "uploadDate"));
        List<GridFSDBFile> list = gridFsTemplate.find(query);
        System.out.println(list.size() + " files found by user " + user + " and paragraphid " + paragraphid + " and filename " + filename);
        if(list.size() == 0){
        	return null;
        }
        return list.get(0);
	}
	
	public List<DBObject> getByUser(String user){
		Query query = new Query();
        query.addCriteria(Criteria.where("metadata.user").is(user));
        //query.limit(1);
        query.with(new Sort(Sort.Direction.DESC, "uploadDate"));
        List<GridFSDBFile> list = gridFsTemplate.find(query);
        List<DBObject> objects = new ArrayList<DBObject>();
        for(GridFSDBFile file : list){
        	objects.add(file.getMetaData());
        }
        System.out.println(list.size() + " files found by user " + user);
        return objects;
	}
	
	public void deleteById(String id){
		this.gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
	}
	
	public void deleteByArticle(String id){
		this.gridFsTemplate.delete(new Query(Criteria.where("articleId").is(id)));
	}
	
	
}
