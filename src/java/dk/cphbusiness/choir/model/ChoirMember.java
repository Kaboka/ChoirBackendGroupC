/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.cphbusiness.choir.model;

import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author
 * kasper
 */
@Entity
@Table(name = "MEMBER")
@DiscriminatorValue(value = "ChoirMember")
@NamedQueries({
    @NamedQuery(name = "ChoirMember.findAll", query = "SELECT m FROM ChoirMember m"),
    @NamedQuery(name = "ChoirMember.findById", query = "SELECT m FROM ChoirMember m WHERE m.id = :id"),
    @NamedQuery(name = "ChoirMember.findByStreet", query = "SELECT m FROM ChoirMember m WHERE m.street = :street"),
    @NamedQuery(name = "ChoirMember.findByZipcode", query = "SELECT m FROM ChoirMember m WHERE m.zipcode = :zipcode"),
    @NamedQuery(name = "ChoirMember.findByCity", query = "SELECT m FROM ChoirMember m WHERE m.city = :city"),
    @NamedQuery(name = "ChoirMember.findByPhone", query = "SELECT m FROM ChoirMember m WHERE m.phone = :phone"),
    @NamedQuery(name = "ChoirMember.findByEmail", query = "SELECT m FROM ChoirMember m WHERE m.email = :email"),
    @NamedQuery(name = "ChoirMember.findByPassword", query = "SELECT m FROM ChoirMember m WHERE m.password = :password")})
public class ChoirMember extends Person {
    private static final long serialVersionUID = 1L;
    
    @Size(max = 40)
    @Column(name = "STREET")
    private String street;
    @Column(name = "ZIPCODE")
    private String zipcode;
    @Size(max = 20)
    @Column(name = "CITY")
    private String city;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 30)
    @Column(name = "PHONE")
    private String phone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 40)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 30)
    @Column(name = "PASSWORD")
    private String password;
    @ManyToMany(mappedBy = "members")
    private Collection<ChoirRole> choirRoles;
    @JoinColumn(name = "CODE", referencedColumnName = "CODE")
    @ManyToOne
    private Voice voice;


    public ChoirMember() {
    }

    public ChoirMember(Integer id) {
        super(id);
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<ChoirRole> getChoirRoles() {
        return choirRoles;
    }

    public void setChoirRoles(Collection<ChoirRole> choirRoles) {
        this.choirRoles = choirRoles;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }
   
    @Override
    public String toString() {
        return "dk.cphbusiness.choir.model.Member1[ id=" + getId() + " ]";
    }
    
}
