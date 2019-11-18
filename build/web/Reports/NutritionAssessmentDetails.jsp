<%-- 
    Document   : NutritionAssessmentDetails
    Created on : Jan 3, 2015, 12:08:53 AM
    Author     : smomoh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nutrition assessment details</title>
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
td {
	padding-left: 11px;
	padding-right: 11px;
	border-left: 1px solid black;
	border-bottom: 1px solid black;
	font-size: 11px;
	color: black;
}
th {
	padding-left: 11px;
	padding-right: 11px;
	border-left: 1px solid black;
	border-bottom: 1px solid black;
        border-top: 1px solid black;
	font-size: 11px;
	color: black;
}
table {
	border-collapse: collapse;
	margin: 10px;
}
.lab
{
    font-family:  arial, sans-serif;
    font-size: 14px;
    /*font-weight: bold;*/
    letter-spacing: 3px;
}
.orgNames
{
    font-family:  arial, sans-serif;
    font-size: 12px;
    border: none; text-align: left; width: 40px;
}
.orgNamestd
{
    border: none; text-align: left; width: 40px;
}
</style>
    </head>
    <body>
        <div align="center">
        <div style=" font-family: arial" align="center">Nutrition Assessment details</div>
        <table width="70%" border="0" cellspacing="0" cellpadding="0" style="border:1px black solid;">
            <tr><td colspan="2">Name: &nbsp;&nbsp;${nutritionAssessmentRecord.ovcFname} ${nutritionAssessmentRecord.ovcSurname} </td><td colspan="2">OVC Id: &nbsp;&nbsp;${nutritionAssessmentRecord.ovcId} </td></tr>
        <tr><td colspan="4">&nbsp; </td></tr>
        <tr><td colspan="2"><label class="lab" style="margin-right: 10px;">State:</label><label class="orgNames">${naorgparams[14]}</label></td>
            <td colspan="2"><label class="lab" style="margin-right: 10px;">LGA:</label> <label class="orgNames">${naorgparams[11]}</label></td></tr>
        <tr><td colspan="2"><label class="lab" style="margin-right: 10px;">CBO:</label><label class="orgNames">${naorgparams[12]}</label></td>
        <td colspan="2"><label class="lab" style="margin-right: 10px;">Community:</label><label class="orgNames">${naorgparams[13]}</label></td></tr>
        <tr><td colspan="4">&nbsp; </td></tr>
                
                <%--<tr><td class="orgNamestd" style=" font-size: 12px; width: 300px;" colspan="8"> <a href="enrollmentpdf.do" style="margin-left: 20px;" target="_blank">Export to PDF</a></td> <td style=" border: none;" width="15%"> </td></tr>--%>
     
    
    <tr><td>Gender</td><td>${nutritionAssessmentRecord.ovcGender} </td><td> </td><td> </td></tr>
    <tr><td>Age </td><td>${nutritionAssessmentRecord.ovcAge} </td><td>Age unit </td><td>${nutritionAssessmentRecord.ageUnit} </td></tr>
    <tr><td>Date of assessment </td><td>${nutritionAssessmentRecord.dateOfAssessment} </td><td>Weight </td><td>${nutritionAssessmentRecord.weight} </td></tr>
    <tr><td>Last Weight </td><td>${nutritionAssessmentRecord.lastWeight} </td><td>Date of last weight </td><td>${nutritionAssessmentRecord.dateOfLastWeight} </td></tr>
    <tr><td>Change in weight </td><td>${nutritionAssessmentRecord.changeInWeight} </td><td>Height </td><td>${nutritionAssessmentRecord.height} </td></tr>
    <tr><td>Oedema </td><td>${nutritionAssessmentRecord.oedema} </td><td>MUAC </td><td>${nutritionAssessmentRecord.muac} </td></tr>
    <tr><td>BMI </td><td>${nutritionAssessmentRecord.bodyMassIndex} </td><td>Nutritional status </td><td>${nutritionAssessmentRecord.nutritionalStatus} </td></tr>
    <tr><td colspan="3">In the last 30 days, was there ever no food to eat in the household? </td><td>${nutritionAssessmentRecord.foodSecurityAndDietQ1} </td></tr>
    <tr><td colspan="3">In the last 30 days did any household member go to sleep hungry because there wasn’t any food?</td><td>${nutritionAssessmentRecord.foodSecurityAndDietQ2} </td></tr>
    <tr><td colspan="3">In the last 30 days, did any household member go a whole day and night without eating anything because there was not enough food?</td><td>${nutritionAssessmentRecord.foodSecurityAndDietQ3} </td></tr>
    <tr><td colspan="3">How many times did the child eat yesterday?</td><td>${nutritionAssessmentRecord.foodSecurityAndDietQ4} </td></tr>
    <tr><td colspan="3">Yesterday, did the child eat any vitamin A rich foods (for example: mango, carrots, papaya, red palm oil, zogali, ugu, cassava, liver, or kidney)?</td><td>${nutritionAssessmentRecord.foodSecurityAndDietQ5} </td></tr>
    <tr><td colspan="3">Yesterday, did the child eat any Iron-rich foods (for example: liver, kidney, beans, groundnut, or dark green leaves such as spinach, zogali, ugu, cassava)?</td><td>${nutritionAssessmentRecord.foodSecurityAndDietQ6} </td></tr>
    <tr><td colspan="3">Yesterday, did the child eat any protein foods (for example: meat, eggs, fish, beans, groundnut, milk, cheese, soya, or Tom Brown)?</td><td>${nutritionAssessmentRecord.foodSecurityAndDietQ7} </td></tr>
    <tr><td colspan="3">Did the child receive any food or liquid besides breast milk in the last 24 hours?</td><td>${nutritionAssessmentRecord.foodSecurityAndDietQ8} </td></tr>
    <tr><td colspan="3">Is the mother experiencing any difficulties with breastfeeding?</td><td>${nutritionAssessmentRecord.foodSecurityAndDietQ9} </td></tr>

    <tr><td colspan="3">Does the household have soap and water to wash dishes and utensils?</td><td>${nutritionAssessmentRecord.hygieneQ1} </td></tr>
    <tr><td colspan="3">Does the household have soap or ash for hand washing?</td><td>${nutritionAssessmentRecord.hygieneQ2} </td></tr>
    <tr><td colspan="3">Do you normally wash your hands with soap/ash before cooking/eating?</td><td>${nutritionAssessmentRecord.hygieneQ3} </td></tr>
    <tr><td colspan="3">Do you normally wash your hands with soap/ash after the toilet?</td><td>${nutritionAssessmentRecord.hygieneQ4} </td></tr>
    <tr><td colspan="4">Clinical – Recent illnesses conditions that have affected child’s nutrition AND REFER FOR MEDICAL EVALUATION</td></tr>

    <tr><td>HIV</td><td>${nutritionAssessmentRecord.medicalEvaluationHIV} </td><td>Diarrhea </td><td>${nutritionAssessmentRecord.medicalEvaluationDiarrhea} </td></tr>
    <tr><td>Nausea</td><td>${nutritionAssessmentRecord.medicalEvaluationNausea} </td><td>Vomiting </td><td>${nutritionAssessmentRecord.medicalEvaluationVomiting} </td></tr>
    <tr><td>Poor appetite</td><td>${nutritionAssessmentRecord.medicalEvaluationPoorApetite} </td><td>Mouth sores</td><td>${nutritionAssessmentRecord.medicalEvaluationMouthSoars} </td></tr>
    <tr><td colspan="3">Difficult or painful chewing/swallowing</td><td>${nutritionAssessmentRecord.medicalEvaluationPainfulChewing} </td></tr>
    <tr><td>Date modified</td><td>${nutritionAssessmentRecord.dateModified} </td><td>Recorded by</td><td>${nutritionAssessmentRecord.recordedBy} </td></tr>
    
</table>
        </div>
    </body>
</html>
