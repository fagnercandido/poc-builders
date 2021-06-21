package io.platformbuilders.clientes.boundary.filter;

public class Filter {

	private int page = 0;
	private int size = 10;


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Filter() {
	}

	public Filter(int page, int size) {
		this.page = page;
		this.size = size;
	}
}
