import com.rabbitmq.client.{CancelCallback, ConnectionFactory, DeliverCallback, Delivery}

object Worker {

  private val TASK_QUEUE_NAME = "task_queue"

  def main(argv: Array[String]) {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")

    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null)
    println(" [*] Waiting for messages. To exit press CTRL+C")

    val deliverCallback = new ResponseCallBack
    val cancelCallback = new Cancle

    channel.basicConsume(TASK_QUEUE_NAME, true, deliverCallback, cancelCallback)
}
  class ResponseCallBack extends DeliverCallback{
    override def handle(consumerTag: String, delivery: Delivery): Unit = {
      val message= new String(delivery.getBody, "UTF-8")
      System.out.println(" [x] Received '" + message + "'")
      try {
        doWork(message)
      } finally {
        println(" [x] Done")
      }

    }


  }

  class Cancle extends CancelCallback{
    override def handle(consumerTag: String): Unit = {
    }
  }

  @throws[InterruptedException]
  private def doWork(task: String): Unit = {
    for (ch <- task.toCharArray) {
      if (ch == '.') Thread.sleep(1000)
    }
  }

}

