import com.knoldus.ClientService
import org.scalatest.{BeforeAndAfterAll, FunSuite}

class ClientServiceTest extends FunSuite with BeforeAndAfterAll{

  val clientService = new ClientService

  test("able to add data into elasticsearch node"){
    val result = clientService.add("Zachary","Tong ","Elastic",1000,"1")
    assert(result.isCreated)
  }

  test("able to update data on elasticsearch node"){
      val response = clientService.update("1","title","ElasticSearch")
      assert(response.getVersion === 2)
  }

  test("able to index data from json file"){
    val response = clientService.addFromJsonFile("src/test/resources/myTestData.json")
    assert(response.hasFailures)
  }

  test("able to delete data from elasticsearch"){
    clientService.add("test","test ","test",1000,"2")
    val result = clientService.delete(2)
    assert(Integer.parseInt(result.getId) === 2)
  }

  test("able to get all records"){
    val result =  clientService.searchAll
    assert(result.getHits.totalHits === 5)
  }

  test("able to search by document id"){
    val result = clientService.search("price","1000")
    assert(result.getHits.getTotalHits === 2)
  }

}
