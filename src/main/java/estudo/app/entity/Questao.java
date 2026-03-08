package estudo.app.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("questoes")
public record Questao(
        @Id Long id,
        String enunciado,
        @Column("alternativa_a") String alternativaA,
        @Column("alternativa_b") String alternativaB,
        @Column("alternativa_c") String alternativaC,
        @Column("alternativa_d") String alternativaD,
        @Column("alternativa_e") String alternativaE,
        @Column("alternativa_correta") String alternativaCorreta,
        @Column("assunto_id")   Long assuntoId,
        @Column("concurso_id")  Long concursoId
) {
    public static Questao nova(
            String enunciado,
            String alternativaA, String alternativaB, String alternativaC,
            String alternativaD, String alternativaE,
            String alternativaCorreta,
            Long assuntoId, Long concursoId) {
        return new Questao(null, enunciado,
                alternativaA, alternativaB, alternativaC, alternativaD, alternativaE,
                alternativaCorreta, assuntoId, concursoId);
    }

    public boolean isCorreta(String resposta) {
        return alternativaCorreta.equalsIgnoreCase(resposta);
    }
}
