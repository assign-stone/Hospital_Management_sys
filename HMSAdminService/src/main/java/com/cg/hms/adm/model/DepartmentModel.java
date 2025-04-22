package com.cg.hms.adm.model;

public class DepartmentModel {
	
	 private int id;
	    private String name;
	    private String desc;

	    private int userId;
	    public DepartmentModel() {
			super();
			// TODO Auto-generated constructor stub
		}

	  

		public DepartmentModel(int id, String name, String desc, int userId) {
			this.id = id;
			this.name = name;
			this.desc = desc;
			this.userId = userId;
		}



		public int getUserId() {
			return userId;
		}



		public void setUserId(int userId) {
			this.userId = userId;
		}



		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

}
