package com.jxlt.stage.model;

import java.util.Date;
import java.util.List;

import com.jxlt.stage.common.utils.Page;
import com.jxlt.stage.model.Function;

public class User extends Page{
    private Integer id;

    private String name;
    
    private Integer sex;
    
    private Date birth;

    private String mobile;

    private String address;

    private Integer refUser;

    private String share;

    private Integer utype;

    private Integer grade;

    private Integer flag;
    
    private String psd;
    //新密码
    private String psd1;
    //验证新密码
    private String psd2;
    //类型名称
	private String utypeName;
	//性别名称
	private String sexs;
    
    // 短信验证码
    private String sendCode;
    
    // 第一次设置的密码
    private String firstPsd;
    
    // 页面生日
    private String birthday;
    
    private int selectedMainMemu;
    
    private int selectedChildMenu;
    
    private List<Function> childMenuList;
    //类型查询
    private Integer searchUtype;
    
	public int getSelectedMainMemu() {
		return selectedMainMemu;
	}

	public void setSelectedMainMemu(int selectedMainMemu) {
		this.selectedMainMemu = selectedMainMemu;
	}

	public int getSelectedChildMenu() {
		return selectedChildMenu;
	}

	public void setSelectedChildMenu(int selectedChildMenu) {
		this.selectedChildMenu = selectedChildMenu;
	}

	public List<Function> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<Function> childMenuList) {
		this.childMenuList = childMenuList;
	}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRefUser() {
        return refUser;
    }

    public void setRefUser(Integer refUser) {
        this.refUser = refUser;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public Integer getUtype() {
        return utype;
    }

    public void setUtype(Integer utype) {
        this.utype = utype;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

	public String getPsd() {
		// TODO Auto-generated method stub
		return psd;
	}
	
	public void setPsd(String psd) {
        this.psd = psd;
    }

	public String getSendCode() {
		return sendCode;
	}

	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}

	public String getFirstPsd() {
		return firstPsd;
	}

	public void setFirstPsd(String firstPsd) {
		this.firstPsd = firstPsd;
	}
	public String getUtypeName() {
		if(utype == 1){
			utypeName = "普通用户";
		}else if(utype == 10){
			utypeName = "管理员";
		}else if(utype == 11){
			utypeName = "员工";
		}else if(utype == 12){
			utypeName = "项目经理";
		}else if(utype == 13){
			utypeName = "超级管理员";
		}else if(utype == 20){
			utypeName = "设计师";
		}else{
			utypeName = "未知";
		}
		return utypeName;
	}

	public void setUtypeName(String utypeName) {
		this.utypeName = utypeName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;

	}

	public String getPsd1() {
		return psd1;
	}

	public void setPsd1(String psd1) {
		this.psd1 = psd1;
	}

	public String getPsd2() {
		return psd2;
	}

	public void setPsd2(String psd2) {
		this.psd2 = psd2;
	}

	public String getSexs() {
		if(sex == 1){
			sexs = "男";
		}else if(sex == 2){
			sexs = "女";
		}else{
			sexs = "未知";
		}
		return sexs;
	}

	public void setSexs(String sexs) {
		this.sexs = sexs;
	}

	public Integer getSearchUtype() {
		return searchUtype;
	}

	public void setSearchUtype(Integer searchUtype) {
		this.searchUtype = searchUtype;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	
}
