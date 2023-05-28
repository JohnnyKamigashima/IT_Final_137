import ENUMS.Status;
import POJO.Category;
import POJO.Pet;
import POJO.Tag;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class CadastroPetTest extends BaseConfig {
    Gson gson = new Gson();
    long petId;
    String nomePet;
    Category categoryPet;
    List<Tag> tagPet = new ArrayList<>();
    Status statusPet;
    List<String> urlPhotosPet = new ArrayList<>();
    Pet pet;
    String jsonPet;
    String apiKey;

    @BeforeEach
    public void setUp() {

        petId = 202305280923L;
        categoryPet = new Category(1, "Gato");
        nomePet = "Bichento";
        tagPet.add(new Tag(1, "Felino"));
        urlPhotosPet.add("https://static.wikia.nocookie.net/harrypotter/images/a/aa/Bichento_FH.png/revision/latest/scale-to-width-down/220?cb=20180831030300&path-prefix=pt-br");
        statusPet = Status.available;
        apiKey = "special-key";

        pet = new Pet(petId, categoryPet, nomePet, urlPhotosPet, tagPet, statusPet);
        jsonPet = gson.toJson(pet);

        Response responsePet =
                given()
                    .header("Content-Type", contentType)
                .when()
                    .get(baseUrl + petEndpoint + "/" + petId);

        if (responsePet.getStatusCode() == 200) {
            given()
                .header("Content-Type", contentType)
                .header("api_key", apiKey)
            .when()
                .delete(baseUrl + petEndpoint + "/" + petId)
            .then()
                .statusCode(200);
            ;
            System.out.println("Deletado com sucesso");
        } else {
            System.out.println("PetId não encontrado");
        }
    }

    @Test
    @Order(1)
    @DisplayName("Cadastro Pet")
     void cadastroPetTest() {

        given()
            .header("Content-Type", contentType)
            .body(jsonPet)
        .when()
            .post(baseUrl + petEndpoint)
        .then()
            .statusCode(200)
            .body("id", equalTo(petId))
            .body("name", equalTo(nomePet))
            .body("status", equalTo(statusPet.name()))
            .body("category.id", equalTo(categoryPet.getId()))
            .body("category.name", equalTo(categoryPet.getName()))
            .body("photoUrls[0]", equalTo(urlPhotosPet.get(0)))
            .body("tags[0].id", equalTo(tagPet.get(0).getId()))
            .body("tags[0].name", equalTo(tagPet.get(0).getName()))
        ;
    }
}
