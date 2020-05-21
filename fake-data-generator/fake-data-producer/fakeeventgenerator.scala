
package com.kbmendoza.dataproj.retail.kafkaandsparkstreaming

import kafka.producer.ProducerConfig
import java.util.Properties
import kafka.producer.Producer
import scala.util.Random
import kafka.producer.Producer
import kafka.producer.Producer
import kafka.producer.Producer
import kafka.producer.KeyedMessage
import java.util.Date
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import scala.concurrent.Future
import spray.json.DefaultJsonProtocol
import scala.collection.immutable.Seq
import scala.concurrent.ExecutionContext.Implicits.global

object AppEventKafkaProducer extends App{
  
  val events = args(0).toInt
  val topic = args(1)
  val brokers = args(2)
  
  val rnd = new Random()
  val props = new Properties()
  props.put("metadata.broker.list", brokers)
  props.put("serializer.class", "kafka.serializer.StringEncoder")
  props.put("producer.type", "async")
 
  val config = new ProducerConfig(props)
  val producer = new Producer[String, String](config)
  val t = System.currentTimeMillis()
  for (nEvents <- Range(0, events)) {
    val msg = Http().singleRequest(createRequest())
    val data = new KeyedMessage[String, String](topic, "", msg);
    producer.send(data);
  }
  
  private def createRequest(): HttpRequest =
	val endpoint = "http://localhost:3000/random"
    HttpRequest(
      method = HttpMethods.GET,
      uri = endpoint,
    )

  System.out.println("sent per second: " + events * 1000 / (System.currentTimeMillis() - t));
  producer.close();
  
}