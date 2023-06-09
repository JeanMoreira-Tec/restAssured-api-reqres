package teste;/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import dominio.Usuario;
import org.apache.http.HttpStatus;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TesteUsuario extends TesteBase {

    private static final String LISTA_USUARIO_ENDPOINT = "/users";
    private static final String CRIAR_USUARIO_ENDPOINT = "/user";
    private static final String MOSTRAR_USUARIO_ENDPOINT = "/users/{userId}";

    @Test
    public void testeMostraPaginaEspecifica() {
        given().
            params("page", "2").
        when().
           get(LISTA_USUARIO_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_OK).
            body("page", is(2)).
            body("data", is(notNullValue()));
    }

    @Test
    public void testeCriarUsuarioComSucesso() {
       Map<String, String> usuario = new HashMap<>();
       usuario.put("name", "morpheus");
       usuario.put("job", "leader");

        given().
            body(usuario).
        when().
            post(CRIAR_USUARIO_ENDPOINT).
        then().
            statusCode(HttpStatus.SC_CREATED).
            body("name", is("morpheus"));
    }

    @Test
    public void testeDaQuantidadeDeItensPorPagina() {
        int paginaEsperada = 2;

        int perPageEsperado = retornaPerPageEsperado(paginaEsperada);

        given().
                params("page", paginaEsperada).
                when().
                get(LISTA_USUARIO_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_OK).
                body(
               "page", is(paginaEsperada),
"data.size", is(perPageEsperado),
                     "data.findAll { it.avatar.startsWith('https://reqres.in') }.size()", is(perPageEsperado)
                );
    }

    @Test
    public void testesMotrausuarioEspecifico() {
        Usuario usuario = given().
        given().
            pathParam("userId", 2).
        when().
            get(MOSTRAR_USUARIO_ENDPOINT).
        then().
           statusCode(HttpStatus.SC_OK).
           extract().
                body().jsonPath().getObject("data", Usuario.class);

        assertThat(usuario.getEmail(), containsString("@reqres.in"));
        assertThat(usuario.getName(), is("Janet"));
        assertThat(usuario.getLastName(), is("Weaver"));

    }

    private static int retornaPerPageEsperado(int page) {
        int perPageEsperado = given().
                param("page", page).
            when().
                get(LISTA_USUARIO_ENDPOINT).
            then().
                statusCode(HttpStatus.SC_OK).
            extract().
                path("per_page");
        return perPageEsperado;
    }
}
