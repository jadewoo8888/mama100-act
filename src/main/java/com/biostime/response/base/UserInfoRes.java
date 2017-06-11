package com.biostime.response.base;

public class UserInfoRes {
	private UserInfoBean response;
	
	public UserInfoBean getResponse() {
		return response;
	}

	public void setResponse(UserInfoBean response) {
		this.response = response;
	}

	public class UserInfoBean{
		private String tsno;
		private String code;
		private String desc;
		private String nickname;
		private String customerId;
		private String cid;
		private String mid;
		public String getTsno() {
			return tsno;
		}
		public void setTsno(String tsno) {
			this.tsno = tsno;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getCustomerId() {
			return customerId;
		}
		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}
		public String getCid() {
			return cid;
		}
		public void setCid(String cid) {
			this.cid = cid;
		}
		public String getMid() {
			return mid;
		}
		public void setMid(String mid) {
			this.mid = mid;
		}
	}
	
}
