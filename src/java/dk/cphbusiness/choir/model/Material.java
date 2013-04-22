/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.choir.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author
 * kasper
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "MATERIAL")
@DiscriminatorColumn(name="DTYPE", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value = "Material")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Material.findAll", query = "SELECT m FROM Material m"),
    @NamedQuery(name = "Material.findById", query = "SELECT m FROM Material m WHERE m.id = :id"),
    @NamedQuery(name = "Material.findByTitle", query = "SELECT m FROM Material m WHERE m.title = :title"),
    @NamedQuery(name = "Material.findByFile", query = "SELECT m FROM Material m WHERE m.file = :file"),
    @NamedQuery(name = "Material.findByFileSize", query = "SELECT m FROM Material m WHERE m.fileSize = :fileSize"),
    @NamedQuery(name = "Material.findByType", query = "SELECT m FROM Material m WHERE m.type = :type")})
public abstract class Material implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 40)
    @Column(name = "TITLE")
    private String title;
    @Size(max = 50)
    @Column(name = "FILE")
    private String file;
    @Column(name = "FILE_SIZE")
    private Integer fileSize;
    @Size(max = 10)
    @Column(name = "TYPE")
    private String type;
    @JoinTable(name = "HAS_VOICE", joinColumns = {
    @JoinColumn(name = "ID", referencedColumnName = "ID")}, inverseJoinColumns = {
    @JoinColumn(name = "CODE", referencedColumnName = "CODE")})
    @ManyToMany
    private Collection<Voice> voices;
    @JoinColumn(name = "MUSIC_ID", referencedColumnName = "ID") 
    @ManyToOne(optional = false)
    private Music music;

    public Material() {
    }

    public Material(Integer id) {
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Collection<Voice> getVoices() {
        return voices;
    }

    public void setVoices(Collection<Voice> voices) {
        this.voices = voices;
    }
    public Music getMusic() {
        return music;
    }

    public void setMusic(Music musicId) {
        this.music = musicId;
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
        if (!(object instanceof Material)) {
            return false;
        }
        Material other = (Material) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dk.cphbusiness.choir.model.Material[ id=" + id + " ]";
    }
    
}
