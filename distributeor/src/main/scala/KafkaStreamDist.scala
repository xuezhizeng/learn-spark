import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created by migle on 2016/8/10.
 */
object  KafkaStreamDist {
def main(args:Array[String]): Unit ={
  val sparkConf = new SparkConf().setAppName("KafkaStreamDist").setMaster("local[2]") //.setMaster("spark://vm-centos-00:7077")
  val ssc = new StreamingContext(sparkConf, Seconds(2))
  val topicsSet=Set("topic-1")
  val kafkaParams = Map[String, String]("metadata.broker.list" -> Conf.kafka,"group.id"->"test")

  //DirectStream
  val messages = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topicsSet)
  //messages.map(x=>x._2).map(x=>x.replaceAll(" ",":")).print()
  //messages.print();
  //messages.print();

//  var offsetRanges = Array[OffsetRange]()
//  messages.transform({ rdd =>
//   offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
//   rdd
// }).foreachRDD(rdd=>for (o <- offsetRanges) {
//    println(s"${o.topic} ${o.partition} ${o.fromOffset} ${o.untilOffset}")
//  })

  ssc.start()
  ssc.awaitTermination()
}

}
