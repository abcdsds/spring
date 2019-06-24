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
					<li class="navbar-item dropdown"><a
						class="nav-link dropdown-toggle" data-toggle="dropdown">게시판 메뉴</a>
						<ul class="dropdown-menu">
							<c:choose>
								<c:when test="${fn:length(menulist) > 0}">
									<c:forEach var="row" items="${menulist}" varStatus="status">
										<li><a
											href="<c:url value='/board/board.do?BOARD_ID=' />${row.BO_ID}">${row.BO_NAME}</a></li>
									</c:forEach>

								</c:when>
							</c:choose>
						</ul></li>

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
							<div class="row"></div>
							<br>
							<table class="table table-striped table-hover">
								<tr>
									<th>${map.BOARD_TITLE }</th>
									<th><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
											value="${map.BOARD_DTM }" /></th>

								</tr>
								<tr>
									<th>${map.USER_NICKNAME }</th>
									<th>조회수 ${map.BOARD_HIT_CNT }</th>



								</tr>
								<tr>

									<th class="contents"><br /> <br /> ${map.BOARD_CONTENTS }</th>
									<th></th>


								</tr>
								<tr>

									<th>첨부파일</th>
									<th></th>
								</tr>
								<tr>
									<th><c:forEach var="row" items="${list }">
											<input type="hidden" id="IDX" value="${row.IDX }">
											<a
												href="<c:url value='/common/downloadFile.do' />?IDX=${row.IDX }"
												name="file">${row.ORIGINAL_FILE_NAME }</a> 
						(${row.FILE_SIZE }kb)
					</c:forEach></th>
									<th></th>
								</tr>
							</table>
							<div class="nextprevbutton">
								<c:choose>
									<c:when test="${map.NEXT == null}">
										<span style="text-align: center; margin-left: 45%"><a
											href="<c:url value='/board/board.do' />?BOARD_ID=${BOARD_ID}"
											class="btn btn-default">목록으로</a> </span>
									</c:when>
									<c:otherwise>
										<span style="text-align: center; margin-left: 34%"><a
											href="<c:url value='/board/board.do' />?BOARD_ID=${BOARD_ID}"
											class="btn btn-default">목록으로</a> </span>

										<span class="pull-left"> <a
											href="<c:url value='/board/read.do?IDX=' />${map.NEXT}"
											class="btn btn-default">이전 글</a>
										</span>
									</c:otherwise>

								</c:choose>
								<span class="pull-right"><a
									href="<c:url value='/board/read.do?IDX=' />${map.PREV}&?BOARD_ID=${BOARD_ID}"
									class="btn btn-default">다음 글</a> </span>
							</div>

							<c:if test="${map.USER_ID.equals(USER_ID)}">
								<div class="editdeletebutton">

									<span class="pull-right"> <a
										href="<c:url value='/board/updateform.do?IDX=' />${map.BOARD_IDX}&BOARD_ID=${BOARD_ID}"
										class="btn btn-default"><span
											class="glyphicon glyphicon-object-align-left"
											aria-hidden="true">수정</span></a>

									</span> <span class="pull-right"> <a href="#"
										onclick="if(confirm('삭제 하시겠습니까?')) fn_delete(${row.IDX});"
										class="btn btn-default"><span
											class="glyphicon glyphicon-erase" aria-hidden="true"></span>삭제</a>

									</span>

								</div>
							</c:if>

							<div class="container">
								<div class="row">
									<div class="col-md-11">
										<div class="page-header">
											<h1>
												<small class="pull-right">${map.COUNTREPLY} comments</small>
												Comments
											</h1>
										</div>
										<ul class="comments-list">
											<c:choose>
												<c:when test="${fn:length(replylist) > 0}">

													<c:forEach var="row" items="${replylist}"
														varStatus="status">
														<c:if test="${row.TOTAL_COUNT > 0}">
															<li class="commentbox" id="commentbox${row.IDX}"><c:choose>
																	<c:when test="${row.IDENT != '0' && row.IDENT != null}">

																		<div class="media"
																			style="margin-left: ${row.IDENT*4}%">
																	</c:when>
																	<c:otherwise>
																		<div class="media">
																	</c:otherwise>
																</c:choose> 
																<c:if test="${row.REPLY_DEL.equals('N')}">
																	<p class="pull-right">

																		<c:if test="${row.USER_ID.equals(USER_ID)}">
																			<a href="#this" class="updatereply"> <span
																				class="glyphicon glyphicon-edit" aria-hidden="true"></span><small>
																					수정</small>
																			</a>
																			<a href="#this"
																				onclick="if(confirm('삭제 하시겠습니까?')) fn_replydelete(${row.IDX});">
																				<span class="glyphicon glyphicon-erase"
																				aria-hidden="true"></span><small> 삭제</small>
																			</a>
																		</c:if>
																		<a href="#this" class="addreply"> <span
																			class="glyphicon glyphicon-comment"
																			aria-hidden="true"></span><small> 댓글</small>
																		</a>
						
																</p>
														      </c:if>
														     
														<div class="media-body">

															<div class="media-heading user_name">
																${row.USER_NICKNAME }<small style="margin-left: 4%"><fmt:formatDate
																		pattern="yyyy-MM-dd HH:mm" value="${row.REPLY_DTM}" /></small>
															</div>
															<p class="TITLE">${row.TITLE }</p>

														</div>
														<div class="rereply_${row.IDX}" style="display: none;">
															<form method="post"
																action="<c:url value='/board/InsertReply.do' />">
																<div class="recommentbox" id="recommentbox_${row.IDX}">
																	<div class="text">
																		<input type="hidden" name="GROUPNUM"
																			value="${row.GROUPNUM}"> <input type="hidden"
																			id="PARENT" name="PARENT" value="${row.IDX}">
																		<input type="hidden" name="DEPTH"
																			value="${row.DEPTH+1}"> <input type="hidden"
																			name="IDENT" value="${row.IDENT+1}"> <input
																			type="hidden" name="BOARDIDX" name="BOARDIDX"
																			value="${map.BOARD_IDX}"> <input
																			type="hidden" name="USER_ID" value="${map.USER_ID }">
																		<input type="hidden" name="USER_NICKNAME"
																			value="${map.USER_NICKNAME }"> <input
																			type="hidden" name="REPLYIDX" value="${row.IDX}">
																		<p class="pull-left">
																			<span class="glyphicon glyphicon-object-align-left"
																				aria-hidden="true"></span>댓글쓰기
																		</p>
																		<p class="pull-right"
																			style="margin-right: ${row.IDENT*33}px; font-size: 10px">
																			<a href="#this" class="close"><span
																				class="glyphicon glyphicon-minus" aria-hidden="true"></span>닫기</a>
																		</p>
																		<textarea class="form-control" id="TITLE" name="TITLE"
																			rows="2" cols="30" maxlength="500"
																			placeholder="댓글을 달아주세요."
																			style="background: rgb(255, 255, 255); overflow: hidden; width: ${100-row.IDENT*5}%; margin-top: 10px; margin-left: 3px;"></textarea>
																	</div>
																	<input type="submit" class="btn btn-success green"
																		value="등록">
																</div>
															</form>

														</div>
														<div class="updatereply_${row.IDX}" style="display: none;">
															<form method="post"
																action="<c:url value='/board/UpdateReply.do' />">
																<div class="upcommentbox" id="upcommentbox_${row.IDX}">
																	<div class="text">
																		<input type="hidden" name="GROUPNUM"
																			value="${row.GROUPNUM}"> <input type="hidden"
																			id="PARENT" name="PARENT" value="${row.IDX}">
																		<input type="hidden" name="DEPTH"
																			value="${row.DEPTH+1}"> <input type="hidden"
																			name="IDENT" value="${row.IDENT+1}"> <input
																			type="hidden" name="BOARDIDX" name="BOARDIDX"
																			value="${map.BOARD_IDX}"> <input
																			type="hidden" name="USER_ID" value="${map.USER_ID }">
																		<input type="hidden" name="USER_NICKNAME"
																			value="${map.USER_NICKNAME }"> <input
																			type="hidden" name="REPLYIDX" value="${row.IDX}">
																		<p class="pull-left">
																			<span class="glyphicon glyphicon-object-align-left"
																				aria-hidden="true"></span>댓글 수정
																		</p>
																		<p class="pull-right"
																			style="margin-right: ${row.IDENT*33}px; font-size: 10px">
																			<a href="#" class="close"><span
																				class="glyphicon glyphicon-minus" aria-hidden="true"></span>닫기</a>
																		</p>
																		<textarea class="form-control" id="TITLE" name="TITLE"
																			rows="2" cols="30" maxlength="500"
																			placeholder="댓글을 달아주세요."
																			style="background: rgb(255, 255, 255); overflow: hidden; width: ${100-row.IDENT*5}%; margin-top: 10px; margin-left: 3px;"></textarea>
																	</div>
																	<input type="submit" class="btn btn-success green"
																		value="등록">
																</div>
															</form>

														</div>
									</div>
									</li>
									</c:if>
									</c:forEach>
									</c:when>
									</c:choose>
									</ul>

								</div>

							</div>
						</div>

						<div class="page">
							<div class="col-md-12">
								<ul class="pagination">
									<c:if test="${not empty paginationInfo}">
										<ui:pagination paginationInfo="${paginationInfo}"
											type="customRenderer" />
									</c:if>

								</ul>
							</div>
						</div>



						<div class="commentform">
							<div class="strong"
								style="padding-left: 5px; margin-bottom: 10px;">
								<strong>댓글 쓰기</strong>
							</div>
							<form method="post"
								action="<c:url value='/board/InsertReply.do' />">
								<div class="text">
									<input type="hidden" name="BOARDIDX" value="${map.BOARD_IDX}">
									<input type="hidden" name="BOARD_ID" value="${BOARD_ID}">
									<input type="hidden" name="USER_ID" value="${USER_ID }">
									<input type="hidden" name="USER_NICKNAME" value="${NICKNAME }">
									<input type="hidden" name="GROUPNUM" value="start"> <input
										type="hidden" name="PARENT" value="0"> <input
										type="hidden" name="DEPTH" value="0"> <input
										type="hidden" name="IDENT" value="0">

									<textarea class="form-control" id="TITLE" name="TITLE" rows="2"
										cols="30" maxlength="500" placeholder="댓글을 달아주세요."
										style="background: rgb(255, 255, 255); overflow: hidden; min-height: 4em; height: 46px; width: 98%; margin-left: 3px;"></textarea>
								</div>
								<input type="submit" class="btn btn-success green" value="등록">

							</form>

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
		$(document).ready(function() {

			$(".addreply").on("click", function(e) {
				e.preventDefault();
				var idx = $(this).parent().parent().find("#PARENT").val();
				$(".rereply_"+idx).css("display","block");
				$(".updatereply_"+idx).css("display","none");
				//fn_rereply($(this));
			});
			
			$(".updatereply").on("click", function(e) {
				e.preventDefault();
				var parentElement = $(this).parent().parent();
				var idx = parentElement.find("#PARENT").val();
				$(".rereply_"+idx).css("display","none");
				$(".updatereply_"+idx).css("display","block");
				$("#upcommentbox_"+idx).find("#TITLE").text(parentElement.parent().find(".media-body").find(".TITLE").text());
			});
			
			
			$(".close").on("click", function(e) {
				e.preventDefault();
				var idx = $(this).parent().parent().find("#PARENT").val();
				var check = $(this).parent().parent().parent().attr('class');
				$(".rereply_"+idx).fadeOut("fast");
				$(".updatereply_"+idx).fadeOut("fast");
			});

		})
				
		function fn_replydelete(idx){
			
			$.ajax({
				type : "POST",
				url : "<c:url value='/board/DeleteReply.do' />",
				data : {"REPLYIDX" : idx,"IDX" : "${map.BOARD_IDX}"},
				success : function(response) {
					location.reload();
				},
				error : function() {
					alert('등록실패');
				}
			});
			
		}
		
        function fn_delete(idx){
			
			$.ajax({
				type : "POST",
				url : "<c:url value='/board/delete.do' />",
				data : {"IDX" : "${map.BOARD_IDX}"},
				success : function(response) {
					location.href = "<c:url value='/board/board.do' />";
				},
				error : function() {
					alert('삭제실패');
				}
			});
			
		}
	</script>
</body>
</html>
