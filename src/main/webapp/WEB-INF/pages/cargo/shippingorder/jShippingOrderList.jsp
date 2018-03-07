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
<li id="view"><a href="#" onclick="formSubmit('shippingOrderAction_toview','_self');this.blur();">查看</a></li>
<li id="update"><a href="#" onclick="formSubmit('shippingOrderAction_toupdate','_self');this.blur();">修改</a></li>
<li id="delete"><a href="#" onclick="formSubmit('shippingOrderAction_delete','_self');this.blur();">删除</a></li>
<li id="save"><a href="#" onclick="formSubmit('shippingOrderAction_tocreate','_self');this.blur();">委托</a></li>

</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    委托列表
  </div> 
  
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">编号</td>
		<td class="tableHeader">运输方式</td>
		<td class="tableHeader">货主</td>
		<td class="tableHeader">提单抬头</td>
		<td class="tableHeader">正本通知人</td>
		<td class="tableHeader">信用证</td>
		<td class="tableHeader">装运港</td>
		<td class="tableHeader">转船港</td>
		<td class="tableHeader">卸货港</td>
		<td class="tableHeader">状态</td>

		
	</tr>
	</thead>
	<tbody class="tableBody" >
${page.links}
	
	<c:forEach items="${page.results}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td>${status.index+1}</td>
		<td>${o.id}</td>
		<td>${o.orderType}</td>
		<td>${o.shipper}</td>
		<td>${o.consignee}</td>
		<td>${o.notifyParty}</td>
		<td>${o.lcNo}</td>
		<td>${o.portOfLoading}</td>
		<td>${o.portOfTrans}</td>
		<td>${o.portOfDischarge}</td>
	
		<td>
			<c:if test="${o.state==0 }">草稿</c:if>
			<c:if test="${o.state==1 }">已装箱</c:if>
			<c:if test="${o.state==2 }">已委托</c:if>
			<c:if test="${o.state==3 }">已通知</c:if>
		</td>
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
 
</div>
 
 
</form>
</body>
</html>

