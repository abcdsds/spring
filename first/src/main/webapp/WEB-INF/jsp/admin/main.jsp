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
<title>Admin Area | Dashboard</title>
<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/style.css" rel="stylesheet">
<!-- 합쳐지고 최소화된 최신 CSS -->

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
					<li class="active"><a href="<c:url value='/admin/main.do' />">Dashboard</a></li>
					<li><a href="<c:url value='/admin/board.do' />">게시판</a></li>
					<li><a
						href="<c:url value='/admin/boardcontents.do' />">게시글</a></li>
					<li><a href="<c:url value='/admin/user.do' />">회원</a></li>
					<li><a href="<c:url value='/admin/log.do' />">접속기록</a></li>
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
						Dashboard 
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
				<li class="active">Dashboard</li>
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
							<h3 class="panel-title">Website Overview</h3>
						</div>
						<div class="panel-body">
							<div class="col-md-3">
								<div class="well dash-box">
									<h2>
										<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
										${map.COUNTMEMBER }
									</h2>
									<h4>회원</h4>
								</div>
							</div>
							<div class="col-md-3">
								<div class="well dash-box">
									<h2>
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										${map.COUNTCONTENT }
									</h2>
									<h4>게시글</h4>
								</div>
							</div>
							<div class="col-md-3">
								<div class="well dash-box">
									<h2>
										<span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
										${map.COUNTBOARD }
									</h2>
									<h4>게시판</h4>
								</div>
							</div>

							<div class="col-md-3">
								<div class="well dash-box">
									<h2>
										<span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
										${map.COUNTLOGMEMBER }
									</h2>
									<h4>접속기록</h4>
								</div>
							</div>

							<div class="col-md-3">
								<div class="well dash-box">
									<h2>
										<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
										${map.COUNTREPLY }
									</h2>
									<h4>최신 댓글</h4>
								</div>
							</div>
						</div>
					</div>

					<!-- Latest Users -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">최근 접속한 회원</h3>
						</div>
						<div class="panel-body">
							<table class="table table-striped table-hover">
								<tr>
									<th>아이디</th>
									<th>이름</th>
									<th>이메일</th>
									<th>접속일</th>
								</tr>
								<c:choose>
									<c:when test="${fn:length(list) > 0}">
										<c:forEach var="row" items="${list}" varStatus="status">
											<tr>
												<td>${row.MEM_ID}</td>
												<td>${row.MEM_NAME}</td>
												<td>${row.MEM_EMAIL}</td>
												<td>${row.LOG_DATE}</td>
											</tr>
										</c:forEach>
									</c:when>
								</c:choose>
							</table>
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

	<!-- Add Board -->
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
	<script src="<c:url value='/js/common.js'/>" charset="utf-8"></script>
	<%@ include file="/WEB-INF/include/include-body.jspf"%>
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
