import com.knoldus.{ClientService, ClientServiceApi}
import org.scalatest.FunSuite

class ClientServiceTest extends FunSuite with ClientServiceApi{

  val clientService = new ClientService

  test("able to add data into elasticsearch node"){
    val result = clientService.add("Zachary","Tong ","Elastic",1000,"2")
    assert(result.isCreated === true)
  }

  test("able to update data on elasticsearch node"){
      val response = clientService.update("2","title","ElasticSearch")
      assert(response.getVersion === 2)
  }

  test("able to index data from json file"){
    val response = clientService.addFromJsonFile("src/test/resources/myTestData.json")
    assert(response.hasFailures === false)
  }

  test("able to delete data from elasticsearch"){
    val result = clientService.delete(1)
    assert(Integer.parseInt(result.getId) === 1)
  }

  test("able to get all records"){
    Thread.sleep(5*1000)
    val result =  clientService.searchAll
    assert(result.getHits.totalHits === 4)
  }

  test("able to search by document id"){
    val result = clientService.search("_id","2")
    assert(result.getHits.getTotalHits === 1)
  }

}
