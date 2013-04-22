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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author
 * kasper
 */
@Entity
@Table(name = "CHOIR_ROLE")
@NamedQueries({
    @NamedQuery(name = "ChoirRole.findAll", query = "SELECT c FROM ChoirRole c"),
    @NamedQuery(name = "ChoirRole.findByCode", query = "SELECT c FROM ChoirRole c WHERE c.code = :code"),
    @NamedQuery(name = "ChoirRole.findByName", query = "SELECT c FROM ChoirRole c WHERE c.name = :name")})
public class ChoirRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CODE")
    private String code;
    @Size(max = 40)
    @Column(name = "NAME")
    private String name;
    @JoinTable(name = "HAS_ROLE", joinColumns = {
        @JoinColumn(name = "CODE", referencedColumnName = "CODE")}, inverseJoinColumns = {
        @JoinColumn(name = "ID", referencedColumnName = "ID")})
    @ManyToMany
    private Collection<ChoirMember> members;

    public ChoirRole() {
    }

    public ChoirRole(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ChoirMember> getMembers() {
        return members;
    }

    public void setMembers (Collection<ChoirMember> members) {
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
        if (!(object instanceof ChoirRole)) {
            return false;
        }
        ChoirRole other = (ChoirRole) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dk.cphbusiness.choir.model.ChoirRole[ code=" + code + " ]";
    }
    
}
