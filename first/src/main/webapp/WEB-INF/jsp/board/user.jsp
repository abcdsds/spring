<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Admin Area | Pages</title>
<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/boardstyle.css" rel="stylesheet">
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
				<a class="navbar-brand" href="#">게시판</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<c:choose>
						<c:when test="${fn:length(menulist) > 0}">
							<c:forEach var="row" items="${menulist}" varStatus="status">
								<c:choose>
									<c:when test="${row.MENU_KIND eq '0'}">

										<li class="navbar-item dropdown"><a
											class="nav-link dropdown-toggle" data-toggle="dropdown">${row.MENU_NAME}</a>
											<ul class="dropdown-menu">
												<c:forEach var="col" items="${menulist}" varStatus="status">
													<c:if
														test="${col.MENU_KIND.equals('1') and row.MENU_GROUP eq col.MENU_GROUP}">
														<li><a
															href="<c:url value='/board/board.do?BOARD_ID=' />${col.BO_ID}">${col.MENU_NAME}</a>
														</li>
													</c:if>
												</c:forEach>
											</ul></li>
									</c:when>

								</c:choose>


							</c:forEach>
						</c:when>
					</c:choose>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${member.level == '0'}">
						<li><a href="<c:url value='/admin/main.do' />">관리페이지</a></li>
					</c:if>
						<li><a href="<c:url value='/board/mycontents.do?act=contents' />">내 글</a></li>
					<li><a href="<c:url value='/board/mycontents.do?act=replys' />">내 댓글</a></li>
					<li><a href="<c:url value='/board/member.do' />">${member.nickname}
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
						게시글
					</h1>
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
				<div class="col-md-12">
					<!-- Website Overview -->
					<div class="panel panel-default">
						<div class="panel-heading main-color-bg">
							<h3 class="panel-title">${BOARD_NAME }</h3>
						</div>
						<div class="panel-body">
							<div class="row">
								<p class="pull-right" style="margin-right: 20px;">
									<a href="<c:url value='/board/memberedit.do' />" class="btn btn-default btn-choose">정보수정</a>
								</p>

								<table class="table table-striped table-hover">
									<tr>
										<th scope="row">아이디</th>
										<td>${member.id}</td>
									</tr>
									<tr>
										<th scope="row">이름</th>
										<td>${member.username}</td>
									</tr>
									<tr>
										<th scope="row">닉네임</th>
										<td>${member.nickname }</td>
									</tr>
									<tr>
										<th scope="row">이메일</th>
										<td>${member.email }</td>
									</tr>
									<tr>
										<th scope="row">가입일</th>
										<td>${member.regdate }</td>
									</tr>
									<tr>
										<th scope="row">소셜타입</th>
										<td>${member.ssoType }</td>
									</tr>
									<tr>
										<th scope="row">등급</th>
										<c:choose>
											<c:when test="${member.level eq '0'}">
												<td>운영자</td>
											</c:when>
											<c:when test="${member.level eq '1'}">
												<td>VIP</td>
											</c:when>
											<c:when test="${member.level eq '2'}">
												<td>일반회원</td>
											</c:when>
										</c:choose>

									</tr>
								</table>
							</div>
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
		
	</script>
</body>
</html>
