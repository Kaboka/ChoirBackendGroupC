/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.choir.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "VOICE")
@NamedQueries({
    @NamedQuery(name = "Voice.findAll", query = "SELECT v FROM Voice v"),
    @NamedQuery(name = "Voice.findByCode", query = "SELECT v FROM Voice v WHERE v.code = :code"),
    @NamedQuery(name = "Voice.findByName", query = "SELECT v FROM Voice v WHERE v.name = :name"),
    @NamedQuery(name = "Voice.findByGroupName", query = "SELECT v FROM Voice v WHERE v.groupName = :groupName")})
public class Voice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE")
    private Integer code;
    @Size(max = 40)
    @Column(name = "NAME")
    private String name;
    @Size(max = 40)
    @Column(name = "GROUP_NAME")
    private String groupName;
    @ManyToMany(mappedBy = "voices")
    private Collection<Material> materials;
    @OneToMany(mappedBy = "voice")
    private Collection<ChoirMember> members;

    public Voice() {
    }

    public Voice(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Collection<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(Collection<Material> materials) {
        this.materials = materials;
    }

    public Collection<ChoirMember> getMembers() {
        return members;
    }

    public void setMembers(Collection<ChoirMember> members) {
        this.members = members;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Voice)) {
            return false;
        }
        Voice other = (Voice) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dk.cphbusiness.choir.model.Voice[ code=" + code + " ]";
    }
    
}
