package queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * description
 *
 * @author fengjirong 2020/05/12 17:43
 */
public class QueueConsumer01 {

	public static void main(String[] args) throws JMSException, IOException {
		//1.创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
		//2.创建连接
		Connection connection = connectionFactory.createConnection();
		//3.打开连接
		connection.start();
		//4.创建session  参数：1.是否开启事务；2.消息的确认方式
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.创建队列
		Queue queue = session.createQueue("queue_001");
		//6.创建消费者
		MessageConsumer consumer = session.createConsumer(queue);
		//7.监听消息
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage)message;
				try {
					System.out.println("接收的消息为："+textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		//8.等待键盘输入(防止效果丢失)
		System.in.read();
		//9.关闭资源
		connection.close();

	}

}
