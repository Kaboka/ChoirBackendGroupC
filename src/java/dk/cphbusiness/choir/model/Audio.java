/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.choir.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author
 * kasper
 */
@Entity
@Table(name = "AUDIO")
//@DiscriminatorValue(value = "Audio")
@NamedQueries({
    @NamedQuery(name = "Audio.findAll", query = "SELECT a FROM Audio a")})
public class Audio extends Material {
    private static final long serialVersionUID = 1L;
    
    
    @Column(name = "PLAYING_TIME")
    private int playingTime;


    public Audio() {
    }

    public Audio(Integer id) {
        super(id);
    }

    public int getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(int playingTime) {
        this.playingTime = playingTime;
    }

    @Override
    public String toString() {
        return "dk.cphbusiness.choir.model.Audio[ id=" + getId() + " ]";
    }
    
}
