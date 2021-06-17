package br.unibh.sdm.appbazinga.activities;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import br.unibh.sdm.appbazinga.R;
import br.unibh.sdm.appbazinga.api.RestServiceGenerator;
import br.unibh.sdm.appbazinga.api.UsuarioService;
import br.unibh.sdm.appbazinga.entidades.Usuario;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;
import java.util.Date;

public class FomularioUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        setTitle("Edição de Usuario");
        configuraBotaoSalvar();
        inicializaObjeto();
    }

    private void inicializaObjeto() {
        Intent intent = getIntent();
        if (intent.getSerializableExtra("objeto") != null) {
            Usuario objeto = (Usuario) intent.getSerializableExtra("objeto");
            EditText usuario = findViewById(R.id.editTextoUsuario);
            EditText jogos = findViewById(R.id.editTextoJOGOS);
            EditText cpf = findViewById(R.id.editTextoCPF);
            EditText senha = findViewById(R.id.editTextoSENHA);
            usuario.setText(objeto.getUsuario());
            jogos.setText(objeto.getJogos());
            cpf.setText(objeto.getCpf());
            senha.setText(objeto.getSenha());
            cpf.setEnabled(false);
            Button botaoSalvar = findViewById(R.id.buttonsalvar);
            botaoSalvar.setText("Atualizar");
        }
    }
/*
    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.buttonsalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("FormularioUsuario","Clicou em Salvar");
                Usuario usuario = recuperaInformacoesFormulario();
                salvaUsuario(usuario);
            }
        });
    }
*/
    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.buttonsalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("FormularioCripto","Clicou em Salvar");
                Usuario usuario = recuperaInformacoesFormulario();
                Intent intent = getIntent();
                if (intent.getSerializableExtra("objeto") != null) {
                    Usuario objeto = (Usuario) intent.getSerializableExtra("objeto");
                    usuario.setUsuario(objeto.getUsuario());
                    usuario.setDataCriacao(objeto.getDataCriacao());
                    //if (validaFormulario(usuario)) {
                    atualizaUsuario(usuario);
                   // }
                } else {
                    usuario.setDataCriacao(new Date());

                        salvaUsuario(usuario);
                    }
                }

        });
    }
    private void salvaUsuario(final Usuario usuario) {
        UsuarioService service = RestServiceGenerator.createService(UsuarioService.class);
        Call<Usuario> call = service.criaUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioUsuario", "Salvou a Usuario "+ usuario.getUsuario());
                    Toast.makeText(getApplicationContext(), "Salvou a Usuario "+ usuario.getUsuario(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioUsuario", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("FormularioUsuario", "Erro: " + t.getMessage());
            }
        });
    }

    private void atualizaUsuario(final Usuario usuario) {
        UsuarioService service = RestServiceGenerator.createService(UsuarioService.class);
        Call<Usuario> call = service.criaUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioUsuario", "Atualiza a Usuario "+ usuario.getUsuario());
                    Toast.makeText(getApplicationContext(), "Salvou a Usuario "+ usuario.getUsuario(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioUsuario", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("FormularioUsuario", "Erro: " + t.getMessage());
            }
        });
    }

    @NotNull
    private Usuario recuperaInformacoesFormulario() {
        EditText usuario = findViewById(R.id.editTextoUsuario);
        EditText jogos = findViewById(R.id.editTextoJOGOS);
        EditText cpf = findViewById(R.id.editTextoCPF);
        EditText senha = findViewById(R.id.editTextoSENHA);
        Usuario usuarios = new Usuario();
        usuarios.setUsuario(usuario.getText().toString());
        usuarios.setJogos(jogos.getText().toString());
        usuarios.setCpf(cpf.getText().toString());
        usuarios.setSenha(senha.getText().toString());
        usuarios.setDataCriacao(new Date());
        return usuarios;
    }

}