package br.unibh.sdm.appbazinga.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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



public class ListaUsuarioActivity extends AppCompatActivity {

    private UsuarioService service = null;
    final private ListaUsuarioActivity listaUsuarioActivity = this;
    private final Context context;

    public ListaUsuarioActivity() {
        context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lista de Usuarios");
        setContentView(R.layout.activity_lista_usuario);
        service = RestServiceGenerator.createService(UsuarioService.class);
        buscaUsuarios();
        criAcaoBotaoFlutuante();
    }


    private void criAcaoBotaoFlutuante () {
        FloatingActionButton botaoNovo = findViewById (R.id.floatingActionButton2);
        botaoNovo.setOnClickListener (new View.OnClickListener() {
            @Override
            public  void  onClick (View v) {
                Log.i("ListaUsuarioActivity"," Clicou no botão para adicionar Nova Criptomoeda ");
                startActivity (new Intent(ListaUsuarioActivity.this,
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
            public void onResponse(Call<List<Usuario>> call, final Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    Log.i("UsuarioDAO", "Retornou " + response.body().size() + " Usuario!");
                    List<String> lista2 = new ArrayList<String>();
                    for (Usuario item : response.body()) {
                        lista2.add(item.getUsuario());
                    }
                    Log.i("ListaUsuarioActivity", lista2.toArray().toString());
                    ListView listView = findViewById(R.id.ListviewListaUsuaeios);
                    listView.setAdapter(new ArrayAdapter<String>(listaUsuarioActivity,
                            android.R.layout.simple_list_item_1,
                            lista2));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("ListaUsuarioActivity", "Selecionou o objeto de posicao "+position);
                        Usuario objetoSelecionado = response.body().get(position);
                        Intent intent = new Intent(ListaUsuarioActivity.this, FomularioUsuario.class);
                        intent.putExtra("objeto", objetoSelecionado);
                        startActivity(intent);

                    }
                });

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

