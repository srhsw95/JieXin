<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/jquery-1.4.4.js"></script>
	<script>
	     function isOnlyChecked(){
	    	/*  var checkBoxArray = document.getElementsByName('id');
				var count=0;
				for(var index=0; index<checkBoxArray.length; index++) {
					if (checkBoxArray[index].checked) {
						count++;
					}	
				} */
			//jquery
			var count = $("[input name='id']:checked").size();
			if(count==1)
				return true;
			else
				return false;
	     }
	     function isChecked(){
		    	/*  var checkBoxArray = document.getElementsByName('id');
					var count=0;
					for(var index=0; index<checkBoxArray.length; index++) {
						if (checkBoxArray[index].checked) {
							count++;
						}	
					} */
				//jquery
				var count = $("[input name='id']:checked").size();
				if(count>=1)
					return true;
				else
					return false;
		     }
	     
	     function toView(){
	    	 if(isOnlyChecked()){
	    		 formSubmit('contractAction_toview','_self');this.blur();
	    	 }else{
	    		 alert("请先选择一项并且只能选择一项，再进行操作！");
	    	 }
	     }
	     //实现更新
	     function toUpdate(){
	    	 if(isOnlyChecked()){
	    		 formSubmit('contractAction_toupdate','_self');this.blur();
	    	 }else{
	    		 alert("请先选择一项并且只能选择一项，再进行操作！");
	    	 }
	     }
	     //实现删除
	      function toDelete(){
	    	 if(isChecked()){
	    		 formSubmit('contractAction_delete','_self');this.blur();
	    	 }else{
	    		 alert("请先选择一项或多项，再进行操作！");
	    	 }
	     }
	     
	     //实现提交
	      function toSubmit(){
	    	 if(isChecked()){
	    		 formSubmit('contractAction_submit','_self');this.blur();
	    	 }else{
	    		 alert("请先选择一项或多项，再进行操作！");
	    	 }
	     }
	     
	    //实现取消
	      function toCancel(){
	    	 if(isChecked()){
	    		 formSubmit('contractAction_cancel','_self');this.blur();
	    	 }else{
	    		 alert("请先选择一项或多项，再进行操作！");
	    	 }
	     }
	     
	</script>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<!-- <li id="view"><a href="#" onclick="formSubmit('contractAction_toview','_self');this.blur();">查看</a></li> -->
<li id="view"><a href="#" onclick="javascript:toView()">查看</a></li>
<li id="new"><a href="#" onclick="formSubmit('contractAction_tocreate','_self');this.blur();">新增</a></li>
<!-- <li id="update"><a href="#" onclick="formSubmit('contractAction_toupdate','_self');this.blur();">修改</a></li> -->
<li id="update"><a href="#" onclick="javascript:toUpdate()">修改</a></li>
<!-- <li id="delete"><a href="#" onclick="formSubmit('contractAction_delete','_self');this.blur();">删除</a></li> -->
<li id="delete"><a href="#" onclick="javascript:toDelete()">删除</a></li>
<!-- <li id="new"><a href="#" onclick="formSubmit('contractAction_submit','_self');this.blur();">提交</a></li> -->
<li id="new"><a href="#" onclick="javascript:toSubmit()">提交</a></li>
<!-- <li id="new"><a href="#" onclick="formSubmit('contractAction_cancel','_self');this.blur();">取消</a></li> -->
<li id="new"><a href="#" onclick="javascript:toCancel()">取消</a></li>
<li id="new"><a href="#" onclick="formSubmit('contractAction_print','_self');this.blur();">打印</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
  <img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    购销合同列表
  </div> 
  </div>
  </div>
  
<div>


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">客户名称</td>
		<td class="tableHeader">合同号</td>
		<td class="tableHeader">货物数/附件数</td>
		<td class="tableHeader">制单人</td>
		<td class="tableHeader">审单人</td>
		<td class="tableHeader">验货员</td>
		<td class="tableHeader">签单日期</td>
		<td class="tableHeader">交货期限</td>
		<td class="tableHeader">船期</td>
		<td class="tableHeader">贸易条款</td>
		<td class="tableHeader">总金额</td>
		<td class="tableHeader">状态</td>
		<td class="tableHeader">操作</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
	${page.links}
	<c:forEach items="${dataList}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td>${status.index+1}</td>
		<td>${o.customerName}</td>
		<td><a href="contractAction_toview?id=${o.id}">${o.contractNo}</a></td>
		<td>
		    ${o.contractProducts.size() }
		    /
		    <c:set var="extNo" value="0"></c:set>
		    <c:forEach items="${o.contractProducts }"  var="cp" >
		        <c:if test="${cp.extCproducts.size()!=0}">
		            <c:set var="extNo" value="${extNo+cp.extCproducts.size() }"></c:set>
		        </c:if>
		    	
		    </c:forEach>
		    ${extNo }
		</td>
		<td>${o.inputBy}</td>
		<td>${o.checkBy}</td>
		<td>${o.inspector}</td>
		<td><fmt:formatDate value="${o.signingDate}" pattern="yyyy-MM-dd"/></td>
		<td><fmt:formatDate value="${o.deliveryPeriod}" pattern="yyyy-MM-dd"/></td>
		<td><fmt:formatDate value="${o.shipTime}" pattern="yyyy-MM-dd"/></td>
		<td>${o.tradeTerms}</td>
		<td>${o.totalAmount}</td>
		<td><c:if test="${o.state==0}">草稿</c:if>
		<c:if test="${o.state==1}"><font color="green">已上报</font></c:if></td>
		<td><a href="${ctx }/cargo/contractProductAction_tocreate?contract.id=${o.id}">[货物]</a></td>
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
 
</div>
 
 
</form>
</body>
</html>

