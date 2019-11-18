<%--
    Document   : EnrollmentRecord
    Created on : Oct 5, 2010, 12:35:14 PM
    Author     : smomoh
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" %>
<%@page import="com.fhi.kidmap.business.*;" %>

<%
    LoadUpInfo loadup=new LoadUpInfo();
    loadup.getAllStates(session);
    loadup.setParamAttributes(request);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enrollent Register</title>
        <style type="text/css">
<!--
a {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	color: #333333;
	font-weight: bold;
}
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
	color: #000000;
}
a:hover {
	text-decoration: underline;
	color: #0075B6;
}
a:active {
	text-decoration: none;
	color: #000000;
}
.title
{
  color: #FFFFFF;
  font-weight: bold;
}
.orglabel
{
    color: #FFFFFF;
}

-->
</style>
        <script language="javascript">
        function retrieveURL3(url) {
                       if (window.XMLHttpRequest) { // Non-IE browsers
                        req = new XMLHttpRequest();
                        req.onreadystatechange = processStateChange3;
                        try {
                            req.open("GET", url, true);
                        } catch (e) {
                            alert(e);
                        }
                        req.send(null);
                    } else if (window.ActiveXObject) { // IE
                        req = new ActiveXObject("Microsoft.XMLHTTP");
                        if (req) {
                            req.onreadystatechange = processStateChange3;
                            req.open("GET", url, true);
                            req.send();
                        }
                    }


                }

                function processStateChange3() {
                    if (req.readyState == 4) 
                    { // Complete
                        alert(req.responseText)
                        if (req.status == 200)
                        { // OK response
                             document.getElementById("selected_2").innerHTML = req.responseText;
                        } 
                        else
                        {
                            alert("Problem: " + req.statusText);
                        }
                    }
                }
        function retrieveURL5(url) {
                    if (window.XMLHttpRequest) { // Non-IE browsers
                        req = new XMLHttpRequest();
                        req.onreadystatechange = processStateChange5;
                        try {
                            req.open("GET", url, true);
                        } catch (e) {
                            alert(e);
                        }
                        req.send(null);
                    } else if (window.ActiveXObject) { // IE
                        req = new ActiveXObject("Microsoft.XMLHTTP");
                        if (req) {
                            req.onreadystatechange = processStateChange5;
                            req.open("GET", url, true);
                            req.send();
                        }
                    }
                }

                function processStateChange5() {
                    if (req.readyState == 4) 
                    { // Complete
                        if (req.status == 200)
                        { // OK response
                         var resArry = req.responseText.split(":");
                            if(resArry[1] == "done") {
                                var newWindow = window.open("matchpageenrollreg.jsp","Enrolment_Register" ,"width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0");
                            }
                        } 
                        else
                        {
                            alert("Problem: " + req.statusText);
                        }
                    }
                }
            function openEnrollmentReg() {

                    var cbo = document.getElementById('cboReg').value;
                    
                    var monthFrom = document.getElementById('enrollmentRegisterMonth1').value;
                    var yearFrom = document.getElementById('enrollmentRegisterYear1').value;
                    var monthTo = document.getElementById('enrollmentRegisterMonth2').value;
                    var yearTo = document.getElementById('enrollmentRegisterYear2').value;

                    if(cbo==''||monthFrom==''||yearFrom==''||monthTo==''||yearTo=='')return;

                    var url = 'getEnrollmentRegister.do?enrollmentRegisterCbo='+cbo;
                    url += '&enrollmentRegisterMonth1='+monthFrom;
                    url += '&enrollmentRegisterYear1='+yearFrom;
                    url += '&enrollmentRegisterMonth2='+monthTo;
                    url += '&enrollmentRegisterYear2='+yearTo;

                    retrieveURL5(url);
                }
            function setActionName(val)
            {
                document.getElementById("actionName").value=val
            }
        </script>
    </head>
    <body>
        <center>
            <html:form action="/enrollmentRegister" method="post">
                <html:hidden property="actionName" styleId="actionName"/>
                <div id="enrollmentReg" style="height: 175px; width: 340px; background-color: #0075B5;">
                <div align="left; width:340px;">
                    <table style="background-color: #0075B5;">

                        <tr align="center">
                            <td colspan="6" class="title"> Enrolment Register </td>

                        </tr>

                        <tr align="left">
                            <td class="orglabel">State </td>
                            <td colspan="5">
                                <html:select styleId="stateReg" property="state" styleClass="smallfieldcellinput" style="width:273px;" onchange="setActionName('lga');forms[0].submit()"  >
                                    <html:option value=""> </html:option>
                                    <logic:iterate id="stateObj" name="states">
                                        <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                                    </logic:iterate>
                                </html:select>
                            </td>
                        </tr>

                        <tr align="left">
                            <td class="orglabel">LGA </td>
                            <td colspan="5">
                                <html:select property="lga" styleClass="smallfieldcellinput" styleId="lga" style="width:273px;" onchange="setActionName('cbo');forms[0].submit()"><html:option value="All">All</html:option>
                                <logic:iterate id="lgaObj" name="lgaList">
                        <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                        </logic:iterate>
                                </html:select> </td>
                        </tr>

                        <tr align="left">
                            <td class="orglabel">CBO </td>
                            <td colspan="5">

                                <html:select property="cbo" styleClass="smallfieldcellinput" styleId="cboReg" style="width:273px;">
                                    <html:option value="All">All</html:option>
                                <logic:iterate id="cboObj" name="cboList">
                      <html:option value="${cboObj}">${cboObj}</html:option>
                        </logic:iterate>
                                </html:select>

                            </td>

                        </tr>

                        <tr align="left">
                            <td class="orglabel">Period </td>
                            <td><select id="enrollmentRegisterMonth1" Class="smallfieldcellinput" style="width: 60px;" ><option value=" "> </option><option value="01">Jan </option><option value="02">Feb </option><option value="03">Mar </option><option value="04">Apr</option><option value="05">May</option><option value="06">Jun</option><option value="07">Jul</option><option value="08">Aug</option><option value="09">Sep</option><option value="10">Oct</option><option value="11">Nov</option><option value="12">Dec</option></select> </td>

                            <td><select id="enrollmentRegisterYear1" Class="smallfieldcellinput" style="width: 60px;" ><option value=" "> </option><option value="2009">2009 </option><option value="2010">2010 </option><option value="2011">2011 </option><option value="2012">2012 </option><option value="2013">2013 </option><option value="2014">2014</option><option value="2015">2015</option><option value="2016">2016</option><option value="2017">2017</option><option value="2018">2018</option><option value="2019">2019</option><option value="2010">2020</option></select></td>
                            <td align="center" class="orglabel"> to </td>
                            <td><select id="enrollmentRegisterMonth2" Class="smallfieldcellinput" style="width: 60px;" ><option value=" "> </option><option value="01">Jan </option><option value="02">Feb </option><option value="03">Mar </option><option value="04">Apr</option><option value="05">May</option><option value="06">Jun</option><option value="07">Jul</option><option value="08">Aug</option><option value="09">Sep</option><option value="10">Oct</option><option value="11">Nov</option><option value="12">Dec</option></select> </td>

                            <td><select id="enrollmentRegisterYear2" Class="smallfieldcellinput" style="width: 60px;" ><option value=" "> </option><option value="2009">2009 </option><option value="2010">2010 </option><option value="2011">2011 </option><option value="2012">2012 </option><option value="2013">2013 </option><option value="2014">2014</option><option value="2015">2015</option><option value="2016">2016</option><option value="2017">2017</option><option value="2018">2018</option><option value="2019">2019</option><option value="2010">2020</option></select></td>

                        </tr>


                        <tr><td>&nbsp; </td>
                            <td colspan="5"> <span id="selected2_2" style="visibility: hidden"><select Class="smallfieldcellinput" style="width:250px;"><option value=" "> </option></select></span></td>

                        </tr>
                        <tr><td colspan="6" align="center">
                                <input type="button" value="OK" id="enrollmentRegSubmitBtn" onClick="openEnrollmentReg()" style="width: 70px;" />&nbsp; <input type="button" name="cancelBtnReg" value="Cancel" onClick="hideComponentReset('enrollmentReg')" style="width: 70px;" />
                            </td></tr>

                    </table>

                </div>
            </div>
            <%--<div id="enrollmentReg" style="height: 160px; width: 340px; background-color: #0075B5;">
                <div align="left">
                    <table style="width: 340px; background-color: #0075B5;">

                        <tr align="center">
                            <td colspan="6" class="title"> Enrolment Register </td>

                        </tr>

                        <tr align="left">
                            <td class="orglabel">State </td>
                            <td colspan="5">
                                <html:select styleId="stateReg" property="state" styleClass="smallfieldcellinput" style="width:273px;" >
                                <logic:iterate id="stateObj" name="states">
                                    <html:option value="${stateObj}">${stateObj}</html:option>
                                </logic:iterate>
                                    
                                </html:select>
                            </td>
                        </tr>

                        <tr align="left">
                            <td class="orglabel">LGA </td>
                            <td colspan="5">
                                <span id="selected_2"><html:select styleClass="smallfieldcellinput" property="lga" style="width:273px;">
                                        <html:option value="All">All</html:option>
                        <logic:iterate id="lgaObj" name="lgaList">
                        <html:option value="${lgaObj}">${lgaObj}</html:option>
                        </logic:iterate></html:select></span>
                            </td>
                        </tr>

                        <tr align="left">
                            <td class="orglabel">CBO </td>
                            <td colspan="5">
                                <html:select property="cbo" styleId="cbo" style="width: 273px;" onchange="setActionName('ward'); forms[0].submit()" ><html:option value="All">
                        </html:option>
                        <logic:iterate id="cboObj" name="cboList">
                      <html:option value="${cboObj}">${cboObj}</html:option>
                        </logic:iterate>
                    </html:select>
                                

                            </td>

                        </tr>

                        <tr align="left">
                            <td class="orglabel">Period </td>
                            <td><select id="enrollmentRegisterMonth1" Class="smallfieldcellinput" style="width: 55px;" ><option value=" "> </option><option value="01">Jan </option><option value="02">Feb </option><option value="03">Mar </option><option value="04">Apr</option><option value="05">May</option><option value="06">Jun</option><option value="07">Jul</option><option value="08">Aug</option><option value="09">Sep</option><option value="10">Oct</option><option value="11">Nov</option><option value="12">Dec</option></select> </td>

                            <td><select id="enrollmentRegisterYear1" Class="smallfieldcellinput" style="width: 55px;" ><option value=" "> </option><option value="2009">2009 </option><option value="2010">2010 </option><option value="2011">2011 </option><option value="2012">2012 </option><option value="2013">2013 </option><option value="2014">2014</option><option value="2015">2015</option><option value="2016">2016</option><option value="2017">2017</option><option value="2018">2018</option><option value="2019">2019</option><option value="2010">2020</option></select></td>
                            <td align="center" class="orglabel"> to </td>
                            <td><select id="enrollmentRegisterMonth2" Class="smallfieldcellinput" style="width: 55px;" ><option value=" "> </option><option value="01">Jan </option><option value="02">Feb </option><option value="03">Mar </option><option value="04">Apr</option><option value="05">May</option><option value="06">Jun</option><option value="07">Jul</option><option value="08">Aug</option><option value="09">Sep</option><option value="10">Oct</option><option value="11">Nov</option><option value="12">Dec</option></select> </td>

                            <td><select id="enrollmentRegisterYear2" Class="smallfieldcellinput" style="width: 55px;" ><option value=" "> </option><option value="2009">2009 </option><option value="2010">2010 </option><option value="2011">2011 </option><option value="2012">2012 </option><option value="2013">2013 </option><option value="2014">2014</option><option value="2015">2015</option><option value="2016">2016</option><option value="2017">2017</option><option value="2018">2018</option><option value="2019">2019</option><option value="2010">2020</option></select></td>

                        </tr>


                        <tr><td>&nbsp; </td>
                            <td colspan="5"> <span id="selected2_2" style="visibility: hidden"><select Class="smallfieldcellinput" style="width:250px;"><option value=" "> </option></select></span></td>

                        </tr>
                        <tr><td colspan="6" align="center">
                                <input type="button" value="OK" id="enrollmentRegSubmitBtn" onClick="openEnrollmentReg()" style="width: 70px;" />&nbsp; <input type="button" name="cancelBtnReg" value="Cancel" onClick="hideComponentReset('enrollmentReg')" style="width: 70px;" />
                            </td></tr>

                    </table>

                </div>
            </div>--%>
        </html:form>
        </center>
    </body>
</html>
