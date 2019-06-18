<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

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
<title>Admin Area | Account Login</title>
<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
<link href="../css/boardstyle.css" rel="stylesheet">
<link href="../css/login.css" rel="stylesheet">
<link href="../css/font-awesome.css" rel="stylesheet">
<script src="http://cdn.ckeditor.com/4.6.1/standard/ckeditor.js"></script>
</head>
<body>

	<nav class="navbar navbar-default">
		<div class="container">
			<div class="navbar-header">

				<a class="navbar-brand" href="#">게시판</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse"></div>
			<!--/.nav-collapse -->
		</div>
	</nav>

	<header id="header">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="text-center">로그인 화면</h1>
				</div>
			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-md-4 col-md-offset-4">
					<form id="login" action="<c:url value='simplelogin.do' />"
						class="well">
						<div class="form-group">
							<label>아이디</label> <input type="text" name="id"
								class="form-control" placeholder="Enter Id">
						</div>
						<div class="form-group">
							<label>암호</label> <input type="password" name="password"
								class="form-control" placeholder="Enter Password">
						</div>
						<button type="submit" class="btn btn-default btn-block">Login</button>
						<a type="button" data-toggle="modal" class="btn btn-default btn-block" data-target="#addUser">회원
							가입</a> <br />

						<div id="snslogin">
							<dl class="sns_btn">
								<dd class="sns_google">
									<a href="/first/Snslogin.do?mode=google" id="google_login"
										class="fa fa-google"><strong> 구글</strong> 계정으로 로그인</a>
								</dd>
								<dd class="sns_naver">
									<a href="/first/Snslogin.do?mode=naver"><img
										src="<c:url value='/ico/naver_Green.PNG' />"
										style="height: auto; width: 28px;"><strong> 네이버</strong>
										계정으로 로그인</a>
								</dd>
								<!--  <dd class="sns_facebook">
									<a href="/first/Snslogin.do?mode=face" class="fa fa-facebook"><strong>
											페이스북</strong> 계정으로 로그인</a>
								</dd>
								-->
								<dd class="sns_insta">
									<a href="/first/Snslogin.do?mode=insta" class="fa fa-instagram"><strong>
											인스타그램</strong> 계정으로 로그인</a>
								</dd>
								
								<dd class="sns_kakao">
									<a href="/first/Snslogin.do?mode=kakao"><img
										src="<c:url value='/ico/kakao.png' />"
										style="height: auto; width: 25px;"><strong> 카카오</strong>
										계정으로 로그인</a>
								</dd>
							</dl>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>

	<footer id="footer">
		<p>Copyright AdminStrap, &copy; 2017</p>
	</footer>
	<!--  modal -->
	<div class="modal fade" id="addUser" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="frmUser" name="frmUser" class="frmUser">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">회원 가입</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label>아이디</label> <input type="text" class="form-control"
								placeholder="아이디" id="user_id" name="user_id">
						</div>
						<div class="form-group">
							<label>이메일</label> <input type="text" class="form-control"
								placeholder="이메일" id="email" name="email">
						</div>
						<div class="form-group">
							<label>암호</label> <input type="password" class="form-control"
								placeholder="암호" id="password" name="password">
						</div>
						<div class="form-group">
							<label>이름</label> <input type="text" class="form-control"
								placeholder="이름" id="username" name="username">
						</div>
						<div class="form-group">
							<label>닉네임</label> <input type="text" class="form-control"
								placeholder="닉네임" id="nickname" name="nickname">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
						<button type="submit" class="btn btn-primary">회원가입</button>
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
	$(document).ready(function() {
		
	    
		$('#frmUser').submit(function(e) {
			e.preventDefault();
			 alert("감지2");
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
				alert("암호는 5~20자 만 사용 가능합니다.");
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
						url : "<c:url value='/login/insertuser.do' />",
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
