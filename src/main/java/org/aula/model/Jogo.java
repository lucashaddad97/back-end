package org.aula.model;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jogo")
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "genero")
    private String genero;

    @OneToMany(mappedBy = "jogo")
    private List<Partida> partidas;

    public List<Partida> getPartidas() {
        if(this.partidas==null)this.partidas=new ArrayList<>();
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }
    public void addPartida(Partida partida){
        getPartidas().add(partida);
        partida.setJogo(this);
    }
    public void removePartida(Partida partida){
        getPartidas().remove(partida);
        if(partida!=null&&partida.getJogo()==this){
            partida.setJogo(null);
        }
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Jogo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", genero='" + genero + '\'' +
                '}';
    }
}
