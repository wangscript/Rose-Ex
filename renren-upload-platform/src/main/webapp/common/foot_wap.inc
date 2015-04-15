<%@ page contentType="text/html;charset=UTF-8"%><div id="J_fieed_side" class="side-menu-fixed"><!-- 选中的a下面增加side-menu-one-select -->
<a class="side-menu-one <c:if test="${wap_bottom_menu == 'home'}">side-menu-one-select</c:if>" href="/?topFlag=0">
<i class="side-menu-icon side-menu-icon-home"></i>
<span class="side-menu-type">首页</span>
</a>
<a class="side-menu-one <c:if test="${wap_bottom_menu == 'category'}">side-menu-one-select</c:if>" href="/goods/wap/goodsCategoryList?topFlag=1">
<i class="side-menu-icon side-menu-icon-type"></i>
<span class="side-menu-type">分类</span>
</a>
<a class="side-menu-one <c:if test="${wap_bottom_menu == 'help'}">side-menu-one-select</c:if>" href="/help/wap/home?topFlag=1">
<i class="side-menu-icon side-menu-icon-help"></i>
<span class="side-menu-type">帮助</span>
</a>
<a class="side-menu-one <c:if test="${wap_bottom_menu == 'mine'}">side-menu-one-select</c:if>" href="/center/wap/mine?topFlag=1">
<i class="side-menu-icon side-menu-icon-my"></i>
<span class="side-menu-type">我的</span>
</a>
</div>
<style>
body {
padding-bottom: 60px;
}
</style>
