<%@ page contentType="text/html;charset=UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><div class="site-nav"><div class="sn-content clearfix">
<p class="sn-known sn-word">人人网官方唯一指定大学生分期购物平台</p>
<div class="sn-contact clearfix">
<span class="sn-phonenum-wrap">
<i class="icon-mobile"></i>
400-910-5151
</span>
</div>
<ul class="sn-menu sn-word clearfix">
<c:if test="${empty requestScope.fenqiUser}"><c:if test="${empty rrName}"><li class="sn-menu-item">
<a class="sn-menu-col sn-word" href="http://fenqi.renren.com/user/loginhome" id="sn_skip_url1">登录</a>
</li>
<li class="sn-menu-item">
<a class="sn-word" href="http://fenqi.renren.com/user/reghome" id="sn_skip_url2">快速注册</a>
</li>
</c:if><c:if test="${not empty rrName}"><li class="sn-menu-item">
<a class="sn-menu-col sn-word" href="#">${rrName}</a>
</li>
</c:if></c:if><c:if test="${!empty requestScope.fenqiUser}"><li class="sn-menu-item">
<c:if test="${empty requestScope.fenqiUser.getName()}"><a class="sn-menu-col sn-word" href="http://fenqi.renren.com/center/myLoanVerify">${requestScope.fenqiUser.getMobilePhone()}</a>
</c:if><c:if test="${!empty requestScope.fenqiUser.getName()}"><a class="sn-menu-col sn-word" href="http://fenqi.renren.com/center/myLoanVerify">${requestScope.fenqiUser.getName()}</a>
</c:if></li><li class="sn-menu-item">
<a class="sn-word" href="http://fenqi.renren.com/user/logout">注销</a>
</li>
</c:if><li class="sn-menu-item sn-menu-item-last"><a class="sn-word" href="http://fenqi.renren.com/help/aboutus" target="_blank">帮助中心</a>
</li>
</ul>
</div>
</div>
<%-- header标签中，class值:
header-border-min-red  1px红底边
header-border-max-red  3px红底边
header-border-max-blue 3px蓝底边
--%>
<header class="header-wrap header-border-max-red"><div class="hw-content clearfix">
<h1 class="hw-logo-wrap">
<a class="hw-logo clearfix" href="http://fenqi.renren.com">
<img class="hw-logo-pic" src="http://a.xnimg.cn/rrloan/img/logo/logo3.png" />
<img class="hw-logo-word" src="http://s.xnimg.cn/rrloan/modules/payCenter/img/title-payCenter.png"  />
</a>
</h1>
<c:if test="${!empty requestScope.fenqiUser}"><a class="hw-myloan" href="http://fenqi.renren.com/center/myLoanVerify">我的分期</a>
</c:if><a class="hw-flow-new" href="/help/approve" target="_blank"></a></div>
</header>
