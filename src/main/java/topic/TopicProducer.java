package topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * description
 *
 * @author fengjirong 2020/05/12 17:05
 */
public class TopicProducer {

	public static void main(String[] args) throws JMSException {
		//1.创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		//2.创建连接
		Connection connection = connectionFactory.createConnection();
		//3.打开连接
		connection.start();
		//4.创建session  参数：1.是否开启事务；2.消息的确认方式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.创建主题
		Topic topic = session.createTopic("topic_002");
		//6.创建生产者
		MessageProducer producer = session.createProducer(topic);
		//7.创建消息
		TextMessage textMessage = session.createTextMessage("这是一条发布/订阅模式的测试文本");
		//8.传递消息
		producer.send(textMessage);
		//9.关闭资源
		connection.close();
	}

}
