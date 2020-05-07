import com.rabbitmq.client.{CancelCallback, ConnectionFactory, DeliverCallback, Delivery}

object Consumer {

  private val QUEUE_NAME = "hello"

  def main(argv: Array[String]) {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")

    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare(QUEUE_NAME, false, false, false, null)
    println(" [*] Waiting for messages. To exit press CTRL+C")

    val deliverCallback = new ResponseCalllBack
    val cancelCallback = new Cancle
    channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback)

  }
}

class ResponseCalllBack extends DeliverCallback{
  override def handle(consumerTag: String, message: Delivery): Unit = {
    val msg= new String(message.getBody, "UTF-8")
    System.out.println(" [x] Received '" + msg + "'")

  }
}
class Cancle extends CancelCallback{
  override def handle(consumerTag: String): Unit = {
  }
}