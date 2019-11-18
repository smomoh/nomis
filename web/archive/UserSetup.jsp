<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>User accounts</title>
<link rel="stylesheet" href="SpryAssets/LookupTableStyle.css"  />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" >

function setBtnName(name)
{
     if(name=="save")
     {
            setActionName(name)
            return true
     }
     if(confirm("Are you sure you want to "+name+" this record?"))
     {
            setActionName(name)
            return true
     }
       return false
}
function setActionName(val)
{
    document.getElementById("actionName").value = val
}
</SCRIPT>
<style>
.labels
{
  font-family: arial,sans-serif;
  font-size: 12px;
}
</style>
</head>
<!--background-color: #4E9258-->
<body  class="setuppagebg" style="border: 1px blue solid;">

<html:form action="/userSetup">
    <html:errors />
    <html:hidden property="actionName" styleId="actionName" />
    <html:hidden property="userId" styleId="userId" />
    <table >
        <tr><td colspan="4" align="center"><label class="labels" style=" margin-left: 30px; font-size: 14px; color: white">User setup  </label></td></tr>
        <tr><td colspan="4" >&nbsp; </td></tr>

        
        <tr><td><label class="labels">Surname</label></td><td> <html:text property="surname" styleId="surname" styleClass="textField" style="margin-left: 10px; width:130px;" value="${userdetails[0]}"/> </td><td><label class="labels" >Other names</label></td><td> <html:text property="other_names" styleId="other_names" styleClass="textField" style="margin-left: 10px; width:155px;" value="${userdetails[1]}"/> </td></tr>
        <tr><td><label class="labels">User name</label></td><td> <html:text property="user_name" styleId="user_name" styleClass="textField" style="margin-left: 10px; width:130px;" value="${userdetails[2]}"/> </td>
            <td><label>User role</label>    </td>
            <td> <html:select property="user_role" styleId="user_role" styleClass="textField" style="margin-left: 10px; width:155px;">
                    <html:option value=" "> </html:option>
                    <logic:iterate id="ur" name="urList">
                        <html:option value="${ur.roleId}">${ur.roleName}</html:option>
                    </logic:iterate>
                    <%--<html:option value="DataEntry">Data Entry Clerk</html:option><html:option value="Data_user">Data User</html:option><html:option value="Data_analyst">Data Analyst</html:option>--%>
                </html:select>  </td></tr>
    <tr><td><label class="labels">Password </label>  </td><td><html:password property="pwd" styleId="pwd" styleClass="textField" style="margin-left: 10px; width:130px;"/>  </td><td><label class="labels">Confirm password </label></td><td> <html:password property="confirm_pwd" styleId="confirm_pwd" styleClass="textField" style="margin-left: 10px; width:155px;"/> </td></tr>
    <tr><td>  </td><td>&nbsp;<html:checkbox property="changePwd" styleId="changePwd" />User can change password  </td><td> </td><td>&nbsp; <html:checkbox property="viewClientDetails" styleId="viewClientDetails" />User can view client details </td></tr>
    
    <tr><td><label class="labels">States </label></td><td colspan="3">
            <div style="width:430px; height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF; margin-left: 10px">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable  bordercolor="#D7E5F2"-->
                    <tr>
                      <td >
                          <table width="410" border="1" bordercolor="green" cellpadding="0" cellspacing="0">
                              <logic:iterate id="stat" name="states">
                                  <tr><td><html:multibox property='state_code' styleId="${stat.state_code}" value="${stat.state_code}" styleClass='smallfieldcellselect'/> </td><td>${stat.name} </td> </tr>
                              </logic:iterate>

                        </table>

                      </td>
                      </tr>
                  </table>
                </div>
        </td>
    </tr>
    
    <tr><td>  </td><td colspan="3">
            <html:select property="userList" styleId="userList" style="height: 100px; width: 430px; margin-left: 10px; font-size:9pt;" multiple="true" ondblclick="setActionName('userdetails');forms[0].submit()">
                <logic:iterate id="user" name="userLists">
                <html:option value="${user.username}">${user.firstName} ${user.lastName}</html:option>
                </logic:iterate></html:select></td>
    </tr>
    
    <tr><td colspan="4" align="center" style="margin-left: 350px;">
            <center>
<fieldset style="width:250px;">
    <html:submit style="width:75px" value="Save" styleId="save" disabled="${userAccountSaveDisabled}" onclick="return setBtnName('save')"/>
    <html:submit style="width:75px" value="Modify..." styleId="modify" disabled="${userAccountModifyDisabled}" onclick="return setBtnName('modify')" />
    <html:submit style="width:75px" value="Delete..." styleId="delete" disabled="${userAccountModifyDisabled}" onclick="return setBtnName('delete')" />

</fieldset>
            </center>
 </td></tr>
</table>
</html:form>
<%
    session.removeAttribute("userdetails");
%>
</body>
</html>