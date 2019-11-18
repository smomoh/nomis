var xmlhttp;
var lgas="";
var callerId="";
var stateVal="";
var secs
var timerID = null
var timerRunning = false
var delay = 1000
var compId=null
var currentRegimen="";

function showSuccessMsg(msg,id)
{
	if(msg != " ")
	InitializeTimer();
	compId=id
}
function InitializeTimer()
{
	secs = 2
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
    if (secs==0)
    {
        StopTheClock()
		document.getElementById(compId).style.visibility='hidden';
    }
    else
    {
        self.status = secs
        secs = secs - 1
        timerRunning = true
		timerID = self.setTimeout("StartTheTimer()", delay)
    }
}

function setDate( elementName,topPos,leftPos,setValue )	
{
	activeElement = document.forms[0].elements[ elementName ];
	document.getElementById('calendarDiv').style.top=topPos;
	document.getElementById('calendarDiv').style.left=leftPos;
	if(document.getElementById('calendarDiv').style.visibility=='visible')
	document.getElementById('calendarDiv').style.visibility='hidden';
	else
	{
		document.getElementById('calendarDiv').style.visibility='visible';
		window.frames['calendarFrame'].setElement(elementName,setValue);
	}
}
function displayDate(date)
{
	var dateArray=date.split('-');
	return dateArray[2]+'/'+dateArray[1]+'/'+dateArray[0];
}
function addDate(dateArray, days)
{
	var d=new Date();
	d.setDate(dateArray[0]);
	d.setMonth(dateArray[1]);
	d.setFullYear(dateArray[2]);
	d.setDate(d.getDate() + parseInt(days));	
	return d.getDate()+'/'+d.getMonth()+'/'+d.getFullYear();
}
function hideCalendar()
{
	document.getElementById('calendarDiv').style.visibility='hidden';
}
function validateDate(dateObj)
{
	var msg="Date must be numeric and in the form dd/mm/yyyy";
	if(dateObj.indexOf('/') ==-1)
	{
		alert(msg)
		return false
	}
	else
	{
		dateArray=dateObj.split('/')
		if(isNaN(dateArray[0]) || isNaN(dateArray[1]) || isNaN(dateArray[2]))
		{
			alert(msg)
			return false
		}
		else
		{
			var mthEnd=getLastDayOfMonth(dateArray[1])
				if(dateArray[0].length !=2 || dateArray[1].length !=2 || dateArray[2].length !=4)
				{
					alert(msg)
					return false
				}
				else if(dateArray[0] > mthEnd)
				{
					alert("Invalid date. Enter correct day")
					return false
				}
				else if(dateArray[0] < 1 || dateArray[1] < 1 || dateArray[2] < 1)
				{
					alert("Invalid date. date cannot be Zero or negative")
					return false
				}
		}
	}
	return true
}
function getLastDayOfMonth(month)
{
	lastDay=28;
	if(month=='April' || month==4 || month=='June' || month==6 || month=='September' || month==9 || month=='November' ||month==11)
	lastDay=30;
	else if(month=='February' || month==2)
	lastDay=28;
	else
	lastDay=31;
	return lastDay;
}
function loadMonth(id)
{
	var month=new Array("January","February","March","April","May","June","July","August","September","October","November","December");
	for(var i=0; i<month.length; i++)
	{
		document.getElementById(id).options[i]=new Option(month[i],month[i]);	
	}
}
function loadYears(id)
{
	var year=1950;
	document.getElementById(id).options.length=0;
	for (var i=0; i<150; i++)
	{
		year++;
		document.getElementById(id).options[i]=new Option(year);
	}
}
function getExistingRegimen(param)
{
	getValuesFromDb('RegimenTypeajaxFile.php',param,'regCombination');
}
function getSuggNextAppointDate()
{
	var dispDate=document.getElementById('dispenseDate').value;
	var days=document.getElementById('refillPeriod').value;
	if(dispDate==" " || !parseInt(days))
	return;
	var dateArray=dispDate.split('/');
	date=addDate(dateArray, days);
	document.getElementById('nextDispenseDate').value=date;
	document.getElementById('sugDispenseDate').value=date;
}
function setupFacility(data)
{
	if(data[6]=='on')
	{
		document.getElementById('adrDiv2').style.visibility='hidden';
		document.getElementById('adrLbl').style.visibility='hidden';
		document.getElementById('anyAdrDiv').style.visibility='visible';
		document.getElementById('oiDiv2').style.visibility='hidden';
		document.getElementById('oiLbl').style.visibility='hidden';
		document.getElementById('anyOiDiv').style.visibility='visible';
	}
	else
	{
		document.getElementById('adrDiv2').style.visibility='visible';
		document.getElementById('adrLbl').style.visibility='visible';
		document.getElementById('anyAdrDiv').style.visibility='hidden';
		
		document.getElementById('oiDiv2').style.visibility='visible';
		document.getElementById('oiLbl').style.visibility='visible';
		document.getElementById('anyOiDiv').style.visibility='hidden';
	}	
}
function openOiDetails()
{
	w = window.open("OIDetailsFrame.php", "oiDetails", 
"width=740,height=585,left=230,top=70,resizable=no,scrollbars=no,menubar=no,location=no,status=no");
}
function openHistoryTabs()
{
	var hNo=document.getElementById('hNo').value;
	var w = window.open("HistoryTabs.html", "patHistory", 
"width=538,height=445,left=230,top=70,resizable=no,scrollbars=no,menubar=no,location=no,status=no");
}
function enableAdrBtn(id,id2)
{
	var dropdownIndex = document.getElementById(id).selectedIndex;
	if(document.getElementById(id).options[dropdownIndex].text=='Yes')
	document.getElementById(id2).disabled= false;
	else
	document.getElementById(id2).disabled='disabled';
}
function openAdrDetails()
{
	w = window.open("AdrDetailsFrame.php", "adrDetails", 
"width=740,height=585,left=230,top=70,resizable=no,scrollbars=no,menubar=no,location=no,status=no");
}
function disableBtnById(id)
{
	document.getElementById(id).disabled='disabled';
}
function enableBtnById(id)
{
	document.getElementById(id).disabled=false;
}
function changeDateOfRegLabel(value)
{
	if(value=='ART Transfer In')
	{
		document.getElementById('dateLbl2').innerHTML='Transfer in:';
	}
	else
	{
		document.getElementById('dateLbl2').innerHTML='Registration:';
	}
}
function clearTextFields(ids)
{
	for(var i=0; i<ids.length; i++)
	{
		document.getElementById(ids[i]).value=" ";
	}
}
function checkPatient(id)
{
	var hospNo=document.getElementById(id).value;
	if(hospNo=="")
	{
	  var idArray=new Array('sName','otherName','ageAtReg','birthDate','address','phone','regDate','nokName','nokAddress','nokPhone');
		clearTextFields(idArray);
		return false;
	}
	getValuesFromDb('PatientInfoajaxFile.php',hospNo,'patInfo');	
}
function getValuesFromDb(url,str,id)
{
    alert('inside')
	callerId=id;
	xmlhttp=GetXmlHttpObject();
if (xmlhttp==null)
  {
  alert ("Browser does not support HTTP Request");
  return;
  }
url=url+"?q="+str;
url=url+"&sid="+Math.random();
xmlhttp.onreadystatechange=stateChanged;
xmlhttp.open("GET",url,true);
xmlhttp.send(null);
}
function GetXmlHttpObject()
{
if (window.XMLHttpRequest)
  {
  // code for IE7+, Firefox, Chrome, Opera, Safari
  return new XMLHttpRequest();
  }
if (window.ActiveXObject)
  {
  // code for IE6, IE5
  return new ActiveXObject("Microsoft.XMLHTTP");
  }
return null;
}
function enableControl(id)
{
	document.getElementById(id).disabled = false;
}
function disableControl(id)
{
	document.getElementById(id).disabled = 'disabled';
}
/*clean up the patient details in the popup box to remove empty spaces*/
function cleanUp(id)
{
		for(i=0; i<document.getElementById(id).options.length; i++ )
		{
			if(document.getElementById(id).options[i].text=="")
			{
				document.getElementById(id).options[i]=null;
				for(j=0; j<document.getElementById(id).options.length; j++ )
				{
					if(document.getElementById(id).options[j].text=="")
					{
						cleanUp(id);
					}
				}
			}
		}
}
function populateNames(id,data)
{
	document.getElementById(id).options.length=0;
	for(var i=0; i<data.length; i+=3)
	{
		var names=data[i+1].toUpperCase()+"    "+data[i+2];
		document.getElementById(id).options[i]=new Option(names,data[i]);
	}
	cleanUp(id);
}
function trim(stringToTrim)
{
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function setSelectBoxItemColor(id,txt)
{
	for(var i=0; i<document.getElementById(id).options.length; i++)
	{
		if(document.getElementById(id).options[i].text ==txt)
		{
			document.getElementById(id).options[i].style.color="#AAFFEE";
			break;
		}
	}
}
function setSelectBoxText(id,txt)
{
	for(var i=0; i<document.getElementById(id).options.length; i++)
	{
		if(document.getElementById(id).options[i].text==trim(txt))
		{
			document.getElementById(id).options[i].selected="selected";
			break;
		}
	}
	
}
function setPatDispensaryDetails(data)
{
	var dispenseDetails=data.split(';');
	var regType=dispenseDetails[0];
	currentRegimen=dispenseDetails[1];
	var nextClinic=dispenseDetails[2];
	//alert(dispenseDetails[3]);
	//document.getElementById('modifyDate').value=dispenseDetails[3];
	//alert(document.getElementById('modifyDate').value)
	setSelectBoxText('drugRegimen',regType);
	populateSelectedRegimen(trim(regType));
	document.getElementById('nextClinicDate').value=displayDate(nextClinic);
	var txt=new String(regimen)
}
function setStateValue(state)
{
		stateVal=state;
}
function calculateAge(id,id2,id3)
{
	var dob=document.getElementById(id).value;
	var dor=document.getElementById(id2).value;
	if(dob=="" || dor=="")
	return false;
	var dobArr=dob.split("/");
	var dorArr=dor.split("/");
	var patAge=dorArr[2]-dobArr[2];
	if(patAge<0)
	{
		alert("Date at Registration less than date of birth");
		return false;
	}
	document.getElementById(id3).value=patAge;
}
function populatePatDetails(dropdownValue)
{
	if(dropdownValue==" ")
	{
		var btnVar=document.patForm;
		document.getElementById('save').disabled=false;
		return false;
	}
	
	a=dropdownValue.split(";");
	var docVar=document.patForm;
	
	docVar.hNo.value=a[0];
	docVar.sName.value=a[1];
	docVar.otherName.value=a[2];
	setSelectBoxText("gender",a[3])
	docVar.birthDate.value=makeDisplayDate(a[4]);
	docVar.ageAtReg.value=a[5];
	docVar.regDate.value=makeDisplayDate(a[6]);
	setSelectBoxText("status",a[7])
	docVar.address.value=a[8];
	docVar.phone.value=a[9];
	setSelectBoxText("stateOfRes",a[11]);
	getValuesFromDb('ajaxFile.php',a[10],'states')
	setSelectBoxText("lgOfRes",a[12]);
	docVar.nokName.value=a[13];
	setSelectBoxText("nokRelationship",a[14]);
	docVar.nokAddress.value=a[15];
	docVar.nokPhone.value=a[16];
	document.getElementById('patInfo').value=a[0];
	enableBtnById('clear');
	enableBtnById('modify');
	enableBtnById('deleteBtn');
	disableBtnById('save');
}
function disableBtn()
{
	var btnVar=document.patForm;
	btnVar.save.disabled="disabled";
	btnVar.modify.disabled="disabled";
	btnVar.deleteBtn.disabled="disabled";
}
function getSeletedItem(id)
{
	var index=document.getElementById(id).selectedIndex;
	var selectedVal=document.getElementById(id).options[index].value;
	return selectedVal;
}
function populatePatInfo(a)
{
	if(a[0]==" ")
	{
		clearPatInfo()
		return
	}
	document.getElementById('sName').innerHTML=a[1]
	document.getElementById('otherName').innerHTML=a[2]
	document.getElementById('gender').innerHTML=a[3]
	document.getElementById('age').innerHTML=a[5]
	document.getElementById('status').innerHTML=a[17]
	document.getElementById('since').innerHTML=a[18]
	document.getElementById('modifyDate').value=a[19]
	enableBtnById('clear')
	enableBtnById('save')
	enableBtnById('modify')
	enableBtnById('deleteBtn')
	getValuesFromDb('DecryPatientInfoajaxFile.php',a[0],'patInfo')
}
function clearPatInfo()
{
	document.getElementById('sName').innerHTML=" "
	document.getElementById('otherName').innerHTML=" "
	document.getElementById('gender').innerHTML=" "
	document.getElementById('status').innerHTML=" "
	document.getElementById('age').innerHTML=" "
	document.getElementById('since').innerHTML=" "
	disableBtnById('save')
	disableBtnById('modify')
	disableBtnById('deleteBtn')
	disableBtnById('clear')
}
function getPatInformation()
{
	var hospNo=document.getElementById('hNo').value;
	if(hospNo=="")
	{
		clearPatInfo()
		return false;
	}
	getValuesFromDb('PatientInfoajaxFile.php',hospNo,'sexAndAge');
}
function getSeletedVal()
{
	var dropdownIndex = document.getElementById('gender').selectedIndex;
	var dropdownValue = document.patForm.gender.options[dropdownIndex].text
	return dropdownValue;
}
function showOtherHistory(id,iframeName)
{
	if(document.getElementById(id).style.visibility=='hidden')
	document.getElementById(id).style.visibility='visible';
	else
	document.getElementById(id).style.visibility='hidden';
	var hNo=document.getElementById('hNo').value;
	window.frames[iframeName].getPatientHNo(hNo,iframeName);
}
function showClinicalHistory(id,iframeName)
{
	if(document.getElementById(id).style.visibility=='hidden')
	document.getElementById(id).style.visibility='visible';
	else
	document.getElementById(id).style.visibility='hidden';
	var hNo=document.getElementById('hNo').value;
	window.frames['historyTabsFrame'].getPatientHNo(hNo,iframeName);
}
function showCurrentRegimen(id)
{
	setSelectBoxItemColor(id,currentRegimen)
	setExistingRegInfo(currentRegimen)
}
function setExistingRegInfo(param)
{
	if(param.indexOf(')') !=-1)
	{
		clearTableIndex=1;
		window.frames['dispensaryGrid'].clearTable();
	}
	else
	clearTableIndex=-1;
	getValuesFromDb('ExistingRegInfoajaxFile.php',param,'existingRegInfo');
	k=0;
}
function validateRegInfo()
{
	if(document.getElementById('sName').value== " " || document.getElementById('otherName').value== " ")
	{
		alert("Please enter Surname and Other names")
		return false;
	}
	else if(document.getElementById('birthDate').value== " ")
	{
		alert("Please enter date of birth")
		return false;
	}
	var dropdownIndex = document.getElementById('gender').selectedIndex;
	var dropdownValue = document.patForm.gender.options[dropdownIndex].text
	if(dropdownValue=="")
	{
		 alert("Please select gender")
		 return false;
	}
	
	var dropdownIndex = document.getElementById('stateOfRes').selectedIndex;
	var stateOfResVal = document.patForm.stateOfRes.options[dropdownIndex].text
	if(stateOfResVal=="")
	{
		alert("Please select State of Residence")
		return false;
	}
	var dropdownIndex = document.getElementById('lgOfRes').selectedIndex;
	var lgOfResVal = document.patForm.lgOfRes.options[dropdownIndex].text
	if(lgOfResVal=="")
	{
		alert("Please select LGA of Residence")
		return false;
	}
	var dropdownIndex = document.getElementById('status').selectedIndex;
	var statusVal = document.patForm.status.options[dropdownIndex].text
	if(statusVal=="")
	{
		alert("Please select status at registration")
		return false;
	}
	if(document.getElementById('regDate').value== " ")
	{
		alert("Please enter date of registration")
		return false;
	}
}
function validateClinical()
{
	if(document.getElementById('visitDate').value== " ")
	{
		alert("Please enter Date of visit")
		return false;
	}
	/*else if(document.getElementById('birthDate').value== " ")
	{
		alert("Please enter date of birth")
		return false;
	}*/
	var dropdownIndex = document.getElementById('clinicalStage').selectedIndex;
	var dropdownValue = document.patForm.clinicalStage.options[dropdownIndex].text
	if(dropdownValue=="")
	{
		 alert("Please select WHO Clinical stage")
		 return false;
	}
	if(!parseInt(document.getElementById('weight').value) || !parseInt(document.getElementById('height').value))
	{
		alert("Weight and height must be numeric")
		return false
	}
}
function confirmAction(opt)
{
	if(document.getElementById('hNo').value=="")
	{
		alert("Please enter Hospital Number");
		return false;
	}
	else if(opt=="save")
	{
		return true;
	}
	else
	{
		if(confirm("Do you want to " +opt+" this record?"))
		return true
		else
		return false;
	}
	return true;
}
