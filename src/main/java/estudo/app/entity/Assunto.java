package estudo.app.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("assuntos")
public record Assunto(
        @Id Long id,
        String nome,
        String descricao
) {
    public static Assunto novo(String nome, String descricao) {
        return new Assunto(null, nome, descricao);
    }
}