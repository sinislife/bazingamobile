package br.unibh.sdm.appbazinga.api;

import java.util.List;
import br.unibh.sdm.appbazinga.entidades.Usuario;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface UsuarioService {

    @Headers({
            "Accept: application/json",
            "User-Agent: AppBazinga"
    })
    @GET("usuario")
    public Call<List<Usuario>> getUsuario();

}


