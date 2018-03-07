<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('exportAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增出口报运
  </div> 
 	<input type="hidden" name="contractIds" value="${contract.id}"/>

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <!-- <tr>
	        	//exportId自动生成，无需手动输入
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="exportId" value=""/></td>
	        </tr> -->	
	        <tr>
	            <td class="columnTitle">制单日期</td>
	            <!-- <td class="tableContent"><input type="text" name="inputDate" value=""/></td> -->
	        	<td class="tableContent">
					<input type="text" style="width:90px;" name="inputDate"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        <!-- <tr>
	        	//ID集合串，无需输入
	            <td class="columnTitle">ID集合串
            
            x,y,z：</td>
	            <td class="tableContent"><input type="text" name="contractIds" value=""/></td>
	        </tr> -->	
	       <!--  <tr>
	       		合同在数据库中表现的唯一是Id ,在纸面上体现的是合同号，同样不需要手动输入
	            <td class="columnTitle">客户的合同号,可选择多个合同：</td>
	            <td class="tableContent"><input type="text" name="customerContract" value=""/></td>
	        </tr> -->	
	        
	            <td class="columnTitle">信用证号</td>
	            <td class="tableContent"><input type="text" name="lcno" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">收货人及地址</td>
	            <td class="tableContent"><input type="text" name="consignee" value=""/></td>
	        
	            <td class="columnTitle">唛头</td>
	            <td class="tableContent"><input type="text" name="marks" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">装运港</td>
	            <td class="tableContent"><input type="text" name="shipmentPort" value=""/></td>
	        
	            <td class="columnTitle">目的港</td>
	            <td class="tableContent"><input type="text" name="destinationPort" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">运输方式</td>
	            <td class="tableContent"><input type="text" name="transportMode" value=""/></td>
	        
	            <td class="columnTitle">价格条件</td>
	            <td class="tableContent"><input type="text" name="priceCondition" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">备注</td>
	            <td class="tableContent"><input type="text" name="remark" value=""/></td>
	        
	            <td class="columnTitle">总箱数</td>
	            <td class="tableContent"><input type="text" name="boxNums" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">总毛重</td>
	            <td class="tableContent"><input type="text" name="grossWeights" value=""/></td>
	        
	            <td class="columnTitle">总体积</td>
	            <td class="tableContent"><input type="text" name="measurements" value=""/></td>
	        </tr>	
	        <!-- <tr>
	        	状态自动注入，无需手动输入
	            <td class="columnTitle">0-草稿 1-已上报 2-装箱 3-委托 4-发票 5-财务：</td>
	            <td class="tableContent"><input type="text" name="state" value=""/></td>
	        </tr> -->	
	       <!--  <tr>
	       		系统自动输入，无需手动注入
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="createBy" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="createDept" value=""/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">：</td>
	            <td class="tableContent"><input type="text" name="createTime" value=""/></td>
	        </tr>	 -->
		</table>
	</div>
 
 
</form>
</body>
</html>

