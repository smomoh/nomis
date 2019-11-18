<%-- 
    Document   : MERReportView
    Created on : Apr 6, 2015, 8:03:02 PM
    Author     : Siaka
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<logic:notPresent name="USER">
    <logic:forward name="login" />
</logic:notPresent>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MER Report form</title>
    </head>
    <body>
        <table align="center" style="border-collapse: collapse;width: 800px;">
            <tr><td colspan="4" align="center">MER Report FORM </td></tr>
            <tr><td >State: </td><td style="margin-right: 10px;">${merReportParam[7]} </td><td >Lga: </td><td >${merReportParam[8]} </td></tr>
            <tr><td>Organization: </td><td>${merReportParam[9]} </td><td >Partner: </td><td >${merReportParam[13]} </td></tr>
            <tr><td >Reporting period: </td><td >${merReportParam[3]} ${merReportParam[4]} to ${merReportParam[5]} ${merReportParam[6]} </td><td> </td><td> </td></tr>
            <tr><td colspan="4"><a href="usgDataEntryFormInPdf.do" target="_blank" >Download as PDF</a> </td></tr>
        </table>
        <table border="1" style="border-collapse: collapse; width: 800px;" align="center">
            
                <logic:iterate id="mer" collection="${merReportList[0]}">
            <tr >
                <td colspan="4" class="tdLine" style="font-size:16px; background-color: lightgray "><div >${mer[0]}</div></td>
             </tr>
            <tr >
                <th  class="tdLine" style="font-size:16px; width: 70px;"><div >Age range</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Male</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Female</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Total</div></th>
            </tr>
            
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div ><1</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[1]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[8]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[1]+mer[8]} </div></td>
                </tr>
                <tr >
                    <td   style="font-size:16px; "><div >1-4</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[2]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[9]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[2]+mer[9]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div > 5-9</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[3]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[10]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[3]+mer[10]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >10-14</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[4]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[11]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[4]+mer[11]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div > 15-17</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[5]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[12]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[5]+mer[12]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >18+</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[6]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[13]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[6]+mer[13]} </div></td>
                </tr>
                <tr >
                    <td  colspan="4" style="height: 20px;"> </td>
                </tr>
                </logic:iterate>
                
                
                <logic:iterate id="mer" collection="${merReportList[1]}">
            <tr >
                <td colspan="4" class="tdLine" style="font-size:16px; background-color: lightgray "><div >${mer[0]}</div></td>
             </tr>
            <tr >
                <th  class="tdLine" style="font-size:16px; width: 70px;"><div >Age range</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Male</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Female</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Total</div></th>
            </tr>
            
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div ><1</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[1]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[8]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[1]+mer[8]} </div></td>
                </tr>
                <tr >
                    <td   style="font-size:16px; "><div >1-4</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[2]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[9]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[2]+mer[9]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div > 5-9</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[3]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[10]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[3]+mer[10]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >10-14</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[4]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[11]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[4]+mer[11]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div > 15-17</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[5]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[12]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[5]+mer[12]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >18-24</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[6]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[13]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[6]+mer[13]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >25+</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[7]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[14]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[7]+mer[14]} </div></td>
                </tr>
                <tr >
                    <td  colspan="4" style="height: 20px;"> </td>
                </tr>
                </logic:iterate>
                <tr >
                    <td  colspan="4" style="height: 20px;"> 
                        
                        <table border="1" style="border-collapse: collapse">
                            <tr style="font-size:16px; background-color: lightgray "> <td>Age range </td><td>${merReportList[2][0][0]} </td><td>${merReportList[2][1][0]} </td><td>${merReportList[2][2][0]} </td><td>${merReportList[2][3][0]} </td><td>${merReportList[2][4][0]} </td><td>${merReportList[2][5][0]} </td></tr>
                            <tr> <td> <1 </td><td>${merReportList[2][0][1]+merReportList[2][0][8]} </td><td>${merReportList[2][1][1]+merReportList[2][1][8]} </td><td>${merReportList[2][2][1]+merReportList[2][2][8]} </td><td>${merReportList[2][3][1]+merReportList[2][3][8]} </td><td>${merReportList[2][4][1]+merReportList[2][4][8]} </td><td>${merReportList[2][5][1]+merReportList[2][5][8]} </td></tr>
                            <tr> <td> 1-4 </td><td>${merReportList[2][0][2]+merReportList[2][0][9]} </td><td>${merReportList[2][1][2]+merReportList[2][1][9]} </td><td>${merReportList[2][2][2]+merReportList[2][2][9]} </td><td>${merReportList[2][3][2]+merReportList[2][3][9]} </td><td>${merReportList[2][4][2]+merReportList[2][4][9]} </td><td>${merReportList[2][5][2]+merReportList[2][5][9]} </td></tr>
                            <tr> <td> 5-9 </td><td>${merReportList[2][0][3]+merReportList[2][0][10]} </td><td>${merReportList[2][1][3]+merReportList[2][1][10]} </td><td>${merReportList[2][2][3]+merReportList[2][2][10]} </td><td>${merReportList[2][3][3]+merReportList[2][3][10]} </td><td>${merReportList[2][4][3]+merReportList[2][4][10]} </td><td>${merReportList[2][5][3]+merReportList[2][5][10]} </td></tr>
                            <tr> <td> 10-14 </td><td>${merReportList[2][0][4]+merReportList[2][0][11]} </td><td>${merReportList[2][1][4]+merReportList[2][1][11]} </td><td>${merReportList[2][2][4]+merReportList[2][2][11]} </td><td>${merReportList[2][3][4]+merReportList[2][3][11]} </td><td>${merReportList[2][4][4]+merReportList[2][4][11]} </td><td>${merReportList[2][5][4]+merReportList[2][5][11]} </td></tr>
                            <tr> <td> 15-17 </td><td>${merReportList[2][0][5]+merReportList[2][0][12]} </td><td>${merReportList[2][1][5]+merReportList[2][1][12]} </td><td>${merReportList[2][2][5]+merReportList[2][2][12]} </td><td>${merReportList[2][3][5]+merReportList[2][3][12]} </td><td>${merReportList[2][4][5]+merReportList[2][4][12]} </td><td>${merReportList[2][5][5]+merReportList[2][5][12]} </td></tr>
                            <tr> <td> 18+ </td><td>${merReportList[2][0][6]+merReportList[2][0][13]} </td><td>${merReportList[2][1][6]+merReportList[2][1][13]} </td><td>${merReportList[2][2][6]+merReportList[2][2][13]} </td><td>${merReportList[2][3][6]+merReportList[2][3][13]} </td><td>${merReportList[2][4][6]+merReportList[2][4][13]} </td><td>${merReportList[2][5][6]+merReportList[2][5][13]} </td></tr>
                        </table>
                        
                    </td>
                </tr>
                <logic:iterate id="mer" collection="${merReportList[3]}">
            <tr >
                <td colspan="4" class="tdLine" style="font-size:16px; background-color: lightgray "><div >${mer[0]}</div></td>
             </tr>
            <tr >
                <th  class="tdLine" style="font-size:16px; width: 70px;"><div >Age range</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Male</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Female</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Total</div></th>
            </tr>

                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div ><1</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[1]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[8]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[1]+mer[8]} </div></td>
                </tr>
                <tr >
                    <td   style="font-size:16px; "><div >1-4</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[2]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[9]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[2]+mer[9]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div > 5-9</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[3]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[10]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[3]+mer[10]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >10-14</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[4]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[11]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[4]+mer[10]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div > 15-17</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[5]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[12]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[5]+mer[12]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >18-24</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[6]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[13]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[6]+mer[13]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >25+</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[6]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[13]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[6]+mer[13]} </div></td>
                </tr>
                <tr >
                    <td  colspan="4" style="height: 20px;"> </td>
                </tr>
                </logic:iterate>
                <logic:iterate id="mer" collection="${merReportList[5]}">
            <tr >
                <td colspan="4" class="tdLine" style="font-size:16px; background-color: lightgray "><div >${mer[0]}</div></td>
             </tr>
            <tr >
                <th  class="tdLine" style="font-size:16px; width: 70px;"><div >Age range</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Male</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Female</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Total</div></th>
            </tr>

                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div ><1</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[1]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[9]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[1]+mer[9]} </div></td>
                </tr>
                <tr >
                    <td   style="font-size:16px; "><div >1-4</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[2]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[10]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[2]+mer[10]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div > 5-9</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[3]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[11]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[3]+mer[11]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >10-14</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[4]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[12]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[4]+mer[12]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div > 15-17</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[5]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[13]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[5]+mer[13]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >18-24</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[6]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[14]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[6]+mer[14]} </div></td>
                </tr>
                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >25+</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[7]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[15]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[7]+mer[15]} </div></td>
                </tr>
                <tr >
                    <td  colspan="4" style="height: 20px;"> </td>
                </tr>
                </logic:iterate>
                <logic:iterate id="mer" collection="${merReportList[4]}">
            <tr >
                <td colspan="4" class="tdLine" style="font-size:16px; background-color: lightgray "><div >${mer[0]}</div></td>
             </tr>
            <tr >
                <th  class="tdLine" style="font-size:16px; width: 70px;"><div >Age range</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Male</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Female</div></th>
                <th   class="tdLine" style="font-size:16px"><div >Total</div></th>
            </tr>

                <tr >
                    <td  class="tdLine" style="font-size:16px; "><div >0-17</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[1]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[5]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[1]+mer[5]} </div></td>
                </tr>
                <tr >
                    <td   style="font-size:16px; "><div >18+</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[2]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[6]}</div></td>
                    <td   class="tdLine" style="font-size:16px"><div >${mer[2]+mer[6]} </div></td>
                </tr>
                                
                <tr >
                    <td  colspan="4" style="height: 20px;"> </td>
                </tr>
                </logic:iterate>
                         
        </table>
    </body>
</html>
