package br.unibh.sdm.appbazinga.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
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


public class FormularioUsuarioActivity extends AppCompatActivity {

    private UsuarioService service = null;

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



/*
    private boolean validaFormulario(Usuario usuario){
        boolean valido = true;
        EditText usuario = findViewById(R.id.editTextoUsuario);
        EditText cpf = findViewById(R.id.editTextoCPF);
        EditText senha = findViewById(R.id.editTextoSENHA);

        if (usuario.getUsuario() == null || usuario.getUsuario().trim().length() == 0){
            usuario.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            usuario.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (cpf.getCpf() == null || usuario.getCpf().trim().length() == 0){
            cpf.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            cpf.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (senha.getSenha() == null || usuario.getSenha().trim().length() == 0){
            senha.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            senha.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (!valido){
            Log.e("FormularioCripto", "Favor verificar os campos destacados");
            Toast.makeText(getApplicationContext(), "Favor verificar os campos destacados", Toast.LENGTH_LONG).show();
        }
        return valido;
    }

*/
    private void salvaUsuario(final Usuario usuario) {
      UsuarioService service = RestServiceGenerator.createService(UsuarioService.class);
        Call<Usuario> call;
        Log.i("FormularioCripto","Vai criar criptomoeda "+usuario.getUsuario());
        call = service.criaUsuario(usuario);
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
       // Call<Usuario> call = service.criaUsuario(usuario);
      /// Call<Usuario> call;

         Call<Usuario> call = service.atualizaUsuario(usuario.getUsuario(), usuario);
           //call = service.atualizaUsuario(usuario.getUsuario(), usuario);
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