<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String basePath = request.getScheme() + "://" +
request.getServerName() + ":" + request.getServerPort() +
request.getContextPath() + "/";

	Map<String,String> map = (Map<String,String>)application.getAttribute("stagePossibility");


	Set<String> strings = map.keySet();

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<base href=<%=basePath%>>

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_typeahead/bootstrap3-typeahead.min.js"></script>
</head>
<script type="text/javascript">


	$(function() {

		var json = {
			<%
            for (String key:strings){
                String value = map.get(key);
                %>
			"<%=key%>":<%=value%>,
			<%
            }
            %>
		}

		$("#create-customerName").typeahead({
			source: function (query, process) {
				$.post(
						"tran/getCustomerList.do",  /*url*/
						{ "name" : query },/*data*/
						function (data) {/*success*/
							//alert(data);
							process(data);
						},
						"json"/*datatype*/
				);
			},
			delay: 1500
		});


		$(".time").datetimepicker({
			minView: "month",
			language: 'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "top-left"
		});
		$(".time2").datetimepicker({
			minView: "month",
			language: 'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});


		$.ajax({
			type:"get",
			data:{},
			dataType:"json",
			url:"tran/getUserList.do",
			success:function (data) {
				var html = "";
				$.each(data,function (i,n) {
					html+="<option value='"+n.id+"'>"+n.name+"</option>"
				})

				$("#create-transactionOwner").html(html);
				$("#create-transactionOwner").val("${user.id}")
			}
		})

		$("#searchActivity").click(function () {

			$("#findMarketActivity").modal("show")
		})

		$("#searchAct").keydown(function (event) {
			if(event.keyCode==13) {
				$.ajax({
					url: "tran/getActitivtyList.do",
					data: {"name": $.trim($("#searchAct").val())},
					dataType: "json",
					success: function (data) {
						var html = ""
						$.each(data, function (i, n) {
							html += '<tr>'
							html += '<td><input type="radio" name="dx" value="' + n.id + '"/></td>'
							html += '<td id="'+n.id+'">' + n.name + '</td>'
							html += '<td>' + n.startDate + '</td>'
							html += '<td>' + n.endDate + '</td>'
							html += '<td>' + n.owner + '</td>'
							html += '</tr>'
						})

						$("#activityBody").html(html);
					}
				})

				return false;
			}
		});
		$("#submitActivityBtn").click(function () {
			var $input = $("input[name=dx]:checked");
			var id = $input.val();

			$("#activityId").val(id)
			$("#activityName").val($("#"+id).html())


			$("#findMarketActivity").modal("hide");
		})

		//????????????????????????
		$("#contactSearch").click(function () {
			$("#findContacts").modal("show")
		})


		$("#contactName").keydown(function (event) {
			if(event.keyCode==13){
				//??????ajax??????

				$.ajax({
					url:"tran/getContactList.do",
					data:{"name":$.trim($("#contactName").val())},
					dataType:"json",
					success:function (data) {
						var html = ""
						$.each(data,function (i,n) {

							html +='<tr>'
							html +='<td><input type="radio" name="con" value="'+n.id+'"/></td>'
							html +='<td id="'+n.id+'">'+n.fullname+'</td>'
							html +='<td>'+n.email+'</td>'
							html +='<td>'+n.phone+'</td>'
							html +='</tr>'
						})

						$("#contactBody").html(html);
					}
				})
				return false;
			}

		})

		$("#contactSubmit").click(function () {

			var $input = $("input[name=con]:checked");
			var id = $input.val();

			$("#contactId").val(id)
			$("#contactsName").val($("#"+id).html())
			$("#contactsName").val("??????")
			$("#findContacts").modal("hide");
		})

		$("#create-stage").change(function () {
			var value = this.value;

			alert(json[value])
			$("#create-possibility").val(json[value]);

		})

		$("#submitBtn").click(function () {
			$("#transForm").submit();
		})
	});

</script>
<body>

<option/>
	<!-- ?????????????????? -->	
	<div class="modal fade" id="findMarketActivity" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">??</span>
					</button>
					<h4 class="modal-title">??????????????????</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="????????????????????????????????????????????????" id="searchAct">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>

							<input type="button" value="??????" id="submitActivityBtn">
						</form>

					</div>
					<table id="activityTable3" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>??????</td>
								<td>????????????</td>
								<td>????????????</td>
								<td>?????????</td>
							</tr>
						</thead>
						<tbody id="activityBody">
						<%--	<tr>
								<td><input type="radio" name="activity"/></td>
								<td>?????????</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>--%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- ??????????????? -->	
	<div class="modal fade" id="findContacts" role="dialog">
		<div class="modal-dialog" role="document" style="width: 80%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">??</span>
					</button>
					<h4 class="modal-title">???????????????</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input type="text" class="form-control" style="width: 300px;" placeholder="?????????????????????????????????????????????" id="contactName">

						    <span class="glyphicon glyphicon-search form-control-feedback" ></span>
						  </div>
							<button id="contactSubmit">??????</button>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>??????</td>
								<td>??????</td>
								<td>??????</td>
							</tr>
						</thead>
						<tbody id="contactBody">

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	
	<div style="position:  relative; left: 30px;">
		<h3>????????????</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button type="button" class="btn btn-primary" id="submitBtn">??????</button>
			<button type="button" class="btn btn-default">??????</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form class="form-horizontal" role="form" style="position: relative; top: -30px;" id="transForm" action="tran/save.do">
		<div class="form-group">
			<label for="create-transactionOwner" class="col-sm-2 control-label">?????????<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionOwner" name="owner">

				</select>
			</div>
			<label for="create-amountOfMoney" class="col-sm-2 control-label">??????</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-amountOfMoney" name="money">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionName" class="col-sm-2 control-label">??????<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-transactionName" name="name">
			</div>
			<label for="create-expectedClosingDate" class="col-sm-2 control-label">??????????????????<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time2" id="create-expectedClosingDate" name="expectedDate">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-accountName" class="col-sm-2 control-label">????????????<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-customerName" placeholder="???????????????????????????????????????????????????" name="customerId">
			</div>
			<label for="create-transactionStage" class="col-sm-2 control-label">??????<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
			  <select class="form-control" id="create-stage" name="stage">
				  <c:forEach items="${stage}" var="s">
					  <option value="${s.value}">${s.text}</option>
				  </c:forEach>
			  </select>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-transactionType" class="col-sm-2 control-label">??????</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-transactionType" name="type">
					<c:forEach items="${transactionType}" var="s">
						<option value="${s.value}">${s.text}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-possibility" class="col-sm-2 control-label">?????????</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="create-possibility">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-clueSource" class="col-sm-2 control-label">??????</label>
			<div class="col-sm-10" style="width: 300px;">
				<select class="form-control" id="create-clueSource" name="source">
					<c:forEach items="${source}" var="s">
						<option value="${s.value}">${s.text}</option>
					</c:forEach>
				</select>
			</div>
			<label for="create-activitySrc" class="col-sm-2 control-label">???????????????&nbsp;&nbsp;<a href="javascript:void(0);"><span class="glyphicon glyphicon-search" id="searchActivity"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="activityName">
				<input type="hidden" id="activityId" name="activityId">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactsName" class="col-sm-2 control-label">???????????????&nbsp;&nbsp;<a href="javascript:void(0);"><span class="glyphicon glyphicon-search" id="contactSearch"></span></a></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="contactsName" value="">
				<input type="hidden" id="contactId" name="contactId">
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-describe" class="col-sm-2 control-label">??????</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-describe" name="description"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-contactSummary" class="col-sm-2 control-label">????????????</label>
			<div class="col-sm-10" style="width: 70%;">
				<textarea class="form-control" rows="3" id="create-contactSummary" name="contactSummary"></textarea>
			</div>
		</div>
		
		<div class="form-group">
			<label for="create-nextContactTime" class="col-sm-2 control-label ">??????????????????</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control time" id="create-nextContactTime" name="nextContactTime">
			</div>
		</div>
		
	</form>
</body>
</html>