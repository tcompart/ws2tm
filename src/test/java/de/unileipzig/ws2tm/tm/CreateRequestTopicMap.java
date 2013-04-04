/**
 * me.master.thesis - de.unileipzig.ws2tm.tm
 *
 * === CreateRequestTopicMap.java ====
 *
 */
package de.unileipzig.ws2tm.tm;

import java.io.File;
import java.io.IOException;

import javax.xml.namespace.QName;

import org.junit.BeforeClass;
import org.junit.Test;
import org.tmapi.core.FactoryConfigurationException;
import org.tmapi.core.MalformedIRIException;

import de.topicmapslab.majortom.model.core.IAssociation;
import de.topicmapslab.majortom.model.core.ITopic;
import de.topicmapslab.majortom.model.core.ITopicMap;
import de.unileipzig.ws2tm.WebService2TopicMapFactory;
import de.unileipzig.ws2tm.tm.factory.TopicMapEngine;
import de.unileipzig.ws2tm.tm.util.MyTopicMapSystem;
import de.unileipzig.ws2tm.tm.util.MyTopicMapSystem.IDs;

/**
 * @author Torsten Grigull
 * @version 0.1 (28.05.2011)
 *
 */
public class CreateRequestTopicMap {

	public static String NS = WebService2TopicMapFactory.NS_SOAP2TM;
	public static String AMAZON = "http://webservices.amazon.com/AWSECommerceService/2010-06-01/";
	public static String WSDL = WebService2TopicMapFactory.NS_WSDL;
	public static String XS = WebService2TopicMapFactory.NS_XSD;
	
	
	public static ITopicMap tm = null;
	
	@BeforeClass
	public static void createTopicMap() throws MalformedIRIException, FactoryConfigurationException, IllegalArgumentException {
		tm = (ITopicMap) TopicMapEngine.newInstance().createNewTopicMapInstance(new File("./tmp/request-topicmap.xtm"), NS);
		TopicMapEngine.OVERWRITE = true;		
	}
	
	@Test
	public void createRequest() throws FactoryConfigurationException, IOException {
		MyTopicMapSystem tms = new MyTopicMapSystem(tm);
		ITopic tTreq = (ITopic) tms.createTopic(new QName(NS,"request"), IDs.SubjectIdentifier).getTopic();
		tTreq.createName("request");
		ITopic tilu = (ITopic) tms.createTopic(new QName(AMAZON,"ItemLookup"), IDs.SubjectIdentifier).getTopic();
		tilu.createName("ItemLookup");
		ITopic tTope = (ITopic) tms.createTopic(new QName(WSDL,"Operation"), IDs.SubjectIdentifier).getTopic();
		tTope.createName("Operation");
		ITopic tTpara = (ITopic) tms.createTopic(new QName(NS,"parameter"), IDs.SubjectIdentifier).getTopic();
		tTpara.createName("parameter");
		
		ITopic tdtP1 = (ITopic) tms.createTopic(new QName(AMAZON,"AWSAccessKeyId"), IDs.ItemIdentifier).getTopic();
		tdtP1.addType(tTpara);
		tdtP1.createName("AWSAccessKeyId");
		ITopic tdtP2 = (ITopic) tms.createTopic(new QName(AMAZON,"IdType"), IDs.ItemIdentifier).getTopic();
		tdtP2.addType(tTpara);
		tdtP2.createName("IdType");
		ITopic tdtP3 = (ITopic) tms.createTopic(new QName(AMAZON,"SearchIndex"), IDs.ItemIdentifier).getTopic();
		tdtP3.addType(tTpara);
		tdtP3.createName("SearchIndex");
		ITopic tdtP4 = (ITopic) tms.createTopic(new QName(AMAZON,"ReviewPage"), IDs.ItemIdentifier).getTopic();
		tdtP4.addType(tTpara);
		tdtP4.createName("ReviewPage");
		
		ITopic treq = (ITopic )tms.createTopic(new QName(NS,"requestInstance"), IDs.ItemIdentifier).getTopic();		
		treq.createName("requestInstance");
		treq.addType(tilu);
		treq.addSupertype(tTreq);
		treq.createOccurrence(tdtP1, "MyAccessKey", (ITopic) tms.createTopic(new QName(XS,"string"), IDs.SubjectIdentifier).getTopic());
		treq.createOccurrence(tdtP2, "UPC", (ITopic) tms.createTopic(new QName(XS,"string"), IDs.SubjectIdentifier).getTopic());
		treq.createOccurrence(tdtP3, "Books", (ITopic) tms.createTopic(new QName(XS,"string"), IDs.SubjectIdentifier).getTopic());
		treq.createOccurrence(tdtP4, "2", (ITopic) tms.createTopic(new QName(XS,"positiveInteger"), IDs.SubjectIdentifier).getTopic());
		/*		
		ITopic tTop_fu = (ITopic) tms.createTopic(new QName(NS,"is-using-operation"), IDs.SubjectIdentifier).getTopic();
		tTop_fu.createName("is-using-operation");
		
		IAssociation asc = (IAssociation) tm.createAssociation(tTop_fu);
		asc.createRole(tTope, tilu);
		asc.createRole(tTreq, treq);
*/		
		this.createResponse();
		TopicMapEngine.newInstance().write(tm, TopicMapEngine.XTM_2_0);
	}
	

	public void createResponse() throws FactoryConfigurationException, IOException {
		MyTopicMapSystem tms = new MyTopicMapSystem(tm);
		
		ITopic tdtContent = (ITopic) tms.createTopic(new QName(NS,"content"), IDs.SubjectIdentifier).getTopic();
		ITopic tdtValue = (ITopic) tms.createTopic(new QName(NS,"parametervalue"), IDs.SubjectIdentifier).getTopic();
		ITopic tdtName = (ITopic) tms.createTopic(new QName(NS,"parametername"), IDs.SubjectIdentifier).getTopic();
		
		ITopic tTreq = (ITopic) tms.createTopic(new QName(NS,"request"), IDs.SubjectIdentifier).getTopic();
		tTreq.createName("request");
		ITopic tTres = (ITopic) tms.createTopic(new QName(NS,"response"), IDs.SubjectIdentifier).getTopic();
		tTres.createName("response");
		
		ITopic tilur = (ITopic) tms.createTopic(new QName(AMAZON,"ItemLookupResponse"), IDs.SubjectIdentifier).getTopic();
		tilur.createName("ItemLookupResponse");
		
		ITopic treq = (ITopic )tms.createTopic(new QName(NS,"requestInstance"), IDs.ItemIdentifier).getTopic();		
		treq.createName("requestInstance");
		
		ITopic tres = (ITopic )tms.createTopic(new QName(NS,"responseInstance"), IDs.ItemIdentifier).getTopic();		
		tres.createName("responseInstance");
		tres.addType(tilur);
		tres.addSupertype(tTres);
		
		ITopic aTOperationRequest = (ITopic) tms.createTopic(new QName(AMAZON,"OperationRequest"), IDs.SubjectIdentifier).getTopic();
		aTOperationRequest.createName("OperationRequest");
		ITopic tOperationRequest = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tOperationRequest.addType(aTOperationRequest);
		tOperationRequest.createName("OperationRequestInstance");
		
		ITopic tstring = (ITopic) tms.createTopic(new QName(XS,"string"), IDs.SubjectIdentifier).getTopic();
		ITopic tint = (ITopic) tms.createTopic(new QName(XS,"positiveInteger"), IDs.SubjectIdentifier).getTopic();
		ITopic tfloat = (ITopic) tms.createTopic(new QName(XS,"float"), IDs.SubjectIdentifier).getTopic();
		ITopic tboolean = (ITopic) tms.createTopic(new QName(XS,"boolean"), IDs.SubjectIdentifier).getTopic();
		ITopic tdate = (ITopic) tms.createTopic(new QName(XS,"dateTime"), IDs.SubjectIdentifier).getTopic();
		ITopic tanyuri = (ITopic) tms.createTopic(new QName(XS,"anyURI"), IDs.SubjectIdentifier).getTopic();
		
		ITopic aTHTTPHeaders = (ITopic) tms.createTopic(new QName(AMAZON,"HTTPHeaders"), IDs.SubjectIdentifier).getTopic();
		aTHTTPHeaders.createName("HTTPHeaders");
		ITopic tHTTPHeaders = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tHTTPHeaders.addType(aTHTTPHeaders);
		tHTTPHeaders.createName("HTTPHeadersInstance");
		
		ITopic aTHeader = (ITopic) tms.createTopic(new QName(AMAZON,"Header"), IDs.SubjectIdentifier).getTopic();
		aTHeader.createName("Header");
		ITopic tHeader = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tHeader.addType(aTHeader);
		tHeader.createName("HeaderInstance");
		tHeader.createOccurrence(tdtName, "UserAgent", tstring);
		tHeader.createOccurrence(tdtValue, ": Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.7) Gecko/20091221 Firefox/3.5.7 (.NET CLR 3.5.30729)", tstring);
		
		ITopic aTRequestId = (ITopic) tms.createTopic(new QName(AMAZON,"RequestId"), IDs.SubjectIdentifier).getTopic();
		aTRequestId.createName("RequestId");
		ITopic tRequestId = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tRequestId.addType(aTRequestId);
		tRequestId.createName("RequestIdInstance");
		tRequestId.createOccurrence(tdtContent,"fbbe0968-c6d2-4f01-a356-88e8d524f949", tstring);
		
		ITopic aTArguments = (ITopic) tms.createTopic(new QName(AMAZON,"Arguments"), IDs.SubjectIdentifier).getTopic();
		aTArguments.createName("Arguments");
		ITopic tArguments = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tArguments.addType(aTArguments);
		tArguments.createName("ArgumentsInstance");
		ITopic aTArgument = (ITopic) tms.createTopic(new QName(AMAZON,"Argument"), IDs.SubjectIdentifier).getTopic();
		aTArgument.createName("Argument");
		ITopic tArgument = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tArgument.addType(aTArgument);
		tArgument.createName("ArgumentInstance");
		tArgument.createOccurrence(tdtName, "Condition", tstring);
		tArgument.createOccurrence(tdtValue, "All", tstring);
		ITopic tArgument2 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tArgument2.addType(aTArgument);
		tArgument2.createName("ArgumentInstance");
		tArgument2.createOccurrence(tdtName, "Operation", tstring);
		tArgument2.createOccurrence(tdtValue, "ItemLookup", tstring);
		ITopic tArgument3 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tArgument3.addType(aTArgument);
		tArgument3.createName("ArgumentInstance");
		tArgument3.createOccurrence(tdtName, "Service", tstring);
		tArgument3.createOccurrence(tdtValue, "AWSECommerceService", tstring);
		ITopic tArgument4 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tArgument4.addType(aTArgument);
		tArgument4.createName("ArgumentInstance");
		tArgument4.createOccurrence(tdtName, "Signature", tstring);
		tArgument.createOccurrence(tdtValue, "ihvJ8mjU1zTciLw2+IS/y08Z3eW8+SC2TrZwkuZZ1tk=", tstring);
		ITopic tArgument5 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tArgument5.addType(aTArgument);
		tArgument5.createName("ArgumentInstance");
		tArgument5.createOccurrence(tdtName, "ReviewPage", tstring);
		tArgument5.createOccurrence(tdtValue, "2", tint);
		ITopic tArgument6 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tArgument6.addType(aTArgument);
		tArgument6.createName("ArgumentInstance");
		tArgument6.createOccurrence(tdtName, "ItemId", tstring);
		tArgument6.createOccurrence(tdtValue, "892685001003", tint);
		ITopic tArgument7 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tArgument7.addType(aTArgument);
		tArgument7.createName("ArgumentInstance");
		tArgument7.createOccurrence(tdtName, "IdType", tstring);
		tArgument7.createOccurrence(tdtValue, "UPC", tstring);
		ITopic tArgument8 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tArgument8.addType(aTArgument);
		tArgument8.createName("ArgumentInstance");
		tArgument8.createOccurrence(tdtName, "AWSAccessKeyId", tstring);
		tArgument8.createOccurrence(tdtValue, "AXXXXXXXXXXXXXXXXX", tstring);
		ITopic tArgument9 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tArgument9.addType(aTArgument);
		tArgument9.createName("ArgumentInstance");
		tArgument9.createOccurrence(tdtName, "Timestamp", tstring);
		tArgument9.createOccurrence(tdtValue, "2011-05-29T22:37:27.000Z", tdate);
		ITopic tArgument10 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tArgument10.addType(aTArgument);
		tArgument10.createName("ArgumentInstance");
		tArgument10.createOccurrence(tdtName, "SearchIndex", tstring);
		tArgument10.createOccurrence(tdtValue, "Books", tstring);
		
		ITopic aTRequestProcessingTime = (ITopic) tms.createTopic(new QName(AMAZON,"RequestProcessingTime"), IDs.SubjectIdentifier).getTopic();
		aTRequestProcessingTime.createName("RequestProcessingTime");
		ITopic tProcessingTime = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tProcessingTime.addType(aTRequestProcessingTime);
		tProcessingTime.createName("RequestProcessingTimeInstance");
		tProcessingTime.createOccurrence(tdtContent, "0.0199830000000000", tfloat);
		
		ITopic aTItems = (ITopic) tms.createTopic(new QName(AMAZON,"Items"), IDs.SubjectIdentifier).getTopic();
		aTItems.createName("Items");
		ITopic tItems = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tItems.addType(aTItems);
		tItems.createName("ItemsInstance");
		
		ITopic aTRequest = (ITopic) tms.createTopic(new QName(AMAZON,"Request"), IDs.SubjectIdentifier).getTopic();
		aTRequest.createName("Request");
		ITopic tRequest = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tRequest.addType(aTRequest);
		tRequest.createName("RequestInstance");
		
		ITopic aTIsValid = (ITopic) tms.createTopic(new QName(AMAZON,"IsValid"), IDs.SubjectIdentifier).getTopic();
		aTIsValid.createName("IsValid");
		ITopic tIsValid = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tIsValid.addType(aTIsValid);
		tIsValid.createName("IsValidInstance");
		tIsValid.createOccurrence(tdtContent, "True", tboolean);
		
		ITopic aTItemLookupRequest = (ITopic) tms.createTopic(new QName(AMAZON,"ItemLookupRequest"), IDs.SubjectIdentifier).getTopic();
		aTItemLookupRequest.createName("ItemLookupRequest");
		ITopic tItemLookupRequest = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tItemLookupRequest.addType(aTItemLookupRequest);
		tItemLookupRequest.createName("ItemLookupRequestInstance");
		
		ITopic aTCondition = (ITopic) tms.createTopic(new QName(AMAZON,"Condition"), IDs.SubjectIdentifier).getTopic();
		aTCondition.createName("Condition");
		ITopic tCondition = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tCondition.addType(aTCondition);
		tCondition.createName("ConditionInstance");
		tCondition.createOccurrence(tdtContent, "All", tstring);
		
		ITopic aTDeliveryMethod = (ITopic) tms.createTopic(new QName(AMAZON,"DeliveryMethod"), IDs.SubjectIdentifier).getTopic();
		aTDeliveryMethod.createName("DeliveryMethod");
		ITopic tDeliveryMethod = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tDeliveryMethod.addType(aTDeliveryMethod);
		tDeliveryMethod.createName("DeliveryMethodInstance");
		tDeliveryMethod.createOccurrence(tdtContent, "Ship", tstring);
		
		ITopic aTIdType = (ITopic) tms.createTopic(new QName(AMAZON,"IdType"), IDs.SubjectIdentifier).getTopic();
		aTIdType.createName("IdType");
		ITopic tIdType = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tIdType.addType(aTIdType);
		tIdType.createName("IdTypeInstance");
		tIdType.createOccurrence(tdtContent, "UPC", tstring);
		
		ITopic aTMerchantId = (ITopic) tms.createTopic(new QName(AMAZON,"MerchantId"), IDs.SubjectIdentifier).getTopic();
		aTMerchantId.createName("MerchantId");
		ITopic tMerchantId = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tMerchantId.addType(aTMerchantId);
		tMerchantId.createName("MerchantIdInstance");
		tMerchantId.createOccurrence(tdtContent, "Amazon", tstring);
		
		ITopic aTOfferPage = (ITopic) tms.createTopic(new QName(AMAZON,"OfferPage"), IDs.SubjectIdentifier).getTopic();
		aTOfferPage.createName("OfferPage");
		ITopic tOfferPage = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tOfferPage.addType(aTOfferPage);
		tOfferPage.createName("OfferPageInstance");
		tOfferPage.createOccurrence(tdtContent, "1", tint);
		
		ITopic aTItemId = (ITopic) tms.createTopic(new QName(AMAZON,"ItemId"), IDs.SubjectIdentifier).getTopic();
		aTItemId.createName("ItemId");
		ITopic tItemId = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tItemId.addType(aTItemId);
		tItemId.createName("ItemIdInstance");
		tItemId.createOccurrence(tdtContent, "892685001003", tint);
		
		ITopic aTResponseGroup = (ITopic) tms.createTopic(new QName(AMAZON,"ResponseGroup"), IDs.SubjectIdentifier).getTopic();
		aTResponseGroup.createName("ResponseGroup");
		ITopic tResponseGroup = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tResponseGroup.addType(aTResponseGroup);
		tResponseGroup.createName("ResponseGroupInstance");
		tResponseGroup.createOccurrence(tdtContent, "Small", tstring);
		
		ITopic aTReviewPage = (ITopic) tms.createTopic(new QName(AMAZON,"ReviewPage"), IDs.SubjectIdentifier).getTopic();
		aTReviewPage.createName("ReviewPage");
		ITopic tReviewPage = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tReviewPage.addType(aTReviewPage);
		tReviewPage.createName("ReviewPageInstance");
		tReviewPage.createOccurrence(tdtContent, "2", tint);
		
		ITopic aTSearchIndex = (ITopic) tms.createTopic(new QName(AMAZON,"SearchIndex"), IDs.SubjectIdentifier).getTopic();
		aTSearchIndex.createName("SearchIndex");
		ITopic tSearchIndex = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tSearchIndex.addType(aTSearchIndex);
		tSearchIndex.createName("SearchIndexInstance");
		tSearchIndex.createOccurrence(tdtContent, "Books", tstring);
		
		ITopic aTItem = (ITopic) tms.createTopic(new QName(AMAZON,"Item"), IDs.SubjectIdentifier).getTopic();
		aTItem.createName("Item");
		ITopic tItem = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tItem.addType(aTItem);
		tItem.createName("ItemInstance");
		
		ITopic aTASIN = (ITopic) tms.createTopic(new QName(AMAZON,"ASIN"), IDs.SubjectIdentifier).getTopic();
		aTASIN.createName("ASIN");
		ITopic tASIN = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tASIN.addType(aTASIN);
		tASIN.createName("ASIN-Instance");
		tASIN.createOccurrence(tdtContent, "B000FI73MA", tstring);

		ITopic aTDetailPageURL = (ITopic) tms.createTopic(new QName(AMAZON,"DetailPageURL"), IDs.SubjectIdentifier).getTopic();
		aTDetailPageURL.createName("DetailPageURL");
		ITopic tDetailPageURL = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tDetailPageURL.addType(aTDetailPageURL);
		tDetailPageURL.createName("DetailPageURL-Instance");
		tDetailPageURL.createOccurrence(tdtContent, "http://www.amazon.com/Kindle-Amazons-Original-Wireless-generation/dp/B000FI73MA%3FSubscriptionId%3DAKIAJDZUI2DX7KCL46AA%26tag%3Dws%26linkCode%3Dxm2%26camp%3D2025%26creative%3D165953%26creativeASIN%3DB000FI73MA", tanyuri);

		ITopic aTItemAttributes = (ITopic) tms.createTopic(new QName(AMAZON,"ItemAttributes"), IDs.SubjectIdentifier).getTopic();
		aTItemAttributes.createName("ItemAttributes");
		ITopic tItemAttributes = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tItemAttributes.addType(aTItemAttributes);
		tItemAttributes.createName("ItemAttributesInstance");
		
		ITopic aTManufacturer = (ITopic) tms.createTopic(new QName(AMAZON,"Manufacturer"), IDs.SubjectIdentifier).getTopic();
		aTManufacturer.createName("Manufacturer");
		ITopic tManufacturer = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tManufacturer.addType(aTManufacturer);
		tManufacturer.createName("Manufacturer-Instance");
		tManufacturer.createOccurrence(tdtContent, "Amazon.com", tstring);
		
		ITopic aTProductGroup = (ITopic) tms.createTopic(new QName(AMAZON,"ProductGroup"), IDs.SubjectIdentifier).getTopic();
		aTProductGroup.createName("ProductGroup");
		ITopic tProductGroup = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tProductGroup.addType(aTProductGroup);
		tProductGroup.createName("ProductGroup-Instance");
		tProductGroup.createOccurrence(tdtContent, "Amazon Devices", tstring);
		
		ITopic aTTitle = (ITopic) tms.createTopic(new QName(AMAZON,"Title"), IDs.SubjectIdentifier).getTopic();
		aTTitle.createName("Title");
		ITopic tTitle = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tTitle.addType(aTTitle);
		tTitle.createName("Title-Instance");
		tTitle.createOccurrence(tdtContent, "Kindle: Amazon's Original Wireless Reading Device (1st generation)", tstring);
		
		ITopic tItem2 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tItem2.addType(aTItem);
		tItem2.createName("ItemInstance");
		
		ITopic tASIN2 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tASIN2.addType(aTASIN);
		tASIN2.createName("ASIN-Instance");
		tASIN2.createOccurrence(tdtContent, "B002NI4LRA", tstring);

		ITopic tDetailPageURL2 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tDetailPageURL2.addType(aTDetailPageURL);
		tDetailPageURL2.createName("DetailPageURL-Instance");
		tDetailPageURL2.createOccurrence(tdtContent, "http://www.amazon.com/Tunnel-Black-Rhinestone-Plugs-Screw/dp/B002NI4LRA%3FSubscriptionId%3DAKIAJDZUI2DX7KCL46AA%26tag%3Dws%26linkCode%3Dxm2%26camp%3D2025%26creative%3D165953%26creativeASIN%3DB002NI4LRA", tanyuri);

		ITopic tItemAttributes2 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tItemAttributes2.addType(aTItemAttributes);
		tItemAttributes2.createName("ItemAttributesInstance");
		
		ITopic tManufacturer2 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tManufacturer2.addType(aTManufacturer);
		tManufacturer2.createName("Manufacturer-Instance");
		tManufacturer2.createOccurrence(tdtContent, "Royale Titanium", tstring);
		
		ITopic tProductGroup2 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tProductGroup2.addType(aTProductGroup);
		tProductGroup2.createName("ProductGroup-Instance");
		tProductGroup2.createOccurrence(tdtContent, "Home", tstring);
		
		ITopic tTitle2 = (ITopic) tms.createTopic((String) null, IDs.ItemIdentifier).getTopic();
		tTitle2.addType(aTTitle);
		tTitle2.createName("Title-Instance");
		tTitle2.createOccurrence(tdtContent, "0G Tunnel Black Rhinestone Plug Plugs Screw Fit Flesh CZ Gauge", tstring);
		
		ITopic aThasSubelement = (ITopic) tms.createTopic(new QName(NS,"has-subelement"), IDs.SubjectIdentifier).getTopic();
		aThasSubelement.createName("has-subelement");
		ITopic aTrequestresponse = (ITopic) tms.createTopic(new QName(NS,"is-request-response"), IDs.SubjectIdentifier).getTopic();
		aTrequestresponse.createName("is-request-response");

		ITopic rTchild = (ITopic) tms.createTopic(new QName(NS,"child"), IDs.SubjectIdentifier).getTopic();
		rTchild.createName("child");
		ITopic rTfather = (ITopic) tms.createTopic(new QName(NS,"father"), IDs.SubjectIdentifier).getTopic();
		rTfather.createName("father");

		IAssociation reqres = (IAssociation) tm.createAssociation(aTrequestresponse);
		reqres.createRole(tTreq, treq);
		reqres.createRole(tTres, tres);
		
		IAssociation a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tManufacturer2);
		a.createRole(rTfather, tItemAttributes2);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tManufacturer);
		a.createRole(rTfather, tItemAttributes);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tProductGroup2);
		a.createRole(rTfather, tItemAttributes2);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tProductGroup);
		a.createRole(rTfather, tItemAttributes);

		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tTitle2);
		a.createRole(rTfather, tItemAttributes2);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tTitle);
		a.createRole(rTfather, tItemAttributes);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tItemAttributes2);
		a.createRole(rTfather, tItem2);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tItemAttributes);
		a.createRole(rTfather, tItem);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tDetailPageURL2);
		a.createRole(rTfather, tItem2);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tDetailPageURL);
		a.createRole(rTfather, tItem);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tASIN2);
		a.createRole(rTfather, tItem2);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tASIN);
		a.createRole(rTfather, tItem);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tSearchIndex);
		a.createRole(rTfather, tItemLookupRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tReviewPage);
		a.createRole(rTfather, tItemLookupRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tResponseGroup);
		a.createRole(rTfather, tItemLookupRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tOfferPage);
		a.createRole(rTfather, tItemLookupRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tMerchantId);
		a.createRole(rTfather, tItemLookupRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tIdType);
		a.createRole(rTfather, tItemLookupRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tDeliveryMethod);
		a.createRole(rTfather, tItemLookupRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tCondition);
		a.createRole(rTfather, tItemLookupRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tItemLookupRequest);
		a.createRole(rTfather, tRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tIsValid);
		a.createRole(rTfather, tRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tItem2);
		a.createRole(rTfather, tItems);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tItem);
		a.createRole(rTfather, tItems);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tRequest);
		a.createRole(rTfather, tItems);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tProcessingTime);
		a.createRole(rTfather, tOperationRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tArguments);
		a.createRole(rTfather, tOperationRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tRequestId);
		a.createRole(rTfather, tOperationRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tHTTPHeaders);
		a.createRole(rTfather, tOperationRequest);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tArgument);
		a.createRole(rTchild, tArgument2);
		a.createRole(rTchild, tArgument3);
		a.createRole(rTchild, tArgument4);
		a.createRole(rTchild, tArgument5);
		a.createRole(rTchild, tArgument6);
		a.createRole(rTchild, tArgument7);
		a.createRole(rTchild, tArgument8);
		a.createRole(rTchild, tArgument9);
		a.createRole(rTchild, tArgument10);
		a.createRole(rTfather, tArguments);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tHeader);
		a.createRole(rTfather, tHTTPHeaders);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tOperationRequest);
		a.createRole(rTfather, tres);
		
		a = (IAssociation) tm.createAssociation(aThasSubelement);
		a.createRole(rTchild, tItems);
		a.createRole(rTfather, tres);
				
//		ITopic t = (ITopic) tms.createTopic(new QName(AMAZON,""), IDs.SubjectIdentifier).getTopic();
		TopicMapEngine.newInstance().write(tm, TopicMapEngine.XTM_2_0);
	}
	
}
