package br.unibh.sdm.appbazinga.api;

import java.util.List;
import br.unibh.sdm.appbazinga.entidades.Usuario;
import retrofit2.Call;
import retrofit2.http.*;


public interface UsuarioService {

    @Headers({
            "Accept: application/json",
            "User-Agent: AppBazinga"
    })

    @GET("usuario")
    Call<List<Usuario>> getUsuario();

    @GET("usuario/{id}")
    Call<Usuario> getUsuario(@Path("id") String codigo);

    @POST("usuario")
    Call<Usuario> criaUsuario(@Body Usuario usuario);

    @PUT("usuario/{id}")
    Call<Usuario> atualizaUsuario(@Path("id") String codigo, @Body Usuario usuario);

    @DELETE("usuario/{id}")
    Call<Boolean> excluiUsuario(@Path("id") String codigo);


    Call<Usuario> atualizaUsuario(Usuario usuario);
}


