package com.eduardocruzdev.forolastversion.domain.topico;

import com.eduardocruzdev.forolastversion.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name= "topico")
@Table(name="topicos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String titulo;

    @Column(length=500, nullable=false)
    private String contenido;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    private boolean activo;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;

public Topico(DatosRegistroTopico datosRegistroTopico){
    this.activo = true;
    this.fechaCreacion = LocalDateTime.now();
    this.titulo = datosRegistroTopico.titulo();
    this.contenido = datosRegistroTopico.contenido();
    this.usuario = datosRegistroTopico.usuario();
}


    public void cerrarTopico() {
        this.activo = false;
    }

    public void actualizarTopico(DatosActualizarTopico datosActualizarTopico) {

        if(datosActualizarTopico.titulo() != null){
            this.titulo = datosActualizarTopico.titulo();
        }

        if(datosActualizarTopico.contenido() != null){
            this.contenido = datosActualizarTopico.contenido();
        }
        if(datosActualizarTopico.usuario() != null){
            this.usuario= datosActualizarTopico.usuario();
        }

    }
}

