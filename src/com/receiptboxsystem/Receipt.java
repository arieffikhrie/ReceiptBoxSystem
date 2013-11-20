package com.receiptboxsystem;

public class Receipt {
	private int id;
	private String desc;
	private String location;
	private String amount;
	private String date;
	private String remark;
	private String imgUrl;
	
	public Receipt(){
	}
	
	public Receipt(String desc, String location, String amount,
			String date, String remark, String imgUrl) {
		super();
		this.desc = desc;
		this.location = location;
		this.amount = amount;
		this.date = date;
		this.remark = remark;
		this.imgUrl = imgUrl;
	}

	@Override
	public String toString() {
		return "Receipt [id=" + id + ", desc=" + desc + ", location="
				+ location + ", amount=" + amount + ", date=" + date
				+ ", remark=" + remark + ", imgUrl=" + imgUrl + "]";
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
