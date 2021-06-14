package br.unibh.sdm.appbazinga.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import br.unibh.sdm.appbazinga.R;
import br.unibh.sdm.appbazinga.api.RestServiceGenerator;
import br.unibh.sdm.appbazinga.api.UsuarioService;
import br.unibh.sdm.appbazinga.entidades.Usuario;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private UsuarioService service = null;
    final private MainActivity mainActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lista de Usuarios");
        setContentView(R.layout.activity_main);
        service = RestServiceGenerator.createService(UsuarioService.class);
        buscaUsuarios();
        criAcaoBotaoFlutuante();
    }


    private void criAcaoBotaoFlutuante () {
        FloatingActionButton botaoNovo = findViewById (R.id.floatingActionButton2);
        botaoNovo.setOnClickListener (new View.OnClickListener() {
            @Override
            public  void  onClick (View v) {
                Log.i("MainActivity"," Clicou no botão para adicionar Nova Criptomoeda ");
                startActivity (new Intent(MainActivity.this,
                        FomularioUsuario.class));

            }
        });
    }


    @Override
    protected  void  onResume () {
        super.onResume();
        buscaUsuarios();
    }


    public void buscaUsuarios(){
        UsuarioService service = RestServiceGenerator.createService(UsuarioService.class);
        Call<List<Usuario>> call = service.getUsuario();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    Log.i("UsuarioDAO", "Retornou " + response.body().size() + " Usuario!");
                    List<String> lista2 = new ArrayList<String>();
                    for (Usuario item : response.body()) {
                        lista2.add(item.getUsuario());
                    }
                    Log.i("MainActivity", lista2.toArray().toString());
                    ListView listView = findViewById(R.id.ListviewListaUsuaeios);
                    listView.setAdapter(new ArrayAdapter<String>(mainActivity,
                            android.R.layout.simple_list_item_1,
                            lista2));
                } else {
                    Log.e("UsuarioDAO", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("Error", "" + t.getMessage());
            }
        });
    }
}