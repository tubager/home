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
	private static Map<String, Integer> companyIndexMap = new HashMap<String, Integer>();
	private static Map<String, List<String>> companyPrizeMap = new HashMap<String, List<String>>();
	private static Map<String, Integer> companyGroupCountMap = new HashMap<String, Integer>();
	
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
		companyIndexMap.put(company, 0);
		companyPrizeMap.put(company, new ArrayList<String>());
		companyGroupCountMap.put(company, 0);
		
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
		List<String> list = companyPrizeMap.get(company);
		list.clear();
		companyIndexMap.put(company, 0);
		companyGroupCountMap.put(company, 1);
	}
	
	@RequestMapping(value="/admin/group/remove/{company}", method=RequestMethod.GET )
	public void removeCompany(@PathVariable String company){
		companyMap.remove(company);
		companyGroupMap.remove(company);
		companyIndexMap.remove(company);
		companyPrizeMap.remove(company);
		companyGroupCountMap.remove(company);
	}

	@RequestMapping(value="/group/get/{company}", method=RequestMethod.GET )
	public GroupData getGroup(@PathVariable String company, @RequestParam("name")String name){
		Map<String, Integer> map = companyGroupMap.get(company);
		System.out.println(name);
		int idx = companyIndexMap.get(company);
		if(map.get(name) == null){
			List<String> nameList = companyMap.get(company);
			if(!nameList.contains(name)){
				return null;
			}
			int groupCount = companyGroupCountMap.get(company);
			map.put(name, (idx % groupCount)+1);
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

	@RequestMapping(value="/admin/group/list/{company}", method=RequestMethod.GET )
	public Map<String, Integer> list(@PathVariable String company){
		Map<String, Integer> map = companyGroupMap.get(company);
		return map;
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

class GroupData{
	private int group;
	private boolean prize;
	
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
