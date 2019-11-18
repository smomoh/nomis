/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fhi.nomis.nomisutils;

import com.fhi.kidmap.business.Wards;
import com.fhi.kidmap.dao.DaoUtil;
import ImportExport.XmlProcessor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
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
 * @author Siaka
 */
public class NomisConfiguration implements Serializable
{
    AppUtility appUtil=new AppUtility();
    
    List tagNames;
    List tagValues;
    public NomisConfiguration()
    {
       tagNames=new ArrayList();
       tagValues=new ArrayList();
       loadTagNamesAndValuesFromConfigurationFile();
    }
    public void loadTagNamesAndValuesFromConfigurationFile()
    {
        XMLFileReader xfr=new XMLFileReader();
        File file=new File(appUtil.getXmlConfigurationFile());
        if(!file.exists())
        return;
        List list=xfr.readXMLFile(appUtil.getXmlConfigurationFile());
        for(int i=0; i<list.size(); i+=2)
        {
            tagNames.add(list.get(i));
            tagValues.add(list.get(i+1));
        }
    }
    public void setTagNames(String tagName)
    {
        tagNames.add(tagName);
    }
    public List getTagNames()
    {
        return tagNames;
    }
    public void setTagValues(String tagValue)
    {
        tagValues.add(tagValue);
    }
    public List getTagValues()
    {
        return tagValues;
    }
    public boolean createXmlConfigurationFile(List tagNames,List tagValues)
    {
        String fileName="";
        try
        {
            appUtil.createDirectories(appUtil.getConfigurationDirectory());
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
        String startDate=appUtil.getCurrentDate();    
          String[] fieldValues={"2.1",startDate};
          hd.startElement("","","NomisInfo",atts);
		for (int i=0;i<tagNames.size();i++)
		{
                    hd.startElement("","",tagNames.get(i).toString(),atts);
                        hd.characters(tagValues.get(i).toString().toCharArray(),0,tagValues.get(i).toString().length());
                    hd.endElement("","",tagNames.get(i).toString());
		}
          hd.endElement("","","NomisInfo");
	
	hd.endDocument();

	String xmlString = sw.toString();
        fileName=appUtil.getXmlConfigurationFile();
        File file = new File(fileName);

        BufferedWriter bw = new BufferedWriter
                      (new OutputStreamWriter(new FileOutputStream(file)));

        bw.write(xmlString);
        bw.flush();
        bw.close();
       }
      catch(Exception ex)
      {
	ex.printStackTrace();
      }
        return false;
    }
    public void addTag(String tagName,String value)
    {

    }
}
