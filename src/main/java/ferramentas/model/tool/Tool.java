package ferramentas.model.tool;

import lombok.*;
import jakarta.persistence.*;
import ferramentas.model.audit.Audit;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tool  extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String link;

    @Column(length = 2000)
    private String description;

    @ElementCollection
    private List<String> tags;
}