package cn.zzuisa.utils;

import java.util.List;

public class PagedResult<T> {
	private int page; // 当前页数
	private int total;// 总页数
	private long records;// 总记录数
	private List<T> rows;// 每行显示的内容

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
