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
@Table(name = "SHEET")
@DiscriminatorValue(value = "Sheet")
@NamedQueries({
    @NamedQuery(name = "Sheet.findAll", query = "SELECT s FROM Sheet s"),
    @NamedQuery(name = "Sheet.findById", query = "SELECT s FROM Sheet s WHERE s.id = :id"),
    @NamedQuery(name = "Sheet.findByPageCount", query = "SELECT s FROM Sheet s WHERE s.pageCount = :pageCount")})
public class Sheet extends Material {
    private static final long serialVersionUID = 1L;
   
    @Column(name = "PAGE_COUNT")
    private Integer pageCount;
    
    public Sheet() {
    }

    public Sheet(Integer id) {
        super(id);
    }


    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }


    @Override
    public String toString() {
        return "dk.cphbusiness.choir.model.Sheet[ id=" + getId() + " ]";
    }
    
}
