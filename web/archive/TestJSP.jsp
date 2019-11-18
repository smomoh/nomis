<%-- 
    Document   : TestJSP
    Created on : May 5, 2012, 10:46:25 AM
    Author     : COMPAQ USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <html:form action="/testbean">
    <html:errors/>
    <html:hidden property="actionName" styleId="actionName" />
    <html:text property="name" value="Name" />
    <h1>Hello World!</h1>
    <fieldset>
                        <legend class="fieldset">Personal information  </legend>
                        <table width="100%" class="regsitertable">
                            <tr><td align="right">Pre-enrolment No </td><td><html:text property="preEnrolmentNo"  styleClass="smallfieldcellinput" styleId="preEnrolmentNo"/> </td><td align="right">Date of assessment </td><td><html:text property="dateOfAssessment" styleId="dateOfAssessment" styleClass="smallfieldcellinput"/> </td></tr>
                            <tr><td align="right">Surname </td><td><html:text property="surName" styleId="surName" styleClass="fieldcellinput" onkeyup="capitalizeAll('surname', this.value)" /> </td><td align="right">First name </td><td><html:text property="firstName" styleId="firstName" styleClass="smallfieldcellinput" onkeyup="capitalize('otherNames1', this.value)"  /> </td></tr>
                            <tr><td align="right">Age unit </td><td><html:select styleId="ageUnit" styleClass="fieldcellinput" property="ageUnit" style="width:72px;" onchange="populateAge(this.value);">
                    <html:option value=""></html:option>
                    <html:option value="Year">Year</html:option>
                    <html:option value="Month">Month</html:option>
                                    </html:select><span style=" margin-left: 56px; ">Age</span>
                    <html:select property="age" styleId="age" styleClass="shortfieldcellinput">

                    <html:option value="0">0</html:option>
                    <html:option value="1">1</html:option>
                    <html:option value="2">2</html:option>
                    <html:option value="3">3</html:option>
                    <html:option value="4">4</html:option>
                    <html:option value="5">5</html:option>
                    <html:option value="6">6</html:option>
                    <html:option value="7">7</html:option>
                    <html:option value="8">8</html:option>
                    <html:option value="9">9</html:option>
                    <html:option value="10">10</html:option>
                    <html:option value="11">11</html:option>
                    <html:option value="12">12</html:option>
                    <html:option value="13">13</html:option>
                    <html:option value="14">14</html:option>
                    <html:option value="15">15</html:option>
                    <html:option value="16">16</html:option>
                    <html:option value="17">17</html:option>
                </html:select></td><td align="right">Sex </td><td><html:select property="gender" styleId="gender" styleClass="fieldcellinput" style="width:70px;">
                    <html:option value=""> </html:option><html:option value="Male">Male</html:option><html:option value="Female">Female</html:option>
                                    </html:select> </td></tr>
                            <tr><td align="right">Address </td><td colspan="3"><html:textarea property="address" styleId="address" rows="4" cols="90" styleClass="textarealongcell"></html:textarea> </td></tr>
                        </table>


                      </fieldset>

                        <fieldset>


                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" > <!--bordercolor="#D7E5F2"-->
                              <table width="100%" border="1" class="regsitertable">

                              <tr>
                                <td width="25%" > Thematic area</td>
                                <td width="64%" align="center" colspan="4">Rating</td>

                              <td width="11%" > Score</td>
                            </tr>

                          </table></td>
                        </tr>
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                              <tr>
                                <td width="25%" > </td>
                                <td width="16%" align="center">0</td>
                              <td width="16%" align="center">1</td>
                              <td width="16%" align="center">2</td>
                              <td width="16%" align="center">3 </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>

                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">


                            <tr>
                                <td width="25%" > Health</td>
                                <td width="16%" align="center"><html:radio property="health" styleId="health1" value="0"/></td>
                              <td width="16%" align="center"><html:radio property="health" styleId="health2" value="1" /></td>
                              <td width="16%" align="center"><html:radio property="health" styleId="health3" value="2" /></td>
                              <td width="16%" align="center"><html:radio property="health" styleId="health4" value="3" /> </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>
                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                            <tr>
                                <td width="25%" > Education</td>
                                <td width="16%" align="center"><html:radio property="education" styleId="education1" value="0"/></td>
                              <td width="16%" align="center"><html:radio property="education" styleId="education2" value="1" /></td>
                              <td width="16%" align="center"><html:radio property="education" styleId="education3" value="2" /></td>
                              <td width="16%" align="center"><html:radio property="education" styleId="education4" value="3" /> </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>

                        <tr>
                          <td width="752" >
                              <table width="100%" border="1" class="regsitertable">

                            <tr>
                                <td width="25%" > Shelter</td>
                                <td width="16%" align="center"><html:radio property="shelter" styleId="shelter1" value="0"/></td>
                              <td width="16%" align="center"><html:radio property="shelter" styleId="shelter2" value="1" /></td>
                              <td width="16%" align="center"><html:radio property="shelter" styleId="shelter3" value="2" /></td>
                              <td width="16%" align="center"><html:radio property="shelter" styleId="shelter4" value="3" /> </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>

                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                            <tr>
                                <td width="25%" > Protection</td>
                                <td width="16%" align="center"><html:radio property="protection" styleId="protection1" value="0"/></td>
                              <td width="16%" align="center"><html:radio property="protection" styleId="protection2" value="1" /></td>
                              <td width="16%" align="center"><html:radio property="protection" styleId="protection3" value="2" /></td>
                              <td width="16%" align="center"><html:radio property="protection" styleId="protection4" value="3" /> </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>

                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                            <tr>
                                <td width="25%" > Nutrition</td>
                                <td width="16%" align="center"><html:radio property="nutrition" styleId="nutrition1" value="0"/></td>
                              <td width="16%" align="center"><html:radio property="nutrition" styleId="nutrition2" value="1" /></td>
                              <td width="16%" align="center"><html:radio property="nutrition" styleId="nutrition3" value="2" /></td>
                              <td width="16%" align="center"><html:radio property="nutrition" styleId="nutrition4" value="3" /> </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>

                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">

                            <tr>
                                <td width="25%" > Economic strengthening</td>
                                <td width="16%" align="center"><html:radio property="economicStrengthening" styleId="economicStrengthening1" value="0"/></td>
                              <td width="16%" align="center"><html:radio property="economicStrengthening" styleId="economicStrengthening2" value="1" /></td>
                              <td width="16%" align="center"><html:radio property="economicStrengthening" styleId="economicStrengthening3" value="2" /></td>
                              <td width="16%" align="center"><html:radio property="economicStrengthening" styleId="economicStrengthening4" value="3" /> </td>
                              <td width="11%" > &nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>



                        <tr>
                          <td width="752" >
                              <table width="100%" border="1"  class="regsitertable">
                              <tr>
                                  <td width="100%" colspan="2" align="center">Total score </td>


                            </tr>
                              <tr>
                                <td width="50%"> Vulnerability status </td>
                                <td width="50%" align="center">Grade</td>

                            </tr>
                            <tr>
                                <td width="50%">&nbsp; </td>
                                <td width="50%" align="center"> &nbsp;</td>

                            </tr>

                          </table></td>
                        </tr>

                      </table></fieldset>
        </html:form>

        
    </body>
</html>
