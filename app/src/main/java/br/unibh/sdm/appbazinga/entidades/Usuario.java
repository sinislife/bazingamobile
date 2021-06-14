package br.unibh.sdm.appbazinga.entidades;

import java.util.Date;
import java.util.Objects;

public class Usuario {

    private String cpf;
    private String usuario;
    private String jogos;
    private String descricao;
    private Date dataCriacao;


   public Usuario() {

}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getUsuario() { return usuario; }

    public void setUsuario(String usuario) {this.usuario = usuario; }

    public String getJogos() {
        return jogos;
    }

    public void setJogos(String jogos) {
        this.jogos = jogos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }




    @Override
    public String toString() {
        return "Usuario{" +
                "cpf='" + cpf + '\'' +
                ", usuario='" + usuario + '\'' +
                ", jogos='" + jogos + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario that = (Usuario) o;
        return Objects.equals(getCpf(), that.getCpf()) &&
                Objects.equals(getUsuario(), that.getUsuario()) &&
                Objects.equals(getJogos(), that.getJogos()) &&
                Objects.equals(getDescricao(), that.getDescricao()) &&
                Objects.equals(getDataCriacao(), that.getDataCriacao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCpf(), getUsuario(), getJogos(),getDescricao(), getDataCriacao());
    }


}
