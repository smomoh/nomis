<%-- 
    Document   : OrganizationRecords2
    Created on : Jun 4, 2012, 10:20:15 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Organization records</title>
<link rel="stylesheet" href="SpryAssets/LookupTableStyle.css"  />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js2/ExternalScript.js" > </script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<SCRIPT LANGUAGE="JavaScript" >
function loadValues()
{
    setActionName('refresh')
    document.getElementById("orgform").submit()   
}
</SCRIPT>
</head>
<body class="setuppagebg" >

    <html:form action="/orgrecords" method="post" styleId="orgform">
                    <html:errors/>
                    <html:hidden property="actionName" styleId="actionName" />
                    <html:hidden property="stateCode" styleId="stateCode" />
                    <html:hidden property="lgaCode" styleId="lgaCode" />
                    <html:hidden property="hiddenOrgCode" styleId="hiddenOrgCode" />
           <fieldset class="fieldset"><legend>Organizational information</legend>
            <table>
                <tr><td>State </td><td>
                        <html:text property="state" styleId="state" styleClass="fieldcellinput" readonly="true" onclick="setActionName('refresh');forms[0].submit()"/>
                        </td>
                    <td align="right">LGA </td><td>
                        <html:select property="lga" styleId="lga" >
                                    <logic:iterate name="lgalistperstate" id="lga">
                                    <html:option value="${lga.lga_code}">${lga.lga_name}</html:option>
                                </logic:iterate>
                        </html:select> </td>
                    
                    
                    <%--<td><html:text property="lga" styleId="lga" styleClass="fieldcellinput" readonly="true" onclick="setActionName('refresh');forms[0].submit()"/>--%>
                            </td> </tr>
                <tr><td>Type </td><td><html:select property="orgType" styleId="orgType" styleClass="fieldcellinput"> <html:option value="Government" >Government</html:option><html:option value="CSO" >CSO</html:option></html:select></td><td align="right">Subtype</td><td><html:select property="orgSubtype" styleId="orgSubtype" styleClass="fieldcellinput"> <html:option value=""></html:option></html:select> </td> </tr>
                <tr><td>Name </td><td><html:text property="orgName" styleId="orgName" styleClass="fieldcellinput"/></td><td colspan="2" align="right">Code &nbsp;&nbsp;&nbsp;&nbsp;<html:text property="orgCode" styleId="orgCode" styleClass="fieldcellinput"/></td></tr>
                <tr><td>Dhis id</td><td><html:text property="dhisId" styleId="dhisId" styleClass="fieldcellinput"/> </td><td><html:checkbox property="dataEntryAllowed" />Enable data entry </td></tr>
                <tr><td>Address</td><td colspan="3"><html:textarea property="address" styleClass="contactslongcell"> </html:textarea> </td></tr>

                <tr><td colspan="4">
                        <fieldset class="fieldset"><legend>Contact information</legend>
                        <table>
                            <tr><td width="70">Contact 1 </td><td>Name </td><td><html:text property="contactName1" styleId="contactName1" styleClass="contactscellinput"/> </td><td>Phone </td><td><html:text property="contactPhone1" styleId="contactPhone1" styleClass="contactscellinput"/> </td><td>Email </td><td><html:text property="contactEmail1" styleId="contactEmail1" styleClass="contactscellinput"/> </td></tr>
                            <tr><td>Contact 2 </td><td>Name </td><td><html:text property="contactName2" styleId="contactName2" styleClass="contactscellinput"/> </td><td>Phone </td><td><html:text property="contactPhone2" styleId="contactPhone2" styleClass="contactscellinput"/> </td><td>Email </td><td><html:text property="contactEmail2" styleId="contactEmail2" styleClass="contactscellinput"/> </td></tr>
                            <%--<tr><td>Contact 3 </td><td>Name </td><td><html:text property="contactName3" styleId="contactName3" styleClass="contactscellinput"/> </td><td>Phone </td><td><html:text property="contactPhone3" styleId="contactPhone3" styleClass="contactscellinput"/> </td><td>Email </td><td><html:text property="contactEmail3" styleId="contactEmail3" styleClass="contactscellinput"/> </td></tr>--%>
                        </table>
                        </fieldset>
                    </td>
                </tr>
                <tr><td colspan="4"><label>LGA Assignment </label> </td></tr>
                <tr>
                    <td colspan="4">
                        <div style="width:570px; height: 100px; border: 1px black solid;background-color: #D7E5F2; overflow: scroll;">
                        <table>
                    <logic:iterate name="lgalistperstate" id="lga">
                        <tr>
                            <td><html:multibox property="assignedLgas" value="${lga.lga_code}"/>${lga.lga_name} </td>
                    </tr>
                    </logic:iterate>
                    </table>
                        </div>
                        </td>
                </tr>
                <tr><td colspan="4"><label>Services </label> </td></tr>
                <tr>
                    <td colspan="4">
                        <div style="width:570px; height: 100px; border: 1px black solid;background-color: #D7E5F2; overflow: scroll;">
                        <table>
                    <logic:iterate name="serviveList" id="service">
                        <tr>
                            <td><html:multibox property="services" value="${service.serviceName}"/>${service.serviceName}</td>
                    </tr>
                    </logic:iterate>
                    </table>
                        </div>
                        </td>
                </tr>

                <tr>
                    <td colspan="4">
                        <html:select property="orgList" multiple="true" style="width:570px; height:100px;" ondblclick="setActionName('orgDetails');forms[0].submit()">
                            <logic:iterate name="organizationList" id="organization">
                                <html:option value="${organization.orgCode}">${organization.orgName}</html:option>
                            </logic:iterate>
                        </html:select>
                    </td>
                </tr>
            </table>
           </fieldset>
                        <table align="center">
                            <tr>
                        <td align="center">
                            <html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${orgSaveDiabled}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${orgModifyDiabled}"/>
                           <html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${orgModifyDiabled}"/>
                        </td>
                    </tr>
                        </table>
        </html:form>


</body>
</html>