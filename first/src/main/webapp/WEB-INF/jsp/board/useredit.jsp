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
<title>Admin Area | Edit Page</title>
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
				<a class="navbar-brand" href="#">운영관리</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="<c:url value='/main.do' />">Dashboard</a></li>
					<li><a href="<c:url value='/board.do' />">게시판</a></li>
					<li><a href="<c:url value='/boardcontents.do' />">게시글</a></li>
					<li class="active"><a href="<c:url value='/user.do' />">회원</a></li>
					<li><a href="<c:url value='/log.do' />">접속기록</a></li>
					<li><a href="<c:url value='/reply.do' />">댓글</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${member.level == '0'}">
						<li><a href="<c:url value='/admin/main.do' />">관리페이지</a></li>
					</c:if>
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
						회원수정
					</h1>
				</div>
			</div>
		</div>
	</header>

	<section id="breadcrumb">
		<div class="container">
			<ol class="breadcrumb">
				<li><a href="index.html">Dashboard</a></li>
				<li><a href="pages.html">Pages</a></li>
				<li class="active">Edit Page</li>
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
							<h3 class="panel-title">Edit Page</h3>
						</div>
						<div class="panel-body">
							<form id="user" action="<c:url value='/board/memberupdate.do' />">
								<div class="form-group">
									<label>닉네임</label> <input type="text" name="nickname"
										id="nickname" class="form-control" placeholder="닉네임"
										value="${member.nickname }">
								</div>
								<div class="form-group">
									<label>이메일</label> <input type="text" id="email"
										class="form-control" placeholder="이메일" name="email"
										value="${member.email }">
								</div>
								<input type="submit" class="btn btn-default" value="수정">
							</form>
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
