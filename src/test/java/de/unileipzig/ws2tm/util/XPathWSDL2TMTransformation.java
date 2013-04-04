/**
 * me.master.thesis - de.unileipzig.ws2tm.util
 *
 * === XPathWSDL2TMTransformation.java ====
 *
 */
package de.unileipzig.ws2tm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import javax.xml.xpath.XPathVariableResolver;

import org.junit.Test;
import org.tmapi.core.TopicMap;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import de.unileipzig.ws2tm.WebService2TopicMapFactory;

/**
 * @author Torsten Grigull
 * @version 0.1 (06.06.2011)
 *
 */
public class XPathWSDL2TMTransformation {

	@Test
	public void tryTransformation() throws XPathExpressionException, FileNotFoundException, InterruptedException {
		XPath xp = XPathFactory.newInstance().newXPath();
		
		NodeList n = (NodeList) xp.evaluate("//*", new InputSource(new FileInputStream(new File("wsdl/AWSECommerceService.wsdl"))), XPathConstants.NODESET);
		System.out.println("Received node list has "+n.getLength()+" nodes.");
		for (int i=0; i < n.getLength(); i++) {
			Node node = n.item(i);
			System.out.println(node.getNamespaceURI()+ ": "+node.getLocalName());
			if (node.hasAttributes()) {
				NamedNodeMap atts = node.getAttributes();
				for (int j=0; j < atts.getLength(); j++) {
					if (atts.item(j).getNamespaceURI() != null) {
						System.out.println("\t"+atts.item(j).getNamespaceURI()+atts.item(j).getNodeName() + ": "+atts.item(j).getNodeValue());
					} else {
						System.out.println("\t"+atts.item(j).getNodeName() + ": "+atts.item(j).getNodeValue());						
					}
				}
			}
			Thread.sleep(20);
		}
		
	}
	
}
