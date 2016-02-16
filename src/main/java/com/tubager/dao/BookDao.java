package com.tubager.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tubager.domain.TBook;
import com.tubager.domain.TLocation;
import com.tubager.domain.TParagraph;

@Repository
public class BookDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final static Logger logger = LoggerFactory.getLogger(BookDao.class);
	
	public String create(TBook book){
		String insertSql="insert into book values(?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(insertSql, new Object[]{book.getUuid(),
													book.getTitle(),
													book.getDescription(),
													book.getCoverImg(),
													book.getUserName(),
													book.getStatus(),
													getDate(book.getDateCreated()),
													getDate(book.getStartDate()),
													getDate(book.getFinishDate())});
		logger.info("book created");
		
		List<TParagraph> paraList = book.getParagraphs();
		insertSql="insert into paragraph values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TParagraph p = paraList.get(i);
				ps.setString(1, p.getUuid());
				ps.setString(2, p.getTitle());
				ps.setInt(3, p.getIndex());
				ps.setString(4, p.getBookUuid());
				ps.setString(5, p.getText());
				ps.setString(6, p.getImg());
				ps.setString(7, p.getImgText());
				ps.setString(8, p.getVideo());
				ps.setString(9, p.getAudio());
				ps.setString(10, p.getLocationUuid());
				ps.setString(11, p.getTimeStamp());
				ps.setDate(12, getDate(p.getDateCreated()));
				ps.setString(13, p.getUserName());
			}
					
			@Override
			public int getBatchSize() {
				return paraList.size();
			}
		  });
		logger.info("paragraph created");
		
		insertSql="insert into location values(?,?,?,?,?,?)";
		jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TParagraph p = paraList.get(i);
				TLocation l = p.getLocation();
				ps.setString(1, l.getUuid());
				ps.setDouble(2, l.getLatitude());
				ps.setDouble(3, l.getLongitude());
				ps.setString(4, l.getName());
				ps.setString(5, l.getCountry());
				ps.setString(6, l.getCity());
			}
					
			@Override
			public int getBatchSize() {
				return paraList.size();
			}
		  });
		logger.info("location created");
		
		return book.getUuid();
	}
	
	private java.sql.Date getDate(java.util.Date date){
		return new java.sql.Date(date.getTime());
	}
	
	public void update(TBook book, TBook oldBook){
		
	}
	
	public void updateImage(String userName, String paragraphId, String ids){
		String sql = "update paragraph set img = ? where user_name = ? and uuid = ?";
		int ret = jdbcTemplate.update(sql, new Object[]{ids, userName, paragraphId});
		System.out.println(ret);
	}
	
	public void updateAudio(String userName, String paragraphId, String ids){
		String sql = "update paragraph set audio = ? where user_name = ? and uuid = ?";
		jdbcTemplate.update(sql, new Object[]{ids, userName, paragraphId});
	}
	
	public void updateVideo(String userName, String paragraphId, String ids){
		String sql = "update paragraph set video = ? where user_name = ? and uuid = ?";
		jdbcTemplate.update(sql, new Object[]{ids, userName, paragraphId});
	}
	
	public void remove(String bookUuid){
		logger.info("removing book " + bookUuid);
		TBook book = this.read(bookUuid);
		if(book == null){
			return;
		}
		
		String sql = "delete from book where uuid = ?";
		jdbcTemplate.update(sql, new Object[]{bookUuid});
		logger.info("book deleted ");
		
		sql = "delete from paragraph where book_uuid = ?";
		jdbcTemplate.update(sql, new Object[]{bookUuid});
		logger.info("paragraph deleted ");
		
		List<TParagraph> paraList = book.getParagraphs();
		if(paraList == null || paraList.size() == 0){
			return;
		}
		for(TParagraph p : paraList){
			sql = "delete from location where uuid = ?";
			jdbcTemplate.update(sql, new Object[]{p.getLocationUuid()});
		}
		logger.info("location deleted ");
	}
	
	public List<TBook> listTop(){
		List<TBook> books = jdbcTemplate.query("select * from book order by date_created desc limit 5", new Object[] {},
				(rs, rowNum) ->{
					TBook b = new TBook();
					b.setUuid(rs.getString("uuid"));
					b.setTitle(rs.getString("title"));
					b.setDescription(rs.getString("description"));
					b.setCoverImg(rs.getString("cover_img"));
					b.setUserName(rs.getString("user_name"));
					b.setStatus(rs.getString("status"));
					b.setDateCreated(rs.getDate("date_created"));
					b.setStartDate(rs.getDate("start_date"));
					b.setFinishDate(rs.getDate("finish_date"));
					return b;
				});
		
		return books;
	}
	
	public List<TBook> search(String text){
		String param = "%"+text+"%";
		List<TBook> books = jdbcTemplate.query("select * from book where title like ? or description like ?", new Object[] {param,param},
				(rs, rowNum) ->{
					TBook b = new TBook();
					b.setUuid(rs.getString("uuid"));
					b.setTitle(rs.getString("title"));
					b.setDescription(rs.getString("description"));
					b.setCoverImg(rs.getString("cover_img"));
					b.setUserName(rs.getString("user_name"));
					b.setStatus(rs.getString("status"));
					b.setDateCreated(rs.getDate("date_created"));
					b.setStartDate(rs.getDate("start_date"));
					b.setFinishDate(rs.getDate("finish_date"));
					return b;
				});
		
		return books;
	}
	
	public List<TBook> list(String user){
		List<TBook> books = jdbcTemplate.query("select * from book where user_name = ?", new Object[] {user},
				(rs, rowNum) ->{
					TBook b = new TBook();
					b.setUuid(rs.getString("uuid"));
					b.setTitle(rs.getString("title"));
					b.setDescription(rs.getString("description"));
					b.setCoverImg(rs.getString("cover_img"));
					b.setUserName(rs.getString("user_name"));
					b.setStatus(rs.getString("status"));
					b.setDateCreated(rs.getDate("date_created"));
					b.setStartDate(rs.getDate("start_date"));
					b.setFinishDate(rs.getDate("finish_date"));
					return b;
				});
		
		return books;
	}
	
	public TBook read(String bookId){
		List<TParagraph> paras = jdbcTemplate.query("select * from paragraph where book_uuid = ? order by `index` asc", new Object[] {bookId},
				(rs, rowNum) ->{
					TParagraph graph = new TParagraph();
					graph.setText(rs.getString("text"));
					graph.setIndex(rs.getInt("index"));
					graph.setUuid(rs.getString("uuid"));
					graph.setTitle(rs.getString("title"));
					graph.setBookUuid(bookId);
					graph.setImg(rs.getString("img"));
					graph.setImgText(rs.getString("img_text"));
					graph.setAudio(rs.getString("audio"));
					graph.setVideo(rs.getString("video"));
					graph.setLocationUuid(rs.getString("location_uuid"));
					graph.setTimeStamp(rs.getString("timestamp"));
					graph.setUserName(rs.getString("user_name"));
					graph.setDateCreated(rs.getDate("date_created"));
					return graph;
				});
		if(paras != null && paras.size()>0){
			for(TParagraph p : paras){
				List<TLocation> locations = jdbcTemplate.query("select * from location where uuid = ?", new Object[] {p.getLocationUuid()},
						(rs, rowNum) ->{
							TLocation b = new TLocation();
							b.setUuid(rs.getString("uuid"));
							b.setLatitude(rs.getDouble("latitude"));
							b.setLongitude(rs.getDouble("longitude"));
							b.setName(rs.getString("name"));
							b.setCountry(rs.getString("country"));
							b.setCity(rs.getString("city"));
							return b;
						});
				TLocation location = null;
				if(locations != null && locations.size() > 0){
					location = locations.get(0);
				}
				p.setLocation(location);
			}
		}
		TBook book = null;
		List<TBook> books = jdbcTemplate.query("select * from book where uuid = ?", new Object[] {bookId},
				(rs, rowNum) ->{
					TBook b = new TBook();
					b.setUuid(rs.getString("uuid"));
					b.setTitle(rs.getString("title"));
					b.setDescription(rs.getString("description"));
					b.setCoverImg(rs.getString("cover_img"));
					b.setUserName(rs.getString("user_name"));
					b.setStatus(rs.getString("status"));
					b.setDateCreated(rs.getDate("date_created"));
					b.setStartDate(rs.getDate("start_date"));
					b.setFinishDate(rs.getDate("finish_date"));
					return b;
				});
		if(books != null && books.size() > 0){
			book = books.get(0);
		}
		if(book != null){
			book.setParagraphs(paras);
			logger.info("book found for " + bookId);
			return book;
		}
		logger.info("book not found for " + bookId);
		return null;
	}
}
