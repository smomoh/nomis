<%--
    Document   : DatabaseMgt
    Created on : Feb 6, 2011, 11:09:55 PM
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
        <title>Database Management</title>
        <link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
<script language="javascript">
/*var enrLoopCount=0
var csiLoopCount=0
var serviceLoopCount=0
var orgRecordsLoopCount=0
var wardRecordsLoopCount=0
var orgAssessmentLoopCount=0
var followupLoopCount=0
var referralLoopCount=0
var schoolLoopCount=0
var hhviLoopCount=0
var hhsLoopCount=0
var hvaLoopCount=0
var caregiverLoopCount=0*/
//var type=""
var param=""
var callerId=""
var t=0
var k=0
var j=0;
var secs
var msgSecs=0
var counter=0
var timerID = null
var timerRunning = false
var delay = 4000
var dataSize=[]
var loopSize=0
var totalCount=0;
var msg=" "
var dbIndicator=" "
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
            var result=xmlhttp.responseText;
            if(result==" " || result=="" || result==";")
            return false;
            var values=result.split(";")
            if(callerId=="sizeOfData")
            {
                dataSize=values
            }
            else if(callerId=="restoreDb")
            {
                  dbIndicator="endOfImport"
                  msgSecs=0
                  msg=result
            }
	}
}
function InitializeTimer()
{
    secs = secCount
    StopTheClock()
    StartTheTimer()
}
function StopTheClock()
{
    if(timerRunning)
        clearTimeout(timerID)
    timerRunning = false
}
function StartTheTimer()
{
    var count=1

    if(loopSize==0 && t==0)
    {
        var data=parseInt(dataSize[0])
        if(data>0)
        {
          count=getLoopCount(dataSize[0],2000)
        }
        else
        {
          count=1
        }
        totalCount=totalCount+count
     }

    if(k==totalCount)
    {
        loopSize++
        var data=parseInt(dataSize[loopSize])
        if(data>0)
        count=getLoopCount(dataSize[loopSize],2000)
        else count=1
        totalCount=totalCount+count
        j++
        t=0
    }

        if (secs==0)
        {
            StopTheClock()
            if(dbIndicator=="endOfImport")
            msg="Database import completed"
            else
            {
                    msg="Click the Finish button to finish export"
                    showMsg("bcMsg",msg)
                    document.getElementById("backup").disabled="disabled"
                    document.getElementById("finisBbtn").disabled=false
                    resetGlobalVariables()

            }
        }
        else
        {
            exportDb(t,j)
            self.status = secs
            secs = secs - 1
            if(secs%2==0)
            msg=" "
            else
            msg="Database export in progress, please wait..."
            showMsg("bcMsg",msg)
            timerRunning = true
            timerID = self.setTimeout("StartTheTimer()", delay)
            k++
            t++
        }
        /*if(k==enrLoopCount)
    {
       j++
       t=0
    }
    else if(k==(csiLoopCount+enrLoopCount))
    {
       j++
       t=0
    }
    else if(k==(csiLoopCount+enrLoopCount+serviceLoopCount))
    {
       j++
       t=0
    }
    else if(k==(csiLoopCount+enrLoopCount+serviceLoopCount+wardRecordsLoopCount))
    {
       j++
       t=0
    }
    else if(k==(csiLoopCount+enrLoopCount+serviceLoopCount+wardRecordsLoopCount+orgAssessmentLoopCount))
    {
       j++
       t=0
    }
    else if(k==(csiLoopCount+enrLoopCount+serviceLoopCount+wardRecordsLoopCount+orgAssessmentLoopCount+followupLoopCount))
    {
       j++
       t=0
    }
    else if(k==(csiLoopCount+enrLoopCount+serviceLoopCount+wardRecordsLoopCount+orgAssessmentLoopCount+followupLoopCount+referralLoopCount))
    {
       j++
       t=0
    }
    else if(k==(csiLoopCount+enrLoopCount+serviceLoopCount+wardRecordsLoopCount+orgAssessmentLoopCount+followupLoopCount+referralLoopCount+schoolLoopCount))
    {
       j++
       t=0
    }
    else if(k==(csiLoopCount+enrLoopCount+serviceLoopCount+wardRecordsLoopCount+orgAssessmentLoopCount+followupLoopCount+referralLoopCount+schoolLoopCount+hhviLoopCount))
    {
       j++
       t=0
    }
    else if(k==(csiLoopCount+enrLoopCount+serviceLoopCount+wardRecordsLoopCount+orgAssessmentLoopCount+followupLoopCount+referralLoopCount+schoolLoopCount+hhviLoopCount+hhsLoopCount))
    {
       j++
       t=0
    }
    else if(k==(csiLoopCount+enrLoopCount+serviceLoopCount+wardRecordsLoopCount+orgAssessmentLoopCount+followupLoopCount+referralLoopCount+schoolLoopCount+hhviLoopCount+hhsLoopCount+hvaLoopCount))
    {
       j++
       t=0
    }
    else if(k==(csiLoopCount+enrLoopCount+serviceLoopCount+wardRecordsLoopCount+orgAssessmentLoopCount+followupLoopCount+referralLoopCount+schoolLoopCount+hhviLoopCount+hhsLoopCount+hvaLoopCount+caregiverLoopCount))
    {
       j++
       t=0
    }*/

}
function showMsg(id,msg)
{
    document.getElementById(id).innerHTML=msg
}
function resetGlobalVariables()
{
    j=0
    k=0
    t=0
    secs=0
    timerID = null
    timerRunning = false
    delay = 4000
    /*enrLoopCount=0
    csiLoopCount=0
    serviceLoopCount=0
    orgRecordsLoopCount=0
    wardRecordsLoopCount=0
    orgAssessmentLoopCount=0
    followupLoopCount=0
    referralLoopCount=0
    schoolLoopCount=0
    hhviLoopCount=0
    hhsLoopCount=0
    hvaLoopCount=0
    caregiverLoopCount=0
    type=""*/
}
function getDataSize()
{
    dataSize=["${datasize[0]}","${datasize[1]}","${datasize[2]}","${datasize[3]}","${datasize[4]}","${datasize[5]}","${datasize[6]}","${datasize[7]}","${datasize[8]}","${datasize[9]}","${datasize[10]}","${datasize[11]}","${datasize[12]}","${datasize[13]}"]

}
function exportDatabase()
{
   /*enrLoopCount=getLoopCount(dataSize[0],2000)//Math.ceil((parseInt(dataSize[0])/2000))
   csiLoopCount=getLoopCount(dataSize[1],2000)//Math.ceil((parseInt(dataSize[1])/5000))
   serviceLoopCount=getLoopCount(dataSize[2],2000)//Math.ceil((parseInt(dataSize[2])/5000))
   orgRecordsLoopCount=getLoopCount(dataSize[3],2000)//Math.ceil((parseInt(dataSize[3])/5000))
   wardRecordsLoopCount=getLoopCount(dataSize[4],2000)//Math.ceil((parseInt(dataSize[4])/5000))
   orgAssessmentLoopCount=getLoopCount(dataSize[5],2000)//Math.ceil((parseInt(dataSize[5])/5000))
   followupLoopCount=getLoopCount(dataSize[6],2000)//Math.ceil((parseInt(dataSize[6])/2000))
   referralLoopCount=getLoopCount(dataSize[7],2000)//referralLoopCount=Math.ceil((parseInt(dataSize[7])/2000))
   schoolLoopCount=getLoopCount(dataSize[8],2000)//Math.ceil((parseInt(dataSize[8])/5000))
   hhviLoopCount=getLoopCount(dataSize[9],2000)
   hhsLoopCount=getLoopCount(dataSize[10],2000)
   hvaLoopCount=getLoopCount(dataSize[11],2000)
   caregiverLoopCount=getLoopCount(dataSize[12],2000)*/
   secCount=0
   var noOfLoops=0
   for(var g=0; g<dataSize.length; g++)
    {
        noOfLoops=getLoopCount(dataSize[g],2000)
        secCount=secCount+noOfLoops
    }
   //secCount=enrLoopCount+csiLoopCount+serviceLoopCount+orgRecordsLoopCount+wardRecordsLoopCount+orgAssessmentLoopCount+followupLoopCount+referralLoopCount+schoolLoopCount+hhviLoopCount+hhsLoopCount+hvaLoopCount+caregiverLoopCount
   InitializeTimer()
}
function getLoopCount(numerator,denominator)
{
    var result=1
    if(numerator > 0)
    result=Math.ceil((parseInt(numerator)/denominator))
  return result
}
function exportDb(i,j)
{
        var req=i+":"+j+":"+0
        getValuesFromDb('dataexport.do',req,'databaseExport')

}
function setActionName(val)
{
     document.getElementById("dbActionName").value=val
}
function finishExport()
{
    getValuesFromDb('dataexport.do',"zip",'databaseExport2')
     msg="Database exported to C:\\Nomis\\Transfer\\Zips"
     showMsg("bcMsg",msg)
     alert(msg)
}
</script>

    </head>
    <body style="background-color: inherit" onload="getDataSize()">
        <br/><br/><br/><br/>
       <center>
<html:form action="/databasemgt">
        <div id="dbBackupDiv" class="paramPage" style="width: 470px;">
            <table><tr><td height="20"> </td> </tr></table>

            <table class="paramPage" width="460" >
                <tr><td colspan="4" class="title" style=" color: white; font-size: 20px">Number of enrollment records ready for export: ${datasize[0]}</td></tr>
            <tr><td class="orglabel" height="20" colspan="4" style=" color: white; font-size: 20px">&nbsp;Number of service records ready for export: ${datasize[2]}</td></tr>
            <tr><td>&nbsp;</td><td> </td></tr>
            <tr><td colspan="4" height="30"> &nbsp;<label id="bcMsg" style="font-size: 20px; color: white;"> </label> </td></tr>

            <tr><td>&nbsp;</td><td> </td></tr>

                     <html:hidden property="dbActionName" styleId="dbActionName"/>
                     <tr><td colspan="2" align="center">
                             <input type="button" id="backup" name="backup" value="Next >>" onclick="exportDatabase()" style="width: 120px; height: 25px" />
                             <input type="button" name="finisBbtn" id="finisBbtn" value="Finish" onclick="finishExport()" style="width: 120px; height: 25px" disabled="true"/>
                             <%--<html:submit property="finisBbtn" styleId="finisBbtn" value="Finish" onclick="setActionName('finishAndZip')" style="width: 120px; height: 25px" disabled="true"/>--%>
                     </td></tr>

        </table>

    </div>
    </html:form>
   </center>
    </body>
</html>
