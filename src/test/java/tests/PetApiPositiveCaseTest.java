package tests;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import model.Category;
import model.Pet;
import model.Tag;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import tools.RandomTool;

import java.io.File;
import java.util.*;


public class PetApiPositiveCaseTest extends PetEndpoints {

    @Test(description = "Add a new pet to the store")
    public void addNewPetTest() {
        Pet pet = generatePet();
        Response response = addNewPet(pet);
        response.then().statusCode(HttpStatus.SC_OK);
    }

    @Test(description = "Find pet by ID")
    public void getPetByIdTest() {
        Pet pet = generatePet();
        addNewPet(pet);

        Response getPetResponse = getPetById(pet.getId());
        getPetResponse.then().statusCode(HttpStatus.SC_OK);

        Pet responsePet = getPetResponse.getBody().as(Pet.class);
        Assert.assertEquals(pet, responsePet);

        deletePetById(pet.getId());
    }

    @Test(description = "Deletes a pet")
    public void deletePetByIdTest() {
        String api_key = "some api key";
        Pet pet = generatePet();
        addNewPet(pet);

        Response deleteResponse = deletePetById(pet.getId(), api_key);
        deleteResponse.then().statusCode(HttpStatus.SC_OK);

        Response getPetResponse = getPetById(pet.getId());
        getPetResponse.then().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(description = "Update an existing pet")
    public void updateExistingPetTest() {
        Pet pet = generatePet();
        addNewPet(pet);

        String newName = pet.getName() + " changed";
        pet.setName(newName);

        Response updatePetResponse = updatePet(pet);
        updatePetResponse.then().statusCode(HttpStatus.SC_OK);
        Pet responsePet = updatePetResponse.getBody().as(Pet.class);
        Assert.assertEquals(newName, responsePet.getName());

        deletePetById(pet.getId());
    }

    @Test(description = "Finds Pets by status")
    public void getPetByStatusTest() {
        Response response = findByStatus(Pet.StatusEnum.AVAILABLE);
        response.then().statusCode(HttpStatus.SC_OK);
    }

    @Test(description = "Updates a pet in the store with form data")
    public void updatePetWithFormDataTest() {
        Pet pet = generatePet();
        addNewPet(pet);

        String newName = pet.getName() + " updated";
        Response updatedResponse = updatePetWithFormData(pet.getId(), newName, Pet.StatusEnum.SOLD);
        updatedResponse.then().statusCode(HttpStatus.SC_OK);
        deletePetById(pet.getId());
    }

    @Test(description = "uploads an image")
    public void updatePetImageTest() {
        String mimetype = "multipart/form-data";
        File file = new File("src/test/resources/Gatto_europeo4.jpg");
        Response response = updatePetImage(RandomTool.getRandomId(), mimetype, file);
        response.then().statusCode(HttpStatus.SC_OK);
    }

    private Pet generatePet() {
        Faker faker = new Faker();

        Category category = new Category()
                .setId(RandomTool.getRandomId())
                .setName(faker.cat().breed());

        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag(RandomTool.getRandomId(), "some tag"));
        tagList.add(new Tag(RandomTool.getRandomId(), "some  another tag"));

        Pet pet = new Pet()
                .setId(RandomTool.getRandomId())
                .setCategory(category)
                .setName(faker.cat().name())
                .setPhotoUrls(Arrays.asList(faker.internet().url()))
                .setTags(tagList)
                .setStatus(Pet.StatusEnum.AVAILABLE);
        return pet;
    }
}
