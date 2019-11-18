<%@page language="java" %>
<%@page import="com.fhi.nomis.nomisutils.*" %>

<%@page import="com.fhi.kidmap.dao.StateDaoImpl" %>
<%@page import="java.util.List" %>
<%@page import="com.fhi.kidmap.dao.StateDao" %>
<%@page import="java.util.ArrayList" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>

<%
    LoadUpInfo loadup=new LoadUpInfo();
    loadup.processCboSetup(session);
    loadup.getSchoolsPerState(session);
    loadup.setEnrollmentAttributes(session);
    loadup.getAllStates(session);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Welcome to KidMAP Orphan &amp; Vulnerable Children Database</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 20px;
	background-color: #F5F5F5;
	background-image: url();
	background-repeat: repeat-x;
	
}
-->
</style>
<link href="images/kidmap.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
a {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 11px;
	color: #333333;
}
a:link {
	text-decoration: none;
	color: #0075B5;
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

-->
</style>
<script type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
	
	//getSetupInfo();
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>

<script language="JavaScript" src="kidmap.js" type="text/JavaScript"></script>
 <link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>
<link href="sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />


<script language="javascript">

$(function() {
                $("#dateEnrollment").datepicker();
            });


 var req;
 var which;
 var callerId=""
 var resetId=0
 var searchIndex=0

function getValuesFromDb(url,str,id)
{
	callerId=id;
	xmlhttp=GetXmlHttpObject();
    if (xmlhttp==null)
    {
        alert ("Browser does not support HTTP Request");
        return;
    }
    url=url+"?qparam="+str;
    url=url+"&sid="+Math.random();
    xmlhttp.onreadystatechange=stateChanged;
    xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}
function GetXmlHttpObject()
{
    if (window.XMLHttpRequest)
    {
        return new XMLHttpRequest();
    }
    if (window.ActiveXObject)
    {
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    return null;
}
function stateChanged()
{
	if (xmlhttp.readyState==4)
	{
		var lgas=xmlhttp.responseText;
                if(lgas==" " || lgas=="" || lgas==";")
                return false;

            var values=lgas.split(";")

            if(callerId=="partofnamestring")
                document.getElementById("ovcNames").innerHTML=lgas
            else if(callerId=="cbo")
            {
                setSelectBoxValues(values, "CbosSelect")
            }
            else if(callerId=="ward")
            {
                setWardSelectBoxValues(values, "WardsSelect")
            }
            else if(callerId=="nameAndAge" || callerId=="nameAndCaregiver")
            {
                if(values[0]=="no duplicate")
                return
                getConfirmation()
            }
            else if(callerId=="schoolList")
            {
                populateSchoolDropdownBox(values, "schoolName")
            }
	}
        else
        {
            //alert("error "+xmlhttp.responseText)
        }

}
    function retrieveURL(url)
    {
      if (window.XMLHttpRequest) { // Non-IE browsers
      req = new XMLHttpRequest();
      req.onreadystatechange = processStateChange;
      try {
        //req.open("GET", url, true);
        req.open("POST", url, true);
      } catch (e) {
        alert(e);
      }
      req.send(null);
    } else if (window.ActiveXObject) { // IE
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req) {
        req.onreadystatechange = processStateChange;
        //req.open("GET", url, true);
        req.open("POST", url, true);
        req.send();
      }
    }
  }

  function processStateChange() {
    if (req.readyState == 4) { // Complete
      if (req.status == 200)
      { // OK response

       if(req.responseText != " ") {
        var resArray = req.responseText.split('-');
        if(resArray[0] == "error")
        {
            resetFieldValues()
            setFieldValues(resArray)
        }
        else{
            var resArray = req.responseText.split('-');

            if(resArray[0] == "success")
            {
                if(resetId==1)
                resetFieldValues()

            document.getElementById("characters").value = resArray[1];
            document.getElementById("characters2").innerHTML = " ";
             var resArray2 = resArray[1].split("/");
            }
        }

        }

      } else {
        alert("Problem: " + req.statusText);
      }
    }
  }
function getConfirmation()
{
     if(confirm("Ovc with duplicate record exist!\nClick OK to view or CANCEL to reset form"))
     {
            var newWindow = window.open("matchpage.jsp", "match",
"width=740,height=585,left=230,top=70,resizable=no,scrollbars=no,menubar=no,location=no,status=no");
            return true
     }
     //resetAllFields();
     return false
}
function getAgeUnit()
{

        var dropdownIndex = document.getElementById("ageUnit").selectedIndex;
        var dropDownValue=document.getElementById("ageUnit").options[dropdownIndex].value
        retrieveURL2('getCharacters.do?ageUnit=' + dropDownValue);

}
function getAge()
{
        var dropDownValue=document.getElementById("age").value
        retrieveURL2('getCharacters.do?age=' + dropDownValue);
}
function capitalize(id, value)
{
    document.getElementById(id).value = value.substring(0,1).toUpperCase() + value.substring(1);
}
function capitalizeAll(id, value)
{
    document.getElementById(id).value = value.toUpperCase();
}
function setFieldValues(resArray)
{
    resetId=1
    
            var resArray2 = resArray[2].split("/");

            var ovcDetails = req.responseText.split('::');
            if(ovcDetails[1] == "ovcdetails"){
                 ovcDetails[2] = changeDateFormat(ovcDetails[2]);
                 document.getElementById("dateEnrollment").value = ovcDetails[2];
                 document.getElementById("surname").value = ovcDetails[3];
                 document.getElementById("otherNames1").value = ovcDetails[4];
                 if(ovcDetails[5] == " ")
                 {
                    document.getElementById("otherNames2").value = "";
                 }
                 else document.getElementById("otherNames2").value = ovcDetails[5];
                 document.getElementById("gender").value = ovcDetails[6];
                 document.getElementById("age").value = ovcDetails[7];
                 document.getElementById("ageUnit").value = ovcDetails[8];
                 document.getElementById("weight").value = ovcDetails[9];
                 document.getElementById("address").value = ovcDetails[10];
                 document.getElementById("phone").value = ovcDetails[11];
                 document.getElementById("height").value = ovcDetails[12];

                 var eligibility = ovcDetails[13];
                 
                 var tokens = tokenizeStr(eligibility);

                 for(i=0; i<tokens.length; i++){

                     if(tokens[i] == "Child is HIV positive") {

                document.getElementById("eligibility1").checked = true;
            }

            else if(tokens[i] == "Lost one parent") {
            
                document.getElementById("eligibility2").checked = true;
            }

            else if(trim(tokens[i]) == "lost both parents") {

                document.getElementById("eligibility3").checked = true;
            }

            else if(tokens[i] == "Child lives on the street") {

                document.getElementById("eligibility4").checked = true;
            }

            else if(tokens[i] == "Living with a chronically ill parent(from any cause)") {

                document.getElementById("eligibility5").checked = true;
            }

             else if(tokens[i] == "Living in a child headed household") {

                document.getElementById("eligibility6").checked = true;
            }

            else if(tokens[i] == "Child living with frail old grandparents") {

                document.getElementById("eligibility7").checked = true;
            }

            else if(tokens[i] == "Child labourer"){

                document.getElementById("eligibility8").checked = true;
            }
          }

                 var hivStatus = ovcDetails[14];

                 if(hivStatus == "HIV status unknown") {

                document.getElementById("hivStatus1").checked = true;
            }

            else if(hivStatus == "HIV negative") {

                document.getElementById("hivStatus2").checked = true;
            }

            else if(hivStatus == "HIV positive"){

                document.getElementById("hivStatus3").checked = true;
            }

            document.getElementById("birthCertificate").value = ovcDetails[15];
            document.getElementById("schoolStatus").value = ovcDetails[16];


            var schoolType = ovcDetails[17];
            if(schoolType == "Public") {

                document.getElementById("schoolType1").checked = true;
              }

            else if(schoolType == "Private"){
            document.getElementById("schoolType2").checked = true;
            }
            document.getElementById("schoolType1").disabled = false;
            document.getElementById("schoolType2").disabled = false;
            document.getElementById("schoolName").options.length=0
            document.getElementById("schoolName").options[0]=new Option(ovcDetails[18],ovcDetails[18])
            document.getElementById("schoolName").disabled = false;
                        
            document.getElementById("currentClass").value = ovcDetails[19];
            document.getElementById("currentClass").disabled = false;

            var orphanage = ovcDetails[20];
            document.getElementById("orphanage").value = orphanage;

            document.getElementById("orphanageName").value = ovcDetails[21];
            var caregiverName = ovcDetails[22];

            tokens = tokenizeStr(caregiverName);

            document.getElementById("caregiverName1").value = tokens[0];
            document.getElementById("caregiverName2").value = tokens[1];

            document.getElementById("caregiverGender").value = ovcDetails[23];

            document.getElementById("caregiverAge").value = ovcDetails[24];
            document.getElementById("caregiverAddress").value = ovcDetails[25];
            document.getElementById("caregiverPhone").value = ovcDetails[26];
            document.getElementById("caregiverOccupation").value = ovcDetails[27];

            var caregiverRelationships = ovcDetails[28];

             if(caregiverRelationships == "Mother/Father") {

                document.getElementById("caregiverRelationships").value = caregiverRelationships;
            }

            else if(caregiverRelationships == "Social worker/Orphanage caregiver") {

                document.getElementById("caregiverRelationships").value = caregiverRelationships;
            }

            else if(caregiverRelationships == "Grandparents") {

                document.getElementById("caregiverRelationships").value = caregiverRelationships;
            }

            else if(caregiverRelationships == "Brother/Sister") {

                document.getElementById("caregiverRelationships").value = caregiverRelationships;
            }

            else {

                document.getElementById("caregiverRelationships2").value = caregiverRelationships;
            }

            var csiFactor1 = ovcDetails[29];

            if(csiFactor1 == "1") {

                document.getElementById("csiFactor1_1").checked = true;
            }

            else if(csiFactor1 == "2") {

                document.getElementById("csiFactor1_2").checked = true;
            }

            else if(csiFactor1 == "3") {

                document.getElementById("csiFactor1_3").checked = true;
            }

            else if(csiFactor1 == "4")  {

                document.getElementById("csiFactor1_4").checked = true;
            }
            var csiFactor2 = ovcDetails[30];

            if(csiFactor2 == "1") {

                document.getElementById("csiFactor2_1").checked = true;
            }

            else if(csiFactor2 == "2") {

                document.getElementById("csiFactor2_2").checked = true;
            }

            else if(csiFactor2 == "3") {

                document.getElementById("csiFactor2_3").checked = true;
            }

            else if(csiFactor2 == "4"){

                document.getElementById("csiFactor2_4").checked = true;
            }

             var csiFactor3 = ovcDetails[31];

            if(csiFactor3 == "1") {

                document.getElementById("csiFactor3_1").checked = true;
            }

            else if(csiFactor3 == "2") {

                document.getElementById("csiFactor3_2").checked = true;
            }

            else if(csiFactor3 == "3") {

                document.getElementById("csiFactor3_3").checked = true;
            }

            else if(csiFactor3 == "4"){

                document.getElementById("csiFactor3_4").checked = true;
            }

             var csiFactor4 = ovcDetails[32];

            if(csiFactor4 == "1") {

                document.getElementById("csiFactor4_1").checked = true;
            }

            else if(csiFactor4 == "2") {

                document.getElementById("csiFactor4_2").checked = true;
            }

            else if(csiFactor4 == "3") {

                document.getElementById("csiFactor4_3").checked = true;
            }

            else if(csiFactor4 == "4"){

                document.getElementById("csiFactor4_4").checked = true;
            }
             var csiFactor5 = ovcDetails[33];

            if(csiFactor5 == "1") {

                document.getElementById("csiFactor5_1").checked = true;
            }

            else if(csiFactor5 == "2") {

                document.getElementById("csiFactor5_2").checked = true;
            }

            else if(csiFactor5 == "3") {

                document.getElementById("csiFactor5_3").checked = true;
            }

            else if(csiFactor5 == "4"){

                document.getElementById("csiFactor5_4").checked = true;
            }

            var csiFactor6 = ovcDetails[34];

            if(csiFactor6 == "1") {

                document.getElementById("csiFactor6_1").checked = true;
            }

            else if(csiFactor6 == "2") {

                document.getElementById("csiFactor6_2").checked = true;
            }

            else if(csiFactor6 == "3") {

                document.getElementById("csiFactor6_3").checked = true;
            }

            else if(csiFactor6 == "4"){

                document.getElementById("csiFactor6_4").checked = true;
            }

            var csiFactor7 = ovcDetails[35];

            if(csiFactor7 == "1") {

                document.getElementById("csiFactor7_1").checked = true;
            }

            else if(csiFactor7 == "2") {

                document.getElementById("csiFactor7_2").checked = true;
            }

            else if(csiFactor7 == "3") {

                document.getElementById("csiFactor7_3").checked = true;
            }

            else if(csiFactor7 == "4"){

                document.getElementById("csiFactor7_4").checked = true;
            }

            var csiFactor8 = ovcDetails[36];

            if(csiFactor8 == "1") {

                document.getElementById("csiFactor8_1").checked = true;
            }

            else if(csiFactor8 == "2") {

                document.getElementById("csiFactor8_2").checked = true;
            }

            else if(csiFactor8 == "3") {

                document.getElementById("csiFactor8_3").checked = true;
            }

            else if(csiFactor8 == "4"){

                document.getElementById("csiFactor8_4").checked = true;
            }

            var csiFactor9 = ovcDetails[37];

            if(csiFactor9 == "1") {

                document.getElementById("csiFactor9_1").checked = true;
            }

            else if(csiFactor9 == "2") {

                document.getElementById("csiFactor9_2").checked = true;
            }

            else if(csiFactor9 == "3") {

                document.getElementById("csiFactor9_3").checked = true;
            }

            else if(csiFactor9 == "4"){

                document.getElementById("csiFactor9_4").checked = true;
            }

            var csiFactor10 = ovcDetails[38];

            if(csiFactor10 == "1") {

                document.getElementById("csiFactor10_1").checked = true;
            }

            else if(csiFactor10 == "2") {

                document.getElementById("csiFactor10_2").checked = true;
            }

            else if(csiFactor10 == "3") {

                document.getElementById("csiFactor10_3").checked = true;
            }

            else if(csiFactor10 == "4"){

                document.getElementById("csiFactor10_4").checked = true;
            }
            var csiFactor11 = ovcDetails[39];

            if(csiFactor11 == "1") {

                document.getElementById("csiFactor11_1").checked = true;
            }

            else if(csiFactor11 == "2") {

                document.getElementById("csiFactor11_2").checked = true;
            }

            else if(csiFactor11 == "3") {

                document.getElementById("csiFactor11_3").checked = true;
            }

            else if(csiFactor11 == "4"){

                document.getElementById("csiFactor11_4").checked = true;
            }
             var csiFactor12 = ovcDetails[40];

            if(csiFactor12 == "1") {

                document.getElementById("csiFactor12_1").checked = true;
            }

            else if(csiFactor12 == "2") {

                document.getElementById("csiFactor12_2").checked = true;
            }

            else if(csiFactor12 == "3") {

                document.getElementById("csiFactor12_3").checked = true;
            }

            else if(csiFactor12 == "4"){

                document.getElementById("csiFactor12_4").checked = true;
            }

            document.getElementById("childIndexId").value=ovcDetails[41]
            var serviceEnrollment = ovcDetails[42];

                 var tokens = tokenizeStr(serviceEnrollment);

                 for(i=0; i<tokens.length; i++){

                     if(tokens[i] == "Psychosocial support") {

                document.getElementById("serviceEnrollment1").checked = true;
            }

            else if(tokens[i] == "Nutrition counseling") {

                document.getElementById("serviceEnrollment2").checked = true;
            }

            else if(tokens[i] == "Health referral"){

                document.getElementById("serviceEnrollment3").checked = true;
            }
          }

             var completedbyName = ovcDetails[43];
             tokens = tokenizeStr(completedbyName);

            document.getElementById("completedbyName1").value = tokens[0];
            document.getElementById("completedbyName2").value = tokens[1];
            document.getElementById("completedbyDesignation").value = ovcDetails[44];
            document.getElementById("verifiedBy").value = ovcDetails[45];
            if(searchIndex==1)
            {
                document.getElementById("CbosSelect").value = ovcDetails[46];
                document.getElementById("WardsSelect").options[0]=new Option(ovcDetails[47],ovcDetails[47])
            }
            document.getElementById("WardsSelect").value=ovcDetails[47]
            
            document.getElementById("ovcId").value = ovcDetails[48];
            serialNo=ovcDetails[48].substr(ovcDetails[48].lastIndexOf("/")+1)
            document.getElementById("ovcSno").value=serialNo
            ageValues=ovcDetails[49].split(";")
            searchIndex=0
            if(ageValues[0]=="overaged")
            alert("This Ovc is above 17 years old. Current age is "+ageValues[1])
            enableControl("newBtn")
            enableControl("modifyBtn")
            enableControl("deleteBtn")
            disableControl("saveBtn")

            }

}

function resetFieldValues()
{
    resetId=0
                 document.getElementById("dateEnrollment").value = " ";
                 document.getElementById("surname").value = " ";
                 document.getElementById("otherNames1").value = " ";
                 document.getElementById("otherNames2").value = " ";
                 document.getElementById("otherNames2").value = " ";
                 document.getElementById("gender").value = " ";
                 document.getElementById("age").value = " ";
                 document.getElementById("ageUnit").value =" ";
                 document.getElementById("weight").value = " ";
                 document.getElementById("address").value = " ";
                 document.getElementById("phone").value = " ";
                 document.getElementById("height").value = " ";

                 document.getElementById("eligibility1").checked = false;
                 document.getElementById("eligibility2").checked = false;
                document.getElementById("eligibility3").checked = false;
                document.getElementById("eligibility4").checked = false;
                document.getElementById("eligibility5").checked = false;
                document.getElementById("eligibility6").checked = false;
                document.getElementById("eligibility7").checked = false;
                document.getElementById("eligibility8").checked = false;
                document.getElementById("hivStatus1").checked = false;
                document.getElementById("hivStatus2").checked = false;
                document.getElementById("hivStatus3").checked = false;
                document.getElementById("birthCertificate").value = " ";
                document.getElementById("schoolStatus").value = " ";
                document.getElementById("schoolType1").checked = false;
                document.getElementById("schoolType2").checked = false;
                //document.getElementById("schoolName").value = " ";
                //document.getElementById("schoolNameSpan").innerHTML=document.getElementById("schText").innerHTML;
                document.getElementById("currentClass").value = " ";
                document.getElementById("orphanage").value = " ";
               document.getElementById("orphanageName").value = " ";

            document.getElementById("caregiverName1").value = " ";
            document.getElementById("caregiverName2").value = " ";

            document.getElementById("caregiverGender").value = " ";

            document.getElementById("caregiverAge").value = " ";
            document.getElementById("caregiverAddress").value = " ";
            document.getElementById("caregiverPhone").value = " ";
            document.getElementById("caregiverOccupation").value = " ";
            document.getElementById("caregiverRelationships").value = " ";
            document.getElementById("caregiverRelationships2").value = " ";

            document.getElementById("csiFactor1_1").checked = false;
            document.getElementById("csiFactor1_2").checked = false;
            document.getElementById("csiFactor1_3").checked = false;
            document.getElementById("csiFactor1_4").checked = false;
            document.getElementById("csiFactor2_1").checked = false;
            document.getElementById("csiFactor2_2").checked = false;
            document.getElementById("csiFactor2_3").checked = false;
            document.getElementById("csiFactor2_4").checked = false;
            document.getElementById("csiFactor3_1").checked = false;
            document.getElementById("csiFactor3_2").checked = false;
            document.getElementById("csiFactor3_3").checked = false;
            document.getElementById("csiFactor3_4").checked = false;


            document.getElementById("csiFactor4_1").checked = false;
            document.getElementById("csiFactor4_2").checked = false;
            document.getElementById("csiFactor4_3").checked = false;
            document.getElementById("csiFactor4_4").checked = false;
            document.getElementById("csiFactor5_1").checked = false;
            document.getElementById("csiFactor5_2").checked = false;
            document.getElementById("csiFactor5_3").checked = false;
            document.getElementById("csiFactor5_4").checked = false;
            document.getElementById("csiFactor6_1").checked = false;
            document.getElementById("csiFactor6_2").checked = false;
            document.getElementById("csiFactor6_3").checked = false;
            document.getElementById("csiFactor6_4").checked = false;
            document.getElementById("csiFactor7_1").checked = false;
            document.getElementById("csiFactor7_2").checked = false;
            document.getElementById("csiFactor7_3").checked = false;
            document.getElementById("csiFactor7_4").checked = false;

            document.getElementById("csiFactor8_1").checked = false;
            document.getElementById("csiFactor8_2").checked = false;
            document.getElementById("csiFactor8_3").checked = false;
            document.getElementById("csiFactor8_4").checked = false;
            document.getElementById("csiFactor9_1").checked = false;
            document.getElementById("csiFactor9_2").checked = false;
            document.getElementById("csiFactor9_3").checked = false;
            document.getElementById("csiFactor9_4").checked = false;
            document.getElementById("csiFactor10_1").checked = false;
            document.getElementById("csiFactor10_2").checked = false;
            document.getElementById("csiFactor10_3").checked = false;
            document.getElementById("csiFactor10_4").checked = false;
            document.getElementById("csiFactor11_1").checked = false;
            document.getElementById("csiFactor11_2").checked = false;
            document.getElementById("csiFactor11_3").checked = false;
            document.getElementById("csiFactor11_4").checked = false;
            document.getElementById("csiFactor12_1").checked = false;
            document.getElementById("csiFactor12_2").checked = false;
            document.getElementById("csiFactor12_3").checked = false;
            document.getElementById("csiFactor12_4").checked = false;
            document.getElementById("serviceEnrollment1").checked = false;
            document.getElementById("serviceEnrollment2").checked = false;
            document.getElementById("serviceEnrollment3").checked = false;

            document.getElementById("completedbyName1").value = " ";
            document.getElementById("completedbyName2").value = " ";
            document.getElementById("completedbyDesignation").value = " ";
            document.getElementById("verifiedBy").value = " ";
            disableControl("newBtn")
            disableControl("modifyBtn")
            disableControl("deleteBtn")
            enableControl("saveBtn")
            
 }
 function populateSchoolDropdownBox(value, id)
{
    document.getElementById(id).options.length=0;
    document.getElementById(id).options[0]=new Option(" ")
    for(var i=0; i<value.length; i++)
    {
        document.getElementById(id).options[i+1]=new Option(value[i],value[i])
    }
    cleanUp(id)
}
function setWardSelectBoxValues(value, id)
{
    document.getElementById(id).options.length=0;
    document.getElementById(id).options[0]=new Option(" ","")
    for(var i=0; i<value.length; i+=2)
    {
        document.getElementById(id).options[i+1]=new Option(trim(value[i]),value[i])
    }
    cleanUp(id)
}
function setSelectBoxValues(value, id)
{
    document.getElementById(id).options.length=0;
    document.getElementById(id).options[0]=new Option(" ","")
    for(var i=0; i<value.length; i+=2)
    {
        document.getElementById(id).options[i+1]=new Option(value[i],value[i+1])
    }
    cleanUp(id)
}
function setSelectBoxValues2(value, id)
{
    document.getElementById(id).options.length=0;
    var j=0;
    for(var i=0; i<value.length; i+=2)
    {
        document.getElementById(id).options[j]=new Option(value[i],value[i+1])
        j++
    }
}
function setSelectBoxValues3(value, id)
{
    document.getElementById(id).options.length=0;
    document.getElementById(id).options[0]=new Option("")
    var j=0;
    for(var i=0; i<value.length; i+=2)
    {
        var optionVal=value[i]+";"+value[i+1]
        document.getElementById(id).options[i+1]=new Option(value[i],optionVal)
    }
    cleanUp(id)
}
function cleanUp(id)
{
    for(i=0; i<document.getElementById(id).options.length; i++ )
    {
        if(document.getElementById(id).options[i].text=="")
        {
            document.getElementById(id).options[i]=null;
        }
    }
}
function tokenizeStr(str){

    var tokenArray = str.split(", ");
    return tokenArray;
}


function changeDateFormat(dateStr){

    var dateArray = dateStr.split("-");
    var newDateStr = dateArray[1] + "/" + dateArray[2] + "/" + dateArray[0];
    return newDateStr;
}
function setBtnName(name)
{
     if(name=="save")
     {
            document.getElementById("buttonId").value = name
            return true
     }
     if(confirm("Are you sure you want to "+name+" this record?"))
     {
            document.getElementById("buttonId").value = name
            return true
     }
       return false
}
function getDropDownValue(id)
{
      var dropDownValue=document.getElementById(id).value
      return dropDownValue;
}
function enableSNoField()
{
      if(getDropDownValue("WardsSelect")=="")
      {
            resetId()
      }
      else
      {
              enableControl("ovcSno")
      }
}
function resetId()
    {
        document.getElementById("ovcSno").value=" ";
        document.getElementById("characters").value=" "
        disableControl("ovcSno")
        disableControl("saveBtn")
        disableControl("modifyBtn")
        disableControl("deleteBtn")
        disableControl("newBtn")
    }
    function enableControl(id)
    {
        document.getElementById(id).disabled=false
    }
    function disableControl(id)
    {
        document.getElementById(id).disabled="disabled"
    }
    function checkControl(id)
    {
        document.getElementById(id).checked="checked"
    }
    function uncheckControl(id)
    {
        document.getElementById(id).checked=false
    }
    function clearField(id)
    {
        document.getElementById(id).value=" "
    }
    function childInSchool(boolValue)
    {
        if (boolValue=="Yes")
        {
            enableControl("schoolName")
            enableControl("schoolType1")
            enableControl("schoolType2")
            enableControl("currentClass")
        }
        else
        {
            disableControl("schoolName")
            disableControl("schoolType1")
            disableControl("schoolType2")
            disableControl("currentClass")
            clearField("currentClass")
            
            uncheckControl("schoolType1")
            uncheckControl("schoolType2")
        }
    }
    function childInOrphanage(boolValue)
    {
        if (boolValue=="Yes")
        {
            enableControl("orphanageName")
        }
        else
        {
            disableControl("orphanageName")
            clearField("orphanageName")
        }
    }
function makeSno(sNo)
{
        if(sNo=="" || sNo==" ")
        {
            sNo=document.getElementById("ovcSno").value
            document.getElementById("ovcId").value=" "
            if(sNo=="" || sNo==" ")
            {
                document.getElementById("ovcId").value=" "
                return false
            }
        }
        else if(isNaN(sNo))
        {
            alert("Serial number must be numeric")
            return false
        }
        else if(sNo.length>5)
        {
            alert("Serial number cannot be greater than 5 digits")
            document.getElementById("ovcId").value=" "
            return false
        }
        if(sNo.length==1)
            sNo="0000"+sNo
        else if(sNo.length==2)
            sNo="000"+sNo
        else if(sNo.length==3)
            sNo="00"+sNo
        else if(sNo.length==4)
            sNo="0"+sNo
        else if(sNo.length==5)
            sNo=sNo
        return sNo
    }
    function makeId()
    {
        var state=document.getElementById("stateId").value
        var lga=document.getElementById("lgaId").value
        var cbo=getDropDownValue("CbosSelect")
        var ward=getDropDownValue("WardsSelect")

        var sNo=trim(document.getElementById("ovcSno").value)
        if(sNo=="" || sNo==" ")
        {
            document.getElementById("ovcId").value=" "
            return
        }
        if(state=="" || state==" " || lga=="" || lga==" " || cbo=="" || cbo==" ")
        {
          alert("State,Lga and CBO must not be empty")
          document.getElementById("ovcSno").value=" "
          return
        }

        var sNo=trim(document.getElementById("ovcSno").value)
        sNo=makeSno(sNo)
        if(sNo==false)
        {
            document.getElementById("ovcSno").focus()
            //disableControl("saveBtn")
            disableControl("modifyBtn")
            disableControl("deleteBtn")
            disableControl("newBtn")
            return false
        }

        var uniqueId=state+"/"+lga+"/"+cbo+"/"+sNo
        document.getElementById("ovcId").value=uniqueId
        document.getElementById("lgaId").value=lga;
        document.getElementById("wardId").value=ward;
        document.getElementById("cboId").value=cbo;
        enableControl("saveBtn")
    }

    function getwards(cboId)
    {
        if(cboId=="")
        return
        var req=cboId+";"+"ward"
        getValuesFromDb('addcbo.do',req,'ward')
    }
    function populateWards()
    {
        var cboId=document.getElementById("CbosSelect").value
        getwards(cboId)
    }
    function trim(stringToTrim)
    {
           return stringToTrim.replace(/^\s+|\s+$/g,"");
    }
     function populateSchools(schtype)
     {
         var stateId=document.getElementById("stateId").value;
         var req=stateId+";"+"schoolList;"+schtype
        getValuesFromDb('addcbo.do',req,'schoolList')
     }
     function setSchoolName(value)
     {
         document.getElementById("schoolName").value=value
     }
     function checkDuplicateByName()
    {
        var sname=document.getElementById("surname").value
        var fname=document.getElementById("otherNames1").value
        var mname=document.getElementById("otherNames2").value
        var age=document.getElementById("age").value
        var ageunit=document.getElementById("ageUnit").value
        if(sname=="" || sname==" " || fname=="" || fname==" " || age=="" || age==" ")
        return
        var otherNames=fname
        if(mname !="" && mname !=" ")
        otherNames+=", "+mname
        var req="duplicateCheck"+";"+sname+":"+otherNames+":"+age+":"+ageunit+";"+"nameAndAge"
        getValuesFromDb('addcbo.do',req,'nameAndAge')
    }
    function checkDuplicateByCareGiver()
    {
        var sname=document.getElementById("surname").value
        var fname=document.getElementById("otherNames1").value
        var mname=document.getElementById("otherNames2").value
        var caregiverSname=document.getElementById("caregiverName1").value
        var caregiverFname=document.getElementById("caregiverName2").value
        var caregiverName=caregiverSname+", "+caregiverFname
        if(sname=="" || sname==" " || fname=="" || fname==" ")
        return
        var otherNames=fname
        if(mname !="" && mname !=" ")
        otherNames+=", "+mname
        var req="duplicateCheck"+";"+sname+":"+otherNames+":"+caregiverName+";"+"nameAndCaregiver"
        getValuesFromDb('addcbo.do',req,'nameAndCaregiver')
    }
     function checkDupId()
     {
         var ovcUniqueId=document.getElementById("ovcId").value;
         getOvcDetails(ovcUniqueId)
     }
     function getOvcDetails(ovcUniqueId)
     {
         if(ovcUniqueId==" " || ovcUniqueId=="")
         return
         retrieveURL('getCharacters.do?ovcId=' + ovcUniqueId)
     }
    function filterNames(namepart)
    {
        if(namepart=="" || namepart==" " || namepart==null)
        return
        req="partofnamestring;"+namepart
        getValuesFromDb('addcbo.do',req,'partofnamestring')
    }
    function showSearchDiv()
    {
        document.getElementById("pop").style.visibility="visible"
    }
    function hideComponent(id)
    {
        document.getElementById(id).style.visibility="hidden"
    }
    function showSelected(ovcId)
    {
        document.getElementById("title").innerHTML="Patient Id -->"+ovcId
    }
    function setPatDetails(ovcId)
    {
        searchIndex=1
        getOvcDetails(ovcId)
        hideComponent("pop")
    }
//-->
</script>
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />

</head>

<body onLoad="MM_preloadImages('images/tabs/care&amp;support_down.jpg','images/tabs/Rapid_assesment_down.jpg','images/tabs/Administration_down.jpg','images/tabs/About_down.jpg')">
<table width="941" border="0" align="center" cellpadding="0" cellspacing="0">
  <!--DWLayoutTable-->
  <tr>
    <td width="170" height="38">&nbsp;</td>
    <td width="751" rowspan="3" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->
      <tr>
        <td width="751" height="33" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!--DWLayoutTable-->
          <tr>
            <td width="642" height="27"></td>
                <td width="109" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="109" height="27"><a href="login.jsp"><img src="images/tabs/log_out.jpg" width="109" height="27" border="0" /></a></td>
                    </tr>
                </table></td>
              </tr>
          <tr>
            <td height="6"></td>
                <td></td>
              </tr>


          </table></td>
        </tr>
      <tr>
        <td height="38" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!--DWLayoutTable-->
          <tr>
            <td width="125" height="38" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="125" height="38"><a href="OVC.jsp"><img src="images/tabs/OVCmain.jpg" width="125" height="38" border="0" /></a></td>
                    </tr>
              </table></td>
                <td width="126" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                        <td width="126" height="38"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('care&amp;support','','images/tabs/care&amp;support_down.jpg',1)"><img src="images/tabs/care&amp;support_up.jpg" alt="care&amp;support" name="careandsupport" width="126" height="38" border="0" id="careandsupport" /></a>  </td>
                      
                    </tr>
                </table></td>
                <td width="126" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                        <td width="126" height="38"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Rapid Assesment','','images/tabs/Rapid_assesment_down.jpg',1)"><img src="images/tabs/Rapid_assesment_up.jpg" alt="Rapid Assesment" name="Rapid Assesment" width="126" height="38" border="0" id="Rapid Assesment" /></a></td>
                      
                    </tr>
                </table></td>
                <td width="126" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="126" height="38"><a href="administration.html" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Administration','','images/tabs/Administration_down.jpg',1)"><img src="images/tabs/Administration_up.jpg" alt="Administration" name="Administration" width="126" height="38" border="0" id="Administration" /></a></td>
                    </tr>
                </table></td>
                <td width="126" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="126" height="38"><a href="#" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('About','','images/tabs/About_down.jpg',1)"><img src="images/tabs/About_up.jpg" alt="About" name="About" width="126" height="38" border="0" id="About" /></a></td>
                    </tr>
                </table></td>
                <td width="122" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="122" height="38"><img src="images/tabs/Last.jpg" width="122" height="38" /></td>
                    </tr>
                </table>            </td>
              </tr>
          </table></td>
        </tr>
      <tr>
        <td height="538" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="boaderall">
          <!--DWLayoutTable-->
          <tr>
            <td width="747" height="1388">
			<div align="center">
			<div style="width:75+7px; height:1388px; background-color:#FFFFFF">
			<div align="left">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
     <td width="30%" height="20" class="homepagestyle">OVC Enrollment form </td>
     <td width="70%"> <html:errors/> </td>
    </tr>
     <tr><td height="10" colspan="2" align="center"><label style=" font-size: 15px;">Partner name:  ${partnername}</label> </td></tr>
  </table>
    <html:form styleId="dataForm"  method="post" action="/Add">
            <input type="hidden" name="stateId2" />
            <input type="hidden" name="lgaId2" />
            <html:hidden property="lga" styleId="lgaId" value="${lga.lga_code}" />
            <input type="hidden" name="lgaCode" id="lgaCode" />
            <input type="hidden" name="stateId" id="stateId" value="${state.state_code}" />
            <html:hidden property="actionName" styleId="buttonId"/>
            <html:hidden property="childIndexId" styleId="childIndexId" />
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
        <!--DWLayoutTable-->
        <tr>
          <td width="8" height="1258">&nbsp;</td>
            <td colspan="2" valign="top">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <!--DWLayoutTable-->
                <tr>
                  <td width="752" height="28" valign="top">
                      <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="17">

                          <table width="100%">
                          <tr>
                              <td colspan="4" align="center"> </td>

                            </tr>
                              
                              <tr>
                                  <td width="4%"><label>State:</label></td>
                          <td width="30%">
                              <html:text styleId="stateList" property="state" readonly="true" styleClass="bigfieldcellinput" value="${state.name}" ></html:text>

                          </td>
                              <td width="4%"><label>LGA:</label></td>
                              <td width="30%"><span id="selected">
                                      <%--<label class="bigfieldcellinput">${lga.name}</label>--%>
                                      <input type="text" class="bigfieldcellinput" readonly="readonly" id="LgasSelect" value="${lga.lga_name}"/></span></td>

                            </tr>
                        <tr>
                            <td width="4%" ><label>CBO:</label></td>
                              <td width="30%">
                                  <span id="selected2">
                                      <html:select property="completedbyCbo" styleId="CbosSelect" styleClass="bigfieldcellinput" style="width:250px;" onchange="getwards(this.value);">
                                      <html:option value=""> </html:option>
                                      <logic:iterate id="cbos" name="cbolist">
                                              <html:option value="${cbos.cbo_code}">${cbos.cbo_name} </html:option>
                                          </logic:iterate>
                                      </html:select>


                                  </span></td>
                            <td width="4%"><label >Ward:</label></td>

                              <td width="30%">
                                  <html:select property="ward" styleId="WardsSelect" styleClass="bigfieldcellinput">
                                  <html:option value=""> </html:option>
                                  <logic:iterate id="lastward" name="lastwardlist">
                                        <html:option value="${lastward.ward_name}">${lastward.ward_name} </html:option>
                                  </logic:iterate>


                              </html:select>
                                  </td>
                            </tr>

                        </table>
                      </td>
                      </tr>
                  </table></td>
                </tr>


                <tr>
                  <td height="210" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="122" valign="top">


                          <fieldset>
                        <legend class="fieldset">Personal information  </legend>
                        <table width="100%" class="regsitertable">
                        <tr><td width="15%" valign="top" class="right">Enrollment date:</td>

                              <td>
                                  <html:text property="dateEnrollment" styleId="dateEnrollment" styleClass="smallfieldcellinput"/>
                                  
                                  &nbsp;&nbsp;<span style="margin-left:110px;">OVC serial no.</span>
                                  <html:text property="ovcSno"  styleClass="smallfieldcellinput" styleId="ovcSno" onkeyup="makeId()" onblur="checkDupId()"/>&nbsp;<input type="button" name="search" value="Search by name" onclick="showSearchDiv()"/></td>
                            </tr>

                        <tr>
                          <td valign="top" class="right">OVC unique id no. </td>
                          <td><html:text property="ovcId" readonly="true" styleClass="fieldcellinput" styleId="ovcId" /><!--styleId="characters" styleId="ovcId"--><br />
                              <span class="textunder" style="font-size:9px;">(State code/LGA code/Org. code/ OVC serial no) </span></td>
                            </tr>
                        <tr>
                          <td valign="top" class="right">Surname: </td>
                              <td>
                                  
                                  <html:text property="surname" styleId="surname" styleClass="fieldcellinput" onkeyup="capitalizeAll('surname', this.value)" onblur="checkDuplicateByName()"/> 
                                  <span style="margin-left:17px; margin-right: 15px;">First name:</span> <html:text property="otherNames1" styleId="otherNames1" styleClass="smallfieldcellinput" onkeyup="capitalize('otherNames1', this.value)" onblur="checkDuplicateByName()" />
                                  <span style="margin-left:10px; margin-right: 5px;">Middle name:</span><html:text property="otherNames2" styleId="otherNames2" styleClass="smallfieldcellinput" onkeyup="capitalize('otherNames2', this.value)" onchange="checkDuplicateByName()"/></td>
                            </tr>
                        <tr>
                          <td valign="top" class="right">Sex: </td>
                              <td>
                                    <html:select property="gender" styleId="gender" styleClass="fieldcellinput" style="width:70px;">
                    <html:option value=""> </html:option><html:option value="Male">Male</html:option><html:option value="Female">Female</html:option>
                                    </html:select> <span style=" margin-left: 52px;">Age:</span>
                    <html:select property="age" styleId="age" styleClass="shortfieldcellinput" onchange="checkDuplicateByName()">

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
                </html:select><span style=" margin-left: 21px; margin-right: 35px;">Age unit</span><html:select styleId="ageUnit" styleClass="fieldcellinput" property="ageUnit" style="width:82px;" onchange="checkDuplicateByName()">
                    <html:option value=""></html:option>
                    <html:option value="Year">Year</html:option>
                    <html:option value="Month">Month</html:option>
                </html:select><span style="margin-left:32px; margin-right: 18px;">Weight(kg)</span><html:text property="weight" styleId="weight" styleClass="shortfieldcellinput" /></td>
                            </tr>


							 <tr>
                          <td valign="top" class="right">&nbsp;</td>
                              <td><span id="characters3" style="color: red"></span>  </td>
                            </tr>


                        <tr>
                            <td valign="top" class="right" style=" height: 20px;"><br/>Address: </td>
                              <td>
                                    <html:textarea property="address" styleId="address" rows="4" cols="80" styleClass="textareacellinput"></html:textarea>
                                    <span style=" margin-left: 16px; margin-right: 18px;">Telephone:</span> <html:text property="phone" styleId="phone" styleClass="smallfieldcellinput" /><span style="margin-left:12px; margin-right: 18px;">Height(cm):</span><html:text property="height" styleId="height" styleClass="shortfieldcellinput" />                               </td>
                            </tr>
                        </table>
                      </fieldset>




                      </td>
                      </tr>
                  </table></td>
                </tr>
                <tr>
                  <td height="123" valign="top">
                      <fieldset>
                        <legend class="fieldset">Eligibility criteria </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="102">
                          <table width="733" border="1" bordercolor="#D7E5F2" class="regsitertable">
                        <tr>
                          <td width="30%">Child is HIV positive </td>
                          <td width="15%"><html:checkbox property="eligibility1" styleId="eligibility1" value="Child is HIV positive" styleClass="smallfieldcellselect"/><span style="width:130px;">&nbsp; </span></td>
                              <td width="39%">Living with a chronically ill parent (from any cause) </td>
                              <td width="31%"><html:checkbox property="eligibility5" styleId="eligibility5" value="Living with a chronically ill parent(from any cause)" styleClass="smallfieldcellselect"/></td>
                            </tr>
                        <tr>
                          <td>Child has lost one  parent (to whatever cause)</td>
                              <td><html:checkbox property="eligibility2" styleId="eligibility2" value="Lost one parent" styleClass="smallfieldcellselect"/></td>
                              <td>Living in a child headed household </td>
                              <td><html:checkbox property="eligibility6" styleId="eligibility6" value="Living in a child headed household" styleClass="smallfieldcellselect"/></td>
                            </tr>
                        <tr>
                          <td>Child has lost both parents (to whatever cause)</td>
                              <td><html:checkbox property="eligibility3" value="lost both parents" styleId="eligibility3" styleClass="smallfieldcellselect"/></td>
                              <td>Child living with frail old grandparents </td>
                              <td><html:checkbox property="eligibility7" styleId="eligibility7" value="Child living with frail old grandparents" styleClass="smallfieldcellselect"/></td>
                            </tr>
                        <tr>
                          <td>Child lives on the street </td>
                              <td><html:checkbox property="eligibility4" styleId="eligibility4" styleClass="smallfieldcellselect" value="Child lives on the street"/></td>
                              <td>Child labourer </td>
                              <td><html:checkbox property="eligibility8" styleId="eligibility8" styleClass="smallfieldcellselect" value="Child labourer"/></td>
                            </tr>


                        </table></td>
                      </tr>
                  </table>
                  </fieldset></td>
                </tr>
                <tr>
                  <td height="53" valign="top">

                      <fieldset>
                        <legend class="fieldset">Child's HIV status </legend><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="34"><table width="100%" class="regsitertable">
                        <tr>
                          <td width="16%">HIV status unknown</td>
                          <td width="10%"><html:radio property="hivStatus" styleClass="smallfieldcellselect" styleId="hivStatus1" value="HIV status unknown" /></td>
                          <td width="10%">HIV negative </td>
                          <td width="10%"><html:radio property="hivStatus" styleClass="smallfieldcellselect" styleId="hivStatus2" value="HIV negative" /></td>
                          <td width="10%">HIV positive </td>
                          <td width="58%"><html:radio property="hivStatus" styleClass="smallfieldcellselect" styleId="hivStatus3" value="HIV positive" /></td>
                        </tr>
                      </table></td>
                    </tr>
                  </table>
                  </fieldset></td>
                </tr>
                <tr>
                  <td height="167" valign="top"><fieldset>
                        <legend class="fieldset">Birth registration and education </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="48"><table width="100%" class="regsitertable">
                        <tr>
                          <td width="40%" class="right">Does the child have birth registration certificate? </td>
                              <td width="60%"><label>
                                      <html:select property="birthCertificate" styleId="birthCertificate" styleClass="shortfieldcellinput">
                    <html:option value=" "> </html:option>
                    <html:option value="Yes">Yes</html:option>
                    <html:option value="No">No</html:option>
                </html:select>
                                  </label> <span style=" margin-left: 10px; margin-right: 10px;">Is the child in school?</span> <html:select property="schoolStatus" styleId="schoolStatus" onchange="childInSchool(this.value)" styleClass="shortfieldcellinput">
                                   <html:option value=" "> </html:option>
                                   <html:option value="Yes">Yes</html:option>
                                   <html:option value="No">No</html:option>
                                   </html:select>
                              </td>
                            </tr>
                         <tr>
                          <td class="right">Is the school the child is enrolled Public or Private: <br /></td>
                              <td><label>
                                      <!--changeSchoolType('schCombo') changeSchoolType('schText')-->
                                      Public school &nbsp;<html:radio property="schoolType" styleId="schoolType1" disabled="true" value="Public" styleClass="smallfieldcellselect" onclick="populateSchools('public')" />
                                                                                <span style=" margin-left: 13px;">Private school</span>
                                                                                &nbsp;<html:radio property="schoolType" styleId="schoolType2" disabled="true" styleClass="smallfieldcellselect" value="Private" onclick="populateSchools('private')" />
                         </label></td>
                            </tr>
                        <tr>
                          <td class="right">Name of school:</td>
                          <td><span id="schoolNameSpan">
                                  <html:select styleClass="fieldcellinput" property="schoolName" styleId="schoolName" disabled="true" >
                                      <html:option value=" "> </html:option>
                                      <logic:iterate id="school" name="schools">
                                      <html:option value="${school.name}">${school.name}</html:option>
                                      </logic:iterate>
                                  </html:select>
                                  <!-- onclick="populateSchools()" <input type="text" id="schoolName1" class="fieldcellinput" disabled="true" />--></span> Class: <html:text property="currentClass" styleId="currentClass" styleClass="smallfieldcellinput" disabled="true" /></td>
                        </tr>

                        <tr>
                          <td class="right">Does the child live in orphange: </td>
                              <td>
                                  <html:select property="orphanage" styleClass="shortfieldcellinput" styleId="orphanage" onchange="childInOrphanage(this.value)">
                                  <html:option value=" "> </html:option>
                                <html:option value="Yes">Yes</html:option>
                    <html:option value="No">No</html:option>
                </html:select>
                                  </td>
                            </tr>
                        <tr>
                          <td class="right">Name of orphanage:</td>
                          <td><html:text property="orphanageName" styleId="orphanageName" styleClass="fieldcellinput" disabled="true" /></td>
                        </tr>
                        </table></td>
                      </tr>
                  </table>
                  </fieldset></td>
                </tr>
                <tr>
                  <td height="553" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="752" height="92"><fieldset>
                        <legend class="fieldset">Caregiver information </legend>
                        <table width="100%" class="regsitertable">
                            <tr>
                          <td width="20%" bgcolor="#F0F0F0"><div align="left">Surname: </div></td>
                          <td width="85%" bgcolor="#F0F0F0"><html:text property="caregiverName1" styleId="caregiverName1" styleClass="fieldcellinput" onblur="checkDuplicateByCareGiver()" />
                              <span style=" margin-left:10px; margin-right: 13px;">First name:</span> <html:text property="caregiverName2" styleId="caregiverName2" styleClass="fieldcellinput" onblur="checkDuplicateByCareGiver()" /><span id="characters4" style="margin-left:20px;color: red"></span></td>
                            </tr>
                          <tr>
                           <td bgcolor="#F0F0F0"><div align="left">Sex:</div></td>
                          <td bgcolor="#F0F0F0">
                              <html:select property="caregiverGender" styleId="caregiverGender" styleClass="smallfieldcellinput"><html:option value=""> </html:option><html:option value="Male">Male</html:option>
                                  <html:option value="Female">Female</html:option></html:select>
                                  <span style=" margin-left: 112px; margin-right:56px;">Age:</span><html:text property="caregiverAge" styleId="caregiverAge" styleClass="shortfieldcellinput"/></td>
                            </tr>
                           <tr>
                            <td class="right">Address:</td>
                            <td colspan="5"><html:textarea property="caregiverAddress" styleId="caregiverAddress" rows="5" cols="40" styleClass="fieldcellinput"></html:textarea></td>
                            </tr>
                          <tr>
                            <td class="right">Telephone:</td>
                            <td colspan="5"><html:text property="caregiverPhone" styleId="caregiverPhone" styleClass="smallfieldcellinput" />
                                <span style="margin-left:110px; margin-right: 15px;">Occupation: </span><html:text property="caregiverOccupation" styleId="caregiverOccupation" styleClass="fieldcellinput" /></td>
                            </tr>
                          <tr>
                            <td class="right"> </td>
                            <td colspan="5"></td>
                            </tr>
                          <tr>
                              <td class="right" ><div>Relationship to child:
                                 </div></td>
                              <td width="60%" bordercolor="#FFFFFF" bgcolor="#F0F0F0"><html:select property="caregiverRelationships" styleId="caregiverRelationships" styleClass="fieldcellinput">
                                      <html:option value=" "> </html:option>
                                      <html:option value="Mother/Father">Mother/Father</html:option>
                    <html:option value="Aunt/Uncle">Aunt/Uncle</html:option>
                    <html:option value="Social worker/Orphanage caregiver">Social worker/Orphanage caregiver</html:option>
					<html:option value="Grandparents">Grandparents</html:option>
					<html:option value="Brother/Sister">Brother/Sister</html:option>
                                  </html:select> <span style=" margin-left: 12px;">Other Relative:</span>  <html:text property="caregiverRelationships2" styleId="caregiverRelationships2" styleClass="fieldcellinput"/></td>

                          </tr>


                        </table></fieldset></td>
                      </tr>
                    <tr>
                      <td height="172" valign="top"><fieldset>
                        <legend class="fieldset">Baseline / Initial child status index assessment </legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" height="172">
                              <table width="100%" border="1" bordercolor="#D7E5F2" class="regsitertable">
                            <tr>
                              <td width="17%" rowspan="2"><strong>Domain</strong></td>
                              <td colspan="4"><strong>Score<br />
                                (Mark as appropriate)</strong></td>
                              <td width="21%" rowspan="2"><strong>Domain</strong></td>
                              <td colspan="4"><strong>Score<br />
(Mark as appropriate)</strong></td>
                              </tr>
                            <tr>
                              <td width="7%" align="center">Very Bad<br /><div align="center">1</div> </td>
                              <td width="7%" align="center">Bad<br /><div align="center">2</div></td>
                              <td width="7%" align="center">Fair<br /><div align="center">3</div></td>
                              <td width="7%" align="center">Good<br /><div align="center">4</div></td>
                              <td width="7%" align="center">Very Bad<br /><div align="center">1</div> </td>
                              <td width="7%" align="center">Bad<br /><div align="center">2</div></td>
                              <td width="7%" align="center">Fair<br /><div align="center">3</div></td>
                              <td width="7%" align="center">Good<br /><div align="center">4</div></td>
                            </tr>
                            <tr>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Psychosocial</strong></td>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Education and work </strong></td>
                              </tr>
                            <tr>
                              <td>Emotional health </td>
                              <td align="center"><html:radio property="csiFactor1" styleId="csiFactor1_1" value="1" />
                            </td><td align="center"><html:radio property="csiFactor1" styleId="csiFactor1_2" value="2" />
                            </td><td align="center"><html:radio property="csiFactor1" styleId="csiFactor1_3" value="3" />
                              </td><td align="center"><html:radio property="csiFactor1" styleId="csiFactor1_4" value="4" /></td>
                              <td>Performance</td>
                              <td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_1" value="1" />
                              </td><td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_2" value="2" />
                              </td><td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_3" value="3" />
                              </td><td align="center"><html:radio property="csiFactor7" styleId="csiFactor7_4" value="4" /></td>
                            </tr>
                            <tr>
                              <td>Social behaviour </td>
                              <td align="center"><html:radio property="csiFactor2" styleId="csiFactor2_1" value="1" /></td><td align="center"><html:radio property="csiFactor2" styleId="csiFactor2_2" value="2" /></td><td align="center"><html:radio property="csiFactor2" styleId="csiFactor2_3" value="3" /></td><td align="center"><html:radio property="csiFactor2" styleId="csiFactor2_4" value="4" /></td>
                              <td>School work </td>
                              <td align="center"><html:radio property="csiFactor8" styleId="csiFactor8_1" value="1" /></td><td align="center"><html:radio property="csiFactor8" styleId="csiFactor8_2" value="2" /></td><td align="center"><html:radio property="csiFactor8" styleId="csiFactor8_3" value="3" /></td><td align="center"><html:radio property="csiFactor8" styleId="csiFactor8_4" value="4" /></td>
                            </tr>
                            <tr>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Food and nutrition </strong></td>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Protection</strong></td>
                              </tr>
                            <tr>
                              <td>Food security </td>
                              <td align="center"><html:radio property="csiFactor3" styleId="csiFactor3_1" value="1" /></td><td align="center"><html:radio property="csiFactor3" styleId="csiFactor3_2" value="2" /></td><td align="center"><html:radio property="csiFactor3" styleId="csiFactor3_3" value="3" /></td><td align="center"><html:radio property="csiFactor3" styleId="csiFactor3_4" value="4" /></td>
                              <td>Abuse and exploitation </td>
                              <td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_1" value="1" /></td><td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_2" value="2" /></td><td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_3" value="3" /></td><td align="center"><html:radio property="csiFactor9" styleId="csiFactor9_4" value="4" /></td>
                            </tr>
                            <tr>
                              <td>Nutrition and growth </td>
                              <td align="center"><html:radio property="csiFactor4" styleId="csiFactor4_1" value="1" /></td><td align="center"><html:radio property="csiFactor4" styleId="csiFactor4_2" value="2" /></td><td align="center"><html:radio property="csiFactor4" styleId="csiFactor4_3" value="3" /></td><td align="center"><html:radio property="csiFactor4" styleId="csiFactor4_4" value="4" /></td>
                              <td>Legal protection </td>
                              <td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_1" value="1" /></td><td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_2" value="2" /></td><td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_3" value="3" /></td><td align="center"><html:radio property="csiFactor10" styleId="csiFactor10_4" value="4" /></td>
                            </tr>
                            <tr>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Health</strong></td>
                              <td colspan="5" bgcolor="#F0F0F0"><strong>Shelter and care </strong></td>
                              </tr>
                            <tr>
                              <td>Wellness</td>
                              <td align="center"><html:radio property="csiFactor5" styleId="csiFactor5_1" value="1" /></td><td align="center"><html:radio property="csiFactor5" styleId="csiFactor5_2" value="2" /></td><td align="center"><html:radio property="csiFactor5" styleId="csiFactor5_3" value="3" /></td><td align="center"><html:radio property="csiFactor5" styleId="csiFactor5_4" value="4" /></td>
                              <td>Shelter</td>
                              <td align="center"><html:radio property="csiFactor11" styleId="csiFactor11_1" value="1" /></td><td align="center"><html:radio property="csiFactor11" styleId="csiFactor11_2" value="2" /></td><td align="center"><html:radio property="csiFactor11" styleId="csiFactor11_3" value="3" /></td><td align="center"><html:radio property="csiFactor11" styleId="csiFactor11_4" value="4" /></td>
                            </tr>
                            <tr>
                              <td>Health care services </td>
                              <td align="center"><html:radio property="csiFactor6" styleId="csiFactor6_1" value="1" /></td><td align="center"><html:radio property="csiFactor6" styleId="csiFactor6_2" value="2" /></td><td align="center"><html:radio property="csiFactor6" styleId="csiFactor6_3" value="3" /></td><td align="center"><html:radio property="csiFactor6" styleId="csiFactor6_4" value="4" /></td>
                              <td>Care</td>
                              <td align="center"><html:radio property="csiFactor12" styleId="csiFactor12_1" value="1" /></td><td align="center"><html:radio property="csiFactor12" styleId="csiFactor12_2" value="2" /></td><td align="center"><html:radio property="csiFactor12" styleId="csiFactor12_3" value="3" /></td><td align="center"><html:radio property="csiFactor12" styleId="csiFactor12_4" value="4" /></td>
                            </tr>
                            <tr>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                              <td>&nbsp;</td>
                            </tr>

                          </table></td>
                        </tr>
                      </table></fieldset>                      </td>
                      </tr>
                    <tr>
                      <td height="52" valign="top"><fieldset>
                        <legend class="fieldset">Services child recieved at intake <span class="opt style1"><em>( Tick if child received services at this encounter ) </em></span></legend>
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                          <td width="752" height="33"><table width="100%">
                            <tr>
                              <td width="17%"><label>Psychosocial support</label> </td>
                              <td width="16%"><html:checkbox property="serviceEnrollment1" styleId="serviceEnrollment1" value="Psychosocial support" /></td>
                              <td width="16%"><label>Nutrition counseling</label> </td>
                              <td width="13%"><html:checkbox property="serviceEnrollment2" styleId="serviceEnrollment2" value="Nutrition counseling" /></td>
                              <td width="12%"><label>Health referral</label> </td>
                              <td width="49%"><html:checkbox property="serviceEnrollment3" styleId="serviceEnrollment3" value="Health referral" /></td>
                            </tr>
                              </table>
                            <table>
                            <tr>
                          <td width="752" height="28" colspan="6">
                              ----- <span class="homepagestyle">Completed by :-------------------------------------------------------------------------------------------------------------</span></td>
                      </tr>
                            <tr>
                              <td width="5%"><label>Surname</label> </td>
                              <td width="6%"><html:text property="completedbyName1" styleId="completedbyName1" styleClass="smallfieldcellinput" /></td>
                              <td width="8%"><label>First name</label> </td>
                              <td width="5%"><html:text property="completedbyName2" styleId="completedbyName2" styleClass="smallfieldcellinput" style="margin-left:10px" /></td>
                              <td width="5%"><label >Designation</label></td>
                              <td width="25%"><html:text property="completedbyDesignation" styleId="completedbyDesignation" styleClass="smallfieldcellinput" style="margin-left:5px" /> <label style="margin-left: 5px;">Verified by</label><html:text property="verifiedBy" styleId="verifiedBy" styleClass="smallfieldcellinput"/></td>
                            </tr>
                          </table></td>
                        </tr>
                      </table>
                      </fieldset></td>
                    </tr>
                    <tr>
                      <td height="27" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <!--DWLayoutTable-->
                        <tr>
                            <td width="752" height="27">&nbsp;</td>
                        </tr>
                      </table>
                      </td>
                    </tr>
                    <tr>
                        <td height="40" align="center">
                            <html:reset value="New" styleId="newBtn" style="width:70px; height:25px;  margin-right:5px;" disabled="true"/><html:submit value="Save" styleId="saveBtn" style="width:70px; height:25px; margin-right:5px;" onclick="return setBtnName('save')" disabled="${enableSave}"/>
                           <html:submit value="Modify" styleId="modifyBtn" onclick="return setBtnName('modify')" style="width:70px; height:25px; margin-right:5px;" disabled="${enableModify}"/><html:submit value="Delete" styleId="deleteBtn"  onclick="return setBtnName('delete')" style="width:70px; height:25px; margin-right:5px;" disabled="${enableModify}"/>
                        </td>
                    </tr>

                  </table></td>
                </tr>

            </table></td>
            <td width="10">&nbsp;</td>
        </tr>

        <tr>
          <td height="30">&nbsp;</td>
          <td width="741">&nbsp;</td>
          <td colspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
            <!--DWLayoutTable-->
            <tr>
              <td width="31" height="30">&nbsp;</td>
                </tr>
          </table></td>
          </tr>
        <tr>
          <td height="1"></td>
          <td></td>
          <td width="21"></td>
          <td></td>
        </tr>
        </table>

                           <div id="schText" style=" visibility: hidden; position: absolute"><span><input type="text" id="privateSchool" class="fieldcellinput" onblur="setSchoolName(this.value)" /></span></div>
                           <%--<div id="schCombo" style=" visibility: hidden; position: absolute"><select class="fieldcellinput" id="publicSchool" onclick="populateSchools()" onchange="setSchoolName(this.value)" ><option value=" "> </option> </select></div>
                           <span id="schCombo" style=" visibility: hidden; position: absolute"><html:select property="schoolName2" styleId="schoolName2" styleClass="fieldcellinput" disabled="true" > <option value=" "> </option></html:select></span>--%>
    </html:form>
    
</div>						
</div>
</div>
			
			
			
			</td>
              </tr>


        </table></td>
        </tr>









    </table></td>
  </tr>
  <tr>
    <td height="448" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->

      <tr>
        <td width="170" height="69" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
          <!--DWLayoutTable-->
          <tr>
            <td width="170" height="69"><img src="images/tabs/logo.jpg" width="170" height="69" /></td>
              </tr>
          </table></td>
        </tr>





        <tr>
          <td height="376" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" class="leftpane_border">
              <!--DWLayoutTable-->
              <tr>
                <td width="168" height="25" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!--DWLayoutTable-->
                  <tr>
                    <td width="168" height="25"><img src="images/tabs/data_E_ELeftpane_header.jpg" width="168" height="25" /></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td height="85" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!--DWLayoutTable-->
                  <tr>
                    <td width="168" height="88" valign="top">
                        <div style="float: left" id="my_menu" class="sdmenu">
                            <div><a href="dataentry.jsp">Enrolment form </a> <a href="serviceform.jsp">Service form </a><a href="followup.jsp">Follow up survey form</a></div>
                      </div></td>
                    </tr>


                    </table></td>
              </tr>
              <tr>
                <td height="25" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                  <!--DWLayoutTable-->
                  <tr>
                    <td width="168" height="25"><img src="images/tabs/Repots_leftpane_header.jpg" width="168" height="25" /></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td height="234" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                    <!--DWLayoutTable-->
                    <tr>
                      <td width="168" height="88" valign="top"><div style="float: left" id="div" class="sdmenu"><!--<a href="Reports/EnrolRegisterDatePage.jsp" target="_blank">Enrollment Register </a> <a href="Reports/EnrolRegisterDatePage.jsp" target="_blank">Enrollment Register </a>-->
                              <div id="reportsDiv" ><a href="Reports/CsiParamPage.jsp" target="_blank" >CSI analysis</a><a href="Reports/EnrollmentRecord.jsp" target="_blank" >Enrollment records</a><a href="Reports/SchoolAttendanceParamPage.jsp" target="_blank" >School attendance</a><a href="Reports/SummStatParamPage.jsp" target="_blank" >Summary statistics</a><a href="Reports/DqaParamPage.jsp" target="_blank" >Data quality assessment</a><a href="Reports/OvcMthlySummaryFormParamPage.jsp" target="_blank" >OVC Monthly summary form</a><a href="ChartPage.jsp">Charts </a><!--<a href="Reports/OvcBaselineDetails.jsp" target="_blank" >Baseline Details</a>--></div>
                      </div></td>
                    </tr>
                  </table></td>
              </tr>

          </table></td>
        </tr>
      <tr>
        <td height="3"></td>
        </tr>

    </table></td>
  </tr>
  <tr>
    <td height="123"></td>
  </tr>
</table> 
   
    <%
        session.removeAttribute("enableSave");
        session.removeAttribute("enableModify");
        //session.removeAttribute("enableDelete");
    %>
</body>
<div id="pop" class="search" style="width:210px;">
    <table><tr><td style="width:208px;"><label id="title" style="color:#FFFFFF; width:198px;">Browse</label></td><td><img name="popClose" src="images/close.jpg" style="width:10px; height:10px;" onClick="hideComponent('pop')"/></td></tr>
        <tr><td colspan="2" align="left"><span><input type="text" name="selectedName" style="width:195px;" style="margin-top:0px;" onkeyup="filterNames(this.value)"/></span></td></tr>
        <tr><td colspan="2"><span id="ovcNames"> </span></td></tr>
    </table>
</div>

</html>

