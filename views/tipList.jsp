	<%@ page language="java" contentType="text/html; charset=UTF-8"
	    pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
    <link href="resources/css/tipWrite.css?after" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<title>Insert title here</title>	
	<style>
	.container-fluid {
    background: #fffbf2;
    padding-top: 80px;
    padding-bottom: 10%;
    min-width: 1200px;
    font-family: 'MinSans-Regular';
    font-size: 20px;
}

.custom-select {
    width: 10%;
    font-size: 20px;
    cursor: pointer;
    background: white;
    border: 1px solid lightgray;
    border-radius: 10px;
}

#honey_table_div {
    background: white;
    width: 70%;
    margin-left: 15%;
    padding: 20px;
    border: 1px solid lightgray;
    border-radius: 10px;
    font-weight: 100;
}

#searchButton:focus, #searchButton:active {
    outline: none;
    box-shadow: none;
}

#honey_table_div:hover {
    cursor: pointer;
}

#honeySearchArea {
    width: 40%;
    margin-left: 30%;
    height: 40px;
    font-size: 20px;
    border-radius: 10px;
    background: white;
}

#honey_write_div {
    width: 120px;
    margin-top: 40px;
    margin-bottom: 15px;
    margin-left: 78%;
}

#honey_write_button {
    width: 120px;
    border-radius: 10px;
    color: white;
    border: none;
    border-radius: 10px;
    height: 40px;
    text-decoration: none;
    font-size: 20px;
    background-color: #f24822;
}

#honey_write_button:hover {
    font-weight: bold;
    background-color: #a5250a;
}

#searchButton {
    width: 80px;
    background: #f24822;
    color: white;
    border: none;
    height: 40px;
    text-decoration: none;
    font-size: 20px;
}

#searchButton:hover {
    font-weight: bold;
    background-color: #a5250a;
}

#searchHoneyKeyword:focus, #searchHoneyKeyword:active {
    outline: none;
    box-shadow: none;
}


	</style>
	
	</head>
	<body>
	
		<jsp:include page="../common/header.jsp"/>


		<div class="container-fluid">
			
			<div id="honey_title">
	    		<p>꿀팁 게시판</p>
	  		</div>
	  		
	  		<!-- 최근 공지 출력바 -->
	  		<jsp:include page="../notice/noticeCommon.jsp"/>
	 		
			<!--꿀팁 검색--> 		
			<form>
				<div class="input-group mb-3" id="honeySearchArea">
					<select name="searchType"class="custom-select">
						<option value="title" <c:if test="${searchType eq 'title'}">selected</c:if>>제목</option>
						<option value="writer" <c:if test="${searchType eq 'writer'}">selected</c:if>>작성자</option>
						<option value="content" <c:if test="${searchType eq 'content'}">selected</c:if>>내용</option>
					</select>
			
		 			<input type="text" class="form-control" name="honeyKeyword" id="searchHoneyKeyword" placeholder="꿀팁 검색란" aria-label="Recipient's username" aria-describedby="search_button">
	
		 			<button class="btn btn-outline-secondary" id="searchButton">검색</button>
				</div>
			</form>	
		
			<!-- 글 작성 페이지 이동 버튼 -->
			<c:if test="${ !empty loginUser }">
				<div id="honey_write_div">
					<a class="btn btn-primary me-md-2" href="${contextPath }/write.tip" role="button" id ="honey_write_button">글쓰기</a>
				</div>			
			</c:if>
			
			<div id="honey_table_div">	
				<div id="honey_table">
					<table class="table table-hover ">
						<thead>
					    	<tr>
					   		   	<th scope="col" style="width: 10%; font-weight:550; text-align:center;">번호</th>
								<th scope="col" style="width: 40%; font-weight:550; text-align:center;">제목</th>
								<th scope="col" style="width: 20%; font-weight:550; text-align:center;">아이디</th>
								<th scope="col" style="width: 20%; font-weight:550; text-align:center;">작성일</th>
								<th scope="col" style="width: 10%; font-weight:550; text-align:center;">조회수</th>
					   		</tr>
						</thead>
						<tbody class="table-group-divider" style="text-align:center;">
							<c:forEach items="${ list }" var="b">
								<tr>
									<td scope="row">${b.boardNo}</td>
									<td style="text-align:left; scope="row">${b.title}</td>
									<td scope="row">${b.writer }</td>
									<td style="text-align:center;">${ b.createDate }</td>
									<td scope="row">${b.boardCount }</td>
								</tr>
							</c:forEach>    
						</tbody>
					</table>			
				</div>
			<jsp:include page="../common/pagination.jsp"/>			
			</div>
			<jsp:include page="../common/topButton.jsp"/>
		</div>
		<jsp:include page="../common/footer.jsp"/>
		<script>
		
		   window.onload = () => {
		       const tbody = document.querySelector('tbody');
		       const tds = tbody.querySelectorAll('td');
		       for(const td of tds) {
		           td.addEventListener('click', function() {
		               const boardNo = this.parentElement.querySelector('td:first-child').innerText;
		               
		               location.href = "${contextPath}/detail.tip?bNo=" + boardNo + "&page=" + '${pi.currentPage}';
		           });
		       }
		   };
		
// 		   const loud_speaker = document.getElementById("loud_speaker");
			
// 			$.ajax({
// 				url : '${contextPath}/noticeCommon.no',
// 				dataType : 'json',
// 				success: data =>{
// 					if(data != null){
// 						loud_speaker.value = data.boardTitle;
						
// 						loud_speaker.addEventListener('click', ()=>{
// 							location.href='${contextPath}/noticeSelect.no?boardNo='+data.boardNo;
// 						});
						
// 					}
// 				},
// 				error:data => console.log(data)
// 			});
			
		</script>
	
	</body>
	</html>
