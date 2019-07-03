<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Admin Area | Users</title>
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
					<li class="active"><a href="<c:url value='/admin/log.do' />">접속기록</a></li>
					<li><a href="<c:url value='/admin/reply.do' />">댓글</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<c:url value='/board/member.do' />">${USER_NICKNAME} 님 환영합니다.</a></li>
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
						접속기록
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
				<li>Users</li>
				<li class="active">Log</li>
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
							<h3 class="panel-title">Users</h3>
						</div>
						<div class="panel-body">
							<div class="row">
								<form methon="get">
									<div class="col-md-2">
										<select class="form-control" id="SCH_TYPE" name="SCH_TYPE">
											<option value="LOG_ID"
												${SCH_TYPE eq "LOG_ID" ? "selected" :""}>아이디</option>
											<option value="LOG_DATE"
												${SCH_TYPE eq "LOG_DATE" ? "selected" :""}>날짜</option>
											<option value="LOG_IP"
												${SCH_TYPE eq "LOG_IP" ? "selected" :""}>아이피</option>
											<option value="LOG_USERAGENT"
												${SCH_TYPE eq "LOG_USERAGENT" ? "selected" :""}>유저에이전트</option>
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
									<th>아이디</th>
									<th>이름</th>
									<th>접속일</th>
									<th>아이피</th>
									<th>등급</th>
									<th>브라우저</th>


								</tr>
								<c:choose>
									<c:when test="${fn:length(list) > 0}">
										<c:forEach var="row" items="${list}" varStatus="status">
											<c:if test="${row.TOTAL_COUNT > 0}">
												<tr>
													<td>${row.LOG_ID}</td>
													<td>${row.MEM_NAME}</td>
													<td>${row.LOG_DATE}</td>
													<td>${row.LOG_IP}</td>
													<c:choose>
														<c:when test="${row.MEM_LEVEL eq '0'}">
															<td>운영자</td>
														</c:when>
														<c:when test="${row.MEM_LEVEL eq '1'}">
															<td>VIP</td>
														</c:when>
														<c:when test="${row.MEM_LEVEL eq '2'}">
															<td>일반회원</td>
														</c:when>
													</c:choose>
													<c:choose>
														<c:when
															test="${fn:indexOf(row.LOG_USERAGENT, 'Chrome') != -1}">
															<td>크롬</td>
														</c:when>
														<c:when
															test="${fn:indexOf(row.LOG_USERAGENT, 'MSIE 11') != -1}">
															<td>익스11</td>
														</c:when>
														<c:when
															test="${fn:indexOf(row.LOG_USERAGENT, 'MSIE 10') != -1}">
															<td>익스10</td>
														</c:when>
														<c:when
															test="${fn:indexOf(row.LOG_USERAGENT, 'Opera') != -1}">
															<td>오페라</td>
														</c:when>
														<c:when
															test="${fn:indexOf(row.LOG_USERAGENT, 'Safari') != -1}">
															<td>사파리</td>
														</c:when>
														<c:when
															test="${fn:indexOf(row.LOG_USERAGENT, 'Firefox') != -1}">
															<td>파이어폭스</td>
														</c:when>
													</c:choose>


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
