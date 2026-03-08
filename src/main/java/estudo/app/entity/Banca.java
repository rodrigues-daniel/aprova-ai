package estudo.app.entity;

// ========== ENTIDADES (Records + Spring Data JDBC) ==========

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("bancas")
public record Banca(
        @Id Long id,
        String nome,
        String descricao
) {
    // Factory para criação sem id (insert)
    public static Banca novo(String nome, String descricao) {
        return new Banca(null, nome, descricao);
    }
}