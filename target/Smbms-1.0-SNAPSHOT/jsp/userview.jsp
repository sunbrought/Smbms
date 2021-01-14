<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@include file="/jsp/common/head.jsp"%>
 <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>用户管理页面 >> 用户信息查看页面</span>
        </div>
        <div class="providerView">
            <p><strong>用户编号：</strong><span>${user.userCode }</span></p>
            <p><strong>用户名称：</strong><span>${user.userName }</span></p>
            <p><strong>用户性别：</strong>
            	<span>
            		<c:if test="${user.gender == 1 }">男</c:if>
					<c:if test="${user.gender == 2 }">女</c:if>
				</span>
			</p>
            <p><strong>出生日期：</strong>
            <span><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></span></p>
            <p><strong>用户电话：</strong><span>${user.phone }</span></p>
            <p><strong>用户地址：</strong><span>${user.address }</span></p>
            <p><strong>用户角色：</strong><span>${user.userRoleName}</span></p>
            <p><strong>证件照：</strong><span><img alt="证件照" src="${pageContext.request.contextPath}/statics/images/${user.idPicPath}" height="150px" width="150px"></span></p>
            <p><strong>工作证照片：</strong><span><img alt="工作证照片" src="${pageContext.request.contextPath }/statics/images/${user.workPicPath}"  height="150px" width="150px"></span></p>
			<div class="providerAddBtn">
            	<input type="button" id="back" name="back" value="返回" >
            </div>
        </div>
    </div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/userview.js"></script>