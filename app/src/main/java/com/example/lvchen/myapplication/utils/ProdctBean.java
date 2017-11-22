package com.example.lvchen.myapplication.utils;

/**
 * Created by lvchen on 2017/10/24.
 */

public class ProdctBean {
	String name;
	int url;

	public ProdctBean(String name, int url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUrl() {
		return url;
	}

	public void setUrl(int url) {
		this.url = url;
	}
}
