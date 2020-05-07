import com.rabbitmq.client.ConnectionFactory

object EmitLogDirect {
  private val EXCHANGE_NAME = "direct_logs"

  @throws[Exception]
  def main(argv: Array[String]): Unit = {
    val factory = new ConnectionFactory
    factory.setHost("localhost")

    val connection = factory.newConnection
    val channel = connection.createChannel

    channel.exchangeDeclare(EXCHANGE_NAME, "direct")
    val severity = getSeverity(argv)
    val message = getMessage(argv)
    channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("UTF-8"))
    System.out.println(" [x] Sent '" + severity + "':'" + message + "'")
  }
  private def getSeverity(strings: Array[String]): String = {
    if (strings.length < 1) "info" else strings(0)
  }

  private def getMessage(strings: Array[String]): String = {
    if (strings.length < 2) "Hello World!" else  joinStrings(strings, " ", 1)
  }

  private def joinStrings(strings: Array[String], delimiter: String, startIndex: Int): String = {
    val length = strings.length
    if (length == 0) return ""
    if (length < startIndex) return ""
    val words = new StringBuilder(strings(startIndex))
    for (i <- startIndex + 1 until length) {
      words.append(delimiter).append(strings(i))
    }
    words.toString
  }
}

