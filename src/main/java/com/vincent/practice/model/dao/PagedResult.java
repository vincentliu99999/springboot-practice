package com.vincent.practice.model.dao;

import java.util.List;
import java.util.Map;
import io.swagger.annotations.ApiModelProperty;

public class PagedResult<T> {

	@ApiModelProperty(notes = "總筆數")
	private Integer total;
	
	@ApiModelProperty(notes = "分頁-起始值")
	private Integer start;
	
	@ApiModelProperty(notes = "分頁-指標值 [上一頁]")
	private String preCursor;
	
	@ApiModelProperty(notes = "分頁-指標值 [下一頁]")
	private String cursor;
	
	@ApiModelProperty(notes = "類別統計值")
	private Map<String,Object> facets;
	
	@ApiModelProperty(notes = "列表資料")
	private List<T> data;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public Map<String, Object> getFacets() {
		return facets;
	}

	public void setFacets(Map<String, Object> facets) {
		this.facets = facets;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getPreCursor() {
		return preCursor;
	}

	public void setPreCursor(String preCursor) {
		this.preCursor = preCursor;
	}
	
	
}
