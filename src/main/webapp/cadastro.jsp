<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<body>
    <c:import url="cabecalho.jsp" />
    <%
        String message = "Bem vindo";
    %>
    <% out.print(message); %>
    <%
        String desenvolvido = "desenvolvido por Ada Lovelace";
    %>
    <%= desenvolvido %>
    <h2>Lista de Contatos</h2>
    <table border="1">
        <tr>
            <th>Nome</th>
            <th>Email</th>
            <th>Endereço</th>
            <th>Data de Nascimento</th>
            <th>Ação</th>
        </tr>
        <c:forEach var="contato" items="${contatos}">
            <tr>
                <td>${contato.nome}</td>
                <td>
                    <c:if test="${not empty contato.email}">
                        <a href="mailto:${contato.email}">${contato.email}</a>
                    </c:if>
                    <c:if test="${empty contato.email}">
                        Email não informado
                    </c:if>
                </td>
                <td>${contato.endereco}</td>
                <td><fmt:formatDate value="${contato.dataNascimento.time}" pattern="dd/MM/yyyy"/></td>
                <td>
                    <a href="mvc?logica=RemoveContatoLogica&id=${contato.id}"
                       onclick="return confirm('Tem certeza que deseja excluir este contato?')">Remover</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="adicionarContato">Adicionar novo contato</a>
    <c:import url="rodape.jsp"/>
</body>
</html>