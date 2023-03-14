package teste;

import dominio.Usuario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class RegistroTeste {

    @BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = "https://reqres.in";
        basePath = "/api";
    }

    @Test
    public void testeNaoEfetuaRegistroQuandoSenhaEstaFaltando() {
        Usuario usuario = new Usuario();
        usuario.setEmail("sydney@fife");
        given().
             contentType(ContentType.JSON).
             body(usuario).
        when().
            post("/register").
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST).
            body("error", is("Missing password"));
    }
}
