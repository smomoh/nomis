<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Lookup Table</title>
<script src="SpryAssets/SpryTabbedPanels.js" type="text/javascript"> </script>
<link href="SpryAssets/SpryTabbedPanels.css" rel="stylesheet" type="text/css" />
<link href="images/kidmap2.css" rel="stylesheet" type="text/css" />

<SCRIPT LANGUAGE="JavaScript" >
function updateComponents(ifName)
{
   window.frames[ifName].loadValues();
}
</SCRIPT>
</head>

    <body class="paramPage">
<div id="TabbedPanels1" class="TabbedPanels">
  <ul class="TabbedPanelsTabGroup">
    <li class="TabbedPanelsTab" tabindex="3" style="width:140px; text-align:center;">Partners/Govt. Agencies</li>
    <li class="TabbedPanelsTab" tabindex="2" style="width:80px; text-align:center;">Site setup</li>
    <li class="TabbedPanelsTab" tabindex="3" style="width:140px; text-align:center;" onclick="updateComponents('orgRecords')">Organization setup</li>
    <li class="TabbedPanelsTab" tabindex="3" style="width:50px; text-align:center;" onclick="updateComponents('wardLookup')"><jsp:include page="includes/WardName.jsp" /></li>
    <li class="TabbedPanelsTab" tabindex="3" style="width:120px; text-align:center;" >Vulnerability status</li>
  </ul>
  <div class="TabbedPanelsContentGroup">
    <div class="TabbedPanelsContent" style="height:600px;"><iframe name="partners" width="100%" height="100%" src="addPartners.do" frameborder="0" scrolling="no" ></iframe></div>
    <div class="TabbedPanelsContent" style="height:600px;"><iframe name="cbosetup" width="100%" height="100%" src="cboSetup.do" frameborder="0" scrolling="no" ></iframe></div>
    <div class="TabbedPanelsContent" style="height:700px;"><iframe name="orgRecords" width="100%" height="100%" src="orgrecords.do" frameborder="0" scrolling="no"></iframe></div>
    <div class="TabbedPanelsContent" style="height:600px;"><iframe name="wardLookup" width="100%" height="100%" src="addWard.do" frameborder="0" scrolling="no" ></iframe></div>
    <div class="TabbedPanelsContent" style="height:600px;"><iframe name="eligibilitySetup" width="100%" height="100%" src="eligibility.do" frameborder="0" scrolling="no" ></iframe></div>
  </div>
</div>
<script type="text/javascript">
<!--
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
//-->
</script>
</body>
</html>
