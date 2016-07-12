package com.tubager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {
	
	private static Map<String, List<String>> companyMap = new HashMap<String, List<String>>();
	private static Map<String, Map<String, Integer>> companyGroupMap = new HashMap<String, Map<String, Integer>>();
	private static Map<String, Map<Integer, String>> companyGroupNameMap = new HashMap<String, Map<Integer, String>>();
	private static Map<String, Map<Integer, Double>> companyGroupScoreMap = new HashMap<String, Map<Integer, Double>>();
	private static Map<String, Integer> companyIndexMap = new HashMap<String, Integer>();
	private static Map<String, List<String>> companyPrizeMap = new HashMap<String, List<String>>();
	private static Map<String, Integer> companyGroupCountMap = new HashMap<String, Integer>();
	private static List<GroupLog> logs = new ArrayList<GroupLog>();
	
	@RequestMapping(value="/admin/group/init/{company}", method=RequestMethod.GET )
	public int init(@PathVariable String company){
		if(company == null || "".equalsIgnoreCase(company)){
			return -1;
		}
		if(companyMap.get(company) != null){
			return 1;
		}
		companyMap.put(company, new ArrayList<String>());
		companyGroupMap.put(company, new HashMap<String, Integer>());
		companyGroupNameMap.put(company, new HashMap<Integer, String>());
		companyGroupScoreMap.put(company, new HashMap<Integer, Double>());
		companyIndexMap.put(company, 0);
		companyPrizeMap.put(company, new ArrayList<String>());
		companyGroupCountMap.put(company, 0);
		
		return 0;
	}
	
	@RequestMapping(value="/admin/group/exist/{company}", method=RequestMethod.GET )
	public int checkExist(@PathVariable String company){
		if(company == null || "".equalsIgnoreCase(company)){
			return -1;
		}
		if(companyMap.get(company) != null){
			return 1;
		}
		return 0;
	}
	
	@RequestMapping(value="/admin/group/users/{company}", method=RequestMethod.POST )
	public void setUsers(@PathVariable String company, @RequestBody UserData userData){
		this.clearCompanyData(company);
		String user = userData.getUsers();
		System.out.println(user);
		String[] users = user.split(",");
		List<String> list = companyMap.get(company);
		list.clear();
		for(String u : users){
			list.add(u);
		}
		companyGroupCountMap.put(company, userData.getGroupCount());
		List<String> prizeList = companyPrizeMap.get(company);
		int count = userData.getPrizeCount();
		if(count > list.size()){
			count = list.size();
		}
		Random ra =new Random();
		while(count > 0){
			int num = ra.nextInt(list.size());
			if(num >= 0 && num < list.size()){
				String u = list.get(num);
				if(prizeList.indexOf(u) < 0){
					prizeList.add(u);
					count--;
				}
			}
		}
		
	}

	@RequestMapping(value="/admin/group/clear/{company}", method=RequestMethod.GET )
	public void clearMap(@PathVariable String company){
		this.clearCompanyData(company);
	}
	
	private void clearCompanyData(String company){
		Map<String, Integer> m = companyGroupMap.get(company);
		m.clear();
		Map<Integer, String> mName = companyGroupNameMap.get(company);
		mName.clear();
		Map<Integer, Double> mScore = companyGroupScoreMap.get(company);
		if(mScore != null){
			mScore.clear();
		}
		List<String> list = companyPrizeMap.get(company);
		list.clear();
		companyIndexMap.put(company, 0);
		companyGroupCountMap.put(company, 1);
	}
	
	@RequestMapping(value="/admin/group/remove/{company}", method=RequestMethod.GET )
	public void removeCompany(@PathVariable String company){
		companyMap.remove(company);
		companyGroupMap.remove(company);
		companyGroupNameMap.remove(company);
		companyGroupScoreMap.remove(company);
		companyIndexMap.remove(company);
		companyPrizeMap.remove(company);
		companyGroupCountMap.remove(company);
	}

	@RequestMapping(value="/group/get/{company}", method=RequestMethod.GET )
	public GroupData getGroup(@PathVariable String company, @RequestParam("name")String name){
		Map<String, Integer> map = companyGroupMap.get(company);
		Map<Integer, String> groupNameMap = companyGroupNameMap.get(company);
		System.out.println(name);
		int idx = companyIndexMap.get(company);
		if(map.get(name) == null){
			List<String> nameList = companyMap.get(company);
			if(!nameList.contains(name)){
				return null;
			}
			int groupCount = companyGroupCountMap.get(company);
			int no = (idx % groupCount)+1;
			map.put(name, no);
			if(!groupNameMap.containsKey(no)){
				groupNameMap.put(no, "ตฺ"+no+"ื้");
			}
			idx++;
			companyIndexMap.put(company, idx);
		}
		List<String> prizes = companyPrizeMap.get(company);
		GroupData group = new GroupData();
		group.setGroup(map.get(name));
		if(prizes.indexOf(name)>=0){
			group.setPrize(true);
		}
		else{
			group.setPrize(false);
		}
		return group;
	}

	@RequestMapping(value="/group/scorelog/{company}", method=RequestMethod.GET )
	public List<GroupLog> getLog(@PathVariable String company, @RequestParam("group")int group){
		List<GroupLog> logList = new ArrayList<GroupLog>();
		for(GroupLog log : logs){
			if(log.getCompany().equals(company) && log.getGroup() == group){
				logList.add(log);
			}
		}
		return logList;
	}

	@RequestMapping(value="/admin/group/list/{company}", method=RequestMethod.GET )
	public Map<String, Integer> list(@PathVariable String company){
		Map<String, Integer> map = companyGroupMap.get(company);
		return map;
	}
	
	@RequestMapping(value="/admin/group/groupname/{company}", method=RequestMethod.GET )
	public Map<Integer, String> groupNameList(@PathVariable String company){
		Map<Integer, String> map = companyGroupNameMap.get(company);
		return map;
	}

	@RequestMapping(value="/group/groupscore/{company}", method=RequestMethod.GET )
	public Map<Integer, Double> groupScoreList(@PathVariable String company){
		Map<Integer, Double> map = companyGroupScoreMap.get(company);
		return map;
	}
	
	@RequestMapping(value="/admin/group/groupname/{company}", method=RequestMethod.POST )
	public void setGroupName(@PathVariable String company, @RequestBody GroupData group){
		Map<Integer, String> map = companyGroupNameMap.get(company);
		if(map != null){
			map.put(group.getGroup(), group.getName());
		}
	}
	
	@RequestMapping(value="/admin/group/groupscore/{company}", method=RequestMethod.POST )
	public void changeGroupScore(@PathVariable String company, @RequestBody GroupLog group){
		Map<Integer, Double> map = companyGroupScoreMap.get(company);
		double score = 0;
		if(map.containsKey(group.getGroup())){
			score = map.get(group.getGroup());
		}
		score += group.getScore();
		map.put(group.getGroup(), score);
		logs.add(group);
	}
	
	@RequestMapping(value="/admin/group/listprize/{company}", method=RequestMethod.GET )
	public List<String> listPrize(@PathVariable String company){
		List<String> list = companyPrizeMap.get(company);
		Map<String, Integer> group = companyGroupMap.get(company);
		Set<String> set = group.keySet();
		List<String> result = new ArrayList<String>();
		for(String name : list){
			if(set.contains(name)){
				result.add(name);
			}
		}
		return result;
	}
}

class GroupLog{
	private String company;
	private int group;
	private double score;
	private String log;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
}

class GroupData{
	private int group;
	private boolean prize;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public boolean isPrize() {
		return prize;
	}
	public void setPrize(boolean prize) {
		this.prize = prize;
	}
}

class UserData{
	private String users;
	private int prizeCount = 0;
	private int groupCount = 1;

	public int getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}

	public int getPrizeCount() {
		return prizeCount;
	}

	public void setPrizeCount(int prizeCount) {
		this.prizeCount = prizeCount;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}
}
