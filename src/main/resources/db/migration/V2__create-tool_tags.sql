create table tool_tags (
     tool_id bigint not null,
     tags varchar(255),
     CONSTRAINT fk_tool FOREIGN KEY (tool_id) REFERENCES tool(id) ON DELETE CASCADE
);