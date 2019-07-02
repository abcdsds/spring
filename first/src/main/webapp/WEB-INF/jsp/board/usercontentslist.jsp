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
					<c:if test="${USER_LEVEL == '0'}">
						<li><a href="<c:url value='/admin/main.do' />">관리페이지</a></li>
					</c:if>
						<li><a href="<c:url value='/board/mycontents.do?act=contents' />">내 글</a></li>
					<li><a href="<c:url value='/board/mycontents.do?act=replys' />">내 댓글</a></li>
					<li><a href="<c:url value='/board/member.do' />">${NICKNAME}
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
				<li>게시판</li>
				<li></li>
				<li class="active">${BOARD_NAME }</li>
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
								<form method="get">
									<div class="col-md-2">
										<select class="form-control" id="SCH_TYPE" name="SCH_TYPE">
											<c:choose>
												<c:when test="${act eq 'contents'}">
													<option value="BOARD_TITLE"
														${SCH_TYPE eq "BOARD_TITLE" ? "selected" :""}>제목</option>
													<option value="BOARD_CONTENTS"
														${SCH_TYPE eq "BOARD_CONTENTS" ? "selected" :""}>내용</option>
													<option value="TITLE_CONTENTS"
														${SCH_TYPE eq "TITLE_CONTENTS" ? "selected" :""}>제목+내용</option>
												</c:when>
												<c:when test="${act eq 'replys'}">
													<option value="TITLE"
														${SCH_TYPE eq "BOARD_TITLE" ? "selected" :""}>제목</option>
												</c:when>
											</c:choose>
										</select>
									</div>

									<div class="col-md-6">
										<input class="form-control" type="hidden" value="${act}"
											id="act" name="act"> <input class="form-control"
											type="text" value="${SCH_KEYWORD}" id="SCH_KEYWORD"
											name="SCH_KEYWORD">
									</div>
									<input type="submit" class="btn btn-default" value="검색">
								</form>

							</div>
							<br>
							<table class="table table-striped table-hover">
								<tr>
									<th class="col-md-1">게시판</th>
									<th class="col-md-5">제목</th>
									<th class="col-md-1">닉네임</th>
									<c:if test="${act eq 'contents'} ">
										<th class="col-md-2">조회수</th>
									</c:if>
									<th class="col-md-3">날짜</th>

								</tr>

								<c:choose>

									<c:when test="${fn:length(contentslist) > 0}">
										<c:forEach var="row" items="${contentslist}"
											varStatus="status">
											<c:if test="${row.TOTAL_COUNT > 0}">
												<tr>
													<td>${row.BO_NAME}</td>
													<td><a
														href="<c:url value='/board/read.do?IDX=' />${row.BOARD_IDX}&BOARD_ID=${row.BOARD_ID}">${row.BOARD_TITLE}</a></td>
													<td>${row.USER_NICKNAME}</td>
													<c:if test="${act eq 'contents'} ">
														<td>${row.BOARD_HIT_CNT}</td>
													</c:if>

													<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
															value="${row.BOARD_DTM}" /></td>

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

		})
	</script>
</body>
</html>
