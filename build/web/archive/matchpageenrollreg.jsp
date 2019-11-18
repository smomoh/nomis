<%--
    Document   : ovclist_1
    Created on : 05-Apr-2010, 17:22:10
    Author     : HP USER
--%>

<%@page buffer="7000kb" autoFlush="false" %>
<%@page import="javax.servlet.http.HttpSession" %>
<%@page import="com.fhi.kidmap.business.States" %>
<%@page import="com.fhi.kidmap.dao.StatesDaoImpl" %>
<%@page import="java.util.List" %>
<%@page import="com.fhi.kidmap.dao.StatesDao" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.fhi.kidmap.business.Lga" %>
<%@page import="com.fhi.kidmap.business.Lgas" %>
<%@page import="com.fhi.kidmap.dao.LgaDaoImpl" %>
<%@page import="com.fhi.kidmap.dao.LgaDao" %>
<%@page import="java.util.List" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>


<logic:notPresent name="ovcMatchesEnrollReg">
    <logic:forward name="login" />
</logic:notPresent>




<%
            String stateCode = "";
            String lgaCode = "";
            String enrollmentListCbo = "";
            String enrollmentListMonth1 = "";
            String enrollmentListMonth2 = "";
            String enrollmentListYear1 = "";
            String enrollmentListYear2 = "";
            States state = null;
            Lgas lga = null;
            HttpSession httpSession = request.getSession();
            if (httpSession.getAttribute("selectionArray") != null && httpSession.getAttribute("enrollmentParameters") != null) {
                if (((String[]) httpSession.getAttribute("selectionArray"))[2] != null) {
                    stateCode = ((String[]) httpSession.getAttribute("selectionArray"))[2];
                }
                if (((String[]) httpSession.getAttribute("selectionArray"))[0] != null) {
                    lgaCode = ((String[]) httpSession.getAttribute("selectionArray"))[0];
                }
                if (((String[]) httpSession.getAttribute("enrollmentParameters"))[0] != null) {
                    enrollmentListCbo = ((String[]) httpSession.getAttribute("enrollmentParameters"))[0];
                }
                if (((String[]) httpSession.getAttribute("enrollmentParameters"))[1] != null) {
                    enrollmentListMonth1 = ((String[]) httpSession.getAttribute("enrollmentParameters"))[1];
                }
                if (((String[]) httpSession.getAttribute("enrollmentParameters"))[3] != null) {
                    enrollmentListMonth2 = ((String[]) httpSession.getAttribute("enrollmentParameters"))[3];
                }
                if (((String[]) httpSession.getAttribute("enrollmentParameters"))[2] != null) {
                    enrollmentListYear1 = ((String[]) httpSession.getAttribute("enrollmentParameters"))[2];
                }
                if (((String[]) httpSession.getAttribute("enrollmentParameters"))[4] != null) {
                    enrollmentListYear2 = ((String[]) httpSession.getAttribute("enrollmentParameters"))[4];
                }
            }


            try {
                StatesDao dao = new StatesDaoImpl();
                List states = dao.getState(stateCode);
                state = (States) states.get(0);
            } catch (Exception e) {
            }

            try {
                LgaDao dao = new LgaDaoImpl();
                List lgas = dao.getLgaByLgaCode(lgaCode);
                lga = (Lgas) lgas.get(0);
            } catch (Exception e) {
            }


            if (enrollmentListMonth1.equals("01")) {
                enrollmentListMonth1 = "Jan";
            } else if (enrollmentListMonth1.equals("02")) {
                enrollmentListMonth1 = "Feb";
            } else if (enrollmentListMonth1.equals("03")) {
                enrollmentListMonth1 = "Mar";
            } else if (enrollmentListMonth1.equals("04")) {
                enrollmentListMonth1 = "Apr";
            } else if (enrollmentListMonth1.equals("05")) {
                enrollmentListMonth1 = "May";
            } else if (enrollmentListMonth1.equals("06")) {
                enrollmentListMonth1 = "Jun";
            } else if (enrollmentListMonth1.equals("07")) {
                enrollmentListMonth1 = "Jul";
            } else if (enrollmentListMonth1.equals("08")) {
                enrollmentListMonth1 = "Aug";
            } else if (enrollmentListMonth1.equals("09")) {
                enrollmentListMonth1 = "Sep";
            } else if (enrollmentListMonth1.equals("10")) {
                enrollmentListMonth1 = "Oct";
            } else if (enrollmentListMonth1.equals("11")) {
                enrollmentListMonth1 = "Nov";
            } else {
                enrollmentListMonth1 = "Dec";
            }


            if (enrollmentListMonth2.equals("01")) {
                enrollmentListMonth2 = "Jan";
            } else if (enrollmentListMonth2.equals("02")) {
                enrollmentListMonth2 = "Feb";
            } else if (enrollmentListMonth2.equals("03")) {
                enrollmentListMonth2 = "Mar";
            } else if (enrollmentListMonth2.equals("04")) {
                enrollmentListMonth2 = "Apr";
            } else if (enrollmentListMonth2.equals("05")) {
                enrollmentListMonth2 = "May";
            } else if (enrollmentListMonth2.equals("06")) {
                enrollmentListMonth2 = "Jun";
            } else if (enrollmentListMonth2.equals("07")) {
                enrollmentListMonth2 = "Jul";
            } else if (enrollmentListMonth2.equals("08")) {
                enrollmentListMonth2 = "Aug";
            } else if (enrollmentListMonth2.equals("09")) {
                enrollmentListMonth2 = "Sep";
            } else if (enrollmentListMonth2.equals("10")) {
                enrollmentListMonth2 = "Oct";
            } else if (enrollmentListMonth2.equals("11")) {
                enrollmentListMonth2 = "Nov";
            } else {
                enrollmentListMonth2 = "Dec";
            }





%>




<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title>Enrollment Register</title>
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
            td,th {
                padding-left: 11px;
                padding-right: 11px;
                border-left: 1px solid black;
                border-bottom: 1px solid black;
                font-size: 11px;
                color: black;
            }
            table {
                border-collapse: collapse;
                margin: 10px;
            }
        </style>

    </head>

    <div align="center">

        <body style="background-color:#FFFFFF; font-family: Arial, Helvetica, sans-serif;">


            <html:errors/>


            <center>
                <div style="margin-top:20px; margin-bottom:30px; background-color:#FFFFFF">
                    <!--<span style="margin-top:20px; margin-bottom:100px">-->
                    <table width="100%" border="0" style="border:none;">
                        <tr>
                            <td style="border:none;"><img src="images/CoatOfArm.jpg"  /></td><td style="border:none; font-family: Arial, Helvetica, sans-serif; font-size:14pt"><label style="margin-left:200px"><b>OVC ENROLLMENT REGISTER </b></label></td><td style="border:none;" width="10">&nbsp;</td>
                    </table>
                    <table width="100%">
                        <%--<tr>
                            <td style="border:none; font-family: 'Courier New', Courier, monospace; font-size:10pt" align="left"><label style="text-align:left"><b>State: <%=state.getName()%></b></label></td><td style="border:none; font-family:'Courier New', Courier, monospace; font-size:10pt"><label><b>LGA: <%=lga.getLga_name()%></b></label></td><td style="border:none; font-family: 'Courier New', Courier, monospace; font-size:10pt;"><label style="margin-right:100px;"><b>Organization Name: <%=enrollmentListCbo%></b> </label>       <label style="margin-left:100px"><b>Month:<%if (enrollmentListMonth1.equals(enrollmentListMonth2) && enrollmentListYear1.equals(enrollmentListYear2)) {%> <%=enrollmentListMonth1%><%} else {%> <%=enrollmentListMonth1%> - <%=enrollmentListMonth2%><%}%>   Year:<%if (enrollmentListYear1.equals(enrollmentListYear2)) {%> <%=enrollmentListYear1%><%} else {%> <%=enrollmentListYear1%> - <%=enrollmentListYear2%><%}%></b></label></td>
                        </tr>--%>




                        <tr>
                            <td style="border:none; font-family: Arial, Helvetica, sans-serif; font-size:10pt" align="left"><label><b>State: <%=state.getName()%></b></label></td>
                        </tr>
                        <tr>
                            <td style="border:none; font-family: Arial, Helvetica, sans-serif; font-size:10pt" align="left"><label><b>LGA: <%=lga.getLga_name()%></b></label></td>
                        </tr>
                        <tr>
                            <td style="border:none; font-family: Arial, Helvetica, sans-serif; font-size:10pt" align="left"><label><b>Organization Name: <%=enrollmentListCbo%></b> </label></td>
                        </tr>
                        <tr>
                            <td style="border:none; font-family: Arial, Helvetica, sans-serif; font-size:10pt" align="left"><label><b>Month:<%if (enrollmentListMonth1.equals(enrollmentListMonth2) && enrollmentListYear1.equals(enrollmentListYear2)) {%> <%=enrollmentListMonth1%><%} else {%> <%=enrollmentListMonth1%> - <%=enrollmentListMonth2%><%}%>   Year:<%if (enrollmentListYear1.equals(enrollmentListYear2)) {%> <%=enrollmentListYear1%><%} else {%> <%=enrollmentListYear1%> - <%=enrollmentListYear2%><%}%></b></label></td>
                        </tr>
                    </table>




                    <table border="0" cellpadding="0" cellspacing="0" style="border:1px black solid; margin-bottom:40px">
                        <tr >
                            <th>&nbsp;</th>
                            <th colspan="7"  style="font-size:16px"><div align="center">Child Registration and Personal Information</div></th>
                            <th  colspan="9"  style="font-size:16px"><div align="center">Eligibility Criteria<br />Mark X in all columns that apply</div></th>
                            <th  colspan="3"  style="font-size:16px"><div align="center">HIV Status<br />Mark X in appropriate column</div></th>
                            <th  style="font-size:16px"><div align="center">Birth <br />Registration</div></th>
                            <th  colspan="3"  style="font-size:16px"><div align="center">Education</div></th>
                            <th  colspan="3"  style="font-size:16px"><div align="center">Caregiver's Information</div></th>
                            <th  colspan="12"  style="font-size:16px"><div align="center">Baseline Child Status Index<br /> vulnerability index score during this encounter</div></th>
                            <th  colspan="3" style="font-size:16px"><div align="center">Services Child Received at Enrollment</div></th>
                        </tr>

                        <tr >
                            <th   ><div align="center">OVC S/N</div></th>
                            <th   ><div align="center">Enrollment Date<br />(dd/mm/yyyy)</div></th>
                            <th   ><div align="center"><span style=" margin-left: 20px; margin-right: 20px">OVC ID No.</span><br /><span style=" margin-left: 20px; margin-right: 20px"><label style="font-size:8px">(State/LGA/CBO/S/N)</label></span></div></th>
                            <th width="100"><div align="center">CHILD's NAME IN FULL<br />Upper space: Surname name<br />Lower space: First name</div></th>
                            <th   ><div align="center">Address </div></th>
                            <th   ><div align="center">Ward</div></th>
                            <th   ><div align="center">Sex<br />(M/F)</div></th>
                            <th   ><div align="center"> <span style=" margin-left: 30px; margin-right: 30px">Age</span> </div></th>


                            <th   class="verticaltext"><div align="center">Child is HIV Positive</div></th>
                            <th   class="verticaltext"><div align="center">Lost one parent</div></th>
                            <th   class="verticaltext"><div align="center">Lost both Parents</div></th>
                            <th   class="verticaltext"><div align="center">Child lives on the street</div></th>
                            <th   class="verticaltext"><div align="center">Living with chronically ill parent</div></th>
                            <th   class="verticaltext"><div align="center">Living in a child headed household</div></th>
                            <th   class="verticaltext"><div align="center">Living with frail old grandparents</div></th>
                            <th   class="verticaltext"><div align="center">Child labourer</div></th>
                            <th   class="verticaltext"><div align="center">Others</div></th>

                            <th   ><div align="center">HIV unknown</div></th>
                            <th   ><div align="center">HIV positive</div></th>
                            <th   ><div align="center">HIV negative</div></th>
                            <th   ><div align="center">Tick if child has a birth registration certificate</div></th>

                            <th   ><div align="center">Is child in school?<br/>(Y/N)</div></th>
                            <th   ><div align="center">Name of school child is enrolled in</div></th>
                            <th   ><div align="center">Class</div></th>

                            <th   ><div align="center">CAREGIVER's NAME IN FULL<br />Upper space: Surname name<br />Lower space: First name</div></th>
                            <th   ><div align="center">Caregiver's Address</div></th>
                            <th   ><div align="center">Caregiver's Phone number</div></th>

                            <th   class="verticaltext "><div align="center">Food Security</div></th>
                            <th   class="verticaltext "><div align="center">Nutrition &amp; Growth</div></th>
                            <th   class="verticaltext "><div align="center">Shelter</div></th>
                            <th   class="verticaltext "><div align="center">Care</div></th>
                            <th   class="verticaltext "><div align="center">Wellness</div></th>
                            <th   class="verticaltext "><div align="center">healthcare Services</div></th>
                            <th   class="verticaltext "><div align="center">Emotional Health</div></th>
                            <th   class="verticaltext "><div align="center">Social Behavior</div></th>
                            <th  class="verticaltext "><div align="center">Abuse &amp; Exploitation</div></th>
                            <th   class="verticaltext "><div align="center">Legal Protection</div></th>
                            <th   class="verticaltext "><div align="center">Performance</div></th>
                            <th   class="verticaltext "><div align="center">School &amp; Work</div></th>

                            <th   class="verticaltext "><div align="center">Psychosocial Support</div></th>
                            <th   class="verticaltext "><div align="center">Nutrition counseling</div></th>
                            <th   class="verticaltext "><div align="center">Health referral</div></th>
                        </tr>


                        <tr><td height="30">&nbsp; </td><td> </td><td> </td><td width="100"><span style="color: #FFFFFF">444444444444444444444</span> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td>
                            <td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td>
                            <td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td><td> </td> </tr>


                        <%
                                    int cnt = 0;

                                    int row = 0;

                        %>
                        <!?? iterate over the results of the query ??>
                        <logic:iterate id="ovcObj" name="ovcMatchesEnrollReg">



                            <tr align="left" style="font-family: Arial, Helvetica, sans-serif;"><td height="60">&nbsp;<%= ++row%>&nbsp;</td><td> <bean:write name="ovcObj" property="dateEnrollment" /> </td><td> <bean:write name="ovcObj" property="ovcId" /> </td><td width="100"> <bean:write name="ovcObj" property="lastName" /> <bean:write name="ovcObj" property="firstName" /> </td><td> <bean:write name="ovcObj" property="address" /> </td><td> <bean:write name="ovcObj" property="ward" /> </td><td> <bean:write name="ovcObj" property="gender" /> </td> <logic:equal name="ovcObj"
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   property="ageUnit"
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   value="Year"><td> <bean:write name="ovcObj" property="age" /> year(s) </td></logic:equal> <logic:notEqual name="ovcObj"
                                    property="ageUnit"
                                    value="Year"><td> <bean:write name="ovcObj" property="age" />&nbsp;month(s) </td></logic:notEqual><td align="center"><logic:match name="ovcObj" property="eligibility" value="Child is HIV positive">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td align="center"><logic:match name="ovcObj" property="eligibility" value="Lost one parent">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td align="center"><logic:match name="ovcObj" property="eligibility" value="lost both parents">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td align="center"><logic:match name="ovcObj" property="eligibility" value="Child lives on the street">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td>
                                <td align="center"><logic:match name="ovcObj" property="eligibility" value="Living with a chronically ill parent(from any cause)">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td align="center"><logic:match name="ovcObj" property="eligibility" value="Living in a child headed household">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td align="center"><logic:match name="ovcObj" property="eligibility" value="Child living with frail old grandparents">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td align="center"><logic:match name="ovcObj" property="eligibility" value="Child labourer">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td></td><td align="center"><logic:match name="ovcObj" property="hivStatus" value="HIV status unknown">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td align="center"><logic:match name="ovcObj" property="hivStatus" value="HIV negative">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td align="center"><logic:match name="ovcObj" property="hivStatus" value="HIV positive">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td align="center"><logic:equal name="ovcObj"
                                                                          property="birthCertificate"
                                                                              value="Yes"><span style="font-size: 15px">X</span></logic:equal></td> <logic:equal name="ovcObj"
                                                                          property="schoolStatus"
                                                                          value="Yes"><td> Yes </td></logic:equal> <logic:notEqual name="ovcObj"
                                    property="schoolStatus"
                                    value="Yes"><td> No </td></logic:notEqual> <td> <logic:equal name="ovcObj"
                                        property="schoolStatus"
                                        value="Yes"><bean:write name="ovcObj" property="schoolName" /></logic:equal> </td><td> <logic:equal name="ovcObj"
                                        property="schoolStatus"
                                            value="Yes"><bean:write name="ovcObj" property="currentClass" /></logic:equal> </td><td> <bean:write name="ovcObj" property="caregiverName" /> </td>
                                <td> <bean:write name="ovcObj" property="caregiverAddress" /> </td><td> <bean:write name="ovcObj" property="caregiverPhone" /> </td><td> <bean:write name="ovcObj" property="csiFactor3" /> </td><td> <bean:write name="ovcObj" property="csiFactor4" /> </td><td> <bean:write name="ovcObj" property="csiFactor11" /> </td><td> <bean:write name="ovcObj" property="csiFactor12" /> </td><td> <bean:write name="ovcObj" property="csiFactor5" /> </td><td> <bean:write name="ovcObj" property="csiFactor6" /> </td><td> <bean:write name="ovcObj" property="csiFactor1" /> </td><td> <bean:write name="ovcObj" property="csiFactor2" /> </td><td> <bean:write name="ovcObj" property="csiFactor9" /> </td><td> <bean:write name="ovcObj" property="csiFactor10" /> </td><td> <bean:write name="ovcObj" property="csiFactor7" /> </td><td> <bean:write name="ovcObj" property="csiFactor8" /> </td><td align="center"><logic:match name="ovcObj" property="serviceEnrollment" value="Psychosocial support">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td align="center"><logic:match name="ovcObj" property="serviceEnrollment" value="Nutrition counseling">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td><td align="center"><logic:match name="ovcObj" property="serviceEnrollment" value="Health referral">
                                        <span style="font-size: 15px">X</span>
                                    </logic:match></td> </tr>
                           

                        </logic:iterate>

                        
                    </table>



                    <br />&nbsp;<br />&nbsp;<br />&nbsp;
                    <!--</span>-->
                </div>
            </center>






        </body>

    </div>

</html>
