/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.choir.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author
 * kasper
 */
@Entity
@Table(name = "ARTIST")
@DiscriminatorValue(value = "Artist")
@NamedQueries({
    @NamedQuery(name = "Artist.findAll", query = "SELECT a FROM Artist a"),
    @NamedQuery(name = "Artist.findPattern", query = "SELECT a FROM Artist a WHERE Concat(a.firstName,' ',a.lastName) LIKE :pattern")})
public class Artist extends Person{
    private static final long serialVersionUID = 1L;
    @Column(name = "DATE_OF_DEATH")
    @Temporal(TemporalType.DATE)
    private Date dateOfDeath;
    @Size(max = 20)
    @Column(name = "COUNTRY")
    private String country;
    @Size(max = 200)
    @Column(name = "WIKI_URL")
    private String wikiUrl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "composer")
    private Collection<Music> musicCollection;

    public Artist() {
    }

    public Artist(Integer id) {
        super(id);
    }


    public Date getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    public Collection<Music> getMusic() {
        return musicCollection;
    }

    public void setMusic(Collection<Music> musicCollection) {
        this.musicCollection = musicCollection;
    }


    @Override
    public String toString() {
        return "dk.cphbusiness.choir.model.Artist[ id=" + getId() + " ]";
    }
    
}
