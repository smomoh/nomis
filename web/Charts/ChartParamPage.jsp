<%-- 
    Document   : ChartParamPage
    Created on : Aug 8, 2017, 8:35:19 PM
    Author     : smomoh
--%>

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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Chart setup </title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-image: url(images/bg.jpg);
	background-repeat: repeat-x;
}
-->
</style>
<script type="text/javascript">
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
<link href="images/untitled.css" rel="stylesheet" type="text/css" />
<link href="images/sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
a {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
}
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: underline;
}
a:active {
	text-decoration: none;
}
-->
</style>
<!--<link href="sdmenu/sdmenu.css" rel="stylesheet" type="text/css" />-->
<link type="text/css" href="css/ui-darkness/jquery-ui-1.8.custom.css" rel="Stylesheet" />
        <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-1.8.custom.min.js"></script>

<script language="javascript">
			$(function() {
                $("#dateOfAssessment").datepicker();
            });
function selectChkBoxes(chkname)
{
   var elements=document.getElementsByName(chkname)
    for(var i=0; i<elements.length; i++)
    {
        elements[i].checked=true
    }
}
function unselectChkBoxes(chkname)
{
   var elements=document.getElementsByName(chkname)
    for(var i=0; i<elements.length; i++)
    {
        elements[i].checked=false
    }
}
function setActionName(val)
{
    document.getElementById("actionName").value=val
}
</script>
<link href="kidmap-default.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />
</head>

<body onload="MM_preloadImages('images/About_down.jpg','images/Admin_down.jpg','images/Rapid_down.jpg','images/care_down.jpg','images/OVC_down.jpg'); checkCurrentAge()">
    <br/><br/>
<table width="949" border="0" align="center" cellpadding="0" cellspacing="0" class="boarder">
  <!--DWLayoutTable-->
  <tr>
    <td height="117" colspan="2" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->
      <tr>
        <td width="7" height="2"></td>
          <td width="271"></td>
          <td width="137"></td>
          <td width="95"></td>
          <td width="8"></td>
          <td width="95"></td>
          <td width="8"></td>
          <td width="95"></td>
          <td width="8"></td>
          <td width="95"></td>
          <td width="8"></td>
          <td width="95"></td>
          <td width="23"></td>
        </tr>


<jsp:include page="../includes/Pagetabs.jsp" />

      
      <tr>
        <td height="30"></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>&nbsp;</td>
        <td></td>
        <td></td>
        <td></td>
        </tr>

      <tr>
        <td height="17"></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td></td>
          <td><jsp:include page="../Logout.jsp" /></td>
          <td></td>
        </tr>
      <tr>
        <td height="3" colspan="13" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#038233">
          <!--DWLayoutTable-->
          <tr>
            <td width="945" height="2"></td>
            </tr>
          <!--DWLayoutTable-->
          <tr>
            <td height="1"></td>
              </tr>
        </table></td>
        </tr>

    </table></td>
  </tr>
  <tr>
    <td width="931" height="351" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <!--DWLayoutTable-->
      <tr>
        <td> </td>
        <td width="231" rowspan="2" valign="top"  bgcolor="#038233">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#038233">
          <!--DWLayoutTable-->
          <tr>
            <td width="231" height="28" valign="top">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="231" height="28"><img src="images/dataentry.jpg" width="231" height="28" /></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td height="85" valign="top"><div style="float: left" id="my_menu" class="sdmenu">
                    <div><jsp:include page="../Navigation/ChartLink.jsp"/></div>
                
            </div></td>
          </tr>
          <tr>
            <td height="30" valign="top">
                <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <!--DWLayoutTable-->
              <tr>
                <td width="180" height="30"><img src="images/reports.jpg" width="231" height="30" /></td>
                    </tr>
            </table></td>
          </tr>
          <tr>
            <td height="157" valign="top"><div style="float: left" id="my_menu2" class="sdmenu" >
              <div>
                  
                    <a href="#" > </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
                    <a href="#"> </a>
              </div>
            </div></td>
          </tr>


      </table></td>
    <td width="13">&nbsp;</td>
      <td width="639" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
	 <td width="30%" height="39" class="homepagestyle">Charts</td>
	 <td width="70%"><html:errors/></td>
    </tr>
    <tr>
        <td class="homepagestyle" colspan="2" align="center"><logic:present name="msg"><label style="color:red; font-size: 15px;">${msg}</label></logic:present> </td>
	 
    </tr>

  
      <tr><td colspan="2">
         <html:form action="/newchartaction" method="post">
                <html:hidden property="actionName" styleId="actionName"/>

        <span>
        <center>
            
        <table >
            

            <tr><td colspan="4" class="label" align="center" style="color: white; font-weight: bolder">Charts</td></tr>

            <tr><td class="label" align="left">Series </td>
                <td align="left">
                    <html:select property="series" styleId="series" style="width: 290px;" onchange="setActionName('categoryList');forms[0].submit()">
                       <logic:iterate name="seriesList" id="series">
                           <html:option value="${series}">${series}</html:option>
                        </logic:iterate>
                    </html:select>
                </td>
                <td class="label" align="left">Category </td>
                <td align="left">
                    <html:select property="category" styleId="category" style="width: 290px;" onchange="setActionName('filterList');forms[0].submit()">

                        <logic:iterate name="chartCategoryList" id="chartCategory">
                           <html:option value="${chartCategory}">${chartCategory}</html:option>
                        </logic:iterate>
                    </html:select>
                </td>
            </tr>
            <tr>
                <td class="label" align="left">Filter </td>
                <td align="left">
                    <html:select property="filter" styleId="filter" style="width: 290px;">
                            <logic:iterate name="filterList" id="filter">
                               <html:option value="${filter}">${filter}</html:option>
                            </logic:iterate>
                        </html:select>
                </td>
                <td class="label" align="left">Charttype</td>
                <td align="left">
                    <html:select property="chartType" styleId="chartType" style="width: 290px;" >
                        <html:option value="barchart">Bar chart</html:option>
                        <html:option value="piechart">Pie chart</html:option>
                        <html:option value="linegraph">Line graph</html:option>
                    </html:select>
                </td>
                
            </tr>
            <tr><td class="label" align="left">State </td>
                <td align="left">
                    <html:select property="state" styleId="state" style="width: 290px;" onchange="setActionName('lga');forms[0].submit()">
                       <html:option value="All">All</html:option>
                       <logic:present name="stateListForReports">
                            <logic:iterate id="stateObj" name="stateListForReports">
                                <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select>
                </td>
            <td class="label" align="left">LGA </td>
            <td align="left">
                <html:select property="lga" styleId="lga" style="width: 290px;" onchange="setActionName('cbo');forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="chartLgaList">
                            <logic:iterate name="chartLgaList" id="lgaObj">
                               <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                            </logic:iterate>
                        </logic:present>
                    </html:select>
            </td></tr>
            <tr><td class="label" align="left">CBO </td>
                <td align="left">
                    <html:select property="cboCode" styleId="cboCode" style="width: 290px;" onchange="setActionName('communityList');forms[0].submit()">
                        <html:option value="All">All</html:option>
                        <logic:present name="chartcboList">
                        <logic:iterate id="cboObj" name="chartcboList">
                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                        </logic:iterate>
                    </logic:present>
                    </html:select>
                </td>

                <td class="label" align="left">Ward </td>
                <td align="left">
                    <html:select property="communityCode" styleId="communityCode" style="width: 290px;" >
                        <html:option value="All">All</html:option>
                        <logic:present name="chartCommunityList">
                        <logic:iterate id="community" name="chartCommunityList">
                      <html:option value="${community.ward_code}">${community.ward_name}</html:option>
                        </logic:iterate>
                    </logic:present>
                    </html:select>
                </td>
                
            </tr>
            
           
            <logic:present name="organizationUnitList">
            <tr><td>&nbsp;</td>
                <td valign="top" colspan="3">
                      <fieldset>
                        <legend class="fieldset">Organization units </legend>
                        <div style="width:620px; min-height: 20px; max-height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">

                    <tr>
                      <td>
                          <logic:present name="multipleSelectionChartOrgUnitList">
                          <table width="620" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:present name="mstateListForReports">
                                  <logic:iterate name="mstateListForReports" id="stateObj">
                                      <tr><td style="width: 30px;"><html:multibox property='selectedOrganizationUnit' styleId="${stateObj.state_code}" value="${stateObj.state_code}" styleClass='smallfieldcellselect'/> </td><td>${stateObj.name} </td> </tr>
                                  </logic:iterate>
                              </logic:present>
                              <logic:present name="mchartLgaList">
                                  <logic:iterate name="mchartLgaList" id="lgaObj">
                                      <tr><td style="width: 30px;"><html:multibox property='selectedOrganizationUnit' styleId="${lgaObj.lga_code}" value="${lgaObj.lga_code}" styleClass='smallfieldcellselect'/> </td><td>${lgaObj.lga_name} </td> </tr>
                                  </logic:iterate>
                              </logic:present>
                              <logic:present name="mchartcboList">
                                  <logic:iterate name="mchartcboList" id="cboObj">
                                      <tr><td style="width: 30px;"><html:multibox property='selectedOrganizationUnit' styleId="${cboObj.orgCode}" value="${cboObj.orgCode}" styleClass='smallfieldcellselect'/> </td><td>${cboObj.orgName} </td> </tr>
                                  </logic:iterate>
                              </logic:present>
                              <logic:present name="mchartCommunityList">
                                  <logic:iterate name="mchartCommunityList" id="community">
                                      <tr><td style="width: 30px;"><html:multibox property='selectedOrganizationUnit' styleId="${community.ward_code}" value="${community.ward_code}" styleClass='smallfieldcellselect'/> </td><td>${community.ward_name} </td> </tr>
                                  </logic:iterate>
                              </logic:present>                              
                        </table>
                          </logic:present>
                          <logic:present name="singleSelectionChartOrgUnitList">
                       <table width="620" border="1" bordercolor="#D7E5F2" class="regsitertable">
                           
                               <tr><td >
                              <html:select property="singleOrgUnitId" style="width: 600px;">
                              <logic:present name="mstateListForReports">
                                  <logic:iterate name="mstateListForReports" id="stateObj">
                                      <html:option value="${stateObj.state_code}">${stateObj.name}</html:option>
                                  </logic:iterate>
                              </logic:present>
                              <logic:present name="mchartLgaList">
                                  <logic:iterate name="mchartLgaList" id="lgaObj">
                                      <html:option value="${lgaObj.lga_code}">${lgaObj.lga_name}</html:option>
                                  </logic:iterate>
                              </logic:present>
                              <logic:present name="mchartcboList">
                                  <logic:iterate name="mchartcboList" id="cboObj">
                                      <html:option value="${cboObj.orgCode}">${cboObj.orgName}</html:option>
                                  </logic:iterate>
                              </logic:present>
                              <logic:present name="mchartCommunityList">
                                  <logic:iterate name="mchartCommunityList" id="community">
                                      <html:option value="${community.ward_code}"> ${community.ward_name} </html:option>
                                  </logic:iterate>
                              </logic:present>  
                               </html:select>
                              </td></tr>
                              
                        </table>
                       </logic:present>
                      </td>
                     </tr>
                  </table>
                </div>
                  </fieldset></td>
            </tr>
            </logic:present>

            <tr><td>&nbsp;</td>
                <td valign="top" colspan="3">
                      <fieldset>
                        <legend class="fieldset">Indicators </legend>
                        <div style="width:620px; min-height: 20px; max-height:120px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">

                    <tr>
                      <td>
                          <table width="620" border="1" bordercolor="#D7E5F2" class="regsitertable">
                              <logic:present name="singleSelectionChartIndicatorList">
                                  <logic:present name="chartIndicatorList">
                                      <tr><td>
                                      <html:select property='singleIndicatorId' styleId="${indicator.indicatorId}" style="width:600px;">
                                      <logic:iterate name="chartIndicatorList" id="indicator">
                                        
                                         <html:option value="${indicator.indicatorId}">${indicator.indicatorName} </html:option>
                                                  
                                          </logic:iterate>
                                      </html:select>
                                              </td><td> </td> 
                                          </tr>
                                      
                                  </logic:present>
                              </logic:present>
                              <logic:present name="multipleSelectionChartIndicatorList">
                                  <logic:present name="chartIndicatorList">
                                      <logic:iterate name="chartIndicatorList" id="indicator">
                                          <tr><td><html:multibox property='indicatorIds' styleId="${indicator.indicatorId}" value="${indicator.indicatorId}" styleClass='smallfieldcellselect'/> </td><td>${indicator.indicatorName} </td> </tr>
                                      </logic:iterate>
                                  </logic:present>
                            </logic:present>
                         </table>

                      </td>
                      </tr>
                  </table>
                </div>
                  </fieldset></td>
            </tr>

            <tr><td class="label" align="left">Period type </td>
                <td align="left">
                    <html:select property="periodType" styleId="periodType" style="width: 290px;" onchange="setActionName('periodList');forms[0].submit()"  >
                        <html:option value="monthly">Monthly</html:option>
                        <html:option value="quarterly">Quarterly</html:option>
                        <html:option value="sixmonthly">Six monthly</html:option>
                        <html:option value="annually">Annually</html:option>
                        <html:option value="custom">Custom</html:option>
                    </html:select>
                </td>

                <td class="label" align="left">Year </td>
                <td align="left"><html:select property="year" styleId="year" style="width: 90px;" >
                        <html:option value="2016">2016</html:option>
                        <html:option value="2015">2015</html:option>
                        <html:option value="2014">2014</html:option>
                        <html:option value="2013">2013</html:option>
                        <html:option value="2012">2012</html:option>
                        <html:option value="2011">2011</html:option>
                        <html:option value="2010">2010</html:option>
                        <html:option value="2009">2009</html:option>
                        <html:option value="2008">2008</html:option>
                    </html:select>
                </td>
            </tr>

            <tr><td>&nbsp;</td>
                <td valign="top" colspan="3">
                      <fieldset>
                        <legend class="fieldset">Period </legend>
                        
                        <div style="width:620px; max-height:150px; overflow:scroll; border:1px silver solid; text-align:left; background-color:#FFFFFF;">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="regsitertable">
                 <logic:present name="multipleSelectionChartPeriodList">
                    <tr>
                      <td>

                              <table width="620" border="1" bordercolor="#D7E5F2" class="regsitertable">
                                  <logic:present name="chartPeriodList">
                                      <logic:iterate name="chartPeriodList" id="period">
                                        <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth1" value="${period}" styleClass='smallfieldcellselect'/> </td><td>${period} </td> </tr>
                                      </logic:iterate>
                                 </logic:present>
                                  <%--
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth1" value="01" styleClass='smallfieldcellselect'/> </td><td>Jan </td> </tr>
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth2" value="02" styleClass='smallfieldcellselect'/> </td><td>Feb </td> </tr>
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth3" value="03" styleClass='smallfieldcellselect'/> </td><td>Mar </td> </tr>
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth4" value="04" styleClass='smallfieldcellselect'/> </td><td>Apr </td> </tr>
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth5" value="05" styleClass='smallfieldcellselect'/> </td><td>May </td> </tr>
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth6" value="06" styleClass='smallfieldcellselect'/> </td><td>Jun </td> </tr>
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth7" value="07" styleClass='smallfieldcellselect'/> </td><td>Jul </td> </tr>
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth8" value="08" styleClass='smallfieldcellselect'/> </td><td>Aug </td> </tr>
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth9" value="09" styleClass='smallfieldcellselect'/> </td><td>Sep </td> </tr>
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth10" value="10" styleClass='smallfieldcellselect'/> </td><td>Oct </td> </tr>
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth11" value="11" styleClass='smallfieldcellselect'/> </td><td>Nov </td> </tr>
                                  <tr><td style="width: 30px;"><html:multibox property='selectedMonths' styleId="selectedMth12" value="12" styleClass='smallfieldcellselect'/> </td><td>Dec </td> </tr>
                                  --%>
                              </table>

                      </td>
                      </tr>
                  </logic:present>
                   <logic:present name="singleSelectionChartPeriodList">
                                  <tr><td>
                                          <logic:present name="chartPeriodList">
                                              <html:select property='selectedSinglePeriod' styleId="selectedSinglePeriod" styleClass='smallfieldcellselect' style="width: 600px;">
                                                  <logic:iterate name="chartPeriodList" id="period">
                                                    <html:option value="${period}">${period}</html:option>
                                                  </logic:iterate>
                                              </html:select>
                                          </logic:present>
                                          </td>
                                  </tr>
                            </logic:present>
                  </table>
                </div>
               
                  </fieldset></td>
            </tr>
            
            <tr><td>&nbsp;</td><td colspan="2" align="left">
                    <input type="button" value="Select all" onclick="selectChkBoxes('selectedMonths')" />
                    <input type="button" value="Unselect all" onclick="unselectChkBoxes('selectedMonths')" />
                    <html:checkbox property="labelVisible" value="true" /> Show label</td></tr>
            <tr><td colspan="3" align="center"><html:submit value="Generate chart" onclick="setActionName('chart')" style="width: 120px; margin-left:200px;" /></td></tr>
        </table>
        </center>
        </span>
          </td>
      </tr>
      </table>
    <!--</div>-->

            </html:form>
		</td>
      </tr>
      
    </table>    </td>
    <td width="18">&nbsp;</td>
  </tr>
</table>
  </body>
</html>

