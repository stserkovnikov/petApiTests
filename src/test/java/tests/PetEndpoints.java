package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Pet;
import org.testng.annotations.BeforeMethod;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PetEndpoints {
    private static final String baseUri = "https://petstore.swagger.io";
    private static final String basePath = "/v2/pet";
    private RequestSpecification rSpec;

    @BeforeMethod
    public void rSpec() {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder
                .setBaseUri(baseUri)
                .setBasePath(basePath)
                .setContentType("application/json");
        rSpec = reqBuilder.build();
    }

    public Response addNewPet(Pet pet) {
        return given()
                .spec(rSpec)
                .body(pet)
                .when()
                .post();
    }

    public Response getPetById(long petId) {
        return given()
                .spec(rSpec)
                .pathParams("id", petId)
                .get("{id}")
                .then()
                .extract()
                .response();
    }

    public Response updatePet(Pet pet) {
        return given()
                .spec(rSpec)
                .body(pet)
                .when()
                .put();
    }

    public Response deletePetById(long petId, String api_key) {
        return given()
                .spec(rSpec)
                .header("api_key", api_key)
                .pathParams("id", petId)
                .delete("{id}")
                .then()
                .extract()
                .response();
    }

    public Response deletePetById(long petId) {
        return given()
                .spec(rSpec)
                .pathParams("id", petId)
                .delete("{id}")
                .then()
                .extract()
                .response();
    }

    public Response findByStatus(Pet.StatusEnum status) {
        return given()
                .spec(rSpec)
                .pathParams("status", status.getValue())
                .get("findByStatus?status={status}")
                .then()
                .extract()
                .response();
    }

    public Response updatePetWithFormData(long petId, String name, Pet.StatusEnum status) {
        String statusValue = null;
        if (status != null) {
            statusValue = status.getValue();
        }
        return given()
                .spec(rSpec)
                .contentType("application/x-www-form-urlencoded")
                .pathParams("id", petId)
                .queryParam("name", name)
                .queryParam("status", statusValue)
                .post("{id}")
                .then()
                .extract()
                .response();
    }

    public Response updatePetImage(long petId, String contentType, File file) {

        return given()
                .spec(rSpec)
                .contentType(contentType)
                .multiPart("file", file)
                .pathParams("id", petId)
                .post("{id}/uploadImage")
                .then()
                .extract()
                .response();

    }

}
