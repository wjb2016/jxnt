	window.onload=function onLoginLoaded() {
		if (isPostBack == "False") {
			GetLastUser();
		}
	}
	
	function GetLastUser() {
		var id = "49BAC005-7D5B-4231-8CEA-16939BEACD67";//GUID标识符
		var usr = GetCookie(id);
		if (usr != null) {
			document.getElementById('loginName').value = usr;
		} else {
			document.getElementById('loginName').value = "001";
		}
		GetPwdAndChk();
	}
	//点击登录时触发客户端事件
	
	function SetPwdAndChk() {
		//取用户名
		var usr = document.getElementById('loginName').value;
		//alert(usr);
		//将最后一个用户信息写入到Cookie
		SetLastUser(usr);
		//如果记住密码选项被选中
		//alert(document.getElementById('chkRememberPwd').checked);
		if (document.getElementById('chkRememberPwd').checked == true) {
			//取密码值
			var pwd = document.getElementById('password').value;
			//alert(pwd);
			var expdate = new Date();
			expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));
			//将用户名和密码写入到Cookie
			SetCookie(usr, pwd, expdate);
		} else {
			//如果没有选中记住密码,则立即过期
			ResetCookie();
		}
	}
	
	function SetLastUser(usr) {
		var id = "49BAC005-7D5B-4231-8CEA-16939BEACD67";
		var expdate = new Date();
		//当前时间加上两周的时间
		expdate.setTime(expdate.getTime() + 14 * (24 * 60 * 60 * 1000));
		SetCookie(id, usr, expdate);
	}
	//用户名失去焦点时调用该方法
	
	function GetPwdAndChk() {
		$("#icheckspan").removeClass("yw-checkbox-true");
		var usr = document.getElementById('loginName').value;
		var pwd = GetCookie(usr);
		if (pwd != null) {
			document.getElementById('chkRememberPwd').checked = true;
			document.getElementById('password').value = pwd;
			$("#password").css("display","block");
			$("#password2").css("display","none");
			$("#icheckspan").addClass("yw-checkbox-true");
		} else {
			document.getElementById('chkRememberPwd').checked = false;
			document.getElementById('password').value = "";
			$("#password").css("display","none");
			$("#password2").css("display","block");
		}
	}
	//取Cookie的值
	
	function GetCookie(name) {
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		while (i < clen) {
			var j = i + alen;
			//alert(j);
			if (document.cookie.substring(i, j) == arg) return getCookieVal(j);
			i = document.cookie.indexOf(" ", i) + 1;
			if (i == 0) break;
		}
		return null;
	}
	var isPostBack = "<%= IsPostBack %>";
	
	function getCookieVal(offset) {
		var endstr = document.cookie.indexOf(";", offset);
		if (endstr == -1) endstr = document.cookie.length;
		return unescape(document.cookie.substring(offset, endstr));
	}
	//写入到Cookie
	
	function SetCookie(name, value, expires) {
		var argv = SetCookie.arguments;
		//本例中length = 3
		var argc = SetCookie.arguments.length;
		var expires = (argc > 2) ? argv[2] : null;
		var path = (argc > 3) ? argv[3] : null;
		var domain = (argc > 4) ? argv[4] : null;
		var secure = (argc > 5) ? argv[5] : false;
		document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : "");
	}
	
	function ResetCookie() {
		var usr = document.getElementById('loginName').value;
		var expdate = new Date();
		SetCookie(usr, null, expdate);
	}