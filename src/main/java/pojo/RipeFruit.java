package pojo;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;

public class RipeFruit {

	public static Type getType() {
		return new TypeToken<List<RipeFruit>>() {
		}.getType();
	};

	private String title;
	// Easier to store the page size here
	private String page_size;
	private String unitPrice;
	private String description;

	public RipeFruit(Object[] fruitData) {
		setTitle((String) fruitData[0]);
		setPageSize(((int) fruitData[1] / 1024) + "kb");
		setUnitPrice((String) fruitData[2]);
		setDescription((String) fruitData[3]);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPageSize() {
		return page_size;
	}

	public void setPageSize(String page_size) {
		this.page_size = page_size;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unit_price) {
		this.unitPrice = unit_price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
