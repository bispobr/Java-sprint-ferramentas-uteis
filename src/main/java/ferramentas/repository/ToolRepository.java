package ferramentas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ferramentas.model.tool.Tool;

import java.util.List;


public interface ToolRepository extends JpaRepository<Tool, Long> {

    @Query("SELECT t FROM Tool t JOIN t.tags tag WHERE tag = :tag")
    List<Tool> findByTag(String tag);

    @Query("""
       SELECT DISTINCT t 
       FROM Tool t 
       JOIN t.tags tag 
       WHERE LOWER(tag) IN :tags
       """)
    List<Tool> findByTagsIgnoreCase(List<String> tags);
}
