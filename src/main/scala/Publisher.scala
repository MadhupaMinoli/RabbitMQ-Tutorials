import com.rabbitmq.client.ConnectionFactory

object Publisher {

  def main(args: Array[String]): Unit = {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    var connection = factory.newConnection()
    var channel = connection.createChannel()
    val QUEUE_NAME: String = "hello"
    channel.queueDeclare(QUEUE_NAME, false, false, false, null)

    val message = "Hello World!"
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes)
    System.out.println(" [x] Sent '" + message + "'")
  }

}
