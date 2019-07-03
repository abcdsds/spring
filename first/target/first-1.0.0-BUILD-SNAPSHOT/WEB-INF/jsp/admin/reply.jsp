<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Admin Area | Pages</title>
<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<script src="http://cdn.ckeditor.com/4.6.1/standard/ckeditor.js"></script>
</head>
<body>

	<nav class="navbar navbar-default">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">운영관리</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="<c:url value='/admin/main.do' />">Dashboard</a></li>
					<li><a href="<c:url value='/admin/menu.do' />">메뉴</a></li>
					<li><a href="<c:url value='/admin/board.do' />">게시판</a></li>
					<li><a href="<c:url value='/admin/boardcontents.do' />">게시글</a></li>
					<li><a href="<c:url value='/admin/user.do' />">회원</a></li>
					<li><a href="<c:url value='/admin/log.do' />">접속기록</a></li>
					<li class="active"><a href="<c:url value='/admin/reply.do' />">댓글</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<c:url value='/board/member.do' />">${USER_NICKNAME}
							님 환영합니다.</a></li>
					<li><a href="<c:url value='/logout.do' />">Logout</a></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>

	<header id="header">
		<div class="container">
			<div class="row">
				<div class="col-md-10">
					<h1>
						<span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
						댓글
					</h1>
				</div>
				<div class="col-md-2">
					<div class="dropdown create">
						<button class="btn btn-default dropdown-toggle" type="button"
							id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="true">
							관리하기 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
							<li><a type="button" data-toggle="modal"
								data-target="#addBoard">게시판 등록</a></li>
							<li><a type="button" data-toggle="modal"
								data-target="#addUser">회원 추가</a></li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</header>

	<section id="breadcrumb">
		<div class="container">
			<ol class="breadcrumb">
				<li><a href="index.html">Dashboard</a></li>
				<li class="active">Pages</li>
			</ol>
		</div>
	</section>

	<section id="main">
		<div class="container">
			<div class="row">
				<%@ include file="/WEB-INF/include/admin-list.jspf"%>
				<div class="col-md-9">
					<!-- Website Overview -->
					<div class="panel panel-default">
						<div class="panel-heading main-color-bg">
							<h3 class="panel-title">Pages</h3>
						</div>
						<div class="panel-body">
							<div class="row">
								<form methon="get">
									<div class="col-md-2">
										<select class="form-control" id="SCH_TYPE" name="SCH_TYPE">
											<option value="TITLE" ${SCH_TYPE eq "TITLE" ? "selected" :""}>내용</option>
											<option value="REPLY_DTM"
												${SCH_TYPE eq "REPLY_DTM" ? "selected" :""}>날짜</option>
											<option value="USER_ID"
												${SCH_TYPE eq "USER_ID" ? "selected" :""}>아이디</option>
											<option value="USER_NICKNAME"
												${SCH_TYPE eq "USER_NICKNAME" ? "selected" :""}>닉네임</option>
										</select>
									</div>

									<div class="col-md-6">
										<input class="form-control" type="text" value="${SCH_KEYWORD}"
											id="SCH_KEYWORD" name="SCH_KEYWORD">
									</div>
									<input type="submit" class="btn btn-default" value="검색">
								</form>
							</div>
							<br>
							<table class="table table-striped table-hover">
								<tr>
									<th>제목</th>
									<th>닉네임</th>
									<th>날짜</th>
									<th></th>
								</tr>
								<c:choose>
									<c:when test="${fn:length(list) > 0}">
										<c:forEach var="row" items="${list}" varStatus="status">
											<c:if test="${row.TOTAL_COUNT > 0}">
												<tr>
													<td>${row.TITLE}</td>
													<td>${row.USER_NICKNAME}</td>
													<td>${row.REPLY_DTM}</td>
													<td><a type="button" data-toggle="modal" class="btn btn-primary" data-target="#updatereply" data-title="${row.TITLE}" data-id="${row.IDX}">댓글 수정</a>
														<button class="btn btn-danger"
															data-href="<c:url value='/admin/deleteReply.do?REPLYIDX=' />${row.IDX}"
															data-toggle="modal" data-target="#confirm-delete">댓글 삭제</button></td>
												</tr>
											</c:if>
										</c:forEach>

									</c:when>
								</c:choose>
							</table>
							<div class="row">
								<div class="col-md-12">
									<ul class="pagination">
										<c:if test="${not empty paginationInfo}">
											<ui:pagination paginationInfo="${paginationInfo}"
												type="customRenderer" />
										</c:if>

									</ul>
								</div>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</section>

	<footer id="footer">
		<p>Copyright AdminStrap, &copy; 2017</p>
	</footer>

	<!-- Modals -->

	<!-- Add Page -->
	<%@ include file="/WEB-INF/include/admin-modal.jspf"%>

	<div class="modal fade" id="updatereply" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="frmreply" name="frmreply" class="frmreply">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Add User</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label>댓글 내용</label> <textarea id="TITLE" name="TITLE" class="form-control"
										placeholder="내용을 입력하세요"></textarea>
							<input type="hidden" id="REPLYIDX" name="REPLYIDX">
						</div>
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Save
							changes</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
		CKEDITOR.replace('editor1');
	</script>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="../js/bootstrap.min.js"></script>
	<script type="text/javascript">
	 $('#confirm-delete').on('show.bs.modal', function(e) {
         $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
         
         //$('.debug-url').html('Delete URL: <strong>' + $(this).find('.btn-ok').attr('href') + '</strong>');
     });
	 $('#updatereply').on('show.bs.modal', function(e) {
		 var button = $(e.relatedTarget);
		 var title = button.data('title');
		 var idx = button.data('id');
		 var modal = $(this);
		 
		 modal.find('#TITLE').text(title);
		 modal.find('#REPLYIDX').val(idx);

	 });
	 $('#frmreply').submit(function(e) {
		 e.preventDefault();
		 $.ajax({
				type : "POST",
				url : "<c:url value='/admin/updatereply.do' />",
				data : $('form.frmreply').serialize(),
				success : function(response) {
					$('#updatereply').modal('hide');
					location.reload();
				},
				error : function() {
					alert('등록실패');
				}
		});
	       return false;
	 });
		var gfv_count = '${fn:length(list)+1}';
		$(document).ready(function() {
			
							$('#frm').submit(function(e) {
												var id = $("#board_id").val();
												var isID = /^[a-z0-9][a-z0-9_\-]{2,9}$/;
												if (!isID.test(id)) {
													alert("3~10자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
													return false;
												}

												e.preventDefault();
												$.ajax({
															type : "POST",
															url : "<c:url value='/admin/insertboard.do' />",
															data : $('form.frm').serialize(),
															success : function(response) {
																alert("등록성공");
																$('#addBoard').modal('hide');
																$(".modal-body input").val("")
															},
															error : function() {
																alert('등록실패');
															}
														});
												return false;
											});

							$('#frmUser').submit(function(e) {
												e.preventDefault();

												var id = $("#id").val();
												var isID = /^[a-z0-9][a-z0-9_\-]{4,19}$/;
												if (!isID.test(id)) {
													alert("4~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
													return false;
												}

												var email = $("#email").val();
												var isEmail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
												var isHan = /[ㄱ-ㅎ가-힣]/g;

												if (!isEmail.test(email)
														|| isHan.test(email)) {
													alert("이메일 주소를 다시 확인해주세요.");
													return false;
												}

												var password = $("#password").val();
												if (password.length < 5 || password.length > 20) {
													alert("5~20자 만 사용 가능합니다.");
										            return false;
										        }
												
												var nickname = $("#nickname").val();
												var name = $("#name").val();
												var nonchar = /[^가-힣a-zA-Z0-9]/gi;
												
												if (nickname != "" && nonchar.test(nickname)) {
										            alert("한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)");
										            return false;
										        }
												
												if (name != "" && nonchar.test(name)) {
													alert("한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)");
										            return false;
										        }
												
												
												$.ajax({
															type : "POST",
															url : "<c:url value='/admin/insertuser.do' />",
															data : $('form.frmUser').serialize(),
															success : function(response) {
																alert(response);
																$('#addUser').modal('hide');
																$(".modal-body input").val("")
															},
															error : function() {
																alert('등록실패');
															}
														});
												return false;
											});
						});
	</script>
</body>
</html>
