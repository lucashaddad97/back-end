package org.aula.model;

import jakarta.persistence.*;

import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jogador")
public class Jogador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToMany(mappedBy = "jogador")
    private List<Partida> partidas;

    @Column(name = "nome")
    private String nome;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "jogador")
    private List<Inventario> inventarios;

    public List<Inventario> getInventarios() {
        return inventarios;
    }

    public void setInventarios(List<Inventario> inventarios) {
        this.inventarios = inventarios;
    }

    public void addPartida(Partida partida){
        getPartidas().add(partida);
        partida.setJogador(this);

    }
    public void removerPartida(Partida partida){
        getPartidas().remove(partida);
        if (partida!=null && partida.getJogador()!=null){
            partida.setJogador(null);
        }
    }



    public List<Partida> getPartidas() {
        if(this.partidas==null)this.partidas=new ArrayList<>();
        return partidas;
    }

    public void addInventario(Inventario inventario) {
        getInventarios().add(inventario);
        inventario.setJogador(this);
    }

    public void removerInventario(Inventario inventario) {
        getInventarios().remove(inventario);
        if (inventario != null && inventario.getJogador() != null) {
            inventario.setJogador(null);
        }
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Jogador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
