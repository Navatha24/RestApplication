package org.send;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

  private final static String QUEUE_NAME = "hello1";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder(); 
    Document doc = db.parse(new File("/Users/navatha/git/RestApplication/RabbitMQ/src/main/java/org/send/test.xml"));
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String message = getStringFromDocument(doc);
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }
  
  public static String getStringFromDocument(Document doc)
  {
      try
      {
         DOMSource domSource = new DOMSource(doc);
         StringWriter writer = new StringWriter();
         StreamResult result = new StreamResult(writer);
         TransformerFactory tf = TransformerFactory.newInstance();
         Transformer transformer = tf.newTransformer();
         transformer.transform(domSource, result);
         return writer.toString();
      }
      catch(TransformerException ex)
      {
         ex.printStackTrace();
         return null;
      }
  } 
}
