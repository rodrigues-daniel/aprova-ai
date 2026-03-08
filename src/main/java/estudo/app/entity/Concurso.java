package estudo.app.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("concursos")
public record Concurso(
        @Id Long id,
        String nome,
        String orgao,
        Integer ano,
        @Column("banca_id") Long bancaId
) {
    public static Concurso novo(String nome, String orgao, Integer ano, Long bancaId) {
        return new Concurso(null, nome, orgao, ano, bancaId);
    }
}
