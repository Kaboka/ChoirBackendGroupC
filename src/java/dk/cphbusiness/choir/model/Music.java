/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.choir.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author
 * kasper
 */
@Entity
@Table(name = "MUSIC")
@NamedQueries({
    @NamedQuery(name = "Music.findAll", query = "SELECT m FROM Music m"),
    @NamedQuery(name = "Music.findById", query = "SELECT m FROM Music m WHERE m.id = :id"),
    @NamedQuery(name = "Music.findByTitle", query = "SELECT m FROM Music m WHERE m.title = :title"),
    @NamedQuery(name = "Music.findByOpus", query = "SELECT m FROM Music m WHERE m.opus = :opus"),
    @NamedQuery(name = "Music.findByReleaseYear", query = "SELECT m FROM Music m WHERE m.releaseYear = :releaseYear"),
    @NamedQuery(name = "Music.findByPlace", query = "SELECT m FROM Music m WHERE m.place = :place"),
    @NamedQuery(name = "Music.findByHistory", query = "SELECT m FROM Music m WHERE m.history = :history")})
public class Music implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 40)
    @Column(name = "TITLE")
    private String title;
    @Column(name = "OPUS")
    private Integer opus;
    @Column(name = "RELEASE_YEAR")
    private Integer releaseYear;
    @Size(max = 40)
    @Column(name = "PLACE")
    private String place;
    @Size(max = 300)
    @Column(name = "HISTORY")
    private String history;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "music")
    private Collection<Material> materialCollection;
    @JoinColumn(name = "COMPOSER", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Artist composer;

    public Music() {
    }

    public Music(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOpus() {
        return opus;
    }

    public void setOpus(Integer opus) {
        this.opus = opus;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public Collection<Material> getMaterials() {
        return materialCollection;
    }

    public void setMaterials(Collection<Material> materialCollection) {
        this.materialCollection = materialCollection;
    }

    public Artist getComposer() {
        return composer;
    }

    public void setComposer(Artist composer) {
        this.composer = composer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Music)) {
            return false;
        }
        Music other = (Music) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dk.cphbusiness.choir.model.Music[ id=" + id + " ]";
    }
    
}
