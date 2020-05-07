import com.rabbitmq.client.{CancelCallback, ConnectionFactory, DeliverCallback, Delivery}

object ReceiveLogsDirect {
  private val EXCHANGE_NAME = "direct_logs"

  def main(argv: Array[String]) {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.exchangeDeclare(EXCHANGE_NAME, "direct")
    val queueName = channel.queueDeclare().getQueue
    if (argv.length < 1) {
      System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]")
      System.exit(1)
    }
    for (severity <- argv) {
      channel.queueBind(queueName, EXCHANGE_NAME, severity)
    }
    println(" [*] Waiting for messages. To exit press CTRL+C")

    val deliverCallback = new ResponseCallBack
    val cancelCallback = new Cancle

    channel.basicConsume(queueName, true, deliverCallback, cancelCallback)
  }

  class ResponseCallBack extends DeliverCallback{
    override def handle(consumerTag: String, delivery: Delivery): Unit = {
      val message= new String(delivery.getBody, "UTF-8")
      println(" [x] Received '" +
        delivery.getEnvelope.getRoutingKey + "':'" + message + "'")
    }
  }
  class Cancle extends CancelCallback{
    override def handle(consumerTag: String): Unit = {
    }
  }
}
