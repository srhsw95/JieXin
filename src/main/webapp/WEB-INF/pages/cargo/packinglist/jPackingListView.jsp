<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   浏览装箱单
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">编号：</td>
	            <td class="tableContent">${id}</td>
	       
	            <td class="columnTitle">卖方：</td>
	            <td class="tableContent">${seller}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">买方：</td>
	            <td class="tableContent">${buyer}</td>
	      
	            <td class="columnTitle">发票号：</td>
	            <td class="tableContent">${invoiceNo}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">发票日期：</td>
	            <td class="tableContent">
	           		 <fmt:formatDate value="${invoiceDate}" pattern="yyyy-MM-dd"/>
	            </td>
	        
	            <td class="columnTitle">唛头：</td>
	            <td class="tableContent">${marks}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">描述：</td>
	            <td class="tableContent">${descriptions}</td>
	      
	            <td class="columnTitle">状态：</td>
	            <td class="tableContent">
	               <c:if test="${state==0}">草稿</c:if>
	               <c:if test="${state==1}">已提交</c:if>
	               <c:if test="${state==2}">已委托</c:if>
	               <c:if test="${state==3}">已催款</c:if>
	               <c:if test="${state==4}">已上报</c:if>
				</td>
	        </tr>	
	       
		</table>
	</div>
 
 
</form>
</body>
</html>

