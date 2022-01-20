package com.instagram.Controller;

import java.io.File;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;



import org.json.JSONObject;

import com.instagram.Entity.instagramImage;
import com.instagram.Entity.instagramUser;
import com.instagram.Service.instagramServiceInterface;
import com.instagram.Utiler.ServiceFactory;


/**
 * Servlet implementation class UserController
 */


//@WebServlet("/UserController")
//@MultipartConfig(fileSizeThreshold=1024*1024*2,maxFileSize=1024*1024*10,maxRequestSize=1024*1024*50)
//@MultipartConfig
public class UserController extends HttpServlet {
	
	HttpSession hs;
	RequestDispatcher rd;
	//public String useridcheck="";
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String ch=request.getParameter("method");
		
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<html><body>");
		
		instagramServiceInterface is=ServiceFactory.createObject();
		
		
	System.out.println(ch);	
		if(ch.equals("signUp"))
		{
			System.out.println("GreatWork");
			
			String email=request.getParameter("email");
			String name=request.getParameter("fullname");
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			
			instagramUser iu=new instagramUser();
			
			iu.setEmail(email);
			iu.setFullname(name);
			iu.setUsername(username);
			iu.setPassword(password);
			
			
			int i=is.createProfileService(iu);
			
			if(i>0) {
				
				out.println("Sign Up Successful");
				 hs=request.getSession(true);
					hs.setAttribute("userid",email);
					hs.setAttribute("password",password );
					hs.setAttribute("username", username);
				
				
				RequestDispatcher rd=getServletContext().getRequestDispatcher("/HomePage.html");
				rd.forward(request, response);
			}
			else {
				out.println("could not create profile");
				RequestDispatcher rd=getServletContext().getRequestDispatcher("/signUp.html");
				rd.forward(request, response);
			}
	
		}
		
		if(ch.equals("signIn"))
		{
			System.out.println("signed in");
			
				String email=request.getParameter("email");
				String password=request.getParameter("password");
				
				
				instagramUser iu=new instagramUser();
				
				iu.setEmail(email);
				iu.setPassword(password);
				
				
				
				int i=is.createLoginService(iu);
				
				if(i>0)
				{
					out.println("Login Successful");
//					useridcheck=email;
					
					 hs=request.getSession(true);
					hs.setAttribute("userid",email);
					hs.setAttribute("password",password );
					
					
					 rd=getServletContext().getRequestDispatcher("/HomePage.html");
					rd.forward(request, response);
						
					}
				
				else
				{
					out.println("Invalid Username and Password");
					
					rd=getServletContext().getRequestDispatcher("/signUp.html");
					rd.forward(request,response);
					out.println("<br/> <a href='signUp.html'>GOBACK</a>");
				}
		}	
		
		
		
		if(ch.equals("viewProfile"))
		{
			
		System.out.println("viewing");
		
		  hs=request.getSession(true);
		 String email=hs.getAttribute("userid").toString();
		 
		 System.out.println(email);
		 
		 instagramUser iu=new instagramUser();
		 iu.setEmail(email);
		 
		 instagramUser iuser=is.viewProfileService(iu);
		
		 if(iuser!=null)
		 {
			 
			 out.println("<!DOCTYPE html>\r\n" + 
			 		"<html>\r\n" + 
			 		"<head>\r\n" + 
			 		"<meta charset=\"ISO-8859-1\">\r\n" + 
			 		"<title>Creating</title>\r\n" + 
			 		"\r\n" + 
			 		"<link rel=\"stylesheet\" href=\"createProfile.css\">\r\n" + 
			 		"<link rel=\"icon\" type=\"image/png\" href=\"https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Instagram_icon.png/2048px-Instagram_icon.png\">\r\n" + 
			 		"\r\n" + 
			 		"<style>\r\n" + 
			 		":root {\r\n" + 
			 		"    font-size: 10px;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		"*,\r\n" + 
			 		"*::before,\r\n" + 
			 		"*::after {\r\n" + 
			 		"    box-sizing: border-box;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		"body {\r\n" + 
			 		"    font-family: \"Open Sans\", Arial, sans-serif;\r\n" + 
			 		"    min-height: 100vh;\r\n" + 
			 		"    background-color: #fafafa;\r\n" + 
			 		"    color: #262626;\r\n" + 
			 		"    padding-bottom: 3rem;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		"img {\r\n" + 
			 		"    display: block;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".container {\r\n" + 
			 		"    max-width: 93.5rem;\r\n" + 
			 		"    margin: 0 auto;\r\n" + 
			 		"    padding: 0 2rem;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".btn {\r\n" + 
			 		"    display: inline-block;\r\n" + 
			 		"    font: inherit;\r\n" + 
			 		"    background: none;\r\n" + 
			 		"    border: none;\r\n" + 
			 		"    color: inherit;\r\n" + 
			 		"    padding: 0;\r\n" + 
			 		"    cursor: pointer;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".btn:focus {\r\n" + 
			 		"    outline: 0.5rem auto #4d90fe;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".visually-hidden {\r\n" + 
			 		"    position: absolute !important;\r\n" + 
			 		"    height: 1px;\r\n" + 
			 		"    width: 1px;\r\n" + 
			 		"    overflow: hidden;\r\n" + 
			 		"    clip: rect(1px, 1px, 1px, 1px);\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		"/* Profile Section */\r\n" + 
			 		"\r\n" + 
			 		".profile {\r\n" + 
			 		"    padding: 5rem 0;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile::after {\r\n" + 
			 		"    content: \"\";\r\n" + 
			 		"    display: block;\r\n" + 
			 		"    clear: both;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-image {\r\n" + 
			 		"    float: left;\r\n" + 
			 		"    width: calc(33.333% - 1rem);\r\n" + 
			 		"    display: flex;\r\n" + 
			 		"    justify-content: center;\r\n" + 
			 		"    align-items: center;\r\n" + 
			 		"    margin-right: 3rem;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-image img {\r\n" + 
			 		"    border-radius: 50%;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-user-settings,\r\n" + 
			 		".profile-stats,\r\n" + 
			 		".profile-bio {\r\n" + 
			 		"    float: left;\r\n" + 
			 		"    width: calc(66.666% - 2rem);\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-user-settings {\r\n" + 
			 		"    margin-top: 1.1rem;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-user-name {\r\n" + 
			 		"    display: inline-block;\r\n" + 
			 		"    font-size: 3.2rem;\r\n" + 
			 		"    font-weight: 300;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-edit-btn {\r\n" + 
			 		"    font-size: 1.4rem;\r\n" + 
			 		"    line-height: 1.8;\r\n" + 
			 		"    border: 0.1rem solid #dbdbdb;\r\n" + 
			 		"    border-radius: 0.3rem;\r\n" + 
			 		"    padding: 0 2.4rem;\r\n" + 
			 		"    margin-left: 2rem;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-settings-btn {\r\n" + 
			 		"    font-size: 2rem;\r\n" + 
			 		"    margin-left: 1rem;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-stats {\r\n" + 
			 		"    margin-top: 2.3rem;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-stats li {\r\n" + 
			 		"    display: inline-block;\r\n" + 
			 		"    font-size: 1.6rem;\r\n" + 
			 		"    line-height: 1.5;\r\n" + 
			 		"    margin-right: 4rem;\r\n" + 
			 		"    cursor: pointer;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-stats li:last-of-type {\r\n" + 
			 		"    margin-right: 0;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-bio {\r\n" + 
			 		"    font-size: 1.6rem;\r\n" + 
			 		"    font-weight: 400;\r\n" + 
			 		"    line-height: 1.5;\r\n" + 
			 		"    margin-top: 2.3rem;\r\n" + 
			 		"}\r\n" + 
			 		"\r\n" + 
			 		".profile-real-name,\r\n" + 
			 		".profile-stat-count,\r\n" + 
			 		".profile-edit-btn {\r\n" + 
			 		"    font-weight: 600;\r\n" + 
			 		"}\r\n" + 
			 		"</style>\r\n" + 
			 		"<script>\r\n" + 
			 		"\r\n" + 
			 		"/*function loadViewData(){\r\n" + 
			 		"	fetch('UserController?method=viewProfile')\r\n" + 
			 		"	 .then(response => response.json())\r\n" + 
			 		"	 .then(data => {\r\n" + 
			 		"	 console.log(data.username);\r\n" + 
			 		"	// console.log(manoj);\r\n" + 
			 		"	 document.getElementById('name').innerHTML=\"<b>\"+data.username+\"</b>\";\r\n" + 
			 		"	 \r\n" + 
			 		"	 });\r\n" + 
			 		"	}*/\r\n" + 
			 		"\r\n" + 
			 		"\r\n" + 
			 		"\r\n" + 
			 		"</script>\r\n" + 
			 		"</head>\r\n" + 
			 		"<body >\r\n" + 
			 		"\r\n" + 
			 		"<header>\r\n" + 
			 		"\r\n" + 
			 		"	<div class=\"container\">\r\n" + 
			 		"\r\n" + 
			 		"		<div class=\"profile\">\r\n" + 
			 		"\r\n" + 
			 		"			<div class=\"profile-image\">\r\n" + 
			 		"\r\n" + 
			 		"				<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAPUAAADOCAMAAADR0rQ5AAAAIVBMVEXn5+f////8/Pzl5eXp6enz8/P5+fnu7u7y8vL29vbs7Ozrxq8QAAAFP0lEQVR4nO2d4ZajIAyFKwQE3/+BR7SOttVWK4Gbmu+c3fmxc3q8m3BJotLbTVEURVEURVEURVEURVEUSKj2BfBj+z/UTVxAcKILLnpjmhFjfHSh/WntXYiT3Cdi6GpfHA+t8+uK73jX1r7E3FDrN6K8wPhfSnW6te/DvAh4+zOGvlvzXfcv0MUDmhNRvLHZmzuoOeGGTV0uh5L7jpGe5uGzcW8oD7Uv/VvodnRFL4lCvZzOiBYq25Jvvs3vhGk8yfM0+sLHnvDCok05RA+yRQk/md4jRli0TxrZjCRLs98UZOs4OY4WsoluGjHlCp1f0jNGSI5nse8ZGY5mc+Z3QsTSzprfCSNgfJw5vxOxtqYP9EFps4tuGvhhGkOoBRhaxyA6BRsbjlD3wa4t6z08oW4a7LFprq7jGWgbz75XT0DXpRzb1giyn3ElOHSKsyU4dIrzJThwilu+BO9THLbz4kvwPsVri9uCGEU3DerC5lzWuAs732R0DVdb3gacZga7Y7O01jOgTfZFVXNuXLDV2UVVs4pG3bBVtar+cdXXdLNrqr5mlcKsura+DS7ZfVy008z9kMIjqI/ltKxzM9RZCuvWBbpx3bhu446gWjivnaGaGd/d6wTqsmZd2KCV2fAAEuM9TdgbPle9f33NZxXYXBzXwRNc8xToUJ97cW0b7FAz1eKwNfgI8QQ7pg+GhsHGsQ18IOP7PRMSXgagHedGHAK2GH0gt6FhW9k/eXMcfde6Q1mnCx797Yd/unw5brCfDH8gX+8lZFGP5BoSo46D16E8juakrOmJHLKF2PeS87IFiran13aQeS7QOdmyjGzBidoUvKXehk7cyE8dhzT/XuC+CbcR6GML6Ga74+H2nZUc6AE6uLrNbxzZaOlAmhsn8MynNZIx7dRtnGwXe4RuFD4PT2MQdtrTDoiC2Y64MQF9+vsVSVMf8ZVZovEpyvJ5o4Goa3vt3htj+r9jaLt3QZb1v/Hhaq1d/pBPMqQutjmilE5fSQfRSog3DSev5th2LaWuTcQBvGMFavqyMtNHwR9o12f3fBj82YpjUdmkk2iBlT+W3Kd65KePwq1f6OXUfz+62pELHn/39VTmNE9CFL7aU/rW2mMX29vg6knUmK62NSU7etj/ZmeawR/zQm8HwcYN1/tpKxv+vXvTncHN0j4du+p3FdoUPgxesCZLn6aCKX4+9qm+GW+iNvr7bwqRvXsUamJIvUaKe1+CD1U4pX5k8ztfXogwtwb2PhY+Kktf3RNdGHHx/0t9dupGKdQy3qPfJRti2y4sGuN1NuZ3Ul8xAJbG/HLmOrUtLdvx6Acwle93EvOrmdtUrdJ438x8g+nqOTnvIVfvZVcTXdy+l1Tav2wV+56p42gMD4Ifo62xfVVc1CM1lnbxQvSV8kub9RTOnZhQevuqnt+J0o9RV920ZormONcrescpWplC5Hei6Ps/deuTJQV77WpNxyvlZuQgVjZSzNBql6KPlClMLUBVtqTQ8TEou9ZEKBFsqFWdKHGeusXZtSYKdNrc529+A38TgraqE45bNmKo+ecLWHv1BLONwxn4CG8Tkv1bI3PBWo1n+apjDlircZi++hnWWRLitjXCWKmAelmCcfPC3LZG+L5pFK8En+HyMwuc4CnFWSqVeg8m7IPr9U7kBGdLccjGY4bJxWFLlDs8VSn2sua6IYC9rJmGpdD7VoJlYaMva56FjdpazzB0IDWeGD2IY3ioFt3MeOzskqrhLZzBxAm9Hh3Ifx5F1xh4OpS3nhRFURRFURRFURRFURRFkcIf/Z9LlKH9beIAAAAASUVORK5CYII=\" alt=\"\">\r\n" + 
			 		"\r\n" + 
			 		"			</div>\r\n" + 
			 		"\r\n" + 
			 		"			<div class=\"profile-user-settings\">\r\n" + 
			 		"			\r\n" + 
			 		"			<form action=\"UserController?method=viewProfile\" method=\"post\">\r\n" + 
			 		"\r\n" + 
			 		"				<h1 class=\"profile-user-name\" id=\"name\">"+iuser.getUsername()+"</h1>\r\n" + 
			 		"		\r\n" + 
			 		"			</form>\r\n" + 
			 		"			\r\n" + 
			 		"				<a href=\"editProfile.html\">\r\n" + 
			 		"				<button  class=\"btn profile-edit-btn\">Edit Profile</button>\r\n" + 
			 		"				</a>\r\n" + 
			 		"				<button class=\"btn profile-settings-btn\" aria-label=\"profile settings\"><i class=\"fas fa-cog\" aria-hidden=\"true\"></i></button>\r\n" + 
			 		"\r\n" + 
			 		"			</div>\r\n" + 
			 		"\r\n" + 
			 		"			<div class=\"profile-stats\">\r\n" + 
			 		"\r\n" + 
			 		"				<ul>\r\n" + 
			 		"					<li><span class=\"profile-stat-count\">0</span> posts</li>\r\n" + 
			 		"					<li><span class=\"profile-stat-count\">0</span> followers</li>\r\n" + 
			 		"					<li><span class=\"profile-stat-count\">0</span> following</li>\r\n" + 
			 		"				</ul>\r\n" + 
			 		"\r\n" + 
			 		"			</div>\r\n" + 
			 		"\r\n" + 
			 		"			<div class=\"profile-bio\">\r\n" + 
			 		"\r\n" + 
			 		"				<p><span class=\"profile-real-name\">hey there!</span> Im Great</p>\r\n" + 
			 		"\r\n" + 
			 		"			</div>\r\n" + 
			 		"\r\n" + 
			 		"		</div>\r\n" + 
			 		"		<!-- End of profile section -->\r\n" + 
			 		"\r\n" + 
			 		"	</div>\r\n" + 
			 		"	<!-- End of container -->\r\n" + 
			 		"\r\n" + 
			 		"</header>");
			 
			 
			 
			 //out.println("Welcome"+);
			 //out.println("<br/><a href='createProfile.html'>Continue To EDIT</a><br/><br/><br/>");
			 //out.println("<br/><a href='HomePage.html'>GO BACK</a>");
			 out.println("<center>");
			 out.println(" <a href='HomePage.html'>\r\n" + 
			 		"        <button class=\"GFG\">\r\n" + 
			 		"            GO BACK\r\n" + 
			 		"        </button>\r\n" + 
			 		"    </a>");
			 out.println("</center>");
		 }
		 else
		 {
			 out.println("View Failed");
			 out.println("<br/><a href='HomePage.html'>GO BACK</a><br/>");
		 }
		 
		 
//			instagramUser iuser=is.viewProfileService(hs.getAttribute("userid").toString(),hs.getAttribute("password").toString());
//			JSONObject jsonObject = new JSONObject(iuser);
//			response.setContentType("application/json");
//			out=response.getWriter();
//			out.print(jsonObject);
		}
		
		if(ch.equals("deleteProfile"))
		{
			System.out.println("Deletion");
			
			 hs=request.getSession(true);
			String email=hs.getAttribute("userid").toString();
			
			System.out.println(email);
			 
			instagramUser iu=new instagramUser();

			iu.setEmail(email);
			
			int i=is.deleteProfileService(iu);
			
			if(i>0)
			{
				RequestDispatcher rd=getServletContext().getRequestDispatcher("/signUp.html");
				rd.forward(request, response);
//				out.println("<html><body><center>");
//				out.println("profile deleted");
//				out.println("<br/><a href='signUp.html'>GoBACK</a>");
//				out.println("</center></body></html>");
			}
			else 
			{
				out.println("deletion failed");
		
				out.println("<center>");
				 out.println(" <a href='HomePage.html'>\r\n" + 
					 		"        <button class=\"GFG\">\r\n" + 
					 		"            GO BACK\r\n" + 
					 		"        </button>\r\n" + 
					 		"    </a>");
					 out.println("</center>");
				
			}
			
			
		}
		
		if(ch.equals("search"))	
		{
			
			System.out.println("Searching Started");
			
//			 hs=request.getSession(true);
//				String username1=hs.getAttribute("username").toString();
			
			
			String username=request.getParameter("username");
			
			instagramUser iu=new instagramUser();
			
			iu.setUsername(username);
			
//			instagramUser iuser=is.searchProfileService(hs.getAttribute("email").toString(), hs.getAttribute("password").toString());
//			JSONObject jsonObject = new JSONObject(iuser);
//			response.setContentType("application/json");
//			out=response.getWriter();
//			out.print(jsonObject);
			
		List<instagramUser> i2=is.searchProfileService(iu);
		
		if(i2.size()>0)
		{
			
			out.println("<br/>UserNames related to your Searches are<br/>");
			for(instagramUser ii:i2)
			{
				
				
out.println("<!DOCTYPE html>\r\n" + 
		"<html>\r\n" + 
		"<body style=\"background-color:lightgrey;\">\r\n" + 
		"\r\n" + 
		"<h3 style=\"color:blue;\"><br>"+ii.getUsername()+"<br></h3>\r\n" + 
		"<p style=\"color:red;\"></p>\r\n" + 
		"\r\n" + 
		"</body>\r\n" + 
		"</html>");
				
				out.println("<=============================================>");
			
			}
			 out.println("<center>");
			 out.println(" <a href='HomePage.html'>\r\n" + 
			 		"        <button class=\"GFG\">\r\n" + 
			 		"            GO BACK\r\n" + 
			 		"        </button>\r\n" + 
			 		"    </a>");
			 out.println("</center>");
			//out.println("<br/><a href='HomePage.html'>GoBACK</a>");
			
			//out.println("<br>Your Username is "+email+"  and password is "+password);
		}
		else
		{
			out.println("No Related Searches Found");
			out.println("<center>");
			 out.println(" <a href='HomePage.html'>\r\n" + 
			 		"        <button class=\"GFG\">\r\n" + 
			 		"            GO BACK\r\n" + 
			 		"        </button>\r\n" + 
			 		"    </a>");
			 out.println("</center>");
			//out.println("<br/><a href='signUp.html'>GoBACK</a>");
		}
		
		}
		
		
		if(ch.equals("viewAllProfile"))	
		{
			
			System.out.println("viewALL Started");
		
			
//			instagramUser iuser=is.searchProfileService(hs.getAttribute("email").toString(), hs.getAttribute("password").toString());
//			JSONObject jsonObject = new JSONObject(iuser);
//			response.setContentType("application/json");
//			out=response.getWriter();
//			out.print(jsonObject);
			
		List<instagramUser> i2=is.viewAllProfileService();
		
		if(i2.size()>0)
		{
			out.println("Showing ALL Profiles<br/>");
			for(instagramUser ii:i2)
			{
				out.println("<==========================================================>");
				
				out.println("<!DOCTYPE html>\r\n" + 
						"<html>\r\n" + 
						"<body style=\"background-color:lightgrey;\">\r\n" + 
						"\r\n" + 
						"<h3 style=\"color:blue;\"><br><br> Your Email is:"+ii.getEmail()+"<br></h3>\r\n" + 
						"<h3 style=\"color:red;\"><br> Your Username is:"+ii.getUsername()+"<br></h3>\r\n" + 
						"<h3 style=\"color:green;\"><br><br> Your Email is:"+ii.getFullname()+"<br></h3>\r\n" + 
						"\r\n" + 
						"</body>\r\n" + 
						"</html>");
								
							
				out.println("<============================================================>");
			}
			//out.println("<br/><a href='HomePage.html'>GoBACK</a>");
			//out.println("<br>Your Username is "+email+"  and password is "+password);
			out.println("<center>");
			 out.println(" <a href='HomePage.html'>\r\n" + 
			 		"        <button class=\"GFG\">\r\n" + 
			 		"            GO BACK\r\n" + 
			 		"        </button>\r\n" + 
			 		"    </a>");
			 out.println("</center>");
		}
		else
		{
			out.println("ViewAll Failed");
			//out.println("<br/><a href='signUp.html'>GoBACK</a>");
			out.println("<center>");
			 out.println(" <a href='HomePage.html'>\r\n" + 
			 		"        <button class=\"GFG\">\r\n" + 
			 		"            GO BACK\r\n" + 
			 		"        </button>\r\n" + 
			 		"    </a>");
			 out.println("</center>");
		}
		
		}


		
		
		
		
		

		if(ch.equals("editProfile"))
		{
			System.out.println("Editing");
			
			
			
				String email=hs.getAttribute("userid").toString();
//			
//			Part part=request.getPart("file");
			
			
			//String username=request.getParameter("username");
			
			
			
			
			
//			InputStream it=part.getInputStream();
//			byte[] data=new byte[it.available()];
//			
//			it.read(data);
//			
//			 String path= "D:\\Front-End";
//			path+="\\"+username;
//			
//			File fol = new File(path); 
//			System.out.println(fol.mkdirs());
//		
//			
//			String image ="nature3.jpg";
//			String file=path+File.separator+image;
//			System.out.println(file);
//			
//			File myPostFile = new File(file);
//		
//			FileOutputStream fos=new FileOutputStream(myPostFile);
//			fos.write(data);
//			
	//		iu.setUserimg(image);
			
			instagramUser iu=new instagramUser();
			
			iu.setEmail(email);
//			System.out.println(useridcheck);
			iu.setFullname(request.getParameter("name"));
			iu.setUsername(request.getParameter("username"));
			iu.setGender(request.getParameter("gender"));
			iu.setMobile(request.getParameter("phno"));
			
			
			
			
			int result=is.editProfileService(iu);
			
		
			
			if(result>0)
			{
				out.println("Updated DATA <br/><br/>");
				out.println("<!DOCTYPE html>\r\n" + 
						"<html>\r\n" + 
						"<body style=\"background-color:lightgrey;\">\r\n" + 
						"\r\n" + 
						"<h3 style=\"color:blue;\"><br><br> Your  Username is:"+iu.getUsername()+"<br></h3>\r\n" + 
						"<h3 style=\"color:red;\"><br> Your Name is:"+iu.getFullname()+"<br></h3>\r\n" + 
						"<h3 style=\"color:green;\"><br><br> Your Gender is:"+iu.getGender()+"<br></h3>\r\n" + 
						"<h3 style=\"color:yellow;\"><br><br> Your Mobile Number is:"+iu.getMobile()+"<br></h3>\r\n" + 
						"\r\n" + 
						"</body>\r\n" + 
						"</html>");
				
				
				
				
				out.println("<center>");
				 out.println(" <a href='HomePage.html'>\r\n" + 
				 		"        <button class=\"GFG\">\r\n" + 
				 		"            GO BACK\r\n" + 
				 		"        </button>\r\n" + 
				 		"    </a>");
				 out.println("</center>");
//				out.println("<br/><a href='HomePage.html'>GoBACK</a>");
//				out.println("<html><body><center>");
//				out.println("<font size=5 color=grey><b>Profile Updated</b></font>");
//				out.println("<br>your user name  is "+iu.getEmail());
//				out.println("</center></body></html>");
				
			}
			
			else
			{
				out.println("could not edit<br/>");
				
				out.println("<center>");
				 out.println(" <a href='HomePage.html'>\r\n" + 
				 		"        <button class=\"GFG\">\r\n" + 
				 		"            GO BACK\r\n" + 
				 		"        </button>\r\n" + 
				 		"    </a>");
				 out.println("</center>");
				//out.println("<br/><a href='signUp.html'>GoBACK</a>");
			}
			
			
			
			
				
//				if(i4>0)
//				{
//					out.println("Profile Edited");
//					
//					ServletContext c = this.getServletContext();
//					String path = c.getRealPath("/");
//					File f = new File(path + "/D:/" + email + "/Front-End/");
//					f.mkdirs();
//
//					File f1 = new File(path + "/user/images/nature.jpg");
//					File f2 = new File(path + "/D:/" + email + "/profilePhoto/nature3.jpg");
//					f1.renameTo(f2);
//				}
		}

		if(ch.equals("uploadphoto")) {
			HttpSession hs=request.getSession(true);
			String email=hs.getAttribute("userid").toString(); 
			out.println(" <h1 style='text-align:center;color:blue;'>Upload Photo</h1>");
			out.println("  <br><br><p style=\"text-align: left;\">  <font size=5 color=black><b> Welcome " +email+"</b></font></p> ");
				out.println("<html><body> <style type=\"text/css\">"
						+ ""
						+ "body{\r\n" + 
						"  text-align:center;\r\n" + 
						"  vertical-align:center;\r\n" + 
						"  background: linear-gradient(to right, #c6ffdd, #fbd786, #f7797d);\r\n" + 
						"}</style></html></body>");
					 
			}


		
			if(ch.equals("logoutProfile"))
			{
				hs.invalidate();
//				useridcheck="";
				rd=getServletContext().getRequestDispatcher("/signUp.html");
				rd.forward(request, response);
				
			}

		
		
		out.println("</html></body>");
	}

	
}
