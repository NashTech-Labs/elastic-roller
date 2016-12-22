package com.knoldus


import org.elasticsearch.action.bulk.BulkRequestBuilder
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.action.update.UpdateResponse
import org.elasticsearch.client.Client
import org.elasticsearch.index.query.QueryBuilders

import scala.io.Source

/**
 * Created by kunal on 18/3/16.
 */
trait ClientServiceApi {
  val client: Client = EsClient.client

  def add(firstName: String,
      lastName: String,
      title: String,
      price: Int,
      id: String): IndexResponse = {
    val jsonString =
      s"""
  {
    "title": "$title",
    "price": $price,
    "author":{
      "first": "$firstName",
      "last": "$lastName"
    }
  }
    """
    client.prepareIndex("library", "books", id).setSource(jsonString).get()
  }

  def update(id: String, field: String, value: Any): UpdateResponse = {
    client.prepareUpdate("library", "books", id).setDoc(field, value).execute().actionGet()
  }


  def addFromJsonFile(source: String) = {
    val fileData = Source.fromFile(source).getLines().toList
    val bulkRequest: BulkRequestBuilder = client.prepareBulk()
    fileData.foreach {
      json => bulkRequest.add(client.prepareIndex("library", "books").setSource(json))
    }
    bulkRequest.execute().actionGet()
  }

  def delete(id: Int) = {
    client.prepareDelete("library", "books", s"$id").get
  }

  def searchAll = {
    client.admin().indices().prepareRefresh("library").get()
    client.prepareSearch("library").execute().actionGet()
  }

  def search(query: String, value: String) = {
    client.admin().indices().prepareRefresh("library").get()
    client.prepareSearch("library").setTypes("books")
      .setQuery(QueryBuilders.termQuery(s"$query", s"$value")).execute().actionGet()
  }

}

class ClientService extends ClientServiceApi




