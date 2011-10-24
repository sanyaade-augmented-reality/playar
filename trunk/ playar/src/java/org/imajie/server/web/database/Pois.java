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
package org.imajie.server.web.database;

import java.io.Serializable;
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
@Table(name = "pois")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pois.findAll", query = "SELECT p FROM Pois p"),
    @NamedQuery(name = "Pois.findById", query = "SELECT p FROM Pois p WHERE p.id = :id"),
    @NamedQuery(name = "Pois.findByUser", query = "SELECT p FROM Pois p WHERE p.user = :user"),
    @NamedQuery(name = "Pois.findByLatitude", query = "SELECT p FROM Pois p WHERE p.latitude = :latitude"),
    @NamedQuery(name = "Pois.findByLongitude", query = "SELECT p FROM Pois p WHERE p.longitude = :longitude")})
public class Pois implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user", nullable = false, length = 50)
    private String user;
    @Basic(optional = false)
    @Column(name = "latitude", nullable = false, length = 25)
    private String latitude;
    @Basic(optional = false)
    @Column(name = "longitude", nullable = false, length = 25)
    private String longitude;
    @Basic(optional = false)
    @Lob
    @Column(name = "avatar", nullable = false)
    private byte[] avatar;

    public Pois() {
    }

    public Pois(Integer id) {
        this.id = id;
    }

    public Pois(Integer id, String user, String latitude, String longitude, byte[] avatar) {
        this.id = id;
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.avatar = avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
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
        if (!(object instanceof Pois)) {
            return false;
        }
        Pois other = (Pois) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.imajie.server.web.database.Pois[ id=" + id + " ]";
    }
    
}
