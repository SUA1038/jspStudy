<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
	int pageCount=0;
	void addCount(){
		pageCount++;
	}
%>

<%
	addCount();
%>

<P>이 사이트 방문은 <%=pageCount %>번째 입니다.</P>