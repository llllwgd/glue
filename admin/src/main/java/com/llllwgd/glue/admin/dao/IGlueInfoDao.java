package com.llllwgd.glue.admin.dao;

import com.llllwgd.glue.admin.core.model.GlueInfo;

import java.util.List;

public interface IGlueInfoDao {
	
	public List<GlueInfo> pageList(int offset, int pagesize, int projectId, String name);
	public int pageListCount(int offset, int pagesize, int projectId, String name);
	
	public int delete(int id);
	
	public int save(GlueInfo codeInfo);
	
	public int update(GlueInfo codeInfo);
	
	public GlueInfo load(int id);
	public GlueInfo loadCodeByName(String name);
	
}
