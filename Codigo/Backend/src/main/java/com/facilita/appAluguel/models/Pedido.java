import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dataCriacao", nullable = false)
    private LocalDateTime dataCriacao;
    @Column(name = "dataInicio", nullable = false)
    private LocalDate dataInicio;
    @Column(name = "dataFim", nullable = false)
    private LocalDate dataFim;
}
