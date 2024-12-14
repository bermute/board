<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f9f9f9;
        margin: 0;
        padding: 20px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }
    h1 {
        margin-bottom: 20px;
        color: #333;
    }
    button {
        padding: 10px 20px;
        background-color: #007BFF;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 1rem;
        margin-bottom: 20px;
        transition: background-color 0.3s ease;
    }
    button:hover {
        background-color: #0056b3;
    }
    table {
        width: 90%;
        max-width: 800px;
        border-collapse: collapse;
        margin-bottom: 20px;
        background-color: #fff;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
        overflow: hidden;
    }
    thead {
        background-color: #007BFF;
        color: white;
    }
    thead th {
        padding: 15px;
        text-align: left;
    }
    tbody td {
        padding: 15px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }
    tbody tr:last-child td {
        border-bottom: none;
    }
    tbody tr:hover {
        background-color: #f1f1f1;
    }
    #no-data {
        color: #555;
        margin: 20px 0;
        font-size: 1.2rem;
    }
</style>
</head>
<body>
    <jsp:include page="loginBox.jsp" />
    <h1>게시판</h1>
    <button onclick="location.href='write.go'">글쓰기</button>
    <table>
        <thead>
            <tr>
                <th>글번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>조회수</th>
                <th>작성일</th>
            </tr>
        </thead>
        <tbody id="list">
            <!-- 데이터가 없을 경우 아래 메시지 표시 -->
        </tbody>
    </table>
    <div id="no-data" style="display: none;">작성된 데이터가 없습니다.</div>
</body>
<script>
    $(document).ready(function() {
        page();
    });

    function page() {
        $.ajax({
            type: 'post',
            url: 'list.ajax',
            data: {},
            dataType: 'JSON',
            success: function(data) {
                if (data.list.length > 0) {
                    drawList(data.list);
                } else {
                    $('#list').html('');
                    $('#no-data').show();
                }
            },
            error: function(e) {
                console.error(e);
            }
        });
    }

    function drawList(list) {
        var content = '';
        list.forEach(function(view, idx) {
            content += '<tr>';
            content += '<td>' + view.idx + '</td>';
            content += '<td><a href="detail.do?idx=' + view.idx + '">' + view.subject + '</a></td>';
            content += '<td>' + view.user_name + '</td>';
            content += '<td>' + view.bHit + '</td>';
            content += '<td>' + view.reg_date + '</td>';
            content += '</tr>';
        });
        $('#list').html(content);
        $('#no-data').hide();
    }
</script>
</html>
