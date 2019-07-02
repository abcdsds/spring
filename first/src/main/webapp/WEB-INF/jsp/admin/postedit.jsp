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
<title>Admin Area | Edit Page</title>
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
				<a class="navbar-brand" href="#">게시판</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="<c:url value='/admin/main.do' />">Dashboard</a></li>
					<li><a href="<c:url value='/admin/menu.do' />">메뉴</a></li>
					<li><a href="<c:url value='/admin/board.do' />">게시판</a></li>
					<li class="active"><a
						href="<c:url value='/admin/boardcontents.do' />">게시글</a></li>
					<li><a href="<c:url value='/admin/user.do' />">회원</a></li>
					<li><a href="<c:url value='/admin/log.do' />">접속기록</a></li>
					<li><a href="<c:url value='/admin/reply.do' />">댓글</a></li>
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
						<span class="glyphicon glyphicon-cog" aria-hidden="true"></span>글
						수정
					</h1>
				</div>
			</div>
		</div>
	</header>

	<section id="breadcrumb">
		<div class="container">
			<ol class="breadcrumb">
				<li><a href="index.html">게시판</a></li>
				<li><a href="pages.html">${BOARD_NAME }</a></li>
				<li class="active">게시글 수정</li>
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
							<form action="<c:url value='/admin/postupdate.do' />" method="post"
								enctype="multipart/form-data">
								<div class="form-group">
									<label>게시판</label> <select class="form-control" id="BOARD_ID"
										name="BOARD_ID">

										<c:choose>
											<c:when test="${fn:length(menulist) > 0}">
												<c:forEach var="row" items="${menulist}" varStatus="status">
													<option value="${row.BO_ID}"
											<c:if test="${map.BOARD_ID.equals(row.BO_ID)}">selected</c:if>>${row.BO_NAME }</option>
												</c:forEach>

											</c:when>
										</c:choose>
									</select>
								</div>

								<div class="form-group">
									<label>글 제목</label> <input type="text" name="TITLE"
										class="form-control" placeholder="제목을 입력하세요"
										value="${map.BOARD_TITLE } ">
								</div>
								<div class="form-group">
									<label>글 내용</label>
									<textarea name="CONTENTS" class="form-control"
										placeholder="내용을 입력하세요">${map.BOARD_CONTENTS }</textarea>
								</div>


								<div class="form-group" id="fileDiv">

									<c:forEach var="row" items="${list }" varStatus="var">
										<input type="hidden" id="IDX" name="IDX_${var.index }"
											value="${row.IDX }">
										<input type="file" name="file_${var.index }"
											class="input-ghost" style="visibility: hidden; height: 0">
										<div class="input-group input-file">
											<span class="input-group-btn">
												<button class="btn btn-default btn-choose" type="button">파일첨부</button>
											</span> <input type="text" class="form-control"
												placeholder='파일선택...' value="${row.ORIGINAL_FILE_NAME }" />
											<span class="input-group-btn">
												<button class="btn btn-warning btn-reset" type="button">Reset</button>
												<a href="#this" class="btn btn-danger" name="delete">삭제</a>
											</span>
										</div>
									</c:forEach>

								</div>



								<a href="#this" class="btn btn-default" id="addFile">파일 추가</a> 
								<input type="hidden" name="IDX" value="${map.BOARD_IDX }"><input
									type="submit" class="btn btn-default" value="Submit">
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
		var gfv_count = 1;
		$(document).ready(function() {

			$("#addFile").on("click", function(e) { //파일 추가 버튼

				e.preventDefault();
				if ($('.input-group').length < 5) {
					fn_addFile();
				} else {

					alert("최대 5개까지만 업로드 할수 있습니다.");
				}
			});

			$("a[name='delete']").on("click", function(e) { //삭제 버튼

				e.preventDefault();
				fn_deleteFile($(this));
			});
		});

		function fn_addFile() {

			var str = '<div class="input-group input-file">'
					+ '<span class="input-group-btn">'
					+ '<button class="btn btn-default btn-choose" type="button">파일첨부</button>'
					+ '</span> <input type="text" class="form-control" placeholder="파일선택..." /> <span class="input-group-btn">'
					+ '<button class="btn btn-warning btn-reset" type="button">Reset</button><a href="#this" class="btn btn-danger"  name="delete">삭제</a></span>'
					+ '</div></div>';

			$("#fileDiv").append(str);
			bs_input_file()
			$("a[name='delete']").on("click", function(e) { //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});

		}

		function fn_deleteFile(obj) {

			obj.parent().parent().remove();
		}

		function bs_input_file() {
			$(".input-file")
					.before(
							function() {
								if (!$(this).prev().hasClass('input-ghost')) {
									var element = $("<input type='file' name='file_"
											+ (gfv_count++)
											+ "'"
											+ " class='input-ghost' style='visibility:hidden; height:0'>");
									element.attr("name", $(this).attr("name"));
									element.change(function() {
										element.next(element).find('input')
												.val(
														(element.val()).split(
																'\\').pop());
									});
									$(this).find("button.btn-choose").click(
											function() {
												element.click();
											});
									$(this).find("button.btn-reset").click(
											function() {
												element.val(null);
												$(this).parents(".input-file")
														.find('input').val('');
											});
									$(this).find('input').css("cursor",
											"pointer");
									$(this).find('input').mousedown(
											function() {
												$(this).parents('.input-file')
														.prev().click();
												return false;
											});
									return element;
								}
							});
		}
		$(function() {
			bs_input_file();
		});
	</script>
</body>
</html>
