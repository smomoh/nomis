
<%@page import="javax.servlet.http.HttpSession" %>
<%@page import="com.fhi.kidmap.business.*;" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>



<%
            LoadUpInfo loadup = new LoadUpInfo();
            loadup.getAllStates(session);
%>

<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

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
                margin-bottom: 0px;
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
        <script type="text/JavaScript">
            <!--
            function MM_swapImgRestore() { //v3.0
                var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
            }

            function MM_preloadImages() { //v3.0
                var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                        if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
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

        <script language="javascript">

                //var chartId;

                function retrieveURL(url) {

                    if (window.XMLHttpRequest) { // Non-IE browsers
                        req = new XMLHttpRequest();
                        req.onreadystatechange = processStateChange;
                        try {
                            req.open("GET", url, true);
                        } catch (e) {
                            alert(e);
                        }
                        req.send(null);
                    } else if (window.ActiveXObject) { // IE
                        req = new ActiveXObject("Microsoft.XMLHTTP");
                        if (req) {
                            req.onreadystatechange = processStateChange;
                            req.open("GET", url, true);
                            req.send();
                        }
                    }
                }

                function processStateChange() {
                    if (req.readyState == 4) { // Complete
                        if (req.status == 200) { // OK response

       
                            var newWindow = window.open("mainreportspage.jsp","","width=337,height=285,left=230,top=70,resizable=no,scrollbars=no,menubar=no,location=no,status=no");
		  
                            /*var newWindow = window.open("mainreportspage.jsp","Enrolment Register","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0");*/
		  
		  

                        } else {
                            alert("Problem: " + req.statusText);
                        }
                    }
                }

                function showNewLoginWindow() {

                    var newWindow = window.open("changeloginpage.jsp","","width=278,height=210,left=230,top=70,resizable=no,scrollbars=no,menubar=no,location=no,status=no");
                }



                function setDivContent(id) {
                    document.getElementById("wizard").innerHTML = document.getElementById(id).innerHTML;
                    document.getElementById("contentContainer").style.background="#FFF";
                }


                function hideComponent()
                {
                    //document.getElementById(id).style.visibility="hidden
                    document.getElementById("wizard").innerHTML = "";
                }


                function getChart(num) {
                    //alert("in chart" + num)
	
                    /*if(num==1){
	
            var count = document.getElementById("count").value;
            var month = document.getElementById("month").value;
            var year = document.getElementById("year").value;

            count = trim(count);

            if(count == "" || isNaN(count)){
                return;
            }
            else if(month == " " || year == " ") {
                return;
            }
            else {
                var url = 'getChart.do?count=' + count + '&month=' + month + '&year=' + year + '&type=chart' + num;
                retrieveURL2(url, num);
            }
	
                }*/
                    //else{
	
                    var month1 = document.getElementById("month1").value;
                    var year1 = document.getElementById("year1").value;
                    var month2 = document.getElementById("month2").value;
                    var year2 = document.getElementById("year2").value;

    

                    if(month1 == " " || year1 == " " || month2 == " " || year2 == " ") {
                        return;
                    }
                    else {
                        var url = 'getChart.do?month1=' + month1 + '&year1=' + year1 + '&month2=' + month2 + '&year2=' + year2 + '&type=chart' + num;
                        retrieveURL2(url, num);
                    }
                    //}
	
	
                }




                function trim(stringToTrim)
                {
                    return stringToTrim.replace(/^\s+|\s+$/g,"");
                }



                var chartId="";

                function retrieveURL2(url, id) {
                    chartId = id;
                    //alert("in url2")
                    if (window.XMLHttpRequest) { // Non-IE browsers
                        req = new XMLHttpRequest();
                        req.onreadystatechange = processStateChange2;

                        try {
                            req.open("GET", url, true);
                        } catch (e) {
                            alert(e);
                        }
                        req.send(null);
                    } else if (window.ActiveXObject) { // IE
                        req = new ActiveXObject("Microsoft.XMLHTTP");
                        if (req) {
                            req.onreadystatechange = processStateChange2;
                            req.open("GET", url, true);
                            req.send();
                        }
                    }
                }

                function processStateChange2() {
                    if (req.readyState == 4) {
                        // Complete
                        if (req.status == 200) { // OK response
                            
                            if(req.responseText != " ") {

                                //alert("in proschg2")

	
                                /*var newWindow = window.open("/Kidmapni/Charts?param=ChartGenerator&type=Chart" + chartId,"","width=740,height=585,left=230,top=70,resizable=yes,scrollbars=yes,menubar=yes,location=no,status=no");*/
		                var title="Chart_" + chartId;
                                var url="/Kidmapni/Charts?param=ChartGenerator&type=Chart" + chartId;
                                var newWindow = window.open(url,title,"width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0");
                            }




                        } else {
                            alert("Problem: " + req.statusText);
                        }
                    }
                }
  
  
                function setNextReports(id) {
                    
                    document.getElementById("reports").innerHTML ="Testing 2" //document.getElementById(id).innerHTML;
                    
  
                }
  
  
                function setPreviousReports(id) {
                    document.getElementById("reports").innerHTML = document.getElementById(id).innerHTML;
  
                }



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
                    if (req.readyState == 4) { // Complete
                        if (req.status == 200) { // OK response
                            //document.getElementById("selected").innerHTML = req.responseText;

                            document.getElementById("selected_2").innerHTML = req.responseText;

                            //document.getElementById("selected_3").innerHTML = req.responseText;
                            //document.getElementById("characters").value = " ";
                            //document.getElementById("ovcSno").value = " ";
                            // document.getElementById("ovcSno").disabled = true;
                        } else {
                            alert("Problem: " + req.statusText);
                        }
                    }
                }



                function retrieveURL4(url) {

                    if (window.XMLHttpRequest) { // Non-IE browsers
                        req = new XMLHttpRequest();
                        req.onreadystatechange = processStateChange4;
                        try {
                            req.open("GET", url, true);
                        } catch (e) {
                            alert(e);
                        }
                        req.send(null);
                    } else if (window.ActiveXObject) { // IE
                        req = new ActiveXObject("Microsoft.XMLHTTP");
                        if (req) {
                            req.onreadystatechange = processStateChange4;
                            req.open("GET", url, true);
                            req.send();
                        }
                    }
                }

                function processStateChange4() {
                    if (req.readyState == 4) { // Complete
                        if (req.status == 200) { // OK response
                            //document.getElementById("selected2").innerHTML = req.responseText;
                            document.getElementById("selected2_2").innerHTML = req.responseText;
                            //document.getElementById("characters").value = " ";
                            //document.getElementById("ovcSno").value = " ";
                            //document.getElementById("ovcSno").disabled = true;
                            //setOptions("cbo","CbosSelect");

                            setOptions("cboReg","CbosSelect");

                            //setOptions("cboSummary","CbosSelect");
                        } else {
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
                    if (req.readyState == 4) { // Complete
                        if (req.status == 200) { // OK response


                            //alert(req.responseText);
                            //document.getElementById("text1").value = req.responseText;


                            var resArry = req.responseText.split(":");

                            if(resArry[1] == "done") {

                                //var newWindow = window.open("matchpageenrollreg.jsp","","width=740,height=585,left=230,top=70,resizable=yes,scrollbars=yes,menubar=yes,location=no,status=no");

                                //var newWindow = window.open("matchpageenrollreg.jsp","","width=740,height=585,left=230,top=70,resizable=yes,scrollbars=yes,menubar=yes,location=no,status=no");

                                var newWindow = window.open("matchpageenrollreg.jsp","Enrolment_Register" ,"width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0");


                            }



                        } else {
                            alert("Problem: " + req.statusText);
                        }
                    }
                }



                function setOptions(id1, id2) {

                    var valueStr, displayStr;
                    for(i=0; i<document.getElementById(id2).options.length; i++)
                    {
                        valueStr = document.getElementById(id2).options[i].value;
                        displayStr = document.getElementById(id2).options[i].text;
                        document.getElementById(id1).options[i]=new Option(displayStr, valueStr);
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




                function hideComponentReset(id)
                {
                    //document.getElementById(id).style.visibility="hidden";

                    if(id == "enrollmentList") {

                        retrieveURL2('getEnrollmentList.do?resetEnrollList=true');



                        document.getElementById("state").value = " ";
                        //document.getElementById("selected").innerHTML = "<select Class=\"smallfieldcellinput\" style=\"width: 130px;\"><option value=\" \"> </option></select>";
                        //document.getElementById("CbosSelect").value = " ";
                        //document.getElementById("LgasSelect").value = " ";
                        document.getElementById("cbo").value = " ";
                        document.getElementById("enrollmentListMonth1").value = " ";
                        document.getElementById("enrollmentListMonth2").value = " ";
                        document.getElementById("enrollmentListYear1").value = " ";
                        document.getElementById("enrollmentListYear2").value = " ";

                        //document.getElementById("enrollmentList").style.visibility="hidden";
                        document.getElementById("component").innerHTML="&nbsp;";
                        window.close();
                    }


                    else if(id == "enrollmentReg") {
     
                        //retrieveURL5('getEnrollmentRegister.do?resetEnrollReg=true');



                        document.getElementById("stateReg").value = " ";
                        //document.getElementById("selected").innerHTML = "<select Class=\"smallfieldcellinput\" style=\"width: 130px;\"><option value=\" \"> </option></select>";
                        //document.getElementById("CbosSelect").value = " ";
                        //document.getElementById("LgasSelect").value = " ";
                        document.getElementById("cboReg").value = " ";
                        document.getElementById("enrollmentRegisterMonth1").value = " ";
                        document.getElementById("enrollmentRegisterMonth2").value = " ";
                        document.getElementById("enrollmentRegisterYear1").value = " ";
                        document.getElementById("enrollmentRegisterYear2").value = " ";

                        //document.getElementById("enrollmentReg").style.visibility="hidden";
                        //document.getElementById("component").innerHTML="&nbsp;";
                        hideComponent();
                        //window.close();
                    }


                    else if(id == "enrollmentSummary") {

                        retrieveURL6('getEnrollmentSummaryReport.do?resetEnrollSummary=true');



                        document.getElementById("stateSummary").value = " ";
                        //document.getElementById("selected").innerHTML = "<select Class=\"smallfieldcellinput\" style=\"width: 130px;\"><option value=\" \"> </option></select>";
                        //document.getElementById("CbosSelect").value = " ";
                        //document.getElementById("LgasSelect").value = " ";
                        document.getElementById("cboSummary").value = " ";
                        document.getElementById("enrollmentSummaryMonth1").value = " ";
                        document.getElementById("enrollmentSummaryMonth2").value = " ";
                        document.getElementById("enrollmentSummaryYear1").value = " ";
                        document.getElementById("enrollmentSummaryYear2").value = " ";

                        //document.getElementById("enrollmentReg").style.visibility="hidden";
                        document.getElementById("component").innerHTML="&nbsp;";
                        window.close();
                    }


                }



        </script>



        <script language="javascript">

                var param=""
                var callerId=""

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

                    //alert(url);

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
                        if(callerId=="cboForReports")
                        {
                            populateSelectBox(values, "cbo")
                        }
                        else if(callerId=="csi_cbo")
                        {
                            populateSelectBox(values, "csi_cbo")
                        }
                        else if(callerId=="lgaForReports")
                        {
                            setSelectBoxValues(values, "lga")
                        }
                        else if(callerId=="csi_lga")
                        {
                            setSelectBoxValues(values, "csi_lga")
                        }
                        else if(callerId=="sumstat_lga")
                        {
                            setSelectBoxValues(values, "sumstat_lga")
                        }
                        else if(callerId=="sumstat_cbo")
                        {
                            setSelectBoxValues(values, "sumstat_cbo")
                        }
                        else if(callerId=="wardForReports")
                        {
                            setSelectBoxValues(values, "ward")
                        }
                        else if(callerId=="schools")
                        {
                            setSelectBoxValues(values, "schools")
                        }
                    }
                }
                function trim(stringToTrim)
                {
                    return stringToTrim.replace(/^\s+|\s+$/g,"");
                }
                function populateSelectBox(value, id)
                {
                    document.getElementById(id).options.length=0;
                    document.getElementById(id).options[0]=new Option("All","All")
                    for(var i=0; i<value.length; i++)
                    {
                        var selectBoxValue=trim(value[i])
                        document.getElementById(id).options[i+1]=new Option(selectBoxValue,value[i])
                    }
                    cleanUp(id)
                }
                function setSelectBoxValues(value, id)
                {
                    document.getElementById(id).options.length=0;
                    document.getElementById(id).options[0]=new Option("All","All")
                    for(var i=0; i<value.length; i++)
                    {
                        var selectBoxValue=trim(value[i])
                        document.getElementById(id).options[i+1]=new Option(selectBoxValue,value[i])
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
                function resetSelectBoxes(id)
                {
                    document.getElementById(id).options.length=0;
                    document.getElementById(id).options[0]=new Option("All","All");
                }
                function showComponent(id,id2)
                {
                    //document.getElementById("component1").innerHTML=document.getElementById(id).innerHTML
                    document.getElementById("wizard").innerHTML=document.getElementById(id).innerHTML;

                    document.getElementById("contentContainer").style.background="#FFF";
                }

                /*
        function hideComponent(id)
        {
            document.getElementById(id).style.visibility="hidden"
        }
                 */

                function getDropDownValue(id)
                {
                    var dropDownValue=document.getElementById(id).value
                    return dropDownValue;
                }
                function openOvcList()
                {
                    var state=getDropDownValue("state")
                    var lga=getDropDownValue("lga")
                    var cbo=getDropDownValue("cbo")
                    var ward=getDropDownValue("ward")
                    var req="EnrollmentReports"+";"+state+":"+lga+":"+cbo+":"+ward
                    getValuesFromDb('addcbo.do',req,'Reports')
                    var w=window.open("Reports/EnrollmentReport.jsp","enrollmentList","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
                }
                function openCsiAnalysis()
                {
                    var state=getDropDownValue("csi_state")
                    var lga=getDropDownValue("csi_lga")
                    var cbo=getDropDownValue("csi_cbo")
                    var req="csiReports"+";"+state+":"+lga+":"+cbo
                    getValuesFromDb('addcbo.do',req,'Reports')
                    var w=window.open("Reports/CsiAnalysis.jsp","csiAnalysis","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
                }
                function openSchoolAttendance()
                {
                    var state=getDropDownValue("schAttend_state")
                    var req="Reports"+";"+state+":"+"schoolAttendanceList"
                    getValuesFromDb('addcbo.do',req,'schoolAttendanceList')
                    var w=window.open("Reports/SchoolAttendanceCount.jsp","SchoolAttendance","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
                }
                function openOvcSummaryStatistics()
                {

                    var state=getDropDownValue("sumstat_state")
                    var lga=getDropDownValue("sumstat_lga")
                    var cbo=getDropDownValue("sumstat_cbo")
                    var month=getDropDownValue("sumstat_month")
                    var year=getDropDownValue("sumstat_year")
                    var req="Reports"+";"+state+":"+lga+":"+cbo+":"+month+":"+year+":"+"summaryStatistics"
                    getValuesFromDb('addcbo.do',req,'summaryStatistics')
                    //var w=window.open("Reports/OvcSummaryStatistics.jsp","summaryStatistics","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
                    var w=window.open("Reports/SummaryStatCountPerMth.jsp","summaryStatistics","width=1300,height=900,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,statusbar=yes,location=yes,left=0,top=0")
                }
                function getSchoolList(state,type)
                {
                    var req="Reports"+";"+state+":"+type
                    getValuesFromDb('addcbo.do',req,type)
                }
                function getLga(state,type)
                {
                    if(state=="All")
                    {
                        if(type=='lgaForReports')
                        {
                            resetSelectBoxes("lga")
                            resetSelectBoxes("cbo")
                            resetSelectBoxes("ward")
                        }
                        else if(type=='csi_lga')
                        {
                            resetSelectBoxes("csi_lga")
                            resetSelectBoxes("csi_cbo")
                        }
                        else if(type=='sumstat_lga')
                        {
                            resetSelectBoxes("sumstat_lga")
                            resetSelectBoxes("sumstat_cbo")
                        }
                        return
                    }
                    var req="Reports"+";"+state+":"+type
                    getValuesFromDb('addcbo.do',req,type)
                    resetSelectBoxes("cbo")
                    resetSelectBoxes("ward")
                }
                function getCbo(lgaValue,type)
                {
                    var state=""
                    var lga=""
                    if(type=='cboForReports')
                    {
                        if(lgaValue=="All")
                        {
                            resetSelectBoxes("cbo")
                            resetSelectBoxes("ward")
                            return
                        }
                        state=getDropDownValue("state")
                        lga=getDropDownValue("lga")
                    }
                    else if(type=='csi_cbo')
                    {
                        if(lgaValue=="All")
                        {
                            resetSelectBoxes("csi_cbo")
                            return
                        }
                        state=getDropDownValue("csi_state")
                        lga=getDropDownValue("csi_lga")
                    }
                    else if(type=='sumstat_cbo')
                    {
                        if(lgaValue=="All")
                        {
                            resetSelectBoxes("sumstat_cbo")
                            return
                        }
                        state=getDropDownValue("sumstat_state")
                        lga=getDropDownValue("sumstat_lga")
                    }
                    var req="Reports"+";"+state+":"+lga+":"+type
                    getValuesFromDb('addcbo.do',req,type)
                    resetSelectBoxes("ward")
                }
                function getWard(cboValue,type)
                {
                    var state=""
                    if(type=='wardForReports')
                    {
                        if(cboValue=="All")
                        {
                            resetSelectBoxes("ward")
                            return
                        }
                        state=getDropDownValue("state")
                    }
                    else if(type=='csi_ward')
                    {
                        if(cboValue=="All")
                        {
                            //resetSelectBoxes("ward_cbo")
                            return
                        }
                        state=getDropDownValue("ward_state")
                    }
                    var req="Reports"+";"+state+":"+cboValue+":"+type
                    getValuesFromDb('addcbo.do',req,type)
                }
                function setNextControls(id)
                {
                    document.getElementById("test").innerHTML="Testing"
                }


        </script>

        <link href="sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
        <link href="kidmap-default.css" rel="stylesheet" type="text/css" />
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
    </head>

    <body onload="MM_preloadImages('images/tabs/care&amp;support_down.jpg','images/tabs/Rapid_assesment_down.jpg','images/tabs/Administration_down.jpg','images/tabs/About_down.jpg')">
        <table width="921" border="0" align="center" cellpadding="0" cellspacing="0">
            <!--DWLayoutTable-->
            <tr>
                <td width="170" height="38">&nbsp;</td>
                <td width="751" rowspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
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
                                                    <td width="126" height="38"><a href="Tabs/CareAndSupport.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('care &amp; support','','images/tabs/care&amp;support_down.jpg',1)"><img src="images/tabs/care&amp;support_up.jpg" alt="care &amp; support" name="care &amp; support" width="126" height="38" border="0" id="careandsupport" /></a></td>
                                                </tr>
                                            </table></td>
                                        <td width="126" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                <!--DWLayoutTable-->
                                                <tr>
                                                    <td width="126" height="38"><a href="Nomis.jsp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Rapid Assesment','','images/tabs/Rapid_assesment_down.jpg',1)"><img src="images/tabs/Rapid_assesment_up.jpg" alt="Rapid Assesment" name="Rapid Assesment" width="126" height="38" border="0" id="Rapid Assesment" /></a></td>
                                                </tr>
                                            </table></td>
                                        <td width="126" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                <!--DWLayoutTable-->
                                                <tr>
                                                    <td width="126" height="38"><a href="administration.html" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Administration','','images/tabs/Administration_down.jpg',1)"><img src="images/tabs/Administration_up.jpg" alt="Administration" name="Administration" width="126" height="38" border="0" id="Administration" /></a></td>
                                                </tr>
                                            </table></td>
                                        <td width="126" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                <!--DWLayoutTable-->
                                                <tr>
                                                    <td width="126" height="38"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('About','','images/tabs/About_down.jpg',1)"><img src="images/tabs/About_up.jpg" alt="About" name="About" width="126" height="38" border="0" id="About" /></a></td>
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
                            <td height="412" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="boaderall">
                                    <!--DWLayoutTable-->
                                    <tr>
                                        
                                        <td width="747" height="542" valign="top" background="images/main_border.jpg" id="contentContainer"><table border="0" cellpadding="0" cellspacing="0" align="center"><tr><td height="191"></td></tr>
                                                <tr><td><div id="wizard"></div></td></tr><tr><td></td></tr></table></td>
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
                                                    <td width="168" height="88" valign="top"><div style="float: left" id="my_menu" class="sdmenu">
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
                                        <td height="234" valign="top">
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                                <!--DWLayoutTable-->
                                                <tr>
                                                    <td width="168" height="88" valign="top">
                                                        <div style="float: left" id="div" class="sdmenu">
                                                            <!--<a href="Reports/EnrolRegisterDatePage.jsp" target="_blank">Enrollment Register </a>-->
                                                            <div id="reports">
                                                       <a href="Reports/CsiParamPage.jsp" target="_blank" >CSI analysis</a><a href="Reports/EnrollmentRecord.jsp" target="_blank" >Enrollment records</a><a href="Reports/SchoolAttendanceParamPage.jsp" target="_blank" >School attendance</a><a href="Reports/SummStatParamPage.jsp" target="_blank" >Summary statistics</a><a href="Reports/DqaParamPage.jsp" target="_blank" >Data quality assessment</a><a href="Reports/OvcMthlySummaryFormParamPage.jsp" target="_blank" >OVC Monthly summary form</a><a href="ChartPage.jsp">Charts </a>
                                                       <!--<a href="#" onclick="setDivContent('enrollmentReg');">Enrolment Register </a><a href="ovcdashboard.jsp" target="_blank">OVC Dashboard </a><a href="#" onclick="setDivContent('cumulativeDiv1');">Cumulative number of OVC enrolled in the LGA-wide OVC program, CO level</a><a href="#" onclick="setDivContent('enrlperlgaDiv');">Number of OVC enrolled in the LGA-wide OVC program per LGA</a><a href="#" onclick="setDivContent('servicesatenrlDiv');">Proportion of OVC that received 3 services at enrolment per LGA</a><a href="#" onclick="setDivContent('hivstatusDiv');">Proportion of OVC enrolled per LGA disaggregated by HIV status</a><a href="#" onclick="setDivContent('withoutbcDiv');">Proportion of OVC without birth certificate at enrolment per LGA</a><a href="#" onclick="setDivContent('ovcinschoolDiv');">Proportion of OVC in school at enrolment per LGA</a>-->
                                                            </div>
                                                                <!--<a href="#" onclick="setDivContent('enrollmentReg');">Enrolment Register </a>
                                                                <a href="ovcdashboard.jsp" target="_blank">OVC Dashboard </a>
                                                                <a href="Reports/CsiParamPage.jsp" target="_blank" >CSI analysis</a>
                                                                <a href="Reports/DqaParamPage.jsp" target="_blank">Data quality assessment </a>
                                                                <a href="#" onclick="setNextReports('reports2');">Next &gt;&gt;</a>-->
                                                            
                                                            <!--<div id="reports1" style="visibility:hidden"><a href="#" onclick="setDivContent('enrollmentReg');">Enrolment Register </a><a href="ovcdashboard.jsp" target="_blank">OVC Dashboard </a><a href="Reports/CsiParamPage.jsp" target="_blank" >CSI analysis</a><a href="Reports/DqaParamPage.jsp" target="_blank" >Data quality assessment</a><a href="#" onclick="setNextReports('reports2');">Next &gt;&gt;</a></div>-->
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
        </table>




        




        <div id="cumulativeDiv1" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> Cumulative number of OVC </td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>


                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>
                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(1)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>





        <div id="enrlperlgaDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> OVC enrolled per LGA </td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>


                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>
                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(2)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>



        <div id="servicesatenrlDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> OVC receiving 3 services </td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>
                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>

                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(3)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>




        <div id="hivstatusDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> OVC by HIV status </td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>
                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>

                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(4)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>



        <div id="withoutbcDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> OVC without birth certificate </td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>
                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>

                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(5)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>




        <div id="ovcinschoolDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> OVC in school </td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>
                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>

                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(6)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>



        <div id="emotionalhealthDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> Psychosocial domain </td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>
                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>

                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(7)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>



        <div id="foodsecurityDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> Food &amp; Nutrition domain </td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>
                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>

                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(8)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>



        <div id="healthcareDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> Health domain </td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>
                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>

                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(9)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>



        <div id="schoolworkDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> Education &amp; Work domain </td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>
                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>

                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(10)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>



        <div id="abuseexplDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> Protection domain</td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>
                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>

                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(11)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>



        <div id="shelterDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">

                        <tr><td align="center" class="title" colspan="4"> Shelter &amp; Care domain </td></tr>

                        <tr><td class="orglabel" align="left">Period </td><td align="left"><select id="month1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year1" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>



                            </td><td>to</td><td>

                                <select id="month2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="01">Jan</option>
                                    <option value="02">Feb</option>
                                    <option value="03">Mar</option>
                                    <option value="04">Apr</option>
                                    <option value="05">May</option>
                                    <option value="06">Jun</option>
                                    <option value="07">Jul</option>
                                    <option value="08">Aug</option>
                                    <option value="09">Sep</option>
                                    <option value="10">Oct</option>
                                    <option value="11">Nov</option>
                                    <option value="12">Dec</option>
                                </select>

                                <select id="year2" class="smallfieldcellinput" style="width:60px;">
                                    <option value=" "> </option>
                                    <option value="2000">2000 </option>
                                    <option value="2001">2001 </option>
                                    <option value="2002">2002 </option>
                                    <option value="2003">2003 </option>
                                    <option value="2004">2004 </option>
                                    <option value="2005">2005 </option>
                                    <option value="2006">2006 </option>
                                    <option value="2007">2007 </option>
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014</option>
                                    <option value="2015">2015</option>
                                    <option value="2016">2016</option>
                                    <option value="2017">2017</option>
                                    <option value="2018">2018</option>
                                    <option value="2019">2019</option>
                                    <option value="2010">2020</option>
                                </select>

                            </td></tr>
                        <tr><td class="orglabel" align="left">&nbsp;</td><td> </td><td> </td><td> </td></tr>

                        <tr><td colspan="4" align="center"><input type="button" id="chart1OkBtn" value="OK" onclick="getChart(12)" style="width: 70px;" />&nbsp; <input type="button" id="chart1CancelBtn" value="Cancel" onclick="hideComponent()" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>





        <div id="mthlySummDate" style="height: 120px; width: 300px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table>
                        <tr><td>&nbsp; </td><td> </td></tr>
                        <tr><td>Month: </td><td><select name="month" style="width: 90px;" ><option value="January">January </option><option value="February">February </option><option value="March">March </option><option value="April">April</option><option value="May">May</option><option value="June">June</option><option value="July">July</option><option value="August">August</option><option value="September">September</option><option value="October">October</option><option value="November">November</option><option value="December">December</option></select> </td><td>Year </td><td><select name="years" style="width: 90px;" ><option value="2009">2009 </option><option value="2010">2010 </option><option value="2011">2011 </option><option value="2012">2012 </option><option value="2013">2013 </option><option value="2014">2014</option><option value="2015">2015</option><option value="2016">2016</option><option value="2017">2017</option><option value="2018">2018</option><option value="2019">2019</option><option value="2010">2020</option></select></td></tr>
                        <tr><td>&nbsp; </td><td> </td></tr>
                        <tr><td colspan="4" align="center"> <input type="button" name="dateBtn" value="Submit" onclick="openMthlySummForm()" style="width: 70px;" />&nbsp; <input type="button" name="cancelBtn" value="Cancel" onclick="hideComponent('mthlySummDate')" style="width: 70px;" /></td></tr>
                    </table>

                </center>
            </span>
        </div>
        <div id="enrlRegDateDiv" style="height: 120px; width: 300px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table>
                        <tr><td>&nbsp; </td><td> </td></tr>
                        <tr><td>Month: </td><td><select name="enrlmonth" style="width: 90px;" ><option value="January">January </option><option value="February">February </option><option value="March">March </option><option value="April">April</option><option value="May">May</option><option value="June">June</option><option value="July">July</option><option value="August">August</option><option value="September">September</option><option value="October">October</option><option value="November">November</option><option value="December">December</option></select> </td><td>Year </td><td><select name="enrlyears" style="width: 90px;" ><option value="2009">2009 </option><option value="2010">2010 </option><option value="2011">2011 </option><option value="2012">2012 </option><option value="2013">2013 </option><option value="2014">2014</option><option value="2015">2015</option><option value="2016">2016</option><option value="2017">2017</option><option value="2018">2018</option><option value="2019">2019</option><option value="2010">2020</option></select></td></tr>
                        <tr><td>&nbsp; </td><td> </td></tr>
                        <tr><td colspan="2" align="center"><input type="button" name="enrldateBtn" value="Submit" onclick="openEnrollmentReg('enrlmonth','enrlyears')" style="width: 70px;" />&nbsp; <input type="button" name="enrlcancelBtn" value="Cancel" onclick="hideComponent('enrlRegDateDiv')" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>

        <div id="enrlRecQueryDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">
                        <tr><td align="center" class="title" colspan="4"> Enrolment records </td></tr>
                        <tr><td class="orglabel" align="left">State </td><td><select name="state" id="state" style="width: 290px;" onchange="getLga(this.value,'lgaForReports')" class="smallfieldcellinput"><option value="All">All</option>
                                    <logic:iterate id="stateObj" name="states">
                                        <option value="${stateObj}">${stateObj}</option>
                                    </logic:iterate>
                                </select> </td><td> </td><td> </td></tr>
                        <tr><td class="orglabel" align="left">LGA </td><td><select name="lga" id="lga" style="width: 290px;" onchange="getCbo(this.value,'cboForReports')" class="smallfieldcellinput">
                                    <option value="All">All</option>
                                </select> </td><td> </td><td></td></tr>
                        <tr><td class="orglabel" align="left">CBO </td><td><select name="cbo" id="cbo" style="width: 290px;" onchange="getWard(this.value,'wardForReports')" class="smallfieldcellinput"><option value="All"> </option></select> </td><td> </td><td> </td></tr>
                        <tr><td class="orglabel" align="left">Ward </td><td><select name="ward" id="ward" style="width: 290px;" class="smallfieldcellinput"><option value="All"> </option></select> </td><td> </td><td> </td></tr>
                        <tr><td>&nbsp; </td><td> </td></tr>
                        <tr><td colspan="4" align="center"><input type="button" name="enrldateQueryBtn" value="Submit" onclick="openOvcList()" style="width: 70px;" />&nbsp; <input type="button" name="enrlQuerycancelBtn" value="Cancel" onclick="hideComponent('enrlRecQueryDiv')" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>
        <div id="csiAnalysisDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">
                        <tr><td colspan="4" class="title"> CSI analysis  </td></tr>
                        <tr><td class="orglabel">State </td><td><select name="csi_state" id="csi_state" style="width: 290px;" onchange="getLga(this.value,'csi_lga')" class="smallfieldcellinput"><option value="All">All</option>
                                    <logic:iterate id="stateObj" name="states">
                                        <option value="${stateObj}">${stateObj}</option>
                                    </logic:iterate>
                                </select> </td><td> </td><td> </td></tr>
                        <tr><td class="orglabel">LGA </td><td><select name="csi_lga" id="csi_lga" style="width: 290px;" onchange="getCbo(this.value,'csi_cbo')" class="smallfieldcellinput">
                                    <option value="All:All">All</option>
                                </select> </td><td> </td><td></td></tr>
                        <tr><td class="orglabel">CBO </td><td><select name="csi_cbo" id="csi_cbo" style="width: 290px;" class="smallfieldcellinput"><option value="All"> </option></select> </td><td> </td><td> </td></tr>
                        <tr><td>&nbsp; </td><td> </td></tr>
                        <tr><td colspan="2" align="center"><input type="button" name="csi_dateBtn" value="Submit" onclick="openCsiAnalysis()" style="width: 70px;" />&nbsp; <input type="button" name="csi_cancelBtn" value="Cancel" onclick="hideComponent('enrlRecQueryDiv')" style="width: 70px;" /></td></tr>
                    </table>
                </center>
            </span>
        </div>

        <div id="schoolAttendanceDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span style="height: 160px; width: 400px; background-color: #0075B5;">
                <center>
                    <table style="background-color: #0075B5;">
                        <tr><td colspan="4" class="title">School attendance  </td></tr>
                        <tr><td>&nbsp; </td><td> </td></tr>
                        <tr><td class="orglabel">State </td><td><select name="schAttend_state" id="schAttend_state" style="width: 290px;" class="smallfieldcellinput">
                                    <logic:iterate id="stateObj" name="states">
                                        <option value="${stateObj}">${stateObj}</option>
                                    </logic:iterate>
                                </select> </td><td> </td><td> </td></tr>
                        <tr><td>&nbsp; </td><td> </td></tr>
                        <tr><td colspan="2" align="center"><input type="button" name="sch_Btn" value="Submit" onclick="openSchoolAttendance()" style="width: 70px;" />&nbsp; <input type="button" name="csi_cancelBtn" value="Cancel" onclick="hideComponent('enrlRecQueryDiv')" style="width: 70px;" /> </td></tr>
                    </table>
                </center>
            </span>
        </div>

        <div id="sumstatDiv" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
            <span>
                <center>
                    <table style="background-color: #0075B5;">
                        <tr><td colspan="4" class="title">Summary statistics</td></tr>
                        <tr><td class="orglabel" align="left">State </td><td colspan="3"><select name="sumstat_state" id="sumstat_state" style="width: 290px;" onchange="getLga(this.value,'sumstat_lga')" class="smallfieldcellinput"><option value="All">All</option>
                                    <logic:iterate id="stateObj" name="states">
                                        <option value="${stateObj}">${stateObj}</option>
                                    </logic:iterate>
                                </select> </td></tr>
                        <tr><td class="orglabel" align="left">LGA </td><td colspan="3"><select name="sumstat_lga" id="sumstat_lga" style="width: 290px;" onchange="getCbo(this.value,'sumstat_cbo')" class="smallfieldcellinput">
                                    <option value="All:All">All</option>
                                </select> </td></tr>
                        <tr><td class="orglabel" align="left">CBO </td><td colspan="3"><select name="sumstat_cbo" id="sumstat_cbo" style="width: 290px;" class="smallfieldcellinput"><option value="All">All</option></select> </td></tr>
                        <tr><td class="orglabel" align="left">Month </td><td>
                                <select name="sumstat_month" id="sumstat_month" style="width: 125px;" class="smallfieldcellinput">
                                    <option value="01">January </option>
                                    <option value="02">February </option>
                                    <option value="03">March </option>
                                    <option value="04">April </option>
                                    <option value="05">May</option>
                                    <option value="06">June</option>
                                    <option value="07">July </option>
                                    <option value="08">August </option>
                                    <option value="09">September </option>
                                    <option value="10">October </option>
                                    <option value="11">November </option>
                                    <option value="12">December</option>
                                </select> </td><td class="orglabel">Year</td><td><select name="sumstat_year" id="sumstat_year" style="width: 130px;" class="smallfieldcellinput">
                                    <option value="2008">2008 </option>
                                    <option value="2009">2009 </option>
                                    <option value="2010" selected="selected">2010 </option>
                                    <option value="2011">2011 </option>
                                    <option value="2012">2012 </option>
                                    <option value="2013">2013 </option>
                                    <option value="2014">2014 </option>
                                    <option value="2015">2015 </option>
                                    <option value="2016">2016 </option>
                                    <option value="2017">2017 </option>
                                    <option value="2018">2018 </option>
                                    <option value="2019">2019 </option>
                                    <option value="2020">2020 </option>
                                </select> </td></tr>
                        <!--<tr><td colspan="3">&nbsp; </td></tr>-->
                        <tr><td colspan="3" align="center"><input type="button" name="sumstat_dateBtn" value="Submit" onclick="openOvcSummaryStatistics()" style="width: 70px; margin-left: 120px;" /></td></tr>
                    </table>
                </center>
            </span>
        </div>






        <html:form action="/Reports" method="post">
            <div id="enrollmentReg" style="height: 160px; width: 400px; background-color: #0075B5; position: absolute; left: 550px; top: 300px; visibility: hidden">
                <div align="left">
                    <table style="background-color: #0075B5;">

                        <tr align="center">
                            <td colspan="6" class="title"> Enrolment Register </td>

                        </tr>

                        <tr align="left">
                            <td class="orglabel">State </td>
                            <td colspan="5">
                                <html:select styleId="stateReg" property="state" styleClass="smallfieldcellinput" style="width:273px;"  onchange="retrieveURL3('getSelectedState.do?stateCode=' + this.value);">

                                    <html:optionsCollection name="reports"
                                                            property="stateCategories"
                                                            value="stateCode"
                                                            label="name"/>
                                </html:select>
                            </td>
                        </tr>

                        <tr align="left">
                            <td class="orglabel">LGA </td>
                            <td colspan="5">
                                <span id="selected_2"><select Class="smallfieldcellinput" style="width:273px;"><option value=" "> </option></select></span>
                            </td>
                        </tr>

                        <tr align="left">
                            <td class="orglabel">CBO </td>
                            <td colspan="5">

                                <select Class="smallfieldcellinput" style="width:273px;" id="cboReg"><option value=" "> </option></select>

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
            
        </html:form>





        






<!--<a href="#">Clients Quary Wizard </a><a href="#">OVC Data Analysis Book </a> <a href="#" onclick="setDivContent('enrollmentReg');">Enrolment Register </a>-->

        
        <div id="reports1" style="visibility:hidden; position: absolute;"><a href="ovcdashboard.jsp" target="_blank">OVC Dashboard </a><a href="Reports/CsiParamPage.jsp" target="_blank" >CSI analysis</a><a href="Reports/DqaParamPage.jsp" target="_blank" >Data quality assessment</a><a href="#" onclick="setNextReports('reports2');">Next &gt;&gt;</a></div>
        <div id="reports2" style="visibility:hidden; position: absolute;"><a href="#" onclick="setDivContent('cumulativeDiv1');">Cumulative number of OVC enrolled in the LGA-wide OVC program, CO level</a><a href="#" onclick="setDivContent('enrlperlgaDiv');">Number of OVC enrolled in the LGA-wide OVC program per LGA</a><a href="#" onclick="setDivContent('servicesatenrlDiv');">Proportion of OVC that received 3 services at enrolment per LGA</a><a href="#" onclick="setPreviousReports('reports1');">&lt;&lt; Previous</a><a href="#" onclick="setNextReports('reports3');">Next &gt;&gt;</a></div>

        <div id="reports3" style="visibility:hidden"><a href="#" onclick="setDivContent('hivstatusDiv');">Proportion of OVC enrolled per LGA disaggregated by HIV status</a><a href="#" onclick="setDivContent('withoutbcDiv');">Proportion of OVC without birth certificate at enrolment per LGA</a><a href="#" onclick="setDivContent('ovcinschoolDiv');">Proportion of OVC in school at enrolment per LGA</a><a href="#" onclick="setPreviousReports('reports2');">&lt;&lt; Previous</a><a href="#" onclick="setNextReports('reports4');">Next &gt;&gt;</a></div>

        <div id="reports4" style="visibility:hidden"><a href="#" onclick="setDivContent('emotionalhealthDiv');">CSI analysis for Psychosocial domain (Emotional health)</a><a href="#" onclick="setDivContent('foodsecurityDiv');">CSI analysis for Food and Nutrition domain (Food security)</a><a href="#" onclick="setDivContent('healthcareDiv');">CSI analysis for Health domain (Health care services)</a><a href="#" onclick="setPreviousReports('reports3');">&lt;&lt; Previous</a><a href="#" onclick="setNextReports('reports5');">Next &gt;&gt;</a></div>

        <div id="reports5" style="visibility:hidden"><a href="#" onclick="setDivContent('schoolworkDiv');">CSI analysis for Education and work domain (School work)</a><a href="#" onclick="setDivContent('abuseexplDiv');">CSI analysis for Protection domain (Abuse and exploitation)</a><a href="#" onclick="setDivContent('shelterDiv');">CSI analysis for Shelter and care domain (Shelter)</a><a href="#" onclick="setPreviousReports('reports4');">&lt;&lt; Previous</a><a href="#" onclick="setNextReports('reports6');">Next &gt;&gt;</a></div>

        <div id="reports6" style="visibility:hidden"><a href="Reports/CsiParamPage.jsp" target="_blank" >CSI analysis</a><a href="Reports/EnrollmentRecord.jsp" target="_blank" >Enrollment records</a><a href="Reports/SchoolAttendanceParamPage.jsp" target="_blank" >School attendance</a><a href="Reports/SummStatParamPage.jsp" target="_blank" >Summary statistics</a><a href="#" onclick="setPreviousReports('reports5');">&lt;&lt; Previous</a></div>




        <p class="copyright"><span style="m">Copyright &copy; KidMAP</span> </p>




        <%
                    HttpSession httpSession = request.getSession();
                    if (httpSession.getAttribute("visitStatus") != null) {
                        String status = (String) httpSession.getAttribute("visitStatus");
                        if (status.equals("first login")) {
        %>

                

        <%                }
                    }
        %>



    </body>
</html>

