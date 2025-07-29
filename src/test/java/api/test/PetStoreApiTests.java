package api.test;

import api.dto.Order;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.delete;
import static org.assertj.core.api.Assertions.assertThat;

public class PetStoreApiTests {
  private static Long testOrderId;
  private static final Long TEST_PET_ID = 10L;
  @BeforeAll
  static void setup() {
    RestAssured.baseURI = "https://petstore.swagger.io/v2";
    testOrderId = System.currentTimeMillis() % 1000 + 1; // Генерация уникального ID
  }
  @BeforeEach
  void cleanup() {
    // Удаление тестовых данных перед каждым тестом
    delete("/store/order/" + testOrderId);
  }
  @Test
  void getInventory_shouldReturnStatusMap() {
    Response response = given()
        .when()
        .get("/store/inventory")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .extract().response();
    Map<String, Integer> inventory = response.as(Map.class);
    assertThat(inventory)
        .isNotEmpty()
        .hasSizeGreaterThan(3);
  }
  @Test
  void createOrder_shouldReturnCreatedOrder() {
    Order newOrder = new Order();
    newOrder.setId(testOrderId);
    newOrder.setPetId(TEST_PET_ID);
    newOrder.setQuantity(1);
    newOrder.setShipDate(Instant.now().toString()); // Исправленный формат даты
    newOrder.setStatus("placed");
    newOrder.setComplete(true);
    Order createdOrder = given()
        .contentType(ContentType.JSON)
        .body(newOrder)
        .when()
        .post("/store/order")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .extract().as(Order.class);
    assertThat(createdOrder.getId()).isEqualTo(testOrderId);
    assertThat(createdOrder.getPetId()).isEqualTo(TEST_PET_ID);
    assertThat(createdOrder.getStatus()).isEqualTo("placed");
    assertThat(createdOrder.getComplete()).isTrue();
  }
  @Test
  void getOrderById_shouldReturnCreatedOrder() {
    // Сначала создаем заказ
    Order newOrder = createTestOrder();
    Order order = given()
        .pathParam("orderId", testOrderId)
        .when()
        .get("/store/order/{orderId}")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .extract().as(Order.class);
    assertThat(order.getId()).isEqualTo(testOrderId);
    assertThat(order.getPetId()).isEqualTo(TEST_PET_ID);
    assertThat(order.getStatus()).isEqualTo("placed");
  }
  @Test
  void deleteOrder_shouldRemoveOrder() {
    // Сначала создаем заказ
    createTestOrder();
    given()
        .pathParam("orderId", testOrderId)
        .when()
        .delete("/store/order/{orderId}")
        .then()
        .statusCode(200);
    given()
        .pathParam("orderId", testOrderId)
        .when()
        .get("/store/order/{orderId}")
        .then()
        .statusCode(404);
  }
  private Order createTestOrder() {
    Order order = new Order();
    order.setId(testOrderId);
    order.setPetId(TEST_PET_ID);
    order.setQuantity(1);
    order.setShipDate(Instant.now().toString());
    order.setStatus("placed");
    order.setComplete(true);
    return given()
        .contentType(ContentType.JSON)
        .body(order)
        .post("/store/order")
        .as(Order.class);
  }
}
