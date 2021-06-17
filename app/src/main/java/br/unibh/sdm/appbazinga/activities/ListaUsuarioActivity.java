package br.unibh.sdm.appbazinga.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        criaAcaoCliqueLongo();
    }


    private void criaAcaoCliqueLongo() {
        ListView listView = findViewById(R.id.ListviewListaUsuaeios);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ListaUsuarioActivity","Clicou em clique longo na posicao "+position);
                final Usuario objetoSelecionado = (Usuario) parent.getAdapter().getItem(position);
                Log.i("ListaUsuarioActivity", "Selecionou a usuario "+objetoSelecionado.getUsuario());
                new AlertDialog.Builder(parent.getContext()).setTitle("Removendo Usuario")
                        .setMessage("Tem certeza que quer remover a Usuario "+objetoSelecionado.getUsuario()+"?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeUsuario(objetoSelecionado);
                            }
                        }).setNegativeButton("Não", null).show();
                return true;
            }
        });
    }
     //Verificar se precisa incluir todos os parametros para exclusão
    private void removeUsuario(final Usuario cpf) {
        Call<Boolean> call = null;
        Log.i("ListaUsuarioActivity","Vai remover Usuario "+cpf.getCpf());
        call = this.service.excluiUsuario(cpf.getCpf());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Log.i("ListaUsuarioActivity", "Removeu a Usuario " + cpf.getCpf());
                    Toast.makeText(getApplicationContext(), "Removeu a Usuario " + cpf.getCpf(), Toast.LENGTH_LONG).show();
                    onResume();
                } else {
                    Log.e("ListaUsuarioActivity", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("ListaUsuarioActivity", "Erro: " + t.getMessage());
            }
        });
    }

    private void criAcaoBotaoFlutuante () {
        FloatingActionButton botaoNovo = findViewById (R.id.floatingActionButton2);
        botaoNovo.setOnClickListener (new View.OnClickListener() {
            @Override
            public  void  onClick (View v) {
                Log.i("ListaUsuarioActivity"," Clicou no botão para adicionar Nova Usuario ");
                startActivity (new Intent(ListaUsuarioActivity.this,
                        FormularioUsuarioActivity.class));

            }
        });
    }


    @Override
    protected  void  onResume () {
        super.onResume();
        buscaUsuarios();
    }


    public void buscaUsuarios(){
        Call<List<Usuario>> call = this.service.getUsuario();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if (response.isSuccessful()) {
                    Log.i("ListaUsuarioActivity", "Retornou " + response.body().size() + " Criptomoedas!");
                    ListView listView = findViewById(R.id.ListviewListaUsuaeios);
                    listView.setAdapter(new ListaUsuarioAdapter(context,response.body()));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.i("ListaUsuarioActivity", "Selecionou o objeto de posicao "+position);
                            Usuario objetoSelecionado = (Usuario) parent.getAdapter().getItem(position);
                            Log.i("ListaUsuarioActivity", "Selecionou a usuario "+objetoSelecionado.getUsuario());
                            Intent intent = new Intent(ListaUsuarioActivity.this, FormularioUsuarioActivity.class);
                            intent.putExtra("objeto", objetoSelecionado);
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.e("ListaUsuarioActivity", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): "+ response.message(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Log.e("ListaUsuarioActivity", "Erro: " + t.getMessage());
            }
        });
    }
}
