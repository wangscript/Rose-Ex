<%@ page contentType="text/html;charset=UTF-8"%><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><c:if test="${!fromRRFenqiClient && !fromRRFenqiNewClient}"><%--
<div class="site-nav clearfix">
<div class="sn-user-wrap clearfix">
<c:if test="${empty requestScope.fenqiUser}"><a class="sn-color-fff sn-float-l" href="http://fenqi.renren.com/user/wap/loginhome" id="sn_skip_url1">登录</a>
<a class="sn-margin-left sn-gray-btn sn-float-l" href="http://fenqi.renren.com/user/wap/reghome" id="sn_skip_url2">快速注册</a>
</c:if><c:if test="${!empty requestScope.fenqiUser}"><c:if test="${empty requestScope.fenqiUser.getName()}"><a class="sn-color-fff sn-float-l" href="http://fenqi.renren.com/center/wap/myLoanVerify">${requestScope.fenqiUser.getMobilePhone()}</a>
</c:if><c:if test="${!empty requestScope.fenqiUser.getName()}"><a class="sn-color-fff sn-float-l" href="http://fenqi.renren.com/center/wap/myLoanVerify">${requestScope.fenqiUser.getName()}</a>
</c:if><a class="sn-margin-left sn-gray-btn sn-float-l" href="http://fenqi.renren.com/user/logout">注销</a></c:if></div><div class="sn-contact clearfix">
<span class="sn-phonenum-wrap">
<i class="icon-mobile"></i>
400-910-5151
</span>
</div>
</div>
--%>
<header class="header-wrap header-border-max-red"><div class="hw-content clearfix">
<h1 class="hw-logo-wrap">
<a class="hw-logo" href="http://fenqi.renren.com">
<img class="hw-logo-pic" src="http://a.xnimg.cn/rrloan/img/logo/wap-logo-141125.png" alt="人人分期" />
</a>
</h1>
<div class="hw-word-wrap">
<img class="hw-word-pic" src="http://a.xnimg.cn/rrloan/img/logo/wap-word-141125.png" alt="人人网官方大学生分期购物平台" />
</div>
</div>
</header>
</c:if>