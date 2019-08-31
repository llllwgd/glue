package com.llllwgd.glue.admin.dao;

import com.llllwgd.glue.admin.core.model.Project;

import java.util.List;

public interface IProjectDao {
	
	public int save(Project project);

	public int update(Project project);

	public int delete(int id);

	public List<Project> loadAll();

	public Project findByAppname(String appname);

    public Project load(int id);

}
