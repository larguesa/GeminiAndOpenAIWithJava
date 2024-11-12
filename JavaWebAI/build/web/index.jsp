<%-- 
    Document   : index
    Created on : 29 de out. de 2024, 11:29:10
    Author     : rlarg
--%>

<%@page import="ai.OpenAI"%>
<%@page import="ai.Gemini"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%
    if(request.getParameter("invoke")!=null){
        try{
            String prompt = request.getParameter("prompt");
            if(request.getParameter("model").equals("gemini")){
                request.setAttribute("completion", Gemini.getCompletion(prompt));
            }else{
                request.setAttribute("completion", OpenAI.getCompletion(prompt));
            }
        }catch(Exception ex){
            request.setAttribute("error", ex.getMessage());
        }
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Meu ChatGPT</title>
    </head>
    <body>
        <h1>Meu Próprio ChatGPT</h1>
        
        <% if(request.getAttribute("error") != null) { %>
        <div>ERRO: <%= request.getAttribute("error") %></div>
        <hr/>
        <% } else if(request.getAttribute("completion") != null) { %>
        <h2>Pergunta</h2>
        <div><%= request.getParameter("prompt") %></div>
        <h2>Resposta da IA</h2>
        <div><%= request.getAttribute("completion") %></div>
        <hr/>
        <% } %>
        
        <form>
            <select name="model">
                <option value="gemini">Gemini</option>
                <option value="open-ai">OpenAI</option>
            </select>
            <input type="text" name="prompt" style="width: 80%"/>
            <input type="submit" name="invoke" value="Enviar"/>
        </form>
        <img src="images/image.png" alt="" width="100%"/>
    </body>
</html>
