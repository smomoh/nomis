

            var req;
            var which;

            function submitData() {
                // Construct a CSV string from the entries.  Make sure all fields are
                // filled in first.
               // f = document.theForm.firstName.value;
               // m = document.theForm.middleName.value;
                //l = document.theForm.lastName.value;
               // a = document.theForm.age.value;


               // f = document.ovc.ovcId.value;
                //m = document.ovc.surname.value;
                //l = document.ovc.otherNames1.value;
                //a = document.ovc.otherNames2.value;


state = document.ovc.state.value;
lga = document.ovc.lga.value;
ward = document.ovc.ward.value;
completedbyCbo = document.ovc.completedbyCbo.value;

dateEnrollment = document.ovc.dateEnrollment.value;
ovcId = document.ovc.ovcId.value;
surname = document.ovc.surname.value;
otherNames1 = document.ovc.otherNames1.value;
otherNames2 = document.ovc.otherNames2.value;
gender = document.ovc.gender.value;
age = document.ovc.age.value;
ageUnit = document.ovc.ageUnit.value;
address = document.ovc.address.value;
phone = document.ovc.phone.value;
eligibility1 = document.ovc.eligibility1.value;
eligibility2 = document.ovc.eligibility2.value;
eligibility3 = document.ovc.eligibility3.value;
eligibility4 = document.ovc.eligibility4.value;
eligibility5 = document.ovc.eligibility5.value;
eligibility6 = document.ovc.eligibility6.value;
eligibility7 = document.ovc.eligibility7.value;
eligibility8 = document.ovc.eligibility8.value;
eligibility9 = document.ovc.eligibility9.value;
eligibility10 = document.ovc.eligibility10.value;
hivStatus = document.ovc.hivStatus.value;
birthCertificate = document.ovc.birthCertificate.value;
schoolStatus = document.ovc.schoolStatus.value;
schoolName = document.ovc.schoolName.value;
currentClass = document.ovc.currentClass.value;
schoolType = document.ovc.schoolType.value;
orphanage = document.ovc.orphanage.value;
orphanageName = document.ovc.orphanageName.value;
caregiverName1 = document.ovc.caregiverName1.value;
caregiverName2 = document.ovc.caregiverName2.value;
caregiverGender = document.ovc.caregiverGender.value;
caregiverAge = document.ovc.caregiverAge.value;
caregiverAddress = document.ovc.caregiverAddress.value;
caregiverPhone = document.ovc.caregiverPhone.value;
caregiverOccupation = document.ovc.caregiverOccupation.value;
caregiverRelationships = document.ovc.caregiverRelationships.value;
caregiverRelationships2 = document.ovc.caregiverRelationships2.value;
csiFactor1 = document.ovc.csiFactor1.value;
csiFactor2 = document.ovc.csiFactor2.value;
csiFactor3 = document.ovc.csiFactor3.value;
csiFactor4 = document.ovc.csiFactor4.value;
csiFactor5 = document.ovc.csiFactor5.value;
csiFactor6 = document.ovc.csiFactor6.value;
csiFactor7 = document.ovc.csiFactor7.value;
csiFactor8 = document.ovc.csiFactor8.value;
csiFactor9 = document.ovc.csiFactor9.value;
csiFactor10 = document.ovc.csiFactor10.value;
csiFactor11 = document.ovc.csiFactor11.value;
csiFactor12 = document.ovc.csiFactor12.value;
serviceEnrollment1 = document.ovc.serviceEnrollment1.value;
serviceEnrollment2 = document.ovc.serviceEnrollment2.value;
serviceEnrollment3 = document.ovc.serviceEnrollment3.value;
completedbyName1 = document.ovc.completedbyName1.value;
completedbyName2 = document.ovc.completedbyName2.value;
completedbyDesignation = document.ovc.completedbyDesignation.value;















                if (state == "" ) {
                    alert("Please fill in all fields first");
                    return false;
                }


                if (lga == "" ) {
                    alert("Please fill in all fields first");
                    return false;
                }

                if (ward == "" ) {
                    alert("Please fill in all fields first");
                    return false;
                }

                if (completedbyCbo == "" ) {
                    alert("Please fill in all fields first");
                    return false;
                }







                
                //csv = f + "," + m + "," + l + "," + a;






 csv =

 state + "," +
 lga + "," +
 ward + "," +
 completedbyCbo + "," +
 dateEnrollment + "," +
 ovcId + "," +
 surname + "," +
 otherNames1 + "*" +
 otherNames2 + "," +
 gender + "," +
 age + "," +
 ageUnit + "," +
 address + "," +
 phone + "," +
 eligibility1 + "*" +
 eligibility2 + "*" +
 eligibility3 + "*" +
 eligibility4 + "*" +
 eligibility5 + "*" +
 eligibility6 + "*" +
 eligibility7 + "*" +
 eligibility8 + "*" +
 eligibility9 + "*" +
 eligibility10 + "," +
 hivStatus + "," +
 birthCertificate + "," +
 schoolStatus + "," +
 schoolName + "," +
 currentClass + "," +
 schoolType + "," +
 orphanage + "," +
 orphanageName + "," +
 caregiverName1 + "*" +
 caregiverName2 + "," +
 caregiverGender + "," +
 caregiverAge + "," +
 caregiverAddress + "," +
 caregiverPhone + "," +
 caregiverOccupation + "," +
 caregiverRelationships + "*" +
 caregiverRelationships2 + "," +
 csiFactor1 + "*" +
 csiFactor2 + "*" +
 csiFactor3 + "*" +
 csiFactor4 + "*" +
 csiFactor5 + "*" +
 csiFactor6 + "*" +
 csiFactor7 + "*" +
 csiFactor8 + "*" +
 csiFactor9 + "*" +
 csiFactor10 + "*" +
 csiFactor11 + "*" +
 csiFactor12 + "," +
 serviceEnrollment1 + "*" +
 serviceEnrollment2 + "*" +
 serviceEnrollment3 + "," +
 completedbyName1 + "*" +
 completedbyName2 + "*" +
 completedbyDesignation;









                // Ok, so now we retrieve the response as in all the other examples,
                // except that now we append the CSV onto the URL as a query string,
                // being sure to escape it first.
                retrieveURL("example5Submit.do?csv=" + escape(csv));
            }

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
                        //document.getElementById("theResponse").innerHTML = req.responseText;
                        document.getElementById("theResponse").innerHTML = req.responseText;
                    } else {
                        alert("Problem: " + req.statusText);
                    }
                }
            }






function setenabledsave(){


var buttonSave = document.getElementById('buttonSave');
var buttonModify = document.getElementById('buttonModify');
var buttonDelete = document.getElementById('buttonDelete');

if(document.ovc.ovcId.value != " ") {
 buttonSave.disabled = false;
 buttonModify.disabled = false;
 buttonDelete.disabled = false;
}
else{
    buttonSave.disabled = true;
    buttonModify.disabled = true;
    buttonDelete.disabled = true;
}
}






$(function() {
                $("#dateEnrollment").datepicker();
            });


function doOnFocus2() {
if(document.ovc.ovcId.value==" enter id")
document.ovcForm.ovcId.value="";
}

function doOnBlur2() {
if(document.ovc.ovcId.value=="")
document.ovcForm.ovcId.value=" enter id";
}