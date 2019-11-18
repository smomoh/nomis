/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ImportExport;

import com.fhi.nomis.nomisutils.AppUtility;
import com.fhi.kidmap.business.Training;
import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.WardDao;
import com.fhi.kidmap.dao.WardDaoImpl;
import com.nomis.databasemanagement.DataMgtUtility;
import java.io.File;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;

/**
 *
 * @author smomoh
 */
public class WardExportImportManager implements Serializable
{
    AppUtility appUtil=new AppUtility();
    public void exportWardRecordsInXml(List wardList) throws Exception
    {
        String fileName="";
        String[] columnNames={"cbo_code","ward_code","ward_name"};
        int count=0;
      try
      {
        StringWriter sw=new StringWriter();
	StreamResult streamResult = new StreamResult(sw);
	SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
	// SAX2.0 ContentHandler.
	TransformerHandler hd = tf.newTransformerHandler();
	Transformer serializer = hd.getTransformer();
	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
	//serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"users.dtd");
	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
	hd.setResult(streamResult);
	hd.startDocument();
	AttributesImpl atts = new AttributesImpl();
        hd.startElement("","","datavaluset",atts);
        Wards ward=null;
        String xmlString =null;
        int counter=0;
       for(int j=0; j<wardList.size(); j++)
	{
           ward=(Wards)wardList.get(j);
          String cbo_code=DataMgtUtility.getPropertyValue(ward.getCbo_code());
          String ward_code=DataMgtUtility.getPropertyValue(ward.getWard_code());
          String ward_name=DataMgtUtility.getPropertyValue(ward.getWard_name());

          String[] fieldValues={cbo_code,ward_code,ward_name};
          hd.startElement("","","datavalue",atts);
            for(int i=0;i<columnNames.length;i++)
            {
                hd.startElement("","",columnNames[i],atts);
                hd.characters(fieldValues[i].toCharArray(),0,fieldValues[i].length());
                hd.endElement("","",columnNames[i]);
            }
          hd.startElement("","","datavalue",atts);
          counter++;
          if(counter==5000 || ((counter+(count*5000))==wardList.size()-1))
          {
              hd.endElement("","","datavaluset");
              hd.endDocument();
              xmlString = sw.toString();
              fileName="Wards.xml";
              if(count>0)
              fileName="Wards"+count+".xml";
              File file = new File(getFilePath()+fileName);
              DataMgtUtility.writeXmlFile(xmlString,file);
              count++;
              counter=0;
          } 
	}
      }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
   }
   public List importWardRecords()
   {
        List list=new ArrayList();
        String filePath=getFilePath();
        appUtil.createDirectories(filePath);
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            File file=null;
            Document doc = null;
            String fileName=filePath+"Wards.xml";
            //System.err.println("fileName is "+fileName);
                file=new File(fileName);
                System.err.println("fileName in importTrainingData() is "+fileName);
                if(!file.exists())
                {
                    return null;
                }
                System.err.println("fileName 2 in importTrainingData() is "+fileName);
                WardDao wdao=new WardDaoImpl();
                doc = docBuilder.parse (file);
                // normalize text representation
                doc.getDocumentElement().normalize();
                NodeList listOfObjects = doc.getElementsByTagName("datavalue");
                String recordId=null;
                Wards ward=null;
                for(int s=0; s<listOfObjects.getLength() ; s++)
                {
                    ward=new Wards();
                    String wardCode=null;
                    Node firstPersonNode = listOfObjects.item(s);
                    //System.err.println("firstPersonNode.getNodeType() is "+firstPersonNode.getNodeType()+" Node.ELEMENT_NODE = "+Node.ELEMENT_NODE);
                    if(firstPersonNode.getNodeType() == Node.ELEMENT_NODE)
                     {
                        wardCode=XmlProcessor.getNodeValue("cbo_code",s,listOfObjects);
                        ward.setCbo_code(wardCode);
                        ward.setWard_code(XmlProcessor.getNodeValue("ward_code",s,listOfObjects));
                        ward.setWard_name(XmlProcessor.getNodeValue("ward_name",s,listOfObjects));
                        if(wardCode !=null && !wardCode.equalsIgnoreCase("none"))
                        {
                            System.err.println("ward.getWard_name() is "+ward.getWard_name());
                            if(wdao.getWard(wardCode) !=null)
                            wdao.updateWard(ward);
                            else
                            wdao.saveWard(ward);
                        }
                        list.add(ward);
                     }
                }
        }
        catch (SAXParseException err)
        {
                err.printStackTrace();
        }
        catch (SAXException e)
        {
                Exception x = e.getException ();
                ((x == null) ? e : x).printStackTrace ();
        }
        catch (Exception ex)
        {
                ex.printStackTrace ();
        }
    return list;
}
   public String getFilePath()
   {
       return appUtil.getExportFilePath()+"/Wards/";
   }
}
