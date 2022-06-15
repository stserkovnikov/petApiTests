package tests;

import io.restassured.response.Response;
import model.Pet;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

public class PetApiNegativeCaseTest extends PetEndpoints {

    @Test
    public void getPetByIdNegativeTest() {
        long petId = -9999;
        Response getPetResponse = getPetById(petId);
        getPetResponse.then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void updateExistedPetNegativeTest() {
        long petId = -9999;
        String newName = "new name";
        Response updatedResponse = updatePetWithFormData(petId, newName, Pet.StatusEnum.SOLD);
        updatedResponse.then().statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
