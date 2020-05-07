import com.rabbitmq.client.ConnectionFactory

object NewTask {

  private val TASK_QUEUE_NAME = "task_queue"

  def main(argv: Array[String]) {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null)



    val message = if (argv.length < 1) "Hello World!" else argv.mkString(" ")
    channel.basicPublish("", TASK_QUEUE_NAME,null, message.getBytes())
    println(" [x] Sent '" + message + "'")
    channel.close()
    connection.close()
  }
}
