import com.rabbitmq.client.{CancelCallback, ConnectionFactory, DeliverCallback, Delivery}

object ReceiveLogs {

  private val EXCHANGE_NAME = "logs"

  def main(argv: Array[String]) {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.exchangeDeclare(EXCHANGE_NAME, "fanout")
    val queueName = channel.queueDeclare.getQueue

    channel.queueBind(queueName, EXCHANGE_NAME, "")
    println(" [*] Waiting for messages. To exit press CTRL+C")

    val deliverCallback = new ResponseCallBack
    val cancelCallback = new Cancle

    channel.basicConsume(queueName, true, deliverCallback, cancelCallback)
  }


class ResponseCallBack extends DeliverCallback{
  override def handle(consumerTag: String, message: Delivery): Unit = {
    val msg= new String(message.getBody, "UTF-8")
    System.out.println(" [x] Received '" + msg + "'")
  }
}
class Cancle extends CancelCallback{
  override def handle(consumerTag: String): Unit = {
  }
}
}