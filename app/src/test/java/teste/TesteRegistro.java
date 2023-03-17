package teste;

import dominio.Usuario;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class TesteRegistro extends TesteBase {

    private static final String REGISTRA_USUARIO_ENDPOINT = "/register";
    private static final String LOGIN_USUARIO_ENDPOINT = "/login";

    @BeforeClass
    public static void setupRegistro() {
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .build();
    }

    @Test
    public void testeNaoEfetuaRegistroQuandoSenhaEstaFaltando() {
        Usuario usuario = new Usuario();
        usuario.setEmail("sydney@fife");
        given().
             body(usuario).
        when().
            post(REGISTRA_USUARIO_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_BAD_REQUEST).
            body("error", is("Missing password"));
    }

    @Test
    public void testeNaoEfetuadoLoginQuandoSenhaEstaFaltando() {
        Usuario usuario = new Usuario();
        usuario.setEmail("sydney@fife");
        given().
                body(usuario).
                when().
                post(LOGIN_USUARIO_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("Missing password"));
    }
}
