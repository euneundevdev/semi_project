<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link href="honeyMain2.css" rel="stylesheet" type="text/css"/>
<title>Insert title here</title>
<style>

</style>

</head>
<body>
	<div class="container-fluid">
		
		
		<div id="honey_title">
    		<p>꿀팁 게시판</p>
  		</div>
	<br>
  		
  		<!-- 최근 공지 출력바  -->
	<div id="notice_recent">
  		 <input class="form-control" id="loud_speaker" type="text" value="최근 공지사항이에요." aria-label="readonly input example" readonly>
	</div>
  		
  		<br>
 		
	<!-- 공지사항 검색--> 		
	<form>
		<div class="input-group mb-3" id="search_receipe">
 			<input type="text" class="form-control" name="honeyKey" id="search_receipe2" placeholder="공지사항 검색" aria-label="Recipient's username" aria-describedby="search_button">
 			<button class="btn btn-outline-secondary" id="search_button">검색</button>
		</div>
	</form>	
	
	<!-- 글 작성 페이지 이동 버튼 -->
	<div id="honey_write_div">
		<a class="btn btn-primary me-md-2" href="honeyWritePage.html" role="button" id ="honey_write_button">글쓰기</a>
	</div>			

	<div id="honey_table_div">	
		<div id="honey_table">
			<table class="table table-hover ">
			  <thead>
			    <tr>
			      <th scope="col" style="width: 10%; font-weight:550; text-align:center;">번호</th>
			      <th scope="col" style="width: 40%; font-weight:550; text-align:center;">공지사항</th>
			      <th scope="col" style="width: 30%; font-weight:550; text-align:center;">작성일</th>
			    </tr>
			  </thead>
			  <tbody class="table-group-divider" style="text-align:center;">
			    <tr>
			      <td scope="row">&nbsp10</td>
			      <td style="text-align:left;">&nbspMark</td>
			      <td>&nbspOtto</td>
			    </tr>
			    <tr>
			      <td scope="row">&nbsp9</td>
			      <td style="text-align:left;">&nbspJacob</td>
			      <td>&nbspThornton</td>
			    </tr>
			    <tr>
			      <td scope="row">&nbsp8</td>
			      <td style="text-align:left;">&nbspJacob</td>
			      <td>&nbspThornton</td>
			    </tr>
				<tr>
			      <td scope="row">&nbsp7</td>
			      <td style="text-align:left;">&nbspJacob</td>
			      <td>&nbspThornton</td>
			    </tr>
			    <tr>
			      <td scope="row">&nbsp6</td>
			      <td style="text-align:left;">&nbspJacob</td>
			      <td>&nbspThornton</td>
			    </tr>
			    <tr>
			      <td scope="row">&nbsp5</td>
			      <td style="text-align:left;">&nbspacob</td>
			      <td>&nbspThornton</td>
			    </tr>
			    <tr>
			      <td scope="row">&nbsp4</td>
			      <td style="text-align:left;">&nbspJacob</td>
			      <td>&nbspThornton</td>
			    </tr>
			    <tr>
			      <td scope="row">&nbsp3</td>
			      <td style="text-align:left;">&nbspJacob</td>
			      <td>&nbspThornton</td>
			    </tr>
			    <tr>
			      <td scope="row">&nbsp2</td>
			      <td style="text-align:left;">&nbspJacob</td>
			      <td>&nbspThornton</td>
			    </tr>
			    <tr>
			      <td scope="row">&nbsp1</td>
			      <td style="text-align:left;">&nbspJacob</td>
			      <td>&nbspThornton</td>
			    </tr>
			  </tbody>
			</table>			
		</div>
		
		<div class="mx-auto" id="page_navigation" style="width: 210px;">
			  <ul class="pagination pagination-sm">
			    <li class="page-item"><a class="page-link" href="#">이전</a></li>
			    <li class="page-item"><a class="page-link" href="#">1</a></li>
			    <li class="page-item"><a class="page-link" href="#">2</a></li>
			    <li class="page-item"><a class="page-link" href="#">3</a></li>
			    <li class="page-item"><a class="page-link" href="#">4</a></li>
			    <li class="page-item"><a class="page-link" href="#">5</a></li>
			    <li class="page-item"><a class="page-link" href="#">다음</a></li>
			  </ul>
		</div>				
	</div>	
	
	
	<!-- top이동바  -->
	<a class="btn" href="#hearder" role="button" id ="go_top_button">
		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-caret-up-fill" viewBox="0 0 16 16">
 			<path d="m7.247 4.86-4.796 5.481c-.566.647-.106 1.659.753 1.659h9.592a1 1 0 0 0 .753-1.659l-4.796-5.48a1 1 0 0 0-1.506 0z"/>
		</svg>
		TOP
	</a>

	
</div>
	
				
				
				
		

</body>
</html>
