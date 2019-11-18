<%-- 
    Document   : ListOfOvcPerCaregivers.jsp
    Created on : Apr 28, 2011, 11:52:36 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>List of OVC per caregiver</title>
        <link href="kidmap.css" rel="stylesheet" type="text/css" />

        <style type="text/css">
            <!--
            #Layer1 {
                position:absolute;
                width:200px;
                height:115px;
                z-index:1;
            }
            -->
        </style>
        <style>
.verticaltext
{
	writing-mode: tb-rl;
	filter: flipV flipH;
	border-right: solid black 1px;
}
.tdLine
{
	border-right: solid black 2px;
}
tr
{
	height:20px;
}
td {
	padding-left: 11px;
	padding-right: 11px;
	border-left: 1px solid black;
	border-bottom: 1px solid black;
	font-size: 11px;
	color: black;
}
th {
	padding-left: 11px;
	padding-right: 11px;
	border-left: 1px solid black;
	border-bottom: 1px solid black;
        border-top: 1px solid black;
	font-size: 11px;
	color: black;
}
table {
	border-collapse: collapse;
	margin: 10px;
}
.lab
{
    font-family:  arial, sans-serif;
    font-size: 14px;
    /*font-weight: bold;*/
    letter-spacing: 3px;
}
.orgNames
{
    font-family:  arial, sans-serif;
    font-size: 12px;
    border: none; text-align: left; width: 40px;
}
.orgNamestd
{
    border: none; text-align: left; width: 40px;
}
</style>
    </head>
    <body style="background-image:url('images/ReportBg.jpg');">

            <html:errors/>
            <center>
                <div align="center" style=" width:920px; height: 2000px; background-color: white;">
                    <br/>
                    <div>
                    <label class="lab" style="margin-right:13px;">Caregiver name: ${caregivername}</label><br>&nbsp;

                    <table border=0 style=" width: 920px;">
                    <tr><td align="left" style=" border: none;">
                <table align="left" border="0" style="border: none;">
                    <tr><td class="orgNamestd"><label class="lab">State:</label></td> <td width="25%" style=" border: none;"><label class="orgNames">${caregiverlistparam[14]} </label></td>
                    <td class="orgNamestd"><label class="lab">LGA:</label></td> <td width="30%" style=" border: none;"><label class="orgNames">${caregiverlistparam[11]} </label></td>
                    <td class="orgNamestd"><label class="lab">CBO:</label></td> <td width="35%" style=" border: none;"><label class="orgNames">${caregiverlistparam[12]} </label></td>
                    </tr>
                    <tr><td class="orgNamestd"><label class="lab"><jsp:include page="../includes/WardName.jsp"/></label></td> <td width="35%" style=" border: none;"><label class="orgNames">${caregiverlistparam[15]} </label></td><td class="orgNamestd"><label class="lab">Partner:</label></td> <td width="35%" colspan="3" style=" border: none;"><label class="orgNames">${caregiverlistparam[13]} </label></td></tr>
                </table>
                </td>
                        </tr>
                        <tr>
                            <td style=" border: none;">
                        <table
                               border="0" cellspacing="0" cellpadding="0" style="border:1px black solid; margin-bottom:40px">
                            <tr align="left" bgcolor="#D7E5F2">
                                <th>SNo</th>
                                <th>OVC Id</th>
                                <th>Surname</th>
                                <th >Other Names</th>
                                <th >Age</th>
                                <th >Gender</th>
                                <th >Address</th>
                                <th >Ward</th>
                                

                            </tr>

                            <logic:iterate id="subList" name="listofovcspercaregiver">

                                
                                <tr align="left" bgcolor="${subList[0]}">
                                    
                                <%--<tr align="left" bgcolor="#DDDDDD">--%>
                                    

                                    <td width="1%">${subList[1]}</td>
                                    <td width="10%">
                                        ${subList[2].ovcId}
                                    </td>
                                    <td width="5%">
                                     ${subList[2].lastName}
                                    </td>
                                    <td width="8%">
                                        ${subList[2].firstName}
                                    </td>
                                    <td width="2%">
                                    ${subList[2].age}
                                    </td>
                                    <td width="3%">
                                    ${subList[2].gender}
                                    </td>
                                    <td width="15%">
                                    ${subList[2].address}
                                    </td>
                                    <td width="5%">
                                    ${subList[2].ward}
                                    </td> 
                                  </tr>
                           </logic:iterate>
                                  </table>
                            </td></tr>
                            </table>

                            </div>
                </div>
                            </center>
        </body>



</html>


