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
	    		 formSubmit('roleAction_toview','_self');this.blur();
	    	 }else{
	    		 alert("请先选择一项并且只能选择一项，再进行操作！");
	    	 }
	     }
	     //实现更新
	     function toUpdate(){
	    	 if(isOnlyChecked()){
	    		 formSubmit('roleAction_toupdate','_self');this.blur();
	    	 }else{
	    		 alert("请先选择一项并且只能选择一项，再进行操作！");
	    	 }
	     }
	     //实现删除
	      function toDelete(){
	    	 if(isChecked()){
	    		 formSubmit('roleAction_delete','_self');this.blur();
	    	 }else{
	    		 alert("请先选择一项或多项，再进行操作！");
	    	 }
	     }
	      //实现权限查看
	      function toModule(){
	    	 if(isOnlyChecked()){
	    		 formSubmit('roleAction_tomodule','_self');this.blur();
	    	 }else{
	    		 alert("请先选择一项并且只能选择一项，再进行操作！");
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
<li id="view"><a href="#" onclick="javascript:toView()">查看</a></li>
<li id="new"><a href="#" onclick="formSubmit('roleAction_tocreate','_self');this.blur();">新增</a></li>
<li id="update"><a href="#" onclick="javascript:toUpdate()">修改</a></li>
<li id="delete"><a href="#" onclick="javascript:toDelete()">删除</a></li>
<li id="new"><a href="#" onclick="javascript:toModule()" title="分配权限">权限</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
    角色列表
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
		<td class="tableHeader">编号</td>
		<td class="tableHeader">名称</td>
		<td class="tableHeader">说明</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
	${page.links}
	<c:forEach items="${dataList}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="checkbox" name="id" value="${o.id}"/></td>
		<td>${status.index+1}</td>
		<td>${o.id}</td>
		<td><a href="roleAction_toview?id=${o.id}">${o.name}</a></td>
		<td>${o.remark}</td>
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div>
 
</div>
 
 
</form>
</body>
</html>
