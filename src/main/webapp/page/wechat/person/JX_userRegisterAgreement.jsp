<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>"> 
    <meta charset="UTF-8">
    <title>用户注册协议</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
	
	<script src="${pageContext.request.contextPath}/source/bootstrap/js/jquery-1.9.1.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/source/bootstrap/js/common.js"></script>

    <link href="${pageContext.request.contextPath}/source/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/source/bootstrap/css/common.css" type="text/css">

</head>
<style type="text/css">
.rule{
   font-size: 16px;
   margin-left: 10px;
}   
</style>
<body style="font-weight: bold;">
<div class="container bg1">
    <header id="JS_mll_header" class="mll-header borderOnePx">
        <a class="layout-back" onclick="javascript:history.go(-1);">返回</a>
        <div class="layout-middle">
            <div id="JS_header_page_title" class="text">用户注册协议</div>
        </div>
    </header>
   <div>
      <div style="margin-top: 10px;">       
       <span class="rule">1、服务条款确认</span><br>      
       <div style="padding: 0 0 10px 10px;">
	       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	              精欣暖通微信公众号的各项网络电子服务的所有权和运营权归成都市精欣暖通工程有限公司，
	              任何单位和个人非经本公司授权不得以本公司及其所有的网站名义进行商业或非商业活动。
	              精欣暖通微信公众号提供的服务将严格按照本公司及本网发布的服务条款和操作规则执行。
	              用户必须确认所有服务条款并完成注册程序，才能成为精欣暖通微信公众号的正式用户。
	   </div>
      </div>
      <div style="margin-top: 10px;">       
       <span class="rule">2、精欣暖通微信公众号版权声明</span><br>      
       <p style="padding: 0 0 10px 10px;">
	       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	              精欣暖通微信公众号旗下网站(www.cdjxssj.com ,www.cdjxnt.cn及精欣暖通微信公众号的其他网站)
	              上刊载的所有内容，包括但不限于文字报导、图片、声音、录像、图表、标志、标识、广告、商标、商号、域名、软件、程序、版面设计、专栏目录与名称、内容分类标准
	              以及为注册用户提供的任何或所有信息，均受《中华人民共和国著作权法》、《中华人民共和国商标法》、《中华人民共和国专利法》
	              及适用之国际公约中有关著作权、商标权、专利权及其它财产所有权法律的保护，为成都市精欣暖通工程有限公司及其相关权利人专属所有或持有。
	              使用者将精欣暖通微信公众号提供的内容与服务用于非商业用途、非盈利、非广告目的而纯属个人使用时，应遵守著作权法以及其他相关法律的规定，
	              不得侵犯精欣暖通微信公众号及其相关权利人的合法权益。 使用者将精欣暖通微信公众号提供的内容与服务用于商业、盈利、广告性目的时，
	              需征得精欣暖通微信公众号及其相关权利人的书面特别授权，注明作者及文章出处“精欣暖通微信公众号”，
	              并按有关国际公约和中华人民共和国法律的有关规定向相关权利人支付费用。否则权利有将追究有关人员的侵权责任。
	              未经精欣暖通微信公众号的明确书面特别授权，任何人不得变更、发行、播送、转载、复制、重制、改动、散布、表演、展示或
	              利用精欣暖通微信公众号的局部或全部的内容或服务或在非精欣暖通微信公众号所属的服务器上作镜像，否则以侵权论，依法追究法律责任。
	               精欣暖通微信公众号所使用的所有软件归属成都市精欣暖通工程有限公司其及软件提供商所有， 受《中华人民共和国著作权法》及国际版权公约法律保护。
	               除经本网特别说明用作销售或免费下载、使用等目的外，任何其他用途包括但不限于复制、修改、经销、转储、发表、展示、演示以及反向工程均在严格禁止之列，
	               任何单位和个人非法使用均构成对本公司及本网的侵权。
	   </p>
      </div>
       <div style="margin-top: 10px;">       
       <span class="rule">3、服务约定</span><br>      
       <p style="padding: 0 0 10px 10px;">
	       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            精欣暖通微信公众号运用自己的电脑操作系统通过国际互联网络为用户提供网络服务。
	            由于服务栏目和内容的不同，要求用户必须：
	      (1)自行配备上网的所需设备， 包括个人电脑、调制解调器或其他必备上网装置。 
	      (2)自行负担个人上网所支付的与此服务有关的电话费用、 网络费用。 
	      (3)提供详尽、准确的个人资料。 
	      (4)不断更新注册资料，符合及时、详尽、准确的要求。 
	            如果用户提供的资料包含有不正确的信息，精欣暖通微信公众号保留结束用户使用网络服务资格的权利。
	   </p>
      </div>
      <div style="margin-top: 10px;">       
       <span class="rule">4、服务修订</span><br>      
       <p style="padding: 0 0 10px 10px;">
	       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	              精欣暖通微信公众号根据自身的服务内容，以及与用户达成的协议提供服务。
	              用户在享用本网提供的专项服务的同时，同意接受网站提供的各类信息服务。
	              精欣暖通微信公众号有权在必要时修改服务条款，精欣暖通微信公众号服务条款一旦发生变动，
	              将会在重要页面上提示修改内容。如果不同意所改动的内容，
	              用户可以在与本网取得联系后取消获得的网络服务。如果用户继续享用网络服务，
	              则视为接受服务条款的变动。精欣暖通微信公众号保留随时修改或中断服务而不需提前告知用户的权利。
	              精欣暖通微信公众号行使修改或中断服务的权利，不需对用户或第三方负责。
	   </p>
      </div>
      <div style="margin-top: 10px;">       
       <span class="rule">5、注册用户隐私保护</span><br>      
       <p style="padding: 0 0 10px 10px;">
	       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	              用户一旦注册成功，成为精欣暖通微信公众号的合法用户，将得到一个密码和用户名。 
	              由用户对用户名和密码安全负全部责任。
	              另外，每个用户都要对以其用户名进行的所有活动和事件负全责。
	              您可随时根据规则改变您的密码。
	              用户若发现任何非法使用用户帐号或存在安全漏洞的情况，请立即通告精欣暖通微信公众号。 
	              精欣暖通微信公众号尊重用户个人隐私，未经合法用户授权，
	              本网不会在公开、编辑或透露其注册资料及保存在本网的非公开内容，
	              除非有法律许可要求或未经合法用户授权在诚信的基础上认为透露这些信息是必要的： 
			(1)用户授权本网或授权某人通过电子邮件服务透露这些信息。<br> 
			(2)相应的法律及程序要求本网提供用户的个人资料。<br>
			(3)遵守有关法律规定，遵从本网合法服务程序。 <br>
			(4)保持维护本网的商标所有权。<br>
			(5)在紧急情况下竭力维护用户个人和社会大众的隐私安全。 <br>
			(6)符合其他相关的要求。<br>
	   </p>
      </div>
      <div style="margin-top: 10px;">       
       <span class="rule">6、风险承担</span><br>      
       <p style="padding: 0 0 10px 10px;">
	       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	             用户个人对网络服务的使用自行承担风险。精欣暖通微信公众号所提供的所有资料权限于对用户参考，
	             不对用户的商业运作做任何具体性指导，用户应自行承担使用或提供本网信息的商业活动及其风险。
	             精欣暖通微信公众号不保证服务一定能满足用户的要求，也不保证服务不会受中断。
	             本网将尽力保证服务的及时性、准确性、安全性，但对及时性、准确性、安全性等都不作任何具体承诺。
	             对用户在运用精欣暖通微信公众号得到的任何商品购物服务或交易进程，均不作任何担保。
	   </p>
      </div>
      <div style="margin-top: 10px;">       
       <span class="rule">7、免责事由</span><br>      
       <p style="padding: 0 0 10px 10px;">
	       
	           （1）精欣暖通微信公众号对用户在接受服务过程中所受的任何直接、间接的损害不负责任，这些损害可能来自：不正当使用网络服务，在网上购买商品或进行同类型服务，在网上进行交易，非法使用网络服务或用户传送的信息有所变动等。<br>
	           （2）精欣暖通微信公众号不对用户所发布信息的删除或储存失败负责。精欣暖通微信公众号有判定用户的行为是否符合本网服务条款的要求和精神的保留权利，如果用户违背了服务条款的规定，精欣暖通微信公众号有中断对其提供网络服务的权利。<br>
	           （3）对用户自行提供的信息，由用户依法自行承担全部责任。精欣暖通微信公众号对此等信息的准确性、完整性、合法性或真实性均不承担任何责任。 <br>
	           （4）用户在本网论坛所发表的任何意见均属于个人意见，并不代表成都市精欣暖通工程有限公司及精欣暖通微信公众号也持同样的观点。
	   </p>
      </div>
      <div style="margin-top: 10px;">       
       <span class="rule">8、用户承诺</span><br>      
       <p style="padding: 0 0 10px 10px;">
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            用户自行承担发布内容的责任。用户对服务的使用是根据所有适用于精欣暖通微信公众号的国家法律、地方法律和国际法律准则的规定的。 <br>
	            用户必须遵循：<br>
			(1)用户必须提供真实的自我信息，对其在本网站上发布的信息负责全部责任。 <br>
			(2)从中国境内向外传输资料信息时必须符合中国有关法规。<br> 
			(3)使用网络服务不作非法用途。<br>
			(4)不干扰或攻击网络服务。<br>
			(5)遵守所有使用网络服务的网络协议、规定、程序和惯例。 用户须承诺不传输任何非法的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、伤害性的、庸俗的，淫秽等信息资料。另外，用户也不能传输任何教唆他人构成犯罪行为的资料；不能传输涉及国家安全的资料；不能传输任何不符合当地法规、国家法律和国际法律的资料。禁止未经许可而非法进入其它电脑系统。用户需对自己在网上的行为承担法律责任。用户若在精欣暖通微信公众号上散布和传播反动、色情或其他违反国家法律的信息，本网的系统记录有可能作为用户违反法律的证据。精欣暖通微信公众号有权对违法行为作出独立判断并立即取消用户服务。 用户特别承诺：用户一旦在精欣暖通微信公众号网站注册，即以明示或默示的方式同意并允许精欣暖通微信公众号将用户的商号、商标等用于精欣暖通微信公众号信息及开发的衍生产品中。但精欣暖通微信公众号在使用过程中应保证其公正的立场，正确反应客观事实，不得恶意诋毁或损害用户的名誉。
	   </p>
      </div>
      <div style="margin-top: 10px;">       
       <span class="rule">9、服务终止或中止</span><br>      
       <p style="padding: 0 0 10px 10px;">
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            精欣暖通微信公众号和用户可根据合同约定或实际情况终止、中止一项或多项网络服务。
	            精欣暖通微信公众号不需对任何个人或第三方负责而随时中止服务。
	            用户对后来的条款修改有异议，或对本网的服务不满，可以行使如下权利：<br> 
          (1)停止使用本网的网络服务。<br>
          (2)通告本网停止对该用户的服务。 
	            结束用户服务后，用户使用网络服务的权利立即终止或中止。
	            从终止或中止之时，用户没有权利，本网也没有义务传送任何未处理的信息或未完成的服务给用户或第三方。

	   </p>
      </div>
      <div style="margin-top: 10px;">       
       <span class="rule">10、通告形式</span><br>      
       <p style="padding: 0 0 10px 10px;">
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            精欣暖通微信公众号服务条款的修改、服务变更、或其它重要事件发生变动而需要通告时，
	            可根据实际情况选择通过重要页面公告、电子邮件、常规信件等形式进行。
	   </p>
      </div>
      <div style="margin-top: 10px;">       
       <span class="rule">11、法律适用</span><br>      
       <div style="padding: 0 0 10px 10px;">
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           本网网络服务条款与中华人民共和国的法律解释相一致，
	           用户和精欣暖通微信公众号一致同意服从中华人民共和国法律的管辖。
	           如发生精欣暖通微信公众号服务条款与中华人民共和国法律相抵触时，
	           则这些条款将完全按法律规定重新解释，
	           而其它条款则依旧保持对用户产生法律效力和影响。
	   </div>
      </div>
   </div> 
   <jsp:include page="/page/wechat/footer.jsp"></jsp:include>
</div>
</body>
</html>
