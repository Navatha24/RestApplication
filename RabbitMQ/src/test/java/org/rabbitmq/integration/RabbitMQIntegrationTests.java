package org.rabbitmq.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RabbitMQIntegrationTests {

	private static final String QUEUE_NAME = "HelloQueue2";
	private static ConnectionFactory factory;
	private static Connection connection;
	private static Channel channel;
	private static DocumentBuilderFactory dbf;
	private static DocumentBuilder db;
	private String sentMessage = "Hello Test2";
	private String receivedMessage;
	ClassLoader classLoader = getClass().getClassLoader();

	@BeforeClass
	public static void setUp() throws IOException, TimeoutException, KeyManagementException, NoSuchAlgorithmException,
			URISyntaxException, ParserConfigurationException {

		dbf = DocumentBuilderFactory.newInstance();
		db = dbf.newDocumentBuilder();

		// Set up connection to RabbitMQ Server
		factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();

		// Create a Channel
		channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

	}

	@Test
	public void testMessagePublishedToQueueIsConsumed() throws IOException, InterruptedException {

		sendMessageToRabbitMQServer(sentMessage);

		channel.basicConsume(QUEUE_NAME, true, newConsumer(channel));

		assertThat(receivedMessage, equalTo(sentMessage));

	}

	@Test
	public void testXmlMessagePublishedToQueueIsConsumed() throws IOException, InterruptedException, SAXException {

		String xmlMessageSend = getStringFromDocument("test.xml");

		sendMessageToRabbitMQServer(xmlMessageSend);

		channel.basicConsume(QUEUE_NAME, true, newConsumer(channel));

		assertThat(receivedMessage, equalTo(xmlMessageSend));

	}

	private void sendMessageToRabbitMQServer(String message) throws IOException {
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
	};

	private DefaultConsumer newConsumer(Channel channel) {

		return new DefaultConsumer(channel) {

			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				receivedMessage = new String(body);
				System.err.println(receivedMessage);
			}
		};

	}

	private String getStringFromDocument(String filename) throws SAXException, IOException {
		try {
			Document doc = db.parse(new File(classLoader.getResource(filename).getFile()));
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			return writer.toString();
		} catch (TransformerException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@AfterClass
	public static void tearDown() throws IOException, TimeoutException {

		// Close channel and connection
		channel.close();
		connection.close();
	}

}
