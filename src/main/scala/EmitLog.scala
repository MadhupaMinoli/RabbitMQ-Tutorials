import com.rabbitmq.client.ConnectionFactory

object EmitLog {
  private val EXCHANGE_NAME = "logs"

  @throws[Exception]
  def main(argv: Array[String]): Unit = {
    val factory = new ConnectionFactory
    factory.setHost("localhost")

    val connection = factory.newConnection
    val channel = connection.createChannel

    channel.exchangeDeclare(EXCHANGE_NAME, "fanout")
    val message = if (argv.length < 1) "Hello World!" else argv.mkString(" ")
    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"))
    System.out.println(" [x] Sent '" + message + "'")


  }
}
