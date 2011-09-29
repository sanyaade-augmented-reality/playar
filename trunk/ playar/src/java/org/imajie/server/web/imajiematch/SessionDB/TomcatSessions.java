/*
 * Copyright (C) 2011 Carl Tremblay <carl_tremblay at imajie.tv>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.imajie.server.web.imajiematch.SessionDB;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carl Tremblay <carl_tremblay at imajie.tv>
 */
@Entity
@Table(name = "tomcat$sessions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TomcatSessions.findAll", query = "SELECT t FROM TomcatSessions t"),
    @NamedQuery(name = "TomcatSessions.findByValid", query = "SELECT t FROM TomcatSessions t WHERE t.valid = :valid"),
    @NamedQuery(name = "TomcatSessions.findByMaxinactive", query = "SELECT t FROM TomcatSessions t WHERE t.maxinactive = :maxinactive"),
    @NamedQuery(name = "TomcatSessions.findByLastaccess", query = "SELECT t FROM TomcatSessions t WHERE t.lastaccess = :lastaccess"),
    @NamedQuery(name = "TomcatSessions.findById", query = "SELECT t FROM TomcatSessions t WHERE t.id = :id")})
public class TomcatSessions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "valid")
    private char valid;
    @Basic(optional = false)
    @Column(name = "maxinactive")
    private int maxinactive;
    @Column(name = "lastaccess")
    private BigInteger lastaccess;
    @Lob
    @Column(name = "data")
    private byte[] data;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;

    public TomcatSessions() {
    }

    public TomcatSessions(String id) {
        this.id = id;
    }

    public TomcatSessions(String id, char valid, int maxinactive) {
        this.id = id;
        this.valid = valid;
        this.maxinactive = maxinactive;
    }

    public char getValid() {
        return valid;
    }

    public void setValid(char valid) {
        this.valid = valid;
    }

    public int getMaxinactive() {
        return maxinactive;
    }

    public void setMaxinactive(int maxinactive) {
        this.maxinactive = maxinactive;
    }

    public BigInteger getLastaccess() {
        return lastaccess;
    }

    public void setLastaccess(BigInteger lastaccess) {
        this.lastaccess = lastaccess;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!(object instanceof TomcatSessions)) {
            return false;
        }
        TomcatSessions other = (TomcatSessions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.imajie.server.web.imajiematch.SessionDB.TomcatSessions[ id=" + id + " ]";
    }
    
}
