package de.turing85;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "animals", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Animal {
  @Id
  @Column("id")
  Long id;

  @Column("name")
  String name = "";

  @Column("species")
  String species = "";
}