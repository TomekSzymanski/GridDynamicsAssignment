package gd

import org.scalatest._
import org.scalatest.mockito.MockitoSugar

trait UnitSpec extends FlatSpec with GivenWhenThen with BeforeAndAfterAll with Matchers with MockitoSugar with Inside