package it.beachill.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "domain_match_type", schema = "tournament")
public class MatchType {
    @Id
    @Column(name = "type")
    private String type;
    private String description;
    @OneToMany(mappedBy = "matchType")
    private List<Match> matches;

    public MatchType() {}

    public MatchType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }


}
